import static org.junit.Assert.assertEquals;

public class leetcode_11_max_water {

    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(49, solution.maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
        assertEquals(1, solution.maxArea(new int[]{1,1}));
    }

    static class SolutionV1 {
        public int maxArea(int[] height) {
            int i = 0;
            int j = height.length-1;
            int area = 0;
            while(i<j){
                area = Math.max(area, area(height, i, j));
                if(height[i]<height[j]){
                    i++;
                }else{
                    j--;
                }
            }
            return area;
        }

        int area(int[] array, int i, int j){
            return (j-i)*Math.min(array[i], array[j]);
        }
    }
}
