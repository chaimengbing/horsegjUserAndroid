package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.information.PublishAreaAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RecyclerViewDivider;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by pb on 2016-12-13.
 */

public class ChoosePublishIdentityActivity extends BaseActivity {

    public static final String AGENT = "agent";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;

    private MLoadingDialog loadingDialog;
    private String[] data = new String[]{"个人", "商家"};
    private PublishIdentityAdapter adapter;

    public static void toChoosePublishIdentity(Activity context, int requestCode) {
        Intent intent = new Intent(context, ChoosePublishIdentityActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_choose_publish_identity);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("选择身份");
        ivBack.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        adapter = new PublishIdentityAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(
                mActivity, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.common_gray_line)));
        recyclerView.setAdapter(adapter);
        adapter.setListener(new PublishAreaAdapter.OnClickListener() {
            @Override
            public void onClick(int positon) {
                Intent intent = new Intent();
                intent.putExtra(AGENT, data[positon]);
                if (positon == 0) {
                    intent.putExtra("identityCount", 1);
                } else {
                    intent.putExtra("identityCount", 2);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        adapter.setList(data);
    }


}
