/**
 * 
 */
package com.nantian.npbs.common;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nantian.npbs.business.dao.BusinessUnitDao;
import com.nantian.npbs.business.dao.CompanyDao;
import com.nantian.npbs.business.model.TbBiBusinessUnit;
import com.nantian.npbs.business.model.TbBiBusinessUnitId;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiProcMem;
import com.nantian.npbs.common.GlobalConst.CHANEL_TYPE;
import com.nantian.npbs.core.modules.utils.SpringContextHolder;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;

/**
 * @author TsaiYee
 * 
 */
@Scope("prototype")
@Component
public class ProcessManager {

	private static Logger logger = LoggerFactory
			.getLogger(ProcessManager.class);

	@Resource
	public BusinessUnitDao businessUnitDao;

	// Company Dao
	@Resource
	protected CompanyDao companyDao;

	// 基Dao
	@Resource
	protected BaseHibernateDao baseHibernateDao;

	public static ProcessManager getProcessManager() {
		return SpringContextHolder.getBean("processManager");
	}

	public void unlockProcess(ControlMessage cm, BusinessMessage bm) {
		if (!cm.isLockProcess())
			return;

		logger.info("开始释放进程 ");
		baseHibernateDao.delete(bm.getTpm());
		logger.info("释放完成 ");
	}

	public boolean lockProcess(ControlMessage cm, BusinessMessage bm) {
		// TODO: 检查进程数是否已经分配完成

		// 判断是否为000开头的交易，如果为000开头的，则另存到一张表里，为了方便便民后台监控情况;
		String substring = bm.getTranCode().substring(0, 3);
		TbBiBusinessUnitId tu = new TbBiBusinessUnitId();
		tu.setBusiCode(substring);
			TbBiCompany tbBiCompany = companyDao.get(bm.getShopCode());
			if (tbBiCompany.equals(null)) {
				bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
				bm.setResponseMsg("商户号不存在！");
				cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
				cm.setResultMsg("商户号不存在！");
				logger.error("商户号不存在！");
				return false;
			}
		tu.setUnitcode(tbBiCompany.getUnitcode());
		String unitcode = tu.getUnitcode();// 所在机构县
		String shi_unit_code = unitcode.substring(0, 4) + "00000";// 市级机构
		String shen_unit_code = unitcode.substring(0, 2) + "0000000";// 省级
		// 县级机构bean
		TbBiBusinessUnit tui = (TbBiBusinessUnit) baseHibernateDao.get(
				TbBiBusinessUnit.class, tu);
		
		if(tui==null){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("县级业务设置不正确，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("县级业务设置不正确，请联系管理员！");
			logger.info("县级业务设置不正确，请联系管理员！");
			return false;	
		}

		TbBiBusinessUnitId shitu = new TbBiBusinessUnitId();
		shitu.setBusiCode(substring);
		shitu.setUnitcode(shi_unit_code);
		// 市级机构bean
		TbBiBusinessUnit tushi = (TbBiBusinessUnit) baseHibernateDao.get(
				TbBiBusinessUnit.class, shitu);
		
		if(tushi==null){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("市级业务设置不正确，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("市级业务设置不正确，请联系管理员！");
			logger.info("市级业务设置不正确，请联系管理员！");
			return false;	
		}
		
		TbBiBusinessUnitId shentu = new TbBiBusinessUnitId();
		shentu.setBusiCode(substring);
		shentu.setUnitcode(shen_unit_code);
		// 省级机构bean
		TbBiBusinessUnit tushen = (TbBiBusinessUnit) baseHibernateDao.get(
				TbBiBusinessUnit.class, shentu);
		if(tushen==null){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("省级业务设置不正确，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("省级业务设置不正确，请联系管理员！");
			logger.info("省级业务设置不正确，请联系管理员！");
			return false;	
		}
		
		
		Integer processnow_xian = tui.getPorcessmax();// 当前商户所在机构的进程数
		Integer processnow_shi = tushi.getPorcessmax();
		Integer processnow_shen = tushen.getPorcessmax();
		TbBiProcMem tm = new TbBiProcMem();
		List find = baseHibernateDao
				.queryBySQL(" select  count(tm.busi_code) from TB_BI_PROC_MEM tm where tm.busi_code='"
						+ substring
						+ "' and tm.unitcode = '"
						+ unitcode
						+ "' union all"
						+ " select  count(tm.busi_code) from TB_BI_PROC_MEM tm where tm.busi_code='"
						+ substring
						+ "' and tm.unitcode like '"
						+ unitcode.substring(0, 4)
						+ "%' union all"
						+ " select  count(tm.busi_code) from TB_BI_PROC_MEM tm where tm.busi_code='"
						+ substring
						+ "' and tm.unitcode like '"
						+ unitcode.substring(0, 2) + "%'");

		// select * from TB_BI_PROC_MEM tm where tm.busi_code='"
		// + substring + "'  and tm.unitcode='" + unitcode + "'"
		TbBiProcMem entity = new TbBiProcMem();
		entity.setPid(bm.getExchangeId());
		entity.setBusi_code(substring);
		entity.setUnitcode(unitcode);
		entity.setC1( bm.getTranTime());
		entity.setC2(bm.getPbSeqno());

		// 检查 省市县参数是否设置合理省>=市>=县>=0

		System.out.println("=======================" + processnow_shen + "   "
				+ processnow_shi + "    " + processnow_xian);
		
		if(processnow_shen.intValue()==0){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("该交易暂时不允许做交易，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("该交易暂时不允许做交易，请联系管理员！");
			logger.info("该交易暂时不允许做交易，请联系管理员");
			return false;
		}
		
		if(processnow_shi.intValue()==0){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("该交易暂时不允许做交易，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("该交易暂时不允许做交易，请联系管理员！");
			logger.info("该交易暂时不允许做交易，请联系管理员");
			return false;
		}

		if(processnow_xian.intValue()==0){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("该交易暂时不允许做交易，请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("该交易暂时不允许做交易，请联系管理员！");
			logger.info("该交易暂时不允许做交易，请联系管理员");
			return false;
		}

		if (processnow_shen.intValue() >= processnow_shi.intValue()
				&& processnow_shi.intValue() >= processnow_xian.intValue()
				&& processnow_xian.intValue() >= 0) {
			  if (Integer.parseInt(find.get(2).toString()) < processnow_shen.intValue()//
					&&Integer.parseInt(find.get(1).toString()) < processnow_shi.intValue()
					&&Integer.parseInt(find.get(0).toString()) < processnow_xian.intValue()  ) {
						logger.info("进程控制数量小于县级数量，直接做交易");
						baseHibernateDao.save(entity);
						bm.setTpm(entity);
					}else{
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("当前业务繁忙，请稍候再试");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("当前业务繁忙，请稍候再试！");
						logger.info("进程控制数量大于 县级数量，直接返回失败。当前业务繁忙，请稍候再试");
						return false;	
					}
		} else {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("进程控制参数设置不合理,请联系管理员！");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("进程控制参数设置不合理,请联系管理员！");
			logger.info("进程控制参数设置不合理,请联系管理员");
			return false;
		}

		cm.setLockProcess(true);
		// TODO: 记录占用进程

		return true;
	}

}
