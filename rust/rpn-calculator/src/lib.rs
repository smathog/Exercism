#[derive(Debug)]
pub enum CalculatorInput {
    Add,
    Subtract,
    Multiply,
    Divide,
    Value(i32),
}

pub fn evaluate(inputs: &[CalculatorInput]) -> Option<i32> {
    use CalculatorInput::*;
    let mut stack = Vec::new();
    for item in inputs {
        match item {
            Value(x) => stack.push(Value(*x)),
            _ => if let (Some(Value(x)), Some(Value(y))) = (stack.pop(), stack.pop()) {
                match item {
                    Add => stack.push(Value(x + y)),
                    Subtract => stack.push(Value(y - x)),
                    Multiply => stack.push(Value(x * y)),
                    Divide => stack.push(Value(y / x)),
                    _ => { break },
                }
            } else { break },
        }
    }
    if stack.len() == 1 {
        if let Some(Value(x)) = stack.pop() {Some(x)} else { None }
    } else {None}

}
