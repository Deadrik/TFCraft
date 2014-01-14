package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TESeaWeed;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockCustomFluid extends Block
{

	@SideOnly(Side.CLIENT)
	public Icon[] theIcon;

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}


	/**
	 * Returns the percentage of the fluid block that is air, based on the given flow decay of the fluid.
	 */
	public static float getFluidHeightPercent(int par0)
	{
		if (par0 >= 8)
			par0 = 0;

		float var1 = (par0 + 1) / 9.0F;
		return var1;
	}

	/**
	 * Returns the amount of fluid decay at the coordinates, or -1 if the block at the coordinates is not the same
	 * material as the fluid.
	 */
	protected int getFlowDecay(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMaterial(par2, par3, par4) != this.blockMaterial ? -1 : par1World.getBlockMetadata(par2, par3, par4);
	}

	/**
	 * Returns the flow decay but converts values indicating falling liquid (values >=8) to their effective source block
	 * value of zero.
	 */
	protected int getEffectiveFlowDecay(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial)
			return -1;
		else
		{
			int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

			if (var5 >= 8)
				var5 = 0;

			return var5;
		}
	}

	/**
	 * The type of render function that is called for this block
	 */
	/*public int getRenderType()
    {
        return TFCBlocks.fluidRenderId;
    }*/

	@Override
	public int tickRate(World world)
	{
		return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? 30 : 0);
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		this.checkForHarden(par1World, par2, par3, par4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		this.checkForHarden(par1World, par2, par3, par4);
	}

	/**
	 * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
	 */
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
		int var6 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3+1, par4, 0);
		int var7 = var5 & 255;
		int var8 = var6 & 255;
		int var9 = var5 >> 16 & 255;
		int var10 = var6 >> 16 & 255;
		return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
	}

	/**
	 * Forces lava to check to see if it is colliding with water, and then decide what it should harden to.
	 */
	private void checkForHarden(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlockId(par2, par3, par4) == this.blockID)
			if (this.blockMaterial == Material.lava)
			{
				boolean flag = false;

				if (flag || par1World.getBlockMaterial(par2, par3, par4 - 1) == Material.water)
					flag = true;

				if (flag || par1World.getBlockMaterial(par2, par3, par4 + 1) == Material.water)
					flag = true;

				if (flag || par1World.getBlockMaterial(par2 - 1, par3, par4) == Material.water)
					flag = true;

				if (flag || par1World.getBlockMaterial(par2 + 1, par3, par4) == Material.water)
					flag = true;

				if (flag || par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.water)
					flag = true;

				if (flag)
				{
					int l = par1World.getBlockMetadata(par2, par3, par4);

					if (l == 0)
						setBlockforLava(par1World, par2,  par3,  par4,0);
					else if (l <= 4)
						setBlockforLava(par1World, par2,  par3,  par4,1);

					this.triggerLavaMixEffects(par1World, par2, par3, par4);
				}
			}
	}

	/**
	 * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
	 */
	@Override
	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		float var5 = par1IBlockAccess.getLightBrightness(par2, par3, par4);
		float var6 = par1IBlockAccess.getLightBrightness(par2, par3+1, par4);
		return var5 > var6 ? var5 : var6;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{

		super.updateTick(par1World, par2, par3, par4, par5Random);

	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int id, int l)
	{
		Material m = world.getBlockMaterial(i, j, k);
		int blockID = world.getBlockId(i,j,k);
		super.breakBlock(world, i, j, k, id, l);
		if(m == Block.ice.blockMaterial){
			if(blockID == TFCBlocks.FreshWaterStill.blockID || blockID == TFCBlocks.FreshWaterFlowing.blockID){
				world.setBlockMetadataWithNotify(i,j,k,1,1);
			}
		}
	}

	protected BlockCustomFluid(int par1, Material par2Material)
	{
		super(par1, par2Material);
		float f = 0.0F;
		float f1 = 0.0F;
		this.setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
		this.setTickRandomly(true);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return this.blockMaterial != Material.lava;
	}

	@Deprecated //Implemented here for compatibility, need to change this when we make vanilla fluids use our fluid methods.
	public float getFilledPercentage(IBlockAccess world, int x, int y, int z)
	{
		return 1 - BlockFluid.getFluidHeightPercent(world.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		return par1 != 0 && par1 != 1 ? this.theIcon[1] : this.theIcon[0];
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in \n@param par1 block metaData \n@param
	 * par2 whether the player right-clicked while holding a boat
	 */
	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return par2 && par1 == 0;
	}

	/**
	 * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
	 * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
		return material == this.blockMaterial ? false : (par5 == 1 ? true : (material == Material.ice ? false : super.isBlockSolid(par1IBlockAccess, par2, par3, par4, par5)));
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
		return material == this.blockMaterial ? false : (par5 == 1 ? true : (material == Material.ice ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)));
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 4;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	/**
	 * Returns a vector indicating the direction and intensity of fluid flow.
	 */
	private Vec3 getFlowVector(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Vec3 vec3 = par1IBlockAccess.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
		int l = this.getEffectiveFlowDecay(par1IBlockAccess, par2, par3, par4);

		for (int i1 = 0; i1 < 4; ++i1)
		{
			int j1 = par2;
			int k1 = par4;

			if (i1 == 0)
				j1 = par2 - 1;

			if (i1 == 1)
				k1 = par4 - 1;

			if (i1 == 2)
				++j1;

			if (i1 == 3)
				++k1;

			int l1 = this.getEffectiveFlowDecay(par1IBlockAccess, j1, par3, k1);
			int i2;

			if (l1 < 0)
			{
				if (!par1IBlockAccess.getBlockMaterial(j1, par3, k1).blocksMovement())
				{
					l1 = this.getEffectiveFlowDecay(par1IBlockAccess, j1, par3 - 1, k1);

					if (l1 >= 0)
					{
						i2 = l1 - (l - 8);
						vec3 = vec3.addVector((j1 - par2) * i2, (par3 - par3) * i2, (k1 - par4) * i2);
					}
				}
			}
			else if (l1 >= 0)
			{
				i2 = l1 - l;
				vec3 = vec3.addVector((j1 - par2) * i2, (par3 - par3) * i2, (k1 - par4) * i2);
			}
		}

		if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) >= 8)
		{
			boolean flag = false;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 - 1, 2))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 + 1, 3))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3, par4, 4))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3, par4, 5))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4))
				flag = true;

			if (flag || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5))
				flag = true;

			if (flag)
				vec3 = vec3.normalize().addVector(0.0D, -6.0D, 0.0D);
		}

		vec3 = vec3.normalize();
		return vec3;
	}

	/**
	 * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
	 */
	@Override
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
	{
		Vec3 vec31 = this.getFlowVector(par1World, par2, par3, par4);
		par6Vec3.xCoord += vec31.xCoord;
		par6Vec3.yCoord += vec31.yCoord;
		par6Vec3.zCoord += vec31.zCoord;
	}


	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass()
	{
		return this.blockMaterial == Material.water ? 1 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int l;

		if (this.blockMaterial == Material.water)
		{
			if (par5Random.nextInt(10) == 0)
			{
				l = par1World.getBlockMetadata(par2, par3, par4);

				if (l <= 0 || l >= 8)
					par1World.spawnParticle("suspended", par2 + par5Random.nextFloat(), par3 + par5Random.nextFloat(), par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
			}

			for (l = 0; l < 0; ++l)
			{
				int i1 = par5Random.nextInt(4);
				int j1 = par2;
				int k1 = par4;

				if (i1 == 0)
					j1 = par2 - 1;

				if (i1 == 1)
					++j1;

				if (i1 == 2)
					k1 = par4 - 1;

				if (i1 == 3)
					++k1;

				if (par1World.getBlockMaterial(j1, par3, k1) == Material.air && (par1World.getBlockMaterial(j1, par3 - 1, k1).blocksMovement() || par1World.getBlockMaterial(j1, par3 - 1, k1).isLiquid()))
				{
					float f = 0.0625F;
					double d0 = par2 + par5Random.nextFloat();
					double d1 = par3 + par5Random.nextFloat();
					double d2 = par4 + par5Random.nextFloat();

					if (i1 == 0)
						d0 = par2 - f;

					if (i1 == 1)
						d0 = par2 + 1 + f;

					if (i1 == 2)
						d2 = par4 - f;

					if (i1 == 3)
						d2 = par4 + 1 + f;

					double d3 = 0.0D;
					double d4 = 0.0D;

					if (i1 == 0)
						d3 = (-f);

					if (i1 == 1)
						d3 = f;

					if (i1 == 2)
						d4 = (-f);

					if (i1 == 3)
						d4 = f;

					par1World.spawnParticle("splash", d0, d1, d2, d3, 0.0D, d4);
				}
			}
		}

		if (this.blockMaterial == Material.water && par5Random.nextInt(64) == 0)
		{
			l = par1World.getBlockMetadata(par2, par3, par4);

			if (l > 0 && l < 8)
				par1World.playSound(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "liquid.water", par5Random.nextFloat() * 0.25F + 0.75F, par5Random.nextFloat() * 1.0F + 0.5F, false);
		}

		double d5;
		double d6;
		double d7;

		if (this.blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
		{
			if (par5Random.nextInt(100) == 0)
			{
				d5 = par2 + par5Random.nextFloat();
				d7 = par3 + this.maxY;
				d6 = par4 + par5Random.nextFloat();
				par1World.spawnParticle("lava", d5, d7, d6, 0.0D, 0.0D, 0.0D);
				par1World.playSound(d5, d7, d6, "liquid.lavapop", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
			}

			if (par5Random.nextInt(200) == 0)
				par1World.playSound(par2, par3, par4, "liquid.lava", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
		}

		if (par5Random.nextInt(10) == 0 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement())
		{
			d5 = par2 + par5Random.nextFloat();
			d7 = par3 - 1.05D;
			d6 = par4 + par5Random.nextFloat();

			if (this.blockMaterial == Material.water)
				par1World.spawnParticle("dripWater", d5, d7, d6, 0.0D, 0.0D, 0.0D);
			else
				par1World.spawnParticle("dripLava", d5, d7, d6, 0.0D, 0.0D, 0.0D);
		}
	}

	public void setBlockforLava(World par1World, int par2, int par3, int par4, int typeOfLava){
		DataLayer rockLayer3 = ((TFCWorldChunkManager)par1World.getWorldChunkManager()).getRockLayerAt(par2, par3, 2);
		int blockId = rockLayer3.data1;
		int meta = rockLayer3.data2;
		Random rand = new Random();
		boolean felsicLava = true;
		if(blockId == TFCBlocks.StoneIgIn.blockID && (meta == 2||meta == 1))
			felsicLava = false;
		else if(blockId == TFCBlocks.StoneIgEx.blockID && (meta == 1||meta==2))
			felsicLava = false;
		if (typeOfLava == 0 || typeOfLava == 2)		//non flowing rock
		{
			if(felsicLava){
				if(rand.nextInt(10)==0 && typeOfLava == 0)
					par1World.setBlock(par2, par3, par4, Block.obsidian.blockID);
				else{
					par1World.setBlock(par2,par3,par4,TFCBlocks.StoneIgEx.blockID);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0,0);
				}
			}
			else{
				par1World.setBlock(par2,par3,par4,TFCBlocks.StoneIgEx.blockID);
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 1,0);
			}
		}
		else if (typeOfLava == 1)
		{
			par1World.setBlock(par2,par3,par4,TFCBlocks.StoneIgExCobble.blockID);
			if(felsicLava)
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 0,0);
			else
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 1,0);
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * the sin and cos of this number determine the surface gradient of the flowing block.
	 */
	public static double getFlowDirection(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, Material par4Material)
	{
		Vec3 vec3 = null;

		if (par4Material == Material.water)
			vec3 = ((BlockCustomFluid)Block.blocksList[8]).getFlowVector(par0IBlockAccess, par1, par2, par3);

		if (par4Material == Material.lava)
			vec3 = ((BlockCustomFluid)Block.blocksList[10]).getFlowVector(par0IBlockAccess, par1, par2, par3);

		return vec3.xCoord == 0.0D && vec3.zCoord == 0.0D ? -1000.0D : Math.atan2(vec3.zCoord, vec3.xCoord) - (Math.PI / 2D);
	}

	/**
	 * Creates fizzing sound and smoke. Used when lava flows over block or mixes with water.
	 */
	protected void triggerLavaMixEffects(World par1World, int par2, int par3, int par4)
	{
		par1World.playSoundEffect(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

		for (int l = 0; l < 8; ++l)
			par1World.spawnParticle("largesmoke", par2 + Math.random(), par3 + 1.2D, par4 + Math.random(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		if (this.blockMaterial == Material.lava)
			this.theIcon = new Icon[] {par1IconRegister.registerIcon("lava_still"), par1IconRegister.registerIcon("lava_flow")};
		else
			this.theIcon = new Icon[] {par1IconRegister.registerIcon("water_still"), par1IconRegister.registerIcon("water_flow")};
	}

	@SideOnly(Side.CLIENT)
	public static Icon getFluidIcon(String par0Str)
	{
		return par0Str == "water_still" ? ((BlockCustomFluid)Block.blocksList[8]).theIcon[0] : (par0Str == "water_flow" ? ((BlockCustomFluid)Block.blocksList[8]).theIcon[1] : (par0Str == "lava_still" ? ((BlockCustomFluid)Block.blocksList[10]).theIcon[0] : (par0Str == "lava_flow" ? ((BlockCustomFluid)Block.blocksList[10]).theIcon[1] : null)));
	}


}
