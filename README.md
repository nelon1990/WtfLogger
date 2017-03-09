# WtfLogger

[![](https://jitpack.io/v/nelon1990/WtfLogger.svg)](https://jitpack.io/#nelon1990/WtfLogger)

## 说明
>1. 链式调用；
>2. 格式化输出；
>3. json、xml 打印；
>4. 线程安全；

## 使用
        WtfLog.i.tag("WtfLog")
                .msg("Easy log with WtfLog !")
                .date()
                .threadInfo()
                .stackTrace()
                .json(json)
                .xml(xml)
                .key("int").intVal(255)
                .key("hex int").hexVal(255)
                .key("hex int").binVal(255)
                .key("map").mapVal(map)
                .key("list").listVal(list)
                .print();

>打印效果如下：

>![Markdown](http://p1.bqimg.com/586440/71bcce54671e830e.png)

    

## 集成
>Step 1. 在你的project的gradle脚本，仓库配置项中增下下发仓库配置；

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


>Step 2. module的gradle脚本中，增加下方依赖

	dependencies {
	        compile 'com.github.nelon1990:WtfLogger:v0.1.0'
	}