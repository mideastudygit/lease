package com.lease.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.lease.controller.ApiController;

/**
 * API引导式配置
 */
public class Config extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		PropKit.use("appConfig.properties");
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/api", ApiController.class);
	}

	public void configEngine(Engine me) {
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}
}
