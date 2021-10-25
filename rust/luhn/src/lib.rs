/// Check a Luhn checksum.
pub fn is_valid(code: &str) -> bool {
    //Character validity check
    if code.chars().any(|c| !(c.is_numeric() || c == ' ')) {return false}

    let mut nums = code.chars()
        .filter(|c| c.is_numeric())
        .map(|c| c.to_digit(10).unwrap())
        .collect::<Vec<u32>>();

    //Length validity check
    if nums.len() <= 1 {return false}

    //Luhn Step 1
    nums.iter_mut()
        .rev()
        .skip(1)
        .step_by(2)
        .for_each(|n| *n = if *n * 2 > 9 {*n * 2 - 9} else {*n * 2});

    //Luhn Step 2
    nums.iter().sum::<u32>() % 10 == 0
}
