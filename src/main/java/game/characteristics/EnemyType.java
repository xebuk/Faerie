package game.characteristics;

import java.io.Serializable;

public class EnemyType implements Serializable {
    public String entityName;
    public boolean alive;
    public boolean destructable;

    public int startHealth;
    public int startArmorClass;
    public int startAttackPower;
}
