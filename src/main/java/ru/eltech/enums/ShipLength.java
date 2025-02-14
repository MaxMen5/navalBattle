package ru.eltech.enums;

public enum ShipLength { ONE(1), TWO(2), THREE(3), FOUR(4);
    private final Integer size;
    ShipLength(Integer size) { this.size = size; }
    public Integer getSize() { return size; }
}
