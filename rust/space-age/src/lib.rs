const SECONDS_PER_EARTH_YEAR: f64 = 31557600f64;

#[derive(Debug)]
pub struct Duration {
    pub earth_years: f64,
}

impl From<u64> for Duration {
    fn from(s: u64) -> Self {
        Duration {
            earth_years: s as f64 / SECONDS_PER_EARTH_YEAR,
        }
    }
}

pub trait Planet {
    fn years_during(d: &Duration) -> f64;
}

pub struct Mercury;
pub struct Venus;
pub struct Earth;
pub struct Mars;
pub struct Jupiter;
pub struct Saturn;
pub struct Uranus;
pub struct Neptune;

//Macro to impl const earth_years_conversion for a given type:
macro_rules! associate {
    (for $(($T:ty, $V:literal)), +) => {
        $(impl $T {
            pub const EARTH_YEARS_CONVERSION: f64 = $V;
        })*
    }
}

associate!(for
    (Mercury, 0.2408467),
    (Venus, 0.61519726),
    (Earth, 1.0),
    (Mars, 1.8808158),
    (Jupiter, 11.862615),
    (Saturn, 29.447498),
    (Uranus, 84.016846),
    (Neptune, 164.79132));

macro_rules! impl_planet {
    (for $($T:ty), +) => {
        $(impl Planet for $T {
            fn years_during(d: &Duration) -> f64 {
                d.earth_years / <$T>::EARTH_YEARS_CONVERSION
            }
        })*
    }
}

impl_planet!(for Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune);
