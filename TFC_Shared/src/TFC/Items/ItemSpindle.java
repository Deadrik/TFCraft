package TFC.Items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.world.World;
import TFC.Enums.EnumSize;

public class ItemSpindle extends ItemTerraTool
{
	static Random random = new Random();
	public ItemSpindle(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
		this.setMaxDamage(e.getMaxUses()/2);
	}
		
	@Override
	public EnumSize getSize() {
		return EnumSize.VERYSMALL;
	}
	

	public static boolean handleActivation(World world, EntityPlayer player, int x, int y, int z, int blockID, int meta, int side, float hitX, float hitY, float hitZ)
	{
		return true;
	}
}