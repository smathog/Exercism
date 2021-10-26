pub struct Luhn {
    digits: Vec<u32>
}

impl Luhn {
    pub fn is_valid(&self) -> bool {
        if self.digits.len() <= 1 {
            false
        } else {
            let mut copy = self.digits.clone();
            copy.iter_mut()
                .rev()
                .skip(1)
                .step_by(2)
                .for_each(|d| *d = if *d * 2 > 9 { *d * 2 - 9 } else { *d * 2 });
            copy.iter().sum::<u32>() % 10 == 0
        }
    }
}

impl<T> From<T> for Luhn
where T: ToString {
    fn from(input: T) -> Self {
        Luhn {
            digits: if input.to_string()
                .chars()
                .any(|c| !(c.is_numeric() || c.is_whitespace())) {
                Vec::new()
            } else {
                input.to_string().chars()
                    .filter_map(|c| c.to_digit(10))
                    .collect()
            }
        }
    }
}
