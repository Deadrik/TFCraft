package net.minecraft.src.TFC_Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.TileEntityTerraLogPile;
import net.minecraft.src.TFC_Core.General.HeatIndex;
import net.minecraft.src.TFC_Core.General.HeatManager;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCHeat;
import net.minecraft.src.TFC_Core.General.Vector3f;

public class TileEntityTerraFirepit extends TileEntityFireEntity implements IInventory
{
    public float fuelTimeLeft;
    public float fuelBurnTemp;
    public int fuelBuildup;
    public float fireTemperature;
    public int timeleft;
    public float AddedAir;

    public ItemStack fireItemStacks[];
    public float inputItemTemp;
    public float ambientTemp;
    Boolean Item1Melted = false;

    private int prevStackSize;
    public int numAirBlocks;
    private ItemStack prevWorkItemStack;

    private int externalFireCheckTimer;
    public Boolean canCreateFire;
    private int externalWoodCount;
    public int charcoalCounter;

    public final int FIREBURNTIME = 240;//default 240

    private static int[] area =  {0,0,2,2,2,0,0};
    private static int[] area0 = {0,2,1,1,1,2,0};
    private static int[] area1 = {2,1,1,1,1,1,2};
    private static int[] area2 = {2,1,1,3,1,1,2};
    private static int[] area3 = {2,1,1,1,1,1,2};
    private static int[] area4 = {0,2,1,1,1,2,0};
    private static int[] area5 = {0,0,2,2,2,0,0};
    private static int[] area0_2 = {0,2,2,2,0};
    private static int[] area1_2 = {2,1,1,1,2};
    private static int[] area2_2 = {2,1,1,1,2};
    private static int[] area3_2 = {2,1,1,1,2};
    private static int[] area4_2 = {0,2,2,2,0};

    public TileEntityTerraFirepit()
    {
        fuelTimeLeft = 375;
        fuelBurnTemp =  613;
        fuelBuildup = 0;
        fireTemperature = 350;
        AddedAir = 0F;

        fireItemStacks = new ItemStack[11];
        inputItemTemp = 0;
        ambientTemp = -1000;
        numAirBlocks = 0;

        externalFireCheckTimer = 0;
        externalWoodCount = 0;
        charcoalCounter = 0;

    }

    public Boolean addByproduct(ItemStack is)
    {
        if(fireItemStacks[2] == null)
        {
            fireItemStacks[2] = is;
            return true;
        }
        else if (fireItemStacks[2].itemID == is.itemID && fireItemStacks[2].getItemDamage() == is.getItemDamage() && fireItemStacks[2].stackSize < fireItemStacks[2].getMaxStackSize())
        {
            fireItemStacks[2].stackSize++;
            return true;
        }

        if(fireItemStacks[6] == null)
        {
            fireItemStacks[6] = is;
            return true;
        }
        else if (fireItemStacks[6].itemID == is.itemID  && fireItemStacks[6].getItemDamage() == is.getItemDamage() && fireItemStacks[6].stackSize < fireItemStacks[6].getMaxStackSize())
        {
            fireItemStacks[6].stackSize++;
            return true;
        }

        if(fireItemStacks[9] == null)
        {
            fireItemStacks[9] = is;
            return true;
        }
        else if (fireItemStacks[9].itemID == is.itemID && fireItemStacks[9].getItemDamage() == is.getItemDamage()  && fireItemStacks[9].stackSize < fireItemStacks[9].getMaxStackSize())
        {
            fireItemStacks[9].stackSize++;
            return true;
        }

        if(fireItemStacks[10] == null)
        {
            fireItemStacks[10] = is;
            return true;
        }
        else if (fireItemStacks[10].itemID == is.itemID && fireItemStacks[10].getItemDamage() == is.getItemDamage()  && fireItemStacks[10].stackSize < fireItemStacks[10].getMaxStackSize())
        {
            fireItemStacks[10].stackSize++;
            return true;
        }

        return false;
    }
    public void careForInventorySlot(int i, float startTemp)
    {
        NBTTagCompound inputCompound;
        HeatManager manager = HeatManager.getInstance();
        if(fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
        {
            HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
            float itemTemp = 0F;
            inputCompound = fireItemStacks[i].getTagCompound();
            itemTemp = inputCompound.getFloat("temperature");

            if(fireTemperature > itemTemp)
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempIncrease(fireItemStacks[i],itemTemp,fireTemperature);
                itemTemp += increase;
            }
            else if(fireTemperature < itemTemp)
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempDecrease(fireItemStacks[i]);
                itemTemp -= increase;
            }
            inputCompound.setFloat("temperature", itemTemp);
            fireItemStacks[i].setTagCompound(inputCompound);

            if(itemTemp <= ambientTemp)
            {
                fireItemStacks[i].stackTagCompound = null;
            }

            if(index.boilTemp <= itemTemp)
            {
                fireItemStacks[i] = null;
            }

        }
        else if(fireItemStacks[i] != null && !fireItemStacks[i].hasTagCompound())
        {
            if(TFCHeat.getMeltingPoint(fireItemStacks[i]) != -1)
            {
                inputCompound = new NBTTagCompound();
                inputCompound.setFloat("temperature", startTemp);
                fireItemStacks[i].setTagCompound(inputCompound);
            }
        }
    }
    @Override
    public void closeChest() {
        // TODO Auto-generated method stub

    }
    public void combineMetals(ItemStack InputItem, ItemStack DestItem)
    {
        int D1 = 100-InputItem.getItemDamage();
        int D2 = 100-DestItem.getItemDamage();
        //This was causing the infinite amounts possibly
        DestItem.setItemDamage(100-(D1 + D2));
    }
    
    public void CookItemNew()
    {
        HeatManager manager = HeatManager.getInstance();
        Random R = new Random();
        if(fireItemStacks[1] != null)
        {
            HeatIndex index = manager.findMatchingIndex(fireItemStacks[1]);
            if(index != null && inputItemTemp > index.meltTemp)
            {
                //Morph the input
                fireItemStacks[1] = index.getMorph();
                
                if(fireItemStacks[1] != null)
                {
                    //if the input is a new item, then apply the old temperature to it
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setFloat("temperature", inputItemTemp);
                    fireItemStacks[1].stackTagCompound = nbt;
                }
                
                ItemStack output = index.getOutput(R);
                
                //Check if we should combine the output with a pre-existing output
                if(output.getItem() instanceof ItemTerraMeltedMetal)
                {
                    int leftover = 0;
                    boolean addLeftover = false;
                    if(fireItemStacks[7] != null && output.getItem().shiftedIndex == fireItemStacks[7].getItem().shiftedIndex)
                    {
                        int amt1 = 100-output.getItemDamage();//the percentage of the output
                        int amt2 = 100-fireItemStacks[7].getItemDamage();//the percentage currently in the out slot
                        int amt3 = amt1 + amt2;//combined amount
                        leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
                        if(leftover > 0) addLeftover = true;
                        int amt4 = 100-amt3;//convert the percent back to mc damage
                        if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                        fireItemStacks[7] = output;
                        fireItemStacks[7].setItemDamage(amt4);
                        
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[7].stackTagCompound = nbt;
                    }
                    else if(fireItemStacks[8] != null && output.getItem().shiftedIndex == fireItemStacks[8].getItem().shiftedIndex)
                    {
                        int amt1 = 100-output.getItemDamage();//the percentage of the output
                        int amt2 = 100-fireItemStacks[8].getItemDamage();//the percentage currently in the out slot
                        int amt3 = amt1 + amt2;//combined amount
                        int amt4 = 100-amt3;//convert the percent back to mc damage
                        if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                        fireItemStacks[8].setItemDamage(amt4);
                        
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[8].stackTagCompound = nbt;
                    }
                    else if(fireItemStacks[7] != null && fireItemStacks[7].getItem() == mod_TFC_Game.terraCeramicMold)
                    {
                        fireItemStacks[7] = output;
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[7].stackTagCompound = nbt;
                    }
                    else if(fireItemStacks[8] != null && fireItemStacks[8].getItem() == mod_TFC_Game.terraCeramicMold)
                    {
                        fireItemStacks[8] = output;
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[8].stackTagCompound = nbt;
                    }
                    
                    if(addLeftover)
                    {
                        if(fireItemStacks[8] != null && output.getItem().shiftedIndex == fireItemStacks[8].getItem().shiftedIndex && fireItemStacks[8].getItemDamage() > 0)
                        {
                            int amt1 = 100-leftover;//the percentage of the output
                            int amt2 = 100-fireItemStacks[8].getItemDamage();//the percentage currently in the out slot
                            int amt3 = amt1 + amt2;//combined amount
                            int amt4 = 100-amt3;//convert the percent back to mc damage
                            if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                            fireItemStacks[8] = output;
                            fireItemStacks[8].setItemDamage(amt4);
                            
                            NBTTagCompound nbt = new NBTTagCompound();
                            nbt.setFloat("temperature", inputItemTemp);
                            fireItemStacks[8].stackTagCompound = nbt;
                        }
                        else if(fireItemStacks[8] != null && fireItemStacks[8].getItem() == mod_TFC_Game.terraCeramicMold)
                        {
                            fireItemStacks[8] = output;
                            fireItemStacks[8].setItemDamage(100-leftover);
                            NBTTagCompound nbt = new NBTTagCompound();
                            nbt.setFloat("temperature", inputItemTemp);
                            fireItemStacks[8].stackTagCompound = nbt;
                        }
                    }
                }
                else
                {
                    if(fireItemStacks[7] != null && 
                            fireItemStacks[7].getItem().shiftedIndex == output.getItem().shiftedIndex && 
                            fireItemStacks[7].stackSize + output.stackSize <= fireItemStacks[7].getMaxStackSize())
                    {
                        fireItemStacks[7].stackSize += output.stackSize; 
                    }
                    else if(fireItemStacks[8] != null && 
                            fireItemStacks[8].getItem().shiftedIndex == output.getItem().shiftedIndex && 
                            fireItemStacks[8].stackSize + output.stackSize <= fireItemStacks[8].getMaxStackSize())
                    {
                        fireItemStacks[8].stackSize += output.stackSize; 
                    }
                    else if(fireItemStacks[7] == null)
                    {
                        fireItemStacks[7] = output; 
                    }
                    else if(fireItemStacks[8] == null)
                    {
                        fireItemStacks[8] = output; 
                    }
                }
            }
        }
    }
  
    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(fireItemStacks[i] != null)
        {
            if(fireItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack = fireItemStacks[i];
                fireItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = fireItemStacks[i].splitStack(j);
            if(fireItemStacks[i].stackSize == 0)
            {
                fireItemStacks[i] = null;
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
            if(fireItemStacks[i]!= null)
            {
                entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, 
                        fireItemStacks[i]);
                entityitem.motionX = (float)rand.nextGaussian() * f3;
                entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }

    public void externalFireCheck()
    {
        Random R = new Random();
        if(externalFireCheckTimer == 0)
        {
            List vecArray = new ArrayList<Vector3f>();
            int oldWoodCount = externalWoodCount;
            externalWoodCount = 0;
            //Base Layer
            for(int x = 0; x < area.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord - 3);
                if(area[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord - 3));
                    }

                    if(area[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord - 3);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area0.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord - 2);
                if(area0[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord - 2));
                    }

                    if(area0[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord - 2);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area1.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord - 1);
                if(area1[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord - 1));
                    }

                    if(area1[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord - 1);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord);
                if(area2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord));
                    }

                    if(area2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area3.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord + 1);
                if(area3[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord +1));
                    }

                    if(area3[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord + 1);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area4.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord + 2);
                if(area4[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord + 2));
                    }

                    if(area4[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord + 2);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area5.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-3, yCoord, zCoord + 3);
                if(area5[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-3, yCoord, zCoord + 3));
                    }

                    if(area5[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord + 3);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }


            //Top Wood layer
            for(int x = 0; x < area0_2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-2, yCoord, zCoord - 2);
                if(area0_2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-2, yCoord, zCoord -2));
                    }

                    if(area0_2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord, zCoord - 2);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area1_2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-2, yCoord, zCoord - 1);
                if(area1_2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-2, yCoord, zCoord -1));
                    }

                    if(area1_2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord, zCoord - 1);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area2_2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-2, yCoord, zCoord);
                if(area2_2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-2, yCoord, zCoord));
                    }

                    if(area2_2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord, zCoord );
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area3_2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-2, yCoord, zCoord + 1);
                if(area3_2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-2, yCoord, zCoord + 1));
                    }

                    if(area3_2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord, zCoord + 1);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            for(int x = 0; x < area4_2.length; x++)
            {
                int id = worldObj.getBlockId(xCoord+x-2, yCoord, zCoord + 2);
                if(area4_2[x] != 0)
                {
                    if(id == 0) {
                        vecArray.add(new Vector3f(xCoord+x-2, yCoord, zCoord + 2));
                    }

                    if(area4_2[x] == 1 && id == mod_TFC_Core.LogPile.blockID)
                    {
                        TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord, zCoord + 2);
                        if(te != null)
                        {
                            if(te.storage[0] != null) {
                                externalWoodCount += te.storage[0].stackSize;
                            }
                            if(te.storage[1] != null) {
                                externalWoodCount += te.storage[1].stackSize;
                            }
                            if(te.storage[2] != null) {
                                externalWoodCount += te.storage[2].stackSize;
                            }
                            if(te.storage[3] != null) {
                                externalWoodCount += te.storage[3].stackSize;
                            }
                        }
                    }
                }
            }
            //Top dirt layer
            for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                    int id = worldObj.getBlockId(xCoord+x, yCoord+2, zCoord +z);
                    if(id == 0 || worldObj.getBlockMaterial(xCoord+x, yCoord+2, zCoord+z) == Material.wood)
                    {
                        vecArray.add(new Vector3f(xCoord+x, yCoord+2, zCoord + z));
                    }
                }
            }

            //Now we actually set fire to the air blocks
            if(vecArray.size() > 0)
            {

                for(int i = 0; i < vecArray.size(); i++)
                {
                    Vector3f vec = (Vector3f)vecArray.toArray()[i];
                    if(R.nextInt(100) > 75 && getNearWood((int)vec.X, (int)vec.Y, (int)vec.Z)) 
                    {
                        worldObj.setBlock((int)vec.X, (int)vec.Y, (int)vec.Z, Block.fire.blockID);
                        worldObj.markBlockNeedsUpdate((int)vec.X, (int)vec.Y, (int)vec.Z);
                    }
                }
            }


            //This is where we handle the counter for producing charcoal. Once it reaches 24hours, we add charcoal to the fire and remove the wood.
            if(oldWoodCount != externalWoodCount) {
                charcoalCounter = 0;
            } else {
                charcoalCounter++;
            }

            //Minecraft mc = ModLoader.getMinecraftInstance();
            //mc.ingameGUI.addChatMessage("charcoalCounter:" + charcoalCounter);

            if(charcoalCounter == FIREBURNTIME)
            {
                charcoalCounter = 0;
                float percent = 25+R.nextInt(25);
                externalWoodCount = (int) (externalWoodCount * (percent/100));
                while(externalWoodCount > 0)
                {
                    if(externalWoodCount > Item.coal.getItemStackLimit())
                    {
                        addByproduct(new ItemStack(Item.coal,Item.coal.getItemStackLimit(),1));
                        externalWoodCount -= Item.coal.getItemStackLimit();
                    }
                    else
                    {
                        addByproduct(new ItemStack(Item.coal,externalWoodCount,1));
                        externalWoodCount = 0;
                    }


                }
                //Empty the fuel stack and set the fire out. It shouldn't be on after all this time.
                fireItemStacks[0] = null;
                fireItemStacks[3] = null;
                fireItemStacks[4] = null;
                fireItemStacks[5] = null;
                fuelTimeLeft = 0;
                fuelBurnTemp = ambientTemp;
                fireTemperature = ambientTemp;


                for(int x = 0; x < area0.length; x++)
                {
                    if(area0[x] == 1 && worldObj.getBlockId(xCoord+x-3, yCoord, zCoord - 2) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord - 2)).clearContents();
                        worldObj.setBlock(xCoord+x-3, yCoord, zCoord - 2, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-3, yCoord, zCoord - 2);
                    }
                }
                for(int x = 0; x < area1.length; x++)
                {
                    if(area1[x] == 1 && worldObj.getBlockId(xCoord+x-3, yCoord, zCoord - 1) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord - 1)).clearContents();
                        worldObj.setBlock(xCoord+x-3, yCoord, zCoord - 1, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-3, yCoord, zCoord - 1);
                    }
                }
                for(int x = 0; x < area2.length; x++)
                {
                    if(area2[x] == 1 && worldObj.getBlockId(xCoord+x-3, yCoord, zCoord) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord)).clearContents();
                        worldObj.setBlock(xCoord+x-3, yCoord, zCoord , 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-3, yCoord, zCoord);
                    }
                }
                for(int x = 0; x < area3.length; x++)
                {
                    if(area3[x] == 1 && worldObj.getBlockId(xCoord+x-3, yCoord, zCoord + 1) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord +1)).clearContents();
                        worldObj.setBlock(xCoord+x-3, yCoord, zCoord + 1, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-3, yCoord, zCoord +1);
                    }
                }
                for(int x = 0; x < area4.length; x++)
                {
                    if(area4[x] == 1 && worldObj.getBlockId(xCoord+x-3, yCoord, zCoord + 2) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-3, yCoord, zCoord +2)).clearContents();
                        worldObj.setBlock(xCoord+x-3, yCoord, zCoord + 2, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-3, yCoord, zCoord + 2);
                    }
                }

                //Top Wood layer
                for(int x = 0; x < area0_2.length; x++)
                {
                    if(area0_2[x] == 1 && worldObj.getBlockId(xCoord+x-2, yCoord+1, zCoord - 2) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord+1, zCoord - 2)).clearContents();
                        worldObj.setBlock(xCoord+x-2, yCoord+1, zCoord - 2, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-2, yCoord+1, zCoord - 2);
                    }
                }
                for(int x = 0; x < area1_2.length; x++)
                {
                    if(area1_2[x] == 1 && worldObj.getBlockId(xCoord+x-2, yCoord+1, zCoord - 1) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord+1, zCoord - 1)).clearContents();
                        worldObj.setBlock(xCoord+x-2, yCoord+1, zCoord - 1, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-2, yCoord+1, zCoord - 1);
                    }
                }
                for(int x = 0; x < area2_2.length; x++)
                {
                    if(area2_2[x] == 1 && worldObj.getBlockId(xCoord+x-2, yCoord+1, zCoord) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord+1, zCoord)).clearContents();
                        worldObj.setBlock(xCoord+x-2, yCoord+1, zCoord, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-2, yCoord+1, zCoord);
                    }
                }
                for(int x = 0; x < area3_2.length; x++)
                {
                    if(area3_2[x] == 1 && worldObj.getBlockId(xCoord+x-2, yCoord+1, zCoord + 1) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord+1, zCoord +1)).clearContents();
                        worldObj.setBlock(xCoord+x-2, yCoord+1, zCoord + 1, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-2, yCoord+1, zCoord +1);
                    }
                }
                for(int x = 0; x < area4_2.length; x++)
                {
                    if(area4_2[x] == 1 && worldObj.getBlockId(xCoord+x-2, yCoord+1, zCoord + 2) == mod_TFC_Core.LogPile.blockID)
                    {
                        ((TileEntityTerraLogPile)worldObj.getBlockTileEntity(xCoord+x-2, yCoord+1, zCoord + 2)).clearContents();
                        worldObj.setBlock(xCoord+x-2, yCoord+1, zCoord + 2, 0);
                        worldObj.markBlockNeedsUpdate(xCoord+x-2, yCoord+1, zCoord + 2);
                    }
                }
            }
        }
    }

    public float getInputTemp()
    {
        if(fireItemStacks[1] != null && fireItemStacks[1].hasTagCompound()) {
            return fireItemStacks[1].getTagCompound().getFloat("temperature");
        } else {
            return ambientTemp;
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        // TODO Auto-generated method stub
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "Firepit";
    }

    public Boolean getNearWood(int x, int y, int z)
    {
        if(worldObj.getBlockMaterial(x+1, y, z) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x-1, y, z) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x, y+1, z) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x, y-1, z) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x, y, z+1) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x, y, z-1) == Material.wood)
        {
            return true;
        }
        if(worldObj.getBlockMaterial(x, y, z-1) == Material.wood)
        {
            return true;
        }
        return false;
    }
    public float getOutput1Temp()
    {
        if(fireItemStacks[7] != null && fireItemStacks[7].hasTagCompound()) {
            return fireItemStacks[7].getTagCompound().getFloat("temperature");
        } else {
            return -1000;
        }
    }
    public float getOutput2Temp()
    {
        if(fireItemStacks[8] != null && fireItemStacks[8].hasTagCompound()) {
            return fireItemStacks[8].getTagCompound().getFloat("temperature");
        } else {
            return -1000;
        }
    }

    @Override
    public int getSizeInventory()
    {
        return fireItemStacks.length;
    }
    @Override
    public ItemStack getStackInSlot(int i)
    {
        // TODO Auto-generated method stub
        return fireItemStacks[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getSurroundedByWood(int x, int y, int z)
    {
        int count = 0;
        if(worldObj.getBlockMaterial(x+1, y, z) == Material.wood)
        {
            count++;
        }
        if(worldObj.getBlockMaterial(x-1, y, z) == Material.wood)
        {
            count++;
        }
        if(worldObj.getBlockMaterial(x, y+1, z) == Material.wood)
        {
            count++;
        }
        if(worldObj.getBlockMaterial(x, y, z+1) == Material.wood)
        {
            count++;
        }
        if(worldObj.getBlockMaterial(x, y, z-1) == Material.wood)
        {
            count++;
        }
        return count;
    }

    public int getTemperatureScaled(int s)
    {
        return (int)(fireTemperature * s) / 1200;
    }

    public void HandleFuelStack()
    {
        if(fireItemStacks[3] == null && fireItemStacks[0] != null)
        {
            fireItemStacks[3] = fireItemStacks[0];
            fireItemStacks[0] = null;
        }
        if(fireItemStacks[4] == null && fireItemStacks[3] != null)
        {
            fireItemStacks[4] = fireItemStacks[3];
            fireItemStacks[3] = null;
        }
        if(fireItemStacks[5] == null && fireItemStacks[4] != null)
        {
            fireItemStacks[5] = fireItemStacks[4];
            fireItemStacks[4] = null;
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
        timeleft = nbttagcompound.getInteger("timeleft");
        fireTemperature = nbttagcompound.getFloat("temperature");
        fuelTimeLeft = nbttagcompound.getFloat("fuelTimeLeft");
        fuelBurnTemp = nbttagcompound.getFloat("fuelBurnTemp");
        charcoalCounter = nbttagcompound.getInteger("charcoalCounter");
        airFromBellowsTime = nbttagcompound.getFloat("airFromBellowsTime");
        airFromBellows = nbttagcompound.getFloat("airFromBellows");

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        fireItemStacks = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < fireItemStacks.length)
            {
                fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        fireItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }


    }

    public void setNumAirBlocks(int n)
    {
        numAirBlocks = n;
    }

    public void updateEntity()
    {
        int Surrounded = getSurroundedByWood(xCoord,yCoord,zCoord);
        if(fireTemperature > 210 && Surrounded == 5)
        {
            externalFireCheckTimer--;
            if(externalFireCheckTimer <= 0)
            {
                externalFireCheck();
                externalFireCheckTimer = 100;
            }
        }
        else
        {
            charcoalCounter = 0;
        }
        
        if(!worldObj.isRemote)
        {
            
            HeatManager manager = HeatManager.getInstance();
            //Here we take care of the item that we are cooking in the fire
            NBTTagCompound inputCompound;
            if(fireItemStacks[1]!= null && fireItemStacks[1].hasTagCompound())
            {
                HeatIndex index = manager.findMatchingIndex(fireItemStacks[1]);
                inputCompound = fireItemStacks[1].getTagCompound();
                inputItemTemp = inputCompound.getFloat("temperature");
                Item1Melted = inputCompound.getBoolean("Item1Melted");

                if(fireTemperature > inputItemTemp)
                {
                    float increase = TFCHeat.getTempIncrease(fireItemStacks[1], inputItemTemp, fireTemperature);
                    inputItemTemp += increase;
                }
                if(fireTemperature < inputItemTemp)
                {
                    float increase = TFCHeat.getTempDecrease(fireItemStacks[1]);
                    inputItemTemp -= increase;
                }

                inputCompound.setFloat("temperature", inputItemTemp);
                fireItemStacks[1].setTagCompound(inputCompound);
                if(index.boilTemp <= inputItemTemp)
                {
                    fireItemStacks[1] = null;
                }
            }
            else if(fireItemStacks[1] != null && !fireItemStacks[1].hasTagCompound())
            {
                if(TFCHeat.getMeltingPoint(fireItemStacks[1]) != -1)
                {
                    inputCompound = new NBTTagCompound();
                    inputCompound.setFloat("temperature", ambientTemp);
                    inputCompound.setBoolean("Item1Melted",false);
                    fireItemStacks[1].setTagCompound(inputCompound);
                    Item1Melted = false;
                    inputItemTemp = ambientTemp;
                }
            }
            else if(fireItemStacks[1] == null)
            {
                inputItemTemp = ambientTemp;
            }
            //careForInventorySlot(1,fireTemperature);
            //careForInventorySlot(6,fireTemperature);
            careForInventorySlot(7,fireTemperature);
            careForInventorySlot(8,fireTemperature);

            ItemStack[] FuelStack = new ItemStack[4];
            FuelStack[0] = fireItemStacks[0];
            FuelStack[1] = fireItemStacks[3];
            FuelStack[2] = fireItemStacks[4];
            FuelStack[3] = fireItemStacks[5];

            TFCHeat.HandleContainerHeat(this.worldObj, FuelStack, xCoord,yCoord,zCoord);



            //Now we cook the input item
            //CookItem();
            CookItemNew();

            //push the input fuel down the stack
            HandleFuelStack();
            if(ambientTemp == -1000)	
            {
                BiomeGenBase var25 = worldObj.getBiomeGenForCoords(xCoord, zCoord);
                float a = var25.getFloatTemperature();
                a = a / 2.0F;//Normalize the value to between 0 and 1
                ambientTemp = 62 * a-20;
            }
            //here we set the various temperatures to range
            if(fireTemperature > 1200F) {
                fireTemperature = 1200F;
            } else if(fireTemperature < ambientTemp) {
                fireTemperature = ambientTemp;
            }

            if(fireTemperature < 210 && fireTemperature != ambientTemp && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1);
            }
            else if(fireTemperature < 100 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0);
                BlockTerraFirepit.updateFurnaceBlockState(false, worldObj, xCoord, yCoord, zCoord);
            }

            //If the fire is still burning and has fuel
            if(fuelTimeLeft > 0 && fireTemperature >= 210 && Surrounded != 5)
            {

                if(worldObj.getBlockId(xCoord, yCoord, zCoord) != mod_TFC_Game.terraFirepitOn.blockID) {
                    BlockTerraFirepit.updateFurnaceBlockState(true, worldObj, xCoord, yCoord, zCoord);
                }

                float desiredTemp = 0;
                if(Surrounded != 5)
                {
                    fuelTimeLeft--;
                    if(airFromBellowsTime > 0)
                    {
                        fuelTimeLeft--;
                    }

                    if(numAirBlocks == 0)
                    {
                        for (int x = -1; x < 2; x++)
                        {
                            for (int y = 0; y < 3; y++)
                            {
                                for (int z = -1; z < 2; z++)
                                {
                                    if(worldObj.getBlockId(xCoord+x, yCoord+y, zCoord+z) == 0) {
                                        numAirBlocks++;
                                    }
                                }
                            }
                        }
                    }

                    float bAir = airFromBellows*(1+(float)airFromBellowsTime/120);

                    AddedAir = (float)(numAirBlocks+bAir)/25/16;//1038.225 Max //0.3625

                    if(yCoord > 145 && yCoord < 210)
                    {					
                        float w = 210 - yCoord;
                        float w1 = w / 210;
                        float w2 = 1 - w1;
                        float w3 = w2 * 0.105F;

                        AddedAir += w3;//0.0725 min / 0.1045 max
                    }
                    else if(yCoord >= 210)
                    {
                        AddedAir += 0.1045F;
                    }

                    desiredTemp = fuelBurnTemp + fuelBurnTemp * AddedAir;
                }
                else
                {
                    desiredTemp = 300;
                }

                if(fireTemperature < desiredTemp)
                {
                    fireTemperature+=0.85F;
                }
                else if(fireTemperature > desiredTemp)
                {
                    if(desiredTemp != ambientTemp)
                    {
                        if(airFromBellows == 0) {
                            fireTemperature-=0.125F;
                        } else {
                            fireTemperature-=0.08F;
                        }
                    }
                }
            }
            else if(fuelTimeLeft <= 0 && fireTemperature >= 210 && fireItemStacks[5] != null)
            {
                if(fireItemStacks[5] != null)
                {
                    EnumWoodMaterial m = TFC_Game.getWoodMaterial(fireItemStacks[5]);
                    fireItemStacks[5] = null;

                    fuelTimeLeft = m.burnTimeMax;
                    fuelBurnTemp = m.burnTempMax;
                }
            }
            //If there is no more fuel and the fire is still hot, we start to cool it off.
            if(fuelTimeLeft <= 0 && fireTemperature > ambientTemp)
            {
                if(airFromBellows == 0) {
                    fireTemperature-=0.125F;
                } else {
                    fireTemperature-=0.08F;
                }
            }

            //Here we handle the bellows
            if(airFromBellowsTime > 0)
            {
                airFromBellowsTime--;
                airFromBellows = (float)airFromBellowsTime/120*10;
            }

            //do a last minute check to verify stack size
            if(fireItemStacks[7] != null)
            {
                if(fireItemStacks[7].stackSize <= 0) {
                    fireItemStacks[7].stackSize = 1;
                }
            }
            if(fireItemStacks[8] != null)
            {
                if(fireItemStacks[8].stackSize <= 0) {
                    fireItemStacks[8].stackSize = 1;
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("timeleft", timeleft);
        nbttagcompound.setFloat("temperature", fireTemperature);
        nbttagcompound.setFloat("fuelTimeLeft", fuelTimeLeft);
        nbttagcompound.setFloat("fuelBurnTemp", fuelBurnTemp);
        nbttagcompound.setInteger("charcoalCounter", charcoalCounter);
        nbttagcompound.setFloat("airFromBellowsTime", airFromBellowsTime);
        nbttagcompound.setFloat("airFromBellows", airFromBellows);


        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < fireItemStacks.length; i++)
        {
            if(fireItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                fireItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);

    }

    public void handlePacketData( int charcoal) 
    {
        TileEntityTerraFirepit pile = this;
        //charcoalCounter = charcoal;

    }
    
    public Packet getDescriptionPacket() 
    {
        return PacketHandler.getPacket(this);
      }
}
