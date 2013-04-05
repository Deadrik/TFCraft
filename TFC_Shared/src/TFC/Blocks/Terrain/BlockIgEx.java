package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Items.ItemChisel;
import TFC.Items.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIgEx extends BlockCollapsable
{
	public BlockIgEx(int i, Material material, int id) {
		super(i, material, id);
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return icons[j];
	}

	public static Icon[] icons = new Icon[4];
	protected String[] names = {"Rhyolite", "Basalt", "Andesite", "Dacite"};

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 0; i < 4; i++)
		{
			icons[i] = iconRegisterer.registerIcon("rocks/"+names[i]+" Raw");
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{	
		Random R = new Random();
		if(R.nextBoolean())
			dropBlockAsItem_do(world, i, j, k, new ItemStack(idDropped(0,R,l), 1+R.nextInt(4), l+13));

		super.harvestBlock(world, entityplayer, i, j, k, l);
	}



	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.LooseRock.itemID;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
			//super.onBlockDestroyedByExplosion(world, i, j, k);
			Random random = new Random();

			ItemStack is = null;

			is = TFC_Core.RandomGem(random, 0);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
		if(!world.isRemote)
		{
			Random random = new Random();

			ItemStack is = null;

			is = TFC_Core.RandomGem(random, 0);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		DropCarvedStone(world, i, j, k);
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null) {
				return false;
			}       
			int side = objectMouseOver.sideHit;

			int id = world.getBlockId(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			return ItemChisel.handleActivation(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < 4; i++)
			par3List.add(new ItemStack(this, 1, i));
	}
}
