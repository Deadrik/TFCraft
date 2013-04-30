package TFC.Items.Tools;

import java.util.List;

import TFC.*;
import TFC.Blocks.BlockSlab;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
				world.markBlockForUpdate(x, y+1, z);
				return true;
			}
		}
		return false;
	}

}