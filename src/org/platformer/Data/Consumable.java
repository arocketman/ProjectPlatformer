package org.platformer.Data;

/**
 * <p>This class is used to describe a consumable in the game.
 * A consumable is an item that can be used only once (normally)
 * by the player (e.g. a potion).</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @Author Nomitso
 */
public class Consumable extends Item
{

    public Consumable(String name, String texture) {

        super(name, texture);

    }

}
