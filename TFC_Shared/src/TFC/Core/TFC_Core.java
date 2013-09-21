package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Entities.IAnimal;
import TFC.API.Enums.EnumWoodMaterial;
import TFC.Blocks.BlockSlab;
import TFC.Food.FoodStatsTFC;
import TFC.Items.ItemOre;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFC_Core
{
	public enum Direction{PosX,PosZ,NegX,NegZ,None,PosXPosZ,PosXNegZ,NegXPosZ,NegXNegZ,NegY,PosY}

	public static boolean PreventEntityDataUpdate = false;

	@SideOnly(Side.CLIENT)
	public static int getMouseX()
	{
		ScaledResolution scaledresolution = new ScaledResolution(
				Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int i = scaledresolution.getScaledWidth();
		int k = Mouse.getX() * i / Minecraft.getMinecraft().displayWidth;

		return k;
	}

	@SideOnly(Side.CLIENT)
	public static int getMouseY()
	{
		ScaledResolution scaledresolution = new ScaledResolution(
				Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int j = scaledresolution.getScaledHeight();
		int l = j - Mouse.getY() * j / Minecraft.getMinecraft().displayHeight - 1;

		return l;
	}

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if(TerraFirmaCraft.proxy.getCurrentWorld().isBlockOpaqueCube(i, j+1, k)) {
			return true;
		}

		return false;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if(random.nextInt(500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate,1,0));
			items.add(new ItemStack(TFCItems.GemAmethyst,1,0));
			items.add(new ItemStack(TFCItems.GemBeryl,1,0));
			items.add(new ItemStack(TFCItems.GemEmerald,1,0));
			items.add(new ItemStack(TFCItems.GemGarnet,1,0));
			items.add(new ItemStack(TFCItems.GemJade,1,0));
			items.add(new ItemStack(TFCItems.GemJasper,1,0));
			items.add(new ItemStack(TFCItems.GemOpal,1,0));
			items.add(new ItemStack(TFCItems.GemRuby,1,0));
			items.add(new ItemStack(TFCItems.GemSapphire,1,0));
			items.add(new ItemStack(TFCItems.GemTourmaline,1,0));
			items.add(new ItemStack(TFCItems.GemTopaz,1,0));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		else if(random.nextInt(1000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate,1,1));
			items.add(new ItemStack(TFCItems.GemAmethyst,1,1));
			items.add(new ItemStack(TFCItems.GemBeryl,1,1));
			items.add(new ItemStack(TFCItems.GemEmerald,1,1));
			items.add(new ItemStack(TFCItems.GemGarnet,1,1));
			items.add(new ItemStack(TFCItems.GemJade,1,1));
			items.add(new ItemStack(TFCItems.GemJasper,1,1));
			items.add(new ItemStack(TFCItems.GemOpal,1,1));
			items.add(new ItemStack(TFCItems.GemRuby,1,1));
			items.add(new ItemStack(TFCItems.GemSapphire,1,1));
			items.add(new ItemStack(TFCItems.GemTourmaline,1,1));
			items.add(new ItemStack(TFCItems.GemTopaz,1,1));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(2000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate,1,2));
			items.add(new ItemStack(TFCItems.GemAmethyst,1,2));
			items.add(new ItemStack(TFCItems.GemBeryl,1,2));
			items.add(new ItemStack(TFCItems.GemEmerald,1,2));
			items.add(new ItemStack(TFCItems.GemGarnet,1,2));
			items.add(new ItemStack(TFCItems.GemJade,1,2));
			items.add(new ItemStack(TFCItems.GemJasper,1,2));
			items.add(new ItemStack(TFCItems.GemOpal,1,2));
			items.add(new ItemStack(TFCItems.GemRuby,1,2));
			items.add(new ItemStack(TFCItems.GemSapphire,1,2));
			items.add(new ItemStack(TFCItems.GemTourmaline,1,2));
			items.add(new ItemStack(TFCItems.GemTopaz,1,2));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(4000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate,1,3));
			items.add(new ItemStack(TFCItems.GemAmethyst,1,3));
			items.add(new ItemStack(TFCItems.GemBeryl,1,3));
			items.add(new ItemStack(TFCItems.GemEmerald,1,3));
			items.add(new ItemStack(TFCItems.GemGarnet,1,3));
			items.add(new ItemStack(TFCItems.GemJade,1,3));
			items.add(new ItemStack(TFCItems.GemJasper,1,3));
			items.add(new ItemStack(TFCItems.GemOpal,1,3));
			items.add(new ItemStack(TFCItems.GemRuby,1,3));
			items.add(new ItemStack(TFCItems.GemSapphire,1,3));
			items.add(new ItemStack(TFCItems.GemTourmaline,1,3));
			items.add(new ItemStack(TFCItems.GemTopaz,1,3));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(8000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate,1,4));
			items.add(new ItemStack(TFCItems.GemAmethyst,1,4));
			items.add(new ItemStack(TFCItems.GemBeryl,1,4));
			items.add(new ItemStack(TFCItems.GemEmerald,1,4));
			items.add(new ItemStack(TFCItems.GemGarnet,1,4));
			items.add(new ItemStack(TFCItems.GemJade,1,4));
			items.add(new ItemStack(TFCItems.GemJasper,1,4));
			items.add(new ItemStack(TFCItems.GemOpal,1,4));
			items.add(new ItemStack(TFCItems.GemRuby,1,4));
			items.add(new ItemStack(TFCItems.GemSapphire,1,4));
			items.add(new ItemStack(TFCItems.GemTourmaline,1,4));
			items.add(new ItemStack(TFCItems.GemTopaz,1,4));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		return is;
	}

	public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
	{
		for (int y = 2; y >= -2; y--)
		{
			for (int x = 2; x >= -2; x--)
			{
				for (int z = 2; z >= -2; z--)
				{
					if(world.getBlockId(i+x, j+y, k+z) == 0) {
						world.setBlock(i+x, j+y, k+z, Block.leaves.blockID, meta, 2);
					}
				}
			}
		}
	}

	public static void SetupWorld(World world)
	{
		long seed = world.getSeed();
		Random R = new Random(seed);
		world.provider.registerWorld(world);
		Recipes.registerAnvilRecipes(R, world);
		//TerraFirmaCraft.proxy.registerSkyProvider(world.provider);
	}

	public static void SetupWorld(World w, long seed)
	{
		World world = w;
		try
		{
			//ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), "randomSeed", seed);
			ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), seed, 0);
			SetupWorld(w);
		}
		catch(Exception ex)
		{

		}

	}

	public static boolean isRawStone(World world,int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == TFCBlocks.StoneIgEx.blockID || id == TFCBlocks.StoneIgIn.blockID || 
				id == TFCBlocks.StoneSed.blockID || id == TFCBlocks.StoneMM.blockID;
	}

	public static boolean isSmoothStone(World world,int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == TFCBlocks.StoneIgExSmooth.blockID || id == TFCBlocks.StoneIgInSmooth.blockID || 
				id == TFCBlocks.StoneSedSmooth.blockID || id == TFCBlocks.StoneMMSmooth.blockID;
	}

	public static boolean isSmoothStone(int id)
	{
		return id == TFCBlocks.StoneIgExSmooth.blockID || id == TFCBlocks.StoneIgInSmooth.blockID || 
				id == TFCBlocks.StoneSedSmooth.blockID || id == TFCBlocks.StoneMMSmooth.blockID;
	}

	public static boolean isBrickStone(int id)
	{
		return id == TFCBlocks.StoneIgExBrick.blockID || id == TFCBlocks.StoneIgInBrick.blockID || 
				id == TFCBlocks.StoneSedBrick.blockID || id == TFCBlocks.StoneMMBrick.blockID;
	}

	public static boolean isRawStone(int id)
	{
		return id == TFCBlocks.StoneIgEx.blockID || id == TFCBlocks.StoneIgIn.blockID || 
				id == TFCBlocks.StoneSed.blockID || id == TFCBlocks.StoneMM.blockID;
	}

	public static boolean isDirt(int id)
	{
		return (id == TFCBlocks.Dirt.blockID || id == TFCBlocks.Dirt2.blockID);
	}

	public static boolean isGrass(int id)
	{
		return (id == TFCBlocks.Grass.blockID || id == TFCBlocks.Grass2.blockID ||
				id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID ||
				id == TFCBlocks.PeatGrass.blockID || id == TFCBlocks.DryGrass.blockID || id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isLushGrass(int id)
	{
		return (id == TFCBlocks.Grass.blockID || id == TFCBlocks.Grass2.blockID ||
				id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID ||
				id == TFCBlocks.PeatGrass.blockID);

	}

	public static boolean isClayGrass(int id)
	{
		return (id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID);

	}

	public static boolean isPeatGrass(int id)
	{
		return (id == TFCBlocks.PeatGrass.blockID);

	}

	public static boolean isDryGrass(int id)
	{
		return (id == TFCBlocks.DryGrass.blockID || id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isGrassType1(int id)
	{
		return (id == TFCBlocks.Grass.blockID ||
				id == TFCBlocks.ClayGrass.blockID ||
				id == TFCBlocks.DryGrass.blockID);

	}

	public static boolean isGrassType2(int id)
	{
		return (id == TFCBlocks.Grass2.blockID ||
				id == TFCBlocks.ClayGrass2.blockID ||
				id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isClay(int id)
	{
		if(id == TFCBlocks.Clay.blockID || id == TFCBlocks.Clay2.blockID) {
			return true;
		}
		return false;
	}

	public static boolean isSand(int id)
	{
		if(id == TFCBlocks.Sand.blockID || id == TFCBlocks.Sand2.blockID) {
			return true;
		}
		return false;
	}

	public static boolean isPeat(int id)
	{
		if(id == TFCBlocks.Peat.blockID) {
			return true;
		}
		return false;
	}

	public static boolean isWater(int id)
	{
		if(id == Block.waterMoving.blockID || id == Block.waterMoving.blockID) {
			return true;
		}
		return false;
	}

	public static boolean isSoil(int id)
	{
		return isGrass(id) || isDirt(id) || isClay(id) || isPeat(id);
	}

	public static int getSoilMetaFromStone(int inType, int inMeta)
	{
		if(inType == TFCBlocks.StoneIgIn.blockID) {
			return inMeta;
		} else if(inType == TFCBlocks.StoneSed.blockID) {
			return inMeta+3;
		} else if(inType == TFCBlocks.StoneIgEx.blockID)
		{
			if(inMeta == 3) {
				return 0;
			}
			return inMeta+13;
		} else {
			return inMeta+1;
		}
	}

	public static int getItemMetaFromStone(int inType, int inMeta)
	{
		if(inType == TFCBlocks.StoneIgIn.blockID) {
			return inMeta;
		} else if(inType == TFCBlocks.StoneSed.blockID) {
			return inMeta+3;
		} else if(inType == TFCBlocks.StoneIgEx.blockID) {
			return inMeta+13;
		} else if(inType == TFCBlocks.StoneMM.blockID) {
			return inMeta+17;
		} else {
			return 0;
		}
	}

	public static int getTypeForGrassWithRain(int inMeta, float rain)
	{
		if(rain >= 500) {
			return getTypeForGrass(inMeta);
		}
		return getTypeForDryGrass(inMeta);

	}

	public static int getTypeForGrass(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.Grass.blockID;
		}
		return TFCBlocks.Grass2.blockID;
	}

	public static int getTypeForDryGrass(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.DryGrass.blockID;
		}
		return TFCBlocks.DryGrass2.blockID;
	}

	public static int getTypeForClayGrass(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.ClayGrass.blockID;
		}
		return TFCBlocks.ClayGrass2.blockID;
	}

	public static int getTypeForDirt(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.Dirt.blockID;
		}
		return TFCBlocks.Dirt2.blockID;
	}

	public static int getTypeForClay(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.Clay.blockID;
		}
		return TFCBlocks.Clay2.blockID;

	}

	public static int getTypeForSand(int inMeta)
	{
		if(inMeta < 16) {
			return TFCBlocks.Sand.blockID;
		}
		return TFCBlocks.Sand2.blockID;

	}

	public static int getRockLayerFromHeight( int y)
	{
		if(y <= TFCOptions.RockLayer3Height) {
			return 2;
		} else if(y <= TFCOptions.RockLayer2Height) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean convertGrassToDirt(World world, int i, int j, int k)
	{
		int id = world.getBlockId(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		if(TFC_Core.isGrass(id))
		{
			if(TFC_Core.isGrassType1(id)) 
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt.blockID, meta, 2);
				return true;
			}
			else if(TFC_Core.isGrassType2(id)) 
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt2.blockID, meta, 2);
				return true;
			}
		}
		return false;
	}

	public static EnumWoodMaterial getWoodMaterial(ItemStack is)
	{
		if(is.itemID == TFCBlocks.Peat.blockID)
		{
			return EnumWoodMaterial.PEAT;
		}
		if(is.getItemDamage() == 0)
		{
			return EnumWoodMaterial.ASH;
		}
		else if(is.getItemDamage() == 1)
		{
			return EnumWoodMaterial.ASPEN;
		}
		else if(is.getItemDamage() == 2)
		{
			return EnumWoodMaterial.BIRCH;
		}
		else if(is.getItemDamage() == 3)
		{
			return EnumWoodMaterial.CHESTNUT;
		}
		else if(is.getItemDamage() == 4)
		{
			return EnumWoodMaterial.DOUGLASFIR;
		}
		else if(is.getItemDamage() == 5)
		{
			return EnumWoodMaterial.HICKORY;
		}
		else if(is.getItemDamage() == 6)
		{
			return EnumWoodMaterial.MAPLE;
		}
		else if(is.getItemDamage() == 7)
		{
			return EnumWoodMaterial.OAK;
		}
		else if(is.getItemDamage() == 8)
		{
			return EnumWoodMaterial.PINE;
		}
		else if(is.getItemDamage() == 9)
		{
			return EnumWoodMaterial.REDWOOD;
		}
		else if(is.getItemDamage() == 10)
		{
			return EnumWoodMaterial.SPRUCE;
		}
		else if(is.getItemDamage() == 11)
		{
			return EnumWoodMaterial.SYCAMORE;
		}
		else if(is.getItemDamage() == 12)
		{
			return EnumWoodMaterial.WHITECEDAR;
		}
		else if(is.getItemDamage() == 13)
		{
			return EnumWoodMaterial.WHITEELM;
		}
		else if(is.getItemDamage() == 14)
		{
			return EnumWoodMaterial.WILLOW;
		}
		else if(is.getItemDamage() == 15)
		{
			return EnumWoodMaterial.KAPOK;
		}
		else
		{
			return EnumWoodMaterial.ASPEN;
		}
	}

	public static boolean showExtraInformation()
	{
		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && 
				(Keyboard.isKeyDown(42))) 
		{
			return true;
		}
		return false;
	}

	public static FoodStatsTFC getPlayerFoodStats(EntityPlayer player)
	{
		FoodStatsTFC foodstats = new FoodStatsTFC();
		foodstats.readNBT(player.getEntityData());
		return foodstats;
	}

	public static void setPlayerFoodStats(EntityPlayer player, FoodStatsTFC foodstats)
	{
		foodstats.writeNBT(player.getEntityData());
	}

	public static boolean isNorthSolid(World world, int x, int y, int z)
	{
		if(world.isBlockNormalCube(x, y, z-1))
		{
			return true;
		}
		else if(world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z-1);
			if(BlockSlab.getNorthChiselLevel(te.extraData) != 0)
			{
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isSouthSolid(World world, int x, int y, int z)
	{
		if(world.isBlockNormalCube(x, y, z+1))
		{
			return true;
		}
		else if(world.getBlockId(x, y, z+1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z+1);
			if(BlockSlab.getSouthChiselLevel(te.extraData) != 0)
			{
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isEastSolid(World world, int x, int y, int z)
	{
		if(world.isBlockNormalCube(x-1, y, z))
		{
			return true;
		}
		else if(world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x-1, y, z);
			if(BlockSlab.getEastChiselLevel(te.extraData) != 0)
			{
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isWestSolid(World world, int x, int y, int z)
	{
		if(world.isBlockNormalCube(x+1, y, z))
		{
			return true;
		}
		else if(world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x+1, y, z);
			if(BlockSlab.getWestChiselLevel(te.extraData) != 0)
			{
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isOreIron(ItemStack is)
	{
		if(is.getItem() instanceof ItemOre && ((ItemOre)is.getItem()).GetMetalType(is) == Global.PIGIRON)
		{
			return true;
		}
		return false;
	}

	public static float getEntityMaxHealth(EntityLivingBase entity)
	{
		return (float) entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
	}

	public static float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public static void bindTexture(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}
}
