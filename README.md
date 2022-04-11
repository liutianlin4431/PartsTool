# PartsTool-配件工具包

##### 远程执行windows命令-------开启winrm service

```shell
#查看状态-无返回信息则是未启动
winrm enumerate winrm/config/listener
#基础配置
winrm quickconfig
#查看winrm service listener:
winrme winrm/contio/listener
#为winrm service 配置auth:
winrm set winrm/config/service/auth @{Basic= "true"}
#为winrm service 配置加密方式为允许非加密：
winrm set winrm/config/service @{AllowUnencrypted="true"}
#winrm service 启动完毕
```



