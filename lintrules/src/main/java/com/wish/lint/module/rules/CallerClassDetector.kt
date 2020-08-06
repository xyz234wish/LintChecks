package com.wish.lint.module.rules

import com.android.SdkConstants
import com.android.tools.lint.checks.AbstractAnnotationDetector
import com.android.tools.lint.detector.api.*
import com.intellij.psi.*
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UElement

/**
 * @description: 限制调用方法
 * @Author: lihongzhi
 * @CreateDate: 2020/6/9 4:01 PM
 */
@Suppress("UnstableApiUsage")
class CallerClassDetector : AbstractAnnotationDetector(), SourceCodeScanner {
    companion object Issues {
        private val IMPLEMENTATION = Implementation(
            CallerClassDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )

        /** Missing call to super  */
        @JvmField
        val ISSUE = Issue.create(
            id = "CallerLimit",
            briefDescription = "Only the defined classes can call method",
            explanation = """
            Only the defined classes in annotation 'CallerClass' can call the method.
            """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.ERROR,
            implementation = IMPLEMENTATION
        )
        const val CallerClass = "com.wish.lint.module.annotation.CallerClass"
    }

    override fun applicableAnnotations(): List<String> = listOf(
        CallerClass
    )

    override fun visitAnnotationUsage(
        context: JavaContext,
        usage: UElement,
        type: AnnotationUsageType,
        annotation: UAnnotation,
        qualifiedName: String,
        method: PsiMethod?,
        referenced: PsiElement?,
        annotations: List<UAnnotation>,
        allMemberAnnotations: List<UAnnotation>,
        allClassAnnotations: List<UAnnotation>,
        allPackageAnnotations: List<UAnnotation>
    ) {
        val callerFile = usage.sourcePsi?.containingFile
        // 调用者的PsiClass
        var callerPsiClass: PsiClass? = null
        // 调用者的全路径名称
        val callerQualifiedName =
            if (callerFile is PsiClassOwner) {
                callerPsiClass = callerFile.classes[0]
                "${callerFile.packageName}.${callerFile.getVirtualFile().nameWithoutExtension}"
            } else {
                ""
            }
        val evaluator = context.evaluator
        // 注册的可调用者集合
        val limitClassArray = hashMapOf<String, String>()
        limitClassArray[method?.containingClass?.qualifiedName ?: ""] = "value"
        annotation.sourcePsi?.let {
            if (it is PsiAnnotation) {
                val valueArray = it.findAttributeValue(SdkConstants.ATTR_VALUE)
                if (valueArray is PsiArrayInitializerMemberValue) {
                    val array = valueArray.initializers
                    array.forEach { value ->
                        if (value is PsiClassObjectAccessExpression) {
                            val classType = value.operand.type as PsiClassType
                            limitClassArray[classType.resolve()?.qualifiedName ?: "null"] = "value"
                        }
                    }
                }
            }
        }

        // 判断调用者是否注册类的子类或实现类
        callerPsiClass?.let {
            limitClassArray.forEach {
                val extendsClass = evaluator.extendsClass(callerPsiClass, it.key, false)
                if (extendsClass) {
                    return
                }
            }
        }

        //给出错误提示
        val message = "Only the defined classes (${limitClassArray.keys}) can call the method"
        if (!limitClassArray.containsKey(callerQualifiedName)) {
            context.report(
                ISSUE, usage, context.getLocation(usage),
                message
            )
        }
    }
}