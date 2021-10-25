import java.util.List;
import java.util.Optional;

class Main {
    public static void main(String[] args) {
        ValueProvider provider = new ValueProvider();
        List<Optional<Integer>> values = provider.getValues();
        int result = values.stream()
                .filter(Optional::isPresent)
                .mapToInt(Optional::get)
                .sum();
        System.out.println(result);
    }
}