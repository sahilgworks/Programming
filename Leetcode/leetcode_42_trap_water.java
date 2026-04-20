import static org.junit.Assert.assertEquals;

public class leetcode_42_trap_water {

    public static void main(String[] args) {
        SolutionV3 solution = new SolutionV3();
        assertEquals(6, solution.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        assertEquals(9, solution.trap(new int[]{4, 2, 0, 3, 2, 5}));
    }

    /**
     * Space - O(n)
     * Time - O(n)
     */
    static class SolutionV1 {
        public int trap(int[] height) {
            //question is how much water we can fill on top of each bar.
            //each bar is bounded by series of  bars from both side.
            //lets say max of left series of bars is called maxL
            //lets say max of right series of bars is called maxR
            //and current height of bar is hBar
            //for each bar we can fill max water capped by min of maxL and maxR, lets call this maxLR
            //because if exceed min of maxL and maxR, water will fall from min side.
            //we have to ensure water does not fall from sides of bar.
            //also if current height which is hBar is greater than maxLR
            //we can not fill any more water
            //so the max water we can fill at current bar is maxLR - hBar.

            //To optimize above algo, we can see we need just need min of maxL and maxR.
            //we can have a singe array - maxArray of lets say max.
            //we will fill it from left side.
            //now we will fill this array from right side, and instead of setting values
            //we would take min of current value in cell which points to maxL and
            //and new calculated value which points to maxR.

            //now we can safely calculate max water per cell, as maxLR which is the value in current cell of maxArray
            //calculate diff as maxArray[index]-hBar
            //if diff<=0 continue to next indexx
            //else sum = sum+diff and coninue to next index
            //after all indexes return sum.
            int len = height.length;
            int[] maxLR = new int[len];
            int max = 0;
            int hBar = 0;
            for (int i = 0; i < len; i++) {
                hBar = height[i];
                maxLR[i] = max;
                max = Math.max(max, hBar);
            }
            max = 0;
            for (int i = len - 1; i >= 0; i--) {
                hBar = height[i];
                maxLR[i] = Math.min(max, maxLR[i]);
                max = Math.max(max, hBar);
            }
            int sum = 0;
            int diff = 0;
            for (int i = 0; i < len; i++) {
                hBar = height[i];
                diff = maxLR[i] - hBar;
                if (diff <= 0) {
                    continue;
                }
                sum += diff;
            }
            return sum;
        }
    }

    /**
     * Space - O(1)
     * Time - O(n^2)
     */
    static class SolutionV2 {
        public int trap(int[] height) {
            //this optimised solution would use constant space
            //idea is we would start scanning array from right side.
            //and before this we would have two things with us.
            //left-max and left-max-idx - we need to make sure we have extreme left of max element.
            //for extreme right element we would have sufficient knowledge of max from left and max from right(which is 0)
            //and now decrement counter to len-2. but doing this we have two critical operations
            //use element at len-1 for calculating right-max
            //And also check len-2 was left-max-idx
            //if yes there can not be a element on left side which is larger than right side
            //because we have given the maximum element to right side since we have ensured in the begining that we always keep
            //extreme left of max value
            //if we did not have done this, we would need to scan the array again to check if we have another element with same value
            //so that max remains same on left after this extreme left, there is no reason to re-scan the array until we have consumed the extreme left max
            //Once we have consumed the extreme left max, we will re-scan the array to get new extreme left maximum and cycle continues


            int len = height.length;
            int maxL = 0;
            int maxLIdx = -1;
            int maxR = 0;
            int[] leftScan = new int[2];
            int result = 0;
            int diff = 0;
            int hBar = 0;

            //first scan
            scanForMax(height, 0, len-1, leftScan); //skip last element because we need max from left series
            maxLIdx = leftScan[0];
            maxL = leftScan[1];
            for(int i=len-1;i>0;i--){
                hBar = height[i];
                diff = Math.max(Math.min(maxL, maxR)-hBar, 0);
                result+=diff;
                if(hBar>maxR){
                    maxR = hBar;
                }
                if((i-1) == maxLIdx){
                    scanForMax(height, 0, i-1, leftScan);
                    maxLIdx = leftScan[0];
                    maxL = leftScan[1];
                }
            }
            return result;
        }

        void scanForMax(int[] array, int start, int end, int[] output){
            int max = -1;
            int idx = -1;
            for(int i = start;i<end;i++){
                if(array[i]>max){
                    max = array[i];
                    idx = i;
                }
            }
            output[0] = idx;
            output[1] = max;
        }
    }


    /**
     * Space - O(1)
     * Time - O(n^2)
     */
    static class SolutionV3 {
        public int trap(int[] height) {

            int len = height.length;
            int maxL = 0;
            int maxLIdx = -1;
            int maxR = 0;
            int[] leftScan = new int[2];
            int result = 0;
            int diff = 0;
            int hBar = 0;

            //first scan
            scanForMax(height, 0, len-1, leftScan); //skip last element because we need max from left series
            maxLIdx = leftScan[0];
            maxL = leftScan[1];
            int i=0;
            for(i=len-1;i>0;i--){
                hBar = height[i];
                diff = Math.max(Math.min(maxL, maxR)-hBar, 0);
                result+=diff;
                if(hBar>maxR){
                    maxR = hBar;
                }
                if((i-1) == maxLIdx){
                    break;
                }
            }
            maxL = 0;
            if(i!=0){
                for(int j=0;j<i;j++){
                    hBar = height[j];
                    diff = Math.max(maxL - hBar, 0);
                    result+=diff;
                    maxL = Math.max(maxL, hBar);
                }
            }
            return result;
        }

        void scanForMax(int[] array, int start, int end, int[] output){
            int max = -1;
            int idx = -1;
            for(int i = start;i<end;i++){
                if(array[i]>max){
                    max = array[i];
                    idx = i;
                }
            }
            output[0] = idx;
            output[1] = max;
        }
    }
}
