/**
 * LeetCode 221 - Maximal Square
 *
 * <p>This class provides two implementations for finding the area of the largest square
 * containing only '1's in a given binary matrix.
 *
 * <p>Both solutions use dynamic programming (DP), but differ in their space usage:
 * <ul>
 *   <li>{@link #maximalSquare1(char[][])} uses a full 2D DP array (O(m × n) space).</li>
 *   <li>{@link #maximalSquare2(char[][])} optimizes space to O(n) using a rolling 1D DP array.</li>
 * </ul>
 *
 * <p>Problem reference: <a href="https://leetcode.com/problems/maximal-square/">LeetCode 221</a>
 *
 * <p><b>Author:</b> Sahil Gupta
 */
public class leetcode_221_max_square_area {

    public static void main(String[] args) {
        // Sample binary matrix
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };

        leetcode_221_max_square_area solver = new leetcode_221_max_square_area();

        int result1 = solver.maximalSquare1(matrix);
        int result2 = solver.maximalSquare2(matrix);

        System.out.println("Input matrix:");
        for (char[] row : matrix) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        System.out.println("\n--- Results ---");
        System.out.println("Maximal Square Area (Solution 1 - 2D DP): " + result1);
        System.out.println("Maximal Square Area (Solution 2 - Optimized 1D DP): " + result2);
    }

    /**
     * Solution 2 - Space-Optimized Dynamic Programming
     *
     * <p>Improvement over {@link #maximalSquare1(char[][])}. Instead of maintaining a full 2D DP table,
     * this method uses a single 1D array to store intermediate DP values for the current and previous rows.
     *
     * <p>At each cell (i, j):
     * <ul>
     *   <li>If matrix[i][j] == '1', the size of the square ending at (i, j) is
     *       1 + min(left, top, top-left).</li>
     *   <li>Otherwise, it's 0.</li>
     * </ul>
     *
     * <p>Time Complexity: O(m × n)
     * Space Complexity: O(n)
     *
     * @param matrix the binary matrix consisting of '0's and '1's
     * @return the area of the largest square consisting entirely of '1's
     * @implNote This version reuses the same DP array for each row, reducing space usage from O(m × n) to O(n).
     */
    public int maximalSquare2(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int result = 0;
        int[] array = new int[col + 1]; // DP array for current row
        int tmp = 0;  // Temporarily stores array[j] before updating
        int prev = 0; // Stores DP value from previous row, previous column

        for (int j = 0; j <= col; j++) {
            array[j] = 0;
        }

        for (int i = 1; i <= row; i++) {
            prev = 0;
            for (int j = 1; j <= col; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    tmp = array[j];
                    array[j] = Math.min(Math.min(array[j - 1], array[j]), prev) + 1;
                    prev = tmp;
                } else {
                    prev = array[j];
                    array[j] = 0;
                }
                result = Math.max(result, array[j]);
            }
        }
        return result * result;
    }

    /**
     * Solution 1 - Standard Dynamic Programming
     *
     * <p>This is the straightforward DP approach that uses a 2D table to track the
     * largest square ending at each cell (i, j). For each cell:
     * <ul>
     *   <li>If matrix[i][j] == '1', DP[i][j] = 1 + min(top, left, top-left).</li>
     *   <li>Otherwise, DP[i][j] = 0.</li>
     * </ul>
     *
     * <p>The largest DP value found represents the side length of the biggest square.
     *
     * <p>Time Complexity: O(m × n)  
     * Space Complexity: O(m × n)
     *
     * @param matrix the binary matrix consisting of '0's and '1's
     * @return the area of the largest square consisting entirely of '1's
     */
    public int maximalSquare1(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int result = 0;
        int[][] rMatrix = new int[row + 1][col + 1];

        for (int j = 0; j <= col; j++) {
            rMatrix[0][j] = 0;
        }
        for (int i = 0; i <= row; i++) {
            rMatrix[i][0] = 0;
        }

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    rMatrix[i][j] = Math.min(Math.min(rMatrix[i - 1][j], rMatrix[i][j - 1]), rMatrix[i - 1][j - 1]) + 1;
                }
                result = Math.max(result, rMatrix[i][j]);
            }
        }
        return result * result;
    }
}