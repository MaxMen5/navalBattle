package ru.eltech.entity;

import ru.eltech.enums.Layout;
import ru.eltech.enums.Length;

public class Ship {

    public Length length;
    public Layout layout;
    private boolean[] status;
    public boolean isAlive;
    public int index;

    Ship(Length length, Layout layout, int index) {
        this.length = length;
        this.layout = layout;
        this.index = index;
        status = new boolean[length.getSize()];
        for (int i = 0; i < length.getSize(); i++) status[i] = true;
        isAlive = true;
    }

    private void Injury(Integer index) {
        status[index] = false;
        for (int i = 0; i < length.getSize(); i++) if (status[i]) return;
        isAlive = false;
    }
}