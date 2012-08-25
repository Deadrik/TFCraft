package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC.*;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.mod_TFC;

public class BlockTerraMolten extends Block
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;

	public BlockTerraMolten(int i)
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
