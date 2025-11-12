import java.lang.*;
import java.util.*;

public class leetcode_980_unique_paths {

    public static void main(String[] args) {
        System.out.println(new SolutionV3().uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));
//        System.out.println(new Solution().uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 2}}));
//        System.out.println(new Solution().uniquePathsIII(new int[][]{{0, 1}, {2, 0}}));
    }

    static class SolutionV3 {

        int result = 0;


        public int uniquePathsIII(int[][] obstacleGrid) {
            int row = obstacleGrid.length;
            int col = obstacleGrid[0].length;
            int startIndexI = -1;
            int startIndexJ = 0;
            int endIndexI = -1;
            int endIndexJ = 0;
            int nonObsCell = 0;
            int[][] memory = new int[row][col];

//            PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
//                public int compare(int[] left, int[] right){
//                    return -Integer.compare(memory[left[0]][left[1]], memory[right[0]][right[1]]);
//                }
//            });

            Queue<int[]> queue = new ArrayDeque<>();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (startIndexI == -1 && obstacleGrid[i][j] == 1) {
                        startIndexI = i;
                        startIndexJ = j;
                    }
                    if (endIndexI == -1 && obstacleGrid[i][j] == 2) {
                        endIndexI = i;
                        endIndexJ = j;
                    }
                    if (obstacleGrid[i][j] == 0) {
                        nonObsCell++;
                    }
                }
            }
            obstacleGrid[startIndexI][startIndexJ] = 0;
            obstacleGrid[endIndexI][endIndexJ] = 0;
            System.out.printf("(%d,%d) -> (%d,%d)\n", startIndexI, startIndexJ, endIndexI, endIndexJ);
            queue.offer(new int[]{startIndexI, startIndexJ});
            while(queue.size()!=0) {
                int[] start = queue.poll();
                uniquePathsWithObstacles(
                        obstacleGrid,
                        memory,
                        queue,
                        row, col,
                        start[0], start[1],
                        endIndexI, endIndexJ,
                        0,
                        nonObsCell);
                memory[start[0]][start[1]] = -1;

            }
            obstacleGrid[startIndexI][startIndexJ] = 1;
            obstacleGrid[endIndexI][endIndexJ] = 2;
            return result;
        }

        public void uniquePathsWithObstacles(int[][] obstacleGrid, int[][] memory,Queue<int[]> queue, int m, int n, int x, int y, int dX, int dY, int sum, int minCount) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return;
            }
            if (obstacleGrid[x][y] != 0) {
                return;
            }
            if (x == dX && y == dY) {
                if (sum >= minCount) {
                    result++;
                }
                return;
            }
            if (memory[x][y] == -1) {
                return;
            }
            memory[x][y] = Math.max(sum, memory[x][y]);
            int temp = memory[x][y];
            memory[x][y] = -1;
            if(updateMemory(obstacleGrid, memory, m, n, x + 1, y, dX, dY, temp + 1, minCount)){
                queue.add(new int[]{x+1,y});
            }
            if(updateMemory(obstacleGrid, memory, m, n, x - 1, y, dX, dY, temp + 1, minCount)){
                queue.add(new int[]{x-1,y});
            }
            if(updateMemory(obstacleGrid, memory, m, n, x, y - 1, dX, dY, temp + 1, minCount)){
                queue.add(new int[]{x,y-1});
            }
            if(updateMemory(obstacleGrid, memory, m, n, x, y + 1, dX, dY, temp + 1, minCount)){
                queue.add(new int[]{x,y+1});
            }
            print(x,y);
            print(memory);
            return;
        }
        public boolean updateMemory(int[][] obstacleGrid, int[][] memory, int m, int n, int x, int y, int dX, int dY, int sum, int minCount) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return false;
            }
            if (obstacleGrid[x][y] != 0) {
                return false;
            }
            if (x == dX && y == dY) {
                if (sum >= minCount) {
                    result++;
                }
                return false;
            }
            if (memory[x][y] == -1) {
                return false;
            }
            memory[x][y] = Math.max(sum, memory[x][y]);
            return true;
        }


        void print(int x,int y){
            System.out.println("("+x+","+y+")");
        }

        void print(int[][] x){
            for(int i=0;i<x.length;i++){
                for(int j=0;j<x[0].length;j++){
                    System.out.print(x[i][j]+", ");
                }
                System.out.print("\n");
            }
        }

    }

    static class SolutionV2 {

        int result = 0;

        public int uniquePathsIII(int[][] obstacleGrid) {
            int row = obstacleGrid.length;
            int col = obstacleGrid[0].length;
            int startIndexI = -1;
            int startIndexJ = 0;
            int endIndexI = -1;
            int endIndexJ = 0;
            int nonObsCell = 0;
            int[][] memory = new int[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (startIndexI == -1 && obstacleGrid[i][j] == 1) {
                        startIndexI = i;
                        startIndexJ = j;
                    }
                    if (endIndexI == -1 && obstacleGrid[i][j] == 2) {
                        endIndexI = i;
                        endIndexJ = j;
                    }
                    if (obstacleGrid[i][j] == 0) {
                        nonObsCell++;
                    }
                }
            }
            obstacleGrid[startIndexI][startIndexJ] = 0;
            obstacleGrid[endIndexI][endIndexJ] = 0;
            System.out.printf("(%d,%d) -> (%d,%d)\n", startIndexI, startIndexJ, endIndexI, endIndexJ);
            uniquePathsWithObstacles(
                    obstacleGrid,
                    memory,
                    row, col,
                    startIndexI, startIndexJ,
                    endIndexI, endIndexJ,
                    0,
                    nonObsCell);

            obstacleGrid[startIndexI][startIndexJ] = 1;
            obstacleGrid[endIndexI][endIndexJ] = 2;
            return result;
        }

        public void uniquePathsWithObstacles(int[][] obstacleGrid, int[][] memory, int m, int n, int x, int y, int dX, int dY, int sum, int minCount) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return;
            }
            if (obstacleGrid[x][y] != 0) {
                return;
            }
            if (x == dX && y == dY) {
                if (sum >= minCount) {
                    result++;
                }
                return;
            }
            if (memory[x][y] == -1) {
                return;
            }
            memory[x][y] = Math.max(sum, memory[x][y]);
            int temp = memory[x][y];
            memory[x][y] = -1;
            updateMemory(obstacleGrid, memory, m, n, x + 1, y, dX, dY, temp + 1, minCount);
            updateMemory(obstacleGrid, memory, m, n, x - 1, y, dX, dY, temp + 1, minCount);
            updateMemory(obstacleGrid, memory, m, n, x, y - 1, dX, dY, temp + 1, minCount);
            updateMemory(obstacleGrid, memory, m, n, x, y + 1, dX, dY, temp + 1, minCount);
            print(x,y);
            print(memory);

            uniquePathsWithObstacles(obstacleGrid, memory, m, n, x + 1, y, dX, dY, temp + 1, minCount);
            uniquePathsWithObstacles(obstacleGrid, memory, m, n, x - 1, y, dX, dY, temp + 1, minCount);
            uniquePathsWithObstacles(obstacleGrid, memory, m, n, x, y - 1, dX, dY, temp + 1, minCount);
            uniquePathsWithObstacles(obstacleGrid, memory, m, n, x, y + 1, dX, dY, temp + 1, minCount);
            return;
        }

        public void updateMemory(int[][] obstacleGrid, int[][] memory, int m, int n, int x, int y, int dX, int dY, int sum, int minCount) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return;
            }
            if (obstacleGrid[x][y] != 0) {
                return;
            }
            if (x == dX && y == dY) {
                if (sum >= minCount) {
                    result++;
                }
                return;
            }
            if (memory[x][y] == -1) {
                return;
            }
            memory[x][y] = Math.max(sum, memory[x][y]);
            return;
        }

        void print(int x,int y){
            System.out.println("("+x+","+y+")");
        }

        void print(int[][] x){
            for(int i=0;i<x.length;i++){
                for(int j=0;j<x[0].length;j++){
                    System.out.print(x[i][j]+", ");
                }
                System.out.print("\n");
            }
        }

    }


    /**
     * this solution does not work with memory because using memory will give us just one path,
     * and if we don't use memory this is very inefficient way to code.
     * Please check v2 version
     */
    static class Solution {

        Integer[] dummy = new Integer[]{-1};

        public int uniquePathsIII(int[][] obstacleGrid) {
            int row = obstacleGrid.length;
            int col = obstacleGrid[0].length;
            Integer memory[][][] = new Integer[row][col][];
            int startIndexI = -1;
            int startIndexJ = 0;
            int endIndexI = -1;
            int endIndexJ = 0;
            int nonObsCell = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (startIndexI == -1 && obstacleGrid[i][j] == 1) {
                        startIndexI = i;
                        startIndexJ = j;
                    }
                    if (endIndexI == -1 && obstacleGrid[i][j] == 2) {
                        endIndexI = i;
                        endIndexJ = j;
                    }
                    if (obstacleGrid[i][j] == 0) {
                        nonObsCell++;
                    }
                    memory[i][j] = dummy;
                }
            }
            nonObsCell++;//for starting cell
            obstacleGrid[startIndexI][startIndexJ] = 0;
            obstacleGrid[endIndexI][endIndexJ] = 0;
            System.out.printf("(%d,%d) -> (%d,%d)\n", startIndexI, startIndexJ, endIndexI, endIndexJ);
            List<Integer> result = uniquePathsWithObstacles(
                    obstacleGrid,
                    memory,
                    row, col,
                    startIndexI, startIndexJ,
                    endIndexI, endIndexJ);

            int count = 0;
            for (Integer i : result) {
                if (i.equals(nonObsCell)) {
                    count++;
                }
            }
            obstacleGrid[startIndexI][startIndexJ] = 1;
            obstacleGrid[endIndexI][endIndexJ] = 2;
            return count;
        }

        public List<Integer> uniquePathsWithObstacles(int[][] obstacleGrid, Integer[][][] memory, int m, int n, int x, int y, int dX, int dY) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return Collections.emptyList();
            }
            if (obstacleGrid[x][y] != 0) {
                return Collections.emptyList();
            }
            if (x == dX && y == dY) {
                return Collections.singletonList(0);
            }
            if (memory[x][y] != dummy) {
                System.out.println("memory out:" + x + "," + y + ":" + Arrays.toString(memory[x][y]));
                //not using memory will give right answer
//                return Arrays.asList(memory[x][y]);
            }
            obstacleGrid[x][y] = -1;
            List<Integer> result = new ArrayList<Integer>();
            addAll(result, uniquePathsWithObstacles(obstacleGrid, memory, m, n, x + 1, y, dX, dY));
            addAll(result, uniquePathsWithObstacles(obstacleGrid, memory, m, n, x - 1, y, dX, dY));
            addAll(result, uniquePathsWithObstacles(obstacleGrid, memory, m, n, x, y - 1, dX, dY));
            addAll(result, uniquePathsWithObstacles(obstacleGrid, memory, m, n, x, y + 1, dX, dY));
            int maxOfAll = maxOfAll(result);
            removeNonMax(result, maxOfAll);
            obstacleGrid[x][y] = 0;
            System.out.println("memory in:" + x + "," + y + ":" + result);
            memory[x][y] = result.toArray(new Integer[result.size()]);
//            if(!checkIfEqual(memory[x][y], result)){
//                System.out.println("error");
//            }
            return result;
        }

        boolean checkIfEqual(Integer[] left, List<Integer> right) {
            Arrays.sort(left);
            Collections.sort(right);
            if (left.length != right.size()) {
                return false;
            }
            for (int i = 0; i < left.length; i++) {
                if (!left[i].equals(right.get(i))) {
                    return false;
                }
            }
            return true;
        }

        void addAll(List<Integer> result, List<Integer> from) {
            for (Integer i : from) {
                result.add(i + 1);
            }
        }

        int maxOfAll(List<Integer> result) {
            int max = 0;
            for (Integer i : result) {
                if (i > max) {
                    max = i;
                }
            }
            return max;
        }

        void removeNonMax(List<Integer> result, Integer max) {
            List<Integer> toRemove = new ArrayList<Integer>();
            for (Integer i : result) {
                if (!i.equals(max)) {
                    toRemove.add(i);
                }
            }
            result.removeAll(toRemove);
        }
    }
}
