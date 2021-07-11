# xk39-live

### 介绍

个人日常记录、问题解决方案总括

### 日常

~~指令重排规则~~

> happens-before

手写AOP

Java8对CAS自旋开销的优化

~~Git使用SSH代理~~

> ~/.ssh/config文件里写入以下内容
>
> ```
> # 这里的 -a none 是 NO-AUTH 模式，参见 https://bitbucket.org/gotoh/connect/wiki/Home 中的 More detail 一节
> ProxyCommand connect -S 127.0.0.1:1080 -a none %h %p
> ```
>
> ~/.ssh/config文件配置方式自行百度
>
> 详情链接: [Windows 下 Git SSH 连接方式配置 Socks 代理_XSemperFI的博客-CSDN博客](https://blog.csdn.net/XSemperFI/article/details/106818669)
>
> [Git HTTP+SSH 代理配置 – 晨鹤小站 (chenhe.cc)](http://www.chenhe.cc/p/406)

~~Git多个远程仓库同步~~

> ```
> # 添加关联的远程仓库
> # 使用此命令时请将各仓库的私钥配置好
> git remote set-url --add origin git@gitee.com:temp/temp.git
> ```
>
> ```
> # 查看现有远程库
> git remote --verbose
> ```
>
> 详情链接：[如何同步多个 git 远程仓库 - taadis - 博客园 (cnblogs.com)](https://www.cnblogs.com/taadis/p/12170953.html)

SaaS-HRM项目技术整理