import static org.junit.Assert.assertEquals;

public class leetcode_122_buy_sell {

    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(7, solution.maxProfit(new int[]{7,1,5,3,6,4}));
        assertEquals(0, solution.maxProfit(new int[]{7,6,4,3,1}));
        assertEquals(4, solution.maxProfit(new int[]{1,2,3,4,5}));
    }
    static class SolutionV1 {
        public int maxProfit(int[] prices) {
            int min = 0;
            int result = 0;
            for(int i=1;i<prices.length;i++){
                //we plan to book profit each day if a higher value from the last minimum is observed
                //we would also buy it since we would like to book profits again
                //and it is allowed to buy and sell same day multiple times
                //so we would buy and sell and (buy on same day)
                //when we buy on same day, we update min to mark it as current buy value
                //also we would add the result to existing result
                //by taking an absolute of current price and minimum
                //in both cases where current value is greater than or less than min, we would update min
                result += Math.max(0, prices[i]-prices[min]);
                min = i;
            }
            return result;
        }
    }
}
