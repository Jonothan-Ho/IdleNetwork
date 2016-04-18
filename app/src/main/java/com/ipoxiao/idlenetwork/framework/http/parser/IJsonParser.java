package com.ipoxiao.idlenetwork.framework.http.parser;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public interface IJsonParser<T> {

    T parseObject(String data, Class<T> clazz);

    List<T> parseArrayObject(String data, Class<T> clazz);
}
