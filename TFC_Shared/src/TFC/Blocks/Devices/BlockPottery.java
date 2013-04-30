package TFC.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityForge;

public class BlockPottery extends BlockTerraContainer
{
	public Icon Clay;
	public Icon Ceramic;
	
	public BlockPottery(int i)
	{
		super(i, Material.iron);
		this.setLightValue(1.0F);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		this.Clay = iconRegisterer.registerIcon("devices/ClayPottery");
		this.Ceramic = iconRegisterer.registerIcon("devices/CeramicPottery");
    }

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}
	
	@Override
	public int getRenderType()
	{
		return TFCBlocks.clayPotteryRenderId;
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
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityForge();
	}
}
