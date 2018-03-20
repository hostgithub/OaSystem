package com.gdtc.oasystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-3-14.
 */

public class DaipiWork implements Serializable{


    /**
     * results : [{"type":"发文","title":"[发文]\t123\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=031220180226048PM56736&flowsort=031220180228042PM567360"},{"type":"发文","title":"[发文]\t黑龙江省高级人民法院文件审批单 测试-2\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=020820180517008PM56736&flowsort=020820180522047PMnull0"},{"type":"发文","title":"[发文]\t黑龙江省高级人民法院文件审批单\t测试文件-1\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=020820180509040PM56736&flowsort=020820180511059PMnull0"},{"type":"发文","title":"[发文]\t2-8测试文件-1\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=020820180957025AM56736&flowsort=020820180449058PMnull0"},{"type":"发文","title":"[发文]\t2-8测试文件-1\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=020820180956014AM56736&flowsort=020820181041001AM561040"},{"type":"发文","title":"[发文]\txj测试1205 ","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=120520170116013PM56736&flowsort=020720180315013PM561040"},{"type":"发文","title":"[发文]\txj测试1205 ","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=120520170639011PM56736&flowsort=020720180304034PM561040"},{"type":"发文","title":"[发文]\t测试wtp123 ","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=103120170230041PM56736&flowsort=020720180241006PM561040"},{"type":"发文","title":"[发文]\t毕既宏测试-2\t","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=020720181005028AM56736&flowsort=020720181010000AM561040"},{"type":"发文","title":"[发文]xj测试1212","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=121220170359057PM56736&flowsort=011020180205040PM561040"},{"type":"发文","title":"[发文]xj测试1107","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=110720170434038PM56104&flowsort=110720170434038PM561040"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-7","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170233022PM56736&flowsort=091520170235023PM567360"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-6","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170226014PM56736&flowsort=091520170229005PM567360"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-1","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170213053PM56736&flowsort=091520170217028PM567360"},{"type":"发文","title":"[发文]测试电子签章915-5","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170159029PM56736&flowsort=091520170209003PM567360"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-3","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170918026AM56736&flowsort=091520170950001AM567360"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-2","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170916028AM56736&flowsort=091520170917006AM567360"},{"type":"发文","title":"[发文]测试电子签章wtp9-15-0","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=091520170907026AM56736&flowsort=091520170911037AM567360"},{"type":"发文","title":"[发文]测试wtp9-8-0","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090820170950051AM56736&flowsort=090820170953013AM567360"},{"type":"发文","title":"[发文]测试wtp9-6-5","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090620171111011AM56736&flowsort=090620171113024AM567360"},{"type":"发文","title":"[发文]测试wtp9-6-3","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090620171015016AM56736&flowsort=090620171016056AM567360"},{"type":"发文","title":"[发文]测试wtp9-6-2","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090620171007035AM56736&flowsort=090620171010002AM567360"},{"type":"发文","title":"[发文]测试wtp9-6-1","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090620171002051AM56736&flowsort=090620171005031AM567360"},{"type":"发文","title":"[发文]测试wtp9-4-5","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090420171005049AM56736&flowsort=090420170306038PM567360"},{"type":"发文","title":"[发文]测试wtp9-4-4","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=090420170958031AM56736&flowsort=090420171002028AM567360"},{"type":"发文","title":"[发文]测试wtp","address":"/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=082120170522041PM56736&flowsort=082220170936033AM567360"},{"type":"收文","title":"[收文]测试wtp10-20-00","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=102020170402057PM56736&sign=56736&flowsort=031220180240011PM567360"},{"type":"收文","title":"[收文]xj测试1204","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=120420170423007PM56736&sign=56736&flowsort=120420170424026PM56104"},{"type":"收文","title":"[收文]测试wtp","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=103120170734046PM56736&sign=56736&flowsort=103120170735020PM567360"},{"type":"收文","title":"[收文]测试wtp10-20-1","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=102020170953005AM56736&sign=56736&flowsort=102020170954004AM567360"},{"type":"收文","title":"[收文]测试wtp","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=102020170950009AM56736&sign=56736&flowsort=102020170952037AM567360"},{"type":"收文","title":"[收文]测试wtp1112","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=101320170335038PM56736&sign=56736&flowsort=101320170336005PM567360"},{"type":"收文","title":"[收文]测试wtp111","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=101320170335019PM56736&sign=56736&flowsort=101320170335038PM567360"},{"type":"收文","title":"[收文]测试wtp999","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=101320170333019PM56736&sign=56736&flowsort=101320170333053PM567360"},{"type":"收文","title":"[收文]测试wtp10-14","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=101320170315019PM56736&sign=56736&flowsort=101320170315046PM567360"},{"type":"收文","title":"[收文]测试wtp10-13","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=101320170313042PM56736&sign=56736&flowsort=101320170314007PM567360"},{"type":"收文","title":"[收文]测试电子签章wtp9-15-4","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=091520170219028PM56736&sign=56736&flowsort=091520170223042PM567360"},{"type":"收文","title":"[收文]测试wtp盖章9-4-2","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=090420170936010AM56736&sign=56736&flowsort=090420170937011AM567360"},{"type":"收文","title":"[收文]测试wtp盖章9-4-1","address":"/app_phone/processedInfo.do?pathdata=sgy&deptunit=10021&file_source_id=090420170921013AM56736&sign=56736&flowsort=090420170932015AM567360"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=011020180203013PM56104&usersend=邢纪文&file_source_id=010520180932006PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=120620171041040AM7237&usersend=杨柳&file_source_id=120620171036014AM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=110820171106040AM7237&usersend=杨柳&file_source_id=110820171102023AM56736"},{"type":"行政审批","title":"[行政审批]工作调整通知书（存根）(信息中心运维部-邢纪文)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=查阅&flowsort=110220170736057PM561040&usersend=邢纪文&file_source_id=110220170736057PM56104"},{"type":"行政审批","title":"[行政审批]审判法庭使用通知单（民事行政类案件）(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=103120170720046PM56104&usersend=邢纪文&file_source_id=103120170720023PM56736"},{"type":"行政审批","title":"[行政审批]更改案件信息审批表(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=运维人员&flowsort=102720170324031PM56066&usersend=井晓鑫&file_source_id=102720170322046PM56736"},{"type":"行政审批","title":"[行政审批]信息化设备耗材申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=C1710271108000000&usersend=邢纪文&file_source_id=102720171108048AM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=102420170932010AM56104&usersend=邢纪文&file_source_id=102420170929048AM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101220171027057AM56736&usersend=王天鹏&file_source_id=101220171026054AM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=库管员&flowsort=101120170247029PM567360&usersend=王天鹏&file_source_id=101120170247029PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101020170718055PM56736&usersend=王天鹏&file_source_id=101020170717035PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101020170716014PM56736&usersend=王天鹏&file_source_id=101020170714055PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101020170709040PM56736&usersend=王天鹏&file_source_id=101020170705020PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=行装处意见&flowsort=101020170707002PM567360&usersend=王天鹏&file_source_id=101020170707002PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101020170704000PM56736&usersend=王天鹏&file_source_id=101020170702059PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=采购科意见&flowsort=101020170701044PM56736&usersend=王天鹏&file_source_id=101020170659051PM56736"},{"type":"行政审批","title":"[行政审批]办公用品申领单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=101020170653057PM56104&usersend=邢纪文&file_source_id=101020170647045PM56736"},{"type":"行政审批","title":"[行政审批]省法院机关请假单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=091920170447058PM56104&usersend=邢纪文&file_source_id=091920170447027PM56736"},{"type":"行政审批","title":"[行政审批]省法院机关请假单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=091820171111016AM56104&usersend=邢纪文&file_source_id=091820171111001AM56736"},{"type":"行政审批","title":"[行政审批]省法院领导及部门主要负责人外出请示、请假报告单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=批准人&flowsort=091520170243028PM567360&usersend=王天鹏&file_source_id=091520170243028PM56736"},{"type":"行政审批","title":"[行政审批]实习申请书(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=090720170445007PM56104&usersend=邢纪文&file_source_id=090720170444055PM56736"},{"type":"行政审批","title":"[行政审批]财装处办公设备维修申请单(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=090720170442028PM56104&usersend=邢纪文&file_source_id=090720170442006PM56736"},{"type":"行政审批","title":"[行政审批]省法院干部申请因私出国（境）及借用证件审批表(信息中心运维部-王天鹏)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=办理&flowsort=090720170440048PM56104&usersend=邢纪文&file_source_id=090720170440025PM56736"},{"type":"行政审批","title":"[行政审批]电话维修申请单(立案一庭-高法运维_周永钱)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=信息中心意见&flowsort=090620170241005PM84520&usersend=高法运维_周永钱&file_source_id=090620170241005PM8452"},{"type":"行政审批","title":"[行政审批]财装处办公设备维修申请单(信息中心运维部-邢纪文)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=部门意见&flowsort=090620171144015AM561040&usersend=邢纪文&file_source_id=090620171144015AM56104"},{"type":"行政审批","title":"[行政审批]财装处办公设备维修申请单(信息中心运维部-邢纪文)","address":"/app_phone/administrationInfo.do?isRead=1&sort=custom&pathdata=sgy&typeAdvice=部门意见&flowsort=090620171142004AM561040&usersend=sgy&file_source_id=090620171142004AM56104"},{"type":"办会","title":"[办会]有关2018年的最新概况","address":"/app_phone/getMeetingHandle.do?flowid=6985&flowsort=020120180858038AM56104"},{"type":"办会","title":"[办会]进入2018年的第32天","address":"/app_phone/getMeetingHandle.do?flowid=6984&flowsort=020120180849021AM56104"},{"type":"办会","title":"[办会]测试数据会议标题4","address":"/app_phone/getMeetingHandle.do?flowid=6983&flowsort=020120180840021AM56104"},{"type":"办会","title":"[办会]测试数据会议标题3","address":"/app_phone/getMeetingHandle.do?flowid=6982&flowsort=020120180839043AM56104"},{"type":"办会","title":"[办会]测试数据会议标题2","address":"/app_phone/getMeetingHandle.do?flowid=6981&flowsort=020120180838029AM56104"}]
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
         * type : 发文
         * title : [发文]	123
         * address : /app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=56736&userid=王天鹏&file_source_id=031220180226048PM56736&flowsort=031220180228042PM567360
         */

        private String type;
        private String title;
        private String address;
        private String time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
