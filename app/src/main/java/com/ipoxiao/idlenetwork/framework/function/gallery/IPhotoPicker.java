package com.ipoxiao.idlenetwork.framework.function.gallery;

import android.content.Intent;
import android.graphics.Bitmap;

import com.ipoxiao.idlenetwork.utils.BitmapUtil;

import java.io.File;
import java.util.List;

/**
 * The image selector interface
 * Created by Administrator on 2015/10/20.
 */
public interface IPhotoPicker {

    public static final int REQUESTCODE_KITKAT_BEFORE = 1;
    public static final int REQUESTCODE_KITKAT_LATER = 2;
    public static final int REQUESTCODE_CAMERA = 3;

    /**
     * content://com.android.providers.media.documents/document/image%3A3512
     * content://media/external/images/media/3685794
     * <p/>
     * Open Album
     */
    void openAlbumPicker();

    /**
     * open camera for get photo
     */
    void openCamera();


    /**
     * Activity Return is called,Through the activity is invoked
     * onActivityResult(int requestCode, int responseCode, Intent data)
     *
     * @param requestCode
     * @param responseCode
     * @param data
     */
    void onActivityResult(int requestCode, int responseCode, Intent data);


    /**
     * Need to call choose photos function implementation
     */
    public static interface IPhotoResult {

        /**
         * handle photo result
         *
         * @param bitmap
         * @param cacheFile
         */
        void handResult(Bitmap bitmap, File cacheFile, int requestCode);

        void handResult(List<Bitmap> bitmaps, List<File> files);
    }


}
