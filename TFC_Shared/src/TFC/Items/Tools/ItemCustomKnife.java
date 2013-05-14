package TFC.Items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Enums.EnumSize;

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

	@Override
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
			if(side == 1 && !TFC_Core.isSoil(id) && !TFC_Core.isWater(id) && world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlock(x, y+1, z, TFCBlocks.FoodPrep.blockID);
				world.markBlockForUpdate(x, y+1, z);
				return true;
			}
		}
		return false;
	}

}