package game.characteristics.types;

import game.characteristics.EnemyType;

public class Mimic extends EnemyType {

    public Mimic() {
        this.entityName = "Мимик";

        this.alive = true;
        this.destructable = true;

        this.startHealth = 20;
        this.startArmorClass = 8;
        this.startAttackPower = 3;
    }
}
