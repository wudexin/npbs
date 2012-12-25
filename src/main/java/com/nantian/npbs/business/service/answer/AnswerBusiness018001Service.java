package com.nantian.npbs.business.service.answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.ElectricityCashData;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.ZJKRQ;

@Scope("prototype")
@Component
public class AnswerBusiness018001Service extends  AnswerBusiness001Service{

	private static Logger logger = LoggerFactory.getLogger(AnswerBusiness018001Service.class);
	
	/**
	 * 保存现金缴费临时表数据
	 * add by fengyafang 
	 */
	@Override
	protected void setTempValue(ControlMessage cm,BusinessMessage bm){
		
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();
			TempData ct = new TempData();
			ct.setPbSeqno(bm.getPbSeqno());
			StringBuffer sb=new StringBuffer();
			sb.append(customData.getCHECK_ID() == null?" ":customData.getCHECK_ID()).append("^");//对帐批次
			sb.append(customData.getCONS_NO() == null?" ":customData.getCONS_NO()).append("^");//客户编号
			sb.append(	customData.getMETER_ID() == null?" ":customData.getMETER_ID()).append("^");//电能表编号
			sb.append(	customData.getMETER_FLAG() == null?"" :customData.getMETER_FLAG()).append("^");//电能表标识
			sb.append(	customData.getCARD_INFO() == null?" ":customData.getCARD_INFO()).append("^");//卡内信息
			sb.append(	customData.getIDDATA() == null?" ":customData.getIDDATA()).append("^");//卡片信息
			sb.append(	customData.getCONS_NAME() == null?" ":customData.getCONS_NAME()).append("^");//用户名称
			sb.append(	customData.getCONS_ADDR() == null?" ":customData.getCONS_ADDR()).append("^");//用电地址
			sb.append(	customData.getPAY_ORGNO() == null?" ":customData.getPAY_ORGNO()).append("^");//核算单位
			sb.append(	customData.getORG_NO() == null?" ":customData.getORG_NO()).append("^");//供电单位  
			sb.append(	customData.getCHARGE_CLASS() == null?" ":customData.getCHARGE_CLASS()).append("^");//预付费类别
			sb.append(	customData.getFACTOR_VALUE() == null?" ":customData.getFACTOR_VALUE()).append("^");//综合倍率 
			sb.append(	customData.getPURP_PRICE() == null?" ":customData.getPURP_PRICE()).append("^") ;//购电电价
			sb.append(	customData.getCARD_NO() == null?" ":customData.getCARD_NO()).append("^") ;//电卡编号
			sb.append(	customData.getOCS_MODE() == null?" ":customData.getOCS_MODE()).append("^") ;//费控方式
			sb.append(	customData.getPRESET_VALUE() == null?" ":customData.getPRESET_VALUE()) ;//预置值
			ct.setTempValue(sb.toString());
			ct.setTradeDate(bm.getTranDate());
			
			try {
				baseHibernateDao.save(ct);
			} catch (Exception e) {
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("保存现金缴费临时信息失败!");
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("保存现金缴费临时信息失败!");
				logger.error("保存现金缴费临时信息失败！insert into TB_BI_CASH_TEMP err", e);
			}
		
	}
}