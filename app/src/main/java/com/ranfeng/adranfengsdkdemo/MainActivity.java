package com.ranfeng.adranfengsdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ranfeng.adranfengsdk.ADRanFengSDK;
import com.ranfeng.adranfengsdk.ad.error.Error;
import com.ranfeng.adranfengsdk.config.CustomController;
import com.ranfeng.adranfengsdk.config.InitConfig;
import com.ranfeng.adranfengsdk.config.LocationProvider;
import com.ranfeng.adranfengsdk.listener.InitListener;
import com.ranfeng.adranfengsdkdemo.ad.BannerAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.InterstitialAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.NativeAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.NativeBidAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.NativeBidExpressAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.NativeExpressAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.RewardAdActivity;
import com.ranfeng.adranfengsdkdemo.ad.SplashAdActivity;
import com.ranfeng.adranfengsdkdemo.constant.DemoConstant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private List<String> permissionList = new ArrayList<>();
    private static final int REQUEST_CODE = 7722;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initListener();
    }

    /**
     * 初始化JgAds广告
     */
    private void initAd() {
        ADRanFengSDK.getInstance().init(this, new InitConfig.Builder()
                .appId(DemoConstant.APP_ID)
                // 是否开启Debug，开启会有详细的日志信息打印
                // 注意上线后请置为false
                .debug(true)
                //【慎改】是否同意隐私政策，将禁用一切设备信息读起严重影响收益
                .agreePrivacyStrategy(true)
                .isCanUseLocation(true)
                .isCanUsePhoneState(true)
                .setCustomController(new CustomController() {
                    /**
                     * 当isCanUsePhoneState=false时，可传入imei信息，天目使用您传入的imei信息
                     * @return imei信息
                     */
                    @Override
                    public String getDevImei() {
                        return super.getDevImei();
                    }

                    /**
                     * 当isCanUsePhoneState=false时，可传入AndroidId信息，天目使用您传入的AndroidId信息
                     * @return androidid信息
                     */
                    @Override
                    public String getAndroidId() {
                        return super.getAndroidId();
                    }

                    /**
                     * 当isCanUseLocation=false时，可传入地理位置信息，天目使用您传入的地理位置信息
                     * @return 地理位置参数LocationProvider
                     */
                    @Override
                    public LocationProvider getLocation() {
                        return super.getLocation();
                    }

                    /**
                     * 当isCanUseWifiState=false时，可传入Mac地址信息，天目使用您传入的Mac地址信息
                     * @return Mac地址
                     */
                    @Override
                    public String getMacAddress() {
                        return super.getMacAddress();
                    }

                    /**
                     * 开发者可以传入oaid ，若不传或为空值，则不使用oaid信息
                     * @return oaid
                     */
                    @Override
                    public String getDevOaid() {
                        return super.getDevOaid();
                    }

                    /**
                     * 开发者可以传入vaid ，若不传或为空值，则不使用oaid信息
                     * @return vaid
                     */
                    @Override
                    public String getDevVaid() {
                        return super.getDevVaid();
                    }
                })
                .build(), initListener);
    }

    private InitListener initListener = new InitListener() {
        @Override
        public void onInitFinished() {
            // 初始化成功
        }

        @Override
        public void onInitFailed(Error error) {
            // 初始化失败
        }
    };

    /**
     * 申请权限
     */
    private void requestPermission() {
        // 6.0及以上获取没有申请的权限，获取权限可增加填充和收益
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                int checkSelfPermission = ContextCompat.checkSelfPermission(this, permission);
                if (PackageManager.PERMISSION_GRANTED == checkSelfPermission) {
                    continue;
                }
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            // 存在未申请的权限则先申请
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), REQUEST_CODE);
        } else {
            initAd();
        }
    }

    private void initListener() {
        findViewById(R.id.tvSplashAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SplashAdActivity.class));
            }
        });

        findViewById(R.id.tvBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerAdActivity.class));
            }
        });

        findViewById(R.id.tvNative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeAdActivity.class));
            }
        });

        findViewById(R.id.tvNativeExpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeExpressAdActivity.class));
            }
        });
        findViewById(R.id.tvNativeBidExpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeBidExpressAdActivity.class));
            }
        });

        findViewById(R.id.tvNativeBid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeBidAdActivity.class));
            }
        });

        findViewById(R.id.tvInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InterstitialAdActivity.class));
            }
        });

        findViewById(R.id.tvRewardVod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RewardAdActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            // 权限获取成功初始化广告
            initAd();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initListener = null;
    }
}