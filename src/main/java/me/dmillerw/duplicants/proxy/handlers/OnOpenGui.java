package me.dmillerw.duplicants.proxy.handlers;

import me.dmillerw.duplicants.network.GuiHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface OnOpenGui {

    public Gui getClientGui(EntityPlayer entityPlayer, GuiHandler.Target target);
    public Container getServerContainer(EntityPlayer entityPlayer, GuiHandler.Target target);
}
