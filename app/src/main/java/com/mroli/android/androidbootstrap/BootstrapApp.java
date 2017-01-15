package com.mroli.android.androidbootstrap;

import android.app.Application;
import com.mroli.android.androidbootstrap.di.components.AppComponent;
import com.mroli.android.androidbootstrap.di.components.DaggerAppComponent;
import com.mroli.android.androidbootstrap.di.modules.AppModule;

/**
 * Created by olivier on 11/30/16.
 */
public class BootstrapApp extends Application {

  private AppComponent mAppComponent;

  @Override public void onCreate() {
    super.onCreate();

    // Put Dagger2 init here
    mAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

}
