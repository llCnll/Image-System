Image-System
---
使用jGit来操作Git作为[图片仓库](https://github.com/llCnll/image-repository)存储图片  
此仓库用于存放非敏感信息, 头像, 图片等.  


## 基本流程
配置文件
```xml
git.dir=/home/chennan02/image-repository
git.repo-dir=/home/chennan02/image-repository/.git
git.remote-url=git@github.com:llCnll/image-repository.git
```
1. 注册GitUtil, 容器初始化的时候执行GitUtil.afterPropertiesSet(), 通过配置文件中的
进行初始化配置, 判断repo-dir是否存在, 不存在则先进行clone, 的到Git对象, 后续操作都是基于Git对象的.  
2. 表单类型 enctype="multipart/form-data", 请求 /avatar.
3. cn.chennan.imagesystem.handler.ControllerExceptionHandler 拦截器, 判断图片大小是否超过设置大小..
4. 进入/avatar请求, 进行业务操作
```
1. 使用UUID作为文件名
2. 写入到配置的URL中(仓库位置 ${git.dir}/avatar)
3. jGit提交至远程仓库
4. 使用消息队列(rabbitmq)将用户ID和文件名称作为消息, 进队, 给别的系统消费.
```

## 测试
### 文件上传
> index.html

### 文件剪裁
使用cropbox.js插件进行剪裁
> cropbox.html

## cros跨域
[相关文章](https://juejin.im/post/5b99dcca6fb9a05d3154f8b7)

这里使用注解@CrossOrigin  
```java
    @CrossOrigin(origins = "http://localhost:8080")
    @CrossOrigin(origins = "*", allowCredentials = "true")
```

origins="*" : (默认)表示所有域名均可访问, 也可置顶域名访问
allowCredentials = "true" : (默认)表示是否允许cookie随请求发送, 如果为`true` 则需要html中的ajax中加入`xhrFields:{withCredentials:true},`  
里面还有很多参数, 可以具体详见源文件.

因此若无特殊要求可以简写成 `@CrossOrigin`
