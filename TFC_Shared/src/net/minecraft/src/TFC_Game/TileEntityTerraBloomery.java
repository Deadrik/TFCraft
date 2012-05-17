package net.minecraft.src.TFC_Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.ItemTerraSmallOre;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCHeat;

public class TileEntityTerraBloomery extends TileEntityFireEntity implements IInventory
{
    public float fuelTimeLeft;
    public float fuelBurnTemp;
    public float fireTemperature;

    public float AddedAir;
    public boolean isValid;

    public ItemStack input[];
    public ItemStack fireItemStacks[];
    public ItemStack outputItemStacks[];
    public float ambientTemp;
    Boolean Item1Melted = false;
    public float inputItemTemps[];

    private int prevStackSize;
    private int numAirBlocks;

    public String OreType;


    //Bloomery
    int charcoalCount;
    int oreCount;
    float outCount;

    public TileEntityTerraBloomery()
    {
        fuelTimeLeft = 0;
        fuelBurnTemp =  0;

        fireTemperature = 0;
        AddedAir = 0F;
        isValid = false;
        OreType = "";
        fireItemStacks = new ItemStack[20];
        outputItemStacks = new ItemStack[20];
        input = new ItemStack[1];
        inputItemTemps = new float[40];
        ambientTemp = -1000;
        numAirBlocks = 0;
        airFromBellows = 0F;
        airFromBellowsTime = 0;
        charcoalCount = 0;
        oreCount = 0;
        outCount = 0;
    }

    public void careForInventorySlot(int i, float startTemp)
    {
        NBTTagCompound inputCompound;
        float mod = 1;
        if(fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
        {
            inputCompound = fireItemStacks[i].getTagCompound();
            inputItemTemps[i] = inputCompound.getFloat("temperature");


            if(fireTemperature*mod > inputItemTemps[i])
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempIncrease(fireItemStacks[i], inputItemTemps[i], fireTemperature*mod);
                inputItemTemps[i] += increase;
            }
            else if(fireTemperature*mod < inputItemTemps[i])
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempDecrease(fireItemStacks[i]);
                inputItemTemps[i] -= increase;
            }
            inputCompound.setFloat("temperature", inputItemTemps[i]);
            fireItemStacks[i].setTagCompound(inputCompound);

            if(inputItemTemps[i] <= ambientTemp)
            {
                fireItemStacks[i].stackTagCompound = null;
            }

            if(TFCHeat.getIsBoiling(fireItemStacks[i]))
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
        else if(fireItemStacks[i] == null)
        {
            inputItemTemps[i] = 0;
        }
    }

    public void careForOutputSlot(int i)
    {
        NBTTagCompound inputCompound;
        float mod = 1;
        if(outputItemStacks[i]!= null && outputItemStacks[i].hasTagCompound())
        {
            inputCompound = outputItemStacks[i].getTagCompound();
            inputItemTemps[10+i] = inputCompound.getFloat("temperature");


            if(fireTemperature*mod > inputItemTemps[10+i])
            {
                String name = outputItemStacks[i].getItem().getItemNameIS(outputItemStacks[i]);
                float increase = TFCHeat.getTempIncrease(outputItemStacks[i], inputItemTemps[10+i], fireTemperature*mod);
                inputItemTemps[10+i] += increase;
            }
            else if(fireTemperature*mod < inputItemTemps[i])
            {
                String name = outputItemStacks[i].getItem().getItemNameIS(outputItemStacks[i]);
                float increase = TFCHeat.getTempDecrease(outputItemStacks[i]);
                inputItemTemps[10+i] -= increase;
            }
            inputCompound.setFloat("temperature", inputItemTemps[10+i]);
            outputItemStacks[i].setTagCompound(inputCompound);

            if(inputItemTemps[10+i] <= ambientTemp)
            {
                outputItemStacks[i].stackTagCompound = null;
            }

            if(TFCHeat.getIsBoiling(outputItemStacks[i]))
            {
                outputItemStacks[i] = null;
            }

        }
        else if(outputItemStacks[i] == null)
        {
            inputItemTemps[10+i] = 0;
        }
    }

    private Boolean CheckValidity() 
    {
        if(!worldObj.isBlockNormalCube(xCoord, yCoord+1, zCoord))
        {
            return false;
        }
        if(!worldObj.isBlockNormalCube(xCoord, yCoord-1, zCoord))
        {
            return false;
        }

        return true;
    }

    @Override
    public void closeChest() {
        // TODO Auto-generated method stub

    }

    public int combineMetals(ItemStack InputItem, ItemStack DestItem)
    {
        int D1 = 100-InputItem.getItemDamage();
        int D2 = 100-DestItem.getItemDamage();

        int damage = 100-(D1 + D2);

        DestItem.setItemDamage(damage);
        if(damage < 0) {
            return 100-damage;
        }

        return 0;//returns false if there is no metal left over to combine
    }

    public void CookItems(int i)
    {
        //Now we perform the actual cooking
        if(fireItemStacks[i] != null)
        {
            String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
            if(TFCHeat.ItemHeatData.containsKey(name) && fireItemStacks[i].hasTagCompound())
            {
                NBTTagCompound nbt = fireItemStacks[i].getTagCompound();
                Object[] meltData = (Object[])TFCHeat.ItemHeatData.get(fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]));
                float meltTemp = (Float)meltData[2];
                float boilTemp = (Float)meltData[1];
                float meltTemp2 = -1; 
                ItemStack OutputItem2 = null;
                if(meltData.length > 4)
                {
                    meltTemp2= (Float)meltData[4];
                    OutputItem2 = (ItemStack)meltData[5];
                }
                Boolean Item1Melted = nbt.hasKey("Item1Melted") ? nbt.getBoolean("Item1Melted") : false;
                ItemStack OutputItem1 = (ItemStack)meltData[3];

                String outName = OutputItem1.getItem().getItemNameIS(OutputItem1);
                ItemStack InputItem = fireItemStacks[i];
                String inName = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);


                if(inputItemTemps[i] >= meltTemp && !Item1Melted)
                {
                    if(AddOreToOutput(OutputItem1,fireItemStacks[i]))
                    {
                        if(meltTemp2 != -1)
                        {
                            nbt.setBoolean("Item1Melted", true);
                            fireItemStacks[i].setTagCompound(nbt);
                        }
                        else
                        {
                            fireItemStacks[i] = null;
                        }
                    }
                }
                else if(Item1Melted && inputItemTemps[i] >= meltTemp2 && OutputItem2 != null )
                {
                    if(AddOreToOutput(OutputItem2,fireItemStacks[i]))
                    {
                        fireItemStacks[i] = null;
                    }
                }
            }
            else
            {
                fireItemStacks[i].setTagCompound(new NBTTagCompound());
            }
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(input[i] != null)
        {
            if(input[i].stackSize <= j)
            {
                ItemStack itemstack = input[i];
                input[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = input[i].splitStack(j);
            if(input[i].stackSize == 0)
            {
                input[i] = null;
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

    @Override
    public int getInventoryStackLimit()
    {
        // TODO Auto-generated method stub
        return 1;
    }


    @Override
    public String getInvName()
    {
        return "Bloomery";
    }

    @Override
    public int getSizeInventory()
    {
        return input.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        // TODO Auto-generated method stub
        return input[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getTemperatureScaled(int s)
    {
        return (int)(fireTemperature * s) / 2500;
    }

    public void HandleTemperature()
    {
        int[] direction = BlockTerraBloomery.headBlockToFootBlockMap[worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3];
        if(ambientTemp == -1000)	
        {
            BiomeGenBase var25 = worldObj.getBiomeGenForCoords(xCoord, zCoord);
            float a = var25.getFloatTemperature();
            a = a / 2.0F;//Normalize the value to between 0 and 1
            ambientTemp = 62 * a-20;
        }

        //Now we increase the temperature
        //If the fire is still burning and has fuel
        if(fuelTimeLeft > 0)
        {
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            float desiredTemp = 0;

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

            AddedAir = (float)(numAirBlocks+bAir)/25/16;

//            if(yCoord > 60)
//            {					
//                float w = worldObj.getHeight() - yCoord;
//                float w1 = w / worldObj.getHeight();
//                float w2 = 1 - w1;
//                float w3 = w2 * 0.105F;
//
//                AddedAir += w3;
//            }

            desiredTemp = fuelBurnTemp + fuelBurnTemp * AddedAir;

            if(fireTemperature < desiredTemp)
            {
                float t = 1.35F;
                if(oreCount > charcoalCount) {
                    t*=charcoalCount/oreCount;
                }
                fireTemperature+=t;
            }
            else if(fireTemperature > desiredTemp)
            {
                if(desiredTemp != ambientTemp)
                {
                    if(airFromBellows == 0) {
                        fireTemperature-=0.225F;
                    } else {
                        fireTemperature-=0.18F;
                    }
                }
            }
        }
        else if(fuelTimeLeft <= 0 && charcoalCount > 0)
        {
            charcoalCount--;
            updateGui();
            fuelTimeLeft = 1875;
            fuelBurnTemp = 1450;
            if(fireTemperature < 210)
            {
                fireTemperature = 220;
            }
            if(worldObj.getBlockId(xCoord, yCoord, zCoord) == mod_TFC_Game.terraBloomery.blockID) {
                BlockTerraBloomery.updateFurnaceBlockState(true, worldObj, xCoord, yCoord, zCoord);
            }
        }
        else
        {
            updateGui();
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            if(worldObj.getBlockId(xCoord, yCoord, zCoord) == mod_TFC_Game.terraBloomeryOn.blockID) {
                BlockTerraBloomery.updateFurnaceBlockState(false, worldObj, xCoord, yCoord, zCoord);
            }
            fuelBurnTemp = 0;
            if(fireTemperature > ambientTemp)
            {
                fireTemperature-=0.425F;
            }
        }



        //here we set the various temperatures to range
        if(fireTemperature > 2500F) {
            fireTemperature = 2500F;
        } else if(fireTemperature < ambientTemp) {
            fireTemperature = ambientTemp;
        }

        //Here we handle the bellows
        if(airFromBellowsTime > 0)
        {
            airFromBellowsTime--;
            airFromBellows = (float)airFromBellowsTime/120*10;
        }

    }

    public boolean isStackValid(int i, int j, int k)
    {
        if(worldObj.getBlockId(i, j-1, k) != mod_TFC_Game.terraMolten.blockID && worldObj.getBlockMaterial(i, j-1, k) != Material.rock)
        {
            return false;
        }
        if(worldObj.getBlockMaterial(i+1, j, k) != Material.rock)
        {
            return false;
        }
        if(worldObj.getBlockMaterial(i-1, j, k) != Material.rock)
        {
            return false;
        }
        if(worldObj.getBlockMaterial(i, j, k+1) != Material.rock)
        {
            return false;
        }
        if(worldObj.getBlockMaterial(i, j, k-1) != Material.rock)
        {
            return false;
        }

        return true;
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

    public boolean AddOreToFire(ItemStack is)
    {
        for (int i = 0; i < fireItemStacks.length; i++)
        {
            if(fireItemStacks[i] == null)
            {
                fireItemStacks[i] = is;
                OreType = ItemTerraSmallOre.getItemNameDamage(is.getItemDamage());
                return true;
            }
        }
        return false;
    }

    public boolean AddOreToOutput(ItemStack is, ItemStack is2)
    {
        ItemStack work = new ItemStack(is.itemID,is.stackSize,is.getItemDamage());
        /* First we check to see if there are any mergeable items*/
        for (int i = 0; i < outputItemStacks.length; i++)
        {
            if(outputItemStacks[i] != null && outputItemStacks[i].getItem().getItemNameIS(outputItemStacks[i]) == work.getItem().getItemNameIS(work))
            {
                if(outputItemStacks[i].getItemDamage() > 0 && work.getItemDamage() > 0)
                {
                    int amt = combineMetals(work,outputItemStacks[i]);
                    if(amt != 0)
                    {
                        work.setItemDamage(amt);
                    } else {
                        oreCount--;
                        
                        return true;
                    }
                }
            }
        }
        /*If not then the item takes a new slot*/
        for (int i = 0; i < outputItemStacks.length; i++)
        {
            if(outputItemStacks[i] == null)
            {
                outputItemStacks[i] = work;
                oreCount--;
                TFCHeat.SetTemperature(outputItemStacks[i], TFCHeat.GetTemperature(is2));
                return true;
            }
        }
        return false;
    }

    public boolean RemoveOre()
    {
        for (int i = 0; i < outputItemStacks.length; i++)
        {
            if(outputItemStacks[i] != null)
            {
                input[0] = outputItemStacks[i];
                if(input[0].stackSize <= 0) {
                    input[0].stackSize = 1;
                }
                outCount -= outputItemStacks[i].getItemDamage();
                outputItemStacks[i] = null;

                return true;
            }
        }
        return false;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        input[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }


    }

    public void setNumAirBlocks(int n)
    {
        numAirBlocks = n;
    }

    public float getOutputCount()
    {
        float out = 0;
        for(int i = 0; i < outputItemStacks.length; i++)
        {
            if(outputItemStacks[i] != null)
            {
                out += 100-outputItemStacks[i].getItemDamage();
            }
        }

        return out;
    }

    int oreDamage = -1;

    public void updateEntity()
    {
        if(!worldObj.isRemote)
        {
            outCount = getOutputCount();
            if(outCount < 0)
                outCount = 0;
            if(oreCount < 0)
                oreCount = 0;
            if(charcoalCount < 0)
                charcoalCount = 0;

            if(oreCount == 0 && outCount == 0)
            {
                OreType = "";
                oreDamage = -1;
            }

            //Do the funky math to find how many molten blocks should be placed
            float count = charcoalCount+oreCount;

            int moltenCount = 0;
            if(count > 0 && count < 1) {
                moltenCount = 1;
            } else
            {
                moltenCount = (int)Math.ceil(count / 8);
            }

            //get the direction that the bloomery is facing so that we know where the stack should be
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;

            int[] direction = BlockTerraBloomery.headBlockToFootBlockMap[meta];

            /*Fill the bloomery stack with molten ore. */
            for (int i = 0; i < 5; i++)
            {
                /*The stack must be air or already be molten rock*/
                if(worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == 0 ||
                        worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == mod_TFC_Game.terraMolten.blockID)
                {
                    //Make sure that the Stack is surrounded by rock
                    if(i < moltenCount && isStackValid(xCoord+direction[0], yCoord+i, zCoord+direction[1])) {
                        worldObj.setBlock(xCoord+direction[0], yCoord+i, zCoord+direction[1], mod_TFC_Game.terraMolten.blockID);
                        worldObj.markBlockNeedsUpdate(xCoord+direction[0], yCoord+i, zCoord+direction[1]);
                    } else {
                        worldObj.setBlock(xCoord+direction[0], yCoord+i, zCoord+direction[1], 0);
                        worldObj.markBlockNeedsUpdate(xCoord+direction[0], yCoord+i, zCoord+direction[1]);
                    }
                }
            }
            /*Create a list of all the items that are falling into the stack */
            List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
                    xCoord+direction[0], yCoord+moltenCount, zCoord+direction[1], 
                    xCoord+direction[0]+1, yCoord+moltenCount+1, zCoord+direction[1]+1));

            /*Make sure the list isn't null or empty and that the stack is valid 1 layer above the Molten Ore*/
            if (list != null && !list.isEmpty() && isStackValid(xCoord+direction[0], yCoord+moltenCount, zCoord+direction[1]))
            {
                /*Iterate through the list and check for charcoal, coke, and ore*/
                for (Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    EntityItem entity = (EntityItem)iterator.next();
                    String name = mod_TFC_Game.proxy.getDisplayName(entity.item) ;
                    if(entity.item.itemID == Item.coal.shiftedIndex && entity.item.getItemDamage() == 1 || entity.item.itemID == mod_TFC_Game.Coke.shiftedIndex)
                    {
                        for(int c = 0; c < entity.item.stackSize; c++)
                        {
                            if(charcoalCount+oreCount < 40 && charcoalCount < 20)
                            {
                                charcoalCount++;
                                entity.item.stackSize--;
                            }
                        }
                        if(entity.item.stackSize == 0) {
                            entity.setDead();
                        }
                    }
                    /*If the item that's been tossed in is a type of Ore and it can melt down into something then add the ore to the list of items in the fire.*/
                    else if(TFCHeat.getMeltingPoint(entity.item) != -1 && entity.item.getItem() instanceof ItemTerraSmallOre && 
                            (entity.item.getItemDamage() == oreDamage || OreType.contentEquals("")))
                    {
                        int c = 0;
                        for(; c < entity.item.stackSize; c++)
                        {
                            if(charcoalCount+oreCount < 40 && oreCount < 20)
                            {
                                if(AddOreToFire(entity.item)) 
                                {
                                    oreCount+=1;
                                    oreDamage = entity.item.getItemDamage();
                                }
                            }
                        }
                        if(entity.item.stackSize == c) {
                            entity.setDead();
                        }
                    }
                }
            }

            /*Handle the temperature of the Bloomery*/
            HandleTemperature();

            for(int i = 0; i < fireItemStacks.length; i++)
            {
                /*Handle temperature for each item in the stack*/
                careForInventorySlot(i,100);
                /*Cook each input item */
                CookItems(i);

                careForOutputSlot(i);
            }

            if(input[0]!= null && input[0].itemID == mod_TFC_Game.terraCeramicMold.shiftedIndex)
            {
                RemoveOre();
            }

            //Here we make sure that the forge is valid
            isValid = CheckValidity();


        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setFloat("temperature", fireTemperature);
        nbttagcompound.setFloat("fuelTimeLeft", fuelTimeLeft);
        nbttagcompound.setFloat("fuelBurnTemp", fuelBurnTemp);
        nbttagcompound.setFloat("airFromBellowsTime", airFromBellowsTime);
        nbttagcompound.setFloat("airFromBellows", airFromBellows);
        nbttagcompound.setBoolean("isValid", isValid);
        nbttagcompound.setInteger("charcoalCount", charcoalCount);
        nbttagcompound.setInteger("oreCount", oreCount);
        nbttagcompound.setString("OreType", OreType);

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

        NBTTagList nbttaglist2 = new NBTTagList();
        for(int i = 0; i < input.length; i++)
        {
            if(input[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                input[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Input", nbttaglist2);

        NBTTagList nbttaglist3 = new NBTTagList();
        for(int i = 0; i < outputItemStacks.length; i++)
        {
            if(outputItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                outputItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Output", nbttaglist3);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        fireTemperature = nbttagcompound.getFloat("temperature");
        fuelTimeLeft = nbttagcompound.getFloat("fuelTimeLeft");
        fuelBurnTemp = nbttagcompound.getFloat("fuelBurnTemp");
        airFromBellowsTime = nbttagcompound.getFloat("airFromBellowsTime");
        airFromBellows = nbttagcompound.getFloat("airFromBellows");
        isValid = nbttagcompound.getBoolean("isValid");
        charcoalCount = nbttagcompound.getInteger("charcoalCount");
        oreCount = nbttagcompound.getInteger("oreCount");
        OreType = nbttagcompound.getString("OreType");

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        fireItemStacks = new ItemStack[20];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < fireItemStacks.length)
            {
                fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        NBTTagList nbttaglist2 = nbttagcompound.getTagList("Input");
        input = new ItemStack[1];
        for(int i = 0; i < nbttaglist2.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist2.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < input.length)
            {
                input[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        NBTTagList nbttaglist3 = nbttagcompound.getTagList("Output");
        outputItemStacks = new ItemStack[20];
        for(int i = 0; i < nbttaglist3.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist3.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < outputItemStacks.length)
            {
                outputItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void handlePacketData(int orecount, int coalcount, float outcount, int dam)
    {
        this.oreCount = orecount;
        this.charcoalCount = coalcount;
        this.outCount = outcount;
        if(dam == -1)
            this.OreType = "";
        else
            this.OreType = ItemTerraSmallOre.getItemNameDamage(dam);
    }

    public void updateGui()
    {
        mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, this.oreCount, this.charcoalCount, this.outCount, this.oreDamage));
    }
}
