## 使用方式
```shell script
sh gen.sh usage  ## 查看可用命令
sh gen.sh start  ## 启动jar
sh gen.sh stop  ## 停止jar
sh gen.sh restart  ## 重启jar
sh gen.sh status  ## 查看jar包状态

```

## 常见问题
### 将脚本文件上传到linux 运行后出现/n等错误信息

解决方式如下:
```shell script
cat > gen.sh
## 将gen.sh的内容粘贴
ctrl+D 退出
chmod u+x gen.sh
```

或者可以修改gen.sh脚本 将起重命令运行修改为echo输出

### 版本迭代，需要修改脚本
例如：3.2版本 -> 3.3版本
```shell script
sed -i 's|gen-3.2.jar|gen-3.3.jar|' gen.sh
```
