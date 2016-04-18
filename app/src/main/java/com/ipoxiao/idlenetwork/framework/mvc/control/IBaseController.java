package com.ipoxiao.idlenetwork.framework.mvc.control;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2015/10/23.
 */
public interface IBaseController {

    View getRootView();

    void openDialog();

    void closeDialog();

    Context getContext();

}
