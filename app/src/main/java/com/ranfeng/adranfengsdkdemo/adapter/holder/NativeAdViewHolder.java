package com.ranfeng.adranfengsdkdemo.adapter.holder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranfeng.adranfengsdk.ADRanFengSDK;
import com.ranfeng.adranfengsdk.ad.bean.NativeAdInfo;
import com.ranfeng.adranfengsdk.ad.listener.NativeVideoAdListener;
import com.ranfeng.adranfengsdk.ad.model.INativeVideoAd;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdk.utils.ViewUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;

/**
 * 信息流自渲染广告ViewHolder
 */
public class NativeAdViewHolder extends RecyclerView.ViewHolder {

    private ConstraintLayout rlAdContainer;
    private FrameLayout flMaterialContainer;
    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView ivClose;

    public NativeAdViewHolder(@NonNull ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad, viewGroup, false));
        rlAdContainer = itemView.findViewById(R.id.rlAdContainer);
        flMaterialContainer = itemView.findViewById(R.id.flMaterialContainer);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDesc = itemView.findViewById(R.id.tvDesc);
        ivClose = itemView.findViewById(R.id.ivClose);
    }

    public void setData(NativeAdInfo nativeAdInfo) {
        if (nativeAdInfo != null) {
            // 注册视频监听
            setVideoListener(nativeAdInfo);
            // 判断是否为视频类广告
            if (nativeAdInfo.isVideo()) {
                // 获取视频视图控件
                View videoView = nativeAdInfo.getMediaView(flMaterialContainer);
                // 添加进布局
                ViewUtil.addAdViewToAdContainer(flMaterialContainer, videoView);
            } else {
                ImageView imageView = new ImageView(flMaterialContainer.getContext());
                ViewGroup.LayoutParams imageViewLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                // 获取图片并展示
                ADRanFengSDK.getInstance().getImageLoader().loadImage(imageView.getContext(), nativeAdInfo.getImageUrl(), imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(imageViewLayoutParams);
                ViewUtil.addAdViewToAdContainer(flMaterialContainer, imageView);
            }
            if (tvTitle != null) {
                // 设置标题
                tvTitle.setText(nativeAdInfo.getTitle());
            }
            if (tvDesc != null) {
                // 设置正文
                tvDesc.setText(nativeAdInfo.getDesc());
            }
            // 注册关闭事件
            nativeAdInfo.registerCloseView(ivClose);
            // 注册视图和点击事件
            nativeAdInfo.registerView(rlAdContainer, rlAdContainer);
        }
    }

    public void setVideoListener(NativeAdInfo nativeAdInfo) {
        if (nativeAdInfo != null && nativeAdInfo.isVideo()) {
            nativeAdInfo.setVideoListener(new NativeVideoAdListener() {
                @Override
                public void onVideoStart(INativeVideoAd nativeVideoAd) {
                    LogUtil.d(DemoConstant.TAG, "onVideoStart");
                }

                @Override
                public void onVideoFinish(INativeVideoAd nativeVideoAd) {
                    LogUtil.d(DemoConstant.TAG, "onVideoFinish");
                }

                @Override
                public void onVideoPause(INativeVideoAd nativeVideoAd) {
                    LogUtil.d(DemoConstant.TAG, "onVideoPause");
                }

                @Override
                public void onVideoError(INativeVideoAd nativeVideoAd) {
                    LogUtil.d(DemoConstant.TAG, "onVideoError");
                }
            });
        }
    }
}
