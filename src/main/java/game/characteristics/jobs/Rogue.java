package game.characteristics.jobs;

import game.characteristics.Job;
import game.items.armors.LightArmor;
import game.items.weapons.Dagger;

public class Rogue extends Job {

    public Rogue() {
        this.jobName = "Плут";
        this.weaponType = new Dagger();
        this.armorType = new LightArmor();

        this.startHealth = 8;
    }
}
