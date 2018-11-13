package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

public class GoodsListAdapter extends BaseListAdapter<NewOrderFragmentModel.ValueEntity.OrderItemsEntity>{

    public GoodsListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, NewOrderFragmentModel.ValueEntity.OrderItemsEntity bean, int position, View convertView, ViewGroup parent) {
        TextView tvGoodName = holder.getView(R.id.tv_good_name);


        tvGoodName.setText(bean.getName());

    }
}
