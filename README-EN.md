
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/title.png)<br>
# AnnularMenu is based on Material Design design of the ring menu control. For specific use, please read down.
　　　　　　　　　　　　　![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo3.gif)<br>
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo1.gif)　　　
![image](https://github.com/DingMouRen/AnnularMenuView/raw/master/screenshot/demo2.gif)<br>
##  Usage
In the module build.gradle
```
com.dingmouren.annularmenu:annularmenu:1.0.1
```

## Example Usage
#### 1.Layout XML
```
<com.dingmouren.annularmenu.AnnularMenu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:position="left_bottom" //The menu's location
    app:radius="120dp"         //The radius of the menu
    app:toggleDuration="500"   //Open / close the menu duration of the animation
    >

    <com.dingmouren.annularmenu.ShadowImageView
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_centerHorizontal="true"
        app:src="@mipmap/menu" //The first is the button that opens or closes the menu
        app:shadowOffsetX="2dp" //The shadow of the offset in the direction of X
        app:shadowOffsetY="2dp" //The offset of the shadow in the Y direction
        app:shadowRadius="5dp"  //Shadow radius
        />
    <com.dingmouren.annularmenu.ShadowImageView
        android:layout_width="45dp"
        android:layout_height="45dp"
        android:layout_centerHorizontal="true"
        app:src="@mipmap/item1" //The following are the item menu
        app:shadowOffsetX="2dp"
        app:shadowOffsetY="2dp"
        app:shadowRadius="2dp"
        />
        ...
 </com.dingmouren.annularmenu.AnnularMenu>
```

#### 2.Java code
```
menu1.setOnMenuItemClickListener(new AnnularMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
```

## Attribute
AnnularMenu | Description
-------|---
position|The menu's location, values are: left_top, left_bottom, right_top, right_bottom, and the default is right_bottom
radius|The radius of the menu, when the number of item items in the menu increases, you need to set the radius to adjust the item spacing
toggleDuration|Open / close the menu duration of the animation, the default is 500 milliseconds

ShadowImageView | Description
-------|---
shadowOffsetX|The shadow of the offset in the direction of X
shadowOffsetY|The offset of the shadow in the Y direction
shadowRadius|Shadow radius
src|picture

## AnnularMenu’Method
AnnularMenu | Description
-------|---
public boolean isOpen()| Determines whether the menu is open or closed at the moment
public void toggle()|Switch menu
public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener)|Listen for the click of the menu item item
public void setMenuButtonClickable(boolean clickable)|Change the menu button clickable state
#### Welcome to make suggestions

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