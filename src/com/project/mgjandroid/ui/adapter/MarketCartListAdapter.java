package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommodityDetailActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by User_Cjh on 2016/10/11.
 */
public class MarketCartListAdapter extends BaseListAdapter<Goods> {

    private View.OnClickListener mListener;

    public MarketCartListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void getRealView(ViewHolder holder, final Goods bean, int position, View convertView, ViewGroup parent) {
        GoodsSpec entity = bean.getGoodsSpecList().get(0);
        TextView exception = holder.getView(R.id.market_cart_exception_status);
//        bean.setCanEdit(true);
        if (bean.getStatus() == 0 || (entity.getStockType() == 1 && entity.getStock() == 0)) {//售罄状态
//            if(!bean.isEditing())
//                bean.setCanEdit(false);
            exception.setVisibility(View.VISIBLE);
            exception.setText("售罄");
//            bean.setSaleStatus(false);
        } else if (bean.getStatus() == 2) {
//            if(!bean.isEditing())
//                bean.setCanEdit(false);
            exception.setVisibility(View.VISIBLE);
            exception.setText("已下架");
//            bean.setSaleStatus(false);
        } else {
            exception.setVisibility(View.GONE);
//            bean.setSaleStatus(true);
        }
        ImageView status = holder.getView(R.id.market_cart_edit_status);
        status.setTag(position);
        status.setOnClickListener(mListener);
        if (bean.isCanEdit()) {
            status.setVisibility(View.VISIBLE);
        } else {
            status.setVisibility(View.INVISIBLE);
        }
        if (bean.isChecked()) {
            status.setImageResource(R.drawable.market_cart_checked);
        } else {
            status.setImageResource(R.drawable.market_cart_unselect);
        }
        LinearLayout editLayout = holder.getView(R.id.market_cart_goods_edit_count_layout);
        if (bean.isCanEdit() && bean.isEditing() && bean.isSaleStatus()) {
            editLayout.setVisibility(View.VISIBLE);
        } else {
            editLayout.setVisibility(View.INVISIBLE);
        }
        ImageView add = holder.getView(R.id.market_cart_goods_add);
        ImageView minus = holder.getView(R.id.market_cart_goods_minus);
        TextView count = holder.getView(R.id.market_cart_goods_count);
        TextView countShow = holder.getView(R.id.market_cart_goods_count_show);
        count.setText("" + bean.getCount());
        countShow.setText("×" + bean.getCount());
        if (bean.getCount() == 1) {
            minus.setImageResource(R.drawable.min_group_goods_gray);
        } else {
            minus.setImageResource(R.drawable.min_group_goods);
        }
        add.setTag(position);
        minus.setTag(position);
        add.setOnClickListener(mListener);
        minus.setOnClickListener(mListener);

        final CornerImageView image = holder.getView(R.id.market_cart_goods_image);
        if (CheckUtils.isNoEmptyStr(bean.getImgs())) {
            String[] strings = bean.getImgs().split(";");
            String imgUrl = strings[0];
            if (CheckUtils.isNoEmptyStr(imgUrl)) {
                ImageUtils.loadBitmap(mActivity, imgUrl, image, R.drawable.horsegj_default,
                        Constants.getEndThumbnail(150, 150));
            } else {
                image.setImageResource(R.drawable.horsegj_default);
            }
        } else {
            image.setImageResource(R.drawable.horsegj_default);
        }
        holder.setText(R.id.market_cart_goods_name, bean.getName());
        holder.setText(R.id.market_cart_goods_spec, entity.getSpec());
        holder.setText(R.id.market_cart_goods_price, "¥" + entity.getPrice());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isEditing() || bean.getStatus() == 2) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Rect rect = new Rect();
                    image.getGlobalVisibleRect(rect);
                    String url = bean.getImgs();
                    Intent intent = new Intent(mActivity, CommodityDetailActivity.class);
                    intent.putExtra("goods", bean);
                    intent.putExtra(CommodityDetailActivity.IMAGE_ORIGIN_RECT, rect);
                    if (TextUtils.isEmpty(url)) {
                        intent.putExtra(CommodityDetailActivity.IMAGE_URL, "");
                    } else {
                        String[] strings = url.split(";");
                        intent.putExtra(CommodityDetailActivity.IMAGE_URL, strings[0]);
                    }
                    mActivity.startActivityForResult(intent, 102);
                    mActivity.overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(mActivity, CommodityDetailActivity.class);
                    intent.putExtra("goods", bean);
                    mActivity.startActivityForResult(intent, 102);
                }
            }

        });
    }
}
