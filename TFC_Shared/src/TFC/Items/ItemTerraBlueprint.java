package TFC.Items;

import TFC.Core.StructureBlueprint;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraBlueprint extends Item implements ITextureProvider
{
	public static StructureBlueprint[] blueprints = new StructureBlueprint[64];

	public ItemTerraBlueprint(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		setItemName("Blueprint");
	}

	public void AddBlueprint(StructureBlueprint sb, int id)
	{
		blueprints[id] = sb;
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		int d = itemstack.getItemDamage();

		if(blueprints[d] == null)
		{return "";}

		String s = new StringBuilder().append(super.getItemName()).append(".").append(blueprints[d].Name).toString();

		return s;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		//		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		//		if(objectMouseOver == null)
		//			return itemstack;		
		//		int x = objectMouseOver.blockX;
		//		int y = objectMouseOver.blockY;
		//		int z = objectMouseOver.blockZ;
		//		int side = objectMouseOver.sideHit;
		//		
		//		if((mc.renderViewEntity.rayTrace(20, 1.0F) != null))
		//		{
		//	        x = mc.renderViewEntity.rayTrace(200, 1.0F).blockX;
		//	        y = mc.renderViewEntity.rayTrace(200, 1.0F).blockY;
		//	        z = mc.renderViewEntity.rayTrace(200, 1.0F).blockZ;
		//	       // int blockHitSide = mc.renderViewEntity.rayTrace(200, 1.0F).sideHit;
		//		}
		//
		//        if(blueprints[itemstack.getItemDamage()] != null && blueprints[itemstack.getItemDamage()].GetFinished(world, x, y, z))
		//        {
		//        	//world.setBlockWithNotify(x, y, z, mod_TFC_Mining.terraSmelter.blockID);
		//        	return new ItemStack(itemstack.itemID,0,0);
		//        }
		//        else
		return itemstack;
	}
}
