package xk;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

public class Solution02 {
    @Test
    public void myTest(){
    
    }
    
    class ReplaceWordsTrie{
        boolean endSign=false;
        ReplaceWordsTrie[] next=new ReplaceWordsTrie[26];
    };
    
    public String replaceWords(List<String> dictionary, String sentence) {
        ReplaceWordsTrie root=new ReplaceWordsTrie();
        dictionary.forEach((dict)->{
            replaceWordsAdd(root,dict,0);
        });
        String[] strings=sentence.split(" ");
        String[] ans=new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            replaceWordsReplace(root,strings[i],0,i,ans);
        }
        StringJoiner joiner = new StringJoiner(" ");
        for (String an : ans) {
            joiner.add(an);
        }
        return joiner.toString();
    }
    
    public void replaceWordsReplace(ReplaceWordsTrie root,String s,int strIndex,int ansIndex,String[] ans){
        if((root==null)||(strIndex==s.length()&&!root.endSign)){
            ans[ansIndex]=s;
            return;
        }
        if(root.endSign){
            ans[ansIndex]=s.substring(0,strIndex);
            return;
        }
        replaceWordsReplace(root.next[s.charAt(strIndex)-'a'],s,strIndex+1,ansIndex,ans );
    }
    
    public void replaceWordsAdd(ReplaceWordsTrie root,String s,int index){
        if(index==s.length()){
            root.endSign=true;
            return;
        }
        int i=s.charAt(index)-'a';
        if(root.next[i]==null){
            root.next[i]=new ReplaceWordsTrie();
        }
        replaceWordsAdd(root.next[i], s, index+1);
    }
    
    
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num,map.getOrDefault(num,0)+1);
        }
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[1]-o2[1];
            }
        });
        map.forEach((key, value) -> {
            queue.add(new Integer[]{key,value});
            while (queue.size()>k){
                queue.poll();
            }
        });
        int[] ans=new int[k];
        for (int i = 0; i < ans.length; i++) {
            if(!queue.isEmpty()){
                ans[i]=queue.poll()[0];
            }
        }
        return ans;
    }
    
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int ans=0;
        int product=1;
        for (int left = 0,right=0; left < nums.length; left++) {
            while (left>right){
                right++;
            }
            
            while (right<nums.length){
                if(product*nums[right]<k){
                    product*=nums[right];
                    right++;
                }else {
                    break;
                }
            }
            ans+=right-left;
            if(right>left){
                product/=nums[left];
            }
        }
        return ans;
    }
    
    public String[][] partition(String s) {
        int n=s.length();
        char[] chars = s.toCharArray();
        boolean[][] dp=new boolean[n][n];
        
        for (int i = 0; i < n; i++) {
            dp[i][i]=true;
        }
        HashMap<Integer, List<List<String>>> map = new HashMap<>();
        ArrayList<List<String>> lasts = new ArrayList<>();
        ArrayList<String> last = new ArrayList<>();
        last.add(String.valueOf(chars[n-1]));
        lasts.add(last);
        map.put(n-1,lasts);
        for (int i = n-2; i >= 0; i--) {
            map.put(i,new ArrayList<List<String>>());
            if(chars[i]==chars[i+1]){
                dp[i][i+1]=true;
            }
            for (int j = i+2; j < n; j++) {
                dp[i][j]=dp[i+1][j-1]&&(chars[i]==chars[j]);
            }
            for (int j = i; j < n; j++) {
                if(dp[i][j]){
                    partitionAdd(map,s,i,j,n);
                }
            }
        }
        List<List<String>> lists = map.get(0);
        String[][] ans=new String[lists.size()][];
        int index=0;
        for (List<String> list : lists) {
            ans[index]=list.toArray(new String[0]);
            index++;
        }
        return ans;
    }
    
    public void partitionAdd(HashMap<Integer, List<List<String>>> map,String s,int start,int end,int n){
        String temp=s.substring(start,end+1);
        if(end+1==n){
            ArrayList<String> list = new ArrayList<>();
            list.add(temp);
            map.get(start).add(list);
        }else {
            List<List<String>> lists=map.get(end+1);
            for (List<String> list : lists) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(temp);
                arrayList.addAll(list);
                map.get(start).add(arrayList);
            }
        }
    }
    
    
    public int mySqrt(int x) {
        int left=0;
        int right=x;
        while (left<right){
            long mid=(left+right)/2;
            if(mid*mid>x){
                right=(int)mid;
            }else {
                left=(int)mid+1;
            }
        }
        return (long)left*left==x?left:left-1;
    }
    

    
    public List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> ans = new ArrayList<>();
        if(p.length()>s.length()){
            return ans;
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        char[] pChars = p.toCharArray();
        char[] sChars = s.toCharArray();
        for (int i = 0; i < p.length(); i++) {
            map.put(pChars[i],map.getOrDefault(pChars[i],0)+1);
        }
        for (int i = 0; i < p.length(); i++) {
            map.put(sChars[i],map.getOrDefault(sChars[i],0)-1);
            if(map.get(sChars[i])==0){
                map.remove(sChars[i]);
            }
        }
        int index=0;
        if(map.isEmpty()){
            ans.add(index);
        }
        int end=s.length()-p.length();
        for (int i = 0,right=p.length(); i < end; i++,right++) {
            map.put(sChars[i],map.getOrDefault(sChars[i],0)+1);
            if(map.get(sChars[i])==0){
                map.remove(sChars[i]);
            }
            map.put(sChars[right],map.getOrDefault(sChars[right],0)-1);
            if(map.get(sChars[right])==0){
                map.remove(sChars[right]);
            }
            if(map.isEmpty()){
                ans.add(i+1);
            }
        }
        return ans;
    }
    
    /**
     * HashMap<Integer,ArrayList<List<Integer>>> combinationSumMap=new HashMap<>();
     */
    
    ArrayList<List<Integer>> ans;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        ans=new ArrayList<List<Integer>>();
        int sum=0;
        List<Integer> temp = new ArrayList<>();
        combinationSumHelp(candidates,target,temp,0,0);
        return ans;
    }
    
    public void combinationSumHelp(int[] candidates,int target,List<Integer> temp,int sum,int index){
        if (index>= candidates.length){
            return;
        }
        combinationSumHelp(candidates, target, temp, sum, index+1);
        
        sum+=candidates[index];
        temp.add(candidates[index]);
        if(sum>=target){
            if(sum==target){
                ans.add(new ArrayList<Integer>(temp));
            }
            temp.remove(temp.size()-1);
            return;
        }
        combinationSumHelp(candidates, target, temp, sum, index);
        temp.remove(temp.size()-1);
    }
    
    public int searchInsert(int[] nums, int target) {
        if(nums.length==0){
            return 0;
        }
        int left=0,right=nums.length-1;
        while (left<=right){
            if(nums[left]>target){
                return left;
            }
            if(nums[right]<target) {
                return right+1;
            }
            int mid=(left+right)>>1;
            if (target>nums[mid]){
                left=mid+1;
            }else if(target<nums[mid]){
                right=mid;
            }else if(nums[mid]==target){
                return mid;
            }
        }
        return 0;
    }
    
//    class LRUCache {
//        int max;
//        PriorityQueue<Integer[]> queue;
//
//        public LRUCache(int capacity) {
//            max=capacity;
//            queue=new PriorityQueue<Integer[]>(new Comparator<Integer[]>() {
//                @Override
//                public int compare(Integer[] o1, Integer[] o2) {
//                    return 0;
//                }
//            })
//        }
//
//        public int get(int key) {
//
//        }
//
//        public void put(int key, int value) {
//
//        }
//    }
    
    public int divide(int a, int b) {
        int symbol=1;
        if(a>>31==b>>31){
            symbol=1;
        }else {
            symbol=-1;
        }
        long aa=a;
        long bb=b;
        aa=a<0?-aa:aa;
        bb=b<0?-bb:bb;
        if(aa<bb){
            return 0;
        }
        long ans=0;
        while (aa>=bb){
            long temp=1;
            long bt=bb;
            while ((bt<<1)<=aa){
                bt<<=1;
                temp<<=1;
            }
            ans+=temp;
            aa-=bt;
        }
        return (int)(symbol*ans>Integer.MAX_VALUE?Integer.MAX_VALUE:symbol*ans);
    }
    
    public List<List<Integer>> subsets(int[] nums) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<Integer>());
        for (int i = 0; i < nums.length; i++) {
            ArrayList<List<Integer>> list = new ArrayList<>();
            for (List<Integer> an : ans) {
                ArrayList<Integer> list1 = new ArrayList<>(an);
                list1.add(nums[i]);
                list.add(list1);
            }
            ans.addAll(list);
        }
        return ans;
    }
    
    
    
    public boolean validPalindrome(String s) {
        if(s.length()==0){
            return true;
        }
        char[] str=s.toCharArray();
        int left=0,right=str.length-1;
        while (left<=right){
            if(str[left]==str[right]){
                left++;
                right--;
            }else {
                return validPalindromeHelp(str,left+1,right)||validPalindromeHelp(str,left,right-1);
            }
        }
        return true;
    }
    
    public boolean validPalindromeHelp(char[] str,int left,int right){
        if(left>right){
            return true;
        }
        while (left<=right){
            if(str[left]==str[right]){
                left++;
                right--;
            }else {
                return false;
            }
        }
        return true;
    }
    
}
