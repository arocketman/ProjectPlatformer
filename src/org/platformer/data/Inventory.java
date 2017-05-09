package org.platformer.data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class is used to access the player's inventory.</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @Author Nomitso
 *
 */
public class Inventory
{

    private int         size;       // size of the inventory, i.e. how many items it can hold
    private List<Item>  items;      // a list that contains the items in the inventory

    /**
     * Constructor
     * @param size size of the inventory, i.e. how many items it can hold
     */
    public Inventory(int size) {

        this.size = size;
        items = new ArrayList<>();

    }

    /**
     * Sets the size of the inventory.
     *
     * @param size size of the inventory, i.e. how many items it can hold
     */
    public void setSize(int size) {

        this.size = size;

    }

    /**
     * Gets the size of the inventory.
     *
     * @return size of the inventory, i.e. how many items it can hold
     */
    public int getSize() {

        return size;

    }

    /**
     * Gets the list that contains the items in the inventory.
     *
     * @return list that contains the items in the inventory
     */
    public List<Item> getItems() {

        return items;

    }

    /**
     * Returns true if the inventory is full.
     *
     * @return true if the inventory is full
     */
    public boolean isFull() {

        return items.size() == size;

    }

    /**
     * Adds an item to the inventory.
     *
     * @param item item to be added to the inventory
     */
    public void addItem(Item item) {

        if(!items.contains(item) && !isFull()) {

            items.add(item);

        }

    }

    /**
     * Removes an item from the inventory
     *
     * @param item item to be removed from the inventory
     */
    public void removeItem(Item item) {

        items.remove(item);

    }

    /**
     * Returns true if the inventory is empty.
     *
     * @return true if the inventory is empty
     */
    public boolean isEmpty() {

        return items.size() == 0;

    }

    /**
     * Returns the item from the inventory at a given position
     * @param index
     * @return
     */
    public Item getItem(int index) {

        // makes sure we're not out of bounds
        if(index < getItems().size() && index >= 0) {

            return getItems().get(index);

        }

        return null;

    }

}
