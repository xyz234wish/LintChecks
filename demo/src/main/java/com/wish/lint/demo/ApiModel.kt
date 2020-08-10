package com.wish.lint.demo

import com.wish.lint.module.annotation.CallerClass

/**
 * @description: 网络请求model
 * @Author: lihongzhi
 * @CreateDate: 2020/6/19 2:51 PM
 */

@CallerClass(LoginRepository::class)
class ApiModel {
    fun getUser(): User {
        return User()
    }
}