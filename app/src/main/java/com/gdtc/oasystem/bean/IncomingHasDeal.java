package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-17.
 */

public class IncomingHasDeal implements Serializable{


    /**
     * results : [{"title":"测试电子签章wtp9-15-4","typeAdvice":"已领导批示","userSend":"王天鹏","sendTime":"2017-09-15 14:23:18.0"},{"title":"测试电子签章wtp9-15-4","typeAdvice":"已拟办意见","userSend":"王天鹏","sendTime":"2017-09-15 14:22:55.0"},{"title":"测试电子签章wtp9-15-4","typeAdvice":"已批办意见","userSend":"王天鹏","sendTime":"2017-09-15 14:22:36.0"},{"title":"测试电子签章wtp9-15-4","typeAdvice":"已转文","userSend":"王天鹏","sendTime":"2017-09-15 14:22:13.0"},{"title":"测试电子签章wtp9-15-4","typeAdvice":"已拟办意见","userSend":"王天鹏","sendTime":"2017-09-15 14:21:38.0"},{"title":"测试电子签章wtp9-15-4","typeAdvice":"已阅文意见","userSend":"王天鹏","sendTime":"2017-09-15 14:20:10.0"},{"title":"测试wtp9-6-0","typeAdvice":"已转文","userSend":"王天鹏","sendTime":"2017-09-06 09:58:40.0"},{"title":"测试wtp9-6-0","typeAdvice":"已阅文意见","userSend":"王天鹏","sendTime":"2017-09-06 09:58:15.0"},{"title":"测试wtp9-6-0","typeAdvice":"已批办意见","userSend":"王天鹏","sendTime":"2017-09-06 09:57:36.0"},{"title":"测试wtp9-6-0","typeAdvice":"已转办文","userSend":"王天鹏","sendTime":"2017-09-06 09:56:55.0"},{"title":"测试wtp9-6-0","typeAdvice":"已转办情况","userSend":"王天鹏","sendTime":"2017-09-06 09:56:34.0"},{"title":"测试wtp9-6-0","typeAdvice":"已拟办意见","userSend":"王天鹏","sendTime":"2017-09-06 09:55:59.0"},{"title":"测试wtp9-6-0","typeAdvice":"已阅文意见","userSend":"王天鹏","sendTime":"2017-09-06 09:53:50.0"}]
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
         * title : 测试电子签章wtp9-15-4
         * typeAdvice : 已领导批示
         * userSend : 王天鹏
         * sendTime : 2017-09-15 14:23:18.0
         */

        private String title;
        private String typeAdvice;
        private String userSend;
        private String sendTime;
        private String file_source_id;
        private String flowsort;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTypeAdvice() {
            return typeAdvice;
        }

        public void setTypeAdvice(String typeAdvice) {
            this.typeAdvice = typeAdvice;
        }

        public String getUserSend() {
            return userSend;
        }

        public void setUserSend(String userSend) {
            this.userSend = userSend;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }
    }
}
