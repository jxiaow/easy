## ExpandView

###### 自定义的属性

|       属性        |      格式  |       说明
|-------------------|----------|------------------
|  title_text       | string\reference    |       item标题
|  expand           | boolean             |       内容展开判断
|   title_size      | dimension           |item标题的文字大小
|title_text_color   | reference           |item标题的文字颜色
|indicator_expand   | reference           |  展开指示图标
|indicator_collapse | reference           |    收缩指示图标
|expand_layout      | reference           |    伸缩的布局
|item_background    |reference\integer\color|  item的颜色

###### 布局使用示例

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
       xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context="cn.xwj.expandview.MainActivity">
    <cn.xwj.widget.ExpandView
           android:layout_margin="5dp"
           app:item_background="@color/colorAccent"
           android:id="@+id/expand_view"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           app:expand_layout="@layout/expand_layout"
           app:indicator_collapse="@drawable/collapse"
           app:indicator_expand="@drawable/expand"
           app:title_text="厂家"
           app:title_size="18dp">
        </cn.xwj.expand_view.ExpandView>
</LinearLayout>
```

###### java中

```java
mExpandView = (ExpandView) this.findViewById(R.id.expand_view);
```

