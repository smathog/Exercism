//Let's reason through this.
//A brute force solution: Generate a list of factors of N = length string. Takes O(N) to do this. Then for a match
// of length k, for the entire length of the original string we must have string[i] = string[i + k]. Checking this
// is O(N); it must be done relative to the number of factors (which is actually O(sqrt(N))?) so the total algorithm
//looks like O(N*sqrt(N)).

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static int solution(String x) {
        ArrayList<Integer> factorList = factorList(x.length());
        factorLoop:
        for (int i : factorList) {
            for (int j = 0; j + i < x.length(); ++j) {
                if (x.charAt(j) != x.charAt(j + i)) {
                    continue factorLoop;
                }
            }
            //Repeating substring of length i detected, so there are x.length() / i copies.
            return x.length() / i;
        }
        //In worst case (the solution is the whole string) the inner loop never executes
        //But return 1 here regardless to make the compiler stop complaining:
        return 1;
    }

    //Returns a list of factors of n (inclusive) in ascending order
    //Not the most efficient factorization algorithm, but N <= 200 here.
    private static ArrayList<Integer> factorList(int n) {
        return IntStream.rangeClosed(1, n)
                .filter(i -> n % i == 0)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}