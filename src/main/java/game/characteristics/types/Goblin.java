package game.characteristics.types;

import game.characteristics.EnemyType;

public class Goblin extends EnemyType {

    public Goblin() {
        this.entityName = "Гоблин";

        this.alive = true;
        this.destructable = true;

        this.startHealth = 10;
        this.startArmorClass = 5;
        this.startAttackPower = 2;
    }
}
