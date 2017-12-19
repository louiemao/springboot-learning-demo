# Undertow介绍

Undertow 是一个采用 Java 开发的灵活的高性能 Web 服务器，提供包括阻塞和基于 NIO 的非堵塞机制。Undertow 是红帽公司的开源产品，是 Wildfly 默认的 Web 服务器。
Undertow 提供一个基础的架构用来构建 Web 服务器，这是一个完全为嵌入式设计的项目，提供易用的构建器 API，完全兼容 Java EE Servlet 3.1 和低级非堵塞的处理器。
 
# Undertow几个特点

* 轻量化 - Undertow 是一个Web 服务器，但它不像传统的Web 服务器有容器的概念，它由两个核心jar包组成，使用API加载一个Web应用可以使用小于10MB的内存。
* HTTP Upgrade 支持 - 设计WildFly时一个重要的考虑因素是在云环境中减少端口数量的需求。在云环境中，一个系统可能运行了几百个甚至几千个WildFly实例。基于HTTP使用HTTP Upgrade可以升级成多种协议，Undertow提供了复用这些协议的能力。
* Web Socket 支持 - 对Web Socket的完全支持，用以满足Web应用现在面对巨大数量的客户端，以及对JSR-356规范的支持。
* Servlet 3.1 的支持 - Undertow支持Servlet 3.1，提供了一个机会来构建一个超越Servlet规范、对开发人员非常友好的系统。
* 可嵌套性 - Web 服务器不在需要容器，我们只需要通过API在J2SE代码下快速搭建Web服务。

 

# 相关链接及快速示例

Undertow 社区主页（http://undertow.io/）：包括Undertow相关的所有新闻，消息。
Undertow 源代码（https://github.com/undertow-io/）：包括所有Undertow相关的代码
 

# Spring Boot中使用Undertow；

在spring boot中使用Undertow特别简单，只需要修改下咱们的pom.xml文件即可：
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
 
  <groupId>com.kfit</groupId>
  <artifactId>spring-boot-package-A</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
 
  <name>spring-boot-package-A</name>
  <url>http://maven.apache.org</url>
 
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!-- jdk版本号，angel在这里使用1.8，大家修改为大家本地配置的jdk版本号即可 -->
    <java.version>1.8</java.version>
<!--     <start-class>com.kfit.App</start-class> -->
  </properties>
 
    <!--
       spring boot 父节点依赖,
       引入这个之后相关的引入就不需要添加version配置，
       spring boot会自动选择最合适的版本进行添加。
     -->
    <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>1.4.1.RELEASE</version><!-- 1.4.1.RELEASE , 1.3.3.RELEASE-->
    </parent>
 
  <dependencies>
    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
       <!-- 从依赖信息里移除 Tomcat配置 -->
       <exclusions> 
            <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
       </exclusions> 
    </dependency>
   
    <!-- 然后添加 Undertow -->
    <dependency> 
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-undertow</artifactId>
    </dependency> 
     
  </dependencies>
</project>
```
重新启动运用程序，查看控制台的输出信息，能够看到如下信息：
```
main] .UndertowEmbeddedServletContainerFactory :
main] io.undertow.websockets.jsr     
main] io.undertow.servlet   
```
 

# Undertow之配置信息

Spring boot也为我们提供了Undertow常用的配置信息（在application.properties）:
```
server.undertow.accesslog.dir= # Undertow access log directory.
server.undertow.accesslog.enabled=false # Enable access log.
server.undertow.accesslog.pattern=common # Format pattern for access logs.
server.undertow.accesslog.prefix=access_log. # Log file name prefix.
server.undertow.accesslog.rotate=true # Enable access log rotation.
server.undertow.accesslog.suffix=log # Log file name suffix.
server.undertow.buffer-size= # Size of each buffer in bytes.
server.undertow.buffers-per-region= # Number of buffer per region.
server.undertow.direct-buffers= # Allocate buffers outside the Java heap.
server.undertow.io-threads= # Number of I/O threads to create for the worker.
server.undertow.max-http-post-size=0 # Maximum size in bytes of the HTTP post content.
server.undertow.worker-threads= # Number of worker threads.
```

# 参考资料
[林祥纤-110. Spring Boot Undertow【从零开始学Spring Boot】](http://412887952-qq-com.iteye.com/blog/2363427)
[嵌入式Web服务器Undertow](http://www.oschina.net/p/undertow)
[JBoss 系列九十三： 高性能非阻塞 Web 服务器 Undertow](http://blog.csdn.net/kylinsoong/article/details/19432375)