package com.project.mgjandroid.ui.activity.carhailing;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.carhailing.CarHailing;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.carhailing.CarHailingTrip;
import com.project.mgjandroid.bean.carhailing.DistrictListBean;
import com.project.mgjandroid.bean.carhailing.WeekDay;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.FindGroupModel;
import com.project.mgjandroid.model.carhailing.CarHailingModel;
import com.project.mgjandroid.model.carhailing.TripListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import org.apache.http.impl.cookie.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/12/7.
 */
@Router("callCar")
public class CarHailingActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.car_hailing_filter_layout)
    private LinearLayout llFilterLayout;
    @InjectView(R.id.car_hailing_date_filter_layout)
    private LinearLayout llDateFilterLayout;
    @InjectView(R.id.car_hailing_type_filter_layout)
    private LinearLayout llTypeFilterLayout;
    @InjectView(R.id.car_hailing_car_filter_layout)
    private LinearLayout llCarFilterLayout;
    @InjectView(R.id.car_hailing_date_filter)
    private TextView tvDateFilter;
    @InjectView(R.id.car_hailing_type_filter)
    private TextView tvTypeFilter;
    @InjectView(R.id.car_hailing_car_filter)
    private TextView tvCarFilter;
    @InjectView(R.id.icon_car_hailing_date_filter)
    private ImageView ivDateIcon;
    @InjectView(R.id.icon_car_hailing_type_filter)
    private ImageView ivTypeIcon;
    @InjectView(R.id.icon_car_hailing_car_filter)
    private ImageView ivCarIcon;
    @InjectView(R.id.detail_filter_layout)
    private LinearLayout llCarDetailLayout;
    @InjectView(R.id.car_filter_get_on)
    private TextView tvGetOn;
    @InjectView(R.id.car_filter_get_off)
    private TextView tvGetOff;
    @InjectView(R.id.car_filter_reset)
    private TextView tvReset;
    @InjectView(R.id.car_filter_inquiry)
    private TextView tvInquiry;
    @InjectView(R.id.type_filter_layout)
    private LinearLayout llTypeDetailLayout;
    @InjectView(R.id.type_filter_all_type)
    private TextView tvAllType;
    @InjectView(R.id.type_filter_pinche)
    private TextView tvPinche;
    @InjectView(R.id.type_filter_baoche)
    private TextView tvBaoche;
    @InjectView(R.id.date_filter_layout)
    private LinearLayout llDateDetailLayout;
    @InjectView(R.id.date_filter_date_1)
    private TextView tvDate1;
    @InjectView(R.id.date_filter_date_2)
    private TextView tvDate2;
    @InjectView(R.id.date_filter_date_3)
    private TextView tvDate3;
    @InjectView(R.id.date_filter_date_4)
    private TextView tvDate4;
    @InjectView(R.id.date_filter_date_5)
    private TextView tvDate5;
    @InjectView(R.id.date_filter_date_6)
    private TextView tvDate6;
    @InjectView(R.id.date_filter_date_7)
    private TextView tvDate7;
    @InjectView(R.id.filter_divider_line)
    private View vLine;
    @InjectView(R.id.car_hailing_shadow_view)
    private View vShadow;
    @InjectView(R.id.car_hailing_list)
    private PullToRefreshListView ptrListView;

    private ArrayList<TextView> dateList = new ArrayList<>();
    private ArrayList<String> dateStrList;
    private ArrayList<Date> dates;
    private CarHailingListAdapter adapter;
    private int start = 0;
    private int maxResults = 10;
    private Date serviceTime;

    private int chauffeurTripType = 0;//当前筛选行程类型
    private int currentFilter = 0;//当前选中的筛选模块
    private int currentDate = 0;//当前选中日期
    private long startProvince, startCity, startDistrict = -1;
    private long endProvince, endCity, endDistrict = -1;
    private String startTime, endTime;
    private long startcarTripId;
    private TripListModel tripListStartModel, tripListEndModel;

    private static final int START = 0;
    private static final int END = 1;
    private static final int REFRESH = 2;
    private MLoadingDialog mLoadingDialog;

    private boolean startCanClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hailing);
        Injector.get(this).inject();
        initView();
        initData();
        setLinstener();
        serviceTime = new Date();
        setDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(false);
    }

    private void initData() {
        ChooseCityModel.getInstance().initData(mActivity);
        mLoadingDialog.show(getFragmentManager(), "");
        dateList.add(tvDate1);
        dateList.add(tvDate2);
        dateList.add(tvDate3);
        dateList.add(tvDate4);
        dateList.add(tvDate5);
        dateList.add(tvDate6);
        dateList.add(tvDate7);
        getTripList(1);
    }

    private void getTripList(final int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        if (type != 1) map.put("startcarTripId", startcarTripId);
        VolleyOperater<TripListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_TRIP_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (mLoadingDialog != null && !mLoadingDialog.isHidden())
                    mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    if (type == 2) {
                        tripListEndModel = (TripListModel) obj;
                        Intent intent = new Intent(mActivity, ChooseAreaActivity.class);
                        intent.putExtra("tripList", tripListEndModel);
                        intent.putExtra("type", type);
                        startCanClick = false;
                        startActivityForResult(intent, END);
                    } else {
                        tripListStartModel = (TripListModel) obj;
                    }
                }
            }
        }, TripListModel.class);
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        llDateFilterLayout.setOnClickListener(this);
        llTypeFilterLayout.setOnClickListener(this);
        llCarFilterLayout.setOnClickListener(this);
        tvDate1.setOnClickListener(this);
        tvDate2.setOnClickListener(this);
        tvDate3.setOnClickListener(this);
        tvDate4.setOnClickListener(this);
        tvDate5.setOnClickListener(this);
        tvDate6.setOnClickListener(this);
        tvDate7.setOnClickListener(this);
        tvAllType.setOnClickListener(this);
        tvPinche.setOnClickListener(this);
        tvBaoche.setOnClickListener(this);
        vShadow.setOnClickListener(this);
        ptrListView.setOnItemClickListener(this);
        tvGetOn.setOnClickListener(this);
        tvGetOff.setOnClickListener(this);
        tvInquiry.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        adapter.setListener(this);
    }

    private void initView() {
        adapter = new CarHailingListAdapter(R.layout.item_car_hailing_list, mActivity);
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(this);
        mLoadingDialog = new MLoadingDialog();
        ptrListView.setAdapter(adapter);
        View emptyView = mInflater.inflate(R.layout.car_hailing_empty_view, null);
        ptrListView.getRefreshableView().setEmptyView(emptyView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
            case R.id.car_hailing_date_filter_layout:
                if (currentFilter != 1) {
                    resetFilter(currentFilter);
                    currentFilter = 1;
                    llDateDetailLayout.setVisibility(View.VISIBLE);
                    vLine.setVisibility(View.VISIBLE);
                    vShadow.setVisibility(View.VISIBLE);
                    tvDateFilter.setTextColor(mResource.getColor(R.color.green_job));
                    ivDateIcon.setImageResource(R.drawable.icon_car_hailing_filter_down);
                } else {
                    resetFilter(currentFilter);
                }
                break;
            case R.id.car_hailing_type_filter_layout:
                if (currentFilter != 2) {
                    resetFilter(currentFilter);
                    currentFilter = 2;
                    llTypeDetailLayout.setVisibility(View.VISIBLE);
                    vLine.setVisibility(View.VISIBLE);
                    vShadow.setVisibility(View.VISIBLE);
                    tvTypeFilter.setTextColor(mResource.getColor(R.color.green_job));
                    ivTypeIcon.setImageResource(R.drawable.icon_car_hailing_filter_down);
                } else {
                    resetFilter(currentFilter);
                }
                break;
            case R.id.car_hailing_car_filter_layout:
                if (currentFilter != 3) {
                    resetFilter(currentFilter);
                    currentFilter = 3;
                    llCarDetailLayout.setVisibility(View.VISIBLE);
                    vLine.setVisibility(View.VISIBLE);
                    vShadow.setVisibility(View.VISIBLE);
                    tvCarFilter.setTextColor(mResource.getColor(R.color.green_job));
                    ivCarIcon.setImageResource(R.drawable.icon_car_hailing_filter_down);
                } else {
                    resetFilter(currentFilter);
                }
                break;
            case R.id.type_filter_all_type:
                if (chauffeurTripType == 0) {
                    break;
                }
                resetFilter(currentFilter);
                chauffeurTripType = 0;
                tvTypeFilter.setText("全部类型");
                tvAllType.setTextColor(mResource.getColor(R.color.green_job));
                getData(false);
                break;
            case R.id.type_filter_pinche:
                if (chauffeurTripType == 1) {
                    break;
                }
                resetFilter(currentFilter);
                chauffeurTripType = 1;
                tvTypeFilter.setText("拼车");
                tvPinche.setTextColor(mResource.getColor(R.color.green_job));
                getData(false);
                break;
            case R.id.type_filter_baoche:
                if (chauffeurTripType == 2) {
                    break;
                }
                resetFilter(currentFilter);
                chauffeurTripType = 2;
                tvTypeFilter.setText("包车");
                tvBaoche.setTextColor(mResource.getColor(R.color.green_job));
                getData(false);
                break;
            case R.id.date_filter_date_1:
                resetFilter(currentFilter);
                currentDate = 1;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_2:
                resetFilter(currentFilter);
                currentDate = 2;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_3:
                resetFilter(currentFilter);
                currentDate = 3;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_4:
                resetFilter(currentFilter);
                currentDate = 4;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_5:
                resetFilter(currentFilter);
                currentDate = 5;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_6:
                resetFilter(currentFilter);
                currentDate = 6;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.date_filter_date_7:
                resetFilter(currentFilter);
                currentDate = 7;
                tvDateFilter.setText(dateStrList.get(currentDate - 1));
                startTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 00:00:00";
                getData(false);
                break;
            case R.id.car_hailing_shadow_view:
                if (currentFilter != 0) {
                    resetFilter(currentFilter);
                    currentFilter = 0;
                }
                break;
            case R.id.car_filter_get_on:
                if (tripListStartModel == null) {
                    break;
                }
                if (!startCanClick)
                    break;
                Intent intent = new Intent(mActivity, ChooseAreaActivity.class);
                intent.putExtra("tripList", tripListStartModel);
                startCanClick = false;
                startActivityForResult(intent, START);
                break;
            case R.id.car_filter_get_off:
                if (startDistrict == -1) {
                    ToastUtils.displayMsg("请选择上车地点", mActivity);
                    break;
                }
                if (!startCanClick)
                    break;
                mLoadingDialog.show(getFragmentManager(), "");
                getTripList(2);
                break;
            case R.id.car_filter_inquiry:
                if (startDistrict == -1) {
                    toast("请选择上车地点");
                    break;
                }
                resetFilter(currentFilter);
                getData(false);
                break;
            case R.id.car_filter_reset:
                startProvince = 0;
                startCity = 0;
                startDistrict = -1;
                endProvince = 0;
                endCity = 0;
                endDistrict = -1;
                tvGetOn.setText("请选择上车地点");
                tvGetOff.setText("");
                getData(false);
                break;
            case R.id.car_hailing_list_header_father:
                int imgPos = (int) v.getTag();
                CarHailingDriver driver = adapter.getData().get(imgPos).getChauffeur();
                Intent driverIntent = new Intent(mActivity, CarHailingDriverActivity.class);
                driverIntent.putExtra("driver", driver);
                startActivity(driverIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == START) {
                if (data != null) {
                    DistrictListBean district = (DistrictListBean) data.getSerializableExtra("selectDistrict");
                    startProvince = district.getProvince();
                    startCity = district.getCity();
                    startDistrict = district.getDistrict();
                    startcarTripId = district.getId();
                    tvGetOn.setText(district.getDistrictName());
                }
            } else if (requestCode == END) {
                if (data != null) {
                    DistrictListBean district = (DistrictListBean) data.getSerializableExtra("selectDistrict");
                    endProvince = district.getProvince();
                    endCity = district.getCity();
                    endDistrict = district.getDistrict();
                    tvGetOff.setText(district.getDistrictName());
                }
            } else if (requestCode == REFRESH) {
                getData(false);
            }
        }
        if (requestCode == START || requestCode == END) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        startCanClick = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    private void getData(final boolean isLoadMore) {//URL_FIND_CAR_HAILING_TRIP_LIST
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        long cityCode = Long.parseLong(PreferenceUtils.getAddressCityCode(mActivity));
        map.put("cityCode", cityCode);
        if (startProvince != 0) map.put("startProvince", startProvince);
        if (startCity != 0) map.put("startCity", startCity);
        if (startDistrict != -1) map.put("startDistrict", startDistrict);
        if (endProvince != 0) map.put("endProvince", endProvince);
        if (endCity != 0) map.put("endCity", endCity);
        if (endDistrict != -1) map.put("endDistrict", endDistrict);
        if (CheckUtils.isNoEmptyStr(startTime)) {
            map.put("startTime", startTime);
            endTime = CommonUtils.formatTime(dates.get(currentDate - 1).getTime(), CommonUtils.yyyy_MM_dd) + " 23:59:59";
        }
        if (CheckUtils.isNoEmptyStr(endTime)) map.put("endTime", endTime);
        if (chauffeurTripType != 0)
            map.put("chauffeurTripType", chauffeurTripType);//1 拼车 2 包车
        VolleyOperater<CarHailingModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_CAR_HAILING_TRIP_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                ptrListView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    CarHailingModel model = (CarHailingModel) obj;
                    serviceTime = model.getServertime();
                    for (CarHailingTrip carHailing : model.getValue()) {
                        carHailing.setServiceTime(serviceTime);
                    }
                    setDate();
                    List<CarHailingTrip> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<CarHailingTrip> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
//                            AnimatorUtils.fadeFadeIn(ptrListView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, CarHailingModel.class);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(serviceTime);
        dateStrList = new ArrayList<>();
        dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (i == 0) {
                dateList.get(i).setText("今天");
                dateStrList.add("今天");
            } else {
                dateList.get(i).setText(month + "月" + day + "日" + "(" + WeekDay.getOrderTypeByValue(dayOfWeek).getName() + ")");
                dateStrList.add(month + "月" + day + "日" + "(" + WeekDay.getOrderTypeByValue(dayOfWeek).getName() + ")");
            }
            if (currentDate - 1 == i) {
                dateList.get(i).setTextColor(mResource.getColor(R.color.green_job));
            } else {
                dateList.get(i).setTextColor(mResource.getColor(R.color.color_3));
            }
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (chauffeurTripType == 0) {
            tvAllType.setTextColor(mResource.getColor(R.color.green_job));
            tvPinche.setTextColor(mResource.getColor(R.color.color_3));
            tvBaoche.setTextColor(mResource.getColor(R.color.color_3));
        } else if (chauffeurTripType == 1) {
            tvAllType.setTextColor(mResource.getColor(R.color.color_3));
            tvPinche.setTextColor(mResource.getColor(R.color.green_job));
            tvBaoche.setTextColor(mResource.getColor(R.color.color_3));
        } else if (chauffeurTripType == 2) {
            tvAllType.setTextColor(mResource.getColor(R.color.color_3));
            tvPinche.setTextColor(mResource.getColor(R.color.color_3));
            tvBaoche.setTextColor(mResource.getColor(R.color.green_job));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CarHailingTrip carHailingTrip = adapter.getData().get(position - 1);
        Intent intent = new Intent(mActivity, CarHailingDetailActivity.class);
        intent.putExtra("carHailingTrip", carHailingTrip);
        startActivityForResult(intent, REFRESH);
    }

    private void resetFilter(int current) {
        if (current == 0)
            return;
        currentFilter = 0;
        vLine.setVisibility(View.GONE);
        vShadow.setVisibility(View.GONE);
        switch (current) {
            case 1:
                llDateDetailLayout.setVisibility(View.GONE);
                tvDateFilter.setTextColor(mResource.getColor(R.color.color_3));
                ivDateIcon.setImageResource(R.drawable.icon_car_hailing_filter_up);
                break;
            case 2:
                llTypeDetailLayout.setVisibility(View.GONE);
                tvTypeFilter.setTextColor(mResource.getColor(R.color.color_3));
                ivTypeIcon.setImageResource(R.drawable.icon_car_hailing_filter_up);
//                if(chauffeurTripType == 0) {
//                    tvAllType.setTextColor(mResource.getColor(R.color.color_3));
//                }else if(chauffeurTripType == 1){
//                    tvPinche.setTextColor(mResource.getColor(R.color.color_3));
//                }else if(chauffeurTripType == 2){
//                    tvBaoche.setTextColor(mResource.getColor(R.color.color_3));
//                }
                break;
            case 3:
                llCarDetailLayout.setVisibility(View.GONE);
                tvCarFilter.setTextColor(mResource.getColor(R.color.color_3));
                ivCarIcon.setImageResource(R.drawable.icon_car_hailing_filter_up);
                break;
        }
    }
}
