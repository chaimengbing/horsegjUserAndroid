package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.fragment.HomeFragment;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * 外卖跳转老版主页
 */
public class OldHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.home_old_act);
        Injector.get(this).inject();
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOld", true);
        if (!homeFragment.isAdded()){
            homeFragment.setArguments(bundle);
        }
        switchContent(homeFragment);
    }


    // 切换Fragment
    public void switchContent(Fragment to) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.home_act_fl, to, to.getClass().getName());
//        } else {
//            //如果之前没有添加过
//            if (!to.isAdded()) {
//                transaction
//                        .hide(currentFragment)
//                        .add(R.id.home_act_fl, to, to.getClass().getName());  //第三个参数为当前的fragment绑定一个tag，tag为当前绑定fragment的类名
//            } else {
//                transaction
//                        .hide(currentFragment)
//                        .show(to);
//            }
//        }
//        currentFragment = to;
        transaction.commit();
    }

    /**
     * 解决App重启后导致Fragment重叠的问题
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

}
