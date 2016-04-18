package com.ipoxiao.idlenetwork.module.common.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.function.gallery.IPhotoPicker;
import com.ipoxiao.idlenetwork.module.common.adapter.ImageEditAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IUploadBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.UploadBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IUploadController;
import com.ipoxiao.idlenetwork.utils.BitmapUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridActivity;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditImageActivity extends ActionBarTwoActivity implements IPhotoPicker.IPhotoResult, IUploadController, AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {

    public static final String EDIT_IMAGE = "image";
    private final int REQ_IMAGE = 1433;
    private final int selectLimit=9;

    @ViewInject(R.id.et_content)
    private EditText mEdiText;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.btn_upload)
    private ImageButton mBtnUpload;

    private ImageEditAdapter mImageEditAdapter;
    private IUploadBusiness mUploadBusiness;

    @Override
    protected int getCustomView() {
        return R.layout.activity_common_edit_image;
    }

    @Override
    protected void initListener() {
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
                AndroidImagePicker.getInstance().setShouldShowCamera(true);
                AndroidImagePicker.getInstance().setSelectLimit(selectLimit - mImageEditAdapter.getItemCount());
                Intent intent = new Intent(EditImageActivity.this, ImagesGridActivity.class);
                startActivityForResult(intent, REQ_IMAGE);
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("输入内容");
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mImageEditAdapter = new ImageEditAdapter(this, mBtnUpload);
        mRecyclerView.setAdapter(mImageEditAdapter);
        mUploadBusiness = new UploadBusinessImpl(this);
    }

    @Override
    protected void onActionEvent() {
        String content = mEdiText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ViewUtil.showToast(getContext(), "编辑内容不能为空");
            return;
        }

        List<LocalBitmap> bitmaps = mImageEditAdapter.getDataSource();
        if (bitmaps == null || bitmaps.size() <= 0) {
            ViewUtil.showToast(getContext(), "图片不能为空");
            return;
        }
        mUploadBusiness.upload(bitmaps, IUploadBusiness.TYPE_MULTI);
    }

    @Override
    public void handResult(Bitmap bitmap, File cacheFile, int requestCode) {
        LocalBitmap bean = new LocalBitmap(bitmap, cacheFile);
        mImageEditAdapter.addData(bean);
    }

    @Override
    public void handResult(List<Bitmap> bitmaps, List<File> files) {
        List<LocalBitmap> localBitmaps = new ArrayList<>();
        for (int i = 0; i < bitmaps.size(); i++) {
            LocalBitmap bean = new LocalBitmap(bitmaps.get(i), files.get(i));
            localBitmaps.add(bean);
        }
        mImageEditAdapter.replaceData(localBitmaps);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_IMAGE) {
                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
                if (imageList != null && imageList.size() > 0) {
                    setAddImageAdapter(imageList);
                }
            }
        }
    }

    @Override
    public void onUpload(String sha1) {
        Intent intent = new Intent();
        intent.putExtra(EDIT_IMAGE, sha1);
        intent.putExtra(EditActivity.EDIT_DATA, mEdiText.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        if (!picturePath.isEmpty()) {
            List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
            imageList.add(new ImageItem(picturePath, "", 0));
            setAddImageAdapter(imageList);
        }
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        if (items != null) {
            setAddImageAdapter(items);
        }
    }

    @Override
    protected void onResume() {
        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);//watching Picture taking
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        super.onDestroy();
    }

    private void setAddImageAdapter(List<ImageItem> imageItems) {
        List<LocalBitmap> localBitmaps = new ArrayList<>();
        for (ImageItem imageItem : imageItems) {
            BitmapUtil bitmapUtil =new BitmapUtil(this, imageItem.path);
            Bitmap bitmap = bitmapUtil.getSmallBitmap(480, 800);
            LocalBitmap bean = new LocalBitmap(bitmap, new File(bitmapUtil.tmpPath));
            localBitmaps.add(bean);
        }
        mImageEditAdapter.addData(localBitmaps);
        if (mImageEditAdapter.getItemCount() == 9) {
            mBtnUpload.setVisibility(View.GONE);
        } else {
            mBtnUpload.setVisibility(View.VISIBLE);
        }
        localBitmaps.clear();
    }
}
