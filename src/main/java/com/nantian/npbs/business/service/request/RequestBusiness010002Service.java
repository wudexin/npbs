package com.nantian.npbs.business.service.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TempData;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.ElectricityCashData;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

@Scope("prototype")
@Component
public class RequestBusiness010002Service extends  RequestBusiness002Service {

private static Logger logger = LoggerFactory.getLogger(RequestBusiness010002Service.class);
	
	@Override
	protected boolean getTempValue(ControlMessage cm,BusinessMessage bm){

			try{
				TempData	cashTemp = (TempData)baseHibernateDao.get(TempData.class, bm.getOldPbSeqno());
					if(cashTemp ==null){
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("缴费失败,010001缴费查询时没有保存扣减金额等重要信息!");
						logger.info("010001查询时,电力电费扣减金额等信息没有保存,商户号: {}" , bm.getShopCode() );
						return false;
					}
					
					String str = cashTemp.getTempValue(); 
					
					ElectricityICCardData cashData = null;
					if(bm.getCustomData() instanceof ElectricityICCardData) {
						cashData = (ElectricityICCardData) bm.getCustomData();
					}
					
					
					if(cashData == null) {
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("缴费失败,数据出错");
						logger.info("010001查询时,数据出错,商户号: {}" , bm.getShopCode() );
						return false;
					}
					
					//设置扣减金额信息
					cashData.setBuckleAmt(Double.valueOf(str));
					bm.setCustomData(cashData);
			}catch(Exception e){
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("缴费不正常,请拨打客服电话咨询!");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("缴费不正常,请拨打客服电话咨询!");
				logger.error("缴费不正常!请查询表:CashTemp.商户号" + bm.getShopCode());
				return false;
			}
			return true;
	}
	
}
