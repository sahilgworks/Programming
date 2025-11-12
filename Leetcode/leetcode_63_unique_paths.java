public class leetcode_63_unique_paths {

    public static void main(String[] args) {
            System.out.println(new Solution().uniquePathsWithObstacles(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
            System.out.println(new Solution().uniquePathsWithObstacles(new int[][]{{0,1},{0,0}}));
            System.out.println(new Solution().uniquePathsWithObstacles(new int[][]{{1,1},{0,0}}));
    }

    static class Solution {

        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int row = obstacleGrid.length;
            int col = obstacleGrid[0].length;
            int memory[][] = new int[row][col];
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    memory[i][j] = -1;
                }
            }
            return  uniquePathsWithObstacles(
                    obstacleGrid,
                    memory,
                    row, col,
                    0, 0,
                    row - 1, col - 1);
        }

        public int uniquePathsWithObstacles(int[][] obstacleGrid,int[][] memory, int m, int n, int x, int y, int dX, int dY){
            if(x>=m||y>=n){
                return 0;
            }
            if(obstacleGrid[x][y]!=0){
                return 0;
            }
            if(x == dX && y == dY){
                return 1;
            }
            if(memory[x][y]!=-1){
                return memory[x][y];
            }
            obstacleGrid[x][y] = -1;
            int result =  uniquePathsWithObstacles(obstacleGrid, memory, m, n, x+1, y, dX, dY)
                    + uniquePathsWithObstacles(obstacleGrid,memory,  m, n, x, y+1, dX, dY);
            memory[x][y] = result;
            obstacleGrid[x][y] = 0;
            return result;

        }
    }
}
