package com.ipoxiao.idlenetwork.module.account.mvc.business;

import com.ipoxiao.idlenetwork.framework.mvc.business.IBaseService;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface IAccountBusiness extends IBaseService {

    void register(String mobile, String password, String repassword, String code, String cityindex, String dist, String trade);

    void login(String username, String passwd);

    void modify(String mobile, String password, String repassword, String code);

    void updateUser(String head, String nickname, String dist, String cityindex, String trade);

}
