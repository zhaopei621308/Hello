package com.example.zhaopei.hello;


import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import java.sql.CallableStatement;

/**vitamio 第三方
 * 做一个简单的视频播放
 *  viadioView
 */
public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {

    private VideoView mVideo;
    //声明媒体控制器
    private MediaController  mediaController;


    private boolean isPrepare;
    private LinearLayout controller;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initView();
        }

    private void initView() {
        mVideo = ((VideoView) findViewById(R.id.video_view));
//        mediaController = new MediaController(this);
//        mVideo.setMediaController(mediaController);
//        //为videoView设置播放的url  设置uri是个耗时的操作
//        mVideo.setVideoURI(Uri.parse("http://7rflo2.com2.z0.glb.qiniucdn.com/5714b0b53c958.mp4"));
//        //播放
//        mVideo.start();
        //设置准备好了的监听
        mVideo.setOnPreparedListener(this);


        //获取屏幕高度
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        //设置videoview的高为屏幕的1/3
        mVideo.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPixels / 3));
        mVideo.setOnTouchListener(this);

        //在异步中加载资源
        new Thread(){
            @Override
            public void run() {
                super.run();
                mVideo.setVideoURI(Uri.parse("http://7rflo2.com2.z0.glb.qiniucdn.com/5714b0b53c958.mp4"));
                isPrepare = true;
            }
        }.start();


        ((CheckBox) findViewById(R.id.player_change)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.player_play)).setOnCheckedChangeListener(this);
        controller = ((LinearLayout) findViewById(R.id.player_controller_view));


    }
//    准备好了的监听
    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideo.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mVideo.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.player_change:
                if (isChecked){
                    //添加全屏的标记
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    //请求全屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    //清除全屏的标记
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.player_play:
                if (isChecked&&isPrepare){
                    mVideo.start();
                }else if (isPrepare&&!isChecked){
                    mVideo.pause();
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        showOrHiden();
        return false;
    }

    private void showOrHiden (){
        if (controller.getVisibility()== View.VISIBLE){
            controller.setVisibility(View.INVISIBLE);
        }else if (controller.getVisibility()== View.INVISIBLE){
            controller.setVisibility(View.VISIBLE);
        }
    }



}
