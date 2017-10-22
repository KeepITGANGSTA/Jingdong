package common;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class Api {
    /** 首页轮播图
     */
    public static final String VP="http://120.27.23.105/ad/getAd";

    /** 首页九宫格
     */
    public static final String GridView="http://120.27.23.105/product/getCatagory";

    /** 登录接口
     */
    public static final String LOGIN="http://120.27.23.105/user/login";

    /** 注册接口
     */
    public static final String RES="http://120.27.23.105/user/reg";

    /** 修改昵称
     */
    public static final String ALTER_NICKNAME="http://120.27.23.105/user/updateNickName";

    /** 获取用户信息
     */
    public static final String USER_INFO="http://120.27.23.105/user/getUserInfo";

    /** 上传头像
     */
    public static final String AlertIcon="http://120.27.23.105/file/upload";

    /** 商品子分类
     */
    public static final String GoodsType="http://120.27.23.105/product/getProductCatagory";

    /** 当前子分类下的商品列表
     */
    public static final String Child_Type="http://120.27.23.105/product/getProducts";

    /* 根据关键词搜索商品
     */
    public static final String SearchGoods="http://120.27.23.105/product/searchProducts";

    /** 商品详情
     */
    public static final String GOODS_DETAILS="http://120.27.23.105/product/getProductDetail";

    /** 添加购物车
     */
    public static final String Add_ShopCar="http://120.27.23.105/product/addCart";

    /** 查询购物车
     */
    public static final String Search_ShopCar="http://120.27.23.105/product/getCarts";

    /** 更新购物车
     */
    public static final String Update_ShopCar="http://120.27.23.105/product/updateCarts?";

    /** 创建订单
     */
    public static final String CreatBill="http://120.27.23.105/product/createOrder";

    /** 获取订单信息
     */
    public static final String GetBillInfo="http://120.27.23.105/product/getOrders";

    /** 修改订单信息
     */
    public static final String AlertBill="http://120.27.23.105/product/updateOrder";

}
