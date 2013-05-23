package TFC.Items;

import java.util.List;

import TFC.TFCItems;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemOreSmall extends ItemOre
{
    public ItemOreSmall(int i)
    {
        super(i);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
        for(int i = 0; i < 14; i++) {
            list.add(new ItemStack(this,1,i));}
            
            list.add(new ItemStack(this,1,35));
            list.add(new ItemStack(this,1,36));
            list.add(new ItemStack(this,1,37));
    }

	@Override
	public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < 14; i++)
			icons[i] = registerer.registerIcon(textureFolder+MetaNames[i]+" Small Ore");
    }
    
    @Override
	public EnumSize getSize() {
		return EnumSize.SMALL;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}
}
