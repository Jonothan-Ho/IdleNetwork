package com.ipoxiao.idlenetwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.common.Common;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bimmap Manage Class..
 * <p/>
 * Such as function is some loading pictures
 *
 * @author Administrator
 */
public class BitmapUtil {

    private static BitmapUtil instance;
    private ImageOptions options;
    private String path = "";
    private String filepath = "";

    public String tmpPath = "";

    private BitmapUtil(Context context) {
        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setSize(150, 150);
        options = builder.build();

    }

    public BitmapUtil(Context context, String filepath) {
        this.path = context.getCacheDir().getPath();
        this.filepath = filepath;
    }

    public static BitmapUtil getInstance(Context context) {
        synchronized (context) {
            if (instance == null) {
                instance = new BitmapUtil(context);
            }
            return instance;
        }
    }

    /**
     * @param imageView
     * @param url
     */
    public static void displayImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            BitmapUtils bitmapUtils = new BitmapUtils(imageView.getContext());
            bitmapUtils.display(imageView, url);
//            x.image().bind(imageView, url);
        } else {
            imageView.setImageResource(R.mipmap.icon_portrait);
        }
    }

    /**
     * @param imageView
     * @param url
     */
    public void displayImageByOptions(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            BitmapUtils bitmapUtils = new BitmapUtils(imageView.getContext());
            bitmapUtils.display(imageView,url);
//            x.image().bind(imageView, url, options);
        }
    }


    /**
     * clear cache
     */
    public void clearCache() {
        x.image().clearMemCache();
        x.image().clearCacheFiles();
    }

    /**
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    /**
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * @param activity
     * @param uri
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            throw new RuntimeException("Bitmap file path don't be null");
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        options.inPreferredConfig = Bitmap.Config.RGB_565;//default
        options.inPurgeable = true;//
        options.inInputShareable = true;//

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getBitmapFromFile(File cacheFile) {
        return getBitmapFromFile(cacheFile.getAbsolutePath());
    }


    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }


    /**
     * @param activity
     * @param uri
     * @return
     * @throws IOException
     */
    public static Bitmap compressBitmapToCacheFile(Activity activity, Uri uri, File cacheFile) throws IOException {
        String filePath = FileUtil.getFilePathFromUri(activity, uri);
        Bitmap bitmap = getBitmapFromFile(filePath);

        //start compress to a cacheFile
        compressBitmap(bitmap, cacheFile, Common.CACHE_BITMAP_MAX_SIZE);

        bitmap = getBitmapFromFile(cacheFile.getAbsolutePath());

        return bitmap;
    }

    /**
     * @param activity
     * @param filePath
     * @param cacheFile
     * @return
     * @throws IOException
     */
    public static Bitmap compressBitmapToCacheFile(Activity activity, String filePath, File cacheFile) throws IOException {
        Bitmap bitmap = getBitmapFromFile(filePath);

        //start compress to a cacheFile
        compressBitmap(bitmap, cacheFile, Common.CACHE_BITMAP_MAX_SIZE);

        bitmap = getBitmapFromFile(cacheFile.getAbsolutePath());

        return bitmap;
    }


    public static Bitmap compressFileToBitmap(Activity activity, File cacheFile) throws IOException {
        Bitmap bitmap = getBitmapFromFile(cacheFile);

        //start compress to a cacheFile
        compressBitmap(bitmap, cacheFile, Common.CACHE_BITMAP_MAX_SIZE);

        bitmap = getBitmapFromFile(cacheFile.getAbsolutePath());

        return bitmap;
    }


    /**
     * compress Bitmap
     *
     * @param bitmap
     * @param cacheFile
     * @param maxSize
     */
    public static void compressBitmap(Bitmap bitmap, File cacheFile, int maxSize) {
        if (bitmap == null) {
            throw new RuntimeException("Bitmap Don't be null");
        }
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        try {
            baos = new ByteArrayOutputStream();
            fos = new FileOutputStream(cacheFile);

            int quality = 90;
            while ((baos.toByteArray().length / 1024) > maxSize) {
                baos.reset();
                quality -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }

            if (baos.toByteArray().length <= 0) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }

            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }

        }

    }


    /**
     * Through the uri call crop from system
     *
     * @param activity
     * @param uri
     * @param outputFile
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    public static void callCropMethodFromSystem(Activity activity, Uri uri, File outputFile, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");//action-crop
        intent.setDataAndType(uri, "image/*");//type
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));// output file
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// output format
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);// scale
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);// no return Thumbnail
        intent.putExtra("noFaceDetection", true);// colse face detection
        activity.startActivityForResult(intent, requestCode);
    }

    public Bitmap getSmallBitmap(int w, int h) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(this.filepath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, w, h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(this.filepath, options);
        if (bm == null) {
            return null;
        }
        int degree = readPictureDegree(this.filepath);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try {
            this.tmpPath = getTmpFilePath(".jpg");
            File f = new File(this.tmpPath);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            Log.d("filePath", "=================================>" + tmpPath);
            bm.compress(Bitmap.CompressFormat.JPEG, 75, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;

    }

    /**
     * 创建临时目录
     *
     * @return
     */
    private String getTmpFilePath(String mimeType) {
        String pathfile = this.path + "/temp/";
        ;
        File f = new File(pathfile);
        if (!f.exists()) {
            f.mkdirs();
        }
        pathfile += System.currentTimeMillis();
        String newpath = "";
        for (int i = 0; i < 10; i++) {
            newpath = pathfile + i + mimeType;
            f = new File(newpath);
            if (f.exists()) {
                continue;
            }
            f = null;
            break;
        }
        return newpath;
    }

    private int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}
