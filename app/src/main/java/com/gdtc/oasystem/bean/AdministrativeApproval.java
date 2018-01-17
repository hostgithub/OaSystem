package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-16.
 */

public class AdministrativeApproval implements Serializable{


    /**
     * results : [{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","senderTime":"2018-01-10 14:03:13.457"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"杨柳","senderTime":"2017-12-06 10:41:40.75"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"杨柳","senderTime":"2017-11-08 11:06:40.333"},{"title":"工作调整通知书（存根）(信息中心运维部-邢纪文)","typeAdvice":"查阅","sender":"邢纪文","senderTime":"2017-11-02 19:36:57.99"},{"title":"审判法庭使用通知单（民事行政类案件）(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","senderTime":"2017-10-31 19:20:46.937"},{"title":"更改案件信息审批表(信息中心运维部-王天鹏)","typeAdvice":"运维人员","sender":"井晓鑫","senderTime":"2017-10-27 15:24:31.36"},{"title":"信息化设备耗材申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","senderTime":"2017-10-27 11:08:00.0"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","senderTime":"2017-10-24 09:32:10.077"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","senderTime":"2017-10-12 10:27:57.347"},{"title":"省法院机关请假单(信息中心运维部-王天鹏)(销假申请单)","typeAdvice":"办理","sender":"邢纪文","senderTime":"2017-10-11 16:32:47.413"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"库管员","sender":"王天鹏","senderTime":"2017-10-11 14:47:29.867"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","senderTime":"2017-10-10 19:18:55.23"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","senderTime":"2017-10-10 19:16:14.677"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","senderTime":"2017-10-10 19:09:40.387"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"行装处意见","sender":"王天鹏","senderTime":"2017-10-10 19:07:02.56"}]
     * success : true
     * count : 27
     */

    private String success;
    private String count;
    private List<ResultsBean> results;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * title : 办公用品申领单(信息中心运维部-王天鹏)
         * typeAdvice : 办理
         * sender : 邢纪文
         * senderTime : 2018-01-10 14:03:13.457
         */

        private String title;
        private String typeAdvice;
        private String sender;
        private String senderTime;

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

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSenderTime() {
            return senderTime;
        }

        public void setSenderTime(String senderTime) {
            this.senderTime = senderTime;
        }
    }
}
