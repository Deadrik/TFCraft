package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.common.FMLLog;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Devices.BlockChestTFC;
import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.Constant.Global;

public class TESRChest extends TileEntitySpecialRenderer
{
	private static ResourceLocation[] texNormal;
	private static ResourceLocation[] texNormalDouble;
	/** The normal small chest model. */
	private ModelChest chestModel = new ModelChest();

	/** The large double chest model. */
	private ModelChest largeChestModel = new ModelLargeChest();

	public TESRChest()
	{
		if(texNormal == null)
		{
			texNormal = new ResourceLocation[Global.WOOD_ALL.length];
			texNormalDouble = new ResourceLocation[Global.WOOD_ALL.length];
			for(int i = 0; i < Global.WOOD_ALL.length; i++)
			{
				texNormal[i] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_" + Global.WOOD_ALL[i] + ".png");
				texNormalDouble[i] = new ResourceLocation(Reference.MOD_ID+":textures/models/chest/normal_double_" + Global.WOOD_ALL[i] + ".png");
			}
		}
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityChestAt(TEChest te, double par2, double par4, double par6, float par8)
	{
		int i;

		if (!te.hasWorldObj())
		{
			i = 0;
		}
		else
		{
			Block block = te.getBlockType();
			i = te.getBlockMetadata();

			if (block instanceof BlockChestTFC && i == 0)
			{
				try
				{
					((BlockChestTFC)block).unifyAdjacentChests(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
				}
				catch (ClassCastException e)
				{
					FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest",
							te.xCoord, te.yCoord, te.zCoord);
				}
				i = te.getBlockMetadata();
			}

			te.checkForAdjacentChests();
		}

		if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null)
		{
			ModelChest modelchest;

			if (te.adjacentChestXPos == null && te.adjacentChestZPos == null && !te.isDoubleChest)
			{
				modelchest = this.chestModel;

				this.bindTexture(texNormal[te.type]);
			}
			else
			{
				modelchest = this.largeChestModel;

				this.bindTexture(texNormalDouble[te.type]);
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

			if (i == 2 && te.adjacentChestXPos != null)
			{
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			}

			if (i == 5 && te.adjacentChestZPos != null)
			{
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
			}

			GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			if (te.isDoubleChest)
			{
				GL11.glScalef(0.5f, 0.5F, 0.5F);
			}

			float f1 = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * par8;
			float f2;

			if (te.adjacentChestZNeg != null)
			{
				f2 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * par8;

				if (f2 > f1)
				{
					f1 = f2;
				}
			}

			if (te.adjacentChestXNeg != null)
			{
				f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * par8;

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
		this.renderTileEntityChestAt((TEChest)par1TileEntity, par2, par4, par6, par8);
	}
}
