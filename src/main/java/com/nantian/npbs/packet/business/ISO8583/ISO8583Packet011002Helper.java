package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 * 新奥燃气IC卡缴费
 * @author wzd
 *
 */
@Component
public class ISO8583Packet011002Helper extends ISO8583Packetxxx002Helper {
	private static Logger logger = LoggerFactory.getLogger(ISO8583Packet011002Helper.class);
	
	@Override
	protected void unpackField55(Object Field, BusinessMessage bm) {
		logger.info("新奥燃气IC缴费====解包55域信息=====开始");
		//使用XAICCardData对象保存解包后的55域信息，并放于bm的customdata中
		XAICCardData icData = new XAICCardData();
		
		//组织需要解包的55域数据字段信息，详细信息参见ElectricField55Config
		int hasFields[] = {54,55,59,60,61}; 
		
		String buffer = (String)Field;
		
		try {
			Object[] values = ElectricField55Utils.unpackElectricField55(hasFields, buffer);
			
			//Ic卡号
			String xAIC_id = (String)values[54];
			icData.setXAIC_Id(xAIC_id);
			bm.setUserCode(xAIC_id);			
			
			//备注信息
			String xAIC_bz = (String)values[55];
			icData.setXAIC_Bz(xAIC_bz);
			
			//发卡次数
			String xAIC_no = (String)values[59];
			icData.setXAIC_No(xAIC_no);
			
			//客户预购气量
			String xAIC_buy = (String)values[60];
			icData.setXAIC_Buy(xAIC_buy);
			
			//加密串
			String xAIC_ifo = (String)values[61];
			icData.setXAIC_Ifo(xAIC_ifo);
			
		} catch (Exception e) {
			logger.info("新奥燃气Ic卡-解包55域--出错");
			e.printStackTrace();
		}
		
		bm.setCustomData(icData);	
		
		logger.info("新奥燃气IC缴费====解包55域信息=====结束");
	}


	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {
		
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return bm.getResponseMsg();
		}
		StringBuffer str = new StringBuffer();
		XAICCardData  icData = null;
		if(null != bm.getCustomData() && bm.getCustomData() instanceof XAICCardData) {
			icData = (XAICCardData)bm.getCustomData();
		}
		
		if(null != icData) {
			str.append("购气方数:").append(Integer.valueOf(icData.getXAIC_Buy())).append("方\n");
		}
		
		String lowInfo = super.packField44(bm);
		
		if(null != lowInfo && !"".equals(lowInfo)) {
			str.append(lowInfo);
		}		
		bm.setResponseMsg(str.toString());
		return str.toString();
	}

	@Override
	protected String packField55(BusinessMessage bm)
			throws PacketOperationException {
		//判读系统是否处理成功
		if(GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			logger.info("打包55域返回值为空");
			return "";
		}
		
		//组织需要打包的55域内容
		int hasFields[] = {61};		
		Object[] valuse = new Object[70];
		
		if(null != bm.getCustomData() && bm.getCustomData() instanceof XAICCardData) {
			XAICCardData customData = (XAICCardData)bm.getCustomData();
			
			//加密串
			valuse[61] =  null == customData.getXAIC_Ifo() ? "" : customData.getXAIC_Ifo();			
			
			
			String buffer = null;
			
			//打包55域字段信息
			buffer = ElectricField55Utils.packElectricField55(hasFields, valuse);
			
			return buffer;			
		
		}else {
			logger.info("程序error！");
			return "";
		}		
	}
	
}
