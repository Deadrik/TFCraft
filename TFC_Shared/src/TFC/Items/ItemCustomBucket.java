package TFC.Items;

import TFC.Core.Helper;
import TFC.Core.TFCItems;
import TFC.Entities.EntityCowTFC;
import net.minecraft.src.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemCustomBucket extends Item
{
    /** field for checking if the bucket has been filled. */
    private int isFull;

    public ItemCustomBucket(int par1, int par2)
    {
        super(par1);
        this.maxStackSize = 1;
        this.isFull = par2;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer par3EntityPlayer)
    {
        float var4 = 1.0F;
        double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
        double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
        double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
        boolean var11 = this.isFull == 0;
        MovingObjectPosition var12 = Helper.getMovingObjectPositionFromPlayer(world, par3EntityPlayer, var11);

        if (var12 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var12.typeOfHit == EnumMovingObjectType.TILE)
            {
                int i = var12.blockX;
                int j = var12.blockY;
                int k = var12.blockZ;

                if (!world.canMineBlock(par3EntityPlayer, i, j, k))
                {
                    return par1ItemStack;
                }

                if (this.isFull == 0)
                {
                    if (!par3EntityPlayer.canPlayerEdit(i, j, k))
                    {
                        return par1ItemStack;
                    }
                    
                    FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, world, var12);
                    if (event.isCanceled())
                    {
                        return par1ItemStack;
                    }
                    
                    if (event.isHandeled())
                    {
                        return event.result;
                    }

                    if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) <=2)
                    {
                        world.setBlockWithNotify(i, j, k, 0);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        return new ItemStack(TFCItems.WoodenBucketWater);
                    }
                }
                else
                {
                    if (this.isFull < 0)
                    {
                        return new ItemStack(TFCItems.WoodenBucketEmpty);
                    }

                    if (var12.sideHit == 0)
                    {
                        --j;
                    }
                    else if (var12.sideHit == 1)
                    {
                        ++j;
                    }
                    else if (var12.sideHit == 2)
                    {
                        --k;
                    }
                    else if (var12.sideHit == 3)
                    {
                        ++k;
                    }
                    else if (var12.sideHit == 4)
                    {
                        --i;
                    }
                    else if (var12.sideHit == 5)
                    {
                        ++i;
                    }

                    if (!par3EntityPlayer.canPlayerEdit(i, j, k))
                    {
                        return par1ItemStack;
                    }

                    if (world.isAirBlock(i, j, k) || !world.getBlockMaterial(i, j, k).isSolid())
                    {
                        if (world.provider.isHellWorld && this.isFull == TFCBlocks.finiteWater.blockID)
                        {
                            world.playSoundEffect(var5 + 0.5D, var7 + 0.5D, var9 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                            for (int var16 = 0; var16 < 8; ++var16)
                            {
                                world.spawnParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                            }
                        }
                        else
                        {
                            if(world.getBlockId(i, j, k) == TFCBlocks.finiteWater.blockID)
                            {
                                int bucketMeta = 0;
                                int blockMeta = world.getBlockMetadata(i, j, k);
                                if(blockMeta > 0)
                                {
                                    bucketMeta = bucketMeta + blockMeta;
                                    world.setBlockAndMetadataWithNotify(i, j, k, TFCBlocks.finiteWater.blockID, 0);
                                    world.setBlockAndMetadataWithNotify(i, j+1, k, TFCBlocks.finiteWater.blockID, bucketMeta);
                                }
                                else
                                {
                                    world.setBlockAndMetadataWithNotify(i, j, k, TFCBlocks.finiteWater.blockID, 0);
                                }
                            }
                            else
                            {
                                world.setBlockAndMetadataWithNotify(i, j, k, this.isFull, 0);
                            }
                        }

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            return par1ItemStack;
                        }

                        return new ItemStack(TFCItems.WoodenBucketEmpty);
                    }
                }
            }
            else if (this.isFull == 0 && var12.entityHit instanceof EntityCowTFC && ((EntityCowTFC)var12.entityHit).sex == 1)
            {
                return new ItemStack(TFCItems.WoodenBucketMilk);
            }

            return par1ItemStack;
        }
    }
    
    @Override
    public String getTextureFile()
    {
        return "/bioxx/terratools.png";
    }
}
