package TFC.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.TerraFirmaCraft;
import TFC.Containers.ContainerSpecialCrafting;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.GUI.GuiKnapping;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

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
		if (inventory.getStackInSlot(0)!=null)
		{
			System.out.println(getStack()+", "+PlayerManagerTFC.getInstance().getPlayerInfoFromName(thePlayer.getDisplayName()).specialCraftingType);
			if (valids.contains(getStack().getItem()) && container != null &&
					getStack().getItemDamage() == PlayerManagerTFC.getInstance().getPlayerInfoFromName(thePlayer.getDisplayName()).specialCraftingType.getItemDamage())
			{
				container.onCraftMatrixChanged(craftMatrix);
			}
		}
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if (valids.contains(itemstack.getItem()) && container != null)
			container.onCraftMatrixChanged(craftMatrix);
		return valids.contains(itemstack.getItem());
	}

	public void setValidity(Item item,boolean TF)
	{
		if(TF)
			if (!valids.contains(item))
				valids.add(item);
		else
			if (valids.contains(item))
				valids.remove(item);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);
		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, itemstack, craftMatrix);

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
			if (itemstack1 != null)
			{
				craftMatrix.decrStackSize(i, 1);
				if(player.worldObj.isRemote && player.openContainer instanceof ContainerSpecialCrafting)
					((GuiKnapping) Minecraft.getMinecraft().currentScreen).resetButton(i);

				if (itemstack1.getItem().getContainerItem() != null)
				{
					ItemStack itemstack2 = new ItemStack(itemstack1.getItem().getContainerItem());
					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !thePlayer.inventory.addItemStackToInventory(itemstack2))
					{
						if (craftMatrix.getStackInSlot(i) == null)
							craftMatrix.setInventorySlotContents(i, itemstack2);
						else
							thePlayer.dropItem(itemstack2.getItem(), itemstack2.stackSize);
					}
				}
			}
		}
	}
}
