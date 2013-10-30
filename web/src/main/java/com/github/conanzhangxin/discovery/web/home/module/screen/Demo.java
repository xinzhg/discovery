package com.github.conanzhangxin.discovery.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.github.conanzhangxin.discovery.common.utils.httpClient.impl.HttpClientImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Demo {
	public void execute(@Param("act") String act, @Param("filename") String filename , @Param("filecontent") String filecontent , Context context, TurbineRunData rundata,HttpServletResponse response, Navigator navigator, HttpServletRequest request) throws Exception {
		context.put("act",act);
        if (StringUtils.isBlank(act)) {
            return;
        } else {
            if (act.equals("put")) {
                if (!filename.startsWith("/")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("/").append(filename);
                    filename = stringBuilder.toString();
                }
                if (HttpClientImpl.prototypeHttpsPutProcessor(filename , filecontent)) {
                    context.put("msg" , "put a file success");
                    return;
                } else {
                    context.put("msg" , "put a file fail");
                    return;
                }
            } else if (act.equals("getafile")) {
                    if (!filename.startsWith("/")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("/").append(filename);
                        filename = stringBuilder.toString();
                    }
                    context.put("msg" , "a file content");
                    context.put("filename", filename);
                    context.put("filecontent"  , HttpClientImpl.prototypeHttpsGetAFileProcessor(filename));
                    return;
            } else if (act.equals("getlist")) {
                    context.put("msg" , "root level folder file list");
                    JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(HttpClientImpl.prototypeHttpsGetListProcessor());
                    JSONArray jsonArray = (JSONArray)jsonObject.get("object_list");
                    List<Map<String,String>> list = new ArrayList<Map<String,String>>();
                    for (Object one : jsonArray.toArray()) {
                        Map<String,String> result = new HashMap<String, String>();
                        result.put("filename",(String) ((JSONObject) one).get("object"));
                        result.put("filecontent",HttpClientImpl.prototypeHttpsGetAFileProcessor(result.get("filename")));
                        list.add(result);
                    }
                    context.put("folder" , list);
                    return;
            }
        }

    }

}
