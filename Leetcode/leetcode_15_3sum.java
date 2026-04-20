import java.util.*;
import java.lang.*;

public class leetcode_15_3sum
{

	public static void main(String[] args){
		new leetcode_15_3sum().run(args);	
	}

	void run(String[] args){
		List<List<Integer>> result = new Solution().threeSum(new int[]{2,0,-1,1,4,-1});
		System.out.println("result:"+result);		
	}
}

class Solution {
	public List<List<Integer>> threeSum(int[] nums) {

		//possible approaches
		//we can either sort the array
		//this way we can use binary search to look for third item while
		//keeping two pointers

		// -1 -1 -4 0 1 2 
		// -1 -1 we can use this look for 2   -1 -1 2
		// -1 -4 look for 5 in subarray using binary search
		// -1 0 look for 1 using binary search -> -1 0 1
		// -1 1 look for 0. not possible it is less
		// -1 2 look for -1 not possible less than the second pointer
		// skip next -1 because same values we will get
		// -4 0 look for 4 we can a do binary search or compare with last integer.
		// -4 1 look for 3 use bs
		// -4 2 look for 2 use bs
		// 0 1 look for -1
		// 0 2 look for -2  


		//one optimisation is for 3 elements
		//if sum of all 3 is not zero return empty
		
		
		//Time complexity = sorting n^2 + lookup -> n * n * logn
		List<List<Integer>> result = new ArrayList<>();	
		Arrays.sort(nums);
		int prev = -1;
		for(int i=0;i<nums.length;i++){
			if(prev!=-1 && nums[i] == nums[prev]){
				continue;
			}
			if(nums[i]>=0){
				continue;
			}
			prev = i;
			List<List<Integer>> sum2List = sum2(nums, i+1, nums.length-1, i);
			if(sum2List.size() == 0){
				continue;
			}
			result.addAll(sum2List);
 
		}	
		return result;
		
	}
	
	public List<List<Integer>> sum2(int[] nums, int left, int right, int current){
		ArrayList<List<Integer>> result = new ArrayList<>();		
		while(left<right){
		   int sum = nums[left]+nums[right]+nums[current];
		   if(sum==0){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(nums[current]);
			list.add(nums[left]);
			list.add(nums[right]);
			result.add(list);
			int prev = left;
			left++;			
			while(left<right && nums[left]==nums[prev]){
				left++;
			} 			
			prev = right;
			right--;
			while(left<right && nums[right]==nums[prev]){
				right--;
			}
			}else if(sum<0){
				left++;
			}else{
				right--;
			}
		}
		return result;
	}
}
