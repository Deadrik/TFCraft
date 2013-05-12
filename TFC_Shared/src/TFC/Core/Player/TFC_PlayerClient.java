package TFC.Core.Player;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;
import TFC.Food.FoodStatsTFC;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.src.*;

public class TFC_PlayerClient extends PlayerBase
{
	public boolean guishowFoodRestoreAmount = false;
	public int guiFoodRestoreAmount = 0;

	//Last time the spawn protection was updated
	private long spawnProtectionTimer = -1;

	private FoodStatsTFC foodstats;
	private FoodStats oldFood;

	public TFC_PlayerClient(PlayerAPI var1) {
		super(var1);
		foodstats = new FoodStatsTFC();
	}

	@Override
	public void beforeOnLivingUpdate() 
	{
		oldFood = this.player.getFoodStats();
		if(this.player.inventory.getCurrentItem() != null)
		{
			if(this.player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
			{
				guishowFoodRestoreAmount = true;
				guiFoodRestoreAmount = ItemMeal.getMealFilling(this.player.inventory.getCurrentItem());
			}
			else if(this.player.inventory.getCurrentItem().getItem() instanceof ItemTerraFood)
			{
				guishowFoodRestoreAmount = true;
				guiFoodRestoreAmount = ((ItemTerraFood)this.player.inventory.getCurrentItem().getItem()).getHealAmount();
			}
			else
			{
				guishowFoodRestoreAmount = false;
			}
		}
		else
		{
			guishowFoodRestoreAmount = false;
		}
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		this.player.setFoodStatsField(oldFood);			
		//this.foodstats.onUpdate(player);
	}

	public FoodStatsTFC getFoodStatsTFC()
	{
		return this.foodstats;
	}

	public static TFC_PlayerClient getFromEntityPlayer(EntityPlayer p)
	{
		EntityClientPlayerMP pmp = (EntityClientPlayerMP) p;
		return (TFC_PlayerClient) pmp.getPlayerBase("TFC Player Client");
	}

	public int getMaxHealth()
	{
		return 1000+(this.player.experienceLevel*25);
	}

	public static int getStartingMaxHealth()
	{
		return 1000;
	}

}
