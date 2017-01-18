package io.ofu.android.videoplayer;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import javax.inject.Inject;

public class HomeActivity extends FragmentActivity {

  @Inject
  Application mApplication;

  PlayerPageAdapter mAdapter;
  ViewPager mPager;

  private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

    int currentPosition = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
      Fragment oldfragment = mAdapter.getItem(currentPosition);
      if(oldfragment instanceof HomeFragmentLifecycle) {
        ((HomeFragmentLifecycle) oldfragment).onPauseFragment();
      }

      Fragment newfragment = mAdapter.getItem(position);
      if(newfragment instanceof HomeFragmentLifecycle) {
        ((HomeFragmentLifecycle) newfragment).onResumeFragment();
      }

      currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ((BootstrapApp) getApplication()).getAppComponent().inject(this);

    setContentView(R.layout.activity_main);

    mPager = (ViewPager) findViewById(R.id.view_pager);
    mPager.addOnPageChangeListener(mPageChangeListener);

    mAdapter = new PlayerPageAdapter(getSupportFragmentManager());
    mAdapter.addPage(new PlayerPageAdapter.HomeTabPage() {
      @Override
      public Fragment createFragment() {
        return VideoPlayerFragment.newInstance(0);
      }
    });

    mAdapter.addPage(new PlayerPageAdapter.HomeTabPage() {
      @Override
      public Fragment createFragment() {
        return ExoPlayerFragment.newInstance(1);
      }
    });

    mPager.setAdapter(mAdapter);
  }
}
