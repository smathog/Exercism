/*
Some observations:
    1) For each turn, we can only make one of two choices: for N bombs of type X, you make N bombs of type Y.
    2) At the conclusion of each cycle, the larger of the two counts must have been the one added to.
    3) From (2), it immediately follows that no cycle except the first can have an end count of (N, N).

Thus, to solve, we can simply work backwards from the desired result until we arrive at either (1, 1) or a
contradiction. This should be O(M + N), although on average better.

Note that since x, y might represent an integer up to 10^50, the BigInteger class must be used to avoid overflow.
 */

import java.math.BigInteger;
import java.util.Optional;

public class Solution {
    public static String solution(String x, String y) {
        BigInteger count = BigInteger.ZERO;
        BigInteger m = new BigInteger(x);
        BigInteger f = new BigInteger(y);
        while (true) {
            //Handle stop condition
            if (m.equals(f)) {
                if (BigInteger.ONE.equals(m)) {
                    return count.toString();
                } else {
                    return "impossible";
                }
            }

            //Handle cycle calculation
            if (m.compareTo(f) > 0) {
                Optional<BigInteger[]> arr = cycleCalculation(m, f);
                if (arr.isPresent()) {
                    count = count.add(arr.get()[0]);
                    m = arr.get()[1];
                } else {
                    return "impossible";
                }
            } else {
                Optional<BigInteger[]> arr = cycleCalculation(f, m);
                if (arr.isPresent()) {
                    count = count.add(arr.get()[0]);
                    f = arr.get()[1];
                } else {
                    return "impossible";
                }
            }
        }
    }

    //Given BigInteger g > l, returns an array
    //a[0] is the value to add to count
    //a[1] is the new value for greater
    //If return is not present, means that impossible result encountered
    private static Optional<BigInteger[]> cycleCalculation(BigInteger g, BigInteger l) {
        BigInteger diff = g.subtract(l);
        if (diff.mod(l).equals(BigInteger.ZERO) && !BigInteger.ONE.equals(l)) {
            return Optional.empty();
        } else {
            BigInteger factor = diff.divide(l).add(BigInteger.ONE);
            if (BigInteger.ONE.equals(l)) {
                factor = diff;
            }
            return Optional.of(new BigInteger[]{factor, g.subtract(factor.multiply(l))});
        }
    }
}