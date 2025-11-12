import java.util.ArrayDeque;

public class leetcode_85_largest_rect_histogram {

    public static void main(String[] args) {

        System.out.println(largestRectangleArea(new int[]{1, 0, 1, 0, 0}));
        System.out.println(largestRectangleArea(new int[]{2, 0, 2, 1, 1}));
        System.out.println(largestRectangleArea(new int[]{3, 1, 3, 3, 3}));
        System.out.println(largestRectangleArea(new int[]{4, 0, 0, 4, 0}));
    }

    public static int largestRectangleArea(int[] heights) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        int result = 0;
        int i = 0;
        while (i <= heights.length) {
            int value = 0;
            if (i < heights.length) {
                value = heights[i];
            }
            //keep on popping until queue is empty or lower value is encounterred
            while (!queue.isEmpty() && heights[queue.peekLast()] >= value) {
                int popped = queue.pollLast();
                result = Math.max(result, ((popped - (queue.isEmpty() ? -1 : queue.peekLast())) * heights[popped]));
            }
            queue.offerLast(i);
            i++;
        }
        return result;
    }
}
