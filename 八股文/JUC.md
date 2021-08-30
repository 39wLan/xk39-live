## wait 和 sleep 的区别

> **sleep 方法和 wait 方法都可以用来放弃 CPU 一定的时间，不同点在于如果线程持有某个对象的监视器，sleep 方法不会放弃这个对象的监视器，wait 方法会放弃这个对象的监视器。**
>
> 源码如下
>
> ```java
> public class Thread implements Runnable { 
>  public static native void sleep(long millis) throws InterruptedException;
>  public static void sleep(long millis, int nanos) throws InterruptedException{
>      if (millis < 0) { 
>          throw new IllegalArgumentException("timeout value is negative"); 
>      }if (nanos < 0 || nanos > 999999) {
>          throw new IllegalArgumentException( "nanosecond timeout value out of range"); 
>      }if (nanos >= 500000 || (nanos != 0 && millis == 0)) { 
>          millis++; 
>      }
>      sleep(millis); 
>  }
>  //... 
> }
> ```
>
> ```java
> public class Object { 
> 	public final native void wait(long timeout) throws InterruptedException;
> 	public final void wait(long timeout, int nanos) throws InterruptedException {
>     	if (timeout < 0) { 
>         	throw new IllegalArgumentException("timeout value is negative"); 
>     	}if (nanos < 0 || nanos > 999999) { 
>             throw new IllegalArgumentException( "nanosecond timeout value out of range");
>         }if (nanos > 0) { 
>             timeout++; 
>         }
>         wait(timeout); 
>     }
>     //... 
> }
> ```
>
> 1、 sleep 来自 Thread 类，而 wait 来自 Object 类。 
>
> 2、最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。
>
> 3、wait，notify和 notifyAll 只能在同步控制方法或者同步控制块里面使用，而 sleep 可以在任何地方使用(使用范围) 
>
> 4、 sleep 必须捕获异常，而 wait ， notify 和 notifyAll 不需要捕获异常
>
> (1) sleep 方法属于 Thread 类中方法，表示让一个线程进入睡眠状态，等待一定的时间之后，自动醒来进入到可运行状态，不会马上进入运行状态，因为线程调度机制恢复线程的运行也需要时间，一个线程对象调用了 sleep
> 方法之后，并不会释放他所持有的所有对象锁，所以也就不会影响其他进程对象的运行。但在 sleep 的过程中过
> 程中有可能被其他对象调用它的 interrupt() ,产生 InterruptedException 异常，如果你的程序不捕获这个异
> 常，线程就会异常终止，进入 TERMINATED 状态，如果你的程序捕获了这个异常，那么程序就会继续执行catch语
> 句块(可能还有 finally 语句块)以及以后的代码。
> 注意 sleep() 方法是一个静态方法，也就是说他只对当前对象有效，通过 t.sleep() 让t对象进入 sleep ，这样
> 的做法是错误的，它只会是使当前线程被 sleep 而不是 t 线程
> (2) wait 属于 Object 的成员方法，一旦一个对象调用了wait方法，必须要采用 notify() 和 notifyAll() 方法
> 唤醒该进程;如果线程拥有某个或某些对象的同步锁，那么在调用了 wait() 后，这个线程就会释放它持有的所有
> 同步资源，而不限于这个被调用了 wait() 方法的对象。 wait() 方法也同样会在 wait 的过程中有可能被其他对
> 象调用 interrupt() 方法而产生 。

## ConcurrentHashMap 的并发度是什么？

> ConcurrentHashMap 的并发度就是 segment 的大小，默认为 16，这意味着最多同时可以有 16 条线程操作 ConcurrentHashMap，这也是 ConcurrentHashMap 对 Hashtable 的最大优势，任何情况下，Hashtable能同时有两条线程获取 Hashtable 中的数据吗？