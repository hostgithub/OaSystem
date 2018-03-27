package com.gdtc.oasystem.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-26.
 */

public class ResponseBean implements Serializable{


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


        private String information;
        private String userid;
        private String depetName;
        private String company;
        private String personnelId;
        private String depetUnit;
        private String pathdata;
        private String user_department;
        private String user_department_big;
        private String right_jd_f;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public String getUserName() {
            return userid;
        }

        public void setUserName(String userName) {
            this.userid = userName;
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

        public String getPathdata() {
            return pathdata;
        }

        public void setPathdata(String pathdata) {
            this.pathdata = pathdata;
        }

        public String getUser_department() {
            return user_department;
        }

        public void setUser_department(String user_department) {
            this.user_department = user_department;
        }

        public String getUser_department_big() {
            return user_department_big;
        }

        public void setUser_department_big(String user_department_big) {
            this.user_department_big = user_department_big;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getRight_jd_f() {
            return right_jd_f;
        }

        public void setRight_jd_f(String right_jd_f) {
            this.right_jd_f = right_jd_f;
        }
    }
}
