package TFC.Handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import TFC.Containers.ContainerAnvil;
import TFC.Containers.ContainerBarrel;
import TFC.Containers.ContainerBlastFurnace;
import TFC.Containers.ContainerChestTFC;
import TFC.Containers.ContainerCrucible;
import TFC.Containers.ContainerFirepit;
import TFC.Containers.ContainerFoodPrep;
import TFC.Containers.ContainerForge;
import TFC.Containers.ContainerHorseInventoryTFC;
import TFC.Containers.ContainerLiquidVessel;
import TFC.Containers.ContainerLogPile;
import TFC.Containers.ContainerMold;
import TFC.Containers.ContainerNestBox;
import TFC.Containers.ContainerPlanSelection;
import TFC.Containers.ContainerQuern;
import TFC.Containers.ContainerQuiver;
import TFC.Containers.ContainerSluice;
import TFC.Containers.ContainerSpecialCrafting;
import TFC.Containers.ContainerVessel;
import TFC.Containers.ContainerWorkbench;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Entities.Mobs.EntityHorseTFC;
import TFC.TileEntities.TEBlastFurnace;
import TFC.TileEntities.TECrucible;
import TFC.TileEntities.TENestBox;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TEBarrel;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TEFoodPrep;
import TFC.TileEntities.TEForge;
import TFC.TileEntities.TileEntityLogPile;
import TFC.TileEntities.TileEntityQuern;
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
			return null;//was scribing table
		}
		case 23:
		{
			return new ContainerForge(player.inventory, (TEForge) te, world, x, y, z);
		}
		case 24:
		{
			return new ContainerPlanSelection(player, (TileEntityAnvil) te, world, x, y, z);//was metallurgy table
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
			return new ContainerFoodPrep(player.inventory, (TEFoodPrep) te, world, x, y, z);
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
			return new ContainerBarrel(player.inventory,((TEBarrel)te),world,x,y,z);
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
		case 42:
		{
			List list = player.worldObj.getEntitiesWithinAABB(EntityHorseTFC.class, player.boundingBox.expand(2, 2, 2));
			if(list.size() > 0){
				EntityHorseTFC horse = (EntityHorseTFC) list.get(0);
				return new ContainerHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
			}
			return null;
		}
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
