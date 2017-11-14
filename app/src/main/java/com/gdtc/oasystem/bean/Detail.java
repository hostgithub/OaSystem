package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-20.
 */

public class Detail implements Serializable {

    private boolean success;
    private List<ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String title;
        public String source;
        public String content;
        public String addTime;
    }
}
