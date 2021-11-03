use std::ops::Sub;
use std::cmp::{PartialEq, PartialOrd};
use std::default::Default;
use std::iter::Sum;
use std::marker::Copy;

pub struct Triangle<T> {
    sides: [T; 3]
}

impl<T> Triangle<T>
    where
        T: Sub<Output = T> + PartialOrd + PartialEq + Default + Copy + Sum {
    pub fn build(sides: [T; 3]) -> Option<Triangle<T>> {
        if sides.iter().any(|&s| s <= Default::default()) {
            None
        } else if sides.iter()
            .any(|&s| s > sides.into_iter().sum::<T>() - s) {
            None
        } else {
            Some(Triangle {
                sides
            })
        }
    }

    pub fn is_equilateral(&self) -> bool {
        self.sides.iter().any(|&s| self.sides.iter().filter(|&&s2| s == s2).count() == 3)
    }

    pub fn is_scalene(&self) -> bool {
        self.sides.iter().all(|&s| self.sides.iter().filter(|&&s2| s == s2).count() == 1)
    }

    pub fn is_isosceles(&self) -> bool {
        self.sides.iter().any(|&s| self.sides.iter().filter(|&&s2| s == s2).count() == 2)
    }
}
