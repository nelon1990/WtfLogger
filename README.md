# WtfLogger

[![](https://jitpack.io/v/nelon1990/WtfLogger.svg)](https://jitpack.io/#nelon1990/WtfLogger)


## 使用
链式调用
###### K-V打印

    WtfLog.d.title("示例")
                    .tmpTag(TAG)
                    .key("key1").intVal(1)
                    .key("key2").stringVal("string1")
                    .key("key2").floatVal(3.5f)
                    .key("map").mapVal(mMap)
                    .key("list").listVal(Arrays.asList(1, 2, 3, 4))
                    .print();

![](http://p1.bpimg.com/567571/c8705631d14a9466.png)

###### bean打印

    public class User {
        String username;
        String password;
    }

    WtfLog.d.title("示例")
                    .tmpTag(TAG)
                    .bean(user)
                    .print();

![](http://p1.bqimg.com/567571/e6e05133e2e62ac5.png)

###### 线程与堆栈打印
    WtfLog.d.title("示例")
                    .tmpTag(TAG)
                    .stackTrace()
                    .threadInfo()
                    .print();
![](http://i1.piimg.com/567571/9575289ca165962d.png)


## 集成
Step 1. 在你的project的gradle脚本，仓库配置项中增下下发仓库配置；

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


Step 2. module的gradle脚本中，增加下方依赖

	dependencies {
	        compile 'com.github.nelon1990:WtfLogger:v1.0.0'
	}