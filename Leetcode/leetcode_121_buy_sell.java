import static org.junit.Assert.assertEquals;

public class leetcode_121_buy_sell {

    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(5, solution.maxProfit(new int[]{7,1,5,3,6,4}));
        assertEquals(0, solution.maxProfit(new int[]{7,6,4,3,1}));
    }

    static class SolutionV1 {
        public int maxProfit(int[] prices) {
            int min = 0;
            int result = 0;
            for(int i=1;i<prices.length;i++){
                if(prices[min]>prices[i]){
                    //we are looking for the min, because it will incease the chances of finding greater delta
                    //in next days
                    min = i;
                }else{
                    //also we also keep track of delta between current value and last recorded minimum
                    result = Math.max(result, prices[i]-prices[min]);
                }
            }
            return result;
        }
    }
}
