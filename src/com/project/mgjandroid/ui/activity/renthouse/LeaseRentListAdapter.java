package com.project.mgjandroid.ui.activity.renthouse;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.HouseLeaseInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by User_Cjh on 2017/1/4.
 */
public class LeaseRentListAdapter extends BaseListAdapter<HouseLeaseInformation> {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public LeaseRentListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, HouseLeaseInformation bean, int position, View convertView, ViewGroup parent) {
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
            showAmt = StringUtils.money2Str(bean.getAmt(), "元/月");
        } else {
            showAmt = bean.getLeasePrice() + "元/月";
        }
        tvPrice.setText(showAmt);

        TextView tvTime = holder.getView(R.id.second_hand_time);
//        tvTime.setText(format.format(bean.getCreateTime()));
        tvTime.setText(DateUtils.format(bean.getModifyTime(), bean.getServiceTime()));

        TextView tvAddress = holder.getView(R.id.rent_house_address);
        tvAddress.setText(bean.getHouseType() + "-" + bean.getSectorArea());

        TextView tvStatus = holder.getView(R.id.second_hand_status);
        if (bean.getIsTop() == 0) {
            tvStatus.setVisibility(View.GONE);
        } else if (bean.getIsTop() == 1) {
            tvStatus.setVisibility(View.VISIBLE);
        }
    }
}
