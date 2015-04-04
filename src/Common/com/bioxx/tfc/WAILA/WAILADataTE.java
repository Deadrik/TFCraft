package com.bioxx.tfc.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Blocks.Devices.BlockAnvil;
import com.bioxx.tfc.Blocks.Flora.BlockBerryBush;
import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.TileEntities.TEBloom;
import com.bioxx.tfc.TileEntities.TEBloomery;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelPreservativeRecipe;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Crafting.LoomManager;
import com.bioxx.tfc.api.Crafting.LoomRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class WAILADataTE implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getBlock() instanceof BlockAnvil)
			return anvilStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEBerryBush)
			return berryBushStack(accessor, config);
		
		else if (accessor.getTileEntity() instanceof TEBloom)
			return new ItemStack(TFCItems.RawBloom);

		else if (accessor.getTileEntity() instanceof TECrop)
			return cropStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEFruitLeaves)
			return fruitLeavesStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEFruitTreeWood)
			return fruitTreeWoodStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEIngotPile)
			return ingotPileStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEOre)
			return oreStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TELoom)
			return loomStack(accessor, config);

		else if (accessor.getTileEntity() instanceof TEMetalSheet)
			return metalSheetStack(accessor, config);
		
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEBarrel)
			currenttip = barrelHead(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEFruitLeaves)
			currenttip = fruitLeavesHead(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEFruitTreeWood)
			currenttip = fruitTreeWoodHead(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEOre)
			currenttip = oreHead(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEAnvil)
			currenttip = anvilBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEBarrel)
			currenttip = barrelBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEBerryBush)
			currenttip = berryBushBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEBlastFurnace)
			currenttip = blastFurnaceBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEBloom)
			currenttip = bloomBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEBloomery)
			currenttip = bloomeryBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TECrop)
			currenttip = cropBody(itemStack, currenttip, accessor, config);

		// I haven't decided if this is OP or not. Useful for debugging though, so uncomment when needing to check farmland nutrient %.
		/*else if (accessor.getTileEntity() instanceof TEFarmland)
			currenttip = farmlandBody(itemStack, currenttip, accessor, config);*/

		else if (accessor.getTileEntity() instanceof TEFirepit)
			currenttip = firepitBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEForge)
			currenttip = forgeBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEFruitLeaves)
			currenttip = fruitLeavesBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TEOre)
			currenttip = oreBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TELogPile)
			currenttip = logPileBody(itemStack, currenttip, accessor, config);

		else if (accessor.getTileEntity() instanceof TELoom)
			currenttip = loomBody(itemStack, currenttip, accessor, config);

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
		reg.registerStackProvider(new WAILADataTE(), BlockAnvil.class);
		reg.registerBodyProvider(new WAILADataTE(), TEAnvil.class);
		reg.registerNBTProvider(new WAILADataTE(), TEAnvil.class);

		reg.registerHeadProvider(new WAILADataTE(), TEBarrel.class);
		reg.registerBodyProvider(new WAILADataTE(), TEBarrel.class);
		reg.registerNBTProvider(new WAILADataTE(), TEBarrel.class);

		reg.registerStackProvider(new WAILADataTE(), TEBerryBush.class);
		reg.registerBodyProvider(new WAILADataTE(), TEBerryBush.class);
		reg.registerNBTProvider(new WAILADataTE(), TEBerryBush.class);

		reg.registerBodyProvider(new WAILADataTE(), TEBlastFurnace.class);
		reg.registerNBTProvider(new WAILADataTE(), TEBlastFurnace.class);

		reg.registerStackProvider(new WAILADataTE(), TEBloom.class);
		reg.registerBodyProvider(new WAILADataTE(), TEBloom.class);
		reg.registerNBTProvider(new WAILADataTE(), TEBloom.class);

		reg.registerBodyProvider(new WAILADataTE(), TEBloomery.class);
		reg.registerNBTProvider(new WAILADataTE(), TEBloomery.class);

		reg.registerStackProvider(new WAILADataTE(), TECrop.class);
		reg.registerBodyProvider(new WAILADataTE(), TECrop.class);
		reg.registerNBTProvider(new WAILADataTE(), TECrop.class);

		reg.registerBodyProvider(new WAILADataTE(), TEFarmland.class);
		reg.registerNBTProvider(new WAILADataTE(), TEFarmland.class);

		reg.registerBodyProvider(new WAILADataTE(), TEFirepit.class);
		reg.registerNBTProvider(new WAILADataTE(), TEFirepit.class);

		reg.registerBodyProvider(new WAILADataTE(), TEForge.class);
		reg.registerNBTProvider(new WAILADataTE(), TEForge.class);

		reg.registerStackProvider(new WAILADataTE(), TEFruitLeaves.class);
		reg.registerHeadProvider(new WAILADataTE(), TEFruitLeaves.class);
		reg.registerBodyProvider(new WAILADataTE(), TEFruitLeaves.class);
		reg.registerNBTProvider(new WAILADataTE(), TEFruitLeaves.class);

		reg.registerStackProvider(new WAILADataTE(), TEFruitTreeWood.class);
		reg.registerHeadProvider(new WAILADataTE(), TEFruitTreeWood.class);

		reg.registerStackProvider(new WAILADataTE(), TEIngotPile.class);
		reg.registerHeadProvider(new WAILADataTE(), TEIngotPile.class);
		reg.registerNBTProvider(new WAILADataTE(), TEIngotPile.class);

		reg.registerStackProvider(new WAILADataTE(), TEOre.class);
		reg.registerHeadProvider(new WAILADataTE(), TEOre.class);
		reg.registerBodyProvider(new WAILADataTE(), TEOre.class);

		reg.registerBodyProvider(new WAILADataTE(), TELogPile.class);
		reg.registerNBTProvider(new WAILADataTE(), TELogPile.class);

		reg.registerStackProvider(new WAILADataTE(), TELoom.class);
		reg.registerBodyProvider(new WAILADataTE(), TELoom.class);
		reg.registerNBTProvider(new WAILADataTE(), TELoom.class);

		reg.registerStackProvider(new WAILADataTE(), TEMetalSheet.class);
		reg.registerNBTProvider(new WAILADataTE(), TEMetalSheet.class);
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
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockBerryBush.MetaNames[accessor.getMetadata()]);

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

		if (crop.Output2 != null)
			itemstack = new ItemStack(crop.Output2);
		else
			itemstack = new ItemStack(crop.Output1);

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
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack storage[] = new ItemStack[1];
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < storage.length)
				storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}

		if (storage[0] != null)
			return storage[0];

		return null;
	}

	public ItemStack oreStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();
		TEOre te = (TEOre) accessor.getTileEntity();

		if (accessor.getBlock() == TFCBlocks.Ore) // Metals & Coal
		{
			ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, getOreGrade(te, meta));
			if (meta == 14 || meta == 15) // Bituminous Coal & Lignite
				itemstack = new ItemStack(TFCItems.Coal);

			return itemstack;
		}
		else if (accessor.getBlock() == TFCBlocks.Ore2) // Minerals
		{
			ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length);
			if (meta == 5)
				itemstack = new ItemStack(TFCItems.GemDiamond); // Kaolinite
			else if (meta == 13)
				itemstack = new ItemStack(TFCItems.Powder, 1, 4); // Saltpeter

			return itemstack;
		}
		else if (accessor.getBlock() == TFCBlocks.Ore3) // Minerals
		{
			ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length);
			return itemstack;
		}

		return null;
	}

	public ItemStack loomStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean finished = tag.getBoolean("finished");
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack storage[] = new ItemStack[2];

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < storage.length)
				storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}
		
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

		if (accessor.getBlock() == TFCBlocks.Ore)
		{
			if (meta == 14)
			{
				currenttip.set(0, TFC_Core.translate("item.Ore.Bituminous Coal.name"));
			}
			else if (meta == 15)
			{
				currenttip.set(0, TFC_Core.translate("item.Ore.Lignite.name"));
			}
		}
		else if (accessor.getBlock() == TFCBlocks.Ore2)
		{
			if (meta == 5)
			{
				currenttip.set(0, TFC_Core.translate("item.Ore.Kimberlite.name"));
			}
			else if (meta == 13)
			{
				currenttip.set(0, TFC_Core.translate("item.Ore.Saltpeter.name"));
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

		return currenttip;
	}

	public List<String> barrelBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TEBarrel te = (TEBarrel) accessor.getTileEntity();
		ItemStack inStack = null;

		NBTTagCompound tag = accessor.getNBTData();

		NBTTagList tagList = tag.getTagList("Items", 10);
		NBTTagCompound itemTag = tagList.getCompoundTagAt(0);
		byte slot = itemTag.getByte("Slot");

		if (slot >= 0 && slot < te.storage.length)
			inStack = ItemStack.loadItemStackFromNBT(itemTag);

		Boolean sealed = te.getSealed();
		int sealTime = accessor.getNBTInteger(tag, "SealTime");
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluidNBT"));
		BarrelRecipe recipe = BarrelManager.getInstance().findMatchingRecipe(inStack, fluid, sealed, te.getTechLevel());

		if (sealed && fluid != null && fluid.getFluid() == TFCFluids.MILKCURDLED && (inStack == null || (inStack.getItem() instanceof IFood && ((IFood) inStack.getItem()).getFoodGroup() != EnumFoodGroup.Dairy && ((IFood) inStack.getItem()).isEdible(inStack) && Food.getWeight(inStack) <= 20.0f)))
			recipe = new BarrelRecipe(null, new FluidStack(TFCFluids.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese, 1), 160), null).setMinTechLevel(0);

		// Fluid Amount
		if (fluid != null)
		{
			currenttip.add(String.valueOf(fluid.amount) + "/" + String.valueOf(te.getMaxLiquid()) + " mB");
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
				if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.Cheese) && !Food.isBrined(inStack))
				{
					currenttip.add(TFC_Core.translate("gui.barrel.brining"));
				}
			}
		}
		else if (recipe == null && sealed && fluid != null && inStack != null && inStack.getItem() instanceof IFood && fluid.getFluid() == TFCFluids.VINEGAR)
		{
			if (!Food.isPickled(inStack) && Food.getWeight(inStack) / fluid.amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid())
			{
				if ((((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.Cheese) && Food.isBrined(inStack))
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
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockBerryBush.MetaNames[accessor.getMetadata()]);
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
		ItemStack oreStack = null;

		NBTTagList tagList = tag.getTagList("Items", 10);
		NBTTagCompound itemTag = tagList.getCompoundTagAt(0);
		byte slot = itemTag.getByte("Slot");

		if (slot >= 0 && slot < te.fireItemStacks.length)
			oreStack = ItemStack.loadItemStackFromNBT(itemTag);

		HeatRegistry manager = HeatRegistry.getInstance();

		if (oreStack != null)
		{
			HeatIndex index = manager.findMatchingIndex(oreStack);
			if (index != null)
			{
				temperature = TFC_ItemHeat.GetTemp(oreStack);
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
			long timeLeft = tag.getLong("fuelTimeLeft");
			long currentTime = TFC_Time.getTotalTicks();
			long timeProcessed = (int) Math.max(timeLeft - currentTime, 0);
			int percent = (int) Math.min(100 - ((timeProcessed / (TFC_Time.hourLength * TFCOptions.bloomeryBurnTime)) * 100), 100);

			currenttip.add(TFC_Core.translate("gui.progress") + " : " + String.valueOf(percent) + "%");
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
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + String.valueOf(percentGrowth) + "%");
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
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack fireItemStacks[] = new ItemStack[11];
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < fireItemStacks.length)
				fireItemStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}

		if (fireItemStacks != null)
		{
			int fuelCount = 0;
			for (ItemStack is : fireItemStacks)
			{
				if (is != null && is.getItem() != null && (is.getItem() == TFCItems.Logs || is.getItem() == Item.getItemFromBlock(TFCBlocks.Peat)))
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
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack fireItemStacks[] = new ItemStack[14];
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < fireItemStacks.length)
				fireItemStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}

		if (fireItemStacks != null)
		{
			int fuelCount = 0;
			boolean hasMold = false;

			for (int i = 5; i <= 9; i++) // Fuels are stored in slots 5 through 9 per te.HandleFuelStack()
			{
				if (fireItemStacks[i] != null && fireItemStacks[i].getItem() != null && fireItemStacks[i].getItem() instanceof ItemCoal)
					fuelCount++;
			}

			if (fuelCount > 0)
				currenttip.add(TFC_Core.translate("gui.fuel") + " : " + fuelCount + "/5");

			for (int j = 10; j <= 13; j++) // Molds are stored in slots 7 through 9 per te.getMold()
			{
				if (fireItemStacks[j] != null && fireItemStacks[j].getItem() == TFCItems.CeramicMold && fireItemStacks[j].stackSize > 0)
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

	public List<String> oreBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();

		if (accessor.getBlock() == TFCBlocks.Ore)
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

			TEOre te = (TEOre) accessor.getTileEntity();

			int ore = getOreGrade(te, meta);

			int units = ore < 14 ? TFCOptions.normalOreUnits : ore < 49 ? TFCOptions.richOreUnits : ore < 63 ? TFCOptions.poorOreUnits : 0;
			if (units > 0)
				currenttip.add(TFC_Core.translate("gui.units") + " : " + units);
		}
		else if (accessor.getBlock() == TFCBlocks.Ore2)
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
		else if (accessor.getBlock() == TFCBlocks.Ore3)
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

	public List<String> logPileBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack storage[] = new ItemStack[4];
		boolean counted[] = new boolean[] {false, false, false, false}; // Used to keep track of which slots have already been combined into others.

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < storage.length)
				storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}

		/**
		 * Rather than just display the number of logs in each slot, I wanted to display the total number of each type of log in the pile.
		 * There's very likely a much better way to do this, but it's all I could come up with for now.
		 * Optimization PRs welcome. :) Kitty
		 */
		for (int j = 0; j < storage.length; j++) // Loop through the 4 storage slots
		{
			if (storage[j] != null && !counted[j]) // Make sure the slot is not empty, and has not already been accounted for
			{
				String log = storage[j].getItem().getItemStackDisplayName(storage[j]) + " : "; // Have to pass the ItemStack in again, since translating getUnlocalizedName() doesn't work. :(
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
		return currenttip;
	}

	public List<String> loomBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean finished = tag.getBoolean("finished");
		int wovenStrings = tag.getInteger("cloth");
		NBTTagList tagList = tag.getTagList("Items", 10);
		ItemStack storage[] = new ItemStack[2];

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");
			if (slot >= 0 && slot < storage.length)
				storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
		}
		
		if (!finished && storage[0] != null)
		{
			LoomRecipe recipe = LoomManager.getInstance().findPotentialRecipes(storage[0]);
			int maxStrings = recipe.getReqSize();

			if (storage[0].stackSize < maxStrings) // The loom isn't full yet
			{
				String name = storage[0].getItem().getItemStackDisplayName(storage[0]) + " : ";
				currenttip.add(name + storage[0].stackSize + "/" + maxStrings);
			}
			else // Weaving in progress
			{
				Item cloth = recipe.getOutItemStack().getItem();
				String clothName = cloth.getItemStackDisplayName(recipe.getOutItemStack()) + " : ";
				int percent = (int) (100.0 * wovenStrings / maxStrings);
				currenttip.add(TFC_Core.translate("gui.weaving") + " " + clothName + String.valueOf(percent) + "%");
			}
		}

		return currenttip;
	}

	// Other
	private int getOreGrade(TEOre te, int ore)
	{
		if (te != null)
		{
			int grade = (te.extraData & 7);
			if (grade == 1)
				ore += 35;
			else if (grade == 2)
				ore += 49;
		}
		return ore;
	}
}