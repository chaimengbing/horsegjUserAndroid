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

import com.project.mgjandroid.R;

import java.io.IOException;

/**
 * Created by ning on 2016/4/18.
 */
public class VoiceService extends Service {
    public static final int PLAY_START = 110;

    //为日志工具设置标签
    private static String TAG = "VoiceService";


    //定义音乐播放器变量
    private static MediaPlayer mPlayer;
    private Handler mMusicHandler;

    @Override
    public void onCreate() {
        initBackThread();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        try {
            // 打开指定音乐文件
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.cashback);
            // 使用MediaPlayer加载指定的声音文件。
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
            mPlayer.setLooping(false);
            mMusicHandler.sendEmptyMessageDelayed(PLAY_START, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
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
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }

    //其他对象通过bindService 方法通知该Service时该方法被调用
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //其它对象通过unbindService方法通知该Service时该方法被调用
    @Override
    public boolean onUnbind(Intent intent) {
        if (mPlayer != null)
            mPlayer.stop();
        return super.onUnbind(intent);
    }
}
