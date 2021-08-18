package xk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class leetCodeUtils {
    /**
     * 二分查找
     *
     **/
    public static int binSearch(int target, int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        if (target < arr[low] || target > arr[high] || low > high) {
            return -1;
        }
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * gcd 求最大公约数 辗转相除法
     * a,b无需关心大小
     * b==0?a:gcd(b,a%b);
     **/
    
    /**
     * lcm 求最小公倍数
     **/
    public static int gcd(int a,int b) {
        if (b == 0){
            return a;
        } else{
            return gcd(b, a % b);
        }
    }
    
    public static int lcm(int a, int b) {
        if (a != 0 || b != 0) {
            return a * b / gcd(a, b);
        } else {
            return 0;
        }
    }
    
    /**
    * 最大队列最小队列
    * 优先队列
    * 最大栈最小栈
    * */
    PriorityQueue<Integer> minPQ=new PriorityQueue<>();
    PriorityQueue<Integer> maxPQ=new PriorityQueue<>((a,b)->(a-b));
    public static void pushPQ(int len,int num,PriorityQueue<Integer> pq){
        pq.add(num);
        while (pq.size()>len){
            pq.poll();
        }
    }
    
    /**
     * max 求最大值
     **/
    public static int getMax(int... nums){
        return IntStream.of(nums).max().getAsInt();
    }
    
    /**
     * min 求最小值
     **/
    public static int getMin(int... nums){
        return IntStream.of(nums).min().getAsInt();
    }
    
    
    /**
     * KMP
     * getNext
     * next数组
     */
    public static int[] getNext(String s){
        char[] chars=s.toCharArray();
        int[] next=new int[chars.length];
        next[0]=-1;
        int prefixIndex=-1;
        int index=0;
        int end= chars.length-1;
        while (index<end){
            if(prefixIndex==-1||chars[prefixIndex]==chars[index]){
                prefixIndex++;
                index++;
                if(chars[index]==chars[prefixIndex]){
                    next[index]=prefixIndex;
                }else {
                    next[index]=next[prefixIndex];
                }
            }else {
                prefixIndex=next[prefixIndex];
            }
        }
        return next;
    }
    
    
    /**
     * 快速乘法
     * quickMulti
     */
    public static long quickMulti(long a,long k){
        long ans=0;
        while (k>0){
            if((k&1)==1){
                ans+=a;
            }
            k>>=1;
            a+=a;
        }
        return ans;
    }
    
    /**
     * swap
     * reverse
     */
    static public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    static public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    
    
    /**
     * 前缀树
     * Trie
     */
    static class Trie {
        
        private Trie[] children;
        private boolean isEnd;
        
        /** Initialize your data structure here. */
        public Trie() {
            children=new Trie[26];
            isEnd=false;
        }
        
        /** Inserts a word into the trie. */
        public void insert(String word) {
            Trie node=this;
            for (int i = 0; i < word.length(); i++) {
                char ch=word.charAt(i);
                int index=ch-'a';
                if(node.children[index]==null){
                    node.children[index]=new Trie();
                }
                node=node.children[index];
            }
            node.isEnd=true;
        }
        
        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node!=null&&node.isEnd;
        }
        
        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix)!=null;
        }
        
        private Trie searchPrefix(String prefix){
            Trie node=this;
            for (int i = 0; i < prefix.length(); i++) {
                char ch=prefix.charAt(i);
                int index=ch-'a';
                if(node.children[index]==null){
                    return null;
                }
                node=node.children[index];
            }
            return node;
        }
    }
    
    
}


