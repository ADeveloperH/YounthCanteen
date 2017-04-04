package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/2/22 0022 10:17
 */

public class OfficeAddressBean {


    /**
     * results : [{"business":"郑汴路商圈","businessid":1,"office":[{"officeid":1,"officename":"御玺大厦"},{"officeid":2,"officename":"写字楼22"}]}]
     * returnCode : 0
     * returnMessage : 获取成功
     */

    private String returnCode;
    private String returnMessage;
    /**
     * business : 郑汴路商圈
     * businessid : 1
     * office : [{"officeid":1,"officename":"御玺大厦"},{"officeid":2,"officename":"写字楼22"}]
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
        private String business;
        private String businessid;
        /**
         * officeid : 1
         * officename : 御玺大厦
         */

        private List<OfficeEntity> office;

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getBusinessid() {
            return businessid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public List<OfficeEntity> getOffice() {
            return office;
        }

        public void setOffice(List<OfficeEntity> office) {
            this.office = office;
        }

        public static class OfficeEntity {
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
}
