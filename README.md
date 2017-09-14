# Easy
 
包含一些常用的工具类

* CrashHandler android程序崩溃日志记录类

* LogUtil android 日志记录类，可根据日志level控制日志输出

* EView 显示类

* CollectionUtil 集合操作类

* ToastUtil 吐司工具类

* EPermission 简单的权限封装

* CustomProgressDialog 自定义dialog

* TitleView 自定义titleView对ToolBar进行封装

* NoticeAngle 角标View

* PinnedHeaderExpandableListView及其Adapter

使用方式：

在Application中，初始化

	E.init(Context context);
	
	
Gradle:

        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
         
        compile 'com.github.xiaowujiang:Easy:releases'
