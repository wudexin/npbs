package com.nantian.npbs.business.service.answer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.PrepayDao;
import com.nantian.npbs.business.dao.TradeDao;
import com.nantian.npbs.business.model.TbBiPrepay;
import com.nantian.npbs.business.model.TbBiPrepayInfo;
import com.nantian.npbs.business.model.TbBiPrepayInfoId;
import com.nantian.npbs.business.model.TbBiPrepayLowamount;
import com.nantian.npbs.business.service.internal.CommonPrepay;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.DoubleUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.services.webservices.models.ModelSvcAns;
import com.nantian.npbs.services.webservices.models.ModelSvcReq;

@Scope("prototype")
@Component(value="WebAnswerBusiness025001Service")
public class WebAnswerBusiness025001Service extends WebAnswerBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(WebAnswerBusiness025001Service.class);

	@Resource
	private CommonPrepay commonPrepay;

	@Resource
	public PrepayDao prepayDao;	
 

	boolean flag = false;

	/**
	 * 业务处理
	 */
	@Override
	public void dealBusiness(ModelSvcReq modelSvcReq, ModelSvcAns modelSvcAns) {
		 		
		 	 
	}
}
