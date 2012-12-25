package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityCashData;

/**
 * 河电无IC卡缴费
 * @author qiaoxl
 * 
 */
@Component
public class ISO8583Packet007002Helper extends ISO8583Packetxxx002Helper {
	
	//addStart MDB 2012年1月12日 17:49:24
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet014002Helper.class);

	@Override
	protected String packField44(BusinessMessage bm) 
			throws PacketOperationException{
	
		String lowBalanceStr = super.packField44(bm);
		StringBuffer alertMsg = new StringBuffer();
		// 拼接错误信息
		if (bm.getResponseCode().equals(GlobalConst.RESPONSECODE_FAILURE)) {
			return bm.getResponseMsg();
		}
		
		ElectricityCashData cash = (ElectricityCashData)bm.getCustomData();
		if(null == cash){
			bm.setResponseMsg("系统错误,请联系管理员!");
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			return bm.getResponseMsg();
		}
		
		alertMsg.append("本次余额:"+cash.getThisBalance()+"元\n");
		
		//-------------add by wzd 2012年5月17日11:27:18 ---start
		if(null == cash.getJtdjxx() || cash.getJtdjxx().length<1) {
			logger.info("阶梯电价信息有问题！");
			
		}
		
		if("1".equals(cash.getJtdjxx()[0])) {
			if( cash.getJtdjxx().length < 3) {
				alertMsg.append("数据错误\n");
			}
			alertMsg.append(cash.getJtdjxx()[1]).append("\n");  //本年累计信息
			for(int i=2;i<cash.getJtdjxx().length-1;i++) {
				StringBuffer str = new StringBuffer();
				String[] sub1 = null;
				if(cash.getJtdjxx()[i].contains("：")) {
					sub1 =  cash.getJtdjxx()[i].split("：");
				}else if(cash.getJtdjxx()[i].contains(":")) {
					sub1 =  cash.getJtdjxx()[i].split(":");
				}else {
					sub1 = cash.getJtdjxx()[i].split(":");
				}
				
				if(sub1.length != 2) {
					alertMsg.append("数据错误\n");
				}else {
					if(sub1[0].contains("1档") || sub1[0].contains("一档")) {
						alertMsg.append("一档用电:").append(sub1[1]).append("\n");
					}else if(sub1[0].startsWith("2档")|| sub1[0].contains("二档")) {
						alertMsg.append("二档用电:").append(sub1[1]).append("\n");
					}else if(sub1[0].startsWith("3档")|| sub1[0].contains("三档")) {
						alertMsg.append("三档用电:").append(sub1[1]).append("\n");
					}					
				}			
			}
			alertMsg.append(cash.getJtdjxx()[cash.getJtdjxx().length-1]).append("\n");//第N档用电量
		}		
		//-------------add by wzd 2012年5月17日11:27:18 ---end
		
		
		//是否显示“本次余额”和“起止示数”
		logger.info("是否显示起止示数提示信息:[{}]",cash.getIsShowDetail());
		
		/**
		 * 添加对cash.getIsShowDetail()为null的判断---如果和电商超时时此值为null；
		 * 排除此处抛出异常pos端不锁屏，用户重新缴费，日终对照又补扣商户备付金情况发生
		 */
		//add by wzd  start 2012年5月8日15:38:53
		if(null == cash.getIsShowDetail()) {
			cash.setIsShowDetail(false);
		}
		//add by wzd end 2012年5月8日15:39:10
		
		
		
		if(true == cash.getIsShowDetail()){
			
			if(null != cash.getSeNum() && !"".equals(cash.getSeNum())) {
				alertMsg.append("起止示数：").append(cash.getSeNum()).append("\n");
			}
			
			alertMsg.append("--------------------------------\n");
			alertMsg.append("您为预交、多月或一户多表缴费,请到电力营业厅打印用电详单！\n");
		}else{
			if(null != cash.getSeNum() && !"".equals(cash.getSeNum())) {
				alertMsg.append("起止示数：").append(cash.getSeNum()).append("\n");
			}			
		}
		
		// 拼接低额提醒信息
		if (null != lowBalanceStr && !"".equals(lowBalanceStr)) {
			if(cash.getIsShowDetail() == false){
				alertMsg.append("--------------------------------\n");
			}
			alertMsg.append(lowBalanceStr).toString();
		}
		
		bm.setResponseMsg(alertMsg.toString());
		
		return alertMsg.toString();
	}
	//addEnd MDB 2012年1月12日 17:49:24
}
