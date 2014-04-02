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
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Constant.Global;
import TFC.Core.TFC_Core;
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		if(entityplayer != null)
		{
			entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			entityplayer.addExhaustion(0.075F);
		}
		Random random = new Random();
		ItemStack itemstack;
		if(l == 14 || l == 15)
			itemstack  = new ItemStack(Items.coal,1+random.nextInt(2));
		else
			itemstack  = new ItemStack(TFCItems.OreChunk, 1, damageDropped(l));

		if (itemstack != null)
			//if(random.nextInt(4) == 0)
			dropBlockAsItem(world, i, j, k, itemstack);

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
	public void onBlockExploded(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
		Random random = new Random();
		ItemStack itemstack;
		int meta = par1World.getBlockMetadata(par2, par3, par4);

		if(meta == 14 || meta == 15)
			itemstack  = new ItemStack(Items.coal,1+random.nextInt(2));
		else
			itemstack  = new ItemStack(TFCItems.OreChunk, 1, meta);
		if (itemstack != null)
			dropBlockAsItem(par1World, par2, par3, par4, itemstack);
		onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
