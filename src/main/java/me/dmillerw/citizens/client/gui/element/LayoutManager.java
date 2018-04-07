package me.dmillerw.citizens.client.gui.element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LayoutManager {

    private GuiScreen parentGui;

    private Map<String, WrappedElement<?>> elements = Maps.newHashMap();

    private List<WrappedElement<?>> sortedByX = Lists.newArrayList();
    private List<WrappedElement<?>> sortedByY = Lists.newArrayList();

    private List<WrappedElement<?>> sortedByZ = Lists.newArrayList();

    private int width = 0;
    private int height = 0;

    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingDown = 0;

    public LayoutManager(GuiScreen gui) {
        this.parentGui = gui;
    }

    protected void addElement(WrappedElement<?> element) {
        this.elements.put(element.getId(), element);

        this.sortedByX.add(element);
        this.sortedByX.sort(Comparator.comparingInt(c -> c.x));

        this.sortedByY.add(element);
        this.sortedByY.sort(Comparator.comparingInt(c -> c.y));
    }

    public void recalculateBounds() {
        int twidth = 0;
        int lastX = 0;

        for (WrappedElement<?> element : sortedByX) {
            if (element.x < lastX) {
                twidth += element.width - (lastX - element.x);
            } else {
                twidth += element.width + (element.x - lastX);
            }

            lastX = element.x;
        }

        int theight = 0;
        int lastY = 0;

        for (WrappedElement<?> element : sortedByY) {
            if (element.y < lastY) {
                theight += element.height - (lastY - element.y);
            } else {
                theight += element.height + (element.y - lastY);
            }

            lastY = element.y;
        }

        this.width = paddingLeft + twidth + paddingRight;
        this.height = paddingTop + theight + paddingDown;
    }

    public Minecraft getMinecraft() {
        return this.parentGui.mc;
    }
}
