# easy-coroutine

在Kotlin中，协程的引入为我们进行耗时任务操作提供了一种新的方式，但是通过官方例子引入到实际项目中，好像又不是太方便。
本库的作用是为了提供一种简单的方式去使用协程，此库来源于真实项目（该项目已运行一年有余）。

## 添加依赖：

在根build.gradle中添加：

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

在使用的模块`build.gradle`中添加：

```groovy
implementation 'com.github.ixiaow.easy:coroutine:x.y.z'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1'
```

(请替换 x. y .z 为最新的版本号: ![](https://jitpack.io/v/ixiaow/easy.svg))

## 使用方式

此库使用方式特别简单，只需实现 `ICoroutineScope`即可使用到相应的Api。

```kotlin
class NewsViewModel : ICoroutineScope {}
```

具体使用

- 开启一个UI协程

```kotlin
fun testUI(){
    launchUI{
        //// 执行相应逻辑
    }
}
```

- 开启一个IO协程

```kotlin
fun testIO(){

    launchIO{
        //// 执行相应逻辑
    }
}
```

- 开启一个Work协程

```kotlin
fun testWork(){

    launchWork{
        //// 执行相应逻辑
    }
}
```

其它使用请参考：[ViewScope](https://github.com/ixiaow/easy/blob/master/coroutine/src/main/java/com/github/ixiaow/coroutine/ViewScope.kt)