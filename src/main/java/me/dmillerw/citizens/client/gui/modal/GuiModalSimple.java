package me.dmillerw.citizens.client.gui.modal;

import me.dmillerw.citizens.client.gui.element.LayoutManager;
import me.dmillerw.citizens.client.gui.element.LinearStackBuilder;
import me.dmillerw.citizens.client.gui.element.WrappedButton;
import me.dmillerw.citizens.client.gui.element.WrappedLabel;

public class GuiModalSimple extends GuiModal {

    @Override
    public void initializeLayout(LayoutManager layoutManager) {
        LinearStackBuilder stackBuilder = new LinearStackBuilder(LinearStackBuilder.Direction.HORIZONTAL);
        stackBuilder.appendElement(new WrappedLabel("name", 45, 20).string("Name:"));
        stackBuilder.appendElement(new WrappedButton("test", 100, 20).displayString("Test"));
        layoutManager.addAll(stackBuilder.getElements());
    }
}
