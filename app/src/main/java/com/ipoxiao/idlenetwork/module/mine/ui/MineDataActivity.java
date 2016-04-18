package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.function.gallery.GalleryPopupwindow;
import com.ipoxiao.idlenetwork.framework.function.gallery.IPhotoPicker;
import com.ipoxiao.idlenetwork.module.account.domain.User;
import com.ipoxiao.idlenetwork.module.account.mvc.business.IAccountBusiness;
import com.ipoxiao.idlenetwork.module.account.mvc.business.impl.AccountBusinessImpl;
import com.ipoxiao.idlenetwork.module.account.mvc.controller.IAccountController;
import com.ipoxiao.idlenetwork.module.account.ui.IndustryActivity;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IUploadBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.UploadBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IUploadController;
import com.ipoxiao.idlenetwork.module.common.ui.AreaActivity;
import com.ipoxiao.idlenetwork.module.common.ui.EditActivity;
import com.ipoxiao.idlenetwork.module.home.domain.ChatSession;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.view.CircleImageView;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridActivity;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MineDataActivity extends ActionBarTwoActivity implements IAccountController, IUploadController, View.OnClickListener, IPhotoPicker.IPhotoResult, AndroidImagePicker.OnPictureTakeCompleteListener {

    public static final int EDIT_USERNAME = 10;
    public static final int EDIT_AREA = 11;
    public static final int EDIT_INDUSTRY = 12;
    private final int REQ_IMAGE = 1433;
    private final int REQ_IMAGE_CROP = 1435;


    @ViewInject(R.id.layout_data)
    private RelativeLayout mLayoutData;
    @ViewInject(R.id.iv_portrait)
    private CircleImageView mImgPortrait;

    @ViewInject(R.id.layout_username)
    private RelativeLayout mLayoutUsername;
    @ViewInject(R.id.tv_username)
    private TextView mTextUsername;

    @ViewInject(R.id.tv_phone)
    private TextView mTextPhone;

    @ViewInject(R.id.layout_area)
    private RelativeLayout mLayoutArea;
    @ViewInject(R.id.tv_area)
    private TextView mTextArea;

    @ViewInject(R.id.layout_industry)
    private RelativeLayout mLayoutIndustry;
    @ViewInject(R.id.tv_industry)
    private TextView mTextIndustry;

    @ViewInject(R.id.tv_date)
    private TextView mTextDate;
    @ViewInject(R.id.layout_date)
    private RelativeLayout mLayoutDate;
    @ViewInject(R.id.tv_times)
    private TextView mTextTimes;
    @ViewInject(R.id.tv_balance)
    private TextView mTextBalance;
    @ViewInject(R.id.layout_times)
    private RelativeLayout mLayoutTimes;
    @ViewInject(R.id.tv_type)
    private TextView mTextType;


    private IUploadBusiness mUploadBusiness;
    private IAccountBusiness mAccountBusiness;


    private int isVip = 0;
    private String headSha1;
    private String cityIndex;
    private String mCurrentDist;
    private String trade;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_data;
    }

    @Override
    protected void initListener() {
        mLayoutData.setOnClickListener(this);
        mLayoutUsername.setOnClickListener(this);
        mLayoutArea.setOnClickListener(this);
        mLayoutIndustry.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("个人资料");
        User user = PreferencesUtil.getInstance(this).get(User.class);
        isVip = user.getIs_vip();
        BitmapUtil.displayImage(mImgPortrait, user.getHead_link());
        mTextUsername.setText(user.getNickname());
        mTextPhone.setText(user.getMobile());
        mTextIndustry.setText(user.getTrade_link());
        mTextBalance.setText(user.getBalance() + "元");
        mTextTimes.setText(user.getFate() + "次");
        String area = user.getCityindex_link();
        try {
            if (!TextUtils.isEmpty(area)) {
                String areaList[] = area.split("-");
                mTextArea.setText(areaList[2]);
            }
        } catch (Exception e) {
            mTextArea.setText("失败");
        }
        mUploadBusiness = new UploadBusinessImpl(this);
        mAccountBusiness = new AccountBusinessImpl(this);

        headSha1 = user.getHead();
        cityIndex = user.getCityindex();
        mCurrentDist = user.getDist();
        trade = user.getTrade();

        if (user.getIs_vip() == 1) {
            //vip
            mTextType.setText("会员用户");
            mTextDate.setText(user.getEnd_time());
        } else {
            mTextType.setText("普通用户");
            mLayoutDate.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActionEvent() {
        String username = mTextUsername.getText().toString().trim();
        mAccountBusiness.updateUser(headSha1, username, mCurrentDist, cityIndex, trade);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_data:
                AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_SINGLE);
                AndroidImagePicker.getInstance().setShouldShowCamera(true);
                intent = new Intent(this, ImagesGridActivity.class);
                startActivityForResult(intent, REQ_IMAGE);
                break;
            case R.id.layout_username:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "更改用户名");
                startActivityForResult(intent, EDIT_USERNAME);
                break;
            case R.id.layout_area:
                if (isVip == 1) {
                    Intent areaIntent = new Intent(MineDataActivity.this, AreaActivity.class);
                    areaIntent.putExtra("trade", false);
                    startActivityForResult(areaIntent, EDIT_AREA);
                } else {
                    ViewUtil.showToast(getContext(), "只有会员才有权限修改地区");
                }

                break;
            case R.id.layout_industry:
                Intent industryIntent = new Intent(this, IndustryActivity.class);
                industryIntent.putExtra("id", mCurrentDist);
                startActivityForResult(industryIntent, EDIT_INDUSTRY);
                break;
        }
    }

    @Override
    public void handResult(Bitmap bitmap, File cacheFile, int requestCode) {
        mImgPortrait.setImageBitmap(bitmap);
        List<LocalBitmap> bitmaps = new ArrayList<>();
        bitmaps.add(new LocalBitmap(bitmap, cacheFile));
        mUploadBusiness.upload(bitmaps, IUploadBusiness.TYPE_MULTI);
    }

    @Override
    public void handResult(List<Bitmap> bitmaps, List<File> files) {
        mImgPortrait.setImageBitmap(bitmaps.get(0));
        List<LocalBitmap> localBitmaps = new ArrayList<>();
        localBitmaps.add(new LocalBitmap(bitmaps.get(0), files.get(0)));
        mUploadBusiness.upload(localBitmaps, IUploadBusiness.TYPE_MULTI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_USERNAME:
                    String username = data.getStringExtra(EditActivity.EDIT_DATA);
                    mTextUsername.setText(username);
                    break;
                case EDIT_AREA:
                    Area area = (Area) data.getSerializableExtra("object");
                    cityIndex = area.getCityindex();
                    mTextArea.setText(area.getName());
                    mCurrentDist = area.getId();
                    break;
                case EDIT_INDUSTRY:
                    ChatSession industry = (ChatSession) data.getSerializableExtra("object");
                    mTextIndustry.setText(industry.getTitle());
                    trade = industry.getId();
                    break;
                default:
                    List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
                    if (imageList != null && imageList.size() > 0) {
                        String path = imageList.get(0).path;

                        BitmapUtil bitmapUtil =new BitmapUtil(this, path);
                        Bitmap bitmap = bitmapUtil.getSmallBitmap(200, 200);
                        LogUtil.printf("picturePath===================================>" + bitmapUtil.tmpPath);
                        handResult(bitmap, new File(bitmapUtil.tmpPath), REQ_IMAGE);
                    }
                    break;
            }
        }
    }

    @Override
    public void onUpload(String sha1) {
        this.headSha1 = sha1;
    }

    @Override
    public void onComplete(User user, boolean isSuccess) {
        if (isSuccess) {
            PreferencesUtil.getInstance(this).put(user, User.class);
            finish();
        }
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        if (!picturePath.isEmpty()) {

            BitmapUtil bitmapUtil =new BitmapUtil(this, picturePath);
            Bitmap bitmap = bitmapUtil.getSmallBitmap(200, 200);
            LogUtil.printf("picturePath===================================>" + bitmapUtil.tmpPath);
            handResult(bitmap, new File(bitmapUtil.tmpPath), REQ_IMAGE);
        }
    }

    @Override
    protected void onResume() {
        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);//拍照完成事件
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);//销毁
        super.onDestroy();
    }
}
