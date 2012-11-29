package com.nantian.npbs.common.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class ConvertFromGBKToUTF8Test {

	@Test
	public void testConvert() throws IOException {
		// assertEquals(new
		// ConverFromGBKToUTF8().gbk2utf8("Hello,你好"),("Hello,你好").getBytes("UTF-8"));

		FileInputStream fi = new FileInputStream("D:/GBK.txt ");
		BufferedReader br = new BufferedReader(new InputStreamReader(fi, "GBK"));
		String GBKString = br.readLine();
		System.out.println(GBKString);
		br.close();
		assertEquals(
				"INFO 2011-08-09 11:20:55 com.nantian.bpcm.atom.controller.AtomlibInfoController-用户[admin|管理员|127.0.0.1]在[公共原子交易库登记]中查询数据",
				GBKString);
	}

}
