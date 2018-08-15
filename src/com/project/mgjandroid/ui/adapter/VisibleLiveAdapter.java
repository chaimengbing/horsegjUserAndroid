package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnImageCapturedListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.base.BaseViewHolder;
import com.project.mgjandroid.bean.VisibleLive;

public class VisibleLiveAdapter extends BaseRecyclerAdapter<VisibleLive> {
    public VisibleLiveAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void newBindViewHolder(final BaseViewHolder holder, int position, VisibleLive bean) {
        holder.setText(R.id.name_textview, bean.getPositionName());
        final PLVideoView videoView = holder.get(R.id.live_video_view);
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        videoView.setAVOptions(options);
        videoView.setVideoPath(bean.getVideoSrc());
        videoView.start();
        videoView.setOnInfoListener(new PLOnInfoListener() {
            @Override
            public void onInfo(int i, int i1) {
                if (i == PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START) {
                    videoView.captureImage(i1);
                }
            }
        });
        final ImageView imageView = holder.get(R.id.cover_imageview);
        imageView.setImageResource(R.drawable.surface_view_bg);
        videoView.setOnImageCapturedListener(new PLOnImageCapturedListener() {
            @Override
            public void onImageCaptured(byte[] bytes) {
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(R.drawable.surface_view_bg);
                    }
                    videoView.stopPlayback();
                }
            }
        });

    }

}
