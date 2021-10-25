pub fn primes_up_to(upper_bound: u64) -> Vec<u64> {
    let mut arr = vec![true; upper_bound as usize + 1];
    arr[0] = false;
    arr[1] = false;
    let mut prime_vec = Vec::new();
    for i in 2..=(upper_bound as usize) {
        if arr[i] {
            prime_vec.push(i as u64);
            arr.iter_mut().skip(i)
                .step_by(i)
                .for_each(|val| *val = false)
        }
    }
    prime_vec
}
