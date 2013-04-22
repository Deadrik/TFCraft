package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Containers.ContainerTerraMetallurgy;
import TFC.TileEntities.TileEntityMetallurgy;


public class GuiTerraMetallurgy extends GuiContainer
{
	private TileEntityMetallurgy entityMetallurgy;


	public GuiTerraMetallurgy(InventoryPlayer inventoryplayer, TileEntityMetallurgy tileentityMetallurgy, World world, int x, int y, int z)
	{
		super(new ContainerTerraMetallurgy(inventoryplayer,tileentityMetallurgy, world, x, y, z) );
		entityMetallurgy = tileentityMetallurgy;
		this.xSize = 176;
		this.ySize = 184;
	}
	
	@Override
	public void initGui()
    {
		super.initGui();
		//guiLeft = (width - 208) / 2;
        //guiTop = guiTop - 9;
		
        //controlList.clear();
        
        //controlList.add(new GuiButton(0, guiLeft+22, guiTop+54, 42, 20, "Combine"));
        
    }
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(true)
		{
			if (guibutton.id == 0)
			{
				//AnvilEntity.actionLightHammer();
			}
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture("/bioxx/gui_metallurgy.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, 175, 183);
		
	}
	
	protected void drawGuiContainerForegroundLayer()
    {        
//		String Data0 = entityMetallurgy.MetalData[0] > 0 ? entityMetallurgy.MetalData[0].toString() + "x " : "";
//		String Data1 = entityMetallurgy.MetalData[1] > 0 ? entityMetallurgy.MetalData[1].toString() + "x " : "";
//		String Data2 = entityMetallurgy.MetalData[2] > 0 ? entityMetallurgy.MetalData[2].toString() + "x " : "";
//		String Data3 = entityMetallurgy.MetalData[3] > 0 ? entityMetallurgy.MetalData[3].toString() + "x " : "";
//		String Data4 = entityMetallurgy.MetalData[4] > 0 ? entityMetallurgy.MetalData[4].toString() + "x " : "";
//		String Data5 = entityMetallurgy.MetalData[5] > 0 ? entityMetallurgy.MetalData[5].toString() + "x " : "";
//		fontRenderer.drawString("Metal Contents:", 81, 9, 0xFFFFFF);
//		fontRenderer.drawString(Data0 + entityMetallurgy.MetalNames[0], 81, 19, 0x404040);
//		fontRenderer.drawString(Data1 + entityMetallurgy.MetalNames[1], 81, 29, 0x404040);
//		fontRenderer.drawString(Data2 + entityMetallurgy.MetalNames[2], 81, 39, 0x404040);
//		fontRenderer.drawString(Data3 + entityMetallurgy.MetalNames[3], 81, 49, 0x404040);
//		fontRenderer.drawString(Data4 + entityMetallurgy.MetalNames[4], 81, 59, 0x404040);
//		fontRenderer.drawString(Data5 + entityMetallurgy.MetalNames[5], 81, 69, 0x404040);
    }
	
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
