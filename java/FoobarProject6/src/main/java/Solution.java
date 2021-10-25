/*
Submitted 10/9/21:

The code is strong with this one...
You can now share your solutions with a Google recruiter!
If you opt in, Google Staffing may reach out to you regarding career opportunities, events, and programs.
We will use your information in accordance with our Applicant and Candidate Privacy Policy.
[#1] [Yes [N]o [A]sk me later:]
[Y]es [N]o [A]sk me later: A
Response: contact postponed.
To share your progress at any time, use the recruitme command.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Solution {
    private static int cached = 0;
    private static int calculated = 0;

    public static void main(String[] args) {
        System.out.println(solution(200));
        System.out.println(cached);
        System.out.println(calculated);
    }

    public static int solution(int n) {
        return recursiveSolutionImpl(n);
    }

    /*
    Some reasoning for this one: because we know that 1 + 2 + ... + k = k(k+1)/2, we can upper bound the number
    of steps k that can be found with a maximum of n blocks. Since 3 <= n <= 200 here, the maximum k encountered
    in this problem will be k = 19 corresponding to n = 190.

    Next: we want to find F(n), the total number of configurations of legal steps from n blocks. Define the
    function F(n, k) as the number of total configurations from n blocks in k steps. Then solving for s as the
    maximum legal number of steps for a given n, we have

        F(n) = F(n, 2) + F(n, 3) + ... + F(n, s)

    Now, observe that F(n, 2) can be found in O(1) time since it simply corresponds to all divisions of n into
    two parts such that the first is smaller than the second; this is floor((n - 1) / 2).

    Next, consider F(n, 3). Similar to how we can bound the number of maximum steps for a given n, for a given
    number of steps k and number of blocks n we can bound the initial value (the number of blocks in the lowest
    step) by roughly n/3 = b. Then, we can rewrite F(n, 3) as

        F(n, 3) = F(n - 3, 2) + F(n - 6, 2) + ... + F(n - 3 * b, 2)

    More generally, for a given number of steps k from n blocks, we may find some largest bound b that allows us
    to rewrite the equation F(n, k) as

        F(n, k) = F(n - k, k - 1) + .... + F(n - k * b, k - 1)

    So, as an example:
        F(10) = F(10, 2) + F(10, 3) + F(10, 4)
        F(10, 2) = floor((10 - 1) / 2) = 4
        F(10, 3) = F(7, 2) + F(4, 2) + F(1, 2) = 3 + 1 + 0 = 4
        F(10, 4) = F(6, 3) + F(2, 3) = F(6, 3) = F(3, 2) = 1
        So F(10) = 9
     */
    private static int recursiveSolutionImpl(int n) {
        HashMap<List<Integer>, Integer> cache = new HashMap<>();

        //k is the closest integer solution for x^2 + x - (2 * n) = 0
        int k = (int) ((-1 + Math.sqrt(1 + 16 * n * n)) / 2);
        int sum = 0;
        for (int i = 2; i <= k; ++i) {
            sum += f(n, i, cache);
        }
        return sum;
    }

    //For a given integers n, k, returns how many stairs of k steps can be constructed
    //with n blocks.
    private static int f(int n, int k, HashMap<List<Integer>, Integer> cache) {
        if (cache.containsKey(Arrays.asList(n, k))) {
            ++cached;
            return cache.get(Arrays.asList(n, k));
        } else if (k == 2) {
            int value = (int) Math.floor((n - 1) / (double) 2);
            cache.putIfAbsent(Arrays.asList(n, k), value);
            ++calculated;
            return value;
        } else {
            int sum = 0;
            for (int i = 1; n - k * i > k - 1; ++i) {
                sum += f(n - k * i, k - 1, cache);
            }
            ++calculated;
            cache.putIfAbsent(Arrays.asList(n, k), sum);
            return sum;
        }
    }

    private static int slowSolutionImpl(int n) {
        ArrayList<Integer> seed = new ArrayList<>(Arrays.asList(1, 2));
        int sum = 3;
        HashSet<ArrayList<Integer>> solutions = new HashSet<>(1);
        for (int i = 3; i <= n; ++i) {
            if (i == 3) {
                solutions.add(new ArrayList<>(seed));
            } else {
                HashSet<ArrayList<Integer>> nextStep = new HashSet<>(solutions.size() * 2);
                //Check if new seed can be added
                if (i - sum > seed.get(seed.size() - 1)) {
                    seed.add(i - sum);
                    nextStep.add(new ArrayList<>(seed));
                    sum = i;
                }
                for (ArrayList<Integer> steps : solutions) {
                    //See if possible to add anywhere but to end:
                    for (int j = 0; j < steps.size() - 1; ++j) {
                        if (steps.get(j + 1) - steps.get(j) > 1) {
                            steps.set(j, steps.get(j) + 1);
                            nextStep.add(new ArrayList<>(steps));
                            steps.set(j, steps.get(j) - 1);
                        }
                    }
                    //Done with steps, so no issue consuming it for this turn:
                    steps.set(steps.size() - 1, steps.get(steps.size() - 1) + 1);
                    nextStep.add(steps);
                }
                System.out.printf("i: %d\tDifference from previous: %d\t Max steps: %d%n", i,
                        nextStep.size() - solutions.size(),
                        seed.size());
                solutions = nextStep;
            }
        }
        return solutions.size();
    }
}


/*
Some examples:
n = 3:
    21
n = 4:
    31
n = 5:
    32      41
n = 6:
    321     51      42
n = 7:
    421     61      52      43
n = 8:
    521     431     71      62      53
n = 9:
    621     531     432     81      72      63      54
n = 10:
    4321    721     631     541     532     91      82      73      64
 */