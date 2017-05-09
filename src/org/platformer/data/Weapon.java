package org.platformer.data;

/**
 * <p>This class is used to describe a weapon in the game.
 * A weapon is anything a player can equip to deal damage
 * to an enemy.</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @Author Nomitso
 */
public class Weapon extends Item
{

    private int damage;

    public Weapon(String name, String texture, String hash, int damage) {

        super(name, texture, hash);
        this.damage = damage;

    }

    public void setDamage(int damage) {

        this.damage = damage;

    }

    public int getDamage() {

        return damage;

    }

}
