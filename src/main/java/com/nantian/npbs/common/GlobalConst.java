package com.nantian.npbs.common;

public class GlobalConst {

	public static final String COMMON_HEAD_REQ_XML_MESSAGE = "RequestHeaderMessage";

	// 系统环境相关

	public static String WEB_INF_PATH = null;

	public static String HOST_ADDRESS = "localhost";

	/* TomcatJNDI前缀 */
	public static final String TOMCAT_JNDI_PREFIX = "java:comp/env/";

	public static String JNDI_PREFIX = "";

	/* Tomcat */
	public static final int WEBSERVER_TOMCAT = 1;

	/* Weblogic */
	public static final int WEBSERVER_WEBLOGIC = 2;

	/* Webserver 类型 */
	public static int WEBSERVER_TYPE = 0;

	public static String BODY_TYPE_FIELD = "bodytype";
	public static String BODY_SIZE_FIELD = "bodysize";
	public static final int BODY_TYPE_NONE = 0;
	public static final int BODY_TYPE_PACKET = 1;
	public static final int BODY_TYPE_STRING = 2;
	public static final int BODY_TYPE_ARRAYBYTE = 3;
	public static final int BODY_TYPE_STREAM = 4;
	public static final int BODY_TYPE_MAP = 5;
	public static final int BODY_TYPE_OBJECT = 6;
	public static final int BODY_TYPE_UNKNOW = 100;

	public static final String BODY_ENCRYPT_TYPE_FIELD = "bodyencrypttype";
	public static final boolean BODY_ENCRYPT_TYPE_NONE = false;
	public static final boolean BODY_ENCRYPT_TYPE_BASE64 = true;
	public static final String BODY_COMPRESS_TYPE_FIELD = "bodycompresstype";
	public static final int BODY_COMPRESS_TYPE_NONE = 0;
	public static final int BODY_COMPRESS_TYPE_ZIP = 1;

	public static final String SPLIT_TYPE_GAP = "#";// "#$#";
	public static final String SPLIT_DATA_GAP = "@";// "#@#";
	public static final String DATA_TYPE_PACKET = "PACKET";
	public static final String DATA_TYPE_META = "META";
	public static final String DATA_TYPE_OBJECT = "OBJECT";

	//pos分4个端口
//	public static final String POS_HOST_REQ_ADDRESS = "netty:tcp://0.0.0.0:7777";
	public static final String NETTY_TCP = "netty:tcp://0.0.0.0:";
	public static final String POS_HOST_REQ_ADDRESS[] = {"8884","8885","8886","8887"};
	//public static final String WEBSERVICES_HOST_ADDRESS = "7001";
	
	
	public static final String POS_HOST_ANS_ADDRESS = "netty:tcp://0.0.0.0:6666";
	public static final String EPOS_HOST_ADDRESS = "netty:tcp://0.0.0.0:8888";
	public static final String ELEBUSIREQUEST_HOST_ADDRESS = "netty:tcp://0.0.0.0:9877";
	

	public static final int MAIN_QUEUE_CONCURRENT_CONSUMERS = 100; // 主消息队列并发数
	public static final int MAIN_QUEUE_LENGTH = 100; // 主消息队列长度

	public static final int COMMON_QUEUE_CONCURRENT_CONSUMERS = 100; //公共、非特定服务队列并发数
	public static final int COMMON_QUEUE_LENGTH = 50; //公共、非特定服务队列长度

	public static final int SERVICE_QUEUE_CONCURRENT_CONSUMERS = 100; // 服务、交易队列并发数
	public static final int SERVICE_QUEUE_LENGTH = 100; // 服务、交易队列长度

	public static final int CHANNEL_QUEUE_CONCURRENT_CONSUMERS = 20; // 渠道队列并发数
	public static final int CHANNEL_QUEUE_LENGTH = 20; // 公共、非特定服务队列长度

	public static enum DATA_TYPE {
		CONTROLOBJECT, BUSINESSOBJECT, ORIGREQPACKET, SERVICEREQPACET, SERVICEANSPACET, ORIGANSPACKET
	//add by fengyafang  20121020
		,WEBREQ,WEBANS
	}

	public static enum CHANEL_TYPE {
		POS, // 便民服务站
		EPOS, // 缴费一站通
		ELEBUSISERIVCE, // 电子商务平台服务渠道
		ELEBUSIREQUEST,//web渠道
		WEB
	 
	 
	}
	
	public static final String posChanelCode = "01";
	public static final String eposChanelCode = "02";
	public static final String eleChanelCode = "03";

	public static enum SEDA_TYPE {
		REQUEST, SERVICEREQUEST, SERVICEANSWER, ASYNCANSWER
	}

	public static enum PACKET_TYPE {
		ISO8583, TUXSTRING, FIXSTRING, SPLITSTRING
	}

	// 报文头，终端状态
	public static final int HEADER_TERMINAL_NORMAL = 0; // 正常交易状态
	public static final int HEADER_TERMINAL_TEST = 1; // 测试交易状态

	// 报文头，处理要求
	public static final char HEADER_DEALREQ_NO = '0'; // 无处理要求
	public static final char HEADER_DEALREQ_PARA = '1'; // 下传终端参数
	public static final char HEADER_DEALREQ_PROGRAM = '2'; // 下载应用程序
	public static final char HEADER_DEALREQ_MENU = '3'; // 下载缴费菜单
	public static final char HEADER_DEALREQ_CHECK = '4'; // 重新签到
	public static final char HEADER_DEALREQ_MSG = '5'; // 商户消息下载
	public static final char HEADER_DEALREQ_CHANNEL = '6'; // 0-POS 1-EPOS 渠道类型

	public static final String RESPONSECODE_SUCCESS = "00"; // 为交易成功
	public static final String RESPONSECODE_FAILURE = "01"; // 为交易失败
	public static final String RESPONSECODE_NO_DOWNLOAD = "02"; // 版本已是最新，不需下载

	// 系统使用
	public static final String RESULTCODE_SUCCESS = "000000"; // 成功
	public static final String RESULTCODE_FAILURE = "000001"; // 失败
	public static final String RESULTCODE_BUSY = "000003"; // 第三方服务繁忙

	// 商户状态
	public static final String SHOP_STATE_NORMAL = "0"; // 0-正常
	public static final String SHOP_STATE_PAUSE = "1"; // 1-暂停
	public static final String SHOP_STATE_LOGOUT = "2"; // 2-注销

	// 商户签到
	public static final String SHOP_CHECK_STATE_NO = "0"; // 0-未签到
	public static final String SHOP_CHECK_STATE_YES = "1"; // 1-签到

	// 商户菜单下载标致
	public static final String SHOP_MENUFLAG_NO = "0"; // 0-无需更新
	public static final String SHOP_MENUFLAG_YES = "1"; // 1-需要更新

	// 商户应用程序更新标致
	public static final String SHOP_PROGRAMFLAG_NO = "0"; // 0-无需更新
	public static final String SHOP_PROGRAMFLAG_YES = "1"; // 1-需要更新

	// 商户参数下载标致
	public static final String SHOP_PARAFLAG_NO = "0"; // 0-无需更新
	public static final String SHOP_PARAFLAG_YES = "1"; // 1-需要更新

	// 支付类型
	public static final String PAYMENT_TYPE_RESCASH = "1"; // 1 - 备付金

	// 费用类型
	public static final String FEE_TYPE_STORED = "00"; // 0 - 预存款
	public static final String FEE_TYPE_OWE = "01"; // 1 - 欠款

	// 酬金本波段计算方式
	public static final String Formula_CALCULATE_TYPE_TIME = "1"; // 1-按笔数
	public static final String Formula_CALCULATE_TYPE_AMOUNT = "2"; // 2-按金额

	// 超时间隔（秒）
	public static final int TIME_OUT_INTERVAL = 60;

	// POS标志域补零常量
	public static final String POS_FLAGFIELD_FILL_ZERO = "00000000000000";

	/* 应用程序更新响应包文件大小 */
	public static final int DOWN_BLOCK_SIZE = 512;

	/* 交易取消授权标致 */
	public static final String TRADE_CANCEL_FLAG_NO = "0";// 取消授权标志 0-未授权
	public static final String TRADE_CANCEL_FLAG_YES = "1";// 取消授权标志 1-已授权

	/* 流水状态 */
	public static final String TRADE_STATUS_SUCCESS = "00"; // 交易成功
	public static final String TRADE_STATUS_FAILURE = "01"; // 交易失败
	public static final String TRADE_STATUS_NOEXSIST = "02"; // 原交易不存在
	public static final String TRADE_STATUS_CANCEL = "03"; // 交易被取消
	public static final String TRADE_STATUS_CHONGZHENG = "04"; // 交易被冲正	
	public static final String TRADE_STATUS_FAILURE_TO_SUCCESS = "05"; // 交易由失败状态改为成功状态
	public static final String TRADE_STATUS_CARD_FAILURE = "06"; // 写卡失败
	public static final String TRADE_CANCEL_ING = "13"; // 交易取消中	
	public static final String TRADE_STATUS_CARD_ORIG = "99"; // 初始状态，交易未确定
	public static final String TRADE_STATUS_NEED_WRITE = "14"; //待写卡状态
	
	/* 备付金默认密码 */
	public static final String PASSWD_INIT = "888888";// 备付金初始密码
	
	/* 交易类型 */
	/**交易类型：缴费*/public static final String TRADE_TYPE_JF = "01";
	/**交易类型：取消*/public static final String TRADE_TYPE_QX = "02";
	/**交易类型：写卡*/public static final String TRADE_TYPE_XK = "03";
	/**交易类型：冲正*/public static final String TRADE_TYPE_CZ = "04";
	/**交易类型：写卡成功确认*/public static final String TRADE_XKCGQR = "05";
	/**交易类型：写卡失败确认*/public static final String TRADE_XKSBQR = "06";
	/**交易类型：查询*/public static final String TRADE_TYPE_CX = "07";
	/**交易类型：管理*/public static final String TRADE_TYPE_GL = "08";
	/**交易类型：取消写卡*/public static final String TRADE_TYPE_QXXK = "09";
	
	
	/* 备付金状态 */
	/**备付金状态 ：正常*/public static final String PREPAY_STATE_ZC = "0";
	/**备付金状态 ：暂停*/public static final String PREPAY_STATE_ZT = "1";
	/**备付金状态 ：注销*/public static final String PREPAY_STATE_ZX = "2";
	
	/* 是否登记流水 */
	/**是否登记流水 ：不登记*/public static final String SEQNO_FLAG_NO = "0";
	/**是否登记流水 ：登记*/public static final String SEQNO_FLAG_YES = "1";
	
	/* 是否调用第三方 */
	/**是否发送第三方 ：不发送*/public static final String SERVICE_CALL_FLAG_NO = "0";
	/**是否发送第三方 ：发送*/public static final String SERVICE_CALL_FLAG_YES = "1";
	
	 


}
