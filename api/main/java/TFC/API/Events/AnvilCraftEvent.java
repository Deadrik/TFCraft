package TFC.API.Events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.EntityEvent;

@Cancelable
public class AnvilCraftEvent extends EntityEvent 
{	
	/**
	 * The item which is currently being worked.
	 */
	public ItemStack input1;
	/**
	 * The result item from the crafting process if it is allowed to finish.
	 */
	public ItemStack result;
	
	/**
	 * The TileEntity instance of the anvil which is being use for crafting.
	 */
	public TileEntity anvilTE;
	
	/**
	 * Fires when an item has finished crafting 
	 * @param entityplayer is the player who is doing the crafting
	 * @param i1 is the item which is currently being worked
	 * @param r is the result item from the crafting process if allowed to finish
	 */
	public AnvilCraftEvent(EntityPlayer entityplayer, TileEntity te, ItemStack i1, ItemStack r)
	{
		super(entityplayer);
		input1 = i1;
		result = r;
		anvilTE = te;
	}
}
