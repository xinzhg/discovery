package com.github.conanzhangxin.discovery.common.constants;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-5
 * Time: ÏÂÎç8:29
 * To change this template use File | Settings | File Templates.
 */
public class HttpConstants {

    private static Properties getBaiduSercetKeyHelper() {
        Properties p = new Properties();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("baidu-secret-key.properties");
            p.load(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public static final Properties BAIDU_SECRET_KEY_PROPERTIES_HELPER =  getBaiduSercetKeyHelper();

    /**
     *  port 80
     */
    public static final int PORT80 = 80;

    /**
     *  port 8080
     */
    public static final int PORT8080 = 8080;

    /**
     * http method post
     */
    public static final String POST = "POST";

    /**
     * http method get
     */
    public static final String GET = "GET";

    /**
     *  baidu openapi host
     */
    public static final String BAIDU_OPENAPI_HOST = "openapi.baidu.com";

    /**
     * baidu pcs (personal cloud store) host
     */
    public static final String BAIDU_PCS_HOST = "pcs.baidu.com";

    /**
     * baidu pcs upload host
     */
    public static final String BAIDU_PCS_UPLOAD_HOST = "c.pcs.baidu.com";

    /**
     * baidu pcs download host
     */
    public static final String BAIDU_PCS_DOWNLOAD_HOST = "d.pcs.baidu.com";

    /**
     *  baidu oauth req path
     */
    public static final String BAIDU_ACCESS_TOKEN_REQ_PATH = "/oauth/2.0/authorize";

    /**
     * baidu application store quota req path
     */
    public static final String BAIDU_APPLICATION_QUOTA_REQ_PATH = "/rest/2.0/pcs/quota";

    /**
     * baidu file rest api req path
     */
    public static final String BAIDU_FILE_REST_API_REQ_PATH = "/rest/2.0/pcs/file";


    /**
     * baidu application app key
     */
    public static final String BAIDU_APPLICATION_APP_KEY = BAIDU_SECRET_KEY_PROPERTIES_HELPER.getProperty("BAIDU_APPLICATION_APP_KEY" , "");

    /**
     * baidu application client id
     */
    public static final String BAIDU_APPLICATION_CLIENT_ID = BAIDU_SECRET_KEY_PROPERTIES_HELPER.getProperty("BAIDU_APPLICATION_APP_KEY" , "");

    /**
     * baidu application app secret key
     */
    public static final String BAIDU_APPLICATION_APP_SECRET_KEY = BAIDU_SECRET_KEY_PROPERTIES_HELPER.getProperty("BAIDU_APPLICATION_APP_SECRET_KEY" , "");

    /**
     * baidu application client_secret
     */
    public static final String BAIDU_APPLICATION_CLIENT_SECRET = BAIDU_SECRET_KEY_PROPERTIES_HELPER.getProperty("BAIDU_APPLICATION_APP_SECRET_KEY" , "");

}
