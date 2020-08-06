# LintChecks

### 目前问题
在用java开发中会设计很多架构，比如mvvm。在model层中会有很多public方法，因架构设计这些public方法只希望repository去调用，而不希望view或者viewmodule直接去调用。简单的架构还可以自觉遵守规范，但在复杂架构上，会有不熟悉业务同学或者图方便误调用public方法破坏架构规范，造成耦合。

### 解决方案
开发CallerClass注解，在类/方法上注册可调用的类名，只有注册的类可以调用public方案，未注册的类在调用方法时会在ide上报错，强制遵守架构规范。

### 使用方法

1. 引入implement 'androidx.core:core-ktx:${lastVersion}'工程
2. 在需要限制调用者的类或方法上添加CallerClass注解，并注册可调用的类。

```
@CallerClass(LoginRepository::class)
class ApiModel {
    fun getUser(): User {
        return User()
    }
}
```
### 使用效果
注册可调用的LoginViewModel可以正常使用ApiModel并调用getUser方法。
![image.png](/attach/5eec63b2016c8.png)

activity没有注册在使用ApiModel就会在ide里报错。
![image.png](/attach/5eec642f65bab.png)

### 使用注意
此功能类似CallSuper，只是在idea里起到错误提示作用，并没有改变java的public使用规则，仍然能够编译通过。