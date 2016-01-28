# AdhesiveLoadingView
一个具有粘性的滑动小球，具有跌落反弹形成文字的效果，形成loading字样

# 效果
![](https://github.com/yxping/AdhesiveLoadingView/raw/master/half1.gif)
![](https://github.com/yxping/AdhesiveLoadingView/raw/master/half2.gif)

# 项目结构
![](https://github.com/yxping/AdhesiveLoadingView/raw/master/structure.png)
这个动画包括了三个过程： 
  1.小球旋转放大，其中还有震动效果 
  2.小球缩小衍生水滴，迅速跌落 
  3.文字弹出展现

在结构上是主要是通过controller对三个animator进行一个控制，并作为其中的信息传递媒介链接各个animator，将canvas分发给animator进行绘制。而view通过controller的初始化来达到展示动画的效果。其中，动画的效果是由AnimationSet进行顺序的控制。
# 用法
1.通过xml引入
``` xml
<com.yxp.loading.lib.AdhesionLoadingView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"/>
```
2.通过java引入
