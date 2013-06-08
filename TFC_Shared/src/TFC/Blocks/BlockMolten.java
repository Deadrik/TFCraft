package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.Reference;
public class BlockMolten extends BlockTerra
{
	public BlockMolten(int i)
	{
		super(i, Material.iron);
		this.setLightValue(1.0F);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Molten Rock");
    }

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}	

}
