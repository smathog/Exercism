use std::collections::HashMap;

const ASCII_LOWER: &str = "abcdefghijklmnopqrstuvwxyz";

/// Determine whether a sentence is a pangram.
pub fn is_pangram(sentence: &str) -> bool {
    let mut count_map:HashMap<char, usize> = ASCII_LOWER.chars()
        .fold(HashMap::new(), |mut map, c| {
            map.entry(c).or_default();
            map
        });
    sentence.chars()
        .map(|c| c.to_ascii_lowercase())
        .filter(char::is_ascii)
        .for_each(|c| *count_map.entry(c).or_default() += 1 );
    count_map.iter()
        .all(|(_, value)| *value > 0)
}
