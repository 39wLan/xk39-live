##### 介绍

琐碎记录

##### 需求分析软件

Power Designer

> 1. 设计数据库表结构
> 2. 对表中字段进行配置
> 3. 导出sql

##### 技术架构

前端技术栈

Vue2.x

后端技术栈

SpringBoot+SpringCloud+SpringMVC+SpringData

##### 系统结构

![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/01-系统结构.png)

##### fastjson

```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>
```

##### Spring Snapshots

##### Spring Milestones

##### 注解

> @JsonInclude(JsonInclude.Include.NON_NULL)
>
> @CrossOrigin
>
> ```
> import com.fasterxml.jackson.annotation.JsonIgnore;
> @ManyToMany
> @JsonIgnore
> @JoinTable(name="pe_user_role",joinColumns= {@JoinColumn(name="user_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")})
> ```
>
> @DynamicInsert(true)
> @DynamicUpdate(true)
>
> @Configuration

##### enum类使用

##### ID生成器

> 1. 雪花算法
>
> ![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/01-雪花算法id生成.png)
>
> ![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/01-雪花算法id生成02.png)
>
> ```java
> import java.lang.management.ManagementFactory;
> import java.net.InetAddress;
> import java.net.NetworkInterface;
> 
> //雪花算法代码实现
> public class IdWorker {
>     // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
>     private final static long twepoch = 1288834974657L;
>     // 机器标识位数
>     private final static long workerIdBits = 5L;
>     // 数据中心标识位数
>     private final static long datacenterIdBits = 5L;
>     // 机器ID最大值
>     private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
>     // 数据中心ID最大值
>     private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
>     // 毫秒内自增位
>     private final static long sequenceBits = 12L;
>     // 机器ID偏左移12位
>     private final static long workerIdShift = sequenceBits;
>     // 数据中心ID左移17位
>     private final static long datacenterIdShift = sequenceBits + workerIdBits;
>     // 时间毫秒左移22位
>     private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
> 
>     private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
>     /* 上次生产id时间戳 */
>     private static long lastTimestamp = -1L;
>     // 0，并发控制
>     private long sequence = 0L;
> 
>     private final long workerId;
>     // 数据标识id部分
>     private final long datacenterId;
> 
>     public IdWorker(){
>         this.datacenterId = getDatacenterId(maxDatacenterId);
>         this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
>     }
>     /**
>      * @param workerId
>      *            工作机器ID
>      * @param datacenterId
>      *            序列号
>      */
>     public IdWorker(long workerId, long datacenterId) {
>         if (workerId > maxWorkerId || workerId < 0) {
>             throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
>         }
>         if (datacenterId > maxDatacenterId || datacenterId < 0) {
>             throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
>         }
>         this.workerId = workerId;
>         this.datacenterId = datacenterId;
>     }
>     /**
>      * 获取下一个ID
>      *
>      * @return
>      */
>     public synchronized long nextId() {
>         long timestamp = timeGen();
>         if (timestamp < lastTimestamp) {
>             throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
>         }
> 
>         if (lastTimestamp == timestamp) {
>             // 当前毫秒内，则+1
>             sequence = (sequence + 1) & sequenceMask;
>             if (sequence == 0) {
>                 // 当前毫秒内计数满了，则等待下一秒
>                 timestamp = tilNextMillis(lastTimestamp);
>             }
>         } else {
>             sequence = 0L;
>         }
>         lastTimestamp = timestamp;
>         // ID偏移组合生成最终的ID，并返回ID
>         long nextId = ((timestamp - twepoch) << timestampLeftShift)
>                 | (datacenterId << datacenterIdShift)
>                 | (workerId << workerIdShift) | sequence;
> 
>         return nextId;
>     }
> 
>     private long tilNextMillis(final long lastTimestamp) {
>         long timestamp = this.timeGen();
>         while (timestamp <= lastTimestamp) {
>             timestamp = this.timeGen();
>         }
>         return timestamp;
>     }
> 
>     private long timeGen() {
>         return System.currentTimeMillis();
>     }
> 
>     /**
>      * <p>
>      * 获取 maxWorkerId
>      * </p>
>      */
>     protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
>         StringBuffer mpid = new StringBuffer();
>         mpid.append(datacenterId);
>         String name = ManagementFactory.getRuntimeMXBean().getName();
>         if (!name.isEmpty()) {
>             /*
>              * GET jvmPid
>              */
>             mpid.append(name.split("@")[0]);
>         }
>         /*
>          * MAC + PID 的 hashcode 获取16个低位
>          */
>         return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
>     }
> 
>     /**
>      * <p>
>      * 数据标识id部分
>      * </p>
>      */
>     protected static long getDatacenterId(long maxDatacenterId) {
>         long id = 0L;
>         try {
>             InetAddress ip = InetAddress.getLocalHost();
>             NetworkInterface network = NetworkInterface.getByInetAddress(ip);
>             if (network == null) {
>                 id = 1L;
>             } else {
>                 byte[] mac = network.getHardwareAddress();
>                 id = ((0x000000FF & (long) mac[mac.length - 1])
>                         | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
>                 id = id % (maxDatacenterId + 1);
>             }
>         } catch (Exception e) {
>             System.out.println(" getDatacenterId: " + e.getMessage());
>         }
>         return id;
>     }
> }
> 
> ```
>
> 2. 百度的id生成算法

##### Specification过滤函数

```java
    protected Specification<T> getSpecification(String companyId) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, 
CriteriaBuilder cb) {
                return cb.equal(root.get("companyId").as(String.class),companyId);
           }
       };
   }
```

##### JWT

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.6.0</version>
</dependency>
```

> 1. token生成
>
> ```java
> public class CreateJwtTest {
>     public static void main(String[] args) {
>         JwtBuilder builder= Jwts.builder().setId("888")
>                 .setSubject("小白")
>                 .setIssuedAt(new Date())
>                 .signWith(SignatureAlgorithm.HS256,"keyname");
>         System.out.println( builder.compact() );
>   //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MjM0MTM0   //NTh9.gq0JcOM_qCNqU_s-d_IrRytaNenesPmqAIhQpYXHZk
>     }
> }
> ```
>
> 2. token解析
>
> ```java
> public class ParseJwtTest {
>     public static void main(String[] args) {
>         String token=
> "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MjM0MTM0NTh9.gq0J-cOM_qCNqU_s-d_IrRytaNenesPmqAIhQpYXHZk";
>         Claims claims =
>           Jwts.parser().setSigningKey("keyname").parseClaimsJws(token).getBody();
>         System.out.println("id:"+claims.getId());
>         System.out.println("subject:"+claims.getSubject());
>         System.out.println("IssuedAt:"+claims.getIssuedAt());
>     }
> }
> ```
>
> 3. 自定义claims
>
> ```java
> public class CreateJwtTest3 {
>     public static void main(String[] args) {
>         //将过期时间设置为1分钟
>         long now = System.currentTimeMillis();//当前时间
>         long exp = now + 1000*60;//过期时间为1分钟
>         JwtBuilder builder= Jwts.builder().setId("888")
>                 .setSubject("小白")
>                 .setIssuedAt(new Date())
>                 .signWith(SignatureAlgorithm.HS256,"keyname")
>                 .setExpiration(new Date(exp))
>                 .claim("roles","admin") //自定义claims存储数据
>                 .claim("logo","logo.png");
>         System.out.println( builder.compact() );
>     }
> }
> ```

##### JWT工具类

>  application.yml
>
> ```yml
> jwt:
>  config:
>     key: saas-ihrm
>     ttl: 360000
> ```
>
>  JwtUtil.java
>
> ```java
> import io.jsonwebtoken.Claims;
> import io.jsonwebtoken.JwtBuilder;
> import io.jsonwebtoken.Jwts;
> import io.jsonwebtoken.SignatureAlgorithm;
> import lombok.Getter;
> import lombok.Setter;
> import org.springframework.boot.context.properties.ConfigurationProperties;
> import org.springframework.boot.context.properties.EnableConfigurationProperties;
> 
> import java.util.Date;
> import java.util.Map;
> 
> @Getter
> @Setter
> @EnableConfigurationProperties(JwtUtils.class)
> @ConfigurationProperties("jwt.config")
> public class JwtUtils {
>     /**
>      * 签名私钥
>      */
>     private String key;
>     
>     /**
>      * 签名的失效时间
>      */
>     private Long ttl;
>     
>     /**
>      * 设置认证token
>      *      id:登录用户id
>      *      subject:登录用户名
>      */
>     public String createJwt(String id, String name, Map<String,Object> map){
>         /**
>          * 设置失效时间
>          */
>         long nowTime = System.currentTimeMillis();
>         long exp = nowTime + ttl;
>         /**
>          * 创建jwtBuilder
>          */
>         JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
>                 .setIssuedAt(new Date())
>                 .signWith(SignatureAlgorithm.HS256, key);
>         /**
>          * 根据map设置claims
>          */
>         for(Map.Entry<String,Object> entry:map.entrySet()){
>             jwtBuilder.claim(entry.getKey(), entry.getValue());
>         }
>         jwtBuilder.setExpiration(new Date(exp));
>         /**
>          * 创建token
>          */
>         String token = jwtBuilder.compact();
>         return token;
>     }
>     
>     /**
>      * 解析token字符串获取clamis
>      */
>     public Claims parseJwt(String token){
>         Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
>         return claims;
>     }
> }
> ```
>
> 

##### Spring中的拦截器

> Spring为我们提供了org.springframework.web.servlet.handler.HandlerInterceptorAdapter这个适配器，继承此类，可以非常方便的实现自己的拦截器。他有三个方法：分别实现预处理、后处理（调用了Service并返回ModelAndView，但未进行页面渲染）、返回处理（已经渲染了页面）
>
> > 1.在preHandle中，可以进行编码、安全控制等处理； 
> >
> > 2.在postHandle中，有机会修改ModelAndView； 
> >
> > 3.在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。
>
> 拦截器中鉴权
>
> > 添加拦截器 JwtInterceptor
> >
> > ```java
> > /**
> >  * 自定义拦截器
> >  *      继承HandlerInterceptorAdapter
> >  *
> >  *      preHandle:进入到控制器方法之前执行的内容
> >  *          boolean:
> >  *              true: 可以继续执行控制器方法
> >  *              false: 拦截
> >  *      posthandler: 执行控制器方法之后执行的内容
> >  *      afterCompletion: 响应结束之前执行的内容
> >  */
> > /**
> >  * 1.简化获取token数据的代码编写
> >  *      统一的用户校验权限
> >  * 2.判断用户是否具有当前访问接口的权限
> >  */
> > @Component
> > public class JwtInterceptor extends HandlerInterceptorAdapter {
> >     
> >     /**
> >      * 简化获取token数据的代码编写（判断是否登录）
> >      *   1.通过request获取请求token信息
> >      *   2.从token中解析获取claims
> >      *   3.将claims绑定到request域中
> >      */
> >     
> >     @Autowired
> >     private JwtUtils jwtUtils;
> >     
> >     @Override
> >     public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
> >         /**
> >          * 通过request获取请求token信息
> >          */
> >         
> >         String authorization = request.getHeader("Authorization");
> >         System.out.println("已获取到:"+authorization);
> >         /**
> >          * 判断请求头信息是否为空，或者是否以Bearer开头
> >          */
> >         if(!StringUtils.isEmpty(authorization)&&authorization.startsWith("Bearer")){
> >             /**
> >              * 获取token数据
> >              */
> >             String token = authorization.replace("Bearer ", "");
> >             /**
> >              * 解析token获取claims
> >              */
> >             System.out.println("开始解析");
> >             Claims claims = jwtUtils.parseJwt(token);
> >             System.out.println("解析完成");
> >             if(claims!=null){
> >                 /**
> >                  * 通过claims获取到当前用户的可访问API权限字符串
> >                  * api-user-delete,api-user-update
> >                  */
> >                 String apis = (String) claims.get("apis");
> >                 /**
> >                  * 通过handler
> >                  */
> >                 HandlerMethod handlerMethod = (HandlerMethod) handler;
> >                 /**
> >                  * 获取接口上的requestmapping注解
> >                  */
> >                 RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
> >                 /**
> >                  * 获取当前请求接口中的name属性
> >                  */
> >                 String name = annotation.name();
> >                 /**
> >                  * 判断当前用户是否具有响应的请求权限
> >                  */
> >                 if(apis.contains(name)){
> >                     request.setAttribute("user_claims",claims);
> >                     return true;
> >                 }else {
> >                     throw new CommonException(ResultCode.UNAUTHORISE);
> >                 }
> >             }
> >         }
> >         throw new CommonException(ResultCode.UNAUTHENTICATED);
> >     }
> > }
> > ```
> >
> > 配置拦截器类
> >
> > ```java
> > @Configuration
> > public class SystemConfig extends WebMvcConfigurationSupport {
> >     @Autowired
> >     private JwtInterceptor jwtInterceptor;
> >     
> >     /**
> >      * 添加拦截器配置
> >      */
> >     @Override
> >     protected void addInterceptors(InterceptorRegistry registry){
> >         /**
> >          * 添加自定义拦截器
> >          */
> >         registry.addInterceptor(jwtInterceptor).
> >                 /**
> >                  * 指定拦截器的url地址
> >                  */
> >                 addPathPatterns("/**").
> >                 /**
> >                  * 指定不拦截的url地址
> >                  */
> >                 excludePathPatterns("/sys/login","/frame/register/**");
> >     }
> > }
> > ```

##### Shiro认证

##### 自定义realm

> Realm域：Shiro从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源
>
> 

##### shiro过滤器

> ![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/06-shiro过滤器.png)

##### shiro授权

##### shiro会话管理

##### shiro会话管理结合redis

##### 服务发现组件Eureka

##### 微服务调用组件Feign

##### POI

##### POI报表导入

##### POI报表导出

##### 模板打印

##### Jvisualvm性能监控

##### 大数据量报表导出与读取

##### 七牛云存储

##### 文件上传

##### PDF报表打印

#####  JasperReport

##### 模板工具Jaspersoft Studio

##### JasperReport报表模板数据填充

##### 数据源填充数据

##### JDBC数据源

##### JavaBean数据源

##### 分组报表

##### Chart图表

##### 父子报表

##### 报表下载

##### //百度云AI

##### FreeMarker

##### 代码生成器实现

#####  Zuul网关

##### 基于Zuul的统一鉴权

##### Activiti工作流

