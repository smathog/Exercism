use std::collections::HashSet;

pub fn sum_of_multiples(limit: u32, factors: &[u32]) -> u32 {
    factors.iter()
        .flat_map(|n| (*n..)
            .step_by(if *n == 0 {1} else {*n as usize})
            .take_while(move |i| if *n == 0 {*i == 0} else {*i < limit}))
        .collect::<HashSet<u32>>()
        .iter()
        .sum()
}
