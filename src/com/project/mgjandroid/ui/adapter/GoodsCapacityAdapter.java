package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.GoodsDetailActivity;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsCapacityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Menu> menuList = new ArrayList<>(); //总的数据集合
    private BottomCartListener listener;
    private Merchant merchant;
    private CustomDialog dialog;
    private GoodsSpec mGoodsSpec;
    private ViewGroup parent;
    private Date serviceTime;

    private int tag;
    private NoticeDialog noticeDialog;
    private int selection = 0;
    private List<Goods> goodsList = new ArrayList<>();

    public static final int REFRESH = 1;
    public static final int LOAD_MORE = 2;
    private List<PickGoods> cartProducts;


    public GoodsCapacityAdapter(Context context, Merchant merchant) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.merchant = merchant;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setSelection(int selection) {
        if (this.selection != selection) {
            this.selection = selection;
            goodsList.clear();
            if (menuList.get(selection).getGoodsList() != null)
                goodsList.addAll(menuList.get(selection).getGoodsList());
        }
    }

    public int getSelection() {
        return selection;
    }

    /**
     * @param list
     * @param loadModel 加载类型  1:刷新  2:加载更多
     */
    public void setGoodsList(int position, List<Goods> list, int loadModel) {
        if (position != selection)
            selection = position;
        Menu menu = menuList.get(selection);
        if (loadModel == REFRESH) {
            menu.setGoodsList(list);
            goodsList.clear();
        } else {
            List<Goods> goodsList = menu.getGoodsList();
            if (goodsList == null) {
                goodsList = new ArrayList<>();
            }
            goodsList.addAll(list);
            menu.setGoodsList(goodsList);
        }
        goodsList.addAll(list);
        notifyDataSetChanged();
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    // 第一次设置数据
    public void setMenuList(List<Menu> menuList) {
        if (menuList != null) {
            this.menuList = menuList;
            this.selection = 0; // 第一次设置  默认第一项分类
            if (menuList.get(selection).getGoodsList().size() > 0) {
                goodsList.clear();
                goodsList.addAll(menuList.get(selection).getGoodsList());
                notifyDataSetChanged();
            }
        }
    }


    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Goods getItem(int i) {
        return goodsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ItemViewHolder holder;
        if (convertView == null) {
            holder = new ItemViewHolder();
            convertView = inflater.inflate(R.layout.goods_item, null);
            holder.img = (CornerImageView) convertView.findViewById(R.id.goods_item_img);
            holder.tvName = (TextView) convertView.findViewById(R.id.goods_item_tv_name);
            holder.tvDes = (TextView) convertView.findViewById(R.id.goods_item_tv_des);
            holder.barScore = (RatingBar) convertView.findViewById(R.id.goods_item_img_rat_score);
            holder.tvCommentCount = (TextView) convertView.findViewById(R.id.goods_item_tv_comment_count);
            holder.tvSellCount = (TextView) convertView.findViewById(R.id.goods_item_tv_sell_count);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.goods_item_tv_price);
            holder.tvOriginPrice = (TextView) convertView.findViewById(R.id.goods_item_tv_original_price);
            holder.tvLimit = (TextView) convertView.findViewById(R.id.goods_item_tv_limit);
            holder.tvMin = (TextView) convertView.findViewById(R.id.goods_item_tv_min);
            holder.tvStock = (TextView) convertView.findViewById(R.id.goods_item_tv_stock);
            holder.imgAdd = (RelativeLayout) convertView.findViewById(R.id.goods_item_img_add);
            holder.tvBuyCount = (TextView) convertView.findViewById(R.id.goods_item_tv_buy_count);
            holder.imgMinus = (RelativeLayout) convertView.findViewById(R.id.goods_item_img_minus);
            holder.specMinus = (RelativeLayout) convertView.findViewById(R.id.goods_item_img_minus_spec);
            holder.rlHideBuyCount = (RelativeLayout) convertView.findViewById(R.id.buy_count_hide);
            holder.tvChooseSpec = (TextView) convertView.findViewById(R.id.goods_item_choose_spec);
            holder.specCount = (TextView) convertView.findViewById(R.id.goods_item_tv_buy_count_spec);
            holder.tvSleep = (TextView) convertView.findViewById(R.id.merchant_sleep);
            holder.divideLine = convertView.findViewById(R.id.divider_line);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        showItem(position, convertView, holder);
        return convertView;
    }

    public void setListener(BottomCartListener listener) {
        this.listener = listener;
    }

    //设置购物车商品
    public void setCartProducts(List<PickGoods> cartProducts) {
        if (cartProducts != null) {
            this.cartProducts = cartProducts;
        }
    }

    static class ItemViewHolder {
        CornerImageView img;
        TextView tvName;
        TextView tvDes;
        RatingBar barScore;
        TextView tvCommentCount;
        TextView tvSellCount;
        TextView tvPrice;
        TextView tvOriginPrice;
        TextView tvStock;
        TextView tvLimit, tvMin;
        TextView tvBuyCount, specCount;
        RelativeLayout imgMinus, imgAdd, specMinus;
        RelativeLayout rlHideBuyCount;
        TextView tvChooseSpec, tvSleep;
        View divideLine;
    }

    private void showItem(int position, View convertView, final ItemViewHolder holder) {
        if (CheckUtils.isNoEmptyList(goodsList) && goodsList.size() > position) {
            holder.divideLine.setVisibility(View.GONE);
            final Goods goods = goodsList.get(position);
            if (goods != null) {
                if (CheckUtils.isNoEmptyStr(goods.getImgs())) {
                    String[] strings = goods.getImgs().split(";");
                    String imgUrl = strings[0];
                    holder.img.setImageResource(R.drawable.horsegj_default);
                    if (CheckUtils.isNoEmptyStr(imgUrl)) {
                        holder.img.setVisibility(View.VISIBLE);
                        ImageUtils.loadBitmap(context, imgUrl, holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL);
                    }
                } else {
                    holder.img.setImageResource(R.drawable.horsegj_default);
                }
                if (CheckUtils.isNoEmptyStr(goods.getName()))
                    holder.tvName.setText(goods.getName());
                if (CheckUtils.isNoEmptyStr(goods.getDescription())) {
                    holder.tvDes.setVisibility(View.VISIBLE);
                    holder.tvDes.setText(goods.getDescription());
                } else {
                    holder.tvDes.setVisibility(View.GONE);
                }
                if (CheckUtils.isNoEmptyList(goods.getGoodsSpecList())) {
                    holder.tvPrice.setText(StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(0).getPrice()));
                }
                if (goods.getCommentScore() != null)
                    holder.barScore.setRating(goods.getCommentScore().floatValue());
                if (goods.getMonthSaled() != 0) {
                    int praiseRate = (int) (goods.getCommentScore().floatValue() * 100 / 5.0);
                    holder.tvSellCount.setText("月售" + goods.getMonthSaled() + "份" + " " + "好评率" + praiseRate + "%");
                } else {
                    holder.tvSellCount.setText("月售" + goods.getMonthSaled() + "份");
                }
                if (goods.getCommentScore() != null)
                    holder.tvCommentCount.setText(goods.getCommentNum() + "评价");
                showBuyView(goods, holder);
                if (selection == goods.getId()) {
                    if (tag == 1) {
                        convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                    } else {
                        convertView.setBackgroundColor(context.getResources().getColor(R.color.goods_item));
                    }
                } else {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Rect rect = new Rect();
                            holder.img.getGlobalVisibleRect(rect);
                            String url = goods.getImgs();
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtra("goods", goods);
                            intent.putExtra("Merchant", merchant);
                            intent.putExtra(GoodsDetailActivity.IMAGE_ORIGIN_RECT, rect);
                            if (TextUtils.isEmpty(url)) {
                                intent.putExtra(GoodsDetailActivity.IMAGE_URL, "");
                            } else {
                                String[] strings = url.split(";");
                                intent.putExtra(GoodsDetailActivity.IMAGE_URL, strings[0]);
                            }
                            ((Activity) context).startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                            ((Activity) context).overridePendingTransition(0, 0);
                        } else {
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtra("goods", goods);
                            intent.putExtra("Merchant", merchant);
                            ((Activity) context).startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                            ((Activity) context).overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                        }
                    }
                });
            }
        }
    }

    private void showBuyView(final Goods goods, final ItemViewHolder holder) {
        if (serviceTime != null) {
            String currentTime = CommonUtils.formatTime(serviceTime.getTime(), "HH:mm");
            if (!DateUtils.isBusinessTime(currentTime, merchant.getWorkingTime())) {
                merchant.setShoppingTime(false);
            } else {
                merchant.setShoppingTime(true);
            }
        }
        if (merchant.getStatus() == 0 || !merchant.isShoppingTime()) {
            holder.tvSleep.setVisibility(View.VISIBLE);
            holder.rlHideBuyCount.setVisibility(View.GONE);
            holder.tvChooseSpec.setVisibility(View.GONE);
            holder.specCount.setVisibility(View.GONE);
            holder.specMinus.setVisibility(View.GONE);
            return;
        }

        if (goods.getStatus() == 0) {
            holder.tvSleep.setVisibility(View.VISIBLE);
            holder.tvSleep.setText("商品已售罄");
            holder.rlHideBuyCount.setVisibility(View.GONE);
            holder.tvChooseSpec.setVisibility(View.GONE);
            holder.specCount.setVisibility(View.GONE);
            holder.specMinus.setVisibility(View.GONE);
            holder.tvLimit.setVisibility(View.GONE);
            holder.tvMin.setVisibility(View.GONE);
        } else {
            holder.tvSleep.setVisibility(View.GONE);
            if (goods.getGoodsSpecList().size() == 1 && goods.getGoodsAttributeList().size() > 0) {
                boolean isOver = true;
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec spec = goods.getGoodsSpecList().get(i);
                    if (spec.getStockType() == 0 || (spec.getStockType() == 1 && spec.getStock() != 0)) {
                        isOver = false;
                        break;
                    }
                }
                if (isOver) {
                    holder.tvSleep.setVisibility(View.VISIBLE);
                    holder.tvSleep.setText("商品已售罄");
                    holder.rlHideBuyCount.setVisibility(View.GONE);
                    holder.tvChooseSpec.setVisibility(View.GONE);
                    holder.specCount.setVisibility(View.GONE);
                    holder.specMinus.setVisibility(View.GONE);
                    return;
                }
                holder.rlHideBuyCount.setVisibility(View.GONE);
                holder.tvChooseSpec.setVisibility(View.VISIBLE);
                holder.specCount.setVisibility(View.VISIBLE);
                holder.specMinus.setVisibility(View.VISIBLE);
                int num = 0;
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                        GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                        List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                        for (PickGoods pickGoods : pickGoodsList) {
                            if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                                if (goodsSpec1.getOrderLimit() != 0 && pickGoods.getPickCount() > goodsSpec1.getOrderLimit()) {
                                    pickGoods.setPickCount(goodsSpec1.getOrderLimit());
                                    goodsSpec1.setBuyCount(goodsSpec1.getOrderLimit());
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                }
                                num += pickGoods.getPickCount();
                                break;
                            }
                        }
                    }
                } else {
                    List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                    for (PickGoods pickGood : pickGoodsList) {
                        if (pickGood.getGoodsId() == goods.getId()) {
                            num += pickGood.getPickCount();
                        }
                    }
                }

                holder.specCount.setText(num + "");
                if (num > 0) {
                    holder.specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                    holder.specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                } else {
                    holder.specMinus.setTranslationX(0f);
                    holder.specCount.setTranslationX(0f);
                }

                holder.tvChooseSpec.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(goods);
                    }
                });
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                    if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                        price = goods.getGoodsSpecList().get(i).getPrice();
                    }
                }
                String str = StringUtils.BigDecimal2Str(price) + "起";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, context.getResources().getDimensionPixelSize(R.dimen.goods_section_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.tvPrice.setText(style);
                holder.tvOriginPrice.setVisibility(View.GONE);
                holder.tvStock.setVisibility(View.GONE);
                holder.tvLimit.setVisibility(View.GONE);
                holder.tvMin.setVisibility(View.GONE);

                holder.specMinus.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                            GoodsSpec goodsSpec1 = null;
                            int num = 0;
                            int specNum = 0;
                            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                                List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                                for (PickGoods pickGoods : pickGoodsList) {
                                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                        num += pickGoods.getPickCount();
                                        specNum += 1;
                                        goodsSpec1 = goodsSpec;
                                        break;
                                    }
                                }
                            }
                            if (specNum > 1) {
                                if (dialog == null) {
                                    dialog = new CustomDialog(context, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                                    dialog.show();
                                } else {
                                    dialog.show();
                                }
                            } else {
                                if (num == 1) {
                                    num--;
                                    holder.tvBuyCount.setText(num + "");
                                    goodsSpec1.setBuyCount(num);
                                    AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                                    AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                                    //只要点击了就去更新购物车
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                                } else {
                                    if (num > 0) {
                                        num--;
                                        goodsSpec1.setBuyCount(num);
                                        listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                    }
                                }

                            }
                        } else {
                            GoodsSpec goodsSpec1 = null;
                            int num = 0;
                            int specNum = 0;
                            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                                List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                                for (PickGoods pickGoods : pickGoodsList) {
                                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                        num += pickGoods.getPickCount();
                                        specNum += 1;
                                        goodsSpec1 = goodsSpec;
                                    }
                                }
                            }
                            if (specNum > 1) {
                                if (dialog == null) {
                                    dialog = new CustomDialog(context, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                                    dialog.show();
                                } else {
                                    dialog.show();
                                }
                            } else {
                                num = num == goodsSpec1.getMinOrderNum() ? 1 : num;
                                if (num == 1) {
                                    num--;
                                    holder.tvBuyCount.setText(num + "");
                                    goodsSpec1.setBuyCount(num);
                                    AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                                    AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                                    //只要点击了就去更新购物车
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                                } else {
                                    if (num > 0) {
                                        num--;
                                        goodsSpec1.setBuyCount(num);
                                        listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                    }
                                }
                            }
                        }
                    }
                });
            } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
                final GoodsSpec goodsSpec = goods.getGoodsSpecList().get(0);
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() == 0) {
                    holder.tvSleep.setVisibility(View.VISIBLE);
                    holder.tvSleep.setText("商品已售罄");
                    holder.rlHideBuyCount.setVisibility(View.GONE);
                    holder.tvChooseSpec.setVisibility(View.GONE);
                    holder.specCount.setVisibility(View.GONE);
                    holder.specMinus.setVisibility(View.GONE);
                    return;
                }
                holder.rlHideBuyCount.setVisibility(View.VISIBLE);
                holder.tvChooseSpec.setVisibility(View.GONE);
                holder.specCount.setVisibility(View.GONE);
                holder.specMinus.setVisibility(View.GONE);

                if (goodsSpec.getOriginalPrice() != null && goodsSpec.getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                    holder.tvOriginPrice.setVisibility(View.VISIBLE);
                    holder.tvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsSpec.getOriginalPrice()));
                } else {
                    holder.tvOriginPrice.setVisibility(View.GONE);
                }
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && 10 > goodsSpec.getStock()) {
                    holder.tvStock.setVisibility(View.VISIBLE);
                    holder.tvStock.setText("仅剩" + goodsSpec.getStock() + "份");
                } else {
                    holder.tvStock.setVisibility(View.GONE);
                }
                if (goodsSpec.getOrderLimit() != null && goodsSpec.getOrderLimit() > 0) {
                    holder.tvLimit.setVisibility(View.VISIBLE);
                    holder.tvLimit.setText("每单限购" + goodsSpec.getOrderLimit() + "份");
                } else {
                    holder.tvLimit.setVisibility(View.GONE);
                }
                if (goodsSpec.getMinOrderNum() != null && goodsSpec.getMinOrderNum() > 0) {
                    holder.tvMin.setVisibility(View.VISIBLE);
                    holder.tvMin.setText(goodsSpec.getMinOrderNum() + "份起购");
                } else {
                    holder.tvMin.setVisibility(View.GONE);
                }

                List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                        if (goodsSpec.getOrderLimit() != 0 && pickGoods.getPickCount() > goodsSpec.getOrderLimit()) {
                            goodsSpec.setBuyCount(goodsSpec.getOrderLimit());
                            listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                        } else {
                            goodsSpec.setBuyCount(pickGoods.getPickCount());
                        }
                        break;
                    }
                }
                holder.tvBuyCount.setText(goodsSpec.getBuyCount() + "");
                if (goodsSpec.getBuyCount() > 0) {
                    holder.imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                    holder.tvBuyCount.setTranslationX(-DipToPx.dip2px(context, 25));
                } else {
                    holder.imgMinus.setTranslationX(0f);
                    holder.tvBuyCount.setTranslationX(0f);
                }

                holder.imgAdd.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int count = goodsSpec.getBuyCount();
                        if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                            ToastUtils.displayMsg("该商品库存不足", context);
                            return;
                        }
                        if (count == 0) {
                            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                    ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                                    count = goods.getGoodsSpecList().get(i).getMinOrderNum() - 1;
                                    break;
                                }
                            }

                        }
                        if (goods.getGoodsSpecList().get(0).getOrderLimit() != 0 && count >= goods.getGoodsSpecList().get(0).getOrderLimit()) {
                            ToastUtils.displayMsg(goods.getName() + "商品限购" + goodsSpec.getOrderLimit() + "份", context);
                            return;
                        }
                        if (count == 0) {
                            count++;
                            goodsSpec.setBuyCount(count);
                            holder.tvBuyCount.setText(count + "");
                            AnimatorUtils.leftTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                            AnimatorUtils.leftTranslationRotating(holder.tvBuyCount, -DipToPx.dip2px(context, 25));
                        } else {
                            count++;
                            holder.tvBuyCount.setText(count + "");
                            goodsSpec.setBuyCount(count);
                        }
                        //只要点击了就去更新购物车
                        listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, true);

                        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        ImageView ball = new ImageView(context);// buyImg是动画的图片
                        ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                        ((CommercialActivity) context).setAnim(ball, startLocation, null);// 开始执行动画
                    }
                });

                holder.imgMinus.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int count = goodsSpec.getBuyCount();
                        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                            if (count == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                    ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                                }
                                count = 1;
                                break;
                            }
                        }
                        if (count == 1) {
                            count--;
                            goodsSpec.setBuyCount(count);
                            holder.tvBuyCount.setText(count + "");
                            AnimatorUtils.rightTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                            AnimatorUtils.rightTranslationRotating(holder.tvBuyCount, -DipToPx.dip2px(context, 25));
                            //只要点击了就去更新购物车

                            listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false);
                        } else {
                            if (count > 0) {
                                count--;
                                holder.tvBuyCount.setText(count + "");
                                goodsSpec.setBuyCount(count);
                                //只要点击了就去更新购物车
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                            }
                        }
                    }
                });
            } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1) {
                boolean isOver = true;
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec spec = goods.getGoodsSpecList().get(i);
                    if (spec.getStockType() == 0 || (spec.getStockType() == 1 && spec.getStock() != 0)) {
                        isOver = false;
                        break;
                    }
                }
                if (isOver) {
                    holder.tvSleep.setVisibility(View.VISIBLE);
                    holder.tvSleep.setText("商品已售罄");
                    holder.rlHideBuyCount.setVisibility(View.GONE);
                    holder.tvChooseSpec.setVisibility(View.GONE);
                    holder.specCount.setVisibility(View.GONE);
                    holder.specMinus.setVisibility(View.GONE);
                    return;
                }
                holder.rlHideBuyCount.setVisibility(View.GONE);
                holder.tvChooseSpec.setVisibility(View.VISIBLE);
                holder.specCount.setVisibility(View.VISIBLE);
                holder.specMinus.setVisibility(View.VISIBLE);
                int num = 0;
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                        GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                        List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                        for (PickGoods pickGoods : pickGoodsList) {
                            if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                                if (goodsSpec1.getOrderLimit() != 0 && pickGoods.getPickCount() > goodsSpec1.getOrderLimit()) {
                                    pickGoods.setPickCount(goodsSpec1.getOrderLimit());
                                    goodsSpec1.setBuyCount(goodsSpec1.getOrderLimit());
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                } else if (goodsSpec1.getOrderLimit() != 0 && pickGoods.getPickCount() < goodsSpec1.getMinOrderNum()) {
                                    pickGoods.setPickCount(goodsSpec1.getMinOrderNum());
                                    goodsSpec1.setBuyCount(goodsSpec1.getMinOrderNum());
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                }
                                num += pickGoods.getPickCount();
                                break;
                            }
                        }
                    }
                } else {
                    List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                    for (PickGoods pickGood : pickGoodsList) {
                        if (pickGood.getGoodsId() == goods.getId()) {
                            num += pickGood.getPickCount();
                        }
                    }
                }

                holder.specCount.setText(num + "");
                if (num > 0) {
                    holder.specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                    holder.specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                } else {
                    holder.specMinus.setTranslationX(0f);
                    holder.specCount.setTranslationX(0f);
                }

                holder.tvChooseSpec.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(goods);
                    }
                });
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                    if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                        price = goods.getGoodsSpecList().get(i).getPrice();
                    }
                }
                String str = StringUtils.BigDecimal2Str(price) + "起";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, context.getResources().getDimensionPixelSize(R.dimen.goods_section_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.tvPrice.setText(style);
                holder.tvOriginPrice.setVisibility(View.GONE);
                holder.tvStock.setVisibility(View.GONE);
                holder.tvLimit.setVisibility(View.GONE);
                holder.tvMin.setVisibility(View.GONE);

                holder.specMinus.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                            int count = goods.getGoodsSpecList().get(0).getBuyCount();
                            GoodsSpec goodsSpec1 = null;
                            int num = 0;
                            int specNum = 0;
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                    GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                                    List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                                    for (PickGoods pickGoods : pickGoodsList) {
                                        if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                            num += pickGoods.getPickCount();
                                            specNum += 1;
                                            goodsSpec1 = goodsSpec;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                    GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                                    List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                                    for (PickGoods pickGoods : pickGoodsList) {
                                        if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                            num += pickGoods.getPickCount();
                                            specNum += 1;
                                            goodsSpec1 = goodsSpec;
                                        }
                                    }
                                }
                            }
                            if (specNum > 1) {
                                if (dialog == null) {
                                    dialog = new CustomDialog(context, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                                    dialog.show();
                                } else {
                                    dialog.show();
                                }
                            } else {
                                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                    if (count == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                        if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                            ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goods.getGoodsSpecList().get(i).getMinOrderNum() + "份", context);
                                            holder.tvBuyCount.setText(num + "");
                                            goodsSpec1.setBuyCount(num);
                                            AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                                            AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                                            //只要点击了就去更新购物车
                                            listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                                            return;
                                        }
                                    }
                                }
                                if (num == 1) {
                                    num--;
                                    holder.tvBuyCount.setText(num + "");
                                    goodsSpec1.setBuyCount(num);
                                    AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                                    AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                                    //只要点击了就去更新购物车
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                                } else {
                                    if (num > 0) {
                                        num--;
                                        goodsSpec1.setBuyCount(num);
                                        listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                    }
                                }

                            }
                        } else {
                            GoodsSpec goodsSpec1 = null;
                            int num = 0;
                            int specNum = 0;
                            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                                List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
                                for (PickGoods pickGoods : pickGoodsList) {
                                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                        num += pickGoods.getPickCount();
                                        specNum += 1;
                                        goodsSpec1 = goodsSpec;
                                    }
                                }
                            }
                            if (specNum > 1) {
                                if (dialog == null) {
                                    dialog = new CustomDialog(context, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                                    dialog.show();
                                } else {
                                    dialog.show();
                                }
                            } else {
                                num = num == goodsSpec1.getMinOrderNum() ? 1 : num;
                                if (num == 1) {
                                    num--;
                                    holder.tvBuyCount.setText(num + "");
                                    goodsSpec1.setBuyCount(num);
                                    AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
                                    AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
                                    //只要点击了就去更新购物车
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                                } else {
                                    if (num > 0) {
                                        num--;
                                        goodsSpec1.setBuyCount(num);
                                        listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    CustomDialog.onBtnClickListener onBtnClickListener = new CustomDialog.onBtnClickListener() {
        @Override
        public void onSure() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onExit() {

        }
    };

    private void showDialog(final Goods goods) {
        mGoodsSpec = goods.getGoodsSpecList().get(0);
        final Dialog chooseSpecDialog = new Dialog(context, R.style.chooseSpecDialog);
        RelativeLayout contentView = (RelativeLayout) inflater.inflate(R.layout.goods_spec_choose_dialog, null);
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.layout_spec);
        TextView tvName = (TextView) contentView.findViewById(R.id.tv_goods_name);
        TextView tvSales = (TextView) contentView.findViewById(R.id.tv_goods_sales);
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        final TextView tvGoodsPrice = (TextView) contentView.findViewById(R.id.tv_goods_price);
        final TextView tvGoodsOriginPrice = (TextView) contentView.findViewById(R.id.tv_original_price);
        final TextView tvStock = (TextView) contentView.findViewById(R.id.tv_stock);
        final TextView tvLimit = (TextView) contentView.findViewById(R.id.tv_limit);
        final TextView tvMin = (TextView) contentView.findViewById(R.id.tv_min);
        final FlowLayout flGoodsSpec = (FlowLayout) contentView.findViewById(R.id.goods_spec_flow_layout);
        final LinearLayout btnLayout = (LinearLayout) contentView.findViewById(R.id.btn_layout);
        final ImageView ivMinus = (ImageView) contentView.findViewById(R.id.goods_img_minus);
        final TextView tvBuyCount = (TextView) contentView.findViewById(R.id.goods_tv_buy_count);
        final ImageView ivAdd = (ImageView) contentView.findViewById(R.id.goods_img_add);
        final Button btnConfirm = (Button) contentView.findViewById(R.id.btn_confirm_goods_spec);
        final TextView tvNoSale = (TextView) contentView.findViewById(R.id.tv_no_count);

        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
            for (PickGoods pickGood : pickGoods) {
                for (GoodsSpec goodsSpec : goods.getGoodsSpecList())
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        if (goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
                                goods.getCategoryId() == pickGood.getMenuId() &&
                                goods.getId() == pickGood.getGoodsId() &&
                                goodsSpec.getId() == pickGood.getGoodsSpecId()) {
                            goodsSpec.setBuyCount(pickGood.getPickCount());
                        }
                        if (mGoodsSpec.getBuyCount() > 0) {
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnLayout.setVisibility(View.VISIBLE);
                            tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        String sName = "";
                        for (int i = 0; i < goods.getGoodsAttributeList().size(); i++) {
                            sName += goods.getGoodsAttributeList().get(i).getName().split("\\|\\*\\|")[0] + ",";
                        }
                        String sName1 = "";
                        sName1 = sName.substring(0, sName.lastIndexOf(","));
                        if (goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
                                goods.getCategoryId() == pickGood.getMenuId() &&
                                goods.getId() == pickGood.getGoodsId() &&
                                goodsSpec.getId() == pickGood.getGoodsSpecId()) {
                            goodsSpec.setBuyCount(pickGood.getPickCount());
                            if (mGoodsSpec.getId() == pickGood.getGoodsSpecId() && sName1.equals(pickGood.getGoodsName())) {
                                btnConfirm.setVisibility(View.INVISIBLE);
                                btnLayout.setVisibility(View.VISIBLE);
                                tvBuyCount.setText(pickGood.getPickCount() + "");
                                //break;
                            }
//                            else {
//                                btnConfirm.setVisibility(View.VISIBLE);
//                                btnLayout.setVisibility(View.INVISIBLE);
//                            }
                        }
                        //break;
                    }
                //break;
            }
        }


        tvName.setText(goods.getName());
        tvSales.setText("已售" + goods.getTotalSaled() + "份");
        final List<View> viewList = new ArrayList<>();
        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, DipToPx.dip2px(context, 15), DipToPx.dip2px(context, 5));
            checkBox.setLayoutParams(layoutParams);
            if (i == 0) {
                checkBox.setChecked(true);
                if (CheckUtils.isNoEmptyStr(goods.getGoodsSpecList().get(i).getSpec())) {
                    checkBox.setText(goods.getGoodsSpecList().get(i).getSpec());
                } else {
                    checkBox.setText("默认");
                }
                checkBox.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
                tvGoodsPrice.setText(StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(i).getPrice()));
                if (goods.getGoodsSpecList().get(i).getOriginalPrice() != null && goods.getGoodsSpecList().get(i).getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                    tvGoodsOriginPrice.setVisibility(View.VISIBLE);
                    tvGoodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tvGoodsOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(i).getOriginalPrice()));
                } else {
                    tvGoodsOriginPrice.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getStockType() == 1 && goods.getGoodsSpecList().get(i).getStock() != null && goods.getGoodsSpecList().get(i).getStock() != 0 && 10 > goods.getGoodsSpecList().get(i).getStock()) {
                    tvStock.setVisibility(View.VISIBLE);
                    tvStock.setText("仅剩" + goods.getGoodsSpecList().get(i).getStock() + "件");
                } else {
                    tvStock.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getOrderLimit() != null && goods.getGoodsSpecList().get(i).getOrderLimit() > 0) {
                    tvLimit.setVisibility(View.VISIBLE);
                    tvLimit.setText("每单限购" + goods.getGoodsSpecList().get(i).getOrderLimit() + "份");
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != null && goods.getGoodsSpecList().get(i).getMinOrderNum() > 0) {
                    tvMin.setVisibility(View.VISIBLE);
                    tvMin.setText(goods.getGoodsSpecList().get(i).getMinOrderNum() + "份起购");
                } else {
                    tvMin.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getStockType() == 1 && goods.getGoodsSpecList().get(i).getStock() != null && goods.getGoodsSpecList().get(i).getStock() == 0) {
                    tvNoSale.setVisibility(View.VISIBLE);
                    btnConfirm.setVisibility(View.INVISIBLE);
                    btnLayout.setVisibility(View.INVISIBLE);
                } else {
                    tvNoSale.setVisibility(View.INVISIBLE);
                }
            }
            if (CheckUtils.isNoEmptyStr(goods.getGoodsSpecList().get(i).getSpec())) {
                checkBox.setText(goods.getGoodsSpecList().get(i).getSpec());
            } else {
                checkBox.setText("默认");
            }
            checkBox.setTag(goods.getGoodsSpecList().get(i));
            checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < viewList.size(); j++) {
                        ((CheckBox) viewList.get(j)).setTextColor(context.getResources().getColor(R.color.color_6));
                        ((CheckBox) viewList.get(j)).setChecked(false);
                    }
                    mGoodsSpec = (GoodsSpec) v.getTag();
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        if (mGoodsSpec.getBuyCount() > 0) {
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnLayout.setVisibility(View.VISIBLE);
                            tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        if (mGoodsSpec.getBuyCount() > 0) {
                            String sName = "";
                            for (int i = 0; i < goods.getSpecName().size(); i++) {
                                sName += goods.getSpecName().get(i) + ",";
                            }
                            sName = sName.substring(0, sName.lastIndexOf(","));
                            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                                for (PickGoods pickGoods : merchantPickGoods.getPickGoods()) {
                                    if (pickGoods.getGoodsSpecId() == mGoodsSpec.getId() && sName.equals(pickGoods.getGoodsName())) {
                                        btnConfirm.setVisibility(View.INVISIBLE);
                                        btnLayout.setVisibility(View.VISIBLE);
                                        tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                                        break;
                                    } else {
                                        btnConfirm.setVisibility(View.VISIBLE);
                                        btnLayout.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
//                        String sName = "";
//                        for (int i = 0; i < goods.getSpecName().size(); i++) {
//                            sName += goods.getSpecName().get(i) + ",";
//                        }
//                        String sName1 = "";
//                        sName1 = sName.substring(0, sName.lastIndexOf(","));
//                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
//                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
//                            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
//                            for (PickGoods pickGood : pickGoods) {
//                                if (mGoodsSpec.getBuyCount() > 0) {
//                                    if (sName1.equals(pickGood.getGoodsName()) && pickGood.getGoodsSpecId() == mGoodsSpec.getId()) {
//                                        btnConfirm.setVisibility(View.INVISIBLE);
//                                        btnLayout.setVisibility(View.VISIBLE);
//                                        tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
//                                    } else {
//                                        btnConfirm.setVisibility(View.VISIBLE);
//                                        btnLayout.setVisibility(View.INVISIBLE);
//                                    }
//                                } else {
//                                    btnConfirm.setVisibility(View.VISIBLE);
//                                    btnLayout.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        }
                    }
                    tvGoodsPrice.setText(StringUtils.BigDecimal2Str(mGoodsSpec.getPrice()));
                    if (mGoodsSpec.getOriginalPrice() != null && mGoodsSpec.getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                        tvGoodsOriginPrice.setVisibility(View.VISIBLE);
                        tvGoodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvGoodsOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(mGoodsSpec.getOriginalPrice()));
                    } else {
                        tvGoodsOriginPrice.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && 10 > mGoodsSpec.getStock()) {
                        tvStock.setVisibility(View.VISIBLE);
                        tvStock.setText("仅剩" + mGoodsSpec.getStock() + "件");
                    } else {
                        tvStock.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getOrderLimit() != null && mGoodsSpec.getOrderLimit() > 0) {
                        tvLimit.setVisibility(View.VISIBLE);
                        tvLimit.setText("每单限购" + mGoodsSpec.getOrderLimit() + "份");
                    } else {
                        tvLimit.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
                        tvMin.setVisibility(View.VISIBLE);
                        tvMin.setText(mGoodsSpec.getMinOrderNum() + "份起购");
                    } else {
                        tvMin.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() == 0) {
                        tvNoSale.setVisibility(View.VISIBLE);
                        btnConfirm.setVisibility(View.INVISIBLE);
                        btnLayout.setVisibility(View.INVISIBLE);
                    } else {
                        tvNoSale.setVisibility(View.INVISIBLE);
//                        btnConfirm.setVisibility(View.VISIBLE);
//                        btnLayout.setVisibility(View.INVISIBLE);
                    }
                    ((CheckBox) v).setTextColor(context.getResources().getColor(R.color.title_bar_bg));
                    ((CheckBox) v).setChecked(true);
                }
            });
            viewList.add(checkBox);
            flGoodsSpec.addView(checkBox);
        }
        final List<List<View>> viewList1 = new ArrayList<>();
        final List<String> specName = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(goods.getGoodsAttributeList()))
            for (int j = 0; j < goods.getGoodsAttributeList().size(); j++) {
                final List<View> list = new ArrayList<>();
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.spec_layout, null);
                TextView tvSauce = (TextView) linearLayout.findViewById(R.id.tv_goods_sauce);
                FlowLayout flGoodsSauce = (FlowLayout) linearLayout.findViewById(R.id.goods_sauce_flow_layout);
                specName.add(goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|")[0]);
                goods.setSpecName(specName);
                for (int a = 0; a < goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|").length; a++) {
                    CheckBox checkBox1 = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(0, 0, DipToPx.dip2px(context, 15), DipToPx.dip2px(context, 5));
                    checkBox1.setLayoutParams(layoutParams1);
                    if (a == 0) {
                        checkBox1.setChecked(true);
                        checkBox1.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
                    }
                    tvSauce.setText(goods.getGoodsAttributeList().get(j).getTitle());
//				specName.add(goods.getGoodsAttributeList().get(j).getName().split(",")[a]);
                    checkBox1.setText(goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|")[a]);

                    checkBox1.setTag(j + "," + a);
                    checkBox1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tag = (String) v.getTag();
                            String[] tags = tag.split(",");
                            for (int z = 0; z < viewList1.get(Integer.parseInt(tags[0])).size(); z++) {
                                ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(z)).setTextColor(context.getResources().getColor(R.color.color_6));
                                ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(z)).setChecked(false);
                            }
                            ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(Integer.parseInt(tags[1]))).setTextColor(context.getResources().getColor(R.color.title_bar_bg));
                            ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(Integer.parseInt(tags[1]))).setChecked(true);

                            specName.remove(Integer.parseInt(tags[0]));
                            specName.add(Integer.parseInt(tags[0]), goods.getGoodsAttributeList().get(Integer.parseInt(tags[0])).getName().split("\\|\\*\\|")[Integer.parseInt(tags[1])]);
                            goods.setSpecName(specName);
                            String sName = "";
                            for (int i = 0; i < specName.size(); i++) {
                                sName += specName.get(i) + ",";
                            }
                            String sName1 = "";
                            sName1 = sName.substring(0, sName.lastIndexOf(","));
                            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                                List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                                for (PickGoods pickGood : pickGoods) {
                                    if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId()) {
                                        if (sName1.equals(pickGood.getGoodsName())) {
                                            btnConfirm.setVisibility(View.INVISIBLE);
                                            btnLayout.setVisibility(View.VISIBLE);
                                            tvBuyCount.setText(pickGood.getPickCount() + "");
                                            break;
                                        } else {
                                            btnConfirm.setVisibility(View.VISIBLE);
                                            btnLayout.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    });

                    list.add(checkBox1);
                    flGoodsSauce.addView(checkBox1);
                }
                viewList1.add(list);
                layout.addView(linearLayout);
            }

        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() == 0) {
                    ToastUtils.displayMsg("该商品库存不足", context);
                    return;
                }
                if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0) {
                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getStock() < mGoodsSpec.getMinOrderNum()) {
                        ToastUtils.displayMsg("该商品库存不足", context);
                        return;
                    }
                }
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = 1;
                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
                        count = mGoodsSpec.getMinOrderNum();
                    }
                    mGoodsSpec.setBuyCount(count);
                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, false);
                    btnConfirm.setVisibility(View.INVISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);
                    tvBuyCount.setText(String.valueOf(count));
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    ivAdd.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(context);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    ((CommercialActivity) context).setAnim(ball, startLocation, parent);// 开始执行动画
                } else {
                    String sName = "";
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
//                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
//                        int buyCount = 0;
//                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
//                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
//                            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
//                            for (PickGoods pickGood : pickGoods) {
//                                if (pickGood.getGoodsId() == goods.getId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
//                                    buyCount += pickGood.getPickCount();
//                                }
//                            }
//                        }
//                        if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
//                            ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", context);
//                            return;
//                        }
//                        if (buyCount >= mGoodsSpec.getStock()) {
//                            ToastUtils.displayMsg("该商品库存不足", context);
//                            return;
//                        }
//                        listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getMinOrderNum(), false, false, sName1);
//                        tvBuyCount.setText(String.valueOf(mGoodsSpec.getMinOrderNum()));
//                    } else {
                    int buyCount = 0;
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
                            if (pickGood.getGoodsId() == goods.getId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
                                buyCount += pickGood.getPickCount();
                            }
                        }
                    }
                    if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
                        ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", context);
                        return;
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() > 0 && buyCount >= mGoodsSpec.getStock()) {
                        ToastUtils.displayMsg("该商品库存不足", context);
                        return;
                    }
                    listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), 1, false, false, sName1);
                    tvBuyCount.setText(String.valueOf(1));
//                    }

                    btnConfirm.setVisibility(View.INVISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);

                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    ivAdd.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(context);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    ((CommercialActivity) context).setAnim(ball, startLocation, parent);// 开始执行动画
                }

            }
        });

        ivAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = mGoodsSpec.getBuyCount();
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && count >= mGoodsSpec.getStock()) {
                        ToastUtils.displayMsg("该商品库存不足", context);
                        return;
                    }
                    if (mGoodsSpec.getOrderLimit() != 0 && count >= mGoodsSpec.getOrderLimit()) {
                        ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", context);
                        return;
                    }
                    count++;
                    tvBuyCount.setText(count + "");
                    mGoodsSpec.setBuyCount(count);
                    //只要点击了就去更新购物车
                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, true);
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(context);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    ((CommercialActivity) context).setAnim(ball, startLocation, parent);// 开始执行动画
                } else {
                    String sName = "";
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    int buyCount = 0;
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
                            if (goods.getId() == pickGood.getGoodsId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
                                buyCount += pickGood.getPickCount();
                            }
                        }
                        for (PickGoods pickGood : pickGoods) {
                            int count = pickGood.getPickCount();
                            if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId() && sName1.equals(pickGood.getGoodsName())) {
                                if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && buyCount >= mGoodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", context);
                                    return;
                                }
                                if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
                                    ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", context);
                                    return;
                                }
                                count++;
                                tvBuyCount.setText(count + "");
                                listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, true, sName1);
                            }
                        }
                    }
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(context);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    ((CommercialActivity) context).setAnim(ball, startLocation, parent);// 开始执行动画

                }
            }
        });

        ivMinus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = 0;
                    String sName = "";
                    long goodsSpecId = 0;
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
//                            for(int i=0;i<goods.getGoodsSpecList().size();i++){
                            if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId() && sName1.equals(pickGood.getGoodsName())) {
                                count = pickGood.getPickCount();
                                goodsSpecId = pickGood.getGoodsSpecId();
                            }
//                            }

                        }
                    }
//                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
//                        if (count == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
//                            if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
//                                ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", context);
//                            }
//                            count = 1;
//                            break;
//                        }
//                    }
                    if (count > 0) {
                        count--;
                        tvBuyCount.setText(count + "");
                        mGoodsSpec.setBuyCount(count);
                        if (count == 0) {
                            btnLayout.setVisibility(View.INVISIBLE);
                            btnConfirm.setVisibility(View.VISIBLE);
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false);
                            } else {
                                String sName2 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName2 += specName.get(i) + ",";
                                }
                                String sName3 = "";
                                sName3 = sName2.substring(0, sName2.lastIndexOf(","));
                                listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false, sName3);
                            }
                        } else {
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                //只要点击了就去更新购物车
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false);
                            } else {
                                String sName4 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName4 += specName.get(i) + ",";
                                }
                                String sName5 = "";
                                sName5 = sName4.substring(0, sName4.lastIndexOf(","));
                                listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false, sName5);
                            }

                        }
                    }
                } else {
                    int count = mGoodsSpec.getBuyCount();
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                            if (count == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                    ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", context);
                                }
                                count = 1;
                                break;
                            }
                        }
                    }
                    if (count > 0) {
                        count--;
                        tvBuyCount.setText(count + "");
                        mGoodsSpec.setBuyCount(count);
                        if (count == 0) {
                            btnLayout.setVisibility(View.INVISIBLE);
                            btnConfirm.setVisibility(View.VISIBLE);
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false);
                            } else {
                                String sName2 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName2 += specName.get(i) + ",";
                                }
                                String sName3 = "";
                                sName3 = sName2.substring(0, sName2.lastIndexOf(","));
                                listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false, sName3);
                            }
                        } else {
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                //只要点击了就去更新购物车
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false);
                            } else {
                                String sName4 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName4 += specName.get(i) + ",";
                                }
                                String sName5 = "";
                                sName5 = sName4.substring(0, sName4.lastIndexOf(","));
                                listener.newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false, sName5);
                            }

                        }
                    }
                }
            }
        });

        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseSpecDialog != null && chooseSpecDialog.isShowing())
                    chooseSpecDialog.dismiss();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        chooseSpecDialog.setContentView(contentView, layoutParams);
        chooseSpecDialog.show();
        parent = createAnimLayout(chooseSpecDialog);
    }

    private ViewGroup createAnimLayout(Dialog dialog) {
        ViewGroup rootView = (ViewGroup) dialog.getWindow().getDecorView();
        if (rootView == null) return null;
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout, rootView.getChildCount());
        return animLayout;
    }
}
