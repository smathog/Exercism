use std::iter;

pub struct PascalsTriangle {
    row_count: u32
}

impl PascalsTriangle {
    pub fn new(row_count: u32) -> Self {
        PascalsTriangle {
            row_count
        }
    }

    pub fn rows(&self) -> Vec<Vec<u32>> {
        let mut triangle_rows = Vec::new();
        for i in 0..self.row_count {
            if i == 0 {triangle_rows.push(vec![1])}
            else if i == 1 {triangle_rows.push(vec![1, 1])}
            else {
                triangle_rows.push(iter::once(1)
                    .chain(triangle_rows.last().unwrap().windows(2)
                        .map(|slice| slice.iter().sum()))
                    .chain(iter::once(1))
                    .collect())
            }
        }
        triangle_rows
    }
}
