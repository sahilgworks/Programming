import java.lang.*;
import java.util.*;

public class leetcode_327_count_range_sum{

    public static void main(String[] args){

        int[] nums;
        int lower;
        int upper;
        int expected;


        SolutionV2 sol = new SolutionV2();

        nums = new int[]{-2, 5,-1};
        lower = -2;
        upper = 2;
        expected = 3;
        System.out.println("expected:  "+expected+", actual:"+sol.countRangeSum(nums, lower, upper));

        nums = new int[]{0};
        lower = 0;
        upper = 0;
        expected = 1;
        System.out.println("expected:  "+expected+", actual:"+sol.countRangeSum(nums, lower, upper));

    }


    static class SolutionV1{
        /**
         * My intitial idea is to create a tree map for this
         *
         * because tree map supports range queries and return a submap with given range.
         * then we can calsulate sum of all values from returned map and we will have answer for that index
         *
         * then we need to add current index value in all keys using replace all method of tree map so that range
         * values are updated. 
         *
         * this solution would take o(N) memory and O(N^2) time because for each index we need to update all values in tree map
         * my solution is taking O(n2) time which is too slow
         *
         *
         * i was not able to identify a better solution so i took help of CHATgpt
         *
         *
         * in original approach we are updating the complete hashmap which takes O(n) time for each index
         * the correct solution is to update query range instead of always quering for lower and upper
         *
         * lets say i is current index and prefix[i] = sum of 0 till i
         * now lets say there exist an index j where j<i and prefix[j] = sum of 0 till j
         * for window j+1 to i sum is prefix[i] - prefix[j]
         * we need to find j index such that
         * prefix[i]-prefix[j] = [lower, upper] -> we can simplify j+1 to j
         * => prefix[j] = [prefix[i]- lower, prefix[i]-upper]
         * => prefix[j] = [prefix[i] - upper, prefix[i]-lower] -> switched places of lower and upper because subtracing upper higher value will give lower bound
         * so we need to find all j where value lies between these ranges
         * also we also need to insert a default value for 0 as 1 so that if prefix[0...i] has a valid value between lower and upper than subracting upper and lower will always give value between prefix[i] - upper and prefix[i] - lower and prefix[i] is <lower, upper> lets say it is equal to upper
         *
         * it will vary between 0 and upper-lower [which is always greate than 1] 
         *
         * if it is lower
         *
         * i will vary between lower-upper[which is less than 1] and 0 
         * 
         * so if current prefix is a valid answer without depending on a j index we should have a 0 as 1 so that we count self as valid answer.
         * 
         * once we have counted available number of prefixes we can insert current prefix
         * 
         *
         * this solution works but the problem is we have a worst case time of O(N^2)
         * 
         * on deep diving and taking help of chatGPT we can use merge sort to find total sum of ranges.
         *
         *
         *
         *
         **/

        public int countRangeSum(int[] nums, int lower, int upper) {
            //int sum = 0;//using int as sum is a vry big mistake since question clearly states that inteeger ranges are 2^31 to 2^31
            //so it can lead to integer overflow so we should use long
            long sum=0;
            TreeMap<Long, Integer> tMap = new TreeMap<>();//similarrly we need t use long as key to prevent integer overflow. 
            int result=0;
            tMap.put(0L,1);
            for(int i=0;i<nums.length;i++){
                sum+=nums[i];
                Map<Long,Integer> map =  tMap.subMap(sum-upper, true, sum-lower, true);
                for(Map.Entry<Long,Integer> entry:map.entrySet()){
                    result+=entry.getValue()==null?0:entry.getValue();
                }
                tMap.put(sum, tMap.getOrDefault(sum, 0)+1);
            }
            return result;
        }

    }


    public static class SolutionV2{
        /**
         * SolutionV1 used tree map which resulted in O(n^2) upper bound time
         * but we can mke this O(nlogn) using merge sort. 
         *
         * using merge sort for this problem is not straight forward
         *
         *
         * lets see how can we do this
         *
         *
         * lets say we have a prefix array of length nums.length+1
         *
         * at zero index we will insert 0 to help us compare whole prefix from 0 to i
         *
         * now for each index calculate prefix and set in prefix array
         *
         * this array would not be sorted since we can have negative numbers as well.
         *
         * what we need to do is split the array in two halves - > left and right
         * 
         * there are three types of pairs in a split
         *
         * 1. a pair in left section
         * 2. a pair in right section
         * 3. a pair across left and right
         *
         * using merge sort we wil keep on dividing each split and calculate above 3 types of pairs post a split and merge the split and return
         *
         *
         * now how does this work
         *
         * lets say we have a pair i, j where j<i
         * and j exist in left because its a unsorted left split
         * and i exist in right because its a unsorted right split
         *
         * so if j<i
         *
         * and prefix[i]-profix[j] = [lower, upper]
         * then prefix[i] = lower+prefix[j] , upper+prefix[j]
         *
         * this means we need to find all prefix[i] in right split which satisfies this condition
         *
         * one thing to remeber here is while naviagting the left split to find number of pairs in left split
         * we would keep on increasing value of prefix[j] and which means requirement of lower+prefix[j] and upper+prefix[j] will also increase
         * which meaans we can increase the range of values on right side
         *
         * so what we can do is we can have a two pointer approach. left pointer for lower and right pointer for upper. since we are adding prefix[j] in both lower and upper
         * both left poiter and right pointer needs to be shifted
         *
         * we will move left pointer until it gte to prefix[j]+lower
         * and we will move right pointer untill is gte to prefix[j] + upper
         *
         * so for j we have right-left+1 number of pairs 
         * Note: before checking cross pairs both left and right split should be sorted
         *
         * once cross are checked we merge-sort the two halves of array
         *
         * now comes the question of self checking in left and right
         *
         * for this we will follow divide and merge
         *
         * we will divide until just 1 or just 2 are left
         *
         * if one is left , it can not be a pair just return
         * if two is left, if prefix[i]-prefix[j] = [lower, upper] return 1 else 0 and swap values if requied so that this two element is sorted array
         *
         * and we will keep on  dividing the array and merge sort the arrays until we have total number of pairs.
         *
         *
         * now comes the stage of time complexity of this solution
         *
        * merge sort takes O(nlogn) time.
            * but we are doing more than merge sort
            *
            * merge sort has a tree structore logn height and each level takes o(n) time so total time complexity is o(nlogn)
            * our additional step of cross check takes O(n) times because we use a sliding window or two pointer mechanism which uses the previous pointers and takes O(n) at each stage
            * so total time is O( (N+2N)*logn) 2N because each index of array is touched twice once for left pointer and once for right pointer so 2N
            * this leads to O(NlogN) time
            *
            *
            * following is ChatGPT version of my analysis:
            *
            *1. Build prefix array
            *
            *
            *2. Recursively:
            *   - count pairs inside LEFT
            *   - count pairs inside RIGHT
            *
            *3. Count cross pairs:
            *
            *   for each j in LEFT:
            *
            *       find i in RIGHT such that:
            *
            *           prefix[i] - prefix[j] ∈ [lower, upper]
            *
            *       ⇒ prefix[i] ∈ [prefix[j] + lower, prefix[j] + upper]
            *
            *4. Since RIGHT is sorted:
            *
            *       valid i form a contiguous range
            *
            *5. Use two pointers:
            *
            **       low  → first i such that prefix[i] - prefix[j] ≥ lower
            *       high → first i such that prefix[i] - prefix[j] > upper
            *
            *6. Count for this j:
            *
            *       count += (high - low)
            *
            *7. After counting, merge LEFT and RIGHT to keep sorted order
            *
            **/
            public int countRangeSum(int[] nums, int lower, int upper) {
                int[] prefix = new int[nums.length+1];
                prefix[0] = 0;
                for(int i=0;i<nums.length;i++){
                    prefix[i+1] = prefix[i]+nums[i];
                }
                return mergesort(prefix, lower, upper, 0, nums.length);
            }


        int mergesort(int[] prefix, int lower, int upper, int start, int end){
            int len = end-start+1;
            if(len <= 1){
                return 0;
            }else{
                int mid = (end-start)/2;
                int left = mergesort(prefix, lower, upper, start, mid);
                int right = mergesort(prefix, lower, upper, mid+1, end);
                int cross = cross(prefix, lower, upper, start, mid, end);
                merge(prefix, start, mid, end);
                return left+right+cross;
            }
        }

        void merge(int[] prefix, int start, int mid, int end){
            int[] left = Arrays.copyOfRange(prefix, start, mid+1);
            int[] right = Arrays.copyOfRange(prefix, mid+1, end+1);
            int i=0;
            int j=0;
            int k=start;
            while(i<left.length && j<right.length){
                if(left[i]<right[j]){
                    prefix[k++] = left[i++];
                }else{
                    prefix[k++] = right[j++];
                }
            }
            if(i<left.length){
                while(i<left.length){
                    prefix[k++] = left[i++];
                }
            }else{
                while(j<right.length){
                    prefix[k++] = left[j++];
                }
            }
        }
        
        int cross(int[] prefix, int lower, int upper, int start, int mid, int end){
            int low = mid+1;
            int high = mid+1;
            int result = 0;
            int i;
            for(int j=start;j<mid+1;j++){
                i = low;
                while(i<=end && prefix[i]<(lower+prefix[j])){
                    i++;
                }
                if(i > end){
                    continue;//no range found
                }
                low = i;
                i = high;
                while(i<=end && prefix[i]<=(upper+prefix[j])){
                    i++;
                }
                high = i;
                result += high - low;
            }
            return result;
        }

    }
}

