package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
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

public class TileEntityToolRack extends NetworkTileEntity implements IInventory
{
    public ItemStack[] storage;
    public byte woodType;

    public TileEntityToolRack()
    {
        storage = new ItemStack[4];
        woodType = 0;
    }

    public void addContents(int index, ItemStack is)
    {
        if(storage[index] == null) {
            storage[index] = is;
        }
    }

    public void clearContents()
    {
        storage[0] = null;
        storage[1] = null;
        storage[2] = null;
        storage[3] = null;
    }

    @Override
    public void closeChest() {


    }

    public boolean contentsMatch(int index, ItemStack is)
    {
        if(storage[index] != null && storage[index].getItem() == is.getItem() && storage[index].getItemDamage() == is.getItemDamage() &&
                storage[index].stackSize < storage[index].getMaxStackSize())
        {
            return true;
        } else {
            return false;
        }
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
        float f1 = rand.nextFloat() * 0.8F + 0.4F;
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
                storage[i] = null;
            }
        }
    }
    
    public void ejectItem(int index, int dir)
    {
    	float f3 = 0.05F;
        EntityItem entityitem;
        Random rand = new Random();
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.2F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;
        
    	if(storage[index] != null)
        {
            entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, 
                    storage[index]);
            entityitem.motionX = (float)rand.nextGaussian() * f3;
            entityitem.motionY = 0;
            entityitem.motionZ = (float)rand.nextGaussian() * f3;
            worldObj.spawnEntityInWorld(entityitem);
            storage[index] = null;
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public String getInvName()
    {
        return "Tool Rack";
    }

    public int getSizeInventory()
    {
        return storage.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return storage[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
    }

    public void injectContents(int index, int count)
    {
        if(storage[index] != null) {
            storage[index] = new ItemStack(storage[index].getItem(),storage[index].stackSize+count,storage[index].getItemDamage());
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

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        woodType = nbttagcompound.getByte("woodType");
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
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) 
    {
        storage[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public void updateEntity()
    {
        TFC_ItemHeat.HandleContainerHeat(this.worldObj,storage, (int)xCoord,(int)yCoord,(int)zCoord);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
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
        nbttagcompound.setByte("woodType", woodType);
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
		outStream.writeByte(woodType);
		outStream.writeInt(storage[0] != null ? storage[0].itemID : -1);
		outStream.writeInt(storage[1] != null ? storage[1].itemID : -1);
		outStream.writeInt(storage[2] != null ? storage[2].itemID : -1);
		outStream.writeInt(storage[3] != null ? storage[3].itemID : -1);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		woodType = inStream.readByte();
		int s1 = inStream.readInt();
		int s2 = inStream.readInt();
		int s3 = inStream.readInt();
		int s4 = inStream.readInt();
		storage[0] = s1 != -1 ? new ItemStack(Item.itemsList[s1]) : null;
		storage[1] = s2 != -1 ? new ItemStack(Item.itemsList[s2]) : null;
		storage[2] = s3 != -1 ? new ItemStack(Item.itemsList[s3]) : null;
		storage[3] = s4 != -1 ? new ItemStack(Item.itemsList[s4]) : null;
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
			dos.writeByte(woodType);
			dos.writeInt(storage[0] != null ? storage[0].itemID : -1);
			dos.writeInt(storage[1] != null ? storage[1].itemID : -1);
			dos.writeInt(storage[2] != null ? storage[2].itemID : -1);
			dos.writeInt(storage[3] != null ? storage[3].itemID : -1);
		} catch (IOException e) {
		}

		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
