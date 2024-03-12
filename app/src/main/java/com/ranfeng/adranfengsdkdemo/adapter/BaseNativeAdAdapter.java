package com.ranfeng.adranfengsdkdemo.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseNativeAdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public abstract void removeData(Object adSuyiNativeAdInfo);

    public abstract void clearData();

    public abstract void addData(List<Object> datas);
}
