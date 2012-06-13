package net.minecraft.src.TFC_Core.Render;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TileEntityToolRack;

public class TileEntityToolRackRenderer extends TileEntitySpecialRenderer
{

    private Random random;
    private RenderBlocks renderBlocks;

    private static float[][] shifts = { { 0.3F, 0.45F, 0.3F }, { 0.7F, 0.45F, 0.3F }, { 0.3F, 0.45F, 0.7F }, { 0.7F, 0.45F, 0.7F },
            { 0.3F, 0.1F, 0.3F }, { 0.7F, 0.1F, 0.3F }, { 0.3F, 0.1F, 0.7F }, { 0.7F, 0.1F, 0.7F }, { 0.5F, 0.32F, 0.5F }, };

    public TileEntityToolRackRenderer() 
    {
        random = new Random();
        renderBlocks = new RenderBlocks();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTick)
    {
        render((TileEntityToolRack) tileentity, x, y, z, partialTick);
    }
    
    public void render(TileEntityToolRack tile, double x, double y, double z, float partialTick) 
    {
        if (tile==null) 
        {
            return;
        }
        
        IBlockAccess access = renderBlocks.blockAccess;
        
        
    }

}
