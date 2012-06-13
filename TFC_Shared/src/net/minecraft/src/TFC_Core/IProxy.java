package net.minecraft.src.TFC_Core;

import java.io.File;
import java.util.Map;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public interface IProxy extends IGuiHandler {

	public abstract void applyExtraDataToDrops(EntityItem item, NBTTagCompound data);

	public abstract World getCurrentWorld();

	public abstract File getMinecraftDir();

	public abstract boolean isRemote();

	public abstract void registerRenderInformation();

	public abstract void registerTileEntities();

	public abstract void registerTranslations();
	
	public abstract String getDisplayName(ItemStack is);
	
	public abstract void addRenderer(Map map);
	
	public abstract boolean renderWorldBlock(Object renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l);
	
	public abstract int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4);
	
	public abstract int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4);
	
	public abstract int blockGetMixedBrightnessForBlock(Block B, IBlockAccess par1IBlockAccess, int par2, int par3, int par4);
	
	public abstract int blockGetRenderBlockPass(Block B);
	
	public abstract AxisAlignedBB blockGetSelectedBoundingBoxFromPool(Block B,World par1World, int par2, int par3, int par4);
	
	public abstract boolean areItemStacksEqual(ItemStack is1, ItemStack is2);
	
	public abstract int getUniqueBlockModelID(BaseMod var0, boolean var1);
	
	public abstract void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory);
	
	public abstract void sendCustomPacket(Packet packet);
	
	public abstract EntityPlayer getPlayer(NetworkManager network);
	
	public abstract int getArmorRenderID(int i);
	
	public abstract boolean getGraphicsLevel();
	
	public abstract void registerKeys(BaseMod b);
	
	public abstract void keyboardEvent(Object e);
}