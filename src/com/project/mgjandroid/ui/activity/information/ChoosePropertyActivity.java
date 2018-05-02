package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.PropertyArrayModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RecyclerViewDivider;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/15.
 */

public class ChoosePropertyActivity extends BaseActivity {

    public static final String PROPERTY = "property";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;

    private String title;
    private PropertyChooseAdapter adapter;
    private MLoadingDialog loadingDialog;

    private List<String> data = new ArrayList<>();

    public static void toChooseProperty(Activity context, String title, int informationType, int type, int requestCode) {
        Intent intent = new Intent(context, ChoosePropertyActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("informationType", informationType);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_choose_property);
        Injector.get(this).inject();
        title = getIntent().getStringExtra("title");
        int informationType = getIntent().getIntExtra("informationType", 0);
        int type = getIntent().getIntExtra("type", 0);
        initView();

        if (informationType > 0) {
            getData(informationType, type);
        } else if (informationType == -1) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            MLog.e(year + "年");
            for (int i = 60; i >= 0; i--) {
                data.add(String.valueOf(year - i));
            }
            adapter.setList(data);
            recyclerView.scrollToPosition(data.size() - 1);
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            MLog.e(year + "年");
            for (int i = 60; i >= 18; i--) {
                data.add(String.valueOf(year - i));
            }
            adapter.setList(data);
            recyclerView.scrollToPosition(data.size() - 1);
        }
    }

    private void initView() {
        tvTitle.setText(title);
        ivBack.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        adapter = new PropertyChooseAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(
                mActivity, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.common_gray_line)));
        recyclerView.setAdapter(adapter);
        adapter.setListener(new PropertyChooseAdapter.PropertyClickListener() {
            @Override
            public void onClick(int positon) {
                Intent intent = new Intent();
                intent.putExtra(PROPERTY, data.get(positon));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 查询个人求职与招聘信息发布基础信息
     *
     * @param informationType(position:求职;recruit:招聘;)
     * @param type{[position(1:最高学历;2:工作经验;3:期望薪资;)][recruit(1:公司性质;2:从事行业;3:公司规模;4:招聘人数;5:学历要求;6:工作年限;7:薪资水平;)]}
     */
    private void getData(int informationType, int type) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();
        params.put("informationType", informationType);
        params.put("type", type);
        VolleyOperater<PropertyArrayModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_IN_BASIC_INFORMATION, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PropertyArrayModel model = (PropertyArrayModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        String[] array = new String[model.getValue().size()];
                        for (int i = 0, size = model.getValue().size(); i < size; i++) {
                            array[i] = model.getValue().get(i).getName();
                        }
                        data = Arrays.asList(array);
                        adapter.setList(data);
                    }
                }
            }
        }, PropertyArrayModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
