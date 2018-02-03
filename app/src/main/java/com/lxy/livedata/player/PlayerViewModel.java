package com.lxy.livedata.player;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lxy.livedata.PlayerLayout;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * @author a
 * @date 2018/2/3
 */

public class PlayerViewModel extends ViewModel implements LifecycleObserver, ITXLivePlayListener {

    private String mUrl = "";
    private TXVodPlayer mPlayer;
    private TXCloudVideoView mVideoView;
    private Context mContext;
    private PlayerLayout playerLayout;
    private MutableLiveData<String> liveData;

    public PlayerViewModel(View view, MutableLiveData<String> liveData) {
        this.liveData = liveData;
        mContext = view.getContext();

        if (view instanceof PlayerLayout) {
            playerLayout = (PlayerLayout) view;
        } else {
            throw new IllegalArgumentException("View must be PlayerLayout");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        initPlayer();
        initEvents();
    }

    private void initPlayer() {
        mVideoView = playerLayout.mViewBinding.txVideoView;
        mPlayer = new TXVodPlayer(mContext);
        mPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        TXVodPlayConfig config = new TXVodPlayConfig();
        config.setConnectRetryCount(5);
        mPlayer.setConfig(config);

        mPlayer.setPlayerView(mVideoView);

        mPlayer.setPlayListener(this);
    }

    private void initEvents() {
        liveData.observe(playerLayout.mAttachActivity, s -> {
            mUrl = s;
            startPlay();
        });

        playerLayout.mViewBinding.tvFullScreen.setOnClickListener(view -> {
            Toast.makeText(mContext, "full", Toast.LENGTH_SHORT).show();
        });
    }

    public void startPlay() {
        if (mPlayer == null || mUrl.length() == 0) {
            return;
        }
        mPlayer.stopPlay(true);
        mPlayer.enableHardwareDecode(true);
        mPlayer.startPlay(mUrl);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        if (mVideoView != null) {
            mVideoView.onResume();
        }
        startPlay();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        if (mPlayer != null) {
            mPlayer.setPlayListener(null);
            mPlayer.stopPlay(true);
        }

        if (mVideoView != null) {
            mVideoView.onPause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory() {

        if (mPlayer != null) {
            mPlayer.stopPlay(true);
            mPlayer = null;
        }

        if (mVideoView != null) {
            mVideoView.onDestroy();
            mVideoView = null;
        }
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {

        switch (i) {
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //视频播放开始，如果有转菊花什么的这个时候该停了
                playerLayout.mViewBinding.progressBar.setVisibility(View.GONE);
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                //视频播放loading，隐藏菊花，如果能够恢复，之后会有BEGIN事件
                playerLayout.mViewBinding.progressBar.setVisibility(View.VISIBLE);
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                //网络断连,且经多次重连抢救无效,放弃治疗
                playerLayout.mViewBinding.progressBar.setVisibility(View.GONE);
                startPlay();//我们就不抛弃不放弃，再试
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                startPlay();// 循环播放
                break;
            case TXLiveConstants.PLAY_WARNING_RECONNECT:

                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
