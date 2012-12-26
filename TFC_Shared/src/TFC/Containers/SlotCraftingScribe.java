package TFC.Containers;

import cpw.mods.fml.common.registry.GameRegistry;
import TFC.*;
import TFC.TileEntities.TileEntityTerraScribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraftforge.common.ForgeHooks;

public class SlotCraftingScribe extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private final IInventory paperSlot;

	public SlotCraftingScribe(EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1,IInventory iinventory2, int i, int j, int k)
	{
		super(iinventory1, i, j, k);
		thePlayer = entityplayer;
		craftMatrix = iinventory;
		paperSlot = iinventory2;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);

		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, itemstack, craftMatrix);
		GameRegistry.onItemCrafted(thePlayer, itemstack, craftMatrix);
		((TileEntityTerraScribe)paperSlot).scribeItemStacks[1].stackSize--;
		if(((TileEntityTerraScribe)paperSlot).scribeItemStacks[1].stackSize <= 0)
		{
			((TileEntityTerraScribe)paperSlot).scribeItemStacks[1] = null;
		}
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
			if (itemstack1 != null)
			{
				craftMatrix.decrStackSize(i, 1);

				if (itemstack1.getItem().hasContainerItem())
				{
					ItemStack itemstack2 = new ItemStack(itemstack1.getItem().getContainerItem());
					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !thePlayer.inventory.addItemStackToInventory(itemstack2))
					{
						if (craftMatrix.getStackInSlot(i) == null)
						{
							craftMatrix.setInventorySlotContents(i, itemstack2);
						}
						else
						{
							thePlayer.dropPlayerItem(itemstack2);
						}
					}
				}
			}
		}
	}
}
