package TFC.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.IMultipleBlock;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemBarrels;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TEStand;
import TFC.TileEntities.TileEntityBarrel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStand extends BlockTerraContainer implements IMultipleBlock
{
	private final Random random = new Random();
	String[] woodNames;
	public BlockStand(int par1)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.125f, 0, 0.125f, 0.875f, 1, 0.875f);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/BarrelHoop");
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1) 
		{
			return TFC_Textures.InvisibleTexture;
		} 
		else 
		{
			return this.blockIcon;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		TEStand te = ((TEStand)world.getBlockTileEntity(i,j,k));
		te.yaw =(((int)((((entityliving.rotationYaw)%360)+360)+45)/90)*90)%360;
		if(te.yaw % 180 == 0){
			te.yaw+=180;
		}
		world.setBlock(i,j+1,k,this.blockID);
		world.setBlockMetadataWithNotify(i, j+1, k, is.getItemDamage(),0);
		world.setBlockTileEntity(i, j+1, k, new TEStand());
		((TEStand)world.getBlockTileEntity(i, j+1, k)).isTop = true;
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
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {


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
		return par1World.isBlockOpaqueCube(par2, par3-1, par4) && par1World.isAirBlock(par2, par3, par4)  && par1World.isAirBlock(par2, par3+1, par4);
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		int j = 0;
		String s = this.getUnlocalizedName();
		for(int i = 0; i < ((ItemBarrels)(TFCItems.Barrel)).MetaNames.length;i++){
			j = s.substring(s.indexOf("l",s.length()))==((ItemBarrels)(TFCItems.Barrel)).MetaNames[i]?i:0;
		}


		return new ItemStack(TFCItems.Barrel, 1, j);
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TEStand var5 = (TEStand)par1World.getBlockTileEntity(par2, par3, par4);
		if(var5 != null){
			if(var5.isTop){
				breakBlock(par1World,par2,par3-1,par4,par5,par6);
				par1World.setBlock(par2,par3-1,par4,0);
				return;
			}				
		}
		else{
			return;					//if TE does not exist, assume this is the top block being destroyed by the bottom
		}
		if(par1World.getBlockTileEntity(par2, par3+1, par4) !=null)((TEStand)par1World.getBlockTileEntity(par2, par3+1, par4)).destroy();
		par1World.setBlock(par2,par3+1,par4,0);
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Random R = new Random();
		//dropBlockAsItem_do(world, i, j, k, new ItemStack(idDropped(0,R,l), 1, l+13));

		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			//((NetworkTileEntity)world.getBlockTileEntity(x,y,z)).validate();
			TEStand TeStand = (TEStand)(world.getBlockTileEntity(x, y, z));
			if(TeStand.isTop){
				return onBlockActivated(world,x,y-1,z,entityplayer,side,hitX,hitY,hitZ);
			}
			ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];
			if(item!=null && item.getItem() instanceof ItemArmor && TeStand.items[4-((ItemArmor)item.getItem()).armorType] == null){
				TeStand.setInventorySlotContents(4-((ItemArmor)item.getItem()).armorType,item.copy());					
			}
			else if(item==null && TeStand.highlightedSlot != -1){
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = TeStand.items[TeStand.highlightedSlot];
				TeStand.setInventorySlotContents(TeStand.highlightedSlot, null);
				TeStand.highlightedSlot = -1;
			}
			return true;
		}
		else
		{

			if(world.getBlockTileEntity(x, y, z) != null){
				TEStand TeStand = (TEStand)(world.getBlockTileEntity(x, y, z));
				if(TeStand.isTop){
					return onBlockActivated(world,x,y-1,z,entityplayer,side,hitX,hitY,hitZ);
				}
				ItemStack item = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];
				if(item!=null && item.getItem() instanceof ItemArmor && TeStand.items[4-((ItemArmor)item.getItem()).armorType] == null){
					TeStand.setInventorySlotContents(4-((ItemArmor)item.getItem()).armorType,item.copy());
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize--;
				}
				else if(item==null && TeStand.highlightedSlot != -1){
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
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TEStand();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}
	
	@Override
	public Block getBlockTypeForRender() {
		return TFCBlocks.Planks;
	}
}
