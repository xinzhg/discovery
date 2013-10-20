package com.github.conanzhangxin.discovery.common.utils.httpClient.impl;

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


    private static String prototypeProcessor(String reqHttpMethod , String reqHost , String reqPath , List<NameValuePair> reqDataList) throws  Exception{
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new RequestContent())
                .add(new RequestTargetHost())
                .add(new RequestConnControl())
                .add(new RequestUserAgent("Test/1.1"))
                .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost(reqHost , 80 );
        coreContext.setTargetHost(host);
        DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
        ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

        try {

            HttpEntity[] requestBodies = {new UrlEncodedFormEntity(reqDataList , HTTP.UTF_8)};

            if (!conn.isOpen()) {
                Socket socket = new Socket(host.getHostName(), host.getPort());
                conn.bind(socket);
            }
            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(reqHttpMethod ,
                    reqPath );
            request.setEntity(requestBodies[0]);
            System.out.println("Request URI: " + request.getRequestLine().getUri());

            httpexecutor.preProcess(request, httpproc, coreContext);
            HttpResponse response = httpexecutor.execute(request, conn, coreContext);
            httpexecutor.postProcess(response, httpproc, coreContext);

            System.out.println("Response: " + response.getStatusLine());

            String tmpResp = EntityUtils.toString(response.getEntity());

            System.out.println(tmpResp);

            if (!connStrategy.keepAlive(response, coreContext)) {
                conn.close();
            } else {
                System.out.println("Connection kept alive...");
            }

            return tmpResp;

        } catch (Exception e) {
            throw new DiscoveryException(e);
        } finally {
            conn.close();
        }
    }


    public static void initSSLContext(HttpClientBuilder httpClientBuilder) {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } }, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sc , new X509HostnameVerifier(){
                public boolean verify(String string,SSLSession ssls) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[] {};
                }
            } );

            httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
            httpClientBuilder.setSslcontext(sc);
            httpClientBuilder.setSchemePortResolver(DefaultSchemePortResolver.INSTANCE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String prototypeHttpsProcessor(String reqHttpMethod , String reqHost , String reqPath , List<NameValuePair> reqDataList) throws Exception {

        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
//        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        initSSLContext(httpClientBuilder);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://").append(reqHost).append(reqPath).append("?");
        for (NameValuePair nameValuePair : reqDataList) {
            stringBuilder.append(nameValuePair.getName()).append("=").append(nameValuePair.getValue()).append("&");
        }
        HttpGet httpget = new HttpGet(stringBuilder.toString());
        HttpClient httpclient = httpClientBuilder.build();
        HttpEntity[] requestBodies = {new UrlEncodedFormEntity(reqDataList , HTTP.UTF_8)};
        BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(reqHttpMethod ,
                reqPath );
        request.setEntity(requestBodies[0]);

        HttpHost httpHost = new HttpHost(reqHost , 443 , "https");

        HttpResponse response = httpclient.execute(httpget);
        String tmpResp = EntityUtils.toString(response.getEntity());
        System.out.println(tmpResp);
        return   tmpResp;
    }

    public static String prototypeHttpsPutProcessor(String reqHost , String reqPath , List<NameValuePair> reqDataList) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://bcs.duapp.com/discoverymaster/%2Fdiscoverymaster?sign=MBO:1775424a7fa49bcb02c88d62d76301f7:Q0iLKNWip4kP6dREi%2BAulcamTsY%3D");
        HttpPut httpPut = new HttpPut(stringBuilder.toString());
        httpPut.setEntity(new FileEntity(new File(Thread.currentThread().getContextClassLoader().getResource("test.properties").toURI())));
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpclient = httpClientBuilder.build();
        HttpResponse httpResponse = httpclient.execute(httpPut);
        return httpResponse.getEntity().getContent().toString();
    }

    public static String prototypeHttpsGetListProcessor(String reqHost , String reqPath , List<NameValuePair> reqDataList) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://bcs.duapp.com/discoverymaster/%2Fdiscoverymaster?sign=MBO:1775424a7fa49bcb02c88d62d76301f7:mT3rX0y38BEJb06BIj1DuqmL%2F%2B8%3D"+"&start=0&limit=100");
        HttpGet httpGet = new HttpGet(stringBuilder.toString());
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient httpclient = httpClientBuilder.build();
        HttpResponse httpResponse = httpclient.execute(httpGet);
        InputStream inputStream =  httpResponse.getEntity().getContent();
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
            prototypeHttpsPutProcessor(null , null ,null);
            prototypeHttpsGetListProcessor(null,null,null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


    @Override
    public String deleteAFile(String key) throws DiscoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QuotaResult getQuota(String accessToken) throws DiscoveryException {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("method","info"));
        data.add(new BasicNameValuePair("access_token",accessToken));
        try {
            String json = prototypeProcessor(POST, BAIDU_OPENAPI_HOST, BAIDU_ACCESS_TOKEN_REQ_PATH, data);
            return covertJson2XResult(QuotaResult.class , json);
        } catch (Exception e) {
            throw new DiscoveryException(e);
        }
    }

    public static QuotaResult getQuota1(String accessToken) throws DiscoveryException {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("method","info"));
        data.add(new BasicNameValuePair("access_token",accessToken));
        try {
            String json = prototypeHttpsProcessor(GET, BAIDU_PCS_HOST, BAIDU_APPLICATION_QUOTA_REQ_PATH , data);
            return covertJson2XResult(QuotaResult.class , json);
        } catch (Exception e) {
            throw new DiscoveryException(e);
        }
    }

    @Deprecated
    public static HttpTokenResult getAccessToken1() throws DiscoveryException {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("grant_type","client_credentials"));
        data.add(new BasicNameValuePair("client_id",BAIDU_APPLICATION_CLIENT_ID));
        data.add(new BasicNameValuePair("client_secret",BAIDU_APPLICATION_CLIENT_SECRET));
        data.add(new BasicNameValuePair("scope","netdisk"));
        data.add(new BasicNameValuePair("response_type", "code"));
        data.add(new BasicNameValuePair("redirect_uri", "oob"));


        try {
            String json = prototypeHttpsProcessor(POST, BAIDU_OPENAPI_HOST, BAIDU_ACCESS_TOKEN_REQ_PATH, data);
            return covertJson2XResult(HttpTokenResult.class , json);
        } catch (Exception e) {
            throw new DiscoveryException(e);
        }
    }

    @Override
    public HttpTokenResult getAccessToken() throws DiscoveryException {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("grant_type","client_credentials"));
        data.add(new BasicNameValuePair("client_id",BAIDU_APPLICATION_CLIENT_ID));
        data.add(new BasicNameValuePair("client_secret",BAIDU_APPLICATION_CLIENT_SECRET));
        try {
            String json = prototypeProcessor(POST, BAIDU_OPENAPI_HOST, BAIDU_ACCESS_TOKEN_REQ_PATH, data);
            return covertJson2XResult(HttpTokenResult.class , json);
        } catch (Exception e) {
            throw new DiscoveryException(e);
        }
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
