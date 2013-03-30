package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {

    }
}
