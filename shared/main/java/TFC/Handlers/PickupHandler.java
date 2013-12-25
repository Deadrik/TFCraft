package TFC.Handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPickupNotifier;

public class PickupHandler implements IPickupNotifier
{

	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player) 
	{
		/*ItemStack quiver = null;
		ItemStack ammo = item.getEntityItem();
		for(int i = 0; i < 9; i++) 
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemQuiver)
			{
				quiver = player.inventory.getStackInSlot(i);
				break;
			}
		}

		if(quiver != null && (ammo.getItem() instanceof ItemArrow || ammo.getItem() instanceof ItemJavelin))
		{
			ItemStack is = ((ItemQuiver)quiver.getItem()).addItem(quiver, ammo);
			item.setEntityItemStack(is);
		}*/
	}

}
