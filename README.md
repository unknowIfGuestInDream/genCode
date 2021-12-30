## 项目介绍
本项目功能：
1. 存储过程生成java代码
2. 根据表生成存储过程
3. 表生成自增主键sql
4. 生成数据库表文档
5. 表生成代码

## 技术框架

后台使用springBoot, druid连接池, 前台使用thymeleaf + extjs4.0, 代码模版使用freemarker引擎

## 项目启动
数据库sql文件在 docs/database文件下  
可以通过配置 gen.isdb来决定数据源是数据库版本还是内存存储
gen.isdb=true数据源为数据库版本，也是默认配置  
数据源为内存版本无需配置数据库，可以直接启动，数据存储在内存中，不可持久化

启动无数据库命令
```shell script
java -Dgen.isdb=false -jar jar包
```

有数据库版本无需配置参数, 默认为有数据库版本
```shell script
java -jar jar包
nohup java -jar jar包  >msg.log 2>&1 &  ## 后台启动(有日志)
nohup java -jar jar包  >/dev/null 2>&1 &  ## 后台启动(无日志)
```

druid监控账号密码默认都为admin

运行后访问 [http://localhost:8669/gen](http://localhost:8669/gen)

## 数据库支持
功能 | oracle | mysql | mariadb | sqlserver
----|----|----|----|----
数据源管理| √ | √ | √ | √
存储过程生成代码| √ | √ | √ | √
表生成存储过程| √ | - | - | -
表生成自增主键| √ | √ | √ | -
生成数据库表文档| √ | √ | √ | √
表生成代码| √ | √ | - | -

## 项目代码介绍
* NameConventService 命名规范
* DataBaseProcedureService 数据库对应获取过程相关的sql&数据库关键词对应的java类型等
* DataBaseInfoService 数据源管理
* DataBaseTableService 数据库表相关sql
* AbstractGenProcedureModel 存储过程代码生成模板
* AbstractTableToProcedure 表生成过程相关接口
* AbstractGenCodeModel 表生成代码相关接口
* DataBaseDocumentService 生成数据库文档

## 新增规范，模板以及数据库
* 如需要添加自己的命名规范则新增 NameConventService 的实现类并新增枚举类NameConventType参数,
* 如需要添加自己的存储过程代码模版则需要实现AbstractGenProcedureModel并新增枚举类GenProcedureModelType参数,
* 如需要添加自己的后台等代码模版则需要实现AbstractGenCodeModel并新增枚举类GenCodeModelType参数,
* 如需添加新的JDBC规范的数据库则实现DataBaseProcedureService，DataBaseTableService和AbstractTableToProcedure并新增枚举类DataBaseType参数，依赖中引入新的驱动

## 代码说明
1. Cache类为具有过期时间的缓存 CacheManage负责维护Cache缓存, 目前主要应用于缓存前台存储过程查询结果等，减少数据库压力
2. 各个工厂类(factory目录下)各个类内部维护的集合，负责存储各个接口的实现类
3. DataSourceUtilFactory缓存数据库连接池，减少连接池频繁创建销毁的开销
4. CacheManageController中为缓存的查询，清除等API
5. 定时任务位于schedule文件夹下，目前任务只有每周五清空数据库连接池缓存
6. SimpleCache类为简易缓存, 主要缓存一些不常变数据

## 感谢

- <a href="https://jb.gg/OpenSource"><img src="http://static.xkcoding.com/spring-boot-demo/064312.jpg" width="100px" alt="jetbrains">**
  感谢 JetBrains 提供的免费开源 License**</a>

## 其它

如果有问题或者需求可以发送邮件或者提交Issues

## CHANGELOG

### 3.2
1. 新增表生成代码功能以及页面
2. maven集成git相关插件，新增git信息接口
3. 前台新增form数据缓存，提高用户体验
4. 修复表生成过程功能问题
5. 新增RPC远程调用接口
6. 新增shutdown关闭接口
7. 增强异步线程池

### 3.1
1. 修复使用中发现的问题
2. 日志配置修改为异步
3. 新增复制等功能，优化体验

### 3.0

1. tomcat更换为undertow
2. 新增生成数据库文档功能
3. 修改2.0部分使用问题或功能问题
4. 升级druid版本 1.1.9 -> 1.1.22
5. 优化架构