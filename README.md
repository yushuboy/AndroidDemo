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
    }```
# 4、引入阿里的ARouter路由
 - Java和kotlin两种不同方式的引入
 - annotationProcessor注解处理器需要时，要在每个module的dependencies中依赖
# 5、在app和lib之间切换
 - 创建debug和release文件夹，放入不同的AndroidManifest和代码，公共文件夹Java不要放，否则代码会重复
 - 在moduleBase模块中创建ApplicationService接口，用于处理Application，通过接口来获取Application和进行初始化
# 6、不同的变体productFlavors
 - 作为lib的module,设置如下
 - '''    //创建productFlavors
            if (!isBuildModule){
                publishNonDefault true
            }
            productFlavors {
                login {//配置测试版包名和应用名
                    dimension "versionCode"
                }
                newUser {//配置生产版包名和应用名
                    dimension "versionCode"
                }
            }''
 - 创建login和newUser两个文件夹，里面的放置不同的代码和资源文件res，文件结构和main里面的一样，打包或调试时它们会合并
 - 不同的变体可以有不同的依赖，newUser变体使用newUserImplementation来独自依赖
 - 在APP的build.gradle文件依赖其他lib module，使用implementation进行依赖，Gradle plugin 3.0.0+会自动感知并匹配对应的variant（前提是app与library中有对应的variant类型）
 - 当app和lib中的变体不一致时，需要采用回退策略,因为lib中没有newLogin变体，所以从['login','newUser']中选择
 '''
   newLogin{
             //https://codezjx.com/2017/11/23/gradle-plugin-3-0-0-migration/
             matchingFallbacks = ['login','newUser']
         }''


