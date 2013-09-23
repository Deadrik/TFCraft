package TFC.Handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.ForgeSubscribe;
import TFC.Containers.ContainerAnvil;
import TFC.Containers.ContainerBarrel;
import TFC.Containers.ContainerBlastFurnace;
import TFC.Containers.ContainerChestTFC;
import TFC.Containers.ContainerCrucible;
import TFC.Containers.ContainerFirepit;
import TFC.Containers.ContainerFoodPrep;
import TFC.Containers.ContainerForge;
import TFC.Containers.ContainerLiquidVessel;
import TFC.Containers.ContainerLogPile;
import TFC.Containers.ContainerMold;
import TFC.Containers.ContainerNestBox;
import TFC.Containers.ContainerQuern;
import TFC.Containers.ContainerQuiver;
import TFC.Containers.ContainerScribe;
import TFC.Containers.ContainerSluice;
import TFC.Containers.ContainerSpecialCrafting;
import TFC.Containers.ContainerVessel;
import TFC.Containers.ContainerWorkbench;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
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
import TFC.GUI.GuiQuern;
import TFC.GUI.GuiQuiver;
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
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case 0:
		{
			return new ContainerLogPile(player.inventory, (TileEntityLogPile) te, world, x, y, z);
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
			return new ContainerFirepit(player.inventory, (TileEntityFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new ContainerAnvil(player.inventory, (TileEntityAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new ContainerScribe(player.inventory, (TileEntityScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new ContainerForge(player.inventory, (TileEntityForge) te, world, x, y, z);
		}
		case 24:
		{
			return null;//was metallurgy table
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
			return new ContainerChestTFC(player.inventory, (TileEntityChestTFC) te, world, x, y, z);
		}
		case 31:
		{
			return new ContainerPlayer(player.inventory, false, player);
		}
		case 32:
		{
			return new ContainerFoodPrep(player.inventory, (TileEntityFoodPrep) te, world, x, y, z);
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
			return new ContainerBarrel(player.inventory,((TileEntityBarrel)te),world,x,y,z);
		}
		case 36:
		{
			return null;//was leatherworking
		}
		case 37:
		{
			return new ContainerCrucible(player.inventory,((TECrucible)te),world,x,y,z);
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
			return new ContainerNestBox(player.inventory,((TENestBox)te),world,x,y,z);
		}
		default:
		{
			return null;
		}
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getBlockTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
		case 0:
		{
			return new GuiLogPile(player.inventory, (TileEntityLogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new GuiWorkbench(player.inventory, (TileEntityWorkbench) te, world, x, y, z);
		}
		case 19:
		{
			return new GuiVesselLiquid(player.inventory, world, x, y, z);
		}
		case 20:
		{
			return new GuiFirepit(player.inventory, (TileEntityFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new GuiAnvil(player.inventory, (TileEntityAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new GuiScribe(player.inventory, (TileEntityScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new GuiForge(player.inventory, (TileEntityForge) te, world, x, y, z);
		}
		case 24:
		{
			return null;//was metallurgy table
		}
		case 25:
		{
			return new GuiSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
		}
		case 26:
		{
			return new GuiBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		}
		case 27:
		{
			return new GuiCalendar(player, world, x, y, z);
		}
		case 28:
		{
			return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingType , world, x, y, z);
		}
		case 29:
		{
			return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te), world, x, y, z);
		}
		case 31:
		{
			return new GuiInventoryTFC(player);
		}
		case 32:
		{
			return new GuiFoodPrep(player.inventory, ((TileEntityFoodPrep) te), world, x, y, z);
		}
		case 33:
		{
			return new GuiQuern(player.inventory, ((TileEntityQuern) te), world, x, y, z);
		}
		case 34:
		{
			return new GuiBlueprint(player, world, x, y, z);
		}
		case 35:
		{
			return new GuiBarrel(player.inventory,((TileEntityBarrel)te),world,x,y,z);
		}
		case 36:
		{
			return null;
		}
		case 37:
		{
			return new GuiCrucible(player.inventory,((TECrucible)te), world, x, y, z);
		}
		case 38:
		{
			return new GuiMold(player.inventory, world, x, y, z);
		}
		case 39:
		{
			return new GuiVessel(player.inventory, world, x, y, z);
		}
		case 40:
		{
			return new GuiQuiver(player.inventory, world, x, y, z);
		}
		case 41:
		{
			return new GuiNestBox(player.inventory, ((TENestBox)te), world, x, y, z);
		}
		default:
		{
			return null;
		}
		}
	}

	@ForgeSubscribe
	public void openGuiHandler(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof GuiInventoryTFC))
		{
			event.gui = new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer);
		}
	}
}
