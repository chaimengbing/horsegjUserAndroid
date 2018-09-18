package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.BuildConfig;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.UserFavorites;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.H5TestActivity;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.GetAlipayInfoModel;
import com.project.mgjandroid.model.MyGroupModel;
import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BalanceOperateActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.MerchantCollectionActivity;
import com.project.mgjandroid.ui.activity.MoreSettingActivity;
import com.project.mgjandroid.ui.activity.MyPublishCategoryActivity;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.activity.NewEvaluateActivity;
import com.project.mgjandroid.ui.activity.NoticeActivity;
import com.project.mgjandroid.ui.activity.RiderActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.UserInfoActivity;
import com.project.mgjandroid.ui.activity.invitingfriends.InvitingFriendsActivity;
import com.project.mgjandroid.ui.activity.pintuan.MyGroupPurchaseActivity;
import com.project.mgjandroid.ui.adapter.HomePlatFormRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.SelUrlAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的
 *
 * @author jian
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final int LOGIN_IN = 303;
    public static final int LOGIN_IN_SUCCESS = 304;
    private static final int TO_MORE_SETTING = 305;
    public static final int EXIT_APP_SUCCESS = 306;
    private static final int TO_EVALUATE = 307;
    private static final int TO_BALANCE_OPERATE = 308;
    protected Activity mActivity;
    protected View view;
    private RoundImageView userAvatar;
    private TextView tvName;
    private TextView tvBalance;
    private TextView tvBalanceUnit;
    private TextView tvRedbag;
    private TextView tvRedBagUnit;
    private TextView tvBankCard;
    private TextView tvBankCardUnit;
    private TextView tvMobile;
    private boolean firstIn;
    private ImageView mobileIcon;

    private int second = 60;
    private CallPhoneDialog dialog;
    private static MineFragment fragment;
    private RelativeLayout rlPintuan;
    private View vPintuan;
    private Dialog avatarDialog;
    private UserFavorites submitOrderEntity;
    private String mgjPhone;
    private String constomer;
    private String agentMobile;
    private RelativeLayout rlServerCenter;
    private RelativeLayout contentView;
    private String balance;
    private RelativeLayout rlInvite;

    private CommonDialog selUrlDialog;

    public static MineFragment newInstance() {
        if (fragment == null) {
            fragment = new MineFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();
        view = inflater.inflate(R.layout.mine_fragment, container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPage();
    }

    private void initView() {

        RelativeLayout tvAddressManage = ViewFindUtils.find(view, R.id.mine_fragment_address_manager);
        LinearLayout rlUserInfo = ViewFindUtils.find(view, R.id.mine_fragment_layout_to_user_info);
        userAvatar = ViewFindUtils.find(view, R.id.mine_fragment_avatar);
        TextView tvLogin = ViewFindUtils.find(view, R.id.mine_fragment_tv_login);
        tvName = ViewFindUtils.find(view, R.id.mine_fragment_username);
        tvBalance = ViewFindUtils.find(view, R.id.mine_fragment_balance);
        tvBalanceUnit = ViewFindUtils.find(view, R.id.mine_fragment_balance_unit);
        tvRedbag = ViewFindUtils.find(view, R.id.mine_fragment_red_bag_count);
        tvRedBagUnit = ViewFindUtils.find(view, R.id.mine_fragment_red_bag_unit);
        tvBankCard = ViewFindUtils.find(view, R.id.mine_fragment_bank_card_count);
        tvBankCardUnit = ViewFindUtils.find(view, R.id.mine_fragment_bank_card_unit);
        tvMobile = ViewFindUtils.find(view, R.id.mine_fragment_mobile);
        mobileIcon = ViewFindUtils.find(view, R.id.mine_fragment_mobile_icon);
        RelativeLayout moreSetting = ViewFindUtils.find(view, R.id.mine_fragment_setting);
        ImageView notice = ViewFindUtils.find(view, R.id.mine_fragment_notice);
        RelativeLayout rlEvaluate = ViewFindUtils.find(view, R.id.mine_fragment_score);
        RelativeLayout rlFeedBack = ViewFindUtils.find(view, R.id.mine_fragment_feed_back);
        RelativeLayout rlAgentJoin = ViewFindUtils.find(view, R.id.mine_fragment_agent_join);
        RelativeLayout rlMerchantJoin = ViewFindUtils.find(view, R.id.mine_fragment_merchant_join);
        LinearLayout llBalanceOperate = ViewFindUtils.find(view, R.id.mine_fragment_balance_operate);
        LinearLayout llRedPackage = ViewFindUtils.find(view, R.id.red_package);
        LinearLayout llMyCards = ViewFindUtils.find(view, R.id.mine_cards);
        RelativeLayout rlMySave = ViewFindUtils.find(view, R.id.mine_fragment_my_save);
        rlServerCenter = ViewFindUtils.find(view, R.id.mine_fragment_serve_center);
        RelativeLayout rlAboutUs = ViewFindUtils.find(view, R.id.mine_fragment_about);
        RelativeLayout rlPublish = ViewFindUtils.find(view, R.id.mine_fragment_my_publish);
        rlPintuan = ViewFindUtils.find(view, R.id.mine_fragment_pintuan);
        vPintuan = ViewFindUtils.find(view, R.id.mine_fragment_pintuan_line);
        rlInvite = ViewFindUtils.find(view, R.id.mine_fragment_my_invite);
        TextView tvTestWeb = ViewFindUtils.find(view, R.id.tv_test_web);
        TextView tvSwitchUrl = ViewFindUtils.find(view, R.id.tv_switch_url);
        //新增投诉
        RelativeLayout telNum = ViewFindUtils.find(view, R.id.mine_fragment_tel_num);
        contentView = (RelativeLayout) View.inflate(mActivity, R.layout.pick_or_take_photo_dialog, null);
        avatarDialog = new Dialog(mActivity, R.style.fullDialog);
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        telNum.setOnClickListener(this);
//        tvLogin.setOnClickListener(this);
        rlUserInfo.setOnClickListener(this);
        tvAddressManage.setOnClickListener(this);
        moreSetting.setOnClickListener(this);
        notice.setOnClickListener(this);
        rlEvaluate.setOnClickListener(this);
        llBalanceOperate.setOnClickListener(this);
        llRedPackage.setOnClickListener(this);
        llMyCards.setOnClickListener(this);
        rlMySave.setOnClickListener(this);
        rlServerCenter.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlFeedBack.setOnClickListener(this);
        rlAgentJoin.setOnClickListener(this);
        rlMerchantJoin.setOnClickListener(this);
        tvSwitchUrl.setOnClickListener(this);
        rlPublish.setOnClickListener(this);
        rlPintuan.setOnClickListener(this);
        rlInvite.setOnClickListener(this);
        tvTestWeb.setOnClickListener(this);

        if (BuildConfig.IS_DEBUG) {
            tvTestWeb.setVisibility(View.VISIBLE);
            tvSwitchUrl.setVisibility(View.VISIBLE);
            initSelUrlsDialog();
        } else {
            tvTestWeb.setVisibility(View.GONE);
            tvSwitchUrl.setVisibility(View.GONE);
        }


    }


    private void initSelUrlsDialog() {
        View view = mInflater.inflate(R.layout.select_url_dialog_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.select_url_listview);
        final SelUrlAdapter selUrlAdapter = new SelUrlAdapter(getActivity(), getResources().getStringArray(R.array.sel_urls));

        listView.setAdapter(selUrlAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PreferenceUtils.saveStringPreference(PreferenceUtils.SELECT_TEST_URL, (String) selUrlAdapter.getItem(i), getActivity());
                App.setIsLogin(false);
                App.setUserInfo(null);
                PreferenceUtils.saveStringPreference("token", "", mActivity);
                String latitude = PreferenceUtils.getFixedLocation(getActivity())[0];
                String longitude = PreferenceUtils.getFixedLocation(getActivity())[1];
                if (!(CheckUtils.isNoEmptyStr(latitude) && CheckUtils.isNoEmptyStr(longitude) && !"4.9E-324".equals(latitude) && !"4.9E-324".equals(longitude) && !"0.0".equals(latitude) && !"0.0".equals(longitude))) {
                    //定位失败清空地址
                    PreferenceUtils.saveAddressName("", getActivity());
                }
                dismiss();
                restartApp();
            }
        });
        selUrlDialog = new CommonDialog(mActivity, view, this);
        selUrlDialog.setCancelable(true);
    }

    private void restartApp() {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        System.exit(0);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
//                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(LaunchIntent);
//            }
//        }, 1000);// 1秒钟后重启应用

    }

    private void showSelUrlsDialog() {
        if (selUrlDialog != null) {
            selUrlDialog.show();
        }
    }

    private void dismiss() {
        if (selUrlDialog != null && selUrlDialog.isShowing()) {
            selUrlDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_fragment_address_manager:
                if (CommonUtils.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, AddressManageActivity.class));
                }
                break;
            case R.id.mine_fragment_tv_login:
                startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), LOGIN_IN);
                break;
            case R.id.mine_fragment_layout_to_user_info:
//                if (CommonUtils.checkLogin(mActivity)) {
//                    startActivity(new Intent(mActivity, UserInfoActivity.class).putExtra("balance", balance));
//                }
                startActivity(new Intent(mActivity,NewEvaluateActivity.class));
                break;
            case R.id.mine_fragment_setting:
                startActivityForResult(new Intent(mActivity, MoreSettingActivity.class), TO_MORE_SETTING);
                break;
            case R.id.mine_fragment_notice:
                if (CommonUtils.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, NoticeActivity.class));
                }
                break;
            case R.id.mine_fragment_score:
//                if(hasAnyMarketInstalled(mActivity)) {
                Intent intent1 = new Intent("android.intent.action.VIEW");
                intent1.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                startActivity(intent1);
//                }else{
//                    ToastUtils.displayMsg("手机上没有应用市场",mActivity);
//                }
                break;
            case R.id.mine_fragment_feed_back:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_FEED_BACK);
                break;
            case R.id.mine_fragment_balance_operate:
//				因为需判定登录状态
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, BalanceOperateActivity.class);
                    startActivityForResult(intent, TO_BALANCE_OPERATE);
                }
                break;
            case R.id.mine_fragment_my_save:
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MerchantCollectionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_serve_center:
                toWebView("http://120.24.16.64/horsegj/dist/html/user/serviceCenter.html", true);
                break;
            case R.id.red_package:
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyRedBagActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_about://我是商家
                ToastUtils.displayMsg("暂不支持，敬请期待", mActivity);
                break;
            case R.id.mine_cards:
                if (CommonUtils.checkLogin(mActivity)) {
                    //Intent intent = new Intent(mActivity, MyBankCardActivity.class);
                    Intent intent = new Intent(mActivity, InvitingFriendsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_my_publish:
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyPublishCategoryActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_pintuan:
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyGroupPurchaseActivity.class);
                    startActivityForResult(intent, HomeActivity.GROUP);
                }
                break;
            case R.id.mine_fragment_tel_num:
                getTelNumXY(Double.parseDouble(PreferenceUtils.getLocation(mActivity)[0]), Double.parseDouble(PreferenceUtils.getLocation(mActivity)[1]));
                initAvatarDialog();
                break;
            case R.id.btn_take_photo:
                if (CheckUtils.isTelNum(mgjPhone)) {
                    mgjPhone = getResources().getString(R.string.sale_phone);
                }
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        //submitOrderEntity.getMerchant().getContacts() 商家电话
                        intent.setData(Uri.parse("tel:" + mgjPhone));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", mgjPhone);
                dialog.show();
                avatarDialog.dismiss();
                break;
            case R.id.btn_pick_photo:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + agentMobile));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", agentMobile);
                dialog.show();
                avatarDialog.dismiss();
                break;
            case R.id.mine_fragment_agent_join:
                toWebView("http://123.56.15.86/horsegj/dist/html/user/joinCoopera.html");
                break;
            case R.id.mine_fragment_merchant_join:
                toWebView("http://123.56.15.86/horsegj/dist/html/user/joinCoopera.html");
                break;
            case R.id.mine_fragment_my_invite://邀请好友
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, InvitingFriendsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_test_web:
                Intent it = new Intent(mActivity, H5TestActivity.class);
                startActivity(it);
                break;
            case R.id.tv_switch_url:
                showSelUrlsDialog();
                break;
            default:
                break;
        }
    }

    private void toWebView(String url) {
        toWebView(url, false);
    }

    private void toWebView(String url, boolean isTitleColor) {
        Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
        intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, url);
        intent.putExtra(YLBSdkConstants.EXTRA_H5_TITLE_COLOR, isTitleColor);
        startActivity(intent);
    }

    /**
     * 投诉电话联系
     */
    private void initAvatarDialog() {
        Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        View line = contentView.findViewById(R.id.line);
        dialog_bt_take_photo.setText("客服热线");
        dialog_bt_pick_photo.setText("区域负责人");
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        if (CheckUtils.isTelNum(agentMobile)) {
            dialog_bt_pick_photo.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            dialog_bt_pick_photo.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
        avatarDialog.show();
    }

    private void showDialog() {
        dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:" + constomer));
                startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", constomer);

        dialog.show();
    }

    /**
     * 获取余额、红包、银行卡信息
     */
    private void getExtraMoney() {
        VolleyOperater<UserAccountModel> operater = new VolleyOperater<UserAccountModel>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_CENTER, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    UserAccountModel userAccountModel = (UserAccountModel) obj;
                    UserAccountModel.ValueEntity value = userAccountModel.getValue();
                    setAccountView(value);
                }
            }
        }, UserAccountModel.class);
    }

    private void setAccountView(UserAccountModel.ValueEntity value) {
        if (value == null) return;

//        if (value.getBalance() != null && value.getBalance().compareTo(BigDecimal.ZERO) > 0) {
//            tvBalance.setTextColor(getResources().getColor(R.color.color_6));
//            tvBalanceUnit.setTextColor(getResources().getColor(R.color.color_6));
//        } else {
//            tvBalance.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//            tvBalanceUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//        }
//        if (value.getUserBankCount() > 0) {
//            tvBankCard.setTextColor(getResources().getColor(R.color.mine_number_color_red));
////            tvBankCardUnit.setTextColor(getResources().getColor(R.color.mine_number_color_red));
//        } else {
//            tvBankCard.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//            tvBankCardUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//        }
//        if (value.getRedBagCount() > 0) {
//            tvRedbag.setTextColor(getResources().getColor(R.color.mine_number_color_red));
////            tvRedBagUnit.setTextColor(getResources().getColor(R.color.mine_number_color_red));
//        } else {
//            tvRedbag.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//            tvRedBagUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
//        }
        balance = "";
        if (value.getBalance() != null) {
            balance = StringUtils.BigDecimal2Str(value.getBalance());
        } else {
            balance = "0";
        }
        tvBalance.setText(balance);
        tvBankCard.setText(String.valueOf(value.getCashbackCount()));
        tvRedbag.setText(String.valueOf(value.getRedBagCount()));
    }

    public void refreshPage() {
        if (App.isLogin()) {
//            tvLogin.setVisibility(View.INVISIBLE);
//            moreSetting.setVisibility(View.VISIBLE);
//            notice.setVisibility(View.VISIBLE);
            AppUserEntity userInfo = App.getUserInfo();
            String name = userInfo.getName();
            String mobile = userInfo.getMobile();
            if (name == null) {
                tvName.setText(mobile);
            } else {
                tvName.setText(name);
            }
            if (!TextUtils.isEmpty(mobile)) {
                tvMobile.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
                mobileIcon.setVisibility(View.VISIBLE);
            } else {
                tvMobile.setText("");
                mobileIcon.setVisibility(View.GONE);
            }
            String headerImg = userInfo.getHeaderImg();

            userAvatar.setImageResource(R.drawable.user_avatar);
            if (headerImg != null) {
                ImageUtils.loadBitmap(mActivity, headerImg + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, userAvatar, R.drawable.user_avatar, "");
            }
            getExtraMoney();
            hasMyGroupInfo();
        } else {
//            tvLogin.setVisibility(View.VISIBLE);
//            moreSetting.setVisibility(View.GONE);
//            notice.setVisibility(View.GONE);
            mobileIcon.setVisibility(View.GONE);
            tvName.setText("点击登录");
            tvMobile.setText("注册登录可抢现金大奖");
            tvBalance.setText("0");
            tvRedbag.setText("0");
            userAvatar.setImageResource(R.drawable.user_avatar);
            setAccountView(new UserAccountModel.ValueEntity());

            rlPintuan.setVisibility(View.GONE);
            vPintuan.setVisibility(View.GONE);
        }
    }

    private void hasMyGroupInfo() {
        VolleyOperater<MyGroupModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("start", 0);
        map.put("size", 10);
        operater.doRequest(Constants.URL_FIND_GROUP_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        rlPintuan.setVisibility(View.GONE);
                        vPintuan.setVisibility(View.GONE);
                        return;
                    }
                    MyGroupModel model = (MyGroupModel) obj;
                    List<MyGroupModel.ValueEntity> list = model.getValue();
                    if (CheckUtils.isNoEmptyList(list)) {
                        rlPintuan.setVisibility(View.VISIBLE);
                        vPintuan.setVisibility(View.VISIBLE);
                    } else {
                        rlPintuan.setVisibility(View.GONE);
                        vPintuan.setVisibility(View.GONE);
                    }
                }
            }
        }, MyGroupModel.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case LOGIN_IN_SUCCESS:
                break;
            case EXIT_APP_SUCCESS:
                refreshPage();
                break;
        }
    }

    public static boolean hasAnyMarketInstalled(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("market://details?id=android.browser"));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0 != list.size();
    }

    private void getTelNumXY(double latitude, double longitude) {
        final Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            latitude = Double.parseDouble(PreferenceUtils.getLocation(mActivity)[0]);
            longitude = Double.parseDouble(PreferenceUtils.getLocation(mActivity)[1]);
            map.put("latitude", latitude);
            map.put("longitude", longitude);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_XY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    constomer = "";
                    agentMobile = "";
                    CustomerAndComplainPhoneDTOModel model = (CustomerAndComplainPhoneDTOModel) obj;
                    for (int i = 0; i < model.getValue().size(); i++) {
                        if (model.getValue() != null && 2 == model.getValue().get(i).getType()) {
                            // 2：投诉电话 (区域负责人)
                            agentMobile = model.getValue().get(i).getPhone();
                        } else if (model.getValue() != null && 3 == model.getValue().get(i).getType()) {
                            //总部热线
                            mgjPhone = model.getValue().get(i).getPhone();
                            PreferenceUtils.saveStringPreference("mgjPhone", mgjPhone, mActivity);
                        } else if (model.getValue() != null && 1 == model.getValue().get(i).getType()) {
                            // 代理商客服电话
                            constomer = model.getValue().get(i).getPhone();
                            PreferenceUtils.saveStringPreference("agentPhone", constomer, mActivity);
                        }
                        if (CheckUtils.isTelNum(constomer)) {
                            rlServerCenter.setVisibility(View.GONE);
                        } else {
                            rlServerCenter.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    public void getLocation(double latitude, double longitude) {
        getTelNumXY(latitude, longitude);
    }

}
