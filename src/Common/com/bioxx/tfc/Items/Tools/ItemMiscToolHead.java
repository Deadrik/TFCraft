package com.bioxx.tfc.Items.Tools;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
public class ItemMiscToolHead extends ItemTerra
{
	private ToolMaterial material;

	public ItemMiscToolHead()
	{
		super();
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFCMisc);
		this.setFolder("toolheads/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	public ItemMiscToolHead(ToolMaterial m)
	{
		this();
		material = m;
	}

	public ToolMaterial getMaterial()
	{
		return material;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("IgIn ", "");
		name = name.replace("IgEx ", "");
		name = name.replace("Sed ", "");
		name = name.replace("MM ", "");
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + name);
	}
}
