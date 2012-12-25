package com.nantian.npbs.test.packet.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 
 * @author 王玮
 * @version 创建时间：2011-11-11 下午4:36:54
 * 
 */

public class FieldUtilsTest {

	private static final Logger logger = LoggerFactory
			.getLogger(FieldUtilsTest.class);

	@Test
	public void testGetFieldByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBcdField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAsciiFieldByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAsciiFieldStringIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFieldByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetBcdField() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAsciiFieldByteArrayIntStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAsciiFieldStringBufferStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetBinaryField() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeftAddZero4FixedLengthString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeftAddSpace4FixedLengthString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeftAddChar4FixedLengthStringByByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testRightAddSpace4FixedLengthString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRightAddZero4FixedLengthString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRightAddChar4FixedLengthString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAssertFieldNull() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFixedLengthBcdField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFixedLengthAsciiFieldIntByteArrayIntIntFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFixedLengthAsciiFieldStringStringIntIntFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFixedLengthBinaryField() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthBcdField() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthAsciiFieldIntByteArrayIntIntStringFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthAsciiFieldIntStringBufferIntStringFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthAsciiFieldStringStringBufferIntStringFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthAsciiFieldStringBufferIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthAsciiFieldSpliter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthBinaryFieldIntByteArrayIntIntByteArrayFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFixedLengthBinaryFieldByteArrayIntIntByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFixedLengthAsciiStringIntStringIntFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFixedLengthAsciiStringStringStringIntFieldsConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFixedLengthAsciiStringAlignTypeStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefactorOfleftAddChar4FixedLengthString() throws Exception {
		assertEquals(FieldUtils.leftAddChar4FixedLengthString("Hello", '0', 8),
				FieldUtils
						.leftAddChar4FixedLengthStringUseByte("Hello", '0', 8));
		assertEquals(FieldUtils.leftAddChar4FixedLengthString("中a文1文文020", '0', 20),
				FieldUtils.leftAddChar4FixedLengthStringUseByte("中a文1文文020", '0', 20));
	}

	@Test
	public void testSpeedOfleftAddChar4FixedLengthString() throws Exception {

		logger.info("begin");
		for (int i = 0; i < 10000000; i++) {
			FieldUtils.leftAddChar4FixedLengthString("中a文1文文020嘈嘈嘈嘈嘈嘈嘈嘈嘈", '0', 51);
		}
		logger.info("end");

		logger.info("begin");
		for (int i = 0; i < 10000000; i++) {
			FieldUtils.leftAddChar4FixedLengthStringUseByte("中a文1文文020嘈嘈嘈嘈嘈嘈嘈嘈嘈", '0', 51);
		}
		logger.info("end");
	}
}
