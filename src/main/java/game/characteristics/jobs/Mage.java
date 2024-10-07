package game.characteristics.jobs;

import game.characteristics.Job;

public class Mage extends Job {

    public Mage() {
        this.jobName = "Маг";
        this.startHealth = 6;
        this.startArmorClass = 6;
        this.startAttackDice = "1d10";
    }
}
