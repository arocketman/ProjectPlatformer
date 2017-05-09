package org.platformer.data;

/**
 * <p>This class is used to access the player's equipment.</p>
 *
 * <p>There shouldn't be logic in here, only data that you
 * can read and change.</p>
 *
 * @Author Nomitso
 *
 */
public class Equipment {

    private Item rightHandItem;
    private Item leftHandItem;
    private Item headItem;
    private Item legsItem;
    private Item torsoItem;

    /**
     * Default constructor
     */
    public Equipment() {

        rightHandItem = null;
        leftHandItem = null;
        headItem = null;
        legsItem = null;
        torsoItem = null;

    }

    /**
     * Constructor with items to equip.
     *
     * @param rightHandItem item to be equipped in the player's right hand
     * @param leftHandItem item to be equipped in the player's left hand
     * @param headItem item to be equipped on the player's head
     * @param legsItem item to be equipped on the player's legs
     * @param torsoItem item to be equipped on the player's torso
     */
    public Equipment(Item rightHandItem, Item leftHandItem, Item headItem, Item legsItem, Item torsoItem) {

        this.rightHandItem = rightHandItem;
        this.leftHandItem = leftHandItem;
        this.headItem = headItem;
        this.legsItem = legsItem;
        this.torsoItem = torsoItem;

    }

    /**
     * Sets the item to be equipped in the player's right hand.
     *
     * @param rightHandItem item to be equipped in the player's right hand
     */
    public void setRightHandItem(Item rightHandItem) {

        this.rightHandItem = rightHandItem;

    }

    /**
     * Sets the item to be equipped in the player's left hand.
     *
     * @param leftHandItem item to be equipped in the player's left hand
     */
    public void setLeftHandItem(Item leftHandItem) {

        this.leftHandItem = leftHandItem;

    }

    /**
     * Sets the item to be equipped on the player's head.
     *
     * @param headItem item to be equipped on the player's head
     */
    public void setHeadItem(Item headItem) {

        this.headItem = headItem;

    }

    /**
     * Sets the item to be equipped on the player's legs.
     *
     * @param legsItem item to be equipped on the player's legs
     */
    public void setLegsItem(Item legsItem) {

        this.legsItem = legsItem;

    }

    /**
     * Sets the item to be equipped on the player's torso.
     *
     * @param torsoItem item to be equipped on the player's torso
     */
    public void setTorsoItem(Item torsoItem) {

        this.torsoItem = torsoItem;

    }

    /**
     * Gets the item equipped in the player's right hand.
     *
     * @return item to be equipped in the player's right hand
     */
    public Item getRightHandItem() {

        return rightHandItem;

    }

    /**
     * Gets the item equipped in the player's left hand.
     *
     * @return item to be equipped in the player's left hand
     */
    public Item getLeftHandItem() {

        return leftHandItem;

    }

    /**
     * Gets the item equipped on the player's head.
     *
     * @return item to be equipped on the player's head
     */
    public Item getHeadItem() {

        return headItem;

    }

    /**
     * Gets the item equipped on the player's legs.
     *
     * @return item to be equipped on the player's legs
     */
    public Item getLegsItem() {

        return legsItem;

    }

    /**
     * Gets the item equipped on the player's torso.
     *
     * @return item to be equipped on the player's torso
     */
    public Item getTorsoItem() {

        return torsoItem;

    }

}
