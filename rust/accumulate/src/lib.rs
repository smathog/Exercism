/// What should the type of _function be?
pub fn map<T, U>(input: Vec<T>, mut function: impl FnMut(T) -> U ) -> Vec<U> {
    let mut output = Vec::new();
    for val in input {
        output.push(function(val));
    }
    output
}
