package me.dmillerw.duplicants.proxy.handlers;

import me.dmillerw.duplicants.entity.EntityDuplicant;
import net.minecraft.entity.player.EntityPlayer;

public interface OnDuplicantSelected {

    public void onDuplicantSelected(EntityPlayer entityPlayer, EntityDuplicant entityDuplicant);
}
