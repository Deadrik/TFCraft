package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.HeatRegistry;
import TFC.API.Metal;
import TFC.API.TFC_ItemHeat;
import TFC.Containers.Slots.SlotForShowOnly;
import TFC.Containers.Slots.SlotLiquidVessel;
import TFC.Core.TFC_Achievements;
import TFC.Core.Metal.MetalRegistry;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Pottery.ItemPotteryMold;

public class ContainerLiquidVessel extends ContainerTFC 
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 1, 1);

	public int bagsSlotNum = 0;
	public int metalAmount = 0;

	public ContainerLiquidVessel(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		bagsSlotNum = player.inventory.currentItem;
		layoutContainer(playerinv);

	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!this.world.isRemote)
		{
			ItemStack itemstack2 = this.containerInv.getStackInSlotOnClosing(0);

			if (itemstack2 != null)
			{
				player.dropPlayerItem(itemstack2);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	private void layoutContainer(IInventory playerInventory) {

		this.addSlotToContainer(new SlotLiquidVessel(containerInv, 0, 80, 34));

		int row;
		int col;

		for (row = 0; row < 9; ++row) 
		{
			if(row == bagsSlotNum) {
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 148));
			} else {
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 148));
			}
		}

		for (row = 0; row < 3; ++row) 
		{
			for (col = 0; col < 9; ++col) 
			{
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 90 + row * 18));
			}
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		//Load the metal info from the liquid container
		NBTTagCompound nbt = player.inventory.getStackInSlot(bagsSlotNum).getTagCompound();

		if(nbt != null)
		{
			Metal m = MetalRegistry.instance.getMetalFromString((nbt.getString("MetalType")));
			metalAmount = nbt.getInteger("MetalAmount");

			if(!world.isRemote && m != null)
			{
				ItemStack input = containerInv.getStackInSlot(0);
				if(input != null && input.getItem().itemID == TFCItems.CeramicMold.itemID && input.getItemDamage() == 1 && input.stackSize == 1 && metalAmount > 0)
				{
					int amt = 99;
					ItemStack is = new ItemStack(m.MeltedItemID, 1, amt);
					TFC_ItemHeat.SetTemp(is, (short)(HeatRegistry.getInstance().getMeltingPoint(is)*1.5f));
					containerInv.setInventorySlotContents(0, is);
					if(metalAmount-1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						player.inventory.getStackInSlot(bagsSlotNum).setItemDamage(1);
					} else 
					{
						nbt.setInteger("MetalAmount", metalAmount-2);
					}

					player.inventory.getStackInSlot(bagsSlotNum).setTagCompound(nbt);
				}
				else if(input != null && input.getItem().itemID == m.MeltedItemID && input.getItemDamage() > 0)
				{
					input.setItemDamage(input.getItemDamage() - 1);
					TFC_ItemHeat.SetTemp(input, (short)(HeatRegistry.getInstance().getMeltingPoint(input)*1.5f));
					if(metalAmount-1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						player.inventory.getStackInSlot(bagsSlotNum).setItemDamage(1);
					} else 
					{
						nbt.setInteger("MetalAmount", metalAmount-1);
					}
				}
				else if(input != null && input.getItem() instanceof ItemPotteryMold && input.getItemDamage() == 1 && input.stackSize == 1 && metalAmount > 0  && (m.Name.equals("Copper") || m.Name.equals("Bronze") || m.Name.equals("Bismuth Bronze") || m.Name.equals("Black Bronze")))
				{
					int amt = -1;
					if(m.Name.equals("Copper")){
						amt = 398;
					}
					else if(m.Name.equals("Bronze")){
						amt = 399;
					}
					else if(m.Name.equals("Bismuth Bronze")){
						amt = 400;
					}
					else if(m.Name.equals("Black Bronze")){
						amt = 401;
					}
					ItemStack is = new ItemStack(input.getItem().itemID, 1, amt);
					TFC_ItemHeat.SetTemp(is, (short)(HeatRegistry.getInstance().getMeltingPoint(is)*1.5f));
					containerInv.setInventorySlotContents(0, is);
					if(metalAmount-1 <= 0)
					{
						nbt.removeTag("MetalType");
						nbt.removeTag("MetalAmount");
						nbt.removeTag("TempTimer");
						player.inventory.getStackInSlot(bagsSlotNum).setItemDamage(1);
					} else 
					{
						nbt.setInteger("MetalAmount", metalAmount-2);
					}

					player.inventory.getStackInSlot(bagsSlotNum).setTagCompound(nbt);
				}
				else if(input != null && input.getItem() instanceof ItemPotteryMold && input.getItemDamage() > 1)
				{
					boolean correctMetalFlag = false;
					if(m.Name.equals("Copper") && (input.getItemDamage()-2)%4 == 0){
						correctMetalFlag = true;
					}
					else if(m.Name.equals("Bronze") && (input.getItemDamage()-2)%4 == 1){
						correctMetalFlag = true;
					}
					else if(m.Name.equals("Bismuth Bronze") && (input.getItemDamage()-2)%4 == 2){
						correctMetalFlag = true;
					}
					else if(m.Name.equals("Black Bronze") && (input.getItemDamage()-2)%4 == 3){
						correctMetalFlag = true;
					}
					if(correctMetalFlag){
						if(input.getItemDamage() > 5){
							input.setItemDamage(input.getItemDamage() - 4);
							TFC_ItemHeat.SetTemp(input, (short)(HeatRegistry.getInstance().getMeltingPoint(input)*1.5f));
							if(metalAmount-1 <= 0)
							{
								nbt.removeTag("MetalType");
								nbt.removeTag("MetalAmount");
								nbt.removeTag("TempTimer");
								player.inventory.getStackInSlot(bagsSlotNum).setItemDamage(1);
								player.triggerAchievement(TFC_Achievements.achCopperAge);
							} else 
							{
								nbt.setInteger("MetalAmount", metalAmount-1);
							}
						}
					}
				}
			}
		}
		super.detectAndSendChanges();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex) {
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);
		Slot slot1 = (Slot)inventorySlots.get(0);

		if (clickedSlot != null
				&& clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex == 0)
			{
				if (!this.mergeItemStack(clickedStack, 1, inventorySlots.size(), true)) {
					return null;
				}
			} else if (clickedIndex > 0 && clickedIndex < inventorySlots.size() &&
					((clickedStack.getItem().itemID == TFCItems.CeramicMold.itemID && clickedStack.getItemDamage() == 1) || 
							(clickedStack.getItem() instanceof ItemMeltedMetal && clickedStack.getItemDamage() > 1) ||
							(clickedStack.getItem() instanceof ItemPotteryMold && clickedStack.getItemDamage() > 0)))
			{
				if(slot1.getHasStack())
				{
					return null;
				}
				ItemStack stack = clickedStack.copy();
				stack.stackSize = 1;                            
				slot1.putStack(stack);                          
				clickedStack.stackSize--;
			}	

			if (clickedStack.stackSize == 0) {
				clickedSlot.putStack((ItemStack)null);
			} else {
				clickedSlot.onSlotChanged();
			}

			if (clickedStack.stackSize == returnedStack.stackSize) {
				return null;
			}
			clickedSlot.onPickupFromSlot(player, clickedStack);
		}
		return returnedStack;
	}
}
