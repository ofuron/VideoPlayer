package com.mroli.android.androidbootstrap.di.components;

import android.app.Application;
import com.mroli.android.androidbootstrap.MainActivity;
import com.mroli.android.androidbootstrap.di.modules.AppModule;
import dagger.Component;
import dagger.Provides;

@Component(modules = { AppModule.class } )
public interface AppComponent {

  void inject(MainActivity activity);

}
