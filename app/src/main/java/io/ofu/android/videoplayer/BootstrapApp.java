package io.ofu.android.videoplayer;

import android.app.Application;
import io.ofu.android.videoplayer.di.components.AppComponent;
import io.ofu.android.videoplayer.di.components.DaggerAppComponent;
import io.ofu.android.videoplayer.di.modules.AppModule;


public class BootstrapApp extends Application {

  private AppComponent mAppComponent;

  @Override public void onCreate() {
    super.onCreate();

    System.loadLibrary("native-lib");

    // Put Dagger2 init here
    mAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }


  public AppComponent getAppComponent() {
    return mAppComponent;
  }
}
