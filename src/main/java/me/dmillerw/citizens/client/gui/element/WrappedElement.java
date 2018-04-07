package me.dmillerw.citizens.client.gui.element;

import net.minecraft.client.Minecraft;

public abstract class WrappedElement<E> {

    private LayoutManager layoutManager;

    private final String id;

    private E object;

    public final int x;
    public final int y;

    public final int width;
    public final int height;

    public WrappedElement(String id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public final void attachLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.object = constructElement(layoutManager.getMinecraft());
    }

    public String getId() {
        return this.id;
    }

    public abstract E constructElement(Minecraft mc);

    public abstract void drawElement(Minecraft mc, int mouseX, int mouseY, float partialTicks);

    public final E getMcElement() {
        return object;
    }
}
