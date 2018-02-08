package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-12.
 */

public class DispatchWaitDeal implements Serializable{

    /**
     * results : [{"_id":"8119","title":"wtp测试推送","sender":"王天鹏","jieanTime":"2017-12-06 09:03:29"},{"_id":"8117","title":"xj测试1205","sender":"王天鹏","jieanTime":"2017-12-05 18:39:31"},{"_id":"8111","title":"xj测试1205","sender":"王天鹏","jieanTime":"2017-12-05 13:16:32"},{"_id":"8085","title":"xj测试1203","sender":"王天鹏","jieanTime":"2017-12-03 17:19:22"},{"_id":"8058","title":"xj测试1129","sender":"王天鹏","jieanTime":"2017-11-29 16:10:54"},{"_id":"7928","title":"测试11-18-0","sender":"井晓鑫","jieanTime":"2017-11-18 20:20:53"},{"_id":"7926","title":"xj测试1118-1","sender":"邢纪文","jieanTime":"2017-11-18 18:54:39"},{"_id":"7925","title":"xj测试1118","sender":"高法运维_周永钱","jieanTime":"2017-11-18 18:40:24"},{"_id":"7703","title":"xj测试1107","sender":"邢纪文","jieanTime":"2017-11-07 16:35:27"},{"_id":"7612","title":"测试wtp","sender":"邢纪文","jieanTime":"2017-10-31 19:33:18"},{"_id":"7607","title":"测试wtp123","sender":"邢纪文","jieanTime":"2017-10-31 14:33:35"},{"_id":"7276","title":"测试盖章10-23-1","sender":"王天鹏","jieanTime":"2017-10-23 11:51:54"},{"_id":"7256","title":"wtp测试删除1019","sender":"邢纪文","jieanTime":"2017-10-19 16:39:47"},{"_id":"6893","title":"测试电子签章wtp9-15-7","sender":"王天鹏","jieanTime":"2017-09-15 14:35:23"},{"_id":"6892","title":"测试电子签章wtp9-15-6","sender":"王天鹏","jieanTime":"2017-09-15 14:29:05"}]
     * success : true
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
         * _id : 8119
         * title : wtp测试推送
         * sender : 王天鹏
         * jieanTime : 2017-12-06 09:03:29
         */

        private String _id;
        private String sign;
        private String deptunit;
        private String title;
        private String sender;
        private String senderTime;

        private String wordfileid;
        private String formid;
        private String flowsort;
        private String fileSourceId;
        private String advice;
        private String typeAdvice;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public void setSenderTime(String jieanTime) {
            this.senderTime = jieanTime;
        }


        public String getWordfileid() {
            return wordfileid;
        }

        public void setWordfileid(String wordfileid) {
            this.wordfileid = wordfileid;
        }

        public String getFormid() {
            return formid;
        }

        public void setFormid(String formid) {
            this.formid = formid;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }

        public String getFileSourceId() {
            return fileSourceId;
        }

        public void setFileSourceId(String fileSourceId) {
            this.fileSourceId = fileSourceId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getDeptunit() {
            return deptunit;
        }

        public void setDeptunit(String deptunit) {
            this.deptunit = deptunit;
        }

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getTypeAdvice() {
            return typeAdvice;
        }

        public void setTypeAdvice(String typeAdvice) {
            this.typeAdvice = typeAdvice;
        }
    }
}
