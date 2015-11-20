package com.bioxx.tfc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import codechicken.nei.api.API;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import com.bioxx.tfc.GUI.GuiBarrel;
import com.bioxx.tfc.GUI.GuiLargeVessel;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;

/**
 * @author Dries007
 */
public class NEIIntegration
{
	public static void hideNEIItems()
	{
		GuiContainerManager.addTooltipHandler(new IContainerTooltipHandler()
		{
			@Override
			public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip)
			{
				return currenttip;
			}

			@Override
			public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip)
			{
				return currenttip;
			}

			@Override
			public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip)
			{
				if (gui instanceof GuiLargeVessel || gui instanceof GuiBarrel)
				{
					Slot slot = gui.getSlotAtPosition(mousex, mousey);
					if (slot != null && !slot.func_111238_b()) currenttip.clear();
				}
				return currenttip;
			}
		});

		if (TFCOptions.enableNEIHiding)
		{
			API.hideItem(new ItemStack(TFCBlocks.bloom));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.writabeBookTFC)); // Book
			API.hideItem(new ItemStack(TFCBlocks.charcoal));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.Coke));
			API.hideItem(new ItemStack(TFCBlocks.crops));
			API.hideItem(new ItemStack(TFCBlocks.detailed));
			API.hideItem(new ItemStack(TFCBlocks.worldItem)); // Debris
			for (Block door : TFCBlocks.doors)
			{
				API.hideItem(new ItemStack(door));
			}
			API.hideItem(new ItemStack(TFCBlocks.firepit));
			API.hideItem(new ItemStack(TFCItems.flatClay, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCItems.flatLeather));
			API.hideItem(new ItemStack(TFCItems.flatRock, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.foodPrep));
			API.hideItem(new ItemStack(TFCBlocks.forge));
			API.hideItem(new ItemStack(TFCBlocks.fruitTreeLeaves, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.fruitTreeLeaves2, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.fruitTreeWood, 1, OreDictionary.WILDCARD_VALUE));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.Hemp));
			API.hideItem(new ItemStack(TFCBlocks.ingotPile));
			API.hideItem(new ItemStack(TFCBlocks.leatherRack));
			API.hideItem(new ItemStack(TFCBlocks.leaves, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.leaves2, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.logNatural, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.logNatural2, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.logPile));
			API.hideItem(new ItemStack(TFCBlocks.woodConstruct)); // Lumber Block
			API.hideItem(new ItemStack(TFCBlocks.metalSheet));
			API.hideItem(new ItemStack(TFCBlocks.molten));
			API.hideItem(new ItemStack(TFCBlocks.moss));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.MudBrick, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.ore));
			API.hideItem(new ItemStack(TFCBlocks.ore2));
			API.hideItem(new ItemStack(TFCBlocks.ore3));
			API.hideItem(new ItemStack(TFCBlocks.pottery));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.PotteryPot, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.reeds));
			API.hideItem(new ItemStack(TFCItems.salad, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCItems.sandwich, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.sluice));
			API.hideItem(new ItemStack(TFCBlocks.smoke));
			API.hideItem(new ItemStack(TFCBlocks.smokeRack));
			//codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.Soup, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.stoneSlabs));
			API.hideItem(new ItemStack(TFCBlocks.stoneStairs));
			API.hideItem(new ItemStack(TFCBlocks.stoneStalac));
			API.hideItem(new ItemStack(TFCBlocks.strawHideBed));
			API.hideItem(new ItemStack(TFCBlocks.sulfur));
			API.hideItem(new ItemStack(TFCBlocks.torchOff));
			API.hideItem(new ItemStack(TFCBlocks.waterPlant));
			API.hideItem(new ItemStack(TFCBlocks.woodHoriz, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodHoriz2, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodHoriz3, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodHoriz4, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodSupportH, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodSupportH2, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodVert, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(TFCBlocks.woodVert2, 1, OreDictionary.WILDCARD_VALUE));

			/*codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.WintergreenLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.BlueberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.RaspberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.StrawberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.BlackberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.BunchberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.CranberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.SnowberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.ElderberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.GooseberryLeaf));
			codechicken.nei.api.API.hideItem(new ItemStack(TFCItems.CloudberryLeaf));*/

			// Vanilla Blocks & Items
			API.hideItem(new ItemStack(Blocks.double_wooden_slab, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.wooden_slab, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.oak_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.acacia_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.dark_oak_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.spruce_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.birch_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.jungle_stairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.waterlily, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.tallgrass, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.yellow_flower, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.brown_mushroom, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.red_mushroom, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.bookshelf, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.crafting_table, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.cactus, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.reeds, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.pumpkin, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.lit_pumpkin, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.wooden_button, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.ice, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.vine, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Blocks.flower_pot, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Items.flower_pot, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Items.reeds, 1, OreDictionary.WILDCARD_VALUE));
		}
	}
}
