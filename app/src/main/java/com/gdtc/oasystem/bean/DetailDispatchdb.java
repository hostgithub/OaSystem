package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-30.
 */

public class DetailDispatchdb implements Serializable{
    private String success;
    private List<DetailDispatchdb.ResultsBean> results;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DetailDispatchdb.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<DetailDispatchdb.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        private String htmls;

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String html) {
            this.htmls = html;
        }
    }
}
