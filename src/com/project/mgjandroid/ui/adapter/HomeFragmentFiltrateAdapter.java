package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantFilterModel;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentFiltrateAdapter extends BaseAdapter {
    private List<MerchantFilterModel.ValueEntity.MerchantFeaturePropertyListEntity> mMerchantFeaturePropertyList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;

    public HomeFragmentFiltrateAdapter(Context context, List<MerchantFilterModel.ValueEntity.MerchantFeaturePropertyListEntity> merchantFeaturePropertyList ) {
        mMerchantFeaturePropertyList = merchantFeaturePropertyList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mMerchantFeaturePropertyList.size();
    }

    public Object getItem(int position) {
        return mMerchantFeaturePropertyList.get(position);
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
        viewTag.mName.setText(mMerchantFeaturePropertyList.get(position).getName());

        // set icon
        ImageUtils.loadBitmap(mContext, mMerchantFeaturePropertyList.get(position).getIcon(), viewTag.mIcon, 0, Constants.getEndThumbnail(10, 10));
        viewTag.mIcon.setVisibility(View.VISIBLE);
        if(mMerchantFeaturePropertyList.get(position).isCheck()){
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
