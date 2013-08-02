package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.TileEntities.TileEntityBloom;

public class BlockBloom extends BlockTerraContainer
{
	
	public BlockBloom(int par1) 
	{
		super(par1, Material.iron);
		//this.setCreativeTab(CreativeTabs.tabDecorations);
		//this.setLightValue(1F);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.RawBloom, 1,l));
	}

	/*@Override
    public Icon getIcon(int i, int j) 
    {
		if(i < 2)
			return iconTop;
		
		return icons[j];
    }*/
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBloom();
	}
}
