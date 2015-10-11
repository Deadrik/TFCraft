package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemBlocks.ItemBarrels;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockLoom extends BlockTerraContainer
{
	private String[] woodNames;

	public BlockLoom()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);
		woodNames = Global.WOOD_ALL;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/BarrelHoop");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		//When doing inventory item render, we increase the side by 10 so that we can draw the hoops instead of planks
		if(side >= 10)
		{
			side-=10;
			if(side == 0 || side == 1)
				return TFC_Textures.invisibleTexture;
			else
				return blockIcon;
		}
		if(meta<16)
			return TFCBlocks.planks.getIcon(side, meta);
		return TFCBlocks.planks2.getIcon(side, meta-16);


	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.invisibleTexture;
		else
			return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.loomRenderId;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase player, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, player, is);
		
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		int j = 0;
		String s = this.getUnlocalizedName();
		for(int i = 0; i < woodNames.length;i++)
			j = s.substring(s.indexOf('l', s.length())) == ((ItemBarrels) (TFCItems.loom)).metaNames[i] ? i : 0;
		return new ItemStack(TFCItems.loom, 1, j);
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if (world.getTileEntity(x, y, z) instanceof TELoom)
		{
			TELoom te = (TELoom) world.getTileEntity(x, y, z);

			ItemStack is = new ItemStack(Item.getItemFromBlock(block), 1, te.loomType);
			NBTTagCompound nbt = writeLoomToNBT(te);
			is.setTagCompound(nbt);
			EntityItem ei = new EntityItem(world,x,y,z,is);
			world.spawnEntityInWorld(ei);
			te.dropItem();
			for(int s = 0; s < te.getSizeInventory(); ++s)
				te.setInventorySlotContents(s, null);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
	}

	public NBTTagCompound writeLoomToNBT(TELoom te)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("loomType", te.loomType);

		return nbt;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TELoom)
			{
				TELoom loomTE = (TELoom)te;
				//player.addChatMessage(new ChatComponentText(loomTE.rotation + ""));
				if(!loomTE.isFinished()){
					if(!loomTE.canWeave()){
						loomTE.addString(player.getCurrentEquippedItem());
					}
					else if (player.isSneaking())
					{
						loomTE.setIsWeaving(true);
					}
				}
				else if (!player.isSneaking()){

					ItemStack item = loomTE.takeFinishedCloth();
					if(item != null){
						EntityItem entityitem;
						entityitem = new EntityItem(world,player.posX,player.posY,player.posZ,item);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			if (player.isSneaking())
			{
				return true;
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TELoom();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}
	
    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
	public int getDamageValue(World world, int x, int y, int z)
    {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TELoom)
			return ((TELoom)te).loomType;
		return 0;
    }

    /**
     * This returns a complete list of items dropped from this block.
     */
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		
		int damageValue = getDamageValue(world, x, y, z);
		ret.add(new ItemStack(this, 1, damageValue));
		
		return ret;
	}
}
