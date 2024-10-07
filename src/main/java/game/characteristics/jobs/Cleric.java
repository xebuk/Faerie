package game.characteristics.jobs;

import game.characteristics.Job;

public class Cleric extends Job {

    public Cleric() {
        this.jobName = "Клерик";
        this.startHealth = 8;
        this.startArmorClass = 12;
        this.startAttackDice = "1d6";
    }
}
