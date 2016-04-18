package com.ipoxiao.idlenetwork.module.common.ui;


import android.content.Intent;
import android.view.KeyEvent;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.adapter.DynamicFragmentAdapter;
import com.ipoxiao.idlenetwork.framework.adapter.FragmentTag;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.home.listener.OnAreaCategoryListener;
import com.ipoxiao.idlenetwork.module.common.ui.fragment.CityFragment;
import com.ipoxiao.idlenetwork.module.common.ui.fragment.DistrictFragment;
import com.ipoxiao.idlenetwork.module.common.ui.fragment.ProvinceFragment;
import com.ipoxiao.idlenetwork.utils.ViewUtil;

import java.util.ArrayList;


public class AreaActivity extends ActionBarOneActivity implements OnAreaCategoryListener {

    private DynamicFragmentAdapter mDynamicFragmentAdapter;
    private String mCurrentProvinceID;
    private String mCurrentProvinceName;
    private String mCurrentCityName;

    @Override
    protected int getCustomView() {
        return R.layout.activity_common_area;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initSubView() {
        initTitle("所在地区");
        ArrayList<FragmentTag> tags = new ArrayList<>();
        tags.add(new FragmentTag(ProvinceFragment.class.getSimpleName(), ProvinceFragment.class, null));
        tags.add(new FragmentTag(CityFragment.class.getSimpleName(), CityFragment.class, null));
        tags.add(new FragmentTag(DistrictFragment.class.getSimpleName(), DistrictFragment.class, null));
        mDynamicFragmentAdapter = new DynamicFragmentAdapter(this, tags, R.id.layout_frame, getSupportFragmentManager());
        mDynamicFragmentAdapter.setIsBack(true);
        mDynamicFragmentAdapter.loadFragment(0);
    }


    @Override
    protected void onBackEvent() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            mDynamicFragmentAdapter.remove(0);
            mDynamicFragmentAdapter.setCurrentIndex(0);
            super.onBackEvent();
        } else {
            if (count == 3) {
                mDynamicFragmentAdapter.remove(2);
            } else if (count == 2) {
                mDynamicFragmentAdapter.remove(1);
            }
            getSupportFragmentManager().popBackStack();
            mDynamicFragmentAdapter.setCurrentIndex(0);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackEvent();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAreaSelected(int category, Area area) {
        switch (category) {
            case AREA_PROVINCE:
                mDynamicFragmentAdapter.loadFragment(1);
                OnAreaSelectedListener cityFragment = (OnAreaSelectedListener) getSupportFragmentManager().findFragmentByTag(CityFragment.class.getSimpleName());
                mCurrentProvinceID = area.getId();
                mCurrentProvinceName = area.getName();
                cityFragment.onAreaSelected(mCurrentProvinceID);
                break;
            case AREA_CITY:
                mDynamicFragmentAdapter.loadFragment(2);
                OnAreaSelectedListener districtFragment = (OnAreaSelectedListener) getSupportFragmentManager().findFragmentByTag(DistrictFragment.class.getSimpleName());
                mCurrentCityName = area.getName();
                districtFragment.onAreaSelected(area.getId());
                break;
            case AREA_DISTRICT:
                boolean isTrade = getIntent().getBooleanExtra("trade", true);

                if (area.getIs_trade() == 0 && isTrade) {
                    ViewUtil.showToast(getContext(), "此地区下没有行业，请重新选择地区");
                    return;
                }

                StringBuffer cityindex = new StringBuffer(mCurrentProvinceID);
                StringBuffer cityindex_link = new StringBuffer(mCurrentProvinceName);
                cityindex.append("-").append(area.getPid()).append("-").append(area.getId());
                cityindex_link.append("-").append(mCurrentCityName).append("-").append(area.getName());
                area.setCityindex(cityindex.toString());
                area.setCityindex_link(cityindex_link.toString());
                Intent intent = new Intent();
                intent.putExtra("object", area);
                setResult(RESULT_OK, intent);
                finish();

                break;
        }
    }

    /**
     *
     */
    public interface OnAreaSelectedListener {
        void onAreaSelected(String id);
    }

}



