package com.ggr.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by GuiRunning on 2017/5/12.
 */
public class ContentTypeUtil {
    //用来存放配置信息
    private static Properties contentTypeConfig=new Properties();

    static{
        String appPath = ServerConfigUtil.getConfig("appPath");
        //静态信息初始化
        try {
            contentTypeConfig.load(new FileInputStream(appPath+"/mime.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getConfig(String name){
        return contentTypeConfig.getProperty(name);
    }

    public static void main(String[] args){
        System.out.print(ContentTypeUtil.getConfig("png"));
    }
}
