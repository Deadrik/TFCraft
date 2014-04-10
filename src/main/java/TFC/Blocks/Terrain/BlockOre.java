package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TEOre;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;

public class BlockOre extends BlockCollapsable
{
	public String[] blockNames = Global.ORE_METAL;

	public BlockOre(Material material)
	{
		super(material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			if(te != null)
				System.out.println("Ore  BaseID = " + te.baseBlockID + "| BaseMeta =" + te.baseBlockMeta);
		}
		return false;
	}

	@Override
	public int[] getDropBlock(World world, int i, int j, int k)
	{
		int[] data = new int[2];
		DataLayer dl =((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(i, k, TFC_Core.getRockLayerFromHeight(world,i,j,k));
		if(dl != null)
		{
			data[0] = Block.getIdFromBlock(this.dropBlock);
			data[1] = dl.data2;
		}
		data[0] = -1;
		data[1] = -1;
		return data;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < blockNames.length; i++)
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
		return icons[j];
	}

	protected IIcon[] icons = new IIcon[blockNames.length];

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < blockNames.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "ores/"+ blockNames[i] + " Ore");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.oreRenderId;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int i, int j, int k)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			TEOre te = (TEOre)world.getTileEntity(i, j, k);
			int ore = getOreGrade(te, meta);
			if(player != null)
			{
				TFC_Core.addPlayerExhaustion(player, 0.001f);
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			}
			Random random = new Random();
			ItemStack itemstack;
			if(meta == 14 || meta == 15)
				itemstack  = new ItemStack(Items.coal,1+random.nextInt(2));
			else
				itemstack  = new ItemStack(TFCItems.OreChunk, 1, damageDropped(ore));

			if (itemstack != null)
				dropBlockAsItem(world, i, j, k, itemstack);
		}
		return world.setBlockToAir(i, j, k);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.OreChunk;
	}

	public static Item getDroppedItem(int meta)
	{
		if(meta == 14 || meta == 15)
			return Items.coal;
		else
			return TFCItems.SmallOreChunk;
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
		par1World.setBlockToAir(par2, par3, par4);
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion exp) 
	{
		if(!world.isRemote)
		{
			TEOre te = (TEOre)world.getTileEntity(i, j, k);
			Random random = new Random();
			ItemStack itemstack;
			int meta = world.getBlockMetadata(i, j, k);
			int ore = getOreGrade(te, meta);

			if(meta == 14 || meta == 15)
				itemstack = new ItemStack(Items.coal, 1 + random.nextInt(2));
			else
				itemstack = new ItemStack(TFCItems.OreChunk, 1, ore);
			if (itemstack != null)
				dropBlockAsItem(world, i, j, k, itemstack);
			onBlockDestroyedByExplosion(world, i, j, k, exp);
		}
	}

	private int getOreGrade(TEOre te, int ore)
	{
		if(te != null)
		{
			if(te.grade == 1)
				ore += 35;
			else if(te.grade == 2)
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
	public TileEntity createTileEntity(World var1, int var2)
	{
		return new TEOre();
	}

}
