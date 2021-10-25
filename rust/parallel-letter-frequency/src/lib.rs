use std::collections::HashMap;
use std::thread;

pub fn frequency(input: &[&str], worker_count: usize) -> HashMap<char, usize> {
    let num_partitions = worker_count.min(input.len());
    if num_partitions == 0 {return HashMap::new()}
    let partition_size = input.len() / num_partitions;
    let results = (0..num_partitions)
        .map(|i| {
            let shard = if i == num_partitions - 1 {
                input[(partition_size * i)..].iter()
                    .map(|s| s.to_string())
                    .collect::<Vec<String>>()
            } else {
                input[(partition_size * i)..(partition_size * (i + 1))].iter()
                    .map(|s| s.to_string())
                    .collect::<Vec<String>>()
            };
            thread::spawn(move || {
            shard.iter()
                .flat_map(|s| s.chars())
                .filter(|c| c.is_alphabetic())
                .fold(HashMap::new(), |mut map, c| {
                    *map.entry(c.to_ascii_lowercase()).or_insert(0) += 1;
                    map
                })
        })})
        .collect::<Vec<_>>();
    results.into_iter().fold(HashMap::new(), |mut map, handle| {
        handle.join().unwrap().iter().for_each(|(key, value)| *map.entry(key.to_ascii_lowercase()).or_insert(0) += *value);
        map
    })
}
