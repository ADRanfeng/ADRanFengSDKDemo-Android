package com.ranfeng.adranfengsdkdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ranfeng.adranfengsdk.ad.bean.NativeExpressAdInfo;
import com.ranfeng.adranfengsdkdemo.adapter.holder.NativeExpressAdViewHolder;
import com.ranfeng.adranfengsdkdemo.adapter.holder.NormalDataViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 信息流模板广告Adapter
 */
public class NativeExpressAdAdapter extends BaseNativeAdAdapter {
    /**
     * 普通数据类型（模拟数据）
     */
    private static final int ITEM_VIEW_TYPE_NORMAL_DATA = 0;
    /**
     * 信息流模板广告类型
     */
    private static final int ITEM_VIEW_TYPE_EXPRESS_AD = 3;

    private List<Object> dataList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_EXPRESS_AD:
                return new NativeExpressAdViewHolder(viewGroup);
            default:
                return new NormalDataViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object item = dataList.get(position);
        if (viewHolder instanceof NormalDataViewHolder) {
            // 普通数据类型的
            ((NormalDataViewHolder) viewHolder).setData((String) item);
        } else if (viewHolder instanceof NativeExpressAdViewHolder) {
            // 信息流模板广告类型
            ((NativeExpressAdViewHolder) viewHolder).setData((NativeExpressAdInfo) item);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = dataList.get(position);
        if  (item instanceof NativeExpressAdInfo) {
            // item 为信息流模板广告数据
            return ITEM_VIEW_TYPE_EXPRESS_AD;
        } else {
            // item 为模拟数据
            return ITEM_VIEW_TYPE_NORMAL_DATA;
        }
    }

    /**
     * 移除广告所在的对象，一般模板广告有可能会渲染失败
     */
    @Override
    public void removeData(Object adSuyiNativeAdInfo) {
        if (dataList.contains(adSuyiNativeAdInfo) && adSuyiNativeAdInfo instanceof NativeExpressAdInfo) {
            // 释放广告Info对象
            ((NativeExpressAdInfo)adSuyiNativeAdInfo).release();
            // 从数据源中移除
            dataList.remove(adSuyiNativeAdInfo);
            // 通知刷新Adapter
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新数据
     */
    @Override
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    @Override
    public void addData(List<Object> datas) {
        int startPosition = dataList.size();
        dataList.addAll(datas);
        if (startPosition <= 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(startPosition + 1, dataList.size() - startPosition);
        }
    }

}
