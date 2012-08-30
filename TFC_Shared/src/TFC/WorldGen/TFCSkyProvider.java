package TFC.WorldGen;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.relauncher.ReflectionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import net.minecraftforge.client.SkyProvider;

public class TFCSkyProvider extends SkyProvider{

	Minecraft mc;

	int starGLCallList;

	int glSkyList;

	int glSkyList2;

	public TFCSkyProvider(Minecraft m)
	{
		mc = m;
		starGLCallList = ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, 15);

		glSkyList= ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, 16);

		glSkyList2= ReflectionHelper.getPrivateValue(RenderGlobal.class, mc.renderGlobal, 17);
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) 
	{
		this.mc = mc;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 var2 = ((TFCProvider)world.provider).getSkyColor(this.mc.renderViewEntity, partialTicks);
		float var3 = (float)var2.xCoord;
		float var4 = (float)var2.yCoord;
		float var5 = (float)var2.zCoord;
		float var8;

		if (mc.gameSettings.anaglyph)
		{
			float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
			float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
			var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
			var3 = var6;
			var4 = var7;
			var5 = var8;
		}

		GL11.glColor3f(var3, var4, var5);
		Tessellator var23 = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(var3, var4, var5);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.disableStandardItemLighting();
		float[] var24 = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		float var9;
		float var10;
		float var11;
		float var12;

		if (var24 != null)
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			var8 = var24[0];
			var9 = var24[1];
			var10 = var24[2];
			float var13;

			if (this.mc.gameSettings.anaglyph)
			{
				var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
				var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
				var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
				var8 = var11;
				var9 = var12;
				var10 = var13;
			}

			var23.startDrawing(6);
			var23.setColorRGBA_F(var8, var9, var10, var24[3]);
			var23.addVertex(0.0D, 100.0D, 0.0D);
			byte var26 = 16;
			var23.setColorRGBA_F(var24[0], var24[1], var24[2], 0.0F);

			for (int var27 = 0; var27 <= var26; ++var27)
			{
				var13 = (float)var27 * (float)Math.PI * 2.0F / (float)var26;
				float var14 = MathHelper.sin(var13);
				float var15 = MathHelper.cos(var13);
				var23.addVertex((double)(var14 * 120.0F), (double)(var15 * 120.0F), (double)(-var15 * 40.0F * var24[3]));
			}

			var23.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glPushMatrix();
		var8 = 1.0F - world.getRainStrength(partialTicks);
		var9 = 0.0F;
		var10 = 0.0F;
		var11 = 0.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
		GL11.glTranslatef(var9, var10, var11);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		var12 = 30.0F;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain/sun.png"));
		var23.startDrawingQuads();
		var23.addVertexWithUV((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
		var23.addVertexWithUV((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
		var23.addVertexWithUV((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
		var23.addVertexWithUV((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
		var23.draw();
		var12 = 20.0F;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain/moon_phases.png"));
		int var28 = world.getMoonPhase(partialTicks);
		int var30 = var28 % 4;
		int var29 = var28 / 4 % 2;
		float var16 = (float)(var30 + 0) / 4.0F;
		float var17 = (float)(var29 + 0) / 2.0F;
		float var18 = (float)(var30 + 1) / 4.0F;
		float var19 = (float)(var29 + 1) / 2.0F;
		var23.startDrawingQuads();
		var23.addVertexWithUV((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
		var23.addVertexWithUV((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
		var23.addVertexWithUV((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
		var23.addVertexWithUV((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
		var23.draw();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float var20 = world.getStarBrightness(partialTicks) * var8;

		if (var20 > 0.0F)
		{
			GL11.glColor4f(var20, var20, var20, var20);
			GL11.glCallList(this.starGLCallList);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double var25 = this.mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

		if (var25 < 0.0D)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(this.glSkyList2);
			GL11.glPopMatrix();
			var10 = 1.0F;
			var11 = -((float)(var25 + 65.0D));
			var12 = -var10;
			var23.startDrawingQuads();
			var23.setColorRGBA_I(0, 255);
			var23.addVertex((double)(-var10), (double)var11, (double)var10);
			var23.addVertex((double)var10, (double)var11, (double)var10);
			var23.addVertex((double)var10, (double)var12, (double)var10);
			var23.addVertex((double)(-var10), (double)var12, (double)var10);
			var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
			var23.addVertex((double)var10, (double)var12, (double)(-var10));
			var23.addVertex((double)var10, (double)var11, (double)(-var10));
			var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
			var23.addVertex((double)var10, (double)var12, (double)(-var10));
			var23.addVertex((double)var10, (double)var12, (double)var10);
			var23.addVertex((double)var10, (double)var11, (double)var10);
			var23.addVertex((double)var10, (double)var11, (double)(-var10));
			var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
			var23.addVertex((double)(-var10), (double)var11, (double)var10);
			var23.addVertex((double)(-var10), (double)var12, (double)var10);
			var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
			var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
			var23.addVertex((double)(-var10), (double)var12, (double)var10);
			var23.addVertex((double)var10, (double)var12, (double)var10);
			var23.addVertex((double)var10, (double)var12, (double)(-var10));
			var23.draw();
		}

		if (world.provider.isSkyColored())
		{
			GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
		}
		else
		{
			GL11.glColor3f(var3, var4, var5);
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -((float)(var25 - 16.0D)), 0.0F);
		GL11.glCallList(this.glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);

	}
	
	@SideOnly(Side.CLIENT)

    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float par1)
    {
        par1 /= 3.0F;

        if (par1 < -1.0F)
        {
            par1 = -1.0F;
        }

        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        return Color.getHSBColor(0.62222224F - par1 * 0.05F, 0.5F + par1 * 0.1F, 1.0F).getRGB();
    }

}
