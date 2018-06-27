package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Banner;
import com.project.mgjandroid.bean.BaseProperty;
import com.project.mgjandroid.bean.Broadcast;
import com.project.mgjandroid.bean.CategoryLeftBean;
import com.project.mgjandroid.bean.CategoryRightBean;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.bean.PrimaryPublicity;
import com.project.mgjandroid.bean.RecommendCategory;
import com.project.mgjandroid.bean.RecommendCategoryGoods;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.AddressManageModel;
import com.project.mgjandroid.model.BroadcastModel;
import com.project.mgjandroid.model.CommercialListModel;
import com.project.mgjandroid.model.FindAgentModel;
import com.project.mgjandroid.model.FindCategoryModel;
import com.project.mgjandroid.model.HomeBannerModel;
import com.project.mgjandroid.model.LotteryStatusModel;
import com.project.mgjandroid.model.MerchantFilterModel;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.MerchantPropertyListEntity;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.PromotionListEntity;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.ShipmentListEntity;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.model.PrimaryCategoryModel;
import com.project.mgjandroid.model.PrimaryPublicityModel;
import com.project.mgjandroid.model.RecommendCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.CommodityDetailActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.LocationNewActivity;
import com.project.mgjandroid.ui.activity.OldHomeActivity;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
import com.project.mgjandroid.ui.activity.SearchActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingCategoryActivity;
import com.project.mgjandroid.ui.adapter.HomeRestaurantAdapter;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopChildAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopGroupAdapter;
import com.project.mgjandroid.ui.view.AlwaysMarqueeTextView;
import com.project.mgjandroid.ui.view.CommonPopupWindow;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.FreeView;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.NoticeView;
import com.project.mgjandroid.ui.view.SegmentedGroup;
import com.project.mgjandroid.ui.view.autoscrollviewpager.AutoScrollViewPager;
import com.project.mgjandroid.ui.view.autoscrollviewpager.indicator.CirclePageIndicator;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首頁
 *
 * @author jian
 */
public class HomeFragment extends BaseFragment implements OnClickListener, OnBannerItemClickListener, RadioGroup.OnCheckedChangeListener {

    private final static String TAG = "HomeFragment";

    public static final int IS_LINK = 1;
    public static final int IS_CATEGORY = 2;
    public static final int IS_MERCHANT = 3;
    public static final int IS_GROUPBUY = 4;
    public static final int TARGET_ID = 918;
    protected Activity mActivity;
    protected View view;
    protected boolean refreshFlag = true;
    private PullToRefreshListView listView;
    private LinearLayout listHeaderButton;
    private CirclePageIndicator navigatorIndicator;
    private LinearLayout menuBar;
    private TextView tvMenu1;
    private TextView tvMenu2;
    private TextView tvMenu3;
    private TextView tvLVMenu1;
    private TextView tvLVMenu2;
    private TextView tvLVMenu3;
    List<String> imageIdList;
    List<Banner> mBannerList;
    private HomeRestaurantAdapter adapter;
    private AlwaysMarqueeTextView tvAdress;
    private static final int maxResults = 10;
    private int currentResultPage = 0;
    private List<View> viewList = new ArrayList<>();
    private MyBanner myBanner;
    private PopupWindow leftMenuWindow;
    private PopupWindow midMenuWindow;
    private PopupWindow rightMenuWindow;
    private Drawable rightDrawableOrange;
    private Drawable rightDrawableGray;
    private LinearLayout hasNoNet;
    private LinearLayout locateFail;
    private String[] names = new String[]{"智能排序", "距离最近", "销量最高", "起送价最低", "配送速度最快", "评分最高"};
    private int[] heads = new int[]{R.drawable.head_01, R.drawable.head_02, R.drawable.head_03,
            R.drawable.head_04, R.drawable.head_05, R.drawable.head_06};
    private int[] sortIds = new int[]{1, 2, 3, 4, 5, 6};
    private MerchantFilterModel.ValueEntity filterValue;
    private int tagId = -1;
    private int tagParentId = -1;
    private SegmentedGroup shipmentLinear;
    private FlowLayout propertyLinear;
    private LinearLayout promotionLinear;
    private HomeSortAdapter homeSortAdapter;
    private int midPrePosition = -1;

    private int leftMenuCurrentGroup = -1, leftMenuCurrentChild = -1;
    private TextView reload, hasNoNetMsg;
    private ImageView hasNoNetIcon;
    private CustomDialog dialog;

    private ImageView ivSearch;
    private ImageView ivBack;
    private View titleBarBg;
    private View titleBarBg1;
    private RelativeLayout navigatorLayout;
    private LoadingDialog mLoadingDialog;
    private LeftMenuPopGroupAdapter mCategoryLeftAdapter;
    private int mPrePosition;
    private int mSelectPosition;
    private int mSelectChildPosition = -1;
    private LinearLayout broadcastLayout;
    private NoticeView noticeView;
    private List<Broadcast> broadcastList = new ArrayList<>();
    private LinearLayout publicityContainer;
    private LinearLayout publicityBottomContainer;
    private LinearLayout recommendLayout;
    private ImageView publicityImg;
    private List<ImageView> publicityViews = new ArrayList<>();
    private List<PrimaryPublicity> publicityList = new ArrayList<>();
    private List<RecommendCategory> recommendList = new ArrayList<>();
    private int SCREEN_WIDTH;
    private RelativeLayout merchantDividerLayout;
    private LinearLayout listHeaderView;
    //    private boolean isFirstIn = true;
    private NoticeDialog noticeDialog;
    private Long agentId = 0l;
    private FreeView freeViewLottery;
    private boolean isOld;
    private int newAgentId;

    private CommonPopupWindow window;
    private ImageView ivLottery;
    private ImageView ivPopupBack;
    private ImageView ivPopupLottery;
    private ListView childListView;
    private List<UserAddress> userAddressList;
    private PopupWindow popupWindow;
    private boolean isShowPop = true;
    private boolean isFail = false;
    private Handler handler;
    private static HomeFragment fragment;

    public Handler getHandler() {
        return handler;
    }

    public static HomeFragment newInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.home_fragment, container, false);
        mActivity = getActivity();
        getAddressList();
        initHandle();
        mLoadingDialog = new LoadingDialog(mActivity);
        initData();
        initViews();
        checkNet();
        return view;
    }

    private void checkNet() {
        hasNoNet = (LinearLayout) view.findViewById(R.id.home_fragment_no_net);
        reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
        hasNoNetIcon = (ImageView) hasNoNet.findViewById(R.id.home_fragment_img_nonet);
        hasNoNetMsg = (TextView) hasNoNet.findViewById(R.id.home_fragment_msg_nonet);
        hasNoNet.setOnClickListener(this);
        if (!NetworkUtils.isConnected(mActivity)) {
            hasNoNet.setVisibility(View.VISIBLE);
            hasNoNetIcon.setImageResource(R.drawable.has_no_net);
            hasNoNetMsg.setText("未能连接到互联网");
            reload.setText("刷新重试");
            reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isConnected(mActivity)) {
                        return;
                    }
                    hasNoNet.setVisibility(View.GONE);
                    handler.sendEmptyMessage(0);
                }
            });
        } else {
            mLoadingDialog.show();
        }
    }

    private void initHandle() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (listView.isRefreshing()) {
                    listView.onRefreshComplete();
                }
                switch (msg.what) {
                    case 0:
                        titleBarBg.setAlpha(0);
                        tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                        tvAdress.setTextColor(getResources().getColor(R.color.white));
                        Drawable drawableLeft = getResources().getDrawable(R.drawable.local_white);
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        Drawable drawableRight = getResources().getDrawable(R.drawable.nabla);
                        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                        tvAdress.setCompoundDrawables(drawableLeft, null, drawableRight, null);
                        ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                        ivSearch.setImageResource(R.drawable.icon_search);
                        mLoadingDialog.show();
                        ((HomeActivity) getActivity()).registeLocation();
                        break;
                    case Constants.LOCATION_SUCCESS:
                        locateFail.setVisibility(View.GONE);
                        break;
                    case Constants.LOCATION_FAIL:
                        isFail = true;
                        String address = PreferenceUtils.getAddressName(App.getInstance());
                        if (CheckUtils.isNoEmptyStr(address) && App.isLogin()) {
                            if (!address.equals(tvAdress.getText().toString())) {
                                ToastUtils.displayMsg("已切换到" + address, mActivity);
                                showAddress();
                            }
                            MineFragment mineFragment = MineFragment.newInstance();
                            if (mineFragment != null) {
                                mineFragment.getLocation(Double.parseDouble(PreferenceUtils.getLocation(mActivity)[0]), Double.parseDouble(PreferenceUtils.getLocation(mActivity)[1]));
                            }
                        } else if (App.isLogin() && userAddressList.size() > 0 && CheckUtils.isEmptyStr(address) && CheckUtils.isEmptyStr(address)) {
                            mPopupWindow(userAddressList);
                            UserAddress info = userAddressList.get(0);
                            if (info != null) {
                                PreferenceUtils.saveAddressName(info.getAddress(), mActivity);
                                if (!TextUtils.isEmpty(info.getHouseNumber())) {
                                    PreferenceUtils.saveAddressDes(info.getHouseNumber(), mActivity);
                                } else {
                                    PreferenceUtils.saveAddressDes("", mActivity);
                                }
                                PreferenceUtils.saveLocation(Double.toString(info.getLatitude()), Double.toString(info.getLongitude()), mActivity);
                                showAddress();
                            }
                        } else {
                            titleBarBg.setAlpha(1);
                            tvAdress.setBackgroundResource(0);
                            tvAdress.setTextColor(getResources().getColor(R.color.title_tv_festival));
                            Drawable drawableLeft2 = getResources().getDrawable(R.drawable.local_white);
                            drawableLeft2.setBounds(0, 0, drawableLeft2.getMinimumWidth(), drawableLeft2.getMinimumHeight());
                            Drawable drawableRight2 = getResources().getDrawable(R.drawable.nabla);
                            drawableRight2.setBounds(0, 0, drawableRight2.getMinimumWidth(), drawableRight2.getMinimumHeight());
                            tvAdress.setCompoundDrawables(drawableLeft2, null, drawableRight2, null);
                            ivSearch.setBackgroundResource(0);
                            ivSearch.setImageResource(R.drawable.icon_search);
                            mLoadingDialog.dismiss();
                            locateFail.setVisibility(View.VISIBLE);
//                    ToastUtils.displayMsg("定位失败，请检查网络或定位是否打开", mActivity);
                            hasNoNet.setVisibility(View.GONE);
                            TextView locateByOneself = (TextView) locateFail.findViewById(R.id.home_fragment_locate_by_oneself);
                            TextView locateReload = (TextView) locateFail.findViewById(R.id.home_fragment_locate_reload);
                            locateByOneself.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mActivity, LocationNewActivity.class);
                                    intent.putExtra("curAddress", tvAdress.getText().toString());
                                    startActivityForResult(intent, ActRequestCode.LOCATION);
                                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                                }
                            });
                            locateReload.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((HomeActivity) getActivity()).registeLocation();
                                }
                            });
                        }
                        break;
                    case Constants.NO_NET:
                        titleBarBg.setAlpha(1);
                        tvAdress.setBackgroundResource(0);
                        tvAdress.setTextColor(getResources().getColor(R.color.title_tv_festival));
                        Drawable drawableLeft3 = getResources().getDrawable(R.drawable.local_white);
                        drawableLeft3.setBounds(0, 0, drawableLeft3.getMinimumWidth(), drawableLeft3.getMinimumHeight());
                        Drawable drawableRight3 = getResources().getDrawable(R.drawable.nabla);
                        drawableRight3.setBounds(0, 0, drawableRight3.getMinimumWidth(), drawableRight3.getMinimumHeight());
                        tvAdress.setCompoundDrawables(drawableLeft3, null, drawableRight3, null);
                        ivSearch.setBackgroundResource(0);
                        ivSearch.setImageResource(R.drawable.icon_search);
                        hasNoNet.setVisibility(View.VISIBLE);
                        hasNoNetIcon.setImageResource(R.drawable.has_no_net);
                        hasNoNetMsg.setText("未能连接到互联网");
                        reload.setText("刷新重试");
                        reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
                        reload.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!NetworkUtils.isConnected(mActivity)) {
                                    return;
                                }
                                hasNoNet.setVisibility(View.GONE);
                                handler.sendEmptyMessage(0);
                            }
                        });
                        break;
                    case Constants.LOCATION_NO_MERCHANT:
                        showNavigateDialog();
                        break;
                }
            }
        };
    }


    //附近无商家提示切换地址
    private void showNavigateDialog() {
        if (dialog == null) {
            dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
//                    Intent intent = new Intent(mActivity, FindDefaultAddressActivity.class);
                    Intent intent = new Intent(mActivity, LocationNewActivity.class);
                    intent.putExtra("curAddress", tvAdress.getText().toString());
                    intent.putExtra("isSelectAddress", true);
                    startActivityForResult(intent, ActRequestCode.LOCATION);
                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
//                    hasNoNet.setVisibility(View.GONE);
                }
            }, "切换", "取消", "温馨提示", "抱歉，您所在的区域正加急开通商家，请切换到已开通商家的区域。");
        }
        showAddress();

        dialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Merchant merchant : adapter.getList()) {
            merchant.setPickGoodsCount(0);
        }
        if (CheckUtils.isNoEmptyList(PickGoodsModel.getInstance().getMerchantPickGoodsList())) {
            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                for (Merchant merchant : adapter.getList()) {
                    if (merchant.getId() != null && merchant.getId() == merchantPickGoods.getMerchantId()) {
                        merchant.setPickGoodsCount(merchantPickGoods.getGoodsCount());
                        break;
                    }
                }
            }
        }
        adapter.setList(adapter.getList());
        if (noticeView != null && CheckUtils.isNoEmptyList(broadcastList)) {
            if (noticeView.getmNoticeList() == null) {
                noticeView.setNoticeList(broadcastList);
            }
            noticeView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (noticeView != null) {
            noticeView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        imageIdList = new ArrayList<>();
        mBannerList = new ArrayList<>();

        isOld = getArguments().getBoolean("isOld");
        newAgentId = PreferenceUtils.getIntPreference("agentId", -1, mActivity);
        if (isOld){
            showAddress();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 51 && App.isLogin()) {
            // 判断是否绑定手机卡
            if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                ToastUtils.displayMsg("绑定手机号即可参与抽奖哦", mActivity);
                Intent intent = new Intent(mActivity, BindMobileActivity.class);
                startActivityForResult(intent, 52);
            } else {
                // 抽奖弹框跳转
                getLotteryJump();
            }
        } else if (requestCode == 52 && App.isLogin()) {
            if (!TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                // 抽奖弹框跳转
                getLotteryJump();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getLotteryStatus() {
        VolleyOperater<LotteryStatusModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", newAgentId);
        operater.doRequest(Constants.URL_GET_LOTTERY_STSTUS, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    LotteryStatusModel.ValueBean value = ((LotteryStatusModel) obj).getValue();
                    if (value == null) {
                        return;
                    }
                    if (value.getSwitchSign() == 8) { //8是打开，7是关闭
                        showLotteryBtn();
                        HomeActivity activity = (HomeActivity) getActivity();
                        if (!activity.isLotteryShow) {
                            //未显示过 请求跳转逻辑 直接弹窗
                            showLotteryPopup();
                        }
                    } else {
                        //隐藏 抽奖按钮
                        if (freeViewLottery != null && freeViewLottery.getVisibility() == View.VISIBLE) {
                            freeViewLottery.setVisibility(View.GONE);
                        }
                    }
                } else {
                    //隐藏 抽奖按钮
                    if (freeViewLottery != null && freeViewLottery.getVisibility() == View.VISIBLE) {
                        freeViewLottery.setVisibility(View.GONE);
                    }
                }
            }
        }, LotteryStatusModel.class);
    }

    private void showLotteryBtn() {
        //开启动画
        ivLottery = (ImageView) view.findViewById(R.id.iv_lottery_draw);
        freeViewLottery = (FreeView) view.findViewById(R.id.free_lottery_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.frame_anim);
        ivLottery.setImageDrawable(animationDrawable);
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        ivLottery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity activity = (HomeActivity) getActivity();
                if (!activity.isLotteryShow) {
                    showLotteryPopup();
                } else {
                    if (!App.isLogin()) {
                        startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), 51);
                    } else {
                        //跳转页面
                        if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                            ToastUtils.displayMsg("绑定手机号即可参与抽奖哦", mActivity);
                            Intent intent = new Intent(mActivity, BindMobileActivity.class);
                            startActivityForResult(intent, 52);
                        } else {
                            // 抽奖弹框跳转
                            getLotteryJump();
                        }
                    }
                }
            }
        });
    }

    private void showLotteryPopup() {
        HomeActivity activity = (HomeActivity) getActivity();
        activity.isLotteryShow = true;

        if (window == null) {
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenHeight = metrics.heightPixels;

            window = new CommonPopupWindow(mActivity, R.layout.dialog_lottery, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.65)) {
                @Override
                protected void initView() {
                    View view = getContentView();
                    ivPopupBack = (ImageView) view.findViewById(R.id.iv_lottery_back);
                    ivPopupLottery = (ImageView) view.findViewById(R.id.iv_lottery_goto);
                }

                @Override
                protected void initEvent() {
                    ivPopupBack.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getPopupWindow().dismiss();
                        }
                    });

                    ivPopupLottery.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!App.isLogin()) {
                                startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), 51);
                            } else {
                                //跳转页面
                                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                                    ToastUtils.displayMsg("绑定手机号即可参与抽奖哦", mActivity);
                                    Intent intent = new Intent(mActivity, BindMobileActivity.class);
                                    startActivityForResult(intent, 52);
                                } else {
                                    // 抽奖弹框跳转
                                    getLotteryJump();
                                }
                            }
                            getPopupWindow().dismiss();
                        }
                    });
                }

                @Override
                protected void initWindow() {
                    super.initWindow();
                    PopupWindow instance = getPopupWindow();
                    instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                            lp.alpha = 1.0f;
                            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            mActivity.getWindow().setAttributes(lp);
                        }
                    });
                }
            };
            window.getPopupWindow().setAnimationStyle(R.style.animScale);
        }

        CommonPopupWindow.LayoutGravity layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_RIGHT | CommonPopupWindow.LayoutGravity.ALIGN_BOTTOM);
        window.showBashOfAnchor(ivLottery, layoutGravity, 0, 0);

        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mActivity.getWindow().setAttributes(lp);
    }

    private void getLotteryJump() {
        VolleyOperater<LotteryStatusModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", newAgentId);
        operater.doRequest(Constants.URL_GET_LOTTERY_JUMP, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    LotteryStatusModel lotteryStatusModel = (LotteryStatusModel) obj;
                    if (!lotteryStatusModel.isSuccess()) {
                        ToastUtils.displayMsg(lotteryStatusModel.getMsg(), mActivity);
                        return;
                    }
                    LotteryStatusModel.ValueBean value = lotteryStatusModel.getValue();
                    if (value.getType() == 1) { //1 网页链接 100001 用户信息不正确，跳转到用户登录界面
                        String lotteryUrl = value.getUrl();
                        HomeActivity activity = (HomeActivity) getActivity();
                        if (!activity.isLotteryShow) {
                            //未显示过 请求跳转逻辑 直接弹窗
                            showLotteryPopup();
                        } else {
                            if (!TextUtils.isEmpty(lotteryUrl)) {
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, lotteryUrl);
                                startActivity(intent);
                            }
                        }
                    } else if (value.getType() == 100001) {
                        mActivity.startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), 51);
                    } else if (value.getType() == 88888) {
                        ToastUtils.displayMsg(lotteryStatusModel.getMsg(), mActivity);
                    }
                }
            }
        }, LotteryStatusModel.class);
    }

    private void initViews() {
        SCREEN_WIDTH = DeviceParameter.getDisplayMetrics().widthPixels;
        RelativeLayout titleLayout = (RelativeLayout) view.findViewById(R.id.home_fragment_title_bar);
        titleLayout.setOnClickListener(this);
        ivSearch = (ImageView) view.findViewById(R.id.home_fragment_iv_search);
        ivBack = (ImageView) view.findViewById(R.id.home_fragment_iv_back);
        ivSearch.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvAdress = (AlwaysMarqueeTextView) view.findViewById(R.id.home_fragment_tv_address);
        titleBarBg = view.findViewById(R.id.home_fragment_title_bar_bg);
        titleBarBg1 = view.findViewById(R.id.home_fragment_title_bar_bg_1);
        menuBar = (LinearLayout) view.findViewById(R.id.home_fragment_menu_bar);
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.button_layout_1);
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.button_layout_2);
        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.button_layout_3);
        locateFail = (LinearLayout) view.findViewById(R.id.home_fragment_locate_fail);
        locateFail.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        tvMenu1 = (TextView) view.findViewById(R.id.tv_1);
        tvMenu2 = (TextView) view.findViewById(R.id.tv_2);
        tvMenu3 = (TextView) view.findViewById(R.id.tv_3);
        initListView();
        initListHeaderView();
        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        if (rightDrawableOrange != null) {
            rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        }
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        if (rightDrawableGray != null) {
            rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());
        }

        if (isOld) {
            ivBack.setVisibility(View.VISIBLE);
            tvAdress.setVisibility(View.GONE);
            titleLayout.setClickable(false);
        } else {
            ivBack.setVisibility(View.GONE);
            tvAdress.setVisibility(View.VISIBLE);
            titleLayout.setClickable(true);
        }

        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //获取抽奖开关状态
                if (!isOld) {
                    getLotteryStatus();
                }
            }
        });
    }

    /**
     * 广告栏
     */
    private void initListHeaderView() {
        listHeaderView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_list_header_layout, null);
        listHeaderButton = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_list_header_button, null);
        listHeaderButton.setVisibility(View.INVISIBLE);
        myBanner = (MyBanner) listHeaderView.findViewById(R.id.slideshowView);
        myBanner.setOnBannerItemClickListener(this);
        AutoScrollViewPager navigatorViewPager = (AutoScrollViewPager) listHeaderView.findViewById(R.id.home_list_header_navigator_view_pager);
        navigatorLayout = (RelativeLayout) listHeaderView.findViewById(R.id.home_list_header_navigator_flipperParent);
        navigatorViewPager.setNeedOnMeasure(true);
        if (PreferenceUtils.getBoolPreference("festivalStatus", false, mActivity)) {
            navigatorViewPager.setBackgroundResource(R.drawable.spring_bg_1);
        } else {
            navigatorViewPager.setBackgroundColor(getResources().getColor(R.color.white));
        }
//        LinearLayout bar = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.search_bar, null);
//        LinearLayout searchBar = (LinearLayout) bar.findViewById(R.id.search_bar);
//        searchBar.setOnClickListener(this);
        navigatorIndicator = (CirclePageIndicator) listHeaderView.findViewById(R.id.home_list_header_navigator_indicator);
        LinearLayout linearLayout1 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_1);
        LinearLayout linearLayout2 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_2);
        LinearLayout linearLayout3 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_3);
        tvLVMenu1 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_1);
        tvLVMenu2 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_2);
        tvLVMenu3 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_3);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

//        listView.getRefreshableView().addHeaderView(bar);
        listView.getRefreshableView().addHeaderView(listHeaderView);
        listView.getRefreshableView().addHeaderView(listHeaderButton);
        listView.setAdapter(adapter);
        // 添加滑动到底部的监听器
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                listView.setLodeMore(true);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!isOld && freeViewLottery != null) {
                    if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                        // 开始滑动 隐藏抽奖
                        freeViewLottery.showView(false);
                    } else if (scrollState == SCROLL_STATE_IDLE) {
                        // 滑动结束 开启显示抽奖的计时
                        freeViewLottery.startTimer();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 2) {
                    menuBar.setVisibility(View.VISIBLE);
                }
//                else {
//                    if (!((leftMenuWindow != null && leftMenuWindow.isShowing())
//                            || (midMenuWindow != null && midMenuWindow.isShowing())
//                            || (rightMenuWindow != null && rightMenuWindow.isShowing()))) {
//                        menuBar.setVisibility(View.GONE);
//                    }
//                }
                if (firstVisibleItem == 1) {
//                    MLog.e("头部滚动距离第一项：" + getScrollY() + "高度" + listView.getRefreshableView().getChildAt(0).getTop());
                    if (checkBarNeedVisible()) {
                        menuBar.setVisibility(View.VISIBLE);
                    } else {
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                }
//                if (firstVisibleItem >= 2) {
//                    if (!ivSearchVisible) {
//                        ivSearch.setVisibility(View.VISIBLE);
//                        AnimatorUtils.scaleIn(ivSearch);
//                    }
//                    ivSearchVisible = true;
//                } else {
//                    if (ivSearchVisible) {
//                        AnimatorUtils.scaleOut(ivSearch);
//                    }
//                    ivSearchVisible = false;
//                }
                if (firstVisibleItem == 1) {
                    if (getScrollY() < -30) {
                        tvAdress.setBackgroundResource(0);
                        ivSearch.setBackgroundResource(0);
                        ivSearch.setImageResource(R.drawable.icon_search);
                        tvAdress.setTextColor(getResources().getColor(R.color.title_tv_festival));
                        Drawable drawableLeft = getResources().getDrawable(R.drawable.local_white);
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        Drawable drawableRight = getResources().getDrawable(R.drawable.nabla);
                        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                        tvAdress.setCompoundDrawables(drawableLeft, null, drawableRight, null);
                    } else {
                        tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                        tvAdress.setTextColor(getResources().getColor(R.color.white));
                        Drawable drawableLeft = getResources().getDrawable(R.drawable.local_white);
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        Drawable drawableRight = getResources().getDrawable(R.drawable.nabla);
                        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                        tvAdress.setCompoundDrawables(drawableLeft, null, drawableRight, null);
                        ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                        ivSearch.setImageResource(R.drawable.icon_search);
                    }
                    titleBarBg.setAlpha(0.3f + Math.abs(getScrollY()) / DipToPx.dip2px(mActivity, 150) * 0.7f);
                }
                if (firstVisibleItem == 0) {
                    titleBarBg.setAlpha(0);
                    menuBar.setVisibility(View.INVISIBLE);
                    tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                    tvAdress.setTextColor(getResources().getColor(R.color.white));
                    Drawable drawableLeft = getResources().getDrawable(R.drawable.local_white);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    Drawable drawableRight = getResources().getDrawable(R.drawable.nabla);
                    drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                    tvAdress.setCompoundDrawables(drawableLeft, null, drawableRight, null);
                    ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                    ivSearch.setImageResource(R.drawable.icon_search);
                } else if (firstVisibleItem > 1) {
                    titleBarBg.setAlpha(1);
                }
                if (isShowPop()) {
                    menuBar.setVisibility(View.VISIBLE);
                }
            }
        });
        navigatorViewPager.setAdapter(mPageAdapter);
        navigatorIndicator.setViewPager(navigatorViewPager);
        navigatorIndicator.setRadius(getResources().getDimension(R.dimen.x3));
        navigatorIndicator.setOrientation(LinearLayout.HORIZONTAL);
        navigatorIndicator.setStrokeWidth(getResources().getDimension(R.dimen.x1));
        navigatorIndicator.setStrokeColor(0xffc9c9c9);
        navigatorIndicator.setSnap(false);
        navigatorIndicator.setFillColor(0xffff9900);
        navigatorViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        navigatorViewPager.stopAutoScroll();
        navigatorViewPager.setCurrentItem(0);
        navigatorViewPager.setCycle(false);
        navigatorViewPager.setBorderAnimation(true);

        broadcastLayout = (LinearLayout) listHeaderView.findViewById(R.id.broadcast_container);
        noticeView = (NoticeView) broadcastLayout.findViewById(R.id.notice_view);
        noticeView.setOnItemClickListener(new NoticeView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Broadcast b = (Broadcast) view.getTag();
                itemClick(b);
            }
        });

        publicityContainer = (LinearLayout) listHeaderView.findViewById(R.id.publicity_container);
        publicityBottomContainer = (LinearLayout) publicityContainer.findViewById(R.id.publicity_container_4);
        publicityImg = (ImageView) publicityContainer.findViewById(R.id.publicity_img_1);
        publicityViews.clear();
        publicityViews.add((ImageView) publicityBottomContainer.findViewById(R.id.publicity_img_2));
        publicityViews.add((ImageView) publicityBottomContainer.findViewById(R.id.publicity_img_3));
        publicityViews.add((ImageView) publicityBottomContainer.findViewById(R.id.publicity_img_4));
        publicityViews.add((ImageView) publicityBottomContainer.findViewById(R.id.publicity_img_5));
        recommendLayout = (LinearLayout) listHeaderView.findViewById(R.id.recommend_container);
        merchantDividerLayout = (RelativeLayout) listHeaderView.findViewById(R.id.merchant_divider_layout);
    }

    private boolean isShowPop() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            return true;
        }
        if (midMenuWindow != null && midMenuWindow.isShowing()) {
            return true;
        }
        if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            return true;
        }
        return false;
    }

    public float getScrollY() {
        View c = listView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return 0;
        }
        return (float) c.getTop();
    }

    public float getHeaderScrollY() {
        View v1 = listView.getRefreshableView().getChildAt(1);
        return listHeaderView.getHeight() - DipToPx.dip2px(mActivity, 48);
    }

    public boolean checkBarNeedVisible() {
        View c = listView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return false;
        }
//        MLog.e("头部第一项剩余高度：" + (c.getHeight() + c.getTop()));
        return (c.getHeight() + c.getTop()) < getResources().getDimensionPixelSize(R.dimen.title_bar_height);
    }

    @Override
    public void onItemClick(int position) {
        //轮播图点击的位置
        Banner bannerItem = mBannerList.get(position);
        int bannerType = bannerItem.getBannerType();
        switch (bannerType) {
            case IS_LINK:
                if (bannerItem.getUrl().startsWith("maguanjia://")) {
                    if (bannerItem.getUrl().replace("maguanjia://", "").startsWith("http")) {
                        Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                        intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl().replace("maguanjia://", ""));
                        startActivity(intent);
                    } else if ("maguanjia://supermarket".equals(bannerItem.getUrl())) {
                        //跳商超
                        if (mActivity != null)
                            if (mActivity instanceof HomeActivity) {
                                ((HomeActivity) mActivity).setToSuperMarket(0, 0);
                            }
                    } else {
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + bannerItem.getUrl().replace("maguanjia://", ""), new RouterCallback() {
                            @Override
                            public void notFound(Context context, Uri uri) {
                                showNoticeDialog();
                            }

                            @Override
                            public void beforeOpen(Context context, Uri uri) {
                            }

                            @Override
                            public void afterOpen(Context context, Uri uri) {
                            }

                            @Override
                            public void error(Context context, Uri uri, Throwable e) {
                            }
                        });
                    }
                } else if (bannerItem.getUrl().startsWith("http")) {
//                    Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                    intent.putExtra(Banner2WebActivity.URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
//                    intent.putExtra("name", mBannerList.get(position).getName());
//                    startActivity(intent);
                    Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                    startActivity(intent);
                }
                break;
            case IS_CATEGORY:
                Intent intent1 = new Intent(mActivity, PrimaryCategoryActivity.class);
                intent1.putExtra("tagCategoryId", bannerItem.getFirstCategoryId());
                intent1.putExtra("tagCategorySecondId", bannerItem.getSecondCategoryId());
                intent1.putExtra("tagCategoryType", bannerItem.getCategoryType());
                intent1.putExtra("categoryName", bannerItem.getName());
                startActivity(intent1);
                break;
            case IS_MERCHANT:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "merchant/" + bannerItem.getMerchantId());
                break;
            case IS_GROUPBUY:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bannerItem.getGroupBuyId());
                break;
            case 6: // 商超分类
                if (mActivity != null)
                    if (mActivity instanceof HomeActivity) {
                        ((HomeActivity) mActivity).setToSuperMarket(bannerItem.getFirstTGoodsCategoryId(), bannerItem.getSecondTGoodsCategoryId());
                    }
                break;
            case 7: // 团购商家
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bannerItem.getGroupPurchaseMerchantId());
                break;
        }
    }

    private MyPageAdapter mPageAdapter = new MyPageAdapter();

    private class MyPageAdapter extends PagerAdapter {

        private List<View> views = new ArrayList<>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setViews(List<View> views) {
            this.views = views;
            notifyDataSetChanged();
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

    }

    //添加分类导航视图
    private void initNavigatorViewPager(List<PrimaryCategory> primaryCategoryList) {
        navigatorLayout.setVisibility(View.VISIBLE);
        for (View view : viewList) {
            ((LinearLayout) view).removeAllViews();
        }
        viewList.clear();
        LinearLayout linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(llp);
        linearLayout.setPadding(0, (int) getResources().getDimension(R.dimen.x15), 0, (int) getResources().getDimension(R.dimen.x10));

        int rowLine = 1;
        if (primaryCategoryList.size() > 5) {
            rowLine = 2;
        }
        for (int i = 0; i < rowLine; i++) {
            LinearLayout row = new LinearLayout(mActivity);
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(llp3);
            if (i == 0) {
                for (int j = 0; j < (5 > primaryCategoryList.size() ? primaryCategoryList.size() : 5); j++) {
                    LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                    row.addView(mLayout);
                }
            } else {
                for (int j = 5; j < (10 > primaryCategoryList.size() ? primaryCategoryList.size() : 10); j++) {
                    LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                    row.addView(mLayout);
                }
            }
            linearLayout.addView(row);
        }
        viewList.add(linearLayout);

        if (primaryCategoryList.size() > 10) {
            LinearLayout linearLayout2 = new LinearLayout(mActivity);
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout2.setLayoutParams(llp2);

            for (int i = 0; i < 2; i++) {
                if (primaryCategoryList.size() == 15 && i == 1) break;
                LinearLayout row = new LinearLayout(mActivity);
                row.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(llp3);
                if (i == 0) {
                    for (int j = 10; j < (15 > primaryCategoryList.size() ? primaryCategoryList.size() : 15); j++) {
                        LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                        row.addView(mLayout);
                    }
                } else {
                    for (int j = 15; j < (20 > primaryCategoryList.size() ? primaryCategoryList.size() : 20); j++) {
                        LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                        row.addView(mLayout);
                    }
                }
                linearLayout2.addView(row);
            }
            viewList.add(linearLayout2);
        }

        mPageAdapter.setViews(viewList);
//        navigatorViewPager.setAdapter(mPageAdapter);

        if (primaryCategoryList.size() <= 10) {
            navigatorIndicator.setVisibility(View.GONE);
        } else {
            navigatorIndicator.setVisibility(View.VISIBLE);
        }
    }

    //添加分类导航单项视图
    private LinearLayout createNavigator(final PrimaryCategory primaryCategory) {
        final LinearLayout mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(SCREEN_WIDTH / 5,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, DipToPx.dip2px(mActivity, 15f));
        mLayout.setLayoutParams(llp);
        mLayout.setGravity(Gravity.CENTER);
        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (primaryCategory.getGraySwitch() == 0) {
                    if (primaryCategory.getGotoType() == 2) {
                        Intent intent = new Intent(mActivity, PrimaryCategoryActivity.class);
                        intent.putExtra("tagCategoryId", primaryCategory.getTagCategoryId());
                        intent.putExtra("tagCategoryType", primaryCategory.getTagCategoryType());
                        intent.putExtra("categoryName", primaryCategory.getName());
                        startActivity(intent);
                    } else if (primaryCategory.getGotoType() == 1) {
                        if (primaryCategory.getGotoUrl().replace("maguanjia://", "").startsWith("http")) {
                            Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl().replace("maguanjia://", ""));
                            startActivity(intent);
                        } else if ("maguanjia://supermarket".equals(primaryCategory.getGotoUrl())) {
                            //跳商超
                            if (mActivity != null)
                                if (mActivity instanceof HomeActivity) {
                                    ((HomeActivity) mActivity).setToSuperMarket(0, 0);
                                }
                        } else {
                            if (primaryCategory.getGotoUrl().startsWith("maguanjia://")) {
                                Routers.open(mActivity, ActivitySchemeManager.SCHEME + primaryCategory.getGotoUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {
                                    }
                                });
                            } else if (primaryCategory.getGotoUrl().startsWith("http")) {
//                                Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                                intent.putExtra(Banner2WebActivity.URL, primaryCategory.getGotoUrl());
//                                startActivity(intent);
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl());
                                startActivity(intent);
                            }
                        }
                    } else if (primaryCategory.getGotoType() == 4) {
                        GroupBuyingCategoryActivity.toGroupBuyingCategoryActivity(mActivity, primaryCategory.getName(), primaryCategory.getGroupPurchaseCategoryId(), primaryCategory.getChildGroupPurchaseCategoryId());
                    }
                } else {
                    ToastUtils.displayMsg("尚未开通，敬请期待", mActivity);
                }
            }
        });

        ImageView iv = new ImageView(mActivity);
        LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 44f),
                DipToPx.dip2px(mActivity, 44f));
        iv.setLayoutParams(ivlp);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.category_default);
        if (primaryCategory.getGraySwitch() == 0) {
            ImageUtils.loadBitmap(mActivity, primaryCategory.getPicUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, iv, R.drawable.category_default, "");
        } else {
            ImageUtils.loadBitmap(mActivity, primaryCategory.getGrayUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, iv, R.drawable.category_default, "");
        }

        TextView tv = new TextView(mActivity);
        tv.setText(primaryCategory.getName());
        tv.setTextColor(getResources().getColor(R.color.color_3));
        tv.setTextSize(12);
        tv.setGravity(Gravity.CENTER);
        tv.setIncludeFontPadding(false);
        LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tvlp.setMargins(0, getResources().getDimensionPixelSize(R.dimen.x10), 0, 0);
        tv.setLayoutParams(tvlp);
        mLayout.addView(iv);
        mLayout.addView(tv);
        return mLayout;
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }

    /**
     * 列表
     */
    private void initListView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.home_fragment_listView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
//        listView.setAddMoreCountText(maxResults);
        adapter = new HomeRestaurantAdapter(mActivity);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                checkRefresh(refreshView);
                if (refreshFlag) {
                    currentResultPage = 0;
                    getDate(false, false);
                    if (imageIdList == null || imageIdList.size() == 0) {
                        getBanner();
                    } else {
                        myBanner.setUrls(imageIdList, true, true);
                    }
                    if (viewList == null || viewList.size() == 0) {
                        getPrimaryCategory();
                    }
//                    if (CheckUtils.isEmptyList(broadcastList)) {
                    getBroadcast();
//                    }
//                    if (CheckUtils.isEmptyList(publicityList)) {
                    getPrimaryPublicity();
//                    }
//                    if (CheckUtils.isEmptyList(recommendList)) {
                    getRecommendCategory();
//                    }
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {
                if (value < 0) {
                    tvAdress.setVisibility(View.INVISIBLE);
                    ivSearch.setVisibility(View.INVISIBLE);
                } else if ((refreshView.getState() == null || refreshView.getState() != PullToRefreshBase.State.REFRESHING) && value >= 0) {
                    if (!getArguments().getBoolean("isOld")) {
                        tvAdress.setVisibility(View.VISIBLE);
                    }
                    ivSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                checkRefresh(refreshView);
                if (refreshFlag) {
                    currentResultPage = adapter.getCount();
                    getDate(false, true);
                }
            }
        });
    }

    private void checkRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        if (!NetworkUtils.isConnected(mActivity)) {
            hasNoNet.setVisibility(View.VISIBLE);
            hasNoNetIcon.setImageResource(R.drawable.has_no_net);
            hasNoNetMsg.setText("未能连接到互联网");
            reload.setText("刷新重试");
//                    listView.onRefreshComplete();
            TextView reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
            reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isConnected(mActivity)) {
                        return;
                    }
                    hasNoNet.setVisibility(View.GONE);
                    showAddress();
                }
            });
        }
    }

//    public void setIsFirstIn() {
//        isFirstIn = false;
//    }

    /**
     * 显示定位信息，并刷新列表
     */
    public void showAddress() {
        String address = PreferenceUtils.getAddressName(getActivity());
        Log.i(TAG, "showAddress::address:" + address);
        if (CheckUtils.isNoEmptyStr(address)) {
//            if (isFirstIn && App.isLogin()) {
//                isFirstIn = false;
//                if (mActivity instanceof HomeActivity) {
//                    ((HomeActivity) mActivity).getSkyRedbagActivity();
//                }
//            }
            if (!address.equals(tvAdress.getText().toString())) {
                tvAdress.setText(address);
                currentResultPage = 0;
                initPopupMenu();
//                getCategory();
//                getFilter();
//                getBanner();
//                getPrimaryCategory();
//                getBroadcast();
//                getPrimaryPublicity();
//                getRecommendCategory();
                listView.resetCurrentMode();
                //            listView.autoRefresh();
//                getDate(false, false);
                getAgentIdByXY();
                handler.sendEmptyMessage(Constants.LOCATION_SUCCESS);
            }
        } else {
            tvAdress.setText("未知位置");
            //定位失败
            doWhileLocationFail();
        }
        MineFragment mineFragment = MineFragment.newInstance();
        if (mineFragment != null) {
            mineFragment.getLocation(Double.parseDouble(PreferenceUtils.getLocation(mActivity)[0]), Double.parseDouble(PreferenceUtils.getLocation(mActivity)[1]));
        }
    }

    public void mPopupWindow(final List<UserAddress> userAddressList) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_address_ist, null);
        ImageView imgClose = (ImageView) view.findViewById(R.id.img_close);
        final TextView tv1 = (TextView) view.findViewById(R.id.text1);
        TextView tv2 = (TextView) view.findViewById(R.id.text2);
        TextView tv3 = (TextView) view.findViewById(R.id.text3);
        RelativeLayout rlOtherAddress = (RelativeLayout) view.findViewById(R.id.rl_other_address);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        if (userAddressList.size() > 0) {
            for (int i = 0; i < userAddressList.size(); i++) {
                if (i == 0) {
                    tv1.setText(userAddressList.get(i).getAddress());
                }
                if (i == 1) {
                    tv2.setText(userAddressList.get(i).getAddress());
                    tv2.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    tv3.setText(userAddressList.get(i).getAddress());
                    tv3.setVisibility(View.VISIBLE);
                }
            }
        }
        imgClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        tv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLocation(userAddressList.get(userAddressList.size() - 1));
            }
        });
        tv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLocation(userAddressList.get(userAddressList.size() - 2));
            }
        });
        tv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLocation(userAddressList.get(userAddressList.size() - 3));
            }
        });
        rlOtherAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

                Intent intent = new Intent(mActivity, LocationNewActivity.class);
                intent.putExtra("curAddress", tvAdress.getText().toString());
                startActivityForResult(intent, ActRequestCode.LOCATION);

            }
        });
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });

    }

    private void switchLocation(UserAddress info) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        mLoadingDialog.show();
        if (info != null) {
            PreferenceUtils.saveAddressName(info.getAddress(), mActivity);
            if (!TextUtils.isEmpty(info.getHouseNumber())) {
                PreferenceUtils.saveAddressDes(info.getHouseNumber(), mActivity);
            } else {
                PreferenceUtils.saveAddressDes("", mActivity);
            }
            PreferenceUtils.saveLocation(Double.toString(info.getLatitude()), Double.toString(info.getLongitude()), mActivity);
            showAddress();
        }
    }

    public void openPop() {
        if (popupWindow != null && !popupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    private void getAddressList() {
        Map<String, Object> map = new HashMap<String, Object>();
        VolleyOperater<AddressManageModel> operater = new VolleyOperater<AddressManageModel>(mActivity);
        operater.doRequest(Constants.URL_GET_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    userAddressList = ((AddressManageModel) obj).getValue();
                }
            }
        }, AddressManageModel.class);
    }

    private void initPopupMenu() {
        midPrePosition = -1;
        leftMenuCurrentChild = -1;
        leftMenuCurrentGroup = -1;
        if (filterValue != null && !isClearFilter())
            clearFilter();
        leftMenuWindow = null;
        midMenuWindow = null;
        rightMenuWindow = null;
        tvLVMenu1.setText("分类");
        tvMenu1.setText("分类");
        tvLVMenu2.setText("排序");
        tvMenu2.setText("排序");
        tagId = -1;
        tagParentId = -1;
    }

    private void doWhileLocationFail() {
        if (!NetworkUtils.isConnected(mActivity)) {
            handler.sendEmptyMessage(Constants.NO_NET);
        } else {
            handler.sendEmptyMessage(Constants.LOCATION_FAIL);
        }
    }


    private void addActivityImages(List<Banner> bannerList) {
        imageIdList.clear();
        mBannerList.clear();
        for (Banner banner : bannerList) {
            imageIdList.add(banner.getPicUrl());
            mBannerList.add(banner);
        }
        myBanner.setUrls(imageIdList, true, true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_fragment_title_bar:
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
                }
//                listView.scrollTo(0,0);titleBarBg1.setVisibility(View.GONE);
                Intent intent = new Intent(mActivity, LocationNewActivity.class);
                intent.putExtra("curAddress", tvAdress.getText().toString());
                startActivityForResult(intent, ActRequestCode.LOCATION);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                break;

            case R.id.button_layout_listview_1:

                if (menuBar.getVisibility() == View.INVISIBLE) {
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_1:
                checkListCount();
                tvMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (leftMenuWindow == null) {
                    showLeftMenuPop();
                } else if (leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                    resetListView();
                    if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    List<CategoryLeftBean> list = mCategoryLeftAdapter.getList();
                    list.get(mPrePosition).setIsChecked(false);
                    list.get(mSelectPosition).setIsChecked(true);
                    List<CategoryRightBean> list1 = list.get(mSelectPosition).getChildTagCategoryList();
                    if (list1 != null) {
                        mCategoryRightAdapter.setList(list1);
                        if (childListView != null) {
                            childListView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mCategoryRightAdapter.setList(new ArrayList<CategoryRightBean>());
                        if (childListView != null) {
                            childListView.setVisibility(View.GONE);
                        }
                    }
                    leftMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
//                    resetListView();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.button_layout_listview_2:

                if (menuBar.getVisibility() == View.INVISIBLE) {
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_2:
                checkListCount();

                tvMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (midMenuWindow == null) {
                    showMidMenuPop();
                } else if (midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                    resetListView();
                    if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    midMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
//                    resetListView();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.button_layout_listview_3:
                if (menuBar.getVisibility() == View.INVISIBLE) {
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_3:
                checkListCount();
                if (filterValue != null) {
                    setFilterState();
                }
                tvMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                        resetListView();
                        if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                            menuBar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        rightMenuWindow.showAsDropDown(menuBar, 0, 0);
                    }
                }
//                else {
//                    getFilter();
//                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
//                    resetListView();
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.home_fragment_menu_left_cover_view:
                leftMenuWindow.dismiss();
                resetListView();
                if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_mid_cover_view:
                midMenuWindow.dismiss();
                resetListView();
                if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_right_cover_view:
                rightMenuWindow.dismiss();
                resetListView();
                if (menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3) {
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_right_clear:
                rightMenuWindow.dismiss();
                resetListView();
                if (menuBar.getVisibility() == View.VISIBLE) {
                    menuBar.setVisibility(View.INVISIBLE);
                }
                clearFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;
            case R.id.home_fragment_menu_right_confirm:
                rightMenuWindow.dismiss();
                resetListView();
                if (menuBar.getVisibility() == View.VISIBLE) {
                    menuBar.setVisibility(View.INVISIBLE);
                }
                confirmFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;
            case R.id.home_fragment_locate_fail:
                break;
            case R.id.home_fragment_no_net:
                break;
            case R.id.clear_filter_condition:
                initPopupMenu();
                getDate(true, false);
                break;
            case R.id.search_bar:
            case R.id.home_fragment_iv_search:
                startActivityForResult(new Intent(mActivity, SearchActivity.class), 0);
                break;
            case R.id.group_category:

                break;
            case R.id.home_fragment_iv_back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void checkListCount() {
        if (adapter.getCount() > 4) {
            listView.getRefreshableView().setSelection(2);
        } else {
            listView.scrollTo(0, (int) getHeaderScrollY());
            titleBarBg1.setVisibility(View.VISIBLE);
        }
    }

    private void resetListView() {
        titleBarBg1.setVisibility(View.GONE);
        listView.scrollTo(0, 0);
    }

    private void confirmFilter() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            if (ship.isCheck()) {
                ship.setIsConfirm(true);
            } else {
                ship.setIsConfirm(false);
            }
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if (merchantProperty.isCheck()) {
                merchantProperty.setIsConfirm(true);
            } else {
                merchantProperty.setIsConfirm(false);
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            if (promotion.isCheck()) {
                promotion.setIsConfirm(true);
            } else {
                promotion.setIsConfirm(false);
            }
        }
    }

    private void clearFilter() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            ship.setIsCheck(false);
            ship.setIsConfirm(false);
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            merchantProperty.setIsCheck(false);
            merchantProperty.setIsConfirm(false);
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            promotion.setIsCheck(false);
            promotion.setIsConfirm(false);
        }
    }

    private boolean isClearFilter() {
        if (filterValue == null) {
            return true;
        }
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            if (ship.isConfirm()) {
                return false;
            }
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if (merchantProperty.isConfirm()) {
                return false;
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            if (promotion.isConfirm()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置按钮3的界面状态
     */
    private void setFilterState() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        int temp = 0;
        for (int i = 0; i < shipmentList.size(); i++) {
            ShipmentListEntity shipmentListEntity = shipmentList.get(i);
            if (shipmentListEntity.isConfirm()) {
                ((RadioButton) shipmentLinear.getChildAt(i)).setChecked(true);
            } else {
                temp++;
                ((RadioButton) shipmentLinear.getChildAt(i)).setChecked(false);
            }
        }
        if (temp == shipmentList.size()) {
            shipmentLinear.clearCheck();
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (int i = 0; i < merchantPropertyList.size(); i++) {
            MerchantPropertyListEntity merchantPropertyListEntity = merchantPropertyList.get(i);
            if (merchantPropertyListEntity.isConfirm()) {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (int i = 0; i < promotionList.size(); i++) {
            PromotionListEntity promotionListEntity = promotionList.get(i);
            if (promotionListEntity.isConfirm()) {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }
    }

    private void getBanner() {
        VolleyOperater<HomeBannerModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FIND_TBANNER, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (CheckUtils.isNoEmptyList(((HomeBannerModel) obj).getValue())) {
                        addActivityImages(((HomeBannerModel) obj).getValue());
                        if (merchantDividerLayout.getVisibility() == View.GONE)
                            merchantDividerLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, HomeBannerModel.class);
    }

    private void getAgentIdByXY() {
        VolleyOperater<FindAgentModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_AGENT_BY_USER_XY, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    FindAgentModel agentModel = (FindAgentModel) obj;
                    if (agentModel.getValue().getAgentType() == 1) {
                        agentId = agentModel.getValue().getId();
                        PreferenceUtils.saveLongPreference("issueAgentId", agentId, mActivity);
                    } else {
                        agentId = 0L;
                    }
                } else {
                    agentId = 0L;
                }
                getDate(false, false);
                getCategory();
                getFilter();
                getBanner();
                getPrimaryCategory();
                getBroadcast();
                getPrimaryPublicity();
                getRecommendCategory();
            }
        }, FindAgentModel.class);
    }

    private void getPrimaryCategory() {
        VolleyOperater<PrimaryCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FIND_PRIMARY_CATEGORY_LIST, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (CheckUtils.isNoEmptyList(((PrimaryCategoryModel) obj).getValue())) {
                        initNavigatorViewPager(((PrimaryCategoryModel) obj).getValue());
                    } else {
                        navigatorLayout.setVisibility(View.GONE);
                    }
                    if (!CommonUtils.isBeyondSpring(((PrimaryCategoryModel) obj).getServertime())) {
                        navigatorLayout.setBackgroundResource(R.drawable.spring_bg_1);
                    } else {
                        navigatorLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            }
        }, PrimaryCategoryModel.class);
    }

    private void getBroadcast() {
        VolleyOperater<BroadcastModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FIND_BROADCAST_LIST, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    BroadcastModel model = (BroadcastModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        broadcastList = model.getValue();
                        broadcastLayout.setVisibility(View.VISIBLE);
                        noticeView.setNoticeList(broadcastList);
                        noticeView.start();
                    } else {
                        broadcastList = new ArrayList<>();
                        broadcastLayout.setVisibility(View.GONE);
                        noticeView.setNoticeList(broadcastList);
                        noticeView.pause();
                    }
                }
            }
        }, BroadcastModel.class);
    }

    private void getPrimaryPublicity() {
        VolleyOperater<PrimaryPublicityModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FIND_PUBLICITY, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    PrimaryPublicityModel model = (PrimaryPublicityModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        publicityList = model.getValue();
                    } else {
                        publicityList = new ArrayList<>();
                    }
                    showPublicity();
                }
            }
        }, PrimaryPublicityModel.class);
    }


    private void getRecommendCategory() {
        VolleyOperater<RecommendCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FIND_RECOMMEND_CATEGORY, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    RecommendCategoryModel model = (RecommendCategoryModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        recommendList = model.getValue();
                    } else {
                        recommendList = new ArrayList<>();
                    }
                    showRecommend();
                }
            }
        }, RecommendCategoryModel.class);
    }

    //显示推广区
    private void showPublicity() {
        if (CheckUtils.isEmptyList(publicityList)) {
            publicityContainer.setVisibility(View.GONE);
            return;
        }
        publicityContainer.setVisibility(View.VISIBLE);
        int i = 0;
        boolean showBigImg = false;
        boolean showFourImg = false;
        for (final PrimaryPublicity publicity : publicityList) {
            if (publicity.getPublicityType() == 1) {
                showBigImg = true;
                if (publicityImg.getVisibility() == View.GONE)
                    publicityImg.setVisibility(View.VISIBLE);
                publicityImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick(publicity);
                    }
                });
                ImageUtils.loadBitmap(mActivity, publicity.getImg() + Constants.IMAGE_URL_END_THUMBNAIL_BANNER, publicityImg, R.drawable.horsegj_default, "");
            } else if (publicity.getPublicityType() == 2) {
                showFourImg = true;
                if (publicityBottomContainer.getVisibility() == View.GONE)
                    publicityBottomContainer.setVisibility(View.VISIBLE);
                publicityViews.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick(publicity);
                    }
                });
                if (i != 1) {
                    ImageUtils.loadBitmap(mActivity, publicity.getImg() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_PUBLICITY, publicityViews.get(i), R.drawable.horsegj_default, "");
                } else {
                    ImageUtils.loadBitmap(mActivity, publicity.getImg(), publicityViews.get(i), R.drawable.horsegj_default, Constants.getEndThumbnail(300, 100));
                }
                i++;
            }
        }
        if (!showBigImg) {
            publicityImg.setVisibility(View.GONE);
        }
        if (!showFourImg) {
            publicityBottomContainer.setVisibility(View.GONE);
        }
    }

    //显示推荐商品区
    private void showRecommend() {
        recommendLayout.removeAllViews();
        if (CheckUtils.isEmptyList(recommendList)) return;
        for (RecommendCategory recommend : recommendList) {
            LinearLayout mLayout = new LinearLayout(mActivity);
            mLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.x10), 0, 0);
            mLayout.setLayoutParams(llp);
            mLayout.addView(recommendTitle(recommend));
            View view = getComponent(recommend);
            if (view != null) mLayout.addView(view);
            recommendLayout.addView(mLayout);
        }
    }

    //推荐商品标题
    private LinearLayout recommendTitle(RecommendCategory recommend) {
        LinearLayout mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(R.dimen.x40));
        mLayout.setLayoutParams(llp);
        mLayout.setGravity(Gravity.CENTER_VERTICAL);
        mLayout.setBackgroundResource(R.drawable.bg_black_stroke);

        View view = new View(mActivity);
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x3),
                getResources().getDimensionPixelOffset(R.dimen.x18));
        vlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x15), 0, getResources().getDimensionPixelOffset(R.dimen.x5), 0);
        view.setLayoutParams(vlp);
        view.setBackgroundColor(getResources().getColor(R.color.orange_text));
        mLayout.addView(view);

        TextView tv = new TextView(mActivity);
        LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        tvlp.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.x1));
        tv.setLayoutParams(tvlp);
        tv.setTextSize(15);
        tv.setSingleLine(true);
        tv.setTextColor(getResources().getColor(R.color.color_3));
        tv.setText(recommend.getName());
        mLayout.addView(tv);

        TextView tv2 = new TextView(mActivity);
        LinearLayout.LayoutParams tv2lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv2lp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), 0, getResources().getDimensionPixelOffset(R.dimen.x15), 0);
        tv2.setLayoutParams(tv2lp);
        tv2.setTextSize(12);
        tv2.setTextColor(getResources().getColor(R.color.orange_text));
        tv2.setText("更多");
        Drawable drawable = getResources().getDrawable(R.drawable.home_more_right_arrow);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv2.setCompoundDrawables(null, null, drawable, null);
        tv2.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.x2));
        tv2.setTag(recommend);
        tv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick((RecommendCategory) v.getTag());
            }
        });
        mLayout.addView(tv2);
        return mLayout;
    }

    private View getComponent(RecommendCategory recommend) {
        switch (recommend.getShowType()) {
            case 1:
                return horizontalScrollView(recommend);
            case 2:
                return fourView(recommend);
            case 3:
                return sixView(recommend);
            default:
                return null;
        }
    }

    //六宫格商品区
    private View sixView(RecommendCategory recommend) {
        int width = (SCREEN_WIDTH - getResources().getDimensionPixelOffset(R.dimen.x24)) / 3;
        int imageWidth = (SCREEN_WIDTH - getResources().getDimensionPixelOffset(R.dimen.x45)) / 3;
        LinearLayout mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(lp);
        mLayout.setBackgroundColor(Color.WHITE);
        int size = recommend.getRecommendCategoryGoodsList().size();
        int i_size = size / 3 + (size % 3 > 0 ? 1 : 0);
        for (int i = 0; i < i_size; i++) {
            LinearLayout layout = new LinearLayout(mActivity);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(mlp);
            layout.setPadding(getResources().getDimensionPixelOffset(R.dimen.x12), 0,
                    getResources().getDimensionPixelOffset(R.dimen.x12), 0);
            for (int j = i * 3; j < (i + 1) * 3 && j < size; j++) {
                final RecommendCategoryGoods recommendGoods = recommend.getRecommendCategoryGoodsList().get(j);
                LinearLayout llayout = new LinearLayout(mActivity);
                llayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams mllp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                llayout.setLayoutParams(mllp);
                llayout.setGravity(Gravity.CENTER_HORIZONTAL);
                if (recommendGoods.getGoods() != null) {
                    llayout.setTag(recommendGoods.getGoods());
                }
                llayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() != null) {
                            Intent intent = new Intent();
                            if (((Goods) v.getTag()).getType() == 1) {
                                intent.setClass(mActivity, CommodityDetailActivity.class);
                            } else {
//                                intent.setClass(mActivity, GoodsDetailActivity.class);
                                intent.setClass(mActivity, CommercialActivity.class);
                                if (recommendGoods.getMerchantId() == null)
                                    return;
                                intent.putExtra("merchantId", recommendGoods.getMerchantId().intValue());
                                intent.putExtra("goodsId", recommendGoods.getGoodsId().intValue());
                            }
                            intent.putExtra("goods", (Goods) v.getTag());
                            startActivity(intent);
                        }
                    }
                });

                ImageView iv = new ImageView(mActivity);
                LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(imageWidth, imageWidth);
                ivlp.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.x5), 0, 0);
                iv.setLayoutParams(ivlp);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (recommendGoods.getGoods() != null && CheckUtils.isNoEmptyStr(recommendGoods.getGoods().getImgs())) {
                    ImageUtils.loadBitmap(mActivity, recommendGoods.getGoods().getImgs().split(";")[0] + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_PUBLICITY, iv, R.drawable.horsegj_default, "");
                } else {
                    iv.setImageResource(R.drawable.horsegj_default);
                }
                llayout.addView(iv);

                TextView tv = new TextView(mActivity);
                LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x4), getResources().getDimensionPixelOffset(R.dimen.x6),
                        getResources().getDimensionPixelOffset(R.dimen.x4), 0);
                tv.setLayoutParams(tvlp);
                tv.setSingleLine(true);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setTextSize(13);
                tv.setTextColor(getResources().getColor(R.color.color_3));
                if (recommendGoods.getGoods() != null) {
                    tv.setText(recommendGoods.getGoods().getName());
                }
                llayout.addView(tv);

                LinearLayout textLayout = new LinearLayout(mActivity);
                LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x4), 0,
                        getResources().getDimensionPixelOffset(R.dimen.x4), getResources().getDimensionPixelOffset(R.dimen.x6));
                textLayout.setLayoutParams(textlp);
                textLayout.setGravity(Gravity.BOTTOM);

                TextView t = new TextView(mActivity);
                LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                t.setLayoutParams(tlp);
                t.setTextSize(10);
                t.setTextColor(getResources().getColor(R.color.orange_text));
                t.setText("¥");
                textLayout.addView(t);

                TextView priceTv = new TextView(mActivity);
                LinearLayout.LayoutParams pricelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                pricelp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x2), 0, 0, 0);
                priceTv.setLayoutParams(pricelp);
                priceTv.setSingleLine(true);
                priceTv.setTextSize(14);
                priceTv.setTextColor(getResources().getColor(R.color.orange_text));
                String price = "";
                if (recommendGoods.getGoods() != null) {
                    price = StringUtils.BigDecimal2Str(recommendGoods.getGoods().getGoodsSpecList().get(0).getPrice());
                }
                priceTv.setText(price);
                textLayout.addView(priceTv);

                if (recommendGoods.getGoods() != null && recommendGoods.getGoods().getGoodsSpecList().size() > 1) {
                    TextView qi = new TextView(mActivity);
                    qi.setLayoutParams(tlp);
                    qi.setTextSize(10);
                    qi.setTextColor(getResources().getColor(R.color.orange_text));
                    qi.setText("起");
                    textLayout.addView(qi);
                }

                llayout.addView(textLayout);
                layout.addView(llayout);

                if (j % 3 != 2) {
                    View v = new View(mActivity);
                    LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                    v.setLayoutParams(vp);
                    v.setBackgroundColor(Color.parseColor("#e5e5e5"));
                    layout.addView(v);
                }
            }
            mLayout.addView(layout);

            View v = new View(mActivity);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            v.setLayoutParams(vp);
            v.setBackgroundColor(Color.parseColor("#e5e5e5"));
            mLayout.addView(v);
        }

        return mLayout;
    }

    //四宫格商品区
    private View fourView(RecommendCategory recommend) {
        int width = (SCREEN_WIDTH - getResources().getDimensionPixelOffset(R.dimen.x40)) / 2;
        FlowLayout mLayout = new FlowLayout(mActivity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(lp);
        mLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.x5), 0,
                getResources().getDimensionPixelOffset(R.dimen.x15), 0);

        for (final RecommendCategoryGoods recommendGoods : recommend.getRecommendCategoryGoodsList()) {
            LinearLayout layout = new LinearLayout(mActivity);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            mlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x10), getResources().getDimensionPixelOffset(R.dimen.x10), 0, 0);
            layout.setLayoutParams(mlp);
            layout.setBackgroundResource(R.drawable.bg_black_stroke);
            if (recommendGoods.getGoods() != null) {
                layout.setTag(recommendGoods.getGoods());
            }
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        Intent intent = new Intent();
                        if (((Goods) v.getTag()).getType() == 1) {
                            intent.setClass(mActivity, CommodityDetailActivity.class);
                        } else {
//                            intent.setClass(mActivity, GoodsDetailActivity.class);
                            intent.setClass(mActivity, CommercialActivity.class);
                            if (recommendGoods.getMerchantId() == null)
                                return;
                            intent.putExtra("merchantId", recommendGoods.getMerchantId().intValue());
                            intent.putExtra("goodsId", recommendGoods.getGoodsId().intValue());
                        }
                        intent.putExtra("goods", (Goods) v.getTag());
                        startActivity(intent);
                    }
                }
            });

            ImageView iv = new ImageView(mActivity);
            LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(width, width);
            iv.setLayoutParams(ivlp);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (recommendGoods.getGoods() != null && CheckUtils.isNoEmptyStr(recommendGoods.getGoods().getImgs())) {
                ImageUtils.loadBitmap(mActivity, recommendGoods.getGoods().getImgs().split(";")[0] + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_PUBLICITY, iv, R.drawable.horsegj_default, "");
            } else {
                iv.setImageResource(R.drawable.horsegj_default);
            }
            layout.addView(iv);

            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x9), getResources().getDimensionPixelOffset(R.dimen.x8),
                    getResources().getDimensionPixelOffset(R.dimen.x9), getResources().getDimensionPixelOffset(R.dimen.x6));
            tv.setLayoutParams(tvlp);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.color_3));
            if (recommendGoods.getGoods() != null) {
                tv.setText(recommendGoods.getGoods().getName());
            }
            layout.addView(tv);

            View v = new View(mActivity);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            vp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x9), 0,
                    getResources().getDimensionPixelOffset(R.dimen.x9), 0);
            v.setLayoutParams(vp);
            v.setBackgroundColor(Color.parseColor("#e5e5e5"));
            layout.addView(v);

            LinearLayout textLayout = new LinearLayout(mActivity);
            LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x9), getResources().getDimensionPixelOffset(R.dimen.x4),
                    getResources().getDimensionPixelOffset(R.dimen.x9), getResources().getDimensionPixelOffset(R.dimen.x8));
            textLayout.setLayoutParams(textlp);
            textLayout.setGravity(Gravity.BOTTOM);

            TextView t = new TextView(mActivity);
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            t.setLayoutParams(tlp);
            t.setTextSize(10);
            t.setTextColor(getResources().getColor(R.color.orange_text));
            t.setText("¥");
            textLayout.addView(t);

            TextView priceTv = new TextView(mActivity);
            LinearLayout.LayoutParams pricelp;
            if (recommendGoods.getGoods() != null && recommendGoods.getGoods().getGoodsSpecList().size() > 1) {
                pricelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            } else {
                pricelp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            }
            pricelp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x2), 0, 0, 0);
            priceTv.setLayoutParams(pricelp);
            priceTv.setSingleLine(true);
            priceTv.setTextSize(15);
            priceTv.setTextColor(getResources().getColor(R.color.orange_text));
            String price = "";
            if (recommendGoods.getGoods() != null) {
                price = StringUtils.BigDecimal2Str(recommendGoods.getGoods().getGoodsSpecList().get(0).getPrice());
            }
            priceTv.setText(price);
            textLayout.addView(priceTv);

            if (recommendGoods.getGoods() != null && recommendGoods.getGoods().getGoodsSpecList().size() > 1) {
                TextView qi = new TextView(mActivity);
                LinearLayout.LayoutParams qilp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                qi.setLayoutParams(qilp);
                qi.setTextSize(10);
                qi.setTextColor(getResources().getColor(R.color.orange_text));
                qi.setText("起");
                textLayout.addView(qi);
            }

            TextView sale = new TextView(mActivity);
            LinearLayout.LayoutParams salelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            salelp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), 0, 0, 0);
            sale.setLayoutParams(salelp);
            sale.setSingleLine(true);
            sale.setTextSize(11);
            sale.setTextColor(getResources().getColor(R.color.color_9));
            if (recommendGoods.getGoods() != null) {
                sale.setText("月售:" + recommendGoods.getGoods().getMonthSaled());
            }
            textLayout.addView(sale);

            layout.addView(textLayout);
            mLayout.addView(layout);
        }

        return mLayout;
    }

    //横向滚动商品区
    private View horizontalScrollView(RecommendCategory recommend) {
        HorizontalScrollView scrollView = new HorizontalScrollView(mActivity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(lp);
        scrollView.setBackgroundColor(Color.WHITE);
        scrollView.setHorizontalScrollBarEnabled(false);

        LinearLayout mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(llp);
        mLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.x5), getResources().getDimensionPixelOffset(R.dimen.x10),
                getResources().getDimensionPixelOffset(R.dimen.x15), getResources().getDimensionPixelOffset(R.dimen.x10));

        for (final RecommendCategoryGoods recommendGoods : recommend.getRecommendCategoryGoodsList()) {
            LinearLayout layout = new LinearLayout(mActivity);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x100),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x10), 0, 0, 0);
            layout.setLayoutParams(mlp);
            layout.setBackgroundResource(R.drawable.bg_black_stroke);
            if (recommendGoods.getGoods() != null) {
                layout.setTag(recommendGoods.getGoods());
            }
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        Intent intent = new Intent();
                        if (((Goods) v.getTag()).getType() == 1) {
                            intent.setClass(mActivity, CommodityDetailActivity.class);
                        } else {
//                            intent.setClass(mActivity, GoodsDetailActivity.class);
                            intent.setClass(mActivity, CommercialActivity.class);
                            if (recommendGoods.getMerchantId() == null)
                                return;
                            intent.putExtra("merchantId", recommendGoods.getMerchantId().intValue());
                            intent.putExtra("goodsId", recommendGoods.getGoodsId().intValue());
                        }
                        intent.putExtra("goods", (Goods) v.getTag());
                        startActivity(intent);
                    }
                }
            });

            ImageView iv = new ImageView(mActivity);
            LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x100),
                    getResources().getDimensionPixelOffset(R.dimen.x100));
            iv.setLayoutParams(ivlp);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (recommendGoods.getGoods() != null && CheckUtils.isNoEmptyStr(recommendGoods.getGoods().getImgs())) {
                ImageUtils.loadBitmap(mActivity, recommendGoods.getGoods().getImgs().split(";")[0] + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_PUBLICITY, iv, R.drawable.horsegj_default, "");
            } else {
                iv.setImageResource(R.drawable.horsegj_default);
            }
            layout.addView(iv);

            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), getResources().getDimensionPixelOffset(R.dimen.x6),
                    getResources().getDimensionPixelOffset(R.dimen.x5), 0);
            tv.setLayoutParams(tvlp);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextSize(13);
            tv.setTextColor(getResources().getColor(R.color.color_3));
            if (recommendGoods.getGoods() != null) {
                tv.setText(recommendGoods.getGoods().getName());
            }
            layout.addView(tv);

            LinearLayout textLayout = new LinearLayout(mActivity);
            LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textlp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x5), 0,
                    getResources().getDimensionPixelOffset(R.dimen.x5), getResources().getDimensionPixelOffset(R.dimen.x6));
            textLayout.setLayoutParams(textlp);
            textLayout.setGravity(Gravity.BOTTOM);

            TextView t = new TextView(mActivity);
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            t.setLayoutParams(tlp);
            t.setTextSize(10);
            t.setTextColor(getResources().getColor(R.color.orange_text));
            t.setText("¥");
            textLayout.addView(t);

            TextView priceTv = new TextView(mActivity);
            LinearLayout.LayoutParams pricelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pricelp.setMargins(getResources().getDimensionPixelOffset(R.dimen.x2), 0, 0, 0);
            priceTv.setLayoutParams(pricelp);
            priceTv.setSingleLine(true);
            priceTv.setTextSize(14);
            priceTv.setTextColor(getResources().getColor(R.color.orange_text));
            String price = "";
            if (recommendGoods.getGoods() != null) {
                price = StringUtils.BigDecimal2Str(recommendGoods.getGoods().getGoodsSpecList().get(0).getPrice());
            }
            priceTv.setText(price);
            textLayout.addView(priceTv);

            if (recommendGoods.getGoods() != null && recommendGoods.getGoods().getGoodsSpecList().size() > 1) {
                TextView qi = new TextView(mActivity);
                qi.setLayoutParams(tlp);
                qi.setTextSize(10);
                qi.setTextColor(getResources().getColor(R.color.orange_text));
                qi.setText("起");
                textLayout.addView(qi);
            }

            layout.addView(textLayout);
            mLayout.addView(layout);
        }

        scrollView.addView(mLayout);
        return scrollView;
    }

    /**
     * 获取列表数据
     *
     * @param isAutoRefresh 加载新地址后自动刷新
     * @param isLoadMore    加载更多
     */
    private void getDate(final boolean isAutoRefresh, final boolean isLoadMore) {
        refreshFlag = false;
        final Map<String, Object> map = new HashMap<>();
        if (App.isLogin()) {
            map.put("userId", App.getUserInfo().getId());
        }
        map.put("queryType", getSortParam());
        map.put("start", currentResultPage);
        map.put("size", maxResults);
        if (tagId != -1) {
            map.put("tagId", tagId);
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        if (tagParentId != -1) {
            map.put("tagParentId", tagParentId);
        }
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        String shipParams = getShipParams();
        if (shipParams.length() > 0) {
            map.put("shipFilter", shipParams);
        }
        String propertyParams = getPropertyParams();
        if (propertyParams.length() > 0) {
            map.put("propertyFilter", propertyParams);
        }
        String promotionParams = getPromotionParams();
        if (promotionParams.length() > 0) {
            map.put("promotionFilter", promotionParams);
        }
        VolleyOperater<CommercialListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_fIND_TAKE_AWAY_MERCHANT, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                listView.setMode(PullToRefreshBase.Mode.BOTH);
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    CommercialListModel model = (CommercialListModel) obj;
                    if (model.getCode() == 0) {
                        ArrayList<Merchant> mlist = ((CommercialListModel) obj).getValue();
                        if (!isLoadMore) {
                            if (mlist.size() == 0 && midPrePosition == -1 && leftMenuCurrentGroup == -1 && leftMenuCurrentChild == -1 && isClearFilter()) {
                                ArrayList<Merchant> data = new ArrayList<>();
                                data.add(new Merchant());
                                adapter.setSystemTime(null);
                                adapter.setList(data);
                                handler.sendEmptyMessage(Constants.LOCATION_NO_MERCHANT);
                                return;
                            }
//                        else {
//                            hasNoNet.setVisibility(View.GONE);
//                        }
                        }
                        if (CheckUtils.isNoEmptyList(mlist)) {
                            if (CheckUtils.isNoEmptyList(PickGoodsModel.getInstance().getMerchantPickGoodsList()))
                                for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                                    for (Merchant merchant : mlist) {
                                        if (merchant.getId() == merchantPickGoods.getMerchantId()) {
                                            merchant.setPickGoodsCount(merchantPickGoods.getGoodsCount());
                                            break;
                                        }
                                    }
                                }
                            if (isLoadMore) {
                                if (mlist.size() < maxResults) {
                                    ToastUtils.displayMsg("到底了", mActivity);
//                                listView.setMode(PullToRefreshBase.Mode.);
                                }
                                ArrayList<Merchant> mlistOrg = adapter.getList();
                                if (mlistOrg != null) {
                                    mlistOrg.addAll(mlist);
                                    adapter.setSystemTime(((CommercialListModel) obj).getServertime());
                                    adapter.setList(mlistOrg);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                if (listHeaderButton.getVisibility() == View.INVISIBLE) {
                                    listHeaderButton.setVisibility(View.VISIBLE);
                                }
                                ArrayList<Merchant> mlistOrg = adapter.getList();
                                mlistOrg.clear();
                                mlistOrg.addAll(mlist);
                                adapter.setSystemTime(((CommercialListModel) obj).getServertime());
                                adapter.setList(mlistOrg);
//                            adapter.notifyDataSetChanged();
//                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                            }
                        } else {

                            if (isLoadMore) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            } else {
                                MLog.d("isempty");
                                mlist = new ArrayList<>();
                                mlist.add(new Merchant());
                                adapter.setSystemTime(null);
                                adapter.setList(mlist);
                            }
                        }
                    } else {
                        ArrayList<Merchant> mlist = new ArrayList<>();
                        mlist.add(new Merchant());
                        adapter.setSystemTime(null);
                        adapter.setList(mlist);
                    }
                } else {
                    ArrayList<Merchant> mlist = new ArrayList<>();
                    mlist.add(new Merchant());
                    adapter.setSystemTime(null);
                    adapter.setList(mlist);
                }
                if (isFail && isShowPop) {
                    openPop();
                    isShowPop = false;
                }
            }
        }, CommercialListModel.class);

    }

    private int getSortParam() {
        if (homeSortAdapter != null) {
            List<HomeBean> data = homeSortAdapter.getData();
            for (HomeBean bean : data) {
                if (bean.isCheck()) {
                    return bean.getId();
                }
            }
        }
        return 1;
    }

    private String getShipParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
            for (int i = 0; i < shipmentList.size(); i++) {
                ShipmentListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    sb.append(ship.getId());
                }
            }
            return sb.toString();
        }
        return "";
    }

    private String getPropertyParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<MerchantPropertyListEntity> shipmentList = filterValue.getMerchantPropertyList();
            for (int i = 0; i < shipmentList.size(); i++) {
                MerchantPropertyListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    if (i == shipmentList.size() - 1) {
                        sb.append(ship.getId());
                    } else {
                        sb.append(ship.getId() + ",");
                    }
                }
            }
            return sb.toString();
        }
        return "";
    }

    private String getPromotionParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<PromotionListEntity> shipmentList = filterValue.getPromotionList();
            for (int i = 0; i < shipmentList.size(); i++) {
                PromotionListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    if (i == shipmentList.size() - 1) {
                        sb.append(ship.getId());
                    } else {
                        sb.append(ship.getId() + ",");
                    }
                }
            }
            return sb.toString();
        }
        return "";
    }


    private void showLeftMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_left, null);
        ListView groupListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_group);
        childListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_child);
        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_left_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
//        leftMenuWindow.setFocusable(true);
        leftMenuWindow.showAsDropDown(menuBar, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu1.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu1.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
                resetListView();
            }
        });
        mCategoryLeftAdapter = new LeftMenuPopGroupAdapter(mActivity, leftPopMenuGroup, groupListener);
        groupListView.setAdapter(mCategoryLeftAdapter);
        mCategoryRightAdapter = new LeftMenuPopChildAdapter(mActivity, leftPopMenuChild, childListener);
        childListView.setAdapter(mCategoryRightAdapter);
    }

    private LeftMenuPopChildAdapter mCategoryRightAdapter;
    private OnClickListener groupListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (int) v.getTag();
            List<CategoryLeftBean> list = mCategoryLeftAdapter.getList();
            CategoryLeftBean categoryLeftBean1 = list.get(mPrePosition);
            categoryLeftBean1.setIsChecked(false);
            CategoryLeftBean categoryLeftBean2 = list.get(mSelectPosition);
            categoryLeftBean2.setIsChecked(false);
            CategoryLeftBean categoryLeftBean = list.get(i);
            categoryLeftBean.setIsChecked(true);
            mCategoryLeftAdapter.setList(list);
            mPrePosition = i;

            leftPopMenuChild = leftPopMenuGroup.get(i).getChildTagCategoryList();
            if (leftPopMenuChild != null) {
                mCategoryRightAdapter.setList(leftPopMenuChild);
                if (childListView != null)
                    childListView.setVisibility(View.VISIBLE);
            } else {
                if (childListView != null)
                    childListView.setVisibility(View.GONE);
                leftMenuCurrentGroup = i;
                tvLVMenu1.setText(list.get(i).getName());
                tvMenu1.setText(list.get(i).getName());
                tagId = list.get(i).getId();
                tagParentId = list.get(i).getParentId();
                currentResultPage = 0;
                getDate(true, false);
                leftMenuWindow.dismiss();
                leftPopMenuChild = new ArrayList<>();
                mCategoryRightAdapter.setList(leftPopMenuChild);

                if (mSelectChildPosition != -1) {
                    List<CategoryLeftBean> list1 = mCategoryLeftAdapter.getList();
                    List<CategoryRightBean> childTagCategoryList = list1.get(mSelectPosition).getChildTagCategoryList();
                    if (childTagCategoryList != null) {
                        CategoryRightBean categoryRightBean = childTagCategoryList.get(mSelectChildPosition);
                        categoryRightBean.setIsChecked(false);
                    }
                }
                mSelectPosition = i;
                mSelectChildPosition = -1;
            }
        }
    };

    private OnClickListener childListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (int) v.getTag();
            if (mSelectPosition != 0) {
                List<CategoryLeftBean> list1 = mCategoryLeftAdapter.getList();
                List<CategoryRightBean> childTagCategoryList = list1.get(mSelectPosition).getChildTagCategoryList();
                CategoryRightBean categoryRightBean = childTagCategoryList.get(mSelectChildPosition);
                categoryRightBean.setIsChecked(false);
            }

            List<CategoryRightBean> list = mCategoryRightAdapter.getList();
            list.get(i).setIsChecked(true);

            mCategoryRightAdapter.setList(list);

            tvLVMenu1.setText(leftPopMenuChild.get(i).getName());
            tvMenu1.setText(leftPopMenuChild.get(i).getName());
            tagId = leftPopMenuChild.get(i).getId();
            tagParentId = leftPopMenuChild.get(i).getParentId();
            currentResultPage = 0;
            getDate(true, false);
            leftMenuCurrentChild = i;
            leftMenuWindow.dismiss();

            mSelectPosition = mPrePosition;
            mSelectChildPosition = i;
        }
    };

    private void showMidMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_mid, null);
        ListView listView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_mid_list);
        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_mid_cover_view);
        coverView.setOnClickListener(this);
        homeSortAdapter = new HomeSortAdapter(R.layout.layout_home_category, mActivity, listenerMid);
        ArrayList<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HomeBean bean = new HomeBean();
            bean.setIcon(heads[i]);
            bean.setName(names[i]);
            bean.setId(sortIds[i]);
            data.add(bean);
        }
        homeSortAdapter.setData(data);
        listView.setAdapter(homeSortAdapter);
        listView.setItemsCanFocus(true);

        midMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        midMenuWindow.setOutsideTouchable(true);
//        midMenuWindow.setFocusable(true);
        midMenuWindow.showAsDropDown(menuBar, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu2.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu2.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
                resetListView();
            }
        });
    }

    private OnClickListener listenerMid = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (midPrePosition != -1) {
                homeSortAdapter.getData().get(midPrePosition).setIsCheck(false);
            }
            HomeBean bean = homeSortAdapter.getData().get(position);
            bean.setIsCheck(!bean.isCheck());
            midPrePosition = position;
            homeSortAdapter.notifyDataSetChanged();
            if (midMenuWindow.isShowing()) {
                midMenuWindow.dismiss();
            }
            currentResultPage = 0;
            getDate(true, false);
            tvMenu2.setText(bean.getName());
            tvLVMenu2.setText(bean.getName());
        }
    };

    private void showRightMenuPop(List<ShipmentListEntity> shipmentList, final List<MerchantPropertyListEntity> merchantPropertyList, final List<PromotionListEntity> promotionList) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_right, null);
        RelativeLayout rlShipment = (RelativeLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_shipment_layout);
        shipmentLinear = (SegmentedGroup) linearLayout.findViewById(R.id.home_fragment_menu_right_segment_group);
        LinearLayout llMerchant = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_merchant_layout);
        propertyLinear = (FlowLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_merchant_flow);
        LinearLayout llPromotion = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_promotion_layout);
        promotionLinear = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_list);
        TextView clear = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_clear);
        clear.setOnClickListener(this);
        TextView confirm = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_confirm);
        confirm.setOnClickListener(this);

        for (ShipmentListEntity shipment : shipmentList) {
            RadioButton radioButton1 = (RadioButton) mActivity.getLayoutInflater().inflate(R.layout.radio_button, null);
            radioButton1.setText(shipment.getName());
            shipmentLinear.addView(radioButton1);
        }
        shipmentLinear.updateBackground();
        ((RadioButton) shipmentLinear.getChildAt(0)).setChecked(false);
        shipmentLinear.setOnCheckedChangeListener(this);

        float width = (DeviceParameter.getScreenWidth() - DipToPx.dip2px(mActivity, 48)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, DipToPx.dip2px(mActivity, 25));
        params.setMargins(0, 0, DipToPx.dip2px(mActivity, 8), DipToPx.dip2px(mActivity, 5));
        for (int i = 0; i < merchantPropertyList.size(); i++) {
            final LinearLayout linearLayout1 = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.right_menu_merchant_feature, null);
            linearLayout1.setTag(i);
            linearLayout1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout1.getChildAt(2).setSelected(!linearLayout1.getChildAt(2).isSelected());
                    merchantPropertyList.get((int) v.getTag()).setIsCheck(linearLayout1.getChildAt(2).isSelected());
                }
            });
            ImageView ivMerchant = (ImageView) linearLayout1.findViewById(R.id.right_menu_item_iv);
            TextView tvMerchant = (TextView) linearLayout1.findViewById(R.id.right_menu_item_tv);
            ImageUtils.loadBitmap(mActivity, merchantPropertyList.get(i).getIcon(), ivMerchant, 0, "");
            tvMerchant.setText(merchantPropertyList.get(i).getName());
            linearLayout1.setLayoutParams(params);
            propertyLinear.addView(linearLayout1);
        }

        for (int i = 0; i < promotionList.size(); i++) {
            final LinearLayout linearLayout2 = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.item_home_filter_promotion, null);
            linearLayout2.setTag(i);
            ImageView ivPromotion = (ImageView) linearLayout2.findViewById(R.id.promotion_icon);
            TextView tvPromotion = (TextView) linearLayout2.findViewById(R.id.promotion_text);
            linearLayout2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout2.getChildAt(2).setSelected(!linearLayout2.getChildAt(2).isSelected());
                    promotionList.get((int) v.getTag()).setIsCheck(linearLayout2.getChildAt(2).isSelected());
                }
            });
            ImageUtils.loadBitmap(mActivity, promotionList.get(i).getIcon(), ivPromotion, 0, "");
            tvPromotion.setText(promotionList.get(i).getName());
//			linearLayout2.setLayoutParams(params2);
            promotionLinear.addView(linearLayout2);
        }

        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_right_cover_view);
        coverView.setOnClickListener(this);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        rightMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        rightMenuWindow.setOutsideTouchable(true);
//        rightMenuWindow.showAsDropDown(menuBar, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu3.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu3.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);
                resetListView();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) group.getChildAt(i);
            filterValue.getShipmentList().get(i).setIsCheck(childAt.isChecked());
        }
    }

    private List<CategoryLeftBean> leftPopMenuGroup = new ArrayList<>();
    private List<CategoryRightBean> leftPopMenuChild = new ArrayList<>();

    /**
     * 获取商家分类
     */
    private void getCategory() {
        VolleyOperater<FindCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_GET_CATEGORY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    leftPopMenuGroup = ((FindCategoryModel) obj).getValue();
                    leftPopMenuGroup.get(0).setIsChecked(true);
                    mSelectPosition = 0;
                    mSelectChildPosition = -1;
                }
            }
        }, FindCategoryModel.class);
    }

    /**
     * 获取筛选
     */
    private void getFilter() {
        VolleyOperater<MerchantFilterModel> operater = new VolleyOperater<MerchantFilterModel>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        if (agentId != 0) {
            map.put("agentId", agentId);
        }
        operater.doRequest(Constants.URL_FILTER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    MerchantFilterModel merchantFilterModel = (MerchantFilterModel) obj;
                    filterValue = merchantFilterModel.getValue();
                    if (filterValue != null) {
//                        menuBar.setVisibility(View.VISIBLE);
                        showRightMenuPop(filterValue.getShipmentList(), filterValue.getMerchantPropertyList(), filterValue.getPromotionList());
                    }
                }
            }
        }, MerchantFilterModel.class);
    }

    public boolean isPopupWindowShowing() {
        if ((leftMenuWindow != null && leftMenuWindow.isShowing()) || (midMenuWindow != null && midMenuWindow.isShowing()) ||
                (rightMenuWindow != null && rightMenuWindow.isShowing())) {
            return true;
        }
        return false;
    }

    public void hidePopupWindow() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            leftMenuWindow.dismiss();
        } else if (midMenuWindow != null && midMenuWindow.isShowing()) {
            midMenuWindow.dismiss();
        } else if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            rightMenuWindow.dismiss();
        }
    }

    //推广、公告、推荐商品区 点击事件
    public <T extends BaseProperty> void itemClick(T bean) {
        int gotoType = bean.getGotoType();
        switch (gotoType) {
            case 1:
                if (bean.getLinkUrl().startsWith("maguanjia://")) {
                    if (bean.getLinkUrl().replace("maguanjia://", "").startsWith("http")) {
                        Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                        intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bean.getLinkUrl().replace("maguanjia://", ""));
                        startActivity(intent);
                    } else if ("maguanjia://supermarket".equals(bean.getLinkUrl())) {
                        //跳商超
                        if (mActivity != null)
                            if (mActivity instanceof HomeActivity) {
                                ((HomeActivity) mActivity).setToSuperMarket(0, 0);
                            }
                    } else {
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + bean.getLinkUrl().replace("maguanjia://", ""), new RouterCallback() {
                            @Override
                            public void notFound(Context context, Uri uri) {
                                showNoticeDialog();
                            }

                            @Override
                            public void beforeOpen(Context context, Uri uri) {
                            }

                            @Override
                            public void afterOpen(Context context, Uri uri) {
                            }

                            @Override
                            public void error(Context context, Uri uri, Throwable e) {
                            }
                        });
                    }
                } else {
//                    Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                    intent.putExtra(Banner2WebActivity.URL, bean.getLinkUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
//                    if (bean instanceof Broadcast) {
//                        intent.putExtra("name", ((Broadcast) bean).getTitle());
//                    } else if (bean instanceof RecommendCategory) {
//                        intent.putExtra("name", ((RecommendCategory) bean).getName());
//                    } else if (bean instanceof PrimaryPublicity) {
//                        intent.putExtra("name", ((PrimaryPublicity) bean).getTitle());
//                    }
//                    startActivity(intent);
                    Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bean.getLinkUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                    intent.putExtra(YLBSdkConstants.EXTRA_H5_SHARE, "tag");
                    startActivity(intent);
                }
                break;
            case 2:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "merchant/" + bean.getMerchantId());
                break;
            case 3:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bean.getGroupBuyId());
                break;
            case 4:
                Intent intent1 = new Intent(mActivity, PrimaryCategoryActivity.class);
                intent1.putExtra("tagCategoryId", bean.getFirstCategoryId());
                intent1.putExtra("tagCategorySecondId", bean.getSecondCategoryId());
                intent1.putExtra("tagCategoryType", bean.getCategoryType());
                if (bean instanceof Broadcast) {
                    intent1.putExtra("categoryName", ((Broadcast) bean).getTitle());
                } else if (bean instanceof RecommendCategory) {
                    intent1.putExtra("categoryName", ((RecommendCategory) bean).getName());
                } else if (bean instanceof PrimaryPublicity) {
                    intent1.putExtra("categoryName", ((PrimaryPublicity) bean).getTitle());
                }
                startActivity(intent1);
                break;
            case 5:
                if (mActivity != null) {
                    if (mActivity instanceof HomeActivity) {
                        ((HomeActivity) mActivity).setToSuperMarket(bean.getFirstTGoodsCategoryId(), bean.getSecondTGoodsCategoryId());
                    } else if (mActivity instanceof OldHomeActivity) {
                        mActivity.setResult(HomeActivity.SUPERMARKET);
                        mActivity.finish();
                    }
                }
                break;
            case 6:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bean.getGroupPurchaseMerchantId());
                break;
            case 7:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + bean.getGroupPurchaseCouponId());
                break;
        }
    }
}
