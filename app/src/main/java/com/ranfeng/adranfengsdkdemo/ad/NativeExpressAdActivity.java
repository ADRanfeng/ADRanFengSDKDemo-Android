package com.ranfeng.adranfengsdkdemo.ad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ranfeng.adranfengsdk.ad.NativeExpressAd;
import com.ranfeng.adranfengsdk.ad.bean.NativeExpressAdInfo;
import com.ranfeng.adranfengsdk.ad.entity.AdSize;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.ad.listener.NativeExpressAdListener;
import com.ranfeng.adranfengsdk.utils.LogUtil;
import com.ranfeng.adranfengsdkdemo.R;
import com.ranfeng.adranfengsdkdemo.adapter.BaseNativeAdAdapter;
import com.ranfeng.adranfengsdkdemo.adapter.NativeExpressAdAdapter;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;
import com.ranfeng.adranfengsdkdemo.widget.MySmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class NativeExpressAdActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {

    private NativeExpressAd nativeExpressAd;
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

        nativeAdAdapter = new NativeExpressAdAdapter();
        recyclerView.setAdapter(nativeAdAdapter);

        initData();
    }

    private void initData() {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        nativeExpressAd = new NativeExpressAd(this, new AdSize(widthPixels, 0));
        nativeExpressAd.setMute(false);
        nativeExpressAd.setListener(new NativeExpressAdListener() {
            @Override
            public void onRenderFailed(NativeExpressAdInfo adInfo, Error error) {
                LogUtil.d(DemoConstant.TAG, "onRenderFailed");
                LogUtil.d(DemoConstant.TAG, "onRenderFailed " + adInfo.toString());
            }

            @Override
            public void onAdReceive(List<NativeExpressAdInfo> adInfos) {
                LogUtil.d(DemoConstant.TAG, "onAdReceive size:" + adInfos.size());
                for (int i = 0; i < adInfos.size(); i++) {
                    LogUtil.d(DemoConstant.TAG, "onAdReceive " + adInfos.get(i).toString());
                    int index = i * 5;
                    NativeExpressAdInfo nativeAdInfo = adInfos.get(i);
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
            public void onAdExpose(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdExpose");
                LogUtil.d(DemoConstant.TAG, "onAdExpose " + adInfo.toString());
            }

            @Override
            public void onAdClick(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClick");
                LogUtil.d(DemoConstant.TAG, "onAdClick " + adInfo.toString());
            }

            @Override
            public void onAdClose(NativeExpressAdInfo adInfo) {
                LogUtil.d(DemoConstant.TAG, "onAdClose");
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
        if (nativeExpressAd != null) {
            nativeExpressAd.release();
            nativeExpressAd = null;
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
        nativeExpressAd.loadAd(DemoConstant.NATIVE_EXPRESS_ID);
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
