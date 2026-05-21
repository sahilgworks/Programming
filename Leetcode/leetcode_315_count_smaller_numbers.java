import java.util.*;
import java.lang.*;

class leetcode_315_count_smaller_numbers{

    public static void main(String[] args){
        SolutionV2 sol = new SolutionV2();
        int[] input;
        int[] expected;
        input = new int[]{5,2,6,1};
        expected = new int[]{2,1,1,0};
        System.out.println("input: "+Arrays.toString(input)+" expected:"+Arrays.toString(expected)+" output: "+ sol.countSmaller(input));

        input = new int[]{-1,-1};
        expected = new int[]{0,0};
        System.out.println("input: "+Arrays.toString(input)+" expected:"+Arrays.toString(expected)+" output: "+ sol.countSmaller(input));

        input = new int[]{2,1,1,1};
        expected = new int[]{3,0,0,0};
        System.out.println("input: "+Arrays.toString(input)+" expected:"+Arrays.toString(expected)+" output: "+ sol.countSmaller(input));

        input = new int[]{2,1,2,1,2,1};
        expected = new int[]{3,0,2,0,1,0};
        System.out.println("input: "+Arrays.toString(input)+" expected:"+Arrays.toString(expected)+" output: "+ sol.countSmaller(input));


        input = new int[]{7,5,6,1};
        expected = new int[]{3,1,1,0};
        System.out.println("input: "+Arrays.toString(input)+" expected:"+Arrays.toString(expected)+" output: "+ sol.countSmaller(input));
    }

    public static class SolutionV1{
        /**
         * question is to find smaller numbers in upcoming array. equal numbers does not qualify. 
         * one brute force way of dong this is to maintain a insertion sort of numbers.
         * such that we just find the number in the sorted array in logn and get to know the smaller numbers
         * also for each step it might take O(n) time so this brute force would take o(n^2) time
         *
         * we should be able to do this in O(n) or (nlogn) time
         *  
         *
         *
         *  one solution i can think of is to maintain a stack kind of thing
         *
         *  we would store indexes of elements and their result in a list of integer
         *  first we need to create a result array of len size
         *  now we will start from end of array
         *  since stack is empty we will do 2 operations
         *  1. set value of result for last index as 0 
         *  2. insert index of last element in stack
         *
         *  now we go to next element which is secnd last element - 6
         *  now since 1 which is in stack is smaller than current value we just add 1 to its value from result array
         *
         * its answer would be 1 and lets insert 6 index in stack
         *
         * now we have 2 we will pop until a smaller number is found and add just 1 to it which is 0
         *
         * and now comes 5 , top of stack is smaller so just add 1 yo its value
         *
         * but one immeditate problem is when numbers are equal lets say
         *
         * 3,1,1,1
         *
         * it answer should [3,0,0,0] but our answer would be [1,0,0,0] which is incorrectt
         *
         * answer to this problem could be to maintain a frequency map as well
         * so when we stop a value  which is smaller than us we would just add the result[index] + frequency[value[index]] 
         * also post scanning each index we need to update self frequency as well
         *
         * snce each index is pushed or popped just once we would have o(n) run time and On) memory requirement oo
         *
         *
         * this solution works for some cases but fails when numbers are in certain order like this 7,5,6,1 will give 2,1,1,0 whereas it should be 3,2,1,0
         *
         *
         **/
        List<Integer> countSmaller(int[] input){
            HashMap<Integer, Integer> freq = new HashMap<>();
            Integer[] result = new Integer[input.length];
            Arrays.fill(result, 0);
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            stack.offerFirst(input.length-1);
            result[input.length-1] = 0;
            for(int i=input.length-1;i>=0;i--){
                while(!stack.isEmpty() && input[stack.peekFirst()] >= input[i]){
                    stack.pollFirst();
                }
                if(!stack.isEmpty()){
                    int index = stack.peekFirst();
                    int value = input[index];
                    int freqV = freq.getOrDefault(value, 0);
                    result[i] = result[index]+freqV;
                }
                stack.offerFirst(i);
                freq.put(input[i], freq.getOrDefault(input[i],0)+1);
            }
            return Arrays.asList(result);
        }
    }
    /**
     * lets try to fix shortcomings of V1 
     *
     * the problem is we are looking for first smallest number whereas it will give false count since there could be numbers more than top of stack but less than current number
     *
     * how can we fix this
     *
     *
     * if we try to generalise the failing use case it is 
     *
     * a <--some numbers> b <--some numbers-->
     * 
     * one way i think is to wrap each number in a custom class so that equals and hashcode does not work.
     * we can use this custom class to wrap individual integer and use it as key of tre map
     * and when we query treemap we can get a submap from treemap for all keys less than current value and jsut use the sum as answer for that index
     * and insert current indx value in treemap
     * and similarly we can do this for indexes 
     * memory compleity is o(n)
     * time copleity is o(nlogn)
     *
     *
     * but again this is incorrect because size() method of submap is not cached which means it time we invoke it calculetes the number of elements in submap of treemap
     * this leads to O(n^2) time complexity
     *
     *
     * how can we fix this?
     *
     *
     * we could not solve this question, again we had to take help of chat gpt
     *
     * i was surpurised to see merge sort use here. i did not understand it either but lets try to see how can we use merge sort here
     *
     * lets say we have two sorted arrays and we need to find number of elements smaller than current element
     *
     * but index also matter to us because how will we know which index needd to be updated.
     *
     * lets say we create an array with indexes as values of length L
     * lets say we have two halves of array and lets compare top of both
     *
     * if top of left is greater than top of right -> we can add length of right to result of current index
     * if inverse happens that left is smaller than right than we can not add length of left to result of right index since we need to find numbers smaller on right side of array not left side
     *
     * we can remove the greater one
     *
     * what happens when both values are equal, we can not use right side of array for left most index since the other number on right side would greater and we are looking for smaller
     * similary left portion is of no use for right since we need count of number on right side
     *
     * in this case we can remove both top since they are equal. one thing to consider here is we are using incorrect terminology it is not both top but extreme right of left side and exteeme left of right side, lets continue usin top of both to maintain consistency
     *
     * lets try on a real example: 7, 5, 6, 1
     *
     * lets divide the array into 2 halves 7,5 and 6,1
     *
     * and merge 7 and 5, 7 is greater than 5 so for 7 it is length of second array and for 5 it is 0 
     *
     * we have 5,7 and values as 1,0
     * for 6,1 it is 1,6 and 1,0
     * now merge 5,7 and 6,1 7>6 so add lenght of right subarray to 7. which is 3,0,1,0
     * we are left with 5 and 6,1 remove 6 no change in count 3,0,1,0
     * we have now 5 and 1 and again 1 is less than 5 so add length of right array to 5 which is now 3,1,1,0
     * just one element left on array and other is empty no channge required
     *
     * what we can do is we can maintain start index and end index of each sub array instead of removing them
     * once we have got the value we can merge the array using plain merge sort. 
     *
     * we can not merge while comparing becaue we are comparing largest of one sub array with smallest of second sub array
     * so we need to add another pass to merge it.
     *
     * 4,7,3,8,0
     * 
     * 4,7 and 3,8,0 
    * 0,0 and 3 and 8,0 
        * 0,0 and 3 and 1,0
        * 0,0 and 3 and 0 -> after popping 8 since 8 is larger  than 3
        * 0,0 and 1 and 1,0 
        * 4,7 and 
        *
        *
        * okay, one mistake which were doing is comparing right end of left array and left end of right array
        *
        * we should compare same ends of both arrays because we need elimition and if we are comparing diferent ends we will get incorrect answer
        *
        * for example here: 1,5 and 0,6,7 now 5>0 so we will add length of right sub array in result of 5 bit it is not correct because
        * 6,7 are larger so we need to compare right ends of both arrays which will give the real picture 
        * and the other advantage is we can merge the aray as well since we want to erodicate the larger of the two numbers
        *
        *
        *
        * 
        *
        **/
    public static class SolutionV2{

        List<Integer> countSmaller(int[] input){
            int len = input.length;
            Integer[] result = new Integer[len];
            int[] indexes =new int[len];
            for(int i=0;i<len;i++){
                indexes[i] = i;
                result[i] = 0;
            }
            mergeSort(input, indexes, result, len, 0, len);
            return Arrays.asList(result);
        }

        void mergeSort(int[] input, int[] indexes, Integer[] result, int len, int start, int end){
            if(start >= (end-1)){
                return;
            }
            int mid = (start+end)/2;
            mergeSort(input, indexes, result, len, start, mid);
            mergeSort(input, indexes, result, len, mid, end);
            merge(input, indexes, result, len, start, mid, end);
        }

        void merge(int[] input, int[] indexes, Integer[] result, int len, int start,int mid, int end){
            int[] left = Arrays.copyOfRange(indexes, start, mid);
            int[] right = Arrays.copyOfRange(indexes, mid,end);
            int len1 = left.length;
            int len2 = right.length;
            int i=len1-1;
            int j=len2-1;
            int k=end-1;
            while(i>=0 && j>=0){
                if(input[left[i]] > input[right[j]]){
                    int lengthToAdd = j+1;
                    //whole right array can be consumed for left max
                 //System.out.println("i "+i+" index "+left[i]+" value "+input[left[i]]+"left array: "+Arrays.toString(left)+" j "+j+" index "+right[j]+" value: "+input[right[j]]+"right array: "+Arrays.toString(right)+" current value "+result[left[i]]+" new value "+(result[left[i]]+(lengthToAdd))+" start "+start+" mid "+mid+" end "+end+" indexes "+Arrays.toString(indexes)+" result "+Arrays.toString(result));
                    result[left[i]]+=lengthToAdd;
                    indexes[k--] = left[i--];
                }else{
                    indexes[k--] = right[j--];
                }
            }
            while(i>=0){
                indexes[k--] = left[i--];
            }
            while(j>=0){
                indexes[k--] = right[j--];
            }
        }
    }
}
