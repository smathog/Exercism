use std::collections::HashSet;

pub struct Allergies {
    allergies: HashSet<Allergen>
}

#[derive(Copy, Clone, Debug, PartialEq, Eq, Hash)]
pub enum Allergen {
    Eggs,
    Peanuts,
    Shellfish,
    Strawberries,
    Tomatoes,
    Chocolate,
    Pollen,
    Cats,
}

impl Allergies {
    pub fn new(score: u32) -> Self {
        use Allergen::*;
        let mut score_copy = score;
        let mut allergies : HashSet<Allergen> = HashSet::new();
        while score_copy > 0 {
            let exponent = (score_copy as f32).log2() as u32;
            let component = 1 << exponent;
            if component <= 128 {
                allergies.insert(match component {
                    1 => Eggs,
                    2 => Peanuts,
                    4 => Shellfish,
                    8 => Strawberries,
                    16 => Tomatoes,
                    32 => Chocolate,
                    64 => Pollen,
                    128 => Cats,
                    _ => panic!("This shouldn't happen!"),
                });
            }
            score_copy -= component;
        }
        Self {
            allergies,
        }
    }

    pub fn is_allergic_to(&self, allergen: &Allergen) -> bool {
        return self.allergies.contains(allergen)
    }

    pub fn allergies(&self) -> Vec<Allergen> {
        return self.allergies.iter()
            .map(|a| *a)
            .collect()
    }
}
