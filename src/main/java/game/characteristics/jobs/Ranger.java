package game.characteristics.jobs;

import game.characteristics.Job;

public class Ranger extends Job {

    public Ranger() {
        this.jobName = "Следопыт";
        this.startHealth = 8;
        this.startArmorClass = 6;
        this.startAttackDice = "1d10";
    }
}
