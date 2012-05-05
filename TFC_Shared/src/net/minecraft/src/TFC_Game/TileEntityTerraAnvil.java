package net.minecraft.src.TFC_Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.TFCHeat;
import net.minecraft.src.TFC_Core.General.PacketHandler;

public class TileEntityTerraAnvil extends TileEntity implements IInventory
{
    public ItemStack anvilItemStacks[];

    public int itemCraftingValue;
    public int[] itemCraftingRules;
    public int craftingValue;
    public int craftingRange;
    public int craftingReq;
    public int[] craftingRules;
    private ItemStack result;

    public Boolean rule1;
    public Boolean rule2;
    public Boolean rule3;

    private boolean isDone = false;
    private int workedRecently = 0;

    //this is the fix the server receiving 3 packets whenever the player works an item.
    private final int lagFixDelay = 5;
    
    public TileEntityTerraAnvil()
    {
        anvilItemStacks = new ItemStack[8];
        itemCraftingValue = 0;
        itemCraftingRules = new int[]{-1,-1,-1};
        craftingValue = 0;
        craftingRules = new int[]{-1,-1,-1};

        rule1 = false;
        rule2 = false;
        rule3 = false;
    }

    public void updateEntity()
    {
        //make sure that the world is not remote
        if(!worldObj.isRemote)
        {
            if(workedRecently > 0)
                workedRecently--;
            //Deal with temperatures
            TFCHeat.HandleContainerHeat(this.worldObj, anvilItemStacks, xCoord,yCoord,zCoord);
            //reset everything when the input is empty
            if(anvilItemStacks[1] == null || isDone)
            {
                rule1 = false;
                rule2 = false;
                rule3 = false;
                result = null;
                itemCraftingValue = 0;
                itemCraftingRules = new int[]{-1,-1,-1};
                craftingValue = 0;
                craftingRules = new int[]{-1,-1,-1};
                craftingRange = 0;
                isDone = false;
            }
            //Find the recipe for the item that we are making. If the item is beign crafted for the first time.
            if(anvilItemStacks[1] != null && getCraftingValue() == 0)
            {
                Collection<Object[]> Recipes = TFC_Game.RecipeMap.get(anvilItemStacks[1].getItem().getItemNameIS(anvilItemStacks[1]));
                Iterator itr = Recipes.iterator();
                while(itr.hasNext() && craftingValue == 0)
                {
                    Object[] O = (Object[]) itr.next();

                    //If the blueprint slot is empty and the recipe calls for no blueprint
                    if(anvilItemStacks[5] == null && (Item)O[2] == null)
                    {
                        craftingValue = (Integer)O[0];
                        craftingRules = (int[])O[4];
                        craftingRange = (Integer)O[5];
                        craftingReq = (Integer)O[6];
                        result = (ItemStack)O[3];
                        if(result.stackSize <= 0) {
                            result.stackSize = 1;
                        }
                        break;
                    }
                    //If the blueprint slot is not empty and the recipe calls for a blueprint
                    if(anvilItemStacks[5] != null && anvilItemStacks[5].getItem() == (Item)O[2])
                    {
                        craftingValue = (Integer)O[0];
                        craftingRules = (int[])O[4];
                        craftingRange = (Integer)O[5];
                        craftingReq = (Integer)O[6];
                        result = (ItemStack)O[3];
                        break;
                    }
                }
            }
            else if(anvilItemStacks[1] != null)//If the slot is not empty and crafting ahs already begun
            {
                int cv = getCraftingValue();
                if(anvilItemStacks[1].hasTagCompound() && itemCraftingValue == 0 && itemCraftingRules[0] == -1 && itemCraftingRules[1] == -1 && itemCraftingRules[2] == -1)
                {
                    itemCraftingValue = anvilItemStacks[1].getTagCompound().getInteger("itemCraftingValue");
                    itemCraftingRules[0] = anvilItemStacks[1].getTagCompound().getInteger("itemCraftingRule1");
                    itemCraftingRules[1] = anvilItemStacks[1].getTagCompound().getInteger("itemCraftingRule2");
                    itemCraftingRules[2] = anvilItemStacks[1].getTagCompound().getInteger("itemCraftingRule3");
                }
                if(cv != craftingValue) {
                    cv = -1;
                }
                if(getItemCraftingValue() != itemCraftingValue && getCraftingValue() != 0)//if the item crafting value has changed and the recipe is valid.
                {
                    anvilItemStacks[1].getTagCompound().setInteger("itemCraftingValue", itemCraftingValue);
                    anvilItemStacks[1].getTagCompound().setInteger("itemCraftingRule1", itemCraftingRules[0]);
                    anvilItemStacks[1].getTagCompound().setInteger("itemCraftingRule2", itemCraftingRules[1]);
                    anvilItemStacks[1].getTagCompound().setInteger("itemCraftingRule3", itemCraftingRules[2]);
                }
                if(itemCraftingValue > 100 || itemCraftingValue < -50)
                {
                    Object[] Data = (Object[]) TFCHeat.ItemHeatData.get(anvilItemStacks[1].getItem().getItemNameIS(anvilItemStacks[1]));
                    if(Data != null)
                    {
                        anvilItemStacks[1] = (ItemStack) Data[3];
                    } else {
                        anvilItemStacks[1] = null;
                    }
                }
            }

            if(getCraftingValue() == getItemCraftingValue())//if the item crafting value matches the blueprint crafting value.
            {
                if(result != null && doRulesMatch(itemCraftingRules, craftingRules))
                {
                    if(anvilItemStacks[1].stackTagCompound.hasKey("temperature") && TFCHeat.getMeltingPoint(result) != -1)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setFloat("temperature", anvilItemStacks[1].getTagCompound().getFloat("temperature"));
                        result.stackTagCompound = tag;
                    }

                    anvilItemStacks[1] = result;
                    if(anvilItemStacks[1].stackSize <= 0) {
                        anvilItemStacks[1].stackSize = 1;
                    }
                    isDone = true;
                    rule1 = false;
                    rule2 = false;
                    rule3 = false;
                    result = null;
                    itemCraftingValue = 0;
                    itemCraftingRules[0] = -1;
                    itemCraftingRules[1] = -1;
                    itemCraftingRules[2] = -1;
                    craftingValue = 0;
                    craftingRules = new int[]{-1,-1,-1};
                    craftingRange = 0;
                    craftingReq = 0;
                }
            }
        }
        else
        {
            if(anvilItemStacks[1] != null && getCraftingValue() == 0)
            {
                Collection<Object[]> Recipes = TFC_Game.RecipeMap.get(anvilItemStacks[1].getItem().getItemNameIS(anvilItemStacks[1]));
                Iterator itr = Recipes.iterator();
                while(itr.hasNext() && craftingValue == 0)
                {
                    Object[] O = (Object[]) itr.next();

                    //If the blueprint slot is empty and the recipe calls for no blueprint
                    if(anvilItemStacks[5] == null && (Item)O[2] == null)
                    {
                        craftingValue = (Integer)O[0];
                        craftingRules = (int[])O[4];
                        craftingRange = (Integer)O[5];
                        craftingReq = (Integer)O[6];
                        result = (ItemStack)O[3];
                        if(result.stackSize <= 0) {
                            result.stackSize = 1;
                        }
                        break;
                    }
                    //If the blueprint slot is not empty and the recipe calls for a blueprint
                    if(anvilItemStacks[5] != null && anvilItemStacks[5].getItem() == (Item)O[2])
                    {
                        craftingValue = (Integer)O[0];
                        craftingRules = (int[])O[4];
                        craftingRange = (Integer)O[5];
                        craftingReq = (Integer)O[6];
                        result = (ItemStack)O[3];
                        break;
                    }
                }
            }
        }
    }

    public void updateRules(int rule, int slot)
    {
        if(anvilItemStacks[slot].hasTagCompound())
        {
            NBTTagCompound Tag = anvilItemStacks[slot].getTagCompound();
            int rule1 = -1;
            int rule2 = -1;
            int rule3 = -1;
            if(Tag.hasKey("itemCraftingRule1")) {
                rule1 = Tag.getInteger("itemCraftingRule1");
            }
            if(Tag.hasKey("itemCraftingRule1")) {
                rule2 = Tag.getInteger("itemCraftingRule2");
            }
            if(Tag.hasKey("itemCraftingRule1")) {
                rule3 = Tag.getInteger("itemCraftingRule3");
            }

            itemCraftingRules[2] = rule2;
            itemCraftingRules[1] = rule1;
            itemCraftingRules[0] = rule;
        }
    }

    public void actionHeavyHammer()
    {
        if(!worldObj.isRemote)
        {

            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue -= 9;
                updateRules(0,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 0));
        }
    }

    public void actionLightHammer()
    {
        if(!worldObj.isRemote)
        {

            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue -= 3;
                updateRules(0,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, -1));
        }

    }

    public void actionDraw()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue -= 15;
                updateRules(1,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 1));
        }
    }

    public void actionQuench()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue -= 49;
                updateRules(2,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 2));
        }
    }

    public void actionPunch()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue += 2;
                updateRules(3,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 3));
        }
    }

    public void actionBend()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue += 7;
                updateRules(4,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 4));
        }
    }

    public void actionUpset()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue += 13;
                updateRules(5,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 5));
        }
    }

    public void actionShrink()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && anvilItemStacks[1].getItemDamage() == 0 && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                itemCraftingValue += 16;
                updateRules(6,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 6));
        }
    } 

    public void actionWeld()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWeldable(2) && isTemperatureWeldable(3) && anvilItemStacks[0] != null && 
                    anvilItemStacks[2].getItemDamage() == 0 && anvilItemStacks[3].getItemDamage() == 0 && getAnvilType() >= craftingReq && anvilItemStacks[7] != null && workedRecently == 0)
            {
                String concact = anvilItemStacks[2].getItem().getItemNameIS(anvilItemStacks[2]) + "|" + anvilItemStacks[3].getItem().getItemNameIS(anvilItemStacks[3]);
                String concact2 = anvilItemStacks[3].getItem().getItemNameIS(anvilItemStacks[3]) + "|" + anvilItemStacks[2].getItem().getItemNameIS(anvilItemStacks[2]);
                if(TFC_Game.AnvilWeldRecipes.containsKey(concact))
                {
                    Object[] weldData = (Object[])TFC_Game.AnvilWeldRecipes.get(concact);
                    if(anvilItemStacks[4] == null)
                    {
                        workedRecently = lagFixDelay;
                        anvilItemStacks[4] = (ItemStack)weldData[0];
                        int w1 = getItemCraftingValueNoSet(2);
                        int w2 = getItemCraftingValueNoSet(3);

                        NBTTagCompound Tag = new NBTTagCompound();
                        Tag.setInteger("itemCraftingValue", (w1+w2)/2 -33);
                        anvilItemStacks[4].setTagCompound(Tag);
                        updateRules(7,7);

                        ItemStack item = new ItemStack(anvilItemStacks[7].getItem(),anvilItemStacks[7].stackSize-1);
                        if(item.stackSize == 0) {
                            item = null;
                        }
                        anvilItemStacks[7] = item;

                        anvilItemStacks[2] = null;
                        anvilItemStacks[3] = null;
                    }

                }
            }
        }
        else
        {
            mod_TFC_Core.proxy.sendCustomPacket(PacketHandler.getPacket(this, 7));
        }
    }
    @Override
    public void closeChest() {
        // TODO Auto-generated method stub

    }
    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(anvilItemStacks[i] != null)
        {
            if(anvilItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack = anvilItemStacks[i];
                anvilItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = anvilItemStacks[i].splitStack(j);
            if(anvilItemStacks[i].stackSize == 0)
            {
                anvilItemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }

    }
    public Boolean doRulesMatch(int[] itemRules, int[] recipesRules)
    {
        if(recipesRules[0] != -1)
        {
            if(itemRules[0] == recipesRules[0])
            {
                rule1 = true;
            }
        } else {
            rule1 = true;
        }

        if(recipesRules[1] != -1)
        {
            if(itemRules[1] == recipesRules[1] || itemRules[0] == recipesRules[1])
            {
                rule2 = true;
            }
        } else {
            rule2 = true;
        }

        if(recipesRules[2] != -1)
        {
            if(itemRules[2] == recipesRules[2] || itemRules[1] == recipesRules[2] || itemRules[0] == recipesRules[2])
            {
                rule3 = true;
            }
        } else {
            rule3 = true;
        }

        if(rule1 && rule2 && rule3) {
            return true;
        }

        return false;
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
            if(anvilItemStacks[i]!= null)
            {
                entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, 
                        anvilItemStacks[i]);
                entityitem.motionX = (float)rand.nextGaussian() * f3;
                entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }
    public int getAnvilType()
    {
        return blockMetadata & 7;
    }
    public int getCraftingValue()
    {
        return getRandomCraftingValue(craftingValue,craftingRange);
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
        return "Anvil";
    }

    public int getItemCraftingValue()
    {
        if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound() && anvilItemStacks[1].getTagCompound().hasKey("itemCraftingValue"))
        {
            return anvilItemStacks[1].getTagCompound().getInteger("itemCraftingValue");
        }
        else if(anvilItemStacks[1] != null && !anvilItemStacks[1].hasTagCompound() && craftingValue != 0)
        {
            NBTTagCompound Tag = new NBTTagCompound();
            Tag.setInteger("itemCraftingValue", 0);
            anvilItemStacks[1].setTagCompound(Tag);
            return 0;
        }
        else if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound() && !anvilItemStacks[1].getTagCompound().hasKey("itemCraftingValue") && craftingValue != 0)
        {
            NBTTagCompound Tag = anvilItemStacks[1].getTagCompound();
            Tag.setInteger("itemCraftingValue", 0);
            anvilItemStacks[1].setTagCompound(Tag);
            return 0;
        } else {
            return 0;
        }
    }

    private int getItemCraftingValueNoSet(int i)
    {
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound())
        {
            if(!anvilItemStacks[i].getTagCompound().hasKey("itemCraftingValue"))
            {
                return 0;
            }
            else 
            {
                return anvilItemStacks[i].getTagCompound().getInteger("itemCraftingValue");
            }
        }
        else if(anvilItemStacks[i] != null && !anvilItemStacks[i].hasTagCompound())
        {
            return 0;
        } else {
            return 0;
        }
    }

    public int getRandomCraftingValue(int cv, int range)
    {
        Random R = new Random(this.worldObj.getSeed());
        int c1 = cv - range;
        int c2 = range*2;
        int c3 = c1 + R.nextInt(1+c2);

        return c3;
    }

    public Boolean isTemperatureWeldable(int i)
    {
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound() && anvilItemStacks[i].getTagCompound().hasKey("temperature"))
        {
            if(TFCHeat.ItemHeatData.containsKey(anvilItemStacks[i].getItem().getItemNameIS(anvilItemStacks[i])))
            {
                Object[] meltData = (Object[])TFCHeat.ItemHeatData.get(anvilItemStacks[i].getItem().getItemNameIS(anvilItemStacks[i]));
                float t = anvilItemStacks[i].getTagCompound().getFloat("temperature");
                float m = (Float)meltData[2];

                return t < m && t > m - m * 0.10;

            }
        }
        return false;
    }

    public Boolean isTemperatureWorkable(int i)
    {
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound() && anvilItemStacks[i].getTagCompound().hasKey("temperature"))
        {
            if(TFCHeat.ItemHeatData.containsKey(anvilItemStacks[1].getItem().getItemNameIS(anvilItemStacks[i])))
            {
                Object[] meltData = (Object[])TFCHeat.ItemHeatData.get(anvilItemStacks[i].getItem().getItemNameIS(anvilItemStacks[i]));
                float t = anvilItemStacks[i].getTagCompound().getFloat("temperature");
                float m = (Float)meltData[2];

                return t < m && t > m - m * 0.40;

            }
        }
        return false;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);

        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < anvilItemStacks.length; i++)
        {
            if(anvilItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                anvilItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);

    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        anvilItemStacks = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < anvilItemStacks.length)
            {
                anvilItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        anvilItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
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

    public int getSizeInventory()
    {
        return anvilItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        // TODO Auto-generated method stub  
        return anvilItemStacks[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
    }

    public void handlePacketData(int id)
    {
        switch(id)
        {
            case -1:
            {
                actionLightHammer();
                break;
            }
            case 0:
            {
                actionHeavyHammer();
                break;
            }
            case 1:
            {
                actionDraw();
                break;
            }
            case 2:
            {
                actionQuench();
                break;
            }
            case 3:
            {
                actionPunch();
                break;
            }
            case 4:
            {
                actionBend();
                break;
            }
            case 5:
            {
                actionUpset();
                break;
            }
            case 6:   
            {
                actionShrink();
                break;
            }
            case 7:
            {
                actionWeld();
                break;
            }
        }

    }
   

}
