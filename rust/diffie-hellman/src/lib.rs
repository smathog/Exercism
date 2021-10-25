///Modular exponentiation based on psuedocode from
/// https://en.wikipedia.org/wiki/Modular_exponentiation
fn mod_exp(mut base: u64, modulus: u64, mut exp: u64) -> u64 {
    if modulus == 1 {
        0
    } else {
        let mut value = 1;
        base = base % modulus;
        while exp > 0 {
            if exp % 2 == 1 {
                value = ((value as u128 * base as u128) % modulus as u128) as u64;
            }
            exp = exp >> 1;
            base = ((base as u128 * base as u128) % modulus as u128) as u64;
        }
        value
    }
}

pub fn private_key(p: u64) -> u64 {
    fastrand::seed(10); //for consistent random numbers
    fastrand::u64(2..p)
}

pub fn public_key(p: u64, g: u64, a: u64) -> u64 {
    mod_exp(g, p, a)
}

pub fn secret(p: u64, b_pub: u64, a: u64) -> u64 {
    mod_exp(b_pub, p, a)
}
