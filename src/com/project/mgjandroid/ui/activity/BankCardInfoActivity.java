package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Bank;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseBankListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.ChooseBankCardListAdapter;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.List;

public class BankCardInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.add_card_next)
    private TextView tvNext;
    @InjectView(R.id.list_dialog_list_view)
    private ListView listView;


    private ChooseBankCardListAdapter mAdapter;
    private Bank mSelectBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_info);
        Injector.get(this).inject();
        tvTitle.setText("开户行类型");
        initView();
        getBank();
    }

    private void initView() {
        imgBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        tvNext.setOnClickListener(this);
        checkState();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_card_next:
                Intent intent = new Intent(this, AddBankCardActivity.class);
                intent.putExtra("bank", mSelectBank);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取开户行类型
     */
    private void getBank() {
        VolleyOperater<ChooseBankListModel> operater = new VolleyOperater<>(BankCardInfoActivity.this);
        operater.doRequest(Constants.URL_FIND_BANK_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    ChooseBankListModel chooseBankListModel = (ChooseBankListModel) obj;
                    mAdapter = new ChooseBankCardListAdapter(R.layout.item_dialog_list_view, mActivity);
                    mAdapter.setData(chooseBankListModel.getValue());
                    listView.setAdapter(mAdapter);
                }
            }
        }, ChooseBankListModel.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<Bank> data = mAdapter.getData();
        for (Bank bank : data) {
            bank.setIsCheck(false);
        }
        mSelectBank = data.get(position);
        mSelectBank.setIsCheck(true);
        mAdapter.notifyDataSetChanged();
        checkState();
    }

    public void checkState() {
        if (mSelectBank != null) {
            tvNext.setEnabled(true);
        } else {
            tvNext.setEnabled(false);
        }
    }
}
