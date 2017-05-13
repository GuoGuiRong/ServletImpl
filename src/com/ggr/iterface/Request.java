package com.ggr.iterface;

import java.util.Map;

/**
 * Created by GuiRunning on 2017/5/12.
 */
public interface Request {
    public String getPathInfo();
    public String getMethod();
    public String getProtocol();
    public String getParameter(String paramName);
    public String getQueryString(String paramName);
    public Map<String,String> getParameters();
}
