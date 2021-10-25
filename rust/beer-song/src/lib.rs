use std::ops::Add;

pub fn verse(n: u32) -> String {
    match n {
        2..=u32::MAX => format!("{0} bottles of beer on the wall, {0} bottles of beer.\
        \nTake one down and pass it around, {1} bottle{2} of beer on the wall.\n",
        n, n - 1, if n - 1 != 1 {"s"} else {""}),
        1 => String::from("1 bottle of beer on the wall, 1 bottle of beer.\
        \nTake it down and pass it around, no more bottles of beer on the wall.\n"),
        0 => String::from("No more bottles of beer on the wall, no more bottles of beer.\
        \nGo to the store and buy some more, 99 bottles of beer on the wall.\n")
    }
}

pub fn sing(start: u32, end: u32) -> String {
    (end..=start).rev()
        .map(|n| verse(n).add( if n != end {"\n"} else {""}))
        .collect()
}
