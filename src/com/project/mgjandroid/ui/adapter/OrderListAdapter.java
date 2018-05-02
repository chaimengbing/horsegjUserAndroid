package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBuyOrder;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.OrderFlowStatus;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.NewOrderFragmentModel.ValueEntity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderListAdapter extends BaseAdapter {
    private ArrayList<NewOrderFragmentModel.ValueEntity> list;
    private Context context;
    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private SimpleDateFormat sdf;

    private static final int TYPE_OTHER = 0;
    private static final int TYPE_GROUP = 1;

    public OrderListAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
        this.listener = listener;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public ArrayList<NewOrderFragmentModel.ValueEntity> getList() {
        return list;
    }

    public void setList(ArrayList<NewOrderFragmentModel.ValueEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
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
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 2)
            return TYPE_GROUP;
        return TYPE_OTHER;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ViewHolderGroup groupHolder;
        int type = getItemViewType(position);
        if (type == TYPE_OTHER) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.order_list_item_new, null);
                holder.tvOrderState = (TextView) convertView.findViewById(R.id.order_list_item_tv_state);
//                holder.tvOrderTime = (TextView) convertView.findViewById(R.id.order_list_item_tv_time);
                holder.tvOrderName = (TextView) convertView.findViewById(R.id.order_list_item_tv_name);
//                holder.imgArrow = (ImageView) convertView.findViewById(R.id.order_list_right_arrow);
                holder.tvOrderMoney = (TextView) convertView.findViewById(R.id.order_list_item_tv_money);
                holder.tvTotalCount = (TextView) convertView.findViewById(R.id.order_list_item_tv_count);
//                holder.imgDelete = (ImageView) convertView.findViewById(R.id.order_list_item_img_delet);
                holder.img = (RoundImageView) convertView.findViewById(R.id.order_list_item_img);
                holder.llImg = (LinearLayout) convertView.findViewById(R.id.order_list_item_img_father);
                holder.goods = (LinearLayout) convertView.findViewById(R.id.goods_layout);
                holder.tvMoreOrder = (TextView) convertView.findViewById(R.id.order_state_more_one);
                holder.tvTakePhoto = (TextView) convertView.findViewById(R.id.order_state_take_photo);
                holder.tvEvaluate = (TextView) convertView.findViewById(R.id.order_state_evaluate);
                holder.tvConfirm = (TextView) convertView.findViewById(R.id.order_state_confirm);
                holder.tvPay = (TimeTextView) convertView.findViewById(R.id.order_state_go_pay);
//                holder.rlGODetail = (RelativeLayout) convertView.findViewById(R.id.go_to_detail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvMoreOrder.setTag(position);
            holder.tvTakePhoto.setTag(position);
            holder.tvEvaluate.setTag(position);
            holder.tvConfirm.setTag(position);
            holder.tvPay.setTag(position);
//            holder.imgDelete.setTag(position);
            holder.tvOrderName.setTag(position);
//            holder.imgArrow.setTag(position);

            holder.llImg.setTag(position);
            holder.llImg.setOnClickListener(listener);
//            holder.rlGODetail.setTag(position);
//            holder.rlGODetail.setOnClickListener(listener);
            holder.tvConfirm.setOnClickListener(listener);
            holder.tvOrderName.setOnClickListener(listener);
//            holder.imgArrow.setOnClickListener(listener);
            if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
                showItem(list.get(position), holder);
            }
        } else {
            if (convertView == null) {
                groupHolder = new ViewHolderGroup();
                convertView = mInflater.inflate(R.layout.item_order_group, null);
                groupHolder.userName = (TextView) convertView.findViewById(R.id.group_order_name);
                groupHolder.groupStatus = (TextView) convertView.findViewById(R.id.group_order_status);
                groupHolder.tvTitle = (TextView) convertView.findViewById(R.id.group_order_title);
                groupHolder.tvPrice = (TextView) convertView.findViewById(R.id.group_order_price);
                groupHolder.tvTotalPrice = (TextView) convertView.findViewById(R.id.group_order_total_price);
                groupHolder.tvTotalCount = (TextView) convertView.findViewById(R.id.group_order_count);
                groupHolder.tvPay = (TimeTextView) convertView.findViewById(R.id.order_state_go_pay);
                groupHolder.tvInvite = (TextView) convertView.findViewById(R.id.to_invite_friend);
                groupHolder.tvEvaluate = (TextView) convertView.findViewById(R.id.to_evaluate_group);
                groupHolder.userImg = (RoundImageView) convertView.findViewById(R.id.group_order_avatar);
                groupHolder.goodsImg = (CornerImageView) convertView.findViewById(R.id.group_order_goods_image);
                groupHolder.tvUser = (LinearLayout) convertView.findViewById(R.id.my_group_to_user);
                groupHolder.tvGroup = (FrameLayout) convertView.findViewById(R.id.my_group_to_group);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (ViewHolderGroup) convertView.getTag();
            }
            groupHolder.tvPay.setTag(position);
            groupHolder.tvPay.setOnClickListener(listener);
            groupHolder.tvEvaluate.setTag(position);
            groupHolder.tvEvaluate.setOnClickListener(listener);
            groupHolder.tvUser.setTag(position);
            groupHolder.tvUser.setOnClickListener(listener);
            groupHolder.tvGroup.setTag(position);
            groupHolder.tvGroup.setOnClickListener(listener);
            groupHolder.tvInvite.setTag(position);
            groupHolder.tvInvite.setOnClickListener(listener);
            if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
                showGroupItem(list.get(position), groupHolder);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvOrderState, tvOrderTime, tvOrderName, tvOrderMoney, tvTotalCount;
        TextView tvMoreOrder, tvTakePhoto, tvEvaluate, tvConfirm;
        TimeTextView tvPay;
        ImageView imgDelete, imgArrow;
        RelativeLayout rlGODetail;
        LinearLayout llImg;
        LinearLayout goods;
        RoundImageView img;
    }

    class ViewHolderGroup {
        TextView userName, groupStatus, tvTitle, tvPrice, tvTotalPrice, tvTotalCount;
        TextView tvInvite, tvEvaluate;
        LinearLayout tvUser;
        FrameLayout tvGroup;
        TimeTextView tvPay;
        RoundImageView userImg;
        CornerImageView goodsImg;
    }

    private void showGroupItem(ValueEntity valueEntity, ViewHolderGroup groupHolder) {
        if (valueEntity != null) {
            GroupBuyOrder groupBuyOrder = valueEntity.getGroupbuyOrder();
            if (CheckUtils.isNoEmptyStr(groupBuyOrder.getGroupBuy().getGroupBuyUser().getHeaderImg())) {
                ImageUtils.loadBitmap(context, groupBuyOrder.getGroupBuy().getGroupBuyUser().getHeaderImg(), groupHolder.userImg, R.drawable.user_avatar, Constants.getEndThumbnail(42, 42));
            } else {
                groupHolder.userImg.setImageResource(R.drawable.user_avatar);
            }
            if (CheckUtils.isNoEmptyStr(groupBuyOrder.getGroupBuy().getImgs())) {
                ImageUtils.loadBitmap(context, groupBuyOrder.getGroupBuy().getImgs().split(";")[0], groupHolder.goodsImg, R.drawable.horsegj_default, Constants.getEndThumbnail(75, 75));
            }
            Integer status = groupBuyOrder.getStatus();
            if (status != null) {
                switch (status) {
                    case -1:
                        groupHolder.groupStatus.setText("取消订单");
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        break;
                    case 0:
                        groupHolder.groupStatus.setText("订单创建");
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        break;
                    case 1:
                        groupHolder.groupStatus.setText("等待付款");
                        groupHolder.tvPay.setVisibility(View.VISIBLE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        groupHolder.tvPay.setTimes(getTimeBetween1(new Date(), groupBuyOrder.getPaymentExpireTime()));
                        break;
                    case 2:
                        groupHolder.groupStatus.setText("已支付,未成团");
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        groupHolder.groupStatus.setText("待发货");
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        break;
                    case 4:
                        if (groupBuyOrder.getHasComments() == 0) {
                            groupHolder.groupStatus.setText("交易成功");
                        } else {
                            groupHolder.groupStatus.setText("已完成");
                        }
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        break;
                    case 5:
                        groupHolder.groupStatus.setText("未成团,退款成功");
                        groupHolder.tvPay.setVisibility(View.GONE);
                        groupHolder.tvInvite.setVisibility(View.GONE);
                        break;
                }
            } else {
                groupHolder.groupStatus.setVisibility(View.GONE);
            }
            groupHolder.userName.setText(groupBuyOrder.getGroupBuy().getGroupBuyUser().getName());
            groupHolder.tvTitle.setText(groupBuyOrder.getGroupBuy().getGoodsName());
            groupHolder.tvPrice.setText(groupBuyOrder.getPrice() + "");
            groupHolder.tvTotalPrice.setText(groupBuyOrder.getTotalPrice() + "");
            groupHolder.tvTotalCount.setText("共" + groupBuyOrder.getQuantity() + "件商品  合计：");

            if (groupBuyOrder.getStatus() == 4 && groupBuyOrder.getHasComments() == 0) {
                groupHolder.tvEvaluate.setVisibility(View.VISIBLE);
            } else {
                groupHolder.tvEvaluate.setVisibility(View.GONE);
            }
        }
    }

    private void showItem(ValueEntity valueEntity, ViewHolder holder) {
        if (valueEntity != null) {
//            int status = valueEntity.getOrderFlowStatus();
//            if(status == 1){
//                holder.tvOrderState.setTextColor(Color.parseColor("#ffff6633"));
//            }else if(status == -1 || status == 7){
//                holder.tvOrderState.setTextColor(Color.parseColor("#ff333333"));
//            }else{
//                holder.tvOrderState.setTextColor(Color.parseColor("#ffff9a00"));
//            }
            holder.tvOrderState.setText(OrderFlowStatus.getOrderStatusByValue(valueEntity.getOrderFlowStatus()).getMemo());
//            holder.tvOrderTime.setText("下单时间：" + valueEntity.getCreateTime());
            holder.img.setImageResource(R.drawable.horsegj_default);
            if (valueEntity.getMerchantId() > 0) {
                holder.tvOrderName.setText(valueEntity.getMerchant().getName());
                ImageUtils.loadBitmap(context, valueEntity.getMerchant().getLogo(), holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            }
            holder.tvOrderMoney.setText(StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
//            holder.imgDelete.setOnClickListener(listener);
            holder.tvMoreOrder.setOnClickListener(listener);
            holder.tvPay.setOnClickListener(listener);
            holder.tvEvaluate.setOnClickListener(listener);
            switch (valueEntity.getOrderFlowStatus()) {
                case -1://取消
                    changeButtonShowState(holder, true, false, false, false, false);
                    break;
                case 0://已创建
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 1://等待付款
                    changeButtonShowState(holder, false, false, false, false, true);
                    break;
                case 2://等待商家确认
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 3://商家已接单
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 4://配送员取货中
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 5://配送员已取货
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 6://等待送达
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 7://完成
                    if (valueEntity.getHasComments() == 0)
                        changeButtonShowState(holder, true, true, true, false, false);
                    else
                        changeButtonShowState(holder, true, true, false, false, false);
                    break;
            }
            String paymentExpireTime = valueEntity.getPaymentExpireTime();
            if (paymentExpireTime != null) {
                holder.tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
            } else {
                holder.tvPay.setVisibility(View.GONE);
            }
            holder.goods.removeAllViews();
            if (CheckUtils.isNoEmptyList(valueEntity.getOrderItems())) {
                int totalCount = 0;
                for (ValueEntity.OrderItemsEntity entity : valueEntity.getOrderItems()) {
                    View view = mInflater.inflate(R.layout.view_order_goods, null);
                    TextView name = (TextView) view.findViewById(R.id.order_goods_name);
                    TextView count = (TextView) view.findViewById(R.id.order_goods_count);
                    if (CheckUtils.isNoEmptyStr(entity.getSpec())) {
                        name.setText(entity.getName() + "(" + entity.getSpec() + ")");
                    } else {
                        name.setText(entity.getName());
                    }
                    totalCount += entity.getQuantity();
                    count.setText("x" + entity.getQuantity());
                    holder.goods.addView(view);
                }
                holder.tvTotalCount.setText("共" + totalCount + "件商品  合计：");
            }
            if (valueEntity.getMerchant().getType() == 1) {
                holder.tvMoreOrder.setVisibility(View.GONE);
            } else {
                holder.tvMoreOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeButtonShowState(ViewHolder holder, boolean tvMoreOrderShow,
                                       boolean tvTakePhotoShow, boolean tvEvaluateShow, boolean tvConfirmShow, boolean tvPayShow) {
        if (tvMoreOrderShow) {
            holder.tvMoreOrder.setVisibility(View.VISIBLE);
        } else {
            holder.tvMoreOrder.setVisibility(View.GONE);
        }
        if (tvTakePhotoShow) {
            holder.tvTakePhoto.setVisibility(View.GONE);
        } else {
            holder.tvTakePhoto.setVisibility(View.GONE);
        }
        if (tvEvaluateShow) {
            holder.tvEvaluate.setVisibility(View.VISIBLE);
        } else {
            holder.tvEvaluate.setVisibility(View.GONE);
        }
        if (tvConfirmShow) {
            holder.tvConfirm.setVisibility(View.VISIBLE);
        } else {
            holder.tvConfirm.setVisibility(View.GONE);
        }
        if (tvPayShow) {
            holder.tvPay.setVisibility(View.VISIBLE);
        } else {
            holder.tvPay.setVisibility(View.GONE);
        }
    }

    private long getTimeBetween(String serverTime, String laterTime) {
        if (CheckUtils.isEmptyStr(serverTime) || CheckUtils.isEmptyStr(laterTime)) {
            return 0;
        }
        try {
            Date date1 = sdf.parse(serverTime);
            long time1 = date1.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long getTimeBetween1(Date serverTime, String laterTime) {
        if (CheckUtils.isEmptyStr(laterTime)) {
            return 0;
        }
        try {
            long time1 = serverTime.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
