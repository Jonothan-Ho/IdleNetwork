package com.ipoxiao.idlenetwork.utils;

import android.content.Context;
import android.util.Xml;

import com.ipoxiao.idlenetwork.framework.http.TextRequestCallBack;
import com.ipoxiao.idlenetwork.module.common.domain.Unifiedorder;
import com.ipoxiao.idlenetwork.module.common.mvc.dao.ICoreDao;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xutils.http.RequestParams;
import org.xutils.http.body.StringBody;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/15.
 */
public class WXChatCore {

    public static final String MCH_ID = "1305815001";
    public static final String KEY = "57adffce9098699bc44101029b2d2686";
    public static final String APP_ID = "wxfbd25b829ade0b1f";
    public static final String SECRET = "3e6932592b9d104b5e8d10e0cdb515a6";

    public static WXChatCore instance;
    private IWXAPI mWIwxapi;

    private WXChatCore() {

    }

    private WXChatCore(Context context) {
        if (mWIwxapi == null) {
            mWIwxapi = WXAPIFactory.createWXAPI(context, null);
            mWIwxapi.registerApp(APP_ID);
            if (!mWIwxapi.isWXAppInstalled()) {
                ViewUtil.showToast(context, "用户未安装微信请稍后再试！");
                mWIwxapi = null;
                return;
            }
        }
    }


    /**
     * @param context
     * @return
     */
    public static WXChatCore getInstance(Context context) {
        if (instance == null) {
            synchronized (WXChatCore.class) {
                if (instance == null) {
                    instance = new WXChatCore(context);
                }
            }
        }
        return instance;
    }


    /**
     * @param orderNO
     * @param money
     * @param url
     * @return
     */
    public RequestParams getUnifiedOrder(String orderNO, String money, String url) {
        RequestParams params = new RequestParams(ICoreDao.URL_UNIFIEDORDER);
        params.addBodyParameter(TextRequestCallBack.METHOD_NULL, TextRequestCallBack.METHOD_NULL);
        HashMap<String, String> maps = new LinkedHashMap<>();
        maps.put("appid", APP_ID);
        maps.put("body", "闲人网");
        maps.put("mch_id", MCH_ID);
        maps.put("nonce_str", getRandomStringByLength(32));
        maps.put("notify_url", url);
        maps.put("out_trade_no", orderNO);
        maps.put("spbill_create_ip", "127.0.0.1");
        maps.put("total_fee", money);
//        maps.put("total_fee", "1");
        maps.put("trade_type", "APP");
        maps.put("sign", getSign(maps));

        try {
            params.setRequestBody(new StringBody(params2Xml(maps), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return params;
    }


    /**
     * @param map
     * @return
     */
    public String params2Xml(HashMap<String, String> map) {
        StringBuffer buffer = new StringBuffer("<xml>");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            buffer.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    public Unifiedorder parseUnifiedorder(String content) throws XmlPullParserException, IOException {
        Unifiedorder unifiedorder = null;
        XmlPullParser parser = Xml.newPullParser();
        StringReader reader = new StringReader(content);
        parser.setInput(reader);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    unifiedorder = new Unifiedorder();
                    break;
                case XmlPullParser.START_TAG:
                    if ("return_code".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setReturn_code(parser.getText());
                    } else if ("return_msg".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setReturn_msg(parser.getText());
                    } else if ("appid".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setAppid(parser.getText());
                    } else if ("mch_id".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setMch_id(parser.getText());
                    } else if ("nonce_str".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setNonce_str(parser.getText());
                    } else if ("sign".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setSign(parser.getText());
                    } else if ("result_code".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setResult_code(parser.getText());
                    } else if ("prepay_id".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setPrepay_id(parser.getText());
                    } else if ("nonce_str".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setNonce_str(parser.getText());
                    } else if ("trade_type".equals(parser.getName())) {
                        eventType = parser.next();
                        unifiedorder.setTrade_type(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        return unifiedorder;
    }

    /**
     * @param length
     * @return
     */
    public String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * @param keyValues
     * @return
     */
    public String getSign(Map<String, String> keyValues) {
        StringBuffer buffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = keyValues.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            buffer.append(key);
            buffer.append("=").append(value);
            buffer.append("&");
        }
        buffer.append("key=").append(KEY);
        LogUtil.printf("====>" + buffer.toString());
        String sign = TextUtil.getMessageDigest(buffer.toString().getBytes()).toUpperCase();
        LogUtil.printf("====>" + sign);
        return sign;

    }


    /**
     *
     */
    public void startPay(Unifiedorder unifiedorder) {
        PayReq request = new PayReq();
        request.appId = APP_ID;
        request.partnerId = MCH_ID;
        request.prepayId = unifiedorder.getPrepay_id();
        request.nonceStr = getRandomStringByLength(32);
        request.timeStamp = unifiedorder.getTimestamp();
        request.packageValue = "Sign=WXPay";

        HashMap<String, String> maps = new LinkedHashMap<>();
        maps.put("appid", request.appId);
        maps.put("noncestr", request.nonceStr);
        maps.put("package", request.packageValue);
        maps.put("partnerid", request.partnerId);
        maps.put("prepayid", request.prepayId);
        maps.put("timestamp", request.timeStamp);
        request.sign = getSign(maps);
        // mWIwxapi.registerApp(APP_ID);
        mWIwxapi.sendReq(request);
    }


    /**
     *
     */
    public void getWXChatCode() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "idlenetwork";
        mWIwxapi.sendReq(req);
    }


    /**
     * @return
     */
    public IWXAPI getAPI() {
        return mWIwxapi;
    }

}
