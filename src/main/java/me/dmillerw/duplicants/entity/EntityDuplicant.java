package me.dmillerw.duplicants.entity;

import me.dmillerw.duplicants.inventory.InventoryDuplicant;
import me.dmillerw.duplicants.item.ModItems;
import me.dmillerw.duplicants.network.GuiHandler;
import me.dmillerw.duplicants.network.PacketHandler;
import me.dmillerw.duplicants.network.packet.CSelectDuplicant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityDuplicant extends EntityLiving {

    public final InventoryDuplicant inventory = new InventoryDuplicant(this);

    public EntityDuplicant(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        EntityPlayer closest = world.getClosestPlayerToEntity(this, 16D);
        if (closest == null) return;

        getLookHelper().setLookPosition(closest.posX, closest.posY + (double) closest.getEyeHeight(), closest.posZ, 10.0F, (float) getVerticalFaceSpeed());
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.inventory.readFromNBT(compound.getCompoundTag("Inventory"));
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return super.getItemStackFromSlot(slotIn);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        System.out.println(player.world.isRemote);
        if (!player.world.isRemote) {
            GuiHandler.openGui(GuiHandler.GuiKey.DUPLICANT, player, new GuiHandler.Target(this.getEntityId()));
        }
        return true;
    }
}
