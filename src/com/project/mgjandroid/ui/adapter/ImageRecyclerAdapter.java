package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter {

    private List<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private boolean isNeedAdd;
    private final static int MAX_COUNT = 12;
    private OnClickListener listener;

    public ImageRecyclerAdapter(Context context, boolean isNeedAdd) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.isNeedAdd = isNeedAdd;
    }

    public void setList(List<String> imageUrls) {
        if (imageUrls == null) {
            mImageUrls = new ArrayList<>();
        } else {
            mImageUrls = imageUrls;
        }
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return mImageUrls;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new AddViewHolder(inflater.inflate(R.layout.recycler_add_view, parent, false));
        } else {
            return new ImageViewHolder(inflater.inflate(R.layout.recycler_image_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mImageUrls.size()) {
            if (isNeedAdd) {
                Bitmap bitmap = BitmapUtil.compressBitmap(mImageUrls.get(position), 510);
                ((ImageViewHolder) holder).iv.setImageBitmap(bitmap);
            } else {
                ImageUtils.loadBitmap(mContext, mImageUrls.get(position), ((ImageViewHolder) holder).iv,
                        R.drawable.horsegj_default, Constants.RECYCLER_IMAGE_URL_END_THUMBNAIL_BANNER);
            }
        } else {
            MLog.i("AddImage-------------------------");
        }
    }

    @Override
    public int getItemCount() {
        if (isNeedAdd && mImageUrls.size() < MAX_COUNT) {
            return mImageUrls.size() + 1;
        }
        return mImageUrls.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isNeedAdd && position >= mImageUrls.size()) {
            return 2;
        }
        return 1;
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public ImageViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.image_view);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickImage(getLayoutPosition());
                    } else {
                        PictureViewActivity.toViewPicture(mContext, JSONArray.toJSONString(mImageUrls), getLayoutPosition());
                    }
                }
            });
        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAdd;

        public AddViewHolder(View view) {
            super(view);
            ivAdd = (ImageView) view.findViewById(R.id.add_view);
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onClichAdd();
                }
            });
        }
    }

    public interface OnClickListener {
        void onClickImage(int currentItem);

        void onClichAdd();
    }
}
