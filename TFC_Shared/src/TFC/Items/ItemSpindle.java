package TFC.Items;

import java.util.BitSet;
import java.util.Random;

import TFC.*;
import TFC.Blocks.BlockSlab;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntitySuperDetailed;
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