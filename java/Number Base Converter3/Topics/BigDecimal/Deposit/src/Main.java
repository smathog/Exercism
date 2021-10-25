import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        BigDecimal startingAmount = new BigDecimal(scanner.nextLine());
        int rate = Integer.parseInt(scanner.nextLine());
        int years = Integer.parseInt(scanner.nextLine());
        BigDecimal finalAmount = startingAmount.multiply(BigDecimal.valueOf(1 + (double) rate / 100).pow(years));
        System.out.printf("Amount of money in the account: %s%n", finalAmount.setScale(2, RoundingMode.CEILING).toString());
    }
}