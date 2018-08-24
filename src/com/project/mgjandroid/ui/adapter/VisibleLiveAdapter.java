package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnImageCapturedListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.base.BaseViewHolder;
import com.project.mgjandroid.bean.VisibleLive;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class VisibleLiveAdapter extends BaseRecyclerAdapter<VisibleLive> {

    public VisibleLiveAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void newBindViewHolder(final BaseViewHolder holder, int position, VisibleLive bean) {
        holder.setText(R.id.name_textview, bean.getPositionName());
        ImageView convreView = holder.get(R.id.cover_imageview);
        if (CheckUtils.isNoEmptyStr(bean.getVideoPic())) {
            ImageUtils.loadBitmap(mContext, bean.getVideoPic(), convreView, R.drawable.horsegj_default, "");
        }
    }
}
