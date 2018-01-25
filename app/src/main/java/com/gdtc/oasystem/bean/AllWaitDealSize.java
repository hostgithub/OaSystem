package com.gdtc.oasystem.bean;

import java.io.Serializable;

/**
 * Created by wangjiawei on 2018-1-25.
 */

public class AllWaitDealSize implements Serializable{


    /**
     * dispatchCount : 20
     * inCount : 13
     * meetingCount : 2
     * administrationCount : 27
     * success : true
     */

    private String dispatchCount;
    private String inCount;
    private String meetingCount;
    private String administrationCount;
    private String success;

    public String getDispatchCount() {
        return dispatchCount;
    }

    public void setDispatchCount(String dispatchCount) {
        this.dispatchCount = dispatchCount;
    }

    public String getInCount() {
        return inCount;
    }

    public void setInCount(String inCount) {
        this.inCount = inCount;
    }

    public String getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(String meetingCount) {
        this.meetingCount = meetingCount;
    }

    public String getAdministrationCount() {
        return administrationCount;
    }

    public void setAdministrationCount(String administrationCount) {
        this.administrationCount = administrationCount;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
