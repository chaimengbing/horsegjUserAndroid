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
import com.project.mgjandroid.bean.DiscountedGoods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ning on 2016/3/9.
 */
public class BottomCartListAdapter extends BaseAdapter {
    private Context context;
    private List<PickGoods> products;
    private BottomCartListener listener;
    private boolean isFirst = true;
    private BigDecimal multiply;
    private BigDecimal decimal;
    private BigDecimal multiply1;
    private BigDecimal decimal1;
    private BigDecimal bigDecimal;

    public BottomCartListAdapter(Context context, List<PickGoods> mCartProducts, BottomCartListener listener) {
        this.context = context;
        this.products = mCartProducts;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return products.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bottom_cart, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        PickGoods product = products.get(i);
        String spec = "";
        for (int j = 0; j < product.getGoods().getGoodsSpecList().size(); j++) {
            if (product.getGoodsSpecId() == product.getGoods().getGoodsSpecList().get(j).getId()) {
                if (!TextUtils.isEmpty(product.getGoods().getGoodsSpecList().get(j).getSpec())) {
                    spec = " (" + product.getGoods().getGoodsSpecList().get(j).getSpec() + ")";
                }
                break;
            }
        }
        if (product.getGoodsName() != null) {
            holder.tv_name.setText(product.getGoods().getName() + spec + product.getGoodsName());
        } else {
            holder.tv_name.setText(product.getGoods().getName() + spec);
        }

        showBuyView(product, holder);
        return convertView;
    }

    private void showBuyView(final PickGoods product, final ViewHolder holder) {
        GoodsSpec myGoodsSpec = null;
        for (GoodsSpec spec : product.getGoods().getGoodsSpecList()) {
            if (spec.getId() == product.getGoodsSpecId()) {
                myGoodsSpec = spec;
            }
        }
        final GoodsSpec goodsSpec = myGoodsSpec;
        final int maxCount = goodsSpec.getOrderLimit();
        int pickCount = product.getPickCount();
        if (product.getGoods().getHasDiscount() == 1) {
            int everyGoodsEveryOrderBuyCount = product.getGoods().getEveryGoodsEveryOrderBuyCount();
            int surplusDiscountStock = product.getGoods().getSurplusDiscountStock();
            DiscountedGoods discountedGoods = product.getGoods().getDiscountedGoods();
            if (discountedGoods != null && discountedGoods.getMaxBuyNum() != null && discountedGoods.getMaxBuyNum() > 0 && discountedGoods.getSurplusBuyNum() != null && pickCount > discountedGoods.getSurplusBuyNum()) {
                multiply = goodsSpec.getPrice().multiply(new BigDecimal(discountedGoods.getSurplusBuyNum()));
                decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pickCount - discountedGoods.getSurplusBuyNum()));
                holder.tv_price.setText("" + multiply.add(decimal));
            } else if (everyGoodsEveryOrderBuyCount >= surplusDiscountStock) {
                if (pickCount >= surplusDiscountStock) {
                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(surplusDiscountStock));
                    decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pickCount - surplusDiscountStock));
                    holder.tv_price.setText("" + multiply.add(decimal));
                } else {
                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(pickCount));
                    holder.tv_price.setText("" + multiply);
                }
            } else {
                if (pickCount >= everyGoodsEveryOrderBuyCount) {
                    if (everyGoodsEveryOrderBuyCount > 0) {
                        multiply = goodsSpec.getPrice().multiply(new BigDecimal(everyGoodsEveryOrderBuyCount));
                        decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pickCount - everyGoodsEveryOrderBuyCount));
                        holder.tv_price.setText("" + multiply.add(decimal));
                    } else {
                        if (pickCount >= surplusDiscountStock) {
                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(surplusDiscountStock));
                            decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pickCount - surplusDiscountStock));
                            holder.tv_price.setText("" + multiply.add(decimal));
                        } else {
                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pickCount));
                            holder.tv_price.setText("" + multiply);
                        }
                    }

                } else {
                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(pickCount));
                    holder.tv_price.setText("" + multiply);
                }
            }
        } else {
            holder.tv_price.setText("" + goodsSpec.getPrice().multiply(new BigDecimal(pickCount)));
        }
        holder.tvBuyCount.setText(product.getPickCount() + "");
        if (product.getPickCount() > 0) {
            holder.imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
            holder.tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
        } else {
            holder.imgMinus.setTranslationX(0f);
            holder.tvBuyCount.setTranslationX(0f);
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
                if (product.getGoods().getHasDiscount() == 0) {
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
                }
                int minBuyCount = 0;
                if (product.getGoods().getHasDiscount() == 1) {
                    if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > product.getGoods().getSurplusDiscountStock()) {
                        if (count == product.getGoods().getSurplusDiscountStock()) {
                            if (goodsSpec.getMinOrderNum() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    minBuyCount = goodsSpec.getMinOrderNum() > goodsSpec.getStock() ? goodsSpec.getStock() : goodsSpec.getMinOrderNum();
                                } else {
                                    minBuyCount = goodsSpec.getMinOrderNum();
                                }
                                for (int i = 0; i < product.getGoods().getGoodsSpecList().size(); i++) {
                                    if (product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() != 0 && (count - product.getGoods().getSurplusDiscountStock()) <= product.getGoods().getGoodsSpecList().get(i).getMinOrderNum()) {
                                        ToastUtils.displayMsg(product.getGoods().getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                                        count = count + minBuyCount - 1;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (count == product.getGoods().getEveryGoodsEveryOrderBuyCount()) {
                            if (goodsSpec.getMinOrderNum() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    minBuyCount = goodsSpec.getMinOrderNum() > goodsSpec.getStock() ? goodsSpec.getStock() : goodsSpec.getMinOrderNum();
                                } else {
                                    minBuyCount = goodsSpec.getMinOrderNum();
                                }
                                for (int i = 0; i < product.getGoods().getGoodsSpecList().size(); i++) {
                                    if (product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() != 0 && (count - product.getGoods().getEveryGoodsEveryOrderBuyCount()) <= product.getGoods().getGoodsSpecList().get(i).getMinOrderNum()) {
                                        ToastUtils.displayMsg(product.getGoods().getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                                        count = count + minBuyCount - 1;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                if (count == 0) {
                    count++;
                    if (product.getGoods().getHasDiscount() == 1) {
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > product.getGoods().getSurplusDiscountStock()) {
                            if ((buyCount + 1) >= product.getGoods().getSurplusDiscountStock()) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                    if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                } else {
                                    if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                        return;
                                    }
                                    if (goodsSpec.getOrderLimit() == 0 && (buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                }
                            } else {
                                if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                    return;
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && ((buyCount + 1) - product.getGoods().getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                ToastUtils.displayMsg("该商品库存不足", context);
                                return;
                            }
                        } else {
                            if ((buyCount + 1) == product.getGoods().getEveryGoodsEveryOrderBuyCount() && product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + product.getGoods().getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                    if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                } else {
                                    if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                        return;
                                    }
                                    if (goodsSpec.getOrderLimit() == 0 && (buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                }
                            } else {
                                if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                    return;
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount()) > goodsSpec.getStock()) {
                                ToastUtils.displayMsg("该商品库存不足", context);
                                return;
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
                            if ((buyCount + 1) >= product.getGoods().getSurplusDiscountStock()) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                    if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                } else {
                                    if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                        return;
                                    }
                                    if (goodsSpec.getOrderLimit() == 0 && (buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", context);
                                        return;
                                    }
                                }
                            } else {
                                if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                    return;
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && ((buyCount + 1) - product.getGoods().getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                ToastUtils.displayMsg("该商品库存不足", context);
                                return;
                            }
                        } else {
                            if ((buyCount + 1) == product.getGoods().getEveryGoodsEveryOrderBuyCount() && product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (product.getGoods().isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + product.getGoods().getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", context);
                                    product.getGoods().setFirst(false);
                                }
                            }
                            if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", context);
                                            return;
                                        }
                                    } else {
                                        if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && (buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", context);
                                            return;
                                        }
                                    }
                                } else {
                                    if ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && ((buyCount + 1) - product.getGoods().getEveryGoodsEveryOrderBuyCount()) > goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", context);
                                    return;
                                }
                            } else {
                                if ((buyCount + 1) >= product.getGoods().getSurplusDiscountStock()) {
                                    if (product.getGoods().isFirst()) {
                                        ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", context);
                                        product.getGoods().setFirst(false);
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", context);
                                            return;
                                        }
                                    } else {
                                        if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && (buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", context);
                                            return;
                                        }
                                    }
                                } else {
                                    if ((buyCount + 1) - product.getGoods().getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", context);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && ((buyCount + 1) - product.getGoods().getSurplusDiscountStock()) > goodsSpec.getStock()) {
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
                if (product.getGoods().getHasDiscount() == 0) {
                    for (int i = 0; i < product.getGoods().getGoodsSpecList().size(); i++) {
                        if (count == product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() && product.getGoods().getGoodsSpecList().get(i).getId() == goodsSpec.getId()) {
                            if (product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= product.getGoods().getGoodsSpecList().get(i).getMinOrderNum()) {
                                ToastUtils.displayMsg(product.getGoods().getName() + "商品最少购买" + product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() + "份", context);
                            }
                            count = 1;
                            break;
                        }
                    }
                }
                if (count == 1) {
                    if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                            if (count <= product.getGoods().getEveryGoodsEveryOrderBuyCount()) {
                                product.getGoods().setFirst(true);
                            }
                        } else {
                            if (count <= product.getGoods().getSurplusDiscountStock()) {
                                product.getGoods().setFirst(true);
                            }
                        }
                    } else {
                        if (count <= product.getGoods().getSurplusDiscountStock()) {
                            product.getGoods().setFirst(true);
                        }
                    }
                    count--;
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
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                            if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (count <= product.getGoods().getEveryGoodsEveryOrderBuyCount()) {
                                    product.getGoods().setFirst(true);
                                }
                            } else {
                                if (count <= product.getGoods().getSurplusDiscountStock()) {
                                    product.getGoods().setFirst(true);
                                }
                            }
                        } else {
                            if (count <= product.getGoods().getSurplusDiscountStock()) {
                                product.getGoods().setFirst(true);
                            }
                        }
                        if (product.getGoods().getHasDiscount() == 1) {
                            if (product.getGoods().getEveryGoodsEveryOrderBuyCount() > product.getGoods().getSurplusDiscountStock()) {
                                if (goodsSpec.getMinOrderNum() > 0 && count - product.getGoods().getSurplusDiscountStock() == goodsSpec.getMinOrderNum()) {
                                    count = count - goodsSpec.getMinOrderNum() + 1;
                                }
                            } else {
                                if (goodsSpec.getMinOrderNum() > 0 && product.getGoods().getEveryGoodsEveryOrderBuyCount() > 0 && count - product.getGoods().getEveryGoodsEveryOrderBuyCount() == goodsSpec.getMinOrderNum()) {
                                    count = count - goodsSpec.getMinOrderNum() + 1;
                                }
                            }
                        }
                        count--;
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

    public void setData(List<PickGoods> data) {
        products = data;
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
