package io.ofu.android.videoplayer;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;

import java.io.IOException;

import io.ofu.android.videoplayer.di.components.AppComponent;
import javax.inject.Inject;

public class VideoPlayerActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

  @Inject Application mApplication;

  // Log tag.
  private static final String TAG = VideoPlayerActivity.class.getName();

  // Asset video file name.
  private static final String URL = "http://techslides.com/demos/sample-videos/small.mp4";

  // MediaPlayer instance to control playback of video file.
  private MediaPlayer mMediaPlayer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextureView textureView = (TextureView) findViewById(R.id.textureView);
    textureView.setSurfaceTextureListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
    Surface surface = new Surface(surfaceTexture);

    try {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setDataSource(URL);
      mMediaPlayer.setSurface(surface);
      mMediaPlayer.setLooping(true);
      mMediaPlayer.prepareAsync();

      // Play video when the media source is ready for playback.
      mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
          mediaPlayer.start();
        }
      });

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

  }

  @Override
  public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
    return true;
  }

  @Override
  public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

  }
}
