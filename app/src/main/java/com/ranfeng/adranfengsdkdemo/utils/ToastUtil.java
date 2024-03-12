package com.ranfeng.adranfengsdkdemo.utils;

import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.ranfeng.adranfengsdk.ADRanFengSDK;



public class ToastUtil {
    public static void toast(String message) {
        if (ADRanFengSDK.getInstance().getContext() != null && !TextUtils.isEmpty(message)) {
            try {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                    SafeToastUtil.showToast(ADRanFengSDK.getInstance().getContext(), message, Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(ADRanFengSDK.getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
