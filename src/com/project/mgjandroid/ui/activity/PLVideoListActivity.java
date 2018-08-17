package com.project.mgjandroid.ui.activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.VisibleLive;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

public class PLVideoListActivity extends BaseActivity {

    @InjectView(R.id.video_list)
    private RecyclerView mVideoListView;
    @InjectView(R.id.full_screen)
    private RelativeLayout fullScreen;
    @InjectView(R.id.iv_back)
    private ImageView backImageView;

    private View playerView;
    private VideoListAdapter mAdapter;
    private List<VisibleLive> mPlayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        Injector.get(this).inject();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();

    }


    private void initView() {
        String result = getIntent().getStringExtra("videoPath");
        if (CheckUtils.isNoEmptyStr(result)) {
            mPlayList = JSON.parseArray(result, VisibleLive.class);
            for (int i = 0; i < 6; i++) {
                mPlayList.add(mPlayList.get(0));
            }
        }
        mAdapter = new VideoListAdapter(mPlayList);
        mVideoListView.setHasFixedSize(true);
        mVideoListView.setLayoutManager(new LinearLayoutManager(this));
        mVideoListView.setAdapter(mAdapter);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.stopAll();
        mVideoListView.setAdapter(null);
    }


//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // 切换为小屏
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            fullScreen.setVisibility(View.GONE);
//            fullScreen.removeAllViews();
//            int mShowFlags =
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            fullScreen.setSystemUiVisibility(mShowFlags);
//        } else {
//            ViewGroup viewGroup = (ViewGroup) playerView.getParent();
//            if (viewGroup == null)
//                return;
//            viewGroup.removeAllViews();
//            fullScreen.addView(playerView);
//            PLVideoTextureView plVideoTextureView = (PLVideoTextureView) playerView.findViewById(R.id.video_view);
//            plVideoTextureView.setDisplayOrientation(90 % 360);
//            fullScreen.setVisibility(View.VISIBLE);
//            int mHideFlags =
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            fullScreen.setSystemUiVisibility(mHideFlags);
//        }
//    }

    class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
        List<VisibleLive> mVideoPathList;
        List<ViewHolder> mViewHolderList;
        private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT;

        class ViewHolder extends RecyclerView.ViewHolder {
            PLVideoTextureView mVideoView;
            ImageView playImageView;
            ImageView coverImageView;
            ImageView screenImageView;
            LinearLayout loadingLayout;
            RelativeLayout controlLayout;
            RelativeLayout control;

            ViewHolder(View view) {
                super(view);
                mVideoView = (PLVideoTextureView) view.findViewById(R.id.video_view);
                playImageView = (ImageView) view.findViewById(R.id.play_imageview);
                coverImageView = (ImageView) view.findViewById(R.id.cover_view);
                loadingLayout = (LinearLayout) view.findViewById(R.id.loading_view);
                screenImageView = (ImageView) view.findViewById(R.id.screen_imageview);
                controlLayout = (RelativeLayout) view.findViewById(R.id.control_layout);
                control = (RelativeLayout) view.findViewById(R.id.control);
            }
        }

        VideoListAdapter(List<VisibleLive> list) {
            mVideoPathList = list;
            mViewHolderList = new ArrayList<>();
        }

        void stopAll() {
            for (ViewHolder vh : mViewHolderList) {
                vh.mVideoView.stopPlayback();
            }
        }

        @Override
        public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_video_list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int pos) {
            VisibleLive visibleLive = mVideoPathList.get(pos);
            holder.mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
            holder.mVideoView.setBufferingIndicator(holder.loadingLayout);
            holder.mVideoView.setCoverView(holder.coverImageView);
            holder.playImageView.setVisibility(View.VISIBLE);
            holder.coverImageView.setVisibility(View.VISIBLE);
            holder.coverImageView.setBackgroundResource(R.drawable.surface_view_bg);
            holder.coverImageView.setImageResource(R.drawable.horsegj_default);
            if (CheckUtils.isNoEmptyStr(visibleLive.getVideoPic())) {
                holder.coverImageView.setBackgroundResource(0);
                ImageUtils.loadBitmap(mActivity, visibleLive.getVideoPic(), holder.coverImageView, R.drawable.horsegj_default, "");
            }

            holder.playImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playerView = holder.itemView;
                    holder.mVideoView.setVideoPath(mVideoPathList.get(pos).getVideoSrc());
                    holder.mVideoView.start();
                    holder.loadingLayout.setVisibility(View.VISIBLE);
                    holder.playImageView.setVisibility(View.GONE);
                    holder.coverImageView.setBackgroundResource(0);
                }
            });

            holder.screenImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //全屏
                    holder.mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
//                    switch (holder.mVideoView.getDisplayAspectRatio()) {
//                        case PLVideoTextureView.ASPECT_RATIO_ORIGIN:
//                            break;
//                        case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
//                            break;
//                        case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
//                            break;
//                        case PLVideoTextureView.ASPECT_RATIO_16_9:
//                            break;
//                        case PLVideoTextureView.ASPECT_RATIO_4_3:
//                            break;
//                        default:
//                            break;
//                    }
                }
            });
            holder.control.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mVideoView.isPlaying()) {
                        holder.controlLayout.setVisibility(holder.controlLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    }
                }
            });
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            mViewHolderList.add(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            holder.mVideoView.pause();
            holder.mVideoView.stopPlayback();
            mViewHolderList.remove(holder);
        }

        @Override
        public int getItemCount() {
            return mVideoPathList.size();
        }
    }
}
