package xk;

import org.junit.jupiter.api.Test;

import java.util.*;

class Solution {
    @Test
    public void test(){
        String s1="1234123412312341234";
        System.out.println(Arrays.toString(getNext(s1)));
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