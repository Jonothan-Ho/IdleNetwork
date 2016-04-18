package com.ipoxiao.idlenetwork.framework.http.parser;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class ArrayParser<T> extends BaseParser<T> implements IJsonParser<T> {

    @Override
    public List<T> parseArrayObject(String data, Class<T> clazz) {
        List<T> list = JSON.parseArray(data, clazz);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
