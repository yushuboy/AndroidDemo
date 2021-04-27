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
 - ```
 if (isBuildModule.toBoolean()) {
          apply plugin: 'com.android.application'
      } else {
          apply plugin: 'com.android.library'
      }
      ```
      
  - 在module的main文件下创建debug和release文件夹，并放入对应的文件
  - ``` 
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
    }
    ```

# 4、引入阿里的ARouter路由
 - Java和kotlin两种不同方式的引入
 - annotationProcessor注解处理器需要时，要在每个module的dependencies中依赖

# 5、在app和lib之间切换
 - 创建debug和release文件夹，放入不同的AndroidManifest和代码，公共文件夹Java不要放，否则代码会重复
 - 在moduleBase模块中创建ApplicationService接口，用于处理Application，通过接口来获取Application和进行初始化

# 6、不同的变体productFlavors
 - 作为lib的module,设置如下
 - ```
 //创建productFlavors
            if (!isBuildModule){
                publishNonDefault true
            }
            productFlavors {
                login {//配置测试版包名和应用名
                    dimension "versionCode"
                }
                register {//配置生产版包名和应用名
                    dimension "versionCode"
                }
            }
      ```
 - 创建login和register两个文件夹，里面的放置不同的代码和资源文件res，文件结构和main里面的一样，打包或调试时它们会合并
 - 不同的变体可以有不同的依赖，register变体使用registerImplementation来独自依赖
 - 在APP的build.gradle文件依赖其他lib module，使用implementation进行依赖，Gradle plugin 3.0.0+会自动感知并匹配对应的variant（前提是app与library中有对应的variant类型）
 - 当app和lib中的变体不一致时，需要采用回退策略,因为lib中没有newLogin变体，所以从['login','newUser']中选择
 ```
   newLogin{
             //https://codezjx.com/2017/11/23/gradle-plugin-3-0-0-migration/
             matchingFallbacks = ['login','newUser']
         }
    ```

# 7、JNI部分
 - JNI是Java和C语言之间相互转换的一套接口，如何Java的String需要转化成C中的char*使用
 - 在SDK的SDK tool选项中下载LLDB(调试)、CMake(编译)、NDK
 - 配置好NDK环境变量
 - 将ndk版本差异检测过滤掉，在gradle.properties中添加：android.useDeprecatedNdk=true
 - 创建native方法，并使用javah -jni+类名，创建头文件.h
 - 把创建好的.h头文件移动到jni目录下，并创建对应的.c文件
 - 配置module的build.gradle和CMakeLists.txt
 - Java调用C方法，非静态方法，和对象实例方法
 - C调用Java方法，通过FindClass获取类对象，通过GetMethodID获取方法ID，具体查看jni_show_dialog.c文件
 - JNI异常处理

# 8、View Module部分
 - RecycleView复杂布局事例
 - 单Activity+多Fragment，Fragment栈的使用
 - Activity和Fragment的万能接口通信方式

# 9、IPC部分
 - Serializable和Parcelable序列化的区别
 - Messenger的使用
 - AIDL的使用
