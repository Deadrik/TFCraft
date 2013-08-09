package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Containers.Slots.SlotCraftingScribe;
import TFC.Containers.Slots.SlotScribeCrafting;
import TFC.Containers.Slots.SlotScribePaper;
import TFC.Core.CraftingManagerTFC;
import TFC.GUI.GuiScribe;
import TFC.TileEntities.TileEntityScribe;

public class ContainerScribe extends ContainerTFC
{
	private TileEntityScribe terraScribe;
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private EntityPlayer entityplayer;
	private World worldObj;
	private GuiScribe te;

	public ContainerScribe(InventoryPlayer inventoryplayer, TileEntityScribe scribe, World world, int x, int y, int z)
	{
		terraScribe = scribe;
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();
		worldObj = world;
		entityplayer = inventoryplayer.player;
		//output
		addSlotToContainer(new SlotCraftingScribe(inventoryplayer.player, craftMatrix, craftResult,terraScribe, 0, 128, 44));
		//paper
		addSlotToContainer(new SlotScribePaper(inventoryplayer.player,scribe, this, 1, 128, 8));

		for (int l = 0; l < 5; l++)
		{
			for (int k1 = 0; k1 < 5; k1++)
			{
				addSlotToContainer(new SlotScribeCrafting(inventoryplayer.player,craftMatrix, k1 + l * 5, 8 + k1 * 18, l * 18 + 8));
			}
		}

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 102 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 160));
		}


		onCraftMatrixChanged(craftMatrix);
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	public void setGUI(GuiScribe TE){
		te = TE;
	}
	@Override
	public void onContainerClosed(EntityPlayer entityplayer)
	{
		super.onContainerClosed(entityplayer);
		if (worldObj.isRemote)
		{
			return;
		}
		for (int i = 0; i < 25; i++)
		{

			ItemStack itemstack = craftMatrix.getStackInSlot(i);
			if (itemstack != null)
			{
				entityplayer.dropPlayerItem(itemstack);
			}
		}
	}

	/*public void openBook(ItemStack temp2){
		//ItemStack temp = temp2;
		terraScribe.scribeItemStacks[1] = null;
		if(entityplayer.worldObj.isRemote && entityplayer.inventory.getCurrentItem()==null){
			//if(temp.hasTagCompound())System.out.println(temp.getTagCompound());
			terraScribe.nullifyBook();
			((EntityPlayerSP) entityplayer).getMcField().displayGuiScreen(new GuiScreenBookTFC(entityplayer, temp2, true));

			}
	}*/
	@Override
	public void onCraftMatrixChanged(IInventory iinventory)
	{
		//Check if there is paper in the paper slot.

		/*if(te!=null){
			te.initGui();
			}
		if(terraScribe.scribeItemStacks[1] != null && 
				terraScribe.scribeItemStacks[1].getItem() == TFCItems.writabeBookTFC){
			ItemStack temp = terraScribe.scribeItemStacks[1];
			//craftResult.setInventorySlotContents(0,temp); 
			//if(!worldObj.isRemote)terraScribe.scribeItemStacks[1]= null;

		}*/
		if(terraScribe.scribeItemStacks[1] != null && 
				terraScribe.scribeItemStacks[1].getItem() == Item.paper) {
			craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(craftMatrix, worldObj));
		}
		else
		{
			craftResult.setInventorySlotContents(0, null);
		}
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotpaper = (Slot)inventorySlots.get(1);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0)
			{
				if(this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true))
				{
					for (int j = 1; j <= 25; j++)
					{
						((Slot)inventorySlots.get(j)).decrStackSize(1);
					}
				}
				else
				{
					return null;
				}
			}
			else if(i <= 26)
			{
				if(!this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(itemstack1.itemID == Item.paper.itemID)
			{
				if(slotpaper.getHasStack())
				{
					return null;
				}
				ItemStack stack = itemstack1.copy();
				stack.stackSize = 1;
				slotpaper.putStack(stack);
				itemstack1.stackSize--;
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		return null;
	}

}
