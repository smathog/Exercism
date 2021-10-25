pub fn annotate(minefield: &[&str]) -> Vec<String> {
    (0..minefield.len())
        .map(|row| (row, minefield[row].len()))
        .map(|(row, x)| {
            (0..x).map(|col| match count_mines(minefield, row, col) {
                None => char::from(b'*'),
                Some(0) => char::from(b' '),
                Some(num) => char::from_digit(num as u32, 10).unwrap(),
            }).collect::<String>()})
        .collect()

}

///Returns none if looking at a mine, otherwise returns Some(x)
///where x is all adjacent.
fn count_mines(minefield: &[&str], row: usize, col: usize) -> Option<u8> {
    if minefield[row].as_bytes()[col] == b'*' {
        None
    } else {
        let mut counter = 0;
        //Horizontal and Vertical
        if row > 0 && minefield[row - 1].as_bytes()[col] == b'*' {counter += 1}
        if row < minefield.len() - 1 && minefield[row + 1].as_bytes()[col] == b'*' {counter += 1}
        if col > 0 && minefield[row].as_bytes()[col - 1] == b'*' {counter += 1}
        if col < minefield[row].len() - 1 && minefield[row].as_bytes()[col + 1] == b'*' {counter += 1}

        //Diagonals
        if row > 0 && col > 0 && minefield[row - 1].as_bytes()[col - 1] == b'*' {counter += 1}
        if row < minefield.len() - 1 && col > 0 && minefield[row + 1].as_bytes()[col - 1] == b'*' {counter += 1}
        if row > 0 && col < minefield[row].len() - 1 && minefield[row - 1].as_bytes()[col + 1] == b'*' {counter += 1}
        if row < minefield.len() - 1 && col < minefield[row].len() - 1 && minefield[row + 1].as_bytes()[col + 1] == b'*' {counter += 1}
        Some(counter)
    }
}
