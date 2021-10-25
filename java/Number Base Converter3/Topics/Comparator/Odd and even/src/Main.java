import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Utils {

    public static List<Integer> sortOddEven(List<Integer> numbers) {
        return numbers.stream()
                .sorted(((Comparator<Integer>) (o1, o2) -> {
                    boolean firstEven = o1 % 2 == 0;
                    boolean secondEven = o2 % 2 == 0;
                    if (firstEven && secondEven) {
                        return 0;
                    } else if (firstEven) {
                        return 1;
                    } else if (!secondEven) {
                        return 0;
                    } else {
                        return -1;
                    }
                }).thenComparing((o1, o2) -> {
                    boolean firstEven = o1 % 2 == 0;
                    boolean secondEven = o2 % 2 == 0;
                    if (firstEven && secondEven) {
                        return Integer.compare(o2, o1);
                    } else if (!firstEven && !secondEven) {
                        return Integer.compare(o1, o2);
                    } else {
                        return 0;
                    }
                }))
                .collect(Collectors.toList());
    }
}