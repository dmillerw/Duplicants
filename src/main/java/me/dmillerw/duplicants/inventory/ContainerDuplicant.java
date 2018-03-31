package me.dmillerw.duplicants.inventory;

import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ContainerDuplicant extends Container {

    private final EntityPlayer entityPlayer;
    private final EntityDuplicant entityDuplicant;

    public ContainerDuplicant(EntityPlayer entityPlayer, EntityDuplicant entityDuplicant) {
        this.entityPlayer = entityPlayer;
        this.entityDuplicant = entityDuplicant;

        // Duplicant Inv
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(entityDuplicant.inventory, i, 8 + 18 * i, 84));
        }

        for (int i = 0; i < 4; i++) {
            addSlotToContainer(new Slot(new WrappedInventory((NonNullList<ItemStack>) entityDuplicant.getArmorInventoryList()), i, 8, 8 + 18 * i));
        }

        addSlotToContainer(new Slot(new WrappedInventory((NonNullList<ItemStack>) entityDuplicant.getHeldEquipment()), 0, 85, 62));

        // Player Inv
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 106 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, l, 8 + l * 18, 164));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
