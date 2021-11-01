package xk;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolutionSt {
    
    @Test
    public void twoSumTest(){
        int[] nums1 = {20, 55, 2, 1510};
        int target1=57;
        int[] nums2 = {2, 5, 8, 6};
        int target2=10;
        int[] nums3 = {2, 7, 5, 6, 5};
        int target3=10;
        
        System.out.println("twoSum(nums1,target1) = " + twoSum(nums1, target1));
        System.out.println("twoSum(nums2,target2) = " + twoSum(nums2, target2));
        System.out.println("twoSum(nums3,target3) = " + twoSum(nums3, target3));
    }
    
    public List<Integer> twoSum(int[] numbers,int target){
        Long temp=new Long(target);
        ArrayList<Integer> ans = new ArrayList<>();
        if(numbers==null||numbers.length==0){
            return ans;
        }
        HashMap<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if(map.containsKey(temp-numbers[i])){
                ans.add(map.get(temp-numbers[i]));
                ans.add(i);
                return ans;
            }
            map.put(new Long(numbers[i]),i);
        }
        return ans;
    }
}
