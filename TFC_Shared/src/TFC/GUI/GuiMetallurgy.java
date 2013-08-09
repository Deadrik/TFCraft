package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerMetallurgy;
import TFC.TileEntities.TileEntityMetallurgy;


public class GuiMetallurgy extends GuiContainer
{
	private TileEntityMetallurgy entityMetallurgy;


	public GuiMetallurgy(InventoryPlayer inventoryplayer, TileEntityMetallurgy tileentityMetallurgy, World world, int x, int y, int z)
	{
		super(new ContainerMetallurgy(inventoryplayer,tileentityMetallurgy, world, x, y, z) );
		entityMetallurgy = tileentityMetallurgy;
		this.xSize = 176;
		this.ySize = 184;
	}
	
	@Override
	public void initGui()
    {
		super.initGui();
        
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.func_110434_K().func_110577_a(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_metallurgy.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, 175, 183);
		
	}
	
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}
