import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        BigDecimal sum = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new BigDecimal(scanner.nextLine()))
                .reduce(BigDecimal::add)
                .orElseThrow();
        System.out.println(sum.divide(new BigDecimal(3), RoundingMode.DOWN).setScale(0, RoundingMode.DOWN));
    }
}