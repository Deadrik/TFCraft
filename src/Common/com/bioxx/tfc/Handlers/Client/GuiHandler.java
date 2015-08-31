package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.client.event.GuiOpenEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.GUI.*;
import com.bioxx.tfc.TileEntities.*;

public class GuiHandler extends com.bioxx.tfc.Handlers.GuiHandler
{
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(id)
		{
		case 0:
			return new GuiLogPile(player.inventory, (TELogPile) te, world, x, y, z);
		case 1:
			return new GuiWorkbench(player.inventory, (TEWorkbench) te, world, x, y, z);
		case 19:
			return new GuiVesselLiquid(player.inventory, world, x, y, z);
		case 20:
			return new GuiFirepit(player.inventory, (TEFirepit) te, world, x, y, z);
		case 21:
			return new GuiAnvil(player.inventory, (TEAnvil) te, world, x, y, z);
		case 22:
			return null;//was scribing table
		case 23:
			return new GuiForge(player.inventory, (TEForge) te, world, x, y, z);
		case 24:
			return new GuiPlanSelection(player, (TEAnvil) te, world, x, y, z);//was metallurgy table
		case 25:
			return new GuiSluice(player.inventory, (TESluice) te, world, x, y, z);
		case 26:
			return new GuiBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		case 27:
			return new GuiCalendar(player);
		case 28:
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			return new GuiKnapping(player.inventory, pi.specialCraftingTypeAlternate == null ? pi.specialCraftingType : null, world, x, y, z);
		case 29:
			return new GuiChestTFC(player.inventory, ((TEChest) te), world, x, y, z);
		case 31:
			return new GuiInventoryTFC(player);
		case 32:
			//return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 0);
		case 33:
			return new GuiQuern(player.inventory, ((TEQuern) te), world, x, y, z);
		case 34:
			return new GuiBlueprint(player, world, x, y, z);
		case 35:
			return new GuiBarrel(player.inventory,((TEBarrel)te),world,x,y,z, 0);
		case 36:
			return new GuiBarrel(player.inventory,((TEBarrel)te),world,x,y,z, 1);
		case 37:
			return new GuiCrucible(player.inventory,((TECrucible)te), world, x, y, z);
		case 38:
			return new GuiMold(player.inventory, world, x, y, z);
		case 39:
			return new GuiVessel(player.inventory, world, x, y, z);
		case 40:
			return new GuiQuiver(player.inventory, world, x, y, z);
		case 41:
			return new GuiNestBox(player.inventory, ((TENestBox)te), world, x, y, z);
		case 42:
		{
			if (player.ridingEntity instanceof EntityHorseTFC)
			{
				EntityHorseTFC horse = (EntityHorseTFC) player.ridingEntity;
				horse.updateChestSaddle();
				return new GuiScreenHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
			}

			return null;
		}
		case 43:
			return new GuiGrill(player.inventory, ((TEGrill)te), world, x, y, z);
		case 44:
			return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 0);
		case 45:
			return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 1);
		case 46:
			return new GuiLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 0);
		case 47:
			return new GuiLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 1);
		case 48:
			return new GuiCustomNametag(player, world, x, y, z);
		case 49:
		{
			return new GuiHopper(player.inventory, ((TEHopper) te), world, x, y, z);
		}
		default:
			return null;
		}
	}

	@SubscribeEvent
	public void openGuiHandler(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof GuiInventoryTFC))
			event.gui = new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer);
	}
}
