package com.dvo.lgs.enums;

/**
 * Created by Dennis van Opstal on 22-12-2017.
 */

public enum EmailDisplayOption {
    VISIBLE(0),
    INVISIBLE(1);

    private int option;

    EmailDisplayOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}