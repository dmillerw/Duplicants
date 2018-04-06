package me.dmillerw.citizens.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySeat extends Entity {

    public EntitySeat(World worldIn) {
        super(worldIn);

        setSize(0, 0);
    }

    public EntitySeat(World world, BlockPos blockPos) {
        super(world);

        setSize(0, 0);
        setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.01, blockPos.getZ() + 0.5);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            return false;
        } else {
            if (!this.world.isRemote) {
                player.startRiding(this);
            }

            return true;
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}
