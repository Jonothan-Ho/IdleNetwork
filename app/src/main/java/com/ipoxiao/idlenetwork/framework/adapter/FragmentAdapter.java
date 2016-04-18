package com.ipoxiao.idlenetwork.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Using FragmentPagerAdapter
 * Created by Administrator on 2015/10/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<FragmentDomain> mArrayFragments;

    public FragmentAdapter(FragmentManager fm, List<FragmentDomain> fragments) {
        super(fm);
        this.mArrayFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mArrayFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mArrayFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayFragments.get(position).getPageTitle();
    }

 /*   @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
        object = null;
    }*/


    public static class FragmentDomain {
        private String pageTitle;
        private Fragment fragment;

        public FragmentDomain(String pageTitle, Fragment fragment) {
            this.pageTitle = pageTitle;
            this.fragment = fragment;
        }

        public String getPageTitle() {
            return pageTitle;
        }

        public void setPageTitle(String pageTitle) {
            this.pageTitle = pageTitle;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }


}
