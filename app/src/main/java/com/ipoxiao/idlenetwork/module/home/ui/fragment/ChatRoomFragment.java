package com.ipoxiao.idlenetwork.module.home.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.adapter.FragmentAdapter;
import com.ipoxiao.idlenetwork.framework.fragment.BaseFragment;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSessionInfo;
import com.ipoxiao.idlenetwork.module.home.listener.OnGroupTypeListener;
import com.ipoxiao.idlenetwork.module.home.domain.ChatCategory;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.module.home.listener.OnObserverListener;
import com.ipoxiao.idlenetwork.module.home.mvc.business.ICategoryBusiness;
import com.ipoxiao.idlenetwork.module.home.mvc.business.impl.CategoryBusinessImpl;
import com.ipoxiao.idlenetwork.module.home.mvc.controller.ICategoryController;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomFragment extends BaseNormalFragment implements ICategoryController, OnGroupTypeListener, OnObserverListener {

    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ICategoryBusiness mCategoryBusiness;


    @Override
    public void initSubView(Bundle savedInstanceState) {
        mTabLayout = ((OnTabLayout) getActivity()).getTabLayout();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.text_black), getResources().getColor(R.color.text_pink));

        mCategoryBusiness = new CategoryBusinessImpl(this);
        mCategoryBusiness.getCategoryList();
    }

    @Override
    protected int getCustomView() {
        return R.layout.fragment_home_chat_room;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCategoryList(List<ChatCategory> sessions) {
        mViewPager.setAdapter(null);
        ArrayList<FragmentAdapter.FragmentDomain> fragments = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            ChatCategory category = sessions.get(i);
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(category.getTitle());
            mTabLayout.addTab(tab);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", category);
            Fragment fragment = BaseFragment.newInstance(ChatSessionFragment.class, bundle);
            FragmentAdapter.FragmentDomain domain = new FragmentAdapter.FragmentDomain(category.getTitle(), fragment);
            fragments.add(domain);
        }

        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onChatSessionInfo(ChatSessionInfo info) {

    }

    @Override
    public void onChatSessionList(List<ChatSession> sessions, Domain domain) {

    }

    @Override
    public void onComplete(Category_Action action) {

    }

    @Override
    public String getType() {
        return ICategoryBusiness.TYPE_GROUP;
    }

    @Override
    public void onUpdate() {
        mTabLayout.removeAllTabs();

       List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                transaction.remove(fragments.get(i));
            }
            //fragments.clear();
            transaction.commit();
        }

       mCategoryBusiness.getCategoryList();
    }

    public interface OnTabLayout {
        TabLayout getTabLayout();
    }

}
