package me.dmillerw.citizens.client.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLabel;

public class WrappedLabel extends WrappedElement<GuiLabel> {

    private String string;
    private int textColor = 4210752;

    public WrappedLabel(String id) {
        super(id);
    }

    public WrappedLabel(String id, int width, int height) {
        super(id, width, height);
    }

    public WrappedLabel(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
    }

    public WrappedLabel string(String string) {
        this.string = string;
        return this;
    }

    public WrappedLabel textColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    @Override
    public GuiLabel constructElement(Minecraft mc) {
        GuiLabel label = new GuiLabel(mc.fontRenderer, 0, x, y, width, height, textColor);
        label.addLine(this.string);
        return label;
    }

    @Override
    public void drawElement(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        getMcElement().drawLabel(mc, mouseX, mouseY);
    }
}
