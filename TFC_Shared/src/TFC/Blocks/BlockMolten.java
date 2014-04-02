package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
public class BlockMolten extends BlockTerra
{
	Icon moltenOff;
	public BlockMolten(int i)
	{
		super(i, Material.iron);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int _meta = world.getBlockMetadata(x, y, z);
		if((_meta & 8) > 0) {
			return 15;
		}
		return 0;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		int meta = access.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0f, 1f, f+(f*meta), 1F);
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		if((meta & 8) == 0) {
			return this.moltenOff;
		}
		return this.blockIcon;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Molten Rock");
		moltenOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Molten Rock Off");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}	

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

}
