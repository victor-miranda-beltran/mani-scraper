package com.victormiranda.mani.scraper.util;

public final class Validate {

    private Validate() {}

    public static boolean notEmpty(final String s) {
        return s != null && s.trim().length() > 0;
    }

    public static boolean notEmpty(final String ... strings) {
        for (String string : strings) {
            if (!notEmpty(string)) {
                return  false;
            }
        }

        return true;
    }
}
