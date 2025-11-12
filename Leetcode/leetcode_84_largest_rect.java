import java.util.ArrayDeque;

public class leetcode_84_largest_rect {

    public static void main(String[] args) {
        System.out.println(maximalRectangle(
                new char[][]
                        {
                                {'1', '0', '1', '0', '0'},
                                {'1', '0', '1', '1', '1'},
                                {'1', '1', '1', '1', '1'},
                                {'1', '0', '0', '1', '0'}
                        }
        ));

    }

    public static int maximalRectangle(char[][] matrix) {
        int result = 0;
        int[] histogram = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '0') {
                    histogram[j] = 0;
                } else {
                    histogram[j]++;
                }
            }
            result = Math.max(result, largestRectangleArea(histogram));
        }
        return result;
    }

    public static int largestRectangleArea(int[] heights) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        int result = 0;
        int i = 0;
        int popped = 0;
        int value = 0;
        while (i <= heights.length) {
            value = 0;
            if (i < heights.length) {
                value = heights[i];
            }
            //keep on popping until queue is empty or lower value is encounterred
            while (!queue.isEmpty() && (heights[queue.peekLast()]) >= value) {
                popped = queue.pollLast();
                result = Math.max(result, (((i - (queue.isEmpty() ? -1 : queue.peekLast())) - 1) * heights[popped]));
            }
            queue.offerLast(i);
            i++;
        }
        return result;
    }
}
