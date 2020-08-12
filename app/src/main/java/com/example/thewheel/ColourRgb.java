package com.example.thewheel;

public class ColourRgb {
    int red;
    int green;
    int blue;

    public ColourRgb(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static String rgbToHex(ColourRgb colour) {
        return "#3832a8";
    }

    public static ColourRgb hexToRgb(String hex) {
        int red = 0;
        int green = 0;
        int blue = 0;

        ColourRgb colour = new ColourRgb(red, green, blue);

        return colour;
    }
}
