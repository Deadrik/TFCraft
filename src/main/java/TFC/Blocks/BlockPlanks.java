package TFC.Blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import TFC.API.Tools.IToolChisel;
import TFC.API.Util.Helper;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlanks extends BlockTerra
{
	String[] woodNames;
	IIcon[] icons;

	public BlockPlanks(Material material)
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		icons = new IIcon[woodNames.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 16; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if(j<icons.length)
			return icons[j];
		return TFCBlocks.Planks2.getIcon(i, j-16);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+woodNames[i]+" Plank");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		if(!world.isRemote && entityplayer.getCurrentEquippedItem() != null && 
				entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel && 
				hasHammer && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null)
				return false;
			int side = objectMouseOver.sideHit;

			Block block = world.getBlock(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, block, meta, side, par7, par8, par9);
		}
		return false;
	}

}
