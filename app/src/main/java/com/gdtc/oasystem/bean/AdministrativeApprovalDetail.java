package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-31.
 */

public class AdministrativeApprovalDetail implements Serializable{


    /**
     * results : [{"formid":"201601081502118452","user_qc":"王天鹏","user_qc_id":"56736","flowsort":"011020180203013PM56104","file_source_id":"010520180932006PM56736","sort":"custom","type_advice":"鍔炵悊","usersend":"閭㈢邯鏂�","htmls":"撒打算打算打算大所大"}]
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
         * formid : 201601081502118452
         * user_qc : 王天鹏
         * user_qc_id : 56736
         * flowsort : 011020180203013PM56104
         * file_source_id : 010520180932006PM56736
         * sort : custom
         * type_advice : 鍔炵悊
         * usersend : 閭㈢邯鏂�
         * htmls : 撒打算打算打算大所大
         */

        private String formid;
        private String user_qc;
        private String user_qc_id;
        private String flowsort;
        private String file_source_id;
        private String sort;
        private String type_advice;
        private String usersend;
        private String htmls;

        public String getFormid() {
            return formid;
        }

        public void setFormid(String formid) {
            this.formid = formid;
        }

        public String getUser_qc() {
            return user_qc;
        }

        public void setUser_qc(String user_qc) {
            this.user_qc = user_qc;
        }

        public String getUser_qc_id() {
            return user_qc_id;
        }

        public void setUser_qc_id(String user_qc_id) {
            this.user_qc_id = user_qc_id;
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getType_advice() {
            return type_advice;
        }

        public void setType_advice(String type_advice) {
            this.type_advice = type_advice;
        }

        public String getUsersend() {
            return usersend;
        }

        public void setUsersend(String usersend) {
            this.usersend = usersend;
        }

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String htmls) {
            this.htmls = htmls;
        }
    }
}
