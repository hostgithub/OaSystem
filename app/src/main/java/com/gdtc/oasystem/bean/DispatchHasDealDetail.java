package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-19.
 */

public class DispatchHasDealDetail implements Serializable{


    /**
     * results : [{"type_advice":"","sort_num":"20151220144847sgy","isBianHao":"null","pdfPath":"null","formid":"1222201531543890PM8452","formflow":"20151220144847sgy","flowsort":"120520170817026PM56104","file_source_id":"120520170817026PM56104"}]
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
         * type_advice :
         * sort_num : 20151220144847sgy
         * isBianHao : null
         * pdfPath : null
         * formid : 1222201531543890PM8452
         * formflow : 20151220144847sgy
         * flowsort : 120520170817026PM56104
         * file_source_id : 120520170817026PM56104
         */

        private String type_advice;
        private String sort_num;
        private String isBianHao;
        private String pdfPath;
        private String formid;
        private String formflow;
        private String flowsort;
        private String file_source_id;

        public String getType_advice() {
            return type_advice;
        }

        public void setType_advice(String type_advice) {
            this.type_advice = type_advice;
        }

        public String getSort_num() {
            return sort_num;
        }

        public void setSort_num(String sort_num) {
            this.sort_num = sort_num;
        }

        public String getIsBianHao() {
            return isBianHao;
        }

        public void setIsBianHao(String isBianHao) {
            this.isBianHao = isBianHao;
        }

        public String getPdfPath() {
            return pdfPath;
        }

        public void setPdfPath(String pdfPath) {
            this.pdfPath = pdfPath;
        }

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

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }
    }
}
