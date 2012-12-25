package com.nantian.npbs.business.service.request;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 佣金计算测试
 * RequestBusiness002Service中的calculateRemuneration方法
 * @author 7tianle
 * @since 2011-08-26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RemunerationCalculateTest {

	/** 日志输出 */
	final Logger logger = LoggerFactory.getLogger(RemunerationCalculateTest.class);
	
	@Resource
	private RequestBusiness002Service requestBusiness002Service;	
	
	@Test  
    public void test() {
		
		ControlMessage cm = new ControlMessage();
		BusinessMessage bm = new BusinessMessage();
		bm.setShopCode("05001022"); // 商户
		
		BigDecimal formulaId = new BigDecimal("1077");     //
		BigDecimal packageId = new BigDecimal("767");

		bm.setAmount(200);    		// 金额
		
        boolean b = requestBusiness002Service.calculateRemuneration(cm,bm,formulaId,packageId);

		assertEquals(new Double(1.96), new Double(bm.getSalary()));
		assertEquals(new Double(0.04), new Double(bm.getTax()));
		assertEquals(new Double(0.00), new Double(bm.getOther()));
		assertEquals(new Double(0.00), new Double(bm.getDepreciation()));
        
        Assert.assertTrue(b);
    } 
	
	/**
	 * 
	 * 
	 * 1、根据商户表TB_BI_COMPANY找到 PACKAGE_ID
	 * 2、根据 PACKAGE_ID 和业务类型（001） 在 表TB_BI_SALLARY_PACKAGE_FORMULA 表找到 FORMULA_ID
	 * 3、根据 PACKAGE_ID 在表TB_BI_SALLARY_PACKAGE 符合条件的记录
	 * 4、根据 FORMULA_ID和金额在表TB_BI_FORMULA_DETAIL 查找符合条件记录
	 * 

dTAX = tbBiSallaryPackage.getTax();
dDEPRECIATION = tbBiSallaryPackage.getDepreciation();
dOTHER = tbBiSallaryPackage.getOther();

tbBiFormulaDetail.getCalculateType() //计算类型

//1-按笔数

	dSumMoney = tbBiFormulaDetail.getCalculateNum();
			
//2-按金额
	dSumMoney = bm.getAmount() * tbBiFormulaDetail.getCalculateNum() / 100.000;

dTAX = dSumMoney * dTAX / 100.000;
dSumMoney = dSumMoney - dTAX  - dOTHER; 
 
        
例子：
        
sql = select * from TB_BI_SALLARY_PACKAGE  sp where sp.package_id = '767'

dTAX = 2
dDEPRECIATION = 0
dOTHER = 0   
       
select * from TB_BI_FORMULA_DETAIL fd where fd.formula_id = '1077';
 
dSumMoney = 200 * 1 / 100.000 = 2;


dTAX = 2 * 2 / 100.000 = 0.04;

dSumMoney = 2 - 0.04 = 1.96   
        
	 * 
	 * 
	 * 
	 * 
	 */
	
}
