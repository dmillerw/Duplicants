package me.dmillerw.duplicants.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityBottledSouls extends EntityThrowable {

    private static final int RANGE = 16;
    private static final int CURE_RANGE = 1;
    private static final int ANGER_RANGE = 12;

    public EntityBottledSouls(World world) {
        super(world);
    }

    public EntityBottledSouls(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(RANGE, RANGE, RANGE);
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

        if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
                if (entitylivingbase instanceof EntityPigZombie) {
                    if (entitylivingbase.canBeHitWithPotion()) {
                        double distance = Double.MAX_VALUE;
                        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                            distance = entitylivingbase.getDistanceSq(result.getBlockPos());
                        } else if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                            distance = entitylivingbase.getDistanceSq(result.entityHit);
                        }

                        if (distance <= CURE_RANGE) {
                            EntityDuplicant entityDuplicant = new EntityDuplicant(((EntityPigZombie) entitylivingbase).world);
                            entityDuplicant.setPosition(
                                    ((EntityPigZombie) entitylivingbase).posX,
                                    ((EntityPigZombie) entitylivingbase).posY,
                                    ((EntityPigZombie) entitylivingbase).posZ
                            );

                            if (!world.isRemote) {
                                ((EntityPigZombie) entitylivingbase).world.spawnEntity(entityDuplicant);
                                entitylivingbase.setDead();
                            }

                            for (int i = 0; i < 300; ++i) {
                                int j = rand.nextInt(2) * 2 - 1;
                                int k = rand.nextInt(2) * 2 - 1;
                                double d0 = entityDuplicant.posX + 0.25D * (double) j;
                                double d1 = (double) ((float) entityDuplicant.posY + entityDuplicant.getEyeHeight() - rand.nextFloat());
                                double d2 = entityDuplicant.posZ + 0.25D * (double) k;
                                double d3 = (double) (rand.nextFloat() * (float) j);
                                double d4 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
                                double d5 = (double) (rand.nextFloat() * (float) k);
                                entityDuplicant.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0, d1, d2, d3, d4, d5);
                            }
                        } else if (distance <= ANGER_RANGE) {
                            if (getThrower() != null && getThrower() instanceof EntityPlayer) {
                                entitylivingbase.setRevengeTarget(getThrower());
                            }
                        }
                    }
                }
            }
        }
    }
}
