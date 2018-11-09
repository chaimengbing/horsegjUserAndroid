package com.project.mgjandroid.constants;

import android.os.Environment;

import com.project.mgjandroid.BuildConfig;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;

import java.io.File;

public class Constants {
    public static final String WE_CHAT_APP_ID = App.getInstance().getString(R.string.wechat_appid);
    //	public static final String WE_CHAT_APP_SECRET = App.getInstance().getString(R.string.wechat_appsecret);
    //	private static boolean isTest = true;
    public static int TEST_IP_TYPE = 0;

    public static final String TEST_IP_123 = "123.56.15.86";
    public static final String TEST_IP_101 = "101.200.34.156";
    public static final String TEST_IP_47 = "47.94.93.98";
    public static final String TEST_IP_60 = "60.205.120.0";
    public static final String TEST_IP_120 = "120.24.16.64";
    //    public static final String TEST_IP = "120.24.16.64";
    //    public static final String TEST_IP = "192.168.199.217:8080";
    public static final String ONLINE_IP = "60.205.120.0";
    //    public static final String TEST_IP = "120.24.16.64";
    //        public static final String TEST_IP = "112.74.18.147";
    public static final String URL_NEW_HOME_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/merchant/newUserClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_HOME_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/merchant/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_SECOND_HAND_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/secondhand/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_RENT_HOUSE_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/houselease/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_EDUCATION_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/education/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_HOME_MAKING_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/homemaking/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_REPAIR_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/repair/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_LOTTERY = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : "118.31.2.132") + "/lottery/user/"; //120.24.16.64正式123.56.15.86
    public static final String URL_BBS_MAIN = BuildConfig.IS_DEBUG ? "http://" + PreferenceUtils.getSelectUrl(App.getInstance()) + "/bbs/userClient?m=" : "http://" + ONLINE_IP + "/bbs/userClient?m=";
    //    public static final String URL_HOME_MAIN = "http://" + (BuildConfig.IS_DEBUG?TEST_IP:ONLINE_IP) + "/merchant/userClient?m="; //192.168.199.50正式123.56.15.86
    public static final String URL_IMAGE_FRONT = "http://7xpvkm.com1.z0.glb.clouddn.com/";

    public static final String URL_fIND_TAKE_AWAY_MERCHANT = URL_HOME_MAIN + "findTakeAwayMerchant4";
    //商铺页面获取商品列表
    public static final String URL_SHOW_MERCHANT_TAKE_AWAY_MENU = URL_HOME_MAIN + "showMerchantTakeAwayMenu";
    //商铺页面获取商品列表(大容量方案)
    public static final String URL_SHOW_MERCHANT_TAKE_AWAY_CATEGORY = URL_HOME_MAIN + "showMerchantTakeAwayCategory2";
    //商铺页面加载分页商品列表(大容量方案)
    public static final String URL_TAKE_AWAY_CATEGORY_MORE = URL_HOME_MAIN + "showMerchantTakeAwayFindByCategoryId2";

    //	public static final String URL_FIND_MERCHANT_TCOMENTS = URL_HOME_MAIN + "findMerchantTComents";
//	public static final String URL_ORDER_LIST = URL_HOME_MAIN + "getOrderList";
    public static final String URL_ORDER_DETAIL = URL_HOME_MAIN + "findTOrderById";
    //	public static final String URL_COMMERCIAL = URL_HOME_MAIN + "getCommercialMainPage";
//	public static final String URL_COMMERCIAL_COMMENT_LIST = URL_HOME_MAIN + "getCommercialCommentList";
//	public static final String URL_GOODS_DETAIL = URL_HOME_MAIN + "getProductDetail";
    //发送登录验证码
    public static final String URL_GET_MSG_CODE = URL_HOME_MAIN + "sendLoginSms";
    //验证码登录
    public static final String URL_SMS_LOGIN = URL_HOME_MAIN + "checkLoginCode";
    //发送手机绑定验证码
    public static final String URL_GET_MOBILE_BIND_MSG_CODE = URL_HOME_MAIN + "sendMobileBindSms";
    //查询发布信息当前所在地区
    public static final String URL_FIND_INFORMATION_AREA = URL_HOME_MAIN + "findInformationAreaByXY";
    //查询首页公告
    public static final String URL_FIND_BROADCAST_LIST = URL_HOME_MAIN + "findBroadcastList";
    //查询首页推广
    public static final String URL_FIND_PUBLICITY = URL_HOME_MAIN + "findPrimaryPublicityList";
    //查询首页推荐商品
    public static final String URL_FIND_RECOMMEND_CATEGORY = URL_HOME_MAIN + "findRecommendCategoryListByUserXY";
    //绑定手机
    public static final String URL_BIND_MOBILE = URL_HOME_MAIN + "checkMobileBindCode";
    //获取个人地址
    public static final String URL_GET_ADDRESS = URL_HOME_MAIN + "findUserAddress";
    //保存个人地址
    public static final String URL_EDIT_ADDRESS = URL_HOME_MAIN + "editUserAddress";
    //检验保存个人地址
    public static final String URL_CHECK_ADDRESS = URL_HOME_MAIN + "checkUserAddress";
    //订单预览
    public static final String URL_GET_ORDER_PREVIEW = URL_HOME_MAIN + "orderPreview2";
    //平台红包订单预览
    public static final String URL_GET_REDBAG_SETTING = URL_HOME_MAIN + "redBagSetting";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "maguanjia";
    public static final String IMG_PATH = BASE_PATH + File.separator + "image";
    public static final String IMAGE_URL_END_THUMBNAIL = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 375) + "/h/" + DipToPx.dip2px(App.getInstance(), 156.25f) + "/interlace/1";
    public static final String IMAGE_URL_END_THUMBNAIL_BANNER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 375) + "/h/" + DipToPx.dip2px(App.getInstance(), 95) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 60) + "/h/" + DipToPx.dip2px(App.getInstance(), 60) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_PUBLICITY = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 300) + "/h/" + DipToPx.dip2px(App.getInstance(), 300) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 88) + "/h/" + DipToPx.dip2px(App.getInstance(), 88) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_MEERCHANT_ICON = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 127.5f) + "/h/" + DipToPx.dip2px(App.getInstance(), 97.5f) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 40) + "/h/" + DipToPx.dip2px(App.getInstance(), 40) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_BANNER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 360) + "/h/" + DipToPx.dip2px(App.getInstance(), 280) + "/interlace/1";
    public static final String RECYCLER_IMAGE_URL_END_THUMBNAIL_BANNER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 255) + "/h/" + DipToPx.dip2px(App.getInstance(), 195) + "/interlace/1";

    public static String getEndThumbnail(int width, int height) {
        return "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), width) + "/h/" + DipToPx.dip2px(App.getInstance(), height) + "/interlace/1";
    }

    //删除地址
    public static final String URL_DELETE_ADDRESS = URL_HOME_MAIN + "delUserAddress";
    //订单提交
    public static final String URL_SUBMIT_ORDER = URL_HOME_MAIN + "orderSubmit2";
    //查询订单
    public static final String URL_GET_ORDER_LIST = URL_HOME_MAIN + "findUserTOrders";
    //查询用户订单(new)
    public static final String URL_GET_NEW_ORDER_LIST = URL_HOME_MAIN + "findNewUserTOrders";
    //查询订单类型(new)
    public static final String URL_FIND_ALL_ORDER_TYPE = URL_HOME_MAIN + "findAllOrderType";
    //订单详情(new)
    public static final String URL_NEW_ORDER_DETAIL = URL_HOME_MAIN + "findNewTOrderById";
    //取消订单
    public static final String URL_CANCEL_ORDER_BY_ID = URL_HOME_MAIN + "cancelTOrderById";
    //确认送达
    public static final String URL_DONE_ORDER_BY_ID = URL_HOME_MAIN + "doneTOrderById";
    //查询用户余额
    public static final String URL_CHECK_EXTRA_MONEY = URL_HOME_MAIN + "findUserAccount";
    //查询用户账户
    public static final String URL_FIND_USER_CENTER = URL_HOME_MAIN + "findUserCenter";
    //更改用户名
    public static final String URL_CHANGE_USERNAME = URL_HOME_MAIN + "updateUserName";
    //更改用户头像
    public static final String URL_CHANGE_HEAD = URL_HOME_MAIN + "updateUserHeaderImg";
    //获取七牛Token
    public static final String URL_GET_QINIU_TOKEN = URL_HOME_MAIN + "obtainQiniuUploadToken";
    //删除订单
    public static final String URL_DELETE_ORDER = URL_HOME_MAIN + "delTOrderById";
    //app初始化
    public static final String URL_INIT_APP = URL_HOME_MAIN + "findAppUserByToken";
    //退出登录
    public static final String URL_EXIT_OUT = URL_HOME_MAIN + "logout";
    //查询订单详情
//	public static final String URL_FIND_ORDER_DETAIL = URL_HOME_MAIN + "findTOrderById";
    //首页分类筛选
    public static final String URL_GET_CATEGORY = URL_HOME_MAIN + "findTagCategory";
    //首页轮播图
    public static final String URL_FIND_TBANNER = URL_HOME_MAIN + "findTBanner";
    //首页分类导航
    public static final String URL_FIND_PRIMARY_CATEGORY_LIST = URL_HOME_MAIN + "findPrimaryCategoryList";
    //首页 菜单图标自定义
    public static final String URL_FIND_APP_MENU_IMG = URL_NEW_HOME_MAIN + "findAppMenuSysImgByState";
    //更多分类导航
    public static final String URL_FIND_MORE_PRIMARY_CATEGORY_LIST = URL_HOME_MAIN + "findMorePrimaryCategoryList";
    //筛选按钮
    public static final String URL_FILTER = URL_HOME_MAIN + "findMerchantFilter";
    //第三方登录
    public static final String URL_THIRD_PARTY_LOGIN = URL_HOME_MAIN + "thirdLogin";
    //查询用户第三方绑定情况
    public static final String URL_CHECK_THIRD_LOGIN = URL_HOME_MAIN + "findUserThirdLogin";
    //解绑第三方
    public static final String URL_UNBIND_THIRD_LOGIN = URL_HOME_MAIN + "thirdLoginUnBind";
    //绑定第三方
    public static final String URL_BIND_THIRD_LOGIN = URL_HOME_MAIN + "thirdLoginBind";
    //支付宝支付
//	public static final String URL_ALIPAY = URL_HOME_MAIN + "alipayApp";
    //余额支付
    public static final String URL_EXTRA = URL_HOME_MAIN + "balancePay";
    //获取能用的支付方式
    public static final String URL_PAY_WAYS = URL_HOME_MAIN + "findChargeTypes";
    //评价订单
    public static final String URL_EVALUATE_ORDER = URL_HOME_MAIN + "createOrderComments";
    //商家评价
    public static final String URL_MERCHANT_EVALUATE = URL_HOME_MAIN + "findMerchantComments";
    //查询商品评价
    public static final String URL_GOODS_EVALUATE = URL_HOME_MAIN + "findGoodsComments";
    //展示商家评价
    public static final String URL_SHOW_EVALUATE = URL_HOME_MAIN + "findMerchantInfo2";
    //ping++ 充值
    public static final String URL_ALIPAY_CHARGE = URL_HOME_MAIN + "pingxxCharge";
    //ping++ 支付
    public static final String URL_PING_PAY = URL_HOME_MAIN + "pingxxPay";
    //检查订单支付状态
    public static final String URL_CHECK_ORDER_PAY = URL_HOME_MAIN + "checkTOrderPay";
    //查询收支明细
    public static final String URL_ACCOUNT_DETAILS = URL_HOME_MAIN + "findUserAccountDetails";
    //appLaunch
    public static final String URL_APP_LAUNCH = URL_HOME_MAIN + "appLaunch";
    //凑单接口
    public static final String URL_FIND_TGOODS_BY_PRICE = URL_HOME_MAIN + "findTGoodsByPrice";
    /**
     * 收藏商家
     */
    public static final String URL_CREAT_USER_FAVORITES = URL_HOME_MAIN + "createUserFavorites";
    /**
     * 查询收藏商家列表
     */
    public static final String URL_FIND_USER_FAVORITES = URL_HOME_MAIN + "findUserFavorites";
    /**
     * 取消收藏
     */
    public static final String URL_CANCEL_USER_FAVORITES = URL_HOME_MAIN + "cancelUserFavorites";
    /**
     * 批量取消收藏
     */
    public static final String URL_CANCEL_USER_FAVORITES_BATCH = URL_HOME_MAIN + "cancelUserFavoritesBatch";
    /**
     * 查询默认地址
     */
    public static final String URL_FIND_DEFAULT_ADDRESS_LIST = URL_HOME_MAIN + "findDefaultAddressList";
    //搜索
    public static final String URL_SEARCH = URL_HOME_MAIN + "searchTakeAwayMerchant2";
    //热搜
    public static final String URL_HOT_SEARCH = URL_HOME_MAIN + "findHotSearch";
    //用户反馈
    public static final String URL_FEED_BACK = URL_HOME_MAIN + "userFeedback";
    /**
     * 查询用户银行卡信息
     */
    public static final String URL_FIND_USER_DRAW_CASH_BANK_LIST = URL_HOME_MAIN + "findUserDrawCashBankList";
    /**
     * 删除用户银行卡信息
     */
    public static final String URL_DEL_USER_DRAW_CASH_BANK = URL_HOME_MAIN + "delUserDrawCashBank";
    /**
     * 查询开户行类型
     */
    public static final String URL_FIND_BANK_LIST = URL_HOME_MAIN + "findTBankList";
    //申请提现
    public static final String URL_DRAW_CASH = URL_HOME_MAIN + "userDrawCash";
    //提现限制
    public static final String URL_CASH_LIMIT = URL_HOME_MAIN + "findUserDrawCashConfine";
    /**
     * 查询我的未使用红包列表
     * <p/>
     * date 当前时间(不传查未过期的，传查已过期的)
     */
    public static final String URL_FIND_USER_RED_BAG_LIST = URL_HOME_MAIN + "findUserRedBagListNew";
    /**
     * 用户下单查询用户可用红包
     */
    public static final String URL_FILTER_USABLE_RED_BAG_LIST = URL_HOME_MAIN + "filterUsableRedBagList";
    /**
     * * 我的红包
     * 接口：queryRedBagList
     */
    public static final String URL_QUERY_RED_BAG_LIST = URL_HOME_MAIN + "queryRedBagList";
    /**
     * * 选择红包
     * 接口：queryPlatformRedBagList
     */
    public static final String URL_QUERY_PLATFORM_REDBAGLIST = URL_HOME_MAIN + "queryPlatformRedBagList";
    /**
     * 订单商品列表，包含是否可退换信息，以及申请退换后的状态
     */
    public static final String URL_RETURN_OR_CHANGE_STATUS = URL_HOME_MAIN + "findReturnOrChangeOrderById";
    /**
     * 退换货申请
     */
    public static final String URL_APPLY_RETURN_OR_CHANGE = URL_HOME_MAIN + "applyReturnOrChange";

    //所有banner图
    public static final String URL_INFOMATION_PUBLISH_BANNER = URL_HOME_MAIN + "findBannerLocations";

    //根据ID查询代理商
    public static final String URL_FIND_AGENT_BY_ID = URL_HOME_MAIN + "findAgentInfoById";

    //查询热门城市
    public static final String URL_FIND_HOT_CITY_LIST = URL_HOME_MAIN + "findHotCityList";
    /**
     * 查询拼团订单列表
     */
    public static final String URL_FIND_GROUP_ORDER = URL_HOME_MAIN + "findGroupBuyOrderList";
    /**
     * 检查是否可创建拼团
     */
    public static final String URL_CHECK_CAN_CREATE_GROUP = URL_HOME_MAIN + "checkToGroupBuy";
    /**
     * 创建拼团订单评价
     */
    public static final String URL_CREATE_GROUP_COMMENT = URL_HOME_MAIN + "createGroupBuyCommonts";
    /**
     * 往期评论接口
     */
    public static final String URL_FIND_USER_GROUP_COMMENT = URL_HOME_MAIN + "findUserGroupBuyCommontsList";
    /**
     * 查询拼团服务类型
     */
    public static final String URL_FIND_SERVICE_TYPE = URL_HOME_MAIN + "findGroupBuyServiceType";
    /**
     * 创建拼团
     */
    public static final String URL_CREATE_GROUP = URL_HOME_MAIN + "createOrMergeGroupBuy";
    /**
     * 首页查询范围内的代理商的拼团列表
     */
    public static final String URL_FIND_GROUP_LIST = URL_HOME_MAIN + "findGroupBuyList";
    /**
     * 查询拼团详情
     */
    public static final String URL_FIND_GROUP_DETAIL = URL_HOME_MAIN + "findGroupBuyById";
    /**
     * 参与拼团获取用户配送地址
     */
    public static final String URL_FIND_USER_ADDRESS_PREVIEW = URL_HOME_MAIN + "findUserAddressPreview";
    /**
     * 拼团订单提交
     */
    public static final String URL_GROUP_ORDER_SUBMIT = URL_HOME_MAIN + "groupBuyOrderSubmit";
    /**
     * 买手详情的拼团列表
     */
    public static final String URL_USER_GROUP_LIST = URL_HOME_MAIN + "findGroupBuyUserDetail";
    /**
     * 查询区列表开通情况
     */
    public static final String URL_FIND_REGION = URL_HOME_MAIN + "findRegionDataList";
    /**
     * 查询商超代理商
     */
    public static final String URL_FIND_MERCHANT_SHOP_AGENT = URL_HOME_MAIN + "findMerchantShopAgent";
    /**
     * 查询商超商品分类
     */
    public static final String URL_FIND_MERCHANT_SHOP_GOODS_CATEGORY_LIST = URL_HOME_MAIN + "findMerchantShopGoodsCategoryList";
    /**
     * 查询商超热销商品列表
     */
    public static final String URL_MERCHANT_SHOP_HOTSALE_GOODS_LIST = URL_HOME_MAIN + "findMerchantShopHotsaleGoodsList";
    /**
     * 查询商超商品列表
     */
    public static final String URL_MERCHANT_SHOP_GOODS_LIST = URL_HOME_MAIN + "findMerchantShopGoodsList";
    /**
     * 商超评价列表
     */
    public static final String URL_FIND_MERCHANT_SHOP_GOODS_COMMENT_LIST = URL_HOME_MAIN + "findTGoodsCommentsList";
    /**
     * 查询购物车商品
     */
    public static final String URL_FIND_MERCHANT_SHOP_GOODS_CART_LIST = URL_HOME_MAIN + "findMerchantShopGoodsCarList";
    /**
     * 商超订单预览
     */
    public static final String URL_MERCHANT_SHOP_ORDER_PREVIEW = URL_HOME_MAIN + "merchantShopOrderPreview";
    /**
     * 商超订单提交
     */
    public static final String URL_MERCHANT_SHOP_ORDER_SUBMIT = URL_HOME_MAIN + "merchantShopOrderSubmit";
    /**
     * 商超索搜商品
     */
    public static final String URL_SEARCH_GOODS_BY_MERCHANT_ID = URL_HOME_MAIN + "searchGoodsByMerchantId";
    /**
     * 商超查询商品配送费
     */
    public static final String URL_FIND_MERCHANT_SHIPPING_FEE = URL_HOME_MAIN + "findMerchantShopShippingFee";


    public static final String URL_FIND_MERCHANT_SHOP_GOODS_INFO = URL_HOME_MAIN + "findMerchantShopGoodsInfo";
    /**
     * 商超查询商品配送费和起送价
     */
    public static final String URL_FIND_MERCHANT_SHIPPING_FEE_AND_MIN_PRICE = URL_HOME_MAIN + "findMerchantShopShippingFeeAndMinPrice";
    /**
     * 根据用户坐标，查询客服、投诉电话：
     */
    public static final String URL_FIND_USER_TELNUM_XY = URL_HOME_MAIN + "findCustomerAndComplainPhoneByUserXY";
    /**
     * 根据代理商id，查询客服、投诉电话：
     */
    public static final String URL_FIND_USER_TELNUM_ID = URL_HOME_MAIN + "findCustomerAndComplainPhoneByAgentId";
    /**
     * 根据代理商id，查询客服、投诉电话：
     */
    public static final String URL_FIND_USER_TELNUM_ID_NEW = URL_HOME_MAIN + "findCustomerAndComplainPhoneByAgentIdAndBusinessType";

    /**
     * 获取社区发布信息短信验证码
     */
    public static final String URL_SEND_RELEASE_INFOMATION_SMS = URL_BBS_MAIN + "sendReleaseInformationSms";
    /**
     * 验证社区发布信息短信验证码
     */
    public static final String URL_CHECK_RELEASE_INFOMATION_SMS = URL_BBS_MAIN + "checkReleaseInformationCode";
    /**
     * 获取推荐社区所有信息列表
     */
    public static final String URL_FIND_ALL_BBS_INFOMATION_LIST = URL_BBS_MAIN + "findAllBbsInformationList";
    /**
     * 社区发布信息
     */
    public static final String URL_RELAESE_INFOMATION = URL_BBS_MAIN + "releaseInformation";
    /**
     * 社区删除信息
     */
    public static final String URL_DELETE_INFOMATION = URL_BBS_MAIN + "deleteInformation";
    /**
     * 获取我的社区所有信息列表
     */
    public static final String URL_FIND_USER_BBS_INFOMATION_LIST = URL_BBS_MAIN + "findUserBbsInformationList";
    /**
     * 获取社区标签列表
     */
    public static final String URL_FIND_BBS_CATEGORY_LIST = URL_BBS_MAIN + "findBbsCategoryList";
    /**
     * 获取社区七牛token
     */
    public static final String URL_OBTIAN_QINIU_TOKEN = URL_BBS_MAIN + "obtainQiniuUploadToken";
    /**
     * 社区举报
     */
    public static final String URL_INFORM_INFOMATION = URL_BBS_MAIN + "informInformation";

    public static final String URL_UPDATE_APP = URL_HOME_MAIN + "findMaxAppVersion";

    public static final String URL_FULL_TIME_ALL_TAG = URL_HOME_MAIN + "findRecruitPositionCategoryList";
    //发布招聘信息
    public static final String URL_CREATE_JOB = URL_HOME_MAIN + "createPositionRecruitInformation";

    /**
     * 获取短信验证码
     */
    public static final String URL_GET_CODE_JOB = URL_HOME_MAIN + "sendReleaseInformationSms";

    public static final String URL_FIND_RECRUIT_HOT_POSITION_CATEGORY_LIST = URL_HOME_MAIN + "findRecruitHotPositionCategoryList";
    /**
     * 查询招聘信息
     */
    public static final String URL_FIND_POSITION_RECRUIT_INFORMATION_LIST = URL_HOME_MAIN + "findPositionRecruitInformationList";
    /**
     * 查询用户招聘信息
     */
    public static final String URL_FIND_USER_POSITION_RECRUIT_INFORMATION_LIST = URL_HOME_MAIN + "findUserPositionRecruitInformationList";
    /**
     * 刷新招聘信息
     */
    public static final String URL_REFRESH_POSITION_RECRUIT_INFORMATION = URL_HOME_MAIN + "refreshPositionRecruitInformation";
    /**
     * 删除招聘信息
     */
    public static final String URL_DELETE_POSITION_RECRUIT_INFORMATION = URL_HOME_MAIN + "deletePositionRecruitInformation";

    //获得可转发的红包
    public static final String URL_GET_EXTRA_RED_BAG = URL_HOME_MAIN + "getMerchantRedBagByOrderId";
    /**
     * 领取商家红包（进店红包、新用户红包）
     */
    public static final String URL_GET_MERCHANT_RED_BAG = URL_HOME_MAIN + "getMerchantRedBag";

    //获取我的发布分类
    public static final String URL_USER_PUBLISH = URL_HOME_MAIN + "findInformationCategoryList";

    /**
     * 创建二手信息
     */
    public static final String URL_CREATE_SECOND_HAND_INFOMATION = URL_SECOND_HAND_MAIN + "createSecondhandInformation";
    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_OBTAIN_QINIU_TOKEN_SECOND_HAND = URL_SECOND_HAND_MAIN + "obtainQiniuUploadToken";
    /**
     * 发送验证码
     */
    public static final String URL_SEND_SMS_SECOND_HAND = URL_SECOND_HAND_MAIN + "sendReleaseInformationSms";

    //获取二手市场的分类
    public static final String URL_GET_HOT_SECOND_HAND_CATEGORY = URL_SECOND_HAND_MAIN + "findHotSecondhandCategoryList";
    //获取二手市场的列表
    public static final String URL_GET_SECOND_HAND_LIST = URL_SECOND_HAND_MAIN + "findSecondhandInformationList";
    /**
     * 查询二手分类
     */
    public static final String URL_FIND_SECOND_HAND_CATEGORY_LIST = URL_SECOND_HAND_MAIN + "findSecondhandCategoryList";

    //查询用户二手信息
    public static final String URL_SEARCH_USER_SECOND_HAND = URL_SECOND_HAND_MAIN + "findUserSecondhandInformationList";

    //根据id查询二手信息
    public static final String URL_SEARCH_SECOND_HAND_BY_ID = URL_SECOND_HAND_MAIN + "findSecondhandInformation";
    //刷新二手信息
    public static final String URL_REFRESH_SECOND_ITEM = URL_SECOND_HAND_MAIN + "refreshSecondhandInformation";
    //删除二手信息
    public static final String URL_DELETE_SECOND_ITEM = URL_SECOND_HAND_MAIN + "deleteSecondhandInformation";
    /**
     * 举报二手信息
     */
    public static final String URL_REPORT_SECOND_HAND_INFOMATION = URL_SECOND_HAND_MAIN + "reportSecondhandInformation";
    /**
     * 再来一单
     */
    public static final String URL_AGAIN_ORDER_PREVIEW = URL_HOME_MAIN + "againOrderPreview2";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_RENT_HOUSE_OBTAIN_QINIU_TOKEN = URL_RENT_HOUSE_MAIN + "obtainQiniuUploadToken";
    //查询租房热门分类
    public static final String URL_RENT_HOUSE_HOT_CATEGORY_LIST = URL_RENT_HOUSE_MAIN + "findHotHouseLeaseCategoryList";
    //查询租房更多分类
    public static final String URL_RENT_HOUSE_MORE_CATEGORY_LIST = URL_RENT_HOUSE_MAIN + "findHouseLeaseCategoryList";
    //创建租房信息
    public static final String URL_CREATE_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "createHouseLeaseInformation";
    //查询租房信息
    public static final String URL_RENT_HOUSE_LIST = URL_RENT_HOUSE_MAIN + "findHouseLeaseInformationList";
    //查询用户租房信息
    public static final String URL_USER_RENT_HOUSE_LIST = URL_RENT_HOUSE_MAIN + "findUserHouseLeaseInformationList";
    //通过id查信息详情
    public static final String URL_RENT_HOUSE_DETAIL = URL_RENT_HOUSE_MAIN + "findHouseLeaseInformation";
    //刷新租房信息
    public static final String URL_REFRESH_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "refreshHouseLeaseInformation";
    //删除租房信息
    public static final String URL_DELETE_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "deleteHouseLeaseInformation";
    //举报租房信息
    public static final String URL_REPORT_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "reportHouseLeaseInformation";
    //发送租房验证码
    public static final String URL_SEND_CODE_RENT_HOUSE_DETAIL = URL_RENT_HOUSE_MAIN + "sendReleaseInformationSms";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_EDUCATION_OBTAIN_QINIU_TOKEN = URL_EDUCATION_MAIN + "obtainQiniuUploadToken";
    //查询家教热门分类
    public static final String URL_EDUCATION_HOT_CATEGORY_LIST = URL_EDUCATION_MAIN + "findHotEducationCategoryList";
    //查询家教更多分类
    public static final String URL_EDUCATION_MORE_CATEGORY_LIST = URL_EDUCATION_MAIN + "findEducationCategoryList";
    //创建家教信息
    public static final String URL_CREATE_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "createEducationInformation";
    //查询家教信息
    public static final String URL_EDUCATION_LIST = URL_EDUCATION_MAIN + "findEducationInformationList";
    //查询用户家教信息
    public static final String URL_USER_EDUCATION_LIST = URL_EDUCATION_MAIN + "findUserEducationInformationList";
    //通过id查信息详情
    public static final String URL_EDUCATION_DETAIL = URL_EDUCATION_MAIN + "findEducationInformation";
    //刷新家教信息
    public static final String URL_REFRESH_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "refreshEducationInformation";
    //删除家教信息
    public static final String URL_DELETE_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "deleteEducationInformation";
    //举报家教信息
    public static final String URL_REPORT_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "reportEducationInformation";
    //发送家教验证码
    public static final String URL_SEND_CODE_EDUCATION_DETAIL = URL_EDUCATION_MAIN + "sendReleaseInformationSms";
    //查询家教教师身份
    public static final String URL_FIND_EDUCATION_TEACHER_TYPE = URL_EDUCATION_MAIN + "findEducationTeacherType";
    //查询家教辅导阶段
    public static final String URL_FIND_EDUCATION_TUTORSHIP_STAGE = URL_EDUCATION_MAIN + "findEducationTutorshipStage";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_HOME_MAKING_OBTAIN_QINIU_TOKEN = URL_HOME_MAKING_MAIN + "obtainQiniuUploadToken";
    //查询家政热门分类
    public static final String URL_HOME_MAKING_HOT_CATEGORY_LIST = URL_HOME_MAKING_MAIN + "findHotHomemakingCategoryList";
    //查询家政更多分类
    public static final String URL_HOME_MAKING_MORE_CATEGORY_LIST = URL_HOME_MAKING_MAIN + "findHomemakingCategoryList";
    //创建家政信息
    public static final String URL_CREATE_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "createHomemakingInformation";
    //查询家政信息
    public static final String URL_HOME_MAKING_LIST = URL_HOME_MAKING_MAIN + "findHomemakingInformationList";
    //查询用户家政信息
    public static final String URL_USER_HOME_MAKING_LIST = URL_HOME_MAKING_MAIN + "findUserHomemakingInformationList";
    //通过id查信息详情
    public static final String URL_HOME_MAKING_DETAIL = URL_HOME_MAKING_MAIN + "findHomemakingInformation";
    //刷新家政信息
    public static final String URL_REFRESH_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "refreshHomemakingInformation";
    //删除家政信息
    public static final String URL_DELETE_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "deleteHomemakingInformation";
    //举报家政信息
    public static final String URL_REPORT_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "reportHomemakingInformation";
    //发送家政验证码
    public static final String URL_SEND_CODE_HOME_MAKING_DETAIL = URL_HOME_MAKING_MAIN + "sendReleaseInformationSms";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_REPAIR_OBTAIN_QINIU_TOKEN = URL_REPAIR_MAIN + "obtainQiniuUploadToken";
    //查询维修热门分类
    public static final String URL_REPAIR_HOT_CATEGORY_LIST = URL_REPAIR_MAIN + "findHotrepairCategoryList";
    //查询维修更多分类
    public static final String URL_REPAIR_MORE_CATEGORY_LIST = URL_REPAIR_MAIN + "findRepairCategoryList";
    //创建维修信息
    public static final String URL_CREATE_REPAIR_MESSAGE = URL_REPAIR_MAIN + "createRepairInformation";
    //查询维修信息
    public static final String URL_REPAIR_LIST = URL_REPAIR_MAIN + "findRepairInformationList";
    //查询用户维修信息
    public static final String URL_USER_REPAIR_LIST = URL_REPAIR_MAIN + "findUserRepairInformationList";
    //通过id查信息详情
    public static final String URL_REPAIR_DETAIL = URL_REPAIR_MAIN + "findRepairInformation";
    //刷新维修信息
    public static final String URL_REFRESH_REPAIR_MESSAGE = URL_REPAIR_MAIN + "refreshRepairInformation";
    //删除维修信息
    public static final String URL_DELETE_REPAIR_MESSAGE = URL_REPAIR_MAIN + "deleteRepairInformation";
    //举报维修信息
    public static final String URL_REPORT_REPAIR_MESSAGE = URL_REPAIR_MAIN + "reportRepairInformation";
    //发送维修验证码
    public static final String URL_SEND_CODE_REPAIR_DETAIL = URL_REPAIR_MAIN + "sendReleaseInformationSms";

    /**
     * 用户通过省市区查询司机行程列表
     */
    public static final String URL_FIND_CAR_HAILING_TRIP_LIST = URL_HOME_MAIN + "findChauffeurTripListByArea";
    /**
     * 用户约车查询起始点价格
     */
    public static final String URL_FIND_CAR_HAILING_TRIP_PRICE = URL_HOME_MAIN + "findChauffeurTripDetail";
    /**
     * 约车提交订单
     */
    public static final String URL_CAR_HAILING_ORDER_SUBMIT = URL_HOME_MAIN + "chauffeurOrderSubmit";
    /**
     * 用户端-查询所有起点/终点行程的省市县
     */
    public static final String URL_FIND_TRIP_LIST = URL_HOME_MAIN + "findCarTripListByUserClient";
    /**
     * 用户约车订单评价
     */
    public static final String URL_CHAUFFEUR_ORDER_COMMENT = URL_HOME_MAIN + "createChauffeurOrderCommonts";
    /**
     * 通过司机编号查询约车订单评论列表
     */
    public static final String URL_CHAUFFEUR_COMMENT_LIST = URL_HOME_MAIN + "findChauffeurOrderCommontsList";
    /**
     * 通过司机编号查询约车订单评论列表
     */
    public static final String URL_CHAUFFEUR_TRIP = URL_HOME_MAIN + "findChauffeurTrip";

    // 查询全部A1B1代理商省市区信息
    public static final String URL_FIND_AGENT_BASIS_LIST = URL_HOME_MAIN + "findAgentBasisList";
    /**
     * 用户约车下单查询用户可用红包
     */
    public static final String URL_FIND_CAR_USABLE_REDBAG_LIST = URL_HOME_MAIN + "findCarUsableRedBagList";
    /**
     * 查询订单所属代理商是否创建折扣活动
     */
    public static final String URL_FIND_ORDER_AGENT_CAR_REDBAG_PROMOTION = URL_HOME_MAIN + "findOrderAgentCarRedBagPromotion";
    /**
     * 查询首页活动弹窗列表
     */
    public static final String URL_FIND_INDEX_POP_ACTIVITY = URL_HOME_MAIN + "findIndexPopActivity";
    /**
     * 约车用户下单查询可用优惠活动和红包
     */
    public static final String URL_FIND_USER_CAR_USABLE_PROMOTION_ACTIVITY = URL_HOME_MAIN + "findUserCarUsablePromotionActivity";
    /**
     * 查询团购商家列表
     */
    public static final String URL_FIND_GROUP_PURCHASE_MERCHANT = URL_HOME_MAIN + "findGroupPurchaseMerchant2";
    /**
     * 查询附近团购商家列表
     */
    public static final String URL_FIND_NEAR_GROUP_PURCHASE_MERCHANT = URL_HOME_MAIN + "findNearGroupPurchaseMerchant2";
    /**
     * 获取团购商家服务分类集合
     */
    public static final String URL_FIND_GROUP_PURCHASE_MERCHANT_SERVICES = URL_HOME_MAIN + "findGroupPurchaseMerchantServices";
    /**
     * 团购—创建用户投诉
     */
    public static final String URL_CREATE_GROUP_PURCHASE_COMPLAIN = URL_HOME_MAIN + "createGroupPurchaseComplain";
    /**
     * 团购—添加评价
     */
    public static final String URL_CREATE_GROUP_PURCHASE_EVALUATE = URL_HOME_MAIN + "createGroupPurchaseEvaluate";
    /**
     * 团购—分页查询全部评价
     */
    public static final String URL_FIND_GROUP_PURCHASE_EVLUATE_LIST = URL_HOME_MAIN + "findGroupPurchaseEvaluateList";
    /**
     * 团购—查询评价详情
     */
    public static final String URL_FIND_GROUP_PURCHASE_EVLUATE_BY_ID = URL_HOME_MAIN + "findGroupPurchaseEvaluateById";
    /**
     * 团购—订单订单提交
     */
    public static final String URL_GROUP_PURCHASE_ORDER_SUBMIT = URL_HOME_MAIN + "groupPurchaseOrderSubmit";
    /**
     * 查询团购商家类别
     */
    public static final String URL_FIND_GROUP_PURCHASE_CATEGORY_LIST = URL_HOME_MAIN + "findGroupPurchaseCategoryList";
    /**
     * 查询团购商家详情
     */
    public static final String URL_FIND_GROUP_PURCHASE_MERCHANT_INFO = URL_HOME_MAIN + "findGroupPurchaseMerchantInfo";
    /**
     * 查询团购订单列表
     */
    public static final String URL_FIND_GROUP_PURCHASE_ORDER_LIST = URL_HOME_MAIN + "findGroupPurchaseOrderList";
    /**
     * 查询代金/团购券信息
     */
    public static final String URL_FIND_GROUP_PURCHASE_COUPON_INFO = URL_HOME_MAIN + "findGroupPurchaseCouponInfo";
    /**
     * 查询代金/团购券信息列表
     */
    public static final String URL_FIND_GROUP_PURCHASE_COUPON_LIST = URL_HOME_MAIN + "findGroupPurchaseCouponList";
    /**
     * 查询团购首页广告图
     */
    public static final String URL_FIND_GROUP_PURCHASE_BANNER_LIST = URL_HOME_MAIN + "findGroupPurchaseBannerList";
    /**
     * 根据团购订单编号查询未使用优惠券id列表
     */
    public static final String URL_FIND_GROUP_PURCHASE_ORDER_COUPON_CODE_ID_LIST = URL_HOME_MAIN + "findGroupPurchaseOrderCouponCodeIDListByOrderId";
    /**
     * 查询团购首页分类
     */
    public static final String URL_FIND_GROUP_PURCHASE_PRIMARY_CATEGORY_LIST = URL_HOME_MAIN + "findGroupPurchasePrimaryCategoryList";
    /**
     * 查询团购首页推广位集合
     */
    public static final String URL_FIND_GROUP_PURCHASE_PRIMARY_PUBLICITY_LIST = URL_HOME_MAIN + "findGroupPurchasePrimaryPublicityList";
    /**
     * 查询团购券退款
     */
    public static final String URL_BATCH_REFUND_GROUP_PURCHASE_ORDER_COUPON_CODE = URL_HOME_MAIN + "batchRefundGroupPurchaseOrderCouponCode";
    /**
     * 团购优惠买单
     */
    public static final String URL_GROUP_PURCHASE_ORDER_PREVIEW = URL_HOME_MAIN + "groupPurchaseOrderPreview";
    /**
     * 查询用户 已购买代金券 列表
     */
    public static final String URL_FIND_GROUP_PURCHASE_ORDER_COUPON_CODE_LIST = URL_HOME_MAIN + "findGroupPurchaseOrderCouponCodeList";

    /**
     * ----------------------------------------新信息发布--------------------------------------------
     */

    public static final String URL_INFORMATION_MAIN = "http://" + (BuildConfig.IS_DEBUG ? PreferenceUtils.getSelectUrl(App.getInstance()) : ONLINE_IP) + "/merchant/informationClient?m="; //120.24.16.64正式123.56.15.86
    // 查询信息发布基础信息
    public static final String URL_FIND_IN_BASIC_INFORMATION = URL_INFORMATION_MAIN + "findInBasicInformation";
    // 查询信息发布分类信息
    public static final String URL_FIND_INFORMATION_SERVICE_CATEGORY_LIST = URL_INFORMATION_MAIN + "findInformationServiceCategoryList";
    // 发送短信验证码
    public static final String URL_NEW_SEND_RELEASE_INFORMATION_SMS = URL_INFORMATION_MAIN + "newSendReleaseInformationSms";
    // 发布信息
    public static final String URL_CREATE_INFORMATION = URL_INFORMATION_MAIN + "createInformation";
    // 查询信息详情
    public static final String URL_FIND_INFORMATION_BY_ID = URL_INFORMATION_MAIN + "findInformationById";
    // 举报信息
    public static final String URL_REPORT_INFORMATION = URL_INFORMATION_MAIN + "reportInformation";
    // 查询信息发布收费标准列表
    public static final String URL_FIND_INFORMATION_FEE_STANDARD_LIST = URL_INFORMATION_MAIN + "findInformationFreeStandardList";
    //
    public static final String URL_CREATE_INFORMATION_ORDER = URL_INFORMATION_MAIN + "createInformationOrder";
    // 查询信息收费方式
    public static final String URL_FIND_INFORMATION_CHARGE_TYPES = URL_INFORMATION_MAIN + "findInformationChargeTypes";
    // 信息余额支付
    public static final String URL_INFORMATION_BALANCE_PAY = URL_INFORMATION_MAIN + "informationBalancePay";
    // 信息ping++支付
    public static final String URL_INFORMATION_PINGXX_PAY = URL_INFORMATION_MAIN + "informationPingxxPay";
    // 查询招聘热门职位分类
    public static final String URL_FIND_INFO_HOT_CATEGORY_LIST = URL_INFORMATION_MAIN + "findInformationHotCategoryList";
    // 查询信息列表
    public static final String URL_FIND_INFORMATION_LIST = URL_INFORMATION_MAIN + "findInformationList";
    // 删除信息
    public static final String URL_DELETE_INFORMATION_BY_ID = URL_INFORMATION_MAIN + "deleteInformation";
    // 刷新信息
    public static final String URL_REFRESH_INFORMATION_BY_ID = URL_INFORMATION_MAIN + "refreshInformation";
    // 查询发布信息订单列表
    public static final String URL_FIND_INFORMATION_ORDER_LIST = URL_INFORMATION_MAIN + "findInformationOrderList";
    // 查询广告位
    public static final String URL_FIND_INFORMATION_BANNER_LOCATION = URL_INFORMATION_MAIN + "findInformationBannerLocations";

    //新版本查询家教教师身份
    public static final String URL_FIND_NEW_EDUCATION_TEACHER_TYPE = URL_INFORMATION_MAIN + "findEducationTeacherTypeList";
    //新版本查询家教辅导阶段
    public static final String URL_FIND_NEW_EDUCATION_TUTORSHIP_STAGE = URL_INFORMATION_MAIN + "findEducationTutorshipStageList";

    /**
     * [违章查询]创建/修改违章查询车辆
     */
    public static final String URL_CREATE_OR_MERGE_ILLEGALQUERY_CAR = URL_HOME_MAIN + "createOrMergeIllegalQueryCar";
    /**
     * [违章查询]违章查询，返回结果
     */
    public static final String URL_RETURN_ILLEGAL_QUERY_RESULT = URL_HOME_MAIN + "returnIllegalQueryResult";
    /**
     * [违章查询]查询车辆列表
     */
    public static final String URL_FIND_ILLEGAL_QUERY_CAR_LIST = URL_HOME_MAIN + "findIllegalQueryCarList";
    /**
     * [违章查询]查询车辆信息详情
     */
    public static final String URL_FIND_iLLEGAL_QUERY_CAR_BY_ID = URL_HOME_MAIN + "findIllegalQueryCarById";
    /**
     * [违章查询]车辆品牌数据
     */
    public static final String URL_FIND_CAR_BRAMDS_LIST = URL_HOME_MAIN + "findCarBrandsList";
    /**
     * [违章查询]查询广告图
     */
    public static final String URL_FIND_ILLEGAL_QUERY_BANNER_LIST = URL_HOME_MAIN + "findIllegalQueryBannerList";
    /**
     * [违章查询]删除车辆
     */
    public static final String URL_DEL_ILLEGAL_QUERY_CAR_BY_ID = URL_HOME_MAIN + "delIllegalQueryCarById";
    /**
     * 查询当前定位A1B2代理商
     */
    public static final String URL_FIND_AGENT_BY_USER_XY = URL_HOME_MAIN + "findAgentByUserXY";
    /**
     * [黄页广告]查询全部广告图
     */
    public static final String URL_FIND_YELLOW_PAGE_BANNER = URL_HOME_MAIN + "findYellowPageBannerList";
    /**
     * [黄页信息]查询黄页分类列表
     */
    public static final String URL_FIND_YELLOW_PAGE_TYPE = URL_HOME_MAIN + "findYellowPageType";
    /**
     * [黄页信息]查询黄页列表
     */
    public static final String URL_FIND_YELLOW_PAGE_LIST = URL_HOME_MAIN + "findYellowPageList";
    /**
     * [黄页信息]根据编号查询黄页详情
     */
    public static final String URL_FIND_YELLOW_PAGE_BY_ID = URL_HOME_MAIN + "findYellowPageById";
    /**
     * 邀请返现活动 邀请人 返现战果列表
     */
    public static final String URL_FIND_INVITE_CASHBACK_DETAIL_lIST = URL_HOME_MAIN + "findInviteCashbackDetailList";
    /**
     * 邀请返现活动 邀请人 邀请地址
     */
    public static final String URL_FIND_INVITER_CODE_URL = URL_HOME_MAIN + "findInviterCodeUrl";
    /**
     * 邀请返现活动 查询邀请人 邀请的用户列表和总返现金额(战果)
     */
    public static final String URL_USER_LIST_AND_CASHBACK_AMT_SUM = URL_HOME_MAIN + "findUserListAndCashbackAmtSum";
    /**
     * 查询推送设置
     */
    public static final String URL_FIND_PUSH_SET = URL_HOME_MAIN + "findUserPushSet";
    /**
     * 设置是否推送
     */
    public static final String URL_UPDATE_USER_PUSH_SET = URL_HOME_MAIN + "updateUserPushSet";
    /**
     * 根据定位坐标 查询当前位置所属代理商(A1B1)和首页版本配置
     */
    public static final String URL_FIND_APP_HOME_PAGE_VERSION = URL_NEW_HOME_MAIN + "findAppHomePageVersion";
    /**
     * 根据定位坐标查询到代理商(A1B1)编号和菜单类型(1：主菜单[需要代理商编号]，2：便民服务[不需要代理商编号])查询应用首页菜单(状态正常，自定义排序编号 正序)
     */
    public static final String URL_FIND_APP_MENU_LIST = URL_NEW_HOME_MAIN + "findAppMenuList";
    /**
     * 首页返回优质商家列表
     */
    public static final String URL_FIND_HIGH_QUALITY_MERCHANT_LIST = URL_NEW_HOME_MAIN + "findHighQualityMerchantList";
    /**
     * 根据定位坐标查询到代理商(A1B1)编号 查询首页马管家公告(头条)
     */
    public static final String URL_FIND_APP_BROADCAST_LIST = URL_NEW_HOME_MAIN + "findAppBroadcastList";
    /**
     * 根据定位坐标查询到代理商(A1B1)编号 查询首页广告图
     */
    public static final String URL_FIND_APP_BANNER_LIST = URL_NEW_HOME_MAIN + "findAppBannerList";
    /**
     * 根据定位坐标查询到代理商(A1B1)编号 查询首页推广位和静态横屏广告
     */
    public static final String URL_FIND_APP_PROMOTION_BIT_MAP = URL_NEW_HOME_MAIN + "findAppPromotionBitMap";
    /**
     * 首页信息展示数据 positionData 个人求职 recruitData 招聘信息 leaseData 房屋租赁 newsInformationData 新闻资讯
     */
    public static final String RETURN_INFORMATION_DATA = URL_NEW_HOME_MAIN + "returnInformationData";
    /**
     * 查询首页管家头条数据
     */
    public static final String URL_FIND_BROADCAST_NEW_LIST = URL_NEW_HOME_MAIN + "findAppHeadlineNews";
    /**
     * 首页返回推荐商品列表
     */
    public static final String URL_RECOMMEND_GOODS_LIST = URL_NEW_HOME_MAIN + "findRecommendGoodsList";
    /**
     * 返回生效的节日氛围UI状态
     */
    public static final String URL_GET_FESTIVAL_STATUS = URL_HOME_MAIN + "findFestivalBannerReturnOn";
    /**
     * 返回应用开启动图
     */
    public static final String URL_GET_OPEN_GIF = URL_HOME_MAIN + "findGifFestivalBannerReturnOn";
    /**
     * 查询跑腿订单详情
     */
    public static final String URL_FIND_LEG_WORK_ORDER_BY_ORDER_ID = URL_HOME_MAIN + "findLegWorkOrderByOrderId";
    /**
     * 查询跑腿骑手信息
     */
    public static final String URL_FIND_DELIVERY_MAN_INFO = URL_HOME_MAIN + "findDeliveryman";
    /**
     * 查询跑腿骑手实时位置
     */
    public static final String URL_FIND_DELIVERY_MAN_BY_ORDER_ID = URL_HOME_MAIN + "findDeliverymanByOrderId";
    /**
     * 创建跑腿订单
     */
    public static final String URL_CREATE_LEG_WORK_ORDER = URL_HOME_MAIN + "createLegWorkOrder";
    /**
     * 修改跑腿订单详情
     */
    public static final String URL_MEGER_LEG_WORK_ORDER = URL_HOME_MAIN + "megerLegWorkOrder";
    /**
     * 创建跑腿评价
     */
    public static final String URL_CREATE_COMMENTS = URL_HOME_MAIN + "createComments";
    /**
     * 计算跑腿服务费
     */
    public static final String URL_CALCULATE_SERVICE_CHARGE = URL_HOME_MAIN + "calculateServiceCharge";
    /**
     * 跑腿查询页面数据
     */
    public static final String URL_GET_LEGWORK_DATA = URL_HOME_MAIN + "findlegWorkEntity";
    /**
     * 跑腿意见反馈
     */
    public static final String URL_CREATE_FEEDBACK = URL_HOME_MAIN + "createFeedback";
    /**
     * 抽奖开关
     */
    public static final String URL_GET_LOTTERY_STSTUS = URL_LOTTERY + "homePageGuideMap";
    /**
     * 抽奖跳转逻辑
     */
    public static final String URL_GET_LOTTERY_JUMP = URL_LOTTERY + "linkPage";
    /**
     * 通过id查询商品
     */
    public static final String URL_QUERY_GOODS_BY_ID = URL_HOME_MAIN + "findTGoodsById";
    /**
     * 查询订单退款详情
     */
    public static final String URL_QUERY_REFUND_INFO = URL_HOME_MAIN + "refundInfo";
    /**
     * 查询领取红包集合
     */
    public static final String URL_GET_PLATFORM_REDBAG = URL_HOME_MAIN + "getPlatformRedBag";


    public static final int LOCATION_SUCCESS = 233;
    public static final int LOCATION_FAIL = 234;
    public static final int NO_NET = 500;
    public static final int LOCATION_NO_MERCHANT = 501;

}
