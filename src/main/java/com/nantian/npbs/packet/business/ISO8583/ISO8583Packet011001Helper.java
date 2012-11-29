package com.nantian.npbs.packet.business.ISO8583;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 * 新奥燃气IC卡查询
 * @author wzd
 *
 */
@Component
public class ISO8583Packet011001Helper extends ISO8583Packetxxx001Helper {
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet011001Helper.class);

	@Override
	protected void unpackField55(Object field, BusinessMessage bm){
		logger.info("新奥燃气IC卡[55]域解包----开始");
		
		//解包pos上送55域卡信息内容至XAICCardData实体对象，成功解包到实体对象后放于bm中的customData。
		XAICCardData xaicCardData = new XAICCardData();
		ISO8583FieldICCardUtil.unpackXAICCardFieldICCard(field, xaicCardData);
		bm.setCustomData(xaicCardData);
		
		
		logger.info("新奥燃气IC卡[55]域解包----结束");
	}
	
	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		
		//检查交易是否成功
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}
		
		StringBuffer str = new StringBuffer();
		XAICCardData cardData = null;
		
		if(null != bm.getCustomData() && bm.getCustomData() instanceof XAICCardData) {
			cardData = (XAICCardData)bm.getCustomData();
		}
		
		
		if(null != cardData) {
			
			if(DoubleUtils.sub(Double.valueOf(cardData.getXAIC_Buy()), 0.0) <=0) {
				
				//客户姓名
				if(null != cardData.getXAIC_Name() && !"".equals(cardData.getXAIC_Name())) {
					str.append("客户姓名：").append(cardData.getXAIC_Name()).append("\n");
				}
				
				//本次最大购气量
				if(null != cardData.getXAIC_MaxGas()&& !"".equals(cardData.getXAIC_MaxGas())) {
					str.append("最大购气量：").append(cardData.getXAIC_MaxGas()).append("方\n");
				}
				
				//账户余额
				if(null != cardData.getXAIC_Amt() && !"".equals(cardData.getXAIC_Amt())) {
					str.append("账户余额：").append(cardData.getXAIC_Amt()).append("元\n");
				}
							
				
			}else {
				//客户姓名
				if(null != cardData.getXAIC_Name() && !"".equals(cardData.getXAIC_Name())) {
					str.append("客户姓名：").append(cardData.getXAIC_Name()).append("\n");
				}			
			
				//购气单价
				if(null != cardData.getXAIC_Amt1()&& !"".equals(cardData.getXAIC_Amt1())) {
					str.append("购气单价：").append(cardData.getXAIC_Amt1()).append("元\n");
				}
				
				//购气金额
				if(null != cardData.getXAIC_Cost()&& !"".equals(cardData.getXAIC_Cost())) {
					str.append("购气金额：").append(Double.valueOf(cardData.getXAIC_Cost())/100).append("元\n");
				}	
			}
			
			
		}
		
		if(null != bm.getResponseMsg()) {
			str.append(bm.getResponseMsg());
		}				
		bm.setResponseMsg(str.toString());		
		return str.toString();
	}
	
	@Override
	protected String packField55(BusinessMessage bm)
			throws PacketOperationException {
		logger.info("新奥燃气IC卡查询打包55域----开始");
		
		
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		
		XAICCardData cashData = null;
		
		
		if(null != bm.getCustomData() && bm.getCustomData() instanceof XAICCardData) {
			cashData = (XAICCardData)bm.getCustomData();
		}
		
		Object[] values = new Object[70];
		int hasFields[] = {64,57,58};	
		String buffer = null;
		
		if(null != cashData) {
			
			//购气金额
			values[64] = null == cashData.getXAIC_Cost()? "": cashData.getXAIC_Cost();		
			
			
			//本次最大购气量
			values[57] = null == cashData.getXAIC_MaxGas() ? "" : cashData.getXAIC_MaxGas();
			
			//账户余额
			values[58] = null == cashData.getXAIC_Amt() ? "" : cashData.getXAIC_Amt();
			
			buffer = ElectricField55Utils.packElectricField55(hasFields, values);
			
		}
		
		if(null == buffer) {
			logger.info("打包55域值为空！");			
		}		
			
		logger.info("新奥燃气IC卡查询打包55域----结束");
		return buffer;
		
	}
}
