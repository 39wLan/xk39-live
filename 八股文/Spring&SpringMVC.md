# Spring&SpringMVC

## BeanFactory 和 ApplicationContext 有什么区别

> BeanFactory 可以理解为含有 bean 集合的工厂类。BeanFactory 包含了种 bean 的定义，以便在接收到客户端请求时将对应的 bean 实例化。
> BeanFactory 还能在实例化对象的时生成协作类之间的关系。此举将 bean 自身与 bean 客
> 户端的配置中解放出来。BeanFactory 还包含了 bean 生命周期的控制，调用客户端的初始
> 化方法（initialization methods）和销毁方法（destruction methods）。
> 从表面上看，application context 如同 bean factory 一样具有 bean 定义、bean 关联关
> 系的设置，根据请求分发 bean 的功能。但 application context 在此基础上还提供了其他
> 的功能。
> 提供了支持国际化的文本消息
> 统一的资源文件读取方式
> 已在监听器中注册的 bean 的事件

## BeanFactory和FactoryBean的区别。

## spring中使⽤到了FactoryBean的哪个⽅法？

## Spring事件的实现原理，写出常⽤的⼏个事件。

> 1. 事件实现原理：理解这篇即可：https://www.jianshu.com/p/dcbe8f0afbdb
> 2. 常⽤事件：
>    (1)ContextRefreshedEvent:当ApplicationContext初始化或者刷新时触发该事件
>    (2)ContextClosedEvent:ApplicationContext被关闭时触发该事件.容器被关闭时,其管理的所有单例Bean都被销毁
>    (3)RequestHandleEvent:在Web应⽤中,当⼀个Http请求结束时触发该事件
>    (4)ContextStartedEvent:当容器调⽤start()⽅法时触发
>    (5)ContextStopEvent:当容器调⽤stop()⽅法时触发

## Spring加载bean的顺序？

> spring容器及bean加载机制源码解读：https://blog.csdn.net/songyang19871115/article/details/54342242

## Spring Bean 的生命周期

> ![image-20210306204849460](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306204849460.png)
>
> Spring Bean 的生命周期简单易懂。在一个 bean 实例被初始化时，需要执行一系列的初
> 始化操作以达到可用的状态。同样的，当一个 bean 不在被调用时需要进行相关的析构操
> 作，并从 bean 容器中移除。
> Spring bean factory 负责管理在 spring 容器中被创建的 bean 的生命周期。Bean 的生命
> 周期由两组回调（call back）方法组成。
> 初始化之后调用的回调方法。
> 销毁之前调用的回调方法。
> Spring 框架提供了以下四种方式来管理 bean 的生命周期事件：
> InitializingBean 和 DisposableBean 回调接口
> 针对特殊行为的其他 Aware 接口
> Bean 配置文件中的 Custom init()方法和 destroy()方法
> @PostConstruct 和@PreDestroy 注解方式

## Spring bean作⽤域，什么时候使⽤request作⽤域。

> ![image-20210306195733982](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306195733982.png)
>
> 详读：https://blog.csdn.net/icarus_wang/article/details/51586776

## 后置处理器的作⽤？

> Spring中bean后置处理器BeanPostProcessor：https://www.jianshu.com/p/f80b77d65d39

## Spring IOC 如何实现

 > Spring 中的 org.springframework.beans 包和 org.springframework.context 包构成了Spring 框架 IoC 容器的基础。

> BeanFactory 接口提供了一个先进的配置机制，使得任何类型的对象的配置成为可能。 ApplicationContex 接口对 BeanFactory（是一个子接口）进行了扩展，在 BeanFactory 的基础上添加了其他功能，比如与 Spring 的 AOP 更容易集成，也提供了处理 message resource 的机制（用于国际化）、事件传播以及应用层的特别配置，比如针对 Web 应用的WebApplicationContext。

> org.springframework.beans.factory.BeanFactory 是 Spring IoC 容器的具体实现，用
> 来包装和管理前面提到的各种 bean。BeanFactory 接口是 Spring IoC 容器的核心接口。

## 说说 Spring AOP

> 面向切面编程，在我们的应用中，经常需要做一些事情，但是这些事情与核心业务无
> 关，比如，要记录所有 update*方法的执行时间时间，操作人等等信息，记录到日志，
> 通过 spring 的 AOP 技术，就可以在不修改 update*的代码的情况下完成该需求。

## 如何实现aop？项⽬中哪些地⽅⽤到了aop？

> 掌握：https://juejin.im/post/5bf4fc84f265da611b57f906

## Spring AOP 实现原理

> Spring AOP 中的动态代理主要有两种方式，JDK 动态代理和 CGLIB 动态代理。JDK 动态代
> 理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK 动态代理的
> 核心是 InvocationHandler 接口和 Proxy 类。
> 如果目标类没有实现接口，那么 Spring AOP 会选择使用 CGLIB 来动态代理目标类。CGLIB
> （Code Generation Library），是一个代码生成的类库，可以在运行时动态的生成某个类
> 的子类，注意，CGLIB 是通过继承的方式做的动态代理，因此如果某个类被标记为 final，
> 那么它是无法使用 CGLIB 做动态代理的。

## 动态代理（cglib 与 JDK） 

> JDK 动态代理类和委托类需要都实现同一个接口。也就是说只有实现了某个接口的类可以
> 使用 Java 动态代理机制。但是，事实上使用中并不是遇到的所有类都会给你实现一个接
> 口。因此，对于没有实现接口的类，就不能使用该机制。而 CGLIB 则可以实现对类的动态
> 代理。

## spring的事务注解是什么？什么情况下事物才会回滚

> a. spring事务实现机制：
>
> ![image-20210306202710129](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306202710129.png)
>
> b. 事务注解@transactional；
> c. 默认情况下，如果在事务中抛出了未检查异常（继承⾃ RuntimeException 的异常）或者 Error，则 Spring 将回滚事务。
> d. @Transactional 只能应⽤到 public ⽅法才有效：只有@Transactional 注解应⽤到 public ⽅法，才能进⾏事务管理。这是因为
> 在使⽤ Spring AOP 代理时，Spring 在调⽤在图 1 中的 TransactionInterceptor 在⽬标⽅法执⾏前后进⾏拦截之前，
> DynamicAdvisedInterceptor（CglibAopProxy 的内部类）的的 intercept ⽅法或 JdkDynamicAopProxy 的 invoke ⽅法会间接调
> ⽤ AbstractFallbackTransactionAttributeSource（Spring 通过这个类获取表 1. @Transactional 注解的事务属性配置属性信息）
> 的 computeTransactionAttribute ⽅法。

## Spring 事务实现方式

> 1、编码方式
> 所谓编程式事务指的是通过编码方式实现事务，即类似于 JDBC 编程实现事务管理。
> 2、声明式事务管理方式
> 声明式事务管理又有两种实现方式：基于 xml 配置文件的方式；另一个实在业务方法上
> 进行@Transaction 注解，将事务规则应用到业务逻辑中

## Spring 事务底层原理

> a、划分处理单元——IOC
> 由于 spring 解决的问题是对单个数据库进行局部事务处理的，具体的实现首先用 spring
> 中的 IOC 划分了事务处理单元。并且将对事务的各种配置放到了 ioc 容器中（设置事务管
> 理器，设置事务的传播特性及隔离机制）。
> b、AOP 拦截需要进行事务处理的类
> Spring 事务处理模块是通过 AOP 功能来实现声明式事务处理的，具体操作（比如事务实
> 行的配置和读取，事务对象的抽象），用 TransactionProxyFactoryBean 接口来使用 AOP
> 功能，生成 proxy 代理对象，通过 TransactionInterceptor 完成对代理方法的拦截，将事
> 务处理的功能编织到拦截的方法中。读取 ioc 容器事务配置属性，转化为 spring 事务处理
> 需要的内部数据结构（TransactionAttributeSourceAdvisor），转化为
> TransactionAttribute 表示的数据对象。
> c、对事物处理实现（事务的生成、提交、回滚、挂起）
> spring 委托给具体的事务处理器实现。实现了一个抽象和适配。适配的具体事务处理
> 器：DataSource 数据源支持、hibernate 数据源事务处理支持、JDO 数据源事务处理支
> 持，JPA、JTA 数据源事务处理支持。这些支持都是通过设计
> PlatformTransactionManager、AbstractPlatforTransaction 一系列事务处理的支持。 为
> 常用数据源支持提供了一系列的 TransactionManager。
> d、结合
> PlatformTransactionManager 实现了 TransactionInterception 接口，让其与
> TransactionProxyFactoryBean 结合起来，形成一个 Spring 声明式事务处理的设计体系。

## 说说spring事物的传播性和隔离级别

> a. 七个事传播属性：
> 1. PROPAGATION_REQUIRED -- ⽀持当前事务，如果当前没有事务，就新建⼀个事务。这是最常⻅的选择。
> 2. PROPAGATION_SUPPORTS -- ⽀持当前事务，如果当前没有事务，就以⾮事务⽅式执⾏。
> 3. PROPAGATION_MANDATORY -- ⽀持当前事务，如果当前没有事务，就抛出异常。
> 4. PROPAGATIONREQUIRESNEW -- 新建事务，如果当前存在事务，把当前事务挂起。
> 5. PROPAGATIONNOTSUPPORTED -- 以⾮事务⽅式执⾏操作，如果当前存在事务，就把当前事务挂起。
> 6. PROPAGATION_NEVER -- 以⾮事务⽅式执⾏，如果当前存在事务，则抛出异常。
> 7. PROPAGATIONNESTED -- 如果当前存在事务，则在嵌套事务内执⾏。如果当前没有事务，则进⾏与
> PROPAGATIONREQUIRED类似的操作。
>
> b. 五个隔离级别：
> 1. ISOLATION_DEFAULT 这是⼀个PlatfromTransactionManager默认的隔离级别，使⽤数据库默认的事务隔离级别.
> 2. 另外四个与JDBC的隔离级别相对应：
> 3. ISOLATIONREADUNCOMMITTED 这是事务最低的隔离级别，它充许别外⼀个事务可以看到这个事务未提交的数据。这种隔离级别会产⽣脏读，不可重复读和幻读。
> 4. ISOLATIONREADCOMMITTED 保证⼀个事务修改的数据提交后才能被另外⼀个事务读取。另外⼀个事务不能读取该事务未提交的数据。这种事务隔离级别可以避免脏读出现，但是可能会出现不可重复读和幻读。
> 5. ISOLATIONREPEATABLEREAD 这种事务隔离级别可以防⽌脏读，不可重复读。但是可能出现幻读。它除了保证⼀个事务不能读取另⼀个事务未提交的数据外，还保证了避免不可重复读。
> 6. ISOLATION_SERIALIZABLE 这是花费最⾼代价但是最可靠的事务隔离级别。事务被处理为顺序执⾏。除了防⽌脏读，不可重复读外，还避免了幻读。
>
> c. 关键词：
>
> ![image-20210306203156395](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306203156395.png)

## 什么是脏读、不可重复读、幻读

> ![image-20210306203258072](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306203258072.png)
>
> 1. 脏读（新增或删除）：脏读就是指当⼀个事务正在访问数据，并且对数据进⾏了修改，⽽这种修改还没有提交到数据库中，这时，另外⼀个事务也访问这个数据，然后使⽤了这个数据；
> > 1. Mary的原⼯资为1000, 财务⼈员将Mary的⼯资改为了8000(但未提交事务) 
> > 2. Mary读取⾃⼰的⼯资 ,发现⾃⼰的⼯资变为了8000，欢天喜地！
> > 3. ⽽财务发现操作有误，回滚了事务,Mary的⼯资⼜变为了1000像这样,Mary记取的⼯资数8000是⼀个脏数据。
> 2. 不可重复读（修改）：是指在⼀个事务内，多次读同⼀数据。在这个事务还没有结束时，另外⼀个事务也访问该同⼀数据。那么，在第⼀个事务中的两次读数据之间，由于第⼆个事务的修改，那么第⼀个事务两次读到的的数据可能是不⼀样的。这样在⼀个事务内两次读到的数据是不⼀样的，因此称为是不可重复读。（解决：只有在修改事务完全提交之后才可以读取数据，则可以避免该问题）；
> >1. 在事务1中，Mary 读取了⾃⼰的⼯资为1000,操作并没有完成
> >2. 在事务2中，这时财务⼈员修改了Mary的⼯资为2000,并提交了事务
> >3. 在事务1中，Mary 再次读取⾃⼰的⼯资时，⼯资变为了2000
>
> 3. 幻读（新增或删除）：是指当事务不是独⽴执⾏时发⽣的⼀种现象，例如第⼀个事务对⼀个表中的数据进⾏了修改，这种修改涉及到表中的全部数据⾏。同时，第⼆个事务也修改这个表中的数据，这种修改是向表中插⼊⼀⾏新数据。那么，以后就会发⽣操作第⼀个事务的⽤户发现表中还有没有修改的数据⾏，就好象发⽣了幻觉⼀样（解决：如果在操作事务完成数据处理之前，任何其他事务都不可以添加新数据，则可避免该问题）。
> >⽬前⼯资为1000的员⼯有10⼈。
> >
> >1. 事务1读取所有⼯资为1000的员⼯。
> >2. 这时事务2向employee表插⼊了⼀条员⼯记录，⼯资也为1000
> >3. 事务1再次读取所有⼯资为1000的员⼯ 共读取到了11条记录。

## 如何自定义注解实现功能

> 创建自定义注解和创建一个接口相似，但是注解的 interface 关键字需要以@符号开头。
> 注解方法不能带有参数；
> 注解方法返回值类型限定为：基本类型、String、Enums、Annotation 或者是这些类型的
> 数组；
> 注解方法可以有默认值；
> 注解本身能够包含元注解，元注解被用来注解其它注解。

## Spring MVC 运行流程

> 1.spring mvc 将所有的请求都提交给 DispatcherServlet,它会委托应用系统的其他模块
> 负责对请求 进行真正的处理工作。
> 2.DispatcherServlet 查询一个或多个 HandlerMapping,找到处理请求的 Controller.
> 3.DispatcherServlet 请请求提交到目标 Controller
> 4.Controller 进行业务逻辑处理后，会返回一个 ModelAndView
> 5.Dispathcher 查询一个或多个 ViewResolver 视图解析器,找到 ModelAndView 对象指定
> 的视图对象
> 6.视图对象负责渲染返回给客户端。

## Spring MVC 启动流程

> 在 web.xml 文件中给 Spring MVC 的 Servlet 配置了 load-on-startup,所以程序启动的
> 时候会初始化 Spring MVC，在 HttpServletBean 中将配置的 contextConfigLocation
> 属性设置到 Servlet 中，然后在 FrameworkServlet 中创建了 WebApplicationContext,
> DispatcherServlet 根据 contextConfigLocation 配置的 classpath 下的 xml 文件初始化
> 了Spring MVC 总的组件。

## Spring 的单例实现原理

> Spring 对 Bean 实例的创建是采用单例注册表的方式进行实现的，而这个注册表的缓存是
> ConcurrentHashMap 对象。

## Spring 框架中用到了哪些设计模式

> 代理模式—在 AOP 和 remoting 中被用的比较多。
> 单例模式—在 spring 配置文件中定义的 bean 默认为单例模式。
> 模板方法—用来解决代码重复的问题。比如. RestTemplate, JmsTemplate,
> JpaTemplate。 
> 前端控制器—Spring 提供了 DispatcherServlet 来对请求进行分发。
> 视图帮助(View Helper )—Spring 提供了一系列的 JSP 标签，高效宏来辅助将分散的代码
> 整合在视图里。
> 依赖注入—贯穿于 BeanFactory / ApplicationContext 接口的核心理念。
> 工厂模式—BeanFactory 用来创建对象的实例。

## Spring中⽤到了哪些设计模式？⾃⼰有⽤过哪些设计模式吗？

> a. 简单⼯⼚：spring中的BeanFactory就是简单⼯⼚模式的体现，根据传⼊⼀个
唯⼀的标识来获得bean对象，但是否是在传⼊参数后创建还是传⼊参数前创建这个
要根据具体情况来定。
b. 单例模式：Spring下默认的bean均为singleton。
c. 代理模式：为其他对象提供⼀种代理以控制对这个对象的访问。 从结构上来看
和Decorator模式类似，但Proxy是控制，更像是⼀种对功能的限制，⽽Decorator
是增加职责。 spring的Proxy模式在aop中有体现，⽐如JdkDynamicAopProxy和
Cglib2AopProxy。
d. 观察者模式：定义对象间的⼀种⼀对多的依赖关系，当⼀个对象的状态发⽣改变
时，所有依赖于它的对象都得到通知并被⾃动更新。spring中Observer模式常⽤的
地⽅是listener的实现。如ApplicationListener。

## 说说 Aop 和 IOC 的应用 

> IOC 的主要应用场景体现在 BeanFactory 接口，BeanFactory 下面有具体的实现类来实现 IOC 的功能。 
> AOP 的主要应用场景：日志、权限、事物等。

## Spring 中 bean 是线程安全的吗？ 

> Spring 容器中的 Bean 本身不具备线程安全的特性，但是具体还是要结合具体 scope 的 Bean 去研究。 
>
> 1. Spring 容器中的 Bean 默认是单例的，所有线程都共享一个单实例的 Bean，因此是存在资源的竞争。如果单例 Bean是一个无状态 Bean，也就是线程中的操作不会对 Bean 的成员执行查询以外的操作，那么这个单例 Bean 是线程安全的。比如 Spring mvc 的 Controller、Service、Dao 等，这些 Bean 大多是无状态的，只 关注于方法本身。对于有状态的 bean，是线程不安全的，但是我们可以通过 ThreadLocal 去解决线程安全的方法。 
> 2. 对于原型 Bean（即 scope="prototype"）,每次创建一个新对象，也就是线程之间并不存在 Bean 共享，自然是不会有线程安全的问题。 
>    参考文章：https://blog.csdn.net/qq_29645505/article/details/88432001

