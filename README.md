本项目用于对数据库中的存储过程生成代码

## 技术框架

后台使用springBoot, druid连接池, 前台使用extjs4.0

## 项目介绍

现在已经支持oracle,mysql,mariadb的存储过程代码生成

* NameConventService 命名规范
* DataBaseProcedureService 数据库对应获取过程相关的sql&数据库关键词对应的java类型等
* DataBaseInfoService 数据源管理
* GenProcedureModelService 存储过程代码生成模板

如需要添加自己的命名规范则新增 NameConventService 的实现类, 并在前台static/public/js/store/nameConventType.js中添加自己的实现类编码  
如需要添加自己的代码模版则需要实现GenProcedureModelService, 并在前台static/public/js/store/genProcedureModelType.js中添加自己的代码模板编码  
如需添加新的数据库则实现DataBaseProcedureService, 并在static/public/js/store/driver.js中添加新的数据库的驱动名称

## 最后

如果有问题或者需求可以发送邮件或者提交Issues