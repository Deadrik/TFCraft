package com.bioxx.tfc.Blocks.Flora;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.Terrain.BlockSand;
import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEWaterPlant;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterPlant extends BlockSand implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon pondWeed;
	private IIcon seaWeed;

	public BlockWaterPlant(int texOff)
	{
		super(texOff);
		float var3 = 0.5F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.waterPlantRenderId;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random R)
	{
		return 1 + R.nextInt(i * 2 + 1);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int l)
	{
		TEWaterPlant te = (TEWaterPlant)(world.getTileEntity(i, j, k));
		Block type = null;
		if(te != null)
			type = te.getBlockFromType();

		super.breakBlock(world, i, j, k, block, l);
		
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
	{
		super.harvestBlock(world, player, i, j, k, l);
	}

	protected boolean canThisPlantGrowUnderThisBlock(Block par1)
	{
		return true;//TFC_Core.isSaltWater(par1)|| TFC_Core.isFreshWater(par1);
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		seaWeed = registerer.registerIcon("tallgrass");//registerer.registerIcon(Reference.ModID + ":" + "plants/Sea Grass");
		pondWeed = registerer.registerIcon("fern");//registerer.registerIcon(Reference.ModID + ":" + "plants/Pond Grass");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return TFCBlocks.Dirt.getIcon(par1, par2);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEWaterPlant();
	}
}
