package com.ranfeng.adranfengsdkdemo.ad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;


import com.ranfeng.adranfengsdk.ad.BannerAd;
import com.ranfeng.adranfengsdk.ad.bean.BannerAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.BannerAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;


public class BannerAdActivity extends AppCompatActivity {

    private FrameLayout flContainer;
    private BannerAd bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initView();
        initListener();

        loadAd();
    }

    private void initView() {
        flContainer = findViewById(R.id.flContainer);
    }

    private void initListener() {
        findViewById(R.id.btnToInterstitialActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BannerAdActivity.this, InterstitialAdActivity.class));
            }
        });
    }

    private void loadAd() {
        bannerAd = new BannerAd(this, flContainer);
        bannerAd.setListener(new BannerAdListener() {
            @Override
            public void onAdExpose(BannerAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdExpose");
            }

            @Override
            public void onAdClick(BannerAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClick");
            }

            @Override
            public void onAdClose(BannerAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClose");
            }

            @Override
            public void onAdReceive(BannerAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdReceive");
            }

            @Override
            public void onAdFailed(Error error) {
                LogUtil.d(DemoConstant.TAG, "onAdFailed error : " + error.toString());
            }
        });
        bannerAd.loadAd(DemoConstant.BANNER_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (bannerAd != null) {
            bannerAd.release();
            bannerAd = null;
        }
    }
}
