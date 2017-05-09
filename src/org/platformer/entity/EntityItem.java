package org.platformer.entity;


import org.platformer.data.Item;
import org.platformer.utils.AABB;
import org.platformer.world.World;

public class EntityItem extends Entity
{

    private Item        item;

    /**
     * Constructor
     *
     * @param world the world the entity is located in
     * @param item the entity's item data
     *
     * @see World
     * @see Item
     */
    public EntityItem(World world, Item item)
    {

        super(world, item.getHash());
        colBox = new AABB(16f,16f);
        this.item = item;

        // used for tests
        findWorldSpawn();
        teleportToSpawn();

    }

    /**
     * Constructor with position
     *
     * @param world the world the entity is located in
     * @param item the entity's item data
     * @param posX entity's start position on the x-axis
     * @param posY entity's start position on the y-axis
     */
    public EntityItem(World world, Item item, float posX, float posY)
    {

        super(world, item.getHash());
        colBox = new AABB(16f,16f);
        this.item = item;
        colBox.posX = posX;
        colBox.posY = posY;

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
