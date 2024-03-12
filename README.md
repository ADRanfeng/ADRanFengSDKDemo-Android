# RanFeng Android Sdk——接入文档 V2.1.0.2


## 1. 概述
### 1.1 概述

尊敬的开发者朋友，欢迎您使用然峰广告SDK。通过本文档，您可以快速完成广告SDK的集成。

**注意：本SDK仅支持中国大陆地区**；如需发布到Google Play，请勿引入本SDK及相关依赖文件。

### 2. 支持的广告类型

<table>
  <tr>
    <th style="width:150px">类型</th>
    <th>简介</th>
    <th>适用场景</th>
  </tr>
  <tr>
    <td><a href="#ad_splash">开屏广告</a></td>
    <td>开屏广告以APP启动作为曝光时机的模板广告，需要将开屏广告视图添加到承载的广告容器中，提供5s可感知广告展示</td>
    <td>APP启动界面常会使用开屏广告</td>
  </tr>
  <tr>
    <td><a href="#ad_banner">横幅广告</a></td>
    <td>Banner广告是横向贯穿整个可视页面的模板广告，需要将Banner广告视图添加到承载的广告容器中</td>
    <td>任意界面的固定位置，不建议放在RecyclerView、List这种滚动布局中当item</td>
  </tr>
  <tr>
    <td><a href="#ad_native">信息流自渲染广告</a></td>
    <td>信息流自渲染广告，可自行设计样式，以满足不同的样式需求</td>
    <td>信息流列表，轮播控件，固定位置都是较为适合</td>
  </tr>
  <tr>
    <td><a href="#ad_native_express">信息流模板广告</a></td>
    <td>信息流模板广告，支持上文下图、下图上文、左图右文、右图左文、纯图</td>
    <td>信息流列表，轮播控件，固定位置都是较为适合</td>
  </tr>
  <tr>
    <td><a href="#ad_interstitial">插屏广告</a></td>
    <td>插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，访问其目标网址，也可以将其关闭并返回应用</td>
    <td>在应用执行流程的自然停顿点，适合投放这类广告</td>
  </tr>
  <tr>
    <td><a href="#ad_reward">激励视频广告</a></td>
    <td>将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励</td>
    <td>常出现在游戏的复活、任务等位置，或者网服类APP的一些增值服务场景</td>
  </tr>
</table>


## 3. Demo下载链接

> [Demo-演示APK下载地址](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app-debug.apk)

## 4. SDK版本说明

> [SDK版本更新日志](http://adssp.ranfenghd.com/doc/pages/adsand/)

## 5. SDK接入流程

### 5.1 添加SDK到工程中

接入环境：**Android Studio**，**minSdkVersion 16**



#### 5.1.1 添加仓库地址

首先需要在项目的build.gradle文件中引入如下配置：

```java
allprojects {
    repositories {
        ...
        google()
        jcenter()
    }
}
```



#### 5.1.2 添加ADRanFengSDK

将广告所需要的依赖集成进去

```java
dependencies {
    // support支持库，如果是AndroidX请使用对应的支持库
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.support:design:28.0.0'

    // ADRanFengSDK广告SDK
    implementation(name: 'RanFengSDK-2.1.0.2', ext: 'aar')
}
```



#### 5.1.3 注意事项

* 支持主流架构，x86架构暂不支持

```java
ndk {
    // 设置支持的SO库架构，暂不支持x86
    abiFilters 'armeabi-v7a', 'arm64-v8a'
}
```

* 引入了Android R的 标签,需要对gradle版本进行限制，限制范围为：3.3.3、 3.4.3、 3.5.4、3.6.4、4.0.1 ，开发者根据自身情况酌情升级


### 5.3 权限申请

  使用SDK时可能需要以下权限，为了保证使用广告的正确，请在6.0及以上的手机中使用SDK前及时申请。

```java
<!-- 广告必须的权限，允许网络访问 -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- 广告必须的权限，允许安装未知来源权限（如下载类广告下载完成后唤起安卓） -->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
<!-- 广告必须的权限，地理位置权限，获取位置信息，用于广告投放。精准广告投放及反作弊 -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<!-- 广告必须的权限，为了避免投放错误的广告，以此提高用户的广告体验。若添加此权限，需要在您的用户隐私文档中声明！通过此权限在Android R系统上判定广告对应的应用是否在用户的设备上安装 -->
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />

<!-- 广告可选权限，提升广告填充，允许应用检测网络状态，SDK 会根据网络状态选择是否发送数据 -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- 广告可选权限，提升广告填充，允许应用获取 MAC 地址。广告投放及广告监测归因、反作弊 -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- 广告可选权限，提升广告填充，影响广告填充，强烈建议的权限，获取设备信息，允许应用获取手机状态（包括手机号码、IMEI、IMSI权限等）。广告投放及广告监测归因、反作弊 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<!-- 为了提高广告收益，建议设置的权限，获取粗略位置信息。精准广告投放及反作弊 -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- 广告可选权限，摇一摇权限，用于广告互动 -->
<uses-permission android:name="android.permission.VIBRATE"/>
```



### 5.4 兼容配置



#### 5.4.1 网络配置

需要在 AndroidManifest.xml 添加依赖声明**uses-library android:name="org.apache.http.legacy" android:required="false"**， 且 application标签中添加 **android:usesCleartextTraffic="true"**，适配网络http请求，否则 SDK可能无法正常工作，接入代码示例如下：

```java
<application
    android:name=".MyApplication"
        ... ...
    android:usesCleartextTraffic="true">

    <uses-library
        android:name="org.apache.http.legacy"
        android:required="false" />
    ... ...
</application>
```



#### 5.4.2 混淆配置

如果打包时开启了混淆配置，请按需添加以下混淆内容，并保证广告资源文件不被混淆

```java
-keep class com.ranfeng.adranfengsdk.**{ *; }
-keep class adranfengsdk.ranfeng.com.** { *; }
-keep interface adranfengsdk.ranfeng.com.** { *; }
```



### 5.5 隐私信息控制开关


**为了保证您的App顺利通过检测，结合当前监管关注重点，我们可以将ADRanFengSDK的初始化放在用户同意隐私政策之后。**

**如合规有更高要求，可以使用以下方法进行控制，但会严重降低广告收益，可根据实际需求进行设置，或联系我发运营人员获取建议。**
同时ADRanFengSDK初始化时开放以下接口，确保mac，imei等设备标识不被读取：

```java
ADRanFengSDK.getInstance().init(this, new InitConfig.Builder()
    ...
    //【慎改】是否同意隐私政策，将禁用一切设备信息读起严重影响收益
    .agreePrivacyStrategy(true)
    // 是否可获取定位数据
    .isCanUseLocation(true)
    // 是否可获取设备信息
    .isCanUsePhoneState(true)
    ...
    .build());
```

### 5.6 设备标识

#### 5.6.1 OAID支持

**Android10之后IMEI等数据无法获取，这对广告投放将产生一定影响，所以移动安全联盟(MSA)提出OAID来代替IMEI参与广告投放决策，OAID的支持会在一定程度上影响广告收益和广告填充；**

OAID集成并不繁琐，可参考OAID官网进行集成[链接](http://www.msa-alliance.cn/col.jsp?id=120)

#### <a name="custom_controller"> 5.6.2 向SDK传入设备标识 </a>

- 可选参数设置，配合隐私信息控制开关一起使用

```java
ADRanFengSDK.getInstance().init(this, new InitConfig.Builder()
    ...
    .setCustomController(new CustomController() {
        /**
         * 当isCanUsePhoneState=false时，可传入imei信息，然峰使用您传入的imei信息
         * @return imei信息
         */
        @Override
        public String getDevImei() {
            return super.getDevImei();
        }

        /**
         * 当isCanUsePhoneState=false时，可传入AndroidId信息，然峰使用您传入的AndroidId信息
         * @return androidid信息
         */
        @Override
        public String getAndroidId() {
            return super.getAndroidId();
        }

        /**
         * 当isCanUseLocation=false时，可传入地理位置信息，然峰使用您传入的地理位置信息
         * @return 地理位置参数LocationProvider
         */
        @Override
        public LocationProvider getLocation() {
            return super.getLocation();
        }

        /**
         * 当isCanUseWifiState=false时，可传入Mac地址信息，然峰使用您传入的Mac地址信息
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
    ...
    .build());
```

### 5.7 个性化开关

```java
// 设置个性化广告，true：开启、false：关闭
ADRanFengSDK.setPersonalizedAds(boolean enablePersonalized);
```


## 6. 示例代码



### 6.1 SDK初始化

在Application或Activity中进行SDK的初始化(详情请参考Demo)

#### 6.1.1 初始化主要 API
**ADRanFengSDK**

com.ranfeng.adranfengsdk.ADRanFengSDK

| 方法名         | 介绍 |
| ------------ | ---- |
| init(Context context, InitConfig config) | 构造方法。参数说明：context（初始化SDK的上下文对象）、config（初始化配置信息）。|
| init(Context context, InitConfig config, InitListener listener) | 构造方法。参数说明：context（初始化SDK的上下文对象）、config（初始化配置信息）、listener（初始化状态监听）。|
| setPersonalizedAds(boolean enablePersonalized) | 设置个性化推荐开关。参数说明：enablePersonalized（true：开启，false：关闭， 默认：true）。|
| setCheckCacheApk(boolean checkCacheApk) | 检测下载完成但未安装的下载类广告。参数说明：checkCacheApk（true：开启，false：关闭， 默认：true）。|
| setCanUseLocation(boolean isCanUseLocation) | 设置SDK是否可以使用定位信息，静态方法可随时调用。参数说明：isCanUseLocation（true：开启，false：关闭， 默认：true）。|
| setCanUsePhoneState(boolean isCanUsePhoneState) | 设置SDK是否可以使用IMEI等设备信息，静态方法可随时调用。参数说明：isCanUsePhoneState（true：开启，false：关闭， 默认：true）。|
| setCanUseWifiState(boolean isCanUseWifiState ) | 设置SDK是否可以使用WIFI信息，静态方法可随时调用。参数说明：isCanUseWifiState（true：开启，false：关闭， 默认：true）。|

**InitConfig**

com.ranfeng.adranfengsdk.config.InitConfig

| 方法名         | 介绍 |
| ------------ | ---- |
| appId(String appId) | 设置AppId，必填参数。|
| debug(boolean debug) | 设置是否是Debug模式。参数说明：debug（true：开启，false：关闭， 默认：false）开发阶段以及提交测试阶段可设置为true，方便异常排查。|
| isCanUseLocation(boolean isCanUseLocation) | 设置SDK是否可以使用定位信息。参数说明：isCanUseLocation（true：开启，false：关闭， 默认：true）。|
| isCanUsePhoneState(boolean isCanUsePhoneState) | 设置SDK是否可以使用IMEI等设备信息。参数说明：isCanUsePhoneState（true：开启，false：关闭， 默认：true）。|
| isCanUseWifiState(boolean isCanUseWifiState) | 设置SDK是否可以使用WIFI信息。参数说明：isCanUseWifiState（true：开启，false：关闭， 默认：true）。|
| agreePrivacyStrategy(boolean agreePrivacyStrategy) | 是否同意隐私政策。参数说明：agreePrivacyStrategy（true：开启，false：关闭， 默认：true）。|
| setMultiprocess(boolean isMultiprocess) | 是否支持多进程初始化。参数说明：isMultiprocess（true：支持，false：不支持， 默认：false），应用如需支持多进获取展示广告，请在每个进程中都初始化SDK。|
| setCustomController(CustomController customController) | 自定义设备信息。可选参数。<a href="#custom_controller"> 请参考5.6.2 向SDK传入设备标识 </a>|

**CustomController**

com.ranfeng.adranfengsdk.config.CustomController

| 方法名         | 介绍 |
| ------------ | ---- |
| getDevImei() | 当isCanUsePhoneState=false时，可传入imei信息，然峰使用您传入的imei信息。|
| getAndroidId() | 当isCanUsePhoneState=false时，可传入AndroidId信息，然峰使用您传入的AndroidId信息。|
| getLocation() | 当isCanUseLocation=false时，可传入地理位置信息，然峰使用您传入的地理位置信息。|
| getMacAddress() | 当isCanUseWifiState=false时，可传入Mac地址信息，然峰使用您传入的Mac地址信息。|
| getDevOaid() | 开发者可以传入oaid ，若不传或为空值，则不使用oaid信息。|
| getDevVaid() | 开发者可以传入vaid ，若不传或为空值，则不使用oaid信息。|


#### 6.1.2 初始化接入示例
```java
// 初始化RanFeng广告SDK
ADRanFengSDK.getInstance().init(this, new InitConfig.Builder()
    // 设置AppId，必须的
    .appId(String appId)
    // 是否开启Debug，开启会有详细的日志信息打印
    // 注意上线后请置为false
    .debug(BuildConfig.DEBUG)
    ...
    .build());
```

<p style="color:red;">PS ：AppId通过后台配置生成，初始化必须在主线程中进行。 </p>



### <a name="ad_splash">6.2 开屏广告</a>

开屏广告建议在闪屏页进行展示，开屏广告的宽度和高度取决于容器的宽高，会撑满广告容器；**开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏之类）的75%**，否则onAdFailed方法会被回调。

#### 6.2.1 开屏广告主要 API

**SplashAd**

com.ranfeng.adranfengsdk.ad.SplashAd

| 方法名         | 介绍 |
| ------------ | ---- |
| SplashAd(Context context) | 构造方法。参数说明：context（加载广告的上下文对象）。|
| SplashAd(Context context, View skipView) | 构造方法。参数说明：context（加载广告的上下文对象）、skipView（自定义跳过按钮）。|
| setImmersive(boolean isImmersive) | 设置沉浸效果。参数说明：isImmersive（true：沉浸，false：不沉浸， 目前仅影响默认跳过按钮位置）。|
| setCountDownTime(long countDownTime) | 设置倒计时跳过时长。参数说明：countDownTime（[3000ms~5000ms]，默认5000ms）。|
| setListener(SplashAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| setSensorDisable(boolean isSensorDisable) | 是否禁用传感器。参数说明：isSensorDisable（true：禁用，false：启用，默认false）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| release() | 释放广告。|

**SplashAdListener**

com.ranfeng.adranfengsdk.ad.listener.SplashAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdTick(long millisUntilFinished) | 广告倒计时剩余时长回调。参数说明：millisUntilFinished（剩余时间，单位：秒））。|
| onAdReceive(SplashAdInfo adInfo) | 广告加载成功回调。|
| onAdExpose(SplashAdInfo adInfo) | 广告展示回调。|
| onAdClick(SplashAdInfo adInfo) | 广告点击回调。|
| onAdSkip(SplashAdInfo adInfo) | 广告跳过回调，用户点击跳过按钮时触发。|
| onAdClose(SplashAdInfo adInfo) | 广告关闭回调，用户点击跳过按钮、触发落地页后返回开屏页、倒计时结束，则触发。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**SplashAdInfo**

com.ranfeng.adranfengsdk.ad.bean.SplashAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| sendWinNotice(int price) | 竞赢上报，竞价广告位必须在展示前上报。<br>参数说明：price（回传竞价队列的二价价格（ecpm），单位：分）。|
| sendLossNotice(int price, @BidLossNotice int reason) | 竞败上报。参数说明：price（回传竞赢价格（ecpm），单位：分）、reason（竞败原因）。|
| getBidPrice() | 获取竞价价格（ecpm），单位：分。|
| getBidFloor() | 获取底价（ecpm），单位：分。|
| isOvertime() | 广告是否超时（广告需要在10分钟内展示）。|
| getSplashAdView() | 获取广告视图。|
| render() | 渲染广告，请在上述方法最后调用。|

#### 6.2.2 开屏广告接入示例

```java
SplashAd splashAd = new SplashAd(Context context);
splashAd.setListener(new SplashAdListener() {
    @Override
    public void onAdTick(long millisUntilFinished) {
        // 倒计时剩余时长（单位：秒）
    }

    @Override
    public void onAdReceive(SplashAdInfo splashAdInfo) {
        // 广告获取成功回调，在此回调中展示广告
        // 获取开屏广告视图
        View view = splashAdInfo.getSplashAdView();
        // 将广告视图添加到容器中，注意容器高度要大于屏幕75%
        flContainer.addView(view);
        // 渲染广告，一定要最后调用
        splashAdInfo.render();
    }

    @Override
    public void onAdExpose(SplashAdInfo splashAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(SplashAdInfo splashAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdSkip(SplashAdInfo splashAdInfo) {
        // 广告跳过回调
    }

    @Override
    public void onAdClose(SplashAdInfo splashAdInfo) {
        // 广告关闭回调，可在此处进入应用首页
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调，可在此处进入应用首页
    }
});
splashAd.loadAd(String posId);
```

> [开屏广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/SplashAdActivity.java)



### <a name="ad_banner">6.3 横幅广告</a>

横幅广告建议放置在 **固定位置**，而非ListView、RecyclerView、ViewPager等控件中充当Item，横幅广告支持多种尺寸比例，可在后台创建广告位时配置，Banner广告的宽度将会撑满容器，高度自适应，建议Banner广告容器高度也为自适应。

#### 6.3.1 横幅广告主要 API

**BannerAd**

com.ranfeng.adranfengsdk.ad.BannerAd

| 方法名         | 介绍 |
| ------------ | ---- |
| BannerAd(Context context, ViewGroup container) | 构造方法。参数说明：context（加载广告的上下文对象）、container（横幅广告展示容器）。|
| BannerAd(Context context) | 构造方法，加载展示分离使用。参数说明：context（加载广告的上下文对象）。|
| setListener(BannerAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| release() | 释放广告。|

**BannerAdListener**

com.ranfeng.adranfengsdk.ad.listener.BannerAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdReceive(BannerAdInfo adInfo) | 广告加载成功回调。|
| onAdExpose(BannerAdInfo adInfo) | 广告展示回调。|
| onAdClick(BannerAdInfo adInfo) | 广告点击回调。|
| onAdClose(BannerAdInfo adInfo) | 广告关闭回调。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**BannerAdInfo**

com.ranfeng.adranfengsdk.ad.bean.BannerAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| getAdView() | 获取广告视图。|
| render() | 渲染广告，请在上述方法最后调用。|


#### 6.3.2 横幅广告接入示例
```java
BannerAd bannerAd = new BannerAd(Context context, ViewGroup container);
bannerAd.setListener(new BannerAdListener() {
    @Override
    public void onAdExpose(BannerAdInfo bannerAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(BannerAdInfo bannerAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdClose(BannerAdInfo bannerAdInfo) {
        // 广告关闭回调
    }

    @Override
    public void onAdReceive(BannerAdInfo bannerAdInfo) {
        // 广告获取成功回调
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调
    }
});
bannerAd.loadAd(String posId);
```


>[横幅广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/BannerAdActivity.java)




### <a name="ad_native">6.4 信息流自渲染广告</a>

信息流自渲染广告，可根据NativeAdInfo返回的素材自定义样式。
**注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除**

#### 6.4.1 信息流自渲染广告主要 API

**NativeAd**

com.ranfeng.adranfengsdk.ad.NativeAd

| 方法名         | 介绍 |
| ------------ | ---- |
| NativeAd(Context context) | 构造方法。参数说明：context（加载广告的上下文对象）。|
| setMute(boolean isMute) | 视频静音设置。参数说明：isMute（true：静音、false：不静音，默认：true）。|
| setListener(NativeAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| release() | 释放广告。|

**NativeAdListener**

com.ranfeng.adranfengsdk.ad.listener.NativeAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdReceive(List\<NativeAdInfo> adInfos) | 广告加载成功回调。|
| onAdExpose(NativeAdInfo adInfo) | 广告展示回调。|
| onAdClick(NativeAdInfo adInfo) | 广告点击回调。|
| onAdClose(NativeAdInfo adInfo) | 广告关闭回调，在此回调中移除页面中的视图。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|
| onRenderFailed(NativeAdInfo adInfo, Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**NativeAdInfo**

com.ranfeng.adranfengsdk.ad.bean.NativeAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| sendWinNotice(int price) | 竞赢上报，竞价广告位必须在展示前上报。<br>参数说明：price（回传竞价队列的二价价格（ecpm），单位：分）。|
| sendLossNotice(int price, @BidLossNotice int reason) | 竞败上报。参数说明：price（回传竞赢价格（ecpm），单位：分）、reason（竞败原因）。|
| getBidPrice() | 获取竞价价格（ecpm），单位：分。|
| getBidFloor() | 获取底价（ecpm），单位：分。|
| isOvertime() | 广告是否超时（广告需要在10分钟内展示）。|
| isVideo() | 判断素材是否包含视频。|
| getMediaView(ViewGroup container) | 获取素材中的视频，获取前请通过isVideo()方法进行判断。|
| setVideoListener(VideoAdListener listener) | 设置视频相关状态回调。参数说明：listener（视频状态监听器）|
| getTitle() | 广告标题。|
| getDesc() | 广告描述。|
| getImageUrl() | 广告图片。|
| getImageUrls() | 广告图片组。|
| getAdSource() | 广告来源。|
| getAdTarget() | 广告标识，当getAdSource()不为空时，该字段将返回“广告”|
| registerCloseView(View ivClose) | 注册视图关闭事件。|
| registerView(ViewGroup viewGroup, View... views) | 注册视图曝光和点击事件。参数说明：viewGroup（曝光检测视图）、views（广告可被点击视图）。|

**NativeVideoAdListener**

com.ranfeng.adranfengsdk.ad.listener.NativeVideoAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onVideoStart(INativeVideoAd nativeVideoAd) | 视频播放开始回调。|
| onVideoFinish(INativeVideoAd nativeVideoAd) | 视频播放完成回调。|
| onVideoPause(INativeVideoAd nativeVideoAd) | 视频暂停回调。|
| onVideoError(INativeVideoAd nativeVideoAd) | 视频播放异常。|

#### 6.4.2 信息流自渲染广告接入示例
```java
NativeAd nativeAd = new NativeAd(Context context);
nativeAd.setListener(new NativeAdListener() {

    @Override
    public void onRenderFailed(NativeAdInfo nativeAdInfo, Error error) {
        // 广告渲染失败回调
    }

    @Override
    public void onAdReceive(List<NativeAdInfo> nativeAdInfos) {
        // 广告获取成功回调
        if (nativeAdInfos == null || nativeAdInfos.size() == 0) {
            return;
        }
        NativeAdInfo nativeAdInfo = nativeAdInfos.get(0);
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
            if (tvAdTarget != null) {
                // 设置渠道
                tvAdTarget.setText(nativeAdInfo.getAdTarget());
            }
            // 注册关闭事件
            nativeAdInfo.registerCloseView(ivClose);
            // 注册视图和点击事件
            nativeAdInfo.registerView(rlAdContainer, rlAdContainer);
        }
    }

    @Override
    public void onAdExpose(NativeAdInfo nativeAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(NativeAdInfo nativeAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdClose(NativeAdInfo nativeAdInfo) {
        // 广告关闭回调
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调
    }
});
nativeAd.loadAd(String posId);
```

> [信息流自渲染广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/NativeAdActivity.java)




### <a name="ad_native_express">6.5 信息流模板广告</a>

信息流模板广告，模板样式则是返回拼装好的广告视图，开发者只需将视图添加到相应容器即可，模板样式的容器高度建议是自适应。
**注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除``**

#### 6.5.1 信息流模板广告主要 API

**NativeExpressAd**

com.ranfeng.adranfengsdk.ad.NativeExpressAd

| 方法名         | 介绍 |
| ------------ | ---- |
| NativeExpressAd(Context context, AdSize adSize) | 构造方法。参数说明：context（加载广告的上下文对象）、adSize（广告容器宽高）。|
| setMute(boolean isMute) | 视频静音设置。参数说明：isMute（true：静音、false：不静音，默认：true）。|
| setListener(NativeExpressAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| release() | 释放广告。|

**AdSize**

com.ranfeng.adranfengsdk.ad.entity.AdSize

| 方法名         | 介绍 |
| ------------ | ---- |
| AdSize(int width, int height)| 构造方法。参数说明：<br>width（容器宽度，单位：px）请传入实际宽度、<br>height（容器高度，单位：px）建议传入0自适应高度。|


**NativeExpressAdListener**

com.ranfeng.adranfengsdk.ad.listener.NativeExpressAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdReceive(List\<NativeExpressAdInfo> adInfos) | 广告加载成功回调。|
| onAdExpose(NativeExpressAdInfo adInfo) | 广告展示回调。|
| onAdClick(NativeExpressAdInfo adInfo) | 广告点击回调。|
| onAdClose(NativeExpressAdInfo adInfo) | 广告关闭回调，在此回调中移除页面中的视图。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|
| onRenderFailed(NativeExpressAdInfo adInfo, Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**NativeExpressAdInfo**

com.ranfeng.adranfengsdk.ad.bean.NativeExpressAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| sendWinNotice(int price) | 竞赢上报，竞价广告位必须在展示前上报。<br>参数说明：price（回传竞价队列的二价价格（ecpm），单位：分）。|
| sendLossNotice(int price, @BidLossNotice int reason) | 竞败上报。参数说明：price（回传竞赢价格（ecpm），单位：分）、reason（竞败原因）。|
| getBidPrice() | 获取竞价价格（ecpm），单位：分。|
| getBidFloor() | 获取底价（ecpm），单位：分。|
| isOvertime() | 广告是否超时（广告需要在10分钟内展示）。|
| getNativeExpressAdView() | 获取广告视图。|
| render() | 渲染广告，请在上述方法最后调用。|

**NativeVideoAdListener**

com.ranfeng.adranfengsdk.ad.listener.NativeVideoAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onVideoStart(INativeVideoAd nativeVideoAd) | 视频播放开始回调。|
| onVideoFinish(INativeVideoAd nativeVideoAd) | 视频播放完成回调。|
| onVideoPause(INativeVideoAd nativeVideoAd) | 视频暂停回调。|
| onVideoError(INativeVideoAd nativeVideoAd) | 视频播放异常。|

#### 6.5.2 信息流模板广告接入示例
```java
NativeExpressAd nativeExpressAd = new NativeExpressAd(Context context, new AdSize(int width, int height));
nativeExpressAd.setListener(new NativeExpressAdListener() {
    @Override
    public void onRenderFailed(NativeExpressAdInfo nativeExpressAdInfo, Error error) {
        // 广告渲染失败
    }

    @Override
    public void onAdReceive(List<NativeExpressAdInfo> nativeExpressAdInfos) {
        // 广告获取成功回调
        if (nativeExpressAdInfos == null || nativeExpressAdInfos.size() == 0) {
            return;
        }
        NativeExpressAdInfo nativeExpressAdInfo = nativeExpressAdInfos.get(0);
        if (nativeExpressAdInfo != null) {
            // 获取广告视图
            View view = nativeExpressAdInfo.getNativeExpressAdView();
            // 将广告视图添加到容器中
            ((ViewGroup) itemView).addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // 渲染广告，一定要最后调用
            nativeExpressAdInfo.render();
        }
    }

    @Override
    public void onAdExpose(NativeExpressAdInfo nativeExpressAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(NativeExpressAdInfo nativeExpressAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdClose(NativeExpressAdInfo nativeExpressAdInfo) {
        // 广告关闭回调
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调
    }
});
nativeExpressAd.loadAd(String posId);
```

> [信息流模板广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/NativeExpressAdActivity.java)



### <a name="ad_interstitial">6.6 插屏广告示例</a>

插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，也可以将其关闭并返回应用。

#### 6.6.1 插屏广告主要 API

**InterstitialAd**

com.ranfeng.adranfengsdk.ad.InterstitialAd

| 方法名         | 介绍 |
| ------------ | ---- |
| InterstitialAd(Context context) | 构造方法。参数说明：context（加载广告的上下文对象）。|
| setMute(boolean isMute) | 视频静音设置。参数说明：isMute（true：静音、false：不静音，默认：true）。|
| setListener(InterstitialAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| setSensorDisable(boolean isSensorDisable) | 是否禁用传感器。参数说明：isSensorDisable（true：禁用，false：启用，默认false）。|
| setShowDirection(int showDirection) | 展示方向。参数说明：showDirection（Config.ScreenOrientation.PORTRAIT：竖屏展示，Config.ScreenOrientation.LANDSCAPE：横屏展示，默认竖屏展示）。|
| setAutoClose(boolean autoClose) | 自动关闭插屏，默认5秒倒计时。参数说明：autoClose（true：启用、false：禁用，默认：false）。|
| setAutoClose(boolean autoClose, int second) | 自动关闭插屏。参数说明：autoClose（true：启用、false：禁用，默认：false）、second（自动关闭时长，范围[3, 100]秒）。|
| release() | 释放广告。|

**InterstitialAdListener**

com.ranfeng.adranfengsdk.ad.listener.InterstitialAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdReceive(InterstitialAdInfo adInfo) | 广告加载成功回调。|
| onAdExpose(InterstitialAdInfo adInfo) | 广告展示回调。|
| onAdClick(InterstitialAdInfo adInfo) | 广告点击回调。|
| onAdClose(InterstitialAdInfo adInfo) | 广告关闭回调。|
| onVideoStart(InterstitialAdInfo adInfo) | 视频播放开始回调。|
| onVideoFinish(InterstitialAdInfo adInfo) | 视频播放完成回调。|
| onVideoPause(InterstitialAdInfo adInfo) | 视频暂停回调。|
| onVideoError(InterstitialAdInfo adInfo) | 视频播放异常。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**InterstitialAdInfo**

com.ranfeng.adranfengsdk.ad.bean.InterstitialAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| sendWinNotice(int price) | 竞赢上报，竞价广告位必须在展示前上报。<br>参数说明：price（回传竞价队列的二价价格（ecpm），单位：分）。|
| sendLossNotice(int price, @BidLossNotice int reason) | 竞败上报。参数说明：price（回传竞赢价格（ecpm），单位：分）、reason（竞败原因）。|
| getBidPrice() | 获取竞价价格（ecpm），单位：分。|
| getBidFloor() | 获取底价（ecpm），单位：分。|
| isOvertime() | 广告是否超时（广告需要在10分钟内展示）。|
| showInterstitial(Activity activity) | 展示广告。参数说明：activity（需要展示插屏的页面）。|

#### 6.6.2 插屏广告接入示例
```java
InterstitialAd interstitialAd = new InterstitialAd(this);
interstitialAd.setListener(new InterstitialAdListener() {
    @Override
    public void onAdReceive(InterstitialAdInfo interstitialAdInfo) {
        // 广告获取成功回调
        if (interstitialAdInfo != null) {
            interstitialAdInfo.showInterstitial(Activity activity);
        }
    }

    @Override
    public void onAdExpose(InterstitialAdInfo interstitialAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(InterstitialAdInfo interstitialAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdClose(InterstitialAdInfo interstitialAdInfo) {
        // 广告关闭回调
    }

    @Override
    public void onVideoStart(InterstitialAdInfo interstitialAdInfo) {
        // 视频开始播放
    }

    @Override
    public void onVideoFinish(InterstitialAdInfo interstitialAdInfo) {
        // 视频播放完毕
    }

    @Override
    public void onVideoPause(InterstitialAdInfo interstitialAdInfo) {
        // 视频暂停
    }

    @Override
    public void onVideoError(InterstitialAdInfo interstitialAdInfo) {
        // 视频错误
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调
    }
});
interstitialAd.loadAd(String posId);
```

> [插屏广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/InterstitialAdActivity.java)



### <a name="ad_reward">6.7 激励视频广告示例</a>

将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励。

#### 6.7.1 激励视频广告主要 API

**RewardAd**

com.ranfeng.adranfengsdk.ad.RewardAd

| 方法名         | 介绍 |
| ------------ | ---- |
| RewardAd(Context context) | 构造方法。参数说明：context（加载广告的上下文对象）。|
| setMute(boolean isMute) | 视频静音设置。参数说明：isMute（true：静音、false：不静音，默认：true）。|
| setSensorDisable(boolean isSensorDisable) | 是否禁用传感器。参数说明：isSensorDisable（true：禁用，false：启用，默认false）。|
| setListener(RewardAdListener listener) | 设置广告相关状态。参数说明：listener（广告状态监听器）。|
| loadAd(String posId) | 请求广告。参数说明：posId（广告位ID）。|
| release() | 释放广告。|

**RewardAdListener**

com.ranfeng.adranfengsdk.ad.listener.RewardAdListener

| 方法名         | 介绍 |
| ------------ | ---- |
| onAdReceive(RewardAdInfo adInfo) | 广告加载成功回调。|
| onAdExpose(RewardAdInfo adInfo) | 广告展示回调。|
| onAdClick(RewardAdInfo adInfo) | 广告点击回调。|
| onAdClose(RewardAdInfo adInfo) | 广告关闭回调。|
| onAdReward(RewardAdInfo adInfo) | 广告奖励回调。|
| onVideoStart(RewardAdInfo adInfo) | 视频播放开始回调。|
| onVideoFinish(RewardAdInfo adInfo) | 视频播放完成回调。|
| onVideoPause(RewardAdInfo adInfo) | 视频暂停回调。|
| onVideoError(RewardAdInfo adInfo) | 视频播放异常。|
| onAdFailed(Error error) | 广告失败回调。参数说明：error（广告错误信息）。|

**InterstitialAdInfo**

com.ranfeng.adranfengsdk.ad.bean.RewardAdInfo

| 方法名         | 介绍 |
| ------------ | ---- |
| sendWinNotice(int price) | 竞赢上报，竞价广告位必须在展示前上报。<br>参数说明：price（回传竞价队列的二价价格（ecpm），单位：分）。|
| sendLossNotice(int price, @BidLossNotice int reason) | 竞败上报。参数说明：price（回传竞赢价格（ecpm），单位：分）、reason（竞败原因）。|
| getBidPrice() | 获取竞价价格（ecpm），单位：分。|
| getBidFloor() | 获取底价（ecpm），单位：分。|
| isOvertime() | 广告是否超时（广告需要在10分钟内展示）。|
| showRewardAd(Activity activity) | 展示广告。参数说明：activity（需要展示激励视频的页面）。|

#### 6.7.2 激励视频广告接入示例
```java
RewardAd rewardAd = new RewardAd(this);
rewardAd.setListener(new RewardAdListener() {
    @Override
    public void onAdReceive(RewardAdInfo rewardAdInfo) {
        // 广告获取成功回调
        if (rewardAdInfo != null) {
            rewardAdInfo.showRewardAd(Activity activity);
        }
    }

    @Override
    public void onAdReward(RewardAdInfo rewardAdInfo) {
        // 广告奖励回调
    }

    @Override
    public void onAdExpose(RewardAdInfo rewardAdInfo) {
        // 广告展示回调
    }

    @Override
    public void onAdClick(RewardAdInfo rewardAdInfo) {
        // 广告点击回调
    }

    @Override
    public void onAdClose(RewardAdInfo rewardAdInfo) {
        // 广告关闭回调
    }

    @Override
    public void onVideoCompleted(RewardAdInfo rewardAdInfo) {
        // 视频播放完毕回调
    }

    @Override
    public void onVideoSkip(RewardAdInfo rewardAdInfo) {
        // 视频跳过回调
    }

    @Override
    public void onVideoError(RewardAdInfo rewardAdInfo, String msg) {
        // 视频播放错误回调
    }

    @Override
    public void onAdFailed(Error error) {
        // 广告失败回调
    }
});
rewardAd.loadAd(String posId);
```


> [激励视频广告示例详情](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/app/src/main/java/com/ranfeng/adranfengsdkdemo/ad/RewardAdActivity.java)



### 6.8 备注

具体的接入代码和流程，请参考Demo

## 7. 广告竞价

然峰开屏、信息流、插屏、激励视频支持广告竞价功能，可以参考demo进行对接

以开屏广告为例：

1.在每次loadAd时，都会在onAdReceive回调中会返回splashAdInfo广告对象，广告对象中会存放广告素材和价格信息

```java
// 获取广告价格（分）
splashAdInfo.getBidPrice();
```

2.获取到价格后可参与比价，比价成功后请先调用竞赢上报，否则广告将无法使用
```java
/**
 * 媒体回传竞价胜利的ecpm，竞价成功后，必须在展示前回传递
 * price 竞赢价格（分）
 */
splashAdInfo.sendWinNotice(int price);
```

3.如果失败，或其他情况，请调用竞败上报

```java
/**
 * price 三方竞赢价格（分）
 * msg 失败原因 LOW_PRICE竞价失败、OTHER其他
 */
splashAdInfo.sendLossNotice(int price, BidLossNotice bidLossNotice);
```

4.展示广告

```java
View view = splashAdInfo.getSplashAdView();
flContainer.addView(view);
splashAdInfo.render();
```

## 8. 常见问题和错误调试

> [常见问题和错误调试及错误码](https://github.com/ADRanfeng/ADRanFengSDKDemo-Android/blob/master/ErrorCode/ErrorCode.md)


## 9.商务合作

邮箱 : business@ssdmobile.cn

