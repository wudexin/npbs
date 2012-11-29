package com.nantian.npbs.test.business.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.business.dao.BusinessUnitDao;

/**
 * 业务对应机构
 * @author 7tianle
 *
 */
@Repository(value="businessUnitDao")
public class BusinessUnitDaoImpl  implements BusinessUnitDao {

	final Logger logger = LoggerFactory.getLogger(BusinessUnitDaoImpl.class);
	
	@Override
	public boolean addProcess(final String busiCode,final String unitcode) throws Exception{

		return true;
	}
	
	@Override
	public boolean releaseProcess(final String busiCode,final String unitcode)throws Exception{
			return true;
	}

}
