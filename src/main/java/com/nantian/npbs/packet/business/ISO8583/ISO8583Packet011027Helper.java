package com.nantian.npbs.packet.business.ISO8583;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.FieldUtils;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 * 新奥燃气IC联动取消
 * @author wzd
 *
 */
@Component
public class ISO8583Packet011027Helper extends ISO8583Packetxxx012Helper{
	
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet011027Helper.class);

	/**
	 * 解包55域函数
	 */
	@Override
	protected void unpackField55(Object Field, BusinessMessage bm) {
		logger.info("新奥燃气取消--解包55域--开始！");
		XAICCardData icData = new XAICCardData();
		
		//定义需要解析的55域字段信息---配置参见int hasFields[] = {54,55,59,60,61};
		int hasFields[] = {54,55,59,61};
		String buffer = (String)Field;		
		Object[] values = new Object[100];
		
		try {
			values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);
			
			//IC卡号
			String xAIC_id = (String)values[54];
			icData.setXAIC_Id(xAIC_id);
			
			//备注信息
			String xAIC_bz = (String)values[55];
			icData.setXAIC_Bz(xAIC_bz);		
			
			//发卡次数
			String xAIC_no = (String)values[59];
			icData.setXAIC_No(xAIC_no);		
		
			
			//加密串	
			String xAIC_ifo = (String)values[61];
			icData.setXAIC_Ifo(xAIC_ifo);
			
			bm.setCustomData(icData);
		} catch (Exception e) {
			logger.info("解包55域出错！");
			e.printStackTrace();
		}		
	
		
		logger.info("新奥燃气取消--解包55域--结束！");	
		
	}	
	
}
