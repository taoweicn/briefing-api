# briefing-api

Spring Boot 练手项目，覆盖常见应用场景：RESTful API，JWT，参数校验，文件上传，邮件发送，统一返回格式，自动化测试，Filter 过滤器，AOP 编程，API 可视化，docker 部署，错误处理等。

## 功能详解

### RESTful API

REST，即 REpresentational State Transfer（表现层状态转移）。

- Resource：资源，即数据。
- Representational：某种表现形式，比如 JSON，XML，JPEG 等。
- State Transfer：状态变化，通过 HTTP 动词实现。

RESTful，就是用 URL 定位资源，用 HTTP 动词（GET, POST, PUT, PATCH, DELETE 等）描述操作。

RESTful API 的相关规范：

- 用名词描述资源，用 HTTP 方法定义操作，如 CRUD(create, read, update, delete) 分别对应 POST, GET, PUT, DELETE 等 HTTP 方法。
- 将 API 的版本号放入 URL。
- 过滤信息应该在 API 中提供参数。

### JSON Web Token

JWT 由头部（Header），负载（Payload）和签名（Signature）三部分组成。前两个部分是 Base64 编码，相当于明文加密，所以不可存储敏感信息。Signature 部分是对前两部分的签名，防止数据被篡改。所以 JWT 是一种用户可见，状态储存在客户端的身份校验方式，签名是验证其真实性的唯一方法。

当用户登录时把用户的唯一标识（尽量是非敏感信息）存入 JWT 的 payload 中并用密钥计算签名拼接后传回客户端，当用户发送请求时，使用 Filter 过滤器核验其头部的 `Authrorization` 字段携带的JWT是否合法，如果是，保存身份信息并继续下一步操作，否则抛出 401 异常。

### 参数校验

主要使用了 `spring validation`，在需要校验的字段上添加相应的 java 注解，然后在参数上添加 `@Valid` 标注，最后定义好 `MethodArgumentNotValidException` 的错误处理即可。

### 文件上传

把上传的文件根据时间戳重命名后丢到用户指定的路径中，然后配置相应的静态资源代理和访问路径，最后传回路径即可。后续也可根据需求上传到云存储等。

### 邮件功能

使用了 `org.springframework.boot:spring-boot-starter-mail` 的包，调库大法好。

### AOP 编程

AOP 编程可以看作是传统 OOP 编程的补充。在 OOP 中，基于父子类的继承可以看作是一种纵向的结构，从上到下，从浅到深的拓展，而 AOP 则更像是一种横向的结构：提取一组有相同需求的切面，在这个切面上定义统一的业务逻辑。常见的使用场景有日志记录，权限校验等。

在 Spring Boot 中使用 AOP，只需先引入 Spring Boot AOP 的 starter，然后定义切入点（PointCut），再根据切入点定义所需要的 Advice 来执行对应的业务逻辑即可。

### API 可视化

使用了 swagger 的工具包，需要注意的是在当前版本（2.9.2）中有一个错误需要通过排除传递依赖来避免。

### Gradle

Gradle 是 java 项目的开发测试部署一体化工具，在装有 gradle 的机器上可直接使用 gradle 命令来运行打包代码。在未装有 gradle 的机器上可以使用项目根目录下的 gradlew 命令来代替 gradle ，该命令会根据 `gradle-wrapper.properties` 中的属性自动安装对应版本的 gradle，然后再继续执行构建命令。

### Docker

Docker 是一个虚拟化容器，可以方便地在不同平台上部署应用。项目使用了Docker的多阶段构建来分别打包和发布代码，还使用了 docker-compose 来编排多个容器。另外建议尽量把密钥作为环境变量提取为单独的 `.env` 文件来避免其随源码一起泄露。

## IDE 相关

- 使用了 [google-java-format](https://plugins.jetbrains.com/plugin/8527-google-java-format) 插件格式化代码
- 使用了 [CheckStyle-IDEA](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea) 插件检查代码风格
- 使用Lombok需下载 [idea相关插件](https://plugins.jetbrains.com/plugin/6317-lombok)
- 为 idea 开启 Spring Boot 热更新：
  - 载入依赖：`developmentOnly 'org.springframework.boot:spring-boot-devtools'`
  - `Setting` –> `Build, Execution, Deployment` –> `Compiler` –> 勾选 `Build project automatically`
  - 搜索 `registry`，勾选 ` compiler.automake.allow.when.app.running`

## 相关文档

- [Official Gradle documentation](https://docs.gradle.org)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## 参考项目

- [Spring All](https://github.com/wuyouzhuguli/SpringAll)
- [spring-boot-examples](https://github.com/ityouknow/spring-boot-examples)
- [halo](https://github.com/halo-dev/halo)
