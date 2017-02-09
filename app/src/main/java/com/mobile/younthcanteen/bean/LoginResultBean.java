package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/2/7 0007 22:53
 */

public class LoginResultBean {

    /**
     * nick : zz_17739775520
     * token : d189724cb2885918
     * userid : 4
     */

    private ResultsEntity results;
    /**
     * results : {"nick":"zz_17739775520","token":"d189724cb2885918","userid":4}
     * returnCode : 0
     * returnMessage : 登录成功！
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
        private String nick;
        private String token;
        private String userid;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

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
