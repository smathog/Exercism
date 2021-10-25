const MAX_RATE: f64 = 1.00f64;
const MODERATE_RATE: f64 = 0.90f64;
const MINIMUM_RATE: f64 = 0.77f64;
const BASE_RATE: i64 = 221;

pub fn production_rate_per_hour(speed: u8) -> f64 {
    let base_yield = (BASE_RATE * speed as i64) as f64;
    match speed {
        1..=4 => base_yield * MAX_RATE,
        5..=8 => base_yield * MODERATE_RATE,
        9..=10 => base_yield * MINIMUM_RATE,
        _ => 0f64
    }
}

pub fn working_items_per_minute(speed: u8) -> u32 {
    (production_rate_per_hour(speed) / 60f64) as u32
}
