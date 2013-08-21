package TFC.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class BlockWorkbench extends BlockTerraContainer
{
	@SideOnly(Side.CLIENT)
	private Icon field_94385_a;
	@SideOnly(Side.CLIENT)
	private Icon field_94384_b;

	public BlockWorkbench(int i)
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 1, world, i, j, k);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.field_94385_a : (par1 == 0 ? TFCBlocks.Planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.field_94384_b));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("crafting_table_side");
		this.field_94385_a = par1IconRegister.registerIcon("crafting_table_top");
		this.field_94384_b = par1IconRegister.registerIcon("crafting_table_front");
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityWorkbench();
	}
}
