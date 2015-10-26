package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class BlockOre extends BlockCollapsible
{
	public String[] blockNames = Global.ORE_METAL;

	public BlockOre(Material mat)
	{
		super(mat);
		this.setTickRandomly(true);
		this.setCreativeTab(null);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			if(te != null)
				TerraFirmaCraft.LOG.info("Ore  BaseID = " + te.baseBlockID + "| BaseMeta =" + te.baseBlockMeta);
		}
		return false;
	}

	@Override
	public int[] getDropBlock(World world, int x, int y, int z)
	{
		int[] data = new int[]{ -1, -1 };
		DataLayer dl = TFC_Climate.getCacheManager(world).getRockLayerAt(x, z, TFC_Core.getRockLayerFromHeight(world, x, y, z));

		if(dl != null)
		{
			BlockStone stone = null;
			if (dl.block instanceof BlockStone)
				stone = (BlockStone) dl.block;

			if (stone != null)
			{
				data[0] = Block.getIdFromBlock(stone.dropBlock); // Cobblestone version of rock in that layer.
				data[1] = dl.data2; // Metadata
			}
		}
		return data;
	}

	@Override
	public int damageDropped(int dmg)
	{
		if (dmg == 14 || dmg == 15) // coal
			return 0;
		return dmg;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		if (meta == 14 || meta == 15) // coal
			return 1 + random.nextInt(2);
		return 1;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= icons.length)
			return icons[0];
		return icons[meta];
	}

	protected IIcon[] icons = new IIcon[blockNames.length];

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < blockNames.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "ores/"+ blockNames[i] + " Ore");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.oreRenderId;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			boolean dropOres = false;
			boolean hasHammer = false;
			int meta = world.getBlockMetadata(x, y, z);
			boolean isCoal = meta == 14 || meta == 15;
			ItemStack itemstack = null;
			if(player != null)
			{
				TFC_Core.addPlayerExhaustion(player, 0.001f);
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
				dropOres = player.canHarvestBlock(this);
				ItemStack heldItem = player.getCurrentEquippedItem();
				if (heldItem != null)
				{
					int[] itemIDs = OreDictionary.getOreIDs(heldItem);
					for (int id : itemIDs)
					{
						String name = OreDictionary.getOreName(id);
						if (name.startsWith("itemHammer"))
						{
							hasHammer = true;
							break;
						}
					}
				}
			}

			if (player == null || dropOres)
			{
				if (isCoal)
					itemstack = new ItemStack(TFCItems.coal, 1 + world.rand.nextInt(2));
				else
				{
					TEOre te = (TEOre) world.getTileEntity(x, y, z);
					int ore = getOreGrade(te, meta);
					itemstack = new ItemStack(TFCItems.oreChunk, 1, damageDropped(ore));
				}
			}
			else if (hasHammer && !isCoal)
				itemstack = new ItemStack(TFCItems.smallOreChunk, 1, meta);

			if (itemstack != null)
				dropBlockAsItem(world, x, y, z, itemstack);
		}
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		//Intentionally empty so that mining ore blocks cannot trigger a cave in.
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TEOre te = (TEOre) world.getTileEntity(x, y, z);
		int ore = getOreGrade(te, metadata);

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack;
			if (metadata == 14 || metadata == 15)
				itemstack = new ItemStack(TFCItems.coal);
			else
				itemstack = new ItemStack(TFCItems.oreChunk, 1, damageDropped(ore));

			ret.add(itemstack);
		}
		return ret;
	}

	public static Item getDroppedItem(int meta)
	{
		if(meta == 14 || meta == 15)
			return TFCItems.coal;
		else
			return TFCItems.smallOreChunk;
	}

	@Override
	public boolean canDropFromExplosion(Explosion exp)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		world.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
	{
		if(!world.isRemote)
		{
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			Random random = new Random();
			ItemStack itemstack;
			int meta = world.getBlockMetadata(x, y, z);
			int ore = getOreGrade(te, meta);

			if(meta == 14 || meta == 15)
				itemstack = new ItemStack(TFCItems.coal, 1 + random.nextInt(2));
			else
				itemstack = new ItemStack(TFCItems.oreChunk, 1, ore);

			dropBlockAsItem(world, x, y, z, itemstack);
			onBlockDestroyedByExplosion(world, x, y, z, exp);
		}
	}

	public int getOreGrade(TEOre te, int ore)
	{
		if(te != null)
		{
			int grade = te.extraData & 7;
			if(grade == 1)
				ore += 35;
			else if(grade == 2)
				ore += 49;
		}
		return ore;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public TileEntity createTileEntity(World w, int meta)
	{
		return new TEOre();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
			scanVisible(world, x, y, z);
	}

	public void scanVisible(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			if((te.extraData & 8) == 0 && y < 255 && y > 0)
			{
				if(world.blockExists(x, y-1, z) && world.blockExists(x, y+1, z) && world.blockExists(x-1, y, z) && world.blockExists(x+1, y, z) &&
						world.blockExists(x, y, z-1) && world.blockExists(x, y, z+1))
					if(!world.getBlock(x, y - 1, z).isOpaqueCube() || !world.getBlock(x, y + 1, z).isOpaqueCube() ||
							!world.getBlock(x - 1, y, z).isOpaqueCube() || !world.getBlock(x + 1, y, z).isOpaqueCube() || 
							!world.getBlock(x, y, z - 1).isOpaqueCube() || !world.getBlock(x, y, z + 1).isOpaqueCube())
					{
						te.setVisible();
					}
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!world.isRemote)
		{
			scanVisible(world, x, y, z);
		}
	}
}
