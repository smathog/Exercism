// This stub file contains items which aren't used yet; feel free to remove this module attribute
// to enable stricter warnings.
#![allow(unused)]

pub struct Player {
    pub health: u32,
    pub mana: Option<u32>,
    pub level: u32,
}

impl Player {
    pub fn revive(&self) -> Option<Player> {
        match self.health {
            0 => Some(Player{
                health: 100,
                mana: if self.level >= 10 {Some(100)} else {None},
                level: self.level,
            }),
            _ => None,
        }
    }

    pub fn cast_spell(&mut self, mana_cost: u32) -> u32 {
        match self.mana {
            None => {
                self.health = std::cmp::max(self.health as i64 - mana_cost as i64,
                                            0i64) as u32;
                0
            },
            Some(x) if x < mana_cost => {
                self.mana = Some(x);
                0
            },
            Some(x) => {
                self.mana = Some(x - mana_cost);
                2 * mana_cost
            },
        }
    }
}
