#[derive(Debug, PartialEq)]
pub enum Comparison {
    Equal,
    Sublist,
    Superlist,
    Unequal,
}

pub fn sublist<T: PartialEq>(_first_list: &[T], _second_list: &[T]) -> Comparison {
    use Comparison::*;
    let mut first_comp = false;
    let mut second_comp = false;
    if _first_list.len() <= _second_list.len() {
        first_comp = kmp(_first_list, _second_list);
    }
    if _second_list.len() <= _first_list.len() {
        second_comp = kmp(_second_list, _first_list);
    }
    match (first_comp, second_comp) {
        (true, true) => Equal,
        (false, true) => Superlist,
        (true, false) => Sublist,
        (false, false) => Unequal,
    }

}
fn kmp<T: PartialEq>(smaller: &[T], larger: &[T]) -> bool {
    //Handle empty subcase
    if smaller.is_empty() { return true;}

    //Use kmp-based algorithm to check if substring is present
    let mut index = 0;
    let mut offset = 0;
    let kmp_table = kmp_psa(smaller);

    while index < larger.len() {
        if smaller[offset] != larger[index] {
            if offset == 0 {
                index += 1;
                offset += 1;
            } else {
                offset = kmp_table[offset - 1];
            }
        } else {
            index += 1;
            offset += 1;
            if offset == smaller.len() {
                return true;
            }
        }
    }
    false
}

//Calculates the pattern prefix-suffix vec for the kmp algorithm
fn kmp_psa<T: PartialEq>(pattern: &[T]) -> Vec<usize> {
    let mut i = 1;
    let mut j = 0;
    let mut psa = vec![0];
    while i < pattern.len() {
        if pattern[i] == pattern[j] {
            psa.push(j + 1);
            i += 1;
            j += 1;
        } else {
            if j == 0 {
                psa.push(0);
                i += 1;
            } else {
                j = psa[j - 1];
            }
        }
    }
    psa
}


