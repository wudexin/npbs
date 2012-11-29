package com.nantian.npbs.test.packet.TUXSTRING;

import com.nantian.npbs.packet.ControlMessage;

public class TuxStringTest {

	public static String backPacket(ControlMessage cm){
		
		String responseDataString = "";
		
		if(cm.getTranCode().equals("003001")){  // IC卡
			responseDataString = "000000||20111031|368|00001|0340123474|孙永强|河北省石家庄市开发区珠峰大街180号22#楼1单元901|13401|02|0|0|0|0.00|0.39|0|01|0|0|0|0|0001非普不满1KV|80.44|";
		}else if(cm.getTranCode().equals("003001")){ // 电信查询
			responseDataString = "000000||20111031|368|00001|孙永强|100|80.44|";
		}else if(cm.getTranCode().equals("003002")){ // 电信缴费
			responseDataString = "000000||20111031|368|00001|13483571178|孙永强|1234567890|01-12|100|80.44|12|13|44|22|58|1|2|3|4|5|6|7|8|9|10|11|";
		}else if(cm.getTranCode().equals("001001")){ // 移动查询
			responseDataString = "000000||20111031|368|00001|孙永强|100|80.44|";
		}else if(cm.getTranCode().equals("001002")){ // 移动缴费
			responseDataString = "000000||20111031|368|00001|11|201111|动感地带|乔晓磊|15811440078|1234567890|12222222222222|300|20111117|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|20110809-20110909|12|12|12|22222|youzheng|";
		}
		
		return responseDataString;
	}
}
