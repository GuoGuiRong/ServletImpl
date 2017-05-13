package com.ggr.service;

import com.ggr.iterface.Request;
import com.ggr.iterface.Response;
import com.ggr.iterface.Servlet;
import com.ggr.iterface.impl.HttpServletRequest;
import com.ggr.iterface.impl.HttpServletResponse;
import com.ggr.utils.ContentTypeEnum;
import com.ggr.utils.ServletContainer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 请求处理线程
 */
public class RequestThread extends Thread {
    private Socket socket;
    public RequestThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        InputStream is=null;
        Request request =null;
        Response response =null;
        try {
            is=socket.getInputStream();
            //服务器一旦接受到请求，request就可以产生了
            request= new HttpServletRequest(is);
            String path = request.getPathInfo();
            System.out.println(path);
            Servlet servlet = ServletContainer.getServlet(path);
            response = new HttpServletResponse(this.socket.getOutputStream());
            if(servlet!=null){
                servlet.service(request,response);
            }else{
                //如果是静态资源的话放行，否则直接回写异常
                int index=path.indexOf(".");
                String prefix = null;
                if(index>-1){
                    //获得后缀
                    prefix = path.substring(index+1);
                    System.out.println(prefix);
                    //获得后缀，说明是静态资源

                    //设置响应的ContentType
                    response.setContentType(prefix);

                    //拼接静态文件地址
                    System.out.println("ServletImpl/webapps"+path);

                    response.response("ServletImpl/webapps"+path);

                    //加载到静态文件后,退出便可
                    return;
                }else {
                    OutputStream os = socket.getOutputStream();
                    //响应行
                    os.write("HTTP/1.1 404 EOOR \r\n".getBytes());
                    //响应头
                    os.write(ContentTypeEnum.PLAIN.getValue().getBytes());
                    //空行
                    os.write("\r\n".getBytes());
                    os.write("抱歉，未找到请求。。。".getBytes());
                }
                //ServletContainer.config();
                //ServletContainer.getServlet(path).service(request,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
