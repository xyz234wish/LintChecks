package com.wish.lint.module.registry

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.wish.lint.module.rules.CallerClassDetector

/**
 * @description: 调用者限制规则注册器
 * @Author: lihongzhi
 * @CreateDate: 2020/6/9 4:38 PM
 */

@Suppress("UnstableApiUsage")
class CallerLimitIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(CallerClassDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API
}