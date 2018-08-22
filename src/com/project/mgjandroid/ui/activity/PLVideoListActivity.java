package com.project.mgjandroid.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnErrorListener;
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
    @InjectView(R.id.visible_list_layout)
    private LinearLayout visibleListLayout;
    @InjectView(R.id.iv_back)
    private ImageView backImageView;

    private View playerView;
    private VideoListAdapter mAdapter;
    private List<VisibleLive> mPlayList = new ArrayList<>();

    /**
     * 全屏的view
     */
    private FrameLayout videoFrameLayout;
    PLVideoTextureView plVideoTextureView;
    RelativeLayout controlLayout;
    RelativeLayout control;


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
        if (plVideoTextureView != null) {
            plVideoTextureView.stopPlayback();
        }
    }


    private void showScreen() {
        //全屏
        ViewGroup viewGroup = (ViewGroup) playerView.getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        fullScreen.addView(playerView);
        videoFrameLayout = (FrameLayout) playerView.findViewById(R.id.video_framelayout);
        plVideoTextureView = (PLVideoTextureView) playerView.findViewById(R.id.video_view);
        ImageView playImageView = (ImageView) playerView.findViewById(R.id.play_imageview);
        ImageView coverImageView = (ImageView) playerView.findViewById(R.id.cover_view);
        LinearLayout loadingLayout = (LinearLayout) playerView.findViewById(R.id.loading_view);
        ImageView screenImageView = (ImageView) playerView.findViewById(R.id.screen_imageview);
        controlLayout = (RelativeLayout) playerView.findViewById(R.id.control_layout);
        control = (RelativeLayout) playerView.findViewById(R.id.control);
        plVideoTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 5);
        plVideoTextureView.setAVOptions(options);
        plVideoTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_FIT_PARENT);
        fullScreen.setVisibility(View.VISIBLE);
        visibleListLayout.setVisibility(View.GONE);

        ViewGroup.LayoutParams params = videoFrameLayout.getLayoutParams();
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        videoFrameLayout.setLayoutParams(params);

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plVideoTextureView.isPlaying()) {
                    controlLayout.setVisibility(controlLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            }
        });
    }


    private void hideScreen() {
        //全屏
        fullScreen.setVisibility(View.GONE);
        fullScreen.removeAllViews();
        visibleListLayout.setVisibility(View.VISIBLE);
        if (playerView != null) {
            playerView = null;
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (plVideoTextureView != null && videoFrameLayout != null) {
//            ViewGroup.LayoutParams params = videoFrameLayout.getLayoutParams();
//            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
//                params.height = (int) getApplicationContext().getResources().getDimension(R.dimen.x200);
//                videoFrameLayout.setLayoutParams(params);
//                plVideoTextureView.setDisplayOrientation(0);
//            } else {//横屏
//                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
//                videoFrameLayout.setLayoutParams(params);
//                plVideoTextureView.setDisplayOrientation(90);
//            }
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

                AVOptions options = new AVOptions();
                // the unit of timeout is ms
                options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
                options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
                // 1 -> hw codec enable, 0 -> disable [recommended]
                options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
                options.setInteger(AVOptions.KEY_LOG_LEVEL, 5);
                mVideoView.setAVOptions(options);
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

            holder.coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((int) holder.coverImageView.getTag() == R.drawable.play_fail) {
                        holder.mVideoView.setVideoPath(mVideoPathList.get(pos).getVideoSrc());
                        holder.mVideoView.start();
                        holder.loadingLayout.setVisibility(View.VISIBLE);
                        holder.control.setVisibility(View.VISIBLE);
                        holder.playImageView.setVisibility(View.GONE);
                        holder.coverImageView.setImageResource(R.drawable.horsegj_default);
                        holder.coverImageView.setBackgroundResource(R.drawable.surface_view_bg);
                    }
                }
            });

            holder.playImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mVideoView.setVideoPath(mVideoPathList.get(pos).getVideoSrc());
                    holder.mVideoView.start();
                    holder.loadingLayout.setVisibility(View.VISIBLE);
                    holder.control.setVisibility(View.VISIBLE);
                    holder.playImageView.setVisibility(View.GONE);
                    holder.coverImageView.setBackgroundResource(0);
                }
            });

            holder.mVideoView.setOnErrorListener(new PLOnErrorListener() {
                @Override
                public boolean onError(int errorCode) {
                    switch (errorCode) {
                        case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                            return false;
                        case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                            holder.loadingLayout.setVisibility(View.GONE);
                            holder.control.setVisibility(View.GONE);
                            holder.coverImageView.setBackgroundResource(0);
                            holder.coverImageView.setImageResource(R.drawable.play_fail);
                            holder.coverImageView.setTag(R.drawable.play_fail);
                            break;
                        case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            holder.screenImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playerView = holder.itemView;
                    if (fullScreen.getVisibility() == View.GONE) {
                        showScreen();
                    } else {
                        hideScreen();
                    }
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
