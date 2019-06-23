# Campus
Pocket Campus , based on android platform.

<img width="125" height="125" src="https://github.com/longer96/CDTU/blob/master/images/logo.png"/>
大一时针对我校开发的校园客户端，方便学生查课表、成绩、一卡通消费记录、失物招领等等。遵循MD设计原则，数据大多通过抓包获取，现开源（已屏蔽学校相关信息，怕被请回去喝茶）

实现功能
----
* **课表功能的实现**（很好看，用户体验棒）
  > 涉及代码：CourseActivity.java , Course_addActivity.java , Course_editActivity.java
  * 高校大多数都是使用正方系统，课表的数据也是从正方系统爬取解析的，解析的方法大家可以参考utils目录下StreamTools.java getcourse方法。
  <br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E8%AF%BE%E8%A1%A8.png"/>
  * 扁平设计，大大提升内容的占有率  
  * 个性化设计，可使用系统自带背景图以及自定义背景图
  * 用户可编辑课程，查看学期安排以及教学时间表
  <br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E8%AF%BE%E8%A1%A8%E5%B1%95%E7%A4%BA.gif"/>  <img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E6%8D%A2%E8%83%8C%E6%99%AF.gif"/>  <img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/课表添加课.gif"/>
<br><br>

* **主页轮播图的实现**
  > 涉及代码：MainActivity.java
  * 预览图使用的是学校的风景图，后台可更换图片url、数量、添加事件。
  * 轮播图的实现参考 [RollViewPager](https://github.com/Jude95/RollViewPager)
  <br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E4%B8%BB%E9%A1%B5%E8%BD%AE%E6%92%AD%E5%9B%BE.gif"/>
<br><br>

* **主页布局实现**（主要参考支付宝）
  > 涉及代码：MainActivity.java , Fragment_Menu.java
  * 底部导航实现参考  [AHBottomNavigation](https://github.com/aurelhubert/ahbottomnavigation)
  * 布局严格遵守 Material Design 准则。 [Material Design 中文版](http://wiki.jikexueyuan.com/project/material-design/)
  * 实现了信息的整合，将有效的信息提取显示，避免效益冗余。
  * 更多功能菜单的实现,可下载查看布局文件app_bar_main.xml    [参考博客](http://blog.csdn.net/yanzhenjie1003/article/details/51938425)
  * 水平RecyclerView的使用，相关博客：[RecyclerViewSnap](https://github.com/rubensousa/RecyclerViewSnap)
  * 场景过度动画 可百度`Shared Element Transition`
  <br><br><img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E4%B8%BB%E9%A1%B5%E5%B1%95%E7%A4%BA.gif"/>  <img width="200px" style="max-width:100%;" src="https://github.com/longer96/CDTU/blob/master/images/%E4%B8%BB%E9%A1%B5%E5%B1%95%E7%A4%BA2.gif"/>
<br><br>

![image](https://github.com/ZoomZFX/Campus/blob/master/161744_Campus.jpg)
![image](https://github.com/ZoomZFX/Campus/blob/master/162101_Campus.jpg)
![image](https://github.com/ZoomZFX/Campus/blob/master/162128_Campus.jpg)

关于我
----
即将毕业的学生，因学历压制，想找个对口的工作都不容易（工作尽心尽责，有上进心）   
<br>★★★★★   **Ps：望收留！（北京 天津）**   ★★★★★
<br>Email：invinciblefx@gmail.com
