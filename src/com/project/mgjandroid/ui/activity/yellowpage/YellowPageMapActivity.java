package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.MapTranslateUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/7.
 */

public class YellowPageMapActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.map_view)
    private MapView mMapView;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.loc_map)
    private TextView locMap;
    @InjectView(R.id.tv_address)
    private TextView tvAddress;

    private String[] packageNames = {"com.baidu.BaiduMap", "com.autonavi.minimap", "com.tencent.map"};
    private boolean[] hasMaps = new boolean[3];
    private BaiduMap baiduMap;
    private String address = "";
    private double latitude;
    private double longitude;
    private PopupWindow mPopupWindow;

    public static void toYellowPageMapActivity(Context context, String name, String address, double latitude, double longitude) {
        Intent intent = new Intent(context, YellowPageMapActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_map);
        Injector.get(this).inject();
        address = getIntent().getStringExtra("address");
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        initView();
    }

    private void initView() {
        tvTitle.setText("地图");
        tvName.setText(getIntent().getStringExtra("name"));
        tvAddress.setText(address);
        ivBack.setOnClickListener(this);
        locMap.setOnClickListener(this);

        mMapView.removeViewAt(1);
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);

        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationCurrentSite(latitude, longitude);
    }

    private void locationCurrentSite(double lat, double lon) {
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(lat)
                .longitude(lon)
                .build();
        baiduMap.setMyLocationData(locData);

        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.group_buying_icon_location));
        baiduMap.setMyLocationConfigeration(config);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.loc_map:
                if (isMapAvilable(mActivity)) {
                    showPopupWindow();
                } else {
                    ToastUtils.displayMsg("没有检测到地图", mActivity);
                }
                break;
            case R.id.baidu_map:
                try {
                    Intent i1 = new Intent(); //调起百度地图驾车路线规划
                    i1.setData(Uri.parse("baidumap://map/direction?destination=" + latitude + "," + longitude + "&mode=driving"));
                    startActivity(i1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissPopupWindow();
                break;
            case R.id.gaode_map:
                try {
                    double[] latLon = MapTranslateUtil.map_bd2hx(latitude, longitude); //调起高德地图驾车路线规划
                    Intent i2 = new Intent();
                    i2.setData(Uri.parse("androidamap://route?sourceApplication=马管家&dlat="
                            + latLon[0] + "&dlon=" + latLon[1] + "&dname=" + address + "&dev=0&m=0&t=2"));
                    startActivity(i2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissPopupWindow();
                break;
            case R.id.tencent_map:
                try {
                    double[] latLon = MapTranslateUtil.map_bd2hx(latitude, longitude); //调起腾讯地图驾车路线规划
                    Intent i3 = new Intent();
                    i3.setData(Uri.parse("qqmap://map/routeplan?type=drive&to=" + address + "&tocoord="
                            + latLon[0] + "," + latLon[1] + "&coord_type=1&policy=0&referer=马管家"));
                    startActivity(i3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissPopupWindow();
                break;
            case R.id.root_view_popup:
            case R.id.cancel:
                dismissPopupWindow();
                break;
        }
    }

    private boolean isMapAvilable(Context context) {
        boolean isAvilable = false;
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        for (int i = 0; i < packageNames.length; i++) {
            if (pName.contains(packageNames[i])) {
                isAvilable = true;
                hasMaps[i] = true;
            } else {
                hasMaps[i] = false;
            }
        }
        return isAvilable;
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_map_app, null);
        LinearLayout baidu = (LinearLayout) linearLayout.findViewById(R.id.baidu_map);
        LinearLayout gaode = (LinearLayout) linearLayout.findViewById(R.id.gaode_map);
        LinearLayout tencent = (LinearLayout) linearLayout.findViewById(R.id.tencent_map);
        RelativeLayout root = (RelativeLayout) linearLayout.findViewById(R.id.root_view_popup);
        root.setOnClickListener(this);
        if (!hasMaps[0]) {
            baidu.setVisibility(View.GONE);
        }
        if (!hasMaps[1]) {
            gaode.setVisibility(View.GONE);
        }
        if (!hasMaps[2]) {
            tencent.setVisibility(View.GONE);
        }
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.cancel);
        baidu.setOnClickListener(this);
        gaode.setOnClickListener(this);
        tencent.setOnClickListener(this);
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
