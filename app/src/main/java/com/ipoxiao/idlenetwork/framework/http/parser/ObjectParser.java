package com.ipoxiao.idlenetwork.framework.http.parser;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2015/11/20.
 */
public class ObjectParser<T> extends BaseParser<T> implements IJsonParser<T> {

    @Override
    public T parseObject(String data, Class<T> clazz) {
        return JSONObject.parseObject(data, clazz);
    }
}
