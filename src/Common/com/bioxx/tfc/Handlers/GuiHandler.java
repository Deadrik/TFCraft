package com.bioxx.tfc.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import com.bioxx.tfc.Containers.*;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.TileEntities.*;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);

		switch(id)
		{
		case 0:
		{
			return new ContainerLogPile(player.inventory, (TELogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new ContainerWorkbench(player.inventory, (TEWorkbench) te, world, x, y, z);
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
			return new ContainerSluice(player.inventory, (TESluice) te, world, x, y, z);
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
			return new ContainerQuern(player.inventory, (TEQuern) te, world, x, y, z);
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
			if (player.ridingEntity instanceof EntityHorseTFC)
			{
				EntityHorseTFC horse = (EntityHorseTFC) player.ridingEntity;
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
		case 48:
		{
			return null;//guinametag
		}
		case 49:
		{
			return new ContainerHopper(player.inventory, ((TEHopper) te));
		}
			case 50:
				return new ContainerCoalPile(player.inventory, ((TECoalPile) te), world, x, y, z);
		default:
		{
			return null;
		}
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
