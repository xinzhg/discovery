package com.github.conanzhangxin.discovery.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.dataresolver.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Demo {
	public void execute(@Param("param") String param, Context context, TurbineRunData rundata,HttpServletResponse response, Navigator navigator, HttpServletRequest request) throws Exception {
		context.put("param",param);
		}

}
