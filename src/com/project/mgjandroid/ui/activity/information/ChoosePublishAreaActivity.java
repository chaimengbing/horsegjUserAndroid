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
import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.AgentListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RecyclerViewDivider;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.SortChineseName;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuandi on 2016/11/22.
 */

public class ChoosePublishAreaActivity extends BaseActivity {

    public static final String AGENT = "agent";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;

    private MLoadingDialog loadingDialog;
    private List<Agent> data = new ArrayList<>();
    private PublishAreaAdapter adapter;

    public static void toChoosePublishArea(Activity context, int requestCode) {
        Intent intent = new Intent(context, ChoosePublishAreaActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_choose_publish_area);
        Injector.get(this).inject();
        initView();
        getData();
    }

    private void initView() {
        tvTitle.setText("发布区域");
        ivBack.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        adapter = new PublishAreaAdapter(mActivity);
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
                intent.putExtra(AGENT, data.get(positon));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getData() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<AgentListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_AGENT_BASIS_LIST, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    AgentListModel model = (AgentListModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        data = model.getValue();
                        for (Agent agent : data) {
                            String area = "";
                            if (agent.getProvinceName() != null) area = agent.getProvinceName();
                            if (agent.getCityName() != null && !area.equals(agent.getCityName()))
                                area += agent.getCityName();
                            if (agent.getAgentType() == 1 && agent.getDistrictName() != null)
                                area += agent.getDistrictName();
                            agent.setSortArea(area);
                        }
                        Collections.sort(data, new SortChineseName());
                        ;
                        adapter.setList(data);
                    }
                }
            }
        }, AgentListModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
