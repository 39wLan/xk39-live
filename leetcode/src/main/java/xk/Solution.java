package xk;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static xk.leetCodeUtils.quickMulti;

class Solution {
    
    @Test
    public void myTest(){
        System.out.println(divide(-2147483648,2));
        
    }
    
    
    public String shortestPalindrome(String s) {
        int n=s.length();
        int base=131;
        int mod=100000007;
        int left=0,right=0,mul=1;
        int best=-1;
        for(int i=0;i<n;i++){
            left=(int)(((long)left*base+s.charAt(i))%mod);
            right=(int)((right+(long)mul*s.charAt(i))%mod);
            if(left==right){
                best=i;
            }
            mul=(int)((long)mul*base%mod);
        }
        String add=(best==n-1?"":s.substring(best+1));
        StringBuffer ans=new StringBuffer(add).reverse();
        ans.append(s);
        return ans.toString();
    }
    
    public int countSubstrings(String s) {
        if(s==null||s.length()==0){
            return 0;
        }
        int n=s.length();
        int ans=0;
        for (int i = 0; i < 2*n-1; i++) {
            int l=i/2,r=i/2+i%2;
            while (l>=0&&r<n&&s.charAt(l)==s.charAt(r)){
                ans++;
                l--;
                r++;
            }
        }
        return ans;
    }
    
    public String multiply(String num1, String num2) {
        if(num1.equals("0")||num2.equals("0")){
            return "0";
        }
        int n1=num1.length(),n2=num2.length();
        int[] arr=new int[n1+n2];
        for(int i=n1-1;i>=0;i--){
            int x=num1.charAt(i)-'0';
            for(int j=n2-1;j>=0;j--){
                int y=num2.charAt(j)-'0';
                arr[i+j+1]+=x*y;
            }
        }
        for(int i=arr.length-1;i>0;i--){
            arr[i-1]+=arr[i]/10;
            arr[i]%=10;
        }
        int index=arr[0]==0?1:0;
        StringBuffer ans=new StringBuffer();
        while(index<arr.length){
            ans.append(arr[index]);
            index++;
        }
        return ans.toString();
    }
    
    public String longestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        int len=charArray.length;
        int left=0;
        int maxLen=1;
        boolean[][] dp=new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i]=true;
        }
        for (int i = len-2; i >= 0; i--) {
            int leftTmp=i;
            int maxLenTmp=1;
            if(charArray[i]==charArray[i+1]){
                dp[i][i+1]=true;
                maxLenTmp=2;
            }
            for (int j = i+2; j < len; j++) {
                if(charArray[i]==charArray[j]&&dp[i+1][j-1]){
                    dp[i][j]=true;
                    maxLenTmp=j-i+1;
                }
            }
            if(maxLenTmp>maxLen){
                left=leftTmp;
                maxLen=maxLenTmp;
            }
        }
        return s.substring(left,left+maxLen);
    }
    
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int s=minutesToTest/minutesToDie+1;
        return (int)Math.ceil(Math.log(buckets)/Math.log(s));
    }
    
    public int countNumbersWithUniqueDigits(int n) {
        if(n==0){
            return 1;
        }
        if(n==1){
            return 10;
        }
        int[] res=new int[n+1];
        res[0]=1;
        res[1]=10;
        for(int i=2;i<=n;i++){
            res[i]=9;
            for(int j=1;j<i&&j<11;j++){
                res[i]*=(10-j);
            }
            res[i]+=res[i-1];
        }
        return res[n];
    }
    
    public double myPowHelp(double x,long N){
        double ans=1.0;
        double c=x;
        while (N>0){
            if((N&1)==1){
                ans*=c;
            }
            c*=c;
            N>>=1;
        }
        return ans;
    }
    
    public double myPow(double x, int n) {
        long N=n;
        return N>=0?myPowHelp(x,N):1.0/myPowHelp(x,-N);
    }
    
    public int divideHelp(int a,int b){
        if(a>b){
            return 0;
        }
        int res=1;
        int step=b;
        while (a-step<=step){
            step+=step;
            res+=res;
        }
        return res+divideHelp(a-step,b);
    }
    
    public int divide(int dividend, int divisor) {
        if(dividend==Integer.MIN_VALUE&&divisor==-1){
            return Integer.MAX_VALUE;
        }
        if(divisor==1){
            return dividend;
        }
        boolean isNega=false;
        if(dividend>0){
            isNega=!isNega;
            dividend=-dividend;
        }
        if(divisor>0){
            isNega=!isNega;
            divisor=-divisor;
        }
        return isNega?-divideHelp(dividend,divisor):divideHelp(dividend,divisor);
    }
    
    
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if(s==null||s.length()==0||words==null||words.length==0){
            return res;
        }
        HashMap<String, Integer> map = new HashMap<>();
        int wordLength=words[0].length();
        int nums=words.length;
        int allLen=wordLength*nums;
        for(String word:words){
            map.put(word,map.getOrDefault(word,0)+1);
        }
        for (int i = 0; i < wordLength; i++) {
            int left=i,right=i,count=0;
            HashMap<String, Integer> tempMap = new HashMap<>();
            while (right+wordLength<=s.length()){
                String w=s.substring(right,right+wordLength);
                right+=wordLength;
                if(!map.containsKey(w)){
                    count=0;
                    left=right;
                    tempMap.clear();
                }else {
                    tempMap.put(w,tempMap.getOrDefault(w,0)+1);
                    count++;
                    while (tempMap.getOrDefault(w,0)>map.getOrDefault(w,0)){
                        String tmpW=s.substring(left,left+wordLength);
                        count--;
                        tempMap.put(tmpW,tempMap.getOrDefault(tmpW,0)-1);
                        left+=wordLength;
                    }
                    if(count==nums){
                        res.add(left);
                    }
                }
            }
        }
        return res;
    }

    
    public int lengthOfLIS(int[] nums) {
        int len = 1, n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] d = new int[n + 1];
        d[len] = nums[0];
        for (int i = 1; i < n; ++i) {
            if (nums[i] > d[len]) {
                d[++len] = nums[i];
            } else {
                /**
                 * 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                 */
                int l = 1, r = len, pos = 0;
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (d[mid] < nums[i]) {
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[pos + 1] = nums[i];
            }
        }
        return len;
    }
    
    
    public int findPeakElement(int[] nums) {
        if(nums.length==1){
            return 0;
        }
        int left=0;
        int right= nums.length-1;
        while (left<=right){
            int mid=(left+right)/2;
            int trend=helpFindPeakElement(nums,mid);
            switch (trend){
                case 121:
                    return mid;
                case 123:
                    left=mid+1;
                    break;
                case 321:
                case 212:
                    right=mid-1;
                    break;
                default:
            }
        }
        return 0;
    }
    
    public int helpFindPeakElement(int[] nums,int n){
        if(n==0){
            if(nums[0]<nums[1]){
                return 123;
            }else if(nums[0]>nums[1]){
                return 121;
            }
        }
        if(n== nums.length-1){
            if(nums[n]<nums[n-1]){
                return 321;
            }else if(nums[n]>nums[n-1]){
                return 121;
            }
        }
        if(nums[n]>nums[n-1]&&nums[n]>nums[n+1]){
            return 121;
        }else if(nums[n]>nums[n-1]&&nums[n]<nums[n+1]){
            return 123;
        }else if(nums[n]<nums[n-1]&&nums[n]>nums[n+1]){
            return 321;
        }else {
            return 212;
        }
    }
    
    
    
    
    
    
    public int[] searchRange(int[] nums, int target) {
        
        int[] ans=new int[]{-1,-1};
        if(nums.length==0){
            return ans;
        }
        int right= nums.length-1;
        int left=0;
        while(left<right){
            int mid=(right+left)/2;
            if(nums[mid]==target){
                int leftRange=mid-1;
                int rightRange=mid+1;
                ans[0]=mid;
                ans[1]=mid;
                while(left<=leftRange){
                    mid=(left+leftRange)/2;
                    if(nums[mid]==target){
                        ans[0]=mid;
                        leftRange=mid-1;
                    }else {
                        left=mid+1;
                    }
                }
                while(right>=rightRange){
                    mid=(right+rightRange)/2;
                    if(nums[mid]==target){
                        ans[1]=mid;
                        rightRange=mid+1;
                    }else {
                        right=mid-1;
                    }
                }
                break;
            }else if(nums[mid]<target){
                left=mid+1;
            }else if(nums[mid]>target){
                right=mid-1;
            }
        }
        return ans;
    }
    
    public int search(int[] nums, int target) {
        if(nums.length==0){
            return -1;
        }
        int right= nums.length-1;
        return helpSearch(nums,target,0,right);
    }
    
    public int helpSearch(int[] nums,int target,int left,int right){
        if(left>right){
            return -1;
        }
        if(target==nums[left]){
            return left;
        }
        if(target==nums[right]){
            return right;
        }
        if(left==right){
            return -1;
        }
        if(nums[left]<nums[right]&&(target<nums[left]||target>nums[right])){
            return -1;
        }
        if(nums[left]>nums[right]&&(target<nums[left]&&target>nums[right])){
            return -1;
        }
        int mid=(left+right)/2;
        return Math.max(helpSearch(nums,target,left,mid),helpSearch(nums,target,mid+1,right));
    }
    
    
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
}