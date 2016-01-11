package com.bioxx.tfc.WAILA;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Blocks.BlockCharcoal;
import com.bioxx.tfc.Blocks.BlockMetalTrapDoor;
import com.bioxx.tfc.Blocks.BlockPartial;
import com.bioxx.tfc.Blocks.Devices.BlockAnvil;
import com.bioxx.tfc.Blocks.Flora.BlockBerryBush;
import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Blocks.Flora.BlockWaterPlant;
import com.bioxx.tfc.Blocks.Terrain.*;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.bioxx.tfc.Blocks.Vanilla.BlockTorch;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.*;
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.Items.ItemGem;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.TileEntities.*;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.*;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WAILAData implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();

		World world = accessor.getWorld();
		MovingObjectPosition pos = accessor.getPosition();

		if (block instanceof BlockAnvil)
			return anvilStack(accessor, config);

		else if (tileEntity instanceof TEBerryBush)
			return berryBushStack(accessor, config);

		else if (tileEntity instanceof TEBloom)
			return new ItemStack(TFCItems.rawBloom);

		else if (block instanceof BlockCharcoal)
			return new ItemStack(TFCItems.coal, accessor.getMetadata(), 1);

		else if (TFC_Core.isClay(block) || TFC_Core.isClayGrass(block))
			return new ItemStack(TFCItems.clayBall);

		else if (block instanceof BlockCobble)
			return new ItemStack(block, 1, accessor.getMetadata() % 8);

		else if (tileEntity instanceof TECrop)
			return cropStack(accessor, config);

		else if (block instanceof BlockCustomDoor)
			return new ItemStack(Recipes.doors[((BlockCustomDoor) block).getWoodType() / 2]);

		else if (tileEntity instanceof TEFruitLeaves)
			return fruitLeavesStack(accessor, config);

		else if (tileEntity instanceof TEFruitTreeWood)
			return fruitTreeWoodStack(accessor, config);

		else if (tileEntity instanceof TEIngotPile)
			return ingotPileStack(accessor, config);

		else if (tileEntity instanceof TELoom)
			return loomStack(accessor, config);

		else if (tileEntity instanceof TEMetalSheet)
			return metalSheetStack(accessor, config);

		else if (tileEntity instanceof TEMetalTrapDoor)
			return metalTrapDoorStack(accessor, config);

		else if (tileEntity instanceof TEOilLamp)
			return oilLampStack(accessor, config);

		else if (tileEntity instanceof TEOre)
			return oreStack(accessor, config);

		else if (block instanceof BlockPartial)
			return partialStack(accessor, config);

		else if (block instanceof BlockWaterPlant && TFC_Core.isSaltWater(world.getBlock(pos.blockX, pos.blockY + 1, pos.blockZ))) // Sea Weed
			return ItemFoodTFC.createTag(new ItemStack(TFCItems.seaWeed, 1, 0));

		else if (tileEntity instanceof TEWorldItem)
			return worldItemStack(accessor, config);

		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();

		if (tileEntity instanceof TEBarrel)
			currenttip = barrelHead(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEFruitLeaves)
			currenttip = fruitLeavesHead(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEFruitTreeWood)
			currenttip = fruitTreeWoodHead(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEOre)
			currenttip = oreHead(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TESmokeRack)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("tile.SmokeRack.name"));

		else if (block instanceof BlockWaterPlant)
			currenttip = waterPlantHead(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();
		if (tileEntity instanceof TEAnvil)
			currenttip = anvilBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEBarrel)
			currenttip = barrelBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEBerryBush)
			currenttip = berryBushBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEBlastFurnace)
			currenttip = blastFurnaceBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEBloom)
			currenttip = bloomBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEBloomery)
			currenttip = bloomeryBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TECrop)
			currenttip = cropBody(itemStack, currenttip, accessor, config);

		// I haven't decided if this is OP or not. Useful for debugging though, so uncomment when needing to check farmland nutrient %.
		/*else if (tileEntity instanceof TEFarmland)
			currenttip = farmlandBody(itemStack, currenttip, accessor, config);*/

		else if (tileEntity instanceof TEFirepit)
			currenttip = firepitBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEForge)
			currenttip = forgeBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEFruitLeaves)
			currenttip = fruitLeavesBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TELogPile)
			currenttip = logPileBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TELoom)
			currenttip = loomBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEMetalTrapDoor)
			currenttip = metalTrapDoorBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TENestBox)
			currenttip = nestBoxBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEOilLamp)
			currenttip = oilLampBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEOre)
			currenttip = oreBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TEPottery)
			currenttip = potteryBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TESapling)
			currenttip = saplingBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TESluice)
			currenttip = sluiceBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TESmokeRack)
			currenttip = smokeRackBody(itemStack, currenttip, accessor, config);

		else if (TFC_Core.isSoilWAILA(block))
			currenttip = soilBody(itemStack, currenttip, accessor, config);

		else if (tileEntity instanceof TESpawnMeter)
			currenttip = spawnMeterBody(itemStack, currenttip, accessor, config);

		else if (block == TFCBlocks.torch)
			currenttip = torchBody(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.addConfig("TerraFirmaCraft", "tfc.oreQuality");

		reg.registerStackProvider(new WAILAData(), BlockAnvil.class);
		reg.registerBodyProvider(new WAILAData(), TEAnvil.class);
		reg.registerNBTProvider(new WAILAData(), TEAnvil.class);

		reg.registerHeadProvider(new WAILAData(), TEBarrel.class);
		reg.registerBodyProvider(new WAILAData(), TEBarrel.class);
		reg.registerNBTProvider(new WAILAData(), TEBarrel.class);

		reg.registerStackProvider(new WAILAData(), TEBerryBush.class);
		reg.registerBodyProvider(new WAILAData(), TEBerryBush.class);
		reg.registerNBTProvider(new WAILAData(), TEBerryBush.class);

		reg.registerBodyProvider(new WAILAData(), TEBlastFurnace.class);
		reg.registerNBTProvider(new WAILAData(), TEBlastFurnace.class);

		reg.registerStackProvider(new WAILAData(), TEBloom.class);
		reg.registerBodyProvider(new WAILAData(), TEBloom.class);
		reg.registerNBTProvider(new WAILAData(), TEBloom.class);

		reg.registerBodyProvider(new WAILAData(), TEBloomery.class);
		reg.registerNBTProvider(new WAILAData(), TEBloomery.class);

		reg.registerStackProvider(new WAILAData(), BlockCharcoal.class);
		reg.registerStackProvider(new WAILAData(), BlockClay.class);
		reg.registerStackProvider(new WAILAData(), BlockClayGrass.class);
		reg.registerStackProvider(new WAILAData(), BlockCobble.class);

		reg.registerStackProvider(new WAILAData(), TECrop.class);
		reg.registerBodyProvider(new WAILAData(), TECrop.class);
		reg.registerNBTProvider(new WAILAData(), TECrop.class);

		reg.registerStackProvider(new WAILAData(), BlockCustomDoor.class);

		reg.registerBodyProvider(new WAILAData(), TEFarmland.class);
		reg.registerNBTProvider(new WAILAData(), TEFarmland.class);

		reg.registerBodyProvider(new WAILAData(), TEFirepit.class);
		reg.registerNBTProvider(new WAILAData(), TEFirepit.class);

		reg.registerBodyProvider(new WAILAData(), TEForge.class);
		reg.registerNBTProvider(new WAILAData(), TEForge.class);

		reg.registerStackProvider(new WAILAData(), TEFruitLeaves.class);
		reg.registerHeadProvider(new WAILAData(), TEFruitLeaves.class);
		reg.registerBodyProvider(new WAILAData(), TEFruitLeaves.class);
		reg.registerNBTProvider(new WAILAData(), TEFruitLeaves.class);

		reg.registerStackProvider(new WAILAData(), TEFruitTreeWood.class);
		reg.registerHeadProvider(new WAILAData(), TEFruitTreeWood.class);

		reg.registerStackProvider(new WAILAData(), TEIngotPile.class);
		reg.registerHeadProvider(new WAILAData(), TEIngotPile.class);
		reg.registerNBTProvider(new WAILAData(), TEIngotPile.class);

		reg.registerBodyProvider(new WAILAData(), TELogPile.class);
		reg.registerNBTProvider(new WAILAData(), TELogPile.class);

		reg.registerStackProvider(new WAILAData(), TELoom.class);
		reg.registerBodyProvider(new WAILAData(), TELoom.class);
		reg.registerNBTProvider(new WAILAData(), TELoom.class);

		reg.registerStackProvider(new WAILAData(), TEMetalSheet.class);
		reg.registerNBTProvider(new WAILAData(), TEMetalSheet.class);

		reg.registerStackProvider(new WAILAData(), TEMetalTrapDoor.class);
		reg.registerBodyProvider(new WAILAData(), TEMetalTrapDoor.class);
		reg.registerNBTProvider(new WAILAData(), TEMetalTrapDoor.class);

		reg.registerBodyProvider(new WAILAData(), TENestBox.class);
		reg.registerNBTProvider(new WAILAData(), TENestBox.class);

		reg.registerStackProvider(new WAILAData(), TEOilLamp.class);
		reg.registerBodyProvider(new WAILAData(), TEOilLamp.class);
		reg.registerNBTProvider(new WAILAData(), TEOilLamp.class);

		reg.registerStackProvider(new WAILAData(), TEOre.class);
		reg.registerHeadProvider(new WAILAData(), TEOre.class);
		reg.registerBodyProvider(new WAILAData(), TEOre.class);

		reg.registerStackProvider(new WAILAData(), BlockPartial.class);
		reg.registerNBTProvider(new WAILAData(), BlockPartial.class);

		reg.registerBodyProvider(new WAILAData(), TEPottery.class);
		reg.registerNBTProvider(new WAILAData(), TEPottery.class);

		reg.registerBodyProvider(new WAILAData(), TESapling.class);
		reg.registerNBTProvider(new WAILAData(), TESapling.class);

		reg.registerBodyProvider(new WAILAData(), TESluice.class);
		reg.registerNBTProvider(new WAILAData(), TESluice.class);

		reg.registerHeadProvider(new WAILAData(), TESmokeRack.class);
		reg.registerBodyProvider(new WAILAData(), TESmokeRack.class);
		reg.registerNBTProvider(new WAILAData(), TESmokeRack.class);

		reg.registerBodyProvider(new WAILAData(), TESpawnMeter.class);
		reg.registerNBTProvider(new WAILAData(), TESpawnMeter.class);

		// Soil
		reg.registerBodyProvider(new WAILAData(), BlockDirt.class);
		reg.registerBodyProvider(new WAILAData(), BlockSand.class);
		reg.registerBodyProvider(new WAILAData(), BlockGrass.class);
		reg.registerBodyProvider(new WAILAData(), BlockGravel.class);

		reg.registerBodyProvider(new WAILAData(), BlockTorch.class);
		reg.registerNBTProvider(new WAILAData(), BlockTorch.class);

		reg.registerStackProvider(new WAILAData(), BlockWaterPlant.class);
		reg.registerHeadProvider(new WAILAData(), BlockWaterPlant.class);

		reg.registerStackProvider(new WAILAData(), TEWorldItem.class);
		reg.registerNBTProvider(new WAILAData(), TEWorldItem.class);
	}

	// Stacks
	public ItemStack anvilStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		Block block = accessor.getBlock();
		int meta = accessor.getMetadata();
		int type = BlockAnvil.getAnvilTypeFromMeta(meta);

		return new ItemStack(block, 1, type);
	}

	public ItemStack berryBushStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean hasFruit = tag.getBoolean("hasFruit");
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockBerryBush.metaNames[accessor.getMetadata()]);

		if (hasFruit)
			return ItemFoodTFC.createTag(index.getOutput());
		else
			return null;
	}

	public ItemStack cropStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int cropId = tag.getInteger("cropId");

		CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
		ItemStack itemstack;

		if (crop.output2 != null)
			itemstack = new ItemStack(crop.output2);
		else
			itemstack = new ItemStack(crop.output1);

		ItemFoodTFC.createTag(itemstack);
		return itemstack;
	}

	public ItemStack fruitLeavesStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata() % 8));

		if (index != null)
			return ItemFoodTFC.createTag(index.getOutput());
		else
			return null;
	}

	public ItemStack fruitTreeWoodStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitWood.getType(accessor.getMetadata()));

		if (index != null)
			return ItemFoodTFC.createTag(index.getOutput());
		else
			return null;
	}

	public ItemStack ingotPileStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		if (storage[0] != null)
			return storage[0];

		return null;
	}

	public ItemStack loomStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean finished = tag.getBoolean("finished");
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		if (finished && storage[1] != null)
		{
			return storage[1];
		}
		return null;
	}

	public ItemStack metalSheetStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		return ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));
	}

	public ItemStack metalTrapDoorStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		return ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));
	}

	public ItemStack oilLampStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();
		if ((meta & 8) != 0)
			meta -= 8;

		return new ItemStack(TFCBlocks.oilLamp, 1, meta);
	}

	public ItemStack oreStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();
		TEOre te = (TEOre) accessor.getTileEntity();
		ItemStack itemstack = null;

		if (accessor.getBlock() == TFCBlocks.ore) // Metals & Coal
		{
			if (config.getConfig("tfc.oreQuality"))
				itemstack = new ItemStack(TFCItems.oreChunk, 1, getOreGrade(te, meta)); // Shows specific quality ore.
			else
				itemstack = new ItemStack(TFCItems.oreChunk, 1, meta); // All normal quality ores.

			if (meta == 14 || meta == 15) // Bituminous Coal & Lignite
				itemstack = new ItemStack(TFCItems.coal);

			return itemstack;
		}
		else if (accessor.getBlock() == TFCBlocks.ore2) // Minerals
		{
			itemstack = new ItemStack(TFCItems.oreChunk, 1, meta + Global.ORE_METAL.length);
			if (meta == 5)
				itemstack = new ItemStack(TFCItems.gemDiamond); // Kaolinite
			else if (meta == 13)
				itemstack = new ItemStack(TFCItems.powder, 1, 4); // Saltpeter

			return itemstack;
		}
		else if (accessor.getBlock() == TFCBlocks.ore3) // Minerals
		{
			itemstack = new ItemStack(TFCItems.oreChunk, 1, meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length);
			return itemstack;
		}

		return null;
	}

	public ItemStack partialStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		byte metaID = tag.getByte("metaID");
		int typeID = tag.getShort("typeID");

		return new ItemStack(Block.getBlockById(typeID), 1, metaID);
	}

	public ItemStack worldItemStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
		return storage[0];
	}

	// Heads
	public List<String> barrelHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		String head = currenttip.get(0);
		NBTTagCompound tag = accessor.getNBTData();
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluidNBT"));

		if (fluid != null)
		{
			head += " (" + fluid.getLocalizedName() + ")";
			currenttip.set(0, head);
		}
		return currenttip;
	}

	public List<String> fruitLeavesHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean hasFruit = tag.getBoolean("hasFruit");
		String type = BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata());

		if (!hasFruit)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("gui." + type));

		return currenttip;
	}

	public List<String> fruitTreeWoodHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		String type = BlockFruitWood.getType(accessor.getMetadata());

		currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("gui." + type));

		return currenttip;
	}

	public List<String> ingotPileHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		String head = currenttip.get(0);
		currenttip.set(0, head + " " + TFC_Core.translate("gui.pile"));
		return currenttip;
	}

	public List<String> oreHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();

		if (accessor.getBlock() == TFCBlocks.ore)
		{
			if (meta == 14)
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("item.Ore.Bituminous Coal.name"));
			}
			else if (meta == 15)
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("item.Ore.Lignite.name"));
			}
		}
		else if (accessor.getBlock() == TFCBlocks.ore2)
		{
			if (meta == 5)
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("item.Ore.Kimberlite.name"));
			}
			else if (meta == 13)
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("item.Ore.Saltpeter.name"));
			}
		}

		return currenttip;
	}

	public List<String> waterPlantHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		MovingObjectPosition pos = accessor.getPosition();
		World world = accessor.getWorld();
		Block blockDirectlyAbove = world.getBlock(pos.blockX, pos.blockY + 1, pos.blockZ);
		boolean airTwoAbove = world.isAirBlock(pos.blockX, pos.blockY + 2, pos.blockZ);

		if (TFC_Core.isFreshWater(blockDirectlyAbove))
		{
			if (airTwoAbove)
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("tile.Flora.Cat Tails.name"));
			}
			else
			{
				currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("tile.Flora.Pond Weed.name"));
			}
		}

		return currenttip;
	}

	// Bodies
	public List<String> anvilBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();

		int tier = tag.getInteger("Tier");
		currenttip.add(TFC_Core.translate("gui.tier") + " : " + tier);

		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
		ItemStack flux = storage[TEAnvil.FLUX_SLOT];

		if (flux != null && flux.getItem() == TFCItems.powder && flux.getItemDamage() == 0 && flux.stackSize > 0)
			currenttip.add(TFC_Core.translate("item.Powder.Flux.name") + " : " + flux.stackSize);

		return currenttip;
	}

	public List<String> barrelBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TEBarrel te = (TEBarrel) accessor.getTileEntity();
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, te);
		ItemStack inStack = storage[TEBarrel.INPUT_SLOT];

		Boolean sealed = te.getSealed();
		int sealTime = accessor.getNBTInteger(tag, "SealTime");
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluidNBT"));
		BarrelRecipe recipe = BarrelManager.getInstance().findMatchingRecipe(inStack, fluid, sealed, te.getTechLevel());

		if (sealed && fluid != null && fluid.getFluid() == TFCFluids.MILKCURDLED && (inStack == null ||
				inStack.getItem() instanceof IFood && ((IFood) inStack.getItem()).getFoodGroup() != EnumFoodGroup.Dairy && ((IFood) inStack.getItem()).isEdible(inStack) && Food.getWeight(inStack) <= 20.0f))
			recipe = new BarrelRecipe(null, new FluidStack(TFCFluids.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese, 1), 160), null).setMinTechLevel(0);

		// Fluid Amount
		if (fluid != null)
		{
			currenttip.add(fluid.amount + "/" + te.getMaxLiquid() + " mB");
		}

		// Sealed Date
		if (sealed && sealTime != 0)
		{
			currenttip.add(TFC_Core.translate("gui.Barrel.SealedOn") + " : " + TFC_Time.getDateStringFromHours(sealTime));
		}


		// Output
		BarrelPreservativeRecipe preservative = BarrelManager.getInstance().findMatchingPreservativeRepice(te, inStack, fluid, sealed);


		if (recipe != null)
		{
			if (!(recipe instanceof BarrelBriningRecipe))
			{
				currenttip.add(TFC_Core.translate("gui.Output") + " : " + recipe.getRecipeName());
			}
			else if (sealed && fluid != null && fluid.getFluid() == TFCFluids.BRINE)
			{
				if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.cheese) && !Food.isBrined(inStack))
				{
					currenttip.add(TFC_Core.translate("gui.barrel.brining"));
				}
			}
		}
		else if (sealed && fluid != null && inStack != null && inStack.getItem() instanceof IFood && fluid.getFluid() == TFCFluids.VINEGAR)
		{
			if (!Food.isPickled(inStack) && Food.getWeight(inStack) / fluid.amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid())
			{
				if ((((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.cheese) && Food.isBrined(inStack))
				{
					currenttip.add(TFC_Core.translate("gui.barrel.pickling"));
				}
			}
			else if (Food.isPickled(inStack) && Food.getWeight(inStack) / fluid.amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid() * 2)
			{
				currenttip.add(TFC_Core.translate("gui.barrel.preserving"));
			}
		}else if(preservative!=null){
			currenttip.add(TFC_Core.translate(preservative.getPreservingString()));
		}

		return currenttip;
	}

	public List<String> berryBushBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockBerryBush.metaNames[accessor.getMetadata()]);
		currenttip.add(TFC_Time.SEASONS[index.harvestStart] + " - " + TFC_Time.SEASONS[index.harvestFinish]);
		return currenttip;
	}

	public List<String> blastFurnaceBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TEBlastFurnace te = (TEBlastFurnace) accessor.getTileEntity();
		NBTTagCompound tag = accessor.getNBTData();

		int charcoalCount = tag.getInteger("charcoalCount");
		int oreCount = tag.getByte("oreCount");
		int stackSize = tag.getInteger("maxValidStackSize");
		float temperature = 0;

		ItemStack storage[] = getStorage(tag, te);
		ItemStack oreStack = storage[TEBlastFurnace.ORE_SLOT1];

		HeatRegistry manager = HeatRegistry.getInstance();

		if (oreStack != null)
		{
			HeatIndex index = manager.findMatchingIndex(oreStack);
			if (index != null)
			{
				temperature = TFC_ItemHeat.getTemp(oreStack);
			}
		}
		String temp = TFC_ItemHeat.getHeatColor(temperature, te.maxFireTempScale);

		currenttip.add(TFC_Core.translate("gui.Bloomery.Charcoal") + " : " + charcoalCount + "/" + stackSize * 4);
		currenttip.add(TFC_Core.translate("gui.Bloomery.Ore") + " : " + oreCount + "/" + stackSize * 4);

		if (te.storage[1] != null)
			currenttip.add(TFC_Core.translate("gui.plans.tuyere") + EnumChatFormatting.GREEN.toString() + " \u2714");
		else
			currenttip.add(TFC_Core.translate("gui.plans.tuyere") + EnumChatFormatting.RED.toString() + " \u2718");

		if (temperature > 0)
		{
			currenttip.add(temp);
		}

		return currenttip;
	}

	public List<String> bloomBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int size = tag.getInteger("size");

		currenttip.add(TFC_Core.translate("gui.units") + " : " + size);
		return currenttip;
	}

	public List<String> bloomeryBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean isLit = tag.getBoolean("isLit");
		int charcoalCount = tag.getInteger("charcoalCount");
		int oreCount = tag.getInteger("oreCount");

		currenttip.add(TFC_Core.translate("gui.Bloomery.Charcoal") + " : " + charcoalCount);
		currenttip.add(TFC_Core.translate("gui.Bloomery.Ore") + " : " + oreCount);

		if (isLit)
		{
			long hours = tag.getLong("fuelTimeLeft") / TFC_Time.HOUR_LENGTH - TFC_Time.getTotalHours();

			if (hours > 0)
			{
				float percent = Helper.roundNumber(Math.min(100 - ((hours / TFCOptions.bloomeryBurnTime) * 100), 100), 10);
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + percent + "%)");
			}
		}

		return currenttip;
	}

	public List<String> cropBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		float growth = tag.getFloat("growth");
		int cropId = tag.getInteger("cropId");

		CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
		int percentGrowth = (int) Math.min((growth / crop.numGrowthStages) * 100, 100);

		if (percentGrowth < 100)
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + percentGrowth + "%");
		else
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + TFC_Core.translate("gui.mature"));

		return currenttip;
	}

	public List<String> farmlandBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		SkillRank rank = TFC_Core.getSkillStats(accessor.getPlayer()).getSkillRank(Global.SKILL_AGRICULTURE);
		if (rank == SkillRank.Expert || rank == SkillRank.Master)
		{
			TEFarmland te = (TEFarmland) accessor.getTileEntity();
			NBTTagCompound tag = accessor.getNBTData();

			int nutrients[] = tag.getIntArray("nutrients");
			int soilMax = te.getSoilMax();

			for (int i = 0; i < nutrients.length; i++)
			{
				int percent = Math.max(nutrients[i] * 100 / soilMax, 0);

				if (i == 0)
					currenttip.add(EnumChatFormatting.RED + TFC_Core.translate("gui.Nutrient.A") + " : " + percent + "%");
				else if (i == 1)
					currenttip.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.Nutrient.B") + " : " + percent + "%");
				else if (i == 2)
					currenttip.add(EnumChatFormatting.YELLOW + TFC_Core.translate("gui.Nutrient.C") + " : " + percent + "%");
				else if (i == 3 && percent != 0)
					currenttip.add(EnumChatFormatting.WHITE + TFC_Core.translate("item.Fertilizer.name") + " : " + percent + "%");
			}
		}

		return currenttip;
	}

	public List<String> firepitBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		if (storage != null)
		{
			int fuelCount = 0;
			for (ItemStack is : storage)
			{
				if (is != null && is.getItem() != null && (is.getItem() == TFCItems.logs || is.getItem() == Item.getItemFromBlock(TFCBlocks.peat)))
					fuelCount++;
			}

			if (fuelCount > 0)
				currenttip.add(TFC_Core.translate("gui.fuel") + " : " + fuelCount + "/4");
		}

		return currenttip;
	}

	public List<String> forgeBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		if (storage != null)
		{
			int fuelCount = 0;
			boolean hasMold = false;

			for (int i = 5; i <= 9; i++) // Fuels are stored in slots 5 through 9 per te.HandleFuelStack()
			{
				if (storage[i] != null && storage[i].getItem() != null && storage[i].getItem() instanceof ItemCoal)
					fuelCount++;
			}

			if (fuelCount > 0)
				currenttip.add(TFC_Core.translate("gui.fuel") + " : " + fuelCount + "/5");

			for (int j = 10; j <= 13; j++) // Molds are stored in slots 7 through 9 per te.getMold()
			{
				if (storage[j] != null && storage[j].getItem() == TFCItems.ceramicMold && storage[j].stackSize > 0)
					hasMold = true;
			}
			if (hasMold)
				currenttip.add(TFC_Core.translate("item.Mold.Ceramic Mold.name") + EnumChatFormatting.GREEN.toString() + " \u2714");
			else
				currenttip.add(TFC_Core.translate("item.Mold.Ceramic Mold.name") + EnumChatFormatting.RED.toString() + " \u2718");
		}

		return currenttip;
	}

	public List<String> fruitLeavesBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata()));
		if (index != null)
			currenttip.add(TFC_Time.SEASONS[index.harvestStart] + " - " + TFC_Time.SEASONS[index.harvestFinish]);
		return currenttip;
	}

	public List<String> logPileBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		Boolean isOnFire = tag.getBoolean("isOnFire");

		if (isOnFire)
		{
			int fireTimer = tag.getInteger("fireTimer");
			int hours = (int) (TFCOptions.charcoalPitBurnTime - (TFC_Time.getTotalHours() - fireTimer));
			currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber(100 - (hours / TFCOptions.charcoalPitBurnTime) * 100, 10) + "%)");
		}
		else
		{
			ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
			boolean counted[] = new boolean[] {false, false, false, false}; // Used to keep track of which slots have already been combined into others.

			/**
			 * Rather than just display the number of logs in each slot, I wanted to display the total number of each type of log in the pile.
			 * There's very likely a much better way to do this, but it's all I could come up with for now.
			 * Optimization PRs welcome. :) Kitty
			 */
			for (int j = 0; j < storage.length; j++) // Loop through the 4 storage slots
			{
				if (storage[j] != null && !counted[j]) // Make sure the slot is not empty, and has not already been accounted for
				{
					String log = storage[j].getDisplayName() + " : ";
					int count = storage[j].stackSize;
					for (int k = j + 1; k < storage.length; k++) // Loop through all slots after the one being checked
					{
						if (storage[k] != null && storage[j].isItemEqual(storage[k])) // Make sure the comparison slot isn't empty, and see if it's the same type of log
						{
							count += storage[k].stackSize; // Increase the count for that log type
							counted[k] = true; // Mark the combined slot as accounted for
						}
					}
					currenttip.add(log + count); // Add to the HUD display
					counted[j] = true; // Mark the initial slot as accounted for
				}
			}
		}

		return currenttip;
	}

	public List<String> loomBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean finished = tag.getBoolean("finished");
		int wovenStrings = tag.getInteger("cloth");
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		if (!finished && storage[0] != null)
		{
			LoomRecipe recipe = LoomManager.getInstance().findPotentialRecipes(storage[0]);
			int maxStrings = recipe.getReqSize();

			if (storage[0].stackSize < maxStrings) // The loom isn't full yet
			{
				String name = storage[0].getDisplayName() + " : ";
				currenttip.add(name + storage[0].stackSize + "/" + maxStrings);
			}
			else // Weaving in progress
			{
				String name = recipe.getOutItemStack().getDisplayName() + " : ";
				int percent = (int) (100.0 * wovenStrings / maxStrings);
				currenttip.add(TFC_Core.translate("gui.weaving") + " " + name + percent + "%");
			}
		}

		return currenttip;
	}

	public List<String> metalTrapDoorBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack sheetStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("sheetType"));

		String metalType = BlockMetalTrapDoor.metalNames[sheetStack.getItemDamage() & 31];
		currenttip.add(TFC_Core.translate("gui.metal." + metalType.replaceAll("\\s", "")));
		return currenttip;
	}

	public List<String> nestBoxBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
		int eggCount = 0, fertEggCount = 0;

		for (ItemStack is : storage)
		{
			if (is != null && is.getItem() == TFCItems.egg)
			{
				if (is.hasTagCompound() && is.getTagCompound().hasKey("Fertilized"))
					fertEggCount++;
				else
					eggCount++;
			}
		}

		if (eggCount > 0)
			currenttip.add(TFC_Core.translate("gui.eggs") + " : " + eggCount);
		if (fertEggCount > 0)
			currenttip.add(TFC_Core.translate("gui.fertEggs") + " : " + fertEggCount);

		return currenttip;
	}

	public List<String> oilLampBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		if (tag.hasKey("Fuel"))
		{
			FluidStack fuel = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Fuel"));
			int hours = fuel.amount * TFCOptions.oilLampFuelMult / 8;
			if (fuel.getFluid() == TFCFluids.OLIVEOIL)
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((hours / (250f * TFCOptions.oilLampFuelMult)) * 100f, 10) + "%)");
			else if (fuel.getFluid() == TFCFluids.LAVA)
				currenttip.add(TFC_Core.translate("gui.infinite") + " " + TFC_Core.translate("gui.hoursRemaining"));
		}
		return currenttip;
	}

	public List<String> oreBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();

		if (accessor.getBlock() == TFCBlocks.ore)
		{
			switch (meta)
			{
			case 0:
			case 9:
			case 13:
				currenttip.add(TFC_Core.translate("gui.metal.Copper"));
				break;
			case 1:
				currenttip.add(TFC_Core.translate("gui.metal.Gold"));
				break;
			case 2:
				currenttip.add(TFC_Core.translate("gui.metal.Platinum") + " - " + TFC_Core.translate("gui.useless"));
				break;
			case 3:
			case 10:
			case 11:
				currenttip.add(TFC_Core.translate("gui.metal.Iron"));
				break;
			case 4:
				currenttip.add(TFC_Core.translate("gui.metal.Silver"));
				break;
			case 5:
				currenttip.add(TFC_Core.translate("gui.metal.Tin"));
				break;
			case 6:
				currenttip.add(TFC_Core.translate("gui.metal.Lead") + " - " + TFC_Core.translate("gui.useless"));
				break;
			case 7:
				currenttip.add(TFC_Core.translate("gui.metal.Bismuth"));
				break;
			case 8:
				currenttip.add(TFC_Core.translate("gui.metal.Nickel"));
				break;
			case 12:
				currenttip.add(TFC_Core.translate("gui.metal.Zinc"));
				break;
			case 14:
			case 15:
				currenttip.add(TFC_Core.translate("item.coal.coal.name"));
				return currenttip;
			}

			if (config.getConfig("tfc.oreQuality"))
			{
				TEOre te = (TEOre) accessor.getTileEntity();

				int ore = getOreGrade(te, meta);

				int units = ore < 14 ? TFCOptions.normalOreUnits : ore < 49 ? TFCOptions.richOreUnits : ore < 63 ? TFCOptions.poorOreUnits : 0;
				if (units > 0)
					currenttip.add(TFC_Core.translate("gui.units") + " : " + units);
			}

		}
		else if (accessor.getBlock() == TFCBlocks.ore2)
		{
			switch (meta)
			{
			case 1:
			case 2:
			case 3:
			case 6:
			case 8:
			case 9:
			case 10:
			case 14:
				currenttip.add(TFC_Core.translate("gui.useless"));
				break;
			case 5:
				currenttip.add(TFC_Core.translate("item.Diamond.Normal.name"));
				break;
			case 11:
			case 12:
				currenttip.add(TFC_Core.translate("item.redstone.name"));
				break;
			case 15:
				currenttip.add(TFC_Core.translate("item.Fertilizer.name"));
				break;
			}
		}
		else if (accessor.getBlock() == TFCBlocks.ore3)
		{
			switch (meta)
			{
			case 0:
				currenttip.add(TFC_Core.translate("item.Powder.Flux.name"));
				break;
			case 1:
				currenttip.add(TFC_Core.translate("gui.useless"));
				break;
			}
		}

		return currenttip;
	}

	public List<String> potteryBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		long burnStart = tag.getLong("burnStart");
		int wood = tag.getInteger("wood");
		int straw = tag.getInteger("straw");

		if (straw > 0 && straw < 8)
			currenttip.add(TFC_Core.translate("item.Straw.name") + " : " + straw + "/8");
		else if (wood > 0 && wood < 8)
			currenttip.add(TFC_Core.translate("gui.logs") + " : " + wood + "/8");
		else if (burnStart > 0)
		{
			int hours = (int) (TFCOptions.pitKilnBurnTime - (TFC_Time.getTotalHours() - (burnStart / TFC_Time.HOUR_LENGTH)));
			currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber(100 - (hours / TFCOptions.pitKilnBurnTime) * 100, 10) + "%)");
		}

		return currenttip;
	}

	public List<String> saplingBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean enoughSpace = tag.getBoolean("enoughSpace");
		long growTime = tag.getLong("growTime");
		int days = (int) ((growTime - TFC_Time.getTotalTicks()) / TFC_Time.DAY_LENGTH);
		if (days > 0)
		{
			currenttip.add(days + " " + TFC_Core.translate("gui.daysRemaining"));
		}
		else if (!enoughSpace)
		{
			currenttip.add(TFC_Core.translate("gui.enoughSpace"));
		}

		return currenttip;
	}

	public List<String> sluiceBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
		int soilAmount = tag.getInteger("soilAmount");

		if (soilAmount == -1)
			currenttip.add(TFC_Core.translate("gui.Sluice.Overworked"));
		else if (soilAmount > 0)
		{
			currenttip.add(TFC_Core.translate("gui.Sluice.Soil") + " : " + soilAmount + "/50");
		}

		int gemCount = 0, oreCount = 0;
		for (ItemStack is : storage)
		{
			if (is != null)
			{
				if (is.getItem() instanceof ItemGem)
					gemCount++;
				else if (is.getItem() instanceof ItemOre)
					oreCount++;
			}
		}

		if (gemCount > 0)
			currenttip.add(TFC_Core.translate("gui.gems") + " : " + gemCount);
		if (oreCount > 0)
			currenttip.add(TFC_Core.translate("gui.Bloomery.Ore") + " : " + oreCount);

		return currenttip;
	}

	public List<String> smokeRackBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());

		for (int i = 0; i < storage.length; i++)
		{
			ItemStack is = storage[i];
			if (is != null)
			{
				int dryHours = Food.DRYHOURS - Food.getDried(is);
				int smokeHours = Food.SMOKEHOURS - Food.getSmokeCounter(is);

				if (smokeHours < Food.SMOKEHOURS && !Food.isSmoked(is))
				{
					smokeHours++; // Display timer off by one
					float percent = Helper.roundNumber(100 - (100f * smokeHours) / Food.SMOKEHOURS, 10);
					currenttip.add(TFC_Core.translate("word.smoking") + " " + is.getDisplayName());
					currenttip.add("\u00B7 " + smokeHours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + percent + "%)");
				}
				else if (dryHours < Food.DRYHOURS && !Food.isDried(is))
				{
					float percent = Helper.roundNumber(100 - (100f * dryHours) / Food.DRYHOURS, 10);
					currenttip.add(TFC_Core.translate("word.drying") + " " + is.getDisplayName());
					currenttip.add("\u00B7 " + dryHours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + percent + "%)");
				}
				else
					currenttip.add(is.getDisplayName());
			}
		}

		return currenttip;
	}

	public List<String> spawnMeterBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int hours = tag.getInteger("protectionHours");

		if (hours > 0)
		{
			currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining"));
		}

		return currenttip;
	}

	public List<String> soilBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		Block b = accessor.getBlock();
		int dam = itemStack.getItemDamage();
		if (b == TFCBlocks.dirt2 || b == TFCBlocks.sand2 || TFC_Core.isGrassType2(b) || b == TFCBlocks.gravel2)
		{
			dam += 16;
		}

		if (dam < Global.STONE_ALL.length)
			currenttip.add(Global.STONE_ALL[dam]);
		else
			currenttip.add(EnumChatFormatting.DARK_RED + "Unknown");

		return currenttip;
	}

	public List<String> torchBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getMetadata() < 8 && TFCOptions.torchBurnTime != 0)
		{
			NBTTagCompound tag = accessor.getNBTData();
			long hours = TFCOptions.torchBurnTime - (TFC_Time.getTotalHours() - tag.getInteger("hourPlaced"));

			if (hours > 0)
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * hours) / TFCOptions.torchBurnTime, 10) + "%)");
		}
		return currenttip;
	}

	// Other
	private ItemStack[] getStorage(NBTTagCompound tag, TileEntity te)
	{
		if (te instanceof IInventory)
		{
			IInventory inv = (IInventory) te;
			NBTTagList tagList = tag.getTagList("Items", 10);
			ItemStack storage[] = new ItemStack[inv.getSizeInventory()];
			for (int i = 0; i < tagList.tagCount(); i++)
			{
				NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
				byte slot = itemTag.getByte("Slot");
				if (slot >= 0 && slot < storage.length)
					storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
			}

			return storage;
		}

		return null;
	}

	private int getOreGrade(TEOre te, int ore)
	{
		if (te != null)
		{
			int grade = te.extraData & 7;
			if (grade == 1)
				ore += 35;
			else if (grade == 2)
				ore += 49;
		}
		return ore;
	}
}