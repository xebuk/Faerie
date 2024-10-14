package game.characteristics.jobs;

import game.characteristics.Job;
import game.items.armors.MediumArmor;
import game.items.weapons.Mace;

public class Cleric extends Job {

    public Cleric() {
        this.jobName = "Клерик";
        this.weaponType = new Mace();
        this.armorType = new MediumArmor();

        this.startHealth = 8;
    }
}
