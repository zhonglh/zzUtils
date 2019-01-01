package com.zz.bms.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhonglihong
 * @date 2016年3月7日 上午11:39:12
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;


	@Override
	public void setApplicationContext(ApplicationContext ctx)	throws BeansException {
		ApplicationContextUtil.context = ctx;
	}

	public static ApplicationContext getContext() {
		return context;
	}









}
