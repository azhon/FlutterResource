### 一、Flutter路由、图片、字体路径等资源生成插件(Android Studio、IDEA...)
<img src="https://github.com/azhon/FlutterResource/blob/main/imgs/plugin.png" width="750" >

### 二、功能介绍

#### [使用案例](https://github.com/azhon/todo-flutter/tree/main/example)

#### Build Assets

- 获取项目`assets/`目录下的所有文件，生成`*_assets.dart`辅助类。
- 注意：2.0x，3.0x格式的辨率文件夹下的文件和`iconfont`相关的文件会被忽略

#### Build Routes

- 获取项目`lib/`目录下所有文件名以`*page.dart`结尾的文件，生成`*_route.dart`辅助类。

#### Build Iconfont
- 获取项目`pubspec.yaml`中配置的`flutter:fonts`图标字体，生成`*_icon.dart`辅助类。
- 注意：配置的fonts图标字体文件必须以`*iconfont.ttf`结尾，同时在`ttf`文件旁边还需要放置`iconfont.json`文件。（在iconfont网站下载的时候都会有这些）

### 三、使用
- [插件市场安装](https://plugins.jetbrains.com/plugin/22595-flutterresource)

- 在`pubspec.yaml`可以配置`flutter_res`字段，如下：

```yaml
flutter_res:
  # 当前模块是否是子模块，对生成的图片资源路径有影响
  isModule: false
  # 配置生成的文件名和类名
  prefix: hsh
  # 忽略需要扫描的文件夹名称
  ignoreDir:
    - images-zh
```
- 如果`isModule`没有配置，默认值为`true`
- 如果`prefix`没有配置，默认值为`pubspec.yaml`中的`name`字段
- 生成的文件位于`/lib/generated/`目录下
