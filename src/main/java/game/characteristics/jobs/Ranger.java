package game.characteristics.jobs;

import game.characteristics.Job;
import game.items.armors.LightArmor;
import game.items.weapons.Bow;

public class Ranger extends Job {

    public Ranger() {
        this.jobName = "Следопыт";
        this.weaponType = new Bow();
        this.armorType = new LightArmor();

        this.startHealth = 8;
    }
}
