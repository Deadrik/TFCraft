package TFC.Render;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.Blocks.Devices.BlockChestTFC;
import TFC.TileEntities.TileEntityChestTFC;
import cpw.mods.fml.common.FMLLog;

public class TileEntityChestRendererTFC extends TileEntitySpecialRenderer
{
	private static final ResourceLocation field_110635_a = new ResourceLocation("textures/entity/chest/trapped_double.png");
	private static final ResourceLocation field_110634_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
	private static final ResourceLocation field_110632_d = new ResourceLocation("textures/entity/chest/normal_double.png");
	private static final ResourceLocation field_110633_e = new ResourceLocation("textures/entity/chest/trapped.png");
	private static final ResourceLocation field_110630_f = new ResourceLocation("textures/entity/chest/christmas.png");
	private static final ResourceLocation field_110631_g = new ResourceLocation("textures/entity/chest/normal.png");

	/** The normal small chest model. */
	private ModelChest chestModel = new ModelChest();

	/** The large double chest model. */
	private ModelChest largeChestModel = new ModelLargeChest();

	/** If true, chests will be rendered with the Christmas present textures. */
	private boolean isChristmas;

	public TileEntityChestRendererTFC()
	{
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
		{
			this.isChristmas = true;
		}
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityChestAt(TileEntityChestTFC par1TileEntityChest, double par2, double par4, double par6, float par8)
	{
		int i;

		if (!par1TileEntityChest.hasWorldObj())
		{
			i = 0;
		}
		else
		{
			Block block = par1TileEntityChest.getBlockType();
			i = par1TileEntityChest.getBlockMetadata();

			if (block instanceof BlockChestTFC && i == 0)
			{
				try
				{
					((BlockChest)block).unifyAdjacentChests(par1TileEntityChest.getWorldObj(), par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
				}
				catch (ClassCastException e)
				{
					FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest",
							par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
				}
				i = par1TileEntityChest.getBlockMetadata();
			}

			par1TileEntityChest.checkForAdjacentChests();
		}

		if (par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null)
		{
			ModelChest modelchest;

			if (par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPosition == null)
			{
				modelchest = this.chestModel;

				if (par1TileEntityChest.getChestType() == 1)
				{
					this.bindTexture(field_110633_e);
				}
				else if (this.isChristmas)
				{
					this.bindTexture(field_110630_f);
				}
				else
				{
					this.bindTexture(field_110631_g);
				}
			}
			else
			{
				modelchest = this.largeChestModel;

				if (par1TileEntityChest.getChestType() == 1)
				{
					this.bindTexture(field_110635_a);
				}
				else if (this.isChristmas)
				{
					this.bindTexture(field_110634_c);
				}
				else
				{
					this.bindTexture(field_110632_d);
				}
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			short short1 = 0;

			if (i == 2)
			{
				short1 = 180;
			}

			if (i == 3)
			{
				short1 = 0;
			}

			if (i == 4)
			{
				short1 = 90;
			}

			if (i == 5)
			{
				short1 = -90;
			}

			if (i == 2 && par1TileEntityChest.adjacentChestXPos != null)
			{
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			}

			if (i == 5 && par1TileEntityChest.adjacentChestZPosition != null)
			{
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
			}

			GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float f1 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
			float f2;

			if (par1TileEntityChest.adjacentChestZNeg != null)
			{
				f2 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;

				if (f2 > f1)
				{
					f1 = f2;
				}
			}

			if (par1TileEntityChest.adjacentChestXNeg != null)
			{
				f2 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;

				if (f2 > f1)
				{
					f1 = f2;
				}
			}

			f1 = 1.0F - f1;
			f1 = 1.0F - f1 * f1 * f1;
			modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
			modelchest.renderAll();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityChestAt((TileEntityChestTFC)par1TileEntity, par2, par4, par6, par8);
	}
}
