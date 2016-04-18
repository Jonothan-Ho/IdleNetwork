package com.ipoxiao.idlenetwork.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.lidroid.xutils.BitmapUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Auto scorll view pager
 * Created by Administrator on 2015/10/15.
 */
public class AutoScrollViewPager extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    private ArrayList<ImageView> mArrayImgViews;
    private ArrayList<View> mArrayPointViews; //Indicator view
    private LinearLayout mIndicatorLayout; //Indicator Layout By external incoming
    private ViewPager mViewPager;

    private int mCurrentItem; //current viewpager item
    private int mLastIndicaotrIndex; //last indicator index
    private boolean isAction; //whether the viewpager is clicked

    private ScheduledExecutorService mExecutorService; //timing service
    private AutoScrollHandler mScrollHandler;
    private AutoScrollTask mScrollTask;

    /*interface*/
    private OnViewPagerChangeListener mViewPagerChangeListener;
    private OnImageViewClickListener mImgViewClickListener;


    /****************************
     * constructer
     **************************************/
    public AutoScrollViewPager(Context context) {
        this(context, null);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    /*********************************Main Method**********************************************/

    /**
     * Open timing automatic scrolling
     */
    public void startAutoScroll(int interval) {
        if (mExecutorService != null) {
            mExecutorService.scheduleAtFixedRate(mScrollTask, interval, interval, TimeUnit.SECONDS);
        }
    }

    /**
     * Stop timing automatic scrolling
     */
    public void stopAutoScroll() {
        if (mExecutorService != null) {
            mExecutorService.shutdown();
        }
    }


    /**
     * The internal components is initialized
     *
     * @return
     */
    public boolean isInitialized() {
        return mViewPager != null;
    }


    /**
     * Set Url of Image
     *
     * @param urls
     */
    public void setUrls(String[] urls) {
        if (urls == null || urls.length == 0) {
            ViewUtil.showToast(getContext(), "没有图片可浏览");
            return;
        }
        if (mViewPager != null) {
            mViewPager.setAdapter(null);
        }
        //init view
        mArrayImgViews = new ArrayList<>();

        //add viewpager
        mViewPager = new ViewPager(mContext);
        mViewPager.setOffscreenPageLimit(0);
        LayoutParams mViewPagerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mViewPager, mViewPagerParams);


        //Via the url initializes the Images
        for (int i = 0; i < urls.length; i++) {
            ImageView imgView = new ImageView(mContext);
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setMaxHeight(LayoutParams.MATCH_PARENT);
            imgView.setMaxWidth(LayoutParams.MATCH_PARENT);

            BitmapUtil.displayImage(imgView, urls[i]);
            imgView.setOnClickListener(this);
            mArrayImgViews.add(imgView);
        }

        //initialize data
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter();
        if (mArrayImgViews.size() == 1) {
            mCurrentItem = 1;
        } else {
            mCurrentItem = Integer.MAX_VALUE >> 2;
            while (mCurrentItem % mArrayImgViews.size() != 0) {
                mCurrentItem -= 1;
            }

            //initialize timing server
            mExecutorService = Executors.newSingleThreadScheduledExecutor();
            mScrollHandler = new AutoScrollHandler(this);
            mScrollTask = new AutoScrollTask();
            isAction = false;
        }

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(mCurrentItem);
        mViewPager.addOnPageChangeListener(new ViewPagerChangeListener());


    }


    /**
     * @param urls
     */
    public void replaceData(String[] urls) {
        if (urls == null || urls.length == 0) {
            throw new RuntimeException("urls not be null");
        }

        if (mArrayImgViews == null) {
            mArrayImgViews = new ArrayList<>();
        } else {
            mArrayImgViews.clear(); //clear
        }

        //Via the url initializes the Images
        for (int i = 0; i < urls.length; i++) {
            ImageView imgView = new ImageView(mContext);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);

            LayoutParams imgParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            imgView.setLayoutParams(imgParam);
            BitmapUtil.displayImage(imgView, urls[i]);
            imgView.setOnClickListener(this);
            mArrayImgViews.add(imgView);
        }

        //initialize data

        if (mArrayImgViews.size() == 1) {
            mCurrentItem = 1;
        } else {
            mCurrentItem = Integer.MAX_VALUE >> 2;
            while (mCurrentItem % mArrayImgViews.size() != 0) {
                mCurrentItem -= 1;
            }

            //initialize timing server
            if (mExecutorService == null) {
                mExecutorService = Executors.newSingleThreadScheduledExecutor();
                mScrollHandler = new AutoScrollHandler(this);
                mScrollTask = new AutoScrollTask();
                isAction = false;
            }

        }

        ViewPagerAdapter adapter = (ViewPagerAdapter) mViewPager.getAdapter();
        if (adapter == null) {
            adapter = new ViewPagerAdapter();
            mViewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        mViewPager.setCurrentItem(mCurrentItem);


    }


    /**
     * Set Indicator Layout
     * Call the method after Call setUrls()
     */
    public void setIndicatorLayout(LinearLayout indicatorLayout, LayoutParams params, int viewSize, int bgResource) {
        this.mIndicatorLayout = indicatorLayout;
        addView(mIndicatorLayout, params);

        mArrayPointViews = new ArrayList<>();
        mLastIndicaotrIndex = 0;

        for (int i = 0; i < mArrayImgViews.size(); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(bgResource);
            LinearLayout.LayoutParams viewParam = new LinearLayout.LayoutParams(viewSize, viewSize);
            viewParam.setMargins(10, 0, 10, 0);
            mIndicatorLayout.addView(view, viewParam);
            mArrayPointViews.add(view);
        }
        mArrayPointViews.get(0).setSelected(true);

    }


    /**
     * set viewpager current show item
     */
    public void setViewPageCurrentItem() {
        mViewPager.setCurrentItem(mCurrentItem);
    }


    /**
     *
     */
    public void nextCurrentItem() {
        mCurrentItem += 1;
        setViewPageCurrentItem();
    }

    /**
     *
     */
    public void lastCurrentItem() {
        mCurrentItem -= 1;
        setViewPageCurrentItem();
    }


    /**
     * add viewpager change listener
     *
     * @param viewPagerChangeListener
     */
    public void setOnViewPagerChangeListener(OnViewPagerChangeListener viewPagerChangeListener) {
        this.mViewPagerChangeListener = viewPagerChangeListener;
    }

    /**
     * add ImageView Click Listener
     *
     * @param imageClickListener
     */
    public void setOnImageClickListener(OnImageViewClickListener imageClickListener) {
        this.mImgViewClickListener = imageClickListener;
    }


    @Override
    public void onClick(View view) {
        if (mImgViewClickListener != null) {
            mImgViewClickListener.onImageClick(mCurrentItem % mArrayImgViews.size(), view);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    /**
     * Handler for auto scroll
     */
    private static class AutoScrollHandler extends Handler {

        private SoftReference<AutoScrollViewPager> mSoftReference;

        public AutoScrollHandler(AutoScrollViewPager autoScrollViewPager) {
            mSoftReference = new SoftReference<AutoScrollViewPager>(autoScrollViewPager);
        }


        @Override
        public void handleMessage(Message msg) {
            AutoScrollViewPager viewPager = mSoftReference.get();
            viewPager.setViewPageCurrentItem();
        }
    }

    /**
     * Timing task
     */
    private class AutoScrollTask implements Runnable {

        @Override
        public void run() {

            try {
                synchronized (mViewPager) {
                    if (mScrollHandler != null && !isAction) {
                        mCurrentItem += 1;
                        mScrollHandler.obtainMessage().sendToTarget();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    /**
     * View Pager Adapter
     */
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mArrayImgViews.size() == 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= mArrayImgViews.size();
            View view = mArrayImgViews.get(position);
            ViewParent viewParent = view.getParent();
            if (viewParent != null) {
                ViewGroup viewGroup = (ViewGroup) viewParent;
                viewGroup.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }


    /**
     * Listener for ViewPager
     */
    private class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPagerChangeListener != null) {
                mViewPagerChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {

            mCurrentItem = position;

            int index = position % mArrayImgViews.size();

            //show indicator
            if (mIndicatorLayout != null) {
                if (mArrayImgViews.size() > 1) {
                    mArrayPointViews.get(index).setSelected(true);
                    mArrayPointViews.get(mLastIndicaotrIndex).setSelected(false);
                    mLastIndicaotrIndex = index;
                }
            }

            if (mViewPagerChangeListener != null) {
                mViewPagerChangeListener.onPageSelected(index);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //state == 1 is sliding
            //state == 2 end of the slide
            //state == 0 do nothing

            switch (state) {
                case 0:
                    // if (isAction)
                    isAction = false;
                    break;
                case 1:
                    //if (!isAction)
                    isAction = true;
                    break;
                case 2:
                   /* if (isAction)
                        isAction = false;*/
                    break;
            }

            if (mViewPagerChangeListener != null) {
                mViewPagerChangeListener.onPageScrollStateChanged(state);
            }

        }
    }

    /**********************************************
     * Interface
     ****************************************************************/
    public interface OnViewPagerChangeListener {
        void onPageScrollStateChanged(int state);

        void onPageSelected(int position);

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    }


    public interface OnImageViewClickListener {
        void onImageClick(int position, View view);
    }


}
