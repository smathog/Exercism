use crate::Error::{SpanTooLong, InvalidDigit};

#[derive(Debug, PartialEq)]
pub enum Error {
    SpanTooLong,
    InvalidDigit(char),
}

pub fn lsp(string_digits: &str, span: usize) -> Result<u64, Error> {
    //Validate all chars
    if let Some(invalid) = string_digits.chars()
        .find(|c| !c.is_numeric()) {
        Err(InvalidDigit(invalid))
    }else if span > string_digits.len() {
        Err(SpanTooLong)
    } else if span == 0 {
        Ok(1)
    } else {
        Ok(string_digits.chars()
            .filter_map(|c| c.to_digit(10).map(|n| n as u64))
            .collect::<Vec<u64>>()
            .windows(span)
            .map(|slice| slice.iter().product())
            .max().unwrap())
    }
}
