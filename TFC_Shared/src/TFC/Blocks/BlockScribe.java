package TFC.Blocks;

import java.util.List;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityTerraScribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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
import net.minecraft.world.gen.feature.*;

public class BlockScribe extends BlockContainer
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;

	public BlockScribe(int i)
	{
		super(i, Material.wood);
		needsRandomTick = true;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
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
			if((TileEntityTerraScribe)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraScribe tileentityanvil;
				tileentityanvil = (TileEntityTerraScribe)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 22, world, i, j, k);
				//ModLoader.openGUI(entityplayer, new GuiTerraScribe(entityplayer.inventory, tileentityanvil, world));
			}
			return true;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 1) {
			return 88;
		}
		return 89;
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = "terraScribe";
		return s;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}
	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraScribe();
	}

}
