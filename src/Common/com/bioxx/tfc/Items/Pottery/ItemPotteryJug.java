package com.bioxx.tfc.Items.Pottery;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemPotteryJug extends ItemPotteryBase
{
    IIcon WaterIcon;
    // The amount of time to wait between playing notes.
    private static int PlayNoteCoolDown = 6;


    // A number of keys too high will cause the pitchMult to go beyond the 0.5 - 2.0 limit of playsound.
    // A possible (and extraneous?) fix for this is to use different sound samples every couple keys
    // and pitch them accordingly.
    // The number of keys in our instruments range. This should be even.
    private static int NumKeys = 18;
    // The base key for our instrument, to sound correct this should match the pitch that the sample is.
    // The jug sample is approx. key E4.
    private static int BaseKey = 44; // (1-88 represents keys A0-C8)

    public ItemPotteryJug()
    {
        super();
        this.MetaNames = new String[]{"Clay Jug", "Ceramic Jug", "Water Jug"};
        this.stackable = false;

        this.setWeight(EnumWeight.LIGHT);
        this.setSize(EnumSize.SMALL);
    }

    @Override
    public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            if(is.getItemDamage() == 2)
            {
                TFC_Core.getPlayerFoodStats(player).restoreWater(player, 24000);
            }

            if (is.getItemDamage() > 1 && !player.capabilities.isCreativeMode)
            {
                if(world.rand.nextInt(50) == 0)
                {
                    is.stackSize--;
                    player.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
                }
                else
                {
                    is.setItemDamage(1);
                }
            }
        }
        return is;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack is)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack is)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
    {
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entity, true);
        FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entity);
        Boolean canDrink = fs.getMaxWater(entity) - 500 > fs.waterLevel;

        boolean playNote = false;
        float lookAngle = 0;
        if(mop == null)
        {
            if(is.getItemDamage() > 1 && canDrink)
            {
                entity.setItemInUse(is, this.getMaxItemUseDuration(is));
            }
            else if(!world.isRemote && is.getItemDamage() == 1){
                Vec3 lookVec = entity.getLookVec();
                lookAngle = (float)(lookVec.yCoord/2d);
                if(!is.hasTagCompound()){
                    playNote = true;
                    is.stackTagCompound = new NBTTagCompound();
                    is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
                }
                else if(is.stackTagCompound.hasKey("blowTime") &&
                        (is.stackTagCompound.getLong("blowTime") + PlayNoteCoolDown < TFC_Time.getTotalTicks())){
                    playNote = true;
                    is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
                }
                else if(!is.stackTagCompound.hasKey("blowTime")){
                    playNote = true;
                    is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
                }
            }
        }
        else
        {
            if(mop.typeOfHit == MovingObjectType.BLOCK)
            {
                int x = mop.blockX;
                int y = mop.blockY;
                int z = mop.blockZ;

                if(!world.isRemote && TFC_Core.isFreshWater(world.getBlock(x, y, z)) && !entity.isSneaking())
                {
                    if(is.getItemDamage() == 1)
                    {
                        is.setItemDamage(2);
                        playNote = false;
                    }
                    else
                    {
                        if(canDrink)
                        {
                            entity.setItemInUse(is, this.getMaxItemUseDuration(is));
                        }
                    }
                }
                else
                {

                    // Must be done one client and server or drinking animation will not be shown on client.
                    if(is.getItemDamage() == 2 && canDrink)
                    {
                        entity.setItemInUse(is, this.getMaxItemUseDuration(is));
                    }
                    // Only do NBT on server.
                    else if(!world.isRemote && is.getItemDamage() == 1){
                        Vec3 lookVec = entity.getLookVec();
                        lookAngle = (float)(lookVec.yCoord/2d);

                        // If we don't have a tag compound in NBT, add it.
                        if(!is.hasTagCompound()){
                            is.stackTagCompound = new NBTTagCompound();
                            is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
                        }

                        if(is.stackTagCompound.hasKey("blowTime") &&
                                (is.stackTagCompound.getLong("blowTime") + PlayNoteCoolDown < TFC_Time.getTotalTicks())){

                            is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());

                            playNote = true;
                        }
                        else if(!is.stackTagCompound.hasKey("blowTime")){
                            playNote = true;
                            is.stackTagCompound.setLong("blowTime", TFC_Time.getTotalTicks());
                        }
                    }
                }

                if(!world.canMineBlock(entity, x, y, z))
                {
                    return is;
                }

                if(!entity.canPlayerEdit(x, y, z, mop.sideHit, is))
                {
                    return is;
                }
            }
        }

        // We are playing a note if blowTime + 10 is still in the future.
        // When the cooldown is done we can play another note.
        /*
        if(is.stackTagCompound.getLong("blowTime") + PlayNoteCoolDown > TFC_Time.getTotalTicks())
        {

        }
        */

        if(playNote){

            // The target key on the 88 key piano scale. 40th key is C4, 49th key is A4.
            // The target key is decided by taking our look angle and multiplying it by the number of keys,
            // so we will get a key within NumKeys keys above or below BaseKey
            // (but the sound will actually depend on the audio samples pitch).
            int targetKey = (int) (lookAngle * NumKeys) + BaseKey;

            // The frequency of our target key.
            double keyFreqHz = pianoKeyToHertz(targetKey);

            // The number we must multiply by to get the target pitch. 440hz is the frequency of the A4 key.
            double pitchMult = keyFreqHz / pianoKeyToHertz(BaseKey);

            //System.out.println("look angle: " + lookAngle + " pitch multiplier: " + pitchMult + "target key: " + targetKey + " target key in hz: " + keyFreqHz);


            world.playSoundAtEntity(entity, TFC_Sounds.JUGBLOW, 1.3f, (float)pitchMult);
        }
        return is;
    }

    // Converts a piano key (1-88) into its frequency in hz.
    private double pianoKeyToHertz(int key)
    {
        return Math.pow(2, (key - 49) / 12d) * 440d;
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        if(damage == 0) {
            return this.ClayIcon;
        } else if(damage == 1) {
            return this.CeramicIcon;
        } else if(damage == 2) {
            return this.WaterIcon;
        }

        return this.WaterIcon;
    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        super.registerIcons(registerer);
        this.WaterIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + "Water Jug");
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
    {
        super.addInformation(is, player, arraylist, flag);
        if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
        {
            arraylist.add(is.stackTagCompound.getString("LiquidType"));
        }
    }
}