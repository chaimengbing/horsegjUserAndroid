package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

/**
 * Created by ning on 2016/3/14.
 */
public class ConfirmOrderListAdapter extends BaseListAdapter<ConfirmOrderModel.ValueEntity.OrderItemsEntity> {

    public ConfirmOrderListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ConfirmOrderModel.ValueEntity.OrderItemsEntity bean, int position, View convertView, ViewGroup parent) {
        TextView tv_name = holder.getView(R.id.name);
        TextView tv_num = holder.getView(R.id.num);
        TextView tv_price = holder.getView(R.id.price);
        TextView tv_type = holder.getView(R.id.type);
        CornerImageView picture_imageview = holder.getView(R.id.picture_imageview);

        if (CheckUtils.isNoEmptyStr(bean.getLabelUrl())) {
            ImageUtils.loadBitmap(mActivity, bean.getLabelUrl(), picture_imageview, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        }

        tv_num.setText("x" + bean.getQuantity());
        tv_price.setText("¥" + StringUtils.BigDecimal2Str(bean.getTotalPrice()));
        tv_name.setText(bean.getName());
        String spec = bean.getSpec();
        if (CheckUtils.isNoEmptyStr(bean.getAttributes())) {
            tv_type.setText(spec + " / " + bean.getAttributes());
        } else {
            tv_type.setText(spec);
        }
    }
}
