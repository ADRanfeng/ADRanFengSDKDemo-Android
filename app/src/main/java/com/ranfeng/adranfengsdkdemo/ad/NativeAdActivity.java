package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ranfeng.adranfengsdk.ad.NativeAd;
import com.ranfeng.adranfengsdk.ad.bean.NativeAdInfo;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.NativeAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.adapter.BaseNativeAdAdapter;
import com.ranfeng.adranfengsdkdemo.adapter.NativeAdAdapter;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.widget.MySmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class NativeAdActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {

    private NativeAd nativeAd;
    private List<Object> tempDataList = new ArrayList<>();
    private int refreshType;

    private MySmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private BaseNativeAdAdapter nativeAdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_express);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);

        nativeAdAdapter = new NativeAdAdapter();
        recyclerView.setAdapter(nativeAdAdapter);

        initData();
    }

    private void initData() {
        nativeAd = new NativeAd(this);
        nativeAd.setMute(false);
        nativeAd.setListener(new NativeAdListener() {

            @Override
            public void onRenderFailed(NativeAdInfo nativeAdInfo, Error error) {
                LogUtil.d(DemoConstant.TAG, "onRenderFailed");
                LogUtil.d(DemoConstant.TAG, "onRenderFailed " + nativeAdInfo.toString());
            }

            @Override
            public void onAdReceive(List<NativeAdInfo> adInfos) {
                LogUtil.d(DemoConstant.TAG, "onAdReceive size:" + adInfos.size());
                for (int i = 0; i < adInfos.size(); i++) {
                    LogUtil.d(DemoConstant.TAG, "onAdReceive " + adInfos.get(i).toString());
                    int index = i * 5;
                    NativeAdInfo nativeAdInfo = adInfos.get(i);
                    if (index >= tempDataList.size()) {
                        tempDataList.add(nativeAdInfo);
                    } else {
                        tempDataList.add(index, nativeAdInfo);
                    }
                }
                nativeAdAdapter.addData(tempDataList);
                refreshLayout.finish(refreshType, true, false);
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
                nativeAdAdapter.removeData(adInfo);
            }

            @Override
            public void onAdFailed(Error error) {
                LogUtil.d(DemoConstant.TAG, "onAdFailed error : " + error.toString());
                nativeAdAdapter.addData(tempDataList);
                refreshLayout.finish(refreshType, false, false);
            }
        });

        // 触发刷新
        refreshLayout.autoRefresh();
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshType = MySmartRefreshLayout.TYPE_LOAD_MORE;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshType = MySmartRefreshLayout.TYPE_FRESH;
        nativeAdAdapter.clearData();
        loadData();
    }

    /**
     * 加载数据和广告
     */
    private void loadData() {
        tempDataList.clear();
        mockNormalDataRequest();
        nativeAd.loadAd(DemoConstant.NATIVE_ID);
    }

    /**
     * 模拟普通数据请求
     */
    private void mockNormalDataRequest() {
        for (int i = 0; i < 20; i++) {
            tempDataList.add("模拟的普通数据 : " + (nativeAdAdapter == null ? 0 : nativeAdAdapter.getItemCount() + i));
        }
    }
}
