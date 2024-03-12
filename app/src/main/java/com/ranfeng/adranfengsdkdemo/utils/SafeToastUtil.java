package com.ranfeng.adranfengsdkdemo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.reflect.Field;


public class SafeToastUtil {
    private static Field sField_TN;
    private static Field sField_TN_Handler;

    static {
        try {
            sField_TN = Toast.class.getDeclaredField("mTN");
            sField_TN.setAccessible(true);
            sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
            sField_TN_Handler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler) sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn, new SafelyHandlerWrapper(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, CharSequence cs, int length) {
        Toast toast = Toast.makeText(context, cs, length);
        hook(toast);
        toast.show();
    }

    private static class SafelyHandlerWrapper extends Handler {

        private Handler impl;

        SafelyHandlerWrapper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            //需要委托给原Handler执行
            if (impl != null) {
                impl.handleMessage(msg);
            }
        }
    }
}
