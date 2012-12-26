package TFC.Core;

import java.util.Comparator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
class RecipeSorterTFC
implements Comparator
{
	final CraftingManagerTFC craftingManager;

	RecipeSorterTFC(CraftingManagerTFC craftingmanager)
	{
		craftingManager = craftingmanager;
	}

	public int compare(Object obj, Object obj1)
	{
		return compareRecipes((IRecipe)obj, (IRecipe)obj1);
	}

	public int compareRecipes(IRecipe irecipe, IRecipe irecipe1)
	{
		if (irecipe instanceof ShapelessRecipes && irecipe1 instanceof ShapedRecipes)
		{
			return 1;
		}
		if (irecipe1 instanceof ShapelessRecipes && irecipe instanceof ShapedRecipes)
		{
			return -1;
		}
		if (irecipe1.getRecipeSize() < irecipe.getRecipeSize())
		{
			return -1;
		}
		return irecipe1.getRecipeSize() <= irecipe.getRecipeSize() ? 0 : 1;
	}
}
