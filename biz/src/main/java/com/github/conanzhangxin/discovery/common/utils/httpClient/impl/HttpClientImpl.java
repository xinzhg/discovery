package com.github.conanzhangxin.discovery.common.utils.httpClient.impl;

import com.github.conanzhangxin.discovery.common.exception.DiscoveryException;
import com.github.conanzhangxin.discovery.common.utils.httpClient.HttpClient;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.github.conanzhangxin.discovery.common.constants.HttpConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-4
 * Time: ����9:40
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientImpl implements HttpClient {


    private static String prototypeProcessor(String reqHttpMethod , String reqHost , String reqPath , List<NameValuePair> reqDataList) throws  Exception{
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new RequestContent())
                .add(new RequestTargetHost())
                .add(new RequestConnControl())
                .add(new RequestUserAgent("Test/1.1"))
                .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost(reqHost , 80);
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

            System.out.println(EntityUtils.toString(response.getEntity()));

            if (!connStrategy.keepAlive(response, coreContext)) {
                conn.close();
            } else {
                System.out.println("Connection kept alive...");
            }

            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            throw new DiscoveryException(e);
        } finally {
            conn.close();
        }
    }

    public static void main (String[] args) {
        try {
//            prototypeConnector() ;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


    @Override
    public String deleteAFile(String key) throws DiscoveryException {
        prototypeConnector(POST , )

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getQuota(String accessToken) throws DiscoveryException {
        prototypeConnector()
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAccessToken() throws DiscoveryException {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("grant_type","client_credentials"));
        data.add(new BasicNameValuePair("client_id",BAIDU_APPLICATION_CLIENT_ID));
        data.add(new BasicNameValuePair("client_secret",BAIDU_APPLICATION_CLIENT_SECRET));
        try {
            String json = prototypeProcessor(POST, BAIDU_OPENAPI_HOST, BAIDU_ACCESS_TOKEN_REQ_PATH, data);
            JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(json);
            jsonObject.get()
        } catch (Exception e) {
            throw new DiscoveryException(e);
        }

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
}
