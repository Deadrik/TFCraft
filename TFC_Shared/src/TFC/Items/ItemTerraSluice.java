package TFC.Items;

import TFC.Blocks.BlockTerraSluice;
import net.minecraft.src.*;

public class ItemTerraSluice extends Item
{

    public ItemTerraSluice(int i)
    {
        super(i);
    }

    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        return super.getItemName();
    }

    public int getPlacedBlockMetadata(int i) {

        //System.out.println(new StringBuilder().append(this.getItemDisplayName(new ItemStack(this,1,i))).toString());

        return i;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        int xCoord = i;
        int yCoord = j;
        int zCoord = k;
        int r = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;
        byte byte0 = 0;
        byte byte1 = 0;
        if(r == 0)//+z
        {
            byte1 = 1;
        }
        if(r == 1)//-x
        {
            byte0 = -1;
        }
        if(r == 2)//-z
        {
            byte1 = -1;
        }
        if(r == 3)//+x
        {
            byte0 = 1;
        }
        if(((BlockTerraSluice)mod_TFC.terraSluice).canPlace(world, i, j+1, k,r))
        {
            world.setBlockAndMetadata(i, j+1, k, mod_TFC.terraSluice.blockID, r);
            if(world.getBlockId(i, j+1, k) == mod_TFC.terraSluice.blockID)
            {
                world.setBlockAndMetadataWithNotify(i + byte0, j+1, k + byte1, mod_TFC.terraSluice.blockID, r + 8);
                entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
            }
            return true;
        }
        return false;
    }

}
