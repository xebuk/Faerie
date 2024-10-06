package game.characteristics.types;

import game.characteristics.EnemyType;

public class Rock extends EnemyType {

    public Rock() {
        this.entityName = "Камень";

        this.alive = false;
        this.destructable = false;

        this.startHealth = 0;
        this.startArmorClass = 0;
        this.startAttackPower = 0;
    }
}
