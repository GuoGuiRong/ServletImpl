package com.ggr.utils;

/**
 * Created by GuiRunning on 2017/5/11.
 */
public enum ContentTypeEnum {
    HTML("text/html"),
    PLAIN("text/plain"),
    PNG("image/png"),
    JPG("image/jpg"),
    CSS("text/css"),
    GIF("image/gif");

    private String value;
    ContentTypeEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public static void main(String[] args){
        System.out.print(ContentTypeEnum.HTML.value);
    }
}
