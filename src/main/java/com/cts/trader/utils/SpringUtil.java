package com.cts.trader.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description SpringBean工具类
 * @version 1.0.0
 **/
@Configuration
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过类型获取bean
     * @param clazz 类
     * @param <T>
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过bean名称获取bean
     * @param name 名称
     * @return bean
     */
    public static Object getBean(String name) {
        try {
            Object _restTemplate = getApplicationContext().getBean(name);
            return _restTemplate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过bean名称和类型获取bean
     * @param name 名称
     * @param clazz 类型
     * @param <T>
     * @return bean
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static void setApplicationContext1(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
