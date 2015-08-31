package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class BlockCustomWall extends BlockWall
{
	private int totalsubTypes;
	private Block block;

	public BlockCustomWall(Block blk, int t)
	{
		super(blk);
		this.block = blk;
		totalsubTypes = t;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return block.getIcon(par1, par2);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) 
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			TerraFirmaCraft.LOG.info("Meta=" + (new StringBuilder()).append(this.getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.wallRenderId;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < totalsubTypes; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean flag0 = this.canConnectWallTo(world, x, y, z - 1);
        boolean flag1 = this.canConnectWallTo(world, x, y, z + 1);
        boolean flag2 = this.canConnectWallTo(world, x - 1, y, z);
        boolean flag3 = this.canConnectWallTo(world, x + 1, y, z);
        //The up flags. These check if there is an adjacent block above us that would raise the height of the wall
        boolean flag0Up = this.canConnectWallTo(world, x, y, z - 1);
        boolean flag1Up = this.canConnectWallTo(world, x, y, z + 1);
        boolean flag2Up = this.canConnectWallTo(world, x - 1, y, z);
        boolean flag3Up = this.canConnectWallTo(world, x + 1, y, z);
        float f = 0.25F;
        float f1 = 0.75F;
        float f2 = 0.25F;
        float f3 = 0.75F;
        float f4 = 1.0F;

        if (flag0)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        if (flag0 && flag1 && !flag2 && !flag3)
        {
        	if(!(flag0Up && flag1Up)){
        		f4 = 0.8125F;
        	}
            f = 0.3125F;
            f1 = 0.6875F;
        }
        else if (!flag0 && !flag1 && flag2 && flag3)
        {
        	if(!(flag2Up && flag3Up)){
        		f4 = 0.8125F;
        	}
            f2 = 0.3125F;
            f3 = 0.6875F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
    }

	@Override
	public boolean canConnectWallTo(IBlockAccess access, int i, int j, int k)
	{
		Block block = access.getBlock(i, j, k);
		if (block != this && block != Blocks.fence_gate && block != TFCBlocks.fenceGate && block != TFCBlocks.fenceGate2 && !(block instanceof BlockCustomWall))
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
		else
			return true;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
}
