package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-19.
 */

public class DispatchHasDealDetail implements Serializable{


    /**
     * results : [{"html":"     黑龙江省高级人民法院文件审批单   文件编号： 密级： 文件标题： wtp测试推送 审核意见： 签发意见： 审核意见输入框 签发意见输入框 主送单位： wtp测试推送 抄送单位： wtp测试推送 拟稿单位： 信息中心运维部 拟稿人： 王天鹏 校对人： wtp测试推送 黑龙江省高级人民法院办公室 印发 共2份   "}]
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
         * html :      黑龙江省高级人民法院文件审批单   文件编号： 密级： 文件标题： wtp测试推送 审核意见： 签发意见： 审核意见输入框 签发意见输入框 主送单位： wtp测试推送 抄送单位： wtp测试推送 拟稿单位： 信息中心运维部 拟稿人： 王天鹏 校对人： wtp测试推送 黑龙江省高级人民法院办公室 印发 共2份
         */

        private String htmls;
        private String file_source_id;

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String html) {
            this.htmls = html;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }
    }
}
