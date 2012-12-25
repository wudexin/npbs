package com.nantian.npbs.packet.business.SPLITSTRING;

import java.util.Map;

import org.springframework.stereotype.Component;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.PacketOperationException;

/**
 *  备付金对账
 *  <p>
 *  每日由便民服务站平台发起对账，电子商务平台收到对账交易后，
 *  将对账日成功缴费交易及成功备付金续费交易统一生成对账文件后FTP到便民服务站平台的指定目录下，
 *  文件名为：BMMatch_对账日期.txt，
 *  </p>
 * @author qiaoxl
 *
 */
@Component
public class SPLITSTRINGPacket000806Helper implements IPacketSPLITSTRING {

		@Override
		public void pack(Map<String, Object> fieldValues, ControlMessage cm,
				BusinessMessage bm) throws PacketOperationException {


		}

		@Override
		public void unpack(Map<String, Object> fieldValues, ControlMessage cm,
				BusinessMessage bm) throws PacketOperationException {
			
			
		}

		@Override
		public String[] hasFields() {
			
			return null;
		}

		@Override
		public String[] addFields(String[] fields, int count, SPLITSTRINGFieldsConfig fieldsConfig) {
			return null;
		}
}
