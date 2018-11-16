package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.horsegj.company.wxapi.WXEntryActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Order;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RiderInformation;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.groupbuying.RiderImpressionAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RatingBar;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiderActivity extends BaseActivity{

    @InjectView(R.id.common_back)
    private ImageView back;
    @InjectView(R.id.rider_avatar)
    private CornerImageView riderAvatar;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.rider_score)
    private RatingBar riderScore;
    @InjectView(R.id.tv_punctuality)
    private TextView tvPunctuality;
    @InjectView(R.id.tv_good_reputation)
    private TextView tvGoodReputation;
    @InjectView(R.id.tv_delivery_time)
    private TextView tvDeliveryTime;
    @InjectView(R.id.phone)
    private RelativeLayout phone;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;

    private MLoadingDialog loadingDialog;
    private int deliverymanId;
    private CallPhoneDialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_rider);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        deliverymanId = getIntent().getIntExtra("deliverymanId", -1);
        getRiderData();
    }

    private void getRiderData(){
//        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deliverymanId", deliverymanId);
        VolleyOperater<RiderInformation> operater = new VolleyOperater<RiderInformation>(RiderActivity.this);
        operater.doRequest(Constants.URL_FIND_DELIVERYMAN_INFO, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                RiderInformation information = (RiderInformation) obj;
                RiderInformation.ValueBean value = information.getValue();
                show(value);
            }
        }, RiderInformation.class);
    }

    private void show(final RiderInformation.ValueBean value ){
        ImageUtils.loadBitmap(mActivity, value.getHeaderImg(), riderAvatar, R.drawable.icon_default_avator, Constants.getEndThumbnail(56, 56));
        tvName.setText(value.getName());
        riderScore.setStar(value.getDeliverymanScore().floatValue());
        riderScore.invalidate();
        tvPunctuality.setText(StringUtils.BigDecimal2Str(value.getDeliverymanPunctualityScore())+"%");
        tvGoodReputation.setText(StringUtils.BigDecimal2Str(value.getDeliverymanGoodScore())+"%");
        tvDeliveryTime.setText(StringUtils.BigDecimal2Str(value.getDeliverymanAvgTime())+"min");
        JSONObject  myJson = JSONObject.parseObject(value.getDeliverymanImpress());
        Map<String,Object> map = myJson;
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            if(Integer.parseInt(entry.getKey())<=6){
                String re = getResult(entry.getKey()) + " " +  entry.getValue();
                list.add(re);
            }
        }
        RiderImpressionAdapter riderImpressionAdapter = new RiderImpressionAdapter(mActivity);
        gridView.setAdapter(riderImpressionAdapter);
        riderImpressionAdapter.setList(list);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCallDialog(value.getMobile());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }


  private String getResult(String source){
        if (source.equals("1")){
            return "穿戴工装";
        }
      if (source.equals("2")){
          return "风雨无阻";
      }
      if (source.equals("3")){
          return "快速准时";
      }
      if (source.equals("4")){
          return "仪容整洁";
      }
      if (source.equals("5")){
          return "货品完好";
      }
      if (source.equals("6")){
          return "礼貌热情";
      }
        return "";
    }

    private void showCallDialog(final String mobild) {
        dialog = new CallPhoneDialog(RiderActivity.this, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                //拨打电话
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                //submitOrderEntity.getMerchant().getContacts() 商家电话
                intent.setData(Uri.parse("tel:" + mobild));
                RiderActivity.this.startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", mobild);
        dialog.show();
    }

}
