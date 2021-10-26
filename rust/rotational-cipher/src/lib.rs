pub fn rotate(input: &str, key: i8) -> String {
    input.chars()
        .map(|c| {
            if c.is_alphabetic() {
                let base = if c.is_uppercase() {'A' as u8} else {'a' as u8};
                ((c as u8 + key.rem_euclid(26) as u8 - base) % 26u8 + base) as char
            } else {c}
        })
        .collect()
}
