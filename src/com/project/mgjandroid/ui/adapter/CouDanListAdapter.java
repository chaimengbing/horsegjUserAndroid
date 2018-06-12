package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CouDanModel;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SunXueLiang on 2018/6/8.
 */
public class CouDanListAdapter extends BaseAdapter {
    private Context context;
    private List<PickGoods> products;
    private List<CouDanModel.ValueBean> couDanModelValue;
    private BottomCartListener listener;
    private PickGoods pickGoods;
    private int buy = 0;
    private PickGoods product;

    public CouDanListAdapter(Context context, List<CouDanModel.ValueBean> couDanModelValue, List<PickGoods> mCartProducts, BottomCartListener listener) {
        this.context = context;
        this.couDanModelValue = couDanModelValue;
        this.products = mCartProducts;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return couDanModelValue.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coudan, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        CouDanModel.ValueBean valueBean = couDanModelValue.get(i);

        if (valueBean.getTGoodsSpec() != null) {
            if (CheckUtils.isEmptyStr(valueBean.getTGoodsSpec().getSpec())) {
                holder.tv_name.setText(valueBean.getName());
            } else {
                holder.tv_name.setText(valueBean.getName() + "(" + valueBean.getTGoodsSpec().getSpec() + ")");
            }
        }

        showBuyView(valueBean, holder, products);

        return convertView;
    }

    private void showBuyView(final CouDanModel.ValueBean bean, final ViewHolder holder, final List<PickGoods> products) {
        final CouDanModel.ValueBean.TGoodsSpecBean tGoodsSpec = bean.getTGoodsSpec();
        GoodsSpec myGoodsSpec = null;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);
        }
        for (GoodsSpec spec : product.getGoods().getGoodsSpecList()) {
            if (spec.getId() == product.getGoodsSpecId()) {
                myGoodsSpec = spec;
            }
        }


        final GoodsSpec goodsSpec = myGoodsSpec;
        final int maxCount = goodsSpec.getOrderLimit();

        for (PickGoods pGoods : products) {
            if (bean.getId() == pGoods.getGoodsId() && tGoodsSpec.getId() == pGoods.getGoodsSpecId()) {
                buy = pGoods.getPickCount();
            }
        }

        holder.tvBuyCount.setText("" + buy);
        if (buy == 0) {
            holder.tv_price.setText("" + tGoodsSpec.getPrice());
        } else {
            holder.tv_price.setText("" + tGoodsSpec.getPrice().multiply(new BigDecimal(buy)));
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = product.getPickCount();
                int buyCount = 0;
                for (int i = 0; i < products.size(); i++) {
                    if (product.getGoodsId() == products.get(i).getGoods().getId() && product.getGoodsSpecId() == products.get(i).getGoodsSpecId()) {
                        buyCount += products.get(i).getPickCount();
                    }
                }
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && buyCount >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("该商品库存不足", context);
                    return;
                }
                if (maxCount != 0 && buyCount >= maxCount) {
                    String spec = "";
                    if (!TextUtils.isEmpty(goodsSpec.getSpec())) {
                        spec = " (" + goodsSpec.getSpec() + ")";
                    }
                    ToastUtils.displayMsg(product.getGoods().getName() + spec + "商品限购" + goodsSpec.getOrderLimit() + "份", context);
                    return;
                }

                if (count == 0) {
                    count++;
                    if (product.getGoods().getHasDiscount() == 1) {
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > product.getGoods().getSurplusDiscountStock()) {
                            if ((buyCount + 1) > product.getGoods().getSurplusDiscountStock()) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                        } else {
                            if ((buyCount + 1) > product.getGoods().getEveryGoodsEveryOrderBuyCount() && product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + product.getGoods().getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
//                            if ((buyCount + 1) > product.getGoods().getSurplusDiscountStock()) {
//                                ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
//                            }

                        }
                        if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                            if ((buyCount + 1) > (product.getGoods().getSurplusDiscountStock() + goodsSpec.getStock())) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", context);
                                    return;
                                }
                            }
                        }
                    }
                    product.setPickCount(count);
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                    AnimatorUtils.leftTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                    AnimatorUtils.leftTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                } else {
                    count++;
                    if (product.getGoods().getHasDiscount() == 1) {
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > product.getGoods().getSurplusDiscountStock()) {
                            if ((buyCount + 1) > product.getGoods().getSurplusDiscountStock()) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                        } else {
                            if ((buyCount + 1) > product.getGoods().getEveryGoodsEveryOrderBuyCount() && product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + product.getGoods().getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
//                            if ((buyCount + 1) > product.getGoods().getSurplusDiscountStock()) {
//                                ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
//                            }

                        }
                        if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                            if ((buyCount + 1) > (product.getGoods().getSurplusDiscountStock() + goodsSpec.getStock())) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", context);
                                    return;
                                }
                            }
                        }
                    }
                    holder.tvBuyCount.setText(count + "");
                    goodsSpec.setBuyCount(count);
                    product.setPickCount(count);
                }
                if (CheckUtils.isEmptyStr(product.getGoodsName())) {
                    //只要点击了就去更新购物车
                    listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                } else {
                    //只要点击了就去更新购物车
                    listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false, product.getGoodsName());
                }

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = product.getPickCount();
                for (int i = 0; i < product.getGoods().getGoodsSpecList().size(); i++) {
                    if (count == product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() && product.getGoods().getGoodsSpecList().get(i).getId() == goodsSpec.getId()) {
                        if (product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= product.getGoods().getGoodsSpecList().get(i).getMinOrderNum()) {
                            ToastUtils.displayMsg(product.getGoods().getName() + "商品最少购买" + product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() + "份", context);
                        }
                        count = 1;
                        break;
                    }
                }
                if (count == 1) {
                    count--;
                    if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                        if (count - product.getGoods().getEveryGoodsEveryOrderBuyCount() <= 0) {
                            product.getGoods().setFirst(true);
                        }
                    } else {
                        if (count - product.getGoods().getSurplusDiscountStock() <= 0) {
                            product.getGoods().setFirst(true);
                        }
                    }
                    product.setPickCount(count);
                    holder.tvBuyCount.setText(count + "");
                    if (CheckUtils.isEmptyList(product.getGoods().getGoodsAttributeList())) {
                        goodsSpec.setBuyCount(count);
//                    AnimatorUtils.rightTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
//                    AnimatorUtils.rightTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                        //只要点击了就去更新购物车
                        listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), true, false);
                    } else {
                        listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), true, false, product.getGoodsName());
                    }

                } else {
                    if (count > 0) {
                        count--;
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                            if (count - product.getGoods().getEveryGoodsEveryOrderBuyCount() <= 0) {
                                product.getGoods().setFirst(true);
                            }
                        } else {
                            if (count - product.getGoods().getSurplusDiscountStock() <= 0) {
                                product.getGoods().setFirst(true);
                            }
                        }
                        holder.tvBuyCount.setText(count + "");
                        product.setPickCount(count);
                        if (CheckUtils.isEmptyList(product.getGoods().getGoodsAttributeList())) {
                            goodsSpec.setBuyCount(count);
                            //只要点击了就去更新购物车
                            listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                        } else {
                            listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), false, false, product.getGoodsName());
                        }
                    }
                }
            }
        });
    }

    public void setData(List<CouDanModel.ValueBean> data) {
        couDanModelValue = data;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tv_name;
        TextView tv_price;
        ImageView imgAdd;
        TextView tvBuyCount;
        ImageView imgMinus;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.product_name);
            tv_price = (TextView) view.findViewById(R.id.goods_item_tv_price);
            imgAdd = (ImageView) view.findViewById(R.id.goods_item_img_add);
            tvBuyCount = (TextView) view.findViewById(R.id.goods_item_tv_buy_count);
            imgMinus = (ImageView) view.findViewById(R.id.goods_item_img_minus);
        }
    }
}
