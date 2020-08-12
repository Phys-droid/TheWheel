package com.example.thewheel;

public class ColourRgb {

    int red;
    int green;
    int blue;

    public static String alphabet = "ABCDEFG";

    public ColourRgb(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static String rgbToHexConverter(ColourRgb colour) {
        String hexSeq = "#";
        hexSeq = hexSeq + hexadecimalConverter(colour.red);
        hexSeq = hexSeq + hexadecimalConverter(colour.green);
        hexSeq = hexSeq + hexadecimalConverter(colour.blue);
        //System.out.println("THE FINAL HEX IS: " + hexSeq);
        return hexSeq;
    }

    private static String hexadecimalConverter(int inValue) {
        int firstVal = (int)Math.floor(inValue / 16);
        //System.out.println("The FirstVal /16 floored is: " + firstVal + "Before it was: " + inValue);
        int secondVal = ((inValue/16) - firstVal)*16;

        char firstLetter;
        char secondLetter;

        if (firstVal > 9) {
            firstLetter = alphabet.charAt(firstVal);
        }
        else {
            //System.out.println("FirstLetter: " + Integer.toString(firstVal).charAt(0)+ " Original: " + firstVal);
            firstLetter = Integer.toString(firstVal).charAt(0);
        }
        if (secondVal > 9) {
            secondLetter = alphabet.charAt(secondVal);
        }
        else {
            secondLetter = Integer.toString(secondVal).charAt(0);
        }
        String finalSeq = "" + firstLetter + secondLetter;

        //System.out.println("I'm returning following Sequence: " + finalSeq);
        return finalSeq;
    }


    public static ColourRgb hexToRgbConverter(String hex) {

        String redSeq = "";
        String greenSeq = "";
        String blueSeq = "";
        redSeq = redSeq + hex.charAt(1);
        redSeq = redSeq + hex.charAt(2);
        greenSeq = greenSeq + hex.charAt(3);
        greenSeq = greenSeq + hex.charAt(4);
        blueSeq = blueSeq + hex.charAt(5);
        blueSeq = blueSeq + hex.charAt(6);

        //System.out.println("THE CHARS ARE: " + redSeq + " - " + greenSeq);
        //System.out.println("THE CHARS 0: " + redSeq.charAt(0) + " -1: " + redSeq.charAt(1));
        //System.out.println("THE CHARS in ALPHA: " + alphabet.indexOf(redSeq.charAt(0)) + " -: " + alphabet.indexOf(redSeq.charAt(1)));

        int redVal = decimalConverter(redSeq.charAt(0), redSeq.charAt(1));
        int greenVal = decimalConverter(greenSeq.charAt(0), redSeq.charAt(1));
        int blueVal = decimalConverter(blueSeq.charAt(0), redSeq.charAt(1));

        ColourRgb colour = new ColourRgb(redVal, greenVal, blueVal);

        return colour;
    }

    private static int decimalConverter(char firstSeq, char secondSeq) {
        int firstDec;
        int secondDec;
        //System.out.println("ALPHABET_CHAR_AT: " + alphabet.indexOf(firstSeq) + " FIRST SEQ: " + firstSeq);
        if (alphabet.indexOf(firstSeq) != -1) {
            firstDec = alphabet.indexOf(firstSeq) + 10;
        }
        else {
            firstDec = Character.getNumericValue(firstSeq) * 16;
            //System.out.println("RIGHT NOW FIRSTDEC: " + firstDec + " - BECAUSE: " + firstSeq + " * 16 = " + firstDec);
        }
        if (alphabet.indexOf(secondSeq) != -1) {
            secondDec = alphabet.indexOf(secondSeq) + 10;
        }
        else {
            secondDec = Character.getNumericValue(secondSeq);
        }
        //System.out.println("INTERESTING: " + firstDec + " SEC: " + secondDec);
        return (firstDec+secondDec);
    }
}
