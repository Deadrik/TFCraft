package com.bioxx.tfc.Blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Tools.IToolChisel;

public class BlockPlanks extends BlockTerra
{
	protected String[] woodNames;
	protected IIcon[] icons;

	public BlockPlanks(Material material)
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
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
		for(int i = 0; i < woodNames.length; i++)
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
		if(j < 0)
			return icons[0];
		if(j<icons.length)
			return icons[j];
		return TFCBlocks.planks2.getIcon(i, j-16);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/"+woodNames[i]+" Plank");
		Blocks.planks.registerBlockIcons(registerer);
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		if(!world.isRemote && entityplayer.getCurrentEquippedItem() != null && 
				entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel && 
				hasHammer && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
		{
			Block block = world.getBlock(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, block, meta, side, par7, par8, par9);
		}
		return false;
	}

}
