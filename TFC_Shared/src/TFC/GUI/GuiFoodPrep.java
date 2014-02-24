package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Containers.ContainerFoodPrep;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TileEntityFoodPrep;

public class GuiFoodPrep extends GuiContainerTFC
{
	private static final ResourceLocation texture = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_foodprep.png");
	TileEntityFoodPrep table;

	public GuiFoodPrep(InventoryPlayer inventoryplayer, TileEntityFoodPrep wb, World world, int i, int j, int k)
	{
		super(new ContainerFoodPrep(inventoryplayer, wb,world, i, j, k), 97, 85);
		table = wb;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		fontRenderer.drawString("10oz", 7, 12, 0x404040);
		fontRenderer.drawString("4oz", 13, 30, 0x404040);
		fontRenderer.drawString("4oz", 13, 48, 0x404040);
		fontRenderer.drawString("2oz", 13, 66, 0x404040);
		if(table.getMealWeight() < 14)
			((GuiButton)buttonList.get(0)).enabled = false;
		else ((GuiButton)buttonList.get(0)).enabled = true;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();

		buttonList.add(new GuiButton(0, guiLeft+52, guiTop + 59, 40, 20, StringUtil.localize("gui.FoodPrep.CreateMeal")));

	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			table.actionCreate();
	}
}
