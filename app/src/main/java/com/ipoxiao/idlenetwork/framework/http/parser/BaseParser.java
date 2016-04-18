package com.ipoxiao.idlenetwork.framework.http.parser;

import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class BaseParser<T> implements IJsonParser<T> {
    @Override
    public T parseObject(String data, Class<T> clazz) {
        return null;
    }

    @Override
    public List<T> parseArrayObject(String data, Class<T> clazz) {
        return null;
    }
}
