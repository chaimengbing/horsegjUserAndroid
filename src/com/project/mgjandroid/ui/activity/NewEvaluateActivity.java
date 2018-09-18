package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.ui.adapter.EvaluateImageRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.GroupBuyingImageRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.RiderEvaluationAdapter;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

public class NewEvaluateActivity extends BaseActivity {

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

    private ArrayList<String> badList = new ArrayList<>();
    private ArrayList<String> goodList = new ArrayList<>();
    private RiderEvaluationAdapter riderEvaluationAdapter;
    private EvaluateImageRecyclerAdapter adapter;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles;
    private boolean packClick;
    private boolean tasteClick;


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
        badList.add("提前点送达");
        badList.add("服务态度差");
        badList.add("餐品翻撒");
        badList.add("送餐慢");
        badList.add("着装脏乱");
        badList.add("沟通困难");
        goodList.add("穿戴工服");
        goodList.add("风雨无阻");
        goodList.add("快速准时");
        goodList.add("仪容整洁");
        goodList.add("货品完好");
        goodList.add("礼貌热情");
        riderEvaluationAdapter = new RiderEvaluationAdapter(this);
        gridView.setAdapter(riderEvaluationAdapter);
        riderEvaluationAdapter.setList(null);
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
                if(RatingScore==1){
                    tvScore.setText("极差");
                }else if(RatingScore==2){
                    tvScore.setText("失望");
                }else if(RatingScore==3){
                    tvScore.setText("一般");
                }else if(RatingScore==4){
                    tvScore.setText("满意");
                }else if(RatingScore==5){
                    tvScore.setText("超赞");
                }else {
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
                break;
            case R.id.layout_ordinary:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(true);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(goodList);
                break;
            case R.id.layout_good:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(true);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                riderEvaluationAdapter.setList(goodList);
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
