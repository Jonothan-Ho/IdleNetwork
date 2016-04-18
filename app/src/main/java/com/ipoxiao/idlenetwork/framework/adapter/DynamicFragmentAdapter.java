package com.ipoxiao.idlenetwork.framework.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ipoxiao.idlenetwork.utils.LogUtil;

import java.util.ArrayList;

/**
 * dynamic load fragment
 * Created by Administrator on 2015/10/27.
 */
public class DynamicFragmentAdapter {

    private int mFrameLayout;
    private ArrayList<FragmentTag> mFragments;
    private FragmentManager mFragmentManager;
    private int mCurrentIndex;
    private Context context;
    private boolean isBack = false;

    public DynamicFragmentAdapter(Context context, ArrayList<FragmentTag> fragments, int frameLayout, FragmentManager fragmentManager) {
        this.context = context;
        this.mFragments = fragments;
        this.mFragmentManager = fragmentManager;
        this.mFrameLayout = frameLayout;
        mCurrentIndex = -1;
    }

    public void loadFragment(int position) {
        if (position == mCurrentIndex) {
            return;
        }


        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (mCurrentIndex != -1) {
            FragmentTag currentTag = mFragments.get(mCurrentIndex);
            if (currentTag.getFragment() != null) {
                ft.hide(currentTag.getFragment());
                currentTag.getFragment().onPause();
            }
        }


        FragmentTag newTag = mFragments.get(position);
        if (newTag.getFragment() == null) {
            newTag.setFragment(Fragment.instantiate(context,
                    newTag.getClss().getName(), newTag.getArgs()));
            ft.add(mFrameLayout, newTag.getFragment(), newTag.getTag());
            if (isBack) {
                ft.addToBackStack(null);
            }
        } else {
            ft.show(newTag.getFragment());
            newTag.getFragment().onResume();
        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        mFragmentManager.executePendingTransactions();

        mCurrentIndex = position;
    }


    /**
     * @param isBack
     */
    public void setIsBack(boolean isBack) {
        this.isBack = isBack;
    }


    public void remove(int position) {
        mFragmentManager.beginTransaction().remove(mFragments.get(position).getFragment());
        mFragments.get(position).setFragment(null);
    }

    public void setCurrentIndex(int index) {
        this.mCurrentIndex = index;
    }

}
