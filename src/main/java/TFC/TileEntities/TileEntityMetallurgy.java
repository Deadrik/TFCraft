package TFC.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import TFC.Core.TFC_ItemHeat;

public class TileEntityMetallurgy extends TileEntity implements IInventory
{
	public ItemStack metalItemStacks[];
	public Boolean AllMelted = false;

	static
	{
	}

	public TileEntityMetallurgy()
	{
		metalItemStacks = new ItemStack[0];
	}

	public float checkTemps(IInventory inv)
	{
		float temp = 0;
		float[] temp1 = new float[inv.getSizeInventory()];
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack is = inv.getStackInSlot(i);
			if(is != null && is.hasTagCompound() && !is.getItem().getUnlocalizedName(is).contains("Clay"))
			{
				if(is.getTagCompound().hasKey("temperature"))
				{
					temp1[i] = is.getTagCompound().getFloat("temperature");
					if(temp1[i] < TFC_ItemHeat.getMeltingPoint(is))
						return (float) -1;
				}
				else
					return (float) -1;
			}
			else if(is == null)
				temp1[i] = -1;
		}
		int  temp2 = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (temp1[i] >= 0)
			{
				temp += temp1[i];
				temp2++;
			}
		}
		if (temp2 > 0)
			temp /= temp2;
		return temp;
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(metalItemStacks[i] != null)
		{
			if(metalItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = metalItemStacks[i];
				metalItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = metalItemStacks[i].splitStack(j);
			if(metalItemStacks[i].stackSize == 0)
				metalItemStacks[i] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public String getInventoryName()
	{
		return "MetallurgyTable";
	}

	public int getSizeInventory()
	{
		return metalItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return metalItemStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		metalItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		//AllMelted = checkTemps();
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		//timeleft = nbttagcompound.getInteger("timeleft");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		metalItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < metalItemStacks.length)
				metalItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		//nbttagcompound.setInteger("timeleft", timeleft);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < metalItemStacks.length; i++)
		{
			if(metalItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				metalItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

}
