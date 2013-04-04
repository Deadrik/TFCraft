package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TileEntities.TileEntitySpawnMeter;

public class BlockSpawnMeter extends BlockTerraContainer
{
	Icon iconTop;
	Icon[] icons = new Icon[9];
	
	public BlockSpawnMeter(int par1) 
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setLightValue(1F);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
		int meta = world.getBlockMetadata(x, y, z);
        return Math.min(meta, 8);
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
    public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
    {
		if(i < 2)
			return iconTop;
		
		return icons[j];
    }
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		iconTop = iconRegisterer.registerIcon("devices/meterTop");
		for(int i = 0; i < 9; i++)
		{
			icons[i] = iconRegisterer.registerIcon("devices/meter"+i);
		}
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySpawnMeter();
	}
}
