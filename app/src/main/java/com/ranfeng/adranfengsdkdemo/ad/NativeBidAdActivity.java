package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranfeng.adranfengsdk.ADRanFengSDK;
import com.ranfeng.adranfengsdk.ad.BidLossNotice;
import com.ranfeng.adranfengsdk.ad.NativeAd;
import com.ranfeng.adranfengsdk.ad.bean.NativeAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.NativeAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdk.utils.ViewUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.utils.ToastUtil;

import java.util.List;


public class NativeBidAdActivity extends AppCompatActivity {

    private ConstraintLayout rlAdContainer;
    private FrameLayout flMaterialContainer;
    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView ivClose;

    private NativeAd nativeAd;
    private NativeAdInfo nativeAdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_bid);

        rlAdContainer = findViewById(R.id.rlAdContainer);
        flMaterialContainer = findViewById(R.id.flMaterialContainer);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        ivClose = findViewById(R.id.ivClose);

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
        ((TextView) findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取中");

        rlAdContainer.setVisibility(View.GONE);
        nativeAd = new NativeAd(this);
        nativeAd.setMute(false);
        nativeAd.setListener(new NativeAdListener() {
            @Override
            public void onAdReceive(List<NativeAdInfo> adInfos) {
                LogUtil.d(DemoConstant.TAG, "onAdReceive size:" + adInfos.size());

                for (int i = 0; i < adInfos.size(); i++) {
                    LogUtil.d(DemoConstant.TAG, "onAdReceive " + adInfos.get(i).toString());
                }

                if (adInfos != null && adInfos.size() > 0) {
                    nativeAdInfo = adInfos.get(0);
                    ((TextView) findViewById(R.id.btnLoadAd)).setText("1.获取广告，当前价格：" + nativeAdInfo.getBidPrice() + "(分)");
                }
            }

            @Override
            public void onAdExpose(NativeAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdExpose " + adInfo.toString());
            }

            @Override
            public void onAdClick(NativeAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClick " + adInfo.toString());
            }

            @Override
            public void onAdClose(NativeAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClose " + adInfo.toString());
                if (adInfo != null) {
                    adInfo.release();
                }
                rlAdContainer.setVisibility(View.GONE);
            }

            @Override
            public void onRenderFailed(NativeAdInfo adInfo, Error error) {
                LogUtil.d(DemoConstant.TAG, "onRenderFailed error : " + error.toString());
                rlAdContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailed(Error error) {
                ((TextView) findViewById(R.id.btnLoadAd)).setText("1.获取广告，获取广告失败");
                LogUtil.d(DemoConstant.TAG, "onAdFailed error : " + error.toString());
            }
        });
        nativeAd.loadAd(DemoConstant.NATIVE_BID_ID);
    }

    private void bidWinAd() {
        String price = ((EditText) findViewById(R.id.etWinPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞赢价格");
            return;
        }
        if (nativeAdInfo != null) {
            ToastUtil.toast("广告竞赢上报");
            /**
             * 媒体回传二价ecpm，竞价成功后，必须在展示前回传递
             *
             * price 竞赢价格（分）
             */
            nativeAdInfo.sendWinNotice(Integer.valueOf(price));
        }
    }

    private void showAd() {
        if (nativeAdInfo != null) {
            ToastUtil.toast("广告展示");
            rlAdContainer.setVisibility(View.VISIBLE);
            if (nativeAdInfo.isVideo()) {
                View videoView = nativeAdInfo.getMediaView(flMaterialContainer);
                ViewUtil.addAdViewToAdContainer(flMaterialContainer, videoView);
            } else {
                ImageView imageView = new ImageView(flMaterialContainer.getContext());
                ViewGroup.LayoutParams imageViewLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ADRanFengSDK.getInstance().getImageLoader().loadImage(imageView.getContext(), nativeAdInfo.getImageUrl(), imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(imageViewLayoutParams);
                ViewUtil.addAdViewToAdContainer(flMaterialContainer, imageView);
            }
            if (tvTitle != null) {
                tvTitle.setText(nativeAdInfo.getTitle());
            }
            if (tvDesc != null) {
                tvDesc.setText(nativeAdInfo.getDesc());
            }
            nativeAdInfo.registerCloseView(ivClose);
            nativeAdInfo.registerView(rlAdContainer, rlAdContainer);
        }
    }

    private void bidLossAd() {
        String price = ((EditText) findViewById(R.id.etLossPrice)).getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtil.toast("请输入竞败价格");
            return;
        }
        if (nativeAdInfo != null) {
            ToastUtil.toast("广告竞败上报");
            nativeAdInfo.sendLossNotice(Integer.valueOf(price), BidLossNotice.LOW_PRICE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放广告
        if (nativeAd != null) {
            nativeAd.release();
            nativeAd = null;
        }
    }
}
