package com.project.mgjandroid.ui.activity;

import android.os.Bundle;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.Injector;

public class RiderActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_rider);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

    }
}
