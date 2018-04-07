package me.dmillerw.citizens.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class WrappedInventory implements IInventory {

    private final NonNullList<ItemStack> backingList;
    public WrappedInventory(NonNullList<ItemStack> backingList) {
        this.backingList = backingList;
    }

    @Override
    public int getSizeInventory() {
        return backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return backingList.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return backingList.get(index).splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack copy = backingList.get(index).copy();
        backingList.set(index, ItemStack.EMPTY);
        return copy;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        backingList.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
