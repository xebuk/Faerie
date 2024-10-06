package game.characteristics.jobs;

import game.characteristics.Job;

public class Rogue extends Job {

    public Rogue() {
        this.jobName = "Плут";
        this.startHp = 8;
        this.startArmorClass = 8;
        this.startAttackDice = "1d12";
    }
}
