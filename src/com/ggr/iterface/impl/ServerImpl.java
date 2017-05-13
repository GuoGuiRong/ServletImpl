package com.ggr.iterface.impl;

import com.ggr.iterface.Server;
import com.ggr.service.RequestThread;
import com.ggr.utils.ServerConfigUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by GuiRunning on 2017/5/11.
 */
public class ServerImpl implements Server{

    private boolean flag=true;

    private ServerSocket serverSocket=null;

    private Socket socket=null;


    public ServerImpl(){}
    /**
     * 重写start
     */
    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(ServerConfigUtil.getConfig("port")));
            while (true){
                socket = serverSocket.accept();
                new RequestThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServerImpl().start();
    }
}
