package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/2/8 0008 15:54
 */

public class RegisterResultBean {

    /**
     * token : 047f896210911975
     * userid : 4
     */

    private ResultsEntity results;
    /**
     * results : {"token":"047f896210911975","userid":4}
     * returnCode : 0
     * returnMessage : 注册成功！
     */

    private String returnCode;
    private String returnMessage;

    public ResultsEntity getResults() {
        return results;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }

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

    public static class ResultsEntity {
        private String token;
        private String userid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
