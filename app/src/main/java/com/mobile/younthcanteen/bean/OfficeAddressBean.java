package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/2/22 0022 10:17
 */

public class OfficeAddressBean {

    /**
     * results : [{"officeid":1,"officename":"御玺大厦"}]
     * returnCode : 0
     * returnMessage : 获取成功
     */

    private String returnCode;
    private String returnMessage;
    /**
     * officeid : 1
     * officename : 御玺大厦
     */

    private List<ResultsEntity> results;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        private String officeid;
        private String officename;

        public String getOfficeid() {
            return officeid;
        }

        public void setOfficeid(String officeid) {
            this.officeid = officeid;
        }

        public String getOfficename() {
            return officename;
        }

        public void setOfficename(String officename) {
            this.officename = officename;
        }
    }
}
