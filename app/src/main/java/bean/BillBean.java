package bean;

import java.util.List;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public class BillBean {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-21T14:59:52","orderid":457,"price":35499,"status":0,"uid":154},{"createtime":"2017-10-21T15:10:11","orderid":461,"price":11899,"status":0,"uid":154},{"createtime":"2017-10-21T15:11:15","orderid":462,"price":35499,"status":0,"uid":154}]
     * page : 1
     */

    public String msg;
    public String code;
    public String page;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createtime : 2017-10-21T14:59:52
         * orderid : 457
         * price : 35499.0
         * status : 0
         * uid : 154
         */

        public String createtime;
        public int orderid;
        public double price;
        public int status;
        public int uid;
    }
}
