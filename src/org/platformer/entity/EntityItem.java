package org.platformer.entity;


import org.platformer.Data.Item;
import org.platformer.utils.AABB;
import org.platformer.world.World;

public class EntityItem extends Entity
{

    private Item        item;

    /**
     * Constructor
     *
     * @param world the world the entity is located in
     * @param hash
     * @param item the entity's item data
     *
     * @see World
     * @see Item
     */
    public EntityItem(World world, String hash, Item item)
    {

        super(world, hash);
        colBox = new AABB(16f,16f);
        this.item = item;

        // used for tests
        findWorldSpawn();
        teleportToSpawn();

    }

    /**
     * Sets the entity's item data
     *
     * @param item the entity's item data
     */
    public void setItem(Item item) {

        this.item = item;

    }

    /**
     * Gets the entity's item data
     *
     * @return the entity's item data
     */
    public Item getItem() {

        return item;

    }

    /**
     * Gets the entity's texture
     *
     * @return the entity's texture
     */
    public String getTexture()
    {

        return item.getTexture();

    }

}
