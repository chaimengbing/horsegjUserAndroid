package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.MarketSearchHistory;

/**
 * Created by User_Cjh on 2016/10/19.
 */
public class MarketSearchHistoryListAdapter extends BaseListAdapter<MarketSearchHistory> {
    public MarketSearchHistoryListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, MarketSearchHistory bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.market_search_history_content, bean.getName());
    }
}
