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
import com.nantian.npbs.packet.internal.ZJKRQ;

@Scope("prototype")
@Component
public class RequestBusiness020002Service extends  RequestBusiness002Service {

private static Logger logger = LoggerFactory.getLogger(RequestBusiness020002Service.class);
	
	@Override
	protected boolean getTempValue(ControlMessage cm,BusinessMessage bm){

			try{
				TempData	cashTemp = (TempData)baseHibernateDao.get(TempData.class, bm.getOldPbSeqno());
					if(cashTemp ==null){
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("缴费失败,请拨打客服电话咨询!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("缴费失败,010001缴费查询时没有保存电费明细数据等重要信息!");
						logger.info("010001查询时,电力电费明细数据等信息没有保存,商户号: {}" , bm.getShopCode() );
						return false;
					}
					
					String str = cashTemp.getTempValue(); 
					String[] split = str.split("\\|");
					Double totalBill = Double.parseDouble(split[0]);
					
					// 缴费金额必须大于应缴金额
					if(totalBill > bm.getAmount()){
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("缴费金额小于应缴金额!");
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("缴费金额小于应缴金额!");
						return false;
					}
					ZJKRQ cashData = new ZJKRQ();
					cashData.setSUM_FEE(split[0]);
					cashData.setUSER_NAME(split[1]);
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
