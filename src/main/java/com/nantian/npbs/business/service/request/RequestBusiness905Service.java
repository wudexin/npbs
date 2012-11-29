package com.nantian.npbs.business.service.request;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nantian.npbs.business.dao.MenuDao;
import com.nantian.npbs.common.DynamicConst;
import com.nantian.npbs.common.GlobalConst;
import com.nantian.npbs.common.utils.ConvertUtils;
import com.nantian.npbs.packet.BusinessMessage;
import com.nantian.npbs.packet.ControlMessage;
import com.nantian.npbs.packet.internal.FieldUtils;

/**
 * 缴费菜单更新
 * @author MDB
 */
@Scope("prototype")
@Component
public class RequestBusiness905Service extends RequestBusinessService {

	private static Logger logger = LoggerFactory.getLogger(RequestBusiness905Service.class);
	
	// Menu DAO
	@Resource
	private MenuDao menuDao;
	
	@Override
	protected boolean checkCommon(ControlMessage cm,BusinessMessage bm) {
		//检查强制下载标致
		checkForceDownLoadFlag(cm, bm);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void dealBusiness(ControlMessage cm,BusinessMessage bm) {
		logger.info("缴费菜单更新业务处理开始：");

		StringBuffer menuString = new StringBuffer();

		List list = menuDao.getMenus(getSQL(bm.getShop().getCompanyCode()));
		
		if(list == null || list.size() < 1){
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("商户尚未开通任何缴费业务!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("商户[{}]查询缴费业务为空!"+bm.getShop().getCompanyCode());
			logger.info("商户[{}]查询缴费业务为空!", bm.getShop().getCompanyCode());
			return ;
		}
		Iterator it = list.iterator();
		
		String rowTemp = null;
		String[] fieldTemp = null;
		while(it.hasNext()){
			rowTemp = (String) it.next();
			
			if(rowTemp != null){
				
				if(rowTemp.split(",").length == 6 ){
					fieldTemp = rowTemp.split(",");
						//目录项
						menuString.append("00");
						//目录ID
						menuString.append(fieldTemp[1]);
						//父目录ID
						menuString.append(fieldTemp[2]);
					try {
						//目录级别
						menuString.append(FieldUtils.leftAddZero4FixedLengthString(fieldTemp[3],2));
						//目录长度
						menuString.append(ConvertUtils.byteToString(Byte.valueOf(fieldTemp[4])));
						//目录内容
						menuString.append(ConvertUtils.bytes2HexStr(fieldTemp[5].getBytes(DynamicConst.PACKETCHARSET)));
						logger.info("目录:目录类型[{}]目录ID[{}]父目录ID[{}]目录级别[{}]目录长度[{}]目录内容[{}]", 
								new Object[]{	"00",
												fieldTemp[1],
												fieldTemp[2],
												FieldUtils.leftAddZero4FixedLengthString(fieldTemp[3],2),
												ConvertUtils.byteToString(Byte.valueOf(fieldTemp[4])),
												ConvertUtils.bytes2HexStr(fieldTemp[5].getBytes(DynamicConst.PACKETCHARSET))
											}
										);
						
					} catch (Exception e) {
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("系统错误!请拨打客服电话咨询!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("系统错误!组织菜单字符串异常!");
						logger.error("菜单数据拼装错误");
						return ;
					}
				}
				
				if(rowTemp.split(",").length == 9 ){
					fieldTemp = rowTemp.split(",");
						//目录项
						menuString.append("01");
						//目录ID
						menuString.append(fieldTemp[1]);
						//父目录ID
						menuString.append(fieldTemp[2]);
					try {
						//目录级别
						menuString.append(FieldUtils.leftAddZero4FixedLengthString(fieldTemp[3],2));
						//目录长度
						menuString.append(ConvertUtils.byteToString(Byte.valueOf(fieldTemp[4])));
						//目录内容
						menuString.append(ConvertUtils.bytes2HexStr(fieldTemp[5].getBytes(DynamicConst.PACKETCHARSET)));
						//业务码
						menuString.append(ConvertUtils.bytes2HexStr(fieldTemp[6].getBytes(DynamicConst.PACKETCHARSET)));
						//信息长度
						menuString.append(ConvertUtils.byteToString(Byte.valueOf(fieldTemp[7])));
						//配置信息
						menuString.append(ConvertUtils.bytes2HexStr(fieldTemp[8].getBytes(DynamicConst.PACKETCHARSET)));
						
						logger.info("目录:目录类型[{}]目录ID[{}]父目录ID[{}]目录级别[{}]目录长度[{}]目录内容[{}]业务码[{}]信息长度[{}]配置信息[{}]", 
								new Object[]{	"01",
												fieldTemp[1],
												fieldTemp[2],
												FieldUtils.leftAddZero4FixedLengthString(fieldTemp[3],2),
												ConvertUtils.byteToString(Byte.valueOf(fieldTemp[4])),
												ConvertUtils.bytes2HexStr(fieldTemp[5].getBytes(DynamicConst.PACKETCHARSET)),
												ConvertUtils.bytes2HexStr(fieldTemp[5].getBytes(DynamicConst.PACKETCHARSET)),
												ConvertUtils.byteToString(Byte.valueOf(fieldTemp[7])),
												ConvertUtils.bytes2HexStr(fieldTemp[8].getBytes(DynamicConst.PACKETCHARSET))
											}
										);
						
					} catch (Exception e) {
						bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
						bm.setResponseMsg("系统错误!请拨打客服电话咨询!");
						cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
						cm.setResultMsg("系统错误!组织菜单字符串异常!");
						logger.error("菜单数据拼装错误");
						return ;
					}
				}
			}
		}
		
		if(null == menuString || "".equals(menuString.toString())){ // modified by wangwei
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("后台系统错误!请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("后台系统错误!组织菜单字符串异常!");
			logger.error("检查MenuDaoImpl.getMenus()方法");
			return;
		}
		
		bm.setCustomData(menuString.toString());
		
		//修改数据库缴费菜单下载为0
		bm.getShop().setMenuFlag(GlobalConst.SHOP_MENUFLAG_NO);
		companyDao.update(bm.getShop());
		
		logger.info("缴费菜单更新业务处理结束!");
		
		/*
		List<TbBiMenu> menuList = menuDao.getAllMenus();
		List<TbBiMenu> busiMenuList = menuDao.getBusinessMenus(bm.getShopCode());

		logger.info(menuList == null ? "目录空" : "目录数"+menuList.size());
		logger.info(busiMenuList == null ? "菜单空" : "业务菜单数"+busiMenuList.size());
		
		if (menuList==null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("后台系统错误!菜单目录为空,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("后台系统错误!菜单目录为空,请拨打客服电话咨询!");
			logger.error("系统错误!菜单目录为空");
			return;
		}
		
		if (busiMenuList==null) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("缴费菜单表为空,请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("缴费菜单表为空,请拨打客服电话咨询!");
			logger.error("商户:"+bm.getShopCode()+"关联的缴费菜单为空");
			return;
		}
		
		//field 55, customData, 应用信息域（菜单数据）
		try {
			Object o = (Object)get55FieldString(menuList , busiMenuList);
			if(null == o){
				throw new Exception();
			}
			bm.setCustomData(o);
		} catch (Exception e) {
			bm.setResponseCode(GlobalConst.RESPONSECODE_FAILURE);
			bm.setResponseMsg("后台系统错误!组织报文异常，请拨打客服电话咨询!");
			cm.setResultCode(GlobalConst.RESULTCODE_FAILURE);
			cm.setResultMsg("后台系统错误!组织报文异常，请拨打客服电话咨询!");
			logger.error("拼接55域错误"+e);
		}
		
		//修改数据库缴费菜单下载为0
		bm.getShop().setMenuFlag(GlobalConst.SHOP_MENUFLAG_NO);
		companyDao.update(bm.getShop());*/
	}
	
	private String getSQL(String compCode){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \n");
		
		sql.append("select distinct * from (select '01'||','||a.menu_code||','||a.up_menucode||','||'0'||a.grade||','||decode(a.bytelen,null,length(a.menu_name)*2,a.bytelen)||','||a.menu_name||','||a.busi_code||','||'04'||','||a.input_type||a.input_len||a.str_type as company_menu_str  \n");
        sql.append("from tb_bi_menu a, tb_bi_company_business b \n");
        sql.append("where a.busi_code = b.busi_code  \n");
        sql.append("and b.company_code = '"+compCode+"' \n");
        sql.append("union all \n");
        sql.append("select '00'||','||a.menu_code||','||a.up_menucode||','||'0'||a.grade||','||decode(a.bytelen,null,length(a.menu_name)*2,a.bytelen)||','||a.menu_name as company_menu_str \n");
        sql.append("from tb_bi_menu a \n");
        sql.append("where a.is_business = '0' \n");
        sql.append("and a.menu_code in (select c.up_menucode \n");
                            sql.append("from tb_bi_menu c, tb_bi_company_business d \n");
                            sql.append("where c.busi_code = d.busi_code \n");
                            sql.append("and d.company_code = '"+compCode+"') \n");
        sql.append("union all \n");
        sql.append("select '00'||','||a.menu_code||','||a.up_menucode||','||'0'||a.grade||','||decode(a.bytelen,null,length(a.menu_name)*2,a.bytelen)||','||a.menu_name as company_menu_str \n");
        sql.append("from tb_bi_menu a \n");
        sql.append("where a.menu_code in (select b.up_menucode \n");
                              sql.append("from tb_bi_menu b \n");
                              sql.append("where  b.is_business = '0' \n");
                              sql.append("and b.menu_code in (select c.up_menucode \n");
                                                  sql.append("from tb_bi_menu c, tb_bi_company_business d \n");
                                                  sql.append("where c.busi_code = d.busi_code \n");
                                                  sql.append("and d.company_code = '"+compCode+"') \n");
                             sql.append(") \n");
        sql.append("union all \n");
        sql.append("select '00'||','||e.menu_code||','||e.up_menucode||','||'0'||e.grade||','||decode(e.bytelen,null,length(e.menu_name)*2,e.bytelen)||','||e.menu_name as company_menu_str \n");
        sql.append("from tb_bi_menu e \n");
        sql.append("where e.menu_code in (select a.up_menucode \n");
                              sql.append("from tb_bi_menu a \n");
                              sql.append("where a.menu_code in (select b.up_menucode \n");
                                                    sql.append("from tb_bi_menu b \n");
                                                    sql.append("where  b.is_business = '0' \n");
                                                    sql.append("and b.menu_code in (select c.up_menucode \n");
                                                                        sql.append("from tb_bi_menu c,tb_bi_company_business d \n");
                                                                        sql.append("where c.busi_code = d.busi_code \n");
                                                                        sql.append("and d.company_code = '"+compCode+"') \n");
                                                   sql.append(") \n");
                             sql.append(") \n");
        sql.append("union all \n");
        sql.append("select '00'||','||f.menu_code||','||f.up_menucode||','||'0'||f.grade||','||decode(f.bytelen,null,length(f.menu_name)*2,f.bytelen)||','||f.menu_name as company_menu_str \n");
        sql.append("from tb_bi_menu f \n");
        sql.append("where f.menu_code in (select e.up_menucode \n");
                              sql.append("from tb_bi_menu e \n");
                              sql.append("where e.menu_code in (select a.up_menucode \n");
                                                    sql.append("from tb_bi_menu a \n");
                                                    sql.append("where a.menu_code in (select b.up_menucode \n");
                                                                          sql.append("from tb_bi_menu b \n");
                                                                          sql.append("where  b.is_business = '0' \n");
                                                                          sql.append("and b.menu_code in (select c.up_menucode \n");
                                                                                              sql.append("from tb_bi_menu c,tb_bi_company_business d \n");
                                                                                              sql.append("where c.busi_code = d.busi_code \n");
                                                                                              sql.append("and d.company_code = '"+compCode+"') \n");
                                                                         sql.append(") \n");
                                                  sql.append(" ) \n");
                            sql.append(" ) \n");
        sql.append(" ) h order by substr(h.company_menu_str,3,7) \n");
		
		return sql.toString();
	}
	
	/**
	 * 拼接55位元字符串
	 * 
	 * @param menuList
	 * @param busiMenuList
	 * @throws Exception 
	 *//*
	private String get55FieldString(List<TbBiMenu> menuList,List<TbBiMenu> busiMenuList) throws Exception {
		StringBuffer str = new StringBuffer();
		//菜单目录
		for(TbBiMenu m : menuList){
			StringBuffer strShort = new StringBuffer();
			//目录项
			strShort.append("00"); 
			//目录ID
			strShort.append(m.getMenuCode());
			//父目录ID
			strShort.append(m.getUpMenucode());
			//目录级别
			strShort.append(FieldUtils.leftAddZero4FixedLengthString(m.getGrade(),2));
			try {
				//目录长度
				System.out.println("ConvertUtils.byteToString(m.getBytelen())"+ConvertUtils.byteToString(m.getBytelen()));
				System.out.println("m.getBytelen()"+m.getBytelen());
				strShort.append(ConvertUtils.byteToString(m.getBytelen()));
				//目录内容
				strShort.append(ConvertUtils.bytes2HexStr(m.getMenuName().getBytes(DynamicConst.PACKETCHARSET)));
				
				if((int)m.getBytelen() != m.getMenuName().getBytes().length){
					logger.error("菜单数据错误，目录名称和长度不一致!");
					throw new UnsupportedEncodingException();
				}
				logger.info("old strShort[{}]",strShort);
				str.append(strShort);
			} catch (UnsupportedEncodingException e) {
				logger.error("菜单目录转换出错!",e);
				return null;
			}
		}
		//缴费业务菜单项
		for(TbBiMenu m : busiMenuList){
			StringBuffer strLong = new StringBuffer();
			//目录项
			strLong.append("01");
			//目录ID
			strLong.append(m.getMenuCode());
			//父目录ID
			strLong.append(m.getUpMenucode());
			try {
				//目录ID
				strLong.append(ConvertUtils.byteToString((byte)Integer.valueOf(m.getGrade()).intValue()));
				//目录长度
				strLong.append(ConvertUtils.byteToString(m.getBytelen()));
				//目录内容
				strLong.append(ConvertUtils.bytes2HexStr(m.getMenuName().getBytes(DynamicConst.PACKETCHARSET)));
				//业务码
				strLong.append(ConvertUtils.bytes2HexStr(m.getBusiCode().getBytes(DynamicConst.PACKETCHARSET)));
				//信息长度
				strLong.append(ConvertUtils.byteToString((byte)((m.getInputType()+m.getInputLen()+m.getStrType()).length())));
				//配置信息
				strLong.append(ConvertUtils.bytes2HexStr((m.getInputType()+
						m.getInputLen()+m.getStrType()).getBytes(DynamicConst.PACKETCHARSET)));
				
				if((int)m.getBytelen() != m.getMenuName().getBytes().length){
					logger.error("菜单数据错误，目录名称和长度不一致!");
					throw new UnsupportedEncodingException();
				}
				str.append(strLong);
				logger.info("old strLong[{}]",strLong);
				
			} catch (UnsupportedEncodingException e) {
				logger.error("缴费业务菜单转换出错!",e);
				return null;
			}
		}
		
		return str.toString();
		
	}*/
	
	
	@Override
	public boolean needLockProcess() {
		return false;
	}

	@Override
	public void setCallServiceFlag(ControlMessage cm) {
		cm.setServiceCallFlag("0");
	}
	
	@Override
	public void setTradeFlag(BusinessMessage bm) {
		bm.setSeqnoFlag("0");
	}
	
	@Override
	protected String tradeType() {
		return "08";
	}
}
