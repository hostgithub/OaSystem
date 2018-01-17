package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-16.
 */

public class MeetingHandle implements Serializable{


    /**
     * results : [{"_id":"5974","flowsort":"011220181001006AM56104","title":"bbbbbbbbbb","sender":"2018-01-12 08:30:00.0","senderTime":"2018-01-12 10:30:00.0"}]
     * success : true
     * count : 1
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
         * _id : 5974
         * flowsort : 011220181001006AM56104
         * title : bbbbbbbbbb
         * sender : 2018-01-12 08:30:00.0
         * senderTime : 2018-01-12 10:30:00.0
         */

        private String _id;
        private String flowsort;
        private String title;
        private String sender;
        private String senderTime;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
