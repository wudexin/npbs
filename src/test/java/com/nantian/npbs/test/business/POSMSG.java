package com.nantian.npbs.test.business;

/**
 * pos报文类
 * @author qiaoxl
 *
 */
public class POSMSG {

	public static String[] getMsg(){
		String[] msg = new String[350];
		
		// 移动手机
		msg[10] = packet001001();
		msg[11] = packet001002();
		msg[12] = packet001012();

		// 联通手机
//		msg[20] = packet002001();
//		msg[21] = packet002002();
//		msg[22] = packet002012();

		//电信手机、固话
		msg[31] = packet003001();
		msg[32] = packet003002();
		msg[33] = packet003012();

		//联通固话
//		msg[41] = packet004001();
//		msg[42] = packet004002();
//		msg[43] = packet004012();
		
		//现金代收张家口水费
//		msg[61] = packet006001();
//		msg[62] = packet006002();
//		msg[63] = packet006012();
		
		//河电现金
		msg[71] = packet007001();
		msg[72] = packet007002();
		msg[73] = packet007012();
		
		// 现金新奥燃气
//		msg[81] = packet008001();
//		msg[82] = packet008002();
//		msg[83] = packet008012();
		
		// 有线电视
//		msg[91] = packet009001();
//		msg[92] = packet009002();
//		msg[93] = packet009012();
		

		// 河电省标电卡
//		msg[101] = packet010001();
//		msg[102] = packet010002();
//		msg[103] = packet010003();
//		msg[104] = packet010004();
//		msg[105] = packet010012();
//		msg[106] = packet010022();
//		msg[107] = packet010023();

		// 新奥燃气IC卡
//		msg[111] = packet011001();
//		msg[112] = packet011002();
//		msg[113] = packet011003();
//		msg[114] = packet011012();
		
		// 河电智能电卡
//		msg[121] = packet012001();
//		msg[122] = packet012002();
//		msg[123] = packet012003();
//		msg[124] = packet012012();
		
		// 华电智能电卡
//		msg[131] = packet013001();
//		msg[132] = packet013002();
//		msg[133] = packet013012();
//		msg[134] = packet013022();
		
		// 华电无IC卡
		msg[141] = packet014001();
		msg[142] = packet014002();
		msg[143] = packet014012();
		
		// 现金代收保定水费
//		msg[161] = packet016001();
//		msg[162] = packet016002();
//		msg[163] = packet016012();
		

		// 备付金明细查询
		msg[250] = packet000005();
		// 备付金明细查询
		msg[251] = packet000006();
		// 备付金明细查询
		msg[252] = packet000007();
		// 备付金明细查询
		msg[253] = packet000008();
		// 备付金明细查询
		msg[254] = packet000009();
		// 备付金明细查询
		msg[255] = packet000010();
		// 备付金明细查询
		msg[256] = packet000011();
		// 备付金明细查询
		msg[257] = packet000013();
		// 备付金明细查询
		msg[258] = packet000014();
		// 备付金明细查询
		msg[259] = packet000015();
		// 备付金明细查询
		msg[260] = packet000019();
		// 备付金明细查询
		msg[261] = packet000020();
		// 备付金明细查询
		msg[262] = packet000021();
		// 备付金明细查询
		msg[263] = packet000901();
		// 备付金明细查询
		msg[264] = packet000903();
		// 备付金明细查询
		msg[265] = packet000905();
		// 备付金明细查询
		msg[266] = packet000906();
		// 备付金明细查询
		msg[267] = packet000907();
			
		return msg;
	}
	
	/**
	 * 移动手机查询
	 */
	private static String packet001001() {
		/*return "005b600008000060220000085802000020000100c0801120444200113135383131343430303738202020202020202020202020202020202020202030353030303030312020202020202020202020203135360010015544964c75ab452a";*/
		return "005b600018000060220000080302000020000100c080110000030011313331373135353035363220202020202020202020202020202020202020203035303031313238202020202020202020202020313536001001ab260ea85029fa24".trim();
	}
	
	/**
	 * 移动手机缴费
	 */
	private static String packet001002() {
		/*return "007960000800006022000008580200502000810cc090110000000000020020444500001131353831313434303037382011111232948306d5c5b6abd1c720202020202020202020202020202020202020203035303030303031202020202020202020202020313536e86512625d5c279c001002f88d8a4d0a23cb3b";*/
		return "008960001800006022000008030200502200810cc09011000000000015000000043130303030303030303030303030303000001131333137313535303536321112110000669806d5c5b6abd1c7202020202020202020202020202020202020202030353030313132382020202020202020202020203135362cf7fdaf49d7946e0010028d078afdb64f6144".trim();
	}
	
	/**
	 *  移动手机取消
	 */
	private static String packet001012() {
		return "006f600008000060220000085802001000000100c0801900000000030000113135383131343430303738202020202020202020202020202020202020202030353030303030312020202020202020202020203135360000120015323031313131323233323936393133da7ff8801a531899";
	}
	
	/**
	 * 联通手机查询
	 */
	private static String packet002001() {
		return "";
	}
	
	/**
	 * 联通手机缴费
	 */
	private static String packet002002() {
		return "";
	}
	
	/**
	 *  联通手机取消
	 */
	private static String packet002012() {
		return "";
	}

	/**
	 * 电信手机、固话查询
	 */
	private static String packet003001() {
		/*return "005B600008000060220000085802000020000100C08011204252001131333438333637323237352020202020202020202020202020202020202020303530303030303120202020202020202020202031353600300151160E78A3BF0016";*/
		return "005b600018000060220000080302000020000100c0801100000500113133303733313939313931202020202020202020202020202020202020202030353030313132382020202020202020202020203135360030018e259958ab7cd2b2".trim();
	}
	
	/**
	 * 电信手机、固话缴费
	 */
	private static String packet003002() {
		/*return "007760000800006022000008580200502000810cc090110000000000020020439800001131353831313434303037382011111232940704b0b2e6c220202020202020202020202020202020202020203035303030303031202020202020202020202020313536e7c6f46bc0fe3c50003002fb434eb4ee5df240";*/
		return "008760001800006022000008030200502200810cc09011000000000055000000063130303030303030303030303030303000001131333037333139393139311112110000670204b0b2e6c2202020202020202020202020202020202020202030353030313132382020202020202020202020203135362cf7fdaf49d7946e00300218622f1a62174b9a".trim();
	}
	
	/**
	 * 电信手机、固话取消
	 */
	private static String packet003012() {
		return "006f600008000060220000085802001000000100c0801900000000030000113135383131343430303738202020202020202020202020202020202020202030353030303030312020202020202020202020203135360000120015323031313131313233323934353233b98d851797118ca3";
	}
	
	/**
	 * 联通固话查询
	 */
	private static String packet004001() {
		return "";
	}
	
	/**
	 * 联通固话缴费
	 */
	private static String packet004002() {
		return "";
	}
	
	/**
	 *  联通固话取消
	 */
	private static String packet004012() {
		return "";
	}
	
	/**
	 * 现金代收张家口水费查询
	 */
	private static String packet006001() {
		return "";
	}
	
	/**
	 * 现金代收张家口水费缴费
	 */
	private static String packet006002() {
		return "";
	}
	
	/**
	 *  现金代收张家口水费取消
	 */
	private static String packet006012() {
		return "";
	}
	
	/**
	 * 河电现金查询
	 * @return
	 */
	private static String packet007001() {
		/*return "005A600008000060220000085802000020000100C0801120379300103032373635333936303020202020202020202020202020202020202020203035303030303031202020202020202020202020313536007001AF57BE1E3EE45235";*/
		return "005a600018000060220000080302000020000100c080110000070010303237363135383931332020202020202020202020202020202020202020303530303131323820202020202020202020202031353600700128a626f4cd1cfa3e".trim();
	}
	
	/**
	 * 河电现金缴费
	 * @return
	 */
	private static String packet007002() {
		/*return "00766000080000602200007830020050200" +
		"0810CC0901100000000000012202237000010" +
		"303237363533393630302011092132576704B" +
		"AEECEC4202020202020202020202020202020" +
		"2020202020303530303030303120202020202" +
		"020202020202031353657F9A62E4E60AA8300" +
		"70023455179382A97083";*/
		return "008c60001800006022000008030200502200810cc090110000000000555500000831303030303030303030303030303030000010303237363135383931331112110000674510caa1b1a3cfd5b9abcbbe2020202020202020202020202020202020202020303530303131323820202020202020202020202031353665fac012649e5827007002421bb6d2ed0e67a2".trim();
	}
	
	/**
	 * 河电现金取消
	 */
	private static String packet007012() {
		return "006e600008000060220000085802001000000100c08019000000000100001030323736353339363030202020202020202020202020202020202020202030353030303030312020202020202020202020203135360000120015323031313131313233323933353000162dddc98f2dcb4b";
	}
	
	/**
	 * 现金新奥燃气查询
	 */
	private static String packet008001() {
		return "0052600008000060220000085802000020000100C080112036880002313120202020202020202020202020202020202020203035303030303031202020202020202020202020313536008001BF15B606B377B7C8";
	}
	
	/**
	 * 现金新奥燃气缴费
	 */
	private static String packet008002() {
		return "007060000800006022000008580200502000810CC090110000000000011120369200000231312011092132824706C1F5D4F6C0FB20202020202020202020202020202020202020203035303030303031202020202020202020202020313536B95580023423781C0080029052E6D3454761FD";
	}
	
	/**
	 * 现金新奥燃气取消
	 */
	private static String packet008012() {
		return "";
	}
	
	/**
	 * 有线电视查询
	 */
	private static String packet009001() {
		return "";
	}
	
	/**
	 * 有线电视缴费
	 */
	private static String packet009002() {
		return "";
	}
	
	/**
	 *  有线电视取消
	 */
	private static String packet009012() {
		return "";
	}
	
	/**
	 * 河电省标电卡查询
	 * @return
	 */
	private static String packet010001() {
		return "012A600008000060220000085802000020000100C082112038700016304136394646383643464144413235354D4F44454D3A2020202000132020202020202020303530303030303120202020202020202020202031353602003030303030303030303037363738383933303038334137384638394538414330363344343041363946463836434641444132353530303030303030303030303030303030303035303030303030313934303030303030373530303030303030303030303030373137303130303030303030303030303030303030303033343037383730303030303030303030303030302020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020010001B76D64DCA2ACBA2D";
	}
	
	/**
	 * 河电省标电卡缴费
	 * @return
	 */
	private static String packet010002(){
		return "026860000800006022000008580200502000810CC0921100000000000100203882000010303334303132333437342011092132858560CBEFD3C0C7BF20202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020203035303030303031202020202020202020202020313536D6D4A6FAFB3E4EB10440303030303030303030303736373838393330303835443643384632414541333746464242304136394646383643464144413235353030303030303030303030303030303030303530303030303031393430303030303037353030303030303030303030303037313730313030303030303030303030303030303030303334303738373030303030303030303030303030202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202030333430313233343734202020202020CBEFD3C0C7BF202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020BAD3B1B1CAA1CAAFBCD2D7AFCAD0BFAAB7A2C7F8D6E9B7E5B4F3BDD6313830BAC5323223C2A531B5A5D4AA3930312020202020202020202020202020202020202020202020202020202020202020202030303030303030303030303030303030B7C7C6D5B2BBC2FA314B56202020202020202020202020202020202020203030303030303030303030303830343430303030303030303030303030303339303030303030010002BBDCA84AB6561B13";
	}
	
	/**
	 * 河电省标电卡写卡申请
	 * @return
	 */
	private static String packet010003(){
		return "";
	}
	
	/**
	 * 河电省标电卡写卡确认
	 * @return
	 */
	private static String packet010004(){
		return "";
	}
	
	/**
	 * 河电省标电卡取消
	 * @return
	 */
	private static String packet010012(){
		return "";
	}
	
	/**
	 * 河电省标电卡申请写卡数据（补写）
	 * @return
	 */
	private static String packet010022(){
		return "";
	}
	
	/**
	 * 河电省标电卡补写卡
	 * @return
	 */
	private static String packet010023(){
		return "";
	}
	
	
	/**
	 * 新奥燃气IC卡查询
	 * @return
	 */
	private static String packet011001() {
		return "";
	}
	
	/**
	 * 新奥燃气IC卡缴费
	 * @return
	 */
	private static String packet011002() {
		return "";
	}
	
	/**
	 * 新奥燃气IC卡写卡
	 * @return
	 */
	private static String packet011003(){
		return "";
	}
	
	/**
	 * 新奥燃气IC卡取消
	 * @return
	 */
	private static String packet011012(){
		return "";
	}
	
	
	/**
	 * 河电智能电卡查询
	 * @return
	 */
	private static String packet012001() {
		return "";
	}
	
	/**
	 * 河电智能电卡缴费
	 * @return
	 */
	private static String packet012002() {
		return "";
	}
	
	/**
	 * 河电智能电卡写卡
	 * @return
	 */
	private static String packet012003(){
		return "";
	}
	
	/**
	 * 河电智能电卡取消
	 * @return
	 */
	private static String packet012012(){
		return "";
	}
	
	/**
	 * 华电智能电卡查询
	 * @return
	 */
	private static String packet013001() {
		return "";
	}
	
	/**
	 * 华电智能电卡缴费写卡
	 * @return
	 */
	private static String packet013002() {
		return "";
	}
	
	/**
	 * 华电智能电卡取消 
	 * @return
	 */
	private static String packet013012(){
		return "";
	}
	
	/**
	 * 华电智能电卡补写卡交易
	 * @return
	 */
	private static String packet013022(){
		return "";
	}
	
	/**
	 * 华电无IC卡查询
	 * @return
	 */
	private static String packet014001() {
		return "";
	}
	
	/**
	 * 华电无IC卡缴费
	 * @return
	 */
	private static String packet014002() {
		return "";
	}
	
	/**
	 * 华电无IC卡取消
	 * @return
	 */
	private static String packet014012(){
		return "";
	}
	
	/**
	 * 现金代收保定水费查询
	 * @return
	 */
	private static String packet016001() {
		return "";
	}
	
	/**
	 * 现金代收保定水费缴费
	 * @return
	 */
	private static String packet016002() {
		return "";
	}
	
	/**
	 * 现金代收保定水费取消
	 * @return
	 */
	private static String packet016012(){
		return "";
	}
	
	
	/**
	 * 备付金明细查询
	 */
	private static String packet000005(){
		return "";
	}
	
	/**
	 * 用户交易流水列表查询
	 */
	private static String packet000006(){
		return "";
	}
	
	/**
	 * 用户交易明细查询
	 */
	private static String packet000007(){
		return "";
	}
	
	/**
	 * 终端查询交易量
	 */
	private static String packet000008(){
		return "0060600018000060220000783002000020000000c080190000342020202020202020202020202020202020202020303530303030303320202020202020202020202031353600000800163230313230313031323031323031303937c01147eb5e941b";
	}
	
	/**
	 * POS流水号交易查询
	 */
	private static String packet000009(){
		return "";
	}
	
	/**
	 * 末笔交易查询
	 */
	private static String packet000010(){
		return "";
	}
	
	/**
	 * 商户信息下载
	 * @return
	 */
	private static String packet000011()  {
		/*return "004B600008000060220000085808000020000000C00011204477202020202020202020202020202020202020202030353030303030312020202020202020202020200000110DA5791239907BC4";*/
		return "004b600018000060220000783008000020000000c00011000035202020202020202020202020202020202020202030353030303030332020202020202020202020200000115f7e93d28a8de9aa";
	}
	
	/**
	 * 末笔成功交易查询
	 */
	private static String packet000013(){
		return "";
	}
	
	/**
	 * 终端查询交易明细（指定业务种类）
	 */
	private static String packet000014(){
		return "";
	}
	
	/**
	 * 终端交易明细查询(指定用户号)
	 */
	private static String packet000015(){
		return "";
	}
	
	/**
	 * 备付金余额查询
	 * @return
	 */
	private static String packet000019()  {
		/*return "0056600008000060220000783008000020000000C0901120193220202020202020202020202020202020202020203035303030303031202020202020202020202020313536B61A8F164E3B543C0000190006323034363137741cfb78260ae2d5";*/
		return "0056600018000060220000783002000020000000c0901100003320202020202020202020202020202020202020203035303030303033202020202020202020202020313536af0b44e03370f0d6000019d6c241bb0945a831";
	}
	
	/**
	 * 绿卡余额查询
	 * @return
	 */
	private static String packet000020() {
		return "005b600008000060220000783008000020000000c018110003032020202020202020202020202020202020202020303530303030303120202020202020202020202076a9c027e294f8d173b358d20548e24c000021d978adf2e19aa2ff";
	}
	
	/**
	 * 备付金密码修改
	 * @return
	 */
	private static String packet000021() {
		/*return "005b600008000060220000783008000020000000c018110003032020202020202020202020202020202020202020303530303030303120202020202020202020202076a9c027e294f8d173b358d20548e24c000021d978adf2e19aa2ff";*/
		return "005b600018000060220000783008000020000000c0181100003220202020202020202020202020202020202020203035303030303033202020202020202020202020af0b44e03370f0d6af0b44e03370f0d60000214da4c2fb52ea9182";
	}
	
	/**
	 * 下载主密钥
	 * @return
	 */
	private static String packet000901() {
		return "004B600003000060100000000008000020000000C00011000073313131312020202020202020202020202020202030353030303030322020202020202020202020200009033131313131313131";
	}
	
	/**
	 * POS签到
	 * @return
	 */
	private static String packet000903() {
		/*return "004B600003000060100000000008000020000000C00011000073313131312020202020202020202020202020202030353030303030322020202020202020202020200009033131313131313131";*/
		return "0043600018000060220000783008000020000000c0001000003020202020202020202020202020202020202020203035303030303033202020202020202020202020000903";
	}
	
	/**
	 * 缴费菜单更新
	 * @return
	 */
	private static String packet000905() {
		/*return "004B600003000060100000000008000020000000C00011000150313131312020202020202020202020202020202030353030303030312020202020202020202020200009051E64744DDE8175AB";*/
		return "004b600018000060220000783008000020000000c0001100002620202020202020202020202020202020202020203035303030303033202020202020202020202020000905f7a0364db80df50e";
	}
	
	/**
	 * POS参数管理
	 * @return
	 */
	private static String packet000906()  {
		/*return "004B600003000060100000000008000020000000C000110001483131313120202020202020202020202020202020303530303030303120202020202020202020202000090688EEC193EE209AE2";*/
		return "004d600018000060220000783008000020000000c002110000272020202020202020202020202020202020202020303530303030303320202020202020202020202000000009068b0697c0a794c4f3";
	}
	
	/**
	 * POS应用程序更新
	 * @return
	 */
	private static String packet000907()  {
		/*return "0051600008000060220000783008000020000000C002112019972020202020202020202020202020202020202020303530303030303120202020202020202020202000043030303100090764A4EDA9317E0F6F";*/
		return "08000020000000c0021100003120202020202020202020202020202020202020203035303030303033202020202020202020202020000430303030000907";
	}
	
	
}
