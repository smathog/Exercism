use std::fmt;
use std::fmt::Formatter;

#[derive(Debug)]
pub struct Clock {
    hours: i32,
    minutes: i32,
}

impl Clock {
    pub fn new(mut hours: i32, mut minutes: i32) -> Self {
        let mut total_minutes = hours * 60 + minutes;
        total_minutes = total_minutes.rem_euclid(60 * 24);
        minutes = total_minutes % 60;
        hours = (total_minutes - minutes) / 60;
        Clock {
            hours,
            minutes,
        }
    }

    pub fn add_minutes(&self, mut minutes: i32) -> Self {
        let mut total_minutes = self.hours * 60 + self.minutes + minutes;
        total_minutes = total_minutes.rem_euclid(60 * 24);
        minutes = total_minutes % 60;
        let hours = (total_minutes - minutes) / 60;
        Clock {
            hours,
            minutes,
        }
    }
}

impl fmt::Display for Clock {
    fn fmt(&self, f: &mut Formatter<'_>) -> fmt::Result {
        write!(f, "{:0width$}:{:0width$}", self.hours, self.minutes, width=2)
    }
}

impl PartialEq<Self> for Clock {
    fn eq(&self, other: &Self) -> bool {
        self.hours == other.hours && self.minutes == other.minutes
    }
}
