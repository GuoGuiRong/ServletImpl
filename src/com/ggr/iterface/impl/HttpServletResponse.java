package com.ggr.iterface.impl;

import com.ggr.iterface.Response;
import com.ggr.utils.ContentTypeEnum;
import com.ggr.utils.ContentTypeUtil;

import java.io.*;

public class HttpServletResponse implements Response {

    //字节流
    private OutputStream outputStream;

    //响应时编码格式,默认utf-8
    private String charSet="utf-8";

    //响应体类型，默认text/html
    private String contentType="html";

    @Override
    public void setCharSet(String CharSet) {
        this.charSet = CharSet;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType=contentType;
    }

    private ContentTypeEnum contentTypeEnum = ContentTypeEnum.HTML;

    public HttpServletResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }
    @Override
    public OutputStream getOutputStream() {

        try {
            //响应行
            outputStream.write("HTTP/1.1 200 OK \r\n".getBytes());
            //响应头
            outputStream.write(("Content-Type: "+ ContentTypeUtil.getConfig(this.contentType)+"; charset="+charSet+" \r\n").getBytes());
            //空行
            outputStream.write("\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.outputStream;
    }

    @Override
    public OutputStreamWriter getOutputStreamWriter() {
        OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(this.getOutputStream());
        return OutputStreamWriter;
    }

    @Override
    public void response(String filepath){
        OutputStream outputStream = null;
        FileInputStream fis=null;
        try {
            outputStream =getOutputStream();
            fis = new FileInputStream(filepath);
            byte[] b=new byte[256];
            int i=-1;
            while((i=fis.read(b))!=-1){
                outputStream.write(b,0,i);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
