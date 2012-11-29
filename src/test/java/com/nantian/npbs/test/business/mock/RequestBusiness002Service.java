package com.nantian.npbs.test.business.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * 业务缴费
 * @author qiaoxiaolei
 * @since 2011-08-24
 */
@Scope("prototype")
@Component
public class RequestBusiness002Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness002Service.class);

	/**
	 * 检查
	 */
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		
		// 检查商户酬金套餐中是否包含此业务,并计算酬金
		if(!checkBusinessRemuneration(cm,bm)){
			return false;
		}
		logger.info("缴费校验,佣金计算成功!");

		logger.info("公共校验成功!");
		return true;
	}

	/**
	 * 业务处理
	 */
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		
		//当交易码为失败时,退出
		if(cm.getResultCode().equals(GlobalConst.RESULTCODE_FAILURE)){
			return;
		}
		
	}
	
	/**
	 * 检查备付金
	 * 设置bm 备付金账户、备付金余额、剩余信用额度
	 * @param tbBiRescash 备付金账户
	 * @param amount 交易金额
	 */
	protected boolean checkReserve(ControlMessage cm,BusinessMessage bm){

		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		return true;
		
	}
	
	/**
	 * 按照C写的
	 * 1、检查商户酬金套餐中是否包含此业务
	 * 2、缴费金额校验
	 * 3、计算酬金
	 * 
	 * @param cm
	 * @param bm
	 */
	protected boolean checkBusinessRemuneration (ControlMessage cm,BusinessMessage bm){

//		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
//		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);
		return true;
	}
	
	
	/**
	 * 缴费金额校验
	 * 
	 * 业务：当支付类型是备付金，并且缴费方式是预存款时，如果缴费金额小于该商户所在机构的该交易最小金额时则退出缴费交易，
	 * 如果缴费金额大于最大金额时，检查商户是否有授权额度信息，并且该授权额度和缴费额度相等。
	 * 缴费成功后则删除此授权信息，日终也删除此授权信息。
	 * @param bm
	 * @param amount 交易金额
	 */
	protected boolean checkPaymentBalance(ControlMessage cm,BusinessMessage bm,TbBiCompany shop){
		
		bm.setResponseCode(GlobalConst.RESPONSECODE_SUCCESS);
		cm.setResultCode(GlobalConst.RESULTCODE_SUCCESS);

		return true;
	}
	
	/**
	 * 返回交易类型
	 * 01-缴费交易；02-取消交易；04-冲正交易
	 * @return
	 */
	protected String tradeType(){
		return "01";
	}

	/* (non-Javadoc)
	 * @see com.nantian.npbs.business.service.request.RequestBusinessService#needLockProcess()
	 */
	@Override
	public boolean needLockProcess() {
		return true;
	}

	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("1");
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		cm.setServiceCallFlag("1");
		
	}
	
	/**
	 * 获取现金缴费临时表数据
	 * @param cm
	 * @param bm
	 */
	protected boolean getTempValue(ControlMessage cm,BusinessMessage bm) {
		return true;
	}
}
