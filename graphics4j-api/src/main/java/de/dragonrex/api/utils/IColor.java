package de.dragonrex.api.utils;

public interface IColor {
    void setRed(int r);
    void setGreen(int g);
    void setBlue(int b);
    void setAlpha(int Alpha);

    byte getAlpha();
    byte getBlue();
    byte getGreen();
    byte getRed();
}
