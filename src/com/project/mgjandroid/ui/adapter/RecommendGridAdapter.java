package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryPublicity;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class RecommendGridAdapter extends BaseListAdapter<GroupPurchasePrimaryPublicity> {

    public RecommendGridAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }


    @Override
    protected void getRealView(ViewHolder holder, GroupPurchasePrimaryPublicity bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.recommend_name);
        ImageView recommendImageView = holder.getView(R.id.recommend_imageview);
        ImageView localImageView = holder.getView(R.id.local_imageview);
        localImageView.setBackground(bean.getResSource());
        ImageUtils.loadBitmap(mActivity, bean.getImg(), recommendImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(355, 105));
        tvName.setText(CheckUtils.isNoEmptyStr(bean.getGroupPurchaseMerchantName()) ? bean.getGroupPurchaseMerchantName() : "");
    }
}
