package com.mroli.android.androidbootstrap.di.modules;

import android.app.Application;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private final Application mApplication;

  public AppModule(final Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

}
