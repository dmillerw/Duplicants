package me.dmillerw.duplicants.client.render.entity;

import me.dmillerw.duplicants.client.model.ModelDuplicant;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import me.dmillerw.duplicants.proxy.ClientProxy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.UUID;

public class RenderDuplicant extends RenderLivingBase<EntityDuplicant> {

    public RenderDuplicant(RenderManager renderManagerIn, float shadowSizeIn) {
        super(renderManagerIn, new ModelDuplicant(), shadowSizeIn);
    }

    @Override
    public void doRender(EntityDuplicant entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
    }

    @Override
    protected void preRenderCallback(EntityDuplicant entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void renderName(EntityDuplicant entity, double x, double y, double z) {
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDuplicant entity) {
        return DefaultPlayerSkin.getDefaultSkin(UUID.fromString("f579a27a-d06b-46a7-9f90-f77af54a108c"));
    }
}
