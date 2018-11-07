package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryPublicity;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class RecommendGridAdapter extends RecyclerView.Adapter {

    private List<GroupPurchasePrimaryPublicity> publicityList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(GroupPurchasePrimaryPublicity purchasePrimaryPublicity);
    }

    public RecommendGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<GroupPurchasePrimaryPublicity> publicityList) {
        if (publicityList == null) {
            this.publicityList = new ArrayList<>();
        } else {
            this.publicityList = publicityList;
        }
        notifyDataSetChanged();
    }

    @Override
    public ReCommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReCommendViewHolder(inflater.inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GroupPurchasePrimaryPublicity bean = publicityList.get(position);
        ReCommendViewHolder viewHolder = (ReCommendViewHolder) holder;
        viewHolder.localImageView.setBackground(bean.getResSource());
        ImageUtils.loadBitmap(mContext, bean.getImg(), viewHolder.recommendImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(355, 105));
        viewHolder.tvName.setText(CheckUtils.isNoEmptyStr(bean.getGroupPurchaseMerchantName()) ? bean.getGroupPurchaseMerchantName() : "");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(publicityList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (publicityList.size() == 1 || publicityList.size() == 3) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return publicityList.size();
    }

    private class ReCommendViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView recommendImageView;
        ImageView localImageView;

        public ReCommendViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.recommend_name);
            recommendImageView = (ImageView) itemView.findViewById(R.id.recommend_imageview);
            localImageView = (ImageView) itemView.findViewById(R.id.local_imageview);
        }
    }
}
