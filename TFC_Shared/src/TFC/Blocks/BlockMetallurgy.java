package TFC.Blocks;

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
import TFC.TileEntities.TileEntityMetallurgy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetallurgy extends BlockTerraContainer
{
	Icon iconTop;
	Icon iconSide;
	
	public BlockMetallurgy(int i)
	{
		super(i, Material.rock);
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
			if((TileEntityMetallurgy)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityMetallurgy tileentityanvil;
				tileentityanvil = (TileEntityMetallurgy)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 24, world, i, j, k);
				//ModLoader.openGUI(entityplayer, new GuiTerraMetallurgy(entityplayer.inventory, tileentityanvil, world));
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
		iconTop = iconRegisterer.registerIcon("devices/Metallurgy Table Top");
		iconSide = iconRegisterer.registerIcon("devices/Metallurgy Table Side");
    }


	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityMetallurgy();
	}
}
