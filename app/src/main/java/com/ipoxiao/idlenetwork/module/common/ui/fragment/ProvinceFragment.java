package com.ipoxiao.idlenetwork.module.common.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.fragment.BaseNormalFragment;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.common.adapter.AreaAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.home.listener.OnAreaCategoryListener;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IAreaBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.AreaBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IAreaController;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvinceFragment extends BaseNormalFragment implements OnItemClickListener, IAreaController {

    @ViewInject(R.id.et_search)
    private EditText mEditSearch;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private AreaAdapter mAreaAdapter;

    private IAreaBusiness mAreaBusiness;


    @Override
    public void initSubView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAreaAdapter = new AreaAdapter(getActivity());
        mAreaAdapter.addOnItemClickListener(this);
        mRecyclerView.setAdapter(mAreaAdapter);

        mAreaBusiness = new AreaBusinessImpl(this);
        mAreaBusiness.getAreaListByID("",mEditSearch.getText().toString());

    }

    @Override
    protected int getCustomView() {
        return R.layout.fragment_common_area;
    }

    @Override
    protected void initListener() {
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mAreaBusiness.getAreaListByID("",mEditSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void click(View v, int position) {
        ((OnAreaCategoryListener) getActivity()).onAreaSelected(OnAreaCategoryListener.AREA_PROVINCE, mAreaAdapter.getItem(position));
    }

    @Override
    public void onArea(List<Area> areas) {
        mAreaAdapter.replaceData(areas);
    }

    @Override
    public void onIndustry(List<ChatSession> chatSessions) {

    }
}
