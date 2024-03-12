package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranfeng.adranfengsdk.ad.BidLossNotice;
import com.ranfeng.adranfengsdk.ad.SplashAd;
import com.ranfeng.adranfengsdk.ad.bean.SplashAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.SplashAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.utils.ToastUtil;


public class SplashAdActivity extends AppCompatActivity {

    private LinearLayout llSplashContainer;
    private FrameLayout flContainer;
    private CheckBox cbOpenCustomerJumpView;
    private CheckBox cbStatusImmersion;
    private TextView tvSkip;

    private SplashAd splashAd;
    private SplashAdInfo splashAdInfo;

    private boolean isOpenCustomerJumpView;
    private boolean isImmersive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        llSplashContainer = findViewById(R.id.llSplashContainer);
        flContainer = findViewById(R.id.flContainer);
        cbOpenCustomerJumpView = findViewById(R.id.cbOpenCustomerJumpView);
        cbStatusImmersion = findViewById(R.id.cbStatusImmersion);
        tvSkip = findViewById(R.id.tvSkip);

        findViewById(R.id.btnLoadAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(DemoConstant.SPLASH_BID_ID);
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
                loadAd(DemoConstant.SPLASH_ID);
            }
        });

        findViewById(R.id.btnShowNormalAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
            }
        });

        cbStatusImmersion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isImmersive = isChecked;
            }
        });

        cbOpenCustomerJumpView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOpenCustomerJumpView = isChecked;
            }
        });
    }

    private void loadAd(final String adPosition) {
        if (adPosition.equals(DemoConstant.SPLASH_BID_ID)) {
            ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取中");
        } else {
            ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取中");
        }

        splashAd = new SplashAd(this);
        splashAd.setImmersive(isImmersive);
        tvSkip.setVisibility(View.GONE);
        if (isOpenCustomerJumpView) {
            tvSkip.setVisibility(View.VISIBLE);
            splashAd = new SplashAd(this, tvSkip);
            splashAd.setCountDownTime(5000);
        }
        splashAd.setListener(new SplashAdListener() {
            @Override
            public void onAdTick(long millisUntilFinished) {
                LogUtil.d(DemoConstant.TAG,"onADTick millisUntilFinished:" + millisUntilFinished);
                tvSkip.setText(millisUntilFinished + "/跳过");
            }

            @Override
            public void onAdReceive(SplashAdInfo adInfo) {
                ToastUtil.toast("广告获取");
                if (adPosition.equals(DemoConstant.SPLASH_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，当前价格：" + adInfo.getBidPrice() + "(分)");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告");
                }
                splashAdInfo = adInfo;
                LogUtil.d(DemoConstant.TAG,"onAdReceive");
            }

            @Override
            public void onAdExpose(SplashAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdExpose");
            }

            @Override
            public void onAdClick(SplashAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClick");
            }

            @Override
            public void onAdSkip(SplashAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onADSkip");
            }

            @Override
            public void onAdClose(SplashAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG,"onAdClose");
                toMain();
            }

            @Override
            public void onAdFailed(Error error) {
                if (adPosition.equals(DemoConstant.SPLASH_BID_ID)) {
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取广告失败");
                } else {
                    ((TextView)findViewById(R.id.btnLoadNormalAd)).setText("获取广告，获取广告失败");
                }
                LogUtil.d(DemoConstant.TAG, "onAdFailed error : " + error.toString());
                toMain();
            }
        });
        splashAd.loadAd(adPosition);
    }

    private void bidWinAd() {
        String price = ((EditText) findViewById(R.id.etWinPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞赢价格");
            return;
        }
        if (splashAdInfo != null) {
            ToastUtil.toast("广告竞赢上报");
            /**
             * 媒体回传二价ecpm，竞价成功后，必须在展示前回传递
             *
             * price 竞赢价格（分）
             */
            splashAdInfo.sendWinNotice(Integer.valueOf(price));
        }
    }

    private void showAd() {
        if (splashAdInfo != null) {
            ToastUtil.toast("广告展示");
            llSplashContainer.setVisibility(View.VISIBLE);
            flContainer.removeAllViews();
            flContainer.addView(splashAdInfo.getSplashAdView());
            splashAdInfo.render();
        }
    }

    private void bidLossAd() {
        String price = ((EditText) findViewById(R.id.etLossPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞败价格");
            return;
        }
        if (splashAdInfo != null) {
            ToastUtil.toast("广告竞败上报");
            /**
             * price 三方竞赢价格（分）
             * msg 失败原因 LOW_PRICE竞价失败、OTHER其他
             */
            splashAdInfo.sendLossNotice(Integer.valueOf(price), BidLossNotice.LOW_PRICE);
        }
    }

    private void toMain() {
        flContainer.removeAllViews();
        llSplashContainer.setVisibility(View.GONE);
        splashAdInfo = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (splashAd != null) {
            splashAd.release();
            splashAd = null;
        }
        if (splashAdInfo != null) {
            splashAdInfo.release();
            splashAdInfo = null;
        }
    }
}
