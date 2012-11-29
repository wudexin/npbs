package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

import weblogic.auddi.util.Logger;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;
import com.nantian.npbs.packet.PacketUtils;
import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * 备付金明细查询
 * 
 * @author hubo
 * 
 */
@Component
public class SPLITSTRINGPacket000804Helper implements IPacketSPLITSTRING {

	@Override
	public void pack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		ArrayList<Object[]> list = (ArrayList) bm.getJournalList();

		if (null == list) {
			bm.setResponseMsg("无数据！");
			return;
		}

		PacketUtils.addFieldValue(fieldValues, "packageNum", String.valueOf(bm
				.getPackageNum()));
		// 取实际返回笔数
		if (null == bm.getNumActual() && bm.getNumActual().equals("0")) {
			bm.setResponseMsg("无数据！");
			return;
		}

		int numActual = Integer.parseInt(bm.getNumActual());
		PacketUtils.addFieldValue(fieldValues, "numActual", String
				.valueOf(numActual));// 实际返回笔数
		for (int i = 0; i < numActual; i++) {
			PacketUtils.addFieldValue(fieldValues, "tranTime" + i, String
					.valueOf(list.get(i)[0]));// 交易时间
			
			//提取交易类型数据
			String tranTypeValue = getTranTypeValue(String.valueOf(list.get(i)[1]).trim()
					,String.valueOf(list.get(i)[5]).trim(),String.valueOf(list.get(i)[3]).trim());
			
			PacketUtils.addFieldValue(fieldValues, "tranType" + i, tranTypeValue);// 交易类型
			
			//提取交易金额数据
			String amountValue = getAmountValue(tranTypeValue.trim(),String.valueOf(list.get(i)[2]).trim());
			PacketUtils.addFieldValue(fieldValues, "amount" + i, amountValue);// 交易金额
			
			//提取业务类型---目的是区分备付金续费及撤销
			String busiCode = getBusiCode(String.valueOf(list.get(i)[3]).trim()
					,String.valueOf(list.get(i)[5]).trim());
			PacketUtils.addFieldValue(fieldValues, "busiCode" + i, busiCode);// 业务类型
			PacketUtils.addFieldValue(fieldValues, "customerNo" + i, String
					.valueOf(list.get(i)[4]));// 用户号码
			
			PacketUtils.addFieldValue(fieldValues, "tradeDate" + i, String
					.valueOf(list.get(i)[6]).substring(4));// 记账日期---月日
			/*PacketUtils.addFieldValue(fieldValues, "customerName" + i, String  2012年4月9日23:35:06删除
					.valueOf(list.get(i)[5]));// 用户名称
			PacketUtils.addFieldValue(fieldValues, "remark" + i, String
					.valueOf(list.get(i)[6]));// 备注
			*/
		}
		PacketUtils.addFieldValue(fieldValues, "packageFlag", String.valueOf(bm
				.getPackageFlag()));// 总批数

	}

	@Override
	public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
			BusinessMessage bm) throws PacketOperationException {

		// 查询类型
		String queryType = (String) fieldValues.get("queryType");
		if (queryType == null)
			throw new PacketOperationException();
		bm.setQueryType(queryType.trim());
		// 商户号
		String shopCode = (String) fieldValues.get("shopCode");
		if (shopCode == null)
			throw new PacketOperationException();
		bm.setShopCode(shopCode.trim());
		// 查询开始时间
		String queryStartDate = (String) fieldValues.get("queryStartDate");
		if (queryStartDate == null)
			throw new PacketOperationException();
		bm.setQueryStartDate(queryStartDate.trim());
		// 查询结束时间
		String queryEndDate = (String) fieldValues.get("queryEndDate");
		if (queryEndDate == null)
			throw new PacketOperationException();
		bm.setQueryEndDate(queryEndDate.trim());
		
		// 每页显示笔数
		/*String numPerPage = (String) fieldValues.get("numPerPage");
		if (numPerPage == null)
			throw new PacketOperationException();
		bm.setNumPerPage(numPerPage);*/
		
		// 第几批
		String packageNum = (String) fieldValues.get("packageNum");
		if (packageNum == null)
			throw new PacketOperationException();
		bm.setPackageNum(packageNum.trim());

	}

	@Override
	public String[] hasFields() {
		String[] fields = { "queryType", "shopCode", "queryStartDate",
				"queryEndDate", "packageNum" };
		return fields;
	}

	@Override
	public String[] addFields(String[] fields, int count,
			SPLITSTRINGFieldsConfig fieldsConfig) {
		String[] addFields = new String[count * 7];
		String fieldName = null;
		Field f = null;
		int j = 0;
		for (int i = 0; i < count; i++) {
			fieldName = "tranTime" + i;
			addFields[j] = "tranTime" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					14, "tranTime");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "tranType" + i;
			addFields[j] = "tranType" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					2, "tranType");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "amount" + i;
			addFields[j] = "amount" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					16, "amount");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "busiCode" + i;
			addFields[j] = "busiCode" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					3, "busiCode");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "customerNo" + i;
			addFields[j] = "customerNo" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					40, "customerNo");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;
			
			fieldName = "tradeDate" + i;
			addFields[j] = "tradeDate" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					4, "tradeDate");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			/*fieldName = "customerName" + i;  2012年4月09日注释
			addFields[j] = "customerName" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					40, "customerName");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;

			fieldName = "remark" + i;
			addFields[j] = "remark" + i;
			f = new Field(fieldName, VariableType.ASCII, LengthType.VARIABLE,
					200, "remark");
			fieldsConfig.addFieldConfig(fieldName, f);
			j++;*/
		}
		// String[] fieldsHelp = new String[fields.length + addFields.length];
		// System.arraycopy(fields, 0, fieldsHelp, 0, fields.length);
		// System.arraycopy(addFields, 0, fieldsHelp, fields.length,
		// addFields.length);
		return null;
	}
	
	/**
	 * 根据存取标志、交易状态、业务码区分返回交易类型
	 * @param flag
	 * @param status
	 * @param busi_code
	 * @return
	 */
	private String getTranTypeValue(String flag, String status, String busi_code) {
		if(null== flag || "".equals(flag)
				|| null == status ||"".equals(status)
				|| null == busi_code || "".equals(busi_code)) {
			Logger.info("流水数据有误！");
			return "";
		}
		
		if("000".equals(busi_code)) {			
			if(GlobalConst.TRADE_STATUS_SUCCESS.equals(status)) {
				return "10";                             //账户续费
			}else if(GlobalConst.TRADE_STATUS_FAILURE.equals(status)) {
				return "11";                             //续费撤销
			}else {
				Logger.info("流水数据有误！");
				return "";
			}
		}else {			
			if(GlobalConst.TRADE_STATUS_SUCCESS.equals(status)) {
				return "01";                          //支付扣减
			}else if(GlobalConst.TRADE_STATUS_FAILURE.equals(status)) {
				return "02";                          //缴费撤销
			}else if(GlobalConst.TRADE_STATUS_NOEXSIST.equals(status)) {
				if("1".equals(flag)) {
					return "12";                      //账户退费
				}else if("2".equals(flag)) {
					return "04";                      //隔日冲正
				}else {
					Logger.info("流水数据有误！");
					return "";
				}				
			}else{
				Logger.info("流水数据有误！");
				return "";
			}
		}
	}
	
	/**
	 * 返回带正负号金额数据供电商显示使用
	 * @param tranType 交易类型： 01-支付扣减、02-缴费撤销、04-隔日冲正
	 	 					     10-账户续费、11-撤销续费、12-账户退费

	 * @param amount    交易金额，数据库中记录统一为负数
	 * @return         账户续费、缴费撤销和调账返款为正数，支付扣减、撤销续费、调账追扣和账户退费为负数
	 */
	private String getAmountValue(String tranType,String amount) {
		
		if("02".equals(tranType)||"10".equals(tranType) || "12".equals(tranType) ) {   //缴费撤销、账户续费、账户退费
			return amount;			
		}else if("01".equals(tranType) || "11".equals(tranType) || "04".equals(tranType)) { //支付扣减、续费撤销、隔日冲正
			return "-"+amount;			
		}else{
			Logger.info("流水数据有误！");
			return "";
		}
		
	}
	
	/**
	 *返回需要打包给电商的业务代码，区分000为备付金续费和撤销
	 * @param busi_code
	 * @return   
	 */
	private String getBusiCode(String busi_code, String status) {
		
		if(null == busi_code || "".equals(busi_code) || null == busi_code || "".equals(status)) {
			Logger.info("流水数据有误！");
			return "";			
		}
		
		if("000".equals(busi_code)) {   //交易码为000时区分备付金续费和撤销
			if(GlobalConst.TRADE_STATUS_SUCCESS.equals(status)) {
				return "100";           //100---表示备付金续费
			}else if(GlobalConst.TRADE_STATUS_FAILURE.equals(status)){
				return "101";          //101----表示备付金撤销续费
			}else {
				Logger.info("流水数据有误！");
				return "";
			}
		}else {  //其他业务照样返回
			return busi_code;
		}
		
		
	}

}