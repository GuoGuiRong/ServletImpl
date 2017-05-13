package com.ggr.iterface;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by GuiRunning on 2017/5/12.
 */
public interface Response {
    public OutputStream getOutputStream();
    public OutputStreamWriter getOutputStreamWriter();
    public void response(String filepath);
    public void setCharSet(String CharSet);
    public void setContentType(String contentType);
}
