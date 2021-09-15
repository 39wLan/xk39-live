##### KMP算法/BM/Sunday

> [从头到尾彻底理解KMP（2014年8月22日版）_结构之法 算法之道-CSDN博客_kmp](https://blog.csdn.net/v_july_v/article/details/7041827)

```java
public int[] getNext(String s){
    char[] chars=s.toCharArray();
    int[] next=new int[chars.length];
    next[0]=-1;
    int prefixIndex=-1;
    int index=0;
    int end= chars.length-1;
    while (index<end){
        /**
         * chars[prefixIndex]==chars[index]
         */
        if(prefixIndex==-1||chars[prefixIndex]==chars[index]){
            prefixIndex++;
            index++;
            /**
             *
             */
            if(chars[index]==chars[prefixIndex]){
                next[index]=next[prefixIndex];
            }else {
                next[index]=prefixIndex;
            }
        }else {
            prefixIndex=next[prefixIndex];
        }
    }
    return next;
}
```

##### io.jsonwebtoken.Jwts包

> ```xml
> <dependency>
>             <groupId>io.jsonwebtoken</groupId>
>             <artifactId>jjwt</artifactId>
>             <version>0.6.0</version>
> </dependency>
> 
> ```
>
> ```java
>         JwtBuilder jwtBuilder = Jwts.builder().setId("88").setSubject("小白")
>                 .setIssuedAt(new Date())
>                 .signWith(SignatureAlgorithm.HS256, "ihrm")
>                 .claim("companyId", "123456")
>                 .claim("companyName", "xk_com");
>         String token = jwtBuilder.compact();
>         System.out.println(token);
> ```
>
> 

##### git

> 1.[git_nrsc-CSDN博客](https://blog.csdn.net/nrsc272420199/category_8554728.html) 
>
> 

##### 报表生成

##### shiro

##### ws.schild.jave

##### Enum

##### MySQL安装

##### 二分 + 倍增乘法

> [【宫水三叶】二分 + 倍增乘法解法（含模板） - 两数相除 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/divide-two-integers/solution/shua-chuan-lc-er-fen-bei-zeng-cheng-fa-j-m73b/)

##### 欧拉快速幂

> [372. 超级次方 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/super-pow/)
>
> [降幂公式+快速幂（时间复杂度干掉100%） - 超级次方 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/super-pow/solution/jiang-mi-gong-shi-kuai-su-mi-shi-jian-fu-za-du-gan/)
>
> [超级次方 - 超级次方 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/super-pow/solution/chao-ji-ci-fang-by-wisemove-2/)

```

a表：500条数据，其中‘time’=2077的有400条
b表：4000条数据，其中‘time’=2077的有100条


in:
for(a.b:as*bs)

exits:
for{a:as
   if(a==target){
   		for(b:bs)
   }
}






```

##### Manacher 算法

[最长回文子串 - 最长回文子串 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zui-chang-hui-wen-zi-chuan-by-leetcode-solution/)

##### Rabin-Karp 算法

##### 妙啊

[寻找重复数 - 寻找重复数 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/find-the-duplicate-number/solution/xun-zhao-zhong-fu-shu-by-leetcode-solution/)

##### Floyd判圈算法

[寻找重复数 - 寻找重复数 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/find-the-duplicate-number/solution/xun-zhao-zhong-fu-shu-by-leetcode-solution/)

[算法-floyd判环(圈)算法_q2nAmor-CSDN博客_floyd 判圈算法](https://blog.csdn.net/u012534831/article/details/74231581)

##### 线段树

[区域和检索 - 数组可修改 - 区域和检索 - 数组可修改 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/range-sum-query-mutable/solution/qu-yu-he-jian-suo-shu-zu-ke-xiu-gai-by-leetcode/)

##### 欧拉图

[重新安排行程 - 重新安排行程 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/reconstruct-itinerary/solution/zhong-xin-an-pai-xing-cheng-by-leetcode-solution/)

##### 并查集

[🎦 399. 除法求值 - 除法求值 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/evaluate-division/solution/399-chu-fa-qiu-zhi-nan-du-zhong-deng-286-w45d/)

![image-20210811171023920](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210811171023920.png)

##### 线段树

[673. 最长递增子序列的个数 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/)

[327. 区间和的个数 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/count-of-range-sum/)

[493. 翻转对 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/reverse-pairs/)

[算法学习笔记(14): 线段树 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/106118909)

[线段树 从入门到进阶 - Dijkstra·Liu - 博客园 (cnblogs.com)](https://www.cnblogs.com/jason2003/p/9676729.html)

##### 延时双删

##### 大字典模糊匹配

[(3条消息) Wu-Manber 经典多模式匹配算法_pi9nc的专栏-CSDN博客](https://blog.csdn.net/pi9nc/article/details/9124623)

##### 字符串多模式匹配

##### kafka和rabbit丢失消息的补救机制

群友说rocket比较稳定，基本上不丢稍息

##### Java 异常: Unsupported major.minor version 51.0

https://blog.csdn.net/admin_mvip/article/details/89327520

![image-20210827190428087](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210827190428087.png)

![image-20210827190454873](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210827190454873.png)

#### 群友问答

##### feign底层原理要往哪方面回答？

![image-20210830172625401](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210830172625401.png)

> https://blog.csdn.net/weixin_41335352/article/details/116711499?spm=1001.2014.3001.5501  我当时看完源码写的。让我就说下feign的 发送请求的过程呗。还有几个注解写的很nice

##### mysql数据页，要怎么组织语言比较好

> - 数据页底层是双向链表，  一个页的数据量有限， 那么页存满时，就会用下一个数据页，  那么久要求我们主键单调递增，  减少页分裂，
>
> - 数据页是数据页，跟双向链表这种数据结构没有直接关系吧，双向链表是B+树叶子节点得排列方式
>
> - 这时候我就要推荐一下我之前写的文章了 哈哈
>
>   [MySQL的最深处-磁盘文件结构 (qq.com)](https://mp.weixin.qq.com/s/1-39TWE7FNj6l7eQYTbqGQ)
>
> - 数据页就是mysql内存和磁盘数据交换得单位，一个数据页默认是16KB
>
> - ![image-20210830172959424](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210830172959424.png)
>
> - 不是吧，是页和页之间是双向链表，页内数据不是，单向得。
>
> - <img src="https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210830173058590.png" alt="image-20210830173058590"  />
>
> - 妈的 记错了 。。。
>
> - 页和页之间双向是为了范围查找，很好得支持>= 和<=，页内不需要，页内就是顺序得
>
> - 这题我面试遇到过
>
> - ![image-20210830173607796](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210830173607796.png)
>
> - 问我索引结构为啥选b +
>
> - 把这个图记一下
>
> - ![image-20210830173704530](https://gitee.com/xk39/typora-imgs/raw/master/imgs/image-20210830173704530.png)

##### double和long的非原子性协定与局部变量表

![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/double和long的非原子性协定与局部变量表-0000.png)

![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/double和long的非原子性协定与局部变量表-0001.png)

![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/double和long的非原子性协定与局部变量表-0002.png)

![](https://gitee.com/xk39/typora-imgs/raw/master/imgs/double和long的非原子性协定与局部变量表-0003.png)

