package TFC.Handlers.Client;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Entities.Mobs.EntityHorseTFC;
import TFC.GUI.GuiAnvil;
import TFC.GUI.GuiBarrel;
import TFC.GUI.GuiBlastFurnace;
import TFC.GUI.GuiBlueprint;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiCrucible;
import TFC.GUI.GuiFirepit;
import TFC.GUI.GuiFoodPrep;
import TFC.GUI.GuiForge;
import TFC.GUI.GuiInventoryTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiLogPile;
import TFC.GUI.GuiMold;
import TFC.GUI.GuiNestBox;
import TFC.GUI.GuiPlanSelection;
import TFC.GUI.GuiQuern;
import TFC.GUI.GuiQuiver;
import TFC.GUI.GuiScreenHorseInventoryTFC;
import TFC.GUI.GuiScribe;
import TFC.GUI.GuiSluice;
import TFC.GUI.GuiVessel;
import TFC.GUI.GuiVesselLiquid;
import TFC.GUI.GuiWorkbench;
import TFC.TileEntities.TEBlastFurnace;
import TFC.TileEntities.TECrucible;
import TFC.TileEntities.TENestBox;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityForge;
import TFC.TileEntities.TileEntityLogPile;
import TFC.TileEntities.TileEntityQuern;
import TFC.TileEntities.TileEntityScribe;
import TFC.TileEntities.TileEntitySluice;
import TFC.TileEntities.TileEntityWorkbench;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler extends TFC.Handlers.GuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
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

		switch(ID)
		{
		case 0:
			return new GuiLogPile(player.inventory, (TileEntityLogPile) te, world, x, y, z);
		case 1:
			return new GuiWorkbench(player.inventory, (TileEntityWorkbench) te, world, x, y, z);
		case 19:
			return new GuiVesselLiquid(player.inventory, world, x, y, z);
		case 20:
			return new GuiFirepit(player.inventory, (TileEntityFirepit) te, world, x, y, z);
		case 21:
			return new GuiAnvil(player.inventory, (TileEntityAnvil) te, world, x, y, z);
		case 22:
			return new GuiScribe(player.inventory, (TileEntityScribe) te, world, x, y, z);
		case 23:
			return new GuiForge(player.inventory, (TileEntityForge) te, world, x, y, z);
		case 24:
			return new GuiPlanSelection(player, (TileEntityAnvil) te, world, x, y, z);//was metallurgy table
		case 25:
			return new GuiSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
		case 26:
			return new GuiBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		case 27:
			return new GuiCalendar(player);
		case 28:
			return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingType , world, x, y, z);
		case 29:
			return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te), world, x, y, z);
		case 31:
			return new GuiInventoryTFC(player);
		case 32:
			return new GuiFoodPrep(player.inventory, ((TileEntityFoodPrep) te), world, x, y, z);
		case 33:
			return new GuiQuern(player.inventory, ((TileEntityQuern) te), world, x, y, z);
		case 34:
			return new GuiBlueprint(player, world, x, y, z);
		case 35:
			return new GuiBarrel(player.inventory,((TileEntityBarrel)te),world,x,y,z);
		case 36:
			return null;
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
			System.out.println("found location");
			List list = player.worldObj.getEntitiesWithinAABB(EntityHorseTFC.class, player.boundingBox.expand(2, 2, 2));
			System.out.println((player.worldObj).getClass());
			EntityHorseTFC horse = (EntityHorseTFC) list.get(0);
			return new GuiScreenHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
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
