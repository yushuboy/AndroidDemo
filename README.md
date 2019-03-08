# 1、dependencies版本的统一
 - 创建config.gradle文件统一版本
 - 在项目的build.gradle文件中引入apply from: "config.gradle"
# 2、创建module_base项目
 - 用于存放一些公共数据
 - 集中管理公共的dependencies
 - 其他module依赖该module
# 3、创建其他module
 - 在gradle.properties文件中设置isBuildModule=false用于其他该module是lib还是app
 - 修改module的build.gradle文件
 - ```java
 if (isBuildModule.toBoolean()) {
          apply plugin: 'com.android.application'
      } else {
          apply plugin: 'com.android.library'
      }
      ```
      
  - 在module的main文件下创建debug和release文件夹，并放入对应的文件
  - ```java
   resourcePrefix 'login_'
    sourceSets {
        main {
            if (isBuildModule.toBoolean()) {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
                java.srcDirs = ['src/main/java','src/main/debug']
            } else {
                manifest.srcFile 'src/main/release/AndroidManifest.xml'
                java.srcDirs = ['src/main/java','src/main/release']
            }
        }
    }```
# 4、引入阿里的ARouter路由
 - Java和kotlin两种不同方式的引入
    
## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持module之间传大容量的数据
 - 支持添加拦截器
 - 支持module单独作为Application编译
 - 支持主app的Application在各个module内调用
 - 路由引导模块：自动生成module的调用方法 （ Thank for [Obo](https://github.com/OboBear)）

