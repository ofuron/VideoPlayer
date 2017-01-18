package io.ofu.android.videoplayer;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import io.ofu.android.videoplayer.di.components.AppComponent;
import javax.inject.Inject;

public class VideoPlayerFragment extends Fragment implements TextureView.SurfaceTextureListener, HomeFragmentLifecycle {

  @Inject Application mApplication;

  private SurfaceTexture m_surfaceTexture;

  // Log tag.
  private static final String TAG = VideoPlayerFragment.class.getName();

  // MediaPlayer instance to control playback of video file.
  private MediaPlayer mMediaPlayer;

  public static VideoPlayerFragment newInstance(int pagePosition) {
    VideoPlayerFragment fragment = new VideoPlayerFragment();
    Bundle args = new Bundle();
    args.putInt("KEY_FRAGMENT_POSITION", pagePosition);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.mediaplayer_fragment, container, false);
    TextureView textureView = (TextureView) view.findViewById(R.id.textureView);
    textureView.setSurfaceTextureListener(this);

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    if(null != m_surfaceTexture) {
      setupMediaPlayer(new Surface(m_surfaceTexture));
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if(mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onDestroy() {
    if(mMediaPlayer != null) {
      mMediaPlayer.release();
    }
    super.onDestroy();
  }

  @Override
  public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
    Surface surface = new Surface(surfaceTexture);

    if(surfaceTexture != m_surfaceTexture) {
      m_surfaceTexture = surfaceTexture;
    }

    setupMediaPlayer(surface);
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

  private void setupMediaPlayer(Surface surface) {

    if(mMediaPlayer != null) return;

    try {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setDataSource(getVideoUrl());
      mMediaPlayer.setSurface(surface);
      mMediaPlayer.setLooping(true);
      mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
          mediaPlayer.start();
        }
      });
      mMediaPlayer.prepareAsync();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native String getVideoUrl();

  @Override
  public void onPauseFragment() {
    if(mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  @Override
  public void onResumeFragment() {
    if(mMediaPlayer == null && null != m_surfaceTexture) {
        setupMediaPlayer(new Surface(m_surfaceTexture));
    }
  }
}
