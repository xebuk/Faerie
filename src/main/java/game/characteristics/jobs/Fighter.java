package game.characteristics.jobs;

import game.characteristics.Job;

public class Fighter extends Job {

    public Fighter() {
        this.startHp = 10;
        this.startArmorClass = 14;
        this.startAttackRoll = "1d8";
    }
}
