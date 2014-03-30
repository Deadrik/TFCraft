package TFC.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Core.TFC_ItemHeat;

public class TileEntityIngotPile extends TileEntity implements IInventory
{
	public ItemStack[] storage;
	public String type;
	public static Item[] INGOTS;
	
	public TileEntityIngotPile()
	{
		storage = new ItemStack[1];
		type = "Copper";
		INGOTS = new Item[]{(TFCItems.BismuthIngot),(TFCItems.BismuthBronzeIngot),(TFCItems.BlackBronzeIngot),
				(TFCItems.BlackSteelIngot),(TFCItems.BlueSteelIngot),(TFCItems.BrassIngot),(TFCItems.BronzeIngot),
				(TFCItems.CopperIngot),(TFCItems.GoldIngot),(TFCItems.WroughtIronIngot),(TFCItems.LeadIngot),
				(TFCItems.NickelIngot),(TFCItems.PigIronIngot),(TFCItems.PlatinumIngot),(TFCItems.RedSteelIngot),
				(TFCItems.RoseGoldIngot),(TFCItems.SilverIngot),(TFCItems.SteelIngot),(TFCItems.SterlingSilverIngot),
				(TFCItems.TinIngot),(TFCItems.ZincIngot), TFCItems.UnknownIngot};
	}
	public static Item[] getIngots()
	{
		return INGOTS;
	}
	
	public void setType(String i)
	{
		type = i;
	}
	
	public int getStack()
	{
		return storage[0].stackSize;
	}

	public String getType()
	{
		return this.type;
	}

	public void addContents(int index, ItemStack is)
	{
		if(storage[index] == null)
			storage[index] = is;
		updateNeighbours();
	}

	public void clearContents()
	{
		storage[0] = null;
		updateNeighbours();
	}

	@Override
	public void closeInventory()
	{
	}

	public boolean contentsMatch(int index, ItemStack is)
	{
		if(storage[index] != null)
			if(storage[index].stackSize == 0)
				return true;

		if(storage[index].getItem() == is.getItem() && storage[index].getItem() == is.getItem() &&
				/*storage[index].stackSize < storage[index].getMaxStackSize() &&*/ storage[index].stackSize+1 <= this.getInventoryStackLimit())
			return true;

		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(storage[i] != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				storage[i] = null;
				updateNeighbours();
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage[i] = null;
			updateNeighbours();
			return itemstack1;
		}
		else
			return null;
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				storage[i] = null;
			}
		}
		updateNeighbours();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Ingot Pile";
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	public void injectContents(int index, int count)
	{
		if(storage[index] != null)
			if(storage[index].stackSize > 0)
				storage[index] = new ItemStack(storage[index].getItem(), storage[index].stackSize+count, storage[index].getItemDamage());
		updateNeighbours();
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
		storage[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,storage, xCoord,yCoord,zCoord);
	}

	public void updateNeighbours()
	{
		if(worldObj.blockExists(xCoord, yCoord+1, zCoord) && !worldObj.isAirBlock(xCoord, yCoord+1, zCoord))
			worldObj.getBlock(xCoord, yCoord+1, zCoord).onNeighborBlockChange(worldObj, xCoord, yCoord+1, zCoord, TFCBlocks.IngotPile);
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
		//nbttagcompound.setInteger("type", type);
		type = nbttagcompound.getString("type");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("type", type);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
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
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
		updateNeighbours();
	}

}
