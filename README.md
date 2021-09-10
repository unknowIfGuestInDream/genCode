本项目功能：
1. 存储过程生成java代码
2. 根据表生成存储过程
3. springBoot/springCloud项目初始化  -- 3.0版本开发计划

## 技术框架

后台使用springBoot, druid连接池, 前台使用extjs4.0, 模版使用freemarker引擎

## 项目启动
数据库sql文件在 database文件下  
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
```

druid监控账号密码默认都为admin

运行后访问 [http://localhost:8669/gen](http://localhost:8669/gen)

## 项目介绍

存储过程生成代码功能支持oracle, mysql, mariadb, sqlserver数据库
表生成存储过程目前仅支持oracle

* NameConventService 命名规范
* DataBaseProcedureService 数据库对应获取过程相关的sql&数据库关键词对应的java类型等
* DataBaseInfoService 数据源管理
* AbstractGenProcedureModel 存储过程代码生成模板
* TableToProcedureService 表生成过程相关接口

如需要添加自己的命名规范则新增 NameConventService 的实现类并新增枚举类NameConventType参数,
 并在前台static/public/js/store/nameConventType.js中添加自己的实现类编码  
如需要添加自己的代码模版则需要实现AbstractGenProcedureModel并新增枚举类GenProcedureModelType参数,
并在前台static/public/js/store/genProcedureModelType.js中添加自己的代码模板编码  
如需添加新的数据库则实现DataBaseProcedureService并新增枚举类DataBaseType参数,
 并在static/public/js/store/driver.js中添加新的数据库的驱动名称

## 代码说明
1. Cache类为具有过期时间的缓存 CacheManage负责维护Cache缓存, 目前主要应用于缓存前台存储过程查询结果等，减少数据库压力
2. 各个工厂类(factory目录下)各个类内部维护的集合，负责存储各个接口的实现类
3. DataSourceUtilFactory缓存数据库连接池，减少连接池频繁创建销毁的开销
4. CacheManageController中为缓存的查询，清除等API
5. 定时任务位于schedule文件夹下，目前任务只有每周五清空数据库连接池缓存

##最后
如果有问题或者需求可以发送邮件或者提交Issues