package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.AppVersion;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.download.FileDownloadManager;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.model.AddressManageModel;
import com.project.mgjandroid.model.AppLaunchModel;
import com.project.mgjandroid.model.FestivalModel;
import com.project.mgjandroid.model.HomeVersionModel;
import com.project.mgjandroid.model.InformationAreaModel;
import com.project.mgjandroid.model.RedBagListModel;
import com.project.mgjandroid.model.RedBagsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.UpdateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.HomePagerAdapter;
import com.project.mgjandroid.ui.adapter.HomePlatFormRecyclerAdapter;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.fragment.HomeFragment;
import com.project.mgjandroid.ui.fragment.MineFragment;
import com.project.mgjandroid.ui.fragment.NewHomeFragment;
import com.project.mgjandroid.ui.fragment.OrderListFragment;
import com.project.mgjandroid.ui.fragment.SuperMarketFragment;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.ui.view.CustomViewPager;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * 主页
 */
@Router("home")
public class HomeActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    private static final int INDEX_HOME = 0;
    private static final int INDEX_SUPERMARKET = 1;
    private static final int INDEX_ORDER = 2;
    private static final int INDEX_MINE = 3;
    public static final int LOCATION_RESPOND_CODE = 10086;
    public static final int GROUP = 10011;
    public static final int SUPERMARKET = 10012;
    public static final int LOGIN_RECEIVER_REDBAG = 10013;
    public final static String CHANGE_PICKED_ADDRESS = "com.project.mgjandroid.HomeActivity.CHANGE_PICKED_ADDRESS";
    private final static String TAG = HomeActivity.class.getSimpleName();

    @InjectView(R.id.home_act_layout_home)
    private LinearLayout homeLayout;
    @InjectView(R.id.home_act_layout_super_market)
    private LinearLayout superMarketLayout;
    @InjectView(R.id.home_act_layout_order)
    private LinearLayout orderLayout;
    @InjectView(R.id.home_act_layout_community)
    private LinearLayout communityLayout;
    @InjectView(R.id.home_act_layout_mine)
    private LinearLayout mineLayout;
    @InjectView(R.id.home_act_img_home)
    private ImageView homeImg;
    @InjectView(R.id.home_act_img_super_market)
    private ImageView superMarketImg;
    @InjectView(R.id.home_act_img_order)
    private ImageView orderImg;
    @InjectView(R.id.home_act_img_mine)
    private ImageView mineImg;
    @InjectView(R.id.home_act_tv_home)
    private TextView homeTv;
    @InjectView(R.id.home_act_tv_super_market)
    private TextView superMarketTv;
    @InjectView(R.id.home_act_tv_order)
    private TextView orderTv;

    @InjectView(R.id.home_act_tv_mine)
    private TextView mineTv;
    @InjectView(R.id.home_act_pager)
    private CustomViewPager pager;

    private HomePagerAdapter homePagerAdapter;
    private ArrayList<BaseFragment> fragments;

    private HomeFragment homeFragment;
    private SuperMarketFragment superMarketFragment;
    private OrderListFragment orderListFragment;
    private MineFragment mineFragment;

    private int exitFlag = 0;
    private FileDownloadManager mManager;
    private CommonDialog mUpdateDialog;
    private ProgressBar mProgressBar;
    private Button mTvCancel;
    private Button mTvStart;
    private TextView mTvTip;
    private TextView mTvTip1;
    private LinearLayout mLlButtonLabel;
    private LinearLayout mLlUpdatingLabel;
    private ImageView mImgDelete;
    public static HomeActivity instance;
    private int versionType = 0;
    private NewHomeFragment newHomeFragment;
    private int agentId;
    private FileDownloadManager mManager2;
    private String gifName;
    public boolean isLotteryShow = false;
    private List<UserAddress> userAddressList;

    /**
     * 红包
     */
    private ImageView redBagNoLoginImageView;
    private ImageView redBagLoginImageView;
    private ImageView redBagLoginImageView1;
    private CommonDialog redBagDialog;
    private LinearLayout noLoginLayout;
    private RelativeLayout loginLayout;
    private TextView receiverTextView;
    private TextView redBagNumTextView;
    private TextView loginTextView;
    private RecyclerView redBagRecylerView;
    private HomePlatFormRecyclerAdapter homePlatFormRecyclerAdapter;
    private boolean isSwitchLocation = false;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.home_act);
        Injector.get(this).inject();
        instance = this;
        PreferenceUtils.saveBoolPreference("isLocation", false, getApplicationContext());
        homePagerAdapter = new HomePagerAdapter(this.getSupportFragmentManager());
        fragments = homePagerAdapter.getFragments();
        pager.setAdapter(homePagerAdapter);
        pager.setOffscreenPageLimit(3);
        pager.setScanScroll(false);
        setListener();

        versionType = PreferenceUtils.getIntPreference("versionType", 0, this);
        agentId = PreferenceUtils.getIntPreference("agentId", -1, this);
        PreferenceUtils.saveBoolPreference("isOpenLocationFailDialog", true, getApplicationContext());

        addFragments();
        initUpdateDialog();
        initReceiverRedBagDialog();
        checkUpdate();
        //预加载闪屏gif
        getSplashGif();

    }

    /**
     * 初始化版本更新对话框
     */
    private void initUpdateDialog() {
        View view = mInflater.inflate(R.layout.layout_update_dialog, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mTvCancel = (Button) view.findViewById(R.id.update_cancel);
        mTvCancel.setOnClickListener(this);
        mTvStart = (Button) view.findViewById(R.id.update_start);
        mTvStart.setOnClickListener(this);

        mTvTip = (TextView) view.findViewById(R.id.update_tip);
        mTvTip1 = (TextView) view.findViewById(R.id.update_tip1);
        mLlButtonLabel = (LinearLayout) view.findViewById(R.id.button_label);
        mImgDelete = (ImageView) view.findViewById(R.id.update_delete);
        mImgDelete.setOnClickListener(this);
        mLlUpdatingLabel = (LinearLayout) view.findViewById(R.id.updating_label);

        mUpdateDialog = new CommonDialog(mActivity, view, this);
        mUpdateDialog.setCancelable(false);
    }


    /**
     * 初始化领取红包对话框
     */
    private void initReceiverRedBagDialog() {
        View view = mInflater.inflate(R.layout.layout_redbag_dialog, null);
        noLoginLayout = (LinearLayout) view.findViewById(R.id.no_login_layout);
        redBagNoLoginImageView = (ImageView) view.findViewById(R.id.redbag_nologin_imageview);
        redBagLoginImageView1 = (ImageView) view.findViewById(R.id.redbag_login_imageview1);
        redBagLoginImageView = (ImageView) view.findViewById(R.id.redbag_login_imageview);
        redBagNoLoginImageView.setOnClickListener(this);
        redBagLoginImageView1.setOnClickListener(this);
        redBagLoginImageView.setOnClickListener(this);
        loginLayout = (RelativeLayout) view.findViewById(R.id.login_layout);
        redBagRecylerView = (RecyclerView) view.findViewById(R.id.redbag_recylerview);

        receiverTextView = (TextView) view.findViewById(R.id.register_receiver_textview);
        redBagNumTextView = (TextView) view.findViewById(R.id.redbag_num_textview);
        loginTextView = (TextView) view.findViewById(R.id.login_textview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        redBagRecylerView.setLayoutManager(layoutManager);
        homePlatFormRecyclerAdapter = new HomePlatFormRecyclerAdapter(this);
        redBagRecylerView.setAdapter(homePlatFormRecyclerAdapter);
        loginTextView.setOnClickListener(this);
        redBagDialog = new CommonDialog(mActivity, view, this);
        redBagDialog.setCancelable(true);
    }

    public void showReceiverRedBagDialog() {
        hiddenReceiverRedBagDialog();
//        DisplayMetrics dpMetrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
//        int screenWidth = dpMetrics.widthPixels;
//        Log.d(HomeActivity.class.getSimpleName(), "initReceiverRedBagDialog:screenWidth：" + screenWidth + ",dpMetrics.height:" + dpMetrics.heightPixels);
        noLoginLayout.setVisibility(View.VISIBLE);
        redBagNoLoginImageView.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        redBagLoginImageView1.setVisibility(View.GONE);
        redBagLoginImageView.setVisibility(View.GONE);

        VolleyOperater<RedBagsModel> operater = new VolleyOperater<>(getApplication());
        final Map<String, Object> map = new HashMap<>();
        if (PreferenceUtils.getLocation(getApplicationContext())[0] != null && PreferenceUtils.getLocation(getApplicationContext())[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(getApplicationContext())[0]);
            map.put("longitude", PreferenceUtils.getLocation(getApplicationContext())[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_GET_PLATFORM_REDBAG, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = ((RedBagsModel) obj).getValue();
                    Log.d(TAG, "redBagListModel.getStatus():" + redBagListModel.getStatus());
                    if (redBagListModel.getStatus() == 1 && pager.getCurrentItem() == INDEX_HOME) {
                        if (App.isLogin()) {
                            if (redBagListModel.getType() == 1) {//已绑定手机号
                                if (redBagListModel.getRedBagList() != null && redBagListModel.getRedBagList().size() > 0) {
                                    noLoginLayout.setVisibility(View.GONE);
                                    redBagNoLoginImageView.setVisibility(View.GONE);
                                    loginLayout.setVisibility(View.VISIBLE);
                                    redBagLoginImageView1.setVisibility(View.VISIBLE);
                                    redBagLoginImageView.setVisibility(View.VISIBLE);
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redBagLoginImageView1.getLayoutParams();

                                    List<RedBag> list = redBagListModel.getRedBagList();
                                    if (list.size() == 1) {
                                        params.height = (int) getResources().getDimension(R.dimen.x120);
                                    } else if (list.size() == 2) {
                                        params.height = (int) getResources().getDimension(R.dimen.x230);
                                    } else {
                                        params.height = (int) getResources().getDimension(R.dimen.x280);
                                    }
                                    redBagLoginImageView1.setLayoutParams(params);
                                    homePlatFormRecyclerAdapter.setList(list);
                                    if (redBagDialog != null && !redBagDialog.isShowing()) {
                                        redBagDialog.show();
                                    }
                                }
                            } else {
                                //未绑定手机号
                                receiverTextView.setText("绑定手机号，即可领取大额红包");
                                loginTextView.setText("立即绑定，领取红包");
                                if (redBagDialog != null && !redBagDialog.isShowing()) {
                                    redBagDialog.show();
                                }
                            }
                        } else {
                            receiverTextView.setText("注册马管家，即可领取大额红包");
                            loginTextView.setText("立即登录，领取红包");
                            if (redBagDialog != null && !redBagDialog.isShowing()) {
                                redBagDialog.show();
                            }
                        }
                    }
                }
            }
        }, RedBagsModel.class);
    }

    public void hiddenReceiverRedBagDialog() {
        if (redBagDialog != null && redBagDialog.isShowing()) {
            redBagDialog.dismiss();
        }
    }

    private void setListener() {
        homeLayout.setOnClickListener(this);
        superMarketLayout.setOnClickListener(this);
        orderLayout.setOnClickListener(this);
        communityLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
    }

    private void addFragments() {
        newHomeFragment = NewHomeFragment.newInstance();//新版首页
        Bundle bundle = new Bundle();
        bundle.putInt("agentId", agentId);
        newHomeFragment.setArguments(bundle);
        homeFragment = HomeFragment.newInstance();//老版首页
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("isOld", false);
        homeFragment.setArguments(bundle2);
        superMarketFragment = SuperMarketFragment.newInstance();//商超页面
        orderListFragment = OrderListFragment.newInstance();//订单页面
        mineFragment = MineFragment.newInstance();//设置页面

        if (versionType == 1) {
            fragments.add(newHomeFragment);
            superMarketLayout.setVisibility(View.GONE);
        } else {
            fragments.add(homeFragment);
            superMarketLayout.setVisibility(View.VISIBLE);
        }
        fragments.add(superMarketFragment);
        fragments.add(orderListFragment);
        fragments.add(mineFragment);
        homePagerAdapter.notify(fragments);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registeLocation();
            }
        }, 600);
    }

    public void changeTabUi(int index) {
        if (index != INDEX_ORDER) {
            if (orderListFragment != null) {
                orderListFragment.resetFilter();
            }
        }
        switch (index) {
            case INDEX_HOME:
                homeImg.setImageResource(R.drawable.icon_home_selected);
                homeTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                superMarketImg.setImageResource(R.drawable.icon_super_market);
                superMarketTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderImg.setImageResource(R.drawable.icon_list);
                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my);
                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
                if (versionType == 1) {
                    newHomeFragment.showAddress();
                } else {
                    homeFragment.showAddress();
                }
                break;
            case INDEX_SUPERMARKET:
                homeImg.setImageResource(R.drawable.icon_home);
                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
                superMarketImg.setImageResource(R.drawable.icon_super_market_selected);
                superMarketTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                orderImg.setImageResource(R.drawable.icon_list);
                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my);
                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
                superMarketFragment.showAddress();
                break;
            case INDEX_ORDER:
                homeImg.setImageResource(R.drawable.icon_home);
                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
                superMarketImg.setImageResource(R.drawable.icon_super_market);
                superMarketTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderImg.setImageResource(R.drawable.icon_list_selected);
                orderTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                mineImg.setImageResource(R.drawable.icon_my);
                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderListFragment.refreshList();
                break;
            case INDEX_MINE:
                homeImg.setImageResource(R.drawable.icon_home);
                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
                superMarketImg.setImageResource(R.drawable.icon_super_market);
                superMarketTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderImg.setImageResource(R.drawable.icon_list);
                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my_selected);
                mineTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                mineFragment.refreshPage();
                break;
            default:
                break;
        }
    }

    public void setToSuperMarket(long firstTGoodsCategoryId, long secondTGoodsCategoryId) {
        if (pager != null && superMarketFragment != null) {
            superMarketFragment.setTGoodsCategoryId(firstTGoodsCategoryId, secondTGoodsCategoryId);
            pager.setCurrentItem(INDEX_SUPERMARKET);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_act_layout_home:
                if (pager.getCurrentItem() != INDEX_HOME) {
                    pager.setCurrentItem(INDEX_HOME, false);
                }
                if (App.isLogin()){
                    showReceiverRedBagDialog();
                }
                break;
            case R.id.home_act_layout_super_market:
                if (pager.getCurrentItem() != INDEX_SUPERMARKET) {
                    pager.setCurrentItem(INDEX_SUPERMARKET, false);
                }
                break;
            case R.id.home_act_layout_order:
                if (pager.getCurrentItem() != INDEX_ORDER) {
                    pager.setCurrentItem(INDEX_ORDER, false);
                }
                break;
            case R.id.home_act_layout_mine:
                if (pager.getCurrentItem() != INDEX_MINE) {
                    pager.setCurrentItem(INDEX_MINE, false);
                }
                break;
            case R.id.update_cancel:
                mManager.start();
                mTvTip.setVisibility(View.INVISIBLE);
                mTvTip1.setVisibility(View.INVISIBLE);
                mLlButtonLabel.setVisibility(View.INVISIBLE);
                mLlUpdatingLabel.setVisibility(View.VISIBLE);
                if (mImgDelete.getVisibility() == View.INVISIBLE)
                    mImgDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.update_start:
                hiddenUpdateDialog();
                break;
            case R.id.update_delete:
                mManager.stop();
                hiddenUpdateDialog();
                break;
            case R.id.redbag_nologin_imageview:
            case R.id.redbag_login_imageview1:
            case R.id.redbag_login_imageview:
                hiddenReceiverRedBagDialog();
                break;
            case R.id.login_textview:
                hiddenReceiverRedBagDialog();
                if (App.isLogin()) {
                    startActivityForResult(new Intent(mActivity, BindMobileActivity.class), LOGIN_RECEIVER_REDBAG);
                } else {
                    startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), LOGIN_RECEIVER_REDBAG);
                }
                break;
            default:
                break;
        }

    }

    public void registeLocation() {
        LocationManager.getIManager().registeLocation(getApplicationContext(), locationListener);
    }

    private void hiddenUpdateDialog() {
        if (mUpdateDialog != null && mUpdateDialog.isShowing()) {
            mUpdateDialog.dismiss();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        changeTabUi(arg0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case RESULT_OK:
                if (orderListFragment != null) {
                    orderListFragment.refreshList();
                }
                break;
            case LOCATION_RESPOND_CODE:
                isSwitchLocation = true;
                getNewHomePage();
//                if (pager != null && homeFragment != null && homeFragment.getView() != null && pager.getCurrentItem() == INDEX_HOME) {
//                    homeFragment.showAddress();
//                }
//                if (pager != null && newHomeFragment != null && newHomeFragment.getView() != null && pager.getCurrentItem() == INDEX_HOME) {
//                    newHomeFragment.showAddress();
//                }
//                if (pager != null && superMarketFragment != null && superMarketFragment.getView() != null && pager.getCurrentItem() == INDEX_SUPERMARKET) {
//                    superMarketFragment.showAddress();
//                }
                getInformationArea();
                break;
            case GROUP:
                pager.setCurrentItem(INDEX_HOME, false);
                break;
            case SUPERMARKET:
                pager.setCurrentItem(INDEX_SUPERMARKET, false);
                break;
            case MineFragment.LOGIN_IN_SUCCESS:
                isSwitchLocation = true;
                break;
            default:
                break;
        }
        if (requestCode == LOGIN_RECEIVER_REDBAG) {
            showReceiverRedBagDialog();
        }
    }

    public void getInformationArea() {
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<InformationAreaModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_AREA, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    PreferenceUtils.saveInformationArea(JSON.toJSONString(((InformationAreaModel) obj).getValue()), mActivity);
                }
            }
        }, InformationAreaModel.class);
    }

//    public void getSkyRedbagActivity() {
//        Map<String, Object> map = new HashMap<>();
//        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
//            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
//            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
//        } else {
//            return;
//        }
//        VolleyOperater<CarFirstOrderActivityModel> operater = new VolleyOperater<>(mActivity);
//        operater.doRequest(Constants.URL_FIND_INDEX_POP_ACTIVITY, map, new VolleyOperater.ResponseListener() {
//            @Override
//            public void onRsp(boolean isSucceed, Object obj) {
//                if (isSucceed && obj != null) {
//                    if (obj instanceof String) {
//                        return;
//                    }
//                    Promotion activity = ((CarFirstOrderActivityModel) obj).getValue().getCarFirstOrder();
//                    if (activity != null) {
//                        FirstCallCarDialog dialog = new FirstCallCarDialog(mActivity, new FirstCallCarDialog.onBtnClickListener() {
//                            @Override
//                            public void onSure() {
//                                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "callCar");
//                            }
//                        }, activity.getDiscountAmount());
//                        dialog.show();
//                        return;
//                    }
//                    ArrayList<RedBag> redBags = ((CarFirstOrderActivityModel) obj).getValue().getRedBagList();
//                    if (CheckUtils.isNoEmptyList(redBags)) {
//                        RedbagDialog dialog = new RedbagDialog(mActivity, new RedbagDialog.onBtnClickListener() {
//                            @Override
//                            public void onSure() {
//                                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "callCar");
//                            }
//                        }, redBags);
//                        dialog.show();
//                    }
//                }
//            }
//        }, CarFirstOrderActivityModel.class);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        if (intent.getAction() != null && intent.getAction().equals(CHANGE_PICKED_ADDRESS) &&
//                pager != null && homeFragment != null) {
//            if (pager.getCurrentItem() != INDEX_HOME) {
//                pager.setCurrentItem(INDEX_HOME);
//            }
//            //homeFragment.showAddress();
//        }
//        if (intent.getAction() != null && intent.getAction().equals(CHANGE_PICKED_ADDRESS) &&
//                pager != null && newHomeFragment != null) {
//            if (pager.getCurrentItem() != INDEX_HOME) {
//                pager.setCurrentItem(INDEX_HOME);
//            }
//            //newHomeFragment.showAddress();
//        }

        getNewHomePage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = PreferenceUtils.getStringPreference("token", "", this);
        if (!"".equals(token)) {
            doLogin();
        } /*else {
            showReceiverRedBagDialog(1);
        }*/

        if (App.isLogin() && !isSwitchLocation){
            showReceiverRedBagDialog();
        }
        isSwitchLocation = false;
    }

    /**
     * 验证码登录操作
     */
    private void doLogin() {
        VolleyOperater<AppLaunchModel> operater = new VolleyOperater<>(HomeActivity.this);
        operater.doRequest(Constants.URL_INIT_APP, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    AppLaunchModel appLaunchModel = (AppLaunchModel) obj;
                    if (appLaunchModel.getCode() == 0) {
                        SmsLoginModel.ValueEntity.AppUserEntity appUserEntity = new SmsLoginModel.ValueEntity.AppUserEntity();
                        AppLaunchModel.ValueEntity valueEntity = appLaunchModel.getValue();
                        appUserEntity.setId(valueEntity.getId());
                        appUserEntity.setCreateTime(valueEntity.getCreateTime());
                        appUserEntity.setModifyTime(valueEntity.getModifyTime());
                        appUserEntity.setName(valueEntity.getName());
                        appUserEntity.setMobile(valueEntity.getMobile());
                        appUserEntity.setPwd(valueEntity.getPwd());
                        appUserEntity.setHeaderImg(valueEntity.getHeaderImg());
                        appUserEntity.setRegTime(valueEntity.getRegTime());
                        appUserEntity.setLastLoginTime(valueEntity.getLastLoginTime());
                        appUserEntity.setChannel(valueEntity.getChannel());
                        appUserEntity.setToken(valueEntity.getToken());
                        //极光推送TAG绑定
                        if (!TextUtils.isEmpty(valueEntity.getAgentId())) {
                            JPushInterface.checkTagBindState(mActivity, 101, "agent_" + valueEntity.getAgentId());
                        }
                        App.setUserInfo(appUserEntity);
                        App.setIsLogin(true);
                    } else if (appLaunchModel.getCode() == 100000) {
                        PreferenceUtils.saveStringPreference("token", "", HomeActivity.this);
                        //清空缓存
                        CookieSyncManager.createInstance(mActivity);  //Create a singleton CookieSyncManager within a context
                        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
                        cookieManager.removeAllCookie();// Removes all cookies.
                        cookieManager.setAcceptCookie(true);
                        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
                        System.gc();
                    }
                    getAddressList();
                } else {
                    getAddressList();
                }
            }
        }, AppLaunchModel.class);
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        VolleyOperater<UpdateModel> operater = new VolleyOperater<>(HomeActivity.this);
        operater.doRequest(Constants.URL_UPDATE_APP, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    UpdateModel updateModel = (UpdateModel) obj;
                    if (updateModel.getCode() == 0) {
                        AppVersion value = updateModel.getValue();
                        if (value == null) {
                            return;
                        }
                        String clientVersion = value.getClientVersion();
                        if (Integer.parseInt(clientVersion) > CommonUtils.getCurrentVersionCode()) {
                            if (value.getIsCoerceUpdate() == 1) {
                                mTvStart.setEnabled(false);
                                mImgDelete.setEnabled(false);
                                mImgDelete.setVisibility(View.GONE);
                                mTvCancel.setText("强制更新");
                            }
                            mManager = new FileDownloadManager(value.getDownloadUrl(), getResources().getString(R.string.app_name) + ".apk");
                            mManager.setListener(mListener);
                            mUpdateDialog.show();
                        }
                    }
                }
            }
        }, UpdateModel.class);
    }

    private FileDownloadManager.IDownloadProgressChangedListener mListener = new FileDownloadManager.IDownloadProgressChangedListener() {
        @Override
        public void onProgressChanged(long completeSize, long totalSize) {
            Log.d("HOME", "----size---->" + completeSize + ";----total---->" + totalSize);
            mProgressBar.setMax((int) totalSize);
            mProgressBar.setProgress((int) completeSize);
        }

        @Override
        public void onStateChanged(int state) {
            Log.d("STATE", "---->" + state);
            if (state == FileDownloadManager.STATE_FINISH) {//&& mMTotalSize != 0
                mUpdateDialog.dismiss();
                CommonUtils.openFile(mActivity, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getResources().getString(R.string.app_name) + ".apk"));
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == INDEX_HOME && homeFragment != null && homeFragment.isPopupWindowShowing()) {
            homeFragment.hidePopupWindow();
            return;
        }
        if (pager.getCurrentItem() == INDEX_HOME && newHomeFragment != null && newHomeFragment.isPopupWindowShowing()) {
            newHomeFragment.hidePopupWindow();
            return;
        }
        switch (exitFlag++) {
            case 0:
                ToastUtils.displayMsg("再按一次，退出程序", mActivity);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        exitFlag = 0;
                    }
                }, 3 * 1000);
                break;
            case 1:
                if (orderListFragment != null)
                    orderListFragment.resetType();
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止程序退出下载thread继续跑
        if (mManager != null) {
            mManager.stop();
        }
        if (mManager2 != null) {
            mManager2.stop();
        }
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    /**
     * 获取新版首页
     * http://120.24.16.64/merchant/userClient?m=findMerchantShopAgent
     * com.project.mgjandroid.net.volley.ParseError: com.alibaba.fastjson.JSONException: syntax error, expect {, actual error
     */
    public void getNewHomePage() {
        Log.i("HomeAvtivity", "getNewHomePage::");
        VolleyOperater<HomeVersionModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
        map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        operater.doRequest(Constants.URL_FIND_APP_HOME_PAGE_VERSION, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    HomeVersionModel model = (HomeVersionModel) obj;

                    if (model.getValue().getAppHomePageVersion() != null) {
                        Log.i("HomeAvtivity", "getNewHomePage::model.getValue().getAppHomePageVersion().getVersionType():" + model.getValue().getAppHomePageVersion().getVersionType());
                        versionType = model.getValue().getAppHomePageVersion().getVersionType();
                        agentId = model.getValue().getAppHomePageVersion().getAgentId();
                        String secondMenuTypeName = model.getValue().getAppHomePageVersion().getSecondMenuTypeName();
                        PreferenceUtils.saveIntPreference("versionType", versionType);
                        PreferenceUtils.saveStringPreference("secondMenuTypeName", secondMenuTypeName, mActivity);
                        PreferenceUtils.saveLongPreference("issueAgentId", (long) agentId, mActivity);
                        PreferenceUtils.saveIntPreference("agentId", agentId);
                    } else {
                        versionType = 0;//老版本
                        PreferenceUtils.saveIntPreference("versionType", versionType);
                    }
                }
                updataFragment();
            }
        }, HomeVersionModel.class);
    }

    /**
     * 注册一个定位监听
     */
    BDLocationListener locationListener = new BDLocationListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceiveLocation(BDLocation location) {
            LocationManager.getIManager().stopLocation();
            Log.i("onReceiveLocation", "onReceiveLocation::");
            BaseFragment fragment = (BaseFragment) homePagerAdapter.getItem(0);

            if (location != null) {

                if (CheckUtils.isNoEmptyStr(location.getAddrStr())) {
                    PreferenceUtils.saveAddressName(location.getAddrStr(), mActivity);
                }
                if (CheckUtils.isNoEmptyList(location.getPoiList())) {
                    List<Poi> list = location.getPoiList();
                    PreferenceUtils.saveAddressDes(list.get(0).getName(), mActivity);
                }
                if (location.getAddress() != null && location.getAddress().city != null) {
                    PreferenceUtils.saveAddressCity(location.getAddress().city, mActivity);
                }
                if (location.getAddress() != null && location.getAddress().cityCode != null) {
                    PreferenceUtils.saveAddressCityCode(location.getAddress().cityCode, mActivity);
                }
                if ("4.9E-324".equals("" + location.getLatitude()) || "4.9E-324".equals("" + location.getLongitude())) {
                    ToastUtils.displayMsg("定位失败,请检查马管家定位权限是否开启", HomeActivity.this);
                    locationFail(fragment);

                } else {
                    PreferenceUtils.saveBoolPreference("isOpenLocationFailDialog", false, getApplicationContext());
                    PreferenceUtils.saveLocation(location.getLatitude() + "", location.getLongitude() + "", mActivity);
                    //保存定位信息,因为上面保存的位置会因为手动选择而改变
                    PreferenceUtils.saveFixedLocation(location.getLatitude() + "", location.getLongitude() + "", location.getAddrStr(), mActivity);
                    getNewHomePage();
                    getInformationArea();
                }

            } else {
                locationFail(fragment);
            }

        }

    };


    private void locationFail(BaseFragment fragment) {
        PreferenceUtils.saveAddressName("", mActivity);
        PreferenceUtils.saveBoolPreference("isOpenLocationFailDialog", true, getApplicationContext());
        if (fragment instanceof HomeFragment) {
            Log.i("onReceiveLocation", "onReceiveLocation::HomeFragment::");
            ((HomeFragment) fragment).setUserAddressList(userAddressList);
            ((HomeFragment) fragment).showLoactionFailAddress();
        } else {
            Log.i("onReceiveLocation", "onReceiveLocation::NewHomeFragment::");
            ((NewHomeFragment) fragment).setUserAddressList(userAddressList);
            ((NewHomeFragment) fragment).showLoactionFailAddress();
        }
    }


    private void getAddressList() {
        if (App.isLogin()) {
            Map<String, Object> map = new HashMap<String, Object>();
            VolleyOperater<AddressManageModel> operater = new VolleyOperater<AddressManageModel>(mActivity);
            operater.doRequest(Constants.URL_GET_ADDRESS, map, new VolleyOperater.ResponseListener() {
                @Override
                public void onRsp(boolean isSucceed, Object obj) {
                    if (isSucceed && obj != null) {
                        userAddressList = ((AddressManageModel) obj).getValue();
                        Log.i("getAddressList", "getAddressList::getAddressList:" + userAddressList.size());
                    }
                }
            }, AddressManageModel.class);
        }
    }

    private void updataFragment() {
        Log.d("HomeActivity", "updataFragment::");
        if (homePagerAdapter != null) {
            ArrayList<BaseFragment> fragments = homePagerAdapter.getFragments();
            BaseFragment currentFragment = fragments.get(0);
            if (versionType == 1 && currentFragment instanceof HomeFragment) {
                fragments.set(0, newHomeFragment);
                superMarketLayout.setVisibility(View.GONE);
            } else if (versionType == 0 && currentFragment instanceof NewHomeFragment) {
                fragments.set(0, homeFragment);
                superMarketLayout.setVisibility(View.VISIBLE);
            }
            BaseFragment fragment = (BaseFragment) homePagerAdapter.getItem(0);
            homePagerAdapter.notifyDataSetChanged();
            if (fragment instanceof HomeFragment) {
                ((HomeFragment) fragment).setUserAddressList(userAddressList);
                ((HomeFragment) fragment).showAddress();
            } else {
                ((NewHomeFragment) fragment).setUserAddressList(userAddressList);
                ((NewHomeFragment) fragment).showAddress();
            }

        }

        if (versionType == 1) {
            if (pager.getCurrentItem() != INDEX_HOME) {
                pager.setCurrentItem(INDEX_HOME, false);
            }
        } else {
            if (pager.getCurrentItem() == INDEX_SUPERMARKET) {
                superMarketFragment.showAddress();
            }
        }
    }

    /**
     * 获取闪屏动画
     */
    public void getSplashGif() {
        VolleyOperater<FestivalModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        operater.doRequest(Constants.URL_GET_OPEN_GIF, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                // 闪屏gif
                if (isSucceed && obj != null) {
                    if (!(obj instanceof String)) {
                        FestivalModel model = (FestivalModel) obj;
                        // 0未生效  1生效
                        int status = model.getValue().getStatus();
                        if (status == 1) {
                            String img = model.getValue().getData().getImg();
                            if (img.lastIndexOf("/") != -1) {
                                gifName = img.substring(img.lastIndexOf("/") + 1, img.length());
                            } else {
                                gifName = img;
                            }
                            //判断当前是否有该gif文件
                            String gifUri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + gifName;
                            String splashGif = PreferenceUtils.getStringPreference("splashGif", "", mActivity);
                            if (new File(gifUri).exists() && gifName.equals(splashGif)) {
                                //存在
                                PreferenceUtils.saveStringPreference("splashGif", gifName, mActivity);
                                return;
                            }
                            mManager2 = new FileDownloadManager(img, gifName);
                            mManager2.setListener(new FileDownloadManager.IDownloadProgressChangedListener() {
                                @Override
                                public void onProgressChanged(long completeSize, long totalSize) {

                                }

                                @Override
                                public void onStateChanged(int state) {
                                    if (state == FileDownloadManager.STATE_FINISH) {
                                        //保存路径
                                        Log.e("TAG", "onRsp: 下载完成");
                                        PreferenceUtils.saveStringPreference("splashGif", gifName, mActivity);
                                    }
                                }
                            });
                            mManager2.start();
                        } else {
                            //清除路径
                            PreferenceUtils.saveStringPreference("splashGif", "", mActivity);
                        }
                    }
                }
            }
        }, FestivalModel.class);
    }
}
