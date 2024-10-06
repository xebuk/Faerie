package game.characteristics.types;

import game.characteristics.EnemyType;

public class Zombie extends EnemyType {

    public Zombie() {
        this.entityName = "Зомби";

        this.alive = true;
        this.destructable = true;

        this.startHealth = 10;
        this.startArmorClass = 10;
        this.startAttackPower = 3;
    }
}
