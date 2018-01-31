package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-31.
 */

public class ShouWenDbDetail implements Serializable{


    /**
     * results : [{"formid":"113201625309620PM8452","formflow":"20151221133011sgy","sort_num":"20151221133011sgy","flowsort":"120420170424026PM56104","type_advice":"转办文","file_source_id":"120420170423007PM56736","userQc":"王天鹏"}]
     * htmls : 爱神的箭卡萨丁看见啊
     * success : true
     */

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
        /**
         * formid : 113201625309620PM8452
         * formflow : 20151221133011sgy
         * sort_num : 20151221133011sgy
         * flowsort : 120420170424026PM56104
         * type_advice : 转办文
         * file_source_id : 120420170423007PM56736
         * userQc : 王天鹏
         */

        private String formid;
        private String formflow;
        private String sort_num;
        private String flowsort;
        private String type_advice;
        private String file_source_id;
        private String userQc;
        private String htmls;

        public String getFormid() {
            return formid;
        }

        public void setFormid(String formid) {
            this.formid = formid;
        }

        public String getFormflow() {
            return formflow;
        }

        public void setFormflow(String formflow) {
            this.formflow = formflow;
        }

        public String getSort_num() {
            return sort_num;
        }

        public void setSort_num(String sort_num) {
            this.sort_num = sort_num;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }

        public String getType_advice() {
            return type_advice;
        }

        public void setType_advice(String type_advice) {
            this.type_advice = type_advice;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }

        public String getUserQc() {
            return userQc;
        }

        public void setUserQc(String userQc) {
            this.userQc = userQc;
        }

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String htmls) {
            this.htmls = htmls;
        }
    }
}
