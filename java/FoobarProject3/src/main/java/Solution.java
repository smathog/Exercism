//To invite a friend to try a challenge, send the link below. This is a single-use code, so choose wisely.
//
//        Refer a friend: "https://foobar.withgoogle.com/?eid=HuxOs" (Unused)
//

/*
There is a relatively straightforwards derivation of a solution to this problem.
We start with an array of n (ascending, positive) integers. Define di = A[i] - A[i-1].
So we end up with n-1 such elements di representing the distances between pegs.
Now, we also define the radius of a gear for each peg as ri. We know that we want a
solution, if one exists, that imposes the constraint r1:rn is 2:1; so we may express
the problem as a system of n-1 equations in n-1 variables (replacing rn with r1)
where all but first and last are of the form di = ri + r(i+1), and the first is of the form
d1 = 2r1 + r2, and the last is d(n-1) = r1 + r(n-1).

Notice that rewriting this in the form of a matrix equation Ax = b, A is nearly upper triangular
and has a clearly nonzero determinant of 2, so the linear transformation represented by A is
invertible and the equation where b is a vector of d1...d(n-1) must have a solution in the set
of reals. This does not imply a solution to the specific bounds imposed by the problem, but this
can be checked by generating a solution in the reals and seeing if it meets the imposed bounds
(i.e. all ri must be greater than 1; a negative gear size does not make physical sense, after all).

Now, to solve: observe that for n > 3, we have that
    d1 + ... + d(n-1) = (2r1 + r2) + .... + (r1 + r(n-1))
    d1 + ... + d(n-1) = 3r1 + 2(r2 + ... + r(n-1))

Now, if n is odd, then n-1 is even, so the following substitution can be used:
    3r1 + 2(r2 + ... + r(n-1)) = r1 + 2((r2 + r3) + ... + (r1 + r(n-1))
    3r1 + 2(r2 + ... + r(n-1)) = r1 + 2(d2 + d4 + ... d(n-1))
Thus
    r1 = d1 - d2 + d3 - d4 + ... - d(n-1)

On the other hand, if n is even, then n-1 is odd, and the following substitution can be used:
    3r1 + 2(r2 + ... + r(n-1)) = 3r1 + 2((r2 + r3) + ... + (r(n-2) + r(n-1))
    3r1 + 2(r2 + ... + r(n-1)) = 3r1 + 2(d2 + d4 + ... d(n-2))
Thus
    r1 = (d1 - d2 + d3 - d4 ... - d(n-2) + d(n-1)) / 3

Having found r1, it is simple to find the other ri using the original equations.

(Note that the above reasoning still applies for n = 2, 3; just the cancellation is more direct).

TIME COMPLEXITY ANALYSIS:
1) 1 O(N) pass to find all di
2) 1 O(N) pass to find r1
3) 1 O(N) pass to find all other ri and simultaneously check for validity

Overall time complexity: O(N).

*/


import java.util.ArrayList;


public class Solution {
    public static int[] solution(int[] pegs) {
        //Convert pegs to distances:
        final ArrayList<Integer> distances = new ArrayList<>();
        for (int i = 1; i < pegs.length; ++i) {
            distances.add(pegs[i] - pegs[i - 1]);
        }

        //Calculate r1
        boolean fractionComp = false;
        int r1 = 0;
        for (int i = 0; i < distances.size(); ++i) {
            if (i % 2 == 0) {
                r1 += distances.get(i);
            } else {
                r1 -= distances.get(i);
            }
        }
        if (distances.size() % 2 == 1) {
            if (r1 % 3 == 0) {
                r1 /= 3;
            } else {
                fractionComp = true;
            }
        }

        //Calculate other gear radii and check validity:
        if (r1 < 1 || (r1 < 3 && fractionComp)) {
            return new int[]{-1, -1};
        }
        int prevR = 2 * r1;
        for (int distance: distances) {
            //Adjust for the possibility r1 is a fraction over 3; avoids double rounding errors:
            if (fractionComp) {
                if (prevR < 3) {
                    return new int[]{-1, -1};
                }
                prevR = 3 * distance - prevR;

            } else {
                if (prevR < 1) {
                    return new int[]{-1, -1};
                }
                prevR = distance - prevR;
            }
        }

        // Validity checks out, return result:
        if (fractionComp) {
            return new int[]{2 * r1, 3};
        } else {
            return new int[]{2 * r1, 1};
        }
    }
}