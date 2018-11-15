package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class GroupBuyingImageRecyclerAdapter extends RecyclerView.Adapter<GroupBuyingImageRecyclerAdapter.ImageViewHolder> {

    private List<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public GroupBuyingImageRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
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


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.groupbuying_recycler_image_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageUtils.loadBitmap(mContext, mImageUrls.get(position), ((ImageViewHolder) holder).iv,
                R.drawable.horsegj_default, Constants.RECYCLER_IMAGE_URL_END_THUMBNAIL_BANNER);
        if (mImageUrls.size() > 1) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.iv.getLayoutParams();
            if (mImageUrls.size() == 2) {
                params.width = (CommonUtils.getScreenWidth(((BaseActivity) mContext).getWindowManager()) / 2) - (int) mContext.getResources().getDimension(R.dimen.x20);
                params.height = (int) mContext.getResources().getDimension(R.dimen.x120);
            } else {
                params.width = (int) mContext.getResources().getDimension(R.dimen.x120);
                params.height = (int) mContext.getResources().getDimension(R.dimen.x101);
            }
            holder.iv.setLayoutParams(params);

            if (position == 0) {
                holder.tvPhotoCount.setVisibility(View.VISIBLE);
                holder.tvPhotoCount.setText("" + mImageUrls.size());
            } else {
                holder.tvPhotoCount.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvPhotoCount;

        public ImageViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.image_view);
            tvPhotoCount = (TextView) view.findViewById(R.id.tv_photo_count);
        }
    }

}
