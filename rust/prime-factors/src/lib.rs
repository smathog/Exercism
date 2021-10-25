pub fn factors(mut n: u64) -> Vec<u64> {
    //Not the most efficient algorithm (sieve would likely be better? but it works)
    let mut factors = Vec::new();
    for i in 2..=n {
        while n % i == 0 {
            n /= i;
            factors.push(i);
        }
        if n == 1 {
            break;
        }
    }
    factors
}
