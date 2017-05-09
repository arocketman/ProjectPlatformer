package org.platformer.data;

/**
 * <p>This class is used to describe an item in the game.</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @author Nomitso
 */
public class Item {

    private String      texture;    // the item's texture that will be rendered
    private String      name;
    private String      hash;

    /**
     * Constructor
     *
     * @param name the item's name
     * @param texture the name of the item's texture that will be rendered
     */
    public Item(String name, String texture, String hash)
    {

        this.name = name;
        this.texture = texture;
        this.hash = hash;

    }

    /**
     * Sets the item's name.
     *
     * @param name the item's name
     */
    public void setName(String name)
    {

        this.name = name;

    }

    /**
     * Gets the item's name.
     *
     * @return the item's name
     */
    public String getName()
    {

        return name;

    }

    /**
     * Sets the name of the item's texture that will be rendered.
     *
     * @param texture the name of the item's texture that will be rendered
     */
    public void setTexture(String texture)
    {

        this.texture = texture;

    }

    /**
     * Gets the name of the item's texture that will be rendered
     *
     * @return the name of the item's texture that will be rendered
     */
    public String getTexture()
    {

        return texture;

    }

    public String getHash() {

        return hash;

    }

    @Override
    public String toString() {

        return "(name:" + name + ")";

    }

}
