package com.project.mgjandroid.ui.activity.geomancy;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationFengShui;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by Administrator on 2016/12/2.
 */

public class FengShuiListAdapter extends BaseListAdapter<InformationFengShui> {

    public FengShuiListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationFengShui bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.fengshui_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], cornerImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        TextView isTop = holder.getView(R.id.fengshui_is_top);
        CornerImageView avatar = holder.getView(R.id.fengshui_user_avatar);
        avatar.setVisibility(View.VISIBLE);

        holder.setText(R.id.fengshui_name, bean.getName());
        holder.setText(R.id.fengshui_age, bean.getAge() + "岁");
        holder.setText(R.id.fengshui_position_name, bean.getCategoryName());
        holder.setText(R.id.fengshui_province, bean.getWhereProvinceName());
        holder.setText(R.id.fengshui_be_good_at, "擅长：" + bean.getGoodField());
        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }


}
