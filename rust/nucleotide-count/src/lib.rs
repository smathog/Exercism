use std::collections::HashMap;

pub fn count(nucleotide: char, dna: &str) -> Result<usize, char> {
    nucleotide_counts(dna)
        .and_then(|map| map.get(&nucleotide)
            .map(|val| *val)
            .ok_or(nucleotide))
}

pub fn nucleotide_counts(dna: &str) -> Result<HashMap<char, usize>, char> {
    let mut counts = [0usize; 4];
    for c in dna.chars() {
        match c.to_ascii_uppercase() {
            'A' => counts[0] += 1,
            'C' => counts[1] += 1,
            'G' => counts[2] += 1,
            'T' => counts[3] += 1,
            _ => return Result::Err(c)
        }
    }
    Ok(['A', 'C', 'G', 'T'].into_iter().zip(counts.into_iter()).collect())
}
