package net.minecraft.src;

import java.io.File;
import java.util.Map;

import TFC.Containers.*;
import TFC.Core.IProxy;
import TFC.Core.*;
import TFC.Entities.*;
import TFC.TileEntities.*;
import cpw.mods.fml.server.FMLServerHandler;

public class ServerProxy implements IProxy {

	@Override
	public void registerRenderInformation() {
		// NOOP on server
	}

	@Override
	public void registerTileEntities() 
	{
	    ModLoader.registerTileEntity(TileEntityTerraLogPile.class, "TerraLogPile");
        ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
        
        ModLoader.registerTileEntity(TileEntityTerraFirepit.class, "TerraFirepit");
        ModLoader.registerTileEntity(TileEntityTerraAnvil.class, "TerraAnvil");
        ModLoader.registerTileEntity(TileEntityTerraScribe.class, "TerraScribe");
        ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
        ModLoader.registerTileEntity(TileEntityTerraForge.class, "TerraForge");
        ModLoader.registerTileEntity(TileEntityTerraMetallurgy.class, "TerraMetallurgy");
        ModLoader.registerTileEntity(TileEntityTerraBloomery.class, "TerraBloomery");
        ModLoader.registerTileEntity(TileEntityTerraSluice.class, "TerraSluice");
        
        ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
        ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
        ModLoader.registerTileEntity(TileEntityChestTFC.class, "TileEntityChestTFC");
        
        ModLoader.registerEntityID(EntityCowTFC.class, "cow", 0);
        ModLoader.registerEntityID(EntitySheepTFC.class, "sheep", 1);
        ModLoader.registerEntityID(EntityBear.class, "bear", 2,0xd1d003, 0x101010);
        ModLoader.registerEntityID(EntityChickenTFC.class, "chicken", 3);
        ModLoader.registerEntityID(EntityPigTFC.class, "pig", 4);
        ModLoader.registerEntityID(EntitySquidTFC.class, "squid", 5);
	}

	@Override
	public void registerTranslations() {
		// NOOP on server
	}

	@Override
	public File getMinecraftDir() {
		return new File(".");
	}

	@Override
	public void applyExtraDataToDrops(EntityItem entityitem, NBTTagCompound data) {
		entityitem.item.setTagCompound(data);
	}

	@Override
	public boolean isRemote() {
		return false;
	}

	@Override
	public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
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
            default:
            {
                return null;
            }
        }
	}

	@Override
	public World getCurrentWorld() {
		// NOOP on server: there's lots
		return null;
	}
	
	@Override
	public String getDisplayName(ItemStack is) 
	{
		return "";
	}

	public boolean onTickInGame(float var1, Object mc) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addRenderer(Map map) {
		// TODO Auto-generated method stub
		
	}

	public boolean renderWorldBlock(Object renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		// TODO Auto-generated method stub
		return false;
	}


	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public int blockGetMixedBrightnessForBlock(Block B, IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int blockGetRenderBlockPass(Block B) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public AxisAlignedBB blockGetSelectedBoundingBoxFromPool(Block B,World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean areItemStacksEqual(ItemStack is1, ItemStack is2) {
		return ItemStack.func_46124_a(is1, is2);
	}

	@Override
	public int getUniqueBlockModelID(BaseMod var0, boolean var1) {
		//Not used on server
		return 0;
	}

    @Override
    public void takenFromCrafting(EntityPlayer entityplayer,
            ItemStack itemstack, IInventory iinventory)
    {
        FMLServerHandler.instance().onItemCrafted(entityplayer, itemstack, iinventory);  
    }

    @Override
    public void sendCustomPacket(Packet packet)
    {
        ModLoader.getMinecraftServerInstance().configManager.sendPacketToAllPlayers(packet);        
    }
    
    @Override
    public EntityPlayer getPlayer(NetworkManager network)
    {
        return ((NetServerHandler)network.getNetHandler()).getPlayerEntity();
    }

    @Override
    public int getArmorRenderID(int i)
    {
        return 0;
    }

    @Override
    public boolean getGraphicsLevel()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void registerKeys(BaseMod b)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyboardEvent(Object e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int waterColorMultiplier(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getLightBrightness(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLightBrightnessSkyBlocks(IBlockAccess par1iBlockAccess,
            int par2, int par3, int par4, int par5)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void sendCustomPacketToPlayer(String player, Packet packet)
    {
        ModLoader.getMinecraftServerInstance().configManager.sendPacketToPlayer(player, packet);     
    }

    @Override
    public String getPlayerName()
    {
        return "";
    }

    @Override
    public void handleHealthUpdate(Class c, byte par1)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean aiTargetShouldExecute(EntityAITarget target, EntityLiving par1EntityLiving, boolean par2)
    {
        return target.func_48284_a(par1EntityLiving, par2);
    }
    @Override
    public boolean aiTargetfunc_48100_a(Class par1Class, EntityLiving entity)
    {
        return entity.func_48336_a(par1Class);
    }
}
