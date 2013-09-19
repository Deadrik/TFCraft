package TFC.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Containers.ContainerQuiver;

public class GuiQuiver extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_quiver.png");

	public GuiQuiver(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerQuiver(inventoryplayer, world, i, j, k), 175, 49);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}
}
