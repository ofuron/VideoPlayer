package io.ofu.android.videoplayer;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class PlayerPageAdapter extends FragmentPagerAdapter {

  private final FragmentManager mFragmentManager;
  private FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;

  public interface HomeTabPage {
    public Fragment createFragment();
  }

  private List<Fragment> mPages;

  public PlayerPageAdapter(FragmentManager fm) {
    super(fm);
    mPages = new ArrayList<>();
    mFragmentManager = fm;
  }

  public void addPage(HomeTabPage page) {
    mPages.add(page.createFragment());
  }


  @Override
  public Fragment getItem(int position) {
    return mPages.get(position);
  }

  @Override
  public int getCount() {
    return mPages.size();
  }
}
