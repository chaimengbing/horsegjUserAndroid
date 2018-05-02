package com.project.mgjandroid.ui.activity.invitingfriends;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.InvitingResultsModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.SimpleDateFormat;

/**
 * Created by SunXueLiang on 2017-07-26.
 */

public class InvitingResultsAdapter extends BaseListAdapter<InvitingResultsModel.ValueBean.UserListBean> {
    public InvitingResultsAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InvitingResultsModel.ValueBean.UserListBean bean, int position, View convertView, ViewGroup parent) {
        CornerImageView ivHeadPortrait = holder.getView(R.id.iv_head_portrait);
        ImageUtils.loadBitmap(mActivity, bean.getHeaderImg(), ivHeadPortrait, R.drawable.info_no_header_icon, Constants.getEndThumbnail(86, 66));
        String mobile = bean.getMobile();
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        holder.setText(R.id.tv_time, format.format(bean.getCreateTime()));
    }

}
