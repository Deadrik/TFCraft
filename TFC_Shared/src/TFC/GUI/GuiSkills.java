package TFC.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import TFC.Reference;
import TFC.Containers.ContainerSkills;
import TFC.Core.TFC_Core;
import TFC.Core.Player.SkillStats;
import TFC.Core.Util.StringUtil;

public class GuiSkills extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_skills.png");
	protected EntityPlayer player;
	public GuiSkills(EntityPlayer player)
	{
		super(new ContainerSkills(), 176, 166);
		this.setDrawInventory(false);
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) 
	{
		SkillStats ss = TFC_Core.getSkillStats(player);
		Object[] skills = ss.getSkillsKeysArray();
		int y = 5;
		for(Object o : skills)
		{
			this.drawString(this.fontRenderer, (String)o + ": " + ss.getSkillMultiplier((String)o)+"%", 4, y, 0xeeeeee);
			y+=10;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft+176, guiTop + 3, 25, 20, 
				0, 86, 25, 20, StringUtil.localize("gui.Inventory.Inventory")));
		buttonList.add(new GuiInventoryButton(1, guiLeft+176, guiTop + 23, 25, 20, 
				25, 86, 25, 20, StringUtil.localize("gui.Inventory.Skills")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
	}
}
