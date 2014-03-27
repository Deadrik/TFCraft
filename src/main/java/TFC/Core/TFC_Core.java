package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.Player.BodyTempStats;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.SkillStats;
import TFC.Items.ItemOre;
import TFC.Items.ItemQuiver;
import TFC.Items.ItemTFCArmor;
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
		if(TerraFirmaCraft.proxy.getCurrentWorld().getBlock(i, j+1, k).isOpaqueCube())
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
					par1 = 1.0F;

				for (int i = 0; i < this.armorInventory.length; ++i)
				{
					if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor && !(this.armorInventory[i].getItem() instanceof ItemQuiver))
					{
						this.armorInventory[i].damageItem((int)par1, this.player);
						if (this.armorInventory[i].stackSize == 0)
							this.armorInventory[i] = null;
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
					NBTTagCompound nbttagcompound = par1NBTTagList.getCompoundTagAt(i);
					int j = nbttagcompound.getByte("Slot") & 255;
					ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
					if (itemstack != null)
					{
						if (j >= 0 && j < this.mainInventory.length)
							this.mainInventory[j] = itemstack;
						if (j >= 100 && j < /*this.armorInventory.length*/4 + 100)
							this.armorInventory[j - 100] = itemstack;
					}
				}
				int j2 = player.getEntityData().getByte("Head") & 255;
				ItemStack itemstack2 = ItemStack.loadItemStackFromNBT(player.getEntityData());
				if(itemstack2!=null && itemstack2.getItem() instanceof ItemTFCArmor && ((ItemTFCArmor)(itemstack2.getItem())).getUnadjustedArmorType() == 0)
					this.armorInventory[4] = itemstack2;
			}

			public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
			{
				int i;
				NBTTagCompound nbttagcompound;

				for (i = 0; i < this.mainInventory.length; ++i)
				{
					if (this.mainInventory[i] != null)
					{
						nbttagcompound = new NBTTagCompound();
						nbttagcompound.setByte("Slot", (byte)i);
						this.mainInventory[i].writeToNBT(nbttagcompound);
						par1NBTTagList.appendTag(nbttagcompound);
					}
				}
				for (i = 0; i < /*this.armorInventory.length*/4; ++i)
				{
					if (this.armorInventory[i] != null)
					{
						nbttagcompound = new NBTTagCompound();
						nbttagcompound.setByte("Slot", (byte)(i + 100));
						this.armorInventory[i].writeToNBT(nbttagcompound);
						par1NBTTagList.appendTag(nbttagcompound);
					}
				}
				//Shitty workaround
				nbttagcompound = player.getEntityData();
				nbttagcompound.setByte("Head", (byte)(4 + 100));
				if(this.armorInventory[4]!=null)
				{
					this.armorInventory[4].writeToNBT(nbttagcompound);
					//par1NBTTagList.appendTag(nbttagcompound);
				}
				else
				{
					ItemStack is = new ItemStack(Item.getItemById(0),0,0);
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
			for (int x = 2; x >= -2; x--)
				for (int z = 2; z >= -2; z--)
					if(world.isAirBlock(i+x, j+y, k+z))
						world.setBlock(i+x, j+y, k+z, Blocks.leaves, meta, 2);
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
		Block block = world.getBlock(x, y, z);
		return (block == TFCBlocks.StoneIgEx
			|| block == TFCBlocks.StoneIgIn
			|| block == TFCBlocks.StoneSed
			|| block == TFCBlocks.StoneMM
		);
	}

	public static boolean isSmoothStone(World world,int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return block == TFCBlocks.StoneIgExSmooth
			|| block == TFCBlocks.StoneIgInSmooth
			|| block == TFCBlocks.StoneSedSmooth
			|| block == TFCBlocks.StoneMMSmooth;
	}

	public static boolean isSmoothStone(Block block)
	{
		return block == TFCBlocks.StoneIgExSmooth
			|| block == TFCBlocks.StoneIgInSmooth
			|| block == TFCBlocks.StoneSedSmooth
			|| block == TFCBlocks.StoneMMSmooth;
	}

	public static boolean isBrickStone(Block block)
	{
		return block == TFCBlocks.StoneIgExBrick
			|| block == TFCBlocks.StoneIgInBrick
			|| block == TFCBlocks.StoneSedBrick
			|| block == TFCBlocks.StoneMMBrick;
	}

	public static boolean isRawStone(Block block)
	{
		return block == TFCBlocks.StoneIgEx
			|| block == TFCBlocks.StoneIgIn
			|| block == TFCBlocks.StoneSed
			|| block == TFCBlocks.StoneMM;
	}

	public static boolean isDirt(Block block)
	{
		return block == TFCBlocks.Dirt
			|| block == TFCBlocks.Dirt2;
	}

	public static boolean isFarmland(Block block)
	{
		return block == TFCBlocks.tilledSoil
			|| block == TFCBlocks.tilledSoil2;
	}

	public static boolean isGrass(Block block)
	{
		return block == TFCBlocks.Grass
			|| block == TFCBlocks.Grass2
			|| block == TFCBlocks.ClayGrass
			|| block == TFCBlocks.ClayGrass2
			|| block == TFCBlocks.PeatGrass
			|| block == TFCBlocks.DryGrass
			|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isLushGrass(Block block)
	{
		return block == TFCBlocks.Grass
			|| block == TFCBlocks.Grass2
			|| block == TFCBlocks.ClayGrass
			|| block == TFCBlocks.ClayGrass2
			|| block == TFCBlocks.PeatGrass;
	}

	public static boolean isClayGrass(Block block)
	{
		return block == TFCBlocks.ClayGrass
			|| block == TFCBlocks.ClayGrass2;
	}

	public static boolean isPeatGrass(Block block)
	{
		return block == TFCBlocks.PeatGrass;
	}

	public static boolean isDryGrass(Block block)
	{
		return block == TFCBlocks.DryGrass
			|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isGrassType1(Block block)
	{
		return block == TFCBlocks.Grass
			|| block == TFCBlocks.ClayGrass
			|| block == TFCBlocks.DryGrass;
	}

	public static boolean isGrassType2(Block block)
	{
		return block == TFCBlocks.Grass2
			|| block == TFCBlocks.ClayGrass2
			|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isClay(Block block)
	{
		if(block == TFCBlocks.Clay || block == TFCBlocks.Clay2)
			return true;
		return false;
	}

	public static boolean isSand(Block block)
	{
		if(block == TFCBlocks.Sand || block == TFCBlocks.Sand2)
			return true;
		return false;
	}

	public static boolean isPeat(Block block)
	{
		if(block == TFCBlocks.Peat)
			return true;
		return false;
	}

	public static boolean isWater(Block block)
	{
		return isSaltWater(block) || isFreshWater(block);
	}
	
	public static boolean isSaltWater(Block block)
	{
		if(block == Blocks.water || block == Blocks.flowing_water)
			return true;
		return false;
	}
	
	public static boolean isSaltWaterIncludeIce(Block block, int meta, Material mat)
	{
		if(block == Blocks.water || block == Blocks.flowing_water || (mat == Material.ice && meta == 0))
			return true;
		return false;
	}
	
	public static boolean isFreshWater(Block block)
	{
		if(block == TFCBlocks.FreshWaterFlowing || block == TFCBlocks.FreshWaterStill)
			return true;
		return false;
	}
	
	public static boolean isFreshWaterIncludeIce(Block block, int meta)
	{
		if(block == TFCBlocks.FreshWaterFlowing || block == TFCBlocks.FreshWaterStill || (block == Blocks.ice) && meta != 0)
			return true;
		return false;
	}
	
	public static boolean isFreshWaterIncludeIce(Block block, int meta, Material mat)
	{
		if(block == TFCBlocks.FreshWaterFlowing || block == TFCBlocks.FreshWaterStill || (mat == Material.ice && meta != 0))
			return true;
		return false;
	}
	
	public static boolean isWaterStill(Block block)
	{
		if(block == Blocks.water || block == Blocks.flowing_water)
			return true;
		return false;
	}
	
	public static boolean isWaterMoving(Block block)
	{
		if(block == TFCBlocks.FreshWaterFlowing || block == Blocks.flowing_water)
			return true;
		return false;
	}

	public static boolean isSoil(Block block)
	{
		return isGrass(block) || isDirt(block) || isClay(block) || isPeat(block);
	}
	
	public static boolean isGravel(Block block)
	{
		return block == Blocks.gravel;
	}

	public static boolean isGround(Block block)
	{
		return isSoil(block)|| isRawStone(block) || isSand(block);
	}

	public static int getSoilMetaFromStone(Block inBlock, int inMeta)
	{
		if(inBlock == TFCBlocks.StoneIgIn)
			return inMeta;
		else if(inBlock == TFCBlocks.StoneSed)
			return inMeta+3;
		else if(inBlock == TFCBlocks.StoneIgEx)
			return inMeta+11;
		else
		{
			if(inMeta == 0)
				return inMeta+15;
			return inMeta;
		}
	}

	public static int getItemMetaFromStone(Block inBlock, int inMeta)
	{
		if(inBlock == TFCBlocks.StoneIgIn)
			return inMeta;
		else if(inBlock == TFCBlocks.StoneSed)
			return inMeta+3;
		else if(inBlock == TFCBlocks.StoneIgEx)
			return inMeta+11;
		else if(inBlock == TFCBlocks.StoneMM)
			return inMeta+15;
		else
			return 0;
	}

	public static Block getTypeForGrassWithRain(int inMeta, float rain)
	{
		if(rain >= 500)
			return getTypeForGrass(inMeta);
		return getTypeForDryGrass(inMeta);
	}

	public static Block getTypeForGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Grass;
		return TFCBlocks.Grass2;
	}

	public static Block getTypeForGrassFromDirt(Block block)
	{
		if(block == TFCBlocks.Dirt)
			return TFCBlocks.Grass;
		return TFCBlocks.Grass2;
	}

	public static Block getTypeForDryGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.DryGrass;
		return TFCBlocks.DryGrass2;
	}

	public static Block getTypeForClayGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.ClayGrass;
		return TFCBlocks.ClayGrass2;
	}

	public static Block getTypeForDirt(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Dirt;
		return TFCBlocks.Dirt2;
	}

	public static Block getTypeForClay(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Clay;
		return TFCBlocks.Clay2;
	}

	public static Block getTypeForSand(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Sand;
		return TFCBlocks.Sand2;
	}

	public static int getRockLayerFromHeight(World world, int x, int y, int z)
	{
		int[] hm = ChunkDataManager.getData(x >> 4, z >> 4).heightmap;
		int localX = x & 15;
		int localZ = z & 15;
		int localY = localX + localZ * 16;
		if(y <= TFCOptions.RockLayer3Height + hm[localY])
			return 2;
		else if(y <= TFCOptions.RockLayer2Height + hm[localY])
			return 1;
		else
			return 0;
	}

	public static boolean convertGrassToDirt(World world, int i, int j, int k)
	{
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		if(TFC_Core.isGrass(block))
			if(TFC_Core.isGrassType1(block))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt, meta, 2);
				return true;
			}
			else if(TFC_Core.isGrassType2(block))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt2, meta, 2);
				return true;
			}
		return false;
	}

	public static EnumWoodMaterial getWoodMaterial(ItemStack is)
	{
		if(is.getItem() == Item.getItemFromBlock(TFCBlocks.Peat))
			return EnumWoodMaterial.PEAT;
		if(is.getItem() == Item.getItemFromBlock(TFCBlocks.LogNatural2))
			if(is.getItemDamage() == 0)
				return EnumWoodMaterial.ACACIA;

		else if(is.getItemDamage() == 0)
			return EnumWoodMaterial.ASH;
		else if(is.getItemDamage() == 1)
			return EnumWoodMaterial.ASPEN;
		else if(is.getItemDamage() == 2)
			return EnumWoodMaterial.BIRCH;
		else if(is.getItemDamage() == 3)
			return EnumWoodMaterial.CHESTNUT;
		else if(is.getItemDamage() == 4)
			return EnumWoodMaterial.DOUGLASFIR;
		else if(is.getItemDamage() == 5)
			return EnumWoodMaterial.HICKORY;
		else if(is.getItemDamage() == 6)
			return EnumWoodMaterial.MAPLE;
		else if(is.getItemDamage() == 7)
			return EnumWoodMaterial.OAK;
		else if(is.getItemDamage() == 8)
			return EnumWoodMaterial.PINE;
		else if(is.getItemDamage() == 9)
			return EnumWoodMaterial.REDWOOD;
		else if(is.getItemDamage() == 10)
			return EnumWoodMaterial.SPRUCE;
		else if(is.getItemDamage() == 11)
			return EnumWoodMaterial.SYCAMORE;
		else if(is.getItemDamage() == 12)
			return EnumWoodMaterial.WHITECEDAR;
		else if(is.getItemDamage() == 13)
			return EnumWoodMaterial.WHITEELM;
		else if(is.getItemDamage() == 14)
			return EnumWoodMaterial.WILLOW;
		else if(is.getItemDamage() == 15)
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
		FoodStatsTFC foodstats = new FoodStatsTFC();
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

	public static boolean isNorthSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z-1).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z-1) == TFCBlocks.stoneSlabs)
		{
			TileEntityPartial te = (TileEntityPartial) world.getTileEntity(x, y, z-1);
			if(BlockSlab.getNorthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isSouthSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z+1).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z+1) == TFCBlocks.stoneSlabs)
		{
			TileEntityPartial te = (TileEntityPartial) world.getTileEntity(x, y, z+1);
			if(BlockSlab.getSouthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isEastSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x-1, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x-1, y, z) == TFCBlocks.stoneSlabs)
		{
			TileEntityPartial te = (TileEntityPartial) world.getTileEntity(x-1, y, z);
			if(BlockSlab.getEastChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isWestSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x+1, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x+1, y, z) == TFCBlocks.stoneSlabs)
		{
			TileEntityPartial te = (TileEntityPartial) world.getTileEntity(x+1, y, z);
			if(BlockSlab.getWestChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		return false;
	}

	public static boolean isOreIron(ItemStack is)
	{
		if(is.getItem() instanceof ItemOre && ((ItemOre)is.getItem()).GetMetalType(is) == Global.PIGIRON)
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
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public static void bindTexture(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}
}
