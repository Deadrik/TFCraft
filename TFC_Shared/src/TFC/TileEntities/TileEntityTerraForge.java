package TFC.TileEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.Blocks.BlockTerraForge;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.PacketHandler;
import TFC.Core.TFCHeat;
import TFC.Items.ItemTerraMeltedMetal;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TFCItems;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_TFC_Core;

public class TileEntityTerraForge extends TileEntityFireEntity implements IInventory
{
    public float fuelTimeLeft;
    public float fuelBurnTemp;
    public int fuelBuildup;
    public float fireTemperature;
    public int timeleft;
    public float AddedAir;
    public boolean isValid;

    public ItemStack fireItemStacks[];
    public float ambientTemp;
    Boolean Item1Melted = false;
    public float inputItemTemps[];

    private int prevStackSize;
    private int numAirBlocks;
    private ItemStack prevWorkItemStack;

    private int externalFireCheckTimer;
    public Boolean canCreateFire;
    private int externalWoodCount;
    private int charcoalCounter;
    
    private final int MaxFireTemp = 2500;

    public TileEntityTerraForge()
    {
        super();
        fuelTimeLeft = 900;
        fuelBurnTemp =  1400;
        fuelBuildup = 0;
        fireTemperature = 400;
        AddedAir = 0F;
        isValid = false;

        fireItemStacks = new ItemStack[14];
        inputItemTemps = new float[5];
        ambientTemp = -1000;
        numAirBlocks = 0;

        externalFireCheckTimer = 0;
        externalWoodCount = 0;
        charcoalCounter = 0;

    }

    public void careForInventorySlot(int i, float startTemp)
    {
        NBTTagCompound inputCompound;
        float mod = 1;
        if(i == 0)
        {
            if(fireItemStacks[5] != null) {
                mod = 0.8F;
            } else
            {
                mod = 0.6F;
                if(fireItemStacks[6] == null) {
                    mod = 0.3F;
                }
            }
        }
        else if(i == 1)
        {
            if(fireItemStacks[6] != null) {
                mod = 0.9F;
            } else
            {
                mod = 0.7F;
                if(fireItemStacks[7] == null) {
                    mod = 0.4F;
                }
            }
        }
        else if(i == 2)
        {
            if(fireItemStacks[7] != null) {
                mod = 1.0F;
            } else {
                mod = 0.5F;
            }
        }
        else if(i == 3)
        {
            if(fireItemStacks[8] != null) {
                mod = 0.9F;
            } else
            {
                mod = 0.7F;
                if(fireItemStacks[7] == null) {
                    mod = 0.4F;
                }
            }
        }
        else if(i == 4)
        {
            if(fireItemStacks[9] != null) {
                mod = 0.8F;
            } else
            {
                mod = 0.6F;
                if(fireItemStacks[8] == null) {
                    mod = 0.3F;
                }
            }
        }

        HeatManager manager = HeatManager.getInstance();

        if(fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
        {
            HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);

            inputCompound = fireItemStacks[i].getTagCompound();

            if(inputCompound.hasKey("temperature")) {
                inputItemTemps[i] = inputCompound.getFloat("temperature");
            } else {
                inputItemTemps[i] = ambientTemp;
            }

            if(fireTemperature*mod > inputItemTemps[i])
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempIncrease(fireItemStacks[i], fireTemperature*mod, MaxFireTemp);
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
                Collection C = fireItemStacks[i].getTagCompound().getTags();
                Iterator itr = C.iterator();
                while(itr.hasNext())
                {
                    Object tag = itr.next();
                    if(TFCHeat.canRemoveTag(tag, "temperature", NBTTagFloat.class))
                    {
                        itr.remove();
                        break;
                    }
                }
                //fireItemStacks[i].stackTagCompound = null;
            }

            if(index.boilTemp <= inputItemTemps[i])
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

    private Boolean CheckValidity() 
    {
        if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))//Check if the forge can directly see the sky
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord+1, yCoord+1, zCoord) && worldObj.canBlockSeeTheSky(xCoord+1, yCoord+1, zCoord))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord-1, yCoord+1, zCoord) && worldObj.canBlockSeeTheSky(xCoord-1, yCoord+1, zCoord))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord+1) && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord+1))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord-1) && worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord-1))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord+1, yCoord+1, zCoord) && !worldObj.isBlockOpaqueCube(xCoord+2, yCoord+1, zCoord) && 
                worldObj.canBlockSeeTheSky(xCoord+2, yCoord+1, zCoord))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord-1, yCoord+1, zCoord) && !worldObj.isBlockOpaqueCube(xCoord-2, yCoord+1, zCoord) && 
                worldObj.canBlockSeeTheSky(xCoord-2, yCoord+1, zCoord))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord+1) && !worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord+2) && 
                worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord+2))
        {
            return true;
        }
        else if(!worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord-1) && !worldObj.isBlockOpaqueCube(xCoord, yCoord+1, zCoord-2) && 
                worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord-2))
        {
            return true;
        }
        else
        {
            return false;
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
        DestItem.setItemDamage(100-(D1 + D2));
    }

    public void CookItemsNew(int i)
    {
        HeatManager manager = HeatManager.getInstance();
        Random R = new Random();
        if(fireItemStacks[i] != null)
        {
            HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
            if(index != null && inputItemTemps[i] > index.meltTemp)
            {
                int dam = fireItemStacks[i].getItemDamage();
                ItemStack is = fireItemStacks[i].copy();
                //Morph the input
                if(!(fireItemStacks[i].getItem() instanceof ItemTerraMeltedMetal))
                    fireItemStacks[i] = index.getMorph();

                if(fireItemStacks[i] != null)
                {
                    HeatIndex index2 = manager.findMatchingIndex(fireItemStacks[i]);
                    if(index2 != null)
                    {
                        //if the input is a new item, then apply the old temperature to it
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemps[i]);
                        fireItemStacks[i].stackTagCompound = nbt;
                    }
                }
                else
                {
                    ItemStack output = index.getOutput(R);
                    int count = 1;
                    Boolean useCount = false;
                    if(output.getItem() instanceof ItemTerraMeltedMetal)
                    {
                        if(output.stackSize > 1)
                        {
                            count = 0;
                            int c = output.stackSize;
                            for(int iterations = 0;c > 0 && iterations <= 20;iterations++)
                            {
                                if(fireItemStacks[10] != null && fireItemStacks[10].getItem().shiftedIndex == TFCItems.terraCeramicMold.shiftedIndex)
                                {
                                    fireItemStacks[10].stackSize--;
                                    c--;
                                    count++;
                                }
                                else if(fireItemStacks[11] != null && fireItemStacks[11].getItem().shiftedIndex == TFCItems.terraCeramicMold.shiftedIndex)
                                {
                                    fireItemStacks[11].stackSize--;
                                    c--;
                                    count++;
                                }
                                else if(fireItemStacks[12] != null && fireItemStacks[12].getItem().shiftedIndex == TFCItems.terraCeramicMold.shiftedIndex)
                                {
                                    fireItemStacks[12].stackSize--;
                                    c--;
                                    count++;
                                }
                                else if(fireItemStacks[13] != null && fireItemStacks[13].getItem().shiftedIndex == TFCItems.terraCeramicMold.shiftedIndex)
                                {
                                    fireItemStacks[13].stackSize--;
                                    c--;
                                    count++;
                                }
                            }
                        }
                        useCount = true;
                    }
                    fireItemStacks[i] = index.getOutput(R);
                    if(useCount)
                        fireItemStacks[i].stackSize = count;
                    
                    HeatIndex index2 = manager.findMatchingIndex(fireItemStacks[i]);
                    if(index2 != null)
                    {
                        //if the input is a new item, then apply the old temperature to it
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemps[i]);
                        fireItemStacks[i].stackTagCompound = nbt;
                    }
                    if(fireItemStacks[i] != null && fireItemStacks[i].getItem() instanceof ItemTerraMeltedMetal && is.getItem() instanceof ItemTerraMeltedMetal)
                    {
                        fireItemStacks[i].setItemDamage(dam);
                    }
                    if(inputItemTemps[i] >= index.boilTemp)
                    {
                        if(fireItemStacks[i] != null && fireItemStacks[i].getItem() instanceof ItemTerraMeltedMetal)
                        {
                            fireItemStacks[i] = new ItemStack(TFCItems.terraCeramicMold, 1);
                            fireItemStacks[i].stackTagCompound = null;
                        }
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

    @Override
    public int getInventoryStackLimit()
    {
        // TODO Auto-generated method stub
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "Forge";
    }

    public int getMoldIndex()
    {
        if(fireItemStacks[10] != null && fireItemStacks[10].itemID == TFCItems.terraCeramicMold.shiftedIndex)
        {
            return 10;
        }
        if(fireItemStacks[11] != null && fireItemStacks[11].itemID == TFCItems.terraCeramicMold.shiftedIndex)
        {
            return 11;
        }
        if(fireItemStacks[12] != null && fireItemStacks[12].itemID == TFCItems.terraCeramicMold.shiftedIndex)
        {
            return 12;
        }
        if(fireItemStacks[13] != null && fireItemStacks[13].itemID == TFCItems.terraCeramicMold.shiftedIndex)
        {
            return 13;
        }
        return -1;
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

    public int getTemperatureScaled(int s)
    {
        return (int)(fireTemperature * s) / MaxFireTemp;
    }

    public void HandleFuelStack()
    {
        Random random = new Random();
        if(fireItemStacks[7] == null)
        {
            if(random.nextBoolean() && fireItemStacks[6] != null)
            {
                fireItemStacks[7] = fireItemStacks[6];
                fireItemStacks[6] = null;
            }
            else
            {
                fireItemStacks[7] = fireItemStacks[8];
                fireItemStacks[8] = null;
            }
        }
        if(fireItemStacks[6] == null)
        {
            if(fireItemStacks[5] != null)
            {
                fireItemStacks[6] = fireItemStacks[5];
                fireItemStacks[5] = null;
            }
        }
        if(fireItemStacks[8] == null)
        {
            if(fireItemStacks[9] != null)
            {
                fireItemStacks[8] = fireItemStacks[9];
                fireItemStacks[9] = null;
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
        isValid = nbttagcompound.getBoolean("isValid");

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

    public void receiveAirFromBellows()
    {
        if(airFromBellowsTime < 360) {
            airFromBellowsTime += 120;
        }
        if(airFromBellowsTime > 360) {
            airFromBellowsTime = 360;
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
        if(!worldObj.isRemote)
        {
            //Here we take care of the items that we are cooking in the fire
            careForInventorySlot(0,40);
            careForInventorySlot(1,40);
            careForInventorySlot(2,40);
            careForInventorySlot(3,40);
            careForInventorySlot(4,40);

            ItemStack[] FuelStack = new ItemStack[9];
            FuelStack[0] = fireItemStacks[5];
            FuelStack[1] = fireItemStacks[6];
            FuelStack[2] = fireItemStacks[7];
            FuelStack[3] = fireItemStacks[8];
            FuelStack[4] = fireItemStacks[9];
            FuelStack[5] = fireItemStacks[10];
            FuelStack[6] = fireItemStacks[11];
            FuelStack[7] = fireItemStacks[12];
            FuelStack[8] = fireItemStacks[13];

            TFCHeat.HandleContainerHeat(this.worldObj, FuelStack, xCoord,yCoord,zCoord);

            //Now we cook the input item
            CookItemsNew(0);
            CookItemsNew(1);
            CookItemsNew(2);
            CookItemsNew(3);
            CookItemsNew(4);

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
            if(fireTemperature > MaxFireTemp) {
                fireTemperature = MaxFireTemp;
            } else if(fireTemperature < ambientTemp) {
                fireTemperature = ambientTemp;
            }
            //Play the fire sound
            Random R = new Random();
            if(R.nextInt(10) == 0 && fireTemperature > 210)
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "fire.fire", 0.4F + (R.nextFloat()/2), 0.7F + R.nextFloat());

            if(fireTemperature < 210 && fireTemperature != ambientTemp && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1);
            }
            else if(fireTemperature < 100 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0);
                if(worldObj.getBlockId(xCoord, yCoord, zCoord) != mod_TFC_Core.terraForge.blockID) {
                    BlockTerraForge.updateFurnaceBlockState(false, worldObj, xCoord, yCoord, zCoord);
                }
            }

            //If the fire is still burning and has fuel
            if(fuelTimeLeft > 0 && fireTemperature >= 210 && (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
            {
                if(worldObj.getBlockId(xCoord, yCoord, zCoord) != mod_TFC_Core.terraForgeOn.blockID) {
                    BlockTerraForge.updateFurnaceBlockState(true, worldObj, xCoord, yCoord, zCoord);
                }

                float desiredTemp = 0;
                if(true)
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

                if(fireTemperature < desiredTemp)
                {
                    fireTemperature+=1.35F;
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
            else if(fuelTimeLeft <= 0 && fireTemperature >= 210 && fireItemStacks[7] != null && 
                    (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
            {
                //here we set the temp and burn time based on the fuel in the bottom slot.
                if(fireItemStacks[7] != null)
                {
                    if(fireItemStacks[7].itemID == Item.coal.shiftedIndex && fireItemStacks[7].getItemDamage() == 0)
                    {
                        fuelTimeLeft = 1100;
                        fuelBurnTemp = 1400;
                    }
                    if(fireItemStacks[7].itemID == Item.coal.shiftedIndex && fireItemStacks[7].getItemDamage() == 1)
                    {
                        fuelTimeLeft = 900;
                        fuelBurnTemp = 1350;
                    }
                    fireItemStacks[7] = null;
                }
            }
            //If there is no more fuel and the fire is still hot, we start to cool it off.
            if(fuelTimeLeft <= 0 && fireTemperature > ambientTemp || worldObj.isRaining())
            {
                if(airFromBellows == 0) {
                    fireTemperature-=0.125F;
                } else {
                    fireTemperature-=0.1F;
                }
            }

            //Here we handle the bellows
            if(airFromBellowsTime > 0)
            {
                airFromBellowsTime--;
                airFromBellows = (float)airFromBellowsTime/120*10;
            }

            //Here we make sure that the forge is valid
            isValid = CheckValidity();

            //do a last minute check to verify stack size
            for(int c = 0; c < 5; c++)
            {
                if(fireItemStacks[c] != null)
                {
                    if(fireItemStacks[c].stackSize <= 0) {
                        fireItemStacks[c].stackSize = 1;
                    }
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
        nbttagcompound.setBoolean("isValid", isValid);


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

    public void handlePacketData(float temp) 
    {
        TileEntityTerraForge pile = this;
        fireTemperature = temp;

    }

}
