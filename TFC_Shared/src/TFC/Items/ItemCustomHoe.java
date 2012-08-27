package TFC.Items;

import java.util.List;

import TFC.Core.TFCSettings;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.UseHoeEvent;

public class ItemCustomHoe extends ItemHoe
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
			UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }
            if (event.isHandeled())
            {
                par1ItemStack.damageItem(1, par2EntityPlayer);
                return true;
            }
            
			int var8 = par3World.getBlockId(par4, par5, par6);
			int var9 = par3World.getBlockId(par4, par5 + 1, par6);

			if (par7 == 0 || var9 != 0 || var8 != TFCBlocks.terraGrass.blockID && var8 != TFCBlocks.terraGrass2.blockID &&
					var8 != TFCBlocks.terraDirt.blockID && var8 != TFCBlocks.terraDirt2.blockID)
			{
				return false;
			}
			else
			{
				Block var10 = var8 == TFCBlocks.terraDirt.blockID || var8 == TFCBlocks.terraGrass.blockID ? TFCBlocks.terraDirt : var8 == TFCBlocks.terraDirt2.blockID || var8 == TFCBlocks.terraGrass2.blockID ? TFCBlocks.terraDirt2 : null;
				if(var10 != null)
				{
					int meta = par3World.getBlockMetadata(par4, par5, par6);
					if(var10.blockID == TFCBlocks.terraDirt.blockID)
					{
						par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (par3World.isRemote)
						{
							return true;
						}
						else
						{
							par3World.setBlockAndMetadataWithNotify(par4, par5, par6, TFCBlocks.tilledSoil.blockID, meta);
							par1ItemStack.damageItem(1, par2EntityPlayer);
							return true;
						}
					}
					else if(var10.blockID == TFCBlocks.terraDirt2.blockID)
					{
						par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (par3World.isRemote)
						{
							return true;
						}
						else
						{
							par3World.setBlockAndMetadataWithNotify(par4, par5, par6, TFCBlocks.tilledSoil2.blockID, meta);
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