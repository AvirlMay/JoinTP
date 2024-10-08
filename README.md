# 插件 JoinTP

## 插件.jar位置

`Project/target/yllq-1.0-SNAPSHOT.jar`

Releases中

## 介绍

本插件为服务端插件，版本`Paper 1.17.1`

## 使用说明

1. 在首次安装插件时服务端会自动关闭，并报错。不要慌张，这是**正常现象**。
2. 前往`plugins/JoinTP/config.yml`中更改进入时需要传送到的位置
   
   格式说明：
   ```yml
   teleport:
     world: （你的世界名称）
     x: x坐标
     y: y坐标
     z: z坐标
     yaw: 方向角
     pitch: 俯仰角
   ```
   请注意：所有冒号均为**英文格式**，后方应当添加一个**英文空格**
   
   （如果实在不知道怎么输入，它会自动生成，只要写到冒号后面再空一格（有自动的空格）的位置就可以了
   
3. 重启服务器，当玩家加入到服务器时，就会自动传送至对应世界的坐标啦！

如果有任何问题，欢迎使用GitHub Issues功能提交反馈。
