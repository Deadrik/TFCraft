package net.minecraft.src.TFC_Core;

import java.io.File;
import java.util.Map;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.TFC_Game.*;
import net.minecraft.src.TFC_Mining.ContainerTerraSluice;
import net.minecraft.src.TFC_Mining.TileEntityTerraSluice;

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
	public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te=world.getBlockTileEntity(x, y, z);
		/*if (te!=null && te instanceof TileEntityIronChest) {
      TileEntityIronChest icte=(TileEntityIronChest) te;
      return new ContainerIronChestBase(player.inventory, icte, icte.getType(), 0, 0);
    } else*/ 
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


	public int blockColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
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
}
