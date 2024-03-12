package com.ranfeng.adranfengsdkdemo.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranfeng.adranfengsdk.ad.bean.NativeExpressAdInfo;
import com.ranfeng.adranfengsdk.utils.ViewUtil;
import com.ranfeng.adranfengsdkdemo.R;

/**
 * 信息流模板广告ViewHolder
 */
public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

    public NativeExpressAdViewHolder(@NonNull ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_express_ad, viewGroup, false));
    }

    public void setData(NativeExpressAdInfo nativeExpressAdInfo) {
        if (nativeExpressAdInfo != null) {
            View view = nativeExpressAdInfo.getNativeExpressAdView();
            ViewUtil.addAdViewToAdContainer(((ViewGroup) itemView), view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nativeExpressAdInfo.render();
        }
    }
}
