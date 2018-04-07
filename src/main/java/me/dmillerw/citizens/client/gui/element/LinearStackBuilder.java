package me.dmillerw.citizens.client.gui.element;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.LinkedList;

public class LinearStackBuilder {

    public enum Direction {

        HORIZONTAL, VERTICAL
    }

    private LinkedList<WrappedElement<?>> elements = Lists.newLinkedList();

    private Direction direction;

    private int nextCoordinate = 0;
    private int maxThickness = 0;

    public LinearStackBuilder(Direction direction) {
        this.direction = direction;
    }

    public LinearStackBuilder appendElement(WrappedElement<?> element) {
        appendElement(element, 0);
        return this;
    }

    public LinearStackBuilder appendElement(WrappedElement<?> element, int padding) {
        this.elements.add(element);

        if (direction == Direction.HORIZONTAL) {
            element.x = nextCoordinate;
            nextCoordinate += element.width + padding;
            if (element.height > maxThickness) maxThickness = element.height;
        }

        if (direction == Direction.VERTICAL) {
            element.y = nextCoordinate;
            nextCoordinate += element.height + padding;
            if (element.width > maxThickness) maxThickness = element.width;
        }

        return this;
    }

    public Collection<WrappedElement<?>> getElements() {
        return elements;
    }
}
