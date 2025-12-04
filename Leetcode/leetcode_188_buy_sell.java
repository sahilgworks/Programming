import static org.junit.Assert.assertEquals;

public class leetcode_188_buy_sell {
    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(2, solution.maxProfit(2, new int[]{2, 4, 1}));
        assertEquals(7, solution.maxProfit(7, new int[]{3, 2, 6, 5, 0, 3}));
    }
    static class SolutionV1 {
        public int maxProfit(int k, int[] prices) {
            return maxProfit(prices, prices.length, 0, k);
        }

        public int maxProfit(int[] prices, int length, int index, int k) {
            //we have two options
            //either we buy at current index and scan upcoming array for

            //we start from left -> current
            //if we ecounter a smaller value -> make current to new smaller value
            //if we encounter a larger value -> get delta from that big - current value ->
            //and we check for remaining array with k-1
            //the other option we had with we continue our search and look for bigger number
            //
            return -1;
        }
    }
}
