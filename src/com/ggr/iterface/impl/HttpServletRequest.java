package com.ggr.iterface.impl;

import com.ggr.iterface.Request;
import com.ggr.utils.ServerConfigUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GuiRunning on 2017/5/12.
 */
public class HttpServletRequest implements Request {

    //字节流
    private InputStream inputStream;
    //路径名
    private String pathInfo;
    //方法名
    private String method;
    //协议名
    private  String protocol;
    //保存参数
    private String queryString;

    public HttpServletRequest(InputStream inputStream){
        this.inputStream=inputStream;
        parseRequest();
    }

    @Override
    public String toString() {
        return "HttpServletRequest{" +
                "inputStream=" + inputStream +
                ", pathInfo='" + pathInfo + '\'' +
                ", method='" + method + '\'' +
                ", protocol='" + protocol + '\'' +
                ", queryString='" + queryString + '\'' +
                ", params=" + params.toString() +
                '}';
    }

    /**
     * 自动装配参数列表
     */
    public void setParams() {
        if(this.queryString!=null){
            String[] temp = this.queryString.split("&");
            for(int i=0;i<temp.length;i++){
                String[] temp2 = temp[i].split("=");
                this.params.put(temp2[0],temp2[1]);
            }
        }
    }

    /**
     * 解析用户发送过来的请求
     */
    private void parseRequest(){
        BufferedReader bufferedReader;
        BufferedInputStream BufferedInputStream;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream, ServerConfigUtil.getConfig("charset")));
            String headline = bufferedReader.readLine();
            if(headline!=null&&!"".equals(headline)){
                String[] lines = headline.split(" ");
                if(lines.length<3){
                    throw new RuntimeException("请求行不合法！");
                }
                this.method = lines[0].toUpperCase();
                this.protocol = lines[2];
                if("GET".equals(this.method)){
                   // System.out.println(lines[1]);
                    int index = lines[1].indexOf("?");
                    //判断是否有请求参数
                    if(index!=-1) {
                        this.pathInfo = lines[1].substring(0, index);
                        this.queryString = lines[1].substring(index+1);
                    }else{
                        this.pathInfo = lines[1];
                    }
                }else if("POST".equals(this.method)){
                    pathInfo = lines[1];
                    String msg=null;
                    int length=0;//定义参数的自己长度
                    while(((msg=bufferedReader.readLine())!=null)&&!"".equals(msg)){
                       if(msg.toUpperCase().startsWith("CONTENT-LENGTH")){
                           String[] contentType = msg.split(":");
                           length = Integer.parseInt(contentType[1].trim());
                       }
                    }
                    /**
                     * 获取参数POST请求时直接在最后面请求体重携带数据的,当然首先判断是否有请求参数
                     */
                    if(length>0){
                        char[] b = new char[length];
                        bufferedReader.read(b);
                        this.queryString=new String(b);
                    }
                }
                setParams();//自动装配参数列表
                System.out.println(this.toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public HttpServletRequest(){};
    //参数列表
    private Map<String,String> params = new HashMap<>();

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String getPathInfo() {
        return this.pathInfo;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getParameter(String paramName) {
        return this.params.get(paramName);
    }

    @Override
    public String getQueryString(String paramName) {
        return this.queryString;
    }

    @Override
    public Map<String, String> getParameters() {
        return this.params;
    }
}
