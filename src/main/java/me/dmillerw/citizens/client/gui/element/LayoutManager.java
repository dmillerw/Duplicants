package me.dmillerw.citizens.client.gui.element;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Collection;
import java.util.Map;

public class LayoutManager {

    private GuiScreen parentGui;

    private Map<String, WrappedElement<?>> elements = Maps.newHashMap();

    private int largestXPosition = -1;
    private int largestYPosition = -1;

    private int width = -1;
    private int height = -1;

    public int paddingLeft = 5;
    public int paddingRight = 5;
    public int paddingTop = 5;
    public int paddingDown = 5;

    public LayoutManager(GuiScreen gui) {
        this.parentGui = gui;
    }

    public void addAll(Collection<WrappedElement<?>> elements) {
        elements.forEach(this::addElement);
    }

    public void addElement(WrappedElement<?> element) {
        element.attachLayoutManager(this);

        this.elements.put(element.getId(), element);

        if (element.x >= largestXPosition) {
            largestXPosition = element.x;
            width = paddingLeft * 2 + largestXPosition + element.width + paddingRight;
        }

        if (element.y >= largestYPosition) {
            largestYPosition = element.y;
            height = paddingTop * 2 + largestYPosition + element.height + paddingDown;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void draw(int mouseX, int mouseY, float partial) {
        elements.values().forEach((e) -> e.drawElement(getMinecraft(), mouseX, mouseY, partial));
    }

    public Minecraft getMinecraft() {
        return this.parentGui.mc;
    }
}
