package TFC.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.Core.TFC_Achievements;
import TFC.TileEntities.TileEntityAnvil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemHammer extends ItemTerraTool implements ICausesDamage
{
	public ItemHammer(int i, EnumToolMaterial e, float damage)
	{
		super(i, 0, e, new Block[] {});
		this.damageVsEntity = damage;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		int id2 = player.worldObj.getBlockId(x, y, z);
		int meta2 = player.worldObj.getBlockMetadata(x, y, z);

		if(id2 == TFCBlocks.StoneIgEx.blockID || id2 == TFCBlocks.StoneIgIn.blockID)
		{
			if(side == 1)
			{
				world.setBlock(x, y, z, TFCBlocks.Anvil.blockID);
				player.triggerAchievement(TFC_Achievements.achAnvil);
				TileEntityAnvil te = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);
				if(te == null)
				{
					world.setBlockTileEntity(x, y, z, new TileEntityAnvil());
				}
				if(te != null)
				{
					te.stonePair[0] = id2;
					te.stonePair[1] = meta2;
					te.validate();
				}
				//world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
		return false;
	}

	public boolean onBlockDestroyed(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, EntityLiving entity)
	{
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumSize.SMALL;
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.CRUSHING;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
		return multimap;
	}
}