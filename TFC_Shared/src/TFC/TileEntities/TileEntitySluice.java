package TFC.TileEntities;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.*;
import TFC.Blocks.Devices.BlockSluice;
import TFC.Blocks.Terrain.BlockOre;
import TFC.Items.ItemOre1;
import TFC.Core.TFC_Time;
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

public class TileEntitySluice extends TileEntity implements IInventory
{	
    public int soilAmount;
    public long lastUpdateTicks;
    public int processTimeRemaining;
    private ItemStack sluiceItemStacks[];
    public boolean waterInput;
    public boolean waterOutput;
    public byte soilType;

    private boolean initialized = false;
    private Random random = new Random();

    private TreeSet<Integer> coreSampleTypes = new TreeSet<Integer>();
    private ArrayList<ItemStack> coreSampleStacks = new ArrayList<ItemStack>();

    public TileEntitySluice()
    {
        sluiceItemStacks = new ItemStack[9];
        soilAmount = 0;
        lastUpdateTicks = 0;
        processTimeRemaining = 0;
        waterInput = false;
        waterOutput = false;
        soilType = 1;
    }

    public void addToInventory(ItemStack is)
    {
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            ItemStack stackInSlot = this.getStackInSlot(i);
            if(stackInSlot == null)
            {
                // slot is empty
                this.setInventorySlotContents(i, is);
                return;
            }
            else
            {
                // slot has something, does it match the type and subtype?
                if((stackInSlot.itemID == is.itemID) && (stackInSlot.getItemDamage() == is.getItemDamage()))
                {
                    // match, add to this slot but make sure it fits
                    if(stackInSlot.stackSize + is.stackSize > this.getInventoryStackLimit())
                    {
                        // doesn't fit, add as much as possible then leave the remaining for the next slot
                        int size = getInventoryStackLimit() - stackInSlot.stackSize;
                        stackInSlot.stackSize += size;
                        is.stackSize -= size;
                        continue;
                    }
                    else
                    {
                        // it fits, add it
                        stackInSlot.stackSize += is.stackSize;
                    }
                    return;
                }
                else
                {
                    // not the same item, try the next slot
                    continue;
                }
            }
        }
        ejectItem(is);
    }

    @Override
    public void closeChest()
    {

    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(sluiceItemStacks[i] != null)
        {
            if(sluiceItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack = sluiceItemStacks[i];
                sluiceItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = sluiceItemStacks[i].splitStack(j);
            if(sluiceItemStacks[i].stackSize == 0)
            {
                sluiceItemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }

    }

    private void ejectItem(ItemStack is)
    {
        float f = random.nextFloat() * 0.8F + 0.1F;
        float f1 = random.nextFloat() * 2.0F + 0.4F;
        float f2 = random.nextFloat() * 0.8F + 0.1F;
        int x = xCoord;

        EntityItem entityitem = new EntityItem(worldObj, (float)xCoord + f, (float)yCoord + f1, (float)zCoord + f2, new ItemStack(is.itemID, is.stackSize, is.getItemDamage()));
        float f3 = 0.05F;
        entityitem.motionX = (float)random.nextGaussian() * f3;
        entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
        entityitem.motionZ = (float)random.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);
    }

    public int getFirstFreeSlot()
    {
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            if(this.getStackInSlot(i) == null) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "Sluice";
    }

    public int getProcessScaled(int i)
    {
        return processTimeRemaining * i / 100;
    }

    @Override
    public int getSizeInventory()
    {
        return sluiceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        // TODO Auto-generated method stub
        return sluiceItemStacks[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;

    }

    @Override
    public void openChest()
    {
        // TODO Auto-generated method stub

    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        sluiceItemStacks = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < sluiceItemStacks.length)
            {
                sluiceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        soilType = nbttagcompound.getByte("soilType");
        soilAmount = nbttagcompound.getInteger("soilAmount");
        processTimeRemaining = nbttagcompound.getInteger("processTimeRemaining");
        lastUpdateTicks = nbttagcompound.getLong("lastUpdateTicks");
        waterInput = nbttagcompound.getBoolean("waterInput");
        waterOutput = nbttagcompound.getBoolean("waterOutput");
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        sluiceItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }


    }

    public void updateEntity()
    {
        int meta = getBlockMetadata();
        boolean isFoot = BlockSluice.isBlockFootOfBed(meta);

        /*********************************************************
         ********************* Server Only Begin
         *********************************************************/
        if(!worldObj.isRemote)
        {
            if(!initialized)
            {
                for(int x = -100; x <= 100; x += 2)
                {
                    for(int z = -100; z <= 100; z += 2)
                    {
                        for(int y = yCoord; y > yCoord-50; y--)
                        {
                            if(worldObj.getBlockId(x+xCoord, y, z+zCoord) == TFCBlocks.Ore.blockID)
                            {
                                int m = worldObj.getBlockMetadata(x+xCoord, y, z+zCoord);
                                if(m != 14 && m != 15)
                                {
                                    if(!coreSampleTypes.contains(m))
                                    {
                                        coreSampleTypes.add(m);
                                        coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
                                    }
                                }
                            }
                        }
                    }
                }
                initialized = true;
            }

            // don't eat blocks on top of foot block, only the head block
            if(!isFoot)
            {
                int[] dir = BlockSluice.headBlockToFootBlockMap[BlockSluice.getDirectionFromMetadata(meta)];
                List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
                        xCoord, yCoord, zCoord, 
                        xCoord+1, yCoord+1.1f, zCoord+1));
            
                for (Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    EntityItem entity = (EntityItem)iterator.next();
                    if(entity.getEntityItem().itemID == Block.gravel.blockID || entity.getEntityItem().itemID == TFCBlocks.Sand.blockID || entity.getEntityItem().itemID == TFCBlocks.Sand2.blockID)
                    {
                        if(soilAmount < 50 && entity.getEntityItem().stackSize == 1)
                        {
                            soilAmount += 20;
                            entity.setDead();
                            if(soilAmount > 50)
                                soilAmount = 50;
                            if(entity.getEntityItem().itemID == Block.gravel.blockID)
                            {
                                soilType = 2;
                            }
                            else
                            {
                                soilType = 1;
                            }
                        }
                    }
                }
            }

            // time since last update
            long tickDiff = TFC_Time.getTotalTicks() - lastUpdateTicks;
            if(lastUpdateTicks == 0)
            {
                // first update
                tickDiff = 0;
            }
            lastUpdateTicks = TFC_Time.getTotalTicks();

            //This is where we handle the processing of the material
            if(soilAmount > 0 && waterInput && waterOutput)
            {
                // note that the input & output flags were checked last update, so it should be ok to use the
                // diff if sluice was just re-loaded
                processTimeRemaining += tickDiff;
                if(processTimeRemaining < 0)
                {
                    // overflow?
                    processTimeRemaining = 0;
                }
                while(processTimeRemaining > 100 && soilAmount > 0)
                {
                    //items.add(mod_TFCraft.terraSmallOre);
                    //items.add(mod_TFCraft.terraTinyOre);

                    BiomeGenBase biome1 = worldObj.getBiomeGenForCoords(xCoord+100, zCoord);
                    BiomeGenBase biome2 = worldObj.getBiomeGenForCoords(xCoord-100, zCoord);
                    BiomeGenBase biome3 = worldObj.getBiomeGenForCoords(xCoord, zCoord+100);
                    BiomeGenBase biome4 = worldObj.getBiomeGenForCoords(xCoord, zCoord-100);

                    float gemMod = 1;
                    float oreMod = 1;
                    if(soilType == 1)
                        gemMod = 0.65f;
                    else if(soilType == 2)
                        oreMod = 0.6f;


                    ArrayList items = new ArrayList<ItemStack>();
                    if(random.nextInt((int) (200*oreMod)) == 0 && !coreSampleStacks.isEmpty())
                    {
                        addToInventory(coreSampleStacks.get(random.nextInt(coreSampleStacks.size())).copy());
                    }
                    else if(random.nextInt((int) (400*gemMod)) == 0)
                    {

                        items.add(new ItemStack(TFCItems.GemAgate,1,0));
                        items.add(new ItemStack(TFCItems.GemAmethyst,1,0));
                        items.add(new ItemStack(TFCItems.GemBeryl,1,0));
                        items.add(new ItemStack(TFCItems.GemEmerald,1,0));
                        items.add(new ItemStack(TFCItems.GemGarnet,1,0));
                        items.add(new ItemStack(TFCItems.GemJade,1,0));
                        items.add(new ItemStack(TFCItems.GemJasper,1,0));
                        items.add(new ItemStack(TFCItems.GemOpal,1,0));
                        items.add(new ItemStack(TFCItems.GemRuby,1,0));
                        items.add(new ItemStack(TFCItems.GemSapphire,1,0));
                        items.add(new ItemStack(TFCItems.GemTourmaline,1,0));
                        items.add(new ItemStack(TFCItems.GemTopaz,1,0));

                        addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

                    }
                    else if(random.nextInt((int) (800*gemMod)) == 0)
                    {
                        items.add(new ItemStack(TFCItems.GemAgate,1,1));
                        items.add(new ItemStack(TFCItems.GemAmethyst,1,1));
                        items.add(new ItemStack(TFCItems.GemBeryl,1,1));
                        items.add(new ItemStack(TFCItems.GemEmerald,1,1));
                        items.add(new ItemStack(TFCItems.GemGarnet,1,1));
                        items.add(new ItemStack(TFCItems.GemJade,1,1));
                        items.add(new ItemStack(TFCItems.GemJasper,1,1));
                        items.add(new ItemStack(TFCItems.GemOpal,1,1));
                        items.add(new ItemStack(TFCItems.GemRuby,1,1));
                        items.add(new ItemStack(TFCItems.GemSapphire,1,1));
                        items.add(new ItemStack(TFCItems.GemTourmaline,1,1));
                        items.add(new ItemStack(TFCItems.GemTopaz,1,1));

                        addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (1600*gemMod)) == 0)
                    {
                        items.add(new ItemStack(TFCItems.GemAgate,1,2));
                        items.add(new ItemStack(TFCItems.GemAmethyst,1,2));
                        items.add(new ItemStack(TFCItems.GemBeryl,1,2));
                        items.add(new ItemStack(TFCItems.GemEmerald,1,2));
                        items.add(new ItemStack(TFCItems.GemGarnet,1,2));
                        items.add(new ItemStack(TFCItems.GemJade,1,2));
                        items.add(new ItemStack(TFCItems.GemJasper,1,2));
                        items.add(new ItemStack(TFCItems.GemOpal,1,2));
                        items.add(new ItemStack(TFCItems.GemRuby,1,2));
                        items.add(new ItemStack(TFCItems.GemSapphire,1,2));
                        items.add(new ItemStack(TFCItems.GemTourmaline,1,2));
                        items.add(new ItemStack(TFCItems.GemTopaz,1,2));

                        addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (3200*gemMod)) == 0)
                    {
                        items.add(new ItemStack(TFCItems.GemAgate,1,3));
                        items.add(new ItemStack(TFCItems.GemAmethyst,1,3));
                        items.add(new ItemStack(TFCItems.GemBeryl,1,3));
                        items.add(new ItemStack(TFCItems.GemEmerald,1,3));
                        items.add(new ItemStack(TFCItems.GemGarnet,1,3));
                        items.add(new ItemStack(TFCItems.GemJade,1,3));
                        items.add(new ItemStack(TFCItems.GemJasper,1,3));
                        items.add(new ItemStack(TFCItems.GemOpal,1,3));
                        items.add(new ItemStack(TFCItems.GemRuby,1,3));
                        items.add(new ItemStack(TFCItems.GemSapphire,1,3));
                        items.add(new ItemStack(TFCItems.GemTourmaline,1,3));
                        items.add(new ItemStack(TFCItems.GemTopaz,1,3));

                        addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
                    }
                    else if(random.nextInt((int) (6400*gemMod)) == 0)
                    {
                        items.add(new ItemStack(TFCItems.GemAgate,1,4));
                        items.add(new ItemStack(TFCItems.GemAmethyst,1,4));
                        items.add(new ItemStack(TFCItems.GemBeryl,1,4));
                        items.add(new ItemStack(TFCItems.GemEmerald,1,4));
                        items.add(new ItemStack(TFCItems.GemGarnet,1,4));
                        items.add(new ItemStack(TFCItems.GemJade,1,4));
                        items.add(new ItemStack(TFCItems.GemJasper,1,4));
                        items.add(new ItemStack(TFCItems.GemOpal,1,4));
                        items.add(new ItemStack(TFCItems.GemRuby,1,4));
                        items.add(new ItemStack(TFCItems.GemSapphire,1,4));
                        items.add(new ItemStack(TFCItems.GemTourmaline,1,4));
                        items.add(new ItemStack(TFCItems.GemTopaz,1,4));

                        addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);

                    }
                    else if(random.nextInt((int) (12800*gemMod)) == 0)
                    {
                        int r = random.nextInt(50);
                        
                        if(r == 0)
                        {
                            addToInventory(new ItemStack(TFCItems.GemDiamond,1,3));
                        }
                        else if(r < 15)
                        {
                            addToInventory(new ItemStack(TFCItems.GemDiamond,1,2));
                        }
                        else if(r < 25)
                        {
                            addToInventory(new ItemStack(TFCItems.GemDiamond,1,1));
                        }
                        else if(r < 50)
                        {
                            addToInventory(new ItemStack(TFCItems.GemDiamond,1,0));
                        }
                    }
                    processTimeRemaining -= 100;
                    soilAmount--;
                }
            }
            if(soilAmount == 0)
            {
                processTimeRemaining = 0;
            }
        }
        /*********************************************************
         ********************* Server Only End
         *********************************************************/
        //Here we make sure that the water flags are checked
        if((meta & 3 )== 0)
        {
            waterInput = worldObj.getBlockId(xCoord, yCoord+1, zCoord-1) == Block.waterStill.blockID;
            waterOutput = worldObj.getBlockId(xCoord, yCoord-1, zCoord+2) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord, yCoord-1, zCoord+2) == Block.waterMoving.blockID;
        }
        if((meta & 3 )== 1)
        {
            waterInput = worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == Block.waterStill.blockID;
            waterOutput = worldObj.getBlockId(xCoord-2, yCoord-1, zCoord) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord-2, yCoord-1, zCoord) == Block.waterMoving.blockID;
        }
        if((meta & 3 )== 2)
        {
            waterInput = worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == Block.waterStill.blockID;
            waterOutput = worldObj.getBlockId(xCoord, yCoord-1, zCoord-2) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord, yCoord-1, zCoord-2) == Block.waterMoving.blockID;
        }
        if((meta & 3 )== 3)
        {
            waterInput = worldObj.getBlockId(xCoord-1, yCoord+1, zCoord) == Block.waterStill.blockID;
            waterOutput = worldObj.getBlockId(xCoord+2, yCoord-1, zCoord) == Block.waterStill.blockID ||
                    worldObj.getBlockId(xCoord+2, yCoord-1, zCoord) == Block.waterMoving.blockID;
        }






        /////////////////////////////////////////////////////////
        ///////////This is where we handle the water flow
        ////////////////////////////////////////////////////////
        boolean isFlowing = (meta & 4) == 4;

        if((meta & 3) == 0 && !isFoot)
        {
            boolean isInputWater = worldObj.getBlockId(xCoord, yCoord+1, zCoord-1) == Block.waterStill.blockID;
            boolean isOutputAir = worldObj.getBlockId(xCoord, yCoord-1, zCoord+2) == 0;
            boolean isOutputWater = worldObj.getBlockId(xCoord, yCoord-1, zCoord+2) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord, yCoord-1, zCoord+2) == Block.waterMoving.blockID;
            boolean isWaterDepth7 = worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord-1) == 7;
            int meta2 = worldObj.getBlockMetadata(xCoord, yCoord, zCoord+1);
            if(isInputWater && isWaterDepth7 && !isFlowing && (isOutputAir || isOutputWater))
            {
                //set main block to water on
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);

                if((meta2 & 4) == 0)
                {
                    //set second block to water on
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord+1, meta2 + 4, 3);
                }

                //Set output water
                worldObj.setBlock(xCoord, yCoord-1, zCoord+2, Block.waterStill.blockID);
            }
            if((!isInputWater || !isWaterDepth7 || !isOutputAir && !isOutputWater)&& isFlowing)
            {
                //set main block to water off
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 3);
                if((meta2 & 4) != 0)
                {
                    //set second block to water off
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord+1, meta2 - 4, 3);
                }
                //Set output water
                if(!isOutputAir && isOutputWater) {
                    worldObj.setBlock(xCoord, yCoord-1, zCoord+2, 0);
                }
            }
        }
        if((meta & 3) == 1 && !isFoot)
        {
            boolean isInputWater = worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == Block.waterStill.blockID;
            boolean isOutputAir = worldObj.getBlockId(xCoord-2, yCoord-1, zCoord) == 0;
            boolean isOutputWater = worldObj.getBlockId(xCoord-2, yCoord-1, zCoord) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord-2, yCoord-1, zCoord) == Block.waterMoving.blockID;
            boolean isWaterDepth7 = worldObj.getBlockMetadata(xCoord+1, yCoord+1, zCoord) == 7;
            int meta2 = worldObj.getBlockMetadata(xCoord-1, yCoord, zCoord);
            if(isInputWater && isWaterDepth7 && !isFlowing && (isOutputAir || isOutputWater))
            {
                //set main block to water on
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);
                if((worldObj.getBlockMetadata(xCoord-1, yCoord, zCoord) & 4) == 0)
                {
                    //set second block to water on
                    worldObj.setBlockMetadataWithNotify(xCoord-1, yCoord, zCoord, meta2 + 4, 3);
                }
                //Set output water
                worldObj.setBlock(xCoord-2, yCoord-1, zCoord, Block.waterStill.blockID);
            }
            if((!isInputWater || !isWaterDepth7 || !isOutputAir && !isOutputWater)&& isFlowing)
            {
                //set main block to water off
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 3);
                if((worldObj.getBlockMetadata(xCoord-1, yCoord, zCoord) & 4) != 0)
                {
                    //set second block to water off
                    worldObj.setBlockMetadataWithNotify(xCoord-1, yCoord, zCoord, meta2 - 4, 3);
                }
                //Set output water
                if(!isOutputAir && isOutputWater) {
                    worldObj.setBlock(xCoord-2, yCoord-1, zCoord, 0);
                }
            }
        }
        if((meta & 3) == 2 && !isFoot)
        {
            boolean isInputWater = worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == Block.waterStill.blockID;
            boolean isOutputAir = worldObj.getBlockId(xCoord, yCoord-1, zCoord-2) == 0;
            boolean isOutputWater = worldObj.getBlockId(xCoord, yCoord-1, zCoord-2) == Block.waterStill.blockID || 
                    worldObj.getBlockId(xCoord, yCoord-1, zCoord-2) == Block.waterMoving.blockID;
            boolean isWaterDepth7 = worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord+1) == 7;
            int meta2 = worldObj.getBlockMetadata(xCoord, yCoord, zCoord-1);

            if(isInputWater && isWaterDepth7 && !isFlowing && (isOutputAir || isOutputWater))
            {
                //set main block to water on
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);

                if((meta2 & 4) == 0)
                {
                    //set second block to water on
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord-1, meta2 + 4, 3);
                }

                //Set output water
                worldObj.setBlock(xCoord, yCoord-1, zCoord-2, Block.waterStill.blockID);
            }
            if((!isInputWater || !isWaterDepth7 || !isOutputAir && !isOutputWater)&& isFlowing)
            {
                //set main block to water off
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 3);
                if((meta2 & 4) != 0)
                {
                    //set second block to water off
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord-1, meta2 - 4, 3);
                }
                //Set output water
                if(!isOutputAir && isOutputWater) {
                    worldObj.setBlock(xCoord, yCoord-1, zCoord-2, 0);
                }
            }
        }
        if((meta & 3) == 3 && !isFoot)
        {
            boolean isInputWater = worldObj.getBlockId(xCoord-1, yCoord+1, zCoord) == Block.waterStill.blockID;
            boolean isOutputAir = worldObj.getBlockId(xCoord+2, yCoord-1, zCoord) == 0;
            boolean isOutputWater = worldObj.getBlockId(xCoord+2, yCoord-1, zCoord) == Block.waterStill.blockID ||
                    worldObj.getBlockId(xCoord+2, yCoord-1, zCoord) == Block.waterMoving.blockID;
            boolean isWaterDepth7 = worldObj.getBlockMetadata(xCoord-1, yCoord+1, zCoord) == 7;
            int meta2 = worldObj.getBlockMetadata(xCoord+1, yCoord, zCoord);

            if(isInputWater && isWaterDepth7 && !isFlowing && (isOutputAir || isOutputWater))
            {
                //set main block to water on
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);
                if((worldObj.getBlockMetadata(xCoord+1, yCoord, zCoord) & 4) == 0)
                {
                    //set second block to water on
                    worldObj.setBlockMetadataWithNotify(xCoord+1, yCoord, zCoord, meta2 + 4, 3);
                }
                //Set output water
                worldObj.setBlock(xCoord+2, yCoord-1, zCoord, Block.waterStill.blockID);
            }
            if((!isInputWater || !isWaterDepth7 || !isOutputAir && !isOutputWater)&& isFlowing)
            {
                //set main block to water off
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 3);
                if((worldObj.getBlockMetadata(xCoord+1, yCoord, zCoord) & 4) != 0)
                {
                    //set second block to water off
                    worldObj.setBlockMetadataWithNotify(xCoord+1, yCoord, zCoord, meta2 - 4, 3);
                }
                //Set output water
                if(!isOutputAir && isOutputWater) {
                    worldObj.setBlock(xCoord+2, yCoord-1, zCoord, 0);
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setByte("soilType", soilType);
        nbttagcompound.setInteger("soilAmount", soilAmount);
        nbttagcompound.setInteger("processTimeRemaining", processTimeRemaining);
        nbttagcompound.setLong("lastUpdateTicks", lastUpdateTicks);
        nbttagcompound.setBoolean("waterInput", waterInput);
        nbttagcompound.setBoolean("waterOutput", waterOutput);

        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < sluiceItemStacks.length; i++)
        {
            if(sluiceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                sluiceItemStacks[i].writeToNBT(nbttagcompound1);
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
}
