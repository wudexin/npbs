/**
 * 
 */
package com.nantian.npbs.test.packet.internal;

import java.sql.Time;
import java.util.BitSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.common.utils.ConvertUtils;

/**
 * @author TsaiYee
 * 
 */
public class ConvertUtilsTest extends Assert {

	private static final Logger logger = LoggerFactory
			.getLogger(ConvertUtilsTest.class);

	@Test
	public void byte2BitSetTest() {
		String hexString = "5020008114c09211";
		System.out.println("hexString:" + hexString);
		byte[] bytes = ConvertUtils.hexStr2Bytes(hexString);
		System.out.println("length of byte:" + bytes.length);
		System.out.println("byte:" + new String(bytes));
		BitSet bits = ConvertUtils.byteArray2BitSet(bytes);
		System.out.println(bits);
	}

	@Test
	public void speedTest() {
		String hexString = "5020008114c09211";
		logger.info("begin");
		for (int i = 0; i < 10000000; i++) {
			ConvertUtils.hexStr2Bytes(hexString);
		}
		logger.info("end");

		logger.info("begin");
		for (int i = 0; i < 10000000; i++) {
			ConvertUtils.hexStr2BytesOld(hexString);
		}
		logger.info("end");
		
	}
}
