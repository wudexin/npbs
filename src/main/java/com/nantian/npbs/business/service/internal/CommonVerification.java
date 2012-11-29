package com.nantian.npbs.business.service.internal;

import java.util.List;

import com.nantian.npbs.business.model.TbBiBusinessUnit;
import com.nantian.npbs.business.model.TbBiCompany;
import com.nantian.npbs.business.model.TbBiCompanyMessage;
import com.nantian.npbs.common.GlobalConst;

/**
 * 公共检查类
 * @author qiaoxiaolei
 * @since 2011-08-16
 */
public class CommonVerification {

	/**
	 * 检查商户所在区域系统资源(业务进程数)
	 * @param orgCode 商户所在机构号
	 * @param type  业务类型
	 * @return
	 */
	public static boolean checkProcessNum(TbBiBusinessUnit businessUnit){
		if(businessUnit.getPorcessmax()-businessUnit.getProcessnow() >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查商户状态（冻结、异常、注销等）
	 * @param cId
	 * @return
	 */
	public static boolean checkShopState(TbBiCompany shop){
		
		//0-正常；1-暂停；2-注销
		if(shop.getState().equals("0")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检查签到状态
	 * @param shop
	 * @return
	 */
	public static boolean checkSignState(TbBiCompany shop){
		//0-未签到 1-签到
		if(shop.getCheckstat().equals("0")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检查强制下载标志
	 * @param shop
	 */
	public static char checkForceDownLoadFlag(TbBiCompany shop,List<TbBiCompanyMessage> shopMsgs){
		
		//检查是否要下传终端参数,下载应用程序,下载缴费菜单,重新签到,商户消息下载 
		
		//终端参数,0-无需更新；1-需要更新
		if(shop.getParaFlag().equals("1"))
			return GlobalConst.HEADER_DEALREQ_PARA;
		
		//应用程序,0-无需更新；1-需要更新
		if(shop.getProgramFlag().equals("1"))
			return GlobalConst.HEADER_DEALREQ_PROGRAM;
		
		//缴费菜单,0-无需更新；1-需要更新
		if(shop.getMenuFlag().equals("1"))
			return GlobalConst.HEADER_DEALREQ_MENU;
		
		//重新签到 0-未签到 1-签到
		if(shop.getCheckstat().equals("0"))
			return GlobalConst.HEADER_DEALREQ_CHECK;
		
		//商户消息下载,0-未发；1-已发
		for(TbBiCompanyMessage shopMsg : shopMsgs){
			if(shopMsg.getId().getIssend().equals("0"))
				return GlobalConst.HEADER_DEALREQ_MSG;
		}
		
		return GlobalConst.HEADER_DEALREQ_NO;
	}
	
}
