pub fn series(digits: &str, len: usize) -> Vec<String> {
    if digits.len() < len {
        vec![]
    } else {
        (0..=(digits.len() - len))
            .map(|i| String::from(&digits[i..(i + len)]))
            .collect()
    }
}
