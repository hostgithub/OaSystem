package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-19.
 */

public class DispatchHasDealDetail implements Serializable{


    private String success;
    private List<ResultsBean> results;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{

        private String htmls;
        private String file_source_id;
        private String wordFileId;

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String html) {
            this.htmls = html;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }

        public String getWordFileId() {
            return wordFileId;
        }

        public void setWordFileId(String wordFileId) {
            this.wordFileId = wordFileId;
        }
    }
}
