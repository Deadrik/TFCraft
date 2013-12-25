package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Core.TFC_Textures;
import TFC.Effects.GasFX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHotWaterStill extends BlockFreshWaterStill 
{

	public BlockHotWaterStill(int par1) 
	{
		super(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0x1f5099;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if(world.isAirBlock(i-1, j, k) || world.isAirBlock(i+1, j, k) || 
				world.isAirBlock(i, j, k-1) || world.isAirBlock(i, j, k+1) || 
				world.isAirBlock(i, j+1, k))
			;
		else return;

		double f = (double)i + 0.5F;
		double f1 = (double)j + 1f;
		double f2 = (double)k + 0.5F;

		double f4 = random.nextFloat() * -0.1F;
		double f5 = random.nextFloat() * -0.1F;
		double f6 = random.nextFloat() * -0.1F;

		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registerer)
	{
		this.theIcon = new Icon[] {registerer.registerIcon(Reference.ModID+":water_still"), registerer.registerIcon(Reference.ModID+":water_flow")};
		TFC_Textures.GasFXIcon = registerer.registerIcon(Reference.ModID + ":" + "Steam");
	}
}
