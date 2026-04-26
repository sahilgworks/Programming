import java.util.*;
import java.lang.*;

class leetcode_85_largest_rect_histogram_v2{


    public static void main(String[] args){
        SolutionV1 sol = new SolutionV1();
        char[][] matrix = new char[][]{
            {'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}
        };
        System.out.println("expected:6  , actual: "+sol.maximalRectangle(matrix));
    }




    /**
     * i have an idea on how to solve this problem. initial idea is to reuse the algorithm of largest rectanfgle area given n bars
     * we can extend this idea and apply the algorith row by row. 
     * intially a row of column width is zero intialised
     * if we found a 0 in i th column it is made 0 in new row and if 1 we will add 1 to new row.
     * we will apply largest rectangle area formula on new row.
     * and once we have the result from this algo we will set in return result if greater than current value
     * and we will do this for each row of given rectangle -> +1 if current value is 1 and make it 0 if current value is 0 for each column of given row.
     *
     **/ 
    static class SolutionV1{

        int maximalRectangle(char[][] matrix){
            int result = 0;
            int[] row = new int[matrix[0].length];
            for(int i=0;i<matrix.length;i++){
                for(int j=0;j<matrix[0].length;j++){
                    if(matrix[i][j]=='0'){
                        row[j] = 0;
                    }else{
                        row[j]++;
                    }    
                }
                result = Math.max(result, maxArea(row));
            }
            return result;
        }


        int maxArea(int[] matrix){
            int result = -1;
            Deque<Integer> deque = new ArrayDeque<Integer>();
            for(int i=0;i<matrix.length+1;i++){
                int value = i<matrix.length?matrix[i]:-1;
                while(!deque.isEmpty() && matrix[deque.peekLast()]>=value){
                    int v = deque.pollLast();
                    result = Math.max(result, (i-(deque.isEmpty()?-1:deque.peekLast())-1)*matrix[v]);
                }
                deque.offerLast(i);
            }
            return result;

        }
    }


}
