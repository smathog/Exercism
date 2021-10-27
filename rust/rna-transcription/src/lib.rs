#[derive(Debug, PartialEq)]
pub struct Dna {
    pattern: Vec<char>,
}

#[derive(Debug, PartialEq)]
pub struct Rna {
    pattern: Vec<char>,
}

impl Dna {
    pub fn new(dna: &str) -> Result<Dna, usize> {
        Ok(Dna {
            pattern: dna.chars()
            .enumerate()
            .map( | (i,
            c) |
            match c.to_ascii_uppercase() {
            'G' => Ok('G'),
            'C' => Ok('C'),
            'T' => Ok('T'),
            'A' => Ok('A'),
            _ => Err(i),
            }).collect::<Result<Vec<_>, usize>>()?
        })
    }

    pub fn into_rna(self) -> Rna {
        Rna {
            pattern: self.pattern.iter()
                .map(|c| match *c {
                    'G' => 'C',
                    'C' => 'G',
                    'T' => 'A',
                    'A' => 'U',
                    _ => '_', //never happens, so who cares
                })
                .collect()
        }
    }
}

impl Rna {
    pub fn new(rna: &str) -> Result<Rna, usize> {
        Ok(Rna {
            pattern: rna.chars()
                .enumerate()
                .map( | (i,
                            c) |
                    match c.to_ascii_uppercase() {
                        'G' => Ok('G'),
                        'C' => Ok('C'),
                        'U' => Ok('U'),
                        'A' => Ok('A'),
                        _ => Err(i),
                    }).collect::<Result<Vec<_>, usize>>()?
        })
    }
}
