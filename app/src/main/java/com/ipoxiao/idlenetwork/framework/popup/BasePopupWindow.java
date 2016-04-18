package com.ipoxiao.idlenetwork.framework.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.listener.OnItemClickListener;
import com.ipoxiao.idlenetwork.utils.ViewUtil;


/**
 * Created by Administrator on 2015/10/30.
 */
public abstract class BasePopupWindow {

    protected PopupWindow mPopupWindow;
    protected Activity mActivity;
    protected View mRootView;
    protected OnDismissListener mDismissListener;
    protected OnItemClickListener mItemClickListener;


    public BasePopupWindow(Activity activity) {
        this.mActivity = activity;
        mRootView = View.inflate(activity, getCustomView(), null);

        mPopupWindow = new PopupWindow(mRootView, getWindowWidth(), getWindowHeight());
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(getPopupWindowAnim());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                ViewUtil.changeActivityAlpha(mActivity, 1.0f);

                if (mDismissListener != null) {
                    mDismissListener.onDismiss(mPopupWindow);
                }

            }
        });

        initView(mRootView);

    }


    /**
     * @return
     */
    public int getPopupWindowAnim() {
        return R.style.popupWindowAnimTwo;
    }

    /**
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    /**
     * @param dismissListener
     */
    public void setOnDismissListener(OnDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }


    /**
     * @return
     */
    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }


    /**
     * close
     */
    public void closePopupWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * empty method body ,sub class implements it
     *
     * @param view
     */
    public void showAsDropDown(View view) {
        mPopupWindow.showAsDropDown(view);
    }

    /**
     * @param view
     */
    public void showAsDropDown(View view, boolean isAlpha) {
        this.showAsDropDown(view);
        if (isAlpha) {
            ViewUtil.changeActivityAlpha(mActivity, 0.3f);
        }
    }

    /**
     * @param parent
     * @param gravity
     */
    public void showAsLocation(View parent, int gravity) {
        mPopupWindow.showAtLocation(parent, gravity, 0, 0);
        ViewUtil.changeActivityAlpha(mActivity, 0.5f);
    }

    /**
     * @return
     */
    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    /**
     * @param mItemClickListener
     */
    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    protected abstract int getCustomView();

    protected abstract int getWindowWidth();

    protected abstract int getWindowHeight();

    protected abstract void initView(View rootView);

    public interface OnDismissListener {
        public void onDismiss(PopupWindow popupWindow);
    }


}
