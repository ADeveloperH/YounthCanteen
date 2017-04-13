package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/4/13 0013 21:27
 */


public class RegisterAgreementBean {

    /**
     * content : 青春食堂用户协议

     青春食堂与用户共同确认：

     一、定义条款
     二、用户注册
     三、用户服务
     四、交易规则
     五、用户的权利和义务
     六、青春食堂的权利和义务
     七、用户信息
     八、特别声明
     九、知识产权
     十、客户服务
     十一、协议的变更和终止
     十二、违约责任
     十三、争议解决
     十四、协议生效
     * name : 《青春食堂用户协议》
     * url :
     */

    private ResultsEntity results;
    /**
     * results : {"content":"青春食堂用户协议\n\n青春食堂与用户共同确认：\n\n一、定义条款\n二、用户注册\n三、用户服务\n四、交易规则\n五、用户的权利和义务\n六、青春食堂的权利和义务\n七、用户信息\n八、特别声明\n九、知识产权\n十、客户服务\n十一、协议的变更和终止\n十二、违约责任\n十三、争议解决\n十四、协议生效","name":"《青春食堂用户协议》","url":""}
     * returnCode : 0
     * returnMessage : 获取成功
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
        private String content;
        private String name;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
