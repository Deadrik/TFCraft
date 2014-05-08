package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.IFood;
import TFC.API.TFCOptions;
import TFC.API.TFC_ItemHeat;
import TFC.API.Constant.Global;
import TFC.API.Entities.IAnimal;
import TFC.API.Enums.EnumWoodMaterial;
import TFC.API.Util.Helper;
import TFC.Blocks.BlockSlab;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.Player.BodyTempStats;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.SkillStats;
import TFC.Food.ItemFoodTFC;
import TFC.Items.ItemOre;
import TFC.Items.ItemQuiver;
import TFC.Items.ItemTFCArmor;
import TFC.Items.ItemTerra;
import TFC.Items.ItemBlocks.ItemTerraBlock;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFC_Core
{
	public enum Direction
	{
		PosX, PosZ, NegX, NegZ, None, PosXPosZ, PosXNegZ, NegXPosZ, NegXNegZ, NegY, PosY
	}

	public static boolean PreventEntityDataUpdate = false;

	@SideOnly(Side.CLIENT)
	public static int getMouseX()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		int i = scaledresolution.getScaledWidth();
		int k = Mouse.getX() * i / Minecraft.getMinecraft().displayWidth;

		return k;
	}

	@SideOnly(Side.CLIENT)
	public static int getMouseY()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		int j = scaledresolution.getScaledHeight();
		int l = j - Mouse.getY() * j / Minecraft.getMinecraft().displayHeight - 1;

		return l;
	}

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if (TerraFirmaCraft.proxy.getCurrentWorld().isBlockOpaqueCube(i, j + 1, k))
			return true;

		return false;
	}

	public static InventoryPlayer getNewInventory(EntityPlayer player)
	{
		InventoryPlayer ip = player.inventory;
		NBTTagList nbt = new NBTTagList();
		nbt = player.inventory.writeToNBT(nbt);
		ip = new InventoryPlayer(player)
		{

			@Override
			public void damageArmor(float par1)
			{
				par1 /= 4.0F;

				if (par1 < 1.0F)
				{
					par1 = 1.0F;
				}

				for (int i = 0; i < this.armorInventory.length; ++i)
				{
					if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor
							&& !(this.armorInventory[i].getItem() instanceof ItemQuiver))
					{
						this.armorInventory[i].damageItem((int) par1, this.player);

						if (this.armorInventory[i].stackSize == 0)
						{
							this.armorInventory[i] = null;
						}
					}
				}
			}

			@Override
			public int getSizeInventory()
			{
				return this.mainInventory.length + armorInventory.length;
			}

			@Override
			public void readFromNBT(NBTTagList par1NBTTagList)
			{
				this.mainInventory = new ItemStack[36];
				this.armorInventory = new ItemStack[5];

				for (int i = 0; i < par1NBTTagList.tagCount(); ++i)
				{
					NBTTagCompound nbttagcompound = (NBTTagCompound) par1NBTTagList.tagAt(i);
					int j = nbttagcompound.getByte("Slot") & 255;
					ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
					if (itemstack != null)
					{
						if (j >= 0 && j < this.mainInventory.length)
						{
							this.mainInventory[j] = itemstack;
						}

						if (j >= 100 && j < /* this.armorInventory.length */4 + 100)
						{
							this.armorInventory[j - 100] = itemstack;
						}
					}
				}
				int j2 = player.getEntityData().getByte("Head") & 255;
				ItemStack itemstack2 = ItemStack.loadItemStackFromNBT(player.getEntityData());
				if (itemstack2 != null && itemstack2.getItem() instanceof ItemTFCArmor && ((ItemTFCArmor) (itemstack2.getItem())).getUnadjustedArmorType() == 0)
				{
					this.armorInventory[4] = itemstack2;
				}
			}

			@Override
			public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
			{
				int i;
				NBTTagCompound nbttagcompound;

				for (i = 0; i < this.mainInventory.length; ++i)
				{
					if (this.mainInventory[i] != null)
					{
						nbttagcompound = new NBTTagCompound();
						nbttagcompound.setByte("Slot", (byte) i);
						this.mainInventory[i].writeToNBT(nbttagcompound);
						par1NBTTagList.appendTag(nbttagcompound);
					}
				}
				for (i = 0; i < /* this.armorInventory.length */4; ++i)
				{
					if (this.armorInventory[i] != null)
					{
						nbttagcompound = new NBTTagCompound();
						nbttagcompound.setByte("Slot", (byte) (i + 100));
						this.armorInventory[i].writeToNBT(nbttagcompound);
						par1NBTTagList.appendTag(nbttagcompound);
					}
				}
				// Shitty workaround
				nbttagcompound = player.getEntityData();
				nbttagcompound.setByte("Head", (byte) (4 + 100));
				if (this.armorInventory[4] != null)
				{
					this.armorInventory[4].writeToNBT(nbttagcompound);
					// par1NBTTagList.appendTag(nbttagcompound);
				} else
				{
					ItemStack is = new ItemStack(0, 0, 0);
					is.writeToNBT(nbttagcompound);
				}
				return par1NBTTagList;
			}
		};
		ip.readFromNBT(nbt);
		return ip;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if (random.nextInt(500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 0));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 0));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 0));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 0));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 0));
			items.add(new ItemStack(TFCItems.GemJade, 1, 0));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 0));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 0));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 0));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 0));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 0));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 0));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];

		} else if (random.nextInt(1000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 1));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 1));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 1));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 1));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 1));
			items.add(new ItemStack(TFCItems.GemJade, 1, 1));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 1));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 1));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 1));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 1));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 1));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 1));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		} else if (random.nextInt(2000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 2));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 2));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 2));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 2));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 2));
			items.add(new ItemStack(TFCItems.GemJade, 1, 2));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 2));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 2));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 2));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 2));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 2));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 2));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		} else if (random.nextInt(4000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 3));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 3));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 3));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 3));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 3));
			items.add(new ItemStack(TFCItems.GemJade, 1, 3));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 3));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 3));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 3));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 3));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 3));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 3));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		} else if (random.nextInt(8000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 4));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 4));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 4));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 4));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 4));
			items.add(new ItemStack(TFCItems.GemJade, 1, 4));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 4));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 4));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 4));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 4));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 4));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 4));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];

		}
		return is;
	}

	public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
	{
		for (int y = 2; y >= -2; y--)
			for (int x = 2; x >= -2; x--)
				for (int z = 2; z >= -2; z--)
					if (world.getBlockId(i + x, j + y, k + z) == 0)
						world.setBlock(i + x, j + y, k + z, Block.leaves.blockID, meta, 2);
	}

	public static void SetupWorld(World world)
	{
		long seed = world.getSeed();
		Random R = new Random(seed);
		world.provider.registerWorld(world);
		Recipes.registerAnvilRecipes(R, world);
		// TerraFirmaCraft.proxy.registerSkyProvider(world.provider);
	}

	public static void SetupWorld(World w, long seed)
	{
		World world = w;
		try
		{
			// ReflectionHelper.setPrivateValue(WorldInfo.class,
			// w.getWorldInfo(), "randomSeed", seed);
			ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), seed, 0);
			SetupWorld(w);
		} catch (Exception ex)
		{

		}

	}

	public static boolean isRawStone(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == TFCBlocks.StoneIgEx.blockID || id == TFCBlocks.StoneIgIn.blockID || id == TFCBlocks.StoneSed.blockID || id == TFCBlocks.StoneMM.blockID;
	}

	public static boolean isSmoothStone(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == TFCBlocks.StoneIgExSmooth.blockID || id == TFCBlocks.StoneIgInSmooth.blockID || id == TFCBlocks.StoneSedSmooth.blockID
				|| id == TFCBlocks.StoneMMSmooth.blockID;
	}

	public static boolean isSmoothStone(int id)
	{
		return id == TFCBlocks.StoneIgExSmooth.blockID || id == TFCBlocks.StoneIgInSmooth.blockID || id == TFCBlocks.StoneSedSmooth.blockID
				|| id == TFCBlocks.StoneMMSmooth.blockID;
	}

	public static boolean isBrickStone(int id)
	{
		return id == TFCBlocks.StoneIgExBrick.blockID || id == TFCBlocks.StoneIgInBrick.blockID || id == TFCBlocks.StoneSedBrick.blockID
				|| id == TFCBlocks.StoneMMBrick.blockID;
	}

	public static boolean isRawStone(int id)
	{
		return id == TFCBlocks.StoneIgEx.blockID || id == TFCBlocks.StoneIgIn.blockID || id == TFCBlocks.StoneSed.blockID || id == TFCBlocks.StoneMM.blockID;
	}

	public static boolean isDirt(int id)
	{
		return (id == TFCBlocks.Dirt.blockID || id == TFCBlocks.Dirt2.blockID);
	}

	public static boolean isFarmland(int id)
	{
		return (id == TFCBlocks.tilledSoil.blockID || id == TFCBlocks.tilledSoil2.blockID);
	}

	public static boolean isGrass(int id)
	{
		return (id == TFCBlocks.Grass.blockID || id == TFCBlocks.Grass2.blockID || id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID
				|| id == TFCBlocks.PeatGrass.blockID || id == TFCBlocks.DryGrass.blockID || id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isLushGrass(int id)
	{
		return (id == TFCBlocks.Grass.blockID || id == TFCBlocks.Grass2.blockID || id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID || id == TFCBlocks.PeatGrass.blockID);

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
		return (id == TFCBlocks.Grass.blockID || id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.DryGrass.blockID);

	}

	public static boolean isGrassType2(int id)
	{
		return (id == TFCBlocks.Grass2.blockID || id == TFCBlocks.ClayGrass2.blockID || id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isClay(int id)
	{
		if (id == TFCBlocks.Clay.blockID || id == TFCBlocks.Clay2.blockID)
			return true;
		return false;
	}

	public static boolean isSand(int id)
	{
		if (id == TFCBlocks.Sand.blockID || id == TFCBlocks.Sand2.blockID)
			return true;
		return false;
	}

	public static boolean isPeat(int id)
	{
		if (id == TFCBlocks.Peat.blockID)
			return true;
		return false;
	}

	public static boolean isWater(int id)
	{
		return isSaltWater(id) || isFreshWater(id) || isHotWater(id);
	}

	public static boolean isNotWater(int id)
	{
		return !isSaltWater(id) || !isFreshWater(id) || !isHotWater(id);
	}

	public static boolean isHotWater(int id)
	{
		if (id == TFCBlocks.HotWaterFlowing.blockID || id == TFCBlocks.HotWaterStill.blockID)
			return true;
		return false;
	}

	public static boolean isSaltWater(int id)
	{
		if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID)
			return true;
		return false;
	}

	public static boolean isSaltWaterIncludeIce(int id, int meta, Material mat)
	{
		if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID || (mat == Material.ice && meta == 0))
			return true;
		return false;
	}

	public static boolean isFreshWater(int id)
	{
		if (id == TFCBlocks.FreshWaterFlowing.blockID || id == TFCBlocks.FreshWaterStill.blockID)
			return true;
		return false;
	}

	public static boolean isFreshWaterIncludeIce(int id, int meta)
	{
		if (id == TFCBlocks.FreshWaterFlowing.blockID || id == TFCBlocks.FreshWaterStill.blockID || (id == Block.ice.blockID && meta != 0))
			return true;
		return false;
	}

	public static boolean isFreshWaterIncludeIce(int id, int meta, Material mat)
	{
		if (id == TFCBlocks.FreshWaterFlowing.blockID || id == TFCBlocks.FreshWaterStill.blockID || (mat == Material.ice && meta != 0))
			return true;
		return false;
	}

	public static boolean isWaterStill(int id)
	{
		if (id == Block.waterStill.blockID || id == TFCBlocks.FreshWaterStill.blockID)
			return true;
		return false;
	}

	public static boolean isWaterMoving(int id)
	{
		if (id == TFCBlocks.FreshWaterFlowing.blockID || id == Block.waterMoving.blockID)
			return true;
		return false;
	}

	public static boolean isSoil(int id)
	{
		return isGrass(id) || isDirt(id) || isClay(id) || isPeat(id);
	}

	public static boolean isGravel(int id)
	{
		return id == Block.gravel.blockID;
	}

	public static boolean isGround(int id)
	{
		return isSoil(id) || isRawStone(id) || isSand(id);
	}

	public static int getSoilMetaFromStone(int inType, int inMeta)
	{
		if (inType == TFCBlocks.StoneIgIn.blockID)
			return inMeta;
		else if (inType == TFCBlocks.StoneSed.blockID)
			return inMeta + 3;
		else if (inType == TFCBlocks.StoneIgEx.blockID)
			return inMeta + 11;
		else
		{
			if (inMeta == 0)
				return inMeta + 15;
			return inMeta-1;
		}
	}

	public static int getItemMetaFromStone(int inType, int inMeta)
	{
		if (inType == TFCBlocks.StoneIgIn.blockID)
			return inMeta;
		else if (inType == TFCBlocks.StoneSed.blockID)
			return inMeta + 3;
		else if (inType == TFCBlocks.StoneIgEx.blockID)
			return inMeta + 11;
		else if (inType == TFCBlocks.StoneMM.blockID)
			return inMeta + 15;
		else
			return 0;
	}

	public static int getTypeForGrassWithRain(int inMeta, float rain)
	{
		if (rain >= 500)
			return getTypeForGrass(inMeta);
		return getTypeForDryGrass(inMeta);
	}

	public static int getTypeForGrassWithRainByID(int inID, float rain)
	{
		if (rain >= 500)
			return getTypeForGrassFromSoil(inID);
		return getTypeForDryGrassFromSoil(inID);
	}

	public static int getTypeForGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.Grass.blockID;
		return TFCBlocks.Grass2.blockID;
	}

	public static int getTypeForGrassFromDirt(int inID)
	{
		if (inID == TFCBlocks.Dirt.blockID)
			return TFCBlocks.Grass.blockID;
		return TFCBlocks.Grass2.blockID;
	}

	public static int getTypeForDryGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.DryGrass.blockID;
		return TFCBlocks.DryGrass2.blockID;
	}

	public static int getTypeForDryGrassFromSoil(int inID)
	{
		if (inID == TFCBlocks.Grass.blockID)
			return TFCBlocks.DryGrass.blockID;
		else if (inID == TFCBlocks.Dirt.blockID)
			return TFCBlocks.DryGrass.blockID;
		return TFCBlocks.DryGrass2.blockID;
	}

	public static int getTypeForGrassFromSoil(int inID)
	{
		if (inID == TFCBlocks.DryGrass.blockID)
			return TFCBlocks.Grass.blockID;
		else if (inID == TFCBlocks.DryGrass2.blockID)
			return TFCBlocks.Grass2.blockID;
		else if (inID == TFCBlocks.Dirt.blockID)
			return TFCBlocks.Grass.blockID;
		return TFCBlocks.Grass2.blockID;
	}

	public static int getTypeForClayGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.ClayGrass.blockID;
		return TFCBlocks.ClayGrass2.blockID;
	}

	public static int getTypeForDirt(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.Dirt.blockID;
		return TFCBlocks.Dirt2.blockID;
	}

	public static int getTypeForDirtFromGrass(int inID)
	{
		if (inID == TFCBlocks.Grass.blockID || inID == TFCBlocks.DryGrass.blockID)
			return TFCBlocks.Dirt.blockID;
		return TFCBlocks.Dirt2.blockID;
	}

	public static int getTypeForClay(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.Clay.blockID;
		return TFCBlocks.Clay2.blockID;

	}

	public static int getTypeForSand(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.Sand.blockID;
		return TFCBlocks.Sand2.blockID;

	}

	public static int getRockLayerFromHeight(World world, int x, int y, int z)
	{
		ChunkData cd = ChunkDataManager.getData(x >> 4, z >> 4);
		if (cd != null)
		{
			int[] hm = cd.heightmap;
			int localX = x & 15;
			int localZ = z & 15;
			int localY = localX + localZ * 16;
			if (y <= TFCOptions.RockLayer3Height + hm[localY])
				return 2;
			else if (y <= TFCOptions.RockLayer2Height + hm[localY])
				return 1;
			else
				return 0;
		}
		return -1;
	}

	public static boolean convertGrassToDirt(World world, int i, int j, int k)
	{
		int id = world.getBlockId(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		if (TFC_Core.isGrass(id))
			if (TFC_Core.isGrassType1(id))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt.blockID, meta, 2);
				return true;
			} else if (TFC_Core.isGrassType2(id))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt2.blockID, meta, 2);
				return true;
			}
		return false;
	}

	public static EnumWoodMaterial getWoodMaterial(ItemStack is)
	{
		if (is.itemID == TFCBlocks.Peat.blockID)
			return EnumWoodMaterial.PEAT;
		if (is.itemID == TFCBlocks.LogNatural2.blockID)
		{
			if (is.getItemDamage() == 0)
				return EnumWoodMaterial.ACACIA;
		} else if (is.getItemDamage() == 0)
			return EnumWoodMaterial.ASH;
		else if (is.getItemDamage() == 1)
			return EnumWoodMaterial.ASPEN;
		else if (is.getItemDamage() == 2)
			return EnumWoodMaterial.BIRCH;
		else if (is.getItemDamage() == 3)
			return EnumWoodMaterial.CHESTNUT;
		else if (is.getItemDamage() == 4)
			return EnumWoodMaterial.DOUGLASFIR;
		else if (is.getItemDamage() == 5)
			return EnumWoodMaterial.HICKORY;
		else if (is.getItemDamage() == 6)
			return EnumWoodMaterial.MAPLE;
		else if (is.getItemDamage() == 7)
			return EnumWoodMaterial.OAK;
		else if (is.getItemDamage() == 8)
			return EnumWoodMaterial.PINE;
		else if (is.getItemDamage() == 9)
			return EnumWoodMaterial.REDWOOD;
		else if (is.getItemDamage() == 10)
			return EnumWoodMaterial.SPRUCE;
		else if (is.getItemDamage() == 11)
			return EnumWoodMaterial.SYCAMORE;
		else if (is.getItemDamage() == 12)
			return EnumWoodMaterial.WHITECEDAR;
		else if (is.getItemDamage() == 13)
			return EnumWoodMaterial.WHITEELM;
		else if (is.getItemDamage() == 14)
			return EnumWoodMaterial.WILLOW;
		else if (is.getItemDamage() == 15)
			return EnumWoodMaterial.KAPOK;
		return EnumWoodMaterial.ASPEN;
	}

	public static boolean showExtraInformation()
	{
		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (Keyboard.isKeyDown(42)))
			return true;
		return false;
	}

	public static FoodStatsTFC getPlayerFoodStats(EntityPlayer player)
	{
		FoodStatsTFC foodstats = new FoodStatsTFC(player);
		foodstats.readNBT(player.getEntityData());
		return foodstats;
	}

	public static void setPlayerFoodStats(EntityPlayer player, FoodStatsTFC foodstats)
	{
		foodstats.writeNBT(player.getEntityData());
	}

	public static BodyTempStats getBodyTempStats(EntityPlayer player)
	{
		BodyTempStats body = new BodyTempStats();
		body.readNBT(player.getEntityData());
		return body;
	}

	public static void setBodyTempStats(EntityPlayer player, BodyTempStats tempStats)
	{
		tempStats.writeNBT(player.getEntityData());
	}

	public static SkillStats getSkillStats(EntityPlayer player)
	{
		SkillStats skills = new SkillStats(player);
		skills.readNBT(player.getEntityData());
		return skills;
	}

	public static void setSkillStats(EntityPlayer player, SkillStats skills)
	{
		skills.writeNBT(player.getEntityData());
	}

	public static boolean isTopFaceSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (world.getBlockId(x, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getTopChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isBottomFaceSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (world.getBlockId(x, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getBottomChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isNorthSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z - 1))
			return true;
		else if (world.getBlockId(x, y, z - 1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z - 1);
			if (BlockSlab.getNorthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isNorthFaceSolid(World world, int x, int y, int z)
	{
		int bid = world.getBlockId(x, y, z);
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (bid == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getNorthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isSouthSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z + 1))
			return true;
		else if (world.getBlockId(x, y, z + 1) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z + 1);
			if (BlockSlab.getSouthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isSouthFaceSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (world.getBlockId(x, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getSouthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isEastSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x - 1, y, z))
			return true;
		else if (world.getBlockId(x - 1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x - 1, y, z);
			if (BlockSlab.getEastChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isEastFaceSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (world.getBlockId(x, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getEastChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isWestSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x + 1, y, z))
			return true;
		else if (world.getBlockId(x + 1, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x + 1, y, z);
			if (BlockSlab.getWestChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isWestFaceSolid(World world, int x, int y, int z)
	{
		if (world.isBlockNormalCube(x, y, z))
			return true;
		else if (world.getBlockId(x, y, z) == TFCBlocks.stoneSlabs.blockID)
		{
			TileEntityPartial te = (TileEntityPartial) world.getBlockTileEntity(x, y, z);
			if (BlockSlab.getWestChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isOreIron(ItemStack is)
	{
		if (is.getItem() instanceof ItemOre && ((ItemOre) is.getItem()).GetMetalType(is) == Global.PIGIRON)
			return true;
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
		float percent = (time - birth) / animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public static void bindTexture(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public static boolean isPlayerInDebugMode(EntityPlayer player)
	{
		if (TFCOptions.enableDebugMode)
			return true;
		return false;
	}

	/**
	 * Adds exhaustion to the player. 0.001 is a standard amount.
	 */
	public static void addPlayerExhaustion(EntityPlayer player, float exhaustion)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		foodstats.addFoodExhaustion(exhaustion);
		foodstats.addWaterExhaustion(exhaustion);
		TFC_Core.setPlayerFoodStats(player, foodstats);
	}

	public static float getEnvironmentalDecay(float temp)
	{
		if (temp > 0)
		{
			float tempFactor = 1f - (15f / (15f + temp));
			return tempFactor * 2;
		}
		else
			return 0;
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z)
	{
		handleItemTicking(iinv, world, x, y, z, 1);
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z)
	{
		handleItemTicking(iinv, world, x, y, z, 1);
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		for (int i = 0; !world.isRemote && i < iinv.getSizeInventory(); i++)
		{
			ItemStack is = iinv.getStackInSlot(i);
			if (is != null && iinv.getStackInSlot(i).stackSize <= 0)
				iinv.setInventorySlotContents(i, null);

			if (is != null)
			{
				if ((is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z)))
				{
					continue;
				} else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
				{
					continue;
				}
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor);
				if(is != null)
					TFC_ItemHeat.HandleItemHeat(is);
			}
			iinv.setInventorySlotContents(i, is);
		}
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		for (int i = 0; !world.isRemote && i < iinv.length; i++)
		{
			ItemStack is = iinv[i];
			if (is != null && iinv[i].stackSize <= 0)
				iinv[i] = null;

			if (is != null)
			{
				if ((is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z)))
				{
					continue;
				} else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
				{
					continue;
				}
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor);
				if(is != null)
					TFC_ItemHeat.HandleItemHeat(is);
			}
			iinv[i] = is;
		}
	}

	/**
	 * @param is
	 * @param nbt
	 */
	private static ItemStack tickDecay(ItemStack is, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt == null || !nbt.hasKey("foodWeight") || !nbt.hasKey("foodDecay"))
			return is;

		// if the tick timer is up then we cause decay.
		if (nbt.getInteger("decayTimer") < TFC_Time.getTotalHours())
		{
			int timeDiff = (int) (TFC_Time.getTotalHours() - nbt.getInteger("decayTimer"));
			float protMult = 1;

			if(TFCOptions.useDecayProtection)
			{
				if(timeDiff > TFCOptions.decayProtectionDays * 24)
				{
					nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() - 24);
				}
				else if(timeDiff > 24)
				{
					protMult = 1-(timeDiff/(TFCOptions.decayProtectionDays * 24));
				}
			}

			float decay = nbt.getFloat("foodDecay");
			float thisDecayRate = 1.0f;
			// Get the base food decay rate
			if (is.getItem() instanceof IFood)
				thisDecayRate = ((IFood) is.getItem()).getDecayRate();
			// check if the food has a specially applied decay rate in its nbt
			// for some reason
			if (nbt.hasKey("decayRate"))
				thisDecayRate = nbt.getFloat("decayRate");
			// if the food is salted then we cut the decay rate in half
			if (nbt.hasKey("isSalted"))
				thisDecayRate *= 0.5f;

			/*
			 * Here we calculate the decayRate based on the environment. We do
			 * this before everything else so that its only done once per
			 * inventory
			 */
			int day = TFC_Time.getDayOfYearFromDays(TFC_Time.getDayFromTotalHours(nbt.getInteger("decayTimer")));
			float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(day,nbt.getInteger("decayTimer"), x, y, z);
			float environmentalDecay = getEnvironmentalDecay(temp) * environmentalDecayFactor;

			if (decay < 0)
			{
				float d = 1 * (thisDecayRate * environmentalDecay);
				if (decay + d < 0)
					decay += d;
				else
					decay = 0;
			} 
			else if (decay == 0)
			{
				decay = (nbt.getFloat("foodWeight") * (world.rand.nextFloat() * 0.005f))*TFCOptions.decayMultiplier;
			} 
			else
			{
				double fdr = Global.FOOD_DECAY_RATE - 1;
				fdr *= thisDecayRate * environmentalDecay * protMult * TFCOptions.decayMultiplier;
				decay *= 1 + fdr;
			}
			nbt.setInteger("decayTimer", nbt.getInteger("decayTimer") + 1);
			nbt.setFloat("foodDecay", decay);
		}

		if (nbt.getFloat("foodDecay") / nbt.getFloat("foodWeight") > 0.9f)
		{
			if(is.getItem() instanceof IFood)
			{
				is = ((IFood)is.getItem()).onDecayed(is, world, x, y, z);
			}
			else
				is.stackSize = 0;
		}
		else
			is.setTagCompound(nbt);

		return is;
	}

	public static void animalDropMeat(Entity e, Item i, float foodWeight)
	{
		while (foodWeight > 0)
		{
			float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, foodWeight), 10);
			if (fw < Global.FOOD_MAX_WEIGHT)
				foodWeight = 0;
			foodWeight -= fw;

			e.entityDropItem(ItemFoodTFC.createTag(new ItemStack(i, 1), fw), 0);
		}
	}

	public static Vec3 getEntityPos(Entity e)
	{
		return Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
	}

	public static void giveItemToPlayer(ItemStack is, EntityPlayer player)
	{
		if(!player.inventory.addItemStackToInventory(is))
			player.entityDropItem(is, 1);
	}

	public static boolean isFence(int id)
	{
		return (id == TFCBlocks.Fence.blockID || id == TFCBlocks.Fence2.blockID);

	}
}
