package com.yc.fresh.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.fresh.util.StringUtil;

/**
 * 当应用程序启动的时候，自动创建文件存放的目录
 * 源辰信息
 * @author navy
 * @date 2019年12月1日
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    public ApplicationListener() {
   
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
   
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	String path = "pics";
    	
    	String temp = sce.getServletContext().getInitParameter("uploadPath");
    	if (!StringUtil.checkNull(temp)) { // 使用用户指定了上传路径
    		path = temp;
    	}
    	
    	// 判断该路径是否存在
    	String basePath = sce.getServletContext().getRealPath("/"); // 到了当前项目下
    	File fl = new File(basePath, "../" + path); // 我们要跳到webapps下创建
    	
    	if (!fl.exists()) {
    		fl.mkdirs();
    	}
    }
}
