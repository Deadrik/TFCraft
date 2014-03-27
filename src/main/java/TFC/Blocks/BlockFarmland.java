package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import TFC.Reference;
import TFC.TileEntities.TileEntityFarmland;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFarmland extends BlockContainer
{
	IIcon[] DirtTexture = new IIcon[23];
	int textureOffset = 0;

	public BlockFarmland(int tex)
	{
		super(Material.ground);
		this.setTickRandomly(true);
		textureOffset = tex;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = textureOffset; i < (textureOffset == 0 ? 16 : 23); i++)
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "farmland/Farmland"+(i));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int xCoord, int yCoord, int zCoord, int par5)
	{
		Block blk = Blocks.dirt;

		if (par5 == 1)//top
			return DirtTexture[access.getBlockMetadata(xCoord, yCoord, zCoord)+textureOffset];
		else
			return blk.getIcon(0, access.getBlockMetadata(xCoord, yCoord, zCoord));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		Block blk = Blocks.dirt;

		if (side == ForgeDirection.UP.ordinal())
			return DirtTexture[meta + textureOffset];
		else
			return blk.getIcon(0, meta);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return null;
	}

	/**
	 * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
	 */
	private boolean isCropsNearby(World par1World, int par2, int par3, int par4)
	{
		byte var5 = 0;
		for (int var6 = par2 - var5; var6 <= par2 + var5; ++var6)
			for (int var7 = par4 - var5; var7 <= par4 + var5; ++var7)
			{
				Block var8 = par1World.getBlock(var6, par3 + 1, var7);
				if (var8 instanceof IPlantable && canSustainPlant(par1World, par2, par3, par4, ForgeDirection.UP, (IPlantable)var8))
					return true;
			}

		return false;
	}
	
	/**
	 * returns true if there's water nearby (x-4 to x+4, y to y+1, k-4 to k+4)
	 */
	public static boolean isWaterNearby(World world, int i, int j, int k)
	{
		for (int x = i - 4; x <= i + 4; ++x)
			for (int y = j; y <= j + 1; ++y)
				for (int z = k - 4; z <= k + 4; ++z)
					if (world.getBlock(x, y, z).getMaterial() == Material.water)
						return true;

		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityFarmland();
	}
}
