package com.ash.application;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Aakash on 07-06-2016.
 */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

        protected Context mContext;
    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class
        switch (position) {
            case 0:
                 return new InboxFragment();
            case 1:
                    return new FriendsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "INBOX";
            case 1:
                return "FRIENDS";
        }
        return null;
    }
}
