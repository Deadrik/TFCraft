package TFC.Items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.*;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

public class ItemFirestarter extends ItemTerra
{

    public ItemFirestarter(int i)
    {
        super(i);
        this.setMaxDamage(8);
        this.hasSubtypes = false;
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
	public EnumSize getSize() {
		return EnumSize.SMALL;
	}

    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        return super.getItemName();
    }

    public int getPlacedBlockMetadata(int i) 
    {
        return i;
    }
    
    @Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(!world.isRemote)
        {
            MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
            if(objectMouseOver == null) {
                return false;
            }       
            int x = objectMouseOver.blockX;
            int y = objectMouseOver.blockY;
            int z = objectMouseOver.blockZ;
            int side = objectMouseOver.sideHit;

            if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
                    world.getBlockMaterial(x, y, z) != Material.wood && world.getBlockMaterial(x, y, z) != Material.cloth &&
                    world.getBlockId(x, y+1, z) == 0 && world.getBlockId(x, y, z) != TFCBlocks.Charcoal.blockID)
            {

                List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y+1, z, x+1, y+2, z+1));
                int numsticks = 0;
                int hasPaper = 0;
                int numcoal = 0;

                if (list != null && !list.isEmpty())
                {
                    for (Iterator iterator = list.iterator(); iterator.hasNext();)
                    {
                        EntityItem entity = (EntityItem)iterator.next();
                        if(entity.item.itemID == Item.paper.shiftedIndex)
                        {
                            hasPaper = 20;
                        }
                        else if(entity.item.itemID == Item.stick.shiftedIndex)
                        {
                            numsticks+=entity.item.stackSize;
                        }
                        else if(entity.item.itemID == Item.coal.shiftedIndex)
                        {
                            numcoal+=entity.item.stackSize;
                        }
                    }
                }

                itemstack.setItemDamage(itemstack.getItemDamage()+1);
                
                if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
                    itemstack.stackSize = 0;

                int chance = new Random().nextInt(100);
                if(chance > 70-hasPaper)
                {
                    if(numsticks >= 3)
                    {
                        for (Iterator iterator = list.iterator(); iterator.hasNext();)
                        {
                            EntityItem entity = (EntityItem)iterator.next();
                            if(entity.item.itemID == Item.stick.shiftedIndex)
                            {
                                entity.setDead();
                            }
                            if(entity.item.itemID == Item.paper.shiftedIndex)
                            {
                                entity.setDead();
                            }
                        }
                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.Firepit.blockID, 1);
                        if(world.isRemote)
                            world.markBlockForUpdate(x, y+1, z);
                    }
                    else if(numcoal >= 7 && world.getBlockMaterial(x, y, z) == Material.rock && 
                            world.getBlockMaterial(x+1, y+1, z) == Material.rock && world.getBlockMaterial(x-1, y+1, z) == Material.rock && 
                            world.getBlockMaterial(x, y+1, z+1) == Material.rock && world.getBlockMaterial(x, y+1, z-1) == Material.rock &&
                            world.isBlockNormalCube(x, y, z) && ((world.isBlockNormalCube(x+1, y+1, z) && world.isBlockNormalCube(x-1, y+1, z) && 
                            world.isBlockNormalCube(x, y+1, z+1) && world.isBlockNormalCube(x, y+1, z-1)) || (checkSlabsAround(world, x, y+1, z))))
                    {
                        for (Iterator iterator = list.iterator(); iterator.hasNext();)
                        {
                            EntityItem entity = (EntityItem)iterator.next();
                            if(entity.item.itemID == Item.stick.shiftedIndex)
                            {
                                entity.setDead();
                            }
                            if(entity.item.itemID == Item.coal.shiftedIndex)
                            {
                                entity.setDead();
                            }
                        }
                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.Forge.blockID, 1);
                        if(world.isRemote)
                            world.markBlockForUpdate(x, y+1, z);
                    }

                    return true;
                }
            }
            else if(world.getBlockId(x, y, z) == TFCBlocks.Charcoal.blockID && world.getBlockMetadata(x, y, z) > 6)
            {
            	if(world.getBlockMaterial(x, y-1, z) == Material.rock && 
                            world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
                            world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
                            world.isBlockNormalCube(x, y-1, z) && ((world.isBlockNormalCube(x+1, y, z) && world.isBlockNormalCube(x-1, y, z) && 
                            world.isBlockNormalCube(x, y, z+1) && world.isBlockNormalCube(x, y, z-1)) || (checkSlabsAround(world, x, y, z))))
            	{
            		int chance = new Random().nextInt(100);
                    if(chance > 70)
                    {
                    	world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.Forge.blockID, 1);
                    	world.markBlockForUpdate(x, y, z);
                    }
            	}
            }
            return false;
        }
        return false;
    }
    
    public static boolean checkSlabsAround(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x-1, y, z);
    		if(BlockSlab.getPosXChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x+1, y, z);
    		if(BlockSlab.getNegXChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
    		if(BlockSlab.getPosZChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
    		if(BlockSlab.getNegZChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	return true;
    }
}
