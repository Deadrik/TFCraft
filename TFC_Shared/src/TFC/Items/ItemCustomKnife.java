package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Blocks.BlockSlab;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCustomKnife extends ItemWeapon
{
	public ItemCustomKnife(int i, EnumToolMaterial e)
	{
		super(i, e);
		this.setMaxDamage(e.getMaxUses());
		this.weaponDamage = 50 + e.getDamageVsEntity();
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	public EnumSize getSize()
	{
		return EnumSize.SMALL;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float HitX, float HitY, float HitZ) 
	{
		int id = world.getBlockId(x, y, z);
		if(!world.isRemote && id != TFCBlocks.ToolRack.blockID)
		{
			if(side == 1 && !TFC_Core.isSoil(id) && !TFC_Core.isWater(id))
			{
				world.setBlock(x, y+1, z, TFCBlocks.FoodPrep.blockID);
				world.markBlockAsNeedsUpdate(x, y+1, z);
				return true;
			}
		}
		return false;
	}

}