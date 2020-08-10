package com.wish.lint.demo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/**
 * @description: 登录ViewModel
 * @Author: lihongzhi
 * @CreateDate: 2020/6/19 2:56 PM
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    // todo ApiModel限制了只有LoginRepository可以调用，所以这里提示错误
    private val apiModel: ApiModel = ApiModel()

    private val apiRepository: LoginRepository = LoginRepository()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun login() {
        // todo ApiModel限制了只有LoginRepository可以调用，所以这里提示错误
        userLiveData.value = apiModel.getUser()

        userLiveData.value = apiRepository.login()
    }

    fun getUserLiveData(): LiveData<User> {
        return userLiveData
    }
}