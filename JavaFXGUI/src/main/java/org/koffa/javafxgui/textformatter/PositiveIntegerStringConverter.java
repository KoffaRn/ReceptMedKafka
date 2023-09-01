package org.koffa.javafxgui.textformatter;

import javafx.util.converter.IntegerStringConverter;

public class PositiveIntegerStringConverter extends IntegerStringConverter {
    @Override
    public Integer fromString(String value) {
        int result = super.fromString(value);
        return Math.max(result, 0);
    }
    @Override
    public String toString(Integer value) {
        if (value < 0) {
            return "0";
        }
        return super.toString(value);
    }
}
