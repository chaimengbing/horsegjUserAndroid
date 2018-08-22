package com.project.mgjandroid.ui.activity;

import android.os.Bundle;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.Injector;

public class AfterPaymentCompletionActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_after_payment_completion);
        Injector.get(this).inject();
    }
}
