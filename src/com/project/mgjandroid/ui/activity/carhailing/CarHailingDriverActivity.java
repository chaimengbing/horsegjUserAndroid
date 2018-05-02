package com.project.mgjandroid.ui.activity.carhailing;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.carhailing.DriverComment;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GroupOrderDetailModel;
import com.project.mgjandroid.model.carhailing.DriverCommentModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/12/7.
 */
public class CarHailingDriverActivity extends BaseActivity {

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    private RoundImageView ivHeader;
    private TextView tvName;
    private ImageView ivSex;
    private RatingBar rbScore;
    private TextView tvScore;
    private TextView tvCarType;
    private LinearLayout llConfirm;
    private RelativeLayout rlCarMater;
    private RelativeLayout rlTrueName;
    private TextView tvTravelCount;
    @InjectView(R.id.driver_evaluate_list)
    private ListView lvList;

    private long chauffeurId;
    private DriverCommentListAdapter adapter;
    private boolean isBottom = false;
    private boolean isLoading = false;
    private boolean hasMoreData = true;
    private int start = 0;

    DecimalFormat format = new DecimalFormat("0.0");
    private ImageView ivCarLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hailing_driver_detail);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void setLinstener() {//URL_CHAUFFEUR_ORDER_COMMENT
        ivBack.setOnClickListener(this);
        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        if (!isLoading) {
                            if (hasMoreData) {
                                start = adapter.getData().size();
                                getDriverMsg(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
    }

    private void initView() {
        CarHailingDriver driver = (CarHailingDriver) getIntent().getSerializableExtra("driver");
        if (driver == null) {
            finish();
            return;
        }
        chauffeurId = driver.getId();
        adapter = new DriverCommentListAdapter(R.layout.item_driver_comment, mActivity);
        View header = mInflater.inflate(R.layout.header_car_hailing_driver, null);
        ivHeader = (RoundImageView) header.findViewById(R.id.car_hailing_detail_order_driver_header);
        tvName = (TextView) header.findViewById(R.id.car_hailing_detail_order_driver_name);
        ivSex = (ImageView) header.findViewById(R.id.car_hailing_detail_order_driver_sex);
        rbScore = (RatingBar) header.findViewById(R.id.car_hailing_detail_order_driver_score);
        tvScore = (TextView) header.findViewById(R.id.car_hailing_detail_order_driver_tv_score);
        tvCarType = (TextView) header.findViewById(R.id.car_hailing_driver_car_type);
        llConfirm = (LinearLayout) header.findViewById(R.id.driver_confirm_layout);
        rlCarMater = (RelativeLayout) header.findViewById(R.id.driver_car_master_confirm);
        rlTrueName = (RelativeLayout) header.findViewById(R.id.driver_true_name_confirm);
        tvTravelCount = (TextView) header.findViewById(R.id.driver_travel_count);
        ivCarLogo = (ImageView) header.findViewById(R.id.car_hailing_driver_car_logo);
        lvList.addHeaderView(header);
        lvList.setAdapter(adapter);
        setViewData(driver);
        getDriverMsg(false);
    }

    private void setViewData(CarHailingDriver driver) {
        if (CheckUtils.isNoEmptyStr(driver.getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, driver.getHeaderImg(), ivHeader, R.drawable.horsegj_default, Constants.getEndThumbnail(55, 55));
        } else {
            ivHeader.setImageResource(R.drawable.horsegj_default);
        }
        tvName.setText(driver.getName());
        if (driver.getSex() == 0) {
            ivSex.setImageResource(R.drawable.icon_driver_female);
        } else if (driver.getSex() == 1) {
            ivSex.setImageResource(R.drawable.icon_driver_male);
        } else {
            ivSex.setVisibility(View.GONE);
        }
        rbScore.setRating(driver.getAverageScore().floatValue());
        tvScore.setText(format.format(driver.getAverageScore()));
        if (CheckUtils.isNoEmptyStr(driver.getCarImgUrl())) {
            ImageUtils.loadBitmap(mActivity, driver.getCarImgUrl(), ivCarLogo, R.drawable.horsegj_default, Constants.getEndThumbnail(15, 15));
        } else {
            ivCarLogo.setVisibility(View.INVISIBLE);
        }
        tvCarType.setText(driver.getCarColor() + "·" + driver.getCarSeries());
        if (driver.getHasCarOwnerAuthenticate() == 0 && driver.getHasCarRealnameAuthenticate() == 0) {
            llConfirm.setVisibility(View.GONE);
        } else if (driver.getHasCarOwnerAuthenticate() == 0) {
            rlCarMater.setVisibility(View.GONE);
        } else if (driver.getHasCarRealnameAuthenticate() == 0) {
            rlTrueName.setVisibility(View.GONE);
        }
        tvTravelCount.setText("出行（" + driver.getTripCount() + "次）");
    }

    private void getDriverMsg(final boolean isloadMore) {
        isLoading = true;
        Map<String, Object> map = new HashMap<>();
        map.put("chauffeurId", chauffeurId);
        map.put("start", start);
        map.put("size", 10);
        VolleyOperater<DriverCommentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CHAUFFEUR_COMMENT_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                isLoading = false;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    DriverCommentModel model = (DriverCommentModel) obj;
                    List<DriverComment> list = model.getValue().getChauffeurOrderCommontsList();
                    hasMoreData = list.size() >= 10;
                    if (isloadMore) {
                        List<DriverComment> data = adapter.getData();
                        data.addAll(list);
                        adapter.setData(data);
                    } else {
                        adapter.setData(list);
                    }
                }
            }
        }, DriverCommentModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
        }
    }
}
