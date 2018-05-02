package com.project.mgjandroid.ui.activity.illegalquery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.IllegalDetailsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SunXueLiang on 2017-05-08.
 */

public class VehicleDetailsActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.lv_list)
    private ListView listView;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.img_car_logos)
    private ImageView imgLogos;
    @InjectView(R.id.tv_car_number)
    private TextView tvCarNumber;
    @InjectView(R.id.tv_illegal)
    private TextView tvIllegal;
    @InjectView(R.id.tv_fine)
    private TextView tvFine;
    @InjectView(R.id.tv_points)
    private TextView tvPoints;
    @InjectView(R.id.tv_edit)
    private TextView tvEdit;
    @InjectView(R.id.layout_title)
    private LinearLayout layoutTitle;
    @InjectView(R.id.layout_null)
    private LinearLayout layoutNull;


    private VehicleDetailsListAdapter adapter;
    private int id;
    private String imgUrl;
    private IllegalDetailsModel model;
    private String engineno;
    private String frameno;
    private String carBrand;
    private MLoadingDialog loadingDialog;
    private String lsprefix;
    private String lsnum;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_edit);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvBack.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        id = getIntent().getIntExtra("id", -1);
        imgUrl = getIntent().getStringExtra("imgUrl");
        engineno = getIntent().getStringExtra("engineno");
        frameno = getIntent().getStringExtra("frameno");
        carBrand = getIntent().getStringExtra("carBrand");
        lsprefix = getIntent().getStringExtra("lsprefix");
        lsnum = getIntent().getStringExtra("lsnum");
        tvEdit.setOnClickListener(this);
        adapter = new VehicleDetailsListAdapter(R.layout.item_edit, mActivity);
        listView.setAdapter(adapter);
        tvTitle.setText(lsprefix + lsnum);
        getCarDetails();
    }

    private void getCarDetails() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("illegalQueryCarId", id);
        VolleyOperater<IllegalDetailsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_RETURN_ILLEGAL_QUERY_RESULT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    loadingDialog.dismiss();
                    if (obj instanceof String) {
                        return;
                    }
                    model = (IllegalDetailsModel) obj;
                    if (model.getValue().getResult() != null) {
                        layoutTitle.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        layoutNull.setVisibility(View.GONE);
                        tvCarNumber.setText(lsprefix + lsnum);
                        ImageUtils.loadBitmap(mActivity, imgUrl, imgLogos, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
                        tvIllegal.setText(model.getValue().getResult().getCount() + "");
                        tvFine.setText(model.getValue().getResult().getTotalprice() + "");
                        tvPoints.setText(model.getValue().getResult().getTotalscore() + "");
                        adapter.setData(model.getValue().getResult().getList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, IllegalDetailsModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_edit:
                Intent intent = new Intent(mActivity, AddVehicleActivity.class);
                intent.putExtra("lsprefix", lsprefix);
                intent.putExtra("lsnum", lsnum);
                intent.putExtra("engineno", engineno);
                intent.putExtra("frameno", frameno);
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("carBrand", carBrand);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
                break;
            case R.id.common_back:
                back();
                break;
        }
    }
}
