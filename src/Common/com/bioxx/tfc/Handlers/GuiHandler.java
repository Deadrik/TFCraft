package com.bioxx.tfc.Handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerAnvil;
import com.bioxx.tfc.Containers.ContainerBarrel;
import com.bioxx.tfc.Containers.ContainerBlastFurnace;
import com.bioxx.tfc.Containers.ContainerChestTFC;
import com.bioxx.tfc.Containers.ContainerCrucible;
import com.bioxx.tfc.Containers.ContainerFirepit;
import com.bioxx.tfc.Containers.ContainerFoodPrep;
import com.bioxx.tfc.Containers.ContainerForge;
import com.bioxx.tfc.Containers.ContainerGrill;
import com.bioxx.tfc.Containers.ContainerHorseInventoryTFC;
import com.bioxx.tfc.Containers.ContainerLargeVessel;
import com.bioxx.tfc.Containers.ContainerLiquidVessel;
import com.bioxx.tfc.Containers.ContainerLogPile;
import com.bioxx.tfc.Containers.ContainerMold;
import com.bioxx.tfc.Containers.ContainerNestBox;
import com.bioxx.tfc.Containers.ContainerPlanSelection;
import com.bioxx.tfc.Containers.ContainerPlayerTFC;
import com.bioxx.tfc.Containers.ContainerQuern;
import com.bioxx.tfc.Containers.ContainerQuiver;
import com.bioxx.tfc.Containers.ContainerSluice;
import com.bioxx.tfc.Containers.ContainerSpecialCrafting;
import com.bioxx.tfc.Containers.ContainerVessel;
import com.bioxx.tfc.Containers.ContainerWorkbench;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.TileEntities.TENestBox;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.TileEntities.TileEntityQuern;
import com.bioxx.tfc.TileEntities.TileEntitySluice;
import com.bioxx.tfc.TileEntities.TileEntityWorkbench;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);

		switch(ID)
		{
		case 0:
		{
			return new ContainerLogPile(player.inventory, (TELogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new ContainerWorkbench(player.inventory, (TileEntityWorkbench) te, world, x, y, z);
		}
		case 19:
		{
			return new ContainerLiquidVessel(player.inventory, world, x, y, z);
		}
		case 20:
		{
			return new ContainerFirepit(player.inventory, (TEFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new ContainerAnvil(player.inventory, (TEAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return null;//was scribing table
		}
		case 23:
		{
			return new ContainerForge(player.inventory, (TEForge) te, world, x, y, z);
		}
		case 24:
		{
			return new ContainerPlanSelection(player, (TEAnvil) te, world, x, y, z);//was metallurgy table
		}
		case 25:
		{
			return new ContainerSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
		}
		case 26:
		{
			return new ContainerBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		}
		case 28:
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			return new ContainerSpecialCrafting(player.inventory, pi.specialCraftingTypeAlternate == null ? pi.specialCraftingType : null, world, x, y, z);
		}
		case 29:
		{
			return new ContainerChestTFC(player.inventory, (TEChest) te, world, x, y, z);
		}
		case 31:
		{
			return new ContainerPlayerTFC(player.inventory, false, player);
		}
		case 33:
		{
			return new ContainerQuern(player.inventory, (TileEntityQuern) te, world, x, y, z);
		}
		case 34:
		{
			return null;
		}
		case 35:
		{
			return new ContainerBarrel(player.inventory, ((TEBarrel) te), world, x, y, z, 0);
		}
		case 36:
		{
			return new ContainerBarrel(player.inventory, ((TEBarrel) te), world, x, y, z, 1);
		}
		case 37:
		{
			return new ContainerCrucible(player.inventory, ((TECrucible) te), world, x, y, z);
		}
		case 38:
		{
			return new ContainerMold(player.inventory, world, x, y, z);
		}
		case 39:
		{
			return new ContainerVessel(player.inventory, world, x, y, z);
		}
		case 40:
		{
			return new ContainerQuiver(player.inventory, world, x, y, z);
		}
		case 41:
		{
			return new ContainerNestBox(player.inventory, ((TENestBox) te), world, x, y, z);
		}
		case 42:
		{
			List list = player.worldObj.getEntitiesWithinAABB(EntityHorseTFC.class, player.boundingBox.expand(2, 2, 2));
			if(list.size() > 0)
			{
				EntityHorseTFC horse = (EntityHorseTFC) list.get(0);
				return new ContainerHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
			}
			return null;
		}
		case 43:
		{
			return new ContainerGrill(player.inventory, ((TEGrill) te), world, x, y, z);
		}
		case 44:
			return new ContainerFoodPrep(player.inventory, (TEFoodPrep) te, world, x, y, z, 0);
		case 45:
			return new ContainerFoodPrep(player.inventory, (TEFoodPrep) te, world, x, y, z, 1);
		case 46:
			return new ContainerLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 0);
		case 47:
			return new ContainerLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 1);
		default:
		{
			return null;
		}
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
