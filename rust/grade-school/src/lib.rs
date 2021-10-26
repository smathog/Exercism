use std::collections::HashMap;

#[allow(clippy::new_without_default)]
pub struct School<'a> {
    roster: HashMap<u32, Vec<&'a str>>
}

impl School<'static> {
    pub fn new() -> School<'static> {
        School {
            roster: HashMap::new()
        }
    }

    pub fn add(&mut self, grade: u32, student: &'static str) {
        self.roster.entry(grade)
            .or_insert(Vec::new()).push(student)
    }

    pub fn grades(&self) -> Vec<u32> {
        let mut grades = self.roster.keys()
            .map(|k|*k)
            .collect::<Vec<u32>>();
        grades.sort();
        grades
    }

    pub fn grade(&self, grade: u32) -> Vec<String> {
        let mut grade = self.roster.get(&grade)
            .map(|v| v.iter().map(|s| s.to_string()).collect())
            .unwrap_or(Vec::new());
        grade.sort();
        grade
    }
}
