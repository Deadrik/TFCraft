package TFC.Items.ItemBlocks;

import java.util.List;

import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.IItemFoodBlock;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Textures;
import TFC.Food.ItemTerraFood;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemFoodBlock extends ItemTerraBlock implements IItemFoodBlock
{
	public ItemFoodBlock(int i) 
	{
		super(i);
		ItemTerraFood.FoodList[this.getBlockID()] = this.getBlockID();
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		if(is!=null){
			if(is.getItem().itemID == Block.mushroomBrown.blockID){
				return EnumSize.VERYSMALL;
			}
		}
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		if(is!=null){
			if(is.getItem().itemID == Block.mushroomBrown.blockID){
				return EnumWeight.LIGHT;
			}
		}
		return EnumWeight.MEDIUM;
	}

	@Override
	public int getFoodId(ItemStack is) {
		if(is.getItem().itemID==Block.mushroomBrown.blockID){
			return 61;
		}
		else if(is.getItem().itemID==Block.pumpkin.blockID){
			return 62;
		}
		else if(is.getItem().itemID==Block.melon.blockID){
			return 63;
		}
		return 1;
	}

	@Override
	public int getHealAmount(ItemStack is) {
		if(is.getItem().itemID==Block.mushroomBrown.blockID){
			return 25;
		}
		else if(is.getItem().itemID==Block.pumpkin.blockID){
			return 35;
		}
		else if(is.getItem().itemID==Block.melon.blockID){
			return 35;
		}
		return 0;
	}
}