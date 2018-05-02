package com.project.mgjandroid.ui.activity.invitingfriends;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CashBackListModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by SunXueLiang on 2017-07-26.
 */

public class CashBackAdapter extends BaseListAdapter<CashBackListModel.ValueBean> {


    public CashBackAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, CashBackListModel.ValueBean bean, int position, View convertView, ViewGroup parent) {
        ImageView ivHeadPortrait = holder.getView(R.id.iv_head_portrait);
        ImageUtils.loadBitmap(mActivity, bean.getInviteeAppUser().getHeaderImg(), ivHeadPortrait, R.drawable.info_no_header_icon, Constants.getEndThumbnail(86, 66));
        String mobile = bean.getInviteeAppUser().getMobile();
        if (!TextUtils.isEmpty(mobile) && mobile.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mobile.length(); i++) {
                char c = mobile.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            holder.setText(R.id.tv_phone, sb.toString());
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        holder.setText(R.id.tv_time, "+ ¥" + df.format(bean.getCashbackAmt()));
    }
}
