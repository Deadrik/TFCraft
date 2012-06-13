package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.forge.ITextureProvider;

public class ItemChisel extends ItemTool
implements ITextureProvider
{
	public ItemChisel(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
		this.setMaxDamage(e.getMaxUses()/2);
		this.maxStackSize = 1;
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
	{
		par1ItemStack.damageItem(1, par6EntityLiving);
		return true;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return itemstack;
		}		
		int x = objectMouseOver.blockX;
		int y = objectMouseOver.blockY;
		int z = objectMouseOver.blockZ;
		int side = objectMouseOver.sideHit;

		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if(id == mod_TFC_Core.terraStoneIgIn.blockID) {
			world.setBlockAndMetadataWithNotify(x, y, z, mod_TFC_Core.terraStoneIgInSmooth.blockID, meta);
		} else if(id == mod_TFC_Core.terraStoneIgEx.blockID) {
			world.setBlockAndMetadataWithNotify(x, y, z, mod_TFC_Core.terraStoneIgExSmooth.blockID, meta);
		} else if(id == mod_TFC_Core.terraStoneSed.blockID) {
			world.setBlockAndMetadataWithNotify(x, y, z, mod_TFC_Core.terraStoneSedSmooth.blockID, meta);
		} else if(id == mod_TFC_Core.terraStoneMM.blockID) {
			world.setBlockAndMetadataWithNotify(x, y, z, mod_TFC_Core.terraStoneMMSmooth.blockID, meta);
		}

		itemstack.setItemDamage(itemstack.getItemDamage()+1);

		if(itemstack.getItemDamage() == itemstack.getMaxDamage()) {
			return new ItemStack(this,0);
		}

		return itemstack;
	}
}