package game.entities;

import game.characteristics.EnemyType;

import java.io.Serializable;

public class NonPlayerCharacter implements Serializable {
    public String name;
    public EnemyType enemyType;

    public int health;
    public int armorClass;
    public int attackPower;
}
