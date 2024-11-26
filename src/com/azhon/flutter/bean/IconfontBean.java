package com.azhon.flutter.bean;

import com.azhon.flutter.common.Constants;

import java.io.File;

/**
 * createDate: 2022/11/23 on 16:38
 * desc:
 *
 * @author azhon
 */


public class IconfontBean {
    private String family;
    private String ttfPath;

    public IconfontBean(String family, String ttfPath) {
        this.family = family;
        this.ttfPath = ttfPath;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getTtfPath() {
        return ttfPath;
    }

    public void setTtfPath(String ttfPath) {
        this.ttfPath = ttfPath;
    }

    public String getTtfJsonPath() {
        return ttfPath.substring(0, ttfPath.lastIndexOf(File.separator)) + File.separator + Constants.ICONFONT_JSON;
    }
}

