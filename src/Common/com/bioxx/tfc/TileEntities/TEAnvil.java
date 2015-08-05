package com.bioxx.tfc.TileEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import com.bioxx.tfc.Items.Tools.ItemSteelBucket;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Enums.RuleEnum;
import com.bioxx.tfc.api.Events.AnvilCraftEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEAnvil extends NetworkTileEntity implements IInventory
{
	public ItemStack anvilItemStacks[];

	public int itemCraftingValue;
	public int[] itemCraftingRules;
	public int craftingValue;
	public int craftingRange;
	public int craftingReq;
	public String craftingPlan;

	public int[] stonePair;

	//private boolean isDone = false;
	private byte workedRecently = 0;

	//this is the fix the server receiving 3 packets whenever the player works an item.
	private final byte LAG_FIX_DELAY = 5;
	public AnvilRecipe workRecipe;
	//private AnvilRecipe workWeldRecipe;
	public int AnvilTier = AnvilReq.STONE.Tier; // Initialize to avoid NPE

	public EntityPlayer lastWorker;

	public static final int INPUT1_SLOT = 1;
	public static final int WELD1_SLOT = 2;
	public static final int WELD2_SLOT = 3;
	public static final int WELDOUT_SLOT = 4;
	public static final int INPUT2_SLOT = 5;
	public static final int FLUX_SLOT = 6;
	public static final int HAMMER_SLOT = 0;

	public TEAnvil()
	{
		anvilItemStacks = new ItemStack[19];
		itemCraftingValue = 0;
		itemCraftingRules = new int[]{-1,-1,-1};
		craftingValue = 0;
		AnvilTier = AnvilReq.STONE.Tier;
		stonePair = new int[]{0,0};
		craftingPlan = "";
	}

	@Override
	public void updateEntity()
	{
		if(anvilItemStacks[1] == null)
		{
			workRecipe = null;
			craftingValue = 0;
		}
		//Disabled auto plan selection until we can solve the issue of correctly determining the lastworker outside of the plan selection screen
		/*else if(anvilItemStacks[INPUT1_SLOT] != null || anvilItemStacks[INPUT2_SLOT] != null && workRecipe == null)
			updateRecipe();*/
		//make sure that the world is not remote
		if(!worldObj.isRemote)
		{
			//if the item exceeds bounds, destroy it
			if(getItemCraftingValue() > 150)
				this.setInventorySlotContents(INPUT1_SLOT, null);

			if(workedRecently > 0)
				workedRecently--;
			//Deal with temperatures
			TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
			/**
			 * Check if the recipe is considered complete
			 * */
			if(workRecipe!= null && getItemCraftingValue() != itemCraftingValue)
			{
				itemCraftingValue = getItemCraftingValue();
				AnvilManager manager = AnvilManager.getInstance();
				Object[] r = getRecipe(manager);
				AnvilRecipe recipe = r != null && r[0] !=  null ? (AnvilRecipe) r[0] : null;
				ItemStack result = r != null && r[1] !=  null ? (ItemStack) r[1] : null;

				//This is where the crafting is completed and the result is added to the anvil
				if(result != null)
				{

					AnvilCraftEvent eventCraft = new AnvilCraftEvent(lastWorker, this, anvilItemStacks[INPUT1_SLOT], anvilItemStacks[INPUT2_SLOT], result);
					MinecraftForge.EVENT_BUS.post(eventCraft);
					if(!eventCraft.isCanceled())
					{
						//Set the item temp if possible
						TFC_ItemHeat.SetTemp(eventCraft.result, TFC_ItemHeat.GetTemp(anvilItemStacks[INPUT1_SLOT]));

						ItemStack output = eventCraft.result;
						//If the lastWorker is not null, then we attempt to apply some crafting buffs to items based on the players skills
						if (output != null && lastWorker != null)
						{
							if (output.getItem() instanceof ItemMiscToolHead)
							{
								AnvilManager.setDurabilityBuff(output, recipe.getSkillMult(lastWorker));
								AnvilManager.setDamageBuff(output, recipe.getSkillMult(lastWorker));

							}
							else if (output.getItem() instanceof ItemTFCArmor)
							{
								AnvilManager.setDurabilityBuff(output, recipe.getSkillMult(lastWorker));
							}

							if (output.getItem() instanceof ItemIngot)
							{
								Item ingot = output.getItem();
								if (ingot == TFCItems.BlackSteelIngot)
									lastWorker.triggerAchievement(TFC_Achievements.achBlackSteel);
								else if (ingot == TFCItems.BlueSteelIngot)
									lastWorker.triggerAchievement(TFC_Achievements.achBlueSteel);
								else if (ingot == TFCItems.RedSteelIngot)
									lastWorker.triggerAchievement(TFC_Achievements.achRedSteel);
							}
							else if (output.getItem() instanceof ItemSteelBucket)
							{
								Item bucket = output.getItem();
								if (bucket == TFCItems.BlueSteelBucketEmpty)
									lastWorker.triggerAchievement(TFC_Achievements.achBlueBucket);
								else if (bucket == TFCItems.RedSteelBucketEmpty)
									lastWorker.triggerAchievement(TFC_Achievements.achRedBucket);
							}

							increaseSkills(recipe);
							removeRules(INPUT1_SLOT);
						}
						// We need to call this after the NBT is set, since this call sets lastWorker to null if the output has no further recipes.
						this.setInventorySlotContents(INPUT1_SLOT, output);

						if(anvilItemStacks[INPUT2_SLOT] != null)
							anvilItemStacks[INPUT2_SLOT].stackSize--;

					}
					workRecipe = null;
					craftingPlan = "";
					craftingValue = 0;
					lastWorker = null;
				}
			}
		}
		if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].stackSize < 1)
			anvilItemStacks[INPUT1_SLOT].stackSize = 1;
	}

	public void increaseSkills(AnvilRecipe recipe)
	{
		if(lastWorker!= null)
		{
			for(String s : recipe.skillsList)
			{
				TFC_Core.getSkillStats(lastWorker).increaseSkill(s, recipe.craftingXP);
			}
		}
	}

	/**
	 * @return returns an array [AnvilRecipe,ItemStack]
	 */
	public Object[] getRecipe(AnvilManager manager)
	{
		Object[] out = new Object[2];

		if(itemCraftingValue == workRecipe.getCraftingValue())
		{
			out = manager.findCompleteRecipe(new AnvilRecipe(anvilItemStacks[INPUT1_SLOT],anvilItemStacks[INPUT2_SLOT], craftingPlan,
					workRecipe.getCraftingValue(), 
					anvilItemStacks[FLUX_SLOT] != null ? true : false, AnvilTier, null), getItemRules());
		}
		return out;
	}

	/**
	 * Called whenever an inventory slot changes
	 */
	public void onSlotChanged(int slot)
	{
		if(slot == INPUT1_SLOT || slot == INPUT2_SLOT || slot == FLUX_SLOT)
			updateRecipe();
	}

	public void updateRecipe()
	{
		AnvilManager manager = AnvilManager.getInstance();
		Object[] plans = manager.getPlans().keySet().toArray();
		Map<String, AnvilRecipe> planList = new HashMap<String, AnvilRecipe>();
		//Here we go through and assemble a list of all possible recipes using the input parameters
		for(Object p : plans)
		{
			AnvilRecipe ar = manager.findMatchingRecipe(new AnvilRecipe(anvilItemStacks[INPUT1_SLOT], anvilItemStacks[INPUT2_SLOT], 
					(String)p, anvilItemStacks[FLUX_SLOT] != null, AnvilTier));

			if(ar != null) 
				planList.put((String)p, ar);
		}

		//We need to pre-emptively remove split blooms from the plan list if the input bloom is too small
		if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].getItem() == TFCItems.Bloom)
		{
			if(anvilItemStacks[INPUT1_SLOT].getItemDamage() <= 100 && planList.containsKey("splitbloom"))
				planList.remove("splitbloom");
		}

		//If there are no recipes found then we need to null everything to prevent any crafting from occurring
		if (planList.size() == 0)
		{
			workRecipe = null;
			craftingPlan = "";
			this.lastWorker = null;
			return;
		}

		//If there is only one possible recipe then we auto-select it on the client's side
		/*if (worldObj.isRemote && planList.size() == 1)
		{
			workRecipe = (AnvilRecipe)(planList.values().toArray())[0];
			craftingPlan = (String)planList.keySet().toArray()[0];
			this.setPlan(craftingPlan);
			return;
		}*/

		//This is the core of the process. If the plan list for the input items contains our crafting plan, then we've found our recipe.
		if (planList.containsKey(craftingPlan))
			workRecipe = planList.get(craftingPlan);
		else
		{
			workRecipe = null;
			return;
		}

		//We don't want to allow refined blooms of < 100 units to be refined into wrought iron ingots so we null the recipe unless the amount == 100
		if (anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].getItem() == TFCItems.Bloom && workRecipe.getCraftingResult().getItem() == TFCItems.Bloom)
		{
			if (anvilItemStacks[INPUT1_SLOT].getItemDamage() < 100)
			{
				craftingPlan = "";
				workRecipe = null;
			}
			else if (anvilItemStacks[INPUT1_SLOT].getItemDamage() == 100)
			{
				craftingPlan = "refinebloom";
				workRecipe = planList.get(craftingPlan);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord +1, yCoord + 1, zCoord + 1);
		return bb;
	}

	public int getCraftingValue()
	{
		/*if(!worldObj.isRemote)
			return workRecipe != null ? workRecipe.getCraftingValue() : 0;
			else
				return craftingValue;*/

		return workRecipe != null ? workRecipe.getCraftingValue() : 0;
	}

	public void updateRules(int rule, int slot)
	{
		if(anvilItemStacks[slot].hasTagCompound())
		{
			NBTTagCompound Tag = anvilItemStacks[slot].getTagCompound();
			int rule1 = -1;
			int rule2 = -1;
			if(Tag.hasKey("itemCraftingRule1"))
				rule1 = Tag.getByte("itemCraftingRule1");
			if(Tag.hasKey("itemCraftingRule2"))
				rule2 = Tag.getByte("itemCraftingRule2");
			if(Tag.hasKey("itemCraftingRule3"))
				Tag.getByte("itemCraftingRule3");

			itemCraftingRules[2] = rule2;
			itemCraftingRules[1] = rule1;
			itemCraftingRules[0] = rule;

			Tag.setByte("itemCraftingRule1", (byte) itemCraftingRules[0]);
			Tag.setByte("itemCraftingRule2", (byte) itemCraftingRules[1]);
			Tag.setByte("itemCraftingRule3", (byte) itemCraftingRules[2]);

			anvilItemStacks[slot].setTagCompound(Tag);
		}
	}

	public void removeRules(int slot)
	{
		if(anvilItemStacks[slot].hasTagCompound())
		{
			NBTTagCompound Tag = anvilItemStacks[slot].getTagCompound();
			if(Tag.hasKey("itemCraftingRule1"))
				Tag.removeTag("itemCraftingRule1");
			if(Tag.hasKey("itemCraftingRule2"))
				Tag.removeTag("itemCraftingRule2");
			if(Tag.hasKey("itemCraftingRule3"))
				Tag.removeTag("itemCraftingRule3");
			if(Tag.hasKey("itemCraftingValue"))
				Tag.removeTag("itemCraftingValue");

			anvilItemStacks[slot].setTagCompound(Tag);
		}
	}

	public int[] getItemRules()
	{
		int[] rules = new int[3];
		if(anvilItemStacks[1] != null && anvilItemStacks[1].hasTagCompound())
		{
			if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule1"))
				rules[0] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule1");
			else
				rules[0] = RuleEnum.ANY.Action;

			if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule2"))
				rules[1] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule2");
			else
				rules[1] = RuleEnum.ANY.Action;

			if(anvilItemStacks[1].stackTagCompound.hasKey("itemCraftingRule3"))
				rules[2] = anvilItemStacks[1].stackTagCompound.getByte("itemCraftingRule3");
			else
				rules[2] = RuleEnum.ANY.Action;
		}
		else
		{
			rules[0] = RuleEnum.ANY.Action;
			rules[1] = RuleEnum.ANY.Action;
			rules[2] = RuleEnum.ANY.Action;
		}
		return rules;
	}

	private void damageHammer()
	{
		anvilItemStacks[HAMMER_SLOT].setItemDamage(anvilItemStacks[HAMMER_SLOT].getItemDamage()+1);
		if(anvilItemStacks[HAMMER_SLOT].getItemDamage() == anvilItemStacks[HAMMER_SLOT].getMaxDamage())
			anvilItemStacks[HAMMER_SLOT] = null;
	}

	private boolean canBeWorked()
	{
		if(isTemperatureWorkable(INPUT1_SLOT) && anvilItemStacks[HAMMER_SLOT] != null && 
				(anvilItemStacks[INPUT1_SLOT].getItemDamage() == 0 || anvilItemStacks[INPUT1_SLOT].getItem().getHasSubtypes() == true) && 
				getAnvilType() >= craftingReq && workedRecently == 0)
		{
			return true;
		}
		return false;
	}

	public void actionHeavyHammer()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(-9);
				updateRules(0,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(0);
	}
	public void actionLightHammer()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(-3);
				updateRules(0,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(-1);
	}

	public void actionDraw()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(-15);
				updateRules(1,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(1);
	}

	public void actionHammer()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(-6);
				updateRules(0,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(2);
	}

	public void actionPunch()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(2);
				updateRules(3,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(3);
	}

	public void actionBend()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(7);
				updateRules(4,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(4);
	}

	public void actionUpset()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(13);
				updateRules(5,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(5);
	}

	public void actionShrink()
	{
		if(!worldObj.isRemote)
		{
			if(canBeWorked())
			{
				workedRecently = LAG_FIX_DELAY;
				setItemCraftingValue(16);
				updateRules(6,1);
				damageHammer();
			}
		}
		else
			this.sendAnvilUsePacket(6);
	}

	public void actionWeld()
	{
		if(!worldObj.isRemote)
		{
			if(isTemperatureWeldable(WELD1_SLOT) && isTemperatureWeldable(WELD2_SLOT) && anvilItemStacks[HAMMER_SLOT] != null && 
					(anvilItemStacks[WELD1_SLOT].getItemDamage() == 0 || anvilItemStacks[WELD1_SLOT].getItem().getHasSubtypes() == true) && 
					(anvilItemStacks[WELD2_SLOT].getItemDamage() == 0 || anvilItemStacks[WELD2_SLOT].getItem().getHasSubtypes() == true) && 
					workedRecently == 0 && anvilItemStacks[WELDOUT_SLOT] == null)
			{
				AnvilManager manager = AnvilManager.getInstance();
				//new Random(worldObj.getSeed());  // Why is this here?
				AnvilRecipe recipe = new AnvilRecipe(anvilItemStacks[WELD1_SLOT],anvilItemStacks[WELD2_SLOT],"", 
						0,
						anvilItemStacks[FLUX_SLOT] != null ? true : false, AnvilTier, null);

				AnvilRecipe recipe2 = new AnvilRecipe(anvilItemStacks[WELD2_SLOT],anvilItemStacks[WELD1_SLOT],"",
						0,
						anvilItemStacks[FLUX_SLOT] != null ? true : false, AnvilTier, null);

				ItemStack result = manager.findCompleteWeldRecipe(recipe);
				if(result == null)
					result = manager.findCompleteWeldRecipe(recipe2);

				if(result != null)
				{
					TFC_ItemHeat.SetTemp(result, (TFC_ItemHeat.GetTemp(anvilItemStacks[2]) + TFC_ItemHeat.GetTemp(anvilItemStacks[3])) / 2);
					if(result.stackSize <= 0)
						result.stackSize = 1;
					setInventorySlotContents(WELDOUT_SLOT, result);
					setInventorySlotContents(WELD1_SLOT, null);
					setInventorySlotContents(WELD2_SLOT, null);
					decrStackSize(FLUX_SLOT, 1);
					damageHammer();
				}
			}
		}
		else
		{
			this.sendAnvilUsePacket(7);
		}
	}
	@Override
	public void closeInventory()
	{
		workRecipe = null;
		if(!worldObj.isRemote && anvilItemStacks[HAMMER_SLOT] == null && this.AnvilTier == AnvilReq.STONE.Tier)
		{
			ejectContents();
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockById(stonePair[0]), stonePair[1], 0x2);
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(anvilItemStacks[i] != null)
		{
			if(anvilItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = anvilItemStacks[i];
				anvilItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = anvilItemStacks[i].splitStack(j);
			if(anvilItemStacks[i].stackSize == 0)
				anvilItemStacks[i] = null;
			return itemstack1;
		}
		else
			return null;

	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(anvilItemStacks[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						anvilItemStacks[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	public int getAnvilType()
	{
		return blockMetadata & 7;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Anvil";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	public boolean setItemCraftingValue(int i)
	{
		if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].hasTagCompound() && anvilItemStacks[INPUT1_SLOT].getTagCompound().hasKey("itemCraftingValue"))
		{
			short icv = anvilItemStacks[INPUT1_SLOT].getTagCompound().getShort("itemCraftingValue");
			if(icv+i >= 0)
				anvilItemStacks[INPUT1_SLOT].getTagCompound().setShort("itemCraftingValue", (short) (icv + i));
			return true;
		}
		else if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].hasTagCompound())
		{
			if(i >= 0)
				anvilItemStacks[INPUT1_SLOT].getTagCompound().setShort("itemCraftingValue", (short)i);
			return true;
		}
		else if(anvilItemStacks[INPUT1_SLOT] != null && !anvilItemStacks[INPUT1_SLOT].hasTagCompound())
		{
			NBTTagCompound Tag = new NBTTagCompound();
			Tag.setShort("itemCraftingValue", (short)i);
			anvilItemStacks[INPUT1_SLOT].stackTagCompound = Tag;
			return true;
		}
		return false;
	}

	public int getItemCraftingValue()
	{
		if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].hasTagCompound() && anvilItemStacks[INPUT1_SLOT].getTagCompound().hasKey("itemCraftingValue"))
			return anvilItemStacks[INPUT1_SLOT].getTagCompound().getShort("itemCraftingValue");
		else if(anvilItemStacks[INPUT1_SLOT] != null && !anvilItemStacks[INPUT1_SLOT].hasTagCompound() && craftingValue != 0)
		{
			NBTTagCompound Tag = new NBTTagCompound();
			Tag.setShort("itemCraftingValue", (short) 0);
			anvilItemStacks[INPUT1_SLOT].setTagCompound(Tag);
			return 0;
		}
		else if(anvilItemStacks[INPUT1_SLOT] != null && anvilItemStacks[INPUT1_SLOT].hasTagCompound() && !anvilItemStacks[INPUT1_SLOT].getTagCompound().hasKey("itemCraftingValue") && craftingValue != 0)
		{
			NBTTagCompound Tag = anvilItemStacks[1].getTagCompound();
			Tag.setShort("itemCraftingValue", (short) 0);
			anvilItemStacks[INPUT1_SLOT].setTagCompound(Tag);
			return 0;
		}
		else
			return 0;
	}

	public int getItemCraftingValueNoSet(int i)
	{
		if(anvilItemStacks[i] != null && anvilItemStacks[i].hasTagCompound())
		{
			if(!anvilItemStacks[i].getTagCompound().hasKey("itemCraftingValue"))
				return 0;
			else
				return anvilItemStacks[i].getTagCompound().getShort("itemCraftingValue");
		}
		else if(anvilItemStacks[i] != null && !anvilItemStacks[i].hasTagCompound())
			return 0;
		else
			return 0;
	}

	public Boolean isTemperatureWeldable(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(TFC_ItemHeat.HasTemp(anvilItemStacks[i]))
		{
			HeatIndex index = manager.findMatchingIndex(anvilItemStacks[i]);
			if(index != null)
			{
				float temp = TFC_ItemHeat.GetTemp(anvilItemStacks[i]);
				return temp < index.meltTemp && temp > index.meltTemp - index.meltTemp * 0.20 && 
						(anvilItemStacks[i].getItem() instanceof ItemMeltedMetal ? anvilItemStacks[i].getItemDamage() == 0 : true);
			}
		}
		return false;
	}

	public Boolean isTemperatureWorkable(int i)
	{

		HeatRegistry manager = HeatRegistry.getInstance();
		if(TFC_ItemHeat.HasTemp(anvilItemStacks[i]))
		{
			HeatIndex index = manager.findMatchingIndex(anvilItemStacks[i]);
			if(index != null)
			{
				float temp = TFC_ItemHeat.GetTemp(anvilItemStacks[i]);
				return temp < index.meltTemp && temp > index.meltTemp - index.meltTemp * 0.40 && 
						(anvilItemStacks[i].getItem() instanceof ItemMeltedMetal ? anvilItemStacks[i].getItemDamage() == 0 : true);
			}
		}
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			if(itemstack.stackSize > getInventoryStackLimit())
				itemstack.stackSize = getInventoryStackLimit();
			if(itemstack.stackSize <= 0)
				itemstack = null;
		}
		anvilItemStacks[i] = itemstack;
		onSlotChanged(i);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public int getSizeInventory()
	{
		return anvilItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return anvilItemStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < anvilItemStacks.length; i++)
		{
			if(anvilItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				anvilItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("Tier", AnvilTier);
		nbt.setIntArray("stonePair", stonePair);
		nbt.setString("plan", craftingPlan);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		anvilItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < anvilItemStacks.length)
				anvilItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		AnvilTier = nbttagcompound.getInteger("Tier");
		stonePair = nbttagcompound.getIntArray("stonePair");
		craftingPlan = nbttagcompound.getString("plan");
	}

	public void setPlan(String s)
	{
		if(worldObj.isRemote)
			this.sendPlanPacket(s);
		this.craftingPlan = s;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		AnvilTier = nbt.getInteger("AnvilTier");
		stonePair = nbt.getIntArray("stonePair");
		if(nbt.hasKey("hammer"))
		{
			anvilItemStacks[TEAnvil.HAMMER_SLOT] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("hammer"));
		}
		if(nbt.hasKey("input"))
		{
			anvilItemStacks[TEAnvil.INPUT1_SLOT] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("input"));
		}
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		boolean soundFlag = false;
		switch(nbt.getInteger("action"))
		{
		case -1:
		{
			soundFlag = canBeWorked();
			actionLightHammer();
			break;
		}
		case 0:
		{
			soundFlag = canBeWorked();
			actionHeavyHammer();
			break;
		}
		case 1:
		{
			soundFlag = canBeWorked();
			actionDraw();
			break;
		}
		case 2:
		{
			soundFlag = canBeWorked();
			actionHammer();
			break;
		}
		case 3:
		{
			soundFlag = canBeWorked();
			actionPunch();
			break;
		}
		case 4:
		{
			soundFlag = canBeWorked();
			actionBend();
			break;
		}
		case 5:
		{
			soundFlag = canBeWorked();
			actionUpset();
			break;
		}
		case 6:   
		{
			soundFlag = canBeWorked();
			actionShrink();
			break;
		}
		case 7:
		{
			boolean weldFlag = anvilItemStacks[WELDOUT_SLOT] == null;
			actionWeld();
			soundFlag = weldFlag && anvilItemStacks[WELDOUT_SLOT] != null;
			break;
		}
		case 8:
		{
			if(!worldObj.isRemote)
			{
				setPlan(nbt.getString("plan"));
				this.lastWorker = worldObj.getPlayerEntityByName(nbt.getString("playername"));
				this.lastWorker.openGui(TerraFirmaCraft.instance, 21, worldObj, xCoord, yCoord, zCoord);
				this.updateRecipe();
			}
			return;
		}
		}
		if(soundFlag){
			worldObj.playSoundEffect(xCoord,yCoord,zCoord, TFC_Sounds.METALIMPACT, 0.1F, 0.1F + (worldObj.rand.nextFloat()/4));
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setInteger("AnvilTier", AnvilTier);
		nbt.setIntArray("stonePair", stonePair);
		if(anvilItemStacks[TEAnvil.HAMMER_SLOT] != null)
		{
			NBTTagCompound hammerNBT = new NBTTagCompound();
			hammerNBT = anvilItemStacks[TEAnvil.HAMMER_SLOT].writeToNBT(hammerNBT);
			nbt.setTag("hammer", hammerNBT);
		}
		if(anvilItemStacks[TEAnvil.INPUT1_SLOT] != null)
		{
			NBTTagCompound inputNBT = new NBTTagCompound();
			inputNBT = anvilItemStacks[TEAnvil.INPUT1_SLOT].writeToNBT(inputNBT);
			nbt.setTag("input", inputNBT);
		}
	}

	@SideOnly(Side.CLIENT)
	private void sendAnvilUsePacket(int i) 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("action", i);
		nbt.setString("playername", PlayerManagerTFC.getInstance().getClientPlayer().Name);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	@SideOnly(Side.CLIENT)
	private void sendPlanPacket(String plan) 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("action", 8);
		nbt.setString("plan", plan);
		nbt.setString("playername", PlayerManagerTFC.getInstance().getClientPlayer().Name);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}


	////////////////////////////////////////////////////////////////////////////
	//TODO Update packet
	/*public void handleDataPacket(DataInputStream inStream) throws IOException  
	{
		switch(inStream.readInt())
		{
		case -1:
		{
			actionLightHammer();
			break;
		}
		case 0:
		{
			actionHeavyHammer();
			break;
		}
		case 1:
		{
			actionDraw();
			break;
		}
		case 2:
		{
			actionHammer();
			break;
		}
		case 3:
		{
			actionPunch();
			break;
		}
		case 4:
		{
			actionBend();
			break;
		}
		case 5:
		{
			actionUpset();
			break;
		}
		case 6:   
		{
			actionShrink();
			break;
		}
		case 7:
		{
			actionWeld();
			break;
		}
		}	
		worldObj.playSoundEffect(xCoord,yCoord,zCoord, TFC_Sounds.METALIMPACT, 0.1F, 0.1F + (worldObj.rand.nextFloat()/4));
	}
	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException  
	{
		outStream.writeInt(AnvilTier);
		outStream.writeInt(stonePair[0]);
		outStream.writeInt(stonePair[1]);
		outStream.writeInt(anvilItemStacks[this.HAMMER_SLOT]!= null ? Item.getIdFromItem(anvilItemStacks[this.HAMMER_SLOT].getItem()) : 0);
		outStream.writeInt(anvilItemStacks[this.INPUT1_SLOT]!= null ? Item.getIdFromItem(anvilItemStacks[this.INPUT1_SLOT].getItem()) : 0);
	}
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		AnvilTier = inStream.readInt();
		stonePair[0] = inStream.readInt();
		stonePair[1] = inStream.readInt();
		int id = inStream.readInt();
		Item item = Item.getItemById(id);
		if(item != null)
			anvilItemStacks[HAMMER_SLOT] = new ItemStack(item);
		id = inStream.readInt();
		item = Item.getItemById(id);
		if(item != null)
			anvilItemStacks[INPUT1_SLOT] = new ItemStack(item);

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public Packet createAnvilUsePacket(int id)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try
		{
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(id);
			dos.writeUTF(PlayerManagerTFC.getInstance().getClientPlayer().Name);
		}
		catch (IOException e)
		{
		}
		return null; this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	public Packet createAnvilPlanPacket(int id, String s)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try
		{
			dos.writeByte(PacketHandler.Packet_Data_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(id);
			dos.writeUTF(s);
			dos.writeUTF(PlayerManagerTFC.getInstance().getClientPlayer().Name);
		}
		catch (IOException e)
		{
		}
		return null; this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	@Override
	public void handleDataPacketServer(DataInputStream inStream)throws IOException 
	{
		switch(inStream.readInt())
		{
		case -1:
		{
			actionLightHammer();
			break;
		}
		case 0:
		{
			actionHeavyHammer();
			break;
		}
		case 1:
		{
			actionDraw();
			break;
		}
		case 2:
		{
			actionHammer();
			break;
		}
		case 3:
		{
			actionPunch();
			break;
		}
		case 4:
		{
			actionBend();
			break;
		}
		case 5:
		{
			actionUpset();
			break;
		}
		case 6:   
		{
			actionShrink();
			break;
		}
		case 7:
		{
			actionWeld();
			break;
		}
		case 8:
		{
			setPlan(inStream.readUTF());
			this.lastWorker = worldObj.getPlayerEntityByName(inStream.readUTF());
			this.lastWorker.openGui(TerraFirmaCraft.instance, 21, worldObj, xCoord, yCoord, zCoord);
			return;
		}
		}		
		this.lastWorker = worldObj.getPlayerEntityByName(inStream.readUTF());
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, TFC_Sounds.METALIMPACT, 0.1F, 0.5F + (worldObj.rand.nextFloat() / 2));
	}*/
}
