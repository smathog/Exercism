use gcd::Gcd;

use std::collections::HashSet;

pub fn find(sum: u32) -> HashSet<[u32; 3]> {
    (1u32..)
        .map(|i| (i, ((i + 1)..)
            .filter(move |&j| (i % 2) + (j % 2) == 1) //One odd, one even
            .filter(move |&j| j.gcd(i) == 1))) //enforce coprimality)
        .take_while(|i| {
            let a = (i.0 + 1).pow(2) - i.0.pow(2);
            let b = 2 * (i.0 + 1) * i.0;
            let c = (i.0 + 1).pow(2) + i.0.pow(2);
            a + b + c  <= sum
        })
        .fold(HashSet::new(), |mut set, (i, j)| {
            for element in j {
                let a = element.pow(2) - i.pow(2);
                let b = 2 * element * i;
                let c = element.pow(2) + i.pow(2);
                if a + b + c > sum {
                    break
                } else if sum % (a + b + c) == 0 {
                    let k = sum / (a + b + c);
                    let mut arr = [k * a, k * b, k * c];
                    arr.sort();
                    set.insert(arr);
                }
            }
            set
        })
}
