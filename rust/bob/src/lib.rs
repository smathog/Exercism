pub fn reply(message: &str) -> &str {
    let upper_case = message.to_uppercase();
    if message.trim().ends_with("?") { //If is question
        if upper_case == message && message.chars().any(char::is_alphabetic) {
            "Calm down, I know what I'm doing!"
        } else {
            "Sure."
        }
    } else {
        if message.trim().is_empty() {
            "Fine. Be that way!"
        } else if upper_case == message && message.chars().any(char::is_alphabetic) {
            "Whoa, chill out!"
        } else {
            "Whatever."
        }
    }
}
