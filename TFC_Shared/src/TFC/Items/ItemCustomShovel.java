package TFC.Items;

import java.util.List;

import TFC.Core.TFCSettings;
import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemCustomShovel extends ItemTool
implements ITextureProvider
{
    /** an array of the blocks this spade is effective against */
    private static Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium, 
        mod_TFC_Core.terraDirt, mod_TFC_Core.terraDirt2, mod_TFC_Core.terraGrass, mod_TFC_Core.terraGrass2, mod_TFC_Core.terraClayGrass, mod_TFC_Core.terraClayGrass2, mod_TFC_Core.terraPeatGrass, mod_TFC_Core.terraPeat, 
        mod_TFC_Core.terraClay, mod_TFC_Core.terraClay2};

    public ItemCustomShovel(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 1, par2EnumToolMaterial, blocksEffectiveAgainst);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
    }

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
	public void addInformation(ItemStack is, List arraylist) 
    {
        if(TFCSettings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
}