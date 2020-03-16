/*
Copyright 2019 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/
package com.acpcoresample;

import android.app.Application;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.adobe.marketing.mobile.reactnative.RCTACPCorePackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.MobileCore; // import MobileCore
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.Signal;
import com.adobe.marketing.mobile.WrapperType;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.InvalidInitException;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
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
