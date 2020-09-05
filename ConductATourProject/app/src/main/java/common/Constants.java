package common;

import android.os.Environment;

/**
 * 常量池定义
 */
public class Constants {
    /**
     * 是否是测试模式
     */
    public static boolean isDebug = true;

    /**
     * 业务状态码成功标识
     */
    public static final String BIZCODE_OK = "OK";

    /**
     * 请求数据缓存
     */
    public static final String HTTP_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/httpCache";


    /**
     * 用户缓存信息
     */
    public static String USERID;
    public static String TOKEN;
    public static String NIKENAME;
}
