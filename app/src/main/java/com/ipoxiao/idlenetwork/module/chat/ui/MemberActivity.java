package com.ipoxiao.idlenetwork.module.chat.ui;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarOneActivity;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.chat.adapter.MemberAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Friend;
import com.ipoxiao.idlenetwork.module.home.domain.GroupUser;
import com.ipoxiao.idlenetwork.module.mine.ui.MineActivity;
import com.ipoxiao.idlenetwork.module.mine.ui.PersonalHomeActivity;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends ActionBarOneActivity implements OnItemClickListener {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;

    @Override
    protected void initSubView() {
        mRecyclerView.setPadding(16, 16, 16, 16);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(16));

        mMemberAdapter = new MemberAdapter(this);
        mMemberAdapter.addOnItemClickListener(this);

        ArrayList<GroupUser> users = getIntent().getParcelableArrayListExtra("object");
        mMemberAdapter.addData(users);
        mRecyclerView.setAdapter(mMemberAdapter);
        initTitle("群成员(" + users.size() + ")");
    }

    @Override
    protected int getCustomView() {
        return R.layout.layout_recyclerview_title_one_no_refresh;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent();
        String memberId = mMemberAdapter.getItem(position).getId();
        User user = PreferencesUtil.getInstance(this).get(User.class);
        if (memberId.equals(user.getId())) {
            intent.setClass(this, MineActivity.class);
        } else {
            intent.setClass(this, PersonalHomeActivity.class);
            intent.putExtra("id", memberId);
        }
        startActivity(intent);
    }
}
