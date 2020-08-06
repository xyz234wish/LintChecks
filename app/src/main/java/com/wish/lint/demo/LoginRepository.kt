package com.wish.lint.demo

/**
 * @description:
 * @Author: lihongzhi
 * @CreateDate: 2020/8/6 6:07 PM
 */
class LoginRepository {
    private val apiModel: ApiModel = ApiModel()

    fun login(): User {
        return apiModel.getUser()
    }
}