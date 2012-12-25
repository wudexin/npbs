package com.nantian.npbs.packet.business.ISO8583;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.HuaElecCash;

/**
 * 农电查询
 * 
 * @author fyf
 * 
 */
@Component
public class ISO8583Packet018001Helper extends ISO8583Packetxxx001Helper {
	private static Logger logger = LoggerFactory
			.getLogger(ISO8583Packet018001Helper.class);

	/**
	 * 解包55位元读卡信息解包
	 */
	@Override
	protected void unpackField55(Object field, BusinessMessage bm) {

		logger.info("河北农电IC卡查询--解包55位元读卡信息解包--开始");

		// 使用HeGBElecICCard存储解包后的内容，然后将对象放入bm的customData
		HeNDElecICCard icData = new HeNDElecICCard();
		ISO8583FieldICCardUtil.unpackHeNDICCardFieldICCard(field, icData);
		bm.setCustomData(icData);
		logger.info("河北农电IC卡查询--解包55位元读卡信息解包--结束");
	}

	/**
	 * 打包55位元
	 */
	@Override
	protected String packField55(BusinessMessage bm) {

		logger.info("河北农电IC卡查询--打包55位元--开始");
		HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}
		StringBuffer sb=new StringBuffer();
		sb.append(customData.getIF_PURP() == null?"":customData.getIF_PURP());//是否允许购电 1为允许
		sb.append(customData.getNOALLOW_MSG() == null?"                                        ":customData.getNOALLOW_MSG()) ;//不允许购电原因
		sb.append(customData.getCONS_ADDR());
		return sb.toString();
	}

	/**
	 * 河北农电打包44域
	 */
	@Override
	protected String packField44(BusinessMessage bm)
			throws PacketOperationException {

		logger.info("河北农电IC卡查询--打包44域位元--开始");
		if (GlobalConst.RESPONSECODE_FAILURE.equals(bm.getResponseCode())) {
			return "";
		}

		StringBuffer str = new StringBuffer();

		if (null != bm.getCustomData()
				&& bm.getCustomData() instanceof HeNDElecICCard) {
			HeNDElecICCard customData = (HeNDElecICCard) bm.getCustomData();

			if (null != customData.getCARD_NO()) {
				str.append("用户编号:").append(customData.getCONS_NO())
						.append("\n");
			}
			if (null != customData.getCONS_NAME()) {
				str.append("用户名称:").append(customData.getCONS_NAME()).append(
						"\n");
			}
			 
			if (null != customData.getOWN_AMT()) {
				str.append("欠费金额:").append(customData.getOWN_AMT())
						.append("\n");
			}

		} else if (null != bm.getAdditionalTip()
				&& !"".equals(bm.getAdditionalTip())) {
			str.append(bm.getAdditionalTip()).append("\n");
		} else if (null != bm.getResponseMsg()
				&& !"".equals(bm.getResponseMsg())) {
			str.append(bm.getResponseMsg()).append("\n");
		}
		bm.setResponseMsg(str.toString());
		logger.info("打包44域完成{}", str.toString());
		return str.toString();
	}
}
