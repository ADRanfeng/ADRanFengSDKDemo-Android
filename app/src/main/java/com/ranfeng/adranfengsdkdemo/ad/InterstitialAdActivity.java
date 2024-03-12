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
import com.ranfeng.adranfengsdk.ad.InterstitialAd;
import com.ranfeng.adranfengsdk.ad.bean.InterstitialAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.InterstitialAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.utils.ToastUtil;

public class InterstitialAdActivity extends AppCompatActivity {

    private TextView tv_voice_state;
    private CheckBox cb_voice_state;

    private boolean mute = true;

    private InterstitialAd interstitialAd;
    private InterstitialAdInfo interstitialAdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        tv_voice_state = findViewById(R.id.tv_voice_state);
        cb_voice_state = findViewById(R.id.cb_voice_state);

        findViewById(R.id.btnLoadAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(DemoConstant.INTERSTITIAL_BID_ID);
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
                loadAd(DemoConstant.INTERSTITIAL_ID);
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
        if (adPosition.equals(DemoConstant.INTERSTITIAL_BID_ID)) {
            ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取中");
        } else {
            ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取中");
        }

        if (interstitialAd != null) {
            interstitialAd.release();
            interstitialAd = null;
        }
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setMute(mute);
        interstitialAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReceive(InterstitialAdInfo adInfo) {
                ToastUtil.toast("广告获取");
                if (adPosition.equals(DemoConstant.INTERSTITIAL_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，当前价格：" + adInfo.getBidPrice() + "(分)");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告");
                }
                interstitialAdInfo = adInfo;
                LogUtil.d(DemoConstant.TAG,"onAdReceive");
            }

            @Override
            public void onAdExpose(InterstitialAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdExpose");
            }

            @Override
            public void onAdClick(InterstitialAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClick");
            }

            @Override
            public void onAdClose(InterstitialAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClose");
            }

            @Override
            public void onVideoStart(InterstitialAdInfo interstitialAdInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoStart");
            }

            @Override
            public void onVideoFinish(InterstitialAdInfo interstitialAdInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoFinish");
            }

            @Override
            public void onVideoPause(InterstitialAdInfo interstitialAdInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoPause");
            }

            @Override
            public void onVideoError(InterstitialAdInfo interstitialAdInfo) {
                LogUtil.d(DemoConstant.TAG,"onVideoError");
            }

            @Override
            public void onAdFailed(Error error) {
                if (adPosition.equals(DemoConstant.INTERSTITIAL_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取广告失败");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取广告失败");
                }
                LogUtil.d(DemoConstant.TAG,"onAdFailed error : " + error.toString());
            }
        });
        interstitialAd.loadAd(adPosition);
    }

    private void bidWinAd() {
        String price = ((EditText) findViewById(R.id.etWinPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞赢价格");
            return;
        }
        if (interstitialAdInfo != null) {
            ToastUtil.toast("广告竞赢上报");
            /**
             * 媒体回传二价ecpm，竞价成功后，必须在展示前回传递
             *
             * price 竞赢价格（分）
             */
            interstitialAdInfo.sendWinNotice(Integer.valueOf(price));
        }
    }

    private void showAd() {
        if (interstitialAdInfo != null) {
            ToastUtil.toast("广告展示");
            interstitialAdInfo.showInterstitial(InterstitialAdActivity.this);
        }
    }

    private void bidLossAd() {
        String price = ((EditText) findViewById(R.id.etLossPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞败价格");
            return;
        }
        if (interstitialAdInfo != null) {
            ToastUtil.toast("广告竞败上报");
            interstitialAdInfo.sendLossNotice(Integer.valueOf(price), BidLossNotice.LOW_PRICE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (interstitialAd != null) {
            interstitialAd.release();
            interstitialAd = null;
        }
        if (interstitialAdInfo != null) {
            interstitialAdInfo.release();
            interstitialAdInfo = null;
        }
    }
}
