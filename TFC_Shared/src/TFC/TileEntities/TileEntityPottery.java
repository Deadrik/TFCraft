package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import TFC.Core.TFC_Time;

public class TileEntityPottery extends NetworkTileEntity implements IInventory
{
	public ItemStack inventory[];
	public boolean hasRack;
	public boolean isBurning;
	public long burnCompleteTime;

	public TileEntityPottery()
	{
		inventory = new ItemStack[4];
		hasRack = false;
		isBurning = false;
	}

	@Override
	public void updateEntity()
	{        
		if(!worldObj.isRemote && isBurning)
		{
			int blockAboveID = worldObj.getBlockId(xCoord, yCoord+1, zCoord);
			if(blockAboveID != Block.fire.blockID && (blockAboveID == 0 || worldObj.getBlockMaterial(xCoord, yCoord+1, zCoord).getCanBurn()))
			{
				worldObj.setBlock(xCoord, yCoord+1, zCoord, Block.fire.blockID);
			}
			else
			{
				isBurning = false;
			}
			

			if(TFC_Time.getTotalTicks() >= burnCompleteTime)
			{
				isBurning = false;
				if(inventory[0] != null)
					inventory[0].setItemDamage(1);
				if(inventory[1] != null)
					inventory[1].setItemDamage(1);
				if(inventory[2] != null)
					inventory[2].setItemDamage(1);
				if(inventory[3] != null)
					inventory[3].setItemDamage(1);
			}
			
			
		}
	}	

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inventory[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub  
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);

	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException  
	{
		if(inventory[0] != null)
		{
			outStream.writeInt(inventory[0].itemID);
			outStream.writeInt(inventory[0].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[1] != null)
		{
			outStream.writeInt(inventory[1].itemID);
			outStream.writeInt(inventory[1].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[2] != null)
		{
			outStream.writeInt(inventory[2].itemID);
			outStream.writeInt(inventory[2].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		if(inventory[3] != null)
		{
			outStream.writeInt(inventory[3].itemID);
			outStream.writeInt(inventory[3].getItemDamage());
		}
		else
		{
			outStream.writeInt(0);
			outStream.writeInt(0);
		}
		outStream.writeBoolean(hasRack);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		int inv0 = inStream.readInt();
		int inv0d = inStream.readInt();
		int inv1 = inStream.readInt();
		int inv1d = inStream.readInt();
		int inv2 = inStream.readInt();
		int inv2d = inStream.readInt();
		int inv3 = inStream.readInt();
		int inv3d = inStream.readInt();
		
		hasRack = inStream.readBoolean();
		
		inventory[0] = inv0 != 0 ? new ItemStack(inv0, 1, inv0d) : null;
		inventory[1] = inv1 != 0 ? new ItemStack(inv1, 1, inv1d) : null;
		inventory[2] = inv2 != 0 ? new ItemStack(inv2, 1, inv2d) : null;
		inventory[3] = inv3 != 0 ? new ItemStack(inv3, 1, inv3d) : null;
		this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)throws IOException 
	{

	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public String getInvName() {
		return "Pottery";
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void closeChest() 
	{		
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
	}
}
