Android Studio 3.0 查看数据库
![](https://i.imgur.com/8pF00in.png)

windows 到Android SDK 的platform-tools 目录下
比如我的 Android SDK位于G盘下
cmd 命令行
cd g:
g:
cd AndroidStudio_SDK
cd sdk
cd platform-tools	
android 程序内部文件存储在 data/data/包名
adb shell su -c "chmod 777 /data"
adb shell su -c "chmod 777 /data/data"
adb shell su -c "chmod 777 /data/data/cn.banmahui.sql"
adb shell su -c "chmod 777 /data/data/cn.banmahui.sql/databases"
adb shell su -c "chmod 777 /data/data/cn.banmahui.sql/databases/SqlVoice.db"
