package me.dmillerw.citizens.common.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventoryUtil {

    public static void dropInventory(EntityLivingBase entityLivingBase, IInventory inventory) {
        for (int i=0; i<inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            entityLivingBase.entityDropItem(itemStack, 0F);
            inventory.setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    public static void dropInventory(EntityLivingBase entityLivingBase, NonNullList<ItemStack> inventory) {
        for (int i=0; i<inventory.size(); i++) {
            ItemStack itemStack = inventory.get(i);
            entityLivingBase.entityDropItem(itemStack, 0F);
            inventory.set(i, ItemStack.EMPTY);
        }
    }
}
