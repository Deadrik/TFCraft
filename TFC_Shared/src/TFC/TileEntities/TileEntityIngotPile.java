package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockIngotPile;
import TFC.Core.TFC_ItemHeat;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class TileEntityIngotPile extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage;
	public int type;


	public TileEntityIngotPile()
	{
		storage = new ItemStack[1];
		storage[0] = new ItemStack(TFCItems.BismuthIngot.shiftedIndex,0,0);
		type = -1;
	}
	
	public void setType(int i){
		
		this.type = i;
		System.out.println("setting type: " +this.type);
		
	}
	
	public int getStack(){
		return storage[0].stackSize;
	}

	public int getType(){
		return this.type;
	}

	public void addContents(int index, ItemStack is)
	{
		if(storage[index] == null) {
			storage[index] = is;
		}
		((BlockIngotPile)(this.getBlockType())).stack = storage[0].stackSize;
	}

	public void clearContents()
	{
		storage[0] = null;
		((BlockIngotPile)(this.getBlockType())).stack = 0;
	}

	@Override
	public void closeChest() {

	}

	public boolean contentsMatch(int index, ItemStack is)
	{
		if(storage[index] != null){
			if(storage[index].stackSize == 0){
				return true;
			}
		}
		if(storage[index].getItem() == is.getItem() && storage[index].getItem().shiftedIndex == is.getItem().shiftedIndex &&
				storage[index].stackSize < storage[index].getMaxStackSize() && storage[index].stackSize+1 <= this.getInventoryStackLimit())
		{
			return true;
		}


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
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
			{
				storage[i] = null;
			}
			return itemstack1;
		} else
		{
			return null;
		}
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
				entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, 
						storage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 32;
	}

	@Override
	public String getInvName()
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
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void injectContents(int index, int count)
	{
		if(storage[index] != null) {
			if(storage[index].stackSize > 0){
				storage[index] = 
						new ItemStack(storage[index].getItem(),
								storage[index].stackSize+count,
								storage[index].getItemDamage());
			}
		}
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {

	}
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		//nbttagcompound.setInteger("type", type);
		type = nbttagcompound.getInteger("type");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
			{
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		System.out.println(storage[0]+": "+xCoord+", "+yCoord+", "+zCoord);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		storage[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		((BlockIngotPile)(this.getBlockType())).stack = storage[0].stackSize;
	}

	@Override
	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,storage, (int)xCoord,(int)yCoord,(int)zCoord);
	}
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		//type = nbttagcompound.getInteger("type");
		nbttagcompound.setInteger("type", type);
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
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		handleInitPacket(inStream);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)throws IOException 
	{
		
		
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeInt(type);
		outStream.writeInt(storage[0] != null ? storage[0].itemID : -1);
		outStream.writeInt(storage[0] != null ? storage[0].stackSize : 0);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		type = inStream.readInt();
		int s1 = inStream.readInt();
		int size = inStream.readInt();
		storage[0] = s1 != -1 ? new ItemStack(Item.itemsList[s1],size) : null;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(type);
			dos.writeInt(storage[0] != null ? storage[0].itemID : -1);
			dos.writeInt(storage[0] != null ? storage[0].stackSize : 0);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
}
