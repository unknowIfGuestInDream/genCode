本项目用于对数据库中的存储过程生成java代码

运行后访问 [http://localhost:8669/gen](http://localhost:8669/gen)

## 技术框架

后台使用springBoot, druid连接池, 前台使用extjs4.0

## 项目介绍

现在已经支持oracle, mysql, mariadb, sqlserver的存储过程代码生成

* NameConventService 命名规范
* DataBaseProcedureService 数据库对应获取过程相关的sql&数据库关键词对应的java类型等
* DataBaseInfoService 数据源管理
* GenProcedureModelService 存储过程代码生成模板

如需要添加自己的命名规范则新增 NameConventService 的实现类, 并在前台static/public/js/store/nameConventType.js中添加自己的实现类编码  
如需要添加自己的代码模版则需要实现GenProcedureModelService, 并在前台static/public/js/store/genProcedureModelType.js中添加自己的代码模板编码  
如需添加新的数据库则实现DataBaseProcedureService, 并在static/public/js/store/driver.js中添加新的数据库的驱动名称

## 代码说明
### 缓存
1. 为具有过期时间的缓存Cache类 CacheManage负责维护Cache缓存, 目前缓存前台存储过程查询结果等，减少数据库压力
2. 各个工厂类(factory目录下)各个类内部维护的集合，负责存储各个接口的实现类
3. DataSourceUtilFactory缓存数据库连接池，减少连接池频繁创建销毁的开销
4. CacheManageController负责缓存的查询，清除等

### 定时
位于schedule文件夹下

### 枚举类
位于enums下

## 最后

如果有问题或者需求可以发送邮件或者提交Issues