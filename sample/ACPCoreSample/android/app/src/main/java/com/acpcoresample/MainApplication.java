package com.acpcoresample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.Signal;
import com.adobe.marketing.mobile.WrapperType;
import com.adobe.marketing.mobile.reactnative.RCTACPCorePackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    return Arrays.<ReactPackage>asList(
                            new MainReactPackage(),
                            new RCTACPCorePackage()
                    );
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }
            };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);

        MobileCore.setApplication(this);
        MobileCore.setLogLevel(LoggingMode.VERBOSE);
        MobileCore.setWrapperType(WrapperType.REACT_NATIVE);

        try {
            Identity.registerExtension();
            Lifecycle.registerExtension();
            Signal.registerExtension();
            MobileCore.start(new AdobeCallback() {
                @Override
                public void call(Object o) {
                    MobileCore.configureWithAppID("yourAppId");
                }
            });
        } catch (InvalidInitException e) {
            Log.e("MainApplication", String.format("Error while registering extensions %s", e.getLocalizedMessage()));
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) { /*no-op*/ }

            @Override
            public void onActivityStarted(Activity activity) { /*no-op*/ }

            @Override
            public void onActivityResumed(Activity activity) {
                MobileCore.setApplication(MainApplication.this);
                MobileCore.lifecycleStart(null);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                MobileCore.lifecyclePause();
            }

            @Override
            public void onActivityStopped(Activity activity) { /*no-op*/ }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) { /*no-op*/ }

            @Override
            public void onActivityDestroyed(Activity activity) { /*no-op*/ }
        });

    }
}
