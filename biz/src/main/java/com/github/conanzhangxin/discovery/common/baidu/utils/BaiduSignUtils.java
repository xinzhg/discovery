package com.github.conanzhangxin.discovery.common.baidu.utils;

import com.github.conanzhangxin.discovery.common.constants.HttpConstants;
import com.github.conanzhangxin.discovery.common.exception.DiscoveryException;
import com.sun.crypto.provider.HmacSHA1;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 10/29/13
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 * refer http://developer.baidu.com/wiki/index.php?title=docs/cplat/stor/access/signed-url
 * the class is made for generating baidu sign
 */
public class BaiduSignUtils {

//    StringBuilder stringBuilder = new StringBuilder();
//    stringBuilder.append("http://bcs.duapp.com/discoverymaster/%2Fdiscoverymaster?sign=MBO:1775424a7fa49bcb02c88d62d76301f7:Q0iLKNWip4kP6dREi%2BAulcamTsY%3D");

    public static final String HOSTS = "http://bcs.duapp.com";

    private static final String ACCESS_KEY = HttpConstants.BAIDU_APPLICATION_APP_KEY;

    private static final String SECRET_KEY = HttpConstants.BAIDU_APPLICATION_APP_SECRET_KEY;



    /**
     * default return standard flag
     * @see \http://developer.baidu.com/wiki/index.php?title=docs/cplat/stor/access/signed-url#.E7.AD.BE.E5.90.8D.E7.BB.84.E6.88.90
     * @return
     */
    public static String getStdFlag() {
       return "MBO";
    }

    /**
     * @see \http://developer.baidu.com/bae/bcs/key/sign/###
     */
    public static String generateSign(String method , String bucket , String object , String ip , String size , String time) throws DiscoveryException {

        BASE64Encoder base64Encoder = new BASE64Encoder();
        StringBuilder content = new StringBuilder();
        content.append(getStdFlag()).append("\n")
                .append("Method=").append(method).append("\n")
                .append("Bucket=").append(bucket).append("\n")
                .append("Object=").append(object).append("\n");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(SECRET_KEY.getBytes(),"HmacSHA1");
            mac.init(secret);
            byte[] digest = mac.doFinal(content.toString().getBytes());
            String sign = URLEncoder.encode(base64Encoder.encode(digest),"utf-8");
            StringBuilder signStringBuilder = new StringBuilder();
            return signStringBuilder.append(HOSTS).append("/").append(bucket).append("/").append(object.replaceAll("/" , "%2F")).append("?sign=").append(getStdFlag()).append(":").append(ACCESS_KEY).append(":").append(sign).toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DiscoveryException(e);
        }
    }

    //for test only
    public static void main(String[] args) {

        try {
            String s = generateSign("PUT" ,"123" ,"/123/123" , null , null , null);
            System.out.print(s);
        } catch (Exception e) {
                 e.printStackTrace();
        }
    }





}
