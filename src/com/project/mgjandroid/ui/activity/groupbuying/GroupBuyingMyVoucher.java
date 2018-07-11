package com.project.mgjandroid.ui.activity.groupbuying;

import android.os.Bundle;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.Injector;

public class GroupBuyingMyVoucher extends BaseActivity{
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_my_voucher);
        Injector.get(this).inject();
    }
}
