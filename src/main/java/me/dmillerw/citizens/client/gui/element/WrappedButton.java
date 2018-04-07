package me.dmillerw.citizens.client.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class WrappedButton extends WrappedElement<GuiButtonExt> {

    private String displayString;
    private String[] tooltip = new String[0];

    public WrappedButton(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
    }

    public WrappedButton(String id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height);

        this.displayString = displayString;
    }

    @Override
    public GuiButtonExt constructElement(Minecraft mc) {
        return new GuiButtonExt(0, x, y, width, height, displayString);
    }

    public WrappedButton displayString(String displayString) {
        getMcElement().displayString = displayString;
        return this;
    }

    public WrappedButton tooltip(String tooltip) {
        tooltip(new String[] { tooltip });
        return this;
    }

    public WrappedButton tooltip(String[] tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public void drawElement(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        getMcElement().drawButton(mc, mouseX, mouseY, partialTicks);
    }
}
