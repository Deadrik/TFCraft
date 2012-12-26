package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.TerraFirmaCraft;
import TFC.TerraFirmaCraft.*;
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
public class BlockMolten extends Block
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;

	public BlockMolten(int i)
	{
		super(i, Material.iron);
		this.setLightValue(1.0F);
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		return 67;
	}

	@Override
	public String getTextureFile() {

		return "/bioxx/terrablocks.png";
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}	

}
