
import java.util.Arrays;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

class Driver {

    public static void main(String[] args) {
        new Driver().run();
    }

    void run() {
        int testCase;
        testCase = 12;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 13;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 1;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 4;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 25;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 100;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 134;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 109;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 200;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 10000;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
        testCase = 909;
        System.out.println("sol for " + testCase + ":" + (new Solution().numSquares(testCase)));
    }

    class Solution {

        int NOT_INIT = -1;
        int NOT_POSSIBLE = Integer.MAX_VALUE;

        public int numSquares(int n) {
            int[] memory = new int[10001];
            TreeMap<Integer, Integer> squares = new TreeMap<>();
            Arrays.fill(memory, NOT_INIT);
            int num = 1;
            int sq = num * num;
            while (sq <= n) {
                squares.put(sq, sq);
                num++;
                sq = num * num;
            }
            //we need to calculate all the perfect sqaures below n
            //and also store them in a collection 
            //also we need to query them, like all sqaures less than a given number

            return numSquares(n, memory, squares);
        }

        private int numSquares(int n, int[] memory, TreeMap<Integer, Integer> squares) {
            boolean printLogs = false;
            if (printLogs) {
                System.out.println("n:" + n + " inside numSquares");
            }
            if (n == 0) {
                if (printLogs) {
                    System.out.println("n:" + n + " returning 0: " + n);
                }
                return 0;
            }
            if (n < 0) {
                throw new IllegalArgumentException("can not be less than equal to 0");
            }
            if (memory[n] != NOT_INIT) {
                return memory[n];
            }
            int minOps = NOT_POSSIBLE;
            Integer lastUsedNum = 0;
            NavigableMap<Integer, Integer> map = squares.headMap(n, true);
            Iterator<Integer> iterator = map.descendingKeySet().iterator();
            while (iterator.hasNext()) {
                lastUsedNum = iterator.next();
                if (printLogs) {
                    System.out.println("n:" + n + " lastUsedNum: " + lastUsedNum);
                }
                int temp = numSquares(n - lastUsedNum, memory, squares);
                if (temp != NOT_POSSIBLE) {
                    minOps = Math.min(minOps, 1 + temp);
                    // memory[n] = minOps;
                    // return minOps;
                }
                if (printLogs) {
                    System.out.println("n:" + n + " lastUsedNum: " + lastUsedNum + " temp:" + temp + " minOps:" + minOps);
                }
            }
            memory[n] = minOps;
            return minOps;
        }
    }
}
