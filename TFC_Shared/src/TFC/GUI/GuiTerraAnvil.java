package TFC.GUI;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
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
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.GL11;
import TFC.*;
import TFC.Enums.CraftingRuleEnum;
import TFC.Containers.*;
import TFC.TileEntities.*;


public class GuiTerraAnvil extends GuiContainer
{
	private TileEntityTerraAnvil AnvilEntity;


	public GuiTerraAnvil(InventoryPlayer inventoryplayer, TileEntityTerraAnvil tileentityanvil, World world, int x, int y, int z)
	{
		super(new ContainerTerraAnvil(inventoryplayer,tileentityanvil, world, x, y, z) );
		AnvilEntity = tileentityanvil;

	}

	public void initGui()
	{
		super.initGui();
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;

		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft+5, guiTop + 5, 66, 20, "\2474Light Hit"));
		buttonList.add(new GuiButton(1, guiLeft+5, guiTop + 24, 66, 20, "\2474Heavy Hit"));
		buttonList.add(new GuiButton(2, guiLeft+5, guiTop + 43, 66, 20, "\2474Draw"));
		buttonList.add(new GuiButton(3, guiLeft+5, guiTop + 62, 66, 20, "\2474Quench"));

		buttonList.add(new GuiButton(4, guiLeft+137, guiTop + 5, 66, 20, "\2472Punch"));
		buttonList.add(new GuiButton(5, guiLeft+137, guiTop + 24, 66, 20, "\2472Bend"));
		buttonList.add(new GuiButton(6, guiLeft+137, guiTop + 43, 66, 20, "\2472Upset"));
		buttonList.add(new GuiButton(7, guiLeft+137, guiTop + 62, 66, 20, "\2472Shrink"));

		buttonList.add(new GuiButton(8, guiLeft+86, guiTop + 74, 36, 20, "\2474Weld"));

	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			AnvilEntity.actionLightHammer();
		}
		else if (guibutton.id == 1)
		{
			AnvilEntity.actionHeavyHammer();
		}
		else if (guibutton.id == 2)
		{
			AnvilEntity.actionDraw();
		}
		else if (guibutton.id == 3)
		{
			AnvilEntity.actionQuench();
		}
		else if (guibutton.id == 4)
		{
			AnvilEntity.actionPunch();
		}
		else if (guibutton.id == 5)
		{
			AnvilEntity.actionBend();
		}
		else if (guibutton.id == 6)
		{
			AnvilEntity.actionUpset();
		}
		else if (guibutton.id == 7)
		{
			AnvilEntity.actionShrink();
		}
		else if (guibutton.id == 8)
		{
			AnvilEntity.actionWeld();
		}
		this.inventorySlots.detectAndSendChanges();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.func_98187_b("/bioxx/gui_anvil.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - 208) / 2;
		int h = (height - 198) / 2;
		drawTexturedModalRect(w, h, 0, 0, 208, 198);

		if(AnvilEntity != null)
		{
			int i1 = AnvilEntity.getCraftingValue();
			//(guiX,guiY,sourceMinX,sourceMinY,sourceMaxX,sourceMaxY
			drawTexturedModalRect(w + 73 + i1, h + 103, 219, 9, 11, 6);

			i1 = AnvilEntity.getItemCraftingValue();
			drawTexturedModalRect(w + 76 + i1, h + 108, 208, 10, 5, 6);

			drawRules(w,h);
		}

	}

	public void drawRules(int w, int h)
	{
		fontRenderer.drawString("Rules:", w + 209, h+30-8, 0x404040);
		if(AnvilEntity.workRecipe != null)
		{
			CraftingRuleEnum[] Rules = AnvilEntity.workRecipe.getRules();
			int[] ItemRules = AnvilEntity.getItemRules();
			for(int i = 0; i < 3; i++)
			{
				int yOffset = 8 * i;
				String s = "\u2022";	
				boolean match = false;

				if(i == 0)
				{
					if(Rules[0].matches(ItemRules, 0))
						match = true;
					
					if(match)
						s += "\2472";
					else
						s += "\2474";

					s += Rules[0].Name;

				}
				else if(i == 1)
				{
					if(Rules[1].matches(ItemRules, 1))
						match = true;
					
					if(match)
						s += "\2472";
					else
						s += "\2474";

					s += Rules[1].Name;
				}
				else if(i == 2)
				{
					if(Rules[2].matches(ItemRules, 2))
						match = true;
					
					if(match)
						s += "\2472";
					else
						s += "\2474";

					s += Rules[2].Name;
				}
				
				if(match)
					s += "\u2714";
				else
					s += "\u2718";
				
				fontRenderer.drawString(s, w + 209, h+30+yOffset, 0x404040);
			}
		}
	}

	protected void drawGuiContainerForegroundLayer()
	{
		((GuiButton)buttonList.get(3)).enabled = false;
	}

	private boolean getIsMouseOverSlot(Slot slot, int i, int j)
	{
		int k = guiLeft;
		int l = guiTop;
		i -= k;
		j -= l;
		return i >= slot.xDisplayPosition - 1 && i < slot.xDisplayPosition + 16 + 1 && j >= slot.yDisplayPosition - 1 && j < slot.yDisplayPosition + 16 + 1;
	}

	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}


}
