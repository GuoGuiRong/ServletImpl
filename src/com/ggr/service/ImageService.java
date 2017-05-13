package com.ggr.service;

import com.ggr.iterface.Request;
import com.ggr.iterface.Response;
import com.ggr.iterface.Servlet;

/**
 * Created by GuiRunning on 2017/5/13.
 */
public class ImageService implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.setContentType("png");
        response.setCharSet("utf-8");
        response.response("ServletImpl/webapps/image/header.png");
    }
}
