 package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.business.service.internal.TelePhoneNumUtils;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.Telecommunications;

/**
 * 业务查询
 * 
 * @author fengyafang 
 * 
 */
@Scope("prototype")
@Component
public class RequestBusiness018002Service extends RequestBusiness002Service{
	private static Logger logger = LoggerFactory
	.getLogger(RequestBusiness018002Service.class);

	@Override
	protected boolean getTempValue(ControlMessage cm, BusinessMessage bm) {
		 
		TempData cashTemp = (TempData) baseHibernateDao.get(TempData.class,
				bm.getOldPbSeqno());
		if (cashTemp == null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费失败,现金查询时没有保存电费明细数据等重要信息!");
			logger.info("现金查询时,电力电费明细数据等信息没有保存,商户号:{} " , bm.getShopCode());
			return false;
		}
		if(bm.getCustomData()!=null){
		HeNDElecICCard customData=(HeNDElecICCard)bm.getCustomData();
		String[] split = String.valueOf(cashTemp.getTempValue()).split("\\^");
		 customData.setCHECK_ID(split[0]);    
		  customData.setCONS_NO(split[1]);     
		 customData.setMETER_ID(split[2]);    
		 customData.setMETER_FLAG(split[3]);  
		 customData.setCARD_INFO(split[4]);   
		 customData.setIDDATA(split[5]);      
		 customData.setCONS_NAME(split[6]);   
		 customData.setCONS_ADDR(split[7]) ;  
		 customData.setPAY_ORGNO(split[8]);   
		 customData.setORG_NO(split[9]) ;     
		 customData.setCHARGE_CLASS(split[10]);
		 customData.setFACTOR_VALUE(split[11]);
		 customData.setPURP_PRICE(split[12])  ;
		 customData.setCARD_NO(split[13])  ;
		 customData.setOCS_MODE(split[14])  ;
		 customData.setPRESET_VALUE(split[15])  ;
		bm.setCustomData(customData);
		return true;
		}else{
			return false;
		}
	
	}
 
	
}
