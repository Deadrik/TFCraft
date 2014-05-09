package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Containers.ContainerPlanSelection;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;

public class GuiPlanSelection extends GuiContainerTFC
{
	TEAnvil AnvilEntity;
	EntityPlayer player;
	World world;
	ArrayList<Object[]> plans;
	int x, y, z;
	public static ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_plan.png");

	public GuiPlanSelection(EntityPlayer p, TEAnvil te, World w, int x, int y, int z)
	{
		super(new ContainerPlanSelection(p, te, w, x, y, z), 175, 130);
		AnvilEntity = te;
		player = p;
		world = w;
		this.drawInventory = false;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		plans = getRecipes();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int xOffset = 5;
		int yOffset = 14;
		int index = plans.size()-1;
		for(Object[] o : plans)
		{
			String p = (String) o[0];
			AnvilRecipe a = (AnvilRecipe) o[1];
			buttonList.add(0,new GuiPlanButton(plans.size()-1-index, guiLeft + xOffset, guiTop + yOffset, 16, 16, a.getCraftingResult().getIconIndex(), this, StatCollector.translateToLocal("gui.plans."+p)));
			index--;
			if(xOffset+36 < xSize)
				xOffset+=18;
			else
			{
				xOffset = 5;
				yOffset+=18;
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		Object[] p = (Object[]) plans.toArray()[guibutton.id];
		AnvilEntity.setPlan((String)p[0]);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
		if(AnvilEntity.getStackInSlot(TEAnvil.INPUT1_SLOT) != null)
			drawCenteredString(this.fontRendererObj, "Plans: "+ AnvilEntity.getStackInSlot(TEAnvil.INPUT1_SLOT).getDisplayName(), guiLeft+xSize/2, guiTop+5, 0x000000);
	}

	private ArrayList<Object[]> getRecipes()
	{
		AnvilManager manager = AnvilManager.getInstance();
		Object[] plans = manager.getPlans().keySet().toArray();
		ArrayList planList = new ArrayList();
		for(Object p : plans)
		{
			AnvilRecipe ar = manager.findMatchingRecipe(new AnvilRecipe(AnvilEntity.anvilItemStacks[TEAnvil.INPUT1_SLOT], AnvilEntity.anvilItemStacks[TEAnvil.INPUT2_SLOT], 
					(String)p,AnvilReq.getReqFromInt(AnvilEntity.AnvilTier), null));

			ar = handleMatchingRecipe(ar);
			if(ar != null) 
				planList.add(new Object[]{(String)p, ar});
		}
		return planList;

	}

	AnvilRecipe handleMatchingRecipe(AnvilRecipe ar)
	{
		if (ar != null)
		if (AnvilEntity.anvilItemStacks[AnvilEntity.INPUT1_SLOT] != null && AnvilEntity.anvilItemStacks[AnvilEntity.INPUT1_SLOT].getItem() == TFCItems.Bloom && ar.getCraftingResult().getItem() == TFCItems.Bloom)
		{
			if (AnvilEntity.anvilItemStacks[AnvilEntity.INPUT1_SLOT].getItemDamage() <= 100)
				return null;
		}
		return ar;
	}

	public void drawTooltip(int mx, int my, String text) {
		List list = new ArrayList();
		list.add(text);
		this.drawHoveringTextZLevel(list, mx, my+15, this.fontRendererObj, 400);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
	}
}
