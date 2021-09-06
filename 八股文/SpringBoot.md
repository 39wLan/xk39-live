# SpringBoot

## 说说常⽤的springboot注解，及其实现？
> a. @Bean：注册Bean
> i. 默认使⽤⽅法名作为id，可以在后⾯定义id如@Bean("xx")；
> ii. 默认为单例。
> iii. 可以指定init⽅法和destroy⽅法：
>
> 1. 对象创建和赋值完成，调⽤初始化⽅法；
> 2. 单实例bean在容器销毁的时候执⾏destroy⽅法；
> 3. 多实例bean，容器关闭是不会调⽤destroy⽅法。
>
> b. @Scope：Bean作⽤域
> i. 默认为singleton；
> ii. 类型：
>
> 1. singleton单实例（默认值）：ioc容器启动时会调⽤⽅法创建对象放到ioc容器中，以后每次获取就是直接从容器中拿
> 实例；
> 2. prototype多实例：ioc容器启动不会创建对象，每次获取时才会调⽤⽅法创建实例；
> 3. request同⼀次请求创建⼀个实例；
> 4. session同⼀个session创建⼀个实例。
>
> c. @Value：给变量赋值
> i. 代码；
>
> ``` java
> import org.springframework.beans.factory.annotation.Value;
> 
> public class Person extends BaseEntity {
>     @Value("xuan")
>     private String name;
>     @Value("27")
>     private int age;
>     @Value("#{20-7}")
>     private int count;
>     @Value("${person.nickName}")
>     private String nickName;
> }
> ```
>
> ii. 方式：
>
> 1. 基本数字
> 2. 可以写SpEL（Spring EL表达式）：#{}
> 3. 可以写${}，取出配置⽂件中的值（在运⾏环境变量⾥⾯的值）
>
> d. @Autowired：⾃动装配
> i. 默认优先按照类型去容器中找对应的组件：BookService bookService = applicationContext.getBean(BookService.class);
> ii. 默认⼀定要找到，如果没有找到则报错。可以使⽤@Autowired(required = false)标记bean为⾮必须的。
> iii. 如果找到多个相同类型的组件，再根据属性名称去容器中查找。
> iv. @Qualifier("bookDao2")明确的指定要装配的bean。
> v. @Primary：让spring默认装配⾸选的bean，也可以使⽤@Qualifier()指定要装配的bean。
>
> e. @Profile：环境标识
> i. Spring为我们提供的可以根据当前环境，动态的激活和切换⼀系列组件的功能；
> ii. @Profile指定组件在哪个环境才能被注册到容器中，默认为"default"@Profile("default")。
> iii. 激活⽅式：
> 1. 运⾏时添加虚拟机参数：-Dspring.profiles.active=test
> 2. 代码⽅式：

## springboot启动过程

> ![image-20210306204351874](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210306204351874.png)
>
> 1. 通过 SpringFactoriesLoader加载 META-INF/spring.factories⽂件，获取并创建
> SpringApplicationRunListener对象
> 2. 然后由 SpringApplicationRunListener来发出 starting 消息
> 3. 创建参数，并配置当前 SpringBoot 应⽤将要使⽤的 Environment
> 4. 完成之后，依然由 SpringApplicationRunListener来发出 environmentPrepared 消息
> 5. 创建 ApplicationContext
> 6. 初始化 ApplicationContext，并设置 Environment，加载相关配置等
> 7. 由 SpringApplicationRunListener来发出 contextPrepared消息，告知SpringBoot 应⽤使⽤的
> ApplicationContext已准备OK
> 8. 将各种 beans 装载⼊ ApplicationContext，继续由 SpringApplicationRunListener来发出 contextLoaded 消息，告知 SpringBoot 应⽤使⽤的 ApplicationContext已装填OK
> 9. refresh ApplicationContext，完成IoC容器可⽤的最后⼀步
> 10. 由 SpringApplicationRunListener来发出 started 消息
> 11. 完成最终的程序的启动
> 12. 由 SpringApplicationRunListener来发出 running 消息，告知程序已运⾏起来了

## 说说⼏个常⽤的注解？

> @RestController ：@ResponseBody和@Controller的合集。
> @EnableAutoConfiguration ：尝试根据你添加的jar依赖⾃动配置你的Spring应⽤。
> @ComponentScan：表示将该类⾃动发现（扫描）并注册为Bean，可以⾃动收集所有的Spring组件，包括@Configuration类。
> @ImportResource ：⽤来加载xml配置⽂件。
> @Configuration ：相当于传统的xml配置⽂件，如果有些第三⽅库需要⽤到xml⽂件，建议仍然通过@Configuration类作为项⽬的配置主类——可以使⽤@ImportResource注解加载xml配置⽂件。
> @SpringBootApplication：相当于@EnableAutoConfiguration、@ComponentScan和@Configuration的合集。