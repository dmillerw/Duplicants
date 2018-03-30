package me.dmillerw.duplicants.client.render.entity;

import me.dmillerw.duplicants.entity.EntityBottledSouls;
import me.dmillerw.duplicants.item.ModItems;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBottledSouls extends RenderSnowball<EntityBottledSouls> {

    public RenderBottledSouls(RenderManager renderManagerIn, RenderItem itemRendererIn) {
        super(renderManagerIn, ModItems.bottledSouls, itemRendererIn);
    }
}