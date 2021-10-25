import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ResistorColor {
    private static final HashMap<String, Integer> colorMap = IntStream.range(0, colors().length)
            .boxed()
            .collect(Collectors.toMap(i -> colors()[i], Function.identity(), (i, j) -> i, HashMap::new));

    static int colorCode(String color) {
        return colorMap.get(color);
    }

    static String[] colors() {
        return new String[] {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white"};
    }
}
