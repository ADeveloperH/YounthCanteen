package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/2/21 0021 14:26
 */

public class APPUpdateBean {

    /**
     * IOSdescribe : 最新版
     * IOSversion : 1.1
     * describe : 第一版
     * md5 : md5
     * sha1 : sha1
     * url : http://116.255.130.49:10001/apk/app-release.apk
     * version : 1.1
     */

    private ResultsEntity results;
    /**
     * results : {"IOSdescribe":"最新版","IOSversion":"1.1","describe":"第一版","md5":"md5","sha1":"sha1","url":"http://116.255.130.49:10001/apk/app-release.apk","version":"1.1"}
     * returnCode : 1
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
        private String IOSdescribe;
        private String IOSversion;
        private String describe;
        private String md5;
        private String sha1;
        private String url;
        private String version;

        public String getIOSdescribe() {
            return IOSdescribe;
        }

        public void setIOSdescribe(String IOSdescribe) {
            this.IOSdescribe = IOSdescribe;
        }

        public String getIOSversion() {
            return IOSversion;
        }

        public void setIOSversion(String IOSversion) {
            this.IOSversion = IOSversion;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
