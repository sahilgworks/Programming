public class leetcode_33_search_rotated {

    public static void main(String[] args) {
        System.out.println(new Solution().search(new int[]{4, 5, 6, 7, 0, 1, 2}, 4));
        System.out.println(new Solution().search(new int[]{1},1));
        System.out.println(new Solution().search(new int[]{1},0));
        System.out.println(new Solution().search(new int[]{1,3},2));
        System.out.println(new Solution().search(new int[]{1,3},3));
        System.out.println(new Solution().search(new int[]{1,3},1));
        System.out.println(new Solution().pivot(new int[]{7,8,1,2,3,4,5,6}));
        System.out.println(new Solution().search(new int[]{7,8,1,2,3,4,5,6},2));
    }

    static class Solution {
        public int search(int[] nums, int target) {
            if(nums.length==1){
                if(nums[0]==target){
                    return 0;
                }
                return -1;
            }
            int pivot = pivot(nums);
            return search(nums, target, pivot);
        }

        public int search(int[] nums, int target, int pivot){
            //if value lies within [pivot, end]
            int start = pivot;
            int end = nums.length-1;
            int result;
            //now we have two regions of array from 0 to pivot -1 and pivot to len-1
            //we would check element in each region seperatoly.
            if(start>=0 && end<nums.length && start<=end && target>=nums[start] && target<=nums[end]){
                result = bs(nums, nums.length, start, end, target);
                if(result!=-1){
                    return result;
                }
            }
            start = 0;
            end = pivot-1;
            //if value lies within [start, pivot-1]
            if(start>=0 && end<nums.length && start<=end && target>=nums[start] && target<=nums[end]){
                result = bs(nums, nums.length, start, end, target);
                if(result!=-1){
                    return result;
                }
            }
            return -1;
        }

        int bs(int[] nums, int len, int start, int end, int target){
            if(nums[start]==target){
                return start;
            }
            if(nums[end]==target){
                return end;
            }
            if(end==(start+1)){
                return -1;
            }
            int mid =0;
            while(start<=end){
                mid = (start+end)/2;
                if(nums[mid]==target){
                    return mid;
                }
                if(nums[mid]<target){
                    start = mid+1;
                }else{
                    end = mid-1;
                }
            }
            return -1;

        }

        /**
         * pivot is the index of lowest value, in other words at which array[pivot-1]>array[pivot]<array[pivot+1]
         *
         * @param nums
         * @return
         */
        int pivot(int nums[]){
            return pivot(nums, nums.length);
        }

        int pivot(int[] nums, int len) {
            //if zero index is pivot
            if (nums[0] < nums[len - 1]) {
                return 0;
            }
            return pivot(nums, 0, len - 1);
        }

        int pivot(int[] nums, int start, int end) {
            //if start is equal to or greater than end, should return -1
            if(start>=end){
                return -1;
            }
            int mid;
            if((end-start+1) == 2){
                //we can not further break if len is already 2
                //so we check for both first and last element.
                mid = start;
                if (mid != 0 && nums[mid - 1] > nums[mid]) {
                    return mid;
                }
                //we already checked for
                mid = end;
                if (mid != 0 && nums[mid - 1] > nums[mid]) {
                    return mid;
                }
                return -1;
            }
            mid = (start + end) / 2;
            //if start is less than end then pivot can not be in this range
            if (nums[start] < nums[end]) {
                return -1;
            }
            //if previous element is more than the mid, we fount pivot as mid
            if (mid != 0 && nums[mid - 1] > nums[mid]) {
                return mid;
            }
            //we break the array in two portions
            int pL = pivot(nums, start, mid - 1);
            int pR = pivot(nums, mid, end);
            if (pL != -1) {
                return pL;
            } else {
                return pR;
            }
        }
    }
}