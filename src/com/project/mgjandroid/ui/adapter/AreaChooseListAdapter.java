package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.AreaBean;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class AreaChooseListAdapter extends BaseListAdapter<AreaBean> {
    private boolean isFromHome = false;

    public AreaChooseListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void isFromHome(boolean isFromHome) {
        this.isFromHome = isFromHome;
    }

    @Override
    protected void getRealView(ViewHolder holder, AreaBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.area_name);
        tvName.setText(bean.getName());
        TextView tvStatus = holder.getView(R.id.is_entry);
        if (isFromHome) {
            tvStatus.setVisibility(View.GONE);
            if (bean.getHasMerchant() == 1) {
                tvStatus.setText("已开通");
                tvStatus.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
            } else {
                tvStatus.setText("未开通");
                tvStatus.setTextColor(mActivity.getResources().getColor(R.color.color_9));
            }
        } else {
            tvStatus.setVisibility(View.GONE);
        }
    }
}
