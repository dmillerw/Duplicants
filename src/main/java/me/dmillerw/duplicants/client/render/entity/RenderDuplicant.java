package me.dmillerw.duplicants.client.render.entity;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.dmillerw.duplicants.client.model.ModelDuplicant;
import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class RenderDuplicant extends RenderBiped<EntityDuplicant> {

    public RenderDuplicant(RenderManager renderManagerIn, float shadowSizeIn) {
        super(renderManagerIn, new ModelDuplicant(), shadowSizeIn);

        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
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
        if (entity.getShowNameplate()) {
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre<>(entity, this, x, y, z)))
                return;

            double d0 = entity.getDistanceSq(this.renderManager.renderViewEntity);
            float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;

            if (d0 < (double) (f * f)) {
                String s = entity.getName();
                GlStateManager.alphaFunc(516, 0.1F);
                this.renderEntityName(entity, x, y, z, s, d0);
            }

            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Specials.Post<>(entity, this, x, y, z));
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDuplicant entity) {
        ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();

        if (entity.clientProfile != null) {
            Minecraft minecraft = Minecraft.getMinecraft();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(entity.clientProfile);

            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            } else {
                minecraft.getSkinManager().loadProfileTextures(entity.clientProfile, (typeIn, location, profileTexture) -> {
                    System.out.println(typeIn);
                    System.out.println(location);
                    System.out.println(profileTexture);
                }, false);
                UUID uuid = EntityPlayer.getUUID(entity.clientProfile);
                resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
            }
        }

        return resourcelocation;
    }
}
