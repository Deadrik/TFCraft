package TFC.TileEntities;

import java.util.Random;

import TFC.Core.PacketHandler;
import TFC.Core.TFCHeat;
import TFC.Core.TFCSeasons;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public class TileEntityFruitTreeWood extends TileEntity implements IInventory
{
    public boolean isTrunk;
    public int height;
    public long birthTime;

    public TileEntityFruitTreeWood()
    {
        height = 0;
        isTrunk = false;
        birthTime = 0;
    }
    
    public void setBirth()
    {
        birthTime = TFCSeasons.totalDays();
    }
    public void setBirth(long t)
    {
        birthTime += t;
    }
    
    public void setTrunk(boolean b)
    {
        isTrunk = b;
    }
    
    public void setHeight(int h)
    {
        height = h;
    }
    
    public void updateEntity()
    {

    }
    
    @Override
    public boolean canUpdate()
    {
        return false;
    }

    @Override
    public void closeChest() 
    {

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
        return "Fruit Tree Wood";
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
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
        birthTime = nbttagcompound.getLong("birthTime");
        isTrunk = nbttagcompound.getBoolean("isTrunk");
        height = nbttagcompound.getInteger("height");
    }
   

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setLong("birthTime", birthTime);
        nbttagcompound.setBoolean("isTrunk", isTrunk);
        nbttagcompound.setInteger("height", height);
    }

    public void handlePacketData() 
    {
        TileEntityFruitTreeWood pile = this;
    }

    @Override
    public int getSizeInventory()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        // TODO Auto-generated method stub
        
    }
}
