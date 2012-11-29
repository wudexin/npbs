/**
 * 
 */
package com.nantian.npbs.packet.business.TUXSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.ElectricityICCardData;

/**
 * 河电省标电卡缴费
 * @author qxl
 *
 */
@Component
public class TUXSTRINGPacket010002Helper extends TUXSTRINGPacketxxx002Helper {

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#pack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		//|1|10.0|00000023|00002230|00000097|00000000|00021757|01|00000000|00000000|00000000|00000000|000000|0.0|
		ElectricityICCardData cardData = (ElectricityICCardData)bm.getCustomData();

		// 地区码
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DQM", " ");   // 不上传
		
		//用户编号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHBH", cardData.getUserCode());
		
		//用户名称
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHMC", cardData.getUserName());
		
		//用电地址
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YDDZ", cardData.getAddress());
		
		//电价名称
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DJMC", cardData.getElecName());
		
		//电价
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DJ", cardData.getPrice());
		
		//上次余额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_SCYE",  cardData.getLastBalance());
		
		//低保户剩余金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DBHSYJE", cardData.getDibaofei());
		
		//电表识别号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DBSBH", cardData.getCardNo());
		
		//卡序列号
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_YHKFSYZ", cardData.getCardSerno());
		
		//随机数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_SJS", cardData.getRandomNum());
		
		//购电方式
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GDFS", "1"); // 默认是1  购电方式为1（金额购电），购电值单位为元
		
		//购电值
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GDZ", String.valueOf(bm.getAmount()));  //cardData.getCurElectric())
		
		//购电次数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GDCS", cardData.getBuyElecNum()); 
		
		//电卡总购电字
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DKZGDZ", cardData.getBuyElecTotal());
		
		//剩余电字
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_SYDZ", cardData.getRemainElec());
		
		//过零电字
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GLDZ", cardData.getZeroElec());
		
		//总用电字
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_ZYDZ", cardData.getAllUseElec());
		
		//电卡类型
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_DKLX", cardData.getElecType());
		
		//尖用电字数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_JYDZS", cardData.getJianydzs());
		
		//峰用电字数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_FYDZS", cardData.getFengydzs());
		
		//谷用电字数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_GYDZS", cardData.getGuydzs());
		
		//平用电字数
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_PYDZS", cardData.getPingydzs());
		
		//回写时间
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_HXSJ", cardData.getWritebtime());
		
		//扣减金额
		PacketUtils.addFieldValue(fieldValues, "D13_13_HESB_KJJE", String.valueOf(cardData.getBuckleAmt()));
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.packet.business.TUXSTRING.IPacketTUXSTRING#unpack(java.util.Map, com.nantian.npbs.packet.ControlMessage, com.nantian.npbs.packet.BusinessMessage)
	 */
	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {
		
	//000000||20120720|130236270|00001|648930FF74D61DFD|   |19   |0.16|YZD10220120720130236270|0.00|1|48  |0   |0           |一档剩余电量:1032| 	
	//000000||20120719|130021988|00001|D6EF93A1A72FBD3B|300|3    |0.75|YZD10220120719130021988|0.00|1|1360|2000|799999999992|二档剩余电量:100| 
		ElectricityICCardData customData = new ElectricityICCardData();
		
		//外部认证数据
		String outAuthData = (String) fieldValues.get("D13_13_HESB_WBRZSJ");
		if (outAuthData == null) throw new PacketOperationException();
		customData.setOutAuthData(outAuthData);
		
		//add by fengyafang 20120718
		String  buyElecValue=(String)fieldValues.get("D13_13_HESB_GDZ");
		if (buyElecValue == null) throw new PacketOperationException();
		customData.setBuyElecValue(buyElecValue);
		
		//本次购电量
		String buyElectric = (String) fieldValues.get("D13_13_HESB_BCGDL");
		if (buyElectric == null) throw new PacketOperationException();
		customData.setBuyElectric(buyElectric);
		
		//本次余额
		String curBalance = (String) fieldValues.get("D13_13_HESB_BCYE");
		if (curBalance == null) throw new PacketOperationException();
		customData.setCurBalance(curBalance);
		
		//原电力交易流水号
		String seqNo = (String) fieldValues.get("D13_13_HESB_YJYLSH");
		if (seqNo == null) throw new PacketOperationException();
		bm.setOldElecSeqNo(seqNo);
		
		//扣减金额
		String buckleAmt = (String) fieldValues.get("D13_13_HESB_KJJE");
		if (buckleAmt == null) throw new PacketOperationException();
		customData.setBuckleAmt(Double.valueOf(buckleAmt));
		
		//阶梯标志
		String levFlag = (String) fieldValues.get("D13_13_HESB_JTBZ");
		if (levFlag == null) throw new PacketOperationException();
		customData.setLevFlag(levFlag);
		
		//本年一档用电
		String lev1Electric = (String) fieldValues.get("D13_13_HESB_BNYDYDL");
		if (lev1Electric == null) throw new PacketOperationException();
		if("".equals(lev1Electric.trim())) {
			lev1Electric = "0";
		}
		customData.setLev1Electric(lev1Electric);
		
		//本年二档用电
		String lev2Electric = (String) fieldValues.get("D13_13_HESB_BNEDYDL");
		if (lev2Electric == null) throw new PacketOperationException();
		if("".equals(lev2Electric.trim())) {
			lev2Electric = "0";
		}
		customData.setLev2Electric(lev2Electric);
		
		//本年三档用电
		String lev3Electric = (String) fieldValues.get("D13_13_HESB_BNSDYDL");
		if (lev3Electric == null) throw new PacketOperationException();
		if("".equals(lev3Electric.trim())) {
			lev3Electric = "0";
		}
		customData.setLev3Electric(lev3Electric);
		
		//第N档剩余电量
		String levnElectric = (String) fieldValues.get("D13_13_HESB_DNDSYDL");
		if (levnElectric == null) throw new PacketOperationException();
		customData.setLevnElectric(levnElectric);		
		
		
		bm.setCustomData(customData);
	}


	/**
	 * 返回报文体
	 */
	@Override
	public String[] hasFields() {
		String[] fields = {
				"D13_13_HESB_WBRZSJ",    // 外部认证数据
				//add by fengyafang 20120718
				"D13_13_HESB_GDZ",        //购电值
				"D13_13_HESB_BCGDL",           // 本次购电量
				"D13_13_HESB_BCYE",            //本次余额
				"D13_13_HESB_YJYLSH",      // 原电力交易流水号
				"D13_13_HESB_KJJE",        //扣减金额
				"D13_13_HESB_JTBZ",        //阶梯标志
				"D13_13_HESB_BNYDYDL",     //本年一档用电
				"D13_13_HESB_BNEDYDL",     //本年二档用电
				"D13_13_HESB_BNSDYDL",     //本年三档用电
				"D13_13_HESB_DNDSYDL"      //第N档剩余电量
				
				};
		return fields;
	}
	
}
