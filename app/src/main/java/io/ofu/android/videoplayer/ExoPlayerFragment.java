package io.ofu.android.videoplayer;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;

public class ExoPlayerFragment extends Fragment implements ExoPlayer.EventListener, HomeFragmentLifecycle {

  private SimpleExoPlayer m_exoPlayer;
  private SimpleExoPlayerView mPlayerView;
  private Handler mMainHandler;
  private DataSource.Factory mediaDataSourceFactory;
  private String userAgent;
  private MediaSource mMediaSource;

  // Log tag.
  private static final String TAG = ExoPlayerFragment.class.getName();
  private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();


  public static ExoPlayerFragment newInstance(int pagePosition) {
    ExoPlayerFragment fragment = new ExoPlayerFragment();
    Bundle args = new Bundle();
    args.putInt("KEY_FRAGMENT_POSITION", pagePosition);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.exoplayer_fragment, container, false);

    mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player_view);

    setupPlayer();

    return view;
  }

  @Override
  public void onDestroy() {
    if(m_exoPlayer != null) {
      m_exoPlayer.release();
    }
    super.onDestroy();
  }

  private void setupPlayer() {

    if(m_exoPlayer == null) {
      Uri uri = Uri.parse("http://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7.8506521BFC350652163895D4C26DEE124209AA9E&key=ik0");
      mMediaSource = new DashMediaSource(uri,
          buildDataSourceFactory(false),
          new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
          mMainHandler,
          null);

      userAgent = getUserAgent(getContext(), "ExoPlayer");
      mMainHandler = new Handler();
      mediaDataSourceFactory = buildDataSourceFactory(true);

      TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
      TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
      LoadControl loadControl = new DefaultLoadControl();

      m_exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
      m_exoPlayer.setPlayWhenReady(true);

      mPlayerView.setPlayer(m_exoPlayer);

      m_exoPlayer.prepare(mMediaSource);
    }
  }


  @Override
  public void onTimelineChanged(Timeline timeline, Object manifest) {

  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

  }

  @Override
  public void onLoadingChanged(boolean isLoading) {

  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

  }

  @Override
  public void onPlayerError(ExoPlaybackException error) {

  }

  @Override
  public void onPositionDiscontinuity() {

  }

  private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
    return new DefaultDataSourceFactory(getContext(), useBandwidthMeter ? BANDWIDTH_METER : null,
        buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null));
  }

  public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
  }

  /**
   * Returns a user agent string based on the given application name and the library version.
   *
   * @param context A valid context of the calling application.
   * @param applicationName String that will be prefix'ed to the generated user agent.
   * @return A user agent string generated using the applicationName and the library version.
   */
  public static String getUserAgent(Context context, String applicationName) {
    String versionName;
    try {
      String packageName = context.getPackageName();
      PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
      versionName = info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      versionName = "?";
    }
    return applicationName + "/" + versionName + " (Linux;Android " + Build.VERSION.RELEASE
        + ") " + "ExoPlayerLib/" + ExoPlayerLibraryInfo.VERSION;
  }

  @Override
  public void onPauseFragment() {
    if(m_exoPlayer != null) {
      m_exoPlayer.release();
      m_exoPlayer = null;
    }
  }

  @Override
  public void onResumeFragment() {
    if(m_exoPlayer != null) {
      m_exoPlayer.release();
      m_exoPlayer = null;
    }

    setupPlayer();
  }
}
