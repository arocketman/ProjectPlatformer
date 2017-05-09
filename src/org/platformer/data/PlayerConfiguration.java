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
    private Equipment equipment;

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
    public PlayerConfiguration(int maxHealth, int currentHealth, Inventory inventory, Equipment equipment) {

        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.inventory = inventory;
        this.equipment = equipment;

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
     * @see Inventory
     */
    public void setInventory(Inventory inventory) {

        this.inventory = inventory;

    }

    /**
     * Gets the player's inventory.
     *
     * @return the player's inventory
     * @see Inventory
     */
    public Inventory getInventory() {

        return inventory;

    }

    /**
     * Sets the player's equipment.
     *
     * @param equipment the player's equipment
     * @see Equipment
     */
    public void setEquipment(Equipment equipment) {

        this.equipment = equipment;

    }

    /**
     * Gets the player's equipment.
     *
     * @return the player's equipment
     * @see Equipment
     */
    public Equipment getEquipment() {

        return equipment;

    }

}
