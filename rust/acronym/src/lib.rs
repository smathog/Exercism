pub fn abbreviate(phrase: &str) -> String {
    phrase.split(|c: char| c.is_ascii_whitespace() || c == '-' || c == '_')
        .map(|s: &str| s.chars().filter(|c| c.is_alphabetic()).collect::<String>())
        .filter(|s| !s.is_empty())
        .map(|s| {
                let bytes = s.as_bytes();
                if s.chars().filter(char::is_ascii_uppercase).count() <= 1
                    || bytes[bytes.len() - 1].is_ascii_uppercase() { //Just take first letter and capitalize
                    String::from(char::from(bytes[0].to_ascii_uppercase()))
                } else {
                    s.chars().filter(char::is_ascii_uppercase).collect::<String>()
                }
        })
        .collect()
}
