package TFC.Containers;

import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.CraftingManagerTFC;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.TileEntities.TileEntityTerraWorkbench;
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
import net.minecraft.server.management.PlayerManager;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class ContainerKnapping extends Container
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 5);

	private EntityPlayer EP;
	private SlotCraftingMetal SCM;
	private boolean firstTime = true;
	private boolean isFinal = true;

	/** The crafting result, size 1. */
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;

	public ContainerKnapping(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		for (int j1 = 0; j1 < 25; j1++)
		{
			craftMatrix.setInventorySlotContents(j1, is.copy());
		}
		this.worldObj = world;

		int var6;
		int var7;

		EP = inventoryplayer.player;
		SCM = new SlotCraftingMetal(this,inventoryplayer.player, craftMatrix, craftResult,0, 128, 35);
		addSlotToContainer(SCM);

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 151));
		}

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 93 + i * 18));
			}

		}

		for (int l = 0; l < 5; l++)
		{
			for (int k1 = 0; k1 < 5; k1++)
			{
				addSlotToContainer(new SlotBlocked(craftMatrix, k1 + l * 5, 8 + k1 * 16, l * 16 - 1));
			}
		}



		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if (!firstTime){
			boolean newKnapp = true;
			for (int i = 0; i < craftMatrix.getSizeInventory();i++){
				if (craftMatrix.getStackInSlot(i)!=null){
					newKnapp = false;
					break;
				}
			}
			if(SCM != null){
				SCM.setValidity(TFCItems.LooseRock,newKnapp);
			}
			if(craftResult.getStackInSlot(0) != null){
				if (craftResult.getStackInSlot(0).getItem().shiftedIndex == TFCItems.LooseRock.shiftedIndex ){
					EP.inventory.addItemStackToInventory(EP.inventory.getItemStack());
					if(craftResult.getStackInSlot(0).stackSize > 1){
						EP.inventory.addItemStackToInventory(new ItemStack(craftResult.getStackInSlot(0).getItem().shiftedIndex,craftResult.getStackInSlot(0).stackSize-1,craftResult.getStackInSlot(0).getItemDamage()));
					}
					isFinal = false;
					EP.openGui(TerraFirmaCraft.instance, 28, EP.worldObj, (int)EP.posX, (int)EP.posY, (int)EP.posZ);
				}
			}
		}
		firstTime = false;
		this.craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(this.craftMatrix, worldObj));
	}
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		if (isFinal){
			par1EntityPlayer.worldObj.spawnEntityInWorld((new EntityItem(par1EntityPlayer.worldObj,par1EntityPlayer.posX,
					par1EntityPlayer.posY+1,par1EntityPlayer.posZ,new ItemStack(TFCItems.LooseRock,1,PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(par1EntityPlayer).knappingRockType.getItemDamage()))));
		}
	}

	public void createNew(){

	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 * @return 
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack var2 = null;
		Slot grabbedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if(grabbedSlot != null && grabbedSlot.getHasStack())
		{
			ItemStack var4 = grabbedSlot.getStack();
			var2 = var4.copy();

			if(clickedIndex < 10)
			{
				if (!this.mergeItemStack(var4, 10, 36, true))
				{
					return null;
				}
			}
			else if(clickedIndex >= 10 && clickedIndex < 37)
			{
				if (!this.mergeItemStack(var4, 0, 9, true))
				{
					return null;
				}
			}
			else if(clickedIndex >= 37 && clickedIndex < 62)
			{
				if (!this.mergeItemStack(var4, 0, 36, true))
				{
					return null;
				}
			}

			if (var4.stackSize == 0)
			{
				grabbedSlot.putStack((ItemStack)null);
			}
			else
			{
				grabbedSlot.onSlotChanged();
			}

			if (var4.stackSize == var2.stackSize)
			{
				return null;
			}

			grabbedSlot.onPickupFromSlot(player, var4);
		}

		return var2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		// TODO Auto-generated method stub
		return true;
	}
}
