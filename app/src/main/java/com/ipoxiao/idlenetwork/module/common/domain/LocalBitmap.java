package com.ipoxiao.idlenetwork.module.common.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by Administrator on 2015/10/28.
 */
public class LocalBitmap implements Parcelable {

    private Bitmap mBitmap;
    private File mLocalFile;

    public LocalBitmap(Bitmap mBitmap, File mLocalFile) {
        this.mBitmap = mBitmap;
        this.mLocalFile = mLocalFile;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public File getFile() {
        return mLocalFile;
    }


    protected LocalBitmap(Parcel in) {
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        mLocalFile = (File) in.readSerializable();
    }


    public static final Creator<LocalBitmap> CREATOR = new Creator<LocalBitmap>() {
        @Override
        public LocalBitmap createFromParcel(Parcel in) {
            return new LocalBitmap(in);
        }

        @Override
        public LocalBitmap[] newArray(int size) {
            return new LocalBitmap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mBitmap, flags);
        dest.writeSerializable(mLocalFile);
    }
}
