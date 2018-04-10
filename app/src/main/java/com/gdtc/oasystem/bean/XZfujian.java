package com.gdtc.oasystem.bean;

import java.io.Serializable;

/**
 * Created by wangjiawei on 2018-4-8.
 */

public class XZfujian implements Serializable{

    private String path;
    private String success;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
