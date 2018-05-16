package com.gdtc.oasystem.presenter;

import com.gdtc.oasystem.base.BasePresenter;
import com.gdtc.oasystem.base.BaseView;
import com.gdtc.oasystem.bean.ApplicationEntity;

/**
 * Created by wangjiawei on 2017/7/18.
 */

public interface MainContract {
    interface View extends BaseView {
        void retureResult(String result);
        void retureUpdateResult(ApplicationEntity entity);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
        void update(ApplicationEntity entity);
    }
}
