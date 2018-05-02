package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Context;
import android.graphics.Bitmap;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.base.BaseViewHolder;
import com.project.mgjandroid.bean.AddImage;
import com.project.mgjandroid.utils.BitmapUtil;

public class AddGoodsRecyclerAdapter extends BaseRecyclerAdapter<AddImage> {

    public AddGoodsRecyclerAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void newBindViewHolder(BaseViewHolder holder, int position, AddImage bean) {
        if (bean.getImageUrl() == null || "".equals(bean.getImageUrl())) {
            holder.setImageResource(R.id.create_group_image, R.drawable.add_icon);
            bean.setHasImage(false);
        } else {
            bean.setHasImage(true);
            Bitmap bitmap = BitmapUtil.compressBitmap(bean.getImageUrl(), 720);
            holder.setImageBitmap(R.id.create_group_image, bitmap);
        }
    }
}