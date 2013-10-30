package com.github.conanzhangxin.discovery.common.utils.httpClient.impl;

import com.github.conanzhangxin.discovery.common.baidu.utils.BaiduSignUtils;
import com.github.conanzhangxin.discovery.common.constants.HttpConstants;
import com.github.conanzhangxin.discovery.common.constants.StoreConstants;
import com.github.conanzhangxin.discovery.common.exception.DiscoveryException;
import com.github.conanzhangxin.discovery.common.utils.CamelCaseUtil;
import com.github.conanzhangxin.discovery.common.utils.httpClient.DiscoveryHttpClient;
import com.github.conanzhangxin.discovery.dataobject.BasicHttpResult;
import com.github.conanzhangxin.discovery.dataobject.HttpTokenResult;
import com.github.conanzhangxin.discovery.dataobject.QuotaResult;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import static com.github.conanzhangxin.discovery.common.constants.HttpConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-4
 * Time: ÏÂÎç9:40
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientImpl implements DiscoveryHttpClient {


    public static boolean prototypeHttpsPutProcessor(String fileKey , String content) throws Exception {
        String sign =  BaiduSignUtils.generateSign("PUT", StoreConstants.STORE_BUCKET_MASTER, fileKey, null , null , null );
        HttpPut httpPut = new HttpPut(sign);
        httpPut.setEntity(new StringEntity(content, HttpConstants.UTF));
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpclient = httpClientBuilder.build();
        HttpResponse httpResponse = httpclient.execute(httpPut);
        return httpResponse.getStatusLine().getStatusCode() == 200;
    }

    public static String prototypeHttpsGetListProcessor() throws Exception {
        String sign =  BaiduSignUtils.generateSign(GET, StoreConstants.STORE_BUCKET_MASTER, "/", null , null , null );
        HttpGet httpGet = new HttpGet(sign);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpclient = httpClientBuilder.build();
        HttpResponse httpResponse = httpclient.execute(httpGet);
        InputStream inputStream =  httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }    catch (Exception e ) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String prototypeHttpsGetAFileProcessor(String fileKey) throws Exception {
        String sign =  BaiduSignUtils.generateSign(GET, StoreConstants.STORE_BUCKET_MASTER, fileKey, null , null , null );
        HttpGet httpGet = new HttpGet(sign);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpclient = httpClientBuilder.build();
        HttpResponse httpResponse = httpclient.execute(httpGet);
        InputStream inputStream =  httpResponse.getEntity().getContent();
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        }    catch (Exception e ) {
            e.printStackTrace();
        }
        return sb.toString();
    }




       //for test
        public static void main (String[] args) {
        try {
//            prototypeConnector() ;
            System.out.println(prototypeHttpsPutProcessor("/test1" , "asda11sdasd"));
            System.out.println(prototypeHttpsGetAFileProcessor("/test1"));
            System.out.println(prototypeHttpsGetListProcessor());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Override
    public HttpTokenResult getAccessToken() throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String deleteAFile(String key) throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QuotaResult getQuota(String accessToken) throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String pushAFile(String fileContent) throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAFile(String key) throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * the method is applicable for once(root) level http response json
     * @param clazz
     * @param json
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends BasicHttpResult> T covertJson2XResult(Class<T> clazz , String json) throws Exception {
        T httpResult = clazz.newInstance();
        JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(json);
        Method[] methods = clazz.getMethods();
        for (Method aMethod : methods) {
            if (aMethod.getName().startsWith("set")) {
                try {
                    Object obj = jsonObject.get(CamelCaseUtil.toUnderScoreCase(aMethod.getName().substring(3)).toLowerCase());
                    if (obj == null) continue;
                    aMethod.invoke(httpResult , obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(e);
                }

            }
        }
        if (httpResult.getError() != null) {
            httpResult.setSuccess(false);
        }  else {
            httpResult.setSuccess(true);
        }
        return httpResult;
    }

}
