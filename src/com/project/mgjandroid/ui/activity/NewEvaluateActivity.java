package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.OrderEvaluateModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingDeliverymanImpress;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.groupbuying.GoodsListAdapter;
import com.project.mgjandroid.ui.adapter.EvaluateImageRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.GroupBuyingImageRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.RiderEvaluationAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewEvaluateActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView back;
    @InjectView(R.id.layout_bad)
    private LinearLayout layoutBad;
    @InjectView(R.id.layout_ordinary)
    private LinearLayout layoutOrdinary;
    @InjectView(R.id.layout_good)
    private LinearLayout layoutGood;
    @InjectView(R.id.img_bad)
    private ImageView imgBad;
    @InjectView(R.id.img_ordinary)
    private ImageView imgOrdinary;
    @InjectView(R.id.img_good)
    private ImageView imgGood;
    @InjectView(R.id.tv_bad)
    private TextView tvBad;
    @InjectView(R.id.tv_ordinary)
    private TextView tvOrdinary;
    @InjectView(R.id.tv_good)
    private TextView tvGood;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;
    @InjectView(R.id.big_score)
    private RatingBarView bigScore;
    @InjectView(R.id.pack_score)
    private RatingBarView packScore;
    @InjectView(R.id.taste_score)
    private RatingBarView tasteScore;
    @InjectView(R.id.layout_small_score)
    private LinearLayout layoutScore;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;
    @InjectView(R.id.rLayout_evaluate)
    private RelativeLayout rlayoutEvaluate;
    @InjectView(R.id.layout_picture_upload)
    private LinearLayout layoutPicture;
    @InjectView(R.id.layout_bottom)
    private LinearLayout layoutBottom;
    @InjectView(R.id.tv_score)
    private TextView tvScore;
    @InjectView(R.id.edu_content)
    private EditText etContent;
    @InjectView(R.id.tv_textview)
    private CheckBox cbCryptonym;
    @InjectView(R.id.tv_submit)
    private TextView tvSubmit;
    @InjectView(R.id.no_scroll_list_view)
    private NoScrollListView noScrollListView;
    @InjectView(R.id.layout_rider)
    private LinearLayout layoutRider;
    @InjectView(R.id.rider_avatar)
    private CornerImageView riderAvatar;
    @InjectView(R.id.merchant_avatar)
    private CornerImageView merchantAvatar;
    @InjectView(R.id.tv_rider_name)
    private TextView tvRiderName;
    @InjectView(R.id.tv_delivery_time)
    private TextView tvDeliveryTime;
    @InjectView(R.id.tv_merchant_name)
    private TextView tvMerchantName;
    @InjectView(R.id.tv_content_length)
    private TextView tvLength;

    private ArrayList<GroupBuyingDeliverymanImpress> badList = new ArrayList<>();
    private ArrayList<GroupBuyingDeliverymanImpress> goodList = new ArrayList<>();
    private RiderEvaluationAdapter riderEvaluationAdapter;
    private EvaluateImageRecyclerAdapter adapter;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles;
    private boolean packClick;
    private boolean tasteClick;
    private MLoadingDialog loadingDialog;
    private String orderId;
    private NewOrderFragmentModel.ValueEntity valueEntityEvaluate;
    private SubmitOrderModel.ValueEntity submitOrderEntity;
    private List<NewOrderFragmentModel.ValueEntity.OrderItemsEntity> orderItems;
    private boolean hasDriverEvaluate;
    private boolean isFromOrderList;
    private Integer eTime;
    private int diliveryCost = 10;
    private StringBuffer imageUrls = new StringBuffer();
    private String startText = "";
    private String endText = "";
    private TextView textView;
    private int agentId;
    private List<Map<String, Object>> list;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_new_evaluate);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        layoutBad.setOnClickListener(this);
        layoutOrdinary.setOnClickListener(this);
        layoutGood.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        back.setOnClickListener(this);

        String[] bad = getResources().getStringArray(R.array.badList);
        String[] good = getResources().getStringArray(R.array.goodList);

        for (String s : bad) {
            GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = new GroupBuyingDeliverymanImpress();
            groupBuyingDeliverymanImpress.setImpress(s);
            badList.add(groupBuyingDeliverymanImpress);
        }
        for (String s : good) {
            GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = new GroupBuyingDeliverymanImpress();
            groupBuyingDeliverymanImpress.setImpress(s);
            goodList.add(groupBuyingDeliverymanImpress);
        }


        loadingDialog = new MLoadingDialog();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId");
            agentId = intent.getIntExtra("agentId", -1);
            valueEntityEvaluate = (NewOrderFragmentModel.ValueEntity) intent.getSerializableExtra("valueEntity");
            submitOrderEntity = (SubmitOrderModel.ValueEntity) intent.getSerializableExtra("submitOrderEntity");
            if (valueEntityEvaluate != null) {
                isFromOrderList = true;
                orderItems = valueEntityEvaluate.getOrderItems();
            } else {
                isFromOrderList = false;
            }
            hasDriverEvaluate = intent.getBooleanExtra("hasDriver", false);
        }
        if (submitOrderEntity.getDeliveryTask().getDeliveryman()!=null) {
            layoutRider.setVisibility(View.VISIBLE);
            tvRiderName.setText(submitOrderEntity.getDeliveryTask().getDeliveryman().getName());
            ImageUtils.loadBitmap(mActivity, submitOrderEntity.getDeliveryTask().getDeliveryman().getHeaderImg().split(";")[0], riderAvatar, R.drawable.icon_default_avator, Constants.getEndThumbnail(56, 56));
            tvDeliveryTime.setText(DateUtils.getFormatTime3(submitOrderEntity.getOrderDoneTime()) + "送达");
        } else {
            layoutRider.setVisibility(View.GONE);
        }
        ImageUtils.loadBitmap(mActivity, submitOrderEntity.getMerchant().getLogo(), merchantAvatar, R.drawable.horsegj_default, Constants.getEndThumbnail(56, 56));
        tvMerchantName.setText(submitOrderEntity.getMerchant().getName());
        if (isFromOrderList)
            eTime = valueEntityEvaluate.getMerchant().getAvgDeliveryTime();
        else
            eTime = submitOrderEntity.getMerchant().getAvgDeliveryTime();
        eTime = eTime / 10 * 10;
        diliveryCost = eTime;
        riderEvaluationAdapter = new RiderEvaluationAdapter(this);
        gridView.setAdapter(riderEvaluationAdapter);
        riderEvaluationAdapter.setList(null);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView = (TextView) view.findViewById(R.id.textview);
                textView.setSelected(!textView.isSelected());
                GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = (GroupBuyingDeliverymanImpress) riderEvaluationAdapter.getItem(i);
                groupBuyingDeliverymanImpress.setChecked(!groupBuyingDeliverymanImpress.isChecked());
                riderEvaluationAdapter.notifyDataSetChanged();

                if (groupBuyingDeliverymanImpress.isChecked()) {
                    if (imgBad.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                    if (imgOrdinary.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                    if (imgGood.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                }
            }
        });
        ChoosePhotoModel.getInstance().setMaxCount(9);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        adapter = new EvaluateImageRecyclerAdapter(mActivity, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new EvaluateImageRecyclerAdapter.OnClickListener() {
            @Override
            public void onClickImage(int currentItem) {
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
            }

            @Override
            public void onClichAdd() {
                showPopupWindow();
            }
        });
        bigScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore, String content) {
                if (RatingScore > 0 && layoutScore.getVisibility() != View.VISIBLE) {
                    layoutScore.setVisibility(View.VISIBLE);
                }
                if (RatingScore == 1) {
                    tvScore.setText("极差");
                } else if (RatingScore == 2) {
                    tvScore.setText("失望");
                } else if (RatingScore == 3) {
                    tvScore.setText("一般");
                } else if (RatingScore == 4) {
                    tvScore.setText("满意");
                } else if (RatingScore == 5) {
                    tvScore.setText("超赞");
                } else {
                    tvScore.setText("");
                }
            }
        });
        packScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore, String content) {
                packClick = true;
                if (packClick && tasteClick) {
                    if (RatingScore > 0 && rlayoutEvaluate.getVisibility() != View.VISIBLE) {
                        rlayoutEvaluate.setVisibility(View.VISIBLE);
                    }
                    if (layoutPicture.getVisibility() != View.VISIBLE) {
                        layoutPicture.setVisibility(View.VISIBLE);
                    }
                    if (layoutBottom.getVisibility() != View.VISIBLE) {
                        layoutBottom.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tasteScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore, String content) {
                tasteClick = true;
                if (packClick && tasteClick) {
                    if (RatingScore > 0 && rlayoutEvaluate.getVisibility() != View.VISIBLE) {
                        rlayoutEvaluate.setVisibility(View.VISIBLE);
                    }
                    if (layoutPicture.getVisibility() != View.VISIBLE) {
                        layoutPicture.setVisibility(View.VISIBLE);
                    }
                    if (layoutBottom.getVisibility() != View.VISIBLE) {
                        layoutBottom.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvLength.setText(editable.toString().length() + "/300字");
            }
        });

        GoodsListAdapter goodsAdapter = new GoodsListAdapter(R.layout.evaluate_goods_list_item, mActivity);
        noScrollListView.setAdapter(goodsAdapter);
        noScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RadioButton rbTrample = (RadioButton) view.findViewById(R.id.rb_trample);
                RadioButton rbPraise = (RadioButton) view.findViewById(R.id.rb_praise);
                rbTrample.setChecked(!rbTrample.isChecked());
                rbPraise.setChecked(!rbPraise.isChecked());
            }
        });
        goodsAdapter.setData(orderItems);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        List<String> list = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(mSelectFiles)) {
            for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
                list.add(mSelectFiles.get(i).getPath());
            }
        }
        if (adapter != null) adapter.setList(list);
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }

    private boolean checkCanPublish() {
        imageUrls.setLength(0);
        if (CheckUtils.isEmptyList(mSelectFiles)) {
            return true;
        }

        for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
            if (TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
            if (i == size - 1) {
                imageUrls.append(mSelectFiles.get(i).getUrl());
            } else {
                imageUrls.append(mSelectFiles.get(i).getUrl() + ";");
            }
        }

        return true;
    }

    //能否发布
    private boolean checkCanEvaluate() {
        if (hasDriverEvaluate) {
            if (!imgBad.isSelected() && !imgOrdinary.isSelected() && !imgGood.isSelected()) {
                ToastUtils.displayMsg("评价不完整,请继续评价", NewEvaluateActivity.this);
                return false;
            }
            if (CheckUtils.isEmptyStr(endText)) {
                ToastUtils.displayMsg("评价不完整,请继续评价", NewEvaluateActivity.this);
                return false;
            }
        }
        if (bigScore.getRating() == 0) {
            ToastUtils.displayMsg("评价不完整,请继续评价", NewEvaluateActivity.this);
            return false;
        }
        if (packScore.getRating() == 0) {
            ToastUtils.displayMsg("评价不完整,请继续评价", NewEvaluateActivity.this);
            return false;
        }
        if (tasteScore.getRating() == 0) {
            ToastUtils.displayMsg("评价不完整,请继续评价", NewEvaluateActivity.this);
            return false;
        }
        return true;
    }

    /**
     * 生成评价
     */
    private void evaluateOrder() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentId", agentId);
        map.put("orderId", orderId);
        map.put("merchantScore", bigScore.getRating());
        map.put("merchantComments", etContent.getText().toString().trim());
        map.put("deliveryCost", diliveryCost);
        if (imgBad.isSelected()) {
            map.put("deliverymanScore", 1);
        }
        if (imgOrdinary.isSelected()) {
            map.put("deliverymanScore", 3);
        }
        if (imgGood.isSelected()) {
            map.put("deliverymanScore", 5);
        }
        map.put("packagingScore", packScore.getRating());
        map.put("tasteScore", tasteScore.getRating());
        map.put("goodsComments", getGoodsEvaluate());
        map.put("imgUrl", imageUrls.toString());
        map.put("isAnonymous", cbCryptonym.isChecked() == true ? 1 : 0);
        map.put("deliverymanImpress", endText);

        VolleyOperater<OrderEvaluateModel> operater = new VolleyOperater<OrderEvaluateModel>(NewEvaluateActivity.this);
        operater.doRequest(Constants.URL_EVALUATE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    setResult(RESULT_OK, new Intent());
                    CommonUtils.hideKeyBoard(NewEvaluateActivity.this);
                    finish();
                }
                loadingDialog.dismiss();
            }
        }, OrderEvaluateModel.class);
    }

    public String getGoodsEvaluate() {
        list = new ArrayList<>();
        if (isFromOrderList) {
            for (int i = 0; i < orderItems.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                RadioButton radioButton = (RadioButton) noScrollListView.getChildAt(i).findViewById(R.id.rb_praise);
                map.put("goodsId", orderItems.get(i).getGoodsId());
                map.put("goodsScore", radioButton.isChecked() ? 5 : 1);
                list.add(map);
            }
        } else {
            for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
                Map<String, Object> map = new HashMap<>();
                RadioButton radioButton = (RadioButton) noScrollListView.getChildAt(i).findViewById(R.id.rb_praise);
                map.put("goodsId", submitOrderEntity.getOrderItems().get(i).getGoodsId());
                map.put("goodsScore", radioButton.isChecked() ? 5 : 1);
                list.add(map);
            }
        }
        return JSON.toJSONString(list);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_bad:
                imgBad.setSelected(true);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(badList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.layout_ordinary:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(true);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(goodList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.layout_good:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(true);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                riderEvaluationAdapter.setList(goodList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.take_photo:
                if (!CheckUtils.hasCamera(mActivity)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }
                Intent camera = new Intent(mActivity, CameraActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                startActivity(camera);
                dismissPopupWindow();
                return;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                return;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                return;
            case R.id.common_back:
                back();
                return;
            case R.id.tv_submit:
                if (checkCanPublish() && checkCanEvaluate()) {
                    evaluateOrder();
                }
                break;
        }
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo, null);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

}
