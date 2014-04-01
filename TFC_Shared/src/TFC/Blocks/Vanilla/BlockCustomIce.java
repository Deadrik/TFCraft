package TFC.Blocks.Vanilla;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.WorldGen.TFCProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockCustomIce extends BlockIce
{
	public BlockCustomIce(int par1)
	{
		super(par1);
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material var7 = par1World.getBlockMaterial(par3, par4 - 1, par5);

		if (var7.blocksMovement() || var7.isLiquid())
		{
			par1World.setBlock(par3, par4, par5, getBlockMeltId(par1World,par3,par4,par5,true), 0, 2);
		}
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int id, int l)
	{
		/*
		if(id == this.blockID){
			if(world.getBlockId(i,j,k)==Block.waterStill.blockID && l != 0){
				world.setBlock(i,j,k,TFCBlocks.FreshWaterStill.blockID);
			}
			else if( world.getBlockId(i,j,k)==Block.waterMoving.blockID && l != 0){
				world.setBlock(i,j,k,TFCBlocks.FreshWaterFlowing.blockID);
			}
			else if(world.getBlockId(i,j,k)==TFCBlocks.FreshWaterStill.blockID && l != 1){
				world.setBlock(i,j,k,Block.waterStill.blockID);
			}
			else if( world.getBlockId(i,j,k)==TFCBlocks.FreshWaterFlowing.blockID && l != 1){
				world.setBlock(i,j,k,Block.waterMoving.blockID);
			}
		}*/
		super.breakBlock(world,i,j,k,id,l);
	}

	@Override
	/**
	 * Determines if this block can support the passed in plant, allowing it to be planted and grow.
	 * Some examples:
	 *   Reeds check if its a reed, or if its sand/dirt/grass and adjacent to water
	 *   Cacti checks if its a cacti, or if its sand
	 *   Nether types check for soul sand
	 *   Crops check for tilled soil
	 *   Caves check if it's a colid surface
	 *   Plains check if its grass or dirt
	 *   Water check if its still water
	 *
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z position
	 * @param direction The direction relative to the given position the plant wants to be, typically its UP
	 * @param plant The plant that wants to check
	 * @return True to allow the plant to be planted/stay.
	 */
	public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
	{
		int plantID = plant.getPlantID(world, x, y + 1, z);
		EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);
		int meta = world.getBlockMetadata(x,y,z);

		if (plantID == cactus.blockID && blockID == cactus.blockID)
		{
			return true;
		}

		if (plantID == reed.blockID && blockID == reed.blockID)
		{
			return true;
		}

		if (plant instanceof BlockCustomLilyPad && ((BlockCustomLilyPad)plant).canThisPlantGrowOnThisBlockID(blockID,meta))
		{
			return true;
		}

		switch (plantType)
		{
		case Desert: return blockID == sand.blockID;
		case Nether: return blockID == slowSand.blockID;
		case Crop:   return blockID == tilledField.blockID;
		case Cave:   return isBlockSolidOnSide(world, x, y, z, UP);
		case Plains: return blockID == grass.blockID || blockID == dirt.blockID;
		case Water:  return world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0;
		case Beach:
			boolean isBeach = (blockID == Block.grass.blockID || blockID == Block.dirt.blockID || blockID == Block.sand.blockID);
			boolean hasWater = (world.getBlockMaterial(x - 1, y, z    ) == Material.water ||
					world.getBlockMaterial(x + 1, y, z    ) == Material.water ||
					world.getBlockMaterial(x,     y, z - 1) == Material.water ||
					world.getBlockMaterial(x,     y, z + 1) == Material.water);
			return isBeach && hasWater;
		}

		return false;
	}

	@Override
	public int getLightOpacity(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x,y,z);
		if (meta == 0){
			//sea ice is cloudy
			return 9;
		}
		return lightOpacity[blockID];
	}

	protected int getBlockMeltId(World world, int i, int j, int k, boolean moving){
		int meta = world.getBlockMetadata(i,j,k);
		int id = world.getBlockId(i,j,k);
		if(id != this.blockID){
			return id;
		}
		switch(meta){
		case 0: return moving? Block.waterMoving.blockID : Block.waterStill.blockID;
		case 1: return moving? TFCBlocks.FreshWaterFlowing.blockID : TFCBlocks.FreshWaterStill.blockID;
		default: return 0;
		}
	}

	Icon seaIce;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister registerer)
	{
		seaIce = registerer.registerIcon(Reference.ModID + ":" + "seaIce");
		super.registerIcons(registerer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		if(par2 == 0){
			return this.seaIce;
		}
		else{
			return super.getIcon(par1, par2);
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if((world.provider) instanceof TFCProvider && !world.isRemote && world.getBlockId(i, j, k)==this.blockID)
		{
			if (!((TFCProvider)(world.provider)).canBlockFreezeTFC(i, j, k, false))
			{
				if (world.getBlockId(i, j+1, k) == Block.snow.blockID)
				{
					int meta = world.getBlockMetadata(i, j+1, k);
					if (meta > 0) {
						world.setBlockMetadataWithNotify(i, j+1, k, meta-1, 2);
					} else {
						world.setBlockToAir(i, j+1, k);
					}
				} else {
					this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
					world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,false), 0, 2);
				}
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				if(j > 143){
					world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,true), 0, 2);
				} else {
					world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,false), 0, 2);
				}
			}
			
			if(world.getBlockMetadata(i, j, k) == 1)
			{
				if(j== 144 && scanForOcean(world,i,j,k))
				{
					world.setBlockMetadataWithNotify(i, j, k, 0, 3);
				}
			}
		}
	}
	
	private boolean scanForOcean(World world, int i, int j, int k)
	{
		if(world.getBiomeGenForCoords(i+5, k).biomeID == BiomeGenBase.ocean.biomeID ||
				world.getBiomeGenForCoords(i+10, k).biomeID == BiomeGenBase.ocean.biomeID || 
				world.getBiomeGenForCoords(i+20, k).biomeID == BiomeGenBase.ocean.biomeID || 
						world.getBiomeGenForCoords(i-5, k).biomeID == BiomeGenBase.ocean.biomeID ||
						world.getBiomeGenForCoords(i-10, k).biomeID == BiomeGenBase.ocean.biomeID || 
						world.getBiomeGenForCoords(i-20, k).biomeID == BiomeGenBase.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k+5).biomeID == BiomeGenBase.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k+10).biomeID == BiomeGenBase.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k+20).biomeID == BiomeGenBase.ocean.biomeID|| 
				world.getBiomeGenForCoords(i, k-5).biomeID == BiomeGenBase.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k-10).biomeID == BiomeGenBase.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k-20).biomeID == BiomeGenBase.ocean.biomeID)
		{
			return true;
		}
		return false;
	}
}
