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

public class ServerProxy implements IProxy {

	@Override
	public void registerRenderInformation() {
		// NOOP on server
	}

	@Override
	public void registerTileEntities() 
	{
		/*for (IronChestType typ : IronChestType.values()) {
			ModLoader.registerTileEntity(typ.clazz, typ.name());
		}*/
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
	public Object getGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
		TileEntity te=world.getBlockTileEntity(X, Y, Z);
		/*if (te!=null && te instanceof TileEntityIronChest) {
      TileEntityIronChest icte=(TileEntityIronChest) te;
      return new ContainerIronChestBase(player.inventory, icte, icte.getType(), 0, 0);
    } else*/ {
    	return null;
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
