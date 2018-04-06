package me.dmillerw.citizens.inventory;

import me.dmillerw.citizens.entity.EntityCitizen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ContainerCitizenInventory extends Container {

    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

    private final EntityPlayer entityPlayer;
    private final EntityCitizen entityCitizen;

    public ContainerCitizenInventory(EntityPlayer entityPlayer, EntityCitizen entityCitizen) {
        this.entityPlayer = entityPlayer;
        this.entityCitizen = entityCitizen;

        // Duplicant Inv
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(entityCitizen.inventory, i, 8 + 18 * i, 84));
        }

        for (int i = 0; i < 4; i++) {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[i];
            addSlotToContainer(new Slot(new WrappedInventory((NonNullList<ItemStack>) entityCitizen.getArmorInventoryList()), 3 - i, 8, 8 + 18 * i) {
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem().isValidArmor(stack, entityequipmentslot, entityPlayer);
                }

                public boolean canTakeStack(EntityPlayer playerIn) {
                    ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
                }

                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }
            });
        }

        addSlotToContainer(new Slot(new WrappedInventory((NonNullList<ItemStack>) entityCitizen.getHeldEquipment()), 0, 85, 62));

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
