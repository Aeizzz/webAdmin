package com.tepusoft.common.utils;

import com.ckfinder.connector.ServletContextFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by Admin on 2017/3/21.
 * 获取Bean
 */
public class SpringContextUtil{
    static Logger logger = Logger.getLogger(SpringContextUtil.class);
    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name){
        try {
            ServletContext sc = ServletContextFactory.getServletContext();
            ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
            return ap.getBean(name);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("", e);
        }
        return null;
    }
}
