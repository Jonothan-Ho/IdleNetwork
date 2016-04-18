package com.ipoxiao.idlenetwork.module.mine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ipoxiao.idlenetwork.R;
import com.ipoxiao.idlenetwork.framework.activity.ActionBarTwoActivity;
import com.ipoxiao.idlenetwork.framework.function.gallery.GalleryPopupwindow;
import com.ipoxiao.idlenetwork.framework.function.gallery.IPhotoPicker.IPhotoResult;
import com.ipoxiao.idlenetwork.framework.http.Domain;
import com.ipoxiao.idlenetwork.module.common.domain.Area;
import com.ipoxiao.idlenetwork.module.common.domain.LocalBitmap;
import com.ipoxiao.idlenetwork.module.common.domain.Order;
import com.ipoxiao.idlenetwork.module.common.domain.ReceiveEnvelope;
import com.ipoxiao.idlenetwork.module.common.domain.Unifiedorder;
import com.ipoxiao.idlenetwork.module.common.mvc.business.ICoreBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IEnvelopeBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.IUploadBusiness;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.CoreBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.EnvelopeBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.business.impl.UploadBusinessImpl;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.ICoreController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IEnvelopeController;
import com.ipoxiao.idlenetwork.module.common.mvc.controller.IUploadController;
import com.ipoxiao.idlenetwork.module.common.ui.AreaActivity;
import com.ipoxiao.idlenetwork.module.common.ui.EditActivity;
import com.ipoxiao.idlenetwork.module.common.ui.EditImageActivity;
import com.ipoxiao.idlenetwork.module.mine.domain.WithdrawRecord;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Envelope;
import com.ipoxiao.idlenetwork.module.red_envelope.domain.Record;
import com.ipoxiao.idlenetwork.utils.LogUtil;
import com.ipoxiao.idlenetwork.utils.PreferencesUtil;
import com.ipoxiao.idlenetwork.utils.ViewUtil;
import com.ipoxiao.idlenetwork.utils.WXChatCore;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrantEnvelopeActivity extends ActionBarTwoActivity implements ICoreController, IEnvelopeController, View.OnClickListener, IPhotoResult, IUploadController {

    public static final int REQUEST_MONEY = 10;
    public static final int REQUEST_NUMBER = 11;
    public static final int REQUEST_TITLE = 12;
    public static final int REQUEST_CONTENT = 13;
    public static final int REQUEST_AREA = 14;

    @ViewInject(R.id.layout_money)
    private RelativeLayout mLayoutMoney;

    @ViewInject(R.id.layout_number)
    private RelativeLayout mLayoutNumber;
    @ViewInject(R.id.layout_area)
    private RelativeLayout mLayoutArea;

    @ViewInject(R.id.layout_content)
    private RelativeLayout mLayoutContent;

    @ViewInject(R.id.tv_money)
    private TextView mTextMoney;
    @ViewInject(R.id.tv_number)
    private TextView mTextNumber;
    @ViewInject(R.id.tv_area)
    private TextView mTextArea;

    @ViewInject(R.id.tv_content)
    private TextView mTextContent;

    private GalleryPopupwindow mPopupwindow;

    private IUploadBusiness mUploadBusiness;
    private IEnvelopeBusiness mEnvelopeBusiness;

    private String money;
    private String number;
    private String cityindex;
    private String content;

    private String imgpiclist; //content sha1

    private ICoreBusiness mCoreBusiness;
    private String mTimes;
    private String mOrderCreateTime;

    @Override
    protected int getCustomView() {
        return R.layout.activity_mine_grab_envelope;
    }

    @Override
    protected void initListener() {
        mLayoutMoney.setOnClickListener(this);

        mLayoutNumber.setOnClickListener(this);
        mLayoutArea.setOnClickListener(this);

        mLayoutContent.setOnClickListener(this);
    }

    @Override
    protected void initSubView() {
        initTitle("发红包");
        initActionText("发送");
        mPopupwindow = new GalleryPopupwindow(this, this, 1);
        mUploadBusiness = new UploadBusinessImpl(this);
        mEnvelopeBusiness = new EnvelopeBusinessImpl(this);
        mCoreBusiness = new CoreBusinessImpl(this);
    }

    @Override
    protected void onActionEvent() {
        String dist = getIntent().getStringExtra("dist");
        if (TextUtils.isEmpty(dist)) {
            dist = PreferencesUtil.getInstance(this).get("dist");
        }
        LogUtil.printf("dist=====>"+dist);
        mEnvelopeBusiness.sendEnvelope(money, number, dist, cityindex, content, imgpiclist);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getBooleanExtra("flag", false)) {
            setResult(RESULT_OK, null);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int requestCode = 0;
        switch (v.getId()) {
            case R.id.layout_money:
                intent.setClass(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "输入金额");
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_INTEGER);
                requestCode = REQUEST_MONEY;
                break;

            case R.id.layout_number:
                intent.setClass(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "输入个数");
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_INTEGER);
                requestCode = REQUEST_NUMBER;
                break;
            case R.id.layout_area:
                intent.setClass(this, AreaActivity.class);
                intent.putExtra("trade", false);
                requestCode = REQUEST_AREA;
                break;
//            case R.id.layout_title:
//                intent.setClass(this, EditActivity.class);
//                intent.putExtra(EditActivity.EDIT_TITLE, "输入标题");
//                requestCode = REQUEST_TITLE;
//                break;
            case R.id.layout_content:
                intent.setClass(this, EditImageActivity.class);
                requestCode = REQUEST_CONTENT;
                break;
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_MONEY:
                    String money = data.getStringExtra(EditActivity.EDIT_DATA);
                    mTextMoney.setText("¥" + money);
                    this.money = money;
                    break;
                case REQUEST_NUMBER:
                    String number = data.getStringExtra(EditActivity.EDIT_DATA);
                    mTextNumber.setText(number + "个");
                    this.number = number;
                    break;
                case REQUEST_AREA:
                    Area area = (Area) data.getSerializableExtra("object");
                    mTextArea.setText(area.getName());
                    this.cityindex = area.getCityindex();
                    break;

                case REQUEST_CONTENT:
                    String content = data.getStringExtra(EditActivity.EDIT_DATA);
                    String sha1 = data.getStringExtra(EditImageActivity.EDIT_IMAGE);
                    this.content = content;
                    this.imgpiclist = sha1;
                    mTextContent.setText("已填写");
                    break;
                default:
                    mPopupwindow.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    @Override
    public void handResult(Bitmap bitmap, File cacheFile, int requestCode) {
        if (bitmap != null) {
            List<LocalBitmap> bitmaps = new ArrayList<>();
            bitmaps.add(new LocalBitmap(bitmap, cacheFile));
            mUploadBusiness.upload(bitmaps, IUploadBusiness.TYPE_MULTI);
        }
    }

    @Override
    public void handResult(List<Bitmap> bitmaps, List<File> files) {
        List<LocalBitmap> localBitmaps = new ArrayList<>();
        localBitmaps.add(new LocalBitmap(bitmaps.get(0), files.get(0)));
        mUploadBusiness.upload(localBitmaps, IUploadBusiness.TYPE_MULTI);
    }

    @Override
    public void onUpload(String sha1) {
//        this.imgpic = sha1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mTextFace.setText("已上传");
            }
        });
    }

    @Override
    public void onComplete(Action_Envelope action, List<Envelope> envelopes, int page) {

    }

    @Override
    public void onCompleteRecord(List<Record> records, int page) {

    }

    @Override
    public void onComplete(Action_Envelope action, Domain domain) {
        if (domain.isSuccess()) {
            String data = domain.getData();
            if (TextUtils.isEmpty(data)) {
                return;
            }
            try {
                JSONObject object = new JSONObject(data);
                String content = object.getString("orderinfo");
                Order order = JSON.parseObject(content, Order.class);
                String money = ((int) (order.getTotalmoney() * 100)) + "";
                mOrderCreateTime = order.getCreate_time();
                RequestParams params = WXChatCore.getInstance(this).getUnifiedOrder(order.getOrdercode(), money, order.getNotify_url());
                mCoreBusiness.postUnifiedorder(params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

           /* setResult(RESULT_OK, null);
            finish();*/
        }
    }

    @Override
    public void onComplete(ReceiveEnvelope envelope) {

    }

    @Override
    public void onComplete(Action_Core action, boolean isSuccess,Domain domain) {

    }

    @Override
    public void onComplete(Order order) {

    }

    @Override
    public void onComplete(Action_Core action, String content) {
        if (!TextUtils.isEmpty(content)) {
            Unifiedorder order = null;
            try {
                order = WXChatCore.getInstance(this).parseUnifiedorder(content);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (order == null) {
                ViewUtil.showToast(getContext(), "微信统一下单失败，请重试");
                return;
            }
            order.setTimestamp(mOrderCreateTime);
            WXChatCore.getInstance(this).startPay(order);

        }

    }

    @Override
    public void onComplete(List<WithdrawRecord> records, int page) {

    }
}
