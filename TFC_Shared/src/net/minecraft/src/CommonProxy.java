package net.minecraft.src;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.MinecraftForge;

import TFC.Containers.*;
import TFC.Core.*;
import TFC.Entities.*;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiTerraAnvil;
import TFC.GUI.GuiTerraBloomery;
import TFC.GUI.GuiTerraFirepit;
import TFC.GUI.GuiTerraForge;
import TFC.GUI.GuiTerraLogPile;
import TFC.GUI.GuiTerraMetallurgy;
import TFC.GUI.GuiTerraScribe;
import TFC.GUI.GuiTerraSluice;
import TFC.GUI.GuiTerraWorkbench;
import TFC.Render.TileEntityChestRendererTFC;
import TFC.TileEntities.*;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.server.FMLServerHandler;

public class CommonProxy implements IGuiHandler
{

	public void registerRenderInformation() {
		// NOOP on server
	}

    public void registerTileEntities()
    {
        ModLoader.registerTileEntity(TileEntityTerraLogPile.class, "TerraLogPile");
        ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
        ModLoader.registerTileEntity(TileEntityTerraFirepit.class, "TerraFirepit");
        ModLoader.registerTileEntity(TileEntityTerraAnvil.class, "TerraAnvil");
        ModLoader.registerTileEntity(TileEntityTerraScribe.class, "TerraScribe");
        ModLoader.registerTileEntity(TileEntityTerraForge.class, "TerraForge");
        ModLoader.registerTileEntity(TileEntityTerraMetallurgy.class, "TerraMetallurgy");
        ModLoader.registerTileEntity(TileEntityTerraBloomery.class, "TerraBloomery");
        ModLoader.registerTileEntity(TileEntityTerraSluice.class, "TerraSluice");
        ModLoader.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
        ModLoader.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

        ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
        ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
        ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest", new TileEntityChestRendererTFC());
        
        EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cow", 100, 0xffffff, 0xbbbbbb);
        EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheep", 101, 0xffffff, 0xbbbbbb);
        EntityRegistry.registerGlobalEntityID(EntityBear.class, "bear", 102, 0xd1d003, 0x101010);
        EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chicken", 103, 0xffffff, 0xbbbbbb);
        EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pig", 104, 0xffffff, 0xbbbbbb);
        EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squid", 105, 0xffffff, 0xbbbbbb);
        EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deer", 106, 0xffffff, 0x105510);
        
        EntityRegistry.registerModEntity(EntityTerraJavelin.class, "javelin", 1,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntitySquidTFC.class, "squid", 2,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityFallingStone.class, "fallingstone", 3,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityFallingDirt.class, "fallingdirt", 4,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityCowTFC.class, "cowTFC", 6,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityWolfTFC.class, "wolfTFC", 7,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityBear.class, "bearTFC", 8,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityChickenTFC.class, "chickenTFC", 9,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityPigTFC.class, "pigTFC", 10,TerraFirmaCraft.instance, 160, 5, true);
        EntityRegistry.registerModEntity(EntityDeer.class, "deerTFC", 11,TerraFirmaCraft.instance, 160, 5, true);
    }

	public void registerTranslations() {
	}

	public File getMinecraftDir() {
		return ModLoader.getMinecraftInstance().getMinecraftDir();/*new File(".");*/
	}

	public boolean isRemote() {
		return false;
	}

	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te=world.getBlockTileEntity(x, y, z);
		switch(ID)
        {
            case 0:
            {
                return new ContainerTerraLogPile(player.inventory, (TileEntityTerraLogPile) te, world, x, y, z);
            }
            case 1:
            {
                return new ContainerTerraWorkbench(player.inventory, (TileEntityTerraWorkbench) te, world, x, y, z);
            }
            case 20:
            {
                return new ContainerTerraFirepit(player.inventory, (TileEntityTerraFirepit) te);
            }
            case 21:
            {
                return new ContainerTerraAnvil(player.inventory, (TileEntityTerraAnvil) te);
            }
            case 22:
            {
                return new ContainerTerraScribe(player.inventory, (TileEntityTerraScribe) te, world);
            }
            case 23:
            {
                return new ContainerTerraForge(player.inventory, (TileEntityTerraForge) te);
            }
            case 24:
            {
                return new ContainerTerraMetallurgy(player.inventory, (TileEntityTerraMetallurgy) te, world);
            }
            case 25:
            {
                return new ContainerTerraSluice(player.inventory, (TileEntityTerraSluice) te);
            }
            case 26:
            {
                return new ContainerTerraBloomery(player.inventory, (TileEntityTerraBloomery) te);
            }
            case 28:
            {
                return new ContainerKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType, world);
            }
            case 29:
            {
                return new ContainerChestTFC(player.inventory, (TileEntityChestTFC) te);
            }
            default:
            {
                return null;
            }
        }
	}

	public World getCurrentWorld() {
		return null;
	}

	public boolean renderWorldBlock(Object renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		// TODO Auto-generated method stub
		return false;
	}

	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		return 0;
    }

	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return 0;
    }


    public void takenFromCrafting(EntityPlayer entityplayer,
            ItemStack itemstack, IInventory iinventory)
    {
        GameRegistry.onItemCrafted(entityplayer, itemstack, iinventory);  
    }

    public void sendCustomPacket(Packet packet)
    {
      
    }

    public int getArmorRenderID(int i)
    {
        return 0;
    }

    public boolean getGraphicsLevel()
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void registerKeys(){
    }

    public void registerKeyBindingHandler(){ 
    }
    

    public void registerSoundHandler() {
    }

    public void sendCustomPacketToPlayer(String player, Packet packet)
    {
        ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);     
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
                return new GuiTerraLogPile(player.inventory, (TileEntityTerraLogPile) te, world, x, y, z);
            }
            case 1:
            {
                return new GuiTerraWorkbench(player.inventory, (TileEntityTerraWorkbench) te, world, x, y, z);
            }
            case 20:
            {
                return new GuiTerraFirepit(player.inventory, (TileEntityTerraFirepit) te);
            }
            case 21:
            {
                return new GuiTerraAnvil(player.inventory, (TileEntityTerraAnvil) te);
            }
            case 22:
            {
                return new GuiTerraScribe(player.inventory, (TileEntityTerraScribe) te, world);
            }
            case 23:
            {
                return new GuiTerraForge(player.inventory, (TileEntityTerraForge) te);
            }
            case 24:
            {
                return new GuiTerraMetallurgy(player.inventory, (TileEntityTerraMetallurgy) te, world);
            }
            case 25:
            {
                return new GuiTerraSluice(player.inventory, (TileEntityTerraSluice) te);
            }
            case 26:
            {
                return new GuiTerraBloomery(player.inventory, (TileEntityTerraBloomery) te);
            }
            case 27:
            {
                return new GuiCalendar(player, world, x, y, z);
            }
            case 28:
            {
                return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType ,world);
            }
            case 29:
            {
                return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te));
            }

        }
		return null;
	}
	
}
