package com.project.mgjandroid.ui.activity.pintuan;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.GroupCheckModel;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by User_Cjh on 2016/8/19.
 */
public class GroupCheckActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.group_check_list)
    private ListView lvCheck;
    private GroupCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_check);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
    }

    private void initView() {
        tvTitle.setText("发起拼团");
        adapter = new GroupCheckAdapter(R.layout.item_group_check, mActivity);
        lvCheck.setAdapter(adapter);
        if (getIntent().hasExtra("check")) {
            GroupCheckModel.ValueEntity.GroupBuyListEntity entity = (GroupCheckModel.ValueEntity.GroupBuyListEntity) getIntent().getSerializableExtra("check");
            adapter.setData(entity.getValue());
        } else {
            finish();
        }
    }
}
