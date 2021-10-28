/// Determines whether the supplied string is a valid ISBN number
pub fn is_valid_isbn(isbn: &str) -> bool {
    isbn.chars()
        .filter(|c| !(*c == '-'))
        .enumerate()
        .map(|(i, c)| {
            if (i < 9 && !c.is_numeric()) || (i == 9 && !(c.is_numeric() || c == 'X')) {
                None
            } else {
                if c == 'X' {
                    Some(10)
                } else {
                    c.to_digit(10)
                        .map(|n| n * (10u32 - i as u32))
                }
            }
        })
        .fold(Some((Some(0), 0i32)), |mut total, val| {
            total = if let (Some(t), Some(n)) = (total.unwrap().0, val) {
                Some((Some(t + n), total.unwrap().1 + 1))
            } else {
                Some((None, total.unwrap().1 + 1))
            };
            total
        })
        .map_or(false, |total|
            total.0.map_or(false, |n| n % 11 == 0)
            && total.1 == 10)
}
