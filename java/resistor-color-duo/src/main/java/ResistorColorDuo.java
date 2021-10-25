import java.util.Arrays;
import java.util.stream.Collectors;

class ResistorColorDuo {
    private enum Colors {
        black(0),
        brown(1),
        red(2),
        orange(3),
        yellow(4),
        green(5),
        blue(6),
        violet(7),
        grey(8),
        white(9);

        private final int value;
        public int getValue() {
            return this.value;
        }

        Colors(int value) {
            this.value = value;
        }
    }
    int value(String[] colors) {
        return Integer.parseInt(Arrays.stream(colors)
                .limit(2)
                .map(s -> Integer.toString(Colors.valueOf(s).getValue()))
                .collect(Collectors.joining()));
    }
}
