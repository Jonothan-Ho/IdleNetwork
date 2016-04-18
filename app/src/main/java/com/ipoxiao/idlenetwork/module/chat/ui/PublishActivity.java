package com.ipoxiao.idlenetwork.module.chat.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.adapter.decoration.SpaceItemDecoration;
import com.ipoxiao.idlenetwork.framework.function.gallery.GalleryPopupwindow;
import com.ipoxiao.idlenetwork.framework.function.gallery.IPhotoPicker;
import com.ipoxiao.idlenetwork.module.common.adapter.ImageEditAdapter;
import com.ipoxiao.idlenetwork.module.common.domain.Dynamic;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IDynamicBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IUploadBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.DynamicBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.UploadBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IDynamicController;
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

public class PublishActivity extends ActionBarTwoActivity implements IPhotoPicker.IPhotoResult, IUploadController, IDynamicController,AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {

    private final int REQ_IMAGE = 1433;
    private final int selectLimit=9;

    @ViewInject(R.id.et_number)
    private EditText mEditNumber;
    @ViewInject(R.id.et_title)
    private EditText mEditTitle;
    @ViewInject(R.id.et_content)
    private EditText mEditContent;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.btn_upload)
    private ImageButton mImageButton;

    private ImageEditAdapter mImageEditAdapter;
    private GalleryPopupwindow mGalleryPopupwindow;

    private IUploadBusiness mUploadBusiness;
    private IDynamicBusiness mDynamicBusiness;


    @Override
    protected int getCustomView() {
        return R.layout.activity_chat_publish;
    }

    @Override
    protected void initListener() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
                AndroidImagePicker.getInstance().setShouldShowCamera(true);
                AndroidImagePicker.getInstance().setSelectLimit(selectLimit - mImageEditAdapter.getItemCount());
                Intent intent = new Intent(PublishActivity.this, ImagesGridActivity.class);
                startActivityForResult(intent, REQ_IMAGE);
            }
        });
    }

    @Override
    protected void initSubView() {
        initTitle("动态发布");
        mEditContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);//设置自动换行
        mEditContent.setSingleLine(false);
        mEditContent.setHorizontallyScrolling(false);
//        mEditContent.setGravity(Gravity.TOP);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mImageEditAdapter = new ImageEditAdapter(this,mImageButton);
        mRecyclerView.setAdapter(mImageEditAdapter);
        mGalleryPopupwindow = new GalleryPopupwindow(this, this);

        mUploadBusiness = new UploadBusinessImpl(this);
        mDynamicBusiness = new DynamicBusinessImpl(this);
    }

    @Override
    protected void onActionEvent() {
        String number = mEditNumber.getText().toString().trim();
        String content = mEditContent.getText().toString().trim();
        String title = mEditTitle.getText().toString().trim();
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
        mImageEditAdapter.addData(localBitmaps);
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
    public void onUpload(String sha1) {
        String number = mEditNumber.getText().toString().trim();
        String content = mEditContent.getText().toString().trim();
        String title = mEditTitle.getText().toString().trim();
        String id = getIntent().getStringExtra("id");
        mDynamicBusiness.publishDynamic(id, title, number, content, sha1);
    }

    @Override
    public void onComplete(Action_Dynamic action, List<Dynamic> dynamics, int page) {

    }

    @Override
    public void onComplete(Action_Dynamic action, boolean isSuccess) {
        if (isSuccess) {
            finish();
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
            mImageButton.setVisibility(View.GONE);
        } else {
            mImageButton.setVisibility(View.VISIBLE);
        }
        localBitmaps.clear();
    }
}
