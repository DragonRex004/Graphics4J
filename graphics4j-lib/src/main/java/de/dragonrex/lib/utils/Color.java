package de.dragonrex.lib.utils;

import de.dragonrex.api.utils.IColor;

public class Color implements IColor {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 0;

    public Color(int r, int g, int b, int alpha) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = alpha;
    }

    @Override
    public void setRed(int r) {
        this.red = r;
    }

    @Override
    public void setGreen(int g) {
        this.green = g;
    }

    @Override
    public void setBlue(int b) {
        this.blue = b;
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public byte getAlpha() {
        return (byte) this.alpha;
    }

    @Override
    public byte getBlue() {
        return (byte) this.blue;
    }

    @Override
    public byte getGreen() {
        return (byte) this.green;
    }

    @Override
    public byte getRed() {
        return (byte) this.red;
    }
}
