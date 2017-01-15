package io.ofu.android.videoplayer.di.components;

import io.ofu.android.videoplayer.VideoPlayerActivity;
import io.ofu.android.videoplayer.di.modules.AppModule;
import dagger.Component;

@Component(modules = { AppModule.class } )
public interface AppComponent {

  void inject(VideoPlayerActivity activity);

}
