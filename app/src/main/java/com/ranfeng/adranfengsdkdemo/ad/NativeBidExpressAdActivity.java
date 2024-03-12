package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ranfeng.adranfengsdk.ad.BidLossNotice;
import com.ranfeng.adranfengsdk.ad.NativeExpressAd;
import com.ranfeng.adranfengsdk.ad.bean.NativeExpressAdInfo;
import com.ranfeng.adranfengsdk.ad.entity.AdSize;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.NativeExpressAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdk.utils.ViewUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.utils.ToastUtil;

import java.util.List;


public class NativeBidExpressAdActivity extends AppCompatActivity {

    private FrameLayout flContainer;

    private NativeExpressAd nativeExpressAd;
    private NativeExpressAdInfo nativeExpressAdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_bid_express);

        flContainer = findViewById(R.id.flContainer);

        findViewById(R.id.btnLoadAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd();
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

    }

    private void loadAd() {
        flContainer.setVisibility(View.GONE);
        ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取中");

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        nativeExpressAd = new NativeExpressAd(this, new AdSize(widthPixels, 0));
        nativeExpressAd.setMute(false);
        nativeExpressAd.setListener(new NativeExpressAdListener() {
            @Override
            public void onAdReceive(List<NativeExpressAdInfo> adInfos) {
                LogUtil.d(DemoConstant.TAG, "onAdReceive size:" + adInfos.size());

                for (int i = 0; i < adInfos.size(); i++) {
                    LogUtil.d(DemoConstant.TAG, "onAdReceive " + adInfos.get(i).toString());
                }

                if (adInfos != null && adInfos.size() > 0) {
                    nativeExpressAdInfo = adInfos.get(0);
                    ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，当前价格：" + nativeExpressAdInfo.getBidPrice() + "(分)");
                }
            }

            @Override
            public void onAdExpose(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdExpose " + adInfo.toString());
            }

            @Override
            public void onAdClick(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClick " + adInfo.toString());
            }

            @Override
            public void onAdClose(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClose " + adInfo.toString());
                if (adInfo != null) {
                    adInfo.release();
                }
                flContainer.setVisibility(View.GONE);
            }

            @Override
            public void onRenderFailed(NativeExpressAdInfo adInfo, Error error) {
                LogUtil.d(DemoConstant.TAG, "onRenderFailed error : " + error.toString());
                flContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailed(Error error) {
                ((TextView)findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取广告失败");
                LogUtil.d(DemoConstant.TAG, "onAdFailed error : " + error.toString());
            }
        });
        nativeExpressAd.loadAd(DemoConstant.NATIVE_BID_EXPRESS_ID);
    }

    private void bidWinAd() {
        String price = ((EditText) findViewById(R.id.etWinPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞赢价格");
            return;
        }
        if (nativeExpressAdInfo != null) {
            ToastUtil.toast("广告竞赢上报");
            /**
             * 媒体回传二价ecpm，竞价成功后，必须在展示前回传递
             *
             * price 竞赢价格（分）
             */
            nativeExpressAdInfo.sendWinNotice(Integer.valueOf(price));
        }
    }

    private void showAd() {
        if (nativeExpressAdInfo != null) {
            flContainer.setVisibility(View.VISIBLE);
            ToastUtil.toast("广告展示");
            View nativeView = nativeExpressAdInfo.getNativeExpressAdView();
            ViewUtil.addAdViewToAdContainer(flContainer, nativeView);
            nativeExpressAdInfo.render();
        }
    }

    private void bidLossAd() {
        String price = ((EditText) findViewById(R.id.etLossPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞败价格");
            return;
        }
        if (nativeExpressAdInfo != null) {
            ToastUtil.toast("广告竞败上报");
            nativeExpressAdInfo.sendLossNotice(Integer.valueOf(price), BidLossNotice.LOW_PRICE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (nativeExpressAd != null) {
            nativeExpressAd.release();
            nativeExpressAd = null;
        }
    }

}
