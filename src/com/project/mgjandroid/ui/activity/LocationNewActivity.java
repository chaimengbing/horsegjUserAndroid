package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.AreaBean;
import com.project.mgjandroid.bean.CityBean;
import com.project.mgjandroid.bean.ProvinceBean;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.model.AddressManageModel;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.model.RegionModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.AddressManagerListAdapter;
import com.project.mgjandroid.ui.adapter.LocalListAdapter;
import com.project.mgjandroid.ui.fragment.ChooseAddressFragment;
import com.project.mgjandroid.ui.fragment.HomeFragment;
import com.project.mgjandroid.ui.fragment.NewHomeFragment;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新定位页面
 */
public class LocationNewActivity extends BaseActivity implements OnClickListener, AreaClick {
    @InjectView(R.id.location_act_edt_search)
    private EditText edtSearch;
    @InjectView(R.id.location_act_iv_back)
    private ImageView btnBack;
    @InjectView(R.id.location_act_tv_current)
    private TextView tvCurrentLoc;
    @InjectView(R.id.location_act_progress_current)
    private ProgressBar progressCurrent;
    @InjectView(R.id.location_act_layout_location)
    private RelativeLayout layoutCurrent;
    @InjectView(R.id.location_act_tv_my_address)
    private TextView tvAddress;
    @InjectView(R.id.location_act_listView)
    private ListView addressListView;
    @InjectView(R.id.location_act_layout_list)
    private RelativeLayout layoutList;
    @InjectView(R.id.location_act_layout_current)
    private RelativeLayout locationCurrent;
    @InjectView(R.id.location_act_progress_list)
    private ProgressBar progressList;
    @InjectView(R.id.location_act_list)
    private ListView listview;
    @InjectView(R.id.location_act_tv_search_fail)
    private TextView tvFail;
    @InjectView(R.id.root_view)
    private View rootView;
    @InjectView(R.id.choose_city_address)
    private FrameLayout chooseAddressLayout;
    @InjectView(R.id.choose_address)
    private TextView showAddress;
    @InjectView(R.id.choose_address_arrow)
    private ImageView showAddressArrow;
    @InjectView(R.id.choose_address_layout)
    private LinearLayout showAddressLayout;
    @InjectView(R.id.location_act_address_search)
    private RelativeLayout searchAddressLayout;
    @InjectView(R.id.current_location_refresh)
    private ImageView locationRefresh;

    private BDLocationListener listener;
    private SuggestionSearch mSugSearch;
    private LocalListAdapter adapter;
    private AddressManagerListAdapter addressManagerListAdapter;

    public static List<ProvinceBean> mProvinces;
    public static List<CityBean> mCitys = new ArrayList<>();
    public static List<AreaBean> mAreas = new ArrayList<>();
    private ProvinceBean mSelectedProvince;
    private CityBean mSelectedCity;
    private AreaBean mSelectedArea;
    private String mFrom = "";
    private String mCitys1;
    //标记  1代表是直辖市，特殊处理
    private int mType = -1;
    private ChooseAddressFragment chooseAddress;
    private boolean isShowAddress = false;
    private BDLocationListener curListener;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.location_act_new);
        Injector.get(this).inject();
        adapter = new LocalListAdapter(LocationNewActivity.this);
        addressManagerListAdapter = new AddressManagerListAdapter(this);
        addressManagerListAdapter.setCanModify(false);
        getCurrentLocation();
        setListener();
        initView();
        getAddressList();
    }

    private void getCurrentLocation() {
        curListener = new BDLocationListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location != null) {
                    if (location.getAddress() != null && location.getAddress().city != null) {
                        tvCurrentLoc.setText(location.getAddrStr());
                    }
                }
                LocationManager.getIManager().stopLocation();
            }
        };
        LocationManager.getIManager().registeLocation(getApplicationContext(), curListener);
    }

    private void setListener() {
        listener = new BDLocationListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location != null) {
                    if ("4.9E-324".equals("" + location.getLatitude()) || "4.9E-324".equals("" + location.getLongitude())) {
                        PreferenceUtils.saveLocation( "0.0", "0.0", LocationNewActivity.this);
                        PreferenceUtils.saveAddressName("", LocationNewActivity.this);
                        locationRefresh.clearAnimation();
                        tvCurrentLoc.setText(R.string.loc_current);
                        toast("请开启定位权限");
                        layoutCurrent.setEnabled(true);
                        return;
                    }
                    PreferenceUtils.saveLocation(location.getLatitude() + "", location.getLongitude() + "", LocationNewActivity.this);
                    PreferenceUtils.saveAddressName(location.getAddrStr(), LocationNewActivity.this);
                    if (CheckUtils.isNoEmptyList(location.getPoiList())) {
                        List<Poi> list = location.getPoiList();
                        PreferenceUtils.saveAddressDes(list.get(0).getName(), LocationNewActivity.this);
                    }
                    if (location.getAddress() != null && location.getAddress().city != null) {
                        PreferenceUtils.saveAddressCity(location.getAddress().city, mActivity);
                    }
                    if (location.getAddress() != null && location.getAddress().cityCode != null) {
                        PreferenceUtils.saveAddressCityCode(location.getAddress().cityCode, mActivity);
                    }
                    setResult(HomeActivity.LOCATION_RESPOND_CODE);
                }else {
                    PreferenceUtils.saveLocation( "0.0", "0.0", LocationNewActivity.this);
                    PreferenceUtils.saveAddressName("", LocationNewActivity.this);
                    locationRefresh.clearAnimation();
                    tvCurrentLoc.setText(R.string.loc_current);
                    toast("请开启定位权限");
                    layoutCurrent.setEnabled(true);
                    return;
                }
                showAddress();
                LocationManager.getIManager().stopLocation();
                if (location != null) {
                    back();
                }
            }
        };
        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    layoutList.setVisibility(View.VISIBLE);
                    progressList.setVisibility(View.VISIBLE);
                    if (mSelectedCity != null) {
                        MLog.d("city = " + mSelectedCity.getName());
                        poiSearch(edtSearch.getText() + "", mSelectedCity.getName());
                    } else {
                        poiSearch(edtSearch.getText() + "", "");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
//                    tvSearch.setVisibility(View.VISIBLE);
                    layoutList.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.VISIBLE);
                } else {
//                    tvSearch.setVisibility(View.GONE);
                    layoutList.setVisibility(View.GONE);
                    listview.setVisibility(View.GONE);
                }
            }

        });
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<SuggestionResult.SuggestionInfo> list = adapter.getList();
                if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
                    SuggestionResult.SuggestionInfo info = list.get(position);
                    if (info != null) {
                        LatLng pt = info.pt;
                        if (pt != null) {
                            PreferenceUtils.saveAddressName(info.key, LocationNewActivity.this);
                            PreferenceUtils.saveAddressDes("", LocationNewActivity.this);
                            PreferenceUtils.saveLocation(Double.toString(pt.latitude), Double.toString(info.pt.longitude), LocationNewActivity.this);
                            codeSearch(info.pt);
                        }
                    }
                }
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtils.hideKeyBoard2(edtSearch);
                return false;
            }
        });

        addressListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtils.hideKeyBoard2(edtSearch);
                return false;
            }
        });

//        tvSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        layoutCurrent.setOnClickListener(this);
        addressListView.setAdapter(addressManagerListAdapter);

        addressListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<UserAddress> userAddressList = addressManagerListAdapter.getList();
                if (CheckUtils.isNoEmptyList(userAddressList) && userAddressList.size() > position) {
                    UserAddress info = userAddressList.get(position);
                    if (info != null) {
                        PreferenceUtils.saveAddressName(info.getAddress(), LocationNewActivity.this);
                        if (!TextUtils.isEmpty(info.getHouseNumber())) {
                            PreferenceUtils.saveAddressDes(info.getHouseNumber(), LocationNewActivity.this);
                        } else {
                            PreferenceUtils.saveAddressDes("", LocationNewActivity.this);
                        }
                        PreferenceUtils.saveLocation(Double.toString(info.getLatitude()), Double.toString(info.getLongitude()), LocationNewActivity.this);
                        codeSearch(new LatLng(info.getLatitude(), info.getLongitude()));
                    }
                }
            }
        });
        showAddressLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_act_layout_location:
                CommonUtils.hideKeyBoard2(edtSearch);
                tvCurrentLoc.setText(R.string.search);
                layoutCurrent.setEnabled(false);
                progressCurrent.setVisibility(View.GONE);
                RotateAnimation rotateAnimation2 = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation2.setDuration(500);
                rotateAnimation2.setRepeatMode(Animation.RESTART);
                rotateAnimation2.setRepeatCount(1000);
                locationRefresh.startAnimation(rotateAnimation2);
                LocationManager.getIManager().registeLocation(getApplicationContext(), listener);
                break;
            case R.id.location_act_tv_search:
                edtSearch.setText("");
                break;
            case R.id.location_act_iv_back:
                if (layoutList.getVisibility() == View.VISIBLE) {
                    layoutList.setVisibility(View.GONE);
                } else {
                    back();
                }
                break;
            case R.id.choose_address_layout:
                layoutList.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                if (isShowAddress) {
                    isShowAddress = false;
                    chooseAddressLayout.setVisibility(View.GONE);
                    showAddressArrow.setImageResource(R.drawable.location_down_arrow);
                } else {
                    isShowAddress = true;
                    chooseAddressLayout.setVisibility(View.VISIBLE);
                    showAddressArrow.setImageResource(R.drawable.location_up_arrow);
                    CommonUtils.hideKeyBoard2(v);
                }
                break;
            default:
                break;
        }
    }

    private void poiSearch(String keyword, String city) {
        if (mSugSearch == null) {
            mSugSearch = SuggestionSearch.newInstance();
            mSugSearch.setOnGetSuggestionResultListener(suggestionListener);
        }
        String latitude = PreferenceUtils.getLocation(LocationNewActivity.this)[0];
        String longitude = PreferenceUtils.getLocation(LocationNewActivity.this)[1];
        LatLng lng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        SuggestionSearchOption option = new SuggestionSearchOption();
        if (CheckUtils.isEmptyStr(city)) {
            mSugSearch.requestSuggestion(option.keyword(keyword).city("").location(lng));
        } else {
            mSugSearch.requestSuggestion(option.keyword(keyword).city(city));
        }
    }

    private void codeSearch(LatLng ll) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=BE94604732413aefd52917b186d69f72&location=" + ll.latitude + "," + ll.longitude + "&output=json&pois=1";
        VolleyOperater<BaiduGeocoderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(url, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    BaiduGeocoderModel baiduGeocoderModel = (BaiduGeocoderModel) obj;
                    if (baiduGeocoderModel.getStatus() == 0) {
                        PreferenceUtils.saveAddressCity(baiduGeocoderModel.getResult().getAddressComponent().getCity(), mActivity);
                        PreferenceUtils.saveAddressCityCode(String.valueOf(baiduGeocoderModel.getResult().getCityCode()), mActivity);
                    }
                }
                setResult(HomeActivity.LOCATION_RESPOND_CODE);
                back();
            }
        }, BaiduGeocoderModel.class);
    }

    OnGetSuggestionResultListener suggestionListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult result) {
            progressList.setVisibility(View.GONE);
            // 获取POI检索结果
            if (result != null) {
                List<SuggestionResult.SuggestionInfo> list = result.getAllSuggestions();
                if (CheckUtils.isNoEmptyList(list)) {
                    listview.setVisibility(View.VISIBLE);
                    tvFail.setVisibility(View.GONE);
                    listview.setAdapter(adapter);
                    adapter.setList(list, edtSearch.getText().toString());
                    adapter.notifyDataSetChanged();
                } else {
                    tvFail.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }
            } else {
                tvFail.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
            }
        }
    };

    private void showAddress() {
        String address = PreferenceUtils.getAddressName(LocationNewActivity.this);
        String addressDes = PreferenceUtils.getAddressDes(LocationNewActivity.this);
        if (CheckUtils.isNoEmptyStr(address)) {
            if (CheckUtils.isNoEmptyStr(addressDes)) {
                tvCurrentLoc.setText(address + addressDes);
            } else {
                tvCurrentLoc.setText(address);
            }
        } else {
            tvCurrentLoc.setText(R.string.loc_current);
        }
        layoutCurrent.setEnabled(true);
        progressCurrent.setVisibility(View.GONE);
    }

    private void getAddressList() {
        Map<String, Object> map = new HashMap<String, Object>();
        VolleyOperater<AddressManageModel> operater = new VolleyOperater<AddressManageModel>(LocationNewActivity.this);
        operater.doRequest(Constants.URL_GET_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    List<UserAddress> userAddressList = ((AddressManageModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(userAddressList)) {
                        tvAddress.setVisibility(View.VISIBLE);
                        addressManagerListAdapter.setList(userAddressList);
                        addressManagerListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, AddressManageModel.class);
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("from")) {
            mFrom = intent.getStringExtra("from");
        }
        if (intent != null && intent.hasExtra("isSelectAddress")) {
            if (intent.getBooleanExtra("isSelectAddress", false)) {
                searchAddressLayout.setVisibility(View.GONE);
                chooseAddressLayout.setVisibility(View.VISIBLE);
            }
        }
        showAddress.setText(PreferenceUtils.getAddressCity(mActivity));

        mCitys1 = CommonUtils.readFileFromAssets(mActivity, "city.json");
        parseCityJson(mCitys1);
        FragmentManager mManager = getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        chooseAddress = new ChooseAddressFragment();
        chooseAddress.setListener(this);
        chooseAddress.setOnClickListener(this);
        ft.add(R.id.choose_city_address, chooseAddress);
        ft.show(chooseAddress);
        ft.commitAllowingStateLoss();

    }

    private void parseCityJson(String citys) {
        Gson gson = new Gson();
        mProvinces = gson.fromJson(citys, new TypeToken<List<ProvinceBean>>() {
        }.getType());
    }

    @Override
    protected void onDestroy() {
        if (mSugSearch != null) mSugSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onAreaClick(int currentItem, int pos) {
        switch (currentItem) {
            case 0:
                parseCityJson(mCitys1);
                mSelectedProvince = mProvinces.get(pos);
                mCitys = mSelectedProvince.getChildCityList();
                if (mCitys.size() == 1) {
                    mType = 1;

                    mSelectedCity = mSelectedProvince.getChildCityList().get(0);
                    //请求开通情况
                    getRegion(mSelectedCity.getId(), currentItem, pos);
                } else {
                    mType = -1;
                    chooseAddress.setCurrentItem(currentItem + 1, pos, mType);
                }
                break;
            case 1:
                if (!CheckUtils.isEmptyList(mCitys.get(0).getChildCityList())) {
                    mSelectedCity = mCitys.get(pos);
                    //请求开通情况
                    getRegion(mSelectedCity.getId(), currentItem, pos);
//                    chooseAddress.setCurrentItem(currentItem + 1, pos, mType);
                } else {
                    clickArea(pos, true);
                }
                break;
            case 2:
                clickArea(pos, false);
                break;
        }
    }

    private void getRegion(int cityId, final int currentItem, final int pos) {
        VolleyOperater<RegionModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("city", cityId);
        operater.doRequest(Constants.URL_FIND_REGION, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    RegionModel model = (RegionModel) obj;
                    mAreas = model.getValue();
                    if (mType == 1) {
                        mCitys = new ArrayList<>();
                        for (AreaBean area : mAreas) {
                            CityBean cityBean = new CityBean();
                            cityBean.setId(area.getParentId());
                            cityBean.setName(area.getName());
                            cityBean.setChildCityList(null);
                            cityBean.setParentId(area.getId());
                            cityBean.setHasMerchant(area.getHasMerchant());
                            cityBean.setLongitude(area.getLongitude());
                            cityBean.setLatitude(area.getLatitude());
                            mCitys.add(cityBean);
                        }
                    }
                    chooseAddress.setCurrentItem(currentItem + 1, pos, mType);
                }
            }
        }, RegionModel.class);
    }

    private String selectAddress;

    private void clickArea(int pos, boolean clickArea) {
        if (getIntent().getBooleanExtra("isSelectAddress", false)) {
            if (mType == 1) {
                selectAddress = mCitys.get(pos).getName();
                PreferenceUtils.saveAddressCity(mSelectedProvince.getName(), mActivity);
                PreferenceUtils.saveAddressName(selectAddress, mActivity);
                if (mCitys.get(pos).getLatitude() != null && mCitys.get(pos).getLongitude() != null) {
                    PreferenceUtils.saveLocation(mCitys.get(pos).getLatitude() + "", mCitys.get(pos).getLongitude() + "", LocationNewActivity.this);
                    codeSearch(new LatLng(mCitys.get(pos).getLatitude().doubleValue(), mCitys.get(pos).getLongitude().doubleValue()));
                } else {
                    setResult(HomeActivity.LOCATION_RESPOND_CODE);
                    back();
                }
            } else {
                selectAddress = mAreas.get(pos).getName();
                PreferenceUtils.saveAddressCity(mSelectedCity.getName(), mActivity);
                PreferenceUtils.saveAddressName(selectAddress, mActivity);
                if (mAreas.get(pos).getLatitude() != null && mAreas.get(pos).getLongitude() != null) {
                    PreferenceUtils.saveLocation(mAreas.get(pos).getLatitude() + "", mAreas.get(pos).getLongitude() + "", LocationNewActivity.this);
                    codeSearch(new LatLng(mAreas.get(pos).getLatitude().doubleValue(), mAreas.get(pos).getLongitude().doubleValue()));
                } else {
                    setResult(HomeActivity.LOCATION_RESPOND_CODE);
                    back();
                }
            }
        } else {
            if (mType == 1) {
                showAddress.setText(mSelectedProvince.getName());
                edtSearch.setText(mCitys.get(pos).getName());
            } else {
                mSelectedArea = mAreas.get(pos);
                showAddress.setText(mSelectedCity.getName());
                edtSearch.setText(mSelectedArea.getName());
            }
            isShowAddress = false;
            chooseAddressLayout.setVisibility(View.GONE);
            showAddressArrow.setImageResource(R.drawable.location_down_arrow);
        }
    }

}
