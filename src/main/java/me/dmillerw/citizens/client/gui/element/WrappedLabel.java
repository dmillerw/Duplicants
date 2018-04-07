package me.dmillerw.citizens.client.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;

public class WrappedLabel extends WrappedElement<GuiLabel> {

    public WrappedLabel(FontRenderer fontRenderer, String id, int x, int y, int width, int height, String string) {
        super(id, x, y, width, height);
    }

    @Override
    public GuiLabel constructElement(Minecraft mc) {
        return null;
    }

    @Override
    public void drawElement(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

    }
}
