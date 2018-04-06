package me.dmillerw.citizens.inventory;

import me.dmillerw.citizens.entity.EntityDuplicant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

public class InventoryCitizen implements IInventory {

    private final NonNullList<ItemStack> mainInventory = NonNullList.withSize(9, ItemStack.EMPTY);

    private final EntityDuplicant entityDuplicant;

    public InventoryCitizen(EntityDuplicant entityDuplicant) {
        this.entityDuplicant = entityDuplicant;
    }

    @Override
    public int getSizeInventory() {
        return mainInventory.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 9)
            return mainInventory.get(index);
        else
            return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return getStackInSlot(index).splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack copy = getStackInSlot(index).copy();

        if (index < 9)
            mainInventory.set(index, ItemStack.EMPTY);

        return copy;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 9)
            mainInventory.set(index, stack.copy());
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
        return true;
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

    public NBTTagCompound writeToNBT(NBTTagCompound mainTag) {
        NBTTagList main = new NBTTagList();
        for (int i = 0; i < this.mainInventory.size(); i++) {
            if (!this.mainInventory.get(i).isEmpty()) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.mainInventory.get(i).writeToNBT(tagCompound);
                main.appendTag(tagCompound);
            }
        }
        mainTag.setTag("Main", main);

        return mainTag;
    }

    public void readFromNBT(NBTTagCompound mainTag) {
        readInventory(mainInventory, mainTag.getTagList("Main", Constants.NBT.TAG_COMPOUND));
    }

    private void readInventory(NonNullList<ItemStack> inventory, NBTTagList list) {
        inventory.clear();
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound itemTag = list.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            ItemStack itemstack = new ItemStack(itemTag);
            inventory.set(slot, itemstack);
        }
    }

    public void dropAllItems() {
        for (int i=0; i<this.mainInventory.size(); i++) {
            ItemStack itemStack = this.mainInventory.get(i);
            if (!itemStack.isEmpty()) {
                this.entityDuplicant.entityDropItem(itemStack, 0F);
                this.mainInventory.set(i, ItemStack.EMPTY);
            }
        }
    }
}
