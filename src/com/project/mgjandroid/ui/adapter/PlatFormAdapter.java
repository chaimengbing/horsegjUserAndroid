package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.bean.RedBag;

import java.text.SimpleDateFormat;

/**
 * Created by yuandi on 2016/5/27.
 */
public class PlatFormAdapter extends BaseListAdapter<RedBag> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private boolean isFromOrderPre;
    private boolean canUse;

    public PlatFormAdapter(int layoutId, Activity mActivity, boolean isFromOrderPre, boolean canUse) {
        super(layoutId, mActivity);
        this.isFromOrderPre = isFromOrderPre;
        this.canUse = canUse;
    }

    @Override
    protected void getRealView(ViewHolder holder, final RedBag bean, int position, View convertView, ViewGroup parent) {

    }
}
