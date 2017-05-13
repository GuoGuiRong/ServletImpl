package com.ggr.utils;

import com.ggr.iterface.Servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ServletContainer {
    private static Properties webConfig = new Properties();
    private static Map<String,Servlet> container = new HashMap<>();

    public static void config(){
        String appPath = ServerConfigUtil.getConfig("appPath");
        try {
            webConfig.load(new FileInputStream(appPath+"/web.properties"));
            Set<Object> set = webConfig.keySet();
            for(Object url:set){
                String className = (String)webConfig.get(url);
                Object object = Class.forName(className).newInstance();
                if(object instanceof Servlet){
                    container.put((String)url, (Servlet) object);
                }else{
                    throw new RuntimeException("NO Servlet find");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static{
        config();
    }
    public static Servlet getServlet(String pathInfo){
        return container.get(pathInfo);
    }

    public static void main(String[] args){
        System.out.println(getServlet("/test.do"));
    }
}