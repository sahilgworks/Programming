import static org.junit.Assert.assertEquals;

public class leetcode_123_buy_sell {
    public static void main(String[] args) {
        SolutionV2 solution = new SolutionV2();
        assertEquals(6, solution.maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        assertEquals(4, solution.maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, solution.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    //this solution is the fastest one uses just two for loops instead of one (SolutionV1)
    //this solution takes 2ms
    static class SolutionV2 {
        public int maxProfit(int[] prices) {
            //take max from left to right
            //take max from right to left
            //scroll from index 0 to len - 1
            //add maxlr + maxlr and compare with result
            int result = 0;
            int finalResult = 0;
            int len = prices.length;
            int[] array = new int[len];
            array[0] = 0;
            int temp = prices[0];
            result = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] < temp) {
                    temp = prices[i];
                } else {
                    result = Math.max(result, prices[i] - temp);
                }
                array[i] = result;
            }
            finalResult = result;
            result = 0;
            temp = Integer.MIN_VALUE;
            for (int i = len - 1; i > 0; i--) {
                if (prices[i] > temp) {
                    temp = prices[i];
                } else {
                    result = Math.max(result, temp - prices[i]);
                }
                finalResult = Math.max(finalResult, array[i-1] + result);
            }
            return finalResult;
        }
    }


    //this is a second best solution takes 3 ms but we are tryng to make it to 2ms in v2 solution
    static class SolutionV1 {
        public int maxProfit(int[] prices) {
            //take max from left to right
            //take max from right to left
            //scroll from index 0 to len - 1
            //add maxlr + maxlr and compare with result
            int result = 0;
            int len = prices.length;
            int[] maxLR = new int[len];
            maxLR[0] = 0;
            int temp = prices[0];
            result = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] < temp) {
                    temp = prices[i];
                } else {
                    result = Math.max(result, prices[i] - temp);
                }
                maxLR[i] = result;
            }
            int[] maxRL = new int[len];
            temp = prices[len - 1];
            result = 0;
            maxRL[len - 1] = 0;
            for (int i = len - 1; i >= 0; i--) {
                if (prices[i] > temp) {
                    temp = prices[i];
                } else {
                    result = Math.max(result, temp - prices[i]);
                }
                maxRL[i] = result;
            }
            for (int i = 0; i < len - 2; i++) {
                result = Math.max(result, maxLR[i] + maxRL[i + 1]);
            }
            return result;
        }
    }
}
