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

D:\Program Files\Java\jdk1.8.0_112\bin>keytool -list -v -keystore android.keystore -storepass BjfuMCS

密钥库类型: JKS
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: android.keystore
创建日期: 2018-2-1
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: CN=MCS, OU=BJFU, O=BJFU, L=BeiJing, ST=BeiJing, C=86
发布者: CN=MCS, OU=BJFU, O=BJFU, L=BeiJing, ST=BeiJing, C=86
序列号: 4a2781fc
有效期开始日期: Thu Feb 01 18:37:41 CST 2018, 截止日期: Fri Nov 04 18:37:41 CST 2072
证书指纹:
         MD5: 07:21:82:9A:F0:85:93:76:13:F5:BF:D4:59:08:8B:4D
         SHA1: 85:24:69:27:27:13:1E:7D:D6:11:6D:EC:F2:71:70:D1:C6:ED:A1:37
         SHA256: 9F:13:4B:1E:8F:D0:ED:58:49:28:E1:71:6B:00:3B:BA:0C:0D:1D:F2:51:0B:FF:85:35:42:18:D3:29:1F:28:2B
         签名算法名称: SHA256withRSA
         版本: 3

扩展:

1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: ED 38 2B 1F BE 11 0A 97   57 08 5C 1D 56 B9 81 1B  .8+.....W.\.V...
0010: BD 1E FE 93                                        ....
]
]