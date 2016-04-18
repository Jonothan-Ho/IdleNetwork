package com.ipoxiao.idlenetwork.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.ipoxiao.idlenetwork.common.Common;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/10/15.
 */
public class FileUtil {

    /**
     * enum type for image format
     */
    public enum Image_Format {
        JPG, JPEG, PNG,
    }

    /**
     * get cache path by from store space
     */
    public static File getCachePath(Context context, String type) {

        File rootFile = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootFile = context.getExternalFilesDir(type);
        } else {
            rootFile = context.getFilesDir();
        }

        //LogUtil.printf("cache path:" + rootFile.getAbsolutePath());

        return rootFile;
    }


    /**
     * get image chche directory
     *
     * @param context
     * @param cacheName
     * @return
     */
    public static String getCacheImageDirPath(Context context, String cacheName) {
        File rootFile = getCachePath(context, Environment.DIRECTORY_PICTURES);
        File cacheFileDir = new File(rootFile, cacheName);
        if (!cacheFileDir.exists()) {
            cacheFileDir.mkdirs();
        }

        return cacheFileDir.getAbsolutePath();
    }


    /**
     * Randomly generated a picture name
     *
     * @return
     */
    private static String getBitmapName(Image_Format format) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        switch (format) {
            case JPEG:
                builder.append(".jpeg");
                break;
            case JPG:
                builder.append(".jpg");
                break;
            case PNG:
                builder.append(".png");
                break;
        }
        return builder.toString();
    }


    /**
     * obtain photo cache directory
     *
     * @param context
     * @return
     */
    public static String getCacheBitmapDir(Context context) {
        return getCacheImageDirPath(context, Common.CACHE_PHOTO_NAME_DIR);
    }


    /**
     * obtain a cache bitmap file
     *
     * @param context
     * @param format
     * @return
     */
    public static File getCacheBitmapFile(Context context, Image_Format format) {
        String path = getCacheBitmapDir(context);
        File file = new File(path, getBitmapName(format));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    /**
     * obtain file path from uri
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    public static String getFilePathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
