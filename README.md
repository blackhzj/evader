evader
======

Java2ME Book Reader Project for Nokia Cellphone

曾经在大学的时候写的一个手机上读书的小程序，用今天的说法来说，是可以自动生成书籍App的生成器。

这个软件做成于2009年4月27日，那个年代Nokia还是火的一塌糊涂，所以，这在当时是很流行的一件事情。

我自己用这个软件生成了很多很多的书，在我当时的Nokia 6500s上伴随我读过了一两年的时间，但是我并没有推广这个软件，现在当然也没有推广的必要了。

现在放上来只是作为一个纪念而已。

# 结构

* mobile

在手机上运行的MIDlet程序，编译出来res2.zip作为generator使用的部件。

* generator

命令行下的生成器。

# 生成

generator编译出jar包，包里面要包含res文件夹，文件结构如下：

```
res/icon.zip
res/res2.zip	#这个是mobile编译出来的文件
```

假如生成的jar包是 genrator.jar，那么：

```
java -jar generator.jar
```

可以查看说明。

# 开发环境的搭建和开发

## 软件要求

1. Java Wireless Toolkit 2.x ，下载地址: [J2ME Wireless Toolkit](http://www.oracle.com/technetwork/java/sjwtoolkit-138075.html)。

> 对于Mac用户来说，去下载：[Java ME SDK 3.0 for Mac OS](http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-javame-419430.html#sun_java_me_sdk-3.0-rr-mac-JPR)
> 但是经过我的测试，在Mac OS X 10.10 Yosemite上是不行的，这里就不研究啦，在Windows XP下去操作吧。

2. [NetBeans 6.5](https://netbeans.org/downloads/6.5/index.html)

如果你使用Eclipse或者IntelliJ IDEA的话，那么请参考相关的文档，IntelliJ在[这里](https://www.jetbrains.com/idea/help/j2me.html)。

## 安装J2ME Wireless Toolkit

### Linux and Mac

下载下来的文件类似于 sun_java_wireless_toolkit-2.5.2_01-linuxi486.bin.sh 这样的，然后加上可执行权限之后，会一步步安装。

对于Mac来说，需要拷贝一份安装目录下的 `wtklib/Linux` 到 `wtklib/Mac`，如下：

```
cp -r wtklib/Linux wtklib/Mac
```

Mac OS X 10.10 是不行的，编译什么都可以通过，但是无法Debug和发布，具体什么原因我也不太清楚，也无心去研究啦。
