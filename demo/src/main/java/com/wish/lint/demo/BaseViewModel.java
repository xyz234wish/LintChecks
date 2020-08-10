package com.wish.lint.demo;

import android.app.Application;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @description: ViewModel基类
 * @Author: lihongzhi
 * @CreateDate: 2020/7/14 6:57 PM
 */
public class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @CallSuper
    public void hello() {

    }
}
