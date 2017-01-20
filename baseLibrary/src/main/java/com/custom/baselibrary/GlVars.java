package com.custom.baselibrary;

public class GlVars {
	private static final GlVars instance = new GlVars();
	public static GlVars Instance() {return instance;}
	
	public static int gIntPressShopParent = 190;
	public static int gIntPressShopDeptId = 57;
	public static int gIntPressShopDieSetterDesgId = 8;
	public static int gIntPressShopOperatorDesgId = 26;
	public static int gIntPressShopHelperDesgId = 18;
	public static int RESULT_OK = 0;
	public static int gIntAssyParentStation = 233;
	public static int gIntAssyDeptId = 18;
	public static String gStrSkilledCode = "02";
	public static String gStrSemiSkilledCode = "03";
	public static String gStrUnSkilledCode = "04";
	public static String gStrFixtureFamilyCode = "13";
	public static String gStrWeldGunFamilyCode = "14";
	public static String gStrSpotGunFamilyCode = "15";
	public static String BATCH_STATUS_NEW = "01";
	public static String BATCH_STATUS_UNDER_PREPARATION = "02";
	public static String BATCH_STATUS_RUNNING = "03";
	public static String BATCH_STATUS_TEMP_STOPPED = "04";
	public static String BATCH_STATUS_COMPLETED = "05";
	public static String BATCH_STATUS_CANCELLED = "06";
	public static String BATCH_STATUS_RESCHEDULED = "07";
}
