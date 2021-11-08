#[macro_export]
macro_rules! hashmap (
    ($(,)+) => {};
    ( $($x:expr => $y:expr),* $(,)?) => {
        {
            let mut hm = ::std::collections::HashMap::new();
            $(
                hm.insert($x, $y);
            )*
            hm
        }
    };
);
