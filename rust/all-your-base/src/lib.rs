#[derive(Debug, PartialEq)]
pub enum Error {
    InvalidInputBase,
    InvalidOutputBase,
    InvalidDigit(u32),
}

///
/// Convert a number between two bases.
///
/// A number is any slice of digits.
/// A digit is any unsigned integer (e.g. u8, u16, u32, u64, or usize).
/// Bases are specified as unsigned integers.
///
/// Return an `Err(.)` if the conversion is impossible.
/// The tests do not test for specific values inside the `Err(.)`.
///
///
/// You are allowed to change the function signature as long as all test still pass.
///
///
/// Example:
/// Input
///   number: &[4, 2]
///   from_base: 10
///   to_base: 2
/// Result
///   Ok(vec![1, 0, 1, 0, 1, 0])
///
/// The example corresponds to converting the number 42 from decimal
/// which is equivalent to 101010 in binary.
///
///
/// Notes:
///  * The empty slice ( "[]" ) is equal to the number 0.
///  * Never output leading 0 digits, unless the input number is 0, in which the output must be `[0]`.
///    However, your function must be able to process input with leading 0 digits.
///
pub fn convert(number: &[u32], from_base: u32, to_base: u32) -> Result<Vec<u32>, Error> {
    use Error::*;

    //Validate bases
    if from_base < 2 {
        return Err(InvalidInputBase);
    }
    if to_base < 2 {
        return Err(InvalidOutputBase);
    }

    //Get a base-10 representation of number
    let mut num_base_10 = 0;
    for (pow, &digit) in number.iter().rev().enumerate() {
        if digit >= from_base {
            return Err(InvalidDigit(digit));
        } else {
            num_base_10 += digit * from_base.pow(pow as u32)
        }
    }

    //Convert to output vector
    let mut out_vec = Vec::new();

    //Handle 0 case
    if num_base_10 == 0 {
        out_vec.push(0)
    }

    while num_base_10 != 0 {
        out_vec.push(num_base_10 % to_base);
        num_base_10 /= to_base
    }
    Ok(out_vec.into_iter().rev().collect())
}
