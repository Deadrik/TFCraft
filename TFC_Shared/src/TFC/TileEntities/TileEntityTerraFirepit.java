package TFC.TileEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.Blocks.BlockFirepit;
import TFC.Core.EnumWoodMaterial;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.PacketHandler;
import TFC.Core.TFCHeat;
import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;
import TFC.Core.TFC_Game;
import TFC.Core.Vector3f;
import TFC.Items.ItemTerraMeltedMetal;
import TFC.WorldGen.TFCBiome;
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
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.mod_TFC;

public class TileEntityTerraFirepit extends TileEntityFireEntity implements IInventory
{
    

    public ItemStack fireItemStacks[];
    public float inputItemTemp;

    private int prevStackSize;
    private ItemStack prevWorkItemStack;

    private int externalFireCheckTimer;
    public Boolean canCreateFire;
    private int externalWoodCount;
    public int charcoalCounter;

    public final int FIREBURNTIME = (int) ((TFCSeasons.hourLength*18)/100);//default 240

    public TileEntityTerraFirepit()
    {
        fuelTimeLeft = 375;
        fuelBurnTemp =  613;
        fireTemperature = 350;
        AddedAir = 0F;
        
        MaxFireTemp = 2000;

        fireItemStacks = new ItemStack[11];
        inputItemTemp = 0;
        ambientTemp = -1000;

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
        HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);

        if( index!= null && fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
        {
            float itemTemp = 0F;
            inputCompound = fireItemStacks[i].getTagCompound();
            itemTemp = inputCompound.getFloat("temperature");

            if(fireTemperature > itemTemp)
            {
                String name = fireItemStacks[i].getItem().getItemNameIS(fireItemStacks[i]);
                float increase = TFCHeat.getTempIncrease(fireItemStacks[i],fireTemperature,MaxFireTemp);
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
            if(index != null && fireTemperature > 210)
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
                ItemStack output = index.getOutput(R);
                int damage = output.getItemDamage();
                if(output.getItem().shiftedIndex == fireItemStacks[1].getItem().shiftedIndex)
                    damage = fireItemStacks[1].getItemDamage();
                ItemStack mold = null;


                if(fireItemStacks[1].getItem() instanceof ItemTerraMeltedMetal)
                {
                    if(fireItemStacks[7] == null && fireItemStacks[8] == null)
                    {
                        fireItemStacks[7] = fireItemStacks[1].copy();
                        fireItemStacks[1] = null;
                        return;
                    }
                    else if(fireItemStacks[7] != null && fireItemStacks[7].getItem() != TFCItems.terraCeramicMold && 
                            (fireItemStacks[7].getItem() != fireItemStacks[1].getItem() || fireItemStacks[7].getItemDamage() == 0))
                    {
                        if(fireItemStacks[8] == null)
                        {
                            fireItemStacks[8] = fireItemStacks[1].copy();
                            fireItemStacks[1] = null;
                            return;
                        }
                    }

                    mold = new ItemStack(TFCItems.terraCeramicMold,1);
                    mold.stackSize = 1;
                    mold.setItemDamage(0);
                }
                //Morph the input
                fireItemStacks[1] = index.getMorph();

                if(fireItemStacks[1] != null && manager.findMatchingIndex(fireItemStacks[1]) != null)
                {
                    //if the input is a new item, then apply the old temperature to it
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setFloat("temperature", inputItemTemp);
                    fireItemStacks[1].stackTagCompound = nbt;
                }


                //Check if we should combine the output with a pre-existing output
                if(output != null && output.getItem() instanceof ItemTerraMeltedMetal)
                {
                    int leftover = 0;
                    boolean addLeftover = false;
                    if(fireItemStacks[7] != null && output != null && output.getItem().shiftedIndex == fireItemStacks[7].getItem().shiftedIndex && fireItemStacks[7].getItemDamage() > 0)
                    {
                        int amt1 = 100-damage;//the percentage of the output
                        int amt2 = 100-fireItemStacks[7].getItemDamage();//the percentage currently in the out slot
                        int amt3 = amt1 + amt2;//combined amount
                        leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
                        if(leftover > 0) addLeftover = true;
                        int amt4 = 100-amt3;//convert the percent back to mc damage
                        if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                        fireItemStacks[7] = output.copy();
                        fireItemStacks[7].setItemDamage(amt4);

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[7].stackTagCompound = nbt;

                        if(fireItemStacks[1] == null && mold != null)
                        {
                            fireItemStacks[1] = mold;
                        }
                    }
                    else if(fireItemStacks[8] != null && output != null && output.getItem().shiftedIndex == fireItemStacks[8].getItem().shiftedIndex && fireItemStacks[8].getItemDamage() > 0)
                    {
                        int amt1 = 100-damage;//the percentage of the output
                        int amt2 = 100-fireItemStacks[8].getItemDamage();//the percentage currently in the out slot
                        int amt3 = amt1 + amt2;//combined amount
                        int amt4 = 100-amt3;//convert the percent back to mc damage
                        if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                        fireItemStacks[8] = output.copy();
                        fireItemStacks[8].setItemDamage(amt4);

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[8].stackTagCompound = nbt;

                        if(fireItemStacks[1] == null && mold != null)
                        {
                            fireItemStacks[1] = mold;
                        }
                    }
                    else if(output != null && fireItemStacks[7] != null && fireItemStacks[7].getItem() == TFCItems.terraCeramicMold)
                    {
                        fireItemStacks[7] = output.copy();
                        fireItemStacks[7].setItemDamage(damage);

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[7].stackTagCompound = nbt;
                    }
                    else if(output != null && fireItemStacks[8] != null && fireItemStacks[8].getItem() == TFCItems.terraCeramicMold)
                    {
                        fireItemStacks[8] = output.copy();
                        fireItemStacks[8].setItemDamage(damage);

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
                            fireItemStacks[8] = output.copy();
                            fireItemStacks[8].setItemDamage(amt4);

                            NBTTagCompound nbt = new NBTTagCompound();
                            nbt.setFloat("temperature", inputItemTemp);
                            fireItemStacks[8].stackTagCompound = nbt;
                        }
                        else if(fireItemStacks[8] != null && fireItemStacks[8].getItem() == TFCItems.terraCeramicMold)
                        {
                            fireItemStacks[8] = output.copy();
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
                        fireItemStacks[7] = output.copy(); 
                    }
                    else if(fireItemStacks[8] == null)
                    {
                        fireItemStacks[8] = output.copy(); 
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

    private List vecArray = new ArrayList<Vector3f>();
    public void externalFireCheck()
    {
        Random R = new Random();
        if(externalFireCheckTimer == 0)
        {
            int oldWoodCount = externalWoodCount;
            externalWoodCount = 0;
            
            vecArray = new ArrayList<Vector3f>();
            ProcessPile(worldObj,xCoord,yCoord,zCoord,false);

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
            } else if(charcoalCounter == 0)
            {
                charcoalCounter = (int) TFCSeasons.getTotalTicks();
            }

            if(charcoalCounter > 0 && charcoalCounter + (FIREBURNTIME*100) < TFCSeasons.getTotalTicks() )
            {
                charcoalCounter = 0;
                float percent = 25+R.nextInt(25);
                externalWoodCount = (int) (externalWoodCount * (percent/100));
                ProcessPile(worldObj,xCoord,yCoord,zCoord,true);
                while(externalWoodCount > 0)
                {
                    if(externalWoodCount > Item.coal.getItemStackLimit())
                    {
                        ItemStack is = new ItemStack(Item.coal,Item.coal.getItemStackLimit(),1);
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord,zCoord,is));
                        //addByproduct(new ItemStack(Item.coal,Item.coal.getItemStackLimit(),1));
                        externalWoodCount -= Item.coal.getItemStackLimit();
                    }
                    else
                    {
                        //addByproduct(new ItemStack(Item.coal,externalWoodCount,1));
                        ItemStack is = new ItemStack(Item.coal,externalWoodCount,1);
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord+1,zCoord,is));
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
                worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    private void ProcessPile(World world, int i, int j, int k, boolean empty)
    {
        int x = i;
        int y = 0;
        int z = k;
        boolean checkArray[][][] = new boolean[25][13][25];
        boolean reachedTop = false;

        while(!reachedTop && j+y >= 0)
        {
            if(world.getBlockId(x, j+y+1, z) != mod_TFC.LogPile.blockID)
            {
                reachedTop = true;
            }
            scanLogs(world,i,j+y,k,checkArray,12,y,12, empty);
            y++;
        }
    }

    private boolean checkOut(World world, int i, int j, int k, boolean empty)
    {
        if(world.getBlockId(i, j, k) == 0)
            vecArray.add(new Vector3f(i, j, k));        

        else if(world.getBlockId(i, j, k) == mod_TFC.LogPile.blockID)
        {
            if(!empty)
            {
                if(world.getBlockId(i+1, j, k) == 0)
                    vecArray.add(new Vector3f(i+1, j, k));
                else if(world.getBlockId(i-1, j, k) == 0)
                    vecArray.add(new Vector3f(i-1, j, k));
                else if(world.getBlockId(i, j, k+1) == 0)
                    vecArray.add(new Vector3f(i, j, k+1));
                else if(world.getBlockId(i, j, k-1) == 0)
                    vecArray.add(new Vector3f(i, j, k-1));
                else if(world.getBlockId(i, j+1, k) == 0)
                    vecArray.add(new Vector3f(i, j+1, k));
                else if(world.getBlockId(i, j-1, k) == 0)
                    vecArray.add(new Vector3f(i, j-1, k));
            }

            TileEntityTerraLogPile te = (TileEntityTerraLogPile)worldObj.getBlockTileEntity(i, j, k);
            if(te != null)
            {
                if(te.storage[0] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[0].stackSize;
                    else
                        te.storage[0] = null;
                }
                if(te.storage[1] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[1].stackSize;
                    else
                        te.storage[1] = null;
                }
                if(te.storage[2] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[2].stackSize;
                    else
                        te.storage[2] = null;
                }
                if(te.storage[3] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[3].stackSize;
                    else
                        te.storage[3] = null;
                }
            }
            if(empty)
            {
                world.setBlock(i, j, k, 0);
                world.markBlockNeedsUpdate(i, j, k);
            }
            return true;
        }
        return false;
    }

    private void scanLogs(World world, int i, int j, int k, boolean[][][] checkArray,int x, int y, int z, boolean empty)
    {
        if(y >= 0)
        {
            checkArray[x][y][z] = true;
            int offsetX = 0;int offsetY = 0;int offsetZ = 0;

            for (offsetY = 0; offsetY <= 1; offsetY++)
            {
                for (offsetX = -1; offsetX <= 1; offsetX++)
                {
                    for (offsetZ = -1; offsetZ <= 1; offsetZ++)
                    {
                        if(x+offsetX < 25 && x+offsetX >= 0 && z+offsetZ < 25 && z+offsetZ >= 0 && y+offsetY < 13 && y+offsetY >= 0)
                        {
                            if(!checkArray[x+offsetX][y+offsetY][z+offsetZ] && checkOut(world, i+offsetX, j+offsetY, k+offsetZ, empty))
                            {
                                scanLogs(world,i+offsetX, j+offsetY, k+offsetZ, checkArray,x+offsetX,y+offsetY,z+offsetZ, empty);
                            }
                        }
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

    public void updateEntity()
    {
        int Surrounded = getSurroundedByWood(xCoord,yCoord,zCoord);
        if(fireTemperature > 210 && worldObj.getBlockId(xCoord, yCoord+1, zCoord) == mod_TFC.LogPile.blockID)
        {
            externalFireCheckTimer--;
            if(externalFireCheckTimer <= 0)
            {
                if(!worldObj.isRemote)
                {
                    externalFireCheck();
                }
                externalFireCheckTimer = 100;
            }
        }
        else
        {
            charcoalCounter = 0;
        }

        Random R = new Random();            

        if(!worldObj.isRemote)
        {
            HeatManager manager = HeatManager.getInstance();
            //Here we take care of the item that we are cooking in the fire
            NBTTagCompound inputCompound;
            HeatIndex index;
            if(fireItemStacks[1]!= null && fireItemStacks[1].hasTagCompound())
            {
                index = manager.findMatchingIndex(fireItemStacks[1]);
                inputCompound = fireItemStacks[1].getTagCompound();
                inputItemTemp = inputCompound.getFloat("temperature");

                if(index != null && fireTemperature > inputItemTemp)
                {
                    float increase = TFCHeat.getTempIncrease(fireItemStacks[1], fireTemperature, MaxFireTemp);
                    inputItemTemp += increase;
                }
                if(fireTemperature < inputItemTemp)
                {
                    float increase = TFCHeat.getTempDecrease(fireItemStacks[1]);
                    inputItemTemp -= increase;
                }

                inputCompound.setFloat("temperature", inputItemTemp);
                fireItemStacks[1].setTagCompound(inputCompound);
                if(index != null && index.boilTemp <= inputItemTemp)
                {
                    fireItemStacks[1] = null;
                }
            }
            else if(fireItemStacks[1] != null && !fireItemStacks[1].hasTagCompound())
            {
                index = manager.findMatchingIndex(fireItemStacks[1]);
                if(fireTemperature > 210 && index != null)
                {
                    inputCompound = new NBTTagCompound();
                    inputCompound.setFloat("temperature",  ((TFCBiome)worldObj.getBiomeGenForCoords(xCoord, zCoord)).getHeightAdjustedTemperature(yCoord)+10);
                    fireItemStacks[1].setTagCompound(inputCompound);
                    inputItemTemp = ((TFCBiome)worldObj.getBiomeGenForCoords(xCoord, zCoord)).getHeightAdjustedTemperature(yCoord);
                }
            }
            else if(fireItemStacks[1] == null)
            {
                inputItemTemp =  ((TFCBiome)worldObj.getBiomeGenForCoords(xCoord, zCoord)).getHeightAdjustedTemperature(yCoord);
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
            CookItemNew();

            //push the input fuel down the stack
            HandleFuelStack();
            if(ambientTemp == -1000)	
            {
                TFCBiome biome = (TFCBiome) worldObj.getBiomeGenForCoords(xCoord, zCoord);
                ambientTemp = biome.getHeightAdjustedTemperature(yCoord);
            }
            //here we set the various temperatures to range
            this.keepTempToRange();

            if(fireTemperature < 210 && fireTemperature != ambientTemp && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1);
            }
            else if(fireTemperature < 100 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0);
                BlockFirepit.updateFurnaceBlockState(false, worldObj, xCoord, yCoord, zCoord);
            }

            //If the fire is still burning and has fuel
            if(fuelTimeLeft > 0 && fireTemperature >= 210 && Surrounded != 5)
            {

                if(worldObj.getBlockId(xCoord, yCoord, zCoord) != mod_TFC.terraFirepitOn.blockID) {
                    BlockFirepit.updateFurnaceBlockState(true, worldObj, xCoord, yCoord, zCoord);
                }

                float desiredTemp = 0;
                if(Surrounded != 5)
                {

                    desiredTemp = handleTemp();
                }
                else
                {
                    desiredTemp = 300;
                }

                handleTempFlux(desiredTemp);
            }
            else if(fuelTimeLeft <= 0 && fireTemperature >= 210 && fireItemStacks[5] != null &&
                    (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
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
            
            if(fuelTimeLeft <= 0)
            {
                TFCHeat.HandleContainerHeat(this.worldObj, fireItemStacks, xCoord,yCoord,zCoord);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
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
