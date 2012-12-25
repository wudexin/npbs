package com.nantian.npbs.business.service.answer;

/**
 * 华电国标、河电国标、河电省标写卡
 * @author MDB
 */
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiTradeContrast;
import com.nantian.npbs.business.model.TbBiTradeContrastId;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
@Scope("prototype")
@Component
public class AnswerBusiness003Service extends AnswerBusinessService {
	private static Logger logger = LoggerFactory
			.getLogger(AnswerBusiness003Service.class);

	@Resource
	private CommonPrepay commonPrepay;

	@SuppressWarnings("unchecked")
	@Override
	public void dealBusiness(ControlMessage cm, BusinessMessage bm) {
		logger.info("写卡交易响应处理!");

		// 如果是河电省标卡则处理备付金
		if ("010003".equals(bm.getTranCode())) {
			//删除缴费上限授权记录
			deleteAuthorizeAmount(cm, bm);
		   //处理备付金
			commonPrepay.payPrepay(cm, bm);	
			//更新原缴费流水状态为成功
			tradeDao.updateTradeStatus(bm.getTranDate(), bm.getOldPbSeqno(), 
						GlobalConst.TRADE_STATUS_SUCCESS);			
		}
		
		// 如果是华电IC卡则增加写卡流水对照
		if ("013003".equals(bm.getTranCode())) {
			
			//检查是否重复写卡
			StringBuffer sql = new StringBuffer();
			List list = null;
			sql.append(" select count(*) from tb_bi_trade_contrast tc " +
						"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
						"and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"'");
			try {
				list = tradeDao.queryBySQL(sql.toString());
			} catch (Exception e) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("写卡失败!系统错误:检测重复写卡错误!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("写卡失败!系统错误:检测重复写卡错误!");
				logger.error("写卡失败!系统错误:检测重复写卡错误!SQL[{}]",sql.toString());
				e.printStackTrace();
				return;
			}
			if(null != list && list.size() == 1){
				Integer number = Integer.valueOf(list.get(0).toString().trim());
				
				//首次写卡新增缴费、写卡关联
				if(number == 0){
					TbBiTradeContrast tc = new TbBiTradeContrast();
					TbBiTradeContrastId id = new TbBiTradeContrastId();
					id.setPbSerial(bm.getPbSeqno());//本交易（写卡）流水
					id.setOriPbSerial(bm.getOldPbSeqno());//POS上送现金缴费流水
					id.setTradeDate(bm.getTranDate());
					tc.setId(id);
					tc.setTradeType(GlobalConst.TRADE_TYPE_XK);
					
					logger.info("写卡流水对照：类型[{}],交易流水（写卡流水）[{}],原交易流水（缴费流水）[{}]",
							new Object[]{tc.getTradeType(), 
								tc.getId().getPbSerial(), 
								tc.getId().getOriPbSerial()});
					
					baseHibernateDao.save(tc);
					return;
				}
				//非首次写卡，更新缴费、写卡关联
				if(number == 1){
					StringBuffer update = new StringBuffer();
					update.append("update tb_bi_trade_contrast tc set tc.pb_serial='"+bm.getPbSeqno()+"' " +
							"where tc.ori_pb_serial = '"+bm.getOldPbSeqno()+"' " +
							"and tc.trade_type='"+GlobalConst.TRADE_TYPE_XK+"'");
					try {
						if(tradeDao.updateTradeContrast(update.toString())){
							return;
						}else{
							bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
							bm.setResponseMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
							cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
							cm.setResultMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("写卡失败!系统错误:更新缴费、写卡关联异常!");
						logger.error("写卡失败!系统错误:更新缴费、写卡关联异常!!SQL[{}]",update.toString());
						return;
					}
				}
				//其它视为异常!
				else{
					bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
					bm.setResponseMsg("写卡失败!系统错误:出现多笔缴费写卡关联!");
					cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
					cm.setResultMsg("写卡失败!系统错误:出现多笔缴费写卡关联!");
					return;
				}
			}
			
		}
		return;
	}

	public boolean returnMsg(BusinessMessage bm) {
		if ("01".equals(bm.getResponseCode())) {
			bm.setResponseMsg("与第三方通讯故障");
		} else if ("02".equals(bm.getResponseCode())) {
			bm.setResponseMsg("第三方返回错误");
		} else if ("03".equals(bm.getResponseCode())) {
			bm.setResponseMsg("原交易无法取消");
		} else if ("11".equals(bm.getResponseCode())) {
			bm.setResponseMsg("帐号不存在");
		} else if ("12".equals(bm.getResponseCode())) {
			bm.setResponseMsg("账户余额不足");
		} else if ("16".equals(bm.getResponseCode())) {
			bm.setResponseMsg("账户状态不正常");
		} else if ("61".equals(bm.getResponseCode())) {
			bm.setResponseMsg("密码错误");
		} else if ("64".equals(bm.getResponseCode())) {
			bm.setResponseMsg("密码错误次数超限");
		} else if ("LA".equals(bm.getResponseCode())) {
			bm.setResponseMsg("本交易不支持跨省交易，交易拒绝");
		} else if ("LL".equals(bm.getResponseCode())) {
			bm.setResponseMsg("卡磁道信息不合法");
		} else if ("N2".equals(bm.getResponseCode())) {
			bm.setResponseMsg("卡号无效");
		} else if ("ZZ".equals(bm.getResponseCode())) {
			bm.setResponseCode("99");
			bm.setResponseMsg("系统内部错, 请拨打客服中心查询该笔交易是否正常!");
			bm.setPbState("01");
		} else if (bm.getResponseMsg().length() == 0) {
			bm.setResponseMsg("系统错误请联系客服！");
		}
		return true;
	}

}
