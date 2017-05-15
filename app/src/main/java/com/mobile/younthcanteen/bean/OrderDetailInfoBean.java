package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/4/11 0011 23:49
 */


public class OrderDetailInfoBean {

    /**
     * results : {"addTime":"2017-3-27 21:36:33","address":"御玺大厦 520","allMoney":12,"backAgreeTime":"","backApplyResult":"","backApplyTime":"","backOverTime":"","consignee":"苗苗","deliveryLocation":"","deliveryMan":"","deliveryPhone":"","deliveryTime":"","freight":0,"imgs":"http://116.255.130.49:10001/pros/007.jpg","orderMoney":12,"orderno":"","payTime":"2017-3-27 21:37:02","payType":"支付宝","paybackstatus":"","phone":"13373931172","pros":[{"choices":"","combo":"","counts":1,"ids":"","imgs":"http://116.255.130.49:10001/pros/007.jpg","price":12,"proid":6,"sale":0,"title":"烧青菜"}],"receiveTime":"","remark":"","sex":0,"shoptel":"614361574admin2","status":"待配送"}
     * returnCode : 0
     * returnMessage : 获取成功
     */

    private OrderGetResultEntity OrderGetResult;

    public OrderGetResultEntity getOrderGetResult() {
        return OrderGetResult;
    }

    public void setOrderGetResult(OrderGetResultEntity OrderGetResult) {
        this.OrderGetResult = OrderGetResult;
    }

    public static class OrderGetResultEntity {
        /**
         * addTime : 2017-3-27 21:36:33
         * address : 御玺大厦 520
         * allMoney : 12
         * backAgreeTime :
         * backApplyResult :
         * backApplyTime :
         * backOverTime :
         * consignee : 苗苗
         * deliveryLocation :
         * deliveryMan :
         * deliveryPhone :
         * deliveryTime :
         * freight : 0
         * imgs : http://116.255.130.49:10001/pros/007.jpg
         * orderMoney : 12
         * orderno :
         * payTime : 2017-3-27 21:37:02
         * payType : 支付宝
         * paybackstatus :
         * phone : 13373931172
         * pros : [{"choices":"","combo":"","counts":1,"ids":"","imgs":"http://116.255.130.49:10001/pros/007.jpg","price":12,"proid":6,"sale":0,"title":"烧青菜"}]
         * receiveTime :
         * remark :
         * sex : 0
         * shoptel : 614361574admin2
         * status : 待配送
         */

        private ResultsEntity results;
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
            private String addTime;
            private String address;
            private String allMoney;
            private String backAgreeTime;
            private String backApplyResult;
            private String backApplyTime;
            private String backOverTime;
            private String consignee;
            private String deliveryLocation;
            private String deliveryMan;
            private String deliveryPhone;
            private String deliveryTime;
            private String freight;
            private String imgs;
            private String okTime;
            private String orderMoney;
            private String orderno;
            private String payTime;
            private String payType;
            private String paybackstatus;
            private String phone;
            private String receiveTime;
            private String remark;
            private String sex;
            private String shoptel;
            private String status;
            /**
             * choices :
             * combo :
             * counts : 1
             * ids :
             * imgs : http://116.255.130.49:10001/pros/007.jpg
             * price : 12
             * proid : 6
             * sale : 0
             * title : 烧青菜
             */

            private List<ProsEntity> pros;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAllMoney() {
                return allMoney;
            }

            public void setAllMoney(String allMoney) {
                this.allMoney = allMoney;
            }

            public String getBackAgreeTime() {
                return backAgreeTime;
            }

            public void setBackAgreeTime(String backAgreeTime) {
                this.backAgreeTime = backAgreeTime;
            }

            public String getBackApplyResult() {
                return backApplyResult;
            }

            public void setBackApplyResult(String backApplyResult) {
                this.backApplyResult = backApplyResult;
            }

            public String getBackApplyTime() {
                return backApplyTime;
            }

            public void setBackApplyTime(String backApplyTime) {
                this.backApplyTime = backApplyTime;
            }

            public String getBackOverTime() {
                return backOverTime;
            }

            public void setBackOverTime(String backOverTime) {
                this.backOverTime = backOverTime;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getDeliveryLocation() {
                return deliveryLocation;
            }

            public void setDeliveryLocation(String deliveryLocation) {
                this.deliveryLocation = deliveryLocation;
            }

            public String getDeliveryMan() {
                return deliveryMan;
            }

            public void setDeliveryMan(String deliveryMan) {
                this.deliveryMan = deliveryMan;
            }

            public String getDeliveryPhone() {
                return deliveryPhone;
            }

            public void setDeliveryPhone(String deliveryPhone) {
                this.deliveryPhone = deliveryPhone;
            }

            public String getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(String deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getOrderMoney() {
                return orderMoney;
            }

            public void setOrderMoney(String orderMoney) {
                this.orderMoney = orderMoney;
            }

            public String getOkTime() {
                return okTime;
            }

            public void setOkTime(String okTime) {
                this.okTime = okTime;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getPaybackstatus() {
                return paybackstatus;
            }

            public void setPaybackstatus(String paybackstatus) {
                this.paybackstatus = paybackstatus;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getReceiveTime() {
                return receiveTime;
            }

            public void setReceiveTime(String receiveTime) {
                this.receiveTime = receiveTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getShoptel() {
                return shoptel;
            }

            public void setShoptel(String shoptel) {
                this.shoptel = shoptel;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<ProsEntity> getPros() {
                return pros;
            }

            public void setPros(List<ProsEntity> pros) {
                this.pros = pros;
            }

            public static class ProsEntity {
                private String choices;
                private String combo;
                private String counts;
                private String ids;
                private String imgs;
                private String price;
                private String proid;
                private String sale;
                private String title;

                public String getChoices() {
                    return choices;
                }

                public void setChoices(String choices) {
                    this.choices = choices;
                }

                public String getCombo() {
                    return combo;
                }

                public void setCombo(String combo) {
                    this.combo = combo;
                }

                public String getCounts() {
                    return counts;
                }

                public void setCounts(String counts) {
                    this.counts = counts;
                }

                public String getIds() {
                    return ids;
                }

                public void setIds(String ids) {
                    this.ids = ids;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
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

                public String getSale() {
                    return sale;
                }

                public void setSale(String sale) {
                    this.sale = sale;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
