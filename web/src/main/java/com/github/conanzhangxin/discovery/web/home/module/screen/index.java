package com.github.conanzhangxin.web.home.module.screen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.dataresolver.Param;


public class index {
	public void execute(@Param("name") String name, Context context, TurbineRunData rundata,HttpServletResponse response, Navigator navigator, HttpServletRequest request) throws Exception {
		context.put("name",name);
		}

}
