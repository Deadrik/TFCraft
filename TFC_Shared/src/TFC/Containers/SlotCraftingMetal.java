package TFC.Containers;

import java.util.ArrayList;
import java.util.List;

import TFC.*;
import TFC.Core.Player.PlayerManagerTFC;
import cpw.mods.fml.common.registry.GameRegistry;
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

public class SlotCraftingMetal extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private List<Item> valids;
	private Container container;

	public SlotCraftingMetal(EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1, int i, int j, int k)
	{
		super(iinventory1, i, j, k);
		thePlayer = entityplayer;
		craftMatrix = iinventory;
		valids = new ArrayList<Item>();
	}
	public SlotCraftingMetal(Container container, EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1, int i, int j, int k)
	{
		super(iinventory1, i, j, k);
		this.container = container;
		thePlayer = entityplayer;
		craftMatrix = iinventory;
		valids = new ArrayList<Item>();
	}

	@Override
	public void onSlotChanged()
	{
		super.onSlotChanged();
		if (inventory.getStackInSlot(0)!=null){
			System.out.println(getStack()+", "+PlayerManagerTFC.getInstance().getPlayerInfoFromName(thePlayer.username).knappingRockType);
			if (valids.contains(getStack().getItem()) && container != null && getStack().getItemDamage()== 
					PlayerManagerTFC.getInstance().getPlayerInfoFromName(thePlayer.username).knappingRockType.getItemDamage()){
				container.onCraftMatrixChanged(craftMatrix);
			}
		}
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if (valids.contains(itemstack.getItem()) && container != null){
			container.onCraftMatrixChanged(craftMatrix);
		}
		return valids.contains(itemstack.getItem());
	}

	public void setValidity(Item item,boolean TF){
		if(TF){
			if (!valids.contains(item)){
				valids.add(item);
			}
		}
		else{
			if (valids.contains(item)){
				valids.remove(item);
			}
		}
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);

		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, itemstack, craftMatrix);
		GameRegistry.onItemCrafted(thePlayer, itemstack, craftMatrix);

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
