package TFC.API;

import TFC.TFCItems;
import net.minecraft.item.ItemStack;

public class Util
{
	public static ItemStack consumeItem(ItemStack stack) {
		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem()) {
				return stack.getItem().getContainerItemStack(stack);
			} else if (stack.getItem() == TFCItems.WoodenBucketWater) {
				return new ItemStack(TFCItems.WoodenBucketEmpty);
			} else {
				return null;
			}
		} else {
			stack.splitStack(1);

			return stack;
		}
	}
}
