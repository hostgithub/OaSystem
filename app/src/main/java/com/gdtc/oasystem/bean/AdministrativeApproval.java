package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-16.
 */

public class AdministrativeApproval implements Serializable{


    /**
     * results : [{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","isRead":"1","flowsort":"011020180203013PM56104","file_source_id":"010520180932006PM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2018-01-10 14:03:13.457"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"杨柳","isRead":"1","flowsort":"120620171041040AM7237","file_source_id":"120620171036014AM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-12-06 10:41:40.75"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"杨柳","isRead":"1","flowsort":"110820171106040AM7237","file_source_id":"110820171102023AM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-11-08 11:06:40.333"},{"title":"工作调整通知书（存根）(信息中心运维部-邢纪文)","typeAdvice":"查阅","sender":"邢纪文","isRead":"1","flowsort":"110220170736057PM561040","file_source_id":"110220170736057PM56104","location":"1","xjtype":"null","model_sf":"查阅","sort":"查阅","senderTime":"2017-11-02 19:36:57.99"},{"title":"审判法庭使用通知单（民事行政类案件）(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","isRead":"1","flowsort":"103120170720046PM56104","file_source_id":"103120170720023PM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-31 19:20:46.937"},{"title":"更改案件信息审批表(信息中心运维部-王天鹏)","typeAdvice":"运维人员","sender":"井晓鑫","isRead":"1","flowsort":"102720170324031PM56066","file_source_id":"102720170322046PM56736","location":"1","xjtype":"null","model_sf":"运维人员","sort":"运维人员","senderTime":"2017-10-27 15:24:31.36"},{"title":"信息化设备耗材申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","isRead":"1","flowsort":"C1710271108000000","file_source_id":"102720171108048AM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-27 11:08:00.0"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"邢纪文","isRead":"1","flowsort":"102420170932010AM56104","file_source_id":"102420170929048AM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-24 09:32:10.077"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","isRead":"1","flowsort":"101220171027057AM56736","file_source_id":"101220171026054AM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-12 10:27:57.347"},{"title":"省法院机关请假单(信息中心运维部-王天鹏)(销假申请单)","typeAdvice":"办理","sender":"邢纪文","isRead":"1","flowsort":"101120170432047PM56104","file_source_id":"101120170432032PM56736","location":"1","xjtype":"1","model_sf":"办理","sort":"办理","senderTime":"2017-10-11 16:32:47.413"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"库管员","sender":"王天鹏","isRead":"1","flowsort":"101120170247029PM567360","file_source_id":"101120170247029PM56736","location":"1","xjtype":"null","model_sf":"库管员","sort":"库管员","senderTime":"2017-10-11 14:47:29.867"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","isRead":"1","flowsort":"101020170718055PM56736","file_source_id":"101020170717035PM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-10 19:18:55.23"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","isRead":"1","flowsort":"101020170716014PM56736","file_source_id":"101020170714055PM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-10 19:16:14.677"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"办理","sender":"王天鹏","isRead":"1","flowsort":"101020170709040PM56736","file_source_id":"101020170705020PM56736","location":"1","xjtype":"null","model_sf":"办理","sort":"办理","senderTime":"2017-10-10 19:09:40.387"},{"title":"办公用品申领单(信息中心运维部-王天鹏)","typeAdvice":"行装处意见","sender":"王天鹏","isRead":"1","flowsort":"101020170707002PM567360","file_source_id":"101020170707002PM56736","location":"1","xjtype":"null","model_sf":"行装处意见","sort":"行装处意见","senderTime":"2017-10-10 19:07:02.56"}]
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

    public static class ResultsBean {
        /**
         * title : 办公用品申领单(信息中心运维部-王天鹏)
         * typeAdvice : 办理
         * userSend : 邢纪文
         * isRead : 1
         * flowsort : 011020180203013PM56104
         * file_source_id : 010520180932006PM56736
         * location : 1
         * xjtype : null
         * model_sf : 办理
         * sort : 办理
         * senderTime : 2018-01-10 14:03:13.457
         */

        private String title;
        private String typeAdvice;
        private String userSend;
        private String isRead;
        private String flowsort;
        private String file_source_id;
        private String location;
        private String xjtype;
        private String model_sf;
        private String sort;
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
            return userSend;
        }

        public void setSender(String userSend) {
            this.userSend = userSend;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getXjtype() {
            return xjtype;
        }

        public void setXjtype(String xjtype) {
            this.xjtype = xjtype;
        }

        public String getModel_sf() {
            return model_sf;
        }

        public void setModel_sf(String model_sf) {
            this.model_sf = model_sf;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getSenderTime() {
            return senderTime;
        }

        public void setSenderTime(String senderTime) {
            this.senderTime = senderTime;
        }
    }
}
