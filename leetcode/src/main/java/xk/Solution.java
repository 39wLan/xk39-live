package xk;



import org.junit.Test;

import java.util.*;

class Solution {

    @Test
    public void myTest(){
        String s1="1234123412312341234";
        System.out.println(Arrays.toString(getNext(s1)));
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