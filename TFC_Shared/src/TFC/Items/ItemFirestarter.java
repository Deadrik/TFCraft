package TFC.Items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.Core.Helper;
import TFC.Enums.EnumSize;

import net.minecraft.src.*;

public class ItemFirestarter extends ItemTerra
{

    public ItemFirestarter(int i)
    {
        super(i);
        this.setMaxDamage(8);
        this.size = EnumSize.SMALL;
        this.hasSubtypes = false;
        this.setTabToDisplayOn(CreativeTabs.tabTools);
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
                    world.getBlockId(x, y+1, z) == 0)
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
                if(chance > 80-hasPaper)
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
                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.FirepitOn.blockID, 2);
                        if(world.isRemote)
                            world.markBlockNeedsUpdate(x, y+1, z);
                    }
                    else if(numcoal >= 9 && world.getBlockMaterial(x, y, z) == Material.rock && 
                            world.getBlockMaterial(x+1, y+1, z) == Material.rock && world.getBlockMaterial(x-1, y+1, z) == Material.rock && 
                            world.getBlockMaterial(x, y+1, z+1) == Material.rock && world.getBlockMaterial(x, y+1, z-1) == Material.rock &&
                            world.isBlockNormalCube(x, y, z) && world.isBlockNormalCube(x+1, y+1, z) && world.isBlockNormalCube(x-1, y+1, z) && 
                            world.isBlockNormalCube(x, y+1, z+1) && world.isBlockNormalCube(x, y+1, z-1))
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
                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.ForgeOn.blockID, 1);
                        if(world.isRemote)
                            world.markBlockNeedsUpdate(x, y+1, z);
                    }

                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    
}
