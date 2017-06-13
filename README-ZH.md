
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/title.png)<br>
# AnnularMenu是基于Material Design设计的环形菜单控件。具体使用请往下阅读。
　　　　　　　　　　　　　![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo3.gif)<br>
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo1.gif)　　　
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo2.gif)<br>
##  引入方式
在module的build.gradle中
```
com.dingmouren.annularmenu:annularmenu:1.0.1
```

## 怎么使用
1.布局文件
```
<com.dingmouren.annularmenu.AnnularMenu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:position="left_bottom" //菜单的位置
    app:radius="120dp"         //菜单的半径
    app:toggleDuration="500"   //菜单打开或者关闭的动画持续时间
    >

    <com.dingmouren.annularmenu.ShadowImageView
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_centerHorizontal="true"
        app:src="@mipmap/menu" //第一个是打开或者关闭菜单的按钮
        app:shadowOffsetX="2dp" //阴影在x方向的偏移量
        app:shadowOffsetY="2dp" //阴影在y方向的偏移量
        app:shadowRadius="5dp"  //阴影的半径
        />
    <com.dingmouren.annularmenu.ShadowImageView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:layout_centerHorizontal="true"
        app:src="@mipmap/item1" //下面的都是菜单的item项
        app:shadowOffsetX="2dp"
        app:shadowOffsetY="2dp"
        app:shadowRadius="2dp"
        />
        ...
 </com.dingmouren.annularmenu.AnnularMenu>
```

2.代码中的菜单item项点击的监听
```
menu1.setOnMenuItemClickListener(new AnnularMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
```

## 属性
AnnularMenu | 描述
-------|---
position|菜单的位置，取值有:left_top left_bottom right_top right_bottom,默认是right_bottom
radius|菜单的半径，在菜单的item项数目增多的时候需要设置radius调整item的间距
toggleDuration|菜单打开/关闭动画的持续时间，默认是500毫秒

ShadowImageView | 描述
-------|---
shadowOffsetX|阴影在x方向上的偏移量
shadowOffsetY|阴影在y方向上的偏移量
shadowRadius|阴影半径
src|指定图片

## AnnularMenu公开的方法
AnnularMenu | 描述
-------|---
public boolean isOpen()| 判断菜单当前是打开还是关闭状态
public void toggle()|根据菜单当前状态切换打开或者关闭菜单
public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener)|菜单item项的点击监听
public void setMenuButtonClickable(boolean clickable)|设置菜单按钮是否可点击

欢迎大家提建议

## License
```
Copyright (C) 2017

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License
```