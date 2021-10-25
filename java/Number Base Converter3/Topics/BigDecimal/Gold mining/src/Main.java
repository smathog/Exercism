import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new BigDecimal(scanner.nextLine()))
                .reduce(BigDecimal::add)
                .orElseThrow());
    }
}