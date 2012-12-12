package TFC.Blocks;

import TFC.Core.TFC_Settings;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockNestBox extends BlockTerraContainer
{
	public BlockNestBox(int par1) 
	{
		super(par1, Material.wood);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)  
    {
		super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);
		
		return false;
    }

}
