package TFC.Items;

import java.util.List;

import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.UseHoeEvent;

public class ItemCustomHoe extends ItemHoe implements ISize
{
	public ItemCustomHoe(int i, EnumToolMaterial e)
	{
		super(i, e);
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if (world.isRemote || world.getBlockId(x, y, z) == TFCBlocks.ToolRack.blockID)
		{
			return false;
		}
		else
		{
			UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }
            if (event.isHandeled())
            {
                stack.damageItem(1, player);
                return true;
            }
            
			int var8 = world.getBlockId(x, y, z);
			int var9 = world.getBlockId(x, y + 1, z);

			if (side != 1 || var9 != 0 || (!TFC_Core.isGrass(var8) && !TFC_Core.isDirt(var8)))
			{
				return false;
			}
			else
			{
				Block var10 = var8 == TFCBlocks.Dirt.blockID || var8 == TFCBlocks.Grass.blockID || var8 == TFCBlocks.DryGrass.blockID ? TFCBlocks.Dirt : 
					var8 == TFCBlocks.Dirt2.blockID || var8 == TFCBlocks.Grass2.blockID || var8 == TFCBlocks.DryGrass2.blockID ? TFCBlocks.Dirt2 : null;
				if(var10 != null)
				{
					int meta = world.getBlockMetadata(x, y, z);
					if(var10.blockID == TFCBlocks.Dirt.blockID)
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.tilledSoil.blockID, meta);
							world.markBlockNeedsUpdate(x, y, z);
							stack.damageItem(1, player);
							return true;
						}
					}
					else if(var10.blockID == TFCBlocks.Dirt2.blockID)
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);

						if (world.isRemote)
						{
							return true;
						}
						else
						{
							world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.tilledSoil2.blockID, meta);
							world.markBlockNeedsUpdate(x, y, z);
							stack.damageItem(1, player);
							return true;
						}
					}
				}
			}
			return false;
		}
	}
	
	public void addInformation(ItemStack is, List arraylist) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: " + is.getItemDamage());
    }
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
	
	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
}