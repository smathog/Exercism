pub fn raindrops(n: u32) -> String {
    if n % 3 == 0 || n % 5 == 0 || n % 7 == 0 {
        let mut result = String::new();
        if n % 3 == 0 {
            result.push_str("Pling");
        }
        if n % 5 == 0 {
            result.push_str("Plang");
        }
        if n % 7 == 0 {
            result.push_str("Plong");
        }
        result
    } else {
        n.to_string()
    }
}
