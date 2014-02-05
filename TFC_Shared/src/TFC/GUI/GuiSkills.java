package TFC.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import TFC.Reference;
import TFC.API.SkillsManager;
import TFC.Containers.ContainerSkills;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
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
		int y = 5;
		for(String o : SkillsManager.instance.getSkillsArray())
		{
			this.drawString(this.fontRenderer, StringUtil.localize(o) + ": " + ss.getSkillMultiplier(o)+"%", 4, y, 0xeeeeee);
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
				0, 86, 25, 20, StringUtil.localize("gui.Inventory.Inventory"), TFC_Textures.GuiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft+176, guiTop + 22, 25, 20, 
				0, 86, 25, 20, StringUtil.localize("gui.Inventory.Skills"), TFC_Textures.GuiSkills));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
	}
}
