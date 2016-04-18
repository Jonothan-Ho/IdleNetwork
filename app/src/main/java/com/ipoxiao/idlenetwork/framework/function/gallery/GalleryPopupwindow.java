package com.ipoxiao.idlenetwork.framework.function.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.dialog.OnDialogLauncherAble;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.FileUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;


/**
 * Created by Administrator on 2015/10/28.
 */
public class GalleryPopupwindow implements OnDialogLauncherAble, View.OnClickListener {

    private PopupWindow popupWindow;
    private Activity activity;
    private View view;

    private IPhotoPicker mPhotoPicker;
    private IPhotoPicker.IPhotoResult mPhotoResult;
    private Config config;


    public GalleryPopupwindow(Activity activity, IPhotoPicker.IPhotoResult photoResult) {
        this.activity = activity;
        this.mPhotoResult = photoResult;

        mPhotoPicker = new PhotoPickerImpl(activity, mPhotoResult);

        config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.blue)
                .setCameraButtonColor(R.color.green)
                .setSelectionLimit(6)    // set photo selection limit. Default unlimited selection.
                .build();

        initData();


    }

    public GalleryPopupwindow(Activity activity, IPhotoPicker.IPhotoResult photoResult, int limit) {
        this.activity = activity;
        this.mPhotoResult = photoResult;

        mPhotoPicker = new PhotoPickerImpl(activity, mPhotoResult);

        config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.blue)
                .setCameraButtonColor(R.color.green)
                .setSelectionLimit(limit)    // set photo selection limit. Default unlimited selection.
                .build();

        initData();

    }

    /**
     *
     */
    private void initData() {
        view = View.inflate(activity, R.layout.popup_take_picture, null);
        TextView mTextPhoto = (TextView) view.findViewById(R.id.tv_photo);
        TextView mTextCamera = (TextView) view.findViewById(R.id.tv_camera);
        TextView mTextCancel = (TextView) view.findViewById(R.id.tv_cancel);

        mTextPhoto.setOnClickListener(this);
        mTextCamera.setOnClickListener(this);
        mTextCancel.setOnClickListener(this);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupWindowAnim);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                ViewUtil.changeActivityAlpha(GalleryPopupwindow.this.activity, 1.0f);
            }
        });
    }

    @Override
    public void startDialog() {
       /* popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        ViewUtil.changeActivityAlpha(activity, 0.3f);*/

        Intent intent = new Intent(activity, ImagePickerActivity.class);
        ImagePickerActivity.setConfig(config);
        activity.startActivityForResult(intent, IPhotoPicker.REQUESTCODE_KITKAT_LATER);
    }

    @Override
    public void closeDialog() {
        popupWindow.dismiss();
    }


    @Override
    public boolean isShow() {
        return popupWindow.isShowing();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                mPhotoPicker.openCamera();
                break;
            case R.id.tv_photo:
                //mPhotoPicker.openAlbumPicker();
                Intent intent = new Intent(activity, ImagePickerActivity.class);
                ImagePickerActivity.setConfig(config);
                activity.startActivityForResult(intent, IPhotoPicker.REQUESTCODE_KITKAT_LATER);
                break;
            case R.id.tv_cancel:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return;
        }
        closeDialog();
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        mPhotoPicker.onActivityResult(requestCode, responseCode, data);
    }

    public void onPickedSuccessfully(String[] paths) {
        List<Bitmap> bitmaps = new ArrayList<>();
        List<File> cacheFiles = new ArrayList<>();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                File cacheFile;
                Bitmap bitmap;
                try {
                    cacheFile = FileUtil.getCacheBitmapFile(activity, FileUtil.Image_Format.JPEG);
                    bitmap = BitmapUtil.compressBitmapToCacheFile(activity, paths[i], cacheFile);
                    bitmaps.add(bitmap);
                    cacheFiles.add(cacheFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        mPhotoResult.handResult(bitmaps, cacheFiles);

    }

}
