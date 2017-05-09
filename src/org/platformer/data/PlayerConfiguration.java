package org.platformer.data;

/**
 * <p>PlayerConfiguration is a class that describes
 * what makes a player different than another
 * (e.g. its maximum health, its inventory and its level).</p>
 *
 * <p>It can be used to create, save and load a character.</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @Author Nomitso
 */
public class PlayerConfiguration {

    private int maxHealth;
    private int currentHealth;
    private Inventory inventory;

    /**
     * Default constructor
     */
    public PlayerConfiguration() { }

    /**
     * Constructor
     *
     * @param maxHealth the player's maximum health
     * @param currentHealth the player's current health
     * @param inventory
     */
    public PlayerConfiguration(int maxHealth, int currentHealth, Inventory inventory) {

        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.inventory = inventory;

    }

    /**
     * Sets the player's maximum health.
     *
     * @param maxHealth the player's maximum health
     */
    public void setMaxHealth(int maxHealth) {

        this.maxHealth = maxHealth;

    }

    /**
     * Gets the player's maximum health.
     *
     * @return the player's maximum health
     */
    public int getMaxHealth() {

        return maxHealth;

    }

    /**
     * Sets the player's current health.
     *
     * @param currentHealth player's current health
     */
    public void setCurrentHealth(int currentHealth) {

        this.currentHealth = currentHealth;

    }

    /**
     * Gets the player's current health.
     *
     * @return the player's current health
     */
    public int getCurrentHealth() {

        return currentHealth;

    }

    /**
     * Sets the player's inventory.
     *
     * @param inventory the player's inventory
     */
    public void setInventory(Inventory inventory) {

        this.inventory = inventory;

    }

    /**
     * Gets the player's inventory.
     *
     * @return the player's inventory
     */
    public Inventory getInventory() {

        return inventory;

    }

}
