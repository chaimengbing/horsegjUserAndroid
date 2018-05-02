package com.project.mgjandroid.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.project.mgjandroid.R;

import java.io.IOException;

/**
 * Created by ning on 2016/4/18.
 */
public class VoiceService extends Service {
    public static final int PLAY_START = 110;
    public static final int PLAY_COUNT_MAX = 43;

    public static void setCurrentCount(int currentCount) {
        VoiceService.currentCount = currentCount;
    }

    private static int currentCount = 0;
    //为日志工具设置标签
    private static String TAG = "VoiceService";

    public static MediaPlayer getmPlayer() {
        return mPlayer;
    }

    public static void setmPlayer(MediaPlayer mPlayer) {
        VoiceService.mPlayer = mPlayer;
    }

    //定义音乐播放器变量
    private static MediaPlayer mPlayer;
    private Handler mMusicHandler;

    //该服务不存在需要被创建时被调用，不管startService()还是bindService()都会启动时调用该方法
    @Override
    public void onCreate() {
        Log.e(TAG, "MusicSerice onCreate()");

//        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        //设置可以重复播放
//        mPlayer.setLooping(false);

        initBackThread();

        super.onCreate();
    }

    /**
     * 创建后台线程播放音乐
     */
    private void initBackThread() {
        HandlerThread handlerThread = new HandlerThread("play_music");
        handlerThread.start();
        mMusicHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case PLAY_START:
                        if (mPlayer != null) {
                            mPlayer.start();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "MusicSerice onStart()");

        currentCount = 0;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        try {
            // 打开指定音乐文件
//            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.);
//            // 使用MediaPlayer加载指定的声音文件。
//            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            afd.close();
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置可以重复播放
        mPlayer.setLooping(false);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "MusicSerice onDestroy()");

        mMusicHandler.removeMessages(PLAY_START);

        if (mPlayer != null) {
            mPlayer.stop();
        }

        super.onDestroy();
    }

    //其他对象通过bindService 方法通知该Service时该方法被调用
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "MusicSerice onBind()");
        mPlayer.start();
        return null;
    }

    //其它对象通过unbindService方法通知该Service时该方法被调用
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "MusicSerice onUnbind()");

        mPlayer.stop();

        return super.onUnbind(intent);
    }
}
