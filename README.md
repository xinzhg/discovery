## 项目 Discovery ##

**Discovery** 是基于webx的java web应用。

### 运行前提条件 ###

安装jdk，推荐1.6。

安装maven，推荐3.x。

安装ide，推荐idea，eclipse。

### 运行 ###

在项目根目录下运行mvn clean install，然后cd web，运行mvn jetty:run

访问 http://localhost:8080/index.htm?name=123


### 文件结构 ###

biz---业务代码区

web---页面逻辑代码区

### 文件命名方式 ###

java文件---首字母大写，后面字母采用驼峰

例如：Demo.java , DemoPage.java 等

vm文件---首字母小写，后面字母采用驼峰

例如：demo.vm , demoPage.vm

### 引用 reference ###

存储：http://developer.baidu.com/wiki/index.php?title=docs/pcs/rest/file_data_apis_list


