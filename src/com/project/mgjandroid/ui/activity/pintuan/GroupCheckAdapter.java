package com.project.mgjandroid.ui.activity.pintuan;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GroupCheckModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by User_Cjh on 2016/8/19.
 */
public class GroupCheckAdapter extends BaseListAdapter<GroupCheckModel.ValueEntity.GroupBuyListEntity.ValueBean> {
    public GroupCheckAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupCheckModel.ValueEntity.GroupBuyListEntity.ValueBean bean, int position, View convertView, ViewGroup parent) {
        CornerImageView image = holder.getView(R.id.my_pintuan_image_2);
        if (CheckUtils.isNoEmptyStr(bean.getImgs())) {
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], image, R.drawable.horsegj_default, Constants.getEndThumbnail(75, 75));
        }
        holder.setText(R.id.group_check_group_price, "¥" + bean.getPrice());
        holder.setText(R.id.group_check_name, bean.getGoodsName());
        TextView market = holder.getView(R.id.group_check_market_price);
        market.setText("¥" + bean.getOriginalPrice());
        market.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
