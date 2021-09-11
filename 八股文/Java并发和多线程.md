### 来源 [并发编程面试题（2021优化版） (qq.com)](https://mp.weixin.qq.com/s/u2BXXtVn2-3wokORi0mSPQ)

### 基础知识

#### 并发编程的优缺点

#### 什么叫线程安全？servlet 是线程安全吗?

#### @$并发编程三要素是什么？在 Java 程序中怎么保证多线程的运行安全？

#### 并行和并发有什么区别？

### 线程和进程的区别

#### 什么是线程和进程?

#### @$进程与线程的区别

#### 什么是上下文切换?

#### 守护线程和用户线程有什么区别呢？

#### @$什么是线程死锁

#### @$形成死锁的四个必要条件是什么

#### @$如何避免线程死锁

### 创建线程的三种方式

#### 创建线程有哪几种方式？

#### 说一下 runnable 和 callable 有什么区别？

#### 线程的 run()和 start()有什么区别？为什么我们调用 start() 方法时会执行 run() 方法，为什么我们不能直接调用 run() 方法？

#### 什么是 Future 和 FutureTask?

### 线程的状态和基本操作

#### @$说说线程的生命周期及五种基本状态？

#### Java 中用到的线程调度算法是什么？

#### 请说出与线程同步以及线程调度相关的方法。

#### sleep() 和 wait() 有什么区别？

#### 线程的 sleep()方法和 yield()方法有什么区别？

#### 为什么线程通信的方法 wait(), notify()和 notifyAll()被定义在 Object 类里？

#### 为什么 wait(), notify()和 notifyAll()必须在同步方法或者同步块中被调用？

#### Java 中你怎样唤醒一个阻塞的线程？

#### Java 如何实现多线程之间的通讯和协作？

#### 什么是线程同步和线程互斥，有哪几种实现方式？

#### 一个线程运行时发生异常会怎样？

### 并发理论

#### Java中垃圾回收有什么目的？

#### 如果对象的引用被置为null，垃圾收集器是否会立即释放对象占用的内存？

#### 为什么代码会重排序？

#### as-if-serial规则和happens-before规则的区别

### 并发关键字

#### @$synchronized 的作用？

#### @$说说自己是怎么使用 synchronized 关键字，在项目中用到了吗

#### @$说一下 synchronized 底层实现原理？

#### 什么是自旋

#### @$多线程中 synchronized 锁升级的原理是什么？

#### @$synchronized 和 Lock 有什么区别？

#### synchronized 和 ReentrantLock 区别是什么？

#### @$volatile 关键字的作用

#### volatile 能保证原子性吗？volatile 能使得一个非原子操作变成原子操作吗？

#### @$synchronized 和 volatile 的区别是什么？

### Lock体系

#### Lock 接口(Lock interface)是什么？对比 synchronized 它有什么优势？

#### @$乐观锁和悲观锁的理解及如何实现，有哪些实现方式？

#### 什么是 CAS

#### CAS 的会产生什么问题？

#### AQS 介绍

#### @$AQS 原理分析

#### 什么是可重入锁（ReentrantLock）？

#### ReadWriteLock 是什么

### 并发容器

#### 什么是ConcurrentHashMap？

#### CopyOnWriteArrayList 是什么，可以用于什么应用场景？有哪些优缺点？

#### @$ThreadLocal 是什么？有哪些使用场景？什么是线程局部变量？

#### @$ThreadLocal造成内存泄漏的原因？

#### @$ThreadLocal内存泄漏解决方案？

#### 什么是阻塞队列？阻塞队列的实现原理是什么？如何使用阻塞队列来实现生产者-消费者模型？

### 线程池

#### 什么是线程池？

#### 有哪几种创建线程池的方式？

#### 线程池有什么优点？

#### 线程池都有哪些状态？

#### 在 Java 中 Executor 和 Executors 的区别？

#### 线程池中 submit() 和 execute() 方法有什么区别？

#### @$Executors和ThreaPoolExecutor创建线程池的区别

#### @$ThreadPoolExecutor构造函数重要参数分析

#### @$ThreadPoolExecutor拒绝策略

#### @$线程池实现原理，一个简单的线程池Demo：`Runnable`+`ThreadPoolExecutor`

### 原子操作类

#### 什么是原子操作？在 Java Concurrency API 中有哪些原子类(atomic classes)？

#### 说一下 atomic 的原理？

### 并发工具

#### 常用的并发工具类有哪些？在 Java 中 CycliBarriar 和 CountdownLatch 有什么区别？