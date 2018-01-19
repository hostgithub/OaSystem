package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-19.
 */

public class IncomingHasDealDetail implements Serializable{


    /**
     * results : [{"formid":"113201625309620PM8452","formflow":"20151221133011sgy","sort_num":"113201625309620PM8452","file_source_id":"091520170219028PM56736"}]
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
         * sort_num : 113201625309620PM8452
         * file_source_id : 091520170219028PM56736
         */

        private String formid;
        private String formflow;
        private String sort_num;
        private String file_source_id;

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

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }
    }
}
