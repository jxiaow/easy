# easy [![](https://jitpack.io/v/jxiaow/easy.svg)](https://jitpack.io/#jxiaow/easy)

此库主要是收集工作中用到的扩展方法。使用此库可以大大节约开发时间，文档和说明目前正在完善中...

目前已经包含有：

| 包含的模块     | 说明                                                                          |
| --------- | --------------------------------------------------------------------------- |
| 协程扩展      | [coroutine](https://github.com/jxiaow/easy/tree/master/coroutine/README.md) |
| ktx 扩展    | [ktx](https://github.com/jxiaow/easy/tree/master/ktx/README.md)             |
| helper帮助类 | [helper](https://github.com/jxiaow/easy/tree/master/hepler/README.md)       |

## 用法

在根`build.gradle`中添加：

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

在使用的模块`build.gradle`中添加：

```groovy
dependencies {
    // 协程扩展
    implementation 'com.github.jxiaow.easy:coroutine:x.y.z'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1'
    // helper帮助类
    implementation 'com.github.jxiaow.easy:helper:x.y.z'
    // 常用扩展工具类
    implementation 'com.github.jxiaow.easy:ktx:x.y.z'
}
```

(请替换 x. y .z 为最新的版本号: ![](https://jitpack.io/v/jxiaow/easy.svg) )

## 注意

如果您的项目不支持`AndroidX`，请使用 **2.0.0**版本。

## 如果方便了您的使用，欢迎[Star](https://github.com/jxiaow/easy) 。
