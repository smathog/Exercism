use std::collections::HashMap;

pub struct CodonsInfo<'a> {
    codon_map: HashMap<&'a str, &'a str>,
}

impl<'a> CodonsInfo<'a> {
    pub fn name_for(&self, codon: &str) -> Option<&'a str> {
        if codon.len() != 3 {
            None
        } else {
            self.codon_map.get(codon).map(|s| *s)
        }
    }

    pub fn of_rna(&self, rna: &str) -> Option<Vec<&'a str>> {
        rna.chars()
            .collect::<Vec<_>>()
            .chunks(3)
            .map(|w| self.name_for(w.iter().collect::<String>().as_str()))
            .take_while(|op| if op.is_some() {!(op.unwrap() == "stop codon")} else {true})
            .collect::<Option<Vec<_>>>()
    }
}

pub fn parse<'a>(pairs: Vec<(&'a str, &'a str)>) -> CodonsInfo<'a> {
    CodonsInfo {
        codon_map: pairs.iter()
            .map(|tuple| *tuple)
            .collect()
    }
}
