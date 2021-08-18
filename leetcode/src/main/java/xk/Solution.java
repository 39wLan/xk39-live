package xk;


import org.junit.jupiter.api.Test;

import java.util.*;

class Solution {
    
    @Test
    public void myTest(){
    
        System.out.println(true?false:true==true?false:true);
    
    }
    
    char[] minMutationChar=new char[]{'A','C','G','T'};
    
    public int minMutation(String start, String end, String[] bank) {
    
    }
    
    public int minMutationHelp(){}
    

    
    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        int ans=0;
        int[][] arr=new int[n][n];
        int[] pair=new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                arr[i][preferences[i][j]]=j;
            }
        }
        for (int[] p:pairs){
            int p0=p[0];
            int p1=p[1];
            pair[p0]=p1;
            pair[p1]=p0;
        }
        for (int x = 0; x < n; x++) {
            int y=pair[x];
            int yArr=arr[x][y];
            for (int i = 0; i < yArr; i++) {
                int u=preferences[x][i];
                int v=pair[u];
                if(arr[u][x]<arr[u][v]){
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }
    
    
    
    boolean[][] pacificAtlantiOne;
    boolean[][] pacificAtlantiTwo;
    int[] pacificAtlanticList0=new int[]{1,-1,0,0};
    int[] pacificAtlanticList1=new int[]{0,0,1,-1};
    public void pacificAtlanticDfs(boolean[][] dp,int[][] heights,int x,int y){
        for (int i = 0; i < 4; i++) {
            int xx=x+pacificAtlanticList0[i];
            int yy=y+pacificAtlanticList1[i];
            if(xx<0||yy<0||xx>=heights.length||yy>=heights[0].length){
                continue;
            }
            if(dp[xx][yy]||heights[xx][yy]<heights[x][y]){
                continue;
            }
            dp[xx][yy]=true;
            pacificAtlanticDfs(dp, heights, xx, yy);
        }
    }
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> ans=new LinkedList<List<Integer>>();
        if(heights==null||heights.length==0||heights[0].length==0){
            return ans;
        }
        int r=heights.length;
        int c=heights[0].length;
        pacificAtlantiOne=new boolean[r][c];
        pacificAtlantiTwo=new boolean[r][c];
        for (int i = 0; i < r; i++) {
            pacificAtlantiOne[i][0]=true;
            pacificAtlantiTwo[i][c-1]=true;
        }
        for (int i = 0; i < c; i++) {
            pacificAtlantiOne[0][i]=true;
            pacificAtlantiTwo[r-1][i]=true;
        }
        for (int i = 0; i < r; i++) {
            pacificAtlanticDfs(pacificAtlantiOne,heights,i,0);
            pacificAtlanticDfs(pacificAtlantiTwo,heights,i,c-1);
        }
        for (int i = 0; i < c; i++) {
            pacificAtlanticDfs(pacificAtlantiOne,heights,0,i);
            pacificAtlanticDfs(pacificAtlantiTwo,heights,r-1,i);
        }
    
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if(pacificAtlantiOne[i][j]&&pacificAtlantiTwo[i][j]){
                    ans.add(Arrays.asList(i,j));
                }
            }
        }
        return ans;
    }
    
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int equationsSize=equations.size();
        UnionFind unionFind = new UnionFind(2 * equationsSize);
        Map<String, Integer> hashMap = new HashMap<>();
        int id=0;
        for (int i = 0; i < equationsSize; i++) {
            List<String> equation = equations.get(i);
            String var1 = equation.get(0);
            String var2 = equation.get(1);
            
            if(!hashMap.containsKey(var1)){
                hashMap.put(var1,id);
                id++;
            }
            if(!hashMap.containsKey(var2)){
                hashMap.put(var2,id);
                id++;
            }
            unionFind.union(hashMap.get(var1),hashMap.get(var2),values[i]);
        }
    
        int queriesSize = queries.size();
        double[] res = new double[queriesSize];
        for (int i = 0; i < queriesSize; i++) {
            String var1 = queries.get(i).get(0);
            String var2 = queries.get(i).get(1);
    
            Integer id1 = hashMap.get(var1);
            Integer id2 = hashMap.get(var2);
            
            if(id1==null||id2==null){
                res[i]=-1.0d;
            }else {
                res[i]=unionFind.isConnected(id1,id2);
            }
        }
        return res;
    }
    
    public class UnionFind{
        
        public int[] parent;
        public double[] weight;
        
        public UnionFind(int n){
            this.parent=new int[n];
            this.weight=new double[n];
            for (int i = 0; i < n; i++) {
                parent[i]=i;
                weight[i]=1.0d;
            }
        }
        
        public void union(int x,int y,double value){
            int rootX=find(x);
            int rootY=find(y);
            if(rootX==rootY){
                return;
            }
            parent[rootX]=rootY;
            weight[rootX]=weight[y]*value/weight[x];
        }
        
        public int find(int x){
            if(x!=parent[x]){
                int origin=parent[x];
                parent[x]=find(parent[x]);
                weight[x]*=weight[origin];
            }
            return parent[x];
        }
        
        public double isConnected(int x,int y){
            int rootX=find(x);
            int rootY=find(y);
            if(rootX==rootY){
                return weight[x]/weight[y];
            }else {
                return -1.0d;
            }
        }
        
    }
    
    Map<String, PriorityQueue<String>> findItineraryMap = new HashMap<>();
    List<String> findItineraryRes =new LinkedList<String>();
    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> tic:tickets){
            String from= tic.get(0);
            String to= tic.get(1);
            if(!findItineraryMap.containsKey(from)){
                findItineraryMap.put(from,new PriorityQueue<String>());
            }
            findItineraryMap.get(from).offer(to);
        }
        findItineraryHelp("JFK");
        Collections.reverse(findItineraryRes);
        return findItineraryRes;
    }
    public void findItineraryHelp(String from){
        while (findItineraryMap.containsKey(from)&& findItineraryMap.get(from).size()>0){
            String to= findItineraryMap.get(from).poll();
            findItineraryHelp(to);
        }
        findItineraryRes.add(from);
    }
    
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> res=new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            char tmp=expression.charAt(i);
            if(tmp=='+'||tmp=='-'||tmp=='*'){
                List<Integer> left=diffWaysToCompute(expression.substring(0,i));
                List<Integer> right=diffWaysToCompute(expression.substring(i+1));
                for (int l: left) {
                    for (int r: right) {
                        if(tmp=='+'){
                            res.add(l+r);
                        }else if(tmp=='-'){
                            res.add(l-r);
                        }else if(tmp=='*'){
                            res.add(l*r);
                        }
                    }
                }
            }
        }
        if(res.size()==0){
            res.add(Integer.valueOf(expression));
            return res;
        }
        return res;
    }
    
    class NumArray {
        
        int[] tree;
        int n;
        public NumArray(int[] nums) {
            if(nums.length>0){
                n=nums.length;
                tree=new int[n*2];
                buildTree(nums);
            }
        }
        
        public void buildTree(int[] nums){
            for (int i = n,j=0; i < 2*n; i++,j++) {
                tree[i]=nums[j];
            }
            for(int i=n-1;i>0;i--){
                tree[i]=tree[i*2]+tree[i*2+1];
            }
        }
        
        public void update(int index, int val) {
            index+=n;
            tree[index]=val;
            while(index>0){
                int left=index;
                int right=index;
                if((index&1)==0){
                    right=index+1;
                }else {
                    left=index-1;
                }
                tree[index/2]=tree[left]+tree[right];
                index>>=1;
            }
        }
        
        public int sumRange(int left, int right) {
            left+=n;
            right+=n;
            int sum=0;
            while(left<=right){
                if((left&1)==1){
                    sum+=tree[left];
                    left++;
                }
                if((right&1)==0){
                    sum+=tree[right];
                    right--;
                }
                left>>=1;
                right>>=1;
            }
            return sum;
        }
    }

    
    
    
    /**
     * 核心: Floyd判圈算法,前进的同时更新起点star
     *      fast==slow时便确认进入圈中，判断圈是否合规即可
     *
     * f1():返回下一个到达的坐标
     * flag:
     *      true:当前循环暂时符合条件;
     *      false:当前循环不符合条件
     * star: 空间O(1)的关键，下一次搜寻的起点坐标
     *      if(slow==star+1||fast==star+1) star++;
     * left: 剪枝用的
     */
    public boolean circularArrayLoop(int[] nums) {
        if(nums.length<2||(nums.length==2&&nums[0]*nums[1]<0)){
            return false;
        }
        int star=0;
        int k=nums.length;
        while (star<k){
            int left=star;
            int slow=star,fast=star;
            star++;
            boolean flag=true;
            do{
                slow=f1(slow,nums[slow],k);
                star+=slow==star?1:0;
                fast=f1(fast,nums[fast],k);
                star+=fast==star?1:0;
                fast=f1(fast,nums[fast],k);
                star+=fast==star?1:0;
                if(slow<left||fast<left){
                    flag=false;
                    break;
                }
            }while (slow!=fast);
            if(!flag){
                continue;
            }
            int t=slow;
            fast=f1(slow,nums[slow],k);
            if(t==fast){
                continue;
            }
            do {
                fast=f1(slow,nums[slow],k);
                if(nums[slow]*nums[fast]<0){
                    flag=false;
                    break;
                }
                slow=fast;
            } while (t!=slow);
            if(flag){
                return true;
            }
        }
        return false;
    }
    
    public int f1(int index,int num,int k){
        index=(index+num%k+k)%k;
        return index;
    }
    
    public int findDuplicate(int[] nums) {
        int slow=0,fast=0;
        do{
            slow=nums[slow];
            fast=nums[nums[fast]];
        }while (slow!=fast);
        slow=0;
        while (slow!=fast){
            slow=nums[slow];
            fast=nums[fast];
        }
        return slow;
    }
    
    public int triangleNumber(int[] nums) {
        if(nums.length<3){
            return 0;
        }
        int ans=0;
        Arrays.sort(nums);
        int n=nums.length;
        for (int i = 1; i < n-1; i++) {
            int z=i+1;
            for (int j = 0; j < i; j++) {
                while (z<n&&nums[i]+nums[j]>nums[z]){
                    z++;
                }
                ans+=z-i-1;
            }
        }
        return ans;
    }
    
    public int subarraySum(int[] nums, int k) {
        int ans=0;
        int sum=0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        for(int num:nums){
            sum+=num;
            ans+=map.getOrDefault(sum-k,0);
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
        return ans;
    }
    
    public int findMaxLength(int[] nums) {
        int ans=0;
        int n=nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum=0;
        map.put(sum,-1);
        for (int i = 0; i < n; i++) {
            if(nums[i]==0){
                sum--;
            }else {
                sum++;
            }
            if(map.containsKey(sum)){
                ans=Math.max(ans,i- map.get(sum));
            }else {
                map.put(sum,i);
            }
        }
        return ans;
    }
    
    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums.length<2){
            return false;
        }
        int n=nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,-1);
        int sum=0;
        for (int i = 0; i < n; i++) {
            sum=(sum+nums[i])%k;
            if(map.containsKey(sum)){
                if(i-map.get(sum)>1){
                    return true;
                }
            }else {
                map.put(sum,i);
            }
        }
        return false;
    }
    
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n=nums.length;
        Map<Long,Long> map=new HashMap<>();
        long w=(long)t+1;
        for(int i=0;i<n;i++){
            long id=containsNearbyAlmostDuplicateHelper(nums[i],w);
            if(map.containsKey(id)){
                return true;
            }
            if(map.containsKey(id-1)&&Math.abs(nums[i]- map.get(id-1))<w){
                return true;
            }
            if(map.containsKey(id+1)&&Math.abs(nums[i]- map.get(id+1))<w){
                return true;
            }
            map.put(id,(long)nums[i]);
            if(i>=k){
                map.remove(containsNearbyAlmostDuplicateHelper(nums[i-k],w));
            }
        }
        return false;
        
    }
    
    public long containsNearbyAlmostDuplicateHelper(long x,long w){
        if(x>=0){
            return x/w;
        }
        return (x+1)/w-1;
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