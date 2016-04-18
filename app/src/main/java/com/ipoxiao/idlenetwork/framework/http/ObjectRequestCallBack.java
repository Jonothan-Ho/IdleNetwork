package com.ipoxiao.idlenetwork.framework.http;

import com.ipoxiao.idlenetwork.framework.http.parser.ArrayParser;
import com.ipoxiao.idlenetwork.framework.http.parser.IJsonParser;
import com.ipoxiao.idlenetwork.framework.http.parser.NullParser;
import com.ipoxiao.idlenetwork.framework.http.parser.ObjectParser;
import com.ipoxiao.idlenetwork.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class ObjectRequestCallBack<T> extends TextRequestCallBack {

    public enum Json_parser {
        Object, Array, NULL
    }

    protected IJsonParser<T> jsonParser;
    private Class<T> clazz;
    private Json_parser mType;

    public ObjectRequestCallBack(Json_parser type, Class<T> clazz) {
        switch (type) {
            case Object:
                jsonParser = new ObjectParser<>();
                break;
            case Array:
                jsonParser = new ArrayParser<>();
                break;
            case NULL:
                jsonParser = new NullParser();
                break;
        }

        this.clazz = clazz;
        mType = type;
    }

    public ObjectRequestCallBack(Json_parser type, IJsonParser<T> jsonParser) {
        mType = type;
        this.jsonParser = jsonParser;
    }


    @Override
    public void onResult(Domain domain) {

        T t = null;
        List<T> list = null;
        try {
            String data = domain.getData();
            if (mType == Json_parser.Object) {
                t = jsonParser.parseObject(data, clazz);
                onResponse(t, domain);
            } else if (mType == Json_parser.NULL) {
                onResponse(t, domain);
            } else if (mType == Json_parser.Array) {
                list = jsonParser.parseArrayObject(data, clazz);
                onResponse(list, domain);
            }

        } catch (Exception e) {
            LogUtil.printf(e + "======>");
            if (mType == Json_parser.Array) {
                onResponse(list, domain);
            } else {
                onResponse(t, domain);
            }
        }
    }

    public void onResponse(T obj, Domain domain) {

    }

    public void onResponse(List<T> array, Domain domain) {

    }

}
