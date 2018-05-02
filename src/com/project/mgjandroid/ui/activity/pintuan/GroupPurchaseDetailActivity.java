package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.GroupUserComments;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.AppLaunchModel;
import com.project.mgjandroid.model.FindGroupAddressModel;
import com.project.mgjandroid.model.GroupDetailModel;
import com.project.mgjandroid.model.ServiceTypeModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.PhotoActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.GroupTimeTextView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/11.
 */
@Router(value = "groupPurchaseDetail/:id")
public class GroupPurchaseDetailActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.com_share)
    private ImageView ivShare;
    @InjectView(R.id.pintuan_evaluate_layout)
    private LinearLayout llEvaluate;
    @InjectView(R.id.previous_evaluate_layout)
    private LinearLayout llEvaluatePrevious;
    @InjectView(R.id.previous_evaluate_count)
    private TextView tvEvaluateCount;
    @InjectView(R.id.pintuan_image_layout)
    private LinearLayout llImage;
    @InjectView(R.id.person_previous_pintuan)
    private TextView tvHistoryPintuan;
    @InjectView(R.id.previous_evaluate_layout)
    private LinearLayout llPreviousLayout;
    @InjectView(R.id.join_in_pintuan)
    private TextView tvJoinIn;
    @InjectView(R.id.pintuan_detail_image)
    private ImageView ivImage;
    @InjectView(R.id.pintuan_detail_title)
    private TextView tvGoodsName;
    @InjectView(R.id.current_progress)
    private ProgressBar currentProgress;
    @InjectView(R.id.current_progress_indicator)
    private TextView tvProgress;
    @InjectView(R.id.residual_time)
    private GroupTimeTextView tvTimer;
    @InjectView(R.id.group_detail_description)
    private TextView tvDescription;
    @InjectView(R.id.group_detail_service_layout)
    private LinearLayout llService;
    @InjectView(R.id.group_detail_send_days)
    private TextView tvDays;
    @InjectView(R.id.group_detail_admin)
    private TextView tvAdmin;
    @InjectView(R.id.group_detail_admin_score)
    private RatingBar rbScore;
    @InjectView(R.id.group_detail_admin_score_show)
    private TextView tvScore;
    @InjectView(R.id.person_previous_pintuan)
    private TextView tvPrevious;//往期拼团
    @InjectView(R.id.group_detail_price)
    private TextView tvPrice;
    @InjectView(R.id.group_detail_origin_price)
    private TextView tvOriginPrice;
    @InjectView(R.id.group_detail_favor)
    private TextView tvFavor;
    @InjectView(R.id.group_detail_favor_layout)
    private LinearLayout llFavor;
    @InjectView(R.id.detail_banner)
    private MyBanner detailBanner;
    @InjectView(R.id.goods_img)
    private RelativeLayout image_container;
    @InjectView(R.id.font_group_price)
    private TextView fontGroupPrice;
    @InjectView(R.id.font_sign_group_price)
    private TextView fontGroupPriceSign;
    @InjectView(R.id.font_origin_price)
    private TextView fontOriginPrice;
    @InjectView(R.id.group_join_count)
    private TextView joinCount;
    @InjectView(R.id.group_total_count)
    private TextView totalCount;
    @InjectView(R.id.current_price)
    private TextView currentPrice;
    @InjectView(R.id.origin_price)
    private TextView originPrice;

    private String id;
    private MLoadingDialog mMLoadingDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private GroupInfo group;
    private ShareUtil shareUtil;
    private boolean isTrueData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_purchase_detail);
        Injector.get(this).inject();

        initView();
        setData();
        setListener();
    }

    private void setData() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        } else {
            finish();
            return;
        }
        boolean isFromLocal = getIntent().getBooleanExtra("isFromLocal", false);
        if (isFromLocal) {
            getDetail();
        } else {
            doLogin();
        }
    }

    private void getDetail() {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupDetailModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        operater.doRequest(Constants.URL_FIND_GROUP_DETAIL, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    GroupDetailModel model = (GroupDetailModel) obj;
                    group = model.getValue();
                    setView(group);
                }
            }
        }, GroupDetailModel.class);
    }

    private void setView(GroupInfo value) {
        if (value == null) {
            isTrueData = false;
            ToastUtils.displayMsg("数据异常,请稍后重试", mActivity);
            return;
        }
        if (CheckUtils.isNoEmptyStr(value.getImgs())) {
            setImages(value.getImgs());
        } else {
            ArrayList<Integer> list1 = new ArrayList<>();
            list1.add(R.drawable.goods_detail);
            detailBanner.setResources(list1);
        }

        tvGoodsName.setText(value.getGoodsName());
        int curProgress = value.getTotalNum() * 100 / value.getMinNum();
        if (curProgress >= 100) {
            currentProgress.setProgress(100);
        } else {
            currentProgress.setProgress(curProgress);
        }
        tvProgress.setText(curProgress + "%");
        int width = DipToPx.dip2px(mActivity, 35);
        int height = DipToPx.dip2px(mActivity, 12);
        int barWidth = (int) DeviceParameter.getScreenWidth() - DipToPx.dip2px(mActivity, 30);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        int leftMargin = curProgress * barWidth / 100 - width / 2;

        MLog.d("width = " + width + ",height = " + height + ",barWidth = " + barWidth + ",leftMargin = " + leftMargin);
        if (leftMargin < 0)
            leftMargin = 0;
        else if (leftMargin > barWidth - width)
            leftMargin = barWidth - width;
        lp.setMargins(leftMargin, 0, 0, 0);
        tvProgress.setLayoutParams(lp);

        joinCount.setText(value.getTotalNum() + "");
        totalCount.setText(value.getMinNum() + "");
        originPrice.setText("¥" + value.getOriginalPrice());
        originPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        currentPrice.setText(value.getPrice() + "");
        tvDescription.setText(value.getDescription());
        if (CheckUtils.isNoEmptyStr(value.getService())) {
            addService(value.getService());
        } else {
            llService.setVisibility(View.GONE);
        }
        tvDays.setText("拼团成功后" + value.getDeliveryDays() + "天内发货，人数不足自动退款");
        tvAdmin.setText("发起人：" + value.getGroupBuyUser().getName());
        rbScore.setRating(value.getGroupBuyUser().getCompositeAvgScore().floatValue());
        tvScore.setText(value.getGroupBuyUser().getCompositeAvgScore().floatValue() + "分");

        if ((value.getPrice() + "").length() + (value.getOriginalPrice() + "").length() >= 11) {
            fontGroupPrice.setTextSize(13);
            fontGroupPriceSign.setTextSize(10);
            tvPrice.setTextSize(17);
            fontOriginPrice.setTextSize(10);
            tvOriginPrice.setTextSize(10);
        }
        tvPrice.setText(value.getPrice() + "");
        tvOriginPrice.setText("¥" + value.getOriginalPrice());
        tvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (CheckUtils.isNoEmptyStr(value.getMarketingImg())) {
            llImage.setVisibility(View.VISIBLE);
            String[] imgs = value.getMarketingImg().split(";");
            for (int i = 0; i < imgs.length; i++) {
                View view = getLayoutInflater().inflate(R.layout.view_group_agent_image, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                if (CheckUtils.isNoEmptyStr(imgs[i])) {
                    ImageUtils.loadBitmapFitCenter(mActivity, imgs[i], imageView, R.drawable.horsegj_default, "");
                    llImage.addView(view);
                } else {
                    break;
                }
            }
        } else {
            llImage.setVisibility(View.GONE);
        }
        setFavor(value);
        setComments(value);

        if (group.getStatus() == 3 || (group.getStatus() == 4 && group.getTotalNum() < group.getMaxNum())) {
            tvJoinIn.setText("参与拼团");
            tvJoinIn.setTextColor(mActivity.getResources().getColor(R.color.white));
            tvJoinIn.setBackgroundColor(mActivity.getResources().getColor(R.color.pintuan_red));
            tvJoinIn.setOnClickListener(this);
            tvTimer.setTimes(getTimeBetween(new Date(), value.getDefaultEndTime()));
        } else {
            tvJoinIn.setText("已结束");
            tvJoinIn.setTextColor(mActivity.getResources().getColor(R.color.mine_divide_grey));
            tvJoinIn.setBackgroundColor(Color.parseColor("#c0c0c0"));
            tvJoinIn.setClickable(false);
            tvTimer.setTimes(0);
        }
    }

    private void setImages(String imgs) {
        String[] strings = imgs.split(";");
        ArrayList<String> list = new ArrayList<>();
        for (String str : strings) {
            list.add(str);
        }
        detailBanner.setUrls(list, false, false, true);
        if (list.size() == 0) {
            ArrayList<Integer> list1 = new ArrayList<>();
            list1.add(R.drawable.goods_detail);
            detailBanner.setResources(list1);
        }
    }

    private void setComments(GroupInfo value) {
        if (CheckUtils.isNoEmptyList(value.getGroupBuyUser().getCommontsAllList())) {
            llEvaluatePrevious.setVisibility(View.VISIBLE);
            tvEvaluateCount.setText("往期评价(" + value.getGroupBuyUser().getTotalCommontsCount().intValue() + ")");
        } else {
            llEvaluatePrevious.setVisibility(View.GONE);
            return;
        }

        for (final GroupUserComments comments : value.getGroupBuyUser().getCommontsAllList()) {
            View view = getLayoutInflater().inflate(R.layout.item_pintuan_evaluate, null);
            RoundImageView avatar = (RoundImageView) view.findViewById(R.id.evaluate_avatar);
            TextView username = (TextView) view.findViewById(R.id.evaluate_username);
            TextView date = (TextView) view.findViewById(R.id.evaluate_date);
            RatingBar score = (RatingBar) view.findViewById(R.id.evaluate_score);
            TextView scoreShow = (TextView) view.findViewById(R.id.evaluate_score_show);
            TextView content = (TextView) view.findViewById(R.id.evaluate_content);
            LinearLayout photoLayout = (LinearLayout) view.findViewById(R.id.evaluate_photo_layout);
            LinearLayout photoLayout2 = (LinearLayout) view.findViewById(R.id.evaluate_photo_layout_2);
            if (CheckUtils.isNoEmptyStr(comments.getUserInfo().getHeaderImg())) {
                ImageUtils.loadBitmap(mActivity, comments.getUserInfo().getHeaderImg(), avatar, R.drawable.default_group_user_avatar, Constants.getEndThumbnail(34, 34));
            }
            String name = comments.getUserInfo().getName();
//            if(name.length() > 1){
//                name = name.substring(0,1)+"***"+name.substring(name.length()-1,name.length());
//            }else {
//                name = "***"+name;
//            }
            username.setText(name);
            date.setText(comments.getCreateTime().split(" ")[0]);
            score.setRating(comments.getCompositeScore().floatValue());
            scoreShow.setText(comments.getCompositeScore() + "分");
            content.setText(comments.getGroupBuyScoreComments());
            if (CheckUtils.isNoEmptyStr(comments.getImgs())) {
                photoLayout.setVisibility(View.VISIBLE);
                String[] photos = comments.getImgs().split(";");
                if (photos.length > 3) {
                    photoLayout2.setVisibility(View.VISIBLE);
                    for (int i = 0; i < photos.length; i++) {
                        if (i >= 3) {
                            LinearLayout fm = (LinearLayout) photoLayout2.getChildAt(i - 3);
                            CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                            if (CheckUtils.isNoEmptyStr(photos[i])) {
                                ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                            }
                            fm.setTag(i);
                            fm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mActivity, PhotoActivity.class);
                                    intent.putExtra("position", (Integer) v.getTag());
                                    intent.putExtra("imgs", comments.getImgs());
                                    mActivity.startActivity(intent);
                                }
                            });
                        } else {
                            LinearLayout fm = (LinearLayout) photoLayout.getChildAt(i);
                            CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                            if (CheckUtils.isNoEmptyStr(photos[i])) {
                                ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                            }
                            fm.setTag(i);
                            fm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mActivity, PhotoActivity.class);
                                    intent.putExtra("position", (Integer) v.getTag());
                                    intent.putExtra("imgs", comments.getImgs());
                                    mActivity.startActivity(intent);
                                }
                            });
                        }
                    }
                } else {
                    photoLayout2.setVisibility(View.GONE);
                    int count = photoLayout.getChildCount() > photos.length ? photos.length : photoLayout.getChildCount();
                    for (int i = 0; i < count; i++) {
                        LinearLayout fm = (LinearLayout) photoLayout.getChildAt(i);
                        CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                        if (CheckUtils.isNoEmptyStr(photos[i])) {
                            ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                        }
                        fm.setTag(i);
                        fm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, PhotoActivity.class);
                                intent.putExtra("position", (Integer) v.getTag());
                                intent.putExtra("imgs", comments.getImgs());
                                startActivity(intent);
                            }
                        });
                    }
                }
            } else {
                photoLayout.setVisibility(View.GONE);
            }
            llEvaluate.addView(view);
        }
    }

    private void setFavor(GroupInfo value) {
        if (CheckUtils.isNoEmptyList(value.getOtherGroupBuyList()) && value.getOtherGroupBuyList().size() == 2) {
            for (int i = 0; i < value.getOtherGroupBuyList().size(); i++) {
                View view = llFavor.getChildAt(i);
                GroupInfo.OtherGroupBuyListBean group = value.getOtherGroupBuyList().get(i);
                CornerImageView image = (CornerImageView) view.findViewById(R.id.pintuan_favor_image);
                TextView title = (TextView) view.findViewById(R.id.pintuan_favor_title);
                TextView price = (TextView) view.findViewById(R.id.pintuan_favor_price);
                ProgressBar bar = (ProgressBar) view.findViewById(R.id.pintuan_favor_progress);
                if (CheckUtils.isNoEmptyStr(group.getImgs())) {
                    ImageUtils.loadBitmap(mActivity, group.getImgs().split(";")[0], image, R.drawable.group_zhanwei, "");
                }
                title.setText(group.getGoodsName());
                price.setText(group.getPrice() + "");
                int progress = group.getTotalNum() * 100 / group.getMinNum();
                if (progress >= 100) {
                    progress = 100;
                }
                bar.setProgress(progress);
                final String id = group.getId();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
            }
        } else {
            tvFavor.setVisibility(View.GONE);
            llFavor.setVisibility(View.GONE);
        }
    }

    private void addService(final String service) {
        if (service == null || CheckUtils.isEmptyStr(service)) {
            return;
        }
        VolleyOperater<ServiceTypeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_SERVICE_TYPE, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        isTrueData = false;
                        return;
                    }
                    ServiceTypeModel model = (ServiceTypeModel) obj;
                    setService(model, service);
                }
            }
        }, ServiceTypeModel.class);
    }

    private void setService(ServiceTypeModel model, String service) {
        List<ServiceTypeModel.ValueEntity> data = model.getValue();
        String[] strs = service.split(",");
        for (int i = 0; i < strs.length; i++) {
            if (i > 3)
                break;
            switch (i) {
                case 0:
                    for (int j = 0; j < data.size(); j++) {
                        if (strs[i].equals(data.get(j).getId())) {
                            TextView tv0 = (TextView) llService.getChildAt(0).findViewById(R.id.detail_service_0);
                            tv0.setText(data.get(j).getName());
                            tv0.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    break;
                case 1:
                    for (int j = 0; j < data.size(); j++) {
                        if (strs[i].equals(data.get(j).getId())) {
                            TextView tv1 = (TextView) llService.getChildAt(1).findViewById(R.id.detail_service_1);
                            tv1.setText(data.get(j).getName());
                            tv1.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    break;
                case 2:
                    for (int j = 0; j < data.size(); j++) {
                        if (strs[i].equals(data.get(j).getId())) {
                            TextView tv2 = (TextView) llService.getChildAt(2).findViewById(R.id.detail_service_2);
                            tv2.setText(data.get(j).getName());
                            tv2.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    break;
                case 3:
                    for (int j = 0; j < data.size(); j++) {
                        if (strs[i].equals(data.get(j).getId())) {
                            TextView tv3 = (TextView) llService.getChildAt(3).findViewById(R.id.detail_service_3);
                            tv3.setText(data.get(j).getName());
                            tv3.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!isTrueData) {
            ToastUtils.displayMsg("数据异常,请稍后重试", mActivity);
            finish();
            return;
        }
        switch (v.getId()) {
            case R.id.person_previous_pintuan://往期拼团
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "previousGroup" + "?userId=" + group.getGroupBuyUser().getId());
                break;
            case R.id.previous_evaluate_layout://往期评价
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "personEvaluate" + "?userId=" + group.getGroupBuyUser().getId());
                break;
            case R.id.join_in_pintuan:
                if (CommonUtils.checkLogin(mActivity)) {
                    getUserAddress(group);
                }
                break;
            case R.id.com_share:
                if (group != null && shareUtil == null) {
                    shareUtil = new ShareUtil(mActivity, group.getGoodsName(), group.getDescription(), group.getShareUrl(), group.getImgs());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        tvHistoryPintuan.setOnClickListener(this);
        llPreviousLayout.setOnClickListener(this);
        tvJoinIn.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        tvTimer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("剩余 00:00:00".equals(s.toString())) {
                    tvJoinIn.setText("已结束");
                    tvJoinIn.setTextColor(mActivity.getResources().getColor(R.color.mine_divide_grey));
                    tvJoinIn.setBackgroundColor(Color.parseColor("#c0c0c0"));
                    tvJoinIn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        tvTitle.setText("详情");
        ivShare.setVisibility(View.VISIBLE);
        mMLoadingDialog = new MLoadingDialog();
    }

    private void getUserAddress(final GroupInfo entity) {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<FindGroupAddressModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", entity.getAgentId());
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_USER_ADDRESS_PREVIEW, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    FindGroupAddressModel model = (FindGroupAddressModel) obj;
                    UserAddress address = model.getValue();
                    Intent intent = new Intent(mActivity, JoinGroupActivity.class);
                    intent.putExtra("group", entity);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            }
        }, FindGroupAddressModel.class);
    }

    private long getTimeBetween(Date curTime, String endTime) {
        try {
            long time1 = curTime.getTime();
            Date date2 = sdf.parse(endTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void doLogin() {
        VolleyOperater<AppLaunchModel> operater = new VolleyOperater<AppLaunchModel>(GroupPurchaseDetailActivity.this);
        operater.doRequest(Constants.URL_INIT_APP, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    AppLaunchModel appLaunchModel = (AppLaunchModel) obj;
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
                    App.setUserInfo(appUserEntity);
                    App.setIsLogin(true);
                }
                getDetail();
            }
        }, AppLaunchModel.class);
    }
}
