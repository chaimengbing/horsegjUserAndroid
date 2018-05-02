package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationCategory;
import com.project.mgjandroid.bean.information.InformationServiceCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationServiceCategoryListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.RecruitActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/19.
 */

public class InformationCategoryChooseActivity extends BaseActivity {

    public static final String FROM_INFORMATION_LIST_PAGE = "from_information_list_page";

    public static final String FROM_INFORMATION_PUBLISH_PAGE = "from_information_publish_page";

    public static final String CATEGORY_ID = "category_id";

    public static final String CATEGORY_NAME = "category_name";

    @InjectView(R.id.list_view)
    private ListView listView;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    private InformationServiceCategoryAdapter adapter;
    private MLoadingDialog mMLoadingDialog;
    private String mFrom;
    private int informationTypeId;

    public static void toChooseInformationCategory(Activity context, int informationTypeId, String from, int requestCode) {
        Intent intent = new Intent(context, InformationCategoryChooseActivity.class);
        intent.putExtra("informationTypeId", informationTypeId);
        intent.putExtra("from", from);
        context.startActivityForResult(intent, requestCode);
    }

    public static void toChooseInformationCategory(Activity context, int informationTypeId, String from) {
        Intent intent = new Intent(context, InformationCategoryChooseActivity.class);
        intent.putExtra("informationTypeId", informationTypeId);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_information_category);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("from")) {
            mFrom = intent.getStringExtra("from");
            informationTypeId = intent.getIntExtra("informationTypeId", 0);
        }

        adapter = new InformationServiceCategoryAdapter(R.layout.item_full_time_list_view, mActivity, this);
        listView.setAdapter(adapter);

        ivBack.setOnClickListener(this);
        tvTitle.setText("选择类别");
        mMLoadingDialog = new MLoadingDialog();
        getAllTag();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.item_grid_label:
                String tag = (String) v.getTag();
                String[] split = tag.split(";");
                int parentPos = Integer.parseInt(split[0]);
                int pos = Integer.parseInt(split[1]);

                List<InformationServiceCategory> data = adapter.getData();
                for (InformationServiceCategory bean : data) {
                    List<InformationCategory> categoryList = bean.getInformationCategoryList();
                    for (InformationCategory posbean : categoryList) {
                        posbean.setCheck(false);
                    }
                }

                InformationServiceCategory bean = data.get(parentPos);
                InformationCategory categoryBean = bean.getInformationCategoryList().get(pos);
                categoryBean.setCheck(true);
                adapter.setData(data);

                if (FROM_INFORMATION_PUBLISH_PAGE.equals(mFrom)) {
                    Intent intent = new Intent();
                    intent.putExtra(CATEGORY_ID, categoryBean.getId());
                    intent.putExtra(CATEGORY_NAME, categoryBean.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (FROM_INFORMATION_LIST_PAGE.equals(mFrom)) {
                    InformationActivity.toInformationList(mActivity, categoryBean.getId(), categoryBean.getName(), informationTypeId);
                    finish();
                }
                break;
        }
    }

    /**
     * 获取所有的tag
     */
    public void getAllTag() {
        mMLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("informationTypeId", informationTypeId);
        VolleyOperater<InformationServiceCategoryListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_SERVICE_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationServiceCategoryListModel model = (InformationServiceCategoryListModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue()))
                        adapter.setData(model.getValue());
                }
            }
        }, InformationServiceCategoryListModel.class);
    }
}
