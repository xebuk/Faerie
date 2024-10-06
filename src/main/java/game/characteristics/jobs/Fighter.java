package game.characteristics.jobs;

import game.characteristics.Job;

public class Fighter extends Job {

    public Fighter() {
        this.jobName = "Воин";
        this.startHp = 10;
        this.startArmorClass = 14;
        this.startAttackDice = "1d8";
    }
}
