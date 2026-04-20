import java.util.*;
import java.lang.*;


public class leetcode_76_substring {

	public static void main(String[] args) {
		new leetcode_76_substring().run();
	}

	void run() {
        SolutionV3 solution = new SolutionV3();
		System.out.println("expected:"+"BANC"+", result:"+ solution.minWindow("ADOBECODEBANC", "ABC"));
		System.out.println("expected:"+"a"+", result:"+ solution.minWindow("a", "a"));
		System.out.println("expected:"+""+", result:"+ solution.minWindow("a", "aa"));
		System.out.println("expected:"+"AABBC"+", result:"+ solution.minWindow("AAABBC", "AABC"));
	}

    /**
     * my solution works on leetcode but lacks on many front:
     *
     * Can I avoid scanning all 52 characters to check validity?
     * What is the minimum information needed to know? if the window is valid?
     * When I move left pointer, how do I know window becomes invalid?
     *
     ***/
	class Solution {
		public String minWindow(String s, String t) {
			//we maintain two arrays -> one for s and other one for t -> countS and countT
			//these array will be of 52 size to take of A-Za-z for bot s & t
			//now we will maintain two result variables
			//resultSize -> length of result_str (initially this will be Integer.MAX_VALUE)
			//result_str -> which needs to be returned(default value null)
			//resultL, resultR (right is inclusive)
			//has match found -> boolean -> signifies if required character of t are in s subset or nor
			//leftMostChar char -> signifies the
			//start from left of s -> maintain two variables of current window -> windowL, windowR
			//lets add s[i] to countS
			//check if countT is subset of countS.
			//if true, we can shrink the window from resultL if match_found was true
			//and compare the new string length with result size if new size < result size. lets update all result values
			//we dont need to shrink the window for each index we need to d this only when current character was part of T.
			//in a way countT[currentChar]>0


			//subset operation
			//for each indx of 52
			//if s[i]>=t[i] continue;
			//else false
			//true
			//return "";

			int resultL = 0;
			int resultR = Integer.MAX_VALUE; //inclusive
			boolean matchFound = false;

			int windowL = -1;
			int windowR = -1; //inclusive

			int[] countT = new int[52];
			int[] countS = new int[52];

			int lengthT = t.length();
			int lengthS = s.length();

			char[] charArrayT = t.toCharArray();
			char[] charArrayS = s.toCharArray();

			boolean isShrinkRequired = false;

			for (int i = 0; i < lengthT; i++) {
				countT[chToIdx(charArrayT[i])]++;
			}

			for (int i = 0; i < lengthS; i++) {
				isShrinkRequired = false;
				char current = charArrayS[i];
				int chIdx = chToIdx(current);
				if (windowL == -1) {
					windowL = i;
				}
				windowR = i;
				countS[chIdx]++;
				if (matchFound) {
					if (countT[chIdx] != 0 && countS[chIdx] > countT[chIdx]) {
						//shrinking might be required on the current window
						isShrinkRequired = true;
					} else {
						//just update current window attributes, result is not impacted
						continue;
					}
				} else {
					if (countT[chIdx] != 0 && countS[chIdx] >= countT[chIdx]) {
						//there is a possibility that we have found a result
						if (isSubset(countS, countT)) {
							matchFound = true;
							isShrinkRequired = true;
						} else {
							//no change required or shrinking required
							continue;
						}
					} else {
						//just update the window attributes no shrinking is required
						continue;
					}
				}

				if (isShrinkRequired) {
					//shrink from left until we found a char in countS[idx]==countT[idx];
					for (int j = windowL; j <= windowR; j++) {
						char current2 = charArrayS[j];
						int chIdx2 = chToIdx(current2);
						if (countS[chIdx2] <= countT[chIdx2]) {
							windowL = j;
							break;
						} else {
							countS[chIdx2]--;
						}
					}
					if ((windowR - windowL) < (resultR - resultL)) {
						resultL = windowL;
						resultR = windowR;
					}
				}
			}
			if (matchFound) {
				return s.substring(resultL, resultR + 1);
			} else {
				return "";
			}
		}

		int chToIdx(char ch) {
			if (ch >= 'A' && ch <= 'Z') {
				return ch - 'A';
			} else {
				return 26 + (ch - 'a');
			}

		}

		boolean isSubset(int[] s, int[] t) {
			for (int i = 0; i < 52; i++) {
				if (s[i] >= t[i]) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
	}

    /**
     *Above solution was quite complex. it has best run time, but we can make it more readable and efficient.
     *
     * my origianl thought is:
     *
     *
     * i am thinking of keeping a required count array of 52 elements which will initially contain the count of charaters from target array. 
     * lets name this array as reqCount[] of len 52 and we also want to keep another variable of len of target array lets name this as reqSum which is length of Target
     * for each element of source array if window is not yet created, we will keep ignoring the current element until reqCount[i]==0, which mean current character is not required.
     * We will keep on scanning until a valid character is found and we will decrement reqCount[i]. We will intialize the window with left and right index which would be same in this case. 
     * we will keep on scanning the window and keep on reducing the values in array. When we are reducing the count in reqCount we will also reduce the value of variable reqSum. 
     * We will reduce reqSum only when we reduce the value of reqCount from a positive value that means we still needed this value.
     * Once reqSum is zero we can safely say that we have a result. Now it can happen we have a elongated window instead of concise one if reqSum is zero we can slide the window from left to right. and 
     * keep on incrementing the count of reqCount only when reqCount[i] becomes greater than 0, we will stop just before that. so doing this we dont change reqSum, we can keep on going like this.
     * and for each index we need to shrink the window only when if left most index of window is now right most index of window and the reqCount[i] is less than 0. then we can shrink it from left otherwise we can skip shrinking. 
     *
     **/

    class SolutionV2{

		public String minWindow(String s, String t) {
            //System.out.println("source "+s+" target"+t);
            char[] sChars = s.toCharArray();
            char[] tChars = t.toCharArray();
            int rStart = -1;
            int rLength = 0;
            int cStart = -1;
            int cLength = 0;
            int reqSum = tChars.length;
            int[] reqCount = new int[52];
            int cid = 0;
            for(int i=0;i<tChars.length;i++){
                cid = cid(tChars[i]);
                reqCount[cid]++;
            }
            for(int i=0;i<sChars.length;i++){
                cid = cid(sChars[i]);
                if(reqCount[cid]>0){
                   if(cStart<0){
                        cStart = i;
                   }
                   cLength++;
                   reqSum--;                        
                   reqCount[cid]--;
                }else{
                    if(cStart>=0){
                        reqCount[cid]--;
                        cLength++;
                    }else{
                        //we need to ignore not required characters to avoid non-required long windows
                    }
                }
                //System.out.println("i"+i+" char"+sChars[i]+" cid"+cid+" cS,CE("+cStart+","+cLength);

                if(reqSum==0 && cStart>=0 && reqCount[cid]<0 && sChars[cStart] == sChars[i]){
                    while(reqCount[cid]<0){
                        reqCount[cid]++; 
                        cStart++;                    
                        cLength--;
                        cid = cid(sChars[cStart]);
                    };
                    //System.out.println("updated window is:"+" cS,CE("+cStart+","+cLength);
                }
                if(reqSum==0){
                    if(rStart<0 || rLength > cLength){
                        rStart = cStart;
                        rLength = cLength;
                        //System.out.println("updated result window is:"+" rS,rE("+rStart+","+rLength);
                    }else{
                        //no change required
                    }
                }else{
                    //we dont have a valid window here

                }

             }             
            if(rStart<0){
                return "";
            }else{
                return s.substring(rStart,  rStart+rLength);

            }
        }

        int cid(char c){
            return c>='a'?c-'a'+26:c-'A';
        }
    }
 
    
    /** 
    * Why must the window ALWAYS include every character (even irrelevant ones)?
    * Why is it incorrect to base shrinking on the current character (s[i])?
    * Why must the window ALWAYS include every character (even irrelevant ones)?
    * When shrinking, why must decisions depend ONLY on s[left]? 
    * my Solution v2 failed on above questions. majorly this test case: 	System.out.println("expected:"+"AABBC"+", result:"+ solution.minWindow("AAABBC", "AABC"));
    **/
    class SolutionV3{

		public String minWindow(String s, String t) {
            //System.out.println("source "+s+" target"+t);
            char[] sChars = s.toCharArray();
            char[] tChars = t.toCharArray();
            int rStart = -1;
            int rEnd = 0;
            int cStart = -1;
            int cEnd = 0;
            int reqSum = tChars.length;
            int[] reqCount = new int[52];
            int cid = 0;
            for(int i=0;i<tChars.length;i++){
                cid = cid(tChars[i]);
                reqCount[cid]++;
            }
            for(int i=0;i<sChars.length;i++){
                cid = cid(sChars[i]);
                if(cStart<0){
                    cStart = i;
                }
                if(reqCount[cid]>0){
                    reqSum--;                        
                }
                cEnd = i;
                reqCount[cid]--;
                //System.out.println("i"+i+" char"+sChars[i]+" cid"+cid+" cS,CE("+cStart+","+cLength);
                //shrinking
                if(reqSum==0){
                    while(cStart<=cEnd && reqCount[cid=cid(sChars[cStart])]<0){
                        reqCount[cid]++; 
                        cStart++;                    
                    };
                }
                    //System.out.println("updated window is:"+" cS,CE("+cStart+","+cLength);
                if(reqSum==0){
                    if(rStart<0 || (rEnd-rStart) > (cEnd-cStart)){
                        rStart = cStart;
                        rEnd = cEnd;
                        //System.out.println("updated result window is:"+" rS,rE("+rStart+","+rLength);
                    }else{
                        //no change required
                    }
                }else{
                    //we dont have a valid window here

                }

             }             
            if(rStart<0){
                return "";
            }else{
                return s.substring(rStart,  rEnd+1);

            }
        }

        int cid(char c){
            return c>='a'?c-'a'+26:c-'A';
        }
    }

}
