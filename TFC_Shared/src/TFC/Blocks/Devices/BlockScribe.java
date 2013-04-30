package TFC.Blocks.Devices;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityScribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockScribe extends BlockTerraContainer
{
	Icon iconTop;
	Icon iconSide;
	
	public BlockScribe(int i)
	{
		super(i, Material.wood);
		needsRandomTick = true;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(i, j, k);
		//Minecraft mc = ModLoader.getMinecraftInstance();
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else
		{
			if((TileEntityScribe)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityScribe tileentityanvil;
				tileentityanvil = (TileEntityScribe)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 22, world, i, j, k);
				//ModLoader.openGUI(entityplayer, new GuiTerraScribe(entityplayer.inventory, tileentityanvil, world));
			}
			return true;
		}
	}
	@Override
	public Icon getIcon(int i, int j)
	{
		if(i == 1) {
			return iconTop;
		}
		return iconSide;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		iconTop = iconRegisterer.registerIcon("devices/Scribing Table Top");
		iconSide = iconRegisterer.registerIcon("devices/Scribing Table Side");
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
		return new TileEntityScribe();
	}

}
