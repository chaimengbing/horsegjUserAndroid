package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommodityDetailActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yuandi on 2016/10/11.
 */

public class SuperMarketGoodsListAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater mInflater;

    private ArrayList<Goods> list;

    private AddOrMinusGoodsListener listener;

    public SuperMarketGoodsListAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public ArrayList<Goods> getList() {
        return list;
    }

    public void setList(ArrayList<Goods> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(AddOrMinusGoodsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.super_market_goods_item, null);
            viewHolder.img = (CornerImageView) convertView.findViewById(R.id.goods_item_img);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.goods_item_tv_name);
            viewHolder.tvSpec = (TextView) convertView.findViewById(R.id.goods_item_tv_spec);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.goods_item_tv_price);
            viewHolder.tvOriginPrice = (TextView) convertView.findViewById(R.id.goods_item_tv_original_price);
            viewHolder.tvBuyCount = (TextView) convertView.findViewById(R.id.goods_item_tv_buy_count);
            viewHolder.tvSleep = (TextView) convertView.findViewById(R.id.merchant_sleep);
            viewHolder.imgAdd = (ImageView) convertView.findViewById(R.id.goods_item_img_add);
            viewHolder.imgMinus = (ImageView) convertView.findViewById(R.id.goods_item_img_minus);
            viewHolder.rlHideBuyCount = (RelativeLayout) convertView.findViewById(R.id.buy_count_hide);
            viewHolder.tvLimit = (TextView) convertView.findViewById(R.id.tv_limit);
            viewHolder.tvStock = (TextView) convertView.findViewById(R.id.tv_stock);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Rect rect = new Rect();
                    viewHolder.img.getGlobalVisibleRect(rect);
                    String url = list.get(position).getImgs();
                    Intent intent = new Intent(context, CommodityDetailActivity.class);
                    intent.putExtra("goods", list.get(position));
                    intent.putExtra(CommodityDetailActivity.IMAGE_ORIGIN_RECT, rect);
                    if (TextUtils.isEmpty(url)) {
                        intent.putExtra(CommodityDetailActivity.IMAGE_URL, "");
                    } else {
                        String[] strings = url.split(";");
                        intent.putExtra(CommodityDetailActivity.IMAGE_URL, strings[0]);
                    }
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(context, CommodityDetailActivity.class);
                    intent.putExtra("goods", list.get(position));
                    context.startActivity(intent);
                }
            }

        });
        showItem(list.get(position), viewHolder, position);

        return convertView;
    }

    private void showItem(final Goods goods, final ViewHolder holder, final int position) {
        if (CheckUtils.isNoEmptyStr(goods.getImgs())) {
            String[] strings = goods.getImgs().split(";");
            String imgUrl = strings[0];
            if (CheckUtils.isNoEmptyStr(imgUrl)) {
                ImageUtils.loadBitmap(context, imgUrl, holder.img, R.drawable.horsegj_default, Constants.getEndThumbnail(150, 150));
            } else {
                holder.img.setImageResource(R.drawable.horsegj_default);
            }
        } else {
            holder.img.setImageResource(R.drawable.horsegj_default);
        }
        if (goods.getName() != null) {
            holder.tvName.setText(goods.getName());
        } else {
            holder.tvName.setText("");
        }
        final GoodsSpec goodsSpec = goods.getGoodsSpecList().get(0);
        if (goodsSpec.getSpec() != null) {
            holder.tvSpec.setText(goodsSpec.getSpec());
        } else {
            holder.tvSpec.setText("默认");
        }
        holder.tvPrice.setText(StringUtils.BigDecimal2Str(goodsSpec.getPrice()));
        if (goodsSpec.getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
            holder.tvOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsSpec.getOriginalPrice()));
        } else {
            holder.tvOriginPrice.setText("");
        }
        if (goodsSpec.getOrderLimit() != null && goodsSpec.getOrderLimit() > 0) {
            holder.tvLimit.setVisibility(View.VISIBLE);
            holder.tvLimit.setText("每单限购" + goodsSpec.getOrderLimit() + "份");
        } else {
            holder.tvLimit.setVisibility(View.GONE);
        }
        if (goods.getStatus() == 0 || (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() == 0)) {
            holder.rlHideBuyCount.setVisibility(View.GONE);
            holder.tvSleep.setVisibility(View.VISIBLE);
            holder.tvLimit.setVisibility(View.GONE);
            holder.tvStock.setVisibility(View.GONE);
        } else if (goods.getStatus() == 1 && (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() > 0 && goodsSpec.getStock() < 10)) {
            holder.tvStock.setVisibility(View.VISIBLE);
            holder.tvStock.setText("仅剩" + goodsSpec.getStock() + "份");
            holder.rlHideBuyCount.setVisibility(View.VISIBLE);
            holder.tvSleep.setVisibility(View.GONE);
        } else {
            holder.tvStock.setVisibility(View.GONE);
            holder.rlHideBuyCount.setVisibility(View.VISIBLE);
            holder.tvSleep.setVisibility(View.GONE);
        }

        holder.tvBuyCount.setText(goodsSpec.getBuyCount() + "");
        if (goodsSpec.getBuyCount() > 0) {
            holder.imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
            holder.tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
        } else {
            holder.imgMinus.setTranslationX(0f);
            holder.tvBuyCount.setTranslationX(0f);
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = goodsSpec.getBuyCount();
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("购买的小伙伴太多，库存告急！", context);
                    return;
                }
                if (goodsSpec.getOrderLimit() != null && goodsSpec.getOrderLimit() > 0 && count >= goodsSpec.getOrderLimit()) {
                    ToastUtils.displayMsg(goods.getName() + "每单最多可购买" + goodsSpec.getOrderLimit() + "份喔！", context);
                    return;
                }
                if (count == 0) {
                    count++;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                    AnimatorUtils.leftTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                    AnimatorUtils.leftTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                } else {
                    if (count >= 99) {
                        ToastUtils.displayMsg("该商品已达到最大购买数量", context);
                        return;
                    }
                    count++;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                }
                if (listener != null) {
                    int[] startPoint = new int[2];
                    holder.img.getLocationInWindow(startPoint);
                    listener.add(startPoint, holder.img.getDrawable());
                    listener.addCart(goodsSpec.getGoodsId(), 1, position);
                }
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = goodsSpec.getBuyCount();
                if (count == 1) {
                    count--;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                    AnimatorUtils.rightTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                    AnimatorUtils.rightTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                } else {
                    if (count > 0) {
                        count--;
                        holder.tvBuyCount.setText(count + "");
                        goodsSpec.setBuyCount(count);
                    }
                }
                //更新购物车
                if (listener != null) listener.minusCart(goodsSpec.getGoodsId(), 1, position);
            }
        });
    }

    class ViewHolder {
        CornerImageView img;
        TextView tvName;
        TextView tvSpec;
        TextView tvPrice;
        TextView tvOriginPrice;
        ImageView imgAdd;
        TextView tvBuyCount;
        ImageView imgMinus;
        RelativeLayout rlHideBuyCount;
        TextView tvSleep;
        TextView tvLimit;
        TextView tvStock;
    }

    public interface AddOrMinusGoodsListener {
        void add(int[] point, Drawable drawable);

        void addCart(long goodsId, int count, int position);

        void minusCart(long goodsId, int count, int position);
    }
}
