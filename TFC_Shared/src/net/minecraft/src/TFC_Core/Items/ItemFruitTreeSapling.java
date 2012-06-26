package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TileEntityFruitTreeWood;
import net.minecraft.src.TFC_Core.TileEntityTerraAnvil;
import net.minecraft.src.TFC_Core.General.AnvilReq;
import net.minecraft.src.TFC_Core.General.Helper;

public class ItemFruitTreeSapling extends ItemTerra
{
    
    String[] Names = {"Red Apple","Banana","Green Apple","Orange","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
    int offset;

    public ItemFruitTreeSapling(int id, String tex, int off)
    {
        super(id, tex);
        offset = off;
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
        if(objectMouseOver == null) 
        {
            return false;
        }       
        int x = objectMouseOver.blockX;
        int y = objectMouseOver.blockY;
        int z = objectMouseOver.blockZ;
        int side = objectMouseOver.sideHit;
        int meta = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;
        if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
                (world.getBlockMaterial(x, y, z) == Material.grass || world.getBlockMaterial(x, y, z) == Material.ground) &&
                world.getBlockId(x, y+1, z) == 0)
        {
            
            world.setBlockAndMetadataWithNotify(x, y+1, z, mod_TFC_Core.fruitTreeWood.blockID, itemstack.getItemDamage()+offset);
            world.markBlockNeedsUpdate(x, y+1, z);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setHeight(0);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setBirth();
            
            
            itemstack.stackSize = itemstack.stackSize-1;
            return true;
        }

        return false;
    }
    
    public int getIconFromDamage(int par1)
    {
        return this.iconIndex + par1;
    }
    
    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemName()).append(".").append(Names[itemstack.getItemDamage()+offset]).toString();
        return s;
    }

    public int getMetadata(int i)
    {
        return i;
    }
}
