package com.bioxx.tfc.Blocks.Flora;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.Terrain.BlockSand;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.TileEntities.TEWaterPlant;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterPlant extends BlockSand implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon pondWeed;
	private IIcon seaWeed;
	private IIcon catTails;

	static String seaweed = Reference.ModID + ":" + "plants/seaweed";
	static String pondweed = Reference.ModID + ":" + "plants/pondweed";
	static String cattails = Reference.ModID + ":" + "plants/Cat Tails";


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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		if(!TFC_Core.isSaltWater(world.getBlock(x, y+1, z)))
			return super.getDrops(world, x, y, z, metadata, fortune);

		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(getSeaWeed(world.rand));
		TEWaterPlant te = (TEWaterPlant)world.getTileEntity(x, y, z);
		ret.add(new ItemStack(te.getBlockType(),1, metadata));
		return ret;
	}

	/* Left-Click Harvest Berries */
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (TFC_Core.isSaltWater(world.getBlock(x, y+1, z)) && entityplayer.inventory.getCurrentItem() != null && 
					entityplayer.inventory.getCurrentItem().getItem() instanceof ItemKnife)
			{
				dropBlockAsItem(world, x, y, z, getSeaWeed(world.rand));
				doBeforeFall(world, x, y, z);
			}
		}
	}

	private ItemStack getSeaWeed(Random r)
	{
		return ItemFoodTFC.createTag(new ItemStack(TFCItems.SeaWeed, 1, 0), Helper.roundNumber(2 + r.nextFloat() * 5, 10));
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

	@Override
	protected void doBeforeFall(World world, int x, int y, int z){
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TEWaterPlant){
			Block block = ((TEWaterPlant)te).getBlockFromType();
			int meta = world.getBlockMetadata(x,y,z);
			if(block != null){
				world.setBlock(x, y, z, block, meta,0);
			}
			else{
				world.setBlockToAir(x,y,z);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		seaWeed = registerer.registerIcon(seaweed);//registerer.registerIcon(Reference.ModID + ":" + "plants/Sea Grass");
		pondWeed = registerer.registerIcon(pondweed);//registerer.registerIcon(Reference.ModID + ":" + "plants/Pond Grass");
		catTails = registerer.registerIcon(cattails);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		switch(par2){
		case 0: return seaWeed;
		case 1: return pondWeed;
		case 2: return catTails;
		default: return catTails;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEWaterPlant();
	}
}
