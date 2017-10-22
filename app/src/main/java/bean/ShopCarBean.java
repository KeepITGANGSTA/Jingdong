package bean;

import java.util.List;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class ShopCarBean {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"list":[{"bargainPrice":99,"createtime":"2017-10-14T21:38:26","detailUrl":"https://item.m.jd.com/product/4345173.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends","images":"https://m.360buyimg.com/n0/jfs/t6037/35/2944615848/95178/6cd6cff0/594a3a10Na4ec7f39.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6607/258/1025744923/75738/da120a2d/594a3a12Ne3e6bc56.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6370/292/1057025420/64655/f87644e3/594a3a12N5b900606.jpg!q70.jpg","num":1,"pid":45,"price":2999,"pscid":39,"selected":0,"sellerid":1,"subhead":"高清双摄，就是清晰！2000+1600万高清摄像头，6GB大内存+高通骁龙835处理器，性能怪兽！","title":"一加手机5 (A5000) 6GB+64GB 月岩灰 全网通 双卡双待 移动联通电信4G手机"}],"sellerName":"商家1","sellerid":"1"},{"list":[{"bargainPrice":11800,"createtime":"2017-10-14T21:38:26","detailUrl":"https://item.m.jd.com/product/5025518.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends","images":"https://m.360buyimg.com/n0/jfs/t8830/106/1760940277/195595/5cf9412f/59bf2ef5N5ab7dc16.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5428/70/1520969931/274676/b644dd0d/591128e7Nd2f70da0.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t5566/365/1519564203/36911/620c750c/591128eaN54ac3363.jpg!q70.jpg","num":2,"pid":58,"price":6399,"pscid":40,"selected":0,"sellerid":2,"subhead":"升级4G大显存！Nvme协议Pcie SSD,速度快人一步】GTX1050Ti就选拯救者！专业游戏键盘&新模具全新设计！","title":"联想(Lenovo)拯救者R720 15.6英寸游戏笔记本电脑(i5-7300HQ 8G 1T+128G SSD GTX1050Ti 4G IPS 黑)"}],"sellerName":"商家2","sellerid":"2"},{"list":[{"bargainPrice":5599,"createtime":"2017-10-10T17:30:32","detailUrl":"https://item.m.jd.com/product/4824715.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends","images":"https://m.360buyimg.com/n12/jfs/t7768/184/1153704394/148460/f42e1432/599a930fN8a85626b.jpg!q70.jpg","num":1,"pid":59,"price":5599,"pscid":40,"selected":0,"sellerid":3,"subhead":"游戏本选择4G独显，拒绝掉帧】升级版IPS全高清防眩光显示屏，WASD方向键颜色加持，三大出风口立体散热！","title":"戴尔DELL灵越游匣15PR-6648B GTX1050 15.6英寸游戏笔记本电脑(i5-7300HQ 8G 128GSSD+1T 4G独显 IPS)黑"}],"sellerName":"商家3","sellerid":"3"}]
     */

    public String msg;
    public String code;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * list : [{"bargainPrice":99,"createtime":"2017-10-14T21:38:26","detailUrl":"https://item.m.jd.com/product/4345173.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends","images":"https://m.360buyimg.com/n0/jfs/t6037/35/2944615848/95178/6cd6cff0/594a3a10Na4ec7f39.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6607/258/1025744923/75738/da120a2d/594a3a12Ne3e6bc56.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6370/292/1057025420/64655/f87644e3/594a3a12N5b900606.jpg!q70.jpg","num":1,"pid":45,"price":2999,"pscid":39,"selected":0,"sellerid":1,"subhead":"高清双摄，就是清晰！2000+1600万高清摄像头，6GB大内存+高通骁龙835处理器，性能怪兽！","title":"一加手机5 (A5000) 6GB+64GB 月岩灰 全网通 双卡双待 移动联通电信4G手机"}]
         * sellerName : 商家1
         * sellerid : 1
         */

        public String sellerName;
        public String sellerid;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * bargainPrice : 99.0
             * createtime : 2017-10-14T21:38:26
             * detailUrl : https://item.m.jd.com/product/4345173.html?utm#_source=androidapp&utm#_medium=appshare&utm#_campaign=t#_335139774&utm#_term=QQfriends
             * images : https://m.360buyimg.com/n0/jfs/t6037/35/2944615848/95178/6cd6cff0/594a3a10Na4ec7f39.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6607/258/1025744923/75738/da120a2d/594a3a12Ne3e6bc56.jpg!q70.jpg|https://m.360buyimg.com/n0/jfs/t6370/292/1057025420/64655/f87644e3/594a3a12N5b900606.jpg!q70.jpg
             * num : 1
             * pid : 45
             * price : 2999.0
             * pscid : 39
             * selected : 0
             * sellerid : 1
             * subhead : 高清双摄，就是清晰！2000+1600万高清摄像头，6GB大内存+高通骁龙835处理器，性能怪兽！
             * title : 一加手机5 (A5000) 6GB+64GB 月岩灰 全网通 双卡双待 移动联通电信4G手机
             */

            public double bargainPrice;
            public String createtime;
            public String detailUrl;
            public String images;
            public int num;
            public int pid;
            public double price;
            public int pscid;
            public int selected;
            public int sellerid;
            public String subhead;
            public String title;
        }
    }
}
