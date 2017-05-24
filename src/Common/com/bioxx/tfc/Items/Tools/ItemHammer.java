package com.bioxx.tfc.Items.Tools;


import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Blocks.Terrain.BlockSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockStone;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Set;

public class ItemHammer extends ItemTerraTool implements ICausesDamage
{
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[] { TFCBlocks.cokeblock });
	private float damageVsEntity;

	public ItemHammer(ToolMaterial e, float damage)
	{
		super(0, e, BLOCKS);
		this.damageVsEntity = damage;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block id2 = player.worldObj.getBlock(x, y, z);
		int meta2 = player.worldObj.getBlockMetadata(x, y, z);

		if(id2 == TFCBlocks.stoneIgEx || id2 == TFCBlocks.stoneIgIn)
		{
			if(side == 1)
			{
				world.setBlock(x, y, z, TFCBlocks.anvil);
				player.triggerAchievement(TFC_Achievements.achAnvil);
				TEAnvil te = (TEAnvil) world.getTileEntity(x, y, z);
				if(te == null)
					world.setTileEntity(x, y, z, new TEAnvil());
				if(te != null)
				{
					te.stonePair[0] = Block.getIdFromBlock(id2);
					te.stonePair[1] = meta2;
					te.validate();
				}
				//world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
    	boolean placed = false;
        int toolSlot = player.inventory.currentItem;
        int nextSlot = toolSlot == 0 ? 8 : toolSlot + 1;
        ItemStack nextSlotStack = null;

        if (toolSlot < 8)
        {
            nextSlotStack = player.inventory.getStackInSlot(nextSlot);
            if (nextSlotStack != null)
            {
                Item item = nextSlotStack.getItem();
  
                if (item instanceof ItemBlock)
                {
                    int posX = x;
                    int posY = y;
                    int posZ = z;

                    switch (side)
                    {
                        case 0:
                            --posY;
                            break;
                        case 1:
                            ++posY;
                            break;
                        case 2:
                            --posZ;
                            break;
                        case 3:
                            ++posZ;
                            break;
                        case 4:
                            --posX;
                            break;
                        case 5:
                            ++posX;
                            break;
                    }

                    AxisAlignedBB blockBounds = AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1);
                    AxisAlignedBB playerBounds = player.boundingBox;

                    if(item instanceof ItemBlock)
                    {
                        Block blockToPlace = ((ItemBlock) item).field_150939_a;
                        if(blockToPlace.getMaterial().blocksMovement())
                        {
                            if (playerBounds.intersectsWith(blockBounds))
                                return false;
                        }
                    }

                    int dmg = nextSlotStack.getItemDamage();
                    int count = nextSlotStack.stackSize;

                	placed = item.onItemUse(nextSlotStack, player, world, x, y, z, side, hitX, hitY, hitZ);

                    if(player.capabilities.isCreativeMode) {

                    	nextSlotStack.setItemDamage(dmg);
                    	nextSlotStack.stackSize = count;
                    }
                    if (nextSlotStack.stackSize < 1)
                    {
                    	nextSlotStack = null;
                        player.inventory.setInventorySlotContents(nextSlot, null);
                    }
                }
            }
        }
        return placed;
	    

	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumDamageType getDamageType()
	{
		return EnumDamageType.CRUSHING;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", getWeaponDamage(is), 0));
		return multimap;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(damageVsEntity + (damageVsEntity * AnvilManager.getDamageBuff(is)));
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return (int) Math.floor(getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(is)));
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}

	@Override
  	public boolean canHarvestBlock(Block block, ItemStack itemStack)
  	{
	  return checkBlock(block);
  	}
    
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack,  int x,  int y,  int z,  EntityPlayer player) {
        if (this.checkBlock(player.worldObj.getBlock(x, y, z)))
        {
	        if (this.checkNeighbours(player.worldObj, x, y, z) || player.capabilities.isCreativeMode) {
	            return false;
	        }
	        else {
	        	itemstack.damageItem( (int) itemstack.getMaxDamage()/ 30 + 100, player);
	        	TFC_Core.addPlayerExhaustion(player, 0.1F);
	        }

        }
        return false;

    }
    @Override
    public float getDigSpeed(ItemStack stack,  Block block,  int meta) {
        float digSpeed = 1.0F;
        if (checkBlock(block)) {
            digSpeed += stack.getMaxDamage() / TFCOptions.hammerBreakSpeed;
        }
        return digSpeed + (digSpeed * AnvilManager.getDurabilityBuff(stack));
    }
    
	private boolean checkNeighbours(World world,  int x,  int y,  int z) {
        		
		return  !checkNeighbourBlock(world.getBlock(x + 1, y, z)) && 
				!checkNeighbourBlock(world.getBlock(x - 1, y, z)) && 
				!checkNeighbourBlock(world.getBlock(x, y + 1, z)) && 
				!checkNeighbourBlock(world.getBlock(x, y - 1, z)) && 
				!checkNeighbourBlock(world.getBlock(x, y, z + 1)) && 
				!checkNeighbourBlock(world.getBlock(x, y, z - 1));
	}
    
    private boolean checkBlock(Block block) {
         String checkBlockName = block.getUnlocalizedName().toLowerCase();
        return block instanceof BlockSmooth || checkBlockName.contains("brick") || checkBlockName.contains("smooth") || checkBlockName.contains("glass");
    }
    private boolean checkNeighbourBlock(Block block) {
    	return block instanceof BlockStone || block instanceof BlockOre;
	}
}
