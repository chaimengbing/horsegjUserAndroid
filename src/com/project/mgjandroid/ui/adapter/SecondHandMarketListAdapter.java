package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.SecondhandInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/7.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandMarketListAdapter extends BaseListAdapter<SecondhandInformation> {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public SecondHandMarketListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, SecondhandInformation bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.second_market_image_view);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], cornerImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }

        TextView tvName = holder.getView(R.id.second_market_name);
        tvName.setText(bean.getTitle());

        TextView tvTag = holder.getView(R.id.second_market_tag);
        tvTag.setText(bean.getCategoryName());

        TextView tvPrice = holder.getView(R.id.second_hand_price);
        String showAmt = "";
        if (bean.getType() == 1) {
            showAmt = StringUtils.money2Str(bean.getAmt(), "元");
        } else {
            showAmt = StringUtils.money2Str(bean.getMinAmt(), "元") + " - " + StringUtils.money2Str(bean.getMaxAmt(), "元");
        }
        tvPrice.setText(showAmt);
        TextView tvTime = holder.getView(R.id.second_hand_time);
        tvTime.setText("发布时间：" + DateUtils.format(bean.getModifyTime(), bean.getServiceTime()));

        TextView tvStatus = holder.getView(R.id.second_hand_status);
        if (bean.getIsTop() == 0) {
            tvStatus.setVisibility(View.GONE);
        } else if (bean.getIsTop() == 1) {
            tvStatus.setVisibility(View.VISIBLE);
        }
    }
}
