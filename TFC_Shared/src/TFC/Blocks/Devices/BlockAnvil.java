package TFC.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.API.Crafting.AnvilReq;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityAnvil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnvil extends BlockTerraContainer
{
	private int anvilId = 0;


	private Random random = new Random();

	public BlockAnvil(int i)
	{
		super(i, Material.iron);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public BlockAnvil(int i, int offset)
	{
		this(i);
		anvilId = offset;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		if(this.blockID == TFCBlocks.Anvil.blockID) {
			for(int i = 1; i < 8; i++) {
				par3List.add(new ItemStack(this, 1, i));
			}
		}
		if(this.blockID == TFCBlocks.Anvil2.blockID) {
			for(int i = 0; i < 3; i++) {
				par3List.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else
		{
			if((TileEntityAnvil)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityAnvil tileentityanvil;
				tileentityanvil = (TileEntityAnvil)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 21, world, i, j, k);
			}
			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);

		if (te != null && te instanceof TileEntityAnvil)
		{
			TileEntityAnvil teAnvil = (TileEntityAnvil) te;
			if (teAnvil.AnvilTier != AnvilReq.STONE.Tier
					|| this.blockID == TFCBlocks.Anvil2.blockID)
			{
				if(direction == 0) {
					return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
				} else {
					return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
				}
			}
			else
			{
				return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
			}
		}
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntityAnvil te = (TileEntityAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0) {
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
				return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
			}
			else {
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
				return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
			}
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 0.9F, (double)par4 + 1);
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntityAnvil te = (TileEntityAnvil)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0) {
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
			}
			else {
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
			}
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
		}
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);

		if(i == 0 || i == 1) 
		{
			return textureMapTop[meta];
		} 
		else 
		{
			return textureMapSide[meta];
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.AnvilRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		int type = BlockAnvil.getAnvilTypeFromMeta(l);

		if(blockID == TFCBlocks.Anvil.blockID)
		{
			if(type == 0) {
				return;
			}
		}
		super.harvestBlock(world, entityplayer, i, j, k, type);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int x, int y, int z, int meta, float par6, int par7)
	{
		if (!par1World.isRemote)
		{
			this.dropBlockAsItem_do(par1World, x, y, z, new ItemStack(this, 1, meta));
		}
	}

	@Override
	protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack is)
	{
		if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			if(is.getItemDamage() == 0 && this.blockID == TFCBlocks.Anvil.blockID)
			{
				is.setItemDamage(1);
			}
			float f = 0.7F;
			double d0 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(par1World, par2 + d0, par3 + d1, par4 + d2, is);
			entityitem.delayBeforeCanPickup = 10;
			par1World.spawnEntityInWorld(entityitem);
		}
	}


	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
		{
			byte0 = 8;
		}
		if(l == 1)//-x
		{
			byte0 = 0;
		}
		if(l == 2)//-z
		{
			byte0 = 8;
		}
		if(l == 3)//+x
		{
			byte0 = 0;
		}
		byte0 += meta;

		world.setBlockMetadataWithNotify(i, j, k, byte0, 3);

		TileEntityAnvil te = (TileEntityAnvil)world.getBlockTileEntity(i, j, k);
		if(blockID == TFCBlocks.Anvil.blockID) {
			te.AnvilTier = AnvilReq.getReqFromInt(meta).Tier;
		} else if(blockID == TFCBlocks.Anvil2.blockID) {
			te.AnvilTier = AnvilReq.getReqFromInt2(meta).Tier;
		}


	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityAnvil var5 = (TileEntityAnvil)par1World.getBlockTileEntity(par2, par3, par4);

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
						{
							var11 = var7.stackSize;
						}

						var7.stackSize -= var11;
						var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.itemID, var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)this.random.nextGaussian() * var13;
						var12.motionY = (float)this.random.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)this.random.nextGaussian() * var13;

						if (var7.hasTagCompound())
						{
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
		}



		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public static int getAnvilTypeFromMeta(int j)
	{
		int l = 7;
		int k = j & l;
		return k;
	}

	public static int getDirectionFromMetadata(int i)
	{
		int d = i >> 3;

			if (d == 1) {
				return 1;
			} else {
				return 0;
			}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityAnvil();
	}

	Icon[] textureMapTop;
	Icon[] textureMapSide;

	@Override
	public void registerIcons(IconRegister registerer)
	{
		textureMapTop = new Icon[anvilId == 0 ? 8 : 3];
		textureMapSide = new Icon[anvilId == 0 ? 8 : 3];
		for(int i = (anvilId == 0 ? 1 : 0); i < (anvilId == 0 ? 8 : 3); i++)
		{
			textureMapTop[i] = registerer.registerIcon(Reference.ModID + ":" + "devices/Anvil_" + (i+anvilId) + "_Top");
			textureMapSide[i] = registerer.registerIcon(Reference.ModID + ":" + "devices/Anvil_" + (i+anvilId) + "_Side");
		}

		TFC_Textures.AnvilHit = registerer.registerIcon(Reference.ModID + ":" + "Anvil Hit");
		TFC_Textures.AnvilHitHeavy = registerer.registerIcon(Reference.ModID + ":" + "Anvil Hit Heavy");
		TFC_Textures.AnvilHitMedium = registerer.registerIcon(Reference.ModID + ":" + "Anvil Hit Medium");
		TFC_Textures.AnvilHitLight = registerer.registerIcon(Reference.ModID + ":" + "Anvil Hit Light");
		TFC_Textures.AnvilDraw = registerer.registerIcon(Reference.ModID + ":" + "Anvil Draw");
		TFC_Textures.AnvilPunch = registerer.registerIcon(Reference.ModID + ":" + "Anvil Punch");
		TFC_Textures.AnvilBend = registerer.registerIcon(Reference.ModID + ":" + "Anvil Bend");
		TFC_Textures.AnvilUpset = registerer.registerIcon(Reference.ModID + ":" + "Anvil Upset");
		TFC_Textures.AnvilShrink = registerer.registerIcon(Reference.ModID + ":" + "Anvil Shrink");
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
