package TFC.Blocks.Devices;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityScribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockScribe extends BlockTerraContainer
{
	IIcon iconTop;
	IIcon iconSide;

	public BlockScribe()
	{
		super(Material.wood);
		needsRandomTick = true;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity te = world.getTileEntity(i, j, k);
			if(te != null && te instanceof TileEntityScribe)
			{
				TileEntityScribe tes;
				tes = (TileEntityScribe) te;
				entityplayer.openGui(TerraFirmaCraft.instance, 22, world, i, j, k);
				return true;
			}
			return false;
		}
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if(i == 1)
			return iconTop;
		return iconSide;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		iconTop = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Scribing Table Top");
		iconSide = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Scribing Table Side");
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
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityScribe();
	}

}
