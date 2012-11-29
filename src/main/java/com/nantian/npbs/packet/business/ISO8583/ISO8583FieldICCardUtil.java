package com.nantian.npbs.packet.business.ISO8583;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.internal.ElectricityICCardData;
import com.nantian.npbs.packet.internal.HeGBElecICCard;
import com.nantian.npbs.packet.internal.HeNDElecICCard;
import com.nantian.npbs.packet.internal.XAICCardData;

/**
 * IC卡缴费--解包读卡信息解包
 * @author qxl
 *
 */
public class ISO8583FieldICCardUtil {
 
	private static Logger logger = LoggerFactory.getLogger(ISO8583FieldICCardUtil.class);
	
	/**
	 * 河电省标卡IC卡缴费--解包读卡信息解包
	 * @param field
	 * @param icData
	 */
	public static void unpackHBSBCardFieldICCard(Object field, ElectricityICCardData icData) {

		logger.info("河电省标卡IC卡--解包读卡信息解包--开始");
		// 组织需要解包的字段，序号参见ElectricFieldReadCardInfoConfig
		int hasFields[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };

		String buffer = (String) field;
		try {
			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, buffer);
			icData.setCardNo(values[1].toString());                 //卡号
			icData.setElectricId(values[1].toString());           	//卡号-电表识别号
			icData.setRandomNum(values[2].toString());   	 		//随机数
			icData.setCardSerno(values[3].toString());              //卡序列号
			icData.setReadWriteHandle(values[4].toString());        //读写器句柄
			icData.setCardStatus(values[5].toString());        		//电卡状态
			icData.setBuyElecNum(values[6].toString());    			//购电次数
			icData.setBuyElecTotal(values[7].toString());           //电卡总购电字
			icData.setRemainElec(values[8].toString());           	//剩余电字
			icData.setZeroElec(values[9].toString());           	//过零电字
			icData.setAllUseElec(values[10].toString());           	//总用电字
			icData.setElecType(values[11].toString());          	//电卡类型
			icData.setJianydzs(values[12].toString());           	//尖用电字数
			icData.setFengydzs(values[13].toString());           	//峰用电字数
			icData.setGuydzs(values[14].toString());           		//谷用电字数
			icData.setPingydzs(values[15].toString());           	//平用电字数
			icData.setWritebtime(values[16].toString());           	//回写时间
		} catch (Exception e) {
			logger.error("河电省标卡IC卡--解包读卡信息解包出错",e);
		}
		logger.info("河电省标卡IC卡--解包读卡信息解包--结束");
		
	}
	
	/**
	 * 河电国标卡IC卡缴费--解包读卡信息解包
	 * @param field
	 * @param icData
	 */
	public static void unpackHBGBCardFieldICCard(Object field,HeGBElecICCard icData){
		
		logger.info("河电国标卡IC卡--解包读卡信息解包--开始");
		// 组织需要解包的字段，序号参见ElectricFieldReadCardInfoConfig
		int hasFields[] = {40,41,42,43,44,45,46,47,48};

		String buffer = (String) field;
		try {
			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, buffer);
			
			icData.setCardSeqNo(values[40].toString());       //卡片序列号
			icData.setCardInfo(values[41].toString());        //卡片信息
			icData.setCardState(values[42].toString());       //卡片状态标志位
			icData.setRandomNum(values[43].toString());       //随机数
			icData.setCardCode(values[44].toString());        //电表识别号(电卡编号)
			icData.setElecFactoryCode(values[45].toString()); //电表出厂编号
			icData.setCardType(values[46].toString());        //电卡类型
			icData.setBuyElecTimes(values[47].toString());    //购电次数
			icData.setSyAmount(values[48].toString());        //剩余金额
			
			
		} catch (Exception e) {
			logger.error("河电国标卡IC卡--解包读卡信息解包出错",e);
		}
		logger.info("河电国标卡IC卡--解包读卡信息解包--结束");
		
	}
	
	/**
	 * 新奥燃气IC卡55域卡信息解包到卡信息实体
	 * @param field  ----55域pos上送信息
	 * @param icData ----新奥燃气IC卡实体
	 */
	
	public static void unpackXAICCardFieldICCard(Object field,XAICCardData icData){
		logger.info("新奥燃气IC卡----55域信息解包到卡信息实体----开始");
		//新奥燃气55域含有数据，内容参见ElectricFieldReadCardInfoConfig
		int  hasFields[] = {30,31,32,33,34};
		
		String buffer = (String)field;
		try {
			Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, buffer);
			icData.setXAIC_Id(values[30].toString());      //IC卡号
			icData.setXAIC_Bz(values[31].toString());      //备注
			icData.setXAIC_No(values[32].toString());      //发卡次数
			icData.setXAIC_Buy(values[33].toString());     //预购气量
			icData.setXAIC_Ifo(values[34].toString());     //加密串			
			
		} catch (Exception e) {
			logger.info("新奥燃气解包55域数据出现异常！");
			e.printStackTrace();					
		}
		
		
		logger.info("新奥燃气IC卡----55域信息解包到卡信息实体----结束");	
		
	}
	/**
	 * 河北农电IC卡55域卡信息解包到卡信息实体 int hasFields[] = {54,49,50,51,68};
	 * @param field 55域pos上送信息
	 * @param icData ----河北农电IC卡
	 */
	public static void unpackHeNDICCardFieldICCard(Object field,HeNDElecICCard icData){
		logger.info("河北农电IC卡----55域信息解包到卡信息实体----开始");
		//河北农电IC卡55域含有数据，内容参见ElectricFieldReadCardInfoConfig
		 int hasFields[] = {54,49,50,51,68};
		 String buffer = (String)field;
		 try {
			 logger.info(buffer.toString());
				Object[] values = ElectricFieldReadCardInfoUtils.unpackElectricField55(hasFields, buffer);
				     String sv =values[54].toString().replace('|', '^');
				//String sv="0008016985|01|LX_001|LK|1||272|1|1|1| |120713165838|0|0|0|0|0||0| | | | |0| | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |  | |";
				//String s =sv.replace('|', '^');
				// String sv="0008016985^3^LX_001^LK^2408^^8^12^^^^120917094609^1^409^0^0^409^240^0^^^^^0^^^^^^0^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^";
				icData.setREAD_INFO(sv);
				//icData.setREAD_INFO(s);//读卡字符串
				icData.setCONS_NO(values[49].toString());//客户编号
				icData.setCARD_NO(values[50].toString());//电卡编号
				icData.setPURP_FLAG(values[51].toString());//业务标识位
				icData.setCARD_INFO("");//卡内信息
				icData.setIDDATA("");//卡内信息
				icData.setBUSI_TYPE(values[68].toString()); 
				
			} catch (Exception e) {
				logger.info("河北农电IC卡解包55域数据出现异常！");
				e.printStackTrace();					
			}
			logger.info("河北农电IC卡----55域信息解包到卡信息实体----结束");
	}
	
	
	/**
	 * 河北农电IC卡55域卡信息解包到卡信息实体 int hasFields[] = {62,49,64,65,66,67};
	 * 
	 * @param field
	 *            55域pos上送信息
	 * @param icData
	 *            ----河北农电IC卡
	 */
	public static void unpackHeNDICCardFieldICCardQX(Object field,
			HeNDElecICCard icData) {
		logger.info("河北农电IC卡----55域信息解包到卡信息实体----开始");
		// 河北农电IC卡55域含有数据，内容参见ElectricFieldReadCardInfoConfig
		int hasFields[] = {62,49,64,65};
		String buffer = (String) field;
		try {
			Object[] values = ElectricFieldReadCardInfoUtils
			.unpackElectricField55(hasFields, buffer);
			icData.setCHECK_ID(values[62].toString());//对账批次
			icData.setCHECK_ID(values[49].toString());//客户编号
			icData.setCHECK_ID(values[64].toString());//电能表编号                    
			icData.setCHECK_ID(values[65].toString());//电能表标识                    
			icData.setCHECK_ID("");//卡内信息                      
			icData.setCHECK_ID("");//卡片信息                      
		} catch (Exception e) {
			logger.info("河北农电IC卡解包55域数据出现异常！");
			e.printStackTrace();
		}
		logger.info("河北农电IC卡----55域信息解包到卡信息实体----结束");
	}
	
	 
}
