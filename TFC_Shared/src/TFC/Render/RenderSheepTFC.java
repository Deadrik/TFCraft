package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Entities.Mobs.EntitySheepTFC;

public class RenderSheepTFC extends RenderSheep
{
	private static final ResourceLocation SheepFurTexture = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private static final ResourceLocation SheepTexture = new ResourceLocation("textures/entity/sheep/sheep.png");

	public RenderSheepTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
	}

	protected int setWoolColorAndRender(EntitySheepTFC par1EntitySheep, int par2, float par3)
	{
		if (par2 == 0 && !par1EntitySheep.getSheared())
		{
			this.func_110776_a(SheepFurTexture);
			float var4 = 1.0F;
			int var5 = par1EntitySheep.getFleeceColor();
			GL11.glColor3f(var4 * EntitySheepTFC.fleeceColorTable[var5][0], var4 * EntitySheepTFC.fleeceColorTable[var5][1], var4 * EntitySheepTFC.fleeceColorTable[var5][2]);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	protected ResourceLocation getTexture(EntitySheep par1EntitySheep)
	{
		return SheepTexture;
	}

	@Override
	protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return this.getTexture((EntitySheep)par1Entity);
	}
}
