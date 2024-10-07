package game.entities;

import game.characteristics.EnemyType;

import java.io.Serializable;

public class InanimateObject implements Serializable {
    public EnemyType object;

    public int health;
    public int armorClass;
    public int attackPower;
}
