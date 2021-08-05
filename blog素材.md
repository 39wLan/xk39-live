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

