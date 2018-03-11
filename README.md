# Javaweb网上书城案例
## 一、项目介绍
### 1.1 项目简介
* 基于Javaweb的简易网上书城，旨在巩固所学知识
### 1.2 项目最终效果展示

![image](/images/003.PNG)
## 二、项目分析
### 2.1 功能分析

![image](/images/001.jpg)
### 2.2 总体架构
* MVC 设计模式：
    * Model:POJO（Plain Old Java Object） 
    * Controller:Servlet
    * View:JSP + EL + JSTL
   
![image](/images/002.PNG)
### 2.3 技术选型
* 数据库：MySQL
* 数据源：C3P0 
* JDBC 工具：DBUtils
* 事务解决方案：Filter + ThreadLocal
* Ajax 解决方案：jQuery + JavaScript + JSON + google-gson
* 层之间解耦方案：工厂设计模式

### 2.4 难点分析
* 通用的分页解决方案
* 带查询条件的分页
* 使用 Filter + ThreadLocal 解决事务

## 三、项目设计
### 3.1 实体类设计

![image](/images/004.png)
### 3.2 数据表设计

![image](/images/005.png)
![image](/images/006.PNG)

### 3.3 搭建环境
* 加入 C3P0 数据源
    * 加入jar包
    * 在类路径下加入配置文件:[c3p0-config.xml](/src/c3p0-config.xml)
* 加入 dbutils 工具类
* 加入 JSTL 
* 其它：使用随时加入

### 3.4 dao层设计

![image](/images/007.PNG)

[点击查看Java代码](/src/me/bookstore/dao)

### 3.4 封装翻页信息的Page类

![image](/images/008.PNG)
![image](/images/009.PNG)

### 3.5 封装查询条件的CriteriaBook类

![image](/images/010.PNG)

### 3.6 功能实现：查看图书信息

![image](/images/011.PNG)

### 3.7 翻页过程中保存查询条件

![image](/images/012.PNG)

### 3.8 使用jQuery完成提示输入的页面不合法

![image](/images/013.PNG)

### 3.9 查看图书详细信息

![image](/images/014.PNG)

### 3.9 加入购物车

![iamge](/images/015.PNG)

### 3.10 加入购物车的流程

![image](/images/016.png)

### 3.11 购物车的数据结构

![image](/images/017.PNG)

### 3.12 购物车实体类设计

![image](/images/018.PNG)

* 具体点击[这里](/src/me/bookstore/domain)查看

### 3.13 查看购物车

![image](/images/019.PNG)

### 3.14 删除购物项

![image](/images/020.PNG)

### 3.15 清空购物车

![image](/images/021.PNG)

### 3.16 继续购物超链接

![image](/images/022.PNG)

### 3.17 Ajax修改购物车商品数量

![image](/images/023.PNG)
* Ajax异步请求信息

![image](/images/024.PNG)

### 3.18 Ajax修改购物车商品数量的校验

![image](/images/025.PNG)
### 3.19 结账操作
* 结账分析

![image](/images/026.PNG)
* 结账校验

![image](/images/027.PNG)
* 结账流程

![image](/images/028.jpg)

### 3.20 关于ThreadLocal

* 通过 ThreadLocal.set() 将对象的引用保存到各线程的自己的一个 map 中，每个线程都有这样一个map，执行ThreadLocal.get()时，各线程从自己的map中取出放进去的对象，因此取出来的是各自自己线程中的对象，ThreadLocal 实例是作为map的key来使用的。
* 一般情况下，通过ThreadLocal.set() 到线程中的对象是该线程自己使用的对象，其他线程是不需要访问的
* ThreadLocal 不是用来解决共享对象的多线程访问问题的：如果ThreadLocal.set() 进去的东西本来就是多个线程共享的同一个对象，那么多个线程的 ThreadLocal.get() 取得的还是这个共享对象本身，还是有并发访问问题。
* ThreadLocal的应用场合：按线程多实例（每个线程对应一个实例）的对象的访问。

![image](/images/029.PNG)

>ThreadLocal + Filter 处理事务

![image](/images/030.PNG)

>查看交易记录

* 新建一个 users.jsp, 该页面中有一个表单，表单中只有一个字段 username，提交到 UserServlet
* 在 Servlet 中：
    * 获取 username 请求参数的值
    * 调用 UserService 的 getUser(username) 获取User 对象：要求 trades 是被装配好的，而且每一个 Trrade 对象的 items 也被装配好
* 具体：
    * 调用 UserDAO 的方法获取 User 对象
    * 调用 TradeDAO 的方法获取 Trade 的集合，把其装配为 User 的属性
    * 调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
* 把 User 对象放入到 request 中
* 转发页面到 /WEB-INF/pages/trades.jsp
    * 获取 User
    * 遍历 User 的 Trade 集合
    * 遍历 Trade 的 TradeItem 的集合

![image](/images/031.PNG)
