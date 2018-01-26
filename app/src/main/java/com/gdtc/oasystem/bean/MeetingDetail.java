package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-16.
 */

public class MeetingDetail implements Serializable{


    /**
     * results : [{"title":"bbbbbbbbbb","content":"bbbbbbbbbb","time":"2018-01-12 08:30:00.0到2018-01-12 10:30:00.0","place":"0909","host":"bbbbbbbb","Explain":"bbbbbbbbbb","dept":"信息中心运维部/信息中心运维部"}]
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
         * title : bbbbbbbbbb
         * content : bbbbbbbbbb
         * time : 2018-01-12 08:30:00.0到2018-01-12 10:30:00.0
         * place : 0909
         * host : bbbbbbbb
         * Explain : bbbbbbbbbb
         * dept : 信息中心运维部/信息中心运维部
         */

        private String title;
        private String content;
        private String time;
        private String place;
        private String host;
        private String Explain;
        private String dept;
        private String user;
        private String flowsort;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getExplain() {
            return Explain;
        }

        public void setExplain(String Explain) {
            this.Explain = Explain;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }
    }
}
