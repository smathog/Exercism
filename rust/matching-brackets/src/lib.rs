pub fn brackets_are_balanced(string: &str) -> bool {
    let mut stack = vec![];
    for c in string.chars() {
        if !['[', '{', '(', ']', '}', ')'].iter().any(|i| *i == c) {
            continue;
        }
        if ['[', '{', '('].iter().any(|i| *i == c) {
            stack.push(c);
        } else if [']', '}', ')'].iter().any(|i| *i == c) {
            if let Some(i) = stack.pop() {
                match i {
                    '[' if c == ']' => (),
                    '{' if c == '}' => (),
                    '(' if c == ')' => (),
                    _ => {return false;}
                }
            } else {
                return false;
            }
        }
    }
    stack.is_empty()
}
