pub fn find(array: &[i32], key: i32) -> Option<usize> {
    match array.len() {
        0 => None,
        1 => if array[0] == key {Some(0)} else {None},
        _ => {
            let mid = array.len() / 2;
            if array[mid] == key {Some(mid)}
            else if array[mid] > key {
                find(&array[0..mid], key)
            } else { //array[mid] > key
                if let Some(x) = find(&array[(mid + 1)..], key) {
                    Some(mid + 1 + x)
                } else {None}
            }
        }
    }
}
