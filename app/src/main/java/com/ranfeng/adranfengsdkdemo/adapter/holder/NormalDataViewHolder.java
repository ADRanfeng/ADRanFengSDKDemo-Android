package com.ranfeng.adranfengsdkdemo.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranfeng.adranfengsdkdemo.R;


/**
 * 普通数据ViewHolder
 */
public class NormalDataViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvNormalData;

    public NormalDataViewHolder(@NonNull ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_normal_data, viewGroup, false));
        tvNormalData = itemView.findViewById(R.id.tvNormalData);
    }

    public void setData(String normalData) {
        tvNormalData.setText(normalData);
    }
}
