//RULES:
//Let S be the total number of LAMBS available.
//Then:
//1) s1 + s2 + ... + sn <= S
//2) s1 = 1
//3) s(n+1) <= 2 * sn
//4) For n > 2, s(n) >= s(n-1) + s(n-2)
//5) For n == 2, s(n) >= s(n-1)
//6) If another henchman can be afforded, they must be paid

//GOAL: Find the difference between the maximum and minimum
//possible number of henchmen to share the LAMBS.

//So, need to find minimum and maximum and just subtract.

import java.util.ArrayList;

public class Solution {
    private static final double PHI = (Math.sqrt(5) + 1) / 2;

    private static final ArrayList<Integer> maxList = new ArrayList<>();
    private static final ArrayList<Integer> minList = new ArrayList<>();
    private static int currentBalanceMin = 0;
    private static int currentBalanceMax = 0;

    private static void updateMax() {
        currentBalanceMax++;
        if (maxList.isEmpty() || maxList.size() == 1) {
            maxList.add(1);
            currentBalanceMax--;
        } else {
            int sizeLimit = maxList.get(maxList.size() - 1) + maxList.get(maxList.size() - 2);
            if (currentBalanceMax == sizeLimit) {
                currentBalanceMax -= sizeLimit;
                maxList.add(sizeLimit);
            }
        }
    }

    private static void updateMin() {
        currentBalanceMin++;
        if (minList.isEmpty() || minList.size() == 1) {
            minList.add(1);
            currentBalanceMin--;
        } else if (minList.size() == 2 && minList.get(1) == 1) {
            minList.set(1, 2);
            currentBalanceMin--;
        } else {
            int upperLimit = 2 * minList.get(minList.size() - 2);
            int lowerLimit = minList.get(minList.size() -1) + minList.get(minList.size() - 2);
            if (minList.get(minList.size() - 1) < upperLimit) {
                minList.set(minList.size() - 1, minList.get(minList.size() - 1) + 1);
                currentBalanceMin--;
            } else if (currentBalanceMin < lowerLimit) {
                //Do nothing; must wait for minimum size to add next element
            } else {
                minList.add(currentBalanceMin);
                currentBalanceMin = 0;
            }
        }
    }

    public static int solution(int total_lambs) {
        for (int i = 1; i < 1_000_000_000; ++i) {
            updateMin();
            updateMax();
            if (maxNumber(i) != maxList.size()) {
                System.out.println("ERROR MAX i: " + i + " " + maxNumber(i) + " " + maxList.size());
            }
            if (minNumber(i) != minList.size()) {
                System.out.println("ERROR MIN i: " + i + " " + minNumber(i) + " " + minList.size());
            }
        }
        return 1;
        //return maxNumber(total_lambs) - minNumber(total_lambs);
    }

    //For minimum: series is 1, 2, 4, 8, 16, ...
    //For n starting at 1, is Sum(s_i) i -> n = 2^(n) - 1
    //Where s_i = 2^(i-1)
    //(Proof by induction trivially.)
    private static int minNumber(int total) {
        //Find the largest value for n so that 2^(n) - 1 =< total:
        int n = (int) (Math.log(total + 1) / Math.log(2));

        //This implies there are at least n elements using the max distribution
        //Need to check if possibly enough leftover for another:
        if (total - (int) Math.pow(2, n) + 1 > (int) Math.pow(2, n - 1) + (int) Math.pow(2, n - 2)) {
            n++;
        }
        return n;
    }

    //For maximum: series is 1, 1, 2, 3, 5, 8, 13,...
    //This is just the fibonacci series which has a closed
    //form solution for the nth term:
    //S_n = [Phi(n)^n - (-Phi(n))^(-n)]/sqrt(5)
    //where Phi = [sqrt(5) + 1] / 2
    //(double arithmetic isn't exact but rounding to nearest
    //int should suffice; indeed, can substitute formula
    //S_n = round(Phi^n / sqrt(5)) for n > 0)
    //Series sum is merely SUM(f_n) = f_(n+2) - 1
    //(Proof by induction trivially)
    //For the purpose of finding the nearest S_n for some
    //integer K; if we write
    //k = round(Phi^n / sqrt(5))
    //We can approximate n by dropping the rounding and using
    //logarithms:
    //log(k * sqrt(5))/log(Phi) = n
    //If k was not a fibonnaci number, then n will be decimal;
    //round up and down to find the closest actually fibonnaci
    //but we are only interested in down since above exceeds
    //the total LAMBS available.
    private static int maxNumber(int total) {
        //Handle cases for total being one or two
        if (total == 1 || total == 2) {
            return total;
        } else {
            //Get n so that F_n - 1 is as close as possible but less than total
            int n = fibInverse(total + 1);
            if (nthFibTerm(n) > total + 1) {
                n--;
            }

            //Recall F_n - 1 = F_1 + ... + F_(n-2), so having found n, we really
            //want n - 2
            return n - 2;
        }
    }

    //Returns the nth fibonacci number using above formula
    private static int nthFibTerm(int n) {
        return (int) Math.round(Math.pow(PHI, n) / Math.sqrt(5));
    }

    //Returns the inverse approximation based on the rounded
    //Fibonnaci estimation formula
    //May be 1 too high; need to check in maxNumber
    private static int fibInverse(long n) {
        if (n == 1) {
            return 1;
        } else {
            return (int) Math.round(Math.log(n * Math.pow(5, 0.5)) / Math.log(PHI));
        }
    }
}