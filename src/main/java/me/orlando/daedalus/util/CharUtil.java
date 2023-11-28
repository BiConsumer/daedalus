package me.orlando.daedalus.util;

public final class CharUtil {

    public static char[] toPrimitive(Character[] characters) {
        char[] primitive = new char[characters.length];
        for (int i = 0; i < characters.length; i++) {
            primitive[i] = characters[i];
        }

        return primitive;
    }

}
