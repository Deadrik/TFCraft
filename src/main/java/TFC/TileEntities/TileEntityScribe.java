package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityScribe extends TileEntity implements IInventory
{
	public static HashMap Recipes;
	public ItemStack scribeItemStacks[];

	static
	{
	}

	public TileEntityScribe()
	{
		scribeItemStacks = new ItemStack[2];
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(scribeItemStacks[i] != null)
		{
			if(scribeItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = scribeItemStacks[i];
				scribeItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = scribeItemStacks[i].splitStack(j);
			if(scribeItemStacks[i].stackSize == 0)
				scribeItemStacks[i] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Scribe";
	}

	@Override
	public int getSizeInventory()
	{
		return scribeItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return scribeItemStacks[i];
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
		scribeItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
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

	public void nullifyBook()
	{
		ByteArrayOutputStream var3 = new ByteArrayOutputStream(1000);
        DataOutputStream var4 = new DataOutputStream(var3);
        //Commented out for being incorrect. See Packethandler for more information.
        /*try{
        var4.writeByte(PacketHandler.Packet_Scribe_Update);
        var4.writeInt(xCoord);
        var4.writeInt(yCoord);
        var4.writeInt(zCoord);
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new Packet250CustomPayload("TerraFirmaCraft", var3.toByteArray()));
        }
        catch(Exception e){
        	return;
        }*/
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		//timeleft = nbttagcompound.getInteger("timeleft");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		scribeItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < scribeItemStacks.length)
				scribeItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		//nbttagcompound.setInteger("timeleft", timeleft);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < scribeItemStacks.length; i++)
		{
			if(scribeItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				scribeItemStacks[i].writeToNBT(nbttagcompound1);
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
