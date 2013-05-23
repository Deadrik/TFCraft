package TFC.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Items.ItemTerra;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityPottery;

public class ItemFirestarter extends ItemTerra
{

    public ItemFirestarter(int i)
    {
        super(i);
        this.setMaxDamage(8);
        this.hasSubtypes = false;
        setCreativeTab(TFCTabs.TFCTools);
    }
    
    @Override
	public EnumSize getSize() {
		return EnumSize.SMALL;
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
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float hitX, float hitY, float hitZ)
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
            
            boolean surroundSolids = world.isBlockNormalCube(x+1, y, z) && world.isBlockNormalCube(x-1, y, z) && 
							world.isBlockNormalCube(x, y, z+1) && world.isBlockNormalCube(x, y, z-1);
			
			boolean rockXP = world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID || 
					world.isBlockNormalCube(x+1, y, z);
			boolean rockXN = world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID || 
					world.isBlockNormalCube(x-1, y, z);
			boolean rockZP = world.getBlockId(x, y, z+1) == TFCBlocks.stoneSlabs.blockID || 
					world.isBlockNormalCube(x, y, z+1);
			boolean rockZN = world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID || 
					world.isBlockNormalCube(x, y, z-1);

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
                        if(entity.getEntityItem().itemID == Item.paper.itemID)
                        {
                            hasPaper = 40;
                        }
                        else if(entity.getEntityItem().itemID == Item.stick.itemID)
                        {
                            numsticks+=entity.getEntityItem().stackSize;
                        }
                        else if(entity.getEntityItem().itemID == Item.coal.itemID)
                        {
                            numcoal+=entity.getEntityItem().stackSize;
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
                            if(entity.getEntityItem().itemID == Item.stick.itemID)
                            {
                                entity.setDead();
                            }
                            if(entity.getEntityItem().itemID == Item.paper.itemID)
                            {
                                entity.setDead();
                            }
                        }
                        world.setBlock(x, y+1, z, TFCBlocks.Firepit.blockID, 1, 2);
                    }
                    else if(numcoal >= 7 && world.getBlockMaterial(x, y, z) == Material.rock && 
                            world.getBlockMaterial(x+1, y+1, z) == Material.rock && world.getBlockMaterial(x-1, y+1, z) == Material.rock && 
                            world.getBlockMaterial(x, y+1, z+1) == Material.rock && world.getBlockMaterial(x, y+1, z-1) == Material.rock &&
                            world.isBlockNormalCube(x, y, z) && ((world.isBlockNormalCube(x+1, y+1, z) && world.isBlockNormalCube(x-1, y+1, z) && 
                            world.isBlockNormalCube(x, y+1, z+1) && world.isBlockNormalCube(x, y+1, z-1)) || (checkIfSlabsAroundAreValid(world, x, y+1, z))))
                    {
                        for (Iterator iterator = list.iterator(); iterator.hasNext();)
                        {
                            EntityItem entity = (EntityItem)iterator.next();
                            if(entity.getEntityItem().itemID == Item.stick.itemID)
                            {
                                entity.setDead();
                            }
                            if(entity.getEntityItem().itemID == Item.coal.itemID)
                            {
                                entity.setDead();
                            }
                        }
                        world.setBlock(x, y+1, z, TFCBlocks.Forge.blockID, 1, 2);
                    }

                    return true;
                }
            }
            else if(world.getBlockId(x, y, z) == TFCBlocks.Charcoal.blockID && world.getBlockMetadata(x, y, z) > 6)
            {
            	if(world.getBlockMaterial(x, y-1, z) == Material.rock && 
                            world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
                            world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
                            world.isBlockNormalCube(x, y-1, z) && (surroundSolids || (checkIfSlabsAroundAreValid(world, x, y, z))))
            	{
            		int chance = new Random().nextInt(100);
                    if(chance > 70)
                    {
                    	world.setBlock(x, y, z, TFCBlocks.Forge.blockID, 1, 2);
                    }
                    return true;
            	}
            }
            else if(world.getBlockId(x, y, z) == TFCBlocks.LogPile.blockID)
            {
            	if(world.getBlockId(x, y-1, z) == TFCBlocks.Pottery.blockID && (surroundSolids || (checkIfSlabsAroundAreValid(world, x, y, z))))
            	{
            		int chance = new Random().nextInt(100);
                    if(chance > 70)
                    {
                    	TileEntityPottery te = (TileEntityPottery) world.getBlockTileEntity(x, y-1, z);
                    	te.StartPitFire();
                    }
            	}
            }
            return false;
        }
        return false;
    }
    
    public static boolean checkIfSlabsAroundAreValid(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x-1, y, z);
    		if(BlockSlab.getEastChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x+1, y, z);
    		if(BlockSlab.getWestChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
    		if(BlockSlab.getSouthChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	if(world.getBlockId(x, y, z+1) == TFCBlocks.stoneSlabs.blockID)
    	{
    		TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z+1);
    		if(BlockSlab.getNorthChiselLevel(te.extraData) != 0)
    		{
    			return false;
    		}
    	}
    	return true;
    }
}
