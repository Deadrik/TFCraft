package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEWorldItem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWorldItem extends BlockTerraContainer
{
	public BlockWorldItem()
	{
		super(Material.wood);
		this.setBlockBounds(0.20F, 0.00F, 0.2F, 0.8F, 0.1F, 0.8F);
	}

	/*@Override
	public int getRenderType()
	{
		return TFCBlocks.looseRockRenderId;
	}*/

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int xCoord, int yCoord, int zCoord, int l)
	{		
		/*DataLayer rockLayer = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);

		ArrayList coreSample = new ArrayList<Item>();
		ArrayList coreSampleStacks = new ArrayList<ItemStack>();

		for(int x = -15; x <= 15; x++)
		{
			for(int z = -15; z <= 15; z++)
			{
				for(int y = yCoord; y > yCoord-35; y--)
				{
					if(world.getBlockId(xCoord+x, y, zCoord+z) == TFCBlocks.Ore.blockID)
					{
						int m = world.getBlockMetadata(xCoord+x, y, zCoord+z);
						if(!coreSample.contains(BlockOre.getDroppedItem(m)))
						{
							//coreSample.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC_Core.terraOre).damageDropped(meta)));
							if(m!= 14 && m != 15)
							{
								coreSample.add(BlockOre.getDroppedItem(m));
								coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
							}
						}
					}
				}
			}
		}
		Random R = new Random();

		if(!coreSampleStacks.isEmpty() && R.nextInt(3) == 0)
		{
			dropBlockAsItem_do(world, xCoord, yCoord, zCoord,(ItemStack)coreSampleStacks.toArray()[R.nextInt(coreSampleStacks.toArray().length)]);
		}
		else{
			dropBlockAsItem_do(world, xCoord, yCoord, zCoord, new ItemStack(TFCItems.LooseRock, 1, TFC_Core.getItemMetaFromStone(rockLayer.data1, rockLayer.data2)));
		}*/
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if (world.isAirBlock(i, j - 1, k))
		{
			world.setBlockToAir(i, j, k);
			return;
		}
		if (!world.getBlock(i, j - 1, k).isOpaqueCube())
		{
			world.setBlockToAir(i, j, k);
			return;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.25, k+1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = TFC_Textures.InvisibleTexture; // This gets registered in BlockGrass
	}

	@Override
	public TileEntity createTileEntity(World var1, int var2)
	{
		return new TEWorldItem();
	}
}
