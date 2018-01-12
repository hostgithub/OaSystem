package com.gdtc.oasystem.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-26.
 */

public class ResponseBean implements Serializable{


    /**
     * results : [{"information":"登陆成功!","userName":"王天鹏","depetName":"信息中心运维部","company":"黑龙江省高级人民法院"}]
     * success : true
     */

    @SerializedName("success")
    public String success;

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * information : 登陆成功!
         * userName : 王天鹏
         * depetName : 信息中心运维部
         * company : 黑龙江省高级人民法院
         */

        private String information;
        private String userName;
        private String depetName;
        private String company;
        private String personnelId;
        private String depetUnit;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDepetName() {
            return depetName;
        }

        public void setDepetName(String depetName) {
            this.depetName = depetName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPersonnelId() {
            return personnelId;
        }

        public void setPersonnelId(String personnelId) {
            this.personnelId = personnelId;
        }

        public String getDepetUnit() {
            return depetUnit;
        }

        public void setDepetUnit(String depetUnit) {
            this.depetUnit = depetUnit;
        }
    }
}
