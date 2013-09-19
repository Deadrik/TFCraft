package TFC.TileEntities;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.Enums.EnumWoodMaterial;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.Vector3f;
import TFC.Items.ItemMeltedMetal;
import TFC.WorldGen.TFCBiome;

public class TileEntityFirepit extends TileEntityFireEntity implements IInventory
{
    public ItemStack fireItemStacks[];
    public float inputItemTemp;

    private int externalFireCheckTimer;
    private int externalWoodCount;
    private int oldWoodCount;
    private boolean logPileChecked;
    public int charcoalCounter;

    public final int FIREBURNTIME = (int) ((TFC_Time.hourLength*18)/100);//default 240

    public TileEntityFirepit()
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
        oldWoodCount = 0;
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
        HeatRegistry manager = HeatRegistry.getInstance();
        HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);

        if( index!= null && fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
        {
            float itemTemp = 0F;
            inputCompound = fireItemStacks[i].getTagCompound();
            itemTemp = inputCompound.getFloat("temperature");

            if(fireTemperature > itemTemp)
            {
                float increase = TFC_ItemHeat.getTempIncrease(fireItemStacks[i],fireTemperature,MaxFireTemp);
                itemTemp += increase;
            }
            else if(fireTemperature < itemTemp)
            {
                float increase = TFC_ItemHeat.getTempDecrease(fireItemStacks[i]);
                itemTemp -= increase;
            }
            inputCompound.setFloat("temperature", itemTemp);
            fireItemStacks[i].setTagCompound(inputCompound);

            if(itemTemp <= ambientTemp)
            {
                fireItemStacks[i].stackTagCompound = null;
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
        HeatRegistry manager = HeatRegistry.getInstance();
        Random R = new Random();
        if(fireItemStacks[1] != null)
        {
            HeatIndex index = manager.findMatchingIndex(fireItemStacks[1]);
            if(index != null && inputItemTemp > index.meltTemp)
            {
                ItemStack output = index.getOutput(fireItemStacks[1], R);
                int damage = output.getItemDamage();
                if(output.getItem().itemID == fireItemStacks[1].getItem().itemID)
                    damage = fireItemStacks[1].getItemDamage();
                ItemStack mold = null;


                //If the input is unshaped metal
                if(fireItemStacks[1].getItem() instanceof ItemMeltedMetal)
                {
                	//if both output slots are empty then just lower the input item into the first output slot
                    if(fireItemStacks[7] == null && fireItemStacks[8] == null)
                    {
                        fireItemStacks[7] = fireItemStacks[1].copy();
                        fireItemStacks[1] = null;
                        return;
                    }
                    //Otherwise if the first output has an item that doesnt match the input item then put the item in the second output slot
                    else if(fireItemStacks[7] != null && fireItemStacks[7].getItem() != TFCItems.CeramicMold && 
                            (fireItemStacks[7].getItem() != fireItemStacks[1].getItem() || fireItemStacks[7].getItemDamage() == 0))
                    {
                        if(fireItemStacks[8] == null)
                        {
                            fireItemStacks[8] = fireItemStacks[1].copy();
                            fireItemStacks[1] = null;
                            return;
                        }
                    }

                    mold = new ItemStack(TFCItems.CeramicMold,1);
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
                if(output != null && output.getItem() instanceof ItemMeltedMetal)
                {
                    int leftover = 0;
                    boolean addLeftover = false;
                    int fromSide = 0;
                    if(fireItemStacks[7] != null && output != null && output.getItem().itemID == fireItemStacks[7].getItem().itemID && fireItemStacks[7].getItemDamage() > 0)
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
                    else if(fireItemStacks[8] != null && output != null && output.getItem().itemID == fireItemStacks[8].getItem().itemID && fireItemStacks[8].getItemDamage() > 0)
                    {
                        int amt1 = 100-damage;//the percentage of the output
                        int amt2 = 100-fireItemStacks[8].getItemDamage();//the percentage currently in the out slot
                        int amt3 = amt1 + amt2;//combined amount
                        leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
                        if(leftover > 0) addLeftover = true;
                        fromSide = 1;
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
                    else if(output != null && fireItemStacks[7] != null && fireItemStacks[7].getItem() == TFCItems.CeramicMold)
                    {
                        fireItemStacks[7] = output.copy();
                        fireItemStacks[7].setItemDamage(damage);

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[7].stackTagCompound = nbt;
                    }
                    else if(output != null && fireItemStacks[8] != null && fireItemStacks[8].getItem() == TFCItems.CeramicMold)
                    {
                        fireItemStacks[8] = output.copy();
                        fireItemStacks[8].setItemDamage(damage);

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setFloat("temperature", inputItemTemp);
                        fireItemStacks[8].stackTagCompound = nbt;
                    }

                    if(addLeftover)
                    {
                    	int dest = fromSide == 1 ? 7 : 8;
                        if(fireItemStacks[dest] != null && output.getItem().itemID == fireItemStacks[dest].getItem().itemID && fireItemStacks[dest].getItemDamage() > 0)
                        {
                            int amt1 = 100-leftover;//the percentage of the output
                            int amt2 = 100-fireItemStacks[dest].getItemDamage();//the percentage currently in the out slot
                            int amt3 = amt1 + amt2;//combined amount
                            int amt4 = 100-amt3;//convert the percent back to mc damage
                            if(amt4 < 0) amt4 = 0;//stop the infinite glitch
                            fireItemStacks[dest] = output.copy();
                            fireItemStacks[dest].setItemDamage(amt4);

                            NBTTagCompound nbt = new NBTTagCompound();
                            nbt.setFloat("temperature", inputItemTemp);
                            fireItemStacks[dest].stackTagCompound = nbt;
                        }
                        else if(fireItemStacks[dest] != null && fireItemStacks[dest].getItem() == TFCItems.CeramicMold)
                        {
                            fireItemStacks[dest] = output.copy();
                            fireItemStacks[dest].setItemDamage(100-leftover);
                            NBTTagCompound nbt = new NBTTagCompound();
                            nbt.setFloat("temperature", inputItemTemp);
                            fireItemStacks[dest].stackTagCompound = nbt;
                        }
                    }
                }
                else
                {
                    if(fireItemStacks[7] != null && 
                            fireItemStacks[7].getItem().itemID == output.getItem().itemID && 
                            fireItemStacks[7].stackSize + output.stackSize <= fireItemStacks[7].getMaxStackSize())
                    {
                        fireItemStacks[7].stackSize += output.stackSize; 
                    }
                    else if(fireItemStacks[8] != null && 
                            fireItemStacks[8].getItem().itemID == output.getItem().itemID && 
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
                    else if((fireItemStacks[7].stackSize == fireItemStacks[7].getMaxStackSize() && fireItemStacks[8].stackSize == fireItemStacks[8].getMaxStackSize())
                    		|| (fireItemStacks[7].getItem().itemID != output.getItem().itemID && fireItemStacks[8].getItem().itemID != output.getItem().itemID)
                    		|| (fireItemStacks[7].stackSize == fireItemStacks[7].getMaxStackSize() && fireItemStacks[8].getItem().itemID != output.getItem().itemID)
                    		|| (fireItemStacks[7].getItem().itemID != output.getItem().itemID && fireItemStacks[8].stackSize == fireItemStacks[8].getMaxStackSize()))
                    {
                    	fireItemStacks[1] = output.copy();
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
        float f1 = rand.nextFloat() * 0.8F + 0.3F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        for (int i = 0; i < getSizeInventory(); i++)
        {
            if(fireItemStacks[i]!= null)
            {
                entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
                        fireItemStacks[i]);
                entityitem.motionX = (float)rand.nextGaussian() * f3;
                entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
                fireItemStacks[i] = null;
            }
        }
    }

    public void externalFireCheck()
    {
        Random R = new Random();
        if(externalFireCheckTimer == 0)
        {
            if(!logPileChecked)
            {
            	logPileChecked = true;
            	oldWoodCount = externalWoodCount;
                externalWoodCount = 0;
                
            	ProcessPile(xCoord,yCoord,zCoord,false);
            	
            	if(oldWoodCount != externalWoodCount) {
                    charcoalCounter = 0;
                }
            }

            //This is where we handle the counter for producing charcoal. Once it reaches 24hours, we add charcoal to the fire and remove the wood.
            if(charcoalCounter == 0)
            {
                charcoalCounter = (int) TFC_Time.getTotalTicks();
            }

            if(charcoalCounter > 0 && charcoalCounter + (FIREBURNTIME*100) < TFC_Time.getTotalTicks() )
            {
            	logPileChecked = false;
                charcoalCounter = 0;
                
                ProcessPile(xCoord,yCoord,zCoord,true);
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            }
        }
    }

    private void ProcessPile(int i, int j, int k, boolean empty)
    {
        int x = i;
        int y = 0;
        int z = k;
        boolean checkArray[][][] = new boolean[25][13][25];
        boolean reachedTop = false;

        while(!reachedTop && j+y >= 0 && y < 13)
        {
            if(worldObj.getBlockId(x, j+y+1, z) != TFCBlocks.LogPile.blockID)
            {
                reachedTop = true;
            }
            checkOut(i, j+y, k, empty);
            scanLogs(i,j+y,k,checkArray,(byte)12,(byte)y,(byte)12, empty);
            y++;
        }
    }

    private boolean checkOut(int i, int j, int k, boolean empty)
    {       
        if(worldObj.getBlockId(i, j, k) == TFCBlocks.LogPile.blockID)
        {
        	TileEntityLogPile te = (TileEntityLogPile)worldObj.getBlockTileEntity(i, j, k);
        	
            int count = 0;
            if(te != null)
            {
            	if(!empty)
            	{
            		Queue<Vector3f> blocksOnFire = new ArrayDeque<Vector3f>();
            		
            		if(worldObj.getBlockId(i+1, j, k) == 0)
                    	blocksOnFire.add(new Vector3f(i+1, j, k));
                    if(worldObj.getBlockId(i-1, j, k) == 0)
                    	blocksOnFire.add(new Vector3f(i-1, j, k));
                    if(worldObj.getBlockId(i, j, k+1) == 0)
                    	blocksOnFire.add(new Vector3f(i, j, k+1));
                    if(worldObj.getBlockId(i, j, k-1) == 0)
                    	blocksOnFire.add(new Vector3f(i, j, k-1));
                    if(worldObj.getBlockId(i, j+1, k) == 0)
                    	blocksOnFire.add(new Vector3f(i, j+1, k));
                    if(worldObj.getBlockId(i, j-1, k) == 0)
                    	blocksOnFire.add(new Vector3f(i, j-1, k));
                    
                    te.blocksToBeSetOnFire = blocksOnFire;
            		te.setCharcoalFirepit(this);
            	}
            	else 
            		te.setCharcoalFirepit(null);
            	
                if(te.storage[0] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[0].stackSize; 
                    else
                    	{count += te.storage[0].stackSize; te.storage[0] = null;}
                }
                if(te.storage[1] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[1].stackSize;
                    else
                    	{count += te.storage[1].stackSize; te.storage[1] = null;}
                }
                if(te.storage[2] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[2].stackSize;
                    else
                    	{count += te.storage[2].stackSize; te.storage[2] = null;}
                }
                if(te.storage[3] != null) 
                {
                    if(!empty)
                        externalWoodCount += te.storage[3].stackSize;
                    else
                    	{count += te.storage[3].stackSize; te.storage[3] = null;}
                }
            }
            if(empty)
            {
            	float percent = 25 + worldObj.rand.nextInt(25);
                count = (int) (count * (percent/100));
                
                worldObj.setBlock(i, j, k, TFCBlocks.Charcoal.blockID, count, 0x2);
                /* Trick to make the block fall or start the combining "chain" with other blocks.
                 * We don't notify the bottom block because it may be air so this block won't fall */
                worldObj.notifyBlockOfNeighborChange(i, j, k, TFCBlocks.Charcoal.blockID); 
            }
            return true;
        }
        return false;
    }

    private void scanLogs(int i, int j, int k, boolean[][][] checkArray, byte x, byte y, byte z, boolean empty)
    {
        if(y >= 0)
        {
            checkArray[x][y][z] = true;
            int offsetX = 0;int offsetZ = 0;

            for (offsetX = -1; offsetX <= 1; offsetX++)
            {
                for (offsetZ = -1; offsetZ <= 1; offsetZ++)
                {
                    if(x+offsetX < 25 && x+offsetX >= 0 && z+offsetZ < 25 && z+offsetZ >= 0 && y < 13 && y >= 0)
                    {
                        if(!checkArray[x+offsetX][y][z+offsetZ] && checkOut(i+offsetX, j, k+offsetZ, empty))
                        {
                            scanLogs(i+offsetX, j, k+offsetZ, checkArray,(byte)(x+offsetX),y,(byte)(z+offsetZ), empty);
                        }
                    }
                }
            }
        }
    }
    
    public void logPileUpdate(int woodChanges)
    {
    	oldWoodCount = externalWoodCount;
    	externalWoodCount += woodChanges;
    	
    	if(oldWoodCount != externalWoodCount)
    		charcoalCounter = 0;
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
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "Firepit";
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

    @Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        fireTemperature = nbttagcompound.getFloat("temperature");
        fuelTimeLeft = nbttagcompound.getFloat("fuelTimeLeft");
        fuelBurnTemp = nbttagcompound.getFloat("fuelBurnTemp");
        charcoalCounter = nbttagcompound.getInteger("charcoalCounter");
        externalWoodCount = nbttagcompound.getInteger("externalWoodCount");
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

    @Override
	public void updateEntity()
    {
        int Surrounded = getSurroundedByWood(xCoord,yCoord,zCoord);
        if(fireTemperature > 210 && worldObj.getBlockId(xCoord, yCoord+1, zCoord) == TFCBlocks.LogPile.blockID)
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
            logPileChecked = false;
        }

        Random R = new Random();            

        if(!worldObj.isRemote)
        {
            HeatRegistry manager = HeatRegistry.getInstance();
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
                    float increase = TFC_ItemHeat.getTempIncrease(fireItemStacks[1], fireTemperature, MaxFireTemp);
                    inputItemTemp += increase;
                }
                if(fireTemperature < inputItemTemp)
                {
                    float increase = TFC_ItemHeat.getTempDecrease(fireItemStacks[1]);
                    inputItemTemp -= increase;
                }

                inputCompound.setFloat("temperature", inputItemTemp);
                fireItemStacks[1].setTagCompound(inputCompound);
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

            TFC_ItemHeat.HandleContainerHeat(this.worldObj, FuelStack, xCoord,yCoord,zCoord);

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
            
            if((fireTemperature < 100) && (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
            else if((fireTemperature >= 100) && (fireTemperature < 210) && (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1))
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }

            //If the fire is still burning and has fuel
            if(fuelTimeLeft > 0 && fireTemperature >= 210 && Surrounded != 5)
            {

                if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2)
                {
                	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
                	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
                    EnumWoodMaterial m = TFC_Core.getWoodMaterial(fireItemStacks[5]);
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
                airFromBellows = airFromBellowsTime/120*10;
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
                TFC_ItemHeat.HandleContainerHeat(this.worldObj, fireItemStacks, xCoord,yCoord,zCoord);
            }
        }

    }

    @Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setFloat("temperature", fireTemperature);
        nbttagcompound.setFloat("fuelTimeLeft", fuelTimeLeft);
        nbttagcompound.setFloat("fuelBurnTemp", fuelBurnTemp);
        nbttagcompound.setInteger("charcoalCounter", charcoalCounter);
        nbttagcompound.setInteger("externalWoodCount", externalWoodCount);
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
	
	public boolean isInactiveCharcoalFirepit()
	{
		return logPileChecked == false && charcoalCounter == 0;
	}
}
