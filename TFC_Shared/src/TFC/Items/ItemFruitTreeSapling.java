package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.AnvilReq;
import TFC.Core.Helper;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityTerraAnvil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemFruitTreeSapling extends ItemTerra
{
    
    String[] Names = {"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
    int offset;

    public ItemFruitTreeSapling(int id, int off)
    {
        super(id);
        offset = off;
        setMaxDamage(0);
        setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
    {
        int meta = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
        if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
                (world.getBlockMaterial(x, y, z) == Material.grass || world.getBlockMaterial(x, y, z) == Material.ground) &&
                world.getBlockId(x, y+1, z) == 0 && !world.isRemote)
        {
            
            world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.fruitTreeWood.blockID, stack.getItemDamage()+offset, 3);
            world.markBlockForUpdate(x, y+1, z);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setHeight(0);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setBirth();
            
            stack.stackSize = stack.stackSize-1;
            return true;
        }

        return false;
    }
    
    @Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
    	if(this.itemID == TFCItems.FruitTreeSapling1.itemID)
    	{
    		for(int i = 0; i < 8; i++) 
    		{
    			list.add(new ItemStack(this,1,i));
    		}
    	}
    	else
    	{
    		list.add(new ItemStack(this,1,0));
    	}
	}
    
    Icon[] icons = new Icon[Names.length];
	@Override
	public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < Names.length; i++)
			registerer.func_94245_a("wood/fruit trees/"+Names[i]+" Fruit Sapling");
    }
    
    @Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}
    
    @Override
    public String getItemDisplayName(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(Names[itemstack.getItemDamage()+offset]).toString();
        return s;
    }

    public int getMetadata(int i)
    {
        return i;
    }
}
