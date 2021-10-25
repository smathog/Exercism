use std::collections::HashSet;

pub fn check(candidate: &str) -> bool {
    let mut letter_set = HashSet::new();
    for c in candidate.chars()
        .filter(|c| c.is_alphabetic())
        .map(|c| c.to_ascii_lowercase()) {
        if !letter_set.insert(c) {return false}
    }
    true
}
