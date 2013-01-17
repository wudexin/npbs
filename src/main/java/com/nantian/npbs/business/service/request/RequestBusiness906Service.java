package com.nantian.npbs.business.service.request;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 参数管理
 * 
 * @author
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness906Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(RequestBusiness906Service.class);

	@Resource
	private TradeDao tradeDao;

	@Override
	protected boolean checkCommon(ControlMessage cm, BusinessMessage bm) {
		// 目前参数管理只有检查进程
		return true;
	}

	@Override
	protected void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		// 开始业务处理
		logger.info("参数管理业务处理开始：");
		// 查询参数项表
		List<Object[]> paraitemList = findParaitemList(cm, bm);
		if (null == paraitemList) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("参数管理表为空,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("参数管理表为空,请拨打客服电话咨询!");
			logger.error("参数管理表为空,查询表：tb_sm_paraitem ");
			return;
		}
		// 拼接55位元字符串
		try {
			bm.setCustomData(getStringByParaitem(paraitemList));
		} catch (Exception e) {
			logger.error("拼接55域错误"+e);
		}
		
		//修改数据库参数下载为0
		bm.getShop().setParaFlag(GlobalConst.SHOP_PARAFLAG_NO);
		companyDao.update(bm.getShop());

		logger.info("参数管理业务处理结束");
	}

	/**
	 * 查询参数项表tb_sm_paraitem,获取PARA_CODE='005' 的对象
	 * 
	 * @param cm
	 * @param bm
	 * @return
	 */
	private List<Object[]> findParaitemList(ControlMessage cm,
			BusinessMessage bm) {
		List<Object[]> paraitemList = new ArrayList<Object[]>();
		//	String sql = "select t.PARA_VALUENAME,t.PARA_VALUE FROM TB_SM_PARAITEM t WHERE t.PARA_CODE='005' order by t.PARA_VALUENAME";
		  String sql="SELECT T.PARA_VALUENAME,T.PARA_VALUE FROM TB_SM_PARAITEM T WHERE T.PARA_CODE='005' "+
		   "UNION  ALL "+
		  "select ND_PARA_VALUENAME,ND_PARA_VALUE from TB_BI_COMPANY_PARA tp where tp.NDCOMPANY_CODE='"+bm.getShopCode()+"'";
		try {
			paraitemList = tradeDao.findInfoList(sql);
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("查询参数管理表出错,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("查询参数管理表出错,请拨打客服电话咨询!");
			logger.error("查询参数管理表出错,查询表：tb_sm_paraitem ");
		}
		return paraitemList;
	}

	/**
	 * 拼接55位元字符串
	 * 
	 * @param paraitemList
	 * @return
	 * @throws Exception 
	 */
	public String getStringByParaitem(List<Object[]> paraitemList) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		for (Object[] tbSmParaitem : paraitemList) {
			String paraValueName = tbSmParaitem[0].toString().trim().substring(0, 2);
			String paraValue = tbSmParaitem[1].toString().trim();
			if ("11".equals(paraValueName)) {
				stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.leftAddZero4FixedLengthString(paraValue,2));
			} else if ("12".equals(paraValueName)) {
				stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.leftAddZero4FixedLengthString(paraValue,4));
			} else if ("13".equals(paraValueName)) {
				stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.leftAddZero4FixedLengthString(paraValue,1));
			} else if ("14".equals(paraValueName)) {
				stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.leftAddZero4FixedLengthString(paraValue,4));
			} else if ("15".equals(paraValueName)) {
				stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.leftAddZero4FixedLengthString(paraValue,4));
			//add by fengafang  
			 }else if("16".equals(paraValueName)){
		 		stringBuffer = stringBuffer.append(paraValueName).append(FieldUtils.rightAddSpace4FixedLengthString(paraValue,50));
			} else{
				logger.info("无此种数据类型!");
			}
		}
		logger.info("参数:{}" , stringBuffer);
		return stringBuffer.toString();
	}

	protected String tradeType() {
		// 管理类型的交易
		return "08";
	}

	@Override
	public boolean needLockProcess() {
		// 需要进程检查
		return true;
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
}
