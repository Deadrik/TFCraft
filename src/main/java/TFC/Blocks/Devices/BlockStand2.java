package TFC.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.IMultipleBlock;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemBarrels;
import TFC.TileEntities.TEStand;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStand2 extends BlockTerraContainer implements IMultipleBlock
{
	private final Random random = new Random();
	String[] woodNames;
	public BlockStand2()
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.125f, 0, 0.125f, 0.875f, 1, 0.875f);
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0,Global.WOOD_ALL.length - 16);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/BarrelHoop");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.InvisibleTexture;
		else
			return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		TileEntity te = world.getTileEntity(i,j,k);
		if(te != null && te instanceof TEStand)
		{
			TEStand tes = (TEStand) te;
			tes.yaw =(((int)((((entityliving.rotationYaw)%360)+360)+45)/90)*90)%360;
			if(tes.yaw % 180 == 0)
				tes.yaw+=180;
			world.setBlock(i,j+1,k,this);
			world.setBlockMetadataWithNotify(i, j+1, k, is.getItemDamage(),0);
			world.setTileEntity(i, j+1, k, new TEStand());
			((TEStand)world.getTileEntity(i, j+1, k)).isTop = true;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.standRenderId;
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
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	/*@Override
	public Icon getBlockTextureFromSideAndMetadata(int par1)
	{
		return par1 == 1 ? this.blockIndexInTexture - 1 : (par1 == 0 ? this.blockIndexInTexture - 1 : (par1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}*/

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
		for(int i = 0; i < ((ItemBarrels)(TFCItems.Barrel)).MetaNames.length;i++)
			j = s.substring(s.indexOf("l",s.length()))==((ItemBarrels)(TFCItems.Barrel)).MetaNames[i]?i:0;
		return new ItemStack(TFCItems.Barrel, 1, j);
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		TEStand var5 = null;
		TileEntity te = par1World.getTileEntity(par2, par3, par4);
		if(te != null && te instanceof TEStand)
		{
			var5 = (TEStand) te;
			if(var5.isTop)
			{
				breakBlock(par1World,par2,par3-1,par4,par5,par6);
				par1World.setBlockToAir(par2,par3-1,par4);
				return;
			}
		}
		else
		{
			return; //if TE does not exist, assume this is the top block being destroyed by the bottom
		}

		if(par1World.getTileEntity(par2, par3+1, par4) !=null)((TEStand)par1World.getTileEntity(par2, par3+1, par4)).destroy();
		par1World.setBlockToAir(par2,par3+1,par4);
		var5.destroy();
		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);
				if (var7 != null)
				{
					float var8 = this.random.nextFloat() * 0.8F + 0.1F;
					float var9 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = this.random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; par1World.spawnEntityInWorld(var12))
					{
						int var11 = this.random.nextInt(21) + 10;
						if (var11 > var7.stackSize)
							var11 = var7.stackSize;
						var7.stackSize -= var11;
						var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)this.random.nextGaussian() * var13;
						var12.motionY = (float)this.random.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)this.random.nextGaussian() * var13;
						if (var7.hasTagCompound())
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
					}
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Random R = new Random();
		//dropBlockAsItem(world, i, j, k, new ItemStack(idDropped(0,R,l), 1, l+13));

		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		TEStand TeStand = null;
		TileEntity te = world.getTileEntity(x, y, z);
		
		if (world.isRemote)
		{
			//((NetworkTileEntity)world.getTileEntity(x,y,z)).validate();
			if(te != null && te instanceof TEStand)
			{
				TeStand = (TEStand) te;
				if(TeStand.isTop)
					return onBlockActivated(world,x,y-1,z,entityplayer,side,hitX,hitY,hitZ);
				ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];
				if(item!=null && item.getItem() instanceof ItemArmor && TeStand.items[4-((ItemArmor)item.getItem()).armorType] == null)
				{
					TeStand.setInventorySlotContents(4-((ItemArmor)item.getItem()).armorType,item.copy());
				}
				else if(item==null && TeStand.highlightedSlot != -1)
				{
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = TeStand.items[TeStand.highlightedSlot];
					TeStand.setInventorySlotContents(TeStand.highlightedSlot, null);
					TeStand.highlightedSlot = -1;
				}
				return true;
			}
		}
		else
		{
			if(te != null && te instanceof TEStand)
			{
				TeStand = (TEStand) te;
				if(TeStand.isTop)
					return onBlockActivated(world,x,y-1,z,entityplayer,side,hitX,hitY,hitZ);
				ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];
				if(item!=null && item.getItem() instanceof ItemArmor && TeStand.items[4-((ItemArmor)item.getItem()).armorType] == null)
				{
					TeStand.setInventorySlotContents(4-((ItemArmor)item.getItem()).armorType,item.copy());
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize--;
				}
				else if(item==null && TeStand.highlightedSlot != -1)
				{
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = TeStand.items[TeStand.highlightedSlot];
					TeStand.setInventorySlotContents(TeStand.highlightedSlot, null);
					TeStand.highlightedSlot = -1;
				}
				//entityplayer.openGui(TerraFirmaCraft.instance, 35, world, x, y, z);
				return true;
			}
		}
		return false;
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
		return new TEStand();
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
	
	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.Planks2;
	}
}
