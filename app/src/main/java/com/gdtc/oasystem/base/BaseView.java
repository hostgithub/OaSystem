package com.gdtc.oasystem.base;

/**
 * Created by wangjiawei on 2017/7/21.
 * mvp基础View
 */

public interface BaseView {
    void showLoading();
    void stopLoading();
    void showErrorTip(String msg);
}
