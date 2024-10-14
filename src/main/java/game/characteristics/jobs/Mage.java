package game.characteristics.jobs;

import game.characteristics.Job;
import game.items.armors.Robe;
import game.items.weapons.Staff;

public class Mage extends Job {

    public Mage() {
        this.jobName = "Маг";
        this.weaponType = new Staff();
        this.armorType = new Robe();

        this.startHealth = 6;
    }
}
