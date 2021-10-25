pub fn is_armstrong_number(num: u32) -> bool {
    let mut digits: Vec<u32> = Vec::new();
    let mut num_copy = num;
    while num_copy > 0 {
        digits.push(num_copy % 10u32);
        num_copy /= 10u32;
    }
    digits.iter().map(|x| x.pow(digits.len() as u32)).sum::<u32>() == num
}
