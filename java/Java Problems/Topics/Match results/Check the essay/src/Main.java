import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class FindTheKeys {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine() + " ";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        HashSet<Character> vowelSet = Stream.of('a', 'e','i', 'o', 'u')
                .collect(Collectors.toCollection(HashSet::new));
        String vowels = vowelSet.stream()
                .map(c -> c.toString() + c.toString().toUpperCase())
                .collect(Collectors.joining());
        String consonants = alphabet.chars()
                .filter(c -> !vowelSet.contains((char) c))
                .mapToObj(c -> String.valueOf((char) c))
                .map(s -> s + s.toUpperCase())
                .collect(Collectors.joining());
        String searchPattern = String.format("(?i:the\\s+key\\s+is\\s+)([\\d%1$s]+|[?!#%2$s]+)\\s", consonants, vowels);
        //String searchPattern = "(?i:the\\s+key\\s+is\\s+)([\\d" + consonants + "]+|[?!#" + vowels + "]+)\\s";
        Pattern pattern = Pattern.compile(searchPattern);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}