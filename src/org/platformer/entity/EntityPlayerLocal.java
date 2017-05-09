package org.platformer.entity;

import org.platformer.data.Equipment;
import org.platformer.data.Inventory;
import org.platformer.data.Item;
import org.platformer.data.PlayerConfiguration;
import org.platformer.register.RegisterKeybinds;
import org.platformer.utils.MiscUtils;
import org.platformer.world.World;
import org.platformer.world.WorldClient;

public class EntityPlayerLocal extends EntityPlayer
{

	public EntityPlayerLocal(World world, String hash)
	{
		super(world, hash);
	}

	/**
	 * Constructor with PlayerConfiguration added.
	 *
	 * @param world
	 * @param hash
	 * @param config
	 */
	public EntityPlayerLocal(World world, String hash, PlayerConfiguration config)
	{

		super(world, hash, config);

	}

    @Override
    public void update()
    {

        handlePlayerControl();
        mouseAngle = MiscUtils.pointTowardsPosition(posX, posY-20, ((WorldClient)world).getMouseInWorld()[0], ((WorldClient)world).getMouseInWorld()[1]);
        super.update();
    }

    /**
     * Handles what needs to be updated after
     * an action has been performed by the player.
     */
	private void handlePlayerControl() {

	    handlePlayerMovements();
	    handleEquippedItemSwitching();
	    handleItemDropping();

    }

    /**
     * Handles player's movement (e.g. to go left, right, up)
     */
    private void handlePlayerMovements() {

        if(RegisterKeybinds.move_left.isPressed()) motionX = -4f;
        if(RegisterKeybinds.move_right.isPressed()) motionX = 4f;

        if(onGround)
        {
            if(RegisterKeybinds.move_up.isPressed()) motionY = -4.2f;
        }

    }

    /**
     * Handles the switching of equipped items controlled by the player.
     *
     * By pressing a key from 1 to 5, the item equipped in the player's
     * right hand switches from the first item to the
     * 5th item in the inventory.
     */
	private void handleEquippedItemSwitching() {

        Equipment equipment = getPlayerConfiguration().getEquipment();
        Inventory inventory = getPlayerConfiguration().getInventory();

        if(RegisterKeybinds.letter("1").isPressedOnce()) {

            equipment.setRightHandItem(inventory.getItem(0));
            System.out.println("Player's right hand : " + equipment.getRightHandItem());

        }
        if(RegisterKeybinds.letter("2").isPressedOnce()) {

            equipment.setRightHandItem(inventory.getItem(1));
            System.out.println("Player's right hand : " + equipment.getRightHandItem());

        }
        if(RegisterKeybinds.letter("3").isPressedOnce()) {

            equipment.setRightHandItem(inventory.getItem(2));
            System.out.println("Player's right hand : " + equipment.getRightHandItem());

        }
        if(RegisterKeybinds.letter("4").isPressedOnce()) {

            equipment.setRightHandItem(inventory.getItem(3));
            System.out.println("Player's right hand : " + equipment.getRightHandItem());

        }
        if(RegisterKeybinds.letter("5").isPressedOnce()) {

            equipment.setRightHandItem(inventory.getItem(4));
            System.out.println("Player's right hand : " + equipment.getRightHandItem());

        }

    }

    /**
     * Handles the ability from the player to drop the
     * item currently equipped in its right hand.
     */
    private void handleItemDropping() {

        // by pressing 'g' we drop the last picked item
        if(RegisterKeybinds.letter("g").isPressedOnce()) {

            Inventory inventory = getPlayerConfiguration().getInventory();

            if(!inventory.isEmpty())
            {

                // removes the last item from the inventory
                Equipment equipment = getPlayerConfiguration().getEquipment();
                Item item = equipment.getRightHandItem();

                if(item != null) {

                    inventory.removeItem(item);
                    equipment.setRightHandItem(null);
                    new EntityItem(world, item, posX, posY);        // adds the new entity to the world

                    // testing
                    System.out.println("\nThe item " + item + " has been dropped from the player's inventory.");
                    System.out.println("Player's inventory: " + inventory.getItems());

                }

            }

        }

    }

}
