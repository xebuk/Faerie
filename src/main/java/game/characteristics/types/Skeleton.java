package game.characteristics.types;

import game.characteristics.EnemyType;

public class Skeleton extends EnemyType {

    public Skeleton() {
        this.entityName = "Скелет";

        this.alive = true;
        this.destructable = true;

        this.startHealth = 5;
        this.startArmorClass = 10;
        this.startAttackPower = 4;
    }
}
