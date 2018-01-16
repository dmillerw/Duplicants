package me.dmillerw.duplicants.entity;

import me.dmillerw.duplicants.handler.SelectionHandler;
import me.dmillerw.duplicants.item.ModItems;
import me.dmillerw.duplicants.network.PacketHandler;
import me.dmillerw.duplicants.network.packet.CSelectDuplicant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityDuplicant extends EntityLiving {

    public static EntityDuplicant getFromWorld(World world, UUID uuid) {
        if (uuid == null) return null;
        return world.getEntities(EntityDuplicant.class, (e) -> e.getPersistentID().equals(uuid)).stream().findFirst().get();
    }

    private BlockPos blockTarget;

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
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!itemStack.isEmpty() && itemStack.getItem() == ModItems.selector) {
            if (!player.world.isRemote) {
                player.sendMessage(new TextComponentString("Selected duplicant"));
                SelectionHandler.INSTANCE.onDuplicantSelected(player, this);
                PacketHandler.INSTANCE.sendTo(new CSelectDuplicant(this), (EntityPlayerMP) player);
            }

            return true;
        } else {
            return false;
        }
    }
}
