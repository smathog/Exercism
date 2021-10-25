use std::collections::HashMap;

pub fn can_construct_note(magazine: &[&str], note: &[&str]) -> bool {
    let magazine_map = to_hashmap(magazine);
    let note_map = to_hashmap(note);
    note_map.iter()
        .all(|(key, value)|
            magazine_map.contains_key(*key)
             && *value <= *magazine_map.get(*key).unwrap_or(&0)
        )
}

fn to_hashmap<'a>(arr: &'a [&str]) -> HashMap<&'a str, usize> {
    arr.iter()
        .fold(HashMap::new(), |mut map, key| {
            *map.entry(*key).or_insert(0) += 1;
            map
        })
}
