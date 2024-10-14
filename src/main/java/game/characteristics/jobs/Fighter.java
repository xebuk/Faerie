package game.characteristics.jobs;

import game.characteristics.Job;
import game.items.armors.HeavyArmor;
import game.items.weapons.HeavySword;

public class Fighter extends Job {

    public Fighter() {
        this.jobName = "Воин";
        this.weaponType = new HeavySword();
        this.armorType = new HeavyArmor();

        this.startHealth = 10;
    }
}
