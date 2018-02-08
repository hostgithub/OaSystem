package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-1-30.
 */

public class DetailDispatchdb implements Serializable{

    /**
     * results : [{"column1":"特提","column2":"立案二庭","column3":"","column6":"123","column75":"邢纪文","column76":"审核意见","column77":",56104,","column78":"2018-01-29 09:04:42","column79":"","file_source_id":"012920180904008AM56104","deptunit":"10021","flowsort":"null","type_advice_sa":"null","sign":"null","userid":"null","user_qcId1":"null","user_qc1":"null","jijian":"null","yffs":"123","title":"毕既宏测试文件","htmls":""}]
     * success  : true
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
         * column1 : 特提
         * column2 : 立案二庭
         * column3 :
         * column6 : 123
         * column75 : 邢纪文
         * column76 : 审核意见
         * column77 : ,56104,
         * column78 : 2018-01-29 09:04:42
         * column79 :
         * file_source_id : 012920180904008AM56104
         * deptunit : 10021
         * flowsort : null
         * type_advice_sa : null
         * sign : null
         * userid : null
         * user_qcId1 : null
         * user_qc1 : null
         * jijian : null
         * yffs : 123
         * title : 毕既宏测试文件
         * htmls :
         */

        private String column1;
        private String column2;
        private String column3;
        private String column6;
        private String column75;
        private String column76;
        private String column77;
        private String column78;
        private String column79;
        private String file_source_id;
        private String deptunit;
        private String flowsort;
        private String type_advice_sa;
        private String sign;
        private String userid;
        private String user_qcId1;
        private String user_qc1;
        private String jijian;
        private String yffs;
        private String title;
        private String htmls;

        public String getColumn1() {
            return column1;
        }

        public void setColumn1(String column1) {
            this.column1 = column1;
        }

        public String getColumn2() {
            return column2;
        }

        public void setColumn2(String column2) {
            this.column2 = column2;
        }

        public String getColumn3() {
            return column3;
        }

        public void setColumn3(String column3) {
            this.column3 = column3;
        }

        public String getColumn6() {
            return column6;
        }

        public void setColumn6(String column6) {
            this.column6 = column6;
        }

        public String getColumn75() {
            return column75;
        }

        public void setColumn75(String column75) {
            this.column75 = column75;
        }

        public String getColumn76() {
            return column76;
        }

        public void setColumn76(String column76) {
            this.column76 = column76;
        }

        public String getColumn77() {
            return column77;
        }

        public void setColumn77(String column77) {
            this.column77 = column77;
        }

        public String getColumn78() {
            return column78;
        }

        public void setColumn78(String column78) {
            this.column78 = column78;
        }

        public String getColumn79() {
            return column79;
        }

        public void setColumn79(String column79) {
            this.column79 = column79;
        }

        public String getFile_source_id() {
            return file_source_id;
        }

        public void setFile_source_id(String file_source_id) {
            this.file_source_id = file_source_id;
        }

        public String getDeptunit() {
            return deptunit;
        }

        public void setDeptunit(String deptunit) {
            this.deptunit = deptunit;
        }

        public String getFlowsort() {
            return flowsort;
        }

        public void setFlowsort(String flowsort) {
            this.flowsort = flowsort;
        }

        public String getType_advice_sa() {
            return type_advice_sa;
        }

        public void setType_advice_sa(String type_advice_sa) {
            this.type_advice_sa = type_advice_sa;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUser_qcId1() {
            return user_qcId1;
        }

        public void setUser_qcId1(String user_qcId1) {
            this.user_qcId1 = user_qcId1;
        }

        public String getUser_qc1() {
            return user_qc1;
        }

        public void setUser_qc1(String user_qc1) {
            this.user_qc1 = user_qc1;
        }

        public String getJijian() {
            return jijian;
        }

        public void setJijian(String jijian) {
            this.jijian = jijian;
        }

        public String getYffs() {
            return yffs;
        }

        public void setYffs(String yffs) {
            this.yffs = yffs;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHtmls() {
            return htmls;
        }

        public void setHtmls(String htmls) {
            this.htmls = htmls;
        }
    }
}
