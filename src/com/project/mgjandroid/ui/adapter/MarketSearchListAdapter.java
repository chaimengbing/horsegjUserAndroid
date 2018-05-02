package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.CommodityDetailActivity;
import com.project.mgjandroid.ui.activity.MarketSearchActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2016/10/12.
 */
public class MarketSearchListAdapter extends BaseListAdapter<Goods> {
    private SuperMarketCartModel superMarketCartModel;
    private SuperMarketCartModel.SuperMarketCartBean superMarketCartBean;
    private AddGoodsListener listener;

    public MarketSearchListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
        superMarketCartModel = SuperMarketCartModel.getInstance();
        superMarketCartModel.initData();
        superMarketCartBean = superMarketCartModel.getSuperMarketCartBean();
    }

    public void setListener(AddGoodsListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(final ViewHolder holder, final Goods bean, final int position, View convertView, ViewGroup parent) {
        GoodsSpec entity = bean.getGoodsSpecList().get(0);
        final CornerImageView image = holder.getView(R.id.goods_item_img);
        if (CheckUtils.isNoEmptyStr(bean.getImgs())) {
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], image, R.drawable.horsegj_default, Constants.getEndThumbnail(150, 150));
        } else {
            image.setImageResource(R.drawable.horsegj_default);
        }
//        holder.setText(R.id.goods_item_tv_name,bean.getName());
        holder.setText(R.id.goods_item_tv_spec, entity.getSpec());
        holder.setText(R.id.goods_item_tv_price, entity.getPrice() + "");
        TextView originPrice = holder.getView(R.id.goods_item_tv_original_price);
        if (entity.getOriginalPrice() == null || entity.getOriginalPrice().compareTo(BigDecimal.ZERO) == 0) {
            originPrice.setVisibility(View.GONE);
        } else {
            originPrice.setVisibility(View.VISIBLE);
            originPrice.setText("¥" + entity.getOriginalPrice());
            originPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        ImageView add = holder.getView(R.id.goods_item_img_add);
        TextView buyCount = holder.getView(R.id.goods_item_tv_buy_count);
        ImageView minus = holder.getView(R.id.goods_item_img_minus);
        if (bean.getCount() != 0) {
            buyCount.setText(bean.getCount() + "");
            minus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mActivity));
            buyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mActivity));
        } else {
            minus.setTranslationX(0f);
            buyCount.setTranslationX(0f);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = bean.getCount();
                GoodsSpec goodsSpec = bean.getGoodsSpecList().get(0);
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("购买的小伙伴太多，库存告急！", mActivity);
                    return;
                }
                if (goodsSpec.getOrderLimit() != 0 && count >= goodsSpec.getOrderLimit()) {
                    ToastUtils.displayMsg(bean.getName() + "每单最多可购买" + goodsSpec.getOrderLimit() + "份喔！", mActivity);
                    return;
                }
                if (count == 0) {
                    count++;
                    bean.setCount(count);
                    ((TextView) holder.getView(R.id.goods_item_tv_buy_count)).setText(count + "");
                    AnimatorUtils.leftTranslationRotating(holder.getView(R.id.goods_item_img_minus), PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mActivity));
                    AnimatorUtils.leftTranslationRotating(holder.getView(R.id.goods_item_tv_buy_count), PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mActivity));
                } else {
                    if (count >= 99) {
                        ToastUtils.displayMsg("该商品已达到最大购买数量", mActivity);
                        return;
                    }
                    count++;
                    bean.setCount(count);
                    ((TextView) holder.getView(R.id.goods_item_tv_buy_count)).setText(count + "");
                }
                if (!superMarketCartModel.isHasChange())
                    superMarketCartModel.setHasChange(true);
                superMarketCartBean.addGoods(bean.getGoodsSpecList().get(0).getGoodsId(), 1);

                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(mActivity);// buyImg是动画的图片
                ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                if (listener != null) listener.addGoods(ball, startLocation, null); // 开始执行动画
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = bean.getCount();
                if (count == 1) {
                    count--;
                    bean.setCount(count);
                    ((TextView) holder.getView(R.id.goods_item_tv_buy_count)).setText(count + "");
                    AnimatorUtils.rightTranslationRotating(holder.getView(R.id.goods_item_img_minus), PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mActivity));
                    AnimatorUtils.rightTranslationRotating(holder.getView(R.id.goods_item_tv_buy_count), PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mActivity));
                } else {
                    if (count > 0) {
                        count--;
                        ((TextView) holder.getView(R.id.goods_item_tv_buy_count)).setText(count + "");
                        bean.setCount(count);
                    }
                }
                //更新购物车
                superMarketCartBean.minusGoods(bean.getGoodsSpecList().get(0).getGoodsId(), 1);
                if (listener != null) listener.minusGoods();
            }
        });
        TextView tvName = holder.getView(R.id.goods_item_tv_name);
        if (CheckUtils.isNoEmptyStr(bean.getHighLights())) {
            String color = bean.getHighLights();
            color = color.replaceAll("<em>", "<font color='#ff9900'>");
            color = color.replace("</em>", "</font>");
            tvName.setText(Html.fromHtml(color));
        } else {
            if (CheckUtils.isNoEmptyStr(bean.getName()))
                tvName.setText(bean.getName());
        }
        TextView tcOver = holder.getView(R.id.merchant_sleep);
        RelativeLayout hideLayout = holder.getView(R.id.buy_count_hide);
        TextView tvLimit = holder.getView(R.id.tv_limit);
        TextView tvRmb = holder.getView(R.id.goods_item_tv_rmb);
        TextView tvPrice = holder.getView(R.id.goods_item_tv_price);
        TextView tvStock = holder.getView(R.id.tv_stock);
        CornerImageView tvSaleOver = holder.getView(R.id.goods_item_img_over);
        tvLimit.setVisibility(View.GONE);
        if (entity.getOrderLimit() != null && entity.getOrderLimit() > 0) {
            tvLimit.setVisibility(View.VISIBLE);
            tvLimit.setText("每单限购" + entity.getOrderLimit() + "份");
        } else {
            tvLimit.setVisibility(View.GONE);
        }
        if (bean.getStatus() == 0 || (bean.getGoodsSpecList().get(0).getStockType() == 1 && bean.getGoodsSpecList().get(0).getStock() == 0)) {
            tcOver.setVisibility(View.GONE);
            hideLayout.setVisibility(View.GONE);
            tvLimit.setVisibility(View.GONE);
            tvStock.setVisibility(View.GONE);
            tvSaleOver.setVisibility(View.VISIBLE);
            tvPrice.setTextColor(mActivity.getResources().getColor(R.color.color_9));
            tvRmb.setTextColor(mActivity.getResources().getColor(R.color.color_9));
        } else {
            tvPrice.setTextColor(mActivity.getResources().getColor(R.color.orange_text));
            tvRmb.setTextColor(mActivity.getResources().getColor(R.color.orange_text));
            tcOver.setVisibility(View.GONE);
            hideLayout.setVisibility(View.VISIBLE);
            tvSaleOver.setVisibility(View.GONE);
            if (bean.getStatus() == 1 && (entity.getStockType() == 1 && entity.getStock() != null && entity.getStock() > 0 && entity.getStock() < 10)) {
                tvStock.setVisibility(View.VISIBLE);
                tvStock.setText("仅剩" + entity.getStock() + "份");
            } else {
                tvStock.setVisibility(View.GONE);
            }
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    mActivity.startActivityForResult(intent, 1001);
                    mActivity.overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(mActivity, CommodityDetailActivity.class);
                    intent.putExtra("goods", bean);
                    mActivity.startActivityForResult(intent, 1001);
                }
            }

        });
    }

    public interface AddGoodsListener {
        void addGoods(final View v, int[] startLocation, final ViewGroup parent);

        void minusGoods();

        void toDetail();
    }
}
