use std::collections::HashSet;
use std::collections::HashMap;

pub fn anagrams_for<'a>(word: &str, possible_anagrams: &[&'a str]) -> HashSet<&'a str> {
    let word_freq_map = word.to_lowercase()
        .chars()
        .fold(HashMap::new(), |mut map, c| {
            *map.entry(c).or_insert(0) += 1;
            map
        });

    (*possible_anagrams).iter()
        .filter(|s| s.to_lowercase() != word.to_lowercase())
        .filter(|s| {
            let freq_map = s.to_lowercase()
                .chars()
                .fold(HashMap::new(), |mut map, c| {
                    *map.entry(c).or_insert(0) += 1;
                    map
                });
            freq_map == word_freq_map
        })
        .map(|s| *s)
        .collect::<HashSet<&'a str>>()
}
