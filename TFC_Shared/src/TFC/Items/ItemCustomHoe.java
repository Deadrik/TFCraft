package TFC.Items;

import java.util.List;

import TFC.Core.TFCSettings;
import net.minecraft.src.*;
import net.minecraft.src.forge.ForgeHooks;
import net.minecraft.src.forge.ITextureProvider;

public class ItemCustomHoe extends ItemHoe
implements ITextureProvider
{
	public ItemCustomHoe(int i, EnumToolMaterial e)
	{
		super(i, e);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
	
	public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFCSettings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
	{
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
		{
			return false;
		}
		else
		{
			if (ForgeHooks.onUseHoe(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6))
			{
				par1ItemStack.damageItem(1, par2EntityPlayer);
				return true;
			}
			int var8 = par3World.getBlockId(par4, par5, par6);
			int var9 = par3World.getBlockId(par4, par5 + 1, par6);

			if (par7 == 0 || var9 != 0 || var8 != mod_TFC_Core.terraGrass.blockID && var8 != mod_TFC_Core.terraGrass2.blockID &&
					var8 != mod_TFC_Core.terraDirt.blockID && var8 != mod_TFC_Core.terraDirt2.blockID)
			{
				return false;
			}
			else
			{
				Block var10 = var8 == mod_TFC_Core.terraDirt.blockID || var8 == mod_TFC_Core.terraGrass.blockID ? mod_TFC_Core.terraDirt : var8 == mod_TFC_Core.terraDirt2.blockID || var8 == mod_TFC_Core.terraGrass2.blockID ? mod_TFC_Core.terraDirt2 : null;
				if(var10 != null)
				{
					int meta = par3World.getBlockMetadata(par4, par5, par6);
					if(var10.blockID == mod_TFC_Core.terraDirt.blockID)
					{
						par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (par3World.isRemote)
						{
							return true;
						}
						else
						{
							par3World.setBlockAndMetadataWithNotify(par4, par5, par6, mod_TFC_Core.tilledSoil.blockID, meta);
							par1ItemStack.damageItem(1, par2EntityPlayer);
							return true;
						}
					}
					else if(var10.blockID == mod_TFC_Core.terraDirt2.blockID)
					{
						par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (par3World.isRemote)
						{
							return true;
						}
						else
						{
							par3World.setBlockAndMetadataWithNotify(par4, par5, par6, mod_TFC_Core.tilledSoil2.blockID, meta);
							par1ItemStack.damageItem(1, par2EntityPlayer);
							return true;
						}
					}
				}
			}
			return false;
		}
	}
}