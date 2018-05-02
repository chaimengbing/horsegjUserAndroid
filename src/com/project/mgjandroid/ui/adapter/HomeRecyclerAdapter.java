package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.NewQualityMerchantsModel;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import static com.project.mgjandroid.R.string.goods;

/**
 * Created by SunXueLiang on 2017-09-01.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater inflate;

    private List<NewQualityMerchantsModel.ValueBean> data = new ArrayList<>();
    private View.OnClickListener listener;

    public HomeRecyclerAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    public List<NewQualityMerchantsModel.ValueBean> getData() {
        return data;
    }

    public void setData(List<NewQualityMerchantsModel.ValueBean> data) {
        if (data == null) data = new ArrayList<>();
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new HomeRecyclerAdapter.ViewHolder(inflate.inflate(R.layout.item_goods_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DipToPx.dip2px(context, 12), 0, DipToPx.dip2px(context, 7), 0);
            holder.root.setLayoutParams(layoutParams);
        } else if (position == getItemCount() - 1) {
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, DipToPx.dip2px(context, 12), 0);
            holder.root.setLayoutParams(layoutParams);
        } else {
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, DipToPx.dip2px(context, 8), 0);
            holder.root.setLayoutParams(layoutParams);
        }
        if (data == null) return;
//        BitmapUtil.loadBitmap(context, R.mipmap.glide_placeholder, holder.image);
        if (!TextUtils.isEmpty(data.get(position).getImg())) {
            ImageUtils.loadBitmap(context, data.get(position).getImg().split(";")[0], holder.image, R.drawable.horsegj_default, Constants.getEndThumbnail(125, 108));
        }
        holder.tvName.setText(data.get(position).getMerchantName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getMerchantType() == 0) {
                    Routers.open(context, ActivitySchemeManager.SCHEME + "merchant/" + data.get(position).getMerchantId());
                } else if (data.get(position).getMerchantType() == 2) {
                    Routers.open(context, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + data.get(position).getMerchantId());
                }
            }
        });
        holder.imageLayout.setTag(position);
        holder.imageLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout root;
        public FrameLayout imageLayout;
        public CornerImageView image;
        public TextView tvName;

        public ViewHolder(View view) {
            super(view);
            root = (FrameLayout) view.findViewById(R.id.root);
            imageLayout = (FrameLayout) view.findViewById(R.id.item_goods_layout);
            image = (CornerImageView) view.findViewById(R.id.item_goods_img);
            tvName = (TextView) view.findViewById(R.id.item_goods_name);
        }
    }
}
