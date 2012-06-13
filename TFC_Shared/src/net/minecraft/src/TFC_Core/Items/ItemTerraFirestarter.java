package net.minecraft.src.TFC_Core.Items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraFirestarter extends Item implements ITextureProvider
{

    public ItemTerraFirestarter(int i)
    {
        super(i);
        this.setMaxDamage(8);
        maxStackSize = 1;
        this.hasSubtypes = false;
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
                            entity.setDead();
                            hasPaper = 20;
                        }
                        else if(entity.item.itemID == Item.stick.shiftedIndex)
                        {
                            numsticks++;

                        }
                        else if(entity.item.itemID == Item.coal.shiftedIndex)
                        {
                            numcoal++;
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
                        }
                        world.setBlockAndMetadataWithNotify(x, y+1, z, mod_TFC_Core.terraFirepitOn.blockID, 2);
                        if(world.isRemote)
                            world.markBlockNeedsUpdate(x, y+1, z);
                    }
                    else if(numcoal >= 9 && world.getBlockMaterial(x, y, z) == Material.rock)
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
                        world.setBlockAndMetadataWithNotify(x, y+1, z, mod_TFC_Core.terraForgeOn.blockID, 1);
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
