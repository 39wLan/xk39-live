# SpringCloud

## springcloud各个组件功能，内部细节，与dubbo区别，dubbo架构，dubbo负载策略

> 1、springcloud各个组件功能：
> a. Ribbon，客户端负载均衡，特性有区域亲和、重试机制。
> b. Hystrix，客户端容错保护，特性有服务降级、服务熔断、请求缓存、请求合并、依赖隔离。
> c. Feign，声明式服务调⽤，本质上就是Ribbon+Hystrix
> d. Stream，消息驱动，有Sink、Source、Processor三种通道，特性有订阅发布、消费组、消息分区。
> e. Bus，消息总线，配合Config仓库修改的⼀种Stream实现，
> f. Sleuth，分布式服务追踪，需要搞清楚TraceID和SpanID以及抽样，如何与ELK整合。
> g. Eureka，服务注册中⼼，特性有失效剔除、服务保护。
> h. Dashboard，Hystrix仪表盘，监控集群模式和单点模式，其中集群模式需要收集器Turbine配合。
> i. Zuul，API服务⽹关，功能有路由分发和过滤。
> j. Config，分布式配置中⼼，⽀持本地仓库、SVN、Git、Jar包内配置等模式
>
> 2、dubbo负载策略：
>
> ![image-20210306194815793](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306194815793.png)

## SpringCloud全家桶包含哪些组件？

> 1. Ribbon，客户端负载均衡，特性有区域亲和、重试机制。
> 2. Hystrix，客户端容错保护，特性有服务降级、服务熔断、请求缓存、请求合并、依赖隔离。
> 3. Feign，声明式服务调⽤，本质上就是Ribbon+Hystrix
> 4. Stream，消息驱动，有Sink、Source、Processor三种通道，特性有订阅发布、消费组、消息分区。
> 5. Bus，消息总线，配合Config仓库修改的⼀种Stream实现，
> 6. Sleuth，分布式服务追踪，需要搞清楚TraceID和SpanID以及抽样，如何与ELK整合。
> 7. Eureka，服务注册中⼼，特性有失效剔除、服务保护。
> 8. Dashboard，Hystrix仪表盘，监控集群模式和单点模式，其中集群模式需要收集器Turbine配合。
> 9. Zuul，API服务⽹关，功能有路由分发和过滤。
> 10. Config，分布式配置中⼼，⽀持本地仓库、SVN、Git、Jar包内配置等模式。

## springcloud有哪些核⼼组件，以及springcloud服务调⽤的详细⼯作流程？

> ![image-20210306195156254](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306195156254.png)
>
> springcloud由以下⼏个核⼼组件构成：
> Eureka：各个服务启动时，Eureka Client都会将服务注册到Eureka Server，并且Eureka Client还可以反过来从Eureka Server拉取注册表，从⽽知道其他服务在哪⾥
> Ribbon：服务间发起请求的时候，基于Ribbon做负载均衡，从⼀个服务的多台机器中选择⼀台
> Feign：基于Feign的动态代理机制，根据注解和选择的机器，拼接请求URL地址，发起请求
> Hystrix：发起请求是通过Hystrix的线程池来⾛的，不同的服务⾛不同的线程池，实现了不同服务调⽤的隔离，避免了服务雪崩的问题
> Zuul：如果前端、移动端要调⽤后端系统，统⼀从Zuul⽹关进⼊，由Zuul⽹关转发请求给对应的服务

## ⽤什么组件发请求?

> 在Spring Cloud中使⽤Feign, 我们可以做到使⽤HTTP请求远程服务时能与调⽤本地⽅法⼀样的编码体验，开发者完全感知不到这是远程⽅法，更感知不到这是个HTTP请求。

## 注册中⼼⼼跳是⼏秒?

> 1、Eureka的客户端默认每隔30s会向eureka服务端更新实例，注册中⼼也会定时进⾏检查，发现某个实例默认90s内没有再收到⼼跳，会注销此实例，这些时间间隔是可配置的。
> 2、不过注册中⼼还有⼀个保护模式（服务端在短时间内丢失过多客户端的时候，就会进⼊保护模式），在这个保护模式下，他会认为是⽹络问题，不会注销任何过期的实例。

## 消费者是如何发现服务提供者的?

> ![image-20210306200446664](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306200446664.png)
>
> a. 当⼀个服务实例启动，会将它的ip地址等信息注册到eureka；
> b. 当a服务调⽤b服务，a服务会通过Ribbon检查本地是否有b服务实例信息的缓存；
> c. Ribbon会定期从eureka刷新本地缓存。

## 多个消费者调⽤同⼀接⼝，eruka默认的分配⽅式是什么?

> a. RoundRobinRule:轮询策略，Ribbon以轮询的⽅式选择服务器，这个是默认值。所以示例中所启动的两个服务会被循环访问;
> b. RandomRule:随机选择，也就是说Ribbon会随机从服务器列表中选择⼀个进⾏访问;
> c. BestAvailableRule:最⼤可⽤策略，即先过滤出故障服务器后，选择⼀个当前并发请求数最⼩的;
> d. WeightedResponseTimeRule:带有加权的轮询策略，对各个服务器响应时间进⾏加权处理，然后在采⽤轮询的⽅式来获取相应的服务器;
> e. AvailabilityFilteringRule:可⽤过滤策略，先过滤出故障的或并发请求⼤于阈值⼀部分服务实例，然后再以线性轮询的⽅式从过滤后的实例清单中选出⼀个;
> f. ZoneAvoidanceRule:区域感知策略，先使⽤主过滤条件（区域负载器，选择最优区域）对所有实例过滤并返回过滤后的实例清单，依次使⽤次过滤条件列表中的过滤条件对主过滤条件的结果进⾏过滤，判断最⼩过滤数（默认1）和最⼩过滤百分⽐（默认0），最后对满⾜条件的服务器则使⽤RoundRobinRule(轮询⽅式)选择⼀个服务器实例。

## eureka的缺点。

> a. 某个服务不可⽤时，各个Eureka Client不能及时的知道，需要1~3个⼼跳周期才能感知，但是，由于基于Netflix的服务调⽤端都会使⽤Hystrix来容错和降级，当服务调⽤不可⽤时Hystrix也能及时感知到，通过熔断机制来降级服务调⽤，因此弥补了基于客户端服务发现的时效性的缺点。

## eureka缓存机制？

> ![image-20210306204048389](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306204048389.png)
>
> a. 第⼀层缓存：readOnlyCacheMap，本质上是ConcurrentHashMap：这是⼀个JVM的CurrentHashMap只读缓存，这个主要是为了供客户端获取注册信息时使⽤，其缓存更新，依赖于定时器的更新，通过和readWriteCacheMap 的值做对⽐，如果数据不⼀致，则以readWriteCacheMap 的数据为准。readOnlyCacheMap 缓存更新的定时器时间间隔，默认为30秒
> b. 第⼆层缓存：readWriteCacheMap，本质上是Guava缓存：此处存放的是最终的缓存， 当服务下线，过期，注册，状态变更，都会来清除这个缓存⾥⾯的数据。 然后通过CacheLoader进⾏缓存加载，在进⾏readWriteCacheMap.get(key)的时候，⾸先看这个缓存⾥⾯有没有该数据，如果没有则通过CacheLoader的load⽅法去加载，加载成功之后将数据放⼊缓存，同时返回数据。readWriteCacheMap 缓存过期时间，默认为 180 秒 。
> c. 缓存机制：设置了⼀个每30秒执⾏⼀次的定时任务，定时去服务端获取注册信息。获取之后，存⼊本地内存。

