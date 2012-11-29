package com.nantian.npbs.packet.business.FIXSTRING;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.ElectricityCashData;
import com.nantian.npbs.packet.internal.HDCashData;
import com.nantian.npbs.packet.internal.HuaElecCash;
import com.nantian.npbs.packet.internal.TVCashData;

/**
 * 缴费业务
 * @author jxw
 *
 */
@Component
public class FIXSTRINGPacketxxx002Helper implements IPacketFIXSTRING {

	private static final Logger logger = LoggerFactory
				.getLogger(FIXSTRINGPacketxxx002Helper.class);
	
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D_PREPAY_PWD", 
				"D_FEE_TYPE", 
				"D_PAY_TYPE", 
				"D_CURRENCY_CODE" ,
				"D_AMOUNT", 
				"D_USER_CODE", 
				"D_USER_NAME"
				};
		return fields;
	}
	
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//备付金密码
		String prePayPwd = (String) fieldValues.get("D_PREPAY_PWD");
		if (prePayPwd == null) throw new PacketOperationException();
		bm.setShopPINData((ConvertUtils.str2Bcd(prePayPwd)));
		
		//欠款，预存款标识
		String feeType = (String) fieldValues.get("D_FEE_TYPE");
		if (feeType == null) throw new PacketOperationException();
		bm.setFeeType(feeType);
		
		//资金归集方式
		String payType = (String) fieldValues.get("D_PAY_TYPE");
		if (payType == null) throw new PacketOperationException();
		bm.setPayType(payType);
		
		//货币代码
		String currencyCode = (String) fieldValues.get("D_CURRENCY_CODE");
		if (currencyCode == null) throw new PacketOperationException();
		bm.setCurrencyCode(currencyCode);
		
		//交易金额
		String amount = (String) fieldValues.get("D_AMOUNT");
		if (amount == null) throw new PacketOperationException();
		bm.setAmount(Integer.parseInt((String)amount) / 100.00);
		
		//用户号
		String userCode = (String) fieldValues.get("D_USER_CODE");
		if (userCode == null) throw new PacketOperationException();
		bm.setUserCode(userCode);
		
		//用户名称
		String userName = (String) fieldValues.get("D_USER_NAME");
		if (userName == null) throw new PacketOperationException();
		bm.setUserName(userName);
		
	}

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
		//资金归集方式
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_PAY_TYPE", bm.getPayType());
		
		//货币代码
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_CURRENCY_CODE", bm.getCurrencyCode());
		
		//交易金额	
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_AMOUNT", Double.toString(bm.getAmount()));
		
		//用户号
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_CODE", bm.getUserCode());
		
		//用户名称
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_USER_NAME", bm.getUserName());
		
		
		if( bm.getCustomData() != null ){
			
			//邯郸燃气特殊处理“本次余额”和“抄表期间”
			if("005002".equals(bm.getTranCode())){
				HDCashData hdCashData = (HDCashData) bm.getCustomData();
				//本次余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", hdCashData.getThisAmt());
				//抄表期间
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", hdCashData.getHd_month());
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", "");
				logger.info("本次余额:[{}],抄表期间:[{}]", 
						new Object[]{hdCashData.getThisAmt(),hdCashData.getHd_month()});
			}
			
			//河电电现金特殊处理“账户余额”和“起止示数”
			if("007002".equals(bm.getTranCode())){
				ElectricityCashData cash = (ElectricityCashData)bm.getCustomData();
				
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", cash.getThisBalance());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", cash.getSeNum());
				
				StringBuffer jtdjxx = new StringBuffer();
				
				//经与证通相关终端技术人员确认，此处给epos返回信息格式为："本年累计：*****度;一档用电：****度/****.**元;二档用电：****度/****.**元;三档用电：*****度/*****.**元;第N档剩余电量****;"
				//如有任何变动需要和epos厂家联系说明
				if(null == cash.getJtdjxx() || cash.getJtdjxx().length<1) {
					logger.info("阶梯电价信息有问题！");
				}
				
				if("1".equals(cash.getJtdjxx()[0])) {
					if( cash.getJtdjxx().length < 3) {
						jtdjxx.append("数据错误;");
					}
					jtdjxx.append(cash.getJtdjxx()[1]).append(";");  //本年累计信息
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
							jtdjxx.append("数据错误;");
						}else {
							if(sub1[0].contains("1档") || sub1[0].contains("一档")) {
								String[] sub2 = null;
								sub2 = sub1[1].split("/");
								if(sub2.length != 2) {
									jtdjxx.append("数据错误;");
								}else {
									jtdjxx.append("一档用电:").append(sub2[0]).append(";");
								}							
								
							}else if(sub1[0].contains("2档") || sub1[0].contains("二档")) {
								String[] sub2 = null;
								sub2 = sub1[1].split("/");
								if(sub2.length != 2) {
									jtdjxx.append("数据错误;");
								}else {
									jtdjxx.append("二档用电:").append(sub2[0]).append(";");
								}
							}else if(sub1[0].contains("3档") || sub1[0].contains("三档")) {
								String[] sub2 = null;
								sub2 = sub1[1].split("/");
								if(sub2.length != 2) {
									jtdjxx.append("数据错误;");
								}else {
									jtdjxx.append("三档用电:").append(sub2[0]).append(";");
								}
							}else {
								jtdjxx.append("数据错误;");
							}					
						}			
					}
					
					String[] sub3 = null;			
					if(cash.getJtdjxx()[cash.getJtdjxx().length-1].contains(":")) {
						sub3 = cash.getJtdjxx()[cash.getJtdjxx().length-1].split(":");
					}else if(cash.getJtdjxx()[cash.getJtdjxx().length-1].contains("：")){
						sub3 = cash.getJtdjxx()[cash.getJtdjxx().length-1].split("：");
					}else {
						sub3 = cash.getJtdjxx()[cash.getJtdjxx().length-1].split(":");
					}
					
					if(sub3.length != 2) {
						if(!cash.getJtdjxx()[cash.getJtdjxx().length-1].endsWith(";")) {
							cash.getJtdjxx()[cash.getJtdjxx().length-1] = cash.getJtdjxx()[cash.getJtdjxx().length-1] + ";";
						}
						jtdjxx.append(cash.getJtdjxx()[cash.getJtdjxx().length-1]);
					}else if(sub3[0].contains("1档") || sub3[0].contains("一档")) {
						jtdjxx.append("第一档剩余电量:").append(sub3[1]).append(";");						
					}else if(sub3[0].contains("2档")|| sub3[0].contains("二档")) {
						jtdjxx.append("第二档剩余电量:").append(sub3[1]).append(";");						
					}else if(sub3[0].contains("3档")|| sub3[0].contains("三档")) {
						jtdjxx.append("第三档剩余电量:").append(sub3[1]).append(";");						
					}else {
						jtdjxx.append("数据错误;");
					}			
				}		
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", jtdjxx.toString());
				
				logger.info("小票打印：本次余额[{}],起止示数[{}]", 
						new Object[]{cash.getThisBalance(), cash.getSeNum()});
			}
			
			//华电现金特殊处理“账户余额”和“起止示数”
			if("014002".equals(bm.getTranCode())){
				HuaElecCash cash = (HuaElecCash)bm.getCustomData();
				
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", cash.getThisBalance());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", cash.getSeNum());
				//阶梯信息
				StringBuffer jtdjxx = new StringBuffer();
				jtdjxx.append("年阶梯累计电量(度):").append(cash.getLevIncpq());
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", jtdjxx.toString());
				logger.info("小票打印：本次余额[{}],起止示数[{}]", 
						new Object[]{cash.getThisBalance(), cash.getSeNum()});
			}
			
			//有线电视特殊处理“账户余额”
			if("009002".equals(bm.getTranCode())) {
				if(bm.getCustomData() instanceof TVCashData) {
					
				}
				TVCashData cashData = (TVCashData)bm.getCustomData();
				double nowBalance = Double.valueOf(cashData.getCurAmt()) + Double.valueOf(cashData.getRecFee());
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_TV_CASH_ACCBAL", String.valueOf(nowBalance));
				
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", "");
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", "");
				logger.info("小票打印：账户余额[{}]",String.valueOf(nowBalance));
			}
			
			if("002002".equals(bm.getTranCode())) {
				String str1=new String ("                   ");
				String str2=new String ("                               ");
				StringBuffer str3=new StringBuffer();
				for(int i=0;i<684;i++){
					str3.append(" ");
				}
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());;
			 
			}
			if("001002".equals(bm.getTranCode())) {
				String str1=new String ("                   ");
				String str2=new String ("                               ");
				StringBuffer str3=new StringBuffer();
				for(int i=0;i<684;i++){
					str3.append(" ");
				}
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());;
			 
			}
			if("004002".equals(bm.getTranCode())) {
				String str1=new String ("                   ");
				String str2=new String ("                               ");
				StringBuffer str3=new StringBuffer();
				for(int i=0;i<684;i++){
					str3.append(" ");
				}
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());;
			 
			}
			
			if("015002".equals(bm.getTranCode())) {
				String str1=new String ("                   ");
				String str2=new String ("                               ");
				StringBuffer str3=new StringBuffer();
				for(int i=0;i<684;i++){
					str3.append(" ");
				}
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());;
			 
			}
			
			if("003002".equals(bm.getTranCode())) {
				String str1=new String ("                   ");
				String str2=new String ("                               ");
				StringBuffer str3=new StringBuffer();
				for(int i=0;i<684;i++){
					str3.append(" ");
				}
				//账户余额
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
				//起止示数
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
				
				//阶梯信息
				FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());;
			 
			}
			
		}else{
			String str1=new String ("                   ");
			String str2=new String ("                               ");
			StringBuffer str3=new StringBuffer();
			for(int i=0;i<684;i++){
				str3.append(" ");
			}
			//账户余额
			FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_ACCBAL", str1.toString());
			//起止示数
			FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_CASH_SENUM", str2.toString());
			
			//阶梯信息
			FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_ELEC_HEE_JTDJXX", str3.toString());
		}
		
		//附加响应数据
		StringBuffer msg = new StringBuffer("");
		if(null != bm.getLowTipType() && !"".equals(bm.getAdditionalTip())){
			if("0".equals(bm.getLowTipType())){
				msg.append("0|"+bm.getLowTipAmount());
			}
			if("1".equals(bm.getLowTipType())){
				msg.append("1|"+bm.getLowTipAmount());
			}
			if("2".equals(bm.getLowTipType())){
				msg.append("2|"+bm.getLowTipAmount());
			}
			logger.info("ePOS低额提醒类型及金额：" + msg.toString());
		}
		
		FIXSTRINGPacketUtils.addFieldValue(fieldValues, "D_LOW_TIP", msg.toString());
		
	}

}
