package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ranfeng.adranfengsdk.ad.BidLossNotice;
import com.ranfeng.adranfengsdk.ad.RewardAd;
import com.ranfeng.adranfengsdk.ad.bean.RewardAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.RewardAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.utils.ToastUtil;


public class RewardAdActivity extends AppCompatActivity {

    private TextView tv_voice_state;
    private CheckBox cb_voice_state;

    private boolean mute = false;

    private RewardAd rewardAd;
    private RewardAdInfo rewardAdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        tv_voice_state = findViewById(R.id.tv_voice_state);
        cb_voice_state = findViewById(R.id.cb_voice_state);

        findViewById(R.id.btnLoadAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(DemoConstant.REWARD_BID_ID);
            }
        });

        findViewById(R.id.btnBidWinAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bidWinAd();
            }
        });

        findViewById(R.id.btnShowAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
            }
        });

        findViewById(R.id.btnAdBidFailed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bidLossAd();
            }
        });

        findViewById(R.id.btnLoadNormalAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(DemoConstant.REWARD_ID);
            }
        });

        findViewById(R.id.btnShowNormalAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
            }
        });

        cb_voice_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mute = !b;
                if (mute) {
                    tv_voice_state.setText("声音关");
                } else {
                    tv_voice_state.setText("声音开");
                }
            }
        });
    }

    private void loadAd(final String adPosition) {
        if (adPosition.equals(DemoConstant.REWARD_BID_ID)) {
            ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取中");
        } else {
            ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取中");
        }

        if (rewardAd != null) {
            rewardAd.release();
            rewardAd = null;
        }
        rewardAd = new RewardAd(this);
        rewardAd.setMute(mute);
        rewardAd.setListener(new RewardAdListener() {

            @Override
            public void onAdReceive(RewardAdInfo adInfo) {
                if (adPosition.equals(DemoConstant.REWARD_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，当前价格：" + adInfo.getBidPrice() + "(分)");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告");
                }
                ToastUtil.toast("广告获取");
                rewardAdInfo = adInfo;
                LogUtil.d(DemoConstant.TAG,"onAdReceive");
            }

            @Override
            public void onAdReward(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdReward");
            }

            @Override
            public void onAdExpose(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdExpose");
            }

            @Override
            public void onAdClick(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClick");
            }

            @Override
            public void onAdClose(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClose");
            }

            @Override
            public void onVideoCompleted(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoCompleted");
            }

            @Override
            public void onVideoSkip(RewardAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoSkip");
            }

            @Override
            public void onVideoError(RewardAdInfo adInfo, String msg) {
                LogUtil.d(DemoConstant.TAG,"onVideoError");
            }

            @Override
            public void onAdFailed(Error error) {
                if (adPosition.equals(DemoConstant.REWARD_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取广告失败");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取广告失败");
                }
                LogUtil.d(DemoConstant.TAG,"onAdFailed error : " + error.toString());
            }
        });
        rewardAd.loadAd(adPosition);
    }

    private void bidWinAd() {
        String price = ((EditText) findViewById(R.id.etWinPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞赢价格");
            return;
        }
        if (rewardAdInfo != null) {
            ToastUtil.toast("广告竞赢上报");
            /**
             * 媒体回传二价ecpm，竞价成功后，必须在展示前回传递
             *
             * price 竞赢价格（分）
             */
            rewardAdInfo.sendWinNotice(Integer.valueOf(price));
        }
    }

    private void showAd() {
        if (rewardAdInfo != null) {
            ToastUtil.toast("广告展示");
            rewardAdInfo.showRewardAd(RewardAdActivity.this);
        }
    }

    private void bidLossAd() {
        String price = ((EditText) findViewById(R.id.etLossPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞败价格");
            return;
        }
        if (rewardAdInfo != null) {
            ToastUtil.toast("广告竞败上报");
            rewardAdInfo.sendLossNotice(Integer.valueOf(price), BidLossNotice.LOW_PRICE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (rewardAd != null) {
            rewardAd.release();
            rewardAd = null;
        }
        if (rewardAdInfo != null) {
            rewardAdInfo.release();
            rewardAdInfo = null;
        }
    }
}
