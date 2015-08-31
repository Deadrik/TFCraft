package com.bioxx.tfc.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockMetalTrapDoor extends BlockTerraContainer
{
	public IIcon[] icons;
	public static String[] metalNames = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze",
			"Copper","Gold","Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver","Steel",
			"Sterling Silver","Tin","Zinc"/*,"Unknown"*/}; // There is no trapdoor anvil recipe involving unknown ingots.

	public BlockMetalTrapDoor()
	{
		super(Material.iron);
		//float f = 0.5F;
		//float f1 = 1.0F;
		this.setBlockBounds(0, 0, 0, 0.001f, 0.001f, 0.001f);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.metalTrapDoorRenderId;
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for (int i = 0; i < metalNames.length; i++)
		{
			// Only add trap doors where both pieces are the same metal to reduce clutter from the creative menu and NEI. Other combinations can still be created.
			list.add(new ItemStack(this, 1, i + (i << 5)));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEMetalTrapDoor();
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return !isTrapdoorOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		if(access.getTileEntity(x, y, z) != null && access.getTileEntity(x, y, z) instanceof TEMetalTrapDoor)
			this.setBlockBoundsForBlockRender(access.getBlockMetadata(x, y, z), ((TEMetalTrapDoor)access.getTileEntity(x, y, z)).data);
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender()
	{
		float f = 0.125F;
		this.setBlockBounds(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
	}

	public void setBlockBoundsForBlockRender(int meta, int data)
	{
		float f = 0.125F;
		int side = data & 7;
		int hinge = data >> 4;
				float fx = 0;
				float fy = 0;
				float fz = 0;
				float fx2 = 1;
				float fy2 = 1;
				float fz2 = 1;

				if (isTrapdoorOpen(meta))
				{
					if(hinge == 0)
					{
						switch(side)
						{
						case 0:
						case 1:
						case 2:
						case 3:
						{
							fx2 = f;
							break;
						}
						case 4:
						case 5:
						{
							fy2 = f;
							break;
						}
						default:
							fx2 = f;
							break;
						}
					}
					else if(hinge == 1)
					{
						switch(side)
						{
						case 2:
						case 3:
						{
							fy2 = f;
							break;
						}
						default:
							fz2 = f;
							break;
						}
					}
					else if(hinge == 2)
					{
						switch(side)
						{
						case 4:
						case 5:
						{
							fy = 1-f;
							break;
						}
						default:
							fx = 1-f;
							break;
						}
					}
					else if(hinge == 3)
					{
						switch(side)
						{
						case 2:
						case 3:
						{
							fy = 1-f;
							break;
						}
						case 4:
						case 5:
						{
							fz = 1-f;
							break;
						}
						default:
							fz = 1-f;
							break;
						}
					}
				}
				else
				{
					if(side == 0)
					{
						fy = 1-f;
					}
					else if(side == 1)
					{
						fy2 = f;
					}
					else if(side == 2)
					{
						fz = 1-f;
					}
					else if(side == 3)
					{
						fz2 = f;
					}
					else if(side == 4)
					{
						fx = 1-f;
					}
					else if(side == 5)
					{
						fx2 = f;
					}
				}

				this.setBlockBounds(fx, fy, fz, fx2, fy2, fz2);
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9)
	{
		int i1 = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 ^ 4, 2);
		par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
		return true;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if(!world.isRemote)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor)world.getTileEntity(i, j, k);
			if(te != null && te.sheetStack != null)
			{
				EntityItem ei = new EntityItem(world, i, j, k, te.sheetStack);
				world.spawnEntityInWorld(ei);
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit. Args: world, x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2,
			int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}

	public static boolean isTrapdoorOpen(int par0)
	{
		return (par0 & 4) != 0;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons = new IIcon[metalNames.length];
		for(int i = 0; i < icons.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/"+metalNames[i]+" Trap Door");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		TEMetalTrapDoor te = (TEMetalTrapDoor) access.getTileEntity(i, j, k);
		if(te!= null && te.sheetStack != null)
			return icons[te.sheetStack.getItemDamage() & 31];
		else
			return TFC_Textures.invisibleTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta&31];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return world.getBlock(x, y, z) == this;
	}
}
