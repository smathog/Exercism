use std::iter;
use crate::Classification::{Abundant, Perfect, Deficient};

#[derive(Debug, PartialEq, Eq)]
pub enum Classification {
    Abundant,
    Perfect,
    Deficient,
}

pub fn classify(num: u64) -> Option<Classification> {
    if num == 0 {return None}
    else {
        let aliquot_sum = (2..=((num as f64).sqrt() as u64))
            .filter(|n| num % *n == 0)
            .map(|n| if n * n == num {n} else {n + (num / n)})
            .chain(iter::once(1))
            .sum::<u64>();
        if aliquot_sum > num {
            Some(Abundant)
        } else if aliquot_sum == num && num != 1 {
            Some(Perfect)
        } else {
            Some(Deficient)
        }
    }
}
