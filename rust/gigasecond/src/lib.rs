use chrono::{DateTime, Duration, Utc};

const GIGASECOND: i64 = 10i64.pow(9);

// Returns a Utc DateTime one billion seconds after start.
pub fn after(start: DateTime<Utc>) -> DateTime<Utc> {
    start + Duration::seconds(GIGASECOND)
}
