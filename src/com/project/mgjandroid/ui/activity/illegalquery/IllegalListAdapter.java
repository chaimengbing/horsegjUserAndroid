package com.project.mgjandroid.ui.activity.illegalquery;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.model.IllegalQueryListModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.SwipeMenuLayout;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by SunXueLiang on 2017-05-05.
 */

public class IllegalListAdapter extends BaseListAdapter<IllegalQueryListModel.ValueBean> {

    private View.OnClickListener listener;
    private View.OnTouchListener touchListener;

    public void setListener(View.OnClickListener listener) {

    }

    public IllegalListAdapter(int layoutId, Activity mActivity, View.OnClickListener listener, View.OnTouchListener touchListener) {
        super(layoutId, mActivity);
        this.listener = listener;
        this.touchListener = touchListener;
    }

    @Override
    protected void getRealView(ViewHolder holder, IllegalQueryListModel.ValueBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvLicensePlateNumber = (TextView) holder.getView(R.id.tv_license_plate_number);
        TextView tvModels = (TextView) holder.getView(R.id.tv_models);
        TextView tvTime = (TextView) holder.getView(R.id.tv_time);
        ImageView imgLogos = (ImageView) holder.getView(R.id.img_logos);
        TextView tvIllegal = (TextView) holder.getView(R.id.tv_illegal);
        TextView tvFine = (TextView) holder.getView(R.id.tv_fine);
        TextView tvPoints = (TextView) holder.getView(R.id.tv_points);
        ImageView imgModify = (ImageView) holder.getView(R.id.img_modify);
        TextView itemDelete = (TextView) holder.getView(R.id.item_delete);
        LinearLayout view = (LinearLayout) holder.getView(R.id.item_illegal);
        view.setOnClickListener(listener);
        imgModify.setOnClickListener(listener);
        itemDelete.setOnClickListener(listener);
        itemDelete.setTag(position);
        view.setTag(position);
        imgModify.setTag(position);
        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) holder.getView(R.id.illegal_item);
        tvLicensePlateNumber.setText(bean.getLsprefix() + bean.getLsnum());
        tvModels.setText(bean.getCarBrand());
        tvTime.setText("上次查询：" + bean.getCreateTime());
        if (CheckUtils.isNoEmptyStr(bean.getImgUrl())) {
            ImageUtils.loadBitmap(mActivity, bean.getImgUrl().split(";")[0], imgLogos, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        } else {
            imgLogos.setImageResource(R.drawable.horsegj_default);
        }
        if (bean.getIllegalQueryResult() != null) {
            if (bean.getIllegalQueryResult().getCount() > 0) {
                tvIllegal.setTextColor(view.getResources().getColor(R.color.illegal_count));
            }
            if (bean.getIllegalQueryResult().getTotalprice() > 0) {
                tvFine.setTextColor(view.getResources().getColor(R.color.illegal_count));
            }
            if (bean.getIllegalQueryResult().getTotalscore() > 0) {
                tvPoints.setTextColor(view.getResources().getColor(R.color.illegal_count));
            }
            tvIllegal.setText(bean.getIllegalQueryResult().getCount() + "");
            tvFine.setText(bean.getIllegalQueryResult().getTotalprice() + "");
            tvPoints.setText(bean.getIllegalQueryResult().getTotalscore() + "");
        } else {
            tvIllegal.setText("0");
            tvFine.setText("0");
            tvPoints.setText("0");
        }
        swipeMenuLayout.setSwipeEnable(true);

    }


}
