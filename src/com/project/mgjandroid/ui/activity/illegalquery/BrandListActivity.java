package com.project.mgjandroid.ui.activity.illegalquery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.AutoLogos;
import com.project.mgjandroid.model.BrandListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.PublishAreaAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by SunXueLiang on 2017-05-08.
 */

public class BrandListActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.exlv)
    private ExpandableListView mExlv;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.lv_right)
    private ListView mLvRight;
    private Map<String, List<AutoLogos>> value;
    private String[] letter = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private MLoadingDialog loadingDialog;
    private List<String> strings;

    public static void toBrandListActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, BrandListActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_brand_list);
        Injector.get(this).inject();
        loadingDialog = new MLoadingDialog();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        getBrandData();
    }

    private void getBrandData() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<BrandListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_CAR_BRAMDS_LIST, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    loadingDialog.dismiss();
                    if (obj instanceof String) {
                        return;
                    }
                    BrandListModel model = (BrandListModel) obj;
                    value = model.getValue();
                    initListener();
                }
            }
        }, BrandListModel.class);
    }

    public void initListener() {
        SimpleExpAdapter adapter = new SimpleExpAdapter(value, this);
        mExlv.setAdapter(adapter);
        //全都展开不能回缩
        for (int i = 0; i < value.size(); i++) {
            mExlv.expandGroup(i);
        }
        mExlv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        strings = new ArrayList<>();
        for (Map.Entry<String, List<AutoLogos>> entry : value.entrySet()) {
            strings.add(entry.getKey());
        }
        Collections.sort(strings);
        mLvRight.setAdapter(new LvRightItemAdapter(this, strings));
        mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mExlv.setSelectedGroup(position);

            }
        });

    }

}
