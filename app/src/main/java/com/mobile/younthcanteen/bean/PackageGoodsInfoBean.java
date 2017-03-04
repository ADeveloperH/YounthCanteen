package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/3/4 0004 17:25
 */

public class PackageGoodsInfoBean {

    /**
     * choices : [""]
     * combos : [{"count":2,"material":[{"counts":0,"name":"土豆丝","proid":17},{"counts":0,"name":"绿豆芽","proid":18},{"counts":0,"name":"青菜","proid":21}]},{"count":1,"material":[{"counts":0,"name":"红烧肉","proid":19},{"counts":0,"name":"土豆鸡块","proid":20}]}]
     * describe : 一个肉菜+二个素菜+米饭一份
     * name : 一荤二素米饭套餐
     * price : 12
     * proid : 2
     * url : ["http://116.255.130.49:10001/pros/002.jpg","http://116.255.130.49:10001/pros/001.jpg","http://116.255.130.49:10001/pros/003.jpg","http://116.255.130.49:10001/pros/004.jpg","http://116.255.130.49:10001/pros/005.jpg"]
     */

    private ResultsEntity results;
    /**
     * results : {"choices":[""],"combos":[{"count":2,"material":[{"counts":0,"name":"土豆丝","proid":17},{"counts":0,"name":"绿豆芽","proid":18},{"counts":0,"name":"青菜","proid":21}]},{"count":1,"material":[{"counts":0,"name":"红烧肉","proid":19},{"counts":0,"name":"土豆鸡块","proid":20}]}],"describe":"一个肉菜+二个素菜+米饭一份","name":"一荤二素米饭套餐","price":12,"proid":2,"url":["http://116.255.130.49:10001/pros/002.jpg","http://116.255.130.49:10001/pros/001.jpg","http://116.255.130.49:10001/pros/003.jpg","http://116.255.130.49:10001/pros/004.jpg","http://116.255.130.49:10001/pros/005.jpg"]}
     * returnCode : 0
     * returnMessage : 成功！
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
        private String describe;
        private String name;
        private String price;
        private String proid;
        private List<String> choices;
        /**
         * count : 2
         * material : [{"counts":0,"name":"土豆丝","proid":17},{"counts":0,"name":"绿豆芽","proid":18},{"counts":0,"name":"青菜","proid":21}]
         */

        private List<CombosEntity> combos;
        private List<String> url;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProid() {
            return proid;
        }

        public void setProid(String proid) {
            this.proid = proid;
        }

        public List<String> getChoices() {
            return choices;
        }

        public void setChoices(List<String> choices) {
            this.choices = choices;
        }

        public List<CombosEntity> getCombos() {
            return combos;
        }

        public void setCombos(List<CombosEntity> combos) {
            this.combos = combos;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }

        public static class CombosEntity {
            private String count;
            /**
             * counts : 0
             * name : 土豆丝
             * proid : 17
             */

            private List<MaterialEntity> material;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<MaterialEntity> getMaterial() {
                return material;
            }

            public void setMaterial(List<MaterialEntity> material) {
                this.material = material;
            }

            public static class MaterialEntity {
                private String counts;
                private String name;
                private String proid;

                public String getCounts() {
                    return counts;
                }

                public void setCounts(String counts) {
                    this.counts = counts;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getProid() {
                    return proid;
                }

                public void setProid(String proid) {
                    this.proid = proid;
                }
            }
        }
    }
}
