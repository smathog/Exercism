/// Returns an upper bound for the nth (1-indexed)
/// Holds for n >= 6
fn bound(n: u32) -> u32 {
    n * ((n as f32).ln() + (n as f32).ln().ln()).ceil() as u32
}

/// Finds the 0-indexed nth prime
/// Based on the Sieve of Eratosthenes
pub fn nth(n: u32) -> u32 {
    let target_n = n + 1;
    let mut counter = 0;
    let mut vec = vec![true; if target_n >= 6 { bound(target_n) + 1 } else { 14 } as u32 as usize];
    vec[0] = false;
    vec[1] = false;
    let mut val = 0;
    for i in 2..vec.len() {
        if !vec[i] {
            continue;
        } else {
            counter += 1;
            if target_n == counter {
                val = i as u32;
                break;
            } else {
                vec.iter_mut()
                    .skip(i)
                    .step_by(i)
                    .for_each(|b| *b = false);
            }
        }
    }
    val
}
