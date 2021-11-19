## 原文 [Docker的学习与使用-CSDN博客](https://blog.csdn.net/qq_38490457/article/details/119428303)

## Docker安装

1. 安装yum-utils包。

> yum install -y yum-utils device-mapper-persistent-data lvm2

2. 在新主机上首次安装 Docker Engine-Community 之前，需要设置 Docker 仓库。之后，您可以从仓库安装和更新 Docker。

> yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

3. 安装最新版本的 Docker Engine 和 containerd，或转至下一步安装特定版本。

> yum install docker-ce docker-ce-cli containerd.io

`注意：如果启用了多个Docker存储库，如果不在yum install或yum update命令中指定版本的情况下安装或更新时，始终会安装可能的最高版本，这可能不适合您的稳定性需要`

4. 要安装特定版本的 Docker Engine，请在repo中列出可用版本，然后选择并安装。

> yum list docker-ce --showduplicates | sort -r
>
> docker-ce.x86_64            3:20.10.8-3.el7                    docker-ce-stable 
> docker-ce.x86_64            3:20.10.8-3.el7                    @docker-ce-stable
> docker-ce.x86_64            3:20.10.7-3.el7                    docker-ce-stable 
> ... ...

`注意：返回的列表取决于启用了哪些存储库，并且特定于您的CentOS版本（在本例中由.el7后缀指示）。`

通过其完全限定的软件包名称安装特定版本，该名称是软件包名称（docker ce）加上版本字符串（第2列），从第一个冒号（：）开始，直到第一个连字符，由连字符（-）分隔。例如，docker-ce-20.10.8。

> yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io

```
安装epel源
docker 安装报错 container-selinux >= 2.9 解决
https://blog.csdn.net/qq_41772936/article/details/81080284
```

`注意：Docker已安装但未启动。将创建docker组，但不会向该组添加任何用户。`

5. 启动Docker。

> sudo systemctl start docker

6. 查看Docker版本。

> docker -v

7. 通过运行hello world映像验证Docker引擎是否已正确安装。

> docker run hello-world

`注意：此命令下载测试映像并在容器中运行。当容器运行时，它打印一条信息后退出。`

8. 要升级Docker引擎，请使用`yum -y upgrade`而不是`yum -y install`，并重复安装步骤。

9. 卸载Docker引擎、CLI和Containerd包：

> yum remove docker-ce docker-ce-cli containerd.io

以上命令并不会自动删除主机上的映像、容器、卷或自定义配置文件。删除所有映像、容器和卷的步骤。

> rm -rf /var/lib/docker
> rm -rf /var/lib/containerd

`注意：必须手动删除任何已编辑的配置文件。`

10. 默认情况下，Docker默认从docker hub（https://hub.docker.com/）上下载docker镜像，太慢。一般都会配置镜像加速器：

打开阿里云镜像加速器网页：https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

![image-20211119105234593](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20211119105234593.png)

## Docker进程相关命令

- 启动docker服务：systemctl start docker
- 停止docker服务：systemctl stop docker
- 重启docker服务：systemctl restart docker
- 查看docker服务状态：systemctl status docker
- 设置docker开机启动：systemctl enable docker
- 取消docker开机启动：systemctl disable docker

## Docker镜像相关命令

查看镜像：查看本地所有的镜像

> docker images
> docker images –q # 查看所用镜像的id

搜索镜像：从网上查找需要镜像

> docker search 镜像名称

拉取镜像：默认从Docker仓库下载镜像到本地，镜像名称格式为`名称:版本号`，如果版本号不指定则是最新的版本。

> docker pull 镜像名称
> docker pull 镜像名称:镜像版本

`注意：如果不知道镜像版本，可以去 docker hub 搜索对应镜像查看。`

删除镜像：删除本地镜像

```
docker rmi 镜像id # 删除指定本地镜像（加-f强制删除）
docker rmi `docker images -q`  # 删除所有本地镜像
```

查看镜像存放信息：

> docker info # 镜像下载后存放在 /var/lib/docker

获取镜像详细信息：

> docker inspect 镜像id

导出镜像：

> docker save -o /目录/文件名 镜像名称:镜像版本

导入镜像：

> docker load -i /目录/文件名

## Docker容器相关命令

容器列表：

> docker ps # 查看正在运行的容器
> docker ps –a # 查看本地所有的容器

创建容器：

> docker create --name="容器名称" 镜像名称:镜像版本 初始化语句
> 例如：
> docker create --name="centos7" centos:7 /bin/bash

启动容器：

> docker start 容器名称/容器ID

停止容器：

> docker stop 容器名称/容器ID

重启容器：

> docker restart 容器名称/容器ID

删除容器：

> docker rm 容器名称/容器ID

查看容器信息：

> docker inspect 容器名称/容器ID

查看容器日志：

> docker logs 容器名称/容器ID

设开机自启动：

> docker update --restart=always 容器名称/容器ID

启动容器：

> docker run 参数 --name="容器名称" 镜像名称:镜像版本 初始化语句
>
> 参数说明：
> -i：保持容器运行。通常与 -t 同时使用。加入it这两个参数后，容器创建后自动进入容器中，退出容器后，容器自动关闭。
> -t：为容器重新分配一个伪输入终端，通常与 -i 同时使用。
> -d：以守护（后台）模式运行容器。创建一个容器在后台运行，需要使用docker exec 进入容器。退出后，容器不会关闭。
> -it：创建的容器一般称为交互式容器，-id 创建的容器一般称为守护式容器。
>
> -P（大写）：暴露随机端口
> -p（小写）：暴露指定端口，具体有以下四种：
> docker run -p container_port                    #将容器的某个端口映射到宿主机的所有接口的一个随机端口上。
> docker run -p host_port:container_port          #将容器的某个端口映射到宿主机的所有接口的一个具体端口上。
> docker run -p host_ip:host_port:container_port  #将容器的某个端口映射到宿主机的一个具体IP地址的具体端口上。
> docker run -p host_ip::container_port           #将容器的某个端口映射到宿主机的一个具体IP地址的一个随机端口上。
>
> 使用命令 docker port 容器名字/容器ID 查看端口映射信息。

进入容器：

> docker exec 参数 镜像名称:镜像版本 初始化语句
>
> 参数说明：
> -i：保持容器运行。通常与 -t 同时使用。加入it这两个参数后，容器创建后自动进入容器中，退出容器后，容器自动关闭。
> -t：为容器重新分配一个伪输入终端，通常与 -i 同时使用。
> -d：以守护（后台）模式运行容器。创建一个容器在后台运行，需要使用docker exec 进入容器。退出后，容器不会关闭。
> -it：创建的容器一般称为交互式容器，-id 创建的容器一般称为守护式容器。

退出容器：

> exit

导出容器：

> docker export -o /目录/文件名 容器名称/容器ID
> 例如：将容器按日期保存为tar文件。
> docker export -o centos-`date +%Y%m%d`.tar c1

导入容器：

> docker import /目录/文件名 镜像名称:镜像版本
> 例如：将上述的tar归档文件导入。
> docker import centos-20210805.tar centos-ccl:7

从容器导出一个新镜像：

> docker commit 容器ID 镜像名称:镜像版本

从容器复制文件到主机：

> 例如：docker cp 2592d3fad0fb:/opt/test.txt ~/abc123.txt

从主机复制文件到容器：

> 例如：docker cp ~/test.txt 2592d3fad0fb:/opt/

## Docker容器的数据卷

配置数据卷：数据卷是宿主机和容器之间的共享。

> docker run 参数 -v 宿主机目录(文件):容器内目录(文件) --name="容器名称" 镜像名称:镜像版本 初始化语句
> 例如：docker run -it -v /root/aaa:/root/bbb --name=c0 centos:7 /bin/bash

配置数据卷容器：数据卷容器是容器和容器之间的共享。

1. 创建启动c3数据卷容器，使用 –v 参数 设置数据卷

> docker run –it –v /volume --name=c3 centos:7 /bin/bash

2. 创建启动 c1 c2 容器，使用 –-volumes-from 参数 设置数据卷

> docker run –it --volumes-from c3 --name=c1 centos:7 /bin/bash
> docker run –it --volumes-from c3 --name=c2 centos:7 /bin/bash

## Docker常见应用部署

### 部署MySQL

1. 搜索mysql镜像

> docker search mysql

2. 拉取mysql镜像

> docker pull mysql:5.7

3. 创建容器，设置端口映射、目录映射

> mkdir ~/mysql
> cd ~/mysql

> docker run -id \
> -p 3306:3306 \
> --name=c_mysql \
> -v $PWD/conf:/etc/mysql/conf.d \
> -v $PWD/logs:/logs \
> -v $PWD/data:/var/lib/mysql \
> -e MYSQL_ROOT_PASSWORD=123456 \
> mysql:5.7

参数说明：

- -p 3306:3306 ：将容器的 3306 端口映射到宿主机的 3306 端口。
- -v $PWD/conf:/etc/mysql/conf.d ：将主机当前目录下的 conf/my.cnf 挂载到容器的 /etc/mysql/my.cnf。配置目录
- -v $PWD/logs:/logs ：将主机当前目录下的 logs 目录挂载到容器的 /logs。日志目录
- -v $PWD/data:/var/lib/mysql ：将主机当前目录下的data目录挂载到容器的 /var/lib/mysql 。数据目录
- -e MYSQL_ROOT_PASSWORD=123456 ：初始化 root 用户的密码。// 不建议使用

4. 进入容器，操作mysql

> docker exec -it c_mysql /bin/bash

> mysql -uroot -p123456

5. 将宿主机端口开放防火墙

> firewall-cmd --zone=public --add-port=3306/tcp --permanent
> firewall-cmd --reload

6. 在虚拟机外使用SQLyog连接mysql

7. 设置容器自启动

> docker update --restart=always c_mysql

### 部署Redis

1. 搜索mysql镜像

> docker search mysql

2. 拉取mysql镜像

> docker pull mysql:5.7

3. 创建容器，设置端口映射

> docker run -id -p 6379:6379 --name=c_redis redis:6.0

4. 将宿主机端口开放防火墙

> firewall-cmd --zone=public --add-port=6379/tcp --permanent
> firewall-cmd --reload

5. 在虚拟机外使用RedisDesktopManager连接redis

6. 设置容器自启动

> docker update --restart=always c_redis

### 部署Tomcat

1. 搜索tomcat镜像

> docker search tomcat

2. 拉取tomcat镜像

> docker pull tomcat

3. 创建容器，设置端口映射、目录映射

> 在/root目录下创建tomcat目录用于存储tomcat数据信息
>
> mkdir ~/tomcat
> cd ~/tomcat

> docker run -id --name=c_tomcat \
> -p 8080:8080 \
> -v $PWD:/usr/local/tomcat/webapps \
> tomcat 

参数说明：

- -p 8080:8080 ：将容器的8080端口映射到主机的8080端口
- -v $PWD:/usr/local/tomcat/webapps ：将主机中当前目录挂载到容器的webapps

4. 将宿主机端口开放防火墙

> firewall-cmd --zone=public --add-port=8080/tcp --permanent
> firewall-cmd --reload

5. 编写一个网页

> mkdir -p ~/tomcat/app/
> echo "<h1> hello tomcat docker </h1>" > ~/tomcat/app/index.html

6. 在虚拟机外使用浏览器访问页面

> ip:8080/app/index.html

7. 设置容器自启动

> docker update --restart=always c_tomcat

### 部署Nginx

1. 搜索nginx镜像

> docker search nginx

2. 拉取nginx镜像

> docker pull nginx

3. 创建容器，设置端口映射、目录映射

> 在/root目录下创建nginx目录用于存储nginx数据信息
>
> mkdir ~/nginx
> cd ~/nginx
> mkdir conf
> cd conf
>
> 在~/nginx/conf/下创建nginx.conf文件,粘贴下面内容
>
> vi nginx.conf

```
user  nginx;
worker_processes  1;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
}
```

```
cd ~/nginx

docker run -id --name=c_nginx \
-p 80:80 \
-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf \
-v $PWD/logs:/var/log/nginx \
-v $PWD/html:/usr/share/nginx/html \
nginx
```

参数说明：

- -p 80:80 ：将容器的 80端口映射到宿主机的 80 端口。
- -v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf ：将主机当前目录下的 /conf/nginx.conf 挂载到容器的:/etc/nginx/nginx.conf。配置目录
- -v $PWD/logs:/var/log/nginx ：将主机当前目录下的 logs 目录挂载到容器的/var/log/nginx。日志目录

4. 将宿主机端口开放防火墙

> firewall-cmd --zone=public --add-port=80/tcp --permanent
> firewall-cmd --reload

5. 编写一个网页

> echo "<h1> hello nginx docker </h1>" > ~/nginx/html/index.html

6. 在虚拟机外使用浏览器访问页面

>  ip/index.html

7. 设置容器自启动

> docker update --restart=always c_nginx

### 部署RabbitMQ

1. 搜索rabbitmq镜像

> docker search rabbitmq

2. 拉取rabbitmq镜像

> docker pull rabbitmq

3. 创建容器，设置端口映射

> docker run -id --name c_rabbitmq \
> -p 15672:15672 \
> rabbitmq:management

4. 将宿主机端口开放防火墙

> firewall-cmd --zone=public --add-port=5672/tcp --permanent
> firewall-cmd --zone=public --add-port=15672/tcp --permanent
> firewall-cmd --reload

5. 在虚拟机外使用浏览器访问页面

>  ip:15672/#/

6. 设置容器自启动

> docker update --restart=always c_rabbitmq

