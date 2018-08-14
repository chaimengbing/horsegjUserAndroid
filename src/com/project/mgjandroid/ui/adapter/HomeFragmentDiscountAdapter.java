package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantFilterModel;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentDiscountAdapter extends BaseAdapter{
    private List<MerchantFilterModel.ValueEntity.PromotionListEntity> mPromotionList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;

    public HomeFragmentDiscountAdapter(Context context, List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList) {
        mPromotionList = promotionList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mPromotionList.size();
    }

    public Object getItem(int position) {
        return mPromotionList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filtrate_item, null);

            // construct an item tag
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.image), (TextView) convertView.findViewById(R.id.text_view),(LinearLayout)convertView.findViewById(R.id.layout));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mPromotionList.get(position).getName());

        // set icon
        viewTag.mIcon.setVisibility(View.GONE);
        if(mPromotionList.get(position).isCheck()){
            viewTag.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.filtrate_select));
            viewTag.mName.setTextColor(mContext.getResources().getColor(R.color.bg_festival));
        }else {
            viewTag.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.filtrate_unselect));
            viewTag.mName.setTextColor(mContext.getResources().getColor(R.color.filtrate_text));
        }
        return convertView;
    }

    class ItemViewTag {
        protected ImageView mIcon;
        protected TextView mName;
        protected LinearLayout mLayout;

        public ItemViewTag(ImageView icon, TextView name,LinearLayout layout) {
            this.mName = name;
            this.mIcon = icon;
            this.mLayout = layout;
        }
    }

}
