package TFC.Items;

import TFC.*;
import TFC.Core.AnvilReq;
import TFC.Core.Helper;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityTerraAnvil;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemFruitTreeSapling extends ItemTerra
{
    
    String[] Names = {"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
    int offset;

    public ItemFruitTreeSapling(int id, String tex, int off)
    {
        super(id, tex);
        offset = off;
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
    {
        int meta = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
        if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
                (world.getBlockMaterial(x, y, z) == Material.grass || world.getBlockMaterial(x, y, z) == Material.ground) &&
                world.getBlockId(x, y+1, z) == 0 && !world.isRemote)
        {
            
            world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.fruitTreeWood.blockID, stack.getItemDamage()+offset);
            world.markBlockNeedsUpdate(x, y+1, z);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setHeight(0);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setBirth();
            
            stack.stackSize = stack.stackSize-1;
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
