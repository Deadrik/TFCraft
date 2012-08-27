package TFC.TileEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.Core.AnvilCraftingManagerTFC;
import TFC.Core.AnvilRecipe;
import TFC.Core.AnvilReq;
import TFC.Core.CraftingRule;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFCHeat;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemTerraMeltedMetal;
import TFC.WorldGen.TFCBiome;

import net.minecraft.src.BiomeGenBase;
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
import net.minecraft.src.World;
import net.minecraft.src.TerraFirmaCraft;

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

    private boolean isDone = false;
    private int workedRecently = 0;

    //this is the fix the server receiving 3 packets whenever the player works an item.
    private final int lagFixDelay = 5;
    public AnvilRecipe workRecipe;
    private AnvilRecipe workWeldRecipe;
    public int AnvilTier;

    public TileEntityTerraAnvil()
    {
        anvilItemStacks = new ItemStack[8];
        itemCraftingValue = 0;
        itemCraftingRules = new int[]{-1,-1,-1};
        craftingValue = 0;
        craftingRules = new int[]{-1,-1,-1};

        AnvilTier = AnvilReq.STONE.Tier;
    }

    public void updateEntity()
    {        
        if(anvilItemStacks[1] == null)
        {
            workRecipe = null;
            craftingValue = 0;
        }
        else if(anvilItemStacks[1] != null && workRecipe == null)
        {
            AnvilRecipe recipe = AnvilCraftingManagerTFC.getInstance().findMatchingRecipe(new AnvilRecipe(anvilItemStacks[1],anvilItemStacks[5], 
                    anvilItemStacks[7] != null ? true : false, this.AnvilTier));
            if(recipe != null)
            {
                workRecipe = recipe;
                //craftingValue = recipe.getCraftingValue();
            }
        }
        //make sure that the world is not remote
        if(!worldObj.isRemote)
        {
            //if the item exceeds bounds, destroy it
            if(getItemCraftingValue() < -50 || getItemCraftingValue() > 100)
                anvilItemStacks[1] = null;

            if(workedRecently > 0)
                workedRecently--;
            //Deal with temperatures
            TFCHeat.HandleContainerHeat(this.worldObj, anvilItemStacks, xCoord,yCoord,zCoord);
            /**
             * Check if the recipe is considered complete
             * */
            if(workRecipe!= null && getItemCraftingValue() != itemCraftingValue)
            {
                itemCraftingValue = getItemCraftingValue();

                AnvilCraftingManagerTFC manager = AnvilCraftingManagerTFC.getInstance();
                Random R = new Random(worldObj.getSeed());
                AnvilRecipe recipe = null;
                ItemStack result = null;
                int offset = 0; 

                for(int i = workRecipe.getCraftingValue() - 5; i < workRecipe.getCraftingValue() + 5; i++)
                {
                    if(itemCraftingValue == i)
                    {
                        offset = i - workRecipe.getCraftingValue();
                        if(offset < 0)
                            offset = offset + (-2 * offset);
                        recipe = new AnvilRecipe(anvilItemStacks[1],anvilItemStacks[5],
                                workRecipe.getCraftingValue(),
                                CraftingRule.ANY,
                                CraftingRule.ANY,
                                CraftingRule.ANY, 
                                anvilItemStacks[7] != null ? true : false, AnvilTier, null);

                        result = manager.findCompleteRecipe(recipe, getItemRules());
                        if(result != null)
                            break;
                    }
                }

                if(result != null)
                {
                    NBTTagCompound Tag = new NBTTagCompound();
                    Tag.setFloat("temperature", TFCHeat.GetTemperature(anvilItemStacks[1]));
                    anvilItemStacks[1] = result; 
                    anvilItemStacks[1].setTagCompound(Tag);
                    float pct = (offset*5);
                    if(anvilItemStacks[1].getItem().getMaxDamage() > 0 && !anvilItemStacks[1].getItem().getHasSubtypes())
                        anvilItemStacks[1].setItemDamage((int)(pct));

                }
            }
        }
        if(anvilItemStacks[1] != null && anvilItemStacks[1].stackSize < 1)
        {
            anvilItemStacks[1].stackSize = 1;
        }
    }
    
    public int getCraftingValue()
    {
        if(!worldObj.isRemote)
        {
            return workRecipe != null ? workRecipe.getCraftingValue() : 0;
        }
        else
        {
            return craftingValue;
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
                rule1 = Tag.getByte("itemCraftingRule1");
            }
            if(Tag.hasKey("itemCraftingRule2")) {
                rule2 = Tag.getByte("itemCraftingRule2");
            }
            if(Tag.hasKey("itemCraftingRule3")) {
                rule3 = Tag.getByte("itemCraftingRule3");
            }

            itemCraftingRules[2] = rule2;
            itemCraftingRules[1] = rule1;
            itemCraftingRules[0] = rule;

            Tag.setByte("itemCraftingRule1", (byte) itemCraftingRules[0]);
            Tag.setByte("itemCraftingRule2", (byte) itemCraftingRules[1]);
            Tag.setByte("itemCraftingRule3", (byte) itemCraftingRules[2]);

            anvilItemStacks[slot].setTagCompound(Tag);
        }
    }

    public int[] getItemRules()
    {
        int[] rules = new int[3];
        if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound())
        {
            if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule1"))
                rules[0] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule1");
            else
                rules[0] = CraftingRule.ANY.Action;

            if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule2"))
                rules[1] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule2");
            else
                rules[1] = CraftingRule.ANY.Action;

            if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule3"))
                rules[2] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule3");
            else
                rules[2] = CraftingRule.ANY.Action;
        }
        else
        {
            rules[0] = CraftingRule.ANY.Action;
            rules[1] = CraftingRule.ANY.Action;
            rules[2] = CraftingRule.ANY.Action;
        }
        return rules;
    }

    public void actionHeavyHammer()
    {
        if(!worldObj.isRemote)
        {

            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(-9);
                updateRules(0,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 0));
        }
    }

    public void actionLightHammer()
    {
        if(!worldObj.isRemote)
        {

            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(-3);
                updateRules(0,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, -1));
        }

    }

    public void actionDraw()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(-15);
                updateRules(1,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 1));
        }
    }

    public void actionQuench()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(-49);
                updateRules(2,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 2));
        }
    }

    public void actionPunch()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(2);
                updateRules(3,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 3));
        }
    }

    public void actionBend()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(7);
                updateRules(4,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 4));
        }
    }

    public void actionUpset()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(13);
                updateRules(5,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 5));
        }
    }

    public void actionShrink()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWorkable(1) && anvilItemStacks[0] != null && (anvilItemStacks[1].getItemDamage() == 0 || anvilItemStacks[1].getItem().getHasSubtypes() == true) && getAnvilType() >= craftingReq && workedRecently == 0)
            {
                workedRecently = lagFixDelay;
                setItemCraftingValue(16);
                updateRules(6,1);
                anvilItemStacks[0].setItemDamage(anvilItemStacks[0].getItemDamage()+1);

                if(anvilItemStacks[0].getItemDamage() == anvilItemStacks[0].getMaxDamage()) {
                    anvilItemStacks[0] = null;
                }
                Random R = new Random(xCoord*yCoord+zCoord);
                worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 6));
        }
    } 

    public void actionWeld()
    {
        if(!worldObj.isRemote)
        {
            if(isTemperatureWeldable(2) && isTemperatureWeldable(3) && anvilItemStacks[0] != null && 
                    (anvilItemStacks[2].getItemDamage() == 0 || anvilItemStacks[2].getItem().getHasSubtypes() == true) && (anvilItemStacks[3].getItemDamage() == 0 || anvilItemStacks[3].getItem().getHasSubtypes() == true) && workedRecently == 0 && anvilItemStacks[4] == null)
            {
                AnvilCraftingManagerTFC manager = AnvilCraftingManagerTFC.getInstance();
                Random R = new Random(worldObj.getSeed());
                AnvilRecipe recipe = new AnvilRecipe(anvilItemStacks[2],anvilItemStacks[3],
                        0,
                        CraftingRule.ANY,
                        CraftingRule.ANY,
                        CraftingRule.ANY, 
                        anvilItemStacks[7] != null ? true : false, AnvilTier, null);

                AnvilRecipe recipe2 = new AnvilRecipe(anvilItemStacks[3],anvilItemStacks[2],
                        0,
                        CraftingRule.ANY,
                        CraftingRule.ANY,
                        CraftingRule.ANY, 
                        anvilItemStacks[7] != null ? true : false, AnvilTier, null);

                ItemStack result = manager.findCompleteWeldRecipe(recipe);
                if(result == null)
                    result = manager.findCompleteWeldRecipe(recipe2);

                if(result != null)
                {
                    NBTTagCompound Tag = new NBTTagCompound();
                    Tag.setFloat("temperature", (TFCHeat.GetTemperature(anvilItemStacks[2])+TFCHeat.GetTemperature(anvilItemStacks[3]))/2);
                    anvilItemStacks[4] = result; 
                    anvilItemStacks[4].setTagCompound(Tag);
                    ItemStack item = new ItemStack(anvilItemStacks[7].getItem(),anvilItemStacks[7].stackSize-1);
                    if(item.stackSize == 0) 
                        item = null;

                    ItemStack item2 = new ItemStack(anvilItemStacks[0].getItem(),anvilItemStacks[0].stackSize-1);
                    if(item2.stackSize == 0) 
                        item2 = null;

                    anvilItemStacks[2] = null;
                    anvilItemStacks[3] = null;
                    anvilItemStacks[7] = null;
                    
                    worldObj.playSoundEffect(xCoord,yCoord,zCoord, "metalimpact", 1.0F, 0.7F + (R.nextFloat()/2));
                }
                
            }
        }
        else
        {
            TerraFirmaCraft.proxy.sendCustomPacket(PacketHandler.getPacket(this, 7));
        }
    }
    @Override
    public void closeChest() 
    {
        workRecipe = null;
        if(AnvilTier == AnvilReq.STONE.Tier)
        {
            ejectContents();
            TFCBiome biome = (TFCBiome) worldObj.getBiomeGenForCoords(xCoord, zCoord);
            if(yCoord < biome.Layer3)
                worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, biome.Layer3Type, biome.Layer3Meta);
            else if(yCoord < biome.Layer2)
                worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, biome.Layer2Type, biome.Layer2Meta);
            else                 
                worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, biome.Layer1Type, biome.Layer1Meta);
            
            
        }
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

    public boolean setItemCraftingValue(int i)
    {
        if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound() && anvilItemStacks[1].getTagCompound().hasKey("itemCraftingValue"))
        {
            Byte icv = anvilItemStacks[1].getTagCompound().getByte("itemCraftingValue");
            anvilItemStacks[1].getTagCompound().setByte("itemCraftingValue", (byte) (icv + i));
            return true;
        }
        else if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound())
        {
            anvilItemStacks[1].getTagCompound().setByte("itemCraftingValue", (byte)i);
            return true;
        }
        else if(anvilItemStacks[1] != null && !anvilItemStacks[1].hasTagCompound())
        {
            NBTTagCompound Tag = new NBTTagCompound();
            Tag.setByte("itemCraftingValue", (byte)i);
            anvilItemStacks[1].stackTagCompound = Tag;
            return true;
        }
        return false;
    }
    public int getItemCraftingValue()
    {
        if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound() && anvilItemStacks[1].getTagCompound().hasKey("itemCraftingValue"))
        {
            return anvilItemStacks[1].getTagCompound().getByte("itemCraftingValue");
        }
        else if(anvilItemStacks[1] != null && !anvilItemStacks[1].hasTagCompound() && craftingValue != 0)
        {
            NBTTagCompound Tag = new NBTTagCompound();
            Tag.setByte("itemCraftingValue", (byte) 0);
            anvilItemStacks[1].setTagCompound(Tag);
            return 0;
        }
        else if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound() && !anvilItemStacks[1].getTagCompound().hasKey("itemCraftingValue") && craftingValue != 0)
        {
            NBTTagCompound Tag = anvilItemStacks[1].getTagCompound();
            Tag.setByte("itemCraftingValue", (byte) 0);
            anvilItemStacks[1].setTagCompound(Tag);
            return 0;
        } else {
            return 0;
        }
    }

    public int getItemCraftingValueNoSet(int i)
    {
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound())
        {
            if(!anvilItemStacks[i].getTagCompound().hasKey("itemCraftingValue"))
            {
                return 0;
            }
            else 
            {
                return anvilItemStacks[i].getTagCompound().getByte("itemCraftingValue");
            }
        }
        else if(anvilItemStacks[i] != null && !anvilItemStacks[i].hasTagCompound())
        {
            return 0;
        } else {
            return 0;
        }
    }

    public Boolean isTemperatureWeldable(int i)
    {
        HeatManager manager = HeatManager.getInstance();
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound() && anvilItemStacks[i].getTagCompound().hasKey("temperature"))
        {
            HeatIndex index = manager.findMatchingIndex(anvilItemStacks[i]);
            if(index != null)
            {
                float temp = anvilItemStacks[i].getTagCompound().getFloat("temperature");

                return temp < index.meltTemp && temp > index.meltTemp - index.meltTemp * 0.20 && 
                        (anvilItemStacks[i].getItem() instanceof ItemTerraMeltedMetal ? anvilItemStacks[i].getItemDamage() == 0 : true);

            }
        }
        return false;
    }

    public Boolean isTemperatureWorkable(int i)
    {
        
        HeatManager manager = HeatManager.getInstance();
        if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound() && anvilItemStacks[i].getTagCompound().hasKey("temperature"))
        {
            HeatIndex index = manager.findMatchingIndex(anvilItemStacks[i]);
            if(index != null)
            {
                float temp = anvilItemStacks[i].getTagCompound().getFloat("temperature");

                return temp < index.meltTemp && temp > index.meltTemp - index.meltTemp * 0.40 && 
                        (anvilItemStacks[i].getItem() instanceof ItemTerraMeltedMetal ? anvilItemStacks[i].getItemDamage() == 0 : true);

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
        nbttagcompound.setInteger("Tier", AnvilTier);

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

        AnvilTier = nbttagcompound.getInteger("Tier");
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
