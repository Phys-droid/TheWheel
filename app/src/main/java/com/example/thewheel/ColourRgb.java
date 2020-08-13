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
        return hexSeq;
    }

    private static String hexadecimalConverter(int inValue) {
        int firstVal = (int) Math.floor(inValue / 16);
        int secondVal = (int) ((((double) inValue / 16) - firstVal) * 16);

        char firstLetter;
        char secondLetter;

        if (firstVal > 9) {
            firstLetter = alphabet.charAt(firstVal - 10); //Adapt to alphabet array
        } else {
            firstLetter = Integer.toString(firstVal).charAt(0);
        }
        if (secondVal > 9) {
            secondLetter = alphabet.charAt(secondVal - 10);
        } else {
            secondLetter = Integer.toString(secondVal).charAt(0);
        }
        String finalSeq = "" + firstLetter + secondLetter;

        return finalSeq;
    }

    public static ColourRgb hexToRgbConverter(String hex) {
        int redVal = decimalConverter(hex.charAt(1), hex.charAt(2));
        int greenVal = decimalConverter(hex.charAt(3), hex.charAt(4));
        int blueVal = decimalConverter(hex.charAt(5), hex.charAt(6));
        ColourRgb colour = new ColourRgb(redVal, greenVal, blueVal);


        return colour;
    }

    private static int decimalConverter(char firstSeq, char secondSeq) {
        int firstDec;
        int secondDec;
        if (alphabet.indexOf(firstSeq) != -1) {
            firstDec = (alphabet.indexOf(firstSeq) + 10) * 16;
        } else {
            firstDec = Character.getNumericValue(firstSeq) * 16;
        }
        if (alphabet.indexOf(secondSeq) != -1) {
            secondDec = alphabet.indexOf(secondSeq) + 10;
        } else {
            secondDec = Character.getNumericValue(secondSeq);
        }
        return (firstDec + secondDec);
    }
}

// Useful Sysos:
//System.out.println("THE FINAL HEX IS: " + hexSeq);
//System.out.println("The FirstVal /16 floored is: " + firstVal + "Before it was: " + inValue);
//System.out.println("FirstVal calculation: " + inValue / 16 + "- " + firstVal);
//System.out.println("SecondVal calculation: " + (((double)inValue)/16) + "- " + (int)((((double)inValue/16) - firstVal)*16));

//System.out.println("firstval: " + firstVal+ "in Alpha: " + alphabet.charAt(firstVal-10));
//System.out.println("FirstLetter: " + Integer.toString(firstVal).charAt(0)+ " Original: " + firstVal);
//System.out.println("I'm returning following Sequence: " + finalSeq);

//System.out.println("THE CHARS ARE: " + redSeq + " - " + greenSeq);
//System.out.println("THE CHARS 0: " + redSeq.charAt(0) + " -1: " + redSeq.charAt(1));
//System.out.println("THE CHARS in ALPHA: " + alphabet.indexOf(redSeq.charAt(0)) + " -: " + alphabet.indexOf(redSeq.charAt(1)));
//System.out.println("THE HEX I RECEIVED: " + hex + " So Green part would be: " + hex.charAt(3) + " And: " + hex.charAt(4));
//System.out.println("ALPHABET_CHAR_AT: " + alphabet.indexOf(firstSeq) + " FIRST SEQ: " + firstSeq);

//System.out.println("Original Sequence: " + redSeq +" green: " + greenSeq);
//System.out.println("Before: " + redSeq.charAt(0) + " - " + redSeq.charAt(1) + " After: " + colour.red);

//System.out.println("RIGHT NOW FIRSTDEC: " + firstDec + " - BECAUSE: " + firstSeq + " * 16 = " + firstDec);
//System.out.println("INTERESTING: " + firstDec + " SEC: " + secondDec);
