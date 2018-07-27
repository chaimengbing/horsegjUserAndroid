package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponGoods;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.EncodingUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/8.
 */

public class GroupBuyingUseAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private SparseArray<byte[]> byteArray = new SparseArray<>();
    private int imgWidth;
    private List<GroupPurchaseOrderCouponCode> codeList;
    private SimpleDateFormat format = new SimpleDateFormat("有效期至：yyyy.MM.dd");
    private List<GroupPurchaseOrderCouponGoods> goodsList;
    private String merchantName;

    public GroupBuyingUseAdapter(Context context, List<GroupPurchaseOrderCouponGoods> goodsList, String merchantName) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imgWidth = context.getResources().getDimensionPixelOffset(R.dimen.x140);
        codeList = new ArrayList<>();
        this.goodsList = goodsList;
        this.merchantName = merchantName;
    }

    public List<GroupPurchaseOrderCouponCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<GroupPurchaseOrderCouponCode> codeList) {
        if (codeList == null) {
            codeList = new ArrayList<>();
        }
        this.codeList = codeList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return codeList.size();
    }

    @Override
    public Object getItem(int position) {
        return codeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.group_buying_use_item, null);
            holder.titleLayout = (RelativeLayout) convertView.findViewById(R.id.title_layout);
            holder.imageView = (CornerImageView) convertView.findViewById(R.id.pic_code);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.taocan = (TextView) convertView.findViewById(R.id.text_taocan);
            holder.dishLayout = (LinearLayout) convertView.findViewById(R.id.dish_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final GroupPurchaseOrderCouponCode couponCode = codeList.get(position);
        if (couponCode.getGroupPurchaseCouponType() == 1) {
            holder.name.setText(merchantName + "代金券");
        } else {
            holder.name.setText(CheckUtils.isNoEmptyStr(couponCode.getGroupPurchaseName()) ? couponCode.getGroupPurchaseName() : (merchantName + "团购券"));
            if (CheckUtils.isNoEmptyList(getCodeList())) {
                holder.taocan.setVisibility(View.VISIBLE);
                holder.dishLayout.setVisibility(View.VISIBLE);
                holder.dishLayout.removeAllViews();
                for (int i = 0, size = goodsList.size(); i < size; i++) {
                    RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.group_buying_goods_item, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    TextView tvName = (TextView) layout.findViewById(R.id.name);
                    TextView tvCount = (TextView) layout.findViewById(R.id.count);
                    TextView tvPrice = (TextView) layout.findViewById(R.id.price);
                    tvName.setText(goodsList.get(i).getName());
                    tvCount.setText(goodsList.get(i).getQuantity() + "份");
                    tvPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsList.get(i).getOriginPrice().multiply(BigDecimal.valueOf(goodsList.get(i).getQuantity()))));
                    holder.dishLayout.addView(layout, layoutParams);
                    if (i != size - 1) {
                        View v = new View(context);
                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.color_e5));
                        holder.dishLayout.addView(v, p);
                    }
                }
            }
        }
        if (couponCode.getEndTime() != null){
            holder.time.setText(format.format(couponCode.getEndTime()));
        }
        holder.id.setText(getCode(couponCode.getCouponCode(), position + 1));
        if (byteArray.get(couponCode.getId().intValue()) == null) {
            MLog.e("---------->EncodingUtils.createQRCode");
            Bitmap bitmap = EncodingUtils.createQRCode(couponCode.getCouponCode(), imgWidth, imgWidth, null);
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                    byte[] bytes = baos.toByteArray();
                    MLog.e("---------->bytes.length" + bytes.length);
                    Glide.with(context).load(bytes).into(holder.imageView);
                    byteArray.put(couponCode.getId().intValue(), bytes);
                    baos.close();
                    baos = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bitmap.recycle();
                bitmap = null;
            }
        } else {
            Glide.with(context).load(byteArray.get(couponCode.getId().intValue())).into(holder.imageView);
        }
        holder.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupBuyingOrderForGoodsDetailsActivity.class);
                intent.putExtra("orderId", couponCode.getGroupPurchaseOrderId());
                context.startActivity(intent);
            }
        });
    }

    private String getCode(String couponCode, int sortId) {
        String str = "券码：";
        if (getCount() > 1) {
            str = "券码" + sortId + "：";
        }
        if (CheckUtils.isNoEmptyStr(couponCode)) {
            StringBuilder code = new StringBuilder(couponCode);
            for (int i = couponCode.length() / 4; i >= 0; i--) {
                code.insert(4 * i, " ");
            }
            str += code.toString().trim();
        }
        return str;
    }

    static class ViewHolder {
        RelativeLayout titleLayout;
        CornerImageView imageView;
        TextView name, time, id, taocan;
        LinearLayout dishLayout;
    }
}
