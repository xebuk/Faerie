package game.characteristics.types;

import game.characteristics.EnemyType;

public class Secret extends EnemyType {

    public Secret() {
        this.entityName = "Секрет";

        this.alive = false;
        this.destructable = true;

        this.startHealth = 1;
        this.startArmorClass = 0;
        this.startAttackPower = 0;
    }
}
