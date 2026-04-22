import java.util.*;
import java.lang.*;


class leetcode_84_largest_rect_v2{


    public static void main(String[] args){

        Solution sol = new Solution();
        System.out.println("expected:"+10+"actual:"+sol.largestRectangleArea(new int[]{2,1,5,6,2,3}));
        System.out.println("expected:"+4+"actual:"+sol.largestRectangleArea(new int[]{2,4}));
        System.out.println("expected:"+6+"actual:"+sol.largestRectangleArea(new int[]{2,2,2}));
        System.out.println("expected:"+4+"actual:"+sol.largestRectangleArea(new int[]{2,2,1}));
        System.out.println("expected:"+15+"actual:"+sol.largestRectangleArea(new int[]{1,2,3,0,3,3,4,3,3}));
    }




    /**
     * the questionis to find the largest rectangle
     * we can a rectangle can be more of a vertical rectangle where vertical ractangles compirosed a biggest rectangle
     * or we can say a rectangle can be more of a horizontal rectangle where lot of small rectangles creates a biggest rectangle
     *
     *
     *
     * lets consider a scenario where we have non-decreasing values in array like this 2,3,3,5,6,6 now in this case we can keep on increasing the sliding window
     * lets consider a scneari where we have decreasing number in slding window (smaller than the last element of window) in this case we need to shrink the window because we can not hav
     *
     *
     * letssay we have a invariant that 
     * in a stack if we have three entries x,y,z which represents the index in arrays
     * then value at x < value at y < value at z - we need to remember x extends till y-1 and y extends till z-1 in case we have series of same values we keep the right most one to calculate the aread from top till next smaller value
     *
     * if we counter a z index which has smaller value than y(which is current top) then check below
     *
     * ----main concept of this question is ---- while popping a value we need to check what will e the maximum breadth of rectangle if current value is height of rectangle. <----- 
     *  
     *
     *
     *
     *
     *
     * x<before>y<after>z
     * value at x is smaller than value at y
     * value at y is greater than value at x [which is incorrect)
     *
     * in other words we can use value at y to calculate the area for this index 
     * max area at y = (z - y) * value at y
     *
     * to maintain the invariant we keep on adding index in stack until a smaller value is encountered then currrent top value
     * if a larger or equal value is encountered just add an entery of index in stack, we can just check if 
     * 
     * the other question would be why only considering the toop index value- because we need to find a rectnagle and we are checking if we consider the top as largest value then what will the largest rectangle size.
     * for top we can extend beyond y-1 because all values are less than y and we can extend till z-1 because all values before z are larger than or equal to y as per invariant we decided intially
     *
     * we need to handle smaller value case very carefully
     * we need to keep on calculating the area at top and compare and set the result until we have a smaller value at top than current_index or stack is empty
     * in any of above case insert current_index which is z
     *
     *
     * one thing can happen that we have a monotonous non-decreasing values and we need to now calculate the max area considering each bar as local maxima of graph.
     * so we would in the end of array insert a sentinal value which will start popping the values in stack to calulcta the max area
     *
     *
     *
     *
     **/
    static class Solution{
        public int largestRectangleArea(int[] heights) {
            int result = 0;
            Deque<Integer> stack = new ArrayDeque<Integer>(heights.length);
            for(int i=0;i<=heights.length;i++){
                int value = i==heights.length?Integer.MIN_VALUE:heights[i];
                while(!stack.isEmpty() && heights[stack.peekLast()]>=value){
                    int top = stack.pollLast();
                    if(heights[top] == value){
                        //this happens when same value occurs repeatedly we can skip calculating area
                        continue;
                    }
                    int area = (i-(stack.isEmpty()?(-1):stack.peekLast())-1) * heights[top];
                    result = Math.max(area, result);
                }
                stack.offerLast(i);
          }  
           return result; 
        }
    }

    






}
