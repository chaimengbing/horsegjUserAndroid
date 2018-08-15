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

import java.util.ArrayList;
import java.util.List;

public class VisibleLiveAdapter extends BaseRecyclerAdapter<VisibleLive> {
    private String videoPath;
    private List<BaseViewHolder> baseViewHolders;

    public VisibleLiveAdapter(Context context, int layoutId) {
        super(context, layoutId);
        baseViewHolders = new ArrayList<>();
    }

    @Override
    protected void newBindViewHolder(final BaseViewHolder holder, int position, VisibleLive bean) {
        holder.setText(R.id.name_textview, bean.getPositionName());
        videoPath = bean.getVideoSrc();

    }

    @Override
    public void onViewAttachedToWindow(final BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (CheckUtils.isNoEmptyStr(videoPath)) {
            final PLVideoTextureView videoView = holder.get(R.id.live_video_view);
            baseViewHolders.add(holder);
            AVOptions options = new AVOptions();
            options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
            options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
            videoView.setAVOptions(options);
            videoView.setVideoPath(videoPath);
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
            imageView.setImageResource(R.drawable.horsegj_default);
            videoView.setOnImageCapturedListener(new PLOnImageCapturedListener() {
                @Override
                public void onImageCaptured(final byte[] bytes) {
                    if (bytes != null) {
                        ((CommercialActivity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoView.setVisibility(View.GONE);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageView.setBackgroundResource(0);
                                if (bitmap != null) {
                                    imageView.setImageBitmap(bitmap);
                                } else {
                                    imageView.setImageResource(R.drawable.horsegj_default);
                                }
                                videoView.stopPlayback();
                            }
                        });

                    }

                }
            });
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        PLVideoTextureView videoView = holder.get(R.id.live_video_view);
        videoView.pause();
        baseViewHolders.remove(holder);

    }

    public void stopAll() {
        if (CheckUtils.isNoEmptyList(baseViewHolders)) {
            for (BaseViewHolder baseViewHolder : baseViewHolders) {
                PLVideoTextureView videoView = baseViewHolder.get(R.id.live_video_view);
                videoView.stopPlayback();
            }
        }
    }
}
