package com.nantian.npbs.business.service.request;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 备付金明细查询
 * 
 * @author HuBo
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness804Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness804Service.class);

	@Resource
	public PrepayDao prepayDao;

	@Resource
	CommonPrepay commonPrepay;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		if (!checkShopState(cm, bm)) { // 检查商户状态
			return false;
		}
		
		//检查查询时间段有效性
		if(!checkDate(cm, bm)) { 
			return false;
		}
		logger.info("公共校验成功!");
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("开始业务处理：");
		boolean suc = false;

		suc = dealPrepay(cm, bm);
		if (suc == false) {
			return;
		}
	}

	/**
	 * 返回交易类型 01-缴费交易；02-取消交易；04-冲正交易
	 * 
	 * @return
	 */
	protected String tradeType() {
		return "07";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.nantian.npbs.business.service.request.RequestBusinessService#
	 * needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		// 不发送第三方
		cm.setServiceCallFlag("0");

	}

	public boolean dealPrepay(ControlMessage cm, BusinessMessage bm) {
		logger.info("备付金明细查询开始！");

		String queryType = bm.getQueryType();
		if (null == queryType) {
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("请上送查询类型！");
			logger.info("请上送查询类型！");
			return false;
		}

		String prepayAccno = prepayDao.searchPreAccnoBySA(bm.getShopCode()); // 查询商户备付金账号
		String queryStartDate = new String(new StringBuffer(bm.getQueryStartDate()).append("000000"));// 查询开始时间
		String queryEndDate = new String(new StringBuffer(bm.getQueryEndDate()).append("235959"));// 查询结束时间
		//int numPerPage = Integer.parseInt(bm.getNumPerPage());// 每页条数
		int numPerPage = 100;                                   //接口确定每批最多100笔
		int packageNum = Integer.parseInt(bm.getPackageNum());// 第几批
		
		
		

		// 查询总页数
		String numSql;
		String sql;
		if (queryType.equals("01")) {
			// 退费
			numSql = "select count(*) from tb_bi_prepay_info t where t.accno = '"
					+ prepayAccno
					+ "' and t.trade_time <= '"
					+ queryEndDate
					+ "' and t.trade_time >= '"
					+ queryStartDate
					+ "' and t.busi_code = '000'";
			sql = "select * from (select a.*,rownum rn from (select t.trade_time,t.flag,t.amount,t.busi_code,t.customerno,t.status,t.trade_date from tb_bi_prepay_info t where t.accno = '"
					+ prepayAccno
					+ "' and t.trade_time <= '"
					+ queryEndDate
					+ "' and t.trade_time >= '"
					+ queryStartDate
					+ "' and t.busi_code = '000' order by t.trade_Time desc ) a where rownum <= "
					+ packageNum
					* numPerPage
					+ " ) where rn >"
					+ (packageNum - 1) * numPerPage;
		} else if (queryType.equals("02")) {
			numSql = "select count(*) from tb_bi_prepay_info t where t.accno = '"
					+ prepayAccno
					+ "' and t.trade_time <= '"
					+ queryEndDate
					+ "' and t.trade_time >= '" + queryStartDate + "'";
			sql = "select * from (select a.*,rownum rn from (select t.trade_time,t.flag,t.amount,t.busi_code,t.customerno,t.status,t.trade_date from tb_bi_prepay_info t where t.accno = '"
					+ prepayAccno
					+ "' and t.trade_time <= '"
					+ queryEndDate
					+ "' and t.trade_time >= '"
					+ queryStartDate
					+ "' order by t.trade_Time desc ) a where rownum <= "
					+ packageNum
					* numPerPage
					+ " ) where rn >"
					+ (packageNum - 1) * numPerPage;
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("无此查询类型！");			
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("无此查询类型");
			logger.info("无此查询类型！");
			return false;
		}

		logger.info("numSql:" + numSql);
		logger.info("sql:" + sql);

		int numSum = 0;
		try {
			numSum = prepayDao.getCountPrepayList(numSql);
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金明细总条数出错！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金明细总条数出错！");
			logger.info("查询备付金明细总条数出错！");
			return false;
		}

		if (!(numSum > 0)) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("无数据");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("无数据！");
			logger.info("备付金明细无数据！numSum = {}", numSum);
			return true;
		}

		// 查询明细
		try {

			ArrayList<Object> tbBiPrepayInfoList = (ArrayList) prepayDao
					.findPrepayInfoList(sql);
			if (null == tbBiPrepayInfoList) {
				cm.setResultMsg("无数据！");
				logger.info("备付金明细无数据！");
				return true;
			}

			// 设置总批数
			if(numSum%100 == 0) {
				bm.setPackageFlag(String.valueOf(numSum/numPerPage));
			}else {
				bm.setPackageFlag(String.valueOf(numSum/numPerPage+1));
			}
			
			
			/*if (numPerPage * packageNum < numSum) {2012年4月9日23:04:13删除
				bm.setPackageFlag("1");
			} else {
				cm.setResultMsg("无后续数据！");
				bm.setPackageFlag("0");
			}*/

			// 设置实际返回笔数
			bm.setNumActual(String.valueOf(tbBiPrepayInfoList.size()));

			bm.setJournalList(tbBiPrepayInfoList);// 设置备付金明细信息

			bm.setCustomData("备付金明细查询成功！");
			cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
			cm.setResultMsg("备付金明细查询成功!");
			logger.info("备付金明细查询成功");
		} catch (Exception e) {
			bm.setAdditionalTip("备付金明细查询出错，请与技术员联系");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询备付金明细出错。");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询备付金明细出错。");
			logger.info("查询备付金明细出错。");
			e.printStackTrace();
		}
		logger.info("查询备付金成功！");

		return true;
	}
	
	/**
	 * 功能： 判断bm中queryStartDate与queryEndDate两日期区间是否超过5日，区间是否包含当日日期。
	 * @param cm   
	 * @param bm
	 * @return  起始日期大于截止日期-->false  
	 * 			区间超过5日-->false 
	 *          区间包含当日-->false  否则返回true；
	 */
	private boolean checkDate(ControlMessage cm, BusinessMessage bm) {
		
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			//一天毫秒数
			long nd = 24*60*60*1000;
			//获取两个日期毫秒差值
			long diffEndToStart = sd.parse(bm.getQueryEndDate()).getTime() - sd.parse(bm.getQueryStartDate()).getTime();
			System.out.println(sd.parse(bm.getQueryEndDate()).getTime());
			
			if(diffEndToStart<0) {
				bm.setAdditionalTip("查询日期时间顺序不正确！");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询日期时间顺序不正确！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询日期时间顺序不正确！");
				return false;			
			}
			
			long day = diffEndToStart/nd;
			
			if(day>5) {
				bm.setAdditionalTip("查询日期范围不能超过5日");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询日期范围不能超过5日");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询日期范围不能超过5日");
				return false;	
			}			
		
			long diffEndToSysDate = sd.parse(bm.getQueryEndDate()).getTime() - sd.parse(bm.getTranDate()).getTime();
									
			if(diffEndToSysDate>=0) {
				bm.setAdditionalTip("查询日期不能包括当日");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("查询日期不能包括当日");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("查询日期不能包括当日");
				return false;				
			}
			
			return true;			
			
		} catch (ParseException e) {
			logger.info("查询日期转换类型出错！startdate:{},endDate:{},nowDate:{}",
					new Object[]{bm.getQueryStartDate(),bm.getQueryEndDate(),bm.getTranDate()});
			e.printStackTrace();
			bm.setAdditionalTip("上送数据格式不正确！");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("上送数据格式不正确");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("上送数据格式不正确");
			return false;		
		} 
		
		
	}
}